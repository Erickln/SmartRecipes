package com.example.smartrecipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class loginActivity extends AppCompatActivity {

    TextView mail, pass;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mail = findViewById(R.id.userName);
        pass = findViewById(R.id.pass);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null){
                    Intent i = new Intent(loginActivity.this, IngredientesAct.class);
                    startActivity(i);
                }else{
                    Toast.makeText(loginActivity.this, "No loging", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }


    public void login(View v){

        if (mail.getText().toString().contentEquals("")) {


            Toast.makeText(loginActivity.this, "Email vacío", Toast.LENGTH_SHORT).show();


        } else if (pass.getText().toString().contentEquals("")) {


            Toast.makeText(loginActivity.this, "Contraseña vacía", Toast.LENGTH_SHORT).show();


        }else if(pass.getText().toString().length() < 6){

            Toast.makeText(loginActivity.this, "Contraseña debe ser de más de 6 caracteres", Toast.LENGTH_SHORT).show();

        }else if(!validate(mail.getText().toString())){

            Toast.makeText(loginActivity.this, "Email no válido", Toast.LENGTH_SHORT).show();

        }else {
            mFirebaseAuth.signInWithEmailAndPassword(mail.getText().toString(), pass.getText().toString()).addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intentlito = new Intent(loginActivity.this, IngredientesAct.class);
                        startActivity(intentlito);
                    } else if(task.getException().getMessage() == "There is no user record corresponding to this identifier. The user may have been deleted."){

                        Toast.makeText(loginActivity.this, "Usuario no existente", Toast.LENGTH_SHORT).show();

                    } else if(task.getException().getMessage() == "The password is invalid or the user does not have a password."){

                        Toast.makeText(loginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

}