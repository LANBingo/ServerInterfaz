package com.lanbingo.servidorbingo;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class InfCompartido {
    static ArrayList<Integer> numBingo = null;

    static int countJuadores;

    static ServerSocket listener = null;
    static Map<String,String> jugadores = new HashMap<>();

    static boolean comienzaPartida = false;

}
