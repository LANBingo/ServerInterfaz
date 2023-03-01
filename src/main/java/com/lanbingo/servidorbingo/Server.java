package com.lanbingo.servidorbingo;

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
                jugador = InfCompartido.listener.accept();
                System.out.println("Jugador aceptado");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public Server() throws IOException {
        //Inicializacion del servidor en el puerto designado en este caso
        // y de la lista que guardará los sockets de los jugadores
        InfCompartido.listener = new ServerSocket(InfCompartido.PUERTO);
        InfCompartido.LISTA_DE_ENVIO = new ArrayList<>();
    }

    @Override //Metodo principal que acepta y se mandan al hilo para que tengan la comunicacion
    // de la partida cuando la partida comienza deja de aceptar jugadores
    public void run() {
        try {
            //IP y puerto que deben ingresar los jugadores en la app movil
            System.out.println("IP conexion: " + InetAddress.getLocalHost());
            System.out.println("Puerto conexion: " + InfCompartido.PUERTO);
            while (true) {
                //Socket socket = InfCompartido.listener.accept(); Linea de prueba
                HacerJugadores hacerJugadores = new HacerJugadores();//Inicio del hilo para comenzar a aceptar jugadores
                hacerJugadores.start();
                while (jugador == null && !InfCompartido.comienzaPartida){} //Para el hilo hasta que se une un jugador
                // o la partida arranca
                if (jugador == null){
                    System.out.println("Comienza Partida");
                    envioGlobal(true);
                    HacerJugadores.interrupted();//Se debe actualizar
                    break;
                }
                while (jugador.isConnected()){//Cuando se conecta el jugador en la la entrada de datos
                    Scanner sc = new Scanner(jugador.getInputStream());
                    if (sc.hasNextLine()){
                        InfCompartido.LISTA_DE_ENVIO.add(jugador);//El socket del jugador se añade a la lista
                        String nombre = sc.nextLine();
                        InfCompartido.jugadores.put(jugador,nombre);
                        System.out.println("Conectado: " + nombre); //Nombre del jugador conectado
                        ServerHilo serverHilo = new ServerHilo(jugador,sc);//Pasa el jugador y su socket a
                        // la conexion de la partida
                        serverHilo.start();
                        InfCompartido.countJuadores++;//La cuenta de jugadores aumenta en 1
                        break;
                    }
                }
                jugador = null;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void envioGlobal(Boolean envio){//Metodo que comienza el bingo mandando a todos
        // los sockets el indicador de comienzo de la partida (true)
       InfCompartido.LISTA_DE_ENVIO.stream().forEach(socket -> {
           PrintWriter printWriter = null;
           try {
               printWriter = new PrintWriter(socket.getOutputStream(),true);
           } catch (IOException e) {
               e.printStackTrace();
               return;
           }
           printWriter.println(envio.booleanValue());
       });
    }

}
class ServerHilo extends Thread {

    private Socket socket;
    private Scanner scan;
    private PrintWriter pw;


    public ServerHilo(Socket socket, Scanner scan) throws IOException {//Recibe el socket y el scaner del
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
                    Server.envioGlobal(true);
                    String msg = scan.nextLine();
                    if(msg.isEmpty()){//No se ha ne viado nada
                        System.out.println("Fallo en el envio de datos");
                        break;
                    }
                    System.out.println(msg);//Escribe la cadena enviada por el usuario en el run
                    msg = msg.substring(1, msg.length()-1);// Saca las llaves de la cadena recibida
                    String[] str = msg.split(", ");// crea un array de strings separado por las ", "
                    for (String num: str) {
                        int numero = Integer.parseInt(num);//Transforma cada string del array en
                        // interger para compararlo con la lista de los numeros sacados por el bingo uno a uno
                        //Si algun numero no esta en la lista devolverá un false al jugador
                        if (numero == 0){
                            continue;
                        }
                        if (!InfCompartido.numBingo.contains(numero)){
                            error = true;
                            break;
                        }
                    }
                    if (error) {
                        Server.envioGlobal(false);
                        System.out.println(false);
                    } else {
                        Server.envioGlobal(true);
                        System.out.println(true);
                        InfCompartido.RondaNombreWinner=InfCompartido.jugadores.get(socket);
                        System.out.println(InfCompartido.RondaNombreWinner); //Para probar si puedo imprimir el
                        // nombre del ganador, nom ge acuerdo de si era justo asi como sacaba el value de una variable
                        //Ahora lo cambiaré por una ventana nueve emergente
                        //pw.println("false")
                    }//Salida de la partida
                    if (msg.equals("Exit")){
                        scan.close();
                    }

                }
                Thread.sleep(3000);
                pw.println("Close");//Cierre del jugador y servidor
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}