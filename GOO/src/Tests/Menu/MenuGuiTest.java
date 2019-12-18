package Menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.ActionEvent;

class MenuGuiTest {

    private MenuGui menuGuiUnderTest;

    @BeforeEach
    void setUp() {
        menuGuiUnderTest = new MenuGui();
    }

    @Test
    void testActionPerformed() {
        final ActionEvent e = new ActionEvent("source", 0, "command", 0L, 0);

        menuGuiUnderTest.actionPerformed(e);

        // Verify the results
    }
}
