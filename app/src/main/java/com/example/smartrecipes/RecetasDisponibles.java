 package com.example.smartrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

 public class RecetasDisponibles extends AppCompatActivity {
     DBHelper db;
     Receta[] Recetas= new Receta[]{new Receta("camarones a la crema",
         new Ingrediente[]{new Ingrediente("camarones"),new Ingrediente("crema")})} /*= Receta.recetario()*/; //Obtener las recetas
     Ingrediente[] ingredientesEnPosesion = {};
     Receta[] recetasPosible = {};

     String[] myArray;


     ImageButton Camarones, Spaguetti;

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
                 for (int k = 0; k < Recetas[j].ingredientes.length; k++) {  //Para ver cada ingrediente de cada receta que existe
                     if (Recetas[j].ingredientes[k].nombre.equals(ingredientesEnPosesion[i].nombre)) { //Y ver si ese ingrediente es el que se tiene n posesión
                         Recetas[j].ingredientes[k].disponibilidad = true;
                         break;                                      //Una receta no puede tener el mismo ingrediente más de una vez
                     }
                 }
             }
         }
         for (int i = 0; i < Recetas.length; i++) {
             for (int j = 0; j < Recetas[i].ingredientes.length; j++) {
                 if (Recetas[i].ingredientes[j].disponibilidad==false){
                     break;
                 }
                 Recetas[i].disponible=true;
             }
         }
         for (int i = 0; i < Recetas.length; i++) {
             if (Recetas[i].getDisponibilidad()) {
                 Receta[] recetaPosibleAux = new Receta[recetasPosible.length + 1];
                 System.arraycopy(recetasPosible, 0, recetaPosibleAux, 0, recetasPosible.length);
                 recetaPosibleAux[recetaPosibleAux.length - 1] = Recetas[i];
                 recetasPosible = recetaPosibleAux;
             }
         }
     }

     public void irCamarones(View v) {
         Intent i = new Intent(this, Camarones.class);
         startActivity(i);
     }

     public void irSpaguetti(View v) {
         Intent i = new Intent(this, Spaguetti.class);
         startActivity(i);
     }
 }