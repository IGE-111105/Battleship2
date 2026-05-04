package battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Position.
 * Author: britoeabreu
 * Date: 2024-03-19 15:30
 * Cyclomatic Complexity for each method:
 * - Constructor: 1
 * - getRow: 1
 * - getColumn: 1
 * - isValid: 4
 * - isAdjacentTo: 4
 * - isOccupied: 1
 * - isHit: 1
 * - occupy: 1
 * - shoot: 1
 * - equals: 3
 * - hashCode: 1
 * - toString: 1
 */
public class PositionTest {
	private Position position;

	@BeforeEach
	void setUp() {
		position = new Position(2, 3);
	//	position = new Position('C', 4);
	}

	@AfterEach
	void tearDown() {
		position = null;
	}

	@Test
	void constructor() {
		Position pos = new Position(1, 1);
		assertNotNull(pos, "Failed to create Position: object is null");
		assertEquals(1, pos.getRow(), "Failed to set row: expected 1 but got " + pos.getRow());
		assertEquals(1, pos.getColumn(), "Failed to set column: expected 1 but got " + pos.getColumn());
		assertFalse(pos.isOccupied(), "New position should not be occupied");
		assertFalse(pos.isHit(), "New position should not be hit");
	}

	@Test
	void getRow() {
		assertEquals(2, position.getRow(), "Failed to get row: expected 2 but got " + position.getRow());
	}

	@Test
	void getColumn() {
		assertEquals(3, position.getColumn(), "Failed to get column: expected 3 but got " + position.getColumn());
	}

	@Test
	void getClassicRow() {
		assertEquals('C', position.getClassicRow(), "Failed to get row: expected 2 but got " + position.getRow());
	}

	@Test
	void getClassicColumn() {
		assertEquals(3, position.getColumn(), "Failed to get column: expected 3 but got " + position.getColumn());
	}

	@Test
	void isValid1() {
		position = new Position(0, 0);
		assertTrue(position.isInside(), "Position (0,0) should be valid");
	}

	@Test
	void isValid2() {
		position = new Position(-1, 5);
		assertFalse(position.isInside(), "Position with negative row should be invalid");
	}

	@Test
	void isValid3() {
		position = new Position(5, -1);
		assertFalse(position.isInside(), "Position with negative column should be invalid");
	}

	@Test
	void isValid4() {
		position = new Position(Game.BOARD_SIZE, 5);
		assertFalse(position.isInside(), "Position with row >= BOARD_SIZE should be invalid");
	}

	@Test
	void isValid5() {
		position = new Position(5, Game.BOARD_SIZE);
		assertFalse(position.isInside(), "Position with column >= BOARD_SIZE should be invalid");
	}

	@Test
	void isAdjacentTo1() {
		Position other = new Position(2, 4);
		assertTrue(position.isAdjacentTo(other), "Failed to detect horizontally adjacent position");
	}

	@Test
	void isAdjacentTo2() {
		Position other = new Position(3, 3);
		assertTrue(position.isAdjacentTo(other), "Failed to detect vertically adjacent position");
	}

	@Test
	void isAdjacentTo3() {
		Position other = new Position(3, 4);
		assertTrue(position.isAdjacentTo(other), "Failed to detect diagonally adjacent position");
	}

	@Test
	void isAdjacentTo4() {
		Position other = new Position(4, 5);
		assertFalse(position.isAdjacentTo(other), "Non-adjacent position incorrectly identified as adjacent");
	}

	@Test
	void isAdjacentToWithNull() {
		assertThrows(NullPointerException.class, () -> position.isAdjacentTo(null),
				"isAdjacentTo should throw NullPointerException for null input");
	}

	@Test
	void isOccupied() {
		assertFalse(position.isOccupied(), "New position should not be occupied");
		position.occupy();
		assertTrue(position.isOccupied(), "Position should be occupied after occupy()");
	}

	@Test
	void isHit() {
		assertFalse(position.isHit(), "New position should not be hit");
		position.shoot();
		assertTrue(position.isHit(), "Position should be hit after shoot()");
	}

	@Test
	void equals1() {
		Position same = new Position(2, 3);
		assertTrue(position.equals(same), "Equal positions not identified as equal");
	}

