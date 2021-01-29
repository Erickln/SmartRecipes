package com.example.smartrecipes;


import android.os.Build;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import androidx.annotation.NonNull;
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

    public void guardaIngrediente(String ingrediente ){
        Log.wtf("firebase", this.userID);
        dbRef.child("users").child(userID).child("ingredientes").push().setValue(ingrediente);
    }


    public void borrarIngrediente(String ingrediente, Map<String, String> ingredientes){

        //Primero se hace la busqueda de la key
        String key = getKeyIngrediente(ingrediente, ingredientes);

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




    public void actualizarIngrediente(String ingrediente, Map<String, String> ingredientes, String nuevoIngrediente){

        //Encuentra la key del elemento a actualizar
        String key = getKeyIngrediente(ingrediente, ingredientes);




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



}
