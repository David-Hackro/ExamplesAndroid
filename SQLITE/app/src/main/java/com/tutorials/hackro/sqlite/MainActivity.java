package com.tutorials.hackro.sqlite;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tutorials.hackro.sqlite.database.BaseManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recuerda que puedes ocupar el Android Device Monitor para acceder a la db de la aplicacion
        //Para poder leer esta db uedes ocupar el plugin de firefox o sqliteman en gnu/linux

        BaseManager dataBaseManager = new BaseManager(this);
            //Insertar usuario
        dataBaseManager.InsertUser("david", "1234");
        dataBaseManager.InsertUser("paulina","qwer987");

            //Eliminar un usuario mediante su ID
        dataBaseManager.Delete("2");
        //Editar el campo password de un usuario mediante su id
        dataBaseManager.EditPassword("1","Hackro");
            //Obtener todos los registros,y recorrerlos
        Cursor cursor = dataBaseManager.GetAllDates();
        Cursor c = cursor;
        String[] data;
        if (c != null) {//mientras el cursor sea distinto a null
            while (c.moveToNext()) {
                Log.e("Resultados:: ", c.getInt(0) + " / " + c.getString(1) + " / " + c.getString(2));//Obtener cada campo de la tabla,en posicion y tipo de dato
            }
            c.close();
        }






    }
}
