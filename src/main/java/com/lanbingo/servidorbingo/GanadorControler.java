package com.lanbingo.servidorbingo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class GanadorControler implements Initializable {
    @FXML
    protected Label laGanador;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        laGanador.setText(InfCompartido.RondaNombreWinner);
    }




}
