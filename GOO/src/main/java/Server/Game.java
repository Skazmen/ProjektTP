package Server;

import java.awt.*;
import java.util.Arrays;

public class Game {

    private GridPosition[][] grid;
    private int[][] extracted;
    private int size;

    Game(int size){
        this.size = size;
        this.grid = new GridPosition[size][size];
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                grid[i][j] = new GridPosition();
            }
        }
    }

    ExtractedGrid extractGrid(){
        ExtractedGrid ex = new ExtractedGrid(size);
        for (int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                ex.setPosition(i,j, grid[i][j].getColor());
            }
        }
        return ex;
    }

    boolean checkMove(Player p, String encoded){
        //TODO logika do sprawdzenia czy da sie polozyc na tej pozycji
        String[] parts = encoded.split("/");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        if(grid[x][y].getPlayer() != null){
            return false;
        } else {
            updateBoard(p,x,y);
            return true;
        }
    }
    void updateBoard(Player p, int x, int y){
        //TODO sprawdzenie czy cos znika po polozeniu tego
        grid[x][y].setPlayer(p);
    }

    /*@Override
    public String toString() {
        return "Game{" +
                ", extracted=" + Arrays.toString(extracted) +
                '}';
    }*/
}
