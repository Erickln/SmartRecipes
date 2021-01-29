package com.example.smartrecipes;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class agregaringFragment extends Fragment {

    private EditText ingrediente;
    private FBHelper fbhelper;



    public agregaringFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fbhelper = new FBHelper();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_agregaring, container, false);
        ingrediente = v.findViewById(R.id.vNuevoIng);
        Button b1 = v.findViewById(R.id.buttonAgregaring);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ing = ingrediente.getText().toString();
                fbhelper.guardaIngrediente(ing);
            }
        });

        return v;
    }
}