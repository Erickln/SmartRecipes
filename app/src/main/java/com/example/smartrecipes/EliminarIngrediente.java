package com.example.smartrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EliminarIngrediente extends AppCompatActivity {
    private EditText ingrediente;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_ingrediente);
        ingrediente = findViewById(R.id.ingredienteTexto2);
        db = new DBHelper(this);
    }
    public void eliminarIngrediente(View v){
        String ing = ingrediente.getText().toString();

        if(db.search(ing)==1) {
            db.borrar(ingrediente.getText().toString());
            Toast.makeText(this, "INGREDIENTE ELIMINADO", Toast.LENGTH_SHORT).show();
            Intent retorno = new Intent(this,IngredientesAct.class);
            startActivity(retorno);
        }
        else {
            Toast.makeText(this, "INGREDIENTE NO EXISTENTE", Toast.LENGTH_SHORT).show();
        }
    }
}