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
    private EditText ingrediente;
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregacion);

        ingrediente = findViewById(R.id.nuevoIngre);
        texto = findViewById(R.id.label);
        boton = findViewById(R.id.agregaBoton);

    }
    public void volver(View v){
        Intent retorno = new Intent();
        retorno.putExtra("ingre", ingrediente.getText().toString());

        //enviarlo
        setResult(Activity.RESULT_OK, retorno);
        ingrediente.setText("");
        //cerrar actividad
        finish();
    }
}