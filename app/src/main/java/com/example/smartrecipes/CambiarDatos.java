package com.example.smartrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CambiarDatos extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_datos);



    }


    public void regresarDatos(View v){
        editText = findViewById(R.id.bEditNombre);
        String nuevoNombre = editText.getText().toString();
        FBHelper fbH = new FBHelper();
        fbH.setUserName(nuevoNombre);

        Intent retorno = new Intent();
        retorno.putExtra("nuevonombre", nuevoNombre);
        setResult(Activity.RESULT_OK, retorno);
        finish();
    }


}