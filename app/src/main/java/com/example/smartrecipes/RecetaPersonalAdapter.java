package com.example.smartrecipes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecetaPersonalAdapter extends RecyclerView.Adapter<RecetaPersonalAdapter.RecetaPerViewHolder> {
    //clase para administrar cada renglón
    public class RecetaPerViewHolder extends RecyclerView.ViewHolder {

        public TextView texto;

        public RecetaPerViewHolder(@NonNull View itemView) {
            super(itemView);

            texto = itemView.findViewById(R.id.text);

        }
    }
    private ArrayList<Receta> recetasPersonales;
    private View.OnClickListener listener;

    public RecetaPersonalAdapter(ArrayList<Receta> recetasPersonales,  View.OnClickListener listener){
        this.recetasPersonales = recetasPersonales;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecetaPerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_receta_personal, parent, false);
        v.setOnClickListener(listener);

        RecetaPerViewHolder ivh = new RecetaPerViewHolder(v);

        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecetaPerViewHolder holder, int position) {
        holder.texto.setText(recetasPersonales.get(position).nombre);
    }

    @Override
    public int getItemCount() {
        return recetasPersonales.size();
    }
}
