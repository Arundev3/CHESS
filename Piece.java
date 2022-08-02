/**
 * CPSC-24500 Object Oriented Programming || Final Project || CHESS
 * This class is the Piece Class. Used as a base class for all other
 * chess pieces.
 * a specific x, y coordinate. 
 * @author Arundev Chandrasekaram
 * @version 1.8.0_241
 * @date 12/17/2021
 */
package Final_Project;

import javafx.scene.layout.StackPane;

public class Piece extends StackPane {
	
	//Fields
    String pieceName;
    String pieceColor;
    protected boolean enPassantDoubleMove = false;
	protected double oldX, oldY;
	protected double mouseX, mouseY;

	
    //Constructors
    public Piece() {
    }
    
	public Piece(String pieceName, String pieceColor) {
		this.pieceName = pieceName;
    	this.pieceColor = pieceColor;
        //this.xCord = x;
        //this.yCord = y;
	}

    
	//Methods
	public double getOldX() {
		return oldX;
	}

	public double getOldY() {
		return oldY;
	}
    
	public void move(int x, int y) {
		oldX = x * Board.TILE_SIZE;
		oldY = y * Board.TILE_SIZE;
		relocate(oldX, oldY);
	}
	
	public void cancelMove() {
		relocate(oldX, oldY);
	}
	
	public void mouse() {
		
	}

	//Getters And Setters
    public String getPiece_color() {
		return pieceColor;
	}

	public void setPieceColor(String pieceColor) {
		this.pieceColor = pieceColor;
	}

    public String getPieceName() {
        return pieceName;
    }

    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }

}

