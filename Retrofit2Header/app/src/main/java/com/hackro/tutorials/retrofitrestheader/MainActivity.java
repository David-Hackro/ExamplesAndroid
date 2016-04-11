package com.hackro.tutorials.retrofitrestheader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    //Header con autenticacion basica
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //realizamos el encode a la cadena con el user y pass
        String basicAuth = "Basic " + Base64.encodeToString(String.format("%s:%s", "Username", "Password").getBytes(), Base64.NO_WRAP);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://service/example/")//
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceDisponibilidad service = retrofit.create(ServiceDisponibilidad.class);

        Call<Disponibilidad> call = service.ValidarDisponibilidad(basicAuth);

        call.enqueue(new Callback<Disponibilidad>() {

            @Override
            public void onResponse(Call<Disponibilidad> call, Response<Disponibilidad> response) {
                Log.e("isServicioDisponible ",response.body().isServicioDisponible()+"");
            }

            @Override
            public void onFailure(Call<Disponibilidad> call, Throwable t) {
                Log.e("onFailure ",t.getMessage().toString());
            }
        });







    }
}
