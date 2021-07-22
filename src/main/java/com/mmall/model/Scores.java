package com.mmall.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Scores {

    private Integer id;

    private Integer scoreIndex;

    private Integer trainingId;

    private Integer traineeId;

    private Date hittingTime;

    private Float px;

    private Float py;

    private Float mx;

    private Float my;

    private Float lx;

    private Float ly;

    private Float rx;

    private Float ry;

    private String offset;
//    员环数计算小数点
//    private Float ringnumber;
    private int ringnumber;

    private String mac;

    private Float radius;

    private Float mmofradius;

    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScoreIndex() {
        return scoreIndex;
    }

    public void setScoreIndex(Integer scoreIndex) {
        this.scoreIndex = scoreIndex;
    }

    public Integer getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Integer traineeId) {
        this.traineeId = traineeId;
    }

    public Date getHittingTime() {
        return hittingTime;
    }

    public void setHittingTime(Date hittingTime) {
        this.hittingTime = hittingTime;
    }

    public Float getPx() {
        return px;
    }

    public void setPx(Float px) {
        this.px = px;
    }

    public Float getPy() {
        return py;
    }

    public void setPy(Float py) {
        this.py = py;
    }

    public Float getMx() {
        return mx;
    }

    public void setMx(Float mx) {
        this.mx = mx;
    }

    public Float getMy() {
        return my;
    }

    public void setMy(Float my) {
        this.my = my;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset == null ? null : offset.trim();
    }

    public int getRingnumber() {
        return ringnumber;
    }

    public void setRingnumber(int ringnumber) {
        this.ringnumber = ringnumber;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac == null ? null : mac.trim();
    }

    public Float getRadius() {
        return radius;
    }

    public void setRadius(Float radius) {
        this.radius = radius;
    }

    public Float getMmofradius() {
        return mmofradius;
    }

    public void setMmofradius(Float mmofradius) {
        this.mmofradius = mmofradius;
    }

    public Float getLx() {
        return lx;
    }

    public void setLx(Float lx) {
        this.lx = lx;
    }

    public Float getLy() {
        return ly;
    }

    public void setLy(Float ly) {
        this.ly = ly;
    }

    public Float getRx() {
        return rx;
    }

    public void setRx(Float rx) {
        this.rx = rx;
    }

    public Float getRy() {
        return ry;
    }

    public void setRy(Float ry) {
        this.ry = ry;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Integer trainingId) {
        this.trainingId = trainingId;
    }
}