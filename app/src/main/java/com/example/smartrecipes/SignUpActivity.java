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

public class SignUpActivity extends AppCompatActivity {

    TextView email, password;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



    }

    public void registro(View v){

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        Intent intent = new Intent();



        //FIREBASE
        mFirebaseAuth = FirebaseAuth.getInstance();
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



    }
}