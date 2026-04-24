package battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @Test
    @DisplayName("toString returns correct format")
    void testToString() {
        List<IPosition> shots = new ArrayList<>();
        shots.add(new Position('A', 1));
        Move move = new Move(1, shots, new ArrayList<>());
        String result = move.toString();
        assertTrue(result.contains("1"));
    }

    @Test
    @DisplayName("getNumber returns correct move number")
    void getNumber() {
        Move move = new Move(5, new ArrayList<>(), new ArrayList<>());
        assertEquals(5, move.getNumber());
    }

    @Test
    @DisplayName("getShots returns correct list of shots")
    void getShots() {
        List<IPosition> shots = new ArrayList<>();
        shots.add(new Position('A', 1));
        Move move = new Move(1, shots, new ArrayList<>());
        assertEquals(1, move.getShots().size());
    }

    @Test
    @DisplayName("getShotResults returns correct list of results")
    void getShotResults() {
        Move move = new Move(1, new ArrayList<>(), new ArrayList<>());
        assertNotNull(move.getShotResults());
        assertEquals(0, move.getShotResults().size());
    }

    @Test
    @DisplayName("processEnemyFire returns valid JSON")
    void processEnemyFire() {
        Move move = new Move(1, new ArrayList<>(), new ArrayList<>());
        String json = move.processEnemyFire(false);
        assertNotNull(json);
        assertTrue(json.contains("validShots"));
    }
}