package com.tutorials.hackro.myapplication;

import android.app.Dialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this,"dshjzfcyawcftyawefye",Toast.LENGTH_SHORT).show();
        new SegundoPlano().execute();


    }


    private class SegundoPlano extends  AsyncTask<Void,Void,Void>
    {

        //Antes
        //Durante
        //Despues


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Hola",Toast.LENGTH_SHORT).show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < 60; i++)
            {
                ProcesoPesado();
                Log.e("Hackro ",i+"");
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Toast.makeText(MainActivity.this,"Adios",Toast.LENGTH_SHORT).show();


            Dialog dialog = new Dialog(MainActivity.this);



        }
    }





































        //
    public void ProcesoPesado()
    {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }






}
