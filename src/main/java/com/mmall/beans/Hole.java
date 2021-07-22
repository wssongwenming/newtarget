package com.mmall.beans;

//每个弹孔的数据

/*
{\n" +
        "       \"id\":1,\n" +
        "       \"offset\":\"l\",\n" +
        "            \"px\": -342.53, \n" +
        "            \"py\": -235.55, \n" +
        "            \"mx\": -252.01, \n" +
        "            \"my\": -173.3, \n" +
        "            \"score\": 4\n" +
        "        },
*/

public class Hole {
    private int id;
    private String offset;
    private float px;
    private float py;
    private float mx;
    private float my;
//    private float score;
    private int score;
    private String shootingTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public float getPx() {
        return px;
    }

    public void setPx(float px) {
        this.px = px;
    }

    public float getPy() {
        return py;
    }

    public void setPy(float py) {
        this.py = py;
    }

    public float getMx() {
        return mx;
    }

    public void setMx(float mx) {
        this.mx = mx;
    }

    public float getMy() {
        return my;
    }

    public void setMy(float my) {
        this.my = my;
    }

//    public float getScore() {
//        return score;
//    }
//
//    public void setScore(float score) {
//        this.score = score;
//    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getShootingTime() {
        return shootingTime;
    }

    public void setShootingTime(String shootingTime) {
        this.shootingTime = shootingTime;
    }
}
