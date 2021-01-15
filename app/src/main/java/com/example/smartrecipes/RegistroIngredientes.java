package com.example.smartrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroIngredientes extends AppCompatActivity {
    private EditText ingrediente;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_ingredientes);

        ingrediente = findViewById(R.id.ingredienteTexto);
        db = new DBHelper(this);
    }
    public void guardarIngrediente(View v){
        db.guardar(ingrediente.getText().toString());
        Toast.makeText(this, "INGREDIENTE GUARDADO", Toast.LENGTH_SHORT).show();
        Intent retorno = new Intent(this,IngredientesAct.class);
        startActivity(retorno);
    }
}