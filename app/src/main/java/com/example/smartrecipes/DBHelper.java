package com.example.smartrecipes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        String query = "CREATE TABLE " + TABLE + "(" +
                FIELD_ID + " INTEGER PRIMARY KEY, " +
                FIELD_INGREDIENTE + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS ?";
        String[] params = {TABLE};
        db.execSQL(query, params);
        onCreate(db);
    }

    public void guardar(String ingre){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(FIELD_INGREDIENTE, ingre);
        db.insert(TABLE, null, valores);
        System.out.println(pruebaDesplegar(ingre)[0]);

    }
    public int eliminar(String ingre){
        SQLiteDatabase db = getWritableDatabase();
        String clause = FIELD_INGREDIENTE + " = ?";
        String[] params = {ingre};
        return db.delete(TABLE, clause, params);
    }

    public String desplegar(String ingre){

        SQLiteDatabase db = getReadableDatabase();
        String clause = FIELD_INGREDIENTE + " = ?";
        String[] params = {ingre};

        Cursor c = db.query(TABLE, null, clause, params, null, null, null);
        String base = "";

        if(c.moveToFirst()){

            base = c.getString(1);

            /*
            // Recorrer todos los resultados, si es necesario
            do {

            }
            while(c.moveToNext());

             */
        }

        return base;
    }

    public LinkedList buscar(String ingre) { //{String} //Actualmente obsoleto
        SQLiteDatabase db = getReadableDatabase();
        String clause = FIELD_INGREDIENTE + " = ?";
        String[] params = {ingre};
        Cursor crsr = db.query(TABLE, null, clause,
                params, null, null, null);
        LinkedList <String[]> L = new LinkedList<String[]>();
        do {
            String id          = crsr.getString(0);
            String modelo      = crsr.getString(2);
            L.add(new String[]{id,modelo});
            //String e = L.get(0)[0];
        }while (crsr.moveToNext());
        return L;
    }
    public String[] pruebaDesplegar(String ingrediente){ //Funciona. Te da toda la columna en un arreglo
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

}
