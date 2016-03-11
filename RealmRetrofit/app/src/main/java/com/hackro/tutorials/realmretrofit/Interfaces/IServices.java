package com.hackro.tutorials.realmretrofit.Interfaces;

import com.hackro.tutorials.realmretrofit.Entidades.Photo;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by david on 10/03/16.
 */
public interface IServices {


   // @FormUrlEncoded
    @GET("photos")
    Call<List<Photo>> photos();
}
