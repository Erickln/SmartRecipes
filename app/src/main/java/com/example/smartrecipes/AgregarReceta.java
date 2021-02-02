package com.example.smartrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class AgregarReceta extends AppCompatActivity {
    public static final String NEW_RECIPE_KEY = "nuevaReceta";

    EditText nombre,ingrediente1,ingredeinte2,procedimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_receta);
        nombre = findViewById(R.id.nombreEditText);
        ingrediente1 = findViewById(R.id.ingredienteEditText1);
        ingredeinte2 = findViewById(R.id.ingredienteEditText2);
        procedimiento = findViewById(R.id.procedimientoEditText);


    }

    public void agregarReceta(View v){

        Intent i = getIntent();
        String nombre = this.nombre.getText()+"";
        String ingrediente1 = this.ingrediente1.getText()+"";
        String ingrediente2 = this.ingredeinte2.getText()+"";
        ArrayList<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(new Ingrediente(ingrediente1, ""));
        ingredientes.add(new Ingrediente(ingrediente2, ""));

        String procedimiento = this.procedimiento.getText()+"";
        i.putExtra(NEW_RECIPE_KEY,new Receta(nombre,ingredientes,procedimiento, "URL"));
        setResult(Activity.RESULT_OK, i);
        finish();
    }

}