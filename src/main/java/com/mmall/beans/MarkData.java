package com.mmall.beans;

import com.mmall.model.Scores;

import java.util.List;

//主要和hole配合，完成每次有子弹上靶时，向display发送数据，同时向server发送数据
/* String markData="{\n" +
         "    \"mac\": \"DC4A3EE6B02F\", \n" +
         "    \"radius\": \"68.64\", \n" +
         "    \"mmOfRadius\": \"50.50\", \n" +
         "    \"holes\": [\n" +
         "        {\n" +
         "       \"id\":1,\n" +
         "       \"offset\":\"l\",\n" +
         "            \"px\": -342.53, \n" +
         "            \"py\": -235.55, \n" +
         "            \"mx\": -252.01, \n" +
         "            \"my\": -173.3, \n" +
         "            \"score\": 4\n" +
         "        }, \n" +
         "        {\n" +
         "      \"id\":2, \n" +
         "       \"offset\":\"r\",\n" +
         "            \"px\": 342.6, \n" +
         "            \"py\": -234.67, \n" +
         "            \"mx\": 252.06, \n" +
         "            \"my\": -172.65, \n" +
         "            \"score\": 4\n" +
         "        }, \n" +*/
public class MarkData {
    //code在display显示中并没有用到，而是特意添加为server显示中指示接受的websocket消息的类型，为markdata
    private int code;
    private String mac;
    private int dataType;//用来给app端指示为Markdata，MARK_DATA=5;
    private String radius;
    private String mmOfRadius;
    private List<Hole> holes;

    private List increasedRingNumbersAndOffset;//告诉app端新增加的环数和方向偏移用于声音播放
    //deviceGroupIndex,在display显示中并没有用到，而是特意添加为server显示中定位靶位用到的
    private int deviceGroupIndex;


    //主要用于在向server输送用于显示成绩表格中的数据
    private List<Scores> shootingScoreList;
    private Float totalScore;


    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getMmOfRadius() {
        return mmOfRadius;
    }

    public void setMmOfRadius(String mmOfRadius) {
        this.mmOfRadius = mmOfRadius;
    }

    public List<Hole> getHoles() {
        return holes;
    }

    public void setHoles(List<Hole> holes) {
        this.holes = holes;
    }

    public int getDeviceGroupIndex() {
        return deviceGroupIndex;
    }

    public void setDeviceGroupIndex(int deviceGroupIndex) {
        this.deviceGroupIndex = deviceGroupIndex;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Scores> getShootingScoreList() {
        return shootingScoreList;
    }

    public void setShootingScoreList(List<Scores> shootingScoreList) {
        this.shootingScoreList = shootingScoreList;
    }

    public Float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Float totalScore) {
        this.totalScore = totalScore;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public List getIncreasedRingNumbersAndOffset() {
        return increasedRingNumbersAndOffset;
    }

    public void setIncreasedRingNumbersAndOffset(List increasedRingNumbersAndOffset) {
        this.increasedRingNumbersAndOffset = increasedRingNumbersAndOffset;
    }
}
