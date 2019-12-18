package Menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameBoardPanelTest {

    private GameBoardPanel gameBoardPanelUnderTest;

    @BeforeEach
    void setUp() {
        gameBoardPanelUnderTest = new GameBoardPanel(3);
    }


    @Test
    void testUpdate() {
        final int[][] pos = new int[][]{{0}};

        gameBoardPanelUnderTest.update(pos);

    }

    @Test
    void testGetPreferredSize() {
        final Dimension expectedResult = new Dimension(648, 648);

        final Dimension result = gameBoardPanelUnderTest.getPreferredSize();

        assertEquals(expectedResult, result);
    }
}
