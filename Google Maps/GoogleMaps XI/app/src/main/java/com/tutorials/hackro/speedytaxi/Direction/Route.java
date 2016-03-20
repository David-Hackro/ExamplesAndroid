package com.tutorials.hackro.speedytaxi.Direction;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hackro on 20/03/16.
 */

public class Route {
    @SerializedName("overview_polyline")
    private OverviewPolyLine overviewPolyLine;

    private List<Legs> legs;

    public OverviewPolyLine getOverviewPolyLine() {
        return overviewPolyLine;
    }

    public List<Legs> getLegs() {
        return legs;
    }
}