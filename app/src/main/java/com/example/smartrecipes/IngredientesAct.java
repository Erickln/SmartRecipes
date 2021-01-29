package com.example.smartrecipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;

public class IngredientesAct extends AppCompatActivity {
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