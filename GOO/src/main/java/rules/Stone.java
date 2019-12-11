package rules;

import Server.Enums.Colours;

public class Stone {

    public Chain chain;
    public Colours colour;
    public int liberties;//swobody
    public int row;
    public int col;

    public Stone(int row, int col, Colours colour) {
        chain = null;
        this.colour = colour;
        liberties = 4;
        this.row = row;
        this.col = col;
    }
}