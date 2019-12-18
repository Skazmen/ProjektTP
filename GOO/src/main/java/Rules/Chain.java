package Rules;

import java.util.ArrayList;


public class Chain {

    public ArrayList<Stone> stones;

    public Chain() {
        stones = new ArrayList<>();
    }

    public int getLiberties() {
        int total = 0;
        for (Stone stone : stones) {
            total += stone.getLiberties();
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