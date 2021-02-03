package com.example.smartrecipes;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecetaPersonalAdapter extends RecyclerView.Adapter<RecetaPersonalAdapter.RecetaPerViewHolder> {
    //clase para administrar cada renglón
    public class RecetaPerViewHolder extends RecyclerView.ViewHolder {

        public TextView texto;

        public RecetaPerViewHolder(@NonNull View itemView) {
            super(itemView);

            texto = itemView.findViewById(R.id.nombreRecetaPersonalButton);

        }
    }
    private ArrayList<Receta> recetasPersonalesDisponibles;
    private View.OnClickListener listener;

    public RecetaPersonalAdapter(ArrayList<Receta> recetasPersonalesDisponibles, View.OnClickListener listener){
        this.recetasPersonalesDisponibles = recetasPersonalesDisponibles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecetaPerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_receta_personal, parent, false);
        //v.setOnClickListener(listener);
        Button bEditar = v.findViewById(R.id.editarRecetaPersonalButton);
        Button bVer = v.findViewById(R.id.verRecetaPersonalButton);
        TextView recetaTextView = v.findViewById(R.id.nombreRecetaPersonalButton);
        //VerReceta

        bEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent( v.getContext(), EditarReceta.class);
                for(Receta actual: recetasPersonalesDisponibles){
                    if (actual.nombre == recetaTextView.getText()){

                        //int pos = mRecyclerView.getChildLayoutPosition(v);
                        //FBHelper fb = new FBHelper();
                        //fb.editaRecetaPublica(recetasDisponibles.get(pos));
                        //Receta recetaEnviada = recetasDisponibles.get(pos);
                        in.putExtra("recetaAEditar", actual);
                        in.putExtra("clave", 1);
                       // Toast.makeText(v.getContext(), "Vamos a editar una receta", Toast.LENGTH_SHORT).show();
                        v.getContext().startActivity(in);
                    }
                }
            }
        });

        bVer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent in = new Intent( v.getContext(), VerReceta.class);
                for(Receta actual: recetasPersonalesDisponibles){
                    if (actual.nombre == recetaTextView.getText()){

                        //int pos = mRecyclerView.getChildLayoutPosition(v);
                        //FBHelper fb = new FBHelper();
                        //fb.editaRecetaPublica(recetasDisponibles.get(pos));
                        //Receta recetaEnviada = recetasDisponibles.get(pos);
                        in.putExtra("recetaParaVer", actual);
                       // Toast.makeText(v.getContext(), "Vamos a editar una receta", Toast.LENGTH_SHORT).show();
                        v.getContext().startActivity(in);
                    }
                }
            }
        });

        RecetaPerViewHolder ivh = new RecetaPerViewHolder(v);

        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecetaPerViewHolder holder, int position) {
        holder.texto.setText(recetasPersonalesDisponibles.get(position).nombre);
    }

    @Override
    public int getItemCount() {
        return recetasPersonalesDisponibles.size();
    }
}
