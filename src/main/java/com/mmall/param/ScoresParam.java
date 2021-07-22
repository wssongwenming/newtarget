package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Getter
@Setter
@ToString
public class ScoresParam {

    private Integer scoreIndex;

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

    private Float ringnumber;

    private String mac;

    private Float radius;

    private Float mmofradius;

    private String imagebase64;

}
