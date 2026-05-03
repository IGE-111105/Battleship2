package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PDFExporterTest {

    private static final String FILE_NAME = "game_results.pdf";

    @AfterEach
    void cleanUp() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            assertTrue(file.delete());
        }
    }

    @Test
    @DisplayName("Should create PDF file successfully")
    void shouldCreatePdfFileSuccessfully() {
        Game game = new Game(new Fleet());

        assertDoesNotThrow(() -> PDFExporter.exportGame(game));

        File file = new File(FILE_NAME);
        assertTrue(file.exists());
    }

    @Test
    @DisplayName("Created PDF should not be empty")
    void createdPdfShouldNotBeEmpty() {
        Game game = new Game(new Fleet());

        PDFExporter.exportGame(game);

        File file = new File(FILE_NAME);

        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    @DisplayName("Export game should not throw exceptions")
    void exportGameShouldNotThrowExceptions() {
        Game game = new Game(new Fleet());

        assertDoesNotThrow(() -> PDFExporter.exportGame(game));
    }
}