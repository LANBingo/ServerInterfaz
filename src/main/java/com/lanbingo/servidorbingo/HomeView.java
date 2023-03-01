package com.lanbingo.servidorbingo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeView extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        /*
        Lanzamiento del programa asignandole el fmlx correspondiente y eligiendo el
        titulo y el icono de la pestaña
         */
        FXMLLoader fxmlLoader = new FXMLLoader(HomeView.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("LANBingo Server");
        stage.getIcons().add(new Image(HomeView.class.getResourceAsStream("/raw/logo.png")));
        stage.setScene(scene);
        stage.show();
    }

    //Cerrar servidor cuando se cierre la pestaña - Sin implementar
    @Override
    public void stop() throws Exception {

    }

    public static void main(String[] args) throws IOException {
        /*
        Lanzamiento/Inializacion del servidor o backEnd y la pesataña home
         */
        Server s = new Server();
        s.start();
        launch();
    }
}