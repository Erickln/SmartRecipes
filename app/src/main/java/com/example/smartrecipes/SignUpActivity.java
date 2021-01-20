package com.example.smartrecipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    TextView email, password;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



    }

    public void registro(View v) {

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Intent intent = new Intent();
        mFirebaseAuth = FirebaseAuth.getInstance();


        if (email.getText().toString().contentEquals("")) {


            Toast.makeText(SignUpActivity.this, "Email vacío", Toast.LENGTH_SHORT).show();


        } else if (password.getText().toString().contentEquals("")) {


            Toast.makeText(SignUpActivity.this, "Contraseña vacía", Toast.LENGTH_SHORT).show();


        }else if(password.getText().toString().length() < 6){

            Toast.makeText(SignUpActivity.this, "Contraseña debe ser de más de 6 caracteres", Toast.LENGTH_SHORT).show();

        }else if(!validate(email.getText().toString())){

            Toast.makeText(SignUpActivity.this, "Email no válido", Toast.LENGTH_SHORT).show();

        }else {
            mFirebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "Entro exito", Toast.LENGTH_SHORT).show();
                        intent.putExtra("mensaje", "Registro exitoso");
                        setResult(Activity.RESULT_OK, intent);
                    }else{
                        Toast.makeText(SignUpActivity.this, "ERROR:" + task.getException(), Toast.LENGTH_SHORT).show();
                        intent.putExtra("mensaje", "Registro Fallo");
                        setResult(Activity.RESULT_OK, intent);
                    }
                }
            });



    }};

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}