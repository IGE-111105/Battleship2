package battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardEntryTest {

    @Test
    @DisplayName("Default constructor should create empty entry")
    void defaultConstructorShouldCreateEmptyEntry() {
        ScoreboardEntry entry = new ScoreboardEntry();

        assertNull(entry.getTimestamp());
        assertEquals(0, entry.getMoves());
        assertEquals(0, entry.getShots());
        assertEquals(0, entry.getHits());
        assertEquals(0, entry.getSunkShips());
        assertNull(entry.getResult());
    }

    @Test
    @DisplayName("Parameterized constructor should initialize all fields")
    void parameterizedConstructorShouldInitializeAllFields() {
        ScoreboardEntry entry = new ScoreboardEntry(
                "2025-05-03",
                10,
                30,
                12,
                4,
                "Victory"
        );

        assertEquals("2025-05-03", entry.getTimestamp());
        assertEquals(10, entry.getMoves());
        assertEquals(30, entry.getShots());
        assertEquals(12, entry.getHits());
        assertEquals(4, entry.getSunkShips());
        assertEquals("Victory", entry.getResult());
    }

    @Test
    @DisplayName("Setters should update values correctly")
    void settersShouldUpdateValuesCorrectly() {
        ScoreboardEntry entry = new ScoreboardEntry();

        entry.setTimestamp("2025-05-03");
        entry.setMoves(20);
        entry.setShots(60);
        entry.setHits(25);
        entry.setSunkShips(8);
        entry.setResult("Defeat");

        assertEquals("2025-05-03", entry.getTimestamp());
        assertEquals(20, entry.getMoves());
        assertEquals(60, entry.getShots());
        assertEquals(25, entry.getHits());
        assertEquals(8, entry.getSunkShips());
        assertEquals("Defeat", entry.getResult());
    }
}