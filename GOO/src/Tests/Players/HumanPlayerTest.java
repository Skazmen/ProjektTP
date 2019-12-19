package Players;

import org.junit.jupiter.api.BeforeEach;

public class HumanPlayerTest {

    private HumanPlayer humanPlayerUnderTest;

    @BeforeEach
    void setUp() {
        String nick = "Player_1234";
        humanPlayerUnderTest = new HumanPlayer(nick);
    }
}
