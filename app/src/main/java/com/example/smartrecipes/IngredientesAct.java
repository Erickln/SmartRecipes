package com.example.smartrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

public class IngredientesAct extends AppCompatActivity {

    private DBHelper db;
    private TextView texto, emailUsuario;
    String[] myArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientes);

        texto = findViewById(R.id.textView4);
        emailUsuario = findViewById(R.id.nombreUsuario);
        db = new DBHelper(this);
        myArray = db.pruebaDesplegar();
        int sizeArr = myArray.length;

        for (int i = 0; i<sizeArr; i++){
            texto.append("-  " + myArray[i] + "\n");
        }

        emailUsuario.setText("Hola, " + FirebaseAuth.getInstance().getCurrentUser().getEmail());
        /////////////////            Pruebas con JSON
        /*
        try {
            JSONObject obj1 = new JSONObject(DBHelper.toJSON(new Receta(
                    "Camarones a la crema",new Ingrediente[]{new Ingrediente("camarones"),
                    new Ingrediente("crema")})));

            JSONObject obj41 = obj1.getJSONObject("ingrediente");
            Log.wtf("JSON",obj1.toString()+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
        /////////////////
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