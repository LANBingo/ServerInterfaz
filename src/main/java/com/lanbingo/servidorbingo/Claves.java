package com.lanbingo.servidorbingo;

import java.util.ArrayList;
import java.util.List;

public class Claves {
    public static List<String> claves = new ArrayList<>() {
    };

    public static void nuevaClave(String pass){
       if (claves.contains(claves.indexOf(pass))){
           System.out.println("Clave ya existente");
       }
       else {
           claves.add(pass);
       }
    }
    /*
    public static Map<String,String> perfiles = new HashMap<>();

    public static void crearPerfiles(String name, String password){
        if(perfiles.containsKey(name)){
            perfiles.replace(name,password);
            System.out.println("Perfil " + name + " modificada contraseña");
        } else {
            perfiles.put(name,password);
            System.out.println("Perfil " + name + " introducido");
        }
    }

    public static void borrarPerfiles(String name){
        if(perfiles.containsKey(name)){
            perfiles.remove(name);
            System.out.println("Perfil " + name + " borrado");
        } else {
            System.out.println("No existe el perfil " + name + " para borrarlo");
        }
    }

     */
}
