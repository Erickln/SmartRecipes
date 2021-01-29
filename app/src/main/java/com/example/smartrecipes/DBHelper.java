package com.example.smartrecipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;


import java.util.ArrayList;
import java.util.LinkedList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_FILE = "ingredientsDatabase.db";
    private static final String TABLE = "Ingredientes";
    private static final String FIELD_ID = "id";
    private static final String FIELD_INGREDIENTE = "ingrediente";

    public DBHelper (Context context){

        super(context, DB_FILE, null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //se llama al crear la bd

        String query = "CREATE TABLE " + TABLE + "(" +
                FIELD_ID + " INTEGER PRIMARY KEY, " +
                FIELD_INGREDIENTE + " TEXT)";

        db.execSQL(query);
    }

    public static String toJSON(Ingrediente i){
        Gson gson = new Gson();
        return gson.toJson(i);
    }

    public static String toJSON(Receta i){
        Gson gson = new Gson();
        return gson.toJson(i);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //se llama al actualizar la bd

        String query = "DROP TABLE IF EXISTS ?";
        String[] params = {TABLE};

        db.execSQL(query, params);

        onCreate(db);

    }

    public void guardar(String ingre){
            //guardar elemento en la bd

        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(FIELD_INGREDIENTE, ingre);
        db.insert(TABLE, null, valores);
        System.out.println(pruebaDesplegar()[0]);

    }

    public int borrar(String ingre){
        //borrar elemento de la bd

        SQLiteDatabase db = getWritableDatabase();
        String clause = FIELD_INGREDIENTE + " = ?";
        String[] params = {ingre};

        return db.delete(TABLE, clause, params);
    }


    public String[] pruebaDesplegar(){ //Funciona. Te da toda la columna en un arreglo
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from "+TABLE,null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex(FIELD_INGREDIENTE));
                list.add(name);
                cursor.moveToNext();
            }
        }
        String[] res = list.toArray(new String[list.size()]);
        for (int i = 0; i < res.length; i++) {

            System.out.println(res[i]);
        }

        return res;
    }

    public int search(String nombre){

        SQLiteDatabase db = getReadableDatabase();
        String clause = FIELD_INGREDIENTE + " = ?";
        String[] params = {nombre};

        Cursor c = db.query(TABLE, null, clause, params, null, null, null);
        int resultado = 0;

        if(c.moveToFirst()){
            nombre = c.getString(1);
            resultado = 1;

        }

        return resultado;
    }



}
