package Server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Board boardUnderTest;

    @BeforeEach
    void setUp() {
        boardUnderTest = Board.getInstance();
    }

    @Test
    void testGetInstance() {
        final Board result = Board.getInstance();
    }
}
