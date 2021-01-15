package com.example.smartrecipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    }

    public int borrar(String ingre){
        //borrar elemento de la bd

        SQLiteDatabase db = getWritableDatabase();
        String clause = FIELD_INGREDIENTE + " = ?";
        String[] params = {ingre};

        return db.delete(TABLE, clause, params);
    }

    //desplegar elementos de la db MEJORARLO
    public String desplegar(String ingre){

        //recibe el nombre del valor a imprimir
        SQLiteDatabase db = getReadableDatabase();
        String clause = FIELD_INGREDIENTE + " = ?";
        String[] params = {ingre};

        Cursor c = db.query(TABLE, null, clause, params, null, null, null);
        String aux = null;

        if(c.moveToFirst()){

            aux = c.getString(1);

        }

        return aux;
    }

}
