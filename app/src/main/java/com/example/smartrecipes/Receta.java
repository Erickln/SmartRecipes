package com.example.smartrecipes;

import java.io.Serializable;
import java.util.ArrayList;

public class Receta implements Serializable {
    public Receta() { }

    public String nombre=" ";
    public String procedimiento=" ";
    public ArrayList<Ingrediente> ingredientes;

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public Receta(String nombre, ArrayList<Ingrediente> ingredientes, String procedimiento) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.procedimiento = procedimiento;
    }

    public boolean disponibilidad(){
        for (int i = 0; i < ingredientes.size(); i++) {
            if (ingredientes.get(i).isEnPosesion() ==false){
                return false;
            }
        }
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Receta(String nombre, ArrayList<Ingrediente> ingredientes){
        this.nombre=nombre;
        this.ingredientes = ingredientes;
    }

    public Receta(String nombre){
        this.nombre=nombre;
        this.ingredientes = new ArrayList<>();
    }

    public ArrayList<Ingrediente> addIngrediente(Ingrediente ingrediente){
        this.ingredientes.add(ingrediente);
        return this.ingredientes;
    }

    public String toString(){
        String res="";
        for (int i = 0; i < this.ingredientes.size(); i++) {
            if (i==this.ingredientes.size()-1){
                res+=ingredientes.get(i);
                break;
            }
            res+=ingredientes.get(i)+", ";
        }
        return "Receta: "+nombre+".\n Ingredientes: "+res;
    }

    public static boolean getDip(String nombre, Receta[] Recetario){
        for (int i = 0; i < Recetario.length; i++) {
            if (nombre==Recetario[i].nombre){
                return Recetario[i].disponibilidad();
            }
        }
        return false;
    }


    public static void main(String[] args){

    }
}


