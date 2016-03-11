package com.hackro.tutorials.realmretrofit;

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hackro.tutorials.realmretrofit.Entidades.Photo;
import com.hackro.tutorials.realmretrofit.Interfaces.IDataService;
import com.hackro.tutorials.realmretrofit.Interfaces.IServices;

import java.util.List;

import io.realm.RealmConfiguration;
import io.realm.RealmObject;
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
    private RealmConfiguration realmConfiguration;
    private RepositoryPhotos repositoryPhotos;

    private boolean respuesta;

    public Servicios(RealmConfiguration res) {

        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();


        this.retrofit = new Retrofit.Builder()
                .baseUrl(ip)//
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        services = retrofit.create(IServices.class);
        repositoryPhotos = new RepositoryPhotos(res);
        repositoryPhotos.readPostAll();


    }



    @Override
    public void requestAllPhotos() {

        Call<List<Photo>> call = services.photos();
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Response<List<Photo>> response, Retrofit retrofit) {
                for (Photo p : response.body())
                        repositoryPhotos.addPhoto(p);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("onFailure ", t.getMessage());
            }
        });

    }



}
