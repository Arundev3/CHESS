/**
 * CPSC-24500 Object Oriented Programming || Final Project || CHESS
 * This class is the Board Class. Used to create the board for the game.
 * It also arranges the board with default piece arrangement. 
 * a specific x, y coordinate. 
 * @author Arundev Chandrasekaram
 * @version 1.8.0_241
 * @date 12/17/2021
 */
package Final_Project;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

public class Board {

    //Fields
	public static final int TILE_SIZE = 100;
	public static final int WIDTH = 8;
	public static final int HEIGHT = 8;
	public static Square[][] squares = new Square[WIDTH][HEIGHT];
	
	public static Group tileGroup = new Group();
	public static Group pieceGroup = new Group();

    //Constructor
    public Board() {
    }
    
	public Parent createEmptyBoard() {
		GridPane root = new GridPane();
		root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
		root.getChildren().addAll(tileGroup, pieceGroup);
		
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				squares[y][x] = new Square((x + y) % 2 == 0, x, y);
				
				tileGroup.getChildren().add(squares[y][x]);
				
			}
		}
		return root;
	}	
	
	//Set Default Pieces
	public void setDefaultPiece() throws IOException {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {


				if (y < 1) {
					if (x == 0 || x == 7) {
						Rook rook_Team_1 = new Rook("Rook", "Color1", x, y, new Image(new FileInputStream
								("src\\Final_Project\\Pieces\\"
										+ "w_rook_png_shadow_1024px.png")));
						squares[y][x].setPiece(rook_Team_1);
						pieceGroup.getChildren().add(rook_Team_1);
					} else if (x == 1 || x == 6) {
						Knight knight_Team_1 = new Knight("Knight", "Color1", x, y, new Image(new FileInputStream
								("src\\Final_Project\\Pieces\\"
										+ "w_knight_png_shadow_1024px.png")));
						squares[y][x].setPiece(knight_Team_1);
						pieceGroup.getChildren().add(knight_Team_1);
					} else if (x == 2 || x == 5) {
						Bishop bishop_Team_1 = new Bishop("Bishop", "Color1", x, y, new Image(new FileInputStream
								("src\\Final_Project\\Pieces\\"
										+ "w_bishop_png_shadow_1024px.png")));
						squares[y][x].setPiece(bishop_Team_1);
						pieceGroup.getChildren().add(bishop_Team_1);
					} else if (x == 3) {
						Queen queen_Team_1 = new Queen("Queen", "Color1", x, y, new Image(new FileInputStream
								("src\\Final_Project\\Pieces\\"
										+ "w_queen_png_shadow_1024px.png")));
						squares[y][x].setPiece(queen_Team_1);
						pieceGroup.getChildren().add(queen_Team_1);
					} else if (x == 4) {
						King king_Team_1 = new King("King", "Color1", x, y, new Image(new FileInputStream
								("src\\Final_Project\\Pieces\\"
										+ "w_king_png_shadow_1024px.png")));
						squares[y][x].setPiece(king_Team_1);
						pieceGroup.getChildren().add(king_Team_1);
					}
				} else if (y == 1) {
					Pawn pawn_Team_1 = new Pawn("Pawn", "Color1", x, y, new Image(new FileInputStream
							("src\\Final_Project\\Pieces\\"
							+ "w_pawn_png_shadow_1024px.png")));
					squares[y][x].setPiece(pawn_Team_1);
					pieceGroup.getChildren().add(pawn_Team_1);
				} else if (y == 6) {
					Pawn pawn_Team_2 = new Pawn("Pawn", "Color2", x, y, new Image(new FileInputStream
							("src\\Final_Project\\Pieces\\"
							+ "b_pawn_png_shadow_1024px.png")));
					squares[y][x].setPiece(pawn_Team_2);
					pieceGroup.getChildren().add(pawn_Team_2);
				} else if (y > 6) {
					if (x == 0 || x == 7) {
						Rook rook_Team_2 = new Rook("Rook", "Color2", x, y, new Image(new FileInputStream
								("src\\Final_Project\\Pieces\\"
										+ "b_rook_png_shadow_1024px.png")));
						squares[y][x].setPiece(rook_Team_2);
						pieceGroup.getChildren().add(rook_Team_2);
					} else if (x == 1 || x == 6) {
						Knight knight_Team_2 = new Knight("Knight", "Color2", x, y, new Image(new FileInputStream
								("src\\Final_Project\\Pieces\\"
										+ "b_knight_png_shadow_1024px.png")));
						squares[y][x].setPiece(knight_Team_2);
						pieceGroup.getChildren().add(knight_Team_2);
					} else if (x == 2 || x == 5) {
						Bishop bishop_Team_2 = new Bishop("Bishop", "Color2", x, y, new Image(new FileInputStream
								("src\\Final_Project\\Pieces\\"
										+ "b_bishop_png_shadow_1024px.png")));
						squares[y][x].setPiece(bishop_Team_2);
						pieceGroup.getChildren().add(bishop_Team_2);
					} else if (x == 3) {
						Queen queen_Team_2 = new Queen("Queen", "Color2", x, y, new Image(new FileInputStream
								("src\\Final_Project\\Pieces\\"
										+ "b_queen_png_shadow_1024px.png")));
						squares[y][x].setPiece(queen_Team_2);
						pieceGroup.getChildren().add(queen_Team_2);
					} else if (x == 4) {
						King king_Team_2 = new King("King", "Color2", x, y, new Image(new FileInputStream
								("src\\Final_Project\\Pieces\\"
										+ "b_king_png_shadow_1024px.png")));
						squares[y][x].setPiece(king_Team_2);
						pieceGroup.getChildren().add(king_Team_2);
					}
				}
				
			}
		}

	}
	
	//2D Board Check!
//	public static void print() {
//		for(int i = 0; i < 8; i++) {
//			for(int x = 0; x < 8; x++) {
//				if(squares[i][x].getPiece() == null) {
//					System.out.print("---- ");
//				} else {
//					System.out.print(squares[i][x].getPiece().getPieceName() + " ");
//				}
//			}
//			System.out.println();
//		}
//	}
}
