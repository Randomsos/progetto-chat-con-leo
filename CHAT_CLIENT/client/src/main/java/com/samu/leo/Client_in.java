package com.samu.leo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Client_in extends Thread{
    private BufferedReader inDalServer;
    private User me;
    private ObjectMapper json = new ObjectMapper();
    public boolean accettato; 

    //* inizializzazione della cronologia della chat
    public ArrayList<Messaggio> messaggi  =  new ArrayList<>(); 
    public ArrayList<Messaggio> multicast =  new ArrayList<>(); //* non è un vero multicast, adoperimo il protocollo TCP 
    public ArrayList<String> utenti = new ArrayList<>();

    public Client_in(Client_out scrittore, Socket socket){
        this.me.socket = socket;
        
        try {
            this.inDalServer = new BufferedReader(new InputStreamReader(this.me.socket.getInputStream()));//Instanzio lo stream di lettura
        } catch (IOException e) {
            System.out.println("errore nella creazione dello stream di input");
        }  
    }

    public void run() {

        try {
            this.inDalServer = new BufferedReader(new InputStreamReader(this.me.socket.getInputStream()));
        } catch (Exception e) {   System.out.println("errore nella creazione dello stream");    }
        
        
        while(true){

            //riceviamo la stringa serializzata
            String stringaRicevuta = "";
            try {
                stringaRicevuta = inDalServer.readLine();
            } catch (Exception e) {   System.out.println("errore nella ricezione di un messaggio");    }

            //la deserializziamo in un oggetto "Messaggio"
            Messaggio messaggio = new Messaggio();
            try {
                messaggio = json.readValue(stringaRicevuta, Messaggio.class);
            } catch (Exception e) {   System.out.println("errore nella deserializzazione di un messaggio");    }

            //effettuiamo il controllo, così da attivare la funzionalità richiesta
            String tipo = messaggio.getHeader();

            switch (tipo) {

                case "@NICKNAME_NOT_ACCEPTED":

                    this.nickName_non_accettato();
                    break;

                case "@NICKNAME_ACCEPTED":

                    this. nickName_accettato(messaggio);
                    break;

                case "@PRIVATE_MESSAGE":

                    this.messaggioUnicast(messaggio);
                    break;

                case "@PUBLIC_MESSAGE":

                    this.messaggioMulticast(messaggio);

                    break;

                case "@CLIENT_LIST":

                    this.clientList(messaggio);

                break;

                case "@!SERVER_CLOSED":

                this.chiusoIlServer();

                    break;
            }
        }
    }

    //================ METODI DELLO SWITCH CASE ================//

    //* metodo per il rifiuto del nickname 
    public void nickName_non_accettato(){
        this.accettato = false;
    }

    //* metodo per l'accettazione del nickname 
    public void nickName_accettato(Messaggio messaggio){
        me.nickname = messaggio.getMittente();
        this.accettato = true;
    }

    
    //* metodo che gestisce l'arrivo di un messaggio privato
    public void messaggioUnicast(Messaggio messaggio) {
        messaggi.add(messaggio);
    }

    //* metodo che gestisce l'arrivo di un messaggio pubblico
    public void messaggioMulticast(Messaggio messaggio) {
        multicast.add(messaggio);
    }

    //* metodo che aggiorna la lista dei client connessi
    public void clientList(Messaggio messaggio) {

        String[] s = messaggio.getCorpo().split("-!-");
        if(s.length > utenti.size()){

            utenti.add(s[s.length - 1]);
            //*metodo che fa partire l'allert (GLI PASSIAMO s[s.length - 1])  

        } else {
            for (int i = 0; i < s.length; i++) {
                if ( ! s[i].equals(utenti.get(i))) {
                    //*metodo che fa partire l'allert (GLI PASSIAMO utenti.get(i))
                    utenti.remove(i);
                    break;
                }
            }
        }
    }
    
    // * metodo che gestisce la situazione in caso di chiusura del server
    public void chiusoIlServer() {
        try {
            me.socket.close();
        } catch (IOException e) {
            System.out.println("Problema con la chiusura del socket");
        }
        // cambiamo la fiestra ( app.setRoot() ) e facciamo comparire quella di disconnessione
    }
   
}
