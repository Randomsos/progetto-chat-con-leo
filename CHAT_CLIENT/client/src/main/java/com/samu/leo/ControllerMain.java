package com.samu.leo;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class ControllerMain {
    @FXML
    private Pane APPLICAZIONE;              //tutta la finestra
    @FXML
    private Pane PARTE_SINISTRA;            //pane con la parte sinistra del'applicazione
    @FXML 
    private ListView TUTTI_GLI_UTENTI;      //Lista di tutti gli utenti online, compresa la chat pubblica
    @FXML
    private Label NOME_DELLA_CHAT;          //Nome della nostra applicazione ("WIZZU")
    @FXML
    private ImageView LOGO;                 //Logo della nostra applicazione
    @FXML   
    private Label NICKNAME;                 //nickname dell'utilizzatore
    @FXML
    private Pane CHAT;                      //pane con la parte destra della chat
    @FXML
    private ListView CHAT_MESSAGGI;         //messaggi della chat selezionata
    @FXML 
    private TextField TEXT_IN;              //input del testo
    @FXML
    private Button BOTTONE_INVIO;           //bottonbe di invio
    @FXML
    private Label NOME_CHAT;                //nickname dell'utente al quale stiamo scrivendo

    @FXML
    public void initialize() {
        //cose che succedono all'accenzione di questa pagina
        this.BOTTONE_INVIO.setOnAction(evento -> {                     //aggiungo l'evento di quando verrà pigiato il bottone
            Invia();
        });
        
        this.NOME_DELLA_CHAT.setText("Wizzu");                   //Setto Il nome della CHAT
        this.NOME_DELLA_CHAT.setFont(new Font("Helvetica", 35)); // gli metto il font e la dimensione
        this.NOME_DELLA_CHAT.setAlignment(Pos.TOP_RIGHT);        // lo alineo a destra
        this.NOME_DELLA_CHAT.setPadding(new Insets(0, 15, 0, 0));// setto il Padding
    }

    public void Invia() {
        System.out.println("s");
    }

    /*
    @FXML
    public void closeButtonAction() { metodo che succede alla chiusura della pagina
        //cose
    }
    */


}
