package com.example.hackro.creacionpdf;

import android.util.Log;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;

import java.io.IOException;
import java.util.Set;

/**
 * Created by hackro on 23/11/15.
 */
public class Metodos {


    public void Fields(String pdfPath) throws IOException {
        // you only need a PdfStamper if you're going to change the existing PDF.
        PdfReader reader = new PdfReader(pdfPath);

        AcroFields fields = reader.getAcroFields();

        Set<String> fldNames = fields.getFields().keySet();

        for (String fldName : fldNames) {
            Log.e("["+fldName + ": " + fields.getField( fldName ),"]");
        }

    }
}
