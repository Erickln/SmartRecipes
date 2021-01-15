 package com.example.smartrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

 public class RecetasDisponibles extends AppCompatActivity {
    DBHelper db;
    Receta[] Recetas= Receta.recetario(); //Obtener las recetas
    Ingrediente[] ingredientesEnPosesion={};
    Receta[] recetasPosible={};
    ImageButton camarones,spaguetti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_disponibles);
        //SQLiteDatabase db = getWritableDatabase();
        db = new DBHelper(this);
        camarones.findViewById(R.id.camarones);
        spaguetti.findViewById(R.id.spaguetti);
        camarones.setVisibility(View.INVISIBLE);
        spaguetti.setVisibility(View.INVISIBLE);
    }
    private void verDisponibilidad(){
        for (int i = 0; i < ingredientesEnPosesion.length; i++) {   //Por cada ingrediente en posesión
            for (int j = 0; j < Recetas.length; j++) {              //Revisar cada receta que existe
                for (int k = 0; k < Recetas[j].ingredientes.length; k++) {  //Para ver cada ingrediente de cada receta que existe
                    if (Recetas[j].ingredientes[k].nombre==ingredientesEnPosesion[i].nombre){ //Y ver si ese ingrediente es el que se tiene n posesión
                        Recetas[j].ingredientes[k].disponibilidad=true;
                        break;                                      //Una receta no puede tener el mismo ingrediente más de una vez
                    }
                }
            }
        }
        for (int i = 0; i < Recetas.length; i++) {
            if (Recetas[i].getDisponibilidad()) {
                Receta[] recetaPosibleAux = new Receta[recetasPosible.length+1];
                System.arraycopy(recetasPosible, 0, recetaPosibleAux, 0, recetasPosible.length);
                recetaPosibleAux[recetaPosibleAux.length - 1] = Recetas[i];
                recetasPosible=recetaPosibleAux;
            }
        }
        if (Recetas[0].getDisponibilidad()){
            camarones.setVisibility(View.VISIBLE);
        }
        if (Recetas[1].getDisponibilidad()){
            spaguetti.setVisibility(View.VISIBLE);
        }
    }

     public void irCamarones(View v){
         Intent i = new Intent(this,Camarones.class);
         startActivity(i);
     }
     public void irSpaguetti(View v){
         Intent i = new Intent(this,Spaguetti.class);
         startActivity(i);
     }
}