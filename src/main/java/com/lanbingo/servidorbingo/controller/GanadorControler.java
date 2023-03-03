package com.lanbingo.servidorbingo.controller;

import com.lanbingo.servidorbingo.VariablesCompartidas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GanadorControler implements Initializable {
    @FXML
    protected TextField tbGanador;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbGanador.setText(VariablesCompartidas.rondaNombreWinner);
    }
}
