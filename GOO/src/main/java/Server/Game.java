package Server;

import Players.HumanPlayer;
import Players.Player;
import rules.Chain;

public class Game {

    private GridPosition[][] grid;
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
//        addStone(x,y,p);
        grid[x][y].setPlayer(p);
    }

    public void addStone(int x, int y, HumanPlayer p) {
        System.out.println("begin add");
        GridPosition newStone = new GridPosition(x, y);
        newStone.setPlayer(p);
        grid[x][y] = newStone;
        // sprawdza sąsiadów
        GridPosition[] neighbors = new GridPosition[4];
        //Nie sprawdza poza planszą
        if (x > 0) {
            neighbors[0] = grid[x - 1][y];
        }
        if (x < size - 1) {
            neighbors[1] = grid[x + 1][y];
        }
        if (y > 1) {
            neighbors[2] = grid[x][y - 1];
        }
        if (y < size - 1) {
            neighbors[3] = grid[x][y + 1];
        }
        System.out.println("middle add");
        //Przygowywuje Łańcuch dla nowego kamienia
        Chain finalChain = new Chain();
        for (GridPosition neighbor : neighbors) {
            //Nic, jeśli nie ma sąsiedniego kamienia
            if (neighbor == null) {
                continue;
            }

            newStone.decreaseLiberties();
            neighbor.decreaseLiberties();

            // Jeśli ma inny kolor niż nowy Kamień, sprawdza
            if (neighbor.getColor() != newStone.getColor()) {
                checkStone(neighbor);
                continue;
            }

            if (neighbor.chain != null) {
                finalChain.join(neighbor.chain);
            }
        }
        finalChain.addStone(newStone);
        System.out.println("end add");
    }

    public void checkStone(GridPosition stone) {
        // spradzamy wszystkie swobody, bo kamienie tworza lancuch
        if (stone.chain.getLiberties() == 0) {
            for (GridPosition s : stone.chain.stones) {
                s.chain = null;
                grid[s.getX()][s.getY()] = null;
            }
        }
    }

}


/*public class Grid {

    private final int SIZE;
    private Stone[][] stones;

    public Grid(int size) {
        SIZE = size;
        stones = new Stone[SIZE][SIZE];
    }

    public void addStone(int row, int col, Colours colour) {
        Stone newStone = new Stone(row, col, colour);
        stones[row][col] = newStone;
        // sprawdza sąsiadów
        Stone[] neighbors = new Stone[4];
        //Nie sprawdza poza planszą
        if (row > 0) {
            neighbors[0] = stones[row - 1][col];
        }
        if (row < SIZE - 1) {
            neighbors[1] = stones[row + 1][col];
        }
        if (col > 1) {
            neighbors[2] = stones[row][col - 1];
        }
        if (col < SIZE - 1) {
            neighbors[3] = stones[row][col + 1];
        }
        //Przygowywuje Łańcuch dla nowego kamienia
        Chain finalChain = new Chain();
        for (Stone neighbor : neighbors) {
            //Nic, jeśli nie ma sąsiedniego kamienia
            if (neighbor == null) {
                continue;
            }

            newStone.liberties--;
            neighbor.liberties--;
            // Jeśli ma inny kolor niż nowy Kamień, sprawdza
            if (neighbor.colour != newStone.colour) {
                checkStone(neighbor);
                continue;
            }

            if (neighbor.chain != null) {
                finalChain.join(neighbor.chain);
            }
        }
        finalChain.addStone(newStone);
    }

    //Sprawdź swobody kamienia

    public void checkStone(Stone stone) {
        // spradzamy wszystkie swobody, bo kamienie tworza lancuch
        if (stone.chain.getLiberties() == 0) {
            for (Stone s : stone.chain.stones) {
                s.chain = null;
                stones[s.row][s.col] = null;
            }
        }
    }



    //Zwraca prawdę, jeśli dana pozycja jest zajęta przez jakis stone

    public boolean isOccupied(int row, int col) {
        return stones[row][col] != null;
    }

    public Colours getState(int row, int col) {
        Stone stone = stones[row][col];
        if (stone == null) {
            return null;
        } else
            return stone.colour;
        }
}*/