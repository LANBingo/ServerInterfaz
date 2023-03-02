package com.lanbingo.servidorbingo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PartidaControler implements Initializable {

    @FXML
    private TextArea taNumbers;
    @FXML
    private Label laNum;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VariablesCompartidas.numBingo = new ArrayList<>();//Inicializadion de la lista de numeros de la partida
    }
    @FXML//Boton generador de numeros de la partida del bingo
    protected void  btGenerationClick(){
        while (true){
            String texto;
            texto = this.taNumbers.getText();
            int numGenerado = generaNumeroAleatorio();
            //Si el numero generado no esta introducido ya en la lista actual es introducido.
            //Y si esta repetido busca otro numero hasta encontrar uno no introducido
            if (!VariablesCompartidas.numBingo.contains(numGenerado)){
                VariablesCompartidas.numBingo.add(numGenerado);
                //Si el tamaño de la lista el multiplo de 10 la lista salta
                // de linea para no ocupar demasiado espacio lateral
                if (VariablesCompartidas.numBingo.size() ==10 || VariablesCompartidas.numBingo.size() ==20 ||
                        VariablesCompartidas.numBingo.size() ==30 || VariablesCompartidas.numBingo.size() ==40 ||
                        VariablesCompartidas.numBingo.size() ==50 || VariablesCompartidas.numBingo.size() ==60 ||
                        VariablesCompartidas.numBingo.size() ==70 || VariablesCompartidas.numBingo.size() ==80 ||
                        VariablesCompartidas.numBingo.size() ==90){
                    texto = texto + "\n";
                }
                //Introduce el numero
                laNum.setText(numGenerado+ "");
                texto =texto + VariablesCompartidas.numBingo.get(VariablesCompartidas.numBingo.size()-1).toString() + " ";
                this.taNumbers.setText(texto);
                break;
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
