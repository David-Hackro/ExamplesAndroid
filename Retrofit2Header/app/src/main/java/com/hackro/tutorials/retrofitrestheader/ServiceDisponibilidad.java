package com.hackro.tutorials.retrofitrestheader;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by david on 11/04/16.
 */
public interface ServiceDisponibilidad {

    ///Se indica el metodo que se ocupara
    @POST("Example.abc")
    Call<Disponibilidad> ValidarDisponibilidad(@Header("Authorization") String authorization);//Se indica el header y su contenido que sera dinamico
}
