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
import androidx.appcompat.app.AppCompatActivity;
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
        Button bBorrar = v.findViewById(R.id.borrarButton2);
        TextView ingrediente = v.findViewById(R.id.textote);
        FBHelper fbh = new FBHelper();

        bEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(Ingrediente actual: ingredientes){
                    if (actual.nombre == ingrediente.getText()){

                       Intent in = new Intent( v.getContext(), EditarIngredienteActivity.class);
                       in.putExtra("ELACTUALNOMBRE", actual.nombre);
                        in.putExtra("ELACTUALKEY", actual.key);
                       v.getContext().startActivity(in);
                    }
                }

                Log.wtf("Ingrediente editado: ", (String) ingrediente.getText());
            }
        });


        bBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(Ingrediente actual: ingredientes){
                    if (actual.nombre == ingrediente.getText()){
                        Log.wtf("KEYACTUALIZARADAPTER", actual.key);
                        fbh.borrarIngrediente(actual.key);
                    }
                }

                Toast.makeText(v.getContext(), "Ingrediente borrado: " + ingrediente.getText(), Toast.LENGTH_SHORT).show();
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
