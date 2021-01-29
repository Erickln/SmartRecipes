package com.example.smartrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class EditarReceta extends AppCompatActivity {
    public static final String NEW_RECIPE_KEY = "recetaAModificar";

    EditText nombre,ingrediente1,ingredeinte2,procedimiento;
    Receta receta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_receta);
        nombre = findViewById(R.id.nombreEditTextEdit);
        ingrediente1 = findViewById(R.id.ingredienteEditText1Edit);
        ingredeinte2 = findViewById(R.id.ingredienteEditText2Edit);
        procedimiento = findViewById(R.id.procedimientoEditTextEdit);

        Intent i = getIntent();
        this.receta = (Receta)i.getSerializableExtra("recetaAModificar");

        nombre.setText(receta.nombre);
        ingrediente1.setText(receta.ingredientes.get(0).nombre);
        ingredeinte2.setText(receta.ingredientes.get(1).nombre);
        procedimiento.setText(receta.procedimiento);

    }

    public void editarReceta(View v){

        Intent i = getIntent();
        String nombre = this.nombre.getText()+"";
        String ingrediente1 = this.ingrediente1.getText()+"";
        String ingrediente2 = this.ingredeinte2.getText()+"";
        ArrayList<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(new Ingrediente(ingrediente1));
        ingredientes.add(new Ingrediente(ingrediente2));

        String procedimiento = this.procedimiento.getText()+"";
        i.putExtra("recetaModificada",new Receta(nombre,ingredientes,procedimiento));
        i.putExtra("recetaAModificar",receta);
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}