package com.example.smartrecipes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//**********************************************************************************
//*Esta es solo y únicamente la actividad de bienvenida, solo Kevin puede usarla.!!*
//**********************************************************************************
public class MainActivity extends AppCompatActivity {

    //VARIABLES
    TextView mail, pass;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //STATICS
    public static final int SIGNUP_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void signbut(View v){
        Intent signUp = new Intent(this, SignUpActivity.class);
        startActivityForResult(signUp, SIGNUP_CODE);
    }


    public void loginBut(View v){
        Intent login = new Intent(this, loginActivity.class);
        startActivity(login);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGNUP_CODE && resultCode == Activity.RESULT_OK && data != null){

            String exito = data.getStringExtra("mensaje");
            Toast.makeText(this, exito, Toast.LENGTH_LONG).show();

        }
    }







    //TOOL SALTARSE LOGIN
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
