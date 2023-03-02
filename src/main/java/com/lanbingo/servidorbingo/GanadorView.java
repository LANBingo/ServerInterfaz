package com.lanbingo.servidorbingo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GanadorView extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GanadorView.class.getResource("ganador-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("LANBingo Ganador");
        primaryStage.getIcons().add(new Image(GanadorView.class.getResourceAsStream("/raw/logo.png")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
