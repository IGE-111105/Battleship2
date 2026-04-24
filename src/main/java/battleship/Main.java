/**
 * 
 */
package battleship;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The type Main.
 *
 * @author britoeabreu
 * @author adrianolopes
 * @author miguelgoulao
 */
public class Main extends Application
{
	static BoardView boardView;

	@Override
	public void start(Stage stage) {
		boardView = new BoardView();

		Tasks.onBoardUpdate = boardView::refresh;  // hook into Tasks

		VBox root = new VBox(10, new Label("My Board"), boardView);
		root.setPadding(new Insets(15));

		stage.setScene(new Scene(root));
		stage.setTitle("Battleship");
		stage.show();

		Thread gameThread = new Thread(Tasks::menu);
		gameThread.setDaemon(true);  // closes with the window
		gameThread.start();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
