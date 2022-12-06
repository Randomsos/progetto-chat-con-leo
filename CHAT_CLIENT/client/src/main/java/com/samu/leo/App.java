package com.samu.leo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    //SETUP della finestra
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("LOGIN"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    //fine SETUP della finestra

    public static void main(String[] args) {
        //Client_out client = new Client_out();   //creazione del client
        //client.connetti();                      //si avvia il client
        Application.launch(); //la schermata viene lanciata
    }
}