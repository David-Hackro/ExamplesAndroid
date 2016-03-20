package com.tutorials.hackro.speedytaxi;

import com.tutorials.hackro.speedytaxi.Direction.DirectionResults;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Url;

/**
 * Created by hackro on 20/03/16.
 */
public interface GoogleDirection {


   // @FormUrlEncoded
   @GET("/maps/api/directions/json")
    Call<DirectionResults> getDirection(@Query("origin") String origin, @Query("destination") String destination);
}
