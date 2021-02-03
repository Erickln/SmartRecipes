 package com.example.smartrecipes;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.internal.$Gson$Preconditions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

 public class RecetasDisponibles extends AppCompatActivity implements View.OnClickListener {
     private RecyclerView recycler;
     private RecyclerView recycler3;
     DBHelper db;
     ArrayList<Receta> recetas = new ArrayList<>();
     ArrayList<Receta> recetasPersonales = new ArrayList<>();
     ArrayList<Ingrediente> ingredientesEnPosesion = new ArrayList<Ingrediente>();
     ArrayList<Receta> recetasPosible = new ArrayList<>();
     ArrayList<Receta> recetasPosiblePersonales = new ArrayList<>();
    int pos;
     String[] myArray;
     private static final int KEY_EDITAR_RECETA=1;
    // ImageButton Camarones, Spaguetti;
     private static final int NEW_RECIPE_CODE=0;


     FirebaseAuth mFirebaseAuth;
     FirebaseDatabase firebaseReference;
     private DatabaseReference dbRef;
     private String userID;
     private List ingredientes;
     private Map<String, String> mapRecetas;
     private Map<String, String> mapRecetasPersonales;
     private FBHelper fbHelper;
     //--------------------------------------------------------------------

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_recetas_disponibles);
         recycler = findViewById(R.id.recycler2);
         recycler3 = findViewById(R.id.recycler3);
         //SQLiteDatabase db = getWritableDatabase();
         db = new DBHelper(this);

         /*
         Recetas[0].ingredientes[0].nombre="camarones";
         Recetas[0].ingredientes[1].nombre="crema";
         */
         Intent i = getIntent();
         ingredientesEnPosesion=(ArrayList<Ingrediente>) i.getSerializableExtra("ingredientes");
         for (int j = 0; j < ingredientesEnPosesion.size(); j++) {
             Log.wtf("Ingrediente","Tengo "+ingredientesEnPosesion.get(j).nombre);
         }
         //Log.wtf("DEBUG",ingredientesEnPosesion.toString());

         ///////////////// Carga ingredientes con Firebase////////////////
         fbHelper = new FBHelper();
         mFirebaseAuth = FirebaseAuth.getInstance();
         firebaseReference = FirebaseDatabase.getInstance();
         dbRef = firebaseReference.getReference();
         this.userID = mFirebaseAuth.getUid();




         dbRef.child("SmartRecipes").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 //For para acceder a todos los children de la base de datos.
                 for (DataSnapshot child: dataSnapshot.getChildren()) {


                     //Se crea un hashmap que se inicializa con lo que recibimos del child de firebase.
                     mapRecetas = (Map) child.getValue();
                     HashMap hashMapRecetas= (HashMap) mapRecetas;
                     ArrayList<String> listaLlaves = (ArrayList<String>) hashMapRecetas.keySet()
                             .stream()
                             .collect(Collectors.toList());
                     JSONObject eljson = new JSONObject(mapRecetas);
                     for (int j = 0; j < listaLlaves.size(); j++) {
                         try {
         //                  Log.wtf("JSON", eljson.getString(listaLlaves.get(j)+""));
                             JSONObject obj41 = eljson.getJSONObject(listaLlaves.get(j)+"");
                             /*
                             String mJsonString = obj41.toString();
                             JsonParser parser = new JsonParser();
                             JsonElement mJson =  parser.parse(mJsonString);
                             Gson gson = new Gson();
                             Receta auxReceta = gson.fromJson(mJson, Receta.class);

                              */
               //            Log.wtf("DAMEELNOMBRTEALV", obj41.getString("nombre"));
                             String nombre = obj41.getString("nombre");
                             String procedimiento = obj41.getString("procedimiento");
                             JSONArray ingredientesAux = obj41.getJSONArray("ingredientes");
                             ArrayList<Ingrediente> ingredientes = new ArrayList<>();
                             for (int i = 0; i < ingredientesAux.length(); i++) {
                                 JSONObject json = new JSONObject(ingredientesAux.getString(i));
                                 ingredientes.add(new Ingrediente(json.getString("nombre"), ""));
                             }
                             String url = obj41.getString("url");
                             String key = listaLlaves.get(j);
                             Receta res = new Receta(nombre,ingredientes,procedimiento, url);
                             res.addKey(key);
                             Log.wtf("RESULTADO :(",new Receta(nombre,ingredientes,procedimiento, url).toString());
                             recetas.add(res);

                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }
             //        Log.wtf("prueba80",eljson.toString());
                     /*try {
                         JSONArray jsonArray = new JSONArray(mapRecetas);

                         for (int j = 0; j < jsonArray.length(); j++) {
                             JSONObject aux = jsonArray.getJSONObject(j);
                             Log.wtf("JSON", aux.getString("nombre"));

                         }
                     } catch (JSONException e) {
                         Log.wtf("error", "No se pudo imprimir");
                         e.printStackTrace();
                     }*/


                     //Aqui es donde se guardan los ingredientes en nuestra variable local "List ingredientes".
//////////////////////ESTE METODO ES IMPORTANTE, ES LA COVERSION DE MAPA A ARRAYLIST////////////////////////////////////////////////////

                     //For para recorrer el arraylist, aqui se asignan los valores a las Views,
                     //Aunque en este caso es una simple concatenacion a un unico textView
                 }
                 verDisponibilidad();
             }


             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });


