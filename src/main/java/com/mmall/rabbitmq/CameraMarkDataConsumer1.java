package com.mmall.rabbitmq;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mmall.beans.Hole;
import com.mmall.beans.MarkData;
import com.mmall.beans.ServerCommand;
import com.mmall.config.SpringWebSocketHandler;
import com.mmall.model.*;
import com.mmall.param.ScoresParam;
import com.mmall.service.*;
import com.mmall.util.Circle;
import com.mmall.util.Point2D;
import com.mmall.util.Rect;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CameraMarkDataConsumer1 implements ChannelAwareMessageListener {
    @Bean//这个注解会从Spring容器拿出Bean
    public SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }
    @Resource
    private AmqpTemplate amqpTemplate;
    @Resource
    private CameraService cameraService;
    @Resource
    private ScoresService scoresService;
    @Resource
    private TraineeGroupService traineeGroupService;
    @Resource
    private DisplayService displayService;
    @Resource
    private DeviceGroupService deviceGroupService;
    @Resource
    private MessageProducer messageProducer;

    private List<Object> increasedRingNumbersAndOffset=new ArrayList();
    public CameraMarkDataConsumer1(){
    }
    /**
     * 判断点是否在多边形内
     * @param point 检测点
     * @param pts   多边形的顶点
     * @return      点在多边形内返回true,否则返回false
     */
    public  boolean IsPtInPoly(Point2D point, List<Point2D> pts){

        int N = pts.size();
        boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
        int intersectCount = 0;//cross points count of x
        double precision = 2e-10; //浮点类型计算时候与0比较时候的容差
        Point2D p1, p2;//neighbour bound vertices
        Point2D p = point; //当前点

        p1 = pts.get(0);//left vertex
        for(int i = 1; i <= N; ++i){//check all rays
            if(p.equals(p1)){
                return boundOrVertex;//p is an vertex
            }

            p2 = pts.get(i % N);//right vertex
            if(p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)){//ray is outside of our interests
                p1 = p2;
                continue;//next ray left point
            }

            if(p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)){//ray is crossing over by the algorithm (common part of)
                if(p.y <= Math.max(p1.y, p2.y)){//x is before of ray
                    if(p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)){//overlies on a horizontal ray
                        return boundOrVertex;
                    }

                    if(p1.y == p2.y){//ray is vertical
                        if(p1.y == p.y){//overlies on a vertical ray
                            return boundOrVertex;
                        }else{//before ray
                            ++intersectCount;
                        }
                    }else{//cross point on the left side
                        double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;//cross point of y
                        if(Math.abs(p.y - xinters) < precision){//overlies on a ray
                            return boundOrVertex;
                        }

                        if(p.y < xinters){//before ray
                            ++intersectCount;
                        }
                    }
                }
            }else{//special case when ray is crossing through the vertex
                if(p.x == p2.x && p.y <= p2.y){//p crossing over p2
                    Point2D p3 = pts.get((i+1) % N); //next vertex
                    if(p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)){//p.x lies between p1.x & p3.x
                        ++intersectCount;
                    }else{
                        intersectCount += 2;
                    }
                }
            }
            p1 = p2;//next ray left point
        }

        if(intersectCount % 2 == 0){//偶数在多边形外
            return false;
        } else { //奇数在多边形内
            return true;
        }

    }

    /**
     * 判断是否在圆形内
     * @param p
     * @param c
     * @return
     */
    public boolean distencePC(Point2D p, Circle c){//判断点与圆心之间的距离和圆半径的关系
        boolean s=false ;
        double d2 = Math.hypot( (p.getX() - c.getCC().getX() ), (p.getY() - c.getCC().getY()) );
        System.out.println("d2=="+d2);
        double r = c.getR();
        if(d2 > r){
            s = false;
        }else if(d2 <= r){
            s = true;
        }
        return s;
    }

    private double ringNumber(List<Point2D> vertexList,double r){

        double R = Math.hypot(vertexList.get(0).getX(), vertexList.get(0).getY() );//返回平方根
        for (int i=0;i<vertexList.size();i++)
        {
            double RR = Math.hypot(vertexList.get(i).getX(), vertexList.get(i).getY() );//返回平方根
            if(RR<R){
                R=RR;
            }
        }
        double ringNumber= (11-R/r);
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(1);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        String ringNumber1=formater.format(ringNumber);
        ringNumber =Double.valueOf(ringNumber1.toString());
        return  ringNumber;
    }
    private String getOffset(double px,double py){
        double PY=-py;//传回来的数据是向右、向下为正，为了求方位角需要变回向右，向上为正
        double PX=px;
        String offset="";

        double Number = Math.atan2(PY, PX);
        double theta = Number*(180/Math.PI);
        if(theta>-22.5&&theta<=22.5){
            offset="r";
        }else if(theta>22.5&&theta<=67.5)
        {
            offset="ru";
        }else if(theta>67.5&&theta<=112.5)
        {
            offset="u";
        }else if(theta>112.5&&theta<=157.5)
        {
            offset="lu";
        }else if(theta>157.5&&theta<=180)
        {
            offset="l";
        }else if(theta>=-180&&theta<=-157.5)
        {
            offset="l";
        }else if(theta>-157.5&&theta<=-112.5)
        {
            offset="ld";
        }else if(theta>-112.5&&theta<=-67.5)
        {
            offset="d";
        }else if(theta>-67.5&&theta<=-22.5)
        {
            offset="rd";
        }
        return offset;
    }

    /**
     *  计算环数
     * @param pts 构成靶面 直线区域的点，但是少了一个圆环部分
     * @param firstConjunction，顶部直线和圆形的第一个交叉点
     * @param secondConjunction，第二个交叉点
     * @param  centerPoint 圆心部分的坐标
     * @param r 顶部圆弧的半径
     * @param point 等待是否有效的点
     * @return
     */
    private boolean isInvalidArea(List<Point2D> pts,Point2D firstConjunction,Point2D secondConjunction,Point2D centerPoint,Double r,Point2D point){
        boolean isInPolygon=false;
        boolean isInArc=false;
        Circle c = new Circle();
        c.setCC(centerPoint);
        c.setR(r);//
        Double x1=firstConjunction.getX();
        Double x2=secondConjunction.getX();
//      判断点在点除去顶弧构成的有效区
        isInPolygon=IsPtInPoly(point, pts);
        //如果不在点区域内看是否在顶弧区域
        if(!isInPolygon)
        {
            if(point.getX()<=x2&&point.getX()>=x1)
            {
                isInArc=distencePC(point,c);
            }
        }
        return (isInArc||isInPolygon);
    }

    private float compute_iou(Rect rect1, Rect rect2){
        float leftColumnMax = Math.max(rect1.getLx(), rect2.getLx());
        float rightColumnMin = Math.min(rect1.getRx(),rect2.getRx());
        float upRowMax = Math.max(rect1.getLy(), rect2.getLy());
        float downRowMin = Math.min(rect1.getRy(),rect1.getRy());

        if (leftColumnMax>=rightColumnMin || downRowMin<=upRowMax){
            return 0;
        }

        float s1 = (rect1.getRx()-rect1.getLx())*(rect1.getRy()-rect1.getLy());
        float s2 = (rect2.getRx()-rect2.getLx())*(rect2.getRy()-rect2.getLy());
        float sCross = (downRowMin-upRowMax)*(rightColumnMin-leftColumnMax);
        return sCross/(s1+s2-sCross);
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        increasedRingNumbersAndOffset.clear();
        int shootingStatus=0;
        try {
            shootingStatus = traineeGroupService.getShootingStatus();
        }catch (Exception e)
        {}
        if(shootingStatus==0) {//如果当前已经单击了“开始射击”按钮,表示可以接受camer端返回的数据了
            //识靶终端发回的坐标是以398*396尺寸发挥的，以下为该尺寸靶子的有效直线区域
            List<Point2D> pts = new ArrayList<Point2D>();
            pts.add(new Point2D(0, 396));
            pts.add(new Point2D(0, 205.92));
            pts.add(new Point2D(110.32, 142.56));
            pts.add(new Point2D(107.46, 102.96));
            pts.add(new Point2D(119.4, 23.76));
            pts.add(new Point2D(278.6, 23.76));
            pts.add(new Point2D(290.54, 102.96));
            pts.add(new Point2D(287.67, 142.56));
            pts.add(new Point2D(398, 205.92));
            pts.add(new Point2D(398, 396));

            //两个交叉点
            Point2D firstConjunction = new Point2D(119.4, 23.76);
            Point2D secondConjunction = new Point2D(278.6, 23.76);
            //靶顶圆弧圆心
            Point2D centerPoint = new Point2D(199, 142.56);
            //顶部圆弧的半径
            Double r = 142.56;
            //靶心的坐标，用于变换坐标，将以靶心为坐标原点的坐标变成以左上角为坐标原点的坐标
            Point2D bullEyePoint = new Point2D(199, 236.8);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 数据是从camera过来的，需要从数据中提取出mac->找到设备编号->靶位编组编号-〉靶位编组中的display的MAC-〉
            // 同时提取射击组中对应的traineeid，把成绩归到该人名下
            try {
                String messageStr = new String(message.getBody(), "UTF-8");
                JSONObject messageJson = JSONObject.parseObject(messageStr);
                String camerMac = messageJson.getString("mac");
                String imagebase64=messageJson.getString("imagebase64");
                Double radius = messageJson.getDouble("radius");
                String mmOfRadius = messageJson.getString("mmOfRadius");
                JSONArray holes = messageJson.getJSONArray("holes");
                Camera camera = cameraService.getCameraByMac(camerMac);
                int cameraIndex = -1;
                if (camera != null) {
                    cameraIndex = camera.getDevice_index();
                }
                Device_Group device_group = deviceGroupService.getDeviceGroupByCameraIndex(cameraIndex);
                int displayIndex = -1;
                int deviceGroupIndex = -1;
                if (device_group != null) {
                    displayIndex = device_group.getDisplayId();
                    deviceGroupIndex = device_group.getGroupIndex();
                }
                String displayMac = null;
                Display display = displayService.getDisplayByIndex(displayIndex);
                if (display != null) {
                    displayMac = display.getMac();
                }
                Trainee_Group traineeGroupInShooting = traineeGroupService.getTraineeGroupInShooting();
                String traineeIdsInShooting = traineeGroupInShooting.getTraineeIds();
                if (traineeIdsInShooting != null) {
                    String[] traineeIdsArray = traineeIdsInShooting.split(",");
                    int traineeId = Integer.parseInt(traineeIdsArray[deviceGroupIndex - 1].trim());


                    int scoreCount = scoresService.getCount(traineeId);
                    int scoreCountInJson = holes.size();

                    // 新加的，
                    //if (scoreCountInJson > scoreCount) {//如果返回的json中有增加的弹孔数据再删除旧的
                    //  scoresService.deleteByTraineeId(traineeId);
                    //}
                    //新加的结束

                    //if(scoreCountInJson>scoreCount) {//新加一行代码**************
                    for (int i = 0; i < scoreCountInJson; i++) {
                        JSONObject newScore = holes.getJSONObject(i);
                        Double px = newScore.getJSONObject("center").getDouble("px");//得到识靶终端的px，py，这时数据是以靶心为坐标原点的，所以为了完成各项检
                        // 测需要转换为屏幕坐标，以左上角为坐标原点
                        Double py = newScore.getJSONObject("center").getDouble("py");
                        //将以靶心为原点的坐标转换为了以左上角为参考点的坐标,
                        Double PX = px + bullEyePoint.getX();
                        Double PY = py + bullEyePoint.getY();
//                      取得每个弹孔的中心坐标，后面要判断是否处于有效区域
                        Point2D point = new Point2D(PX, PY);
//                      vertexList存储每个弹孔bbox的四个顶点
                        List<Point2D> vertexList = new ArrayList<>();
                        JSONArray bbox = newScore.getJSONArray("bbox");
                        ScoresParam scoresParam = new ScoresParam();
//                      bbox的数据是弹孔包围框的顶点（px,py）顺时针列表
                        for (int j = 0; j < bbox.size(); j++) {
                            JSONObject vertex = bbox.getJSONObject(j);
                            Double vertex_x = vertex.getDouble("px");
                            Double vertex_y = vertex.getDouble("py");
                            Point2D vertex_point = new Point2D(vertex_x, vertex_y);
                            vertexList.add(vertex_point);
                            if (j == 0) {//取得左上角坐标准备入库
                                scoresParam.setLx(vertex.getFloat("px"));
                                scoresParam.setLy(vertex.getFloat("py"));
                            }
                            if (j == 2) {//取得右下角坐标准备入库
                                scoresParam.setRx(vertex.getFloat("px"));
                                scoresParam.setRy(vertex.getFloat("py"));
                            }
                        }


                        String now = df.format(System.currentTimeMillis());
                        scoresParam.setHittingTime(df.parse(now));
                        scoresParam.setMac(camerMac);
                        scoresParam.setMmofradius(0.0f);
                        //scoresParam.setMx(newScore.getFloat("mx"));
                        scoresParam.setMx(0.0f);//原数据没有传过来，该项，所以暂时设置为0
                        //scoresParam.setMy(newScore.getFloat("my"));
                        scoresParam.setMy(0.0f);//
                        String offset = getOffset(px, py);//px,py现在还是
                        scoresParam.setOffset(offset);
                        //px,py类型float和double不匹配，再取一遍
                        scoresParam.setPx(newScore.getJSONObject("center").getFloat("px"));
                        scoresParam.setPy(newScore.getJSONObject("center").getFloat("py"));
                        scoresParam.setRadius(Float.valueOf(String.valueOf(radius)));
                        scoresParam.setImagebase64(imagebase64);
//                      vertexList为一个弹孔的四个顶点
                        Double ringNumber = ringNumber(vertexList, radius);//radius应该是39.8,如果不是和前面的数据会有问题
                        if (isInvalidArea(pts, firstConjunction, secondConjunction, centerPoint, r, point))//如果在有效区域内计算环数
                        {
                            scoresParam.setRingnumber(Float.valueOf(String.valueOf(ringNumber)));
                        } else {
                            scoresParam.setRingnumber(0.0f);
                        }
                        double iou = 0.0;
                        List<Scores> scoresListold = scoresService.getScoresByTraineeId(traineeId);
                        //n为库中已有的数据量
                        int n = scoresListold.size();
                        float LX = scoresParam.getLx();
                        float LY = scoresParam.getLy();
                        float RX = scoresParam.getRx();
                        float RY = scoresParam.getRy();
                        Rect rectnew = new Rect();
                        rectnew.setLx(LX);
                        rectnew.setLy(LY);
                        rectnew.setRx(RX);
                        rectnew.setRy(RY);
                        for (int m = 0; m < n; m++) {
                            Scores scores = scoresListold.get(m);
                            float lx = scores.getLx();
                            float ly = scores.getLy();
                            float rx = scores.getRx();
                            float ry = scores.getRy();
                            Rect rectold = new Rect();
                            rectold.setLx(lx);
                            rectold.setLy(ly);
                            rectold.setRx(rx);
                            rectold.setRy(ry);
                            float IOU = compute_iou(rectnew, rectold);
                            if (IOU > iou) {
                                iou = IOU;
                            }
                        }
                        scoresParam.setScoreIndex(n + 1);
                        scoresParam.setTraineeId(traineeId);
                        if (iou < 0.02) {
                            scoresService.save(scoresParam);
                            increasedRingNumbersAndOffset.add((int)(Double.parseDouble(scoresParam.getRingnumber()+"")));
                            increasedRingNumbersAndOffset.add(offset);

                        }
                    }//将最近收到的数据保存完毕，下面统一取出，装配发送给display和server的数据
                    List<Scores> scoresList = scoresService.getScoresByTraineeId(traineeId);
                    Float totalScore = getTotalScore(scoresList);
                    MarkData markData = new MarkData();
                    String mac = "";//没有用到其实
                    String radius1 = "";
                    String mmOfRadius1 = "";
                    List<Hole> holes1 = new ArrayList<>();
                    for (int i = 0; i < scoresList.size(); i++) {
                        Scores scores1 = scoresList.get(i);
                        Hole hole = new Hole();
                        if (scores1 != null) {
                            hole.setId(scores1.getScoreIndex());
                            hole.setMx(scores1.getMx());
                            hole.setMy(scores1.getMy());
                            hole.setOffset(scores1.getOffset());
                            hole.setPx(scores1.getPx());
                            hole.setPy(scores1.getPy());
                            // 以前取环数是带小数点的，现在取整数
//                          hole.setScore(scores1.getRingnumber());
                            hole.setScore((int)Double.parseDouble(scores1.getRingnumber()+""));

                            hole.setShootingTime(df.format(scores1.getHittingTime()));
                            //TODO应该只取一次取到即可，但这里取了多次,
                            mac = scores1.getMac();
                            radius1 = String.valueOf(scores1.getRadius());
                            mmOfRadius1 = String.valueOf(scores1.getMmofradius());
                        }
                        holes1.add(hole);
                    }
                    markData.setHoles(holes1);//App端用于显示，server端也使用
                    markData.setTotalScore(totalScore);//server端用于显示成绩列表中的总成绩
                    markData.setMac(mac);
                    markData.setDeviceGroupIndex(deviceGroupIndex);
                    markData.setRadius(radius1);
                    markData.setMmOfRadius(mmOfRadius1);
                    markData.setIncreasedRingNumbersAndOffset(increasedRingNumbersAndOffset);
                    markData.setCode(1);//告诉ｓｅｒｖｅｒ现在打靶成绩的显示
                    markData.setDataType(ServerCommand.MARK_DATA);
                    JSONObject jo = (JSONObject) JSONObject.toJSON(markData);
                    String markDataStr = jo.toJSONString();
                    System.out.print(markDataStr);
                    if(increasedRingNumbersAndOffset.size()>0) {
                        //messageProducer.sendTopicMessage("server-to-other-exchange","server-to-display-markdata-routing-key-"+displayMac,markDataStr );
                        messageProducer.sendTopicMessage("server-to-display-exchange", "server-to-display-routing-key-" + displayMac, markDataStr);
//                        long delieverTag = message.getMessageProperties().getDeliveryTag();
//                        channel.basicAck(delieverTag, false);//TODO:应该为false
                        //向server发送新的射击websocket消息
                        infoHandler().sendMessageToUsers(new TextMessage(markDataStr));
                    }
//                    long delieverTag = message.getMessageProperties().getDeliveryTag();
//                    channel.basicAck(delieverTag, false);//TODO:应该为false
                }

                //}
                System.out.print("tag========" + message.getMessageProperties().getDeliveryTag());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
//                long delieverTag = message.getMessageProperties().getDeliveryTag();
//                channel.basicAck(delieverTag, false);//TODO:应该为false
            }
        }
        long delieverTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(delieverTag, false);//TODO:应该为false
    }

 /*   @Override
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
        try {
            System.out.print("===endDat===="+ new String(message.getBody(),"UTF-8"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    //以前计算总成绩为计算小数点，现在只取整数
    public float getTotalScore(List<Scores> scoresList) {

//        float totalScore=0.0f;
        int totalScore=0;
        // 去掉重复的key
        for (Scores scores : scoresList) {
            totalScore=totalScore+(int)Double.parseDouble(scores.getRingnumber()+"");
        }
        //NumberFormat nf=new DecimalFormat( "0.0 ");
        //totalScore = (float) Double.parseDouble(nf.format(totalScore));
        return totalScore;
    }

}