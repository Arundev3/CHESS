/**
 * CPSC-24500 Object Oriented Programming || Final Project || CHESS
 * This class is the Main Class. Used to execute the GUI
 * @author Arundev Chandrasekaram
 * @version 1.8.0_241
 * @date 12/17/2021
 */
package Final_Project;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	

	@Override
	public void start(Stage window) throws Exception {
		
		//Scenes
		Scene menu, player, game;
		
		//Menu
		Label label1 = new Label("Welcome! to CHESS");
		label1.setFont(new Font("Ariel", 24));
		Button play = new Button("Play");
		play.setFont(new Font("Ariel", 30));
		VBox menuLayout = new VBox(20);
		menuLayout.getChildren().addAll(label1, play);
		menuLayout.setAlignment(Pos.CENTER);
		menu = new Scene(menuLayout, 800, 800); 
	
		//Player Selection
//		Button player1 = new Button("Player 1");
//		Button player2 = new Button("Player 2");
//		player1.setFont(new Font("Ariel", 24));
//		player2.setFont(new Font("Ariel", 24));
//		VBox playerLayout = new VBox(20);
//		playerLayout.getChildren().addAll(player1, player2);
//		playerLayout.setAlignment(Pos.CENTER);
//		player = new Scene(playerLayout, 800, 800); 
		
		//Game
		Board board = new Board();
		game = new Scene(board.createEmptyBoard());
		board.setDefaultPiece();
		TakeTurns.whosTurn();
		play.setOnAction(e -> window.setScene(game));
//		player1.setOnAction(e -> window.setScene(game));
//		player2.setOnAction(e -> window.setScene(game));
		
		
		//Default Window
		window.setTitle("CHESS");
		window.setScene(menu);
		window.show();
		
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}

}
