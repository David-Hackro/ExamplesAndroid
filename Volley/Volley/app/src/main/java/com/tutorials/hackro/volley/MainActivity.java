package com.tutorials.hackro.volley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    EditText t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = (EditText)findViewById(R.id.textView);

    }

    WebService ws = new WebService(this);//Creamos la instancia de la clase WebService,pasando el contexto de esta actividad con [this]
        //Recuerda compartir tus conocimientos
    public void Consulta(View v){
        String titulo = t.getText().toString();
        ws.QueryPost("1");//Metodo Post
        ws.QueryGet("her");//Metodo Get

    }




}
