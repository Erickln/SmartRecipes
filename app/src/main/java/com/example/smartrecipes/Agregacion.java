package com.example.smartrecipes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Agregacion extends AppCompatActivity {
    private TextView texto;
    private EditText ingredienteV;
    private Button boton;
    private FBHelper fbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregacion);

        ingredienteV = findViewById(R.id.nuevoIngre);
        texto = findViewById(R.id.label);
        boton = findViewById(R.id.agregaBoton);
        fbHelper = new FBHelper();

    }
    public void volver(View v){
        Intent retorno = new Intent();
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.nombre = ingredienteV.getText().toString();

        retorno.putExtra("ingre", ingrediente);

        //enviarlo
        setResult(Activity.RESULT_OK, retorno);

        //cerrar actividad
        finish();
    }
}