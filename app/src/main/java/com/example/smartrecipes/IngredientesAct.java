package com.example.smartrecipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class IngredientesAct extends AppCompatActivity implements agregaringFragment.Callback{

    //Variables de firebase para recoleccion de ingredientes
    List myArray2;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseReference;
    private DatabaseReference dbRef;
    private String userID;
    private ArrayList<Ingrediente> ingredientes;
    private Map<String, String> mapIngredientes;
    private FBHelper fbHelper;

    //GUI
    IngredientesFragment ingredienteFragment;
    agregaringFragment agregaringFragment;
    private static final String TAG_FRAGMENTO = "fragmento";
    private TextView texto, emailUsuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientes);

        texto = findViewById(R.id.textote);
        emailUsuario = findViewById(R.id.nombreUsuario);


//////////Carga ingredientes con la base de datos local

//        db = new DBHelper(this);
//        myArray = db.pruebaDesplegar();
//        //listado de ingredientes
//        int sizeArr = myArray.length;
//
//        for (int i = 0; i<sizeArr; i++){
//            texto.append("-  " + myArray[i] + "\n");
//        }


        //saludo
        emailUsuario.setText("Hola, " + FirebaseAuth.getInstance().getCurrentUser().getEmail());

        agregaringFragment = new agregaringFragment();


        ///////////////// Carga ingredientes con Firebase////////////////
        fbHelper = new FBHelper();
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseReference = FirebaseDatabase.getInstance();
        dbRef = firebaseReference.getReference();
        this.userID = mFirebaseAuth.getUid();


        dbRef.child("users").child(this.userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //For para acceder a todos los children de la base de datos.
                for (DataSnapshot child: dataSnapshot.getChildren()) {


                    //Se crea un hashmap que se inicializa con lo que recibimos del child de firebase.
                    mapIngredientes = (Map) child.getValue();
                    Log.wtf("mapeado", mapIngredientes.toString());


                    //Aqui es donde se guardan los ingredientes en nuestra variable local "List ingredientes".
//////////////////////ESTE METODO ES IMPORTANTE, ES LA COVERSION DE MAPA A ARRAYLIST/////////////////////////////////////////////////////
                    //Keys
                    ArrayList <String> ingredientesKeysAux = new ArrayList(mapIngredientes.keySet());
                    ArrayList<Ingrediente> auxKeys = new ArrayList<>();
                    //IngredientesValues
                    ArrayList <String> ingredientes3 = new ArrayList(mapIngredientes.values());
                    ArrayList<Ingrediente> aux = new ArrayList<>();

                    for (int i = 0; i < ingredientes3.size(); i++) {
                        aux.add(new Ingrediente(ingredientes3.get(i), ingredientesKeysAux.get(i)));
                    }
                    ingredientes=aux;

                    ingredienteFragment = IngredientesFragment.newInstance(ingredientes, ingredientesKeysAux);
                    //añadir fragmento al contenedor
                    cambiarFragmento(ingredienteFragment);

                    //For para recorrer el arraylist, aqui se asignan los valores a las Views,
                    //Aunque en este caso es una simple concatenacion a un unico textView
                   //for (int i = 0; i < ingredientes.size(); i++){

                   //}

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void logOut(View v){
        FirebaseAuth.getInstance().signOut();
        Intent toLogin = new Intent(IngredientesAct.this, MainActivity.class);
        startActivity(toLogin);
    }

    public void irRecetas(View v){
        Intent i = new Intent(this, RecetasDisponibles.class);
        i.putExtra("ingredientes",ingredientes);
        startActivity(i);
    }

    public void cambiarFragmento(Fragment nuevo){

        FragmentManager manager = getSupportFragmentManager();
        Fragment f = manager.findFragmentByTag(TAG_FRAGMENTO);
        FragmentTransaction transaction = manager.beginTransaction();

        // si el fragmento que queremos agregar es el que ya está
        if(nuevo == f)
            return;

        if(f != null){
            transaction.remove(f);
        }

        transaction.add(R.id.contenedor, nuevo, TAG_FRAGMENTO);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    public void fragmentoagregar(View v){
 //       fbHelper.actualizarIngrediente("sal", mapIngredientes, "manzana");
//        fbHelper.borrarIngrediente("uva", mapIngredientes);
        //fbHelper.guardaIngrediente("sandia");
        cambiarFragmento(agregaringFragment);
    }

    @Override
    public void pasarDato(String ingrediente) {
        fbHelper.guardaIngrediente(ingrediente);
    }
}


