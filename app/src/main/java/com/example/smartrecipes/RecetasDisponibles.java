 package com.example.smartrecipes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

 public class RecetasDisponibles extends AppCompatActivity {
     DBHelper db;
     Receta[] Recetas;
     Ingrediente[] ingredientesEnPosesion = {};
     Receta[] recetasPosible = {};

     String[] myArray;


     ImageButton Camarones, Spaguetti;

     private static final int NEW_RECIPE_CODE=0;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_recetas_disponibles);
         //SQLiteDatabase db = getWritableDatabase();
         db = new DBHelper(this);
         /*
         Recetas[0].nombre="camarones a la crema";
         Recetas[0].ingredientes[0].nombre="camarones";
         Recetas[0].ingredientes[1].nombre="crema";
         */
         Intent i = getIntent();
         ingredientesEnPosesion=Ingrediente.StringToIng(i.getStringArrayExtra("ingredientes"));
         verDisponibilidad();
         Camarones=findViewById(R.id.Camarones);
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
         }

     }

     private void verDisponibilidad() {
         for (int i = 0; i < ingredientesEnPosesion.length; i++) {   //Por cada ingrediente en posesión
             for (int j = 0; j < Recetas.length; j++) {              //Revisar cada receta que existe
                 for (int k = 0; k < Recetas[j].ingredientes.size(); k++) {  //Para ver cada ingrediente de cada receta que existe
                     if (Recetas[j].ingredientes.get(k).nombre.equals(ingredientesEnPosesion[i].nombre)) { //Y ver si ese ingrediente es el que se tiene n posesión
                         Recetas[j].ingredientes.get(k).setEnPosesion(true);
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
         for (int i = 0; i < Recetas.length; i++) {
             if (Recetas[i].disponibilidad()) {
                 Receta[] recetaPosibleAux = new Receta[recetasPosible.length + 1];
                 System.arraycopy(recetasPosible, 0, recetaPosibleAux, 0, recetasPosible.length);
                 recetaPosibleAux[recetaPosibleAux.length - 1] = Recetas[i];
                 recetasPosible = recetaPosibleAux;
             }
         }
     }


     public void agregarReceta(View v){
         Intent i = new Intent(this,AgregarReceta.class);
         startActivityForResult(i,NEW_RECIPE_CODE);
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         if(requestCode == NEW_RECIPE_CODE && resultCode == Activity.RESULT_OK && data != null){

             Receta resultado = (Receta) data.getSerializableExtra("nuevaReceta");
             //añadir resultado a Recetas
             verDisponibilidad();
             Toast.makeText(this, "La receta " + resultado.nombre + " se ha añadido con éxito.", Toast.LENGTH_SHORT).show();
         }
     }

 }