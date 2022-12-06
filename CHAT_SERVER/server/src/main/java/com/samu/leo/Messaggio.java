package com.samu.leo;

import com.samu.leo.utils.Constants;

public class Messaggio{ //POJO per tutti i messaggi inviati
    private String header;
    private String corpo;
    private String mittente;
    private String destinatario;

    //===CONSTRUCTORS====
    public Messaggio() {   } //void constructor    
    public Messaggio(int typeOfMessage, String corpo, String mittente, String destinatario) {
        this.header = Constants.Headers[typeOfMessage];
        this.corpo          = corpo;
        this.destinatario   = destinatario;
        this.mittente       = mittente;

    }

    //===GETTERS===
    public String       getHeader()         {   return header;          } 
    public String       getCorpo()          {   return corpo;           }
    public String       getMittente()       {   return mittente;        }
    public String       getDestinatario()   {   return destinatario;    }
    
    //===SETTERS===    
    public void         setHeader(String header)                {   this.header = header;               }
    public void         setCorpo(String corpo)                  {   this.corpo = corpo;                 }
    public void         setMittente(String mittente)            {   this.mittente = mittente;           }
    public void         setDestinatario(String destinatario)    {   this.destinatario = destinatario;   }  
    
}