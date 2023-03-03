package com.lanbingo.servidorbingo.controller;

import com.lanbingo.servidorbingo.VariablesCompartidas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class PartidaControler implements Initializable {

    @FXML
    private TextArea taNumbers;
    @FXML
    private TextField tfNum;

    public static boolean habilitar = false;

    @FXML
    private Button btGenera;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VariablesCompartidas.numBingo = new ArrayList<>();//Inicializadion de la lista de numeros de la partida
    }
    @FXML
    public void btHabilitar() {
        habilitar = !habilitar;
        if (habilitar) {
            GenerateNunmbers generateNunmbers = new GenerateNunmbers();
            generateNunmbers.start();
        }
    }
    //Boton generador de numeros de la partida del bingo
   public void actualiza(int i){

        StringBuilder stringBuilder = new StringBuilder();
        AtomicInteger saltolinea = new AtomicInteger();
        VariablesCompartidas.numBingo.forEach(num -> {
            stringBuilder.append(num);
            saltolinea.getAndIncrement();
            if (saltolinea.compareAndSet(10,0)){
                stringBuilder.append("\n");
            }
            stringBuilder.append(" ");
        });
        this.taNumbers.setText(stringBuilder.toString());
   }
    class GenerateNunmbers extends Thread{
        @Override
        public void run() {
            while (habilitar) {
                if (VariablesCompartidas.numBingo.size() >= 90) {
                    btGenera.disabledProperty();
                    break;
                } else {
                    int numGenerado = generaNumeroAleatorio();
                    tfNum.setText(numGenerado +"");
                    //Si el numero generado no esta introducido ya en la lista actual es introducido.
                    //Y si esta repetido busca otro numero hasta encontrar uno no introducido
                    if (!VariablesCompartidas.numBingo.contains(numGenerado)) {
                        VariablesCompartidas.numBingo.add(numGenerado);
                        //Si el tamaño de la lista el multiplo de 10 la lista salta
                        // de linea para no ocupar demasiado espacio lateral
                        //Introduce el numero
                        actualiza(numGenerado);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        public int generaNumeroAleatorio() {
            //Genera un numero aleatorio entre los valores indicados,
            // en este caso el 90 y el 1 para el Bingo
            int num = (int)Math.floor(Math.random() * (double)(90 - 1 + 1) + (double)1);
            return num;
        }
    }



}
