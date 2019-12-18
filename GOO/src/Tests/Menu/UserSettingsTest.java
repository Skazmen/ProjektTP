package Menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserSettingsTest {

    private UserSettings userSettingsUnderTest;

    @BeforeEach
    void setUp() {
        userSettingsUnderTest = new UserSettings();
    }

    @Test
    void testSetNick() {
        userSettingsUnderTest.setNick("n");
    }
}
