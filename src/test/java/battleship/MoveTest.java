package battleship;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MoveTest {

    private Move move;
    private List<IPosition> shots;
    private List<IGame.ShotResult> shotResults;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        shots = new ArrayList<>();
        shotResults = new ArrayList<>();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testToString() {
        shots.add(mock(IPosition.class));
        shots.add(mock(IPosition.class));
        shotResults.add(mock(IGame.ShotResult.class));

        move = new Move(1, shots, shotResults);
        String result = move.toString();

        assertTrue(result.contains("Move{"), "Should contain 'Move{'");
        assertTrue(result.contains("number=1"), "Should contain move number");
        assertTrue(result.contains("shots=2"), "Should contain shots count");
        assertTrue(result.contains("results=1"), "Should contain results count");
    }

    @Test
    void testGetNumber() {
        move = new Move(5, shots, shotResults);

        assertEquals(5, move.getNumber(), "Move number should be 5");
    }

    @Test
    void testGetShots() {
        shots.add(mock(IPosition.class));
        shots.add(mock(IPosition.class));
        move = new Move(1, shots, shotResults);

        List<IPosition> result = move.getShots();

        assertNotNull(result, "Shots list should not be null");
        assertEquals(2, result.size(), "Should return 2 shots");
        assertEquals(shots, result, "Should return the same shots list");
    }

    @Test
    void testGetShotResults() {
        shotResults.add(mock(IGame.ShotResult.class));
        shotResults.add(mock(IGame.ShotResult.class));
        move = new Move(1, shots, shotResults);

        List<IGame.ShotResult> result = move.getShotResults();

        assertNotNull(result, "Shot results list should not be null");
        assertEquals(2, result.size(), "Should return 2 results");
        assertEquals(shotResults, result, "Should return the same results list");
    }

    @Test
    void testProcessEnemyFireAllValidHits() {
        IPosition pos1 = mock(IPosition.class);
        shots.add(pos1);

        IShip ship = mock(IShip.class);
        when(ship.getCategory()).thenReturn("Battleship");

        IGame.ShotResult result = mock(IGame.ShotResult.class);
        when(result.valid()).thenReturn(true);
        when(result.repeated()).thenReturn(false);
        when(result.ship()).thenReturn(ship);
        when(result.sunk()).thenReturn(false);

        shotResults.add(result);
        move = new Move(1, shots, shotResults);

        String json = move.processEnemyFire(false);

        assertNotNull(json, "JSON should not be null");
        assertTrue(json.contains("validShots"), "Should contain validShots");
        assertTrue(json.contains("\"validShots\" : 1"), "Should have 1 valid shot");
    }

    @Test
    void testProcessEnemyFireWithMissedShots() {
        IPosition pos1 = mock(IPosition.class);
        shots.add(pos1);

        IGame.ShotResult result = mock(IGame.ShotResult.class);
        when(result.valid()).thenReturn(true);
        when(result.repeated()).thenReturn(false);
        when(result.ship()).thenReturn(null);
        when(result.sunk()).thenReturn(false);

        shotResults.add(result);
        move = new Move(1, shots, shotResults);

        String json = move.processEnemyFire(false);

        assertNotNull(json, "JSON should not be null");
        assertTrue(json.contains("missedShots"), "Should contain missedShots");
        assertTrue(json.contains("\"missedShots\" : 1"), "Should have 1 missed shot");
    }

    @Test
    void testProcessEnemyFireWithRepeatedShots() {
        IPosition pos1 = mock(IPosition.class);
        shots.add(pos1);

        IGame.ShotResult result = mock(IGame.ShotResult.class);
        when(result.valid()).thenReturn(true);
        when(result.repeated()).thenReturn(true);

        shotResults.add(result);
        move = new Move(1, shots, shotResults);

        String json = move.processEnemyFire(false);

        assertNotNull(json, "JSON should not be null");
        assertTrue(json.contains("repeatedShots"), "Should contain repeatedShots");
        assertTrue(json.contains("\"repeatedShots\" : 1"), "Should have 1 repeated shot");
    }

    @Test
    void testProcessEnemyFireWithSunkBoats() {
        IPosition pos1 = mock(IPosition.class);
        shots.add(pos1);

        IShip ship = mock(IShip.class);
        when(ship.getCategory()).thenReturn("Destroyer");

        IGame.ShotResult result = mock(IGame.ShotResult.class);
        when(result.valid()).thenReturn(true);
        when(result.repeated()).thenReturn(false);
        when(result.ship()).thenReturn(ship);
        when(result.sunk()).thenReturn(true);

        shotResults.add(result);
        move = new Move(1, shots, shotResults);

        String json = move.processEnemyFire(false);

        assertNotNull(json, "JSON should not be null");
        assertTrue(json.contains("sunkBoats"), "Should contain sunkBoats");
        assertTrue(json.contains("Destroyer"), "Should contain sunk boat type");
    }

    @Test
    void testProcessEnemyFireWithOutsideShots() {
        IGame.ShotResult result = mock(IGame.ShotResult.class);
        when(result.valid()).thenReturn(false);

        shotResults.add(result);
        move = new Move(1, shots, shotResults);

        String json = move.processEnemyFire(false);

        assertNotNull(json, "JSON should not be null");
        assertTrue(json.contains("outsideShots"), "Should contain outsideShots");
    }

    @Test
    void testProcessEnemyFireVerboseMode() {
        IPosition pos1 = mock(IPosition.class);
        shots.add(pos1);

        IShip ship = mock(IShip.class);
        when(ship.getCategory()).thenReturn("Battleship");

        IGame.ShotResult result = mock(IGame.ShotResult.class);
        when(result.valid()).thenReturn(true);
        when(result.repeated()).thenReturn(false);
        when(result.ship()).thenReturn(ship);
        when(result.sunk()).thenReturn(false);

        shotResults.add(result);
        move = new Move(2, shots, shotResults);

        String json = move.processEnemyFire(true);

        String output = outputStream.toString();
        assertTrue(output.contains("Jogada nº2"), "Should print move number in verbose mode");
        assertNotNull(json, "JSON should not be null");
    }

    @Test
    void testProcessEnemyFireEmptyResults() {
        move = new Move(1, shots, shotResults);

        String json = move.processEnemyFire(false);

        assertNotNull(json, "JSON should not be null");
        assertTrue(json.contains("validShots"), "Should contain validShots");
    }

    @Test
    void testProcessEnemyFireMultipleSunkBoatsSameType() {
        IPosition pos1 = mock(IPosition.class);
        IPosition pos2 = mock(IPosition.class);
        shots.add(pos1);
        shots.add(pos2);

        IShip ship1 = mock(IShip.class);
        when(ship1.getCategory()).thenReturn("Corvette");

        IShip ship2 = mock(IShip.class);
        when(ship2.getCategory()).thenReturn("Corvette");

        IGame.ShotResult result1 = mock(IGame.ShotResult.class);
        when(result1.valid()).thenReturn(true);
        when(result1.repeated()).thenReturn(false);
        when(result1.ship()).thenReturn(ship1);
        when(result1.sunk()).thenReturn(true);

        IGame.ShotResult result2 = mock(IGame.ShotResult.class);
        when(result2.valid()).thenReturn(true);
        when(result2.repeated()).thenReturn(false);
        when(result2.ship()).thenReturn(ship2);
        when(result2.sunk()).thenReturn(true);

        shotResults.add(result1);
        shotResults.add(result2);
        move = new Move(1, shots, shotResults);

        String json = move.processEnemyFire(false);

        assertNotNull(json, "JSON should not be null");
        assertTrue(json.contains("sunkBoats"), "Should contain sunkBoats");
        assertTrue(json.contains("\"count\" : 2"), "Should count 2 sunk Corvettes");
    }

    @Test
    void testProcessEnemyFireJsonFormatValid() throws Exception {
        IPosition pos1 = mock(IPosition.class);
        shots.add(pos1);

        IGame.ShotResult result = mock(IGame.ShotResult.class);
        when(result.valid()).thenReturn(true);
        when(result.repeated()).thenReturn(false);
        when(result.ship()).thenReturn(null);

        shotResults.add(result);
        move = new Move(1, shots, shotResults);

        String json = move.processEnemyFire(false);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);

        assertTrue(jsonNode.has("validShots"), "JSON should have validShots field");
        assertTrue(jsonNode.has("missedShots"), "JSON should have missedShots field");
        assertTrue(jsonNode.has("repeatedShots"), "JSON should have repeatedShots field");
        assertTrue(jsonNode.has("outsideShots"), "JSON should have outsideShots field");
        assertTrue(jsonNode.has("sunkBoats"), "JSON should have sunkBoats field");
        assertTrue(jsonNode.has("hitsOnBoats"), "JSON should have hitsOnBoats field");
    }
}