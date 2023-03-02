package com.lanbingo.servidorbingo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PartidaView extends Application {
    @Override//Visualizacion de la ventana partida
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(PartidaView.class.getResource("partida-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("LANBingo Partida");
        stage.getIcons().add(new Image(PartidaView.class.getResourceAsStream("/raw/logo.png")));
        stage.setScene(scene);
        stage.show();
    }
}
