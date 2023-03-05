package com.lanbingo.servidorbingo;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//Varibles compartidas para las distintas clases
public abstract class VariablesCompartidas {
    public static ArrayList<Integer> numBingo = null; //Lista de los numeros del bingo que lleva la partida
    public static int countJuadores = 0; // cuenta de los jugadores unidos a la partida
    public static int maxPoints = 3; //puntos maximos para acabar la partida inicializado a 3
    public static ServerSocket listener = null;//Servidor del socket creado en la clase compartida

    public static Map<Socket,String> jugadoresEnPartida = new HashMap<>();//Map que contiene los jugadores unidos a la partida
   public static Map<Socket,Integer> pointsJugadores = new HashMap<>();
   public static boolean comienzaPartida = false;//booleno que inicia la partida
   public static final int PUERTO = 5000;//Puerto de entrada y salida
    public static List<Socket> LISTA_DE_ENVIO;//Lista con socket clientes
     public static String rondaNombreWinner = "Campeon";//Nombre que aparece en la ventana del ganador
}
