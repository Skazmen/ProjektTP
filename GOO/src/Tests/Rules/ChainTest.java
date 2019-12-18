package Rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChainTest {

    private Chain chainUnderTest;

    @BeforeEach
    void setUp() {
        chainUnderTest = new Chain();
    }

    @Test
    void testGetLiberties() {
        final int result = chainUnderTest.getLiberties();
        assertEquals(0, result);
    }

    @Test
    void testAddStone() {
        final Stone stone = new Stone(0, 0);
        chainUnderTest.addStone(stone);
    }

    @Test
    void testJoin() {
        final Chain chain = new Chain();
        chainUnderTest.join(chain);
    }
}
