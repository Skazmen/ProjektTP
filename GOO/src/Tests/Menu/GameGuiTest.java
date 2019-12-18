package Menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;

class GameGuiTest {

    private GameGui gameGuiUnderTest;

    @BeforeEach
    void setUp() {
        gameGuiUnderTest = new GameGui();
    }

    @Test
    void testActionPerformed() {
        final ActionEvent e = new ActionEvent("source", 0, "command", 0L, 0);

        gameGuiUnderTest.actionPerformed(e);
    }
}
