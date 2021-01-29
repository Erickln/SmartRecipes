package com.example.smartrecipes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.grpc.okhttp.internal.framed.FrameReader;

public class IngredientesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<String> ingredientes;
    private static final String KEY_INGREDIENT = "Ingrediente";

    public IngredientesFragment() {
        // Required empty public constructor
    }

    public static IngredientesFragment newInstance() {
        IngredientesFragment fragment = new IngredientesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredientes = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_ingredientes, container, false);
        recyclerView = v.findViewById(R.id.recycler);
        creaLista();
        return v;
    }
    public void creaLista(){
        IngredientAdapter adapter = new IngredientAdapter(ingredientes);

        // layout manager
        // define cómo se van a organizar los elementos en el recycler view
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        GridLayoutManager glm = new GridLayoutManager(getContext(), 1);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

}