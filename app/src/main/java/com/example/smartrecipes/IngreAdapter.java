package com.example.smartrecipes;

import android.content.Intent;
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

public class IngreAdapter extends RecyclerView.Adapter<IngreAdapter.IngreViewHolder> {
        //clase para administrar cada renglón
        public class IngreViewHolder extends RecyclerView.ViewHolder {

            public TextView textoIngre;

            public IngreViewHolder(@NonNull View itemView) {
                super(itemView);

                textoIngre = itemView.findViewById(R.id.nombreRecetaPersonalButton);

            }
        }

        private ArrayList<Ingrediente> ingredientes;

        public IngreAdapter(ArrayList<Ingrediente> ingredientes) {
            this.ingredientes = ingredientes;
        }

        @NonNull
        @Override
        public IngreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_ingre, parent, false);
            Button bEditar = v.findViewById(R.id.editarBoton);
            Button bBorrar = v.findViewById(R.id.verBoton);
            TextView textoIngre = v.findViewById(R.id.nombreRecetaPersonalButton);
            FBHelper fb = new FBHelper();

            bEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(Ingrediente actual: ingredientes){
                        if (actual.nombre == textoIngre.getText()){
                            Intent in = new Intent( v.getContext(), EditarIngredienteActivity.class);
                            in.putExtra("ELACTUALNOMBRE", actual.nombre);
                            in.putExtra("ELACTUALKEY", actual.key);
                            v.getContext().startActivity(in);
                        }
                    }

                    Log.wtf("Ingrediente editado: ", (String) textoIngre.getText());
                }
            });

            bBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for(Ingrediente actual: ingredientes){
                        if (actual.nombre == textoIngre.getText()){
                            Log.wtf("KEYACTUALIZARADAPTER", actual.key);
                            fb.borrarIngrediente(actual.key);
                        }
                    }

                    Toast.makeText(v.getContext(), "Ingrediente borrado: " + textoIngre.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            IngreViewHolder ivh = new IngreViewHolder(v);

            return ivh;
        }

        @Override
        public void onBindViewHolder(@NonNull IngreViewHolder holder, int position) {
            holder.textoIngre.setText(ingredientes.get(position).nombre);
        }

        @Override
        public int getItemCount() {
            return ingredientes.size();
        }
    }
