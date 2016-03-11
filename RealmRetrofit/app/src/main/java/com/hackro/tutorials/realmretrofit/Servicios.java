package com.hackro.tutorials.realmretrofit;

import android.util.Log;

import com.hackro.tutorials.realmretrofit.Entidades.Photo;
import com.hackro.tutorials.realmretrofit.Interfaces.IDataService;
import com.hackro.tutorials.realmretrofit.Interfaces.IServices;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by david on 10/03/16.
 */
public class Servicios implements IDataService {

    private Retrofit retrofit;
    private IServices services;

    public Servicios() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(ip)//
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        services = retrofit.create(IServices.class);
    }



        @Override
    public boolean requestAllPhotos() {


            Call<List<Photo>> call = services.photos();

            call.enqueue(new Callback<List<Photo>> () {

                @Override
                public void onResponse(Response<List<Photo>> response, Retrofit retrofit) {

                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("onFailure ", t.getMessage());

                }
            });

            return false;
    }


}
