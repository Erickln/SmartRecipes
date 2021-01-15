package com.example.smartrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class IngredientesAct extends AppCompatActivity {

    private DBHelper db;
    private TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientes);

        texto = findViewById(R.id.textView4);
        db = new DBHelper(this);

        db.guardar("pasta");
    }

    public void agregarView(View v) {
        Intent intent = new Intent(this, RegistroIngredientes.class);
        startActivity(intent);
    }

    public void actualizar(View v){
        texto.setText(db.pruebaDesplegar("pasta")[0]);
        System.out.println(db.pruebaDesplegar("pasta")[0]);
    }

}