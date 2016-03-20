package com.tutorials.hackro.speedytaxi.Direction;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hackro on 20/03/16.
 */

public class OverviewPolyLine {

    @SerializedName("points")
    public String points;

    public String getPoints() {
        return points;
    }
}