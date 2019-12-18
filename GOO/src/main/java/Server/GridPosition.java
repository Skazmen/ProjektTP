package Server;

import rules.Chain;

import java.awt.*;

public class GridPosition {

    public Chain chain;
    private Player player;
    private int liberties;
    public int x;
    public int y;

    public GridPosition() {
    }

    public GridPosition(int x, int y) {
        chain = null;
        liberties = 4;
        this.x = x;
        this.y = y;
    }

    void setPlayer(Player p) {
        this.player = p;
    }

    Color getColor() {
        if (player != null) return player.getColor();
        else return null;
    }

    Player getPlayer() {
        return this.player;
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
}

/*public class Stone {

    public Chain chain;
    public Colours colour;
    private int liberties;
    public int row;
    public int col;

    public Stone(int row, int col, Colours colour) {
        chain = null;
        this.colour = colour;
        liberties = 4;
        this.row = row;
        this.col = col;
    }

}*/