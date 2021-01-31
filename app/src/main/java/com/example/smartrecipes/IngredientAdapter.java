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

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    //clase para dministrar cada renglón
    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        public TextView textoide;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            textoide = itemView.findViewById(R.id.textote);

        }
    }
    private ArrayList<Ingrediente> ingredientes= new ArrayList<>();

    public IngredientAdapter(ArrayList<Ingrediente> ingredientes){
        this.ingredientes=ingredientes;
    }

   // public IngredientAdapter(ArrayList<Ingrediente> ingredientes){this.ingredientes=ingredientes;}

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_ingrediente, parent, false);
        Button bEditar = v.findViewById(R.id.editarButton);

        bEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView recyclerView = v.findViewById(R.id.recycler);
                int pos = recyclerView.getChildLayoutPosition(v);
                Toast.makeText(recyclerView.getContext(), ingredientes.get(pos).nombre, Toast.LENGTH_SHORT).show();
                Log.wtf("BOTONAZO", "BOTON PRESIONADO");

            }
        });

        Button bBorrar = v.findViewById(R.id.borrarButton2);

        bBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView recyclerView = v.findViewById(R.id.recycler);
                int pos = recyclerView.getChildLayoutPosition(v);
                Toast.makeText(recyclerView.getContext(), "INGREDIENTE: " +ingredientes.get(pos).nombre + " BORRADO", Toast.LENGTH_SHORT).show();
                Log.wtf("BOTONAZO", "BOTON PRESIONADO");
            }
        });

        IngredientViewHolder ivh = new IngredientViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.IngredientViewHolder holder, int position) {
        holder.textoide.setText(ingredientes.get(position).nombre);
    }

    @Override
    public int getItemCount() {
        return ingredientes.size();
    }
}
