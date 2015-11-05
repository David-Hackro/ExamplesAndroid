package com.tutorials.hackro.facebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {

    TextView nombre,email;
    ImageView foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        nombre = (TextView)findViewById(R.id.nombre);
        email = (TextView)findViewById(R.id.email);
        foto = (ImageView)findViewById(R.id.foto);
            //obtenemos los datos que mandamos del activity principal
        String f = getIntent().getStringExtra("foto");//Obtenemos el mail
        String n = getIntent().getStringExtra("usuario");//Obtenemos en nombre
        String e = getIntent().getStringExtra("email");//Obtenemos el mail

        //Asignamos los valores
        nombre.setText(n);
        email.setText(e);
        Picasso.with(this).load(f).into(foto);

    }
}
