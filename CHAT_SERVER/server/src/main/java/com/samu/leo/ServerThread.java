package com.samu.leo;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigestSpi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServerThread extends Thread{

    protected ServerSocket server;
    protected User client;
    protected BufferedReader inDalClient;
    protected DataOutputStream outVersoIlClient;
    protected boolean running;
    //instanziamo il mapper per la serializzazione/deserializzazione
    protected ObjectMapper json = new ObjectMapper();  


    //================ COSTRUTTORI ================//
    public ServerThread(User client){
        this.client = client;
        this.running = true;
    }

    public void run() {
        try {
            comunica();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }


    //================ METODI ================//

    public void comunica() throws IOException{
        
        //INSTANZIAMO GLI STREAM
        this.inDalClient = new BufferedReader(new InputStreamReader(this.client.socket.getInputStream()));
        this.outVersoIlClient = new DataOutputStream(this.client.socket.getOutputStream());

        while(running){

            //riceviamo la stringa serializzata
            String stringaRicevuta = inDalClient.readLine();

            //la deserializziamo in un oggetto "Messaggio"
            Messaggio messaggio = json.readValue(stringaRicevuta, Messaggio.class);

            //effettuiamo il controllo, così da attivare la funzionalità richiesta
            String tipo = messaggio.getHeader();

            switch (tipo) {

                case "#NICKNAME_REQUEST":

                    this.richiestanickName(messaggio);
                    break;

                case "#PRIVATE_TO":

                    this.messaggio_unicast(messaggio);
                    break;

                case "#PUBLIC_MESSAGE":

                    this.MessaggioATutti(messaggio);
                    break;

                case "#I_AM_DISCONNECTED":

                    try {
                        this.disconnect(client);
                    } catch (Exception e) {
                        System.out.println("Errore nella disconnessione");
                    }
                break;
            }
        }
        
    }

    //================ METODI DELLO SWITCH CASE ================//

    //DISCONNESSIONE
    public void disconnect(User user) throws IOException{  

        ServerMain.UtentiConnessi.remove(user);       
        AggoirnaLista();
        this.client.socket.close();
        System.out.println("User: " + this.client.nickname + " si e' disconnesso");
        inDalClient.close();
        outVersoIlClient.close();
        this.running = false;
        this.interrupt();

    }    


    //RICHIESTA NICKNAME
    public void richiestanickName(Messaggio m) throws JsonProcessingException, IOException{
        boolean giàPresente = false;
        for (User utente : ServerMain.UtentiConnessi) {                
            if (utente.nickname.equals(m.getCorpo())){
                giàPresente = true;
            }
        }
        Messaggio risposta;
        if (giàPresente){
            risposta = new Messaggio(4, "NICKNAME NON ACCETTATO", "", client.nickname);
            System.out.println(this.client.nickname + " e' già in uso prego cambiare nickname");
            
        } else{
            risposta = new Messaggio(5, "NICKNAME ACCETTATO", "", m.getCorpo());
            client.nickname = m.getCorpo();
            ServerMain.UtentiConnessi.get(client.position).nickname = m.getCorpo();
            AggoirnaLista();
            System.out.println("Client " + this.client.nickname + " si e' connesso");          
            
        }
        
        outVersoIlClient.writeBytes(json.writeValueAsString(risposta) + "\n");
    }


    //PRIVATE TO
    public void messaggio_unicast(Messaggio messago) throws JsonProcessingException, IOException{        
        Messaggio m = new Messaggio(6, messago.getCorpo(), messago.getMittente(), messago.getDestinatario());
        for (User utente : ServerMain.UtentiConnessi) { 
            if (utente.nickname.equals(messago.getDestinatario())){
                DataOutputStream outVersoIlClientTemporaneo = new DataOutputStream(utente.socket.getOutputStream());
                outVersoIlClientTemporaneo.writeBytes(json.writeValueAsString(m) + "\n");
                System.out.println(m.getMittente() + ": " + m.getCorpo());
            }
        }
    }


    //PUBLIC MESSAGE
    public void MessaggioATutti(Messaggio messaggio) throws JsonProcessingException, IOException{
        Messaggio aTutti = new Messaggio(7, messaggio.getCorpo(), messaggio.getMittente(), messaggio.getDestinatario());
        for (User utente : ServerMain.UtentiConnessi) {
            DataOutputStream outVersoIlClientTemporaneo = new DataOutputStream(utente.socket.getOutputStream());             
            outVersoIlClientTemporaneo.writeBytes(json.writeValueAsString(aTutti) + "\n");
            System.out.println(aTutti.getCorpo());
        }
    }


    //================ ALTRI METODI ================//

    public void AggoirnaLista() throws JsonProcessingException, IOException{
        String s = "";
        for (User utente : ServerMain.UtentiConnessi){
            s = s + "-!-" + utente.nickname;
        }
        for (User utente : ServerMain.UtentiConnessi){
            Messaggio aTutti = new Messaggio(8, s, "SERVER", "");
            DataOutputStream outVersoIlClientTemporaneo = new DataOutputStream(utente.socket.getOutputStream());             
            outVersoIlClientTemporaneo.writeBytes(json.writeValueAsString(aTutti) + "\n");
        }
    } 
    
}
