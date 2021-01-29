package com.example.smartrecipes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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

    public RecetaAdapter(ArrayList<Receta> recetasDisponibles){
        this.recetasDisponibles = recetasDisponibles;
    }

    @NonNull
    @Override
    public RecetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.fila, parent, false);
        Button b = v.findViewById(R.id.editarButton);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(), "BOTON PRESIONADO", Toast.LENGTH_SHORT).show();
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
