package com.example.smartrecipes;


import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

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


    public void borrar(String ingrediente){
        Log.wtf("firebase", this.userID);
        dbRef.child("users").child(userID).child("ingredientes").push().setValue(ingrediente);
    }

    public void actualizar(){

    }

}
