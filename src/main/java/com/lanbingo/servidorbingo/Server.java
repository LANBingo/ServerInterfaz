package com.lanbingo.servidorbingo;


import com.lanbingo.servidorbingo.controller.PartidaControler;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server extends Thread{
    private Socket jugador = null; // Socket del jugador que llama al servidor
     // Lista de socket que usaremos mas tarde
    class HacerJugadores extends Thread{ //Clase para la Generacion de sockets de los jugadores
        @Override
        public void run() {
            try {
                //El socket es aceptado por el servidor y informa de la conexion con un jugador
                jugador = VariablesCompartidas.listener.accept();
                System.out.println("Jugador aceptado");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Server() throws IOException {
        //Inicializacion del servidor en el puerto designado en este caso
        // y de la lista que guardará los sockets de los jugadores
        VariablesCompartidas.listener = new ServerSocket(VariablesCompartidas.PUERTO);
        VariablesCompartidas.LISTA_DE_ENVIO = new ArrayList<>();

        Claves.nuevaClave(VariablesCompartidas.claveDeAcesso);
        /*
        Claves.crearPerfiles("Pedro","Ped2511");
        Claves.crearPerfiles("Juan","Ja1901");
        Claves.crearPerfiles("Paco","Pc0610");
        Claves.crearPerfiles("Jaime","Aprovad0s");
        Claves.crearPerfiles("Andres","Presidente2k");
        Claves.crearPerfiles("Patri","2kTuto");

         */
    }

    @Override //Metodo principal que acepta y se mandan al hilo para que tengan la comunicacion
    // de la partida cuando la partida comienza deja de aceptar jugadores
    public void run() {
        try {

            System.out.println("\n-----SERVER LANBINGO----- \n");
            //IP y puerto que deben ingresar los jugadores en la app movil
            System.out.println("IP conexion: " + InetAddress.getLocalHost().getHostAddress());
            System.out.println("Puerto conexion: " + VariablesCompartidas.PUERTO);
            while (true) {
                //Socket socket = VariablesCompartidas.listener.accept(); Linea de prueba
                HacerJugadores hacerJugadores = new HacerJugadores();//Inicio del hilo para comenzar a aceptar jugadores
                hacerJugadores.start();
                while (jugador == null || !VariablesCompartidas.comienzaPartida) {
                } //Para el hilo hasta que se une un jugador
                // o la partida arranca
                if (jugador == null) {
                    System.out.println("Comienza Partida");
                    envioGlobal(true);
                    hacerJugadores.interrupt();//Corta el hilo de aceptar jugadores
                    System.out.println("Ya no se aceptan mas jugadores");
                    break;
                }
                while (jugador.isConnected()) {//Cuando se conecta el jugador en la entrada de datos
                    Scanner sc = new Scanner(jugador.getInputStream());
                    PrintWriter pw = new PrintWriter(jugador.getOutputStream(), true);
                    if (sc.hasNextLine()) {
                        String pasword = Cifrado.descifrador(sc.nextLine());
                        if (Claves.claves.contains(pasword)){
                            pw.println(true);
                            String nombre = sc.nextLine();
                            VariablesCompartidas.LISTA_DE_ENVIO.add(jugador);//El socket del jugador se añade a la lista
                            VariablesCompartidas.jugadoresEnPartida.put(jugador, nombre);//Añade el jugador con su nombre a la lista y a la lista de puntos
                            VariablesCompartidas.pointsJugadores.put(jugador, 0);
                            System.out.println("Conectado: " + nombre); //Nombre del jugador conectado
                            PartidaHilo partidaHilo = new PartidaHilo(jugador, sc);//Pasa el jugador y su socket a
                            partidaHilo.start();// Comienza la conexion de la partida
                            //new HomeController().setContadorJugadores();Sin implementar
                            VariablesCompartidas.countJuadores++;//La cuenta de jugadores aumenta en 1
                            break;
                        } else {
                                pw.println(false);
                                break;
                        }
                    }else {
                        System.out.println("No recibe nada");
                    }
                }
            }
            jugador = null;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
    public static void envioGlobal(Boolean envio){//Metodo que comienza el bingo mandando a todos
        // los sockets el indicador de comienzo de la partida (true)
       VariablesCompartidas.LISTA_DE_ENVIO.stream().forEach(socket -> {
           PrintWriter printWriter = null;
           try {
               printWriter = new PrintWriter(socket.getOutputStream(),true);
           } catch (IOException e) {
               e.printStackTrace();
               return;
           }
           printWriter.println((envio.booleanValue()));
       });
    }

}
class PartidaHilo extends Thread {

    private final Socket socket;
    private Scanner scan;
    private PrintWriter pw;


    public PartidaHilo(Socket socket, Scanner scan) throws IOException {//Recibe el socket y el scaner del
        // servidor principal en el constructor
        this.socket = socket;
        this.scan = scan;
        this.pw = new PrintWriter(socket.getOutputStream(),true);//Inicializa el printer para el
        // envio de datos al jugador

    }

    @Override
    public void run() {
        try {
            boolean error = false;//Boolenao que devuelve el resultado de la comparacion de las lineas
            scan = new Scanner(socket.getInputStream());
            while (socket.isConnected()) {
                while (scan.hasNextLine()) {
                    PartidaControler.habilitar = false;
                    Server.envioGlobal(true);
                    String msg = Cifrado.descifrador(scan.nextLine());
                    if(msg.isEmpty() || msg == "error"){//No se ha ne viado nada
                        System.out.println("Fallo en el envio de datos");
                        break;
                    }else {
                        System.out.println(msg);//Escribe la cadena enviada por el usuario en el run
                        msg = msg.substring(1, msg.length() - 1);// Saca las llaves de la cadena recibida
                        String[] str = msg.split(", ");// crea un array de strings separado por las ", "
                        for (String num : str) {
                            int numero = Integer.parseInt(num);//Transforma cada string del array en
                            // interger para compararlo con la lista de los numeros sacados por el bingo uno a uno
                            //Si algun numero no esta en la lista devolverá un false al jugador
                            if (!VariablesCompartidas.numBingo.contains(numero) || numero != 0) {
                                error = false;
                                break;
                            }
                        }
                        if (error) {
                            Server.envioGlobal(false);
                            System.out.println("Linea fallada" + false);
                        } else {
                            //Suma los puntos adquiridos. Si es el maximo acaba la partida
                            VariablesCompartidas.pointsJugadores.replace(socket, VariablesCompartidas.pointsJugadores.get(socket) + 1);
                            if (VariablesCompartidas.pointsJugadores.get(socket) >= VariablesCompartidas.maxPoints) {
                                VariablesCompartidas.rondaNombreWinner = VariablesCompartidas.jugadoresEnPartida.get(socket);
                                //singWinner();//Lanza ventana ganador - Colapsa hilo, javafx no lo permite
                                System.out.println(VariablesCompartidas.rondaNombreWinner);
                                Server.envioGlobal(true);
                                System.out.println(true);
                                VariablesCompartidas.numBingo.clear();

                                break;
                            } else {
                                VariablesCompartidas.rondaNombreWinner = VariablesCompartidas.jugadoresEnPartida.get(socket);
                                //singWinner();
                                Server.envioGlobal(false);
                                System.out.println(true);
                                System.out.println(VariablesCompartidas.rondaNombreWinner);
                            }
                        }//Salida de la partida
                        if (msg.equals("Exit")) {
                            scan.close();
                            break;
                        }
                    }
                }
                Thread.sleep(3000);
                pw.println(Cifrado.cifrador("Close"));//Cierre del jugador y servidor
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void singWinner() throws IOException {
        GanadorView ganadorView = new GanadorView();
        ganadorView.start(new Stage());
    }
}