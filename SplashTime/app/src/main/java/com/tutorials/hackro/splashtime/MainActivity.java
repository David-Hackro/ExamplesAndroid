package com.tutorials.hackro.splashtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
        //Recuerda que en el activity_main.xml se agrego una imagen y un progressbar
    private long ms=0;//milisegundos
    private long splashTime=3000;//tiempo de duracion del splashScrenn en milisegundos
    private boolean splashActive = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //---------------------------------------------//
        Thread mythread = new Thread() {//Creamos un hilo
            public void run() {
                try {
                    while (splashActive && ms < splashTime) {//Condicional,mientras los ms sea menor al tiempo de duracion
                            ms=ms+1000;//Se incrementara de segundo en segundo 1..2..3..4..5 etc
                        sleep(1000);
                    }
                } catch(Exception e) {}
                finally {//Cuando se termine el tiempo duracion
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);//Mandaremos al usuario a un nuevo Activity  Intent(ActivityActual.this, NuevoActivity.class);
                    startActivity(intent);
                }
            }
        };
        mythread.start();
    }

}
