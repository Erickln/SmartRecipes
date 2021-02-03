package com.example.smartrecipes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Agregacion extends AppCompatActivity {

    private EditText mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregacion);

        mTextView = (EditText) findViewById(R.id.nuevoIngre);

    }
    public void volver(){
        Intent retorno = new Intent();
        retorno.putExtra("ingre", mTextView.getText().toString());

        //enviarlo
        setResult(Activity.RESULT_OK, retorno);
        mTextView.setText("");
        //cerrar actividad
        finish();
    }
}