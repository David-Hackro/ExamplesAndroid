package com.tutorials.hackro.speedytaxi.Direction;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hackro on 20/03/16.
 */
public class DirectionResults {
    @SerializedName("routes")
    private List<Route> routes;

    public List<Route> getRoutes() {
        return routes;
    }}

