package com.example.smartrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class RegistroIngredientes extends AppCompatActivity {
    EditText ingrediente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_ingredientes);

        ingrediente = findViewById(R.id.ingredienteTexto);
    }
}