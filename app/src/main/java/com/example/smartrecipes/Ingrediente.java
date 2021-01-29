package com.example.smartrecipes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Ingrediente implements Serializable {
    public String nombre;
    private boolean enPosesion;

    public Ingrediente(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEnPosesion() {
        return enPosesion;
    }

    public void setEnPosesion(boolean enPosesion) {
        this.enPosesion = enPosesion;
    }

    public Ingrediente(String nombre){
        this.nombre=nombre;
        enPosesion =false;
    }

    public static Ingrediente[] StringToIng(String[] array){
        Ingrediente[] res = new Ingrediente[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i]=new Ingrediente(array[i]);
        }
        return res;
    }

    public JSONObject toJSON() throws JSONException {

        JSONObject JSON = new JSONObject("{'nombre':"+nombre+'"');
        return JSON;
    }

    public String toString(){
        return "Ingrediente: "+this.nombre;
    }

}


