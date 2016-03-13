package com.example.david.googlemapsix;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by david on 7/03/16.
 */
public class MyItem implements ClusterItem, com.google.maps.android.clustering.ClusterItem {
    private final LatLng mPosition;

    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}