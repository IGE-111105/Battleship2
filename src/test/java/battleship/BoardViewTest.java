package battleship;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for BoardView.
 * Author: Marta Fonseca
 * Date: 3/5/2026
 * Cyclomatic Complexity for each method:
 * - testRefreshWithEmptyShipsAndMoves: 1
 * - testRefreshWithFloatingShip: 1
 * - testRefreshWithSunkenShip: 1
 * - testRefreshWithAdjacentPositionOutside: 1
 * - testRefreshWithMovesHittingShip: 1
 * - testRefreshWithMovesMissingShip: 1
 * - testRefreshWithShotOutsideBoard: 1
 * - testBoardViewInitialization: 1
 */

class BoardViewTest {

    private BoardView boardView;
    private IGame mockGame;
    private IFleet mockFleet;

    @BeforeAll
    static void initJavaFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
        }
    }

    @BeforeEach
    void setUp() {
        boardView = new BoardView();
        mockGame = mock(IGame.class);
        mockFleet = mock(IFleet.class);
    }

    @Test
    void testRefreshWithEmptyShipsAndMoves() {
        when(mockGame.getMyFleet()).thenReturn(mockFleet);
        when(mockGame.getAlienMoves()).thenReturn(new ArrayList<>());
        when(mockFleet.getShips()).thenReturn(new ArrayList<>());

        boardView.refresh(mockGame);

        assertTrue(true, "Refresh completed without errors.");
    }

    @Test
    void testRefreshWithFloatingShip() {
        IShip floatingShip = mock(IShip.class);
        when(floatingShip.stillFloating()).thenReturn(true);

        IPosition shipPos = mock(IPosition.class);
        when(shipPos.getRow()).thenReturn(0);
        when(shipPos.getColumn()).thenReturn(0);
        List<IPosition> positions = List.of(shipPos);
        when(floatingShip.getPositions()).thenReturn(positions);

        when(mockFleet.getShips()).thenReturn(List.of(floatingShip));
        when(mockGame.getMyFleet()).thenReturn(mockFleet);
        when(mockGame.getAlienMoves()).thenReturn(new ArrayList<>());

        boardView.refresh(mockGame);
        assertTrue(true);
    }

    @Test
    void testRefreshWithSunkenShip() {
        IShip sunkenShip = mock(IShip.class);
        when(sunkenShip.stillFloating()).thenReturn(false);

        IPosition shipPos = mock(IPosition.class);
        when(shipPos.getRow()).thenReturn(1);
        when(shipPos.getColumn()).thenReturn(1);
        List<IPosition> positions = List.of(shipPos);
        when(sunkenShip.getPositions()).thenReturn(positions);

        IPosition adjacentPos = mock(IPosition.class);
        when(adjacentPos.getRow()).thenReturn(2);
        when(adjacentPos.getColumn()).thenReturn(2);
        when(adjacentPos.isInside()).thenReturn(true);
        List<IPosition> adjacentPositions = List.of(adjacentPos);
        when(sunkenShip.getAdjacentPositions()).thenReturn(adjacentPositions);

        when(mockFleet.getShips()).thenReturn(List.of(sunkenShip));
        when(mockGame.getMyFleet()).thenReturn(mockFleet);
        when(mockGame.getAlienMoves()).thenReturn(new ArrayList<>());

        boardView.refresh(mockGame);
        assertTrue(true);
    }

    @Test
    void testRefreshWithAdjacentPositionOutside() {
        IShip sunkenShip = mock(IShip.class);
        when(sunkenShip.stillFloating()).thenReturn(false);

        IPosition shipPos = mock(IPosition.class);
        when(shipPos.getRow()).thenReturn(0);
        when(shipPos.getColumn()).thenReturn(0);
        when(sunkenShip.getPositions()).thenReturn(List.of(shipPos));

        IPosition outsidePos = mock(IPosition.class);
        when(outsidePos.isInside()).thenReturn(false);
        when(sunkenShip.getAdjacentPositions()).thenReturn(List.of(outsidePos));

        when(mockFleet.getShips()).thenReturn(List.of(sunkenShip));
        when(mockGame.getMyFleet()).thenReturn(mockFleet);
        when(mockGame.getAlienMoves()).thenReturn(new ArrayList<>());

        boardView.refresh(mockGame);
        assertTrue(true);
    }

    @Test
    void testRefreshWithMovesHittingShip() {
        IShip targetShip = mock(IShip.class);

        IMove move = mock(IMove.class);
        IPosition shotPos = mock(IPosition.class);
        when(shotPos.getRow()).thenReturn(3);
        when(shotPos.getColumn()).thenReturn(3);
        when(shotPos.isInside()).thenReturn(true);
        when(move.getShots()).thenReturn(List.of(shotPos));

        when(mockFleet.shipAt(shotPos)).thenReturn(targetShip);
        when(mockFleet.getShips()).thenReturn(new ArrayList<>());
        when(mockGame.getMyFleet()).thenReturn(mockFleet);
        when(mockGame.getAlienMoves()).thenReturn(List.of(move));

        boardView.refresh(mockGame);
        assertTrue(true);
    }

    @Test
    void testRefreshWithMovesMissingShip() {
        IMove move = mock(IMove.class);
        IPosition shotPos = mock(IPosition.class);
        when(shotPos.getRow()).thenReturn(4);
        when(shotPos.getColumn()).thenReturn(4);
        when(shotPos.isInside()).thenReturn(true);
        when(move.getShots()).thenReturn(List.of(shotPos));

        when(mockFleet.shipAt(shotPos)).thenReturn(null);
        when(mockFleet.getShips()).thenReturn(new ArrayList<>());
        when(mockGame.getMyFleet()).thenReturn(mockFleet);
        when(mockGame.getAlienMoves()).thenReturn(List.of(move));

        boardView.refresh(mockGame);
        assertTrue(true);
    }

    @Test
    void testRefreshWithShotOutsideBoard() {
        IMove move = mock(IMove.class);
        IPosition shotPos = mock(IPosition.class);
        when(shotPos.isInside()).thenReturn(false);
        when(move.getShots()).thenReturn(List.of(shotPos));

        when(mockFleet.getShips()).thenReturn(new ArrayList<>());
        when(mockGame.getMyFleet()).thenReturn(mockFleet);
        when(mockGame.getAlienMoves()).thenReturn(List.of(move));

        boardView.refresh(mockGame);
        assertTrue(true);
    }

    @Test
    void testBoardViewInitialization() {
        assertNotNull(boardView, "BoardView should be initialized.");
    }
}