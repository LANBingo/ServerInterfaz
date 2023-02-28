package com.lanbingo.servidorbingo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController{
    @FXML
    public TextField txNumPlayer;
     int cont = 0;
    @FXML
    protected void onHelloButtonClick() throws Exception {
        InfCompartido.comienzaPartida = true;
        PartidaView partidaView = new PartidaView();
        partidaView.start(new Stage());
    }

    protected void setContadorJugadores() throws IOException {
            this.txNumPlayer.setText(cont++ + "");
    }

}