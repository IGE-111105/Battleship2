package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTimerTest {

    private GameTimer gameTimer;

    @BeforeEach
    void setUp() {
        gameTimer = new GameTimer();
    }

    @AfterEach
    void tearDown() {
        gameTimer.reset();
    }

    @Test
    void testStart() {
        gameTimer.start();
        assertTrue(gameTimer.getElapsedSeconds() >= 0, "Timer should be running after start.");
    }

    @Test
    void testPause() {
        gameTimer.start();
        long elapsedBefore = gameTimer.getElapsedSeconds();
        gameTimer.pause();
        long elapsedAfter = gameTimer.getElapsedSeconds();
        assertEquals(elapsedBefore, elapsedAfter, "Elapsed time should not change after pause.");
    }

    @Test
    void testResume() {
        gameTimer.start();
        gameTimer.pause();
        long pausedTime = gameTimer.getElapsedSeconds();
        gameTimer.resume();
        long resumedTime = gameTimer.getElapsedSeconds();
        assertTrue(resumedTime >= pausedTime, "Timer should continue counting after resume.");
    }

    @Test
    void testGetElapsedSeconds() {
        gameTimer.start();
        long elapsed = gameTimer.getElapsedSeconds();
        assertTrue(elapsed >= 0, "Elapsed seconds should be non-negative.");
    }

    @Test
    void testGetElapsedSecondsBeforeStart() {
        long elapsed = gameTimer.getElapsedSeconds();
        assertEquals(0, elapsed, "Elapsed seconds should be 0 before timer starts.");
    }

    @Test
    void testGetFormattedTime() {
        gameTimer.start();
        String formatted = gameTimer.getFormattedTime();
        assertNotNull(formatted, "Formatted time should not be null.");
        assertTrue(formatted.matches("\\d{2}:\\d{2}:\\d{2}"), "Formatted time should match pattern HH:MM:SS.");
    }

    @Test
    void testReset() {
        gameTimer.start();
        gameTimer.pause();
        gameTimer.reset();
        assertEquals(0, gameTimer.getElapsedSeconds(), "Elapsed seconds should be 0 after reset.");
        assertEquals("00:00:00", gameTimer.getFormattedTime(), "Formatted time should be 00:00:00 after reset.");
    }
}
