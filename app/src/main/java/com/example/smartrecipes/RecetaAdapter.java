package com.example.smartrecipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.RecetaViewHolder> {
    //clase para administrar cada renglón
    public class RecetaViewHolder extends RecyclerView.ViewHolder {

        public TextView texto;

        public RecetaViewHolder(@NonNull View itemView) {
            super(itemView);

            texto = itemView.findViewById(R.id.text);

        }
    }

    private ArrayList<Receta> recetasDisponibles;
    private View.OnClickListener listener;
    RecyclerView mRecyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }

    public RecetaAdapter(ArrayList<Receta> recetasDisponibles, View.OnClickListener listener) {
        this.recetasDisponibles = recetasDisponibles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_receta, parent, false);
        //v.setOnClickListener(listener);
        int pos = mRecyclerView.getChildLayoutPosition(v);
        Button bEditar = v.findViewById(R.id.button6);
        TextView recetaTextView = v.findViewById(R.id.text);

        bEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent( v.getContext(), EditarReceta.class);
                for(Receta actual: recetasDisponibles){
                    if (actual.nombre == recetaTextView.getText()){

                        //int pos = mRecyclerView.getChildLayoutPosition(v);
                        //FBHelper fb = new FBHelper();
                        //fb.editaRecetaPublica(recetasDisponibles.get(pos));
                        //Receta recetaEnviada = recetasDisponibles.get(pos);
                        //recetasDisponibles.remove(pos);
                        in.putExtra("recetaAEditar", actual);
                        in.putExtra("recetaAEditarLlave", actual.key);
                        Toast.makeText(v.getContext(), "Vamos a editar una receta", Toast.LENGTH_SHORT).show();
                        v.getContext().startActivity(in);
                    }
                }


            }

        });

        RecetaViewHolder ivh = new RecetaViewHolder(v);

        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecetaViewHolder holder, int position) {
        holder.texto.setText(recetasDisponibles.get(position).nombre);
    }

    @Override
    public int getItemCount() {
        return recetasDisponibles.size();
    }
}
