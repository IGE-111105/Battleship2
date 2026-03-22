package battleship;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.List;

public class BoardView extends GridPane {

    private static final int CELL_SIZE = 45;
    private static final int SIZE = Game.BOARD_SIZE;
    private final Rectangle[][] cells = new Rectangle[SIZE][SIZE];

    public BoardView() {
        setHgap(2);
        setVgap(2);

        for (int col = 0; col < SIZE; col++)
            add(new Label(String.valueOf(col + 1)), col + 1, 0);
        for (int row = 0; row < SIZE; row++)
            add(new Label(String.valueOf((char) ('A' + row))), 0, row + 1);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(Color.LIGHTBLUE);
                cell.setStroke(Color.GRAY);
                cells[row][col] = cell;
                add(cell, col + 1, row + 1);
            }
        }
    }

    /** Called from the game thread — safely dispatches to the JavaFX thread */
    public void refresh(IGame game) {
        Platform.runLater(() -> update(game.getMyFleet(), game.getAlienMoves()));
    }

    private void update(IFleet fleet, List<IMove> moves) {
        // Reset to water
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                cells[r][c].setFill(Color.LIGHTBLUE);

        // Draw ships
        for (IShip ship : fleet.getShips()) {
            Color color = ship.stillFloating() ? Color.SLATEGRAY : Color.DARKRED;
            for (IPosition pos : ship.getPositions())
                cells[pos.getRow()][pos.getColumn()].setFill(color);
            if (!ship.stillFloating())
                for (IPosition pos : ship.getAdjacentPositions())
                    if (pos.isInside())
                        cells[pos.getRow()][pos.getColumn()].setFill(Color.LIGHTYELLOW);
        }

        // Draw shots on top
        for (IMove move : moves) {
            for (IPosition shot : move.getShots()) {
                if (!shot.isInside()) continue;
                IShip ship = fleet.shipAt(shot);  // null = missed water
                cells[shot.getRow()][shot.getColumn()]
                        .setFill(ship != null ? Color.ORANGERED : Color.WHITE);
            }
        }
    }
}