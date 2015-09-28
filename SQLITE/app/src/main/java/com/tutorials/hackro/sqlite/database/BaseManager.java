package com.tutorials.hackro.sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by hackro on 27/09/15.
 */
public class BaseManager implements InterfaceValues {

    private  DBHelper dbHelper;//Instanciamos el DBHelper
    private SQLiteDatabase db; //Instanciamos SQLiteDatabase

        //Creamos la sentencia para crear la tabla Users,concatenando los valores que previamente creamos en la interface InterfaceValues
    public static final String CreateTableUsers =
            "create table "+TABLE_NAME+" ("+I_ID+" integer primary key autoincrement, " +
                    ""+I_USER +" text not null," +
                    " "+I_PASSWORD +" text not null);";

    public BaseManager(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();//Creation of DB
    }

    public ContentValues GenerateContentValues(String user,String password){//Ocupamos este Metodo para regresar un ContentValues que ocuparemos para insertar un nuevo usuario
        ContentValues contentValues = new ContentValues();
        contentValues.put(I_USER,user);
        contentValues.put(I_PASSWORD, password);
        return  contentValues;
    }

    public void InsertUser(String user,String password){;//Unicamente pasamos dos parametros,ues el ID es autoincrementable
        db.insert(TABLE_NAME, null, GenerateContentValues(user, password));//Ocupamos el metodo que ofrece dbHelper,pasando la tabla,y ContentValues como parametros
    }
    public void Delete(String id){
        db.delete(TABLE_NAME, I_ID + "=?", new String[]{id});
    }//De igual manera ocuparemos un metodo de ContentValues [delete] colocando la tabla,el parametro identificador y el parametro (id)

    public void DeleteMultiple(String id,String id2){//Similar al anterior solo cambiando el = por un IN(Recuerda que los arametros deber ir en orden)

        db.delete(TABLE_NAME,I_ID + " IN (?,?) ",new String[]{id,id2});
    }

    public void EditPassword(String id,String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put(I_ID,id);
        contentValues.put(I_PASSWORD,password);
        db.update(TABLE_NAME, contentValues, I_ID + " =? ", new String[]{id});

    }

    public Cursor GetAllDates(){//MEtodo para obtener todos los datos
        String[] columnas = new String[]{I_ID,I_USER,I_PASSWORD};//En un arreglo colocamos los campos
        return db.query(TABLE_NAME,columnas,null,null,null,null,null);//OCupamos nuevamente un metodo de ContentValues,pasando como parametro el nombre de la tabla y el arreglo de campos
    }


}
