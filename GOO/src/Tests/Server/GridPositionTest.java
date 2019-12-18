package Server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GridPositionTest {

    private GridPosition gridPositionUnderTest;

    @BeforeEach
    void setUp() {
        gridPositionUnderTest = new GridPosition(2, 2);
    }

    @Test
    void testDecreaseLiberties() {
        gridPositionUnderTest.decreaseLiberties();
    }
}
