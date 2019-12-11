package Game;

import Server.Enums.Colours;
import rules.Chain;
import rules.Stone;

public class Grid {

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
        Chain finalChain = new Chain(newStone.colour);
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
}