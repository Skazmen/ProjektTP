package Server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExtractedGridTest {

    private ExtractedGrid extractedGridUnderTest;

    @BeforeEach
    void setUp() {
        extractedGridUnderTest = new ExtractedGrid(1);
    }


    @Test
    void testToString() {
        // Setup

        // Run the test
        final String result = extractedGridUnderTest.toString();

        // Verify the results
        assertEquals("1/0/", result);
    }
}