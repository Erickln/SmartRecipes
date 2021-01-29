 package com.example.smartrecipes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

 public class RecetasDisponibles extends AppCompatActivity implements View.OnClickListener {
     private RecyclerView recycler;
     DBHelper db;
     ArrayList<Receta> recetas = new ArrayList<>();
     ArrayList<Ingrediente> ingredientesEnPosesion = new ArrayList<>();
     ArrayList<Receta> recetasPosible = new ArrayList<>();
    int pos;
     String[] myArray;

     private static final int KEY_EDITAR_RECETA=1;
    // ImageButton Camarones, Spaguetti;

     private static final int NEW_RECIPE_CODE=0;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_recetas_disponibles);
         recycler = findViewById(R.id.recycler2);
         //SQLiteDatabase db = getWritableDatabase();
         db = new DBHelper(this);

         recetas.add(new Receta());
         recetas.get(0).nombre="camarones a la crema";
         recetas.get(0).addIngrediente(new Ingrediente("tortilla"));
         recetas.get(0).addIngrediente(new Ingrediente("queso"));
         /*
         Recetas[0].ingredientes[0].nombre="camarones";
         Recetas[0].ingredientes[1].nombre="crema";
         */
         Intent i = getIntent();
         ingredientesEnPosesion=(ArrayList<Ingrediente>) i.getSerializableExtra("ingredientes");

        /* Camarones=findViewById(R.id.Camarones);
         Spaguetti=findViewById(R.id.Spaguetti);
         if (Receta.getDip("camarones a la crema",Recetas)) {
             Camarones.setVisibility(View.VISIBLE);
         }else{
             Toast.makeText(this,"No puedes camarones",Toast.LENGTH_SHORT);
         }
         if (Receta.getDip("spaguetti a la boloñesa",Recetas)) {
             Spaguetti.setVisibility(View.VISIBLE);
         }else{
             Toast.makeText(this,"No puedes spaguetti",Toast.LENGTH_SHORT);
         }  */
         verDisponibilidad();
         adapter();
     }

     private void verDisponibilidad() {
         for (int i = 0; i < ingredientesEnPosesion.size(); i++) {   //Por cada ingrediente en posesión
             for (int j = 0; j < recetas.size(); j++) {              //Revisar cada receta que existe
                 for (int k = 0; k < recetas.get(j).ingredientes.size(); k++) {  //Para ver cada ingrediente de cada receta que existe
                     if (recetas.get(j).ingredientes.get(k).nombre.equals(ingredientesEnPosesion.get(i).nombre)) { //Y ver si ese ingrediente es el que se tiene n posesión
                         recetas.get(j).ingredientes.get(k).setEnPosesion(true);
                         break;                                      //Una receta no puede tener el mismo ingrediente más de una vez
                     }
                 }
             }
         }/*
         for (int i = 0; i < Recetas.length; i++) { //Obtener disponibilidad ::Obsoleto::
             for (int j = 0; j < Recetas[i].ingredientes.size(); j++) {
                 if (Recetas[i].ingredientes.get(j).disponibilidad==false){
                     break;
                 }
                 Recetas[i].isDisponible()=true;
             }
         }
         */
         for (int i = 0; i < recetas.size(); i++) {
             if (recetas.get(i).disponibilidad() && !recetasPosible.contains(recetas.get(i))) {

                 recetasPosible.add(recetas.get(i));
             }
         }
     }

     public void agregarReceta(View v){
         Intent i = new Intent(this, AgregarReceta.class);
         startActivityForResult(i,NEW_RECIPE_CODE);
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         if(requestCode == NEW_RECIPE_CODE && resultCode == Activity.RESULT_OK && data != null){

             Receta resultado = (Receta) data.getSerializableExtra("nuevaReceta");
             //añadir resultado a Recetas
             recetas.add(resultado);
             verDisponibilidad();
             adapter();
             Toast.makeText(this, "La receta " + resultado.nombre + " se ha añadido con éxito.", Toast.LENGTH_SHORT).show();
         }else if(requestCode == KEY_EDITAR_RECETA && resultCode == Activity.RESULT_OK && data != null){
             Receta resultado = (Receta) data.getSerializableExtra("recetaModificada");
             Receta resultado2 = (Receta) data.getSerializableExtra("recetaAModificar");
             recetas.set(pos,resultado);
             verDisponibilidad();
             adapter();
         }
     }

     public void adapter(){
         RecetaAdapter recetaAdapter = new RecetaAdapter(recetasPosible, this);
         LinearLayoutManager llm = new LinearLayoutManager(this);
         llm.setOrientation(LinearLayoutManager.VERTICAL);

         GridLayoutManager glm = new GridLayoutManager(this, 1);

         recycler.setLayoutManager(llm);
         recycler.setAdapter(recetaAdapter);
     }

     @Override
     public void onClick(View v) {
         pos = recycler.getChildLayoutPosition(v);
         Intent i = new Intent(this, EditarReceta.class);
         i.putExtra("recetaAModificar",recetas.get(pos));
         Toast.makeText(this, "VOY A MOSTRAR UNA ACTIVIDAD", Toast.LENGTH_SHORT).show();
         startActivityForResult(i,KEY_EDITAR_RECETA);
     }
 }