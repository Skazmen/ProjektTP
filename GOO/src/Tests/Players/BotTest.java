package Players;

import org.junit.jupiter.api.BeforeEach;

public class BotTest {

    private Bot botUnderTest;

    @BeforeEach
    void setUp() {
        int size = 19;
        botUnderTest = new Bot(size);
    }
}