//////////////////////Recetas personales/////////////////////////////////////////////////
         dbRef.child("users").child(this.userID).child("recetasPersonales").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot child) {

                     //Se crea un hashmap que se inicializa con lo que recibimos del child de firebase.
                     mapRecetasPersonales = (Map) child.getValue();
                     HashMap hashMapRecetas= (HashMap) mapRecetasPersonales;
                     ArrayList<String> listaLlaves = (ArrayList<String>) hashMapRecetas.keySet()
                             .stream()
                             .collect(Collectors.toList());
                     JSONObject eljson = new JSONObject(mapRecetasPersonales);
                     for (int j = 0; j < listaLlaves.size(); j++) {
                         try {
                             JSONObject obj41 = eljson.getJSONObject(listaLlaves.get(j)+"");

                             String nombre = obj41.getString("nombre");
                             String procedimiento = obj41.getString("procedimiento");
                             JSONArray ingredientesAux = obj41.getJSONArray("ingredientes");
                             ArrayList<Ingrediente> ingredientes = new ArrayList<>();
                             for (int i = 0; i < ingredientesAux.length(); i++) {
                                 JSONObject json = new JSONObject(ingredientesAux.getString(i));
                                 ingredientes.add(new Ingrediente(json.getString("nombre"), ""));
                             }
                             String url = obj41.getString("url");
                             String key = listaLlaves.get(j);
                             Receta res = new Receta(nombre,ingredientes,procedimiento, url);
                             res.addKey(key);
                             Log.wtf("RESULTADO :(",new Receta(nombre,ingredientes,procedimiento, url).toString());
                             recetasPersonales.add(res);

                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }


                 verDisponibilidad();
                 verDisponibilidadPersonales();
             }


             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });


         adapter();
         adapter2();

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
     }

     private void verDisponibilidad() {
         recetasPosible = new ArrayList<>();
         for (int i = 0; i < ingredientesEnPosesion.size(); i++) {   //Por cada ingrediente en posesión
             for (int j = 0; j < recetas.size(); j++) {              //Revisar cada receta que existe
                 for (int k = 0; k < recetas.get(j).ingredientes.size(); k++) {  //Para ver cada ingrediente de cada receta que existe
                     String a = recetas.get(j).ingredientes.get(k).nombre;
                     String b = ingredientesEnPosesion.get(i).nombre;

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
         adapter();
     }

     public void remove(){
 //        recetasPosible.remove();
     }

     private void verDisponibilidadPersonales() {
         ArrayList<Receta> Aux =  recetasPosible;
         ArrayList<Receta> Aux2 =  recetasPosiblePersonales;

         recetasPosiblePersonales = new ArrayList<>();
         for (int i = 0; i < ingredientesEnPosesion.size(); i++) {   //Por cada ingrediente en posesión
             for (int j = 0; j < recetasPersonales.size(); j++) {              //Revisar cada receta que existe
                 for (int k = 0; k < recetasPersonales.get(j).ingredientes.size(); k++) {  //Para ver cada ingrediente de cada receta que existe
                     String a = recetasPersonales.get(j).ingredientes.get(k).nombre;
                     String b = ingredientesEnPosesion.get(i).nombre;

                     if (recetasPersonales.get(j).ingredientes.get(k).nombre.equals(ingredientesEnPosesion.get(i).nombre)) { //Y ver si ese ingrediente es el que se tiene n posesión
                         recetasPersonales.get(j).ingredientes.get(k).setEnPosesion(true);
                         break;                                      //Una receta no puede tener el mismo ingrediente más de una vez
                     }
                 }
             }
         }
         for (int i = 0; i < recetasPersonales.size(); i++) {
             if (recetasPersonales.get(i).disponibilidad() && !recetasPosiblePersonales.contains(recetasPersonales.get(i))) {

                 recetasPosiblePersonales.add(recetasPersonales.get(i));
             }
         }

         Log.wtf("Recetas personales disponibles: ", recetasPersonales.toString());
         adapter2();
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


    //Metodo para el adapter del recyclerview de recetas disponibles
     public void adapter(){
         RecetaAdapter recetaAdapter = new RecetaAdapter(recetasPosible, this);
         LinearLayoutManager llm = new LinearLayoutManager(this);
         llm.setOrientation(LinearLayoutManager.VERTICAL);

         GridLayoutManager glm = new GridLayoutManager(this, 1);

         recycler.setLayoutManager(llm);
         recycler.setAdapter(recetaAdapter);
     }



     //Metodo para el adapter del recyclerview de recetas personales
     public void adapter2(){

         RecetaPersonalAdapter recetaPersonalAdapter = new RecetaPersonalAdapter(recetasPosiblePersonales, this);
         LinearLayoutManager llm = new LinearLayoutManager(this);
         llm.setOrientation(LinearLayoutManager.VERTICAL);

         GridLayoutManager glm = new GridLayoutManager(this, 1);

         recycler3.setLayoutManager(llm);
         recycler3.setAdapter(recetaPersonalAdapter);
     }



     @Override
     public void onClick(View v) {
         pos = recycler.getChildLayoutPosition(v);
         Intent i = new Intent(this, EditarReceta.class);
         Receta recetaEnviada = recetasPosible.get(pos);
         recetasPosible.remove(pos);
         i.putExtra("recetaAModificar",recetaEnviada);
         //Toast.makeText(this, "VOY A MOSTRAR UNA ACTIVIDAD", Toast.LENGTH_SHORT).show();
         startActivityForResult(i,KEY_EDITAR_RECETA);
     }
 }