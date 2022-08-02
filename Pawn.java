/**
 * CPSC-24500 Object Oriented Programming || Final Project || CHESS
 * This class is Pawn Class. Used to create Pawn objects. This class has methods to 
 * set image for object. And uses Convenience methods to register mouse event handlers.
 * The Pawn logic works entirely on mouse release and switch turns.  
 * @author Arundev Chandrasekaram
 * @version 1.8.0_241
 * @date 12/17/2021
 */
package Final_Project;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Pawn extends Piece {
	
	private Image image;
	private String doubleMove = "";
	private String moved = "";
	private int enPassantCount = 0;
	
	//Constructor
	public Pawn(String pieceName, String pieceColor, int x, int y, Image img) {
		super(pieceName, pieceColor);
		this.image = img;
		this.doubleMove = doubleMove;
		this.enPassantCount = enPassantCount;
		super.enPassantDoubleMove = enPassantDoubleMove;
		
		move(x , y);
		
		Rectangle pawn = new Rectangle(Board.TILE_SIZE * 0.80, Board.TILE_SIZE * 0.80);
		pawn.setFill(new ImagePattern(image));
		pawn.setTranslateX(10);
		
		getChildren().addAll(pawn);
		
		mouseCommands();
	}
	
	public void mouseCommands() {
		
		setOnMousePressed(e -> {
			if(TakeTurns.turn.contentEquals("Player1") && this.getPiece_color().contentEquals("Color1") || TakeTurns.turn.contentEquals("Player2") && this.getPiece_color().contentEquals("Color2")) {
			mouseX = e.getSceneX();
			mouseY = e.getSceneY();
			}
		});
		
		setOnMouseDragged(e -> {
			if(TakeTurns.turn.contentEquals("Player1") && this.getPiece_color().contentEquals("Color1") || TakeTurns.turn.contentEquals("Player2") && this.getPiece_color().contentEquals("Color2")) {
				if((e.getSceneX() <= 750 && e.getSceneX() >= 50) && (e.getSceneY() <= 750 && e.getSceneY() >= 50)) {
					relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
				}
			
			}
		});
		
		
		setOnMouseReleased(e -> {
			if(TakeTurns.turn.contentEquals("Player1") && this.getPiece_color().contentEquals("Color1") || TakeTurns.turn.contentEquals("Player2") && this.getPiece_color().contentEquals("Color2")) {
				
					int newX = toBoard(this.getLayoutX());
					int newY = toBoard(this.getLayoutY());
					
					int x0 = toBoard(this.getOldX());
					int y0 = toBoard(this.getOldY());
					 
					//If it's a White Piece
					if (this.getPiece_color().contentEquals("Color1")) {
						//Pawn Promotion Squares
						if (newY == 7 && newX == 0 || newY == 7 && newX == 1 || newY == 7 && newX == 2 || newY == 7 && newX == 3 ||
						    newY == 7 && newX == 4 || newY == 7 && newX == 5 || newY == 7 && newX == 6 || newY == 7 && newX == 7) {
							if (newY == y0 + 1 && newX == x0 - 1 || newY == y0 + 1 && newX == x0 + 1) {
								//Kill Enemy Piece
								if(Board.squares[newY][newX].hasPiece()) {
									if(Board.squares[newY][newX].getPiece().getPiece_color().contentEquals("Color2")) {
										Board.pieceGroup.getChildren().remove(Board.squares[y0][x0].getPiece());
										Board.pieceGroup.getChildren().remove(Board.squares[newY][newX].getPiece());
										Board.squares[y0][x0].setPiece(null);
										SpecialRules.pawnPromotionC1(newY, newX);
										if(TakeTurns.turn.contentEquals("Player1")) {
											TakeTurns.turn = "Player2";
										} else {
											TakeTurns.turn = "Player1";
										}
									}
								} else {
									this.cancelMove();
								}
							//Move
							} else if(Board.squares[newY][newX].hasPiece() == false) {
								Board.pieceGroup.getChildren().remove(Board.squares[y0][x0].getPiece());
								Board.squares[y0][x0].setPiece(null);
								SpecialRules.pawnPromotionC1(newY, newX);
								if(TakeTurns.turn.contentEquals("Player1")) {
									TakeTurns.turn = "Player2";
								} else {
									TakeTurns.turn = "Player1";
								}
							} else {
								this.cancelMove();
							}
						//Normal Move
						} else if (newY == y0 + 1 && newX == x0) {
							if(Board.squares[newY][newX].hasPiece()) {
								this.cancelMove();
							} else {
								this.move(newX, newY);
								Board.squares[y0][x0].setPiece(null);
								Board.squares[newY][newX].setPiece(this);
								doubleMove = "DONE";
								if(TakeTurns.turn.contentEquals("Player1")) {
									TakeTurns.turn = "Player2";
								} else {
									TakeTurns.turn = "Player1";
								}
								SpecialRules.enPassantReady = false;
								enPassantDoubleMove = false;
								enPassantCount++;
								King.Check();
								//Board.print();
							}
						//Double Move
						} else if (newY == y0 + 2 && newX == x0) {
							if(Board.squares[newY][newX].hasPiece()) {
								this.cancelMove();
							} else if(!doubleMove.contentEquals("DONE")) {
								this.move(newX, newY);
								Board.squares[y0][x0].setPiece(null);
								Board.squares[newY][newX].setPiece(this);
								doubleMove = "DONE";
								if(TakeTurns.turn.contentEquals("Player1")) {
									TakeTurns.turn = "Player2";
								} else {
									TakeTurns.turn = "Player1";
								}
								enPassantDoubleMove = true;
								SpecialRules.enPassantReady = true;
								King.Check();
								//Board.print();
							} else {
								this.cancelMove();
							}
						//Diagonal Move
						} else if (newY == y0 + 1 && newX == x0 - 1 || newY == y0 + 1 && newX == x0 + 1) {
							//Kill Enemy Piece
							if(Board.squares[newY][newX].hasPiece()) {
								if(Board.squares[newY][newX].getPiece().getPiece_color().contentEquals("Color2")) { 
									Board.pieceGroup.getChildren().remove(Board.squares[newY][newX].getPiece());
									this.move(newX, newY);
									Board.squares[y0][x0].setPiece(null);
									Board.squares[newY][newX].setPiece(this);
									doubleMove = "DONE";
									if(TakeTurns.turn.contentEquals("Player1")) {
										TakeTurns.turn = "Player2";
									} else {
										TakeTurns.turn = "Player1";
									}
									enPassantDoubleMove = false;
									SpecialRules.enPassantReady = false;
									King.Check();
									//Board.print();
								} else {
									this.cancelMove();
								}
							//EnPassant
							} else if(Board.squares[newY - 1][newX].hasPiece() && Board.squares[newY - 1][newX].getPiece().enPassantDoubleMove && 
									  Board.squares[newY - 1][newX].getPiece().getPiece_color().contentEquals("Color2") && SpecialRules.enPassantReady == true) {
								if(this.enPassantCount == 3) {
									this.move(newX, newY);
									Board.squares[y0][x0].setPiece(null);
									Board.squares[newY][newX].setPiece(this);
									Board.pieceGroup.getChildren().remove(Board.squares[newY - 1][newX].getPiece());
									Board.squares[newY - 1][newX].setPiece(null);
									if(TakeTurns.turn.contentEquals("Player1")) {
										TakeTurns.turn = "Player2";
									} else {
										TakeTurns.turn = "Player1";
									}
									SpecialRules.enPassantReady = false;
									King.Check();
								}
							} else {
								this.cancelMove();
							}
						} else {
							this.cancelMove();
						}
					//If it's a Black Piece
					} else {
						//Pawn Promotion Squares
						if (newY == 0 && newX == 0 || newY == 0 && newX == 1 || newY == 0 && newX == 2 || newY == 0 && newX == 3 ||
							newY == 0 && newX == 4 || newY == 0 && newX == 5 || newY == 0 && newX == 6 || newY == 0 && newX == 7) {
							if (newY == y0 - 1 && newX == x0 - 1 || newY == y0 - 1 && newX == x0 + 1) {
								//Kill Enemy Piece
								if(Board.squares[newY][newX].hasPiece()) {
									if(Board.squares[newY][newX].getPiece().getPiece_color().contentEquals("Color1")) {			
										Board.pieceGroup.getChildren().remove(Board.squares[y0][x0].getPiece());
										Board.pieceGroup.getChildren().remove(Board.squares[newY][newX].getPiece());
										Board.squares[y0][x0].setPiece(null);
										SpecialRules.pawnPromotionC2(newY, newX);
										if(TakeTurns.turn.contentEquals("Player1")) {
											TakeTurns.turn = "Player2";
										} else {
											TakeTurns.turn = "Player1";
										}
									}
								} else {
									this.cancelMove();
								}
							//Move
							} else if(Board.squares[newY][newX].hasPiece() == false) {
								Board.pieceGroup.getChildren().remove(Board.squares[y0][x0].getPiece());
								Board.squares[y0][x0].setPiece(null);
								SpecialRules.pawnPromotionC2(newY, newX);
								if(TakeTurns.turn.contentEquals("Player1")) {
									TakeTurns.turn = "Player2";
								} else {
									TakeTurns.turn = "Player1";
								}
							} else {
								this.cancelMove();
							}
						//Normal Move
						} else if (newY == y0 - 1 && newX == x0) {
							if(Board.squares[newY][newX].hasPiece()) {
								this.cancelMove();
							} else {
								this.move(newX, newY);
								Board.squares[y0][x0].setPiece(null);
								Board.squares[newY][newX].setPiece(this);
								doubleMove = "DONE";
								if(TakeTurns.turn.contentEquals("Player1")) {
									TakeTurns.turn = "Player2";
								} else {
									TakeTurns.turn = "Player1";
								}
								SpecialRules.enPassantReady = false;
								enPassantDoubleMove = false;
								enPassantCount++;
								King.Check();
								//Board.print();
							}
						//Double Move
						} else if (newY == y0 - 2 && newX == x0) {
							if(Board.squares[newY][newX].hasPiece()) {
								this.cancelMove();
							} else if(!doubleMove.contentEquals("DONE")) {
								this.move(newX, newY);
								Board.squares[y0][x0].setPiece(null);
								Board.squares[newY][newX].setPiece(this);
								doubleMove = "DONE";
								if(TakeTurns.turn.contentEquals("Player1")) {
									TakeTurns.turn = "Player2";
								} else {
									TakeTurns.turn = "Player1";
								}
								enPassantDoubleMove = true;
								SpecialRules.enPassantReady = true;
								King.Check();
								//Board.print();
							} else {
								this.cancelMove();
							}
						//Diagonal Move
						} else if (newY == y0 - 1 && newX == x0 - 1 || newY == y0 - 1 && newX == x0 + 1) {
							//Kill Enemy Piece
							if(Board.squares[newY][newX].hasPiece()) {
								if(Board.squares[newY][newX].getPiece().getPiece_color().contentEquals("Color1")) {
									Board.pieceGroup.getChildren().remove(Board.squares[newY][newX].getPiece());
									this.move(newX, newY);
									Board.squares[y0][x0].setPiece(null);
									Board.squares[newY][newX].setPiece(this);
									doubleMove = "DONE";
									if(TakeTurns.turn.contentEquals("Player1")) {
										TakeTurns.turn = "Player2";
									} else {
										TakeTurns.turn = "Player1";
									}
									enPassantDoubleMove = false;
									SpecialRules.enPassantReady = false;
									King.Check();
									//Board.print();
								} else {
									this.cancelMove();
								}
							//EnPassant
							} else if(Board.squares[newY + 1][newX].hasPiece() && Board.squares[newY + 1][newX].getPiece().enPassantDoubleMove && 
									  Board.squares[newY + 1][newX].getPiece().getPiece_color().contentEquals("Color1") && SpecialRules.enPassantReady == true) {
								if(this.enPassantCount == 3) {
									this.move(newX, newY);
									Board.squares[y0][x0].setPiece(null);
									Board.squares[newY][newX].setPiece(this);
									Board.pieceGroup.getChildren().remove(Board.squares[newY + 1][newX].getPiece());
									Board.squares[newY + 1][newX].setPiece(null);
									if(TakeTurns.turn.contentEquals("Player1")) {
										TakeTurns.turn = "Player2";
									} else {
										TakeTurns.turn = "Player1";
									}
									SpecialRules.enPassantReady = false;
									King.Check();
								}
							} else {
								this.cancelMove();
							}
						} else {
							this.cancelMove();
						}
					}
			}
		});
	}
	
	private int toBoard(double pixel) {
		return (int)(pixel + Board.TILE_SIZE / 2) / Board.TILE_SIZE;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
