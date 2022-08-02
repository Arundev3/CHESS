/**
 * CPSC-24500 Object Oriented Programming || Final Project || CHESS
 * This class is Special Rules Class and is used to store boolean values to 
 * determine when to activate special rules like Check, EnPassant, Castle and 
 * Pawn Promotion. It also has display windows to select pieces for pawn promotion 
 * and to indicate CheckMate & winner of the game. 
 * @author Arundev Chandrasekaram
 * @version 1.8.0_241
 * @date 12/17/2021
 */
package Final_Project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SpecialRules {
	
	//Castle
	public static boolean kingMovedC1 = false;
	public static boolean leftRookMovedC1 = false;
	public static boolean rightRookMovedC1 = false;
	
	public static boolean kingMovedC2 = false;
	public static boolean leftRookMovedC2 = false;
	public static boolean rightRookMovedC2 = false;
	
	//EnPassant
	public static boolean enPassantReady = false;
	
	//Check
	public static boolean CheckC1 = false;
	public static boolean CheckC2 = false;


	//CheckMate For White Piece
	public static void checkMateDisplayC1()  {
		//Scene
		Scene victory;
		
		//Text
		Label label1 = new Label("CHECKMATE!!!" + "\n" + "Player 2 Won!");
		label1.setFont(new Font("Ariel", 40));
		VBox endLayout = new VBox(20);
		endLayout.getChildren().add(label1);
		endLayout.setAlignment(Pos.CENTER);
		victory = new Scene(endLayout, 500, 300);

		//Window
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("THE END");
		window.setScene(victory);
		window.showAndWait();
		
	}
	
	//CheckMate For Black Piece
	public static void checkMateDisplayC2()  {
		//Scene
		Scene victory;
		
		//Text
		Label label1 = new Label("CHECKMATE!!!" + "\n" + "Player 1 Won!");
		label1.setFont(new Font("Ariel", 40));
		VBox endLayout = new VBox(20);
		endLayout.getChildren().add(label1);
		endLayout.setAlignment(Pos.CENTER);
		victory = new Scene(endLayout, 500, 300);

		//Window
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("THE END");
		window.setScene(victory);
		window.showAndWait();
		
	}
	
	//Pawn Promotion For White Piece
	public static void pawnPromotionC1(int y, int x)  {
		//Scene
		Scene promotion;
		
		//Stage
		Stage window = new Stage();
		
		//Text
		Label label1 = new Label("Please Select A Piece To Promote");
		label1.setFont(new Font("Ariel", 40));
		Button rook = new Button("Rook");
		rook.setFont(new Font("Ariel", 16));
		rook.setOnAction(e -> {
			try {
				Board.squares[y][x].setPiece(new Rook("Rook", "Color1", x, y, new Image(new FileInputStream
						("src\\Final_Project\\Pieces\\"
								+ "w_rook_png_shadow_1024px.png"))));
				Board.pieceGroup.getChildren().add(Board.squares[y][x].getPiece());
				window.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		Button knight = new Button("Knight");
		knight.setFont(new Font("Ariel", 16));
		knight.setOnAction(e -> {
			try {
				Board.squares[y][x].setPiece(new Knight("Knight", "Color1", x, y, new Image(new FileInputStream
						("src\\Final_Project\\Pieces\\"
								+ "w_knight_png_shadow_1024px.png"))));
				Board.pieceGroup.getChildren().add(Board.squares[y][x].getPiece());
				window.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		Button bishop = new Button("Bishop");
		bishop.setFont(new Font("Ariel", 16));
		bishop.setOnAction(e -> {
			try {
				Board.squares[y][x].setPiece(new Bishop("Bishop", "Color1", x, y, new Image(new FileInputStream
						("src\\Final_Project\\Pieces\\"
								+ "w_bishop_png_shadow_1024px.png"))));
				Board.pieceGroup.getChildren().add(Board.squares[y][x].getPiece());
				window.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		Button queen = new Button("Queen");
		queen.setFont(new Font("Ariel", 16));
		queen.setOnAction(e -> {
			try {
				Board.squares[y][x].setPiece(new Queen("Queen", "Color1", x, y, new Image(new FileInputStream
						("src\\Final_Project\\Pieces\\"
								+ "w_queen_png_shadow_1024px.png"))));
				Board.pieceGroup.getChildren().add(Board.squares[y][x].getPiece());
				window.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		HBox pieces = new HBox(40);
		pieces.getChildren().addAll(rook, knight, bishop, queen);
		VBox layout = new VBox(20);
		layout.getChildren().addAll(label1, pieces);
		layout.setAlignment(Pos.CENTER);
		promotion = new Scene(layout, 600, 300);

		//Window
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Pawn Promotion");
		window.setScene(promotion);
		window.showAndWait();
	}
	
	//Pawn Promotion For Black Piece
	public static void pawnPromotionC2(int y, int x)  {
		//Scene
		Scene promotion;
		
		//Stage
		Stage window = new Stage();
		
		//Text
		Label label1 = new Label("Please Select A Piece To Promote");
		label1.setFont(new Font("Ariel", 40));
		Button rook = new Button("Rook");
		rook.setFont(new Font("Ariel", 16));
		rook.setOnAction(e -> {
			try {
				Board.squares[y][x].setPiece(new Rook("Rook", "Color2", x, y, new Image(new FileInputStream
						("src\\Final_Project\\Pieces\\"
								+ "b_rook_png_shadow_1024px.png"))));
				Board.pieceGroup.getChildren().add(Board.squares[y][x].getPiece());
				window.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		Button knight = new Button("Knight");
		knight.setFont(new Font("Ariel", 16));
		knight.setOnAction(e -> {
			try {
				Board.squares[y][x].setPiece(new Knight("Knight", "Color2", x, y, new Image(new FileInputStream
						("src\\Final_Project\\Pieces\\"
								+ "b_knight_png_shadow_1024px.png"))));
				Board.pieceGroup.getChildren().add(Board.squares[y][x].getPiece());
				window.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		Button bishop = new Button("Bishop");
		bishop.setFont(new Font("Ariel", 16));
		bishop.setOnAction(e -> {
			try {
				Board.squares[y][x].setPiece(new Bishop("Bishop", "Color2", x, y, new Image(new FileInputStream
						("src\\Final_Project\\Pieces\\"
								+ "b_bishop_png_shadow_1024px.png"))));
				Board.pieceGroup.getChildren().add(Board.squares[y][x].getPiece());
				window.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		Button queen = new Button("Queen");
		queen.setFont(new Font("Ariel", 16));
		queen.setOnAction(e -> {
			try {
				Board.squares[y][x].setPiece(new Queen("Queen", "Color2", x, y, new Image(new FileInputStream
						("src\\Final_Project\\Pieces\\"
								+ "b_queen_png_shadow_1024px.png"))));
				Board.pieceGroup.getChildren().add(Board.squares[y][x].getPiece());
				window.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		HBox pieces = new HBox(40);
		pieces.getChildren().addAll(rook, knight, bishop, queen);
		VBox layout = new VBox(20);
		layout.getChildren().addAll(label1, pieces);
		layout.setAlignment(Pos.CENTER);
		promotion = new Scene(layout, 600, 300);

		//Window
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Pawn Promotion");
		window.setScene(promotion);
		window.showAndWait();
	}
}
