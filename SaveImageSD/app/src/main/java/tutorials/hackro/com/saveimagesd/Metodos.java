package tutorials.hackro.com.saveimagesd;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Metodos {

    private Context context ;

    public Metodos(Context c) {
        context  = c;
    }

    public String getPath(Uri uri) {
        if( uri == null ) {
            return null;
        }

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getApplicationContext().getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    //----------------------------------------------------------------------------------------------------------//

    public void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) throws FileNotFoundException {


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
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







}
