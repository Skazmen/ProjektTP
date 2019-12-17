package rules;

import Server.Enums.Colours;
import Server.GridPosition;

import java.util.ArrayList;


public class Chain {

    public ArrayList<GridPosition> stones;

    public Chain() {
        stones = new ArrayList<>();
    }

    public int getLiberties() {
        int total = 0;
        for (GridPosition stone : stones) {
            total += stone.getLiberties();
        }
        return total;
    }

    public void addStone(GridPosition stone) {
        stone.chain = this;
        stones.add(stone);
    }

    public void join(Chain chain) {
        for (GridPosition stone : chain.stones) {
            addStone(stone);
        }
    }

}