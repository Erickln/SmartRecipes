 package com.example.smartrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class RecetasDisponibles extends AppCompatActivity {
    DBHelper db;
    Receta[] Recetas = Receta.recetario(); //Obtener las recetas
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_disponibles);
        //SQLiteDatabase db = getWritableDatabase();
        db = new DBHelper(this);
    }
}