package com.samu.leo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain  {
    
    ServerSocket server = null;
    Socket client = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoCliente;
    public static ArrayList<User> UtentiConnessi = new ArrayList<>();

    public void avvia(){
        try{
            ServerSocket serversocket = new ServerSocket(7777);
            for(;;){
                Socket socket =  serversocket.accept();
                User utente = new User("VOID", socket);
                UtentiConnessi.add(utente);
                utente.setPosition(UtentiConnessi.size()-1);
                ServerThread serverThread = new ServerThread(utente);
                serverThread.start();
            } 
        }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Errore durante l'istanza del server");
                System.exit(1);
            }
        }


}
