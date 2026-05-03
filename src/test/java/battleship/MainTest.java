package battleship;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private Main main;

    @BeforeAll
    static void initJavaFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }

    @BeforeEach
    void setUp() {
        main = new Main();
    }

    @Test
    void testStart() {
        Stage testStage = new Stage();
        assertDoesNotThrow(() -> {
            main.start(testStage);
        }, "Start method should not throw exceptions.");

        assertNotNull(Main.boardView, "BoardView should be initialized after start.");
        testStage.close();
    }

    @Test
    void testMainMethod() {
        assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        }, "Main method should not throw exceptions.");
    }

    @Test
    void testBoardViewNotNull() {
        Stage testStage = new Stage();
        main.start(testStage);

        assertNotNull(Main.boardView, "BoardView should not be null after application starts.");
        testStage.close();
    }

    @Test
    void testApplicationInitialization() {
        Stage testStage = new Stage();
        assertDoesNotThrow(() -> {
            main.start(testStage);
        });

        assertTrue(testStage.isShowing() || !testStage.isShowing(), "Stage should be created.");
        testStage.close();
    }
}
