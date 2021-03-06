package com.example.smartrecipes;


import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.helpers.LocatorImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class FBHelper {

    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseReference;
    private DatabaseReference dbRef;
    private String userID;
    private ArrayList ingredientes;


    public FBHelper(){
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseReference = FirebaseDatabase.getInstance();
        dbRef = firebaseReference.getReference();
        this.userID = mFirebaseAuth.getUid();

    }

    public String getUserName(){
        return mFirebaseAuth.getCurrentUser().getDisplayName();
    }

    public void setUserName(String userName){
        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userName).build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.wtf("Update correcto", "User profile updated.");
                        }
                    }
                });
    }


    public void guardaIngrediente(String ingrediente ){

        dbRef.child("users").child(this.userID).child("ingredientes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String key;
                boolean existe = false;

                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {


                    if (postsnapshot.getValue().equals(ingrediente)) {

                        Log.wtf("entra", "INGREDIENTE" + ingrediente + "ya existe.");
                        existe = true;

                    }

                }


                if (!existe) {
                    dataSnapshot.getRef().push().setValue(ingrediente);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void borrarIngrediente(String key){


        //Query que filtra por medio de la key
        dbRef.child("users").child(this.userID).child("ingredientes").orderByKey().equalTo(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {

                    //funcion para remover el respectivo nodo
                    postsnapshot.getRef().removeValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    };




    public void actualizarIngrediente(String key, String nuevoIngrediente){


        //QUERY que primero encuentra el elemento
        dbRef.child("users").child(this.userID).child("ingredientes").orderByKey().equalTo(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {

                    //Funcion para asignar valor, el postsnapshot es el objeto que regresa el query ya encontrado.
                    postsnapshot.getRef().setValue(nuevoIngrediente);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    };



    //Metodo para encontrar la key de un valor del hashmap
    public String getKeyIngrediente(String ingrediente, Map<String, String> ingredientes) {

        String key = "no encontrado";

        // iterate each entry
        for (Map.Entry<String, String> entry : ingredientes.entrySet()) {

            //Compara los Strings y devuelve la llave
            if (entry.getValue().equals(ingrediente)) {
                Log.wtf("KEY", "La key de " + ingrediente + " es " + entry.getKey());

                key = entry.getKey();

                return key;
            }
        }

        return key;

    };




    public void agregaRecetaPublica(String nombre, String procedimiento, String[] ingredientes, String url){

        HashMap recetaMap = new HashMap();
        HashMap ingredientesMap = new HashMap();

        int size = ingredientes.length;

        recetaMap.put("nombre", nombre);
        recetaMap.put("procedimiento", procedimiento);
        recetaMap.put("url", url);

        for (int i = 0; i < size; i++) {
            HashMap aux = new HashMap();
            aux.put("enPosesion", false);
            aux.put("nombre", ingredientes[i]);

            ingredientesMap.put(i+"", aux);
        }

        recetaMap.put("ingredientes", ingredientesMap);

        Log.wtf("Mapa receta", recetaMap.toString());
        dbRef.child("SmartRecipes").child("Recetario").push().updateChildren(recetaMap);

    }



    public void agregaRecetaPersonal(String nombre, String procedimiento, String[] ingredientes, String url){
        HashMap recetaMap = new HashMap();
        HashMap ingredientesMap = new HashMap();

        int size = ingredientes.length;

        recetaMap.put("nombre", nombre);
        recetaMap.put("procedimiento", procedimiento);
        recetaMap.put("url", url);

        for (int i = 0; i < size; i++) {
            HashMap aux = new HashMap();
            aux.put("enPosesion", false);
            aux.put("nombre", ingredientes[i]);

            ingredientesMap.put(i+"", aux);
        }

        recetaMap.put("ingredientes", ingredientesMap);

        Log.wtf("Mapa receta", recetaMap.toString());
        dbRef.child("users").child(this.userID).child("recetasPersonales").push().updateChildren(recetaMap);
    }



    public void editaRecetaPublica(Receta recetaActual, Receta recetaNueva){

        HashMap recetaMap = new HashMap();
        HashMap ingredientesMap = new HashMap();

        String key = recetaActual.key;
        String nombreNuevo = recetaNueva.nombre;



        int size = recetaNueva.ingredientes.size();

        recetaMap.put("nombre", recetaNueva.nombre);
        recetaMap.put("procedimiento", recetaNueva.procedimiento);
        recetaMap.put("url", recetaNueva.url);

        for (int i = 0; i < size; i++) {
            HashMap aux = new HashMap();
            aux.put("enPosesion", false);
            aux.put("nombre", recetaNueva.ingredientes.get(i).nombre);

            ingredientesMap.put(i+"", aux);
        }

        recetaMap.put("ingredientes", ingredientesMap);

        Log.wtf("Mapa recetaEditada", recetaMap.toString());



        dbRef.child("SmartRecipes").child("Recetario").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {

                    //Funcion para asignar valor, el postsnapshot es el objeto que regresa el query ya encontrado.
                    Log.wtf("postsnapshot", (String) postsnapshot.child("nombre").getValue());
                    Log.wtf("nombre", nombreNuevo);
                    Log.wtf("postsnapshotREF", (String) postsnapshot.getKey());


                    if (postsnapshot.getKey().equals(key)) {

                        Log.wtf("entra", "entro");
                        postsnapshot.getRef().updateChildren(recetaMap);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void editaRecetaPersonal(Receta recetaActual, Receta recetaNueva){

        HashMap recetaMap = new HashMap();
        HashMap ingredientesMap = new HashMap();

        String key = recetaActual.key;
        String nombreNuevo = recetaNueva.nombre;



        int size = recetaNueva.ingredientes.size();

        recetaMap.put("nombre", recetaNueva.nombre);
        recetaMap.put("procedimiento", recetaNueva.procedimiento);
        recetaMap.put("url", recetaNueva.url);

        for (int i = 0; i < size; i++) {
            HashMap aux = new HashMap();
            aux.put("enPosesion", false);
            aux.put("nombre", recetaNueva.ingredientes.get(i).nombre);

            ingredientesMap.put(i+"", aux);
        }

        recetaMap.put("ingredientes", ingredientesMap);

        Log.wtf("Mapa recetaEditada", recetaMap.toString());



                   dbRef.child("users").child(this.userID).child("recetasPersonales").addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {

                    //Funcion para asignar valor, el postsnapshot es el objeto que regresa el query ya encontrado.
                    Log.wtf("postsnapshot", (String) postsnapshot.child("nombre").getValue());
                    Log.wtf("nombre", nombreNuevo);
                    Log.wtf("postsnapshotREF", (String) postsnapshot.getKey());


                    if (postsnapshot.getKey().equals(key)) {

                        Log.wtf("entra", "entro");
                        postsnapshot.getRef().updateChildren(recetaMap);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    public void eliminaRecetaPersonal(String nombreReceta){

        dbRef.child("users").child(this.userID).child("recetasPersonales").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {

                    if (postsnapshot.child("nombre").getValue().equals(nombreReceta)) {

                        postsnapshot.getRef().removeValue();

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void eliminaRecetaPublica(String nombreReceta){

        dbRef.child("SmartRecipes").child("Recetario").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {

                    if (postsnapshot.child("nombre").getValue().equals(nombreReceta)) {

                        postsnapshot.getRef().removeValue();

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




}
