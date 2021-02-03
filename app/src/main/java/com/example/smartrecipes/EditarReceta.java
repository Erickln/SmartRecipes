package com.example.smartrecipes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EditarReceta extends AppCompatActivity {
    public static final String NEW_RECIPE_KEY = "recetaAModificar";
    private static final int AGREGAR_CODE = 0;
    private RecyclerView recycler;
    private ArrayList<Ingrediente> ingredientes;
    EditText nombre, procedimiento, url;
    Receta receta;
    int clave;
    private FBHelper fbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_receta);
        nombre = findViewById(R.id.nombreEditTextEdit);
        procedimiento = findViewById(R.id.procedimientoEditTextEdit);
        url = findViewById(R.id.urlEditText);
        recycler = findViewById(R.id.recyclerView);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        clave = b.getInt("clave"); //0 pública 1 personal
        this.receta = (Receta) i.getSerializableExtra("recetaAEditar");

        nombre.setText(receta.nombre);
        procedimiento.setText(receta.procedimiento);
        url.setText(receta.url);

        ingredientes=receta.ingredientes;

        adapter();

    }

    public void editarReceta(View v) {
        Intent i = getIntent();
        String nombre = this.nombre.getText() + "";
        String url = this.url.getText() + "";
        FBHelper fb = new FBHelper();

        String procedimiento = this.procedimiento.getText() + "";
        Receta resultado = new Receta(nombre, ingredientes, procedimiento, url);
        //i.putExtra("recetaAModificar",receta);
        if (clave==0) {
            fb.editaRecetaPublica(receta, resultado);
        }else if (clave==1){
            fb.editaRecetaPersonal(receta,resultado);
        }

        finish();

    }
    public void agregarIngre(View v) {
        Intent i = new Intent(this, Agregacion.class);
        startActivityForResult(i, AGREGAR_CODE);
    }

    public void adapter() {
        IngreAdapter ingreAdapter = new IngreAdapter(ingredientes);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        GridLayoutManager glm = new GridLayoutManager(this, 1);

        recycler.setLayoutManager(llm);
        recycler.setAdapter(ingreAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AGREGAR_CODE && resultCode == Activity.RESULT_OK && data != null) {

            String ingre = data.getStringExtra("ingre");
            fbHelper.guardaIngrediente(ingre);
        }
    }
}