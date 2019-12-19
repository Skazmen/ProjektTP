package Rules;

import org.junit.jupiter.api.BeforeEach;

public class GameTest {

    private Game gameUnderTest;

    @BeforeEach
    void setUp() {
        int size = 19;
        gameUnderTest = new Game(19);
    }


}
