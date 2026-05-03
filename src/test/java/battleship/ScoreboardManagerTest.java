package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardManagerTest {

    private static final String FILE_PATH = "data/scoreboard.json";

    @AfterEach
    void cleanUp() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            assertTrue(file.delete());
        }

        File folder = new File("data");
        if (folder.exists()) {
            folder.delete();
        }
    }

    @Test
    @DisplayName("Load scoreboard should return empty list when file does not exist")
    void loadScoreboardShouldReturnEmptyListWhenFileDoesNotExist() {
        List<ScoreboardEntry> entries = ScoreboardManager.loadScoreboard();

        assertNotNull(entries);
        assertTrue(entries.isEmpty());
    }

    @Test
    @DisplayName("Save scoreboard should create file")
    void saveScoreboardShouldCreateFile() {
        List<ScoreboardEntry> entries = new ArrayList<>();

        entries.add(new ScoreboardEntry(
                "2025-05-03",
                10,
                30,
                15,
                5,
                "DERROTA"
        ));

        ScoreboardManager.saveScoreboard(entries);

        File file = new File(FILE_PATH);

        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    @DisplayName("Saved scoreboard should be loaded correctly")
    void savedScoreboardShouldBeLoadedCorrectly() {
        List<ScoreboardEntry> entries = new ArrayList<>();

        entries.add(new ScoreboardEntry(
                "2025-05-03",
                10,
                30,
                15,
                5,
                "DERROTA"
        ));

        ScoreboardManager.saveScoreboard(entries);

        List<ScoreboardEntry> loaded = ScoreboardManager.loadScoreboard();

        assertEquals(1, loaded.size());
        assertEquals("2025-05-03", loaded.get(0).getTimestamp());
        assertEquals(10, loaded.get(0).getMoves());
        assertEquals(30, loaded.get(0).getShots());
    }

    @Test
    @DisplayName("Add game record should append new entry")
    void addGameRecordShouldAppendNewEntry() {
        Game game = new Game(new Fleet());

        ScoreboardManager.addGameRecord(game);

        List<ScoreboardEntry> entries = ScoreboardManager.loadScoreboard();

        assertEquals(1, entries.size());
        assertEquals(0, entries.get(0).getMoves());
        assertEquals(0, entries.get(0).getShots());
        assertEquals(0, entries.get(0).getHits());
        assertEquals(0, entries.get(0).getSunkShips());
        assertEquals("DERROTA", entries.get(0).getResult());
        assertNotNull(entries.get(0).getTimestamp());
    }
}