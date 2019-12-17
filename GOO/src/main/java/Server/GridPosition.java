package Server;

import java.awt.*;

public class GridPosition {

    private Player player;
    private int libertirs;

    void setPlayer(Player p){
        this.player = p;
    }

    Color getColor(){
        if(player != null) return player.getColor();
        else return null;
    }
    Player getPlayer(){
        return this.player;
    }
}
