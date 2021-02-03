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

    EditText nombre,ingrediente1,ingredeinte2,procedimiento,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_receta);
        nombre = findViewById(R.id.nombreEditText);
        ingrediente1 = findViewById(R.id.ingredienteEditText1);
        ingredeinte2 = findViewById(R.id.ingredienteEditText2);
        procedimiento = findViewById(R.id.procedimientoEditText);
        url = findViewById(R.id.url5);


    }

    public void agregarReceta(View v){

        Intent i = getIntent();
        String nombre           = this.nombre.getText()+"";
        String ingrediente1     = this.ingrediente1.getText()+"";
        String ingrediente2     = this.ingredeinte2.getText()+"";
        String procedimiento    = this.procedimiento.getText()+"";
        String url              = this.url.getText()+"";
        ArrayList<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(new Ingrediente(ingrediente1, ""));
        ingredientes.add(new Ingrediente(ingrediente2, ""));

        FBHelper fb = new FBHelper();

        //String [] xx = new String[]{"2","2"};

        Receta resultado = new Receta(nombre,ingredientes,procedimiento,Receta.toUrl(url));
        fb.agregaRecetaPersonal(nombre, procedimiento,
                new String[]{ingrediente1,ingrediente2}
                ,Receta.toUrl(url));
        //i.putExtra(NEW_RECIPE_KEY,new Receta(nombre,ingredientes,procedimiento, "URL"));
        setResult(Activity.RESULT_OK, i);
        finish();
    }

}