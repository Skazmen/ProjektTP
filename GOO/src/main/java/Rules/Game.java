package Rules;

import Players.HumanPlayer;
import Players.Player;
import Server.ExtractedGrid;

public class Game {

    private Stone[][] grid;
    private int size;

    public Game(int size){
        this.size = size;
        this.grid = new Stone[size][size];
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                grid[i][j] = new Stone();
            }
        }
    }

    public ExtractedGrid extractGrid(){
        ExtractedGrid ex = new ExtractedGrid(size);
        for (int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                ex.setPosition(i,j, grid[i][j].getColor());
            }
        }
        return ex;
    }

    public boolean checkMove(Player p, String encoded){
        //TODO logika do sprawdzenia czy da sie polozyc na tej pozycji
        // nie można położyć:
        // - pkt bez oddechu
        // - tak zeby zabrac ostatni oddech swojemu łańcuchowi (chyba ze taki ruch dusi kamienie przeciwnika)

        String[] parts = encoded.split("/");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        boolean free = noPlayerInThatPlace(x,y);
        boolean liberties = hasLibertiesAround(x,y);
        boolean notKamikadze = doesntTakeLastChainLiberty(x,y);

        updateBoard(p,x,y);
        return free && liberties && notKamikadze;
    }


    // zwraca true gdy na danej pozycji jest puste pole
    private boolean noPlayerInThatPlace(int x, int y) {
        return grid[x][y].getPlayer() == null;
    }
    //zwraca true gdy dana pozycja ma co najmniej 1 oddech
    private boolean hasLibertiesAround(int x, int y) {
        boolean up =    (y!=0       && grid[x][y-1]!=null);
        boolean down =  (y!=size-1  && grid[x][y+1]!=null);
        boolean left =  (x!=0       && grid[x-1][y]!=null);
        boolean right = (x!=size-1  && grid[x+1][y]!=null);
        System.out.println(up +" "+ down +" "+ left +" "+ right);
        return up || down || left || right;
    }
    //zwraca true gdy położenie w tej pozycji nie zabierze ostatniego oddechu łańcuchowi
    private boolean doesntTakeLastChainLiberty(int x, int y){
        //znaleźć łańcuch sąsiadujący
        return true;
    }

    void updateBoard(Player p, int x, int y){
        //TODO znikniecie kamieni które nie mają oddechów
//        addStone(x,y,p);
        grid[x][y].setPlayer(p);
    }

    public void addStone(int x, int y, HumanPlayer p) {
        System.out.println("begin add");
        Stone newStone = new Stone(x, y);
        newStone.setPlayer(p);
        grid[x][y] = newStone;
        // sprawdza sąsiadów
        Stone[] neighbors = new Stone[4];
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
        for (Stone neighbor : neighbors) {
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

    public void checkStone(Stone stone) {
        // spradzamy wszystkie swobody, bo kamienie tworza lancuch
        if (stone.chain.getLiberties() == 0) {
            for (Stone s : stone.chain.stones) {
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