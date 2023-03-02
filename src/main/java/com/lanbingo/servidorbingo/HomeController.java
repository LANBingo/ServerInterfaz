package com.lanbingo.servidorbingo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    public TextField txNumPlayer;
    @FXML
    private ComboBox<String> cbxMaxPoints;

    @FXML
    private Button btComenzar;
    @FXML
    private Label laIP;
    @FXML
    private Label laPuerto;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Metodo que inicializa el comboBox de puntos y el numero de jugadores --- Sin implementar funcional
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("3", "6", "9", "12", "15");
        cbxMaxPoints.setItems(items);
        cbxMaxPoints.getSelectionModel().selectFirst();//Selecciona el predeterminado de puntos
        txNumPlayer.setText("0");
        try {
            laIP.setText(InetAddress.getLocalHost().getHostAddress() +"");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        laPuerto.setText(VariablesCompartidas.PUERTO + "");
    }
    @FXML
    protected void btComenzarPartida_Click() throws Exception {
        //Controlador del boton-Cuando clickas el boton "Comenzar partida" lanza
        // la siguiente ventana que contiene la partida
        VariablesCompartidas.comienzaPartida = true;
        VariablesCompartidas.maxPoints=Integer.parseInt(cbxMaxPoints.getSelectionModel().getSelectedItem());
        PartidaView partidaView = new PartidaView();
        partidaView.start(new Stage());
    }
    @FXML
    protected void setContadorJugadores() {//Mostrar el numero de jugadores unidos -- Sin implementar - Fallo en Javafx
        //this.txNumPlayer.setText(VariablesCompartidas.countJuadores +"");
    }

    @FXML
    protected void setCbxMaxPoints(ActionEvent actionEvent) throws Exception {
        VariablesCompartidas.maxPoints = Integer.parseInt(cbxMaxPoints.getSelectionModel().getSelectedItem());
    }

}