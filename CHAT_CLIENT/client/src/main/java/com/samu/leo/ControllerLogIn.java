package com.samu.leo;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class ControllerLogIn {

    @FXML
    Button INVIO;


    @FXML
    public void initialize() {
        //cose che succedono all'accenzione di questa pagina
        this.INVIO.setOnAction(evento -> {                     //aggiungo l'evento di quando verrà pigiato il bottone
            try {
                App.setRoot("CHAT");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    
}
