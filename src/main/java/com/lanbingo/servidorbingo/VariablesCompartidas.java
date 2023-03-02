package com.lanbingo.servidorbingo;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//Varibles compartidas para las distintas clases
public abstract class VariablesCompartidas {
    static ArrayList<Integer> numBingo = null; //Lista de los numeros del bingo que lleva la partida
    static int countJuadores = 0; // cuenta de los jugadores unidos a la partida
    static int maxPoints = 3; //puntos maximos para acabar la partida inicializado a 3
    static ServerSocket listener = null;//Servidor del socket creado en la clase compartida
    static Map<Socket,String> jugadores = new HashMap<>();//Map que contiene los jugadores unidos a la partida
    static Map<Socket,Integer> pointsJugadores = new HashMap<>();
    static boolean comienzaPartida = false;//booleno que inicia la partida
    static final int PUERTO = 5000;
    static List<Socket> LISTA_DE_ENVIO;
    static String RondaNombreWinner = "Nadie";
}