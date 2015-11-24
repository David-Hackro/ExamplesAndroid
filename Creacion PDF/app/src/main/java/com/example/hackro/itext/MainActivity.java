package com.example.hackro.itext;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.pdf.BaseFont;

import java.io.File;

public class MainActivity extends Activity {

    private static final String LOG_TAG = "GeneratePDF";


    private BaseFont bfBold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


    public void GeneratePDF(View view)
    {
        // TODO Auto-generated method stub
        String filename = "david";
        String filecontent = "Contenido";
        Metodos fop = new Metodos();
        fop.write(filename, filecontent);
        if (fop.write(filename, filecontent)) {
            Toast.makeText(getApplicationContext(),
                    filename + ".pdf created", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getApplicationContext(), "I/O error",
                    Toast.LENGTH_SHORT).show();
        }
    }

  }

