package Server;

import java.awt.*;
import java.io.PrintWriter;
import java.util.Scanner;

public class Player {

    private Scanner player_in;
    private PrintWriter player_out;
    private String nick;
    private Color color;


    public Player(String n){
        this.nick = n;
    }

    void setInputStream(Scanner in){
        this.player_in = in;
    }
    void setOutputStream(PrintWriter out){
        this.player_out = out;
    }
    void setColor(Color c){  this.color = c;  }


    Scanner getInputStream(){
        return this.player_in;
    }
    PrintWriter getOutputStream(){
        return this.player_out;
    }
    String getNickname(){
        return this.nick;
    }
    Color getColor(){  return this.color;  }
}
