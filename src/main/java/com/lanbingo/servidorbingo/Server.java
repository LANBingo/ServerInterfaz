package com.lanbingo.servidorbingo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class Server extends Thread{

    private Socket jugador = null;
    private final List<Socket> LISTA_DE_ENVIO;
    class HacerJugadores extends Thread{
        @Override
        public void run() {
            try {
                jugador = InfCompartido.listener.accept();
                System.out.println("Jugador aceptado");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public Server() throws IOException {
        InfCompartido.listener = new ServerSocket(5000);
        this.LISTA_DE_ENVIO = new ArrayList<>();
    }

    //Se conectan los jugadores y se mandan al hilo para que tengan la comunicacion de la partida
    @Override
    public void run() {
        try {
            int cont = 0;
            while (true) {
                //Acepta jugadores hasta que el controlador del servidor decida o hasta que sean 6 jugadores

                System.out.println("Puerto: " + InetAddress.getLocalHost());
                //Socket socket = InfCompartido.listener.accept();
                HacerJugadores hacerJugadores = new HacerJugadores();
                hacerJugadores.start();
                while (jugador == null || InfCompartido.comienzaPartida){}
                if (jugador == null){
                    System.out.println("Comienza Partida");
                    comenzarBingo();
                    HacerJugadores.interrupted();
                    break;
                }
                while (jugador.isConnected()){
                    Scanner sc = new Scanner(jugador.getInputStream());
                    if (sc.hasNextLine()){
                        LISTA_DE_ENVIO.add(jugador);
                        System.out.println("Conectado: " + sc.nextLine());
                        ServerHilo serverHilo = new ServerHilo(jugador,sc);
                        serverHilo.start();
                        cont++;
                        break;
                    }
                }
                jugador = null;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void comenzarBingo(){
       LISTA_DE_ENVIO.stream().forEach(socket -> {
           PrintWriter printWriter = null;
           try {
               printWriter = new PrintWriter(socket.getOutputStream());
           } catch (IOException e) {
               e.printStackTrace();
               return;
           }
           printWriter.println(true);
       });
    }

}
class ServerHilo extends Thread {

    private Socket socket;
    private Scanner scan;
    private PrintWriter pw;


    public ServerHilo(Socket socket, Scanner scan) throws IOException {
        this.socket = socket;
        this.scan = scan;
        this.pw = new PrintWriter(socket.getOutputStream(),true);

    }

    @Override
    public void run() {
        try {
            boolean error = false;
            scan = new Scanner(socket.getInputStream());
            while (socket.isConnected()) {

                while (scan.hasNextLine()) {
                    String msg = scan.nextLine();
                    if(msg.isEmpty()){
                        System.out.println("Fallo en el envio de datos");
                        break;
                    }
                    System.out.println(msg);
                    msg = msg.substring(1, msg.length()-1);
                    String[] str = msg.split(", ");
                    for (String num: str) {
                        int numero = Integer.parseInt(num);
                        if (numero == 0){
                            continue;
                        }
                        if (!InfCompartido.numBingo.contains(numero)){
                            error = true;
                            break;
                        }
                    }
                    if (error) {
                        pw.println(false);
                        //pw.println("true")
                    } else {
                        pw.println(true);
                        //pw.println("false")
                    }
                    if (msg.equals("Exit")){
                        scan.close();
                    }
                }
                Thread.sleep(3000);
                pw.println("Close");
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String escritor() {
        String texto = "";
        for (int i = 0; i < InfCompartido.numBingo.size(); i++) {
            texto = texto + InfCompartido.numBingo.get(i) + " ";
        }
        return texto;
    }
    public boolean compador(int i) {
        if (i == 0){
            return false;
        }
        if (InfCompartido.numBingo.contains(i)){
            return true;
        }
        else
            return false;
    }
    public List<Integer> transformador(String cadeena){
        List<Integer> nueva_cadena = new ArrayList<>();
        List<String> list = Arrays.asList( "-1" , "2", "3", "4", "5" );
        // https://parzibyte.me/blog/2019/02/19/leer-datos-introducidos-por-usuario-teclado-java/
        String cadena = "Hola mundo, programando en Java desde parzibyte.me";
        // El contador de espacios
        int cantidadDeEspacios = 0;
        // Recorremos la cadena:
        for (int i = 0; i < cadena.length(); i++) {
            // Si el carácter en [i] es un espacio (' ') aumentamos el contador

        }
        // Finalmente lo imprimimos
        System.out.println("La cantidad de espacios es: " + cantidadDeEspacios);
        List<Integer> newList = list.stream()
                .map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());

        System.out.println(newList);
        return nueva_cadena;
    }
}