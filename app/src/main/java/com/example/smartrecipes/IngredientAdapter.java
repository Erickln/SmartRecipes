package com.example.smartrecipes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private ArrayList<String> ingredientes;

    public IngredientAdapter(ArrayList<String> ingredientes){
        this.ingredientes = ingredientes;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.fililla, parent, false);
        IngredientViewHolder ivh = new IngredientViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.IngredientViewHolder holder, int position) {
        holder.textoide.setText(ingredientes.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredientes.size();
    }
}
