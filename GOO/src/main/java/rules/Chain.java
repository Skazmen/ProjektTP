package rules;

import Server.Enums.Colours;

import java.util.ArrayList;


public class Chain {

    public ArrayList<Stone> stones;
    public Colours colour;

    public Chain(Colours state) {
        stones = new ArrayList<>();
    }

    public int getLiberties() {
        int total = 0;
        for (Stone stone : stones) {
            total += stone.liberties;
        }
        return total;
    }

    public void addStone(Stone stone) {
        stone.chain = this;
        stones.add(stone);
    }

    public void join(Chain chain) {
        for (Stone stone : chain.stones) {
            addStone(stone);
        }
    }

}