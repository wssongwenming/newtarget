package com.mmall.beans;

import com.mmall.model.Scores;

import java.util.List;

public class TraineeShooting_DeviceGroup_Data {
    //靶位编号
    private int deviceGroupIndex;
    //射击人员信息
    private String name;
    private int traineeStatus;
    private String photo;
    private List<Scores> shootingScoreList;
//    private Float totalScore;
    private int totalScore;
    //靶位设备信息
    private int displayStatus;
    private int cameraStatus;
    private int targetStatus;

    //靶位信息，在用或停用

    private int deviceGroupStatus;

    public int getDeviceGroupIndex() {
        return deviceGroupIndex;
    }

    public void setDeviceGroupIndex(int deviceGroupIndex) {
        this.deviceGroupIndex = deviceGroupIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTraineeStatus() {
        return traineeStatus;
    }

    public void setTraineeStatus(int traineeStatus) {
        this.traineeStatus = traineeStatus;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<Scores> getShootingScoreList() {
        return shootingScoreList;
    }

    public void setShootingScoreList(List<Scores> shootingScoreList) {
        this.shootingScoreList = shootingScoreList;
    }

    public int getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(int displayStatus) {
        this.displayStatus = displayStatus;
    }

    public int getCameraStatus() {
        return cameraStatus;
    }

    public void setCameraStatus(int cameraStatus) {
        this.cameraStatus = cameraStatus;
    }

    public int getTargetStatus() {
        return targetStatus;
    }

    public void setTargetStatus(int targetStatus) {
        this.targetStatus = targetStatus;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getDeviceGroupStatus() {
        return deviceGroupStatus;
    }

    public void setDeviceGroupStatus(int deviceGroupStatus) {
        this.deviceGroupStatus = deviceGroupStatus;
    }
}
