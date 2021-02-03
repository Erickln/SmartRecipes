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

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.RecetaViewHolder> {
    //clase para administrar cada renglón
    public class RecetaViewHolder extends RecyclerView.ViewHolder {

        public TextView texto;

        public RecetaViewHolder(@NonNull View itemView) {
            super(itemView);

            texto = itemView.findViewById(R.id.nombreRecetaPersonalButton);

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
        Button bEditar = v.findViewById(R.id.editarBoton);
        Button bVer = v.findViewById(R.id.verBoton);
        TextView recetaTextView = v.findViewById(R.id.nombreRecetaPersonalButton);
        //VerReceta

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
                        in.putExtra("recetaAEditar", actual);
                        in.putExtra("clave", 0);
                        Toast.makeText(v.getContext(), "Vamos a editar una receta", Toast.LENGTH_SHORT).show();
                        v.getContext().startActivity(in);
                    }
                }
            }
        });

        bVer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent in = new Intent( v.getContext(), VerReceta.class);
                for(Receta actual: recetasDisponibles){
                    if (actual.nombre == recetaTextView.getText()){

                        //int pos = mRecyclerView.getChildLayoutPosition(v);
                        //FBHelper fb = new FBHelper();
                        //fb.editaRecetaPublica(recetasDisponibles.get(pos));
                        //Receta recetaEnviada = recetasDisponibles.get(pos);
                        in.putExtra("recetaParaVer", actual);
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
