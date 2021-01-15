package com.example.test;

public class Ingrediente {
    String nombre;
    boolean disponibilidad;

    public Ingrediente(String nombre){
        this.nombre=nombre;
        disponibilidad=false;
    }

    public String toString(){
        return this.nombre;
    }

}
