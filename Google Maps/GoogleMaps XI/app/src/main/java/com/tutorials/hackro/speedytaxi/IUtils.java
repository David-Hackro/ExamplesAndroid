package com.tutorials.hackro.speedytaxi;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by hackro on 19/03/16.
 */
public interface IUtils {

     List<LatLng> decodePoly(String encoded);
}
