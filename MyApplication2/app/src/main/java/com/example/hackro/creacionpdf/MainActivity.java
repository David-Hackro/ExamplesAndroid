package com.example.hackro.creacionpdf;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Metodos metodos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        metodos = new Metodos();

        try {
            String path = Environment.getExternalStorageDirectory()
                    + File.separator + Environment.DIRECTORY_DOWNLOADS;
            File dir = new File(path+"/hoja.pdf");
                metodos.Fields(path+"/hoja.pdf");
        } catch (IOException e) {
            Log.e("Error",e.getMessage());
        }

    }
}
