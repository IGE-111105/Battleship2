package battleship;

import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Game.
 * Author: britoeabreu
 * Date: 2024-03-19
 * Time: 15:30
 * Cyclomatic Complexity for each method:
 * - Game (constructor): 1
 * - fire: 7
 * - getShots: 1
 * - getRepeatedShots: 1
 * - getInvalidShots: 1
 * - getHits: 1
 * - getSunkShips: 1
 * - getRemainingShips: 1
 * - validShot: 3
 * - repeatedShot: 2
 * - printBoard: 1
 * - printValidShots: 1
 * - printFleet: 1
 */
public class GameTest {

	private Game game;

	@BeforeEach
	void setUp() {
		game = new Game(new Fleet()); // Assuming Fleet is a concrete implementation of IFleet
	}

	@AfterEach
	void tearDown() {
		game = null;
	}

	@Test
	void constructor() {
		assertNotNull(game, "Game instance should not be null after construction.");
		assertNotNull(game.getAlienMoves(), "Shots list should not be null after initialization.");
		assertTrue(game.getAlienMoves().isEmpty(), "Shots list should be empty upon initialization.");
		assertEquals(0, game.getInvalidShots(), "Invalid shots count should be zero upon initialization.");
		assertEquals(0, game.getRepeatedShots(), "Repeated shots count should be zero upon initialization.");
		assertEquals(0, game.getHits(), "Hits count should be zero upon initialization.");
		assertEquals(0, game.getSunkShips(), "Sunk ships count should be zero upon initialization.");
	}

	@Test
	void fire2() {
		Position invalidPosition = new Position(-1, 5);
		game.fireSingleShot(invalidPosition, false);
		assertEquals(1, game.getInvalidShots(), "Invalid shots counter should increase for an invalid shot.");
	}

	@Test
	void fire3() {
		Position position = new Position(2, 3);
		game.fireSingleShot(position, false);
		game.fireSingleShot(position, true);
		assertEquals(1, game.getRepeatedShots(), "Repeated shots counter should increase for a repeated shot.");
	}

	@Test
	void repeatedShot1() {
		List<IPosition> positions = List.of(new Position(2, 3), new Position(2, 4), new Position(2, 5));
		game.fireShots(positions);
		Position position = new Position(2, 3);
		assertTrue(game.repeatedShot(position), "Position (2,3) should be marked as repeated after firing.");
	}

	@Test
	void repeatedShot2() {
		Position position = new Position(2, 3);
		assertFalse(game.repeatedShot(position), "Position (2,3) should not be marked as repeated before firing.");
	}

	@Test
	void getAlienMoves() {
		List<IPosition> positions = List.of(new Position(2, 3), new Position(2, 4), new Position(2, 5));
		game.fireShots(positions);
		assertEquals(1, game.getAlienMoves().size(), "Shots list should contain one shot after firing once.");
	}

	@Test
	void getRemainingShips() {
		IFleet fleet = game.getMyFleet();
		Ship ship1 = new Barge(Compass.NORTH, new Position(1, 1));
		Ship ship2 = new Frigate(Compass.EAST, new Position(5, 5));

		fleet.addShip(ship1);
		assertEquals(1, game.getRemainingShips(), "Just one ship was created!");
		fleet.addShip(ship2);
		assertEquals(2, game.getRemainingShips(), "Two ships were created!");
		ship2.sink();
		assertEquals(1, game.getRemainingShips(), "Remaining ships count should be 1 after sinking one of two ships.");
	}
	@Test
	void fireShotsWithWrongNumberOfShots() {
		List<IPosition> positions = List.of(new Position(2, 3), new Position(2, 4));

		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> game.fireShots(positions),
				"Erro: esperava-se uma exceção quando o número de tiros é diferente de 3."
		);

