package com.example.smartrecipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IngredientesAct extends AppCompatActivity {
    ArrayList myArray2;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseReference;
    private DatabaseReference dbRef;
    private String userID;
    private ArrayList ingredientes;

    IngredientesFragment fragmento;
    private static final String TAG_FRAGMENTO = "fragmento";
    private DBHelper db;
    private TextView texto, emailUsuario;
    String[] myArray;
    public EditText textoIngrediente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientes);

        texto = findViewById(R.id.textote);
        emailUsuario = findViewById(R.id.nombreUsuario);
        db = new DBHelper(this);
        myArray = db.pruebaDesplegar();
        //listado de ingredientes
        int sizeArr = myArray.length;

        for (int i = 0; i<sizeArr; i++){
            texto.append("-  " + myArray[i] + "\n");
        }
        //saludo
        emailUsuario.setText("Hola, " + FirebaseAuth.getInstance().getCurrentUser().getEmail());

        fragmento = new IngredientesFragment();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.contenedor, fragmento, TAG_FRAGMENTO);
        transaction.commit();

///////////////////////////////////////////// Carga ingredientes////////////////

        //FIREBASE
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseReference = FirebaseDatabase.getInstance();
        dbRef = firebaseReference.getReference();
        this.userID = mFirebaseAuth.getUid();

        dbRef.child("users").child(this.userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ingredientes = new ArrayList();

                for (DataSnapshot child: dataSnapshot.getChildren()) {

                    String value = child.getValue().toString();
                    ingredientes.add(value);
                    Log.wtf("forrrrrrrr", ingredientes.toString());
                    for (int i = 0; i<ingredientes.size(); i++){
                        texto.append("-  " + ingredientes.get(i) + "\n");
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void agregarView(View v) {

        Intent intent = new Intent(this, RegistroIngredientes.class);

        startActivity(intent);
    }


    public void logOut(View v){
        FirebaseAuth.getInstance().signOut();
        Intent toLogin = new Intent(IngredientesAct.this, MainActivity.class);
        startActivity(toLogin);
    }
    public void eliminarView(View v) {
        Intent i = new Intent(this, EliminarIngrediente.class);
        startActivity(i);
    }

    public void irRecetas(View v){
        Intent i = new Intent(this, RecetasDisponibles.class);
        i.putExtra("ingredientes",myArray);
        startActivity(i);
    }


}