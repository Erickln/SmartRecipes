package com.example.smartrecipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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

    //profilepic
    StorageReference storageReference;
    ImageButton imageButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientes);

        texto = findViewById(R.id.textote);
        emailUsuario = findViewById(R.id.nombreUsuario);
        imageButton = findViewById(R.id.imageButton);

        //profile pic
        storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            getProfilePic(user);
        } else {
            // No user is signed in
            Log.wtf("msg","no hay user");
        }




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


        dbRef.child("users").child(this.userID).child("ingredientes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot child) {

                //For para acceder a todos los children de la base de datos.

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


///////////Aqui estan testeados los metodos de nuestro firebaseHelper///////////////

 //       fbHelper.actualizarIngrediente("sal", mapIngredientes, "manzana");

//        fbHelper.borrarIngrediente("uva", mapIngredientes);

//        fbHelper.guardaIngrediente("sandia");

//        String[] ingredientes = {"pera", "durazno"};
//        fbHelper.agregaRecetaPersonal("PDPDPD", "Colocas pera y durazno", ingredientes, "saludos");

//        String[] ingredientes = {"pera", "durazno"};
//        fbHelper.agregaRecetaPublica("PDPDPD", "Colocas pera y durazno", ingredientes, "saludos");


//El editarRecetas utiliza el nombre de la receta como referencia. Le deben pasar el nombre de la receta que quieran editar y los demas datos son los que se van a actualizar.

//        String[] ingredientes = {"manzan", "pina"};
//        fbHelper.editaRecetaPersonal("PDPDPD", "Colocas manzana y pina", ingredientes, "saludos2");

//        String[] ingredientes = {"manzan", "pina"};
//        fbHelper.editaRecetaPublica("PDPDPD", "Colocas manzana y pina", ingredientes, "saludos2");

//Igual el eliminar, utiliza unicamente el nombre como referencia.

//        fbHelper.eliminaRecetaPersonal("PDPDPD");
//        fbHelper.eliminaRecetaPublica("PDPDPD");

////////////////////////////////////////////////////////////////////////////////////////


        cambiarFragmento(agregaringFragment);
    }

    @Override
    public void pasarDato(String ingrediente) {
        fbHelper.guardaIngrediente(ingrediente);
    }

    //imagebutton para el perfil
    public void datosusuario(View v){
        Intent i = new Intent(this, DatosUsuario.class);
        startActivityForResult(i, 1);
    }

    //obtener la imagen del storage
    public void getProfilePic(FirebaseUser user){
        StorageReference profileRef = storageReference.child("users/"+user.getUid()+"/profilepic.jpg");
        Log.wtf("msg","fileref:" + profileRef.toString());

        if (profileRef == null){

        }
        else{
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(imageButton);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.wtf("msg","error al cargar la imagen de perfil");
                }
            });
        }

    }

    //reiniciar la actividad para que se muestre la nueva foto
    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

}


