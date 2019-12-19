package Rules;

import Players.HumanPlayer;
import Players.Player;
import Server.ExtractedGrid;

import java.awt.*;
import java.util.ArrayList;

public class Game {

    private Stone[][] grid;
    private int size;
    private int[][] possibilities;

    public Game(int size){
        this.size = size;
        this.grid = new Stone[size][size];
        this.possibilities = new int[size][size];
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                possibilities[i][j] = 1;
            }
        }
    }

    public ExtractedGrid extractGrid(){
        ExtractedGrid ex = new ExtractedGrid(size);
        for (int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(grid[i][j]==null) ex.setPosition(i,j,null);
                else ex.setPosition(i,j, grid[i][j].getColor());
            }
        }
        return ex;
    }

    public boolean checkMove(Player p, String encoded){
        // t o d o - nie można położyć tak zeby zabrac ostatni oddech swojemu łańcuchowi (chyba ze taki ruch dusi kamienie przeciwnika)

        String[] parts = encoded.split("/");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        boolean free = noPlayerInThatPlace(x,y);
        boolean liberties = hasLibertiesAround(x,y);
        if(!liberties)     possibilities[x][y] = 0;
        return free && liberties;
    }


    // zwraca true gdy na danej pozycji jest puste pole
    private boolean noPlayerInThatPlace(int x, int y) {
        return grid[x][y] == null;
    }
    //zwraca true gdy dana pozycja ma co najmniej 1 oddech
    private boolean hasLibertiesAround(int x, int y) {
        Color c = (grid[x][y]==null ? null : grid[x][y].getColor());
        boolean up =    (y!=0       && (grid[x][y-1]==null || grid[x][y-1].getColor()==c));
        boolean down =  (y!=size-1  && (grid[x][y+1]==null || grid[x][y+1].getColor()==c));
        boolean left =  (x!=0       && (grid[x-1][y]==null || grid[x-1][y].getColor()==c));
        boolean right = (x!=size-1  && (grid[x+1][y]==null || grid[x+1][y].getColor()==c));
        return up || down || left || right;
    }


    public void updateBoard(Player p, String encoded) {
        String[] parts = encoded.split("/");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);

        Stone newStone = new Stone(x, y, size, p);
        grid[x][y] = newStone;
        possibilities[x][y] = 0;
        // create array of neighbors
        ArrayList<Stone> neighbors = new ArrayList<>();
        if ((x > 0)      && grid[x-1][y]!=null)   neighbors.add( grid[x-1][y] );
        if ((x < size-1) && grid[x+1][y]!=null)   neighbors.add( grid[x+1][y] );
        if ((y > 1)      && grid[x][y-1]!=null)   neighbors.add( grid[x][y-1] );
        if ((y < size-1) && grid[x][y+1]!=null)   neighbors.add( grid[x][y+1] );

        //Przygowywuje Łańcuch dla nowego kamienia
        Chain finalChain = new Chain();
        for (Stone neighbor : neighbors) {
            newStone.decreaseLiberties();
            neighbor.decreaseLiberties();

            // Jeśli ma inny kolor niż nowy Kamień, sprawdza czy zamknął inny łańcuch i powinien zniknąć
            if (neighbor.getColor() != newStone.getColor()) {
                checkIfClosesOtherChain(neighbor);
                continue;
            }

            if (neighbor.getChain() != null) {
                finalChain.join(neighbor.getChain());
            }
        }
        finalChain.addStone(newStone);
    }

    public void checkIfClosesOtherChain(Stone stone) {
        // spradzamy wszystkie swobody, bo kamienie tworza lancuch
        if (stone.getChain()!=null && stone.getChain().getLiberties() == 0) {
            for (Stone s : stone.chain.stones) {
                grid[s.getX()][s.getY()] = null;
                s.chain = null;
                //dodanie spowrotem oddechow
                if ((s.getX() > 0)      && grid[s.getX()-1][s.getY()]!=null)   grid[s.getX()-1][s.getY()].increaseLiberties();
                if ((s.getX() < size-1) && grid[s.getX()+1][s.getY()]!=null)   grid[s.getX()+1][s.getY()].increaseLiberties();
                if ((s.getY() > 1)      && grid[s.getX()][s.getY()-1]!=null)   grid[s.getX()][s.getY()-1].increaseLiberties();
                if ((s.getY() < size-1) && grid[s.getX()][s.getY()+1]!=null)   grid[s.getX()][s.getY()+1].increaseLiberties();
            }
        }
    }

    //zwraca true gdy nie da się już wykonać żadnego nowego ruchu
    public boolean impossibleToMove() {
        int sum = 0;
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                sum += possibilities[i][j];
            }
        }
        return sum < (0.1*size*size);
    }
}