		assertEquals("Must fire exactly 3 shots per move.", exception.getMessage(),
				"Erro: a mensagem da exceção não corresponde ao erro esperado.");
	}

	@Test
	void getTotalMovesAndTotalShotsAfterOneMove() {
		List<IPosition> positions = List.of(new Position(2, 3), new Position(2, 4), new Position(2, 5));

		game.fireShots(positions);

		assertAll("Validação dos totais após uma jogada",
				() -> assertEquals(1, game.getTotalMoves(),
						"Erro: esperava-se 1 jogada registada após disparar uma vez."),
				() -> assertEquals(3, game.getTotalShots(),
						"Erro: esperava-se um total de 3 tiros após uma jogada.")
		);
	}

	@Test
	void fireSingleShotShouldRegisterHitAndSink() {
		IFleet fleet = game.getMyFleet();
		Ship ship = new Barge(Compass.NORTH, new Position(1, 1));
		fleet.addShip(ship);

		game.fireSingleShot(new Position(1, 1), false);

		assertAll("Validação de tiro certeiro com afundamento",
				() -> assertEquals(1, game.getHits(),
						"Erro: esperava-se 1 acerto após atingir o navio."),
				() -> assertEquals(1, game.getSunkShips(),
						"Erro: esperava-se 1 navio afundado após atingir uma Barge de tamanho 1."),
				() -> assertEquals(0, game.getRemainingShips(),
						"Erro: esperava-se 0 navios restantes após afundar o único navio.")
		);
	}

	@Test
	void readEnemyFireWithCombinedTokens() {
		Scanner scanner = new Scanner("A1 B2 C3");

		String json = game.readEnemyFire(scanner);

		assertAll("Validação da leitura de tiros no formato combinado",
				() -> assertNotNull(json,
						"Erro: esperava-se que o JSON devolvido não fosse nulo."),
				() -> assertEquals(1, game.getTotalMoves(),
						"Erro: esperava-se 1 jogada registada após ler três tiros."),
				() -> assertEquals(3, game.getTotalShots(),
						"Erro: esperava-se um total de 3 tiros registados."),
				() -> assertTrue(json.contains("row"),
						"Erro: esperava-se que o JSON contivesse o campo row."),
				() -> assertTrue(json.contains("column"),
						"Erro: esperava-se que o JSON contivesse o campo column.")
		);
	}

	@Test
	void readEnemyFireWithSeparatedTokens() {
		Scanner scanner = new Scanner("A 1 B 2 C 3");

		String json = game.readEnemyFire(scanner);

		assertAll("Validação da leitura de tiros no formato separado",
				() -> assertNotNull(json,
						"Erro: esperava-se que o JSON devolvido não fosse nulo."),
				() -> assertEquals(1, game.getAlienMoves().size(),
						"Erro: esperava-se uma jogada registada após ler três posições."),
				() -> assertEquals(3, game.getTotalShots(),
						"Erro: esperava-se que fossem registados 3 tiros.")
		);
	}

	@Test
	void readEnemyFireWithIncompletePosition() {
		Scanner scanner = new Scanner("A B2 C3");

		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> game.readEnemyFire(scanner),
				"Erro: esperava-se exceção quando uma coluna não é seguida por linha."
		);

		assertTrue(exception.getMessage().contains("Posição incompleta"),
				"Erro: esperava-se mensagem de posição incompleta.");
	}

	@Test
	void readEnemyFireWithInsufficientNumberOfShots() {
		Scanner scanner = new Scanner("A1 B2");

		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> game.readEnemyFire(scanner),
				"Erro: esperava-se exceção quando são dadas menos de 3 posições."
		);

		assertEquals("Você deve inserir exatamente 3 posições!", exception.getMessage(),
				"Erro: a mensagem da exceção não corresponde ao número insuficiente de tiros.");
	}

	@Test
	void jsonShotsShouldSerializePositions() {
		List<IPosition> positions = List.of(new Position(0, 0), new Position(1, 1), new Position(2, 2));

		String json = Game.jsonShots(positions);

		assertAll("Validação da serialização JSON dos tiros",
				() -> assertNotNull(json,
						"Erro: esperava-se que o JSON não fosse nulo."),
				() -> assertTrue(json.contains("row"),
						"Erro: esperava-se que o JSON contivesse o campo row."),
				() -> assertTrue(json.contains("column"),
						"Erro: esperava-se que o JSON contivesse o campo column."),
				() -> assertTrue(json.contains("A"),
						"Erro: esperava-se que o JSON contivesse a linha clássica A.")
		);
	}

	@Test
	void getMyMovesShouldBeInitiallyEmpty() {
		assertTrue(game.getMyMoves().isEmpty(),
				"Erro: esperava-se que a lista de jogadas próprias estivesse vazia no início.");
	}
}