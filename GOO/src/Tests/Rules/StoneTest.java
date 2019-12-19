package Rules;

import Players.Bot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Rules.Stone;

class StoneTest {

    private Stone stoneUnderTest;

    @BeforeEach
    void setUp() {
        stoneUnderTest = new Stone(2, 2, 19, new Bot(19));
    }

    @Test
    void testDecreaseLiberties() {
        stoneUnderTest.decreaseLiberties();
    }
}
