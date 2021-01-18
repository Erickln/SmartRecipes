package com.example.smartrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class IngredientesAct extends AppCompatActivity {

    private DBHelper db;
    private TextView texto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientes);

        texto = findViewById(R.id.textView4);
        db = new DBHelper(this);
        String[] myArray = db.pruebaDesplegar();
        int sizeArr = myArray.length;

        for (int i = 0; i<sizeArr; i++){
            texto.append("-  " + myArray[i] + "\n");
        }
    }


    public void agregarView(View v) {

        Intent intent = new Intent(this, RegistroIngredientes.class);

        startActivity(intent);
    }


    public void logOut(View v){
        FirebaseAuth.getInstance().signOut();
        Intent toLogin = new Intent(IngredientesAct.this, MainActivity.class);
        startActivity(toLogin);
    }
    public void eliminarView(View v) {
        Intent i = new Intent(this, EliminarIngrediente.class);
        startActivity(i);
    }

    public void irRecetas(View v){
        Intent i = new Intent(this, RecetasDisponibles.class);
        startActivity(i);
    }


}