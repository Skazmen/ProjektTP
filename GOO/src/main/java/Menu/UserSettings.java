package Menu;

import Server.Enums.Boards;
import Server.Enums.Players;

public class UserSettings {
    /*public static Players players;
    public static String color;
    public static Boards boards;*/

    private int size;
    private int players;
    private String nick;

    void setSize(int s){  this.size = s;  }
    void setPlayersCount(int p){  this.players = p;  }
    void setNick(String n){
        if(n.isEmpty()){
            this.nick = "Player" + ((int) Math.floor(1+Math.random()*999));
        } else {
            this.nick = n;
        }
    }

    public int getSize(){  return this.size;  }
    public int getPlayersCount(){  return this.players;  }
    public String getNick(){  return  this.nick;  }
}