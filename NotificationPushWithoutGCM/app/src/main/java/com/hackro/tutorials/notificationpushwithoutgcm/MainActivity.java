package com.hackro.tutorials.notificationpushwithoutgcm;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity{

    private EditText TituloCorto,Titulo,Contenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TituloCorto = (EditText)findViewById(R.id.tituloCorto);
        Titulo= (EditText)findViewById(R.id.titulo);
        Contenido= (EditText)findViewById(R.id.contenido);


    }



    public void showNotification(View view) {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setTicker(TituloCorto.getText().toString())
                .setContentText(Contenido.getText().toString())
                .setContentIntent(PendingIntent.getActivity(this, 0, notificationIntent, 0))
                .setWhen(System.currentTimeMillis())
                .setContentTitle(Contenido.getText().toString())
                .setDefaults(Notification.DEFAULT_ALL);

        Notification notification = builder.build();
        ((NotificationManager) this.getSystemService(NOTIFICATION_SERVICE)).notify(0, notification);


    }
}
