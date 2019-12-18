package Menu;

import org.junit.jupiter.api.BeforeEach;

class BoardGuiTest {

    private BoardGui boardGuiUnderTest;

    @BeforeEach
    void setUp() {
        boardGuiUnderTest = new BoardGui(new UserSettings());
    }
}
