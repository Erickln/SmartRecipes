package com.example.smartrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditarIngredienteActivity extends AppCompatActivity {

    TextView ingredienteActual;
    String iActual;
    String keyActual;
    Button guardar;
    FBHelper fbh;
    EditText nuevoIngrediente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ingrediente);

        iActual = getIntent().getStringExtra("ELACTUALNOMBRE");
        keyActual = getIntent().getStringExtra("ELACTUALKEY");
        ingredienteActual = findViewById(R.id.ieditar);
        ingredienteActual.setText(iActual);
        fbh = new FBHelper();
        guardar = findViewById(R.id.editaBoton);
        nuevoIngrediente = findViewById(R.id.nuevoIngredienteET);
    }

    public void editaIngrediente(View v){
        fbh.actualizarIngrediente(keyActual, nuevoIngrediente.getText().toString());
        finish();
    }
}