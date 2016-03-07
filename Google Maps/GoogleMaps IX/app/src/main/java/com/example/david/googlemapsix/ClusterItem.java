package com.example.david.googlemapsix;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by david on 7/03/16.
 */
public interface ClusterItem {

    /**
     * The position of this marker. This must always return the same value.
     */
    LatLng getPosition();
}