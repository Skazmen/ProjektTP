package Players;

import java.awt.*;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Scanner;

public class HumanPlayer implements Player {

    private Scanner player_in;
    private PrintWriter player_out;
    private String nick;
    private Color color;

    public HumanPlayer(String n){
        this.nick = n;
    }


    @Override
    public void setInputOutputStream(ServerSocket listener, Scanner sc, PrintWriter pw) {
        this.player_in = sc;
        this.player_out = pw;
    }
    @Override
    public void setColor(Color c){
        this.color = c;
    }

    @Override
    public Scanner getInputStream(){
        return this.player_in;
    }
    @Override
    public PrintWriter getOutputStream(){
        return this.player_out;
    }
    @Override
    public String getNickname(){
        return this.nick;
    }
    @Override
    public Color getColor(){
        return this.color;
    }

}
