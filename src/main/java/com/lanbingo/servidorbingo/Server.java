package com.lanbingo.servidorbingo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class Server extends Thread{



    public Server() throws IOException {
        InfCompartido.listener = new ServerSocket(5000);
    }

    //Se conectan los jugadores y se mandan al hilo para que tengan la comunicacion de la partida
    @Override
    public void run() {
        try {
            int cont = 0;
            while (true) {
                //Acepta jugadores hasta que el controlador del servidor decida o hasta que sean 6 jugadores
                if (cont==6 || InfCompartido.comienzaPartida == true){
                    System.out.println("Comeinza Partida");
                    break;
                }
                System.out.println("Puerto: " + InetAddress.getLocalHost());
                Socket socket = InfCompartido.listener.accept();
                Scanner sc = new Scanner(socket.getInputStream());
                System.out.println("Conectado: " + socket.getInetAddress());
                //ServerHilo serverHilo = new ServerHilo(socket,sc);
                //serverHilo.start();
                cont++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
class ServerHilo extends Thread {

    private Socket socket;
    private Scanner scan;
    private DataOutputStream out;

    public ServerHilo() {
    }

    public ServerHilo(Socket socket, Scanner scan, DataOutputStream out) {
        this.socket = socket;
        this.scan = scan;
        this.out = out;

    }

    @Override
    public void run() {
        try {
            //Falta arreglar
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            scan = new Scanner(socket.getInputStream());
            System.out.println("Conectado: " + socket.getInetAddress());
            while (socket.isConnected()) {
                while (scan.hasNextLine()) {
                    String msg = scan.nextLine();
                    if (Objects.equals(msg, escritor())) {
                        pw.println(true);
                        //pw.println("true")
                    } else {
                        pw.println(false);
                        //pw.println("false")
                    }
                    if(!msg.isEmpty()){
                        System.out.println(msg);
                        break;
                    }
                    if (msg.equals("Exit")){
                        scan.close();
                    }


                }
                Thread.sleep(3000);
                pw.println("Ronda Acabada");
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
    public List<Integer> transformador(String cadena){
        List<Integer> nueva_cadena = new ArrayList<>();
        List<String> list = Arrays.asList( "-1" , "2", "3", "4", "5" );

        List<Integer> newList = list.stream()
                .map(s -> Integer.parseInt(s))
                .collect(Collectors.toList());

        System.out.println(newList);
        return nueva_cadena;
    }
}