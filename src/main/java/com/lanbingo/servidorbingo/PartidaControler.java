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
        InfCompartido.numBingo = new ArrayList<>();
    }
    @FXML
    protected void  btGenerationClick(){
        while (true){
            String texto;
            texto = this.taNumbers.getText();
            int numGenerado = generaNumeroAleatorio();
            if (!InfCompartido.numBingo.contains(numGenerado)){
                InfCompartido.numBingo.add(numGenerado);
                if (InfCompartido.numBingo.size() ==10 || InfCompartido.numBingo.size() ==20 ||
                        InfCompartido.numBingo.size() ==30 || InfCompartido.numBingo.size() ==40 ||
                        InfCompartido.numBingo.size() ==50 || InfCompartido.numBingo.size() ==60 ||
                        InfCompartido.numBingo.size() ==70 || InfCompartido.numBingo.size() ==80 ||
                        InfCompartido.numBingo.size() ==90){
                    texto = texto + "\n";
                }
                laNum.setText(numGenerado+ "");
                texto =texto + InfCompartido.numBingo.get(InfCompartido.numBingo.size()-1).toString() + " ";
                this.taNumbers.setText(texto);
                break;
            }
        }
    }
    public int generaNumeroAleatorio() {
        int num = (int)Math.floor(Math.random() * (double)(90 - 1 + 1) + (double)1);
        return num;
    }
}
