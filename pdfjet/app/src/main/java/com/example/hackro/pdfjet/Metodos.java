package com.example.hackro.pdfjet;

import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


/**
 * Created by hackro on 23/11/15.
 */
public class Metodos {


    public void GeneratePDF()
    {
        Document document = new Document();
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/sdcard/TutorialesHackro/hoja.pdf"));

            document.open();

            PdfPTable table = new PdfPTable(3); // 3 columns.
            table.setWidthPercentage(100); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table

            //Set Column widths
            float[] columnWidths = {1f, 1f, 1f};
            table.setWidths(columnWidths);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Cell 1"));
            cell1.setBorderColor(BaseColor.BLUE);
            cell1.setPaddingLeft(10);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell2 = new PdfPCell(new Paragraph("Cell 2"));
            cell2.setBorderColor(BaseColor.GREEN);
            cell2.setPaddingLeft(10);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell cell3 = new PdfPCell(new Paragraph("Cell 3"));
            cell3.setBorderColor(BaseColor.RED);
            cell3.setPaddingLeft(10);
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

            //To avoid having the cell border and the content overlap, if you are having thick cell borders
            //cell1.setUserBorderPadding(true);
            //cell2.setUserBorderPadding(true);
            //cell3.setUserBorderPadding(true);

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);

            document.add(table);

            createDirectoryAndSaveFile(writer, "david");
            document.close();
            writer.close();
        } catch (Exception e)
        {
            e.printStackTrace();
            Log.e("ewdfhyfafedyatfawytedfytew b",e.getMessage());
        }

    }



    public void createDirectoryAndSaveFile(PdfWriter imageToSave, String fileName) throws FileNotFoundException {


        File direct = new File(Environment.getExternalStorageDirectory() + "/TutorialesHackro");

        if (!direct.exists()) {
            File wallpaperDirectory = new File("/sdcard/TutorialesHackro/");
            wallpaperDirectory.mkdirs();
        }

        File file = new File(new File("/sdcard/TutorialesHackro/"), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("edwq",e.getMessage());
        }
    }

}
