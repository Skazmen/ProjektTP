package Server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Rules.Stone;

class StoneTest {

    private Stone stoneUnderTest;

    @BeforeEach
    void setUp() {
        stoneUnderTest = new Stone(2, 2);
    }

    @Test
    void testDecreaseLiberties() {
        stoneUnderTest.decreaseLiberties();
    }
}
