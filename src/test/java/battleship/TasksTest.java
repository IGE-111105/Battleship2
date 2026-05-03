package battleship;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TasksTest {

    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testMenuHelp() {
        Tasks.menuHelp();
        String output = outputStream.toString();

        assertTrue(output.contains("AJUDA DO MENU"), "Output should contain menu title.");
        assertTrue(output.contains("gerafrota"), "Output should contain gerafrota command.");
        assertTrue(output.contains("lefrota"), "Output should contain lefrota command.");
        assertTrue(output.contains("desisto"), "Output should contain desisto command.");
    }

    @Test
    void testReadPositionValid() {
        String input = "5 7";
        Scanner scanner = new Scanner(input);

        Position position = Tasks.readPosition(scanner);

        assertNotNull(position, "Position should not be null.");
        assertEquals(5, position.getRow(), "Row should be 5.");
        assertEquals(7, position.getColumn(), "Column should be 7.");
    }

    @Test
    void testReadPositionZeroValues() {
        String input = "0 0";
        Scanner scanner = new Scanner(input);

        Position position = Tasks.readPosition(scanner);

        assertNotNull(position, "Position should not be null.");
        assertEquals(0, position.getRow(), "Row should be 0.");
        assertEquals(0, position.getColumn(), "Column should be 0.");
    }

    @Test
    void testReadClassicPositionCompactFormat() {
        String input = "A5";
        Scanner scanner = new Scanner(input);

        IPosition position = Tasks.readClassicPosition(scanner);

        assertNotNull(position, "Position should not be null.");
        assertEquals(5, position.getRow(), "Row should be 5.");
        assertEquals('A', position.getColumn(), "Column should be A.");
    }

    @Test
    void testReadClassicPositionSpaceFormat() {
        String input = "B 7";
        Scanner scanner = new Scanner(input);

        IPosition position = Tasks.readClassicPosition(scanner);

        assertNotNull(position, "Position should not be null.");
        assertEquals(7, position.getRow(), "Row should be 7.");
        assertEquals('B', position.getColumn(), "Column should be B.");
    }

    @Test
    void testReadClassicPositionLowercaseFormat() {
        String input = "c9";
        Scanner scanner = new Scanner(input);

        IPosition position = Tasks.readClassicPosition(scanner);

        assertNotNull(position, "Position should not be null.");
        assertEquals(9, position.getRow(), "Row should be 9.");
        assertEquals('C', position.getColumn(), "Column should be C.");
    }

    @Test
    void testReadClassicPositionInvalidFormat() {
        String input = "123 456";
        Scanner scanner = new Scanner(input);

        assertThrows(IllegalArgumentException.class, () -> {
            Tasks.readClassicPosition(scanner);
        }, "Should throw IllegalArgumentException for invalid format.");
    }

    @Test
    void testReadClassicPositionNoInput() {
        String input = "";
        Scanner scanner = new Scanner(input);

        assertThrows(IllegalArgumentException.class, () -> {
            Tasks.readClassicPosition(scanner);
        }, "Should throw IllegalArgumentException when no input available.");
    }

    @Test
    void testReadShipValid() {
        String input = "Battleship 5 7 N";
        Scanner scanner = new Scanner(input);

        Ship ship = Tasks.readShip(scanner);

        assertNotNull(ship, "Ship should not be null.");
        assertNotNull(ship.getPosition(), "Ship position should not be null.");
        assertNotNull(ship.getBearing(), "Ship bearing should not be null.");
    }

    @Test
    void testReadShipWithDifferentBearing() {
        String input = "Destroyer 2 3 S";
        Scanner scanner = new Scanner(input);

        Ship ship = Tasks.readShip(scanner);

        assertNotNull(ship, "Ship should not be null.");
        assertEquals(Compass.SOUTH, ship.getBearing(), "Bearing should be SOUTH.");
    }

    @Test
    void testBuildFleetSuccess() {
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            input.append("Battleship ").append(i).append(" ").append(i).append(" N ");
        }
        Scanner scanner = new Scanner(input.toString());

        Fleet fleet = Tasks.buildFleet(scanner);

        assertNotNull(fleet, "Fleet should not be null.");
        assertTrue(fleet.getShips().size() > 0, "Fleet should contain ships.");
    }

    @Test
    void testBuildFleetEmpty() {
        String input = "";
        Scanner scanner = new Scanner(input);

        assertThrows(Exception.class, () -> {
            Tasks.buildFleet(scanner);
        }, "Should throw exception when building empty fleet.");
    }

    @Test
    void testReadPositionBoundary() {
        String input = "9 9";
        Scanner scanner = new Scanner(input);

        Position position = Tasks.readPosition(scanner);

        assertNotNull(position, "Position should not be null.");
        assertEquals(9, position.getRow(), "Row should be 9.");
        assertEquals(9, position.getColumn(), "Column should be 9.");
    }

    @Test
    void testReadClassicPositionMultipleDigits() {
        String input = "A10";
        Scanner scanner = new Scanner(input);

        IPosition position = Tasks.readClassicPosition(scanner);

        assertNotNull(position, "Position should not be null.");
        assertEquals(10, position.getRow(), "Row should be 10.");
    }
}
