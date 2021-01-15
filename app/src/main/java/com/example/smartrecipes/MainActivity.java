package com.example.smartrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

//**********************************************************************************
//*Esta es solo y únicamente la actividad de bienvenida, solo Kevin puede usarla.!!*
//**********************************************************************************
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void ingredientesView(View v) {

        //toast - mensaje temporat en la parte inferior del celular
        Toast.makeText(this, "BOTON PRESIONADO", Toast.LENGTH_SHORT).show();


        //para abrir actividad hay que solicitarlo
        //hay que llenar formato/intent para la solicitud
        //puede ser implicito con accion o explicito con tipo

        Intent intentlito = new Intent(this, IngredientesAct.class);

        startActivity(intentlito);

    }
}
