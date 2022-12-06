package com.samu.leo;

import java.net.Socket;

public class User {
    
    String nickname;
    Socket socket;
    int position;
    
    public User(String nickname, Socket socket) {
        this.nickname = nickname;
        this.socket = socket;
    }

    public void setPosition(int position) {  this.position = position;  }

}
