package com.mmall.socket;

import com.alibaba.fastjson.JSONObject;
import com.mmall.beans.DeviceType;
import com.mmall.config.SpringWebSocketHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client2 {
    @Bean//这个注解会从Spring容器拿出Bean
    public SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }
    public Client2(String host, int port, int deviceGroupIndex) throws Exception{
        this.socket = new Socket(host, port);
        this.getSocket().setOOBInline(true);
        this.deviceGroupIndex=deviceGroupIndex;
        System.out.print("client[port:" + socket.getLocalPort() + "]与服务器端建立连接");
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream in = null;
                int deviceStatus = 0;
                String ret=null;
                String backinfo=null;
                while (!socket.isClosed()) {
                    try {
                        in = socket.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    byte[] buf = new byte[1024];

                    try {
                        in.read(buf);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    backinfo = bytesToHexString(buf);
                    ret=getLastFeedBack(backinfo);
                    if (ret.equalsIgnoreCase("00")) {
                        try {
                            socket.shutdownInput();
                            socket.shutdownOutput();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(ret.contains("21")) {
                        deviceStatus = 2;
                    }
                    else  if (ret.contains("22")) {
                        deviceStatus = 3;
                    }
                    else  if (ret.contains("31")) {
                        deviceStatus = 4;
                    }
                    else  if (ret.contains("32")) {
                        deviceStatus = 5;
                    }
//                                try {
//                                    currentThread().sleep(200);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
                    JSONObject traineeJsonnew = new JSONObject();
                    JSONObject datanew = new JSONObject();
                    traineeJsonnew.put("code", 2);//code=0用户的状态数据（未登陆，已登陆．正在射击）,(code=1打靶数据),code=2：代表设备的状态
                    datanew.put("targetIndex", deviceGroupIndex);
                    datanew.put("deviceStatus", deviceStatus);
                    datanew.put("deviceType", DeviceType.TARGET);
                    traineeJsonnew.put("data", datanew);
                    infoHandler().sendMessageToUsers(new TextMessage(traineeJsonnew.toJSONString()));
                }
            }

        }).start();

    }

    public String getLastFeedBack(String info){
        String[]rets=info.split(" ");
        int len=rets.length;
        String ret="00";
        for (int i=0;i<len;i++){
            String temp=rets[i];
            if(!temp.equalsIgnoreCase("00")&&(!ret.equalsIgnoreCase(temp))){
                ret=temp;
            }
        }
        return ret;
    }

    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket client) {
        this.socket = client;
    }
    private int deviceGroupIndex;
    private Socket socket;
    public void send(String cmd)throws Exception{
//      InputStream in = null;
//      String ret=null;
        OutputStream out = socket.getOutputStream();
        byte[] hexStrToByteArrs = hexStrToByteArrs(cmd);
        out.write(hexStrToByteArrs);
        out.flush();
//        while (socket.isConnected()) {
//            String aa=Thread.currentThread().getName();
//            in = socket.getInputStream();
//            byte[] buf = new byte[1];
//
//            in.read(buf);
//            ret = bytesToHexString(buf);
//            int deviceStatus = 0;
//            if (ret.contains("21")) {
//                deviceStatus = 2;
//            }
//            if (ret.contains("22")) {
//                deviceStatus = 3;
//            }
//            if (ret.contains("31")) {
//                deviceStatus = 4;
//            }
//            if (ret.contains("32")) {
//                deviceStatus = 5;
//            }
//
//            JSONObject traineeJsonnew = new JSONObject();
//            JSONObject datanew = new JSONObject();
//            traineeJsonnew.put("code", 2);//code=0用户的状态数据（未登陆，已登陆．正在射击）,(code=1打靶数据),code=2：代表设备的状态
//            datanew.put("targetIndex", deviceGroupIndex);
//            datanew.put("deviceStatus", deviceStatus);
//            datanew.put("deviceType", DeviceType.TARGET);
//            traineeJsonnew.put("data", datanew);
//            infoHandler().sendMessageToUsers(new TextMessage(traineeJsonnew.toJSONString()));
//        }
    }
    public void send1(String cmd){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OutputStream out = socket.getOutputStream();
                    byte[] hexStrToByteArrs = hexStrToByteArrs(cmd);
                    out.write(hexStrToByteArrs);
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
//    public String queryState(String cmd)throws Exception{
//        InputStream in = null;
//        OutputStream out = socket.getOutputStream();
//        byte[] hexStrToByteArrs = hexStrToByteArrs(cmd);
//        out.write(hexStrToByteArrs);
//        out.flush();
//        in = socket.getInputStream();
//        byte[] buf = new byte[1];
//        in.read(buf);
//        String ret = bytesToHexString(buf);
//        return ret;
//
//    }

    public void stop(){
        try{
            if(socket!=null){
                socket.close();
                socket=null;
            }
        }catch (Exception e)
        {

        }
    }

    /**
     * 将十六进制的字符串转换成字节数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStrToByteArrs(String hexString) {
        if (StringUtils.isEmpty(hexString)) {
            return null;
        }

        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        int index = 0;

        byte[] bytes = new byte[len / 2];

        while (index < len) {
            String sub = hexString.substring(index, index + 2);
            bytes[index / 2] = (byte) Integer.parseInt(sub, 16);
            index += 2;
        }

        return bytes;
    }

    /**
     * 数组转换成十六进制字符串
     *
     * @return HexString
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
            // 在这里故意追加一个逗号便于最后的区分
            sb.append(" ");
        }
        return sb.toString();
    }
}
