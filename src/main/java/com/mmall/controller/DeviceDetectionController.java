package com.mmall.controller;

import com.alibaba.fastjson.JSONObject;
import com.mmall.beans.*;

import com.mmall.common.JsonData;
import com.mmall.config.SpringWebSocketHandler;
import com.mmall.model.Camera;
import com.mmall.model.Device_Group;
import com.mmall.model.Display;
import com.mmall.model.Target;
import com.mmall.param.DisplayParam;
import com.mmall.rabbitmq.MessageProducer;
import com.mmall.service.CameraService;
import com.mmall.service.DeviceGroupService;
import com.mmall.service.DisplayService;
import com.mmall.service.TargetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("sys/device")
@Slf4j
public class DeviceDetectionController {
    @Bean//这个注解会从Spring容器拿出Bean
    public SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }
    @Resource
    private DisplayService displayService;

    @Resource
    private TargetService targetService;
    @Resource
    private MessageProducer messageProducer;
    @Resource
    private CameraService cameraService;

    @Resource
    private DeviceGroupService deviceGroupService;
    @RequestMapping("/devicedetection.json")
    @ResponseBody
    public void deviceDetection() throws ParseException {
        List<Device_Group> deviceGroupList=deviceGroupService.getAll().getData();
        for(int i=0;i<deviceGroupList.size();i++)
        {
            int deviceGroupIndex=i+1;//靶位编号
            Device_Group device_group=deviceGroupList.get(i);
            int displayIndex=device_group.getDisplayId();
            int cameraIndex=device_group.getCameraId();
            int targetIndex=device_group.getTargetId();

            Display display=displayService.getDisplayByIndex(displayIndex);
            Camera camera  =cameraService.getCameraByIndex(cameraIndex);
            Target target  =targetService.getTargetByIndex(targetIndex);

            String displayMac="";
            String cameraIP="";
            String targetIP="";
            if(display!=null)
            {
                DisplayParam displayParam=new DisplayParam();
                displayParam.setId(display.getId());
                displayParam.setDevice_index(display.getDevice_index());
                displayParam.setStatus(1);//先将设备设置为异常，然后消息队列返回信息后再设置为正常
                displayParam.setIp(display.getIp());
                displayParam.setMac(display.getMac());
                displayParam.setMemo(display.getMemo());
                displayParam.setName(display.getName());
                displayParam.setNumber(display.getNumber());
                displayService.update(displayParam);
                displayMac=display.getMac();
                Command statusCommand=new Command();
                statusCommand.setDataType(ServerCommand.DEVICESTATUS);
                statusCommand.setCode(0);
                statusCommand.setMessage("询问Display设备状态");
                DeviceInfo deviceInfo=new DeviceInfo();
                deviceInfo.setDeviceGroupIndex(deviceGroupIndex);
                deviceInfo.setDeviceType(DeviceType.DISPLAY);
                deviceInfo.setDeviceIndex(displayIndex);
                deviceInfo.setDeviceId(display.getId());

                statusCommand.setData(deviceInfo);
                JSONObject jo= (JSONObject) JSONObject.toJSON(statusCommand);
                messageProducer.sendTopicMessage("server-to-display-exchange","server-to-display-routing-key-"+displayMac,jo.toJSONString());

                JSONObject traineeJson=new JSONObject();
                JSONObject data=new JSONObject();
                traineeJson.put("code",2);//code=0用户的状态数据（未登陆，已登陆．正在射击）,(code=1打靶数据),code=2：代表设备的状态
                data.put("targetIndex",deviceGroupIndex);
                data.put("deviceStatus", DisplayStatus.ERROR);
                data.put("deviceType", DeviceType.DISPLAY);
                traineeJson.put("data",data);
                infoHandler().sendMessageToUsers( new TextMessage(traineeJson.toJSONString())) ;

            }
            if(camera!=null){
                JSONObject traineeJson=new JSONObject();
                JSONObject data=new JSONObject();
                traineeJson.put("code",2);//code=0用户的状态数据（未登陆，已登陆．正在射击）,(code=1打靶数据),code=2：代表设备的状态
                data.put("targetIndex",deviceGroupIndex);
                data.put("deviceStatus", CameraStatus.ERROR);
                data.put("deviceType", DeviceType.CARMER);
                traineeJson.put("data",data);
                infoHandler().sendMessageToUsers( new TextMessage(traineeJson.toJSONString())) ;
                cameraIP=camera.getIp();
                if(checkIpStatus(cameraIP))
                {
                    JSONObject traineeJsonnew=new JSONObject();
                    JSONObject datanew=new JSONObject();
                    traineeJsonnew.put("code",2);//code=0用户的状态数据（未登陆，已登陆．正在射击）,(code=1打靶数据),code=2：代表设备的状态
                    datanew.put("targetIndex",deviceGroupIndex);
                    datanew.put("deviceStatus", CameraStatus.NORMAL);
                    datanew.put("deviceType", DeviceType.CARMER);
                    traineeJsonnew.put("data",datanew);
                    infoHandler().sendMessageToUsers( new TextMessage(traineeJsonnew.toJSONString())) ;

                }


            }
            if(target!=null){
                JSONObject traineeJson=new JSONObject();
                JSONObject data=new JSONObject();
                traineeJson.put("code",2);//code=0用户的状态数据（未登陆，已登陆．正在射击）,(code=1打靶数据),code=2：代表设备的状态
                data.put("targetIndex",deviceGroupIndex);
                data.put("deviceStatus", TargetStatus.CONNECT_ERROR);
                data.put("deviceType", DeviceType.TARGET);
                traineeJson.put("data",data);
                infoHandler().sendMessageToUsers( new TextMessage(traineeJson.toJSONString())) ;

                targetIP=target.getIp();

                if(checkIpStatus(targetIP))
                {
                    JSONObject traineeJsonnew=new JSONObject();
                    JSONObject datanew=new JSONObject();
                    traineeJsonnew.put("code",2);//code=0用户的状态数据（未登陆，已登陆．正在射击）,(code=1打靶数据),code=2：代表设备的状态
                    datanew.put("targetIndex",deviceGroupIndex);
                    datanew.put("deviceStatus", TargetStatus.CONNECT_NORMAL);
                    datanew.put("deviceType", DeviceType.TARGET);
                    traineeJsonnew.put("data",datanew);
                    infoHandler().sendMessageToUsers( new TextMessage(traineeJsonnew.toJSONString())) ;

                }

            }
        }

    }
    public static boolean checkIpStatus(String ipAddress) {
        boolean reachable = false;
        try {
            reachable = InetAddress.getByName(ipAddress).isReachable(100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reachable;
    }
}
