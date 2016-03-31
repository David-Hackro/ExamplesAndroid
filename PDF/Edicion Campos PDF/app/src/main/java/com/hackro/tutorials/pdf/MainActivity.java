package com.hackro.tutorials.pdf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cete.dynamicpdf.merger.MergeDocument;
import com.cete.dynamicpdf.merger.PdfDocument;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            //El pdf lo he colocado en la carpeta Assets
            InputStream inputStream = this.getAssets().open("DavidHackro.pdf");
            long avail = 0;
            avail = inputStream.available();

            byte[] samplePDF = new byte[(int) avail];
            inputStream.read(samplePDF , 0, (int) avail);
            inputStream.close();
            PdfDocument objPDF = new PdfDocument(samplePDF);

            MergeDocument document = new MergeDocument(objPDF);
            document.getForm().getFields().getFormField("nombre").setValue("Tutoriales Hackro");
            document.getForm().getFields().getFormField("twitter").setValue("@DavidHackro");
            document.draw("/sdcard/FilledPDF.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
