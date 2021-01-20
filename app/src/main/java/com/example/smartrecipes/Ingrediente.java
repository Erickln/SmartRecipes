package com.example.smartrecipes;

import java.io.Serializable;

public class Ingrediente implements Serializable {
    String nombre;
    boolean disponibilidad;

    public Ingrediente(String nombre){
        this.nombre=nombre;
        disponibilidad=false;
    }

    public static Ingrediente[] StringToIng(String[] array){
        Ingrediente[] res = new Ingrediente[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i]=new Ingrediente(array[i]);
        }
        return res;
    }

    public String toString(){
        return this.nombre;
    }

}
