package Server;

import java.awt.*;

public class Game {

    private GridPosition[][] grid;
    private int size;

    Game(int size){
        this.size = size;
        this.grid = new GridPosition[size][size];
    }

    int[][] extractGrid(){
        int[][] temp = new int[size][size];
        for (int i=0; i<size; i++){
            for(int j=0; j<size; j++){

                if(grid[i][j].getColor() == Color.BLACK){
                    temp[i][j] = 1;
                }
                else if(grid[i][j].getColor() == Color.WHITE){
                    temp[i][j] = -1;
                }
                else {
                    temp[i][j] = 0;
                }

            }
        }
        return temp;
    }

    boolean checkMove(Player p, int x, int y){
        //TODO logika do sprawdzenia czy da sie polozyc na tej pozycji
        return true;
    }
    void updateBoard(Player p, int x, int y){
        //TODO sprawdzenie czy cos znika po polozeniu tego
        grid[x][y].setPlayer(p);
    }
}
