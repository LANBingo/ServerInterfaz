package com.lanbingo.servidorbingo;

import java.util.HashMap;
import java.util.Map;

public class Jugadores {
    public static Map<String,String> perfiles = new HashMap<>();

    public static void crearPerfiles(String name, String password){
        perfiles.put(name,password);
    }
}
