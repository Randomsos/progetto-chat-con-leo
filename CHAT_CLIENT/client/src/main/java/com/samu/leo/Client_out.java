package com.samu.leo;

import java.io.*;
import java.net.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Client_out {
    private Socket socket;
    private DataOutputStream outVersoIlServer;   

    private Client_in ascoltatore;
    private User meMedesimo;

    private ObjectMapper json = new ObjectMapper();
    

    public Socket connetti(){       

        try {
            this.socket = new Socket("localhost", 7777);    //inizializzo la connessione con il server creando il socket
            meMedesimo.socket = socket;
            this.outVersoIlServer   = new DataOutputStream(socket.getOutputStream());   //Instanzio lo stream di scrittura
            
            this.ascoltatore = new Client_in(this, this.socket);
            this.ascoltatore.start();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("===Disconnesso dal Server===");
        }
        
        return socket;
    }

    /*INVIO di un messaggio privato */
    public void messaggioSingolo(String corpo, String desitnatario){
        Messaggio messaggio = new Messaggio(1, corpo, this.meMedesimo.nickname, desitnatario);
        try {
            outVersoIlServer.writeBytes(json.writeValueAsString(messaggio) + "\n");
        } catch (IOException e) {
            System.out.println("errore nell'invio del messaggio");
        }
        this.ascoltatore.messaggi.add(messaggio);
    }

    /*INVIO di un messaggio pubblico */
    public void messaggioATutti(String corpo) {
        Messaggio messaggio = new Messaggio(2, corpo, this.meMedesimo.nickname, "NULL");
        try {
            outVersoIlServer.writeBytes(json.writeValueAsString(messaggio) + "\n");
        } catch (IOException e) {
            System.out.println("errore nell'invio del messaggio");
        }
        this.ascoltatore.multicast.add(messaggio);
    }

    /*INVIO della richiaste del nickname (la risposta verrà gestita in "Client_in") */
    public void richiestaNickname(String nickName) {
        Messaggio messaggio = new Messaggio(0, nickName, "", "SERVER");
        try {
            outVersoIlServer.writeBytes(json.writeValueAsString(messaggio) + "\n");
        } catch (IOException e) {
            System.out.println("errore nell'invio del messaggio");
        }
    }

    /*Avviso il server della disconnessione */
    public void miStoDisconnettendo() {
        Messaggio messaggio = new Messaggio(3, "", "", "SERVER");
        try {
            outVersoIlServer.writeBytes(json.writeValueAsString(messaggio) + "\n");
        } catch (IOException e) {
            System.out.println("errore nell'invio del messaggio");
        }
    }

}
