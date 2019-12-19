package Rules;

import Players.Player;

import java.awt.*;

public class Stone {

    public Chain chain;
    private Player player;
    private int liberties;
    public int x;
    public int y;

    public Stone(int x, int y, int size, Player p) {
        this.chain = null;
        this.x = x;
        this.y = y;
        this.player = p;

        //calculate liberties
        this.liberties = 4;
        if( x == 0 || x == size-1 ) decreaseLiberties();
        if( y == 0 || y == size-1 ) decreaseLiberties();
    }

    Chain getChain(){
        return this.chain;
    }

    Color getColor() {
        if (player != null) return player.getColor();
        else return null;
    }

    int getX() {
        return this.x;
    }

    int getY() {
        return this.y;
    }

    public int getLiberties() {
        return this.liberties;
    }

    public void decreaseLiberties() {
        this.liberties--;
    }
    public void increaseLiberties() {
        this.liberties++;
    }


}