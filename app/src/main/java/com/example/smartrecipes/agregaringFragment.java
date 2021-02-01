package com.example.smartrecipes;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import javax.security.auth.callback.Callback;


public class agregaringFragment extends Fragment {

    private EditText ingrediente;
    private Callback observador;

    public agregaringFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                observador.pasarDato(ing);
                ingrediente.setText("");
            }
        });

        return v;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof Callback) {

            observador = (Callback)context;
        } else {

            throw new RuntimeException("FALTA IMPLEMENTAR LA INTERFAZ CALLBACK EN ACTIVIDAD QUE ANEXA FRAGMENTO");
        }
    }

    public interface Callback{

        public void pasarDato(String ingrediente);
    }

    // Para volver al fragmento anterior con el botón de "atras" del celular
    public void onBackPressed()
    {
        //   FragmentManager fm = getActivity().getSupportFragmentManager();
        //   fm.popBackStack();
        getFragmentManager().beginTransaction().
                remove(this).commit();
    }
}