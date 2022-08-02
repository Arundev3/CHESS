/**
 * CPSC-24500 Object Oriented Programming || Final Project || CHESS
 * This class is Rook Class. Used to create Rook objects. This class has methods to 
 * set image for object. And uses Convenience methods to register mouse event handlers.
 * The Rook logic works entirely on mouse release and switch turns.  
 * @author Arundev Chandrasekaram
 * @version 1.8.0_241
 * @date 12/17/2021
 */
package Final_Project;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Rook extends Piece {
	
	private Image image;
	
	//Constructor
	public Rook(String pieceName, String pieceColor, int x, int y, Image img) {
		super(pieceName, pieceColor);
		this.image = img;
		
		move(x , y);
		
		Rectangle rook = new Rectangle(Board.TILE_SIZE * 0.80, Board.TILE_SIZE * 0.80);
		rook.setFill(new ImagePattern(image));
		rook.setTranslateX(10);
		
		getChildren().addAll(rook);
		
		mouseCommands();
	}
	
	private void mouseCommands() {
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
				
				//Cancel Move if the played piece and target piece belong to the same color.
				if(this.getPiece_color().contentEquals("Color1") && Board.squares[newY][newX].hasPiece() && Board.squares[newY][newX].getPiece().getPiece_color().contentEquals("Color1")) {
					this.cancelMove();
				} else if (this.getPiece_color().contentEquals("Color2") && Board.squares[newY][newX].hasPiece() && Board.squares[newY][newX].getPiece().getPiece_color().contentEquals("Color2")) {
					this.cancelMove();
				//The logic works similar for both colored pieces.
				} else {
					//Check Vertical
					if(newX == x0) {
						//Check Down
						if(newY > y0) {
							int tempY = y0 + 1;
							boolean pathBlock = false;
							while(tempY != newY) {
								if(Board.squares[tempY][x0].hasPiece()) {
									pathBlock = true;
									break;
								}
								tempY++;
							} 
							//Move
							if(!Board.squares[newY][newX].hasPiece()) {
								if(pathBlock == false) {
									this.move(newX, newY);
									Board.squares[y0][x0].setPiece(null);
									Board.squares[newY][newX].setPiece(this);
									if(TakeTurns.turn.contentEquals("Player1")) {
										TakeTurns.turn = "Player2";
									} else {
										TakeTurns.turn = "Player1";
									}
									//Castle
									if(this.getPiece_color().contains("Color1") && this == Board.squares[0][0].getPiece()) {
										SpecialRules.leftRookMovedC1 = true;
									} else if (this.getPiece_color().contains("Color1") && this == Board.squares[0][7].getPiece()) {
										SpecialRules.rightRookMovedC1 = true;
									} else if(this.getPiece_color().contains("Color2") && this == Board.squares[7][0].getPiece()) {
										SpecialRules.leftRookMovedC2 = true;
									} else if (this.getPiece_color().contains("Color2") && this == Board.squares[7][7].getPiece()) {
										SpecialRules.rightRookMovedC2 = true;
									}
									King.Check();
								} else {
									this.cancelMove();
								}
							//Kill Enemy Piece
							} else {
								if(pathBlock == false) {
									Board.pieceGroup.getChildren().remove(Board.squares[newY][newX].getPiece());
									this.move(newX, newY);
									Board.squares[y0][x0].setPiece(null);
									Board.squares[newY][newX].setPiece(this);
									if(TakeTurns.turn.contentEquals("Player1")) {
										TakeTurns.turn = "Player2";
									} else {
										TakeTurns.turn = "Player1";
									}
									King.Check();
									//Castle
									if(this.getPiece_color().contains("Color1") && this == Board.squares[0][0].getPiece()) {
										SpecialRules.leftRookMovedC1 = true;
									} else if (this.getPiece_color().contains("Color1") && this == Board.squares[0][7].getPiece()) {
										SpecialRules.rightRookMovedC1 = true;
									} else if(this.getPiece_color().contains("Color2") && this == Board.squares[7][0].getPiece()) {
										SpecialRules.leftRookMovedC2 = true;
									} else if (this.getPiece_color().contains("Color2") && this == Board.squares[7][7].getPiece()) {
										SpecialRules.rightRookMovedC2 = true;
									}
								} else {
									this.cancelMove();
								}
							}
						//Check Up
						} else if (newY < y0) {
							int tempY = y0 - 1;
							boolean pathBlock = false;
							while(tempY != newY) {
								if(Board.squares[tempY][x0].hasPiece()) {
									pathBlock = true;
									break;
								}
								tempY--;
							} 
							//Move
							if(!Board.squares[newY][newX].hasPiece()) {
								if(pathBlock == false) {
									this.move(newX, newY);
									Board.squares[y0][x0].setPiece(null);
									Board.squares[newY][newX].setPiece(this);
									if(TakeTurns.turn.contentEquals("Player1")) {
										TakeTurns.turn = "Player2";
									} else {
										TakeTurns.turn = "Player1";
									}
									King.Check();
									//Castle
									if(this.getPiece_color().contains("Color1") && this == Board.squares[0][0].getPiece()) {
										SpecialRules.leftRookMovedC1 = true;
									} else if (this.getPiece_color().contains("Color1") && this == Board.squares[0][7].getPiece()) {
										SpecialRules.rightRookMovedC1 = true;
									} else if(this.getPiece_color().contains("Color2") && this == Board.squares[7][0].getPiece()) {
										SpecialRules.leftRookMovedC2 = true;
									} else if (this.getPiece_color().contains("Color2") && this == Board.squares[7][7].getPiece()) {
										SpecialRules.rightRookMovedC2 = true;
									}
									
								} else {
									this.cancelMove();
								}
							//Kill Enemy Piece
							} else {
								if(pathBlock == false) {
									Board.pieceGroup.getChildren().remove(Board.squares[newY][newX].getPiece());
									this.move(newX, newY);
									Board.squares[y0][x0].setPiece(null);
									Board.squares[newY][newX].setPiece(this);
									if(TakeTurns.turn.contentEquals("Player1")) {
										TakeTurns.turn = "Player2";
									} else {
										TakeTurns.turn = "Player1";
									}
									King.Check();
									//Castle
									if(this.getPiece_color().contains("Color1") && this == Board.squares[0][0].getPiece()) {
										SpecialRules.leftRookMovedC1 = true;
									} else if (this.getPiece_color().contains("Color1") && this == Board.squares[0][7].getPiece()) {
										SpecialRules.rightRookMovedC1 = true;
									} else if(this.getPiece_color().contains("Color2") && this == Board.squares[7][0].getPiece()) {
										SpecialRules.leftRookMovedC2 = true;
									} else if (this.getPiece_color().contains("Color2") && this == Board.squares[7][7].getPiece()) {
										SpecialRules.rightRookMovedC2 = true;
									}
									
								} else {
									this.cancelMove();
								}
							}
						} else {
							this.cancelMove();
						}
					//Check Horizontal
					} else if (newY == y0) {
						//Check To The Right
						if(newX > x0) {
							int tempX = x0 + 1;
							boolean pathBlock = false;
							while(tempX != newX) {
								if(Board.squares[y0][tempX].hasPiece()) {
									pathBlock = true;
									break;
								}
								tempX++;
							} 
							//Move
							if(!Board.squares[newY][newX].hasPiece()) {
								if(pathBlock == false) {
									this.move(newX, newY);
									Board.squares[y0][x0].setPiece(null);
									Board.squares[newY][newX].setPiece(this);
									if(TakeTurns.turn.contentEquals("Player1")) {
										TakeTurns.turn = "Player2";
									} else {
										TakeTurns.turn = "Player1";
									}
									King.Check();
									//Castle
									if(this.getPiece_color().contains("Color1") && this == Board.squares[0][0].getPiece()) {
										SpecialRules.leftRookMovedC1 = true;
									} else if (this.getPiece_color().contains("Color1") && this == Board.squares[0][7].getPiece()) {
										SpecialRules.rightRookMovedC1 = true;
									} else if(this.getPiece_color().contains("Color2") && this == Board.squares[7][0].getPiece()) {
										SpecialRules.leftRookMovedC2 = true;
									} else if (this.getPiece_color().contains("Color2") && this == Board.squares[7][7].getPiece()) {
										SpecialRules.rightRookMovedC2 = true;
									}
								
								} else {
									this.cancelMove();
								}
							//Kill Enemy Piece
							} else {
								if(pathBlock == false) {
									Board.pieceGroup.getChildren().remove(Board.squares[newY][newX].getPiece());
									this.move(newX, newY);
									Board.squares[y0][x0].setPiece(null);
									Board.squares[newY][newX].setPiece(this);
									if(TakeTurns.turn.contentEquals("Player1")) {
										TakeTurns.turn = "Player2";
									} else {
										TakeTurns.turn = "Player1";
									}
									King.Check();
									//Castle
									if(this.getPiece_color().contains("Color1") && this == Board.squares[0][0].getPiece()) {
										SpecialRules.leftRookMovedC1 = true;
									} else if (this.getPiece_color().contains("Color1") && this == Board.squares[0][7].getPiece()) {
										SpecialRules.rightRookMovedC1 = true;
									} else if(this.getPiece_color().contains("Color2") && this == Board.squares[7][0].getPiece()) {
										SpecialRules.leftRookMovedC2 = true;
									} else if (this.getPiece_color().contains("Color2") && this == Board.squares[7][7].getPiece()) {
										SpecialRules.rightRookMovedC2 = true;
									}
							
								} else {
									this.cancelMove();
								}
							}
						//Check To The Left
						} else if (newX < x0) {
							int tempX = x0 - 1;
							boolean pathBlock = false;
							while(tempX != newX) {
								if(Board.squares[y0][tempX].hasPiece()) {
									pathBlock = true;
									break;
								}
								tempX--;
							} 
							//Move
							if(!Board.squares[newY][newX].hasPiece()) {
								if(pathBlock == false) {
									this.move(newX, newY);
									Board.squares[y0][x0].setPiece(null);
									Board.squares[newY][newX].setPiece(this);
									if(TakeTurns.turn.contentEquals("Player1")) {
										TakeTurns.turn = "Player2";
									} else {
										TakeTurns.turn = "Player1";
									}
									King.Check();
									//Castle
									if(this.getPiece_color().contains("Color1") && this == Board.squares[0][0].getPiece()) {
										SpecialRules.leftRookMovedC1 = true;
									} else if (this.getPiece_color().contains("Color1") && this == Board.squares[0][7].getPiece()) {
										SpecialRules.rightRookMovedC1 = true;
									} else if(this.getPiece_color().contains("Color2") && this == Board.squares[7][0].getPiece()) {
										SpecialRules.leftRookMovedC2 = true;
									} else if (this.getPiece_color().contains("Color2") && this == Board.squares[7][7].getPiece()) {
										SpecialRules.rightRookMovedC2 = true;
									}
									
								} else {
									this.cancelMove();
								}
							//Kill Enemy Piece
							} else {
								if(pathBlock == false) {
									Board.pieceGroup.getChildren().remove(Board.squares[newY][newX].getPiece());
									this.move(newX, newY);
									Board.squares[y0][x0].setPiece(null);
									Board.squares[newY][newX].setPiece(this);
									if(TakeTurns.turn.contentEquals("Player1")) {
										TakeTurns.turn = "Player2";
									} else {
										TakeTurns.turn = "Player1";
									}
									King.Check();
									//Castle
									if(this.getPiece_color().contains("Color1") && this == Board.squares[0][0].getPiece()) {
										SpecialRules.leftRookMovedC1 = true;
									} else if (this.getPiece_color().contains("Color1") && this == Board.squares[0][7].getPiece()) {
										SpecialRules.rightRookMovedC1 = true;
									} else if(this.getPiece_color().contains("Color2") && this == Board.squares[7][0].getPiece()) {
										SpecialRules.leftRookMovedC2 = true;
									} else if (this.getPiece_color().contains("Color2") && this == Board.squares[7][7].getPiece()) {
										SpecialRules.rightRookMovedC2 = true;
									}
							
								} else {
									this.cancelMove();
								}
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