	@Test
	void equals2() {
		assertFalse(position.equals(null), "Position should not equal null");
	}

	@Test
	void equals3() {
		Object other = new Object();
		assertFalse(position.equals(other), "Position should not equal non-Position object");
	}

	@Test
	void equals4() {
		Position other = new Position(2, 4);
		assertFalse(position.equals(other), "Positions with the same row but different column should not be equal");
	}

	@Test
	void equals5() {
		assertTrue(position.equals(position), "A position should be equal to itself");
	}

	@Test
	void hashCodeConsistency() {
		Position same = new Position(2, 3);
		assertEquals(position.hashCode(), same.hashCode(),
				"Hash codes not consistent for equal positions");
	}

	@Test
	void toStringFormat() {
//		String expected = "Row = C, Column = 4";
		String expected = "C4";
		assertEquals(expected, position.toString(),
				"Incorrect string representation: expected '" + expected +
						"' but got '" + position.toString() + "'");
	}
	@Test
	void constructorWithClassicCoordinates() {
		Position pos = new Position('C', 4);

		assertAll("Validação do construtor com coordenadas clássicas",
				() -> assertEquals(2, pos.getRow(),
						"Error: expected classic row C to correspond to row index 2."),
				() -> assertEquals(3, pos.getColumn(),
						"Error: expected classic column 4 to correspond to column index 3."),
				() -> assertEquals('C', pos.getClassicRow(),
						"Error: expected classic row to be C."),
				() -> assertEquals(4, pos.getClassicColumn(),
						"Error: expected classic column to be 4.")
		);
	}

	@Test
	void constructorWithLowercaseClassicRow() {
		Position pos = new Position('c', 4);

		assertAll("Validação do construtor com letra minúscula",
				() -> assertEquals(2, pos.getRow(),
						"Error: expected lowercase classic row c to correspond to row index 2."),
				() -> assertEquals(3, pos.getColumn(),
						"Error: expected classic column 4 to correspond to column index 3.")
		);
	}

	@Test
	void randomPositionShouldBeInsideBoard() {
		Position random = Position.randomPosition();

		assertAll("Validação de posição aleatória",
				() -> assertNotNull(random,
						"Error: expected randomPosition to return a non-null position."),
				() -> assertTrue(random.isInside(),
						"Error: expected random position to be inside the board.")
		);
	}

	@Test
	void adjacentPositionsFromCenterShouldReturnEightPositions() {
		Position center = new Position(5, 5);

		var adjacents = center.adjacentPositions();

		assertAll("Validação das posições adjacentes no centro do tabuleiro",
				() -> assertEquals(8, adjacents.size(),
						"Error: expected a center position to have 8 adjacent positions."),
				() -> assertTrue(adjacents.contains(new Position(4, 5)),
						"Error: expected north adjacent position to be included."),
				() -> assertTrue(adjacents.contains(new Position(5, 6)),
						"Error: expected east adjacent position to be included."),
				() -> assertTrue(adjacents.contains(new Position(6, 5)),
						"Error: expected south adjacent position to be included."),
				() -> assertTrue(adjacents.contains(new Position(5, 4)),
						"Error: expected west adjacent position to be included.")
		);
	}

	@Test
	void adjacentPositionsFromCornerShouldReturnThreePositions() {
		Position corner = new Position(0, 0);

		var adjacents = corner.adjacentPositions();

		assertAll("Validação das posições adjacentes no canto do tabuleiro",
				() -> assertEquals(3, adjacents.size(),
						"Error: expected top-left corner to have only 3 valid adjacent positions."),
				() -> assertTrue(adjacents.contains(new Position(0, 1)),
						"Error: expected east adjacent position to be included."),
				() -> assertTrue(adjacents.contains(new Position(1, 0)),
						"Error: expected south adjacent position to be included."),
				() -> assertTrue(adjacents.contains(new Position(1, 1)),
						"Error: expected diagonal adjacent position to be included.")
		);
	}

	@Test
	void adjacentPositionsFromBorderShouldReturnFivePositions() {
		Position border = new Position(0, 5);

		var adjacents = border.adjacentPositions();

		assertEquals(5, adjacents.size(),
				"Error: expected a non-corner border position to have 5 valid adjacent positions.");
	}
}