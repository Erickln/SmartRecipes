package com.example.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Receta {

    String nombre;
    String[] ingredientes;

    public Receta(String nombre, String[] ingredientes){
        this.nombre=nombre;
        this.ingredientes = ingredientes;
    }

    public String toString(){
        String res="";
        for (int i = 0; i < this.ingredientes.length; i++) {
            if (i==this.ingredientes.length-1){
                res+=ingredientes[i];
            }
            res+=ingredientes[i]+", ";
        }
        return "Receta: "+nombre+". Ingredientes: "+res;
    }
    public static Receta[] recetario(){
        String nombre="";
        Receta[] Recetas=new Receta[0];
        String[] ing = new String[0];
        try {
            File file = new File("app/src/main/java/com/example/smartrecipes/recetario.csv");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArr;

            boolean flag=true;
            br.readLine();
            while((line = br.readLine()) != null) {
                tempArr = line.split(",");
                for(String tempStr : tempArr) {
                    if (flag){
                        flag=false;
                        nombre=tempStr;
                        continue;
                    }
                    String[] res=new String[ing.length+1];
                    System.arraycopy(ing,0,res,0,ing.length);
                    res[res.length-1]=tempStr;
                    ing=res;
                }
                Receta[] RecetasAux=new Receta[Recetas.length+1];
                System.arraycopy(Recetas,0,RecetasAux,0,Recetas.length);
                RecetasAux[RecetasAux.length-1]=new Receta(nombre,ing);
                Recetas=RecetasAux;
                flag=true;
            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return Recetas;
    }

    public static void main(String[] args) {
        String nombre="";
        Receta[] Recetas=new Receta[0];
        String[] ing = new String[0];
        try {
            File file = new File("app/src/main/java/com/example/smartrecipes/recetario.csv");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArr;

            boolean flag=true;
            br.readLine();
            while((line = br.readLine()) != null) {
                tempArr = line.split(",");
                for(String tempStr : tempArr) {
                    if (flag){
                        flag=false;
                        nombre=tempStr;
                        continue;
                    }
                    String[] res=new String[ing.length+1];
                    System.arraycopy(ing,0,res,0,ing.length);
                    res[res.length-1]=tempStr;
                    ing=res;
                }
                Receta[] RecetasAux=new Receta[Recetas.length+1];
                System.arraycopy(Recetas,0,RecetasAux,0,Recetas.length);
                RecetasAux[RecetasAux.length-1]=new Receta(nombre,ing);
                Recetas=RecetasAux;
                flag=true;
            }
            br.close();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        for (int i = 0; i < Recetas.length; i++) {
            System.out.println(Recetas[i]);
        }
    }
}
