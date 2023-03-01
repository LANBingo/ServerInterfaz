package com.lanbingo.servidorbingo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    public TextField txNumPlayer;
    @FXML
    private ComboBox<String> cbxMaxPoints;


    @FXML
    protected void btComenzarPartida_Click() throws Exception {
        //Controlador del boton-Cuando clickas el boton "Comenzar partida" lanza
        // la siguiente ventana que contiene la partida
        InfCompartido.comienzaPartida = true;
        PartidaView partidaView = new PartidaView();
        partidaView.start(new Stage());
    }

    //Aumentar el numero de jugadores unidos -- Sin implementar
    @FXML
    protected void setContadorJugadores() throws IOException {
            this.txNumPlayer.setText(InfCompartido.countJuadores + "");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Metodo que inicializa el comboBox de puntos y el numero de jugadores --- Sin implementar funcional
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("3", "6", "9", "12", "15");
        cbxMaxPoints.setItems(items);
        txNumPlayer.setText("0");
    }
    @FXML
    protected void setCbxMaxPoints(ActionEvent actionEvent) throws Exception {
        InfCompartido.maxPoints = Integer.parseInt(cbxMaxPoints.getSelectionModel().getSelectedItem());
    }
}