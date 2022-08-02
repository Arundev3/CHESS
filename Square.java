/**
 * CPSC-24500 Object Oriented Programming || Final Project || CHESS
 * This class is the Square Class. Used to create tiles for the board.
 * Extends Rectangle class to create similar object and relocates it to 
 * a specific x, y coordinate. 
 * @author Arundev Chandrasekaram
 * @version 1.8.0_241
 * @date 12/17/2021
 */
package Final_Project;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Square extends Rectangle {

	//Fields
    Piece piece;
	
	public Square() {
	}
	
	public Square(boolean light, int x, int y) {
		setWidth(Board.TILE_SIZE);
		setHeight(Board.TILE_SIZE);
	
		relocate(x * Board.TILE_SIZE, y * Board.TILE_SIZE);	
		setFill(light ? Color.valueOf("black") : Color.valueOf("white"));
	}
	
	public boolean hasPiece() {
		return piece != null;
	}

    //Getters And Setters
	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}
}
