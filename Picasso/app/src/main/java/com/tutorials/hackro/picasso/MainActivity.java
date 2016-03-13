package com.tutorials.hackro.picasso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Recuerda agregar los permisos de internet en el manifest --  <uses-permission android:name="android.permission.INTERNET" />
        //Simplemente necesitamos agregar  compile 'com.squareup.picasso:picasso:2.5.2' en el archivo build.gradle
        String url = "http://i.imgur.com/DvpvklR.png";
        imageView = (ImageView)findViewById(R.id.foto);
        //Simplemente necesitaremos cargar la imagen con picasso,
        // pasando como parametros el contexto[this] y el imageView donde se mostrara
        Picasso.with(this).load(url).into(imageView);


    }
}
