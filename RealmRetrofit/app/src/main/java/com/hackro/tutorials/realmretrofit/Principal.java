package com.hackro.tutorials.realmretrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.realm.RealmConfiguration;
import io.realm.processor.Utils;

public class Principal extends AppCompatActivity {

    private Servicios servicios;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        new MyAsyncClass().execute();
       // servicios = new Servicios();
        servicios = new Servicios();

    }

    private class MyAsyncClass extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           // RealmConfiguration r = new RealmConfiguration.Builder(Principal.this).build();

            progress = ProgressDialog.show(Principal.this, null, null, true);
            progress.setContentView(R.layout.elemento_progress_dialog);
            progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progress.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(Principal.this).build();
            servicios.setRealmConfiguration(realmConfiguration);
            servicios.requestAllPhotos();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            progress.dismiss();
        }
    }




}
