/**
 * CPSC-24500 Object Oriented Programming || Final Project || CHESS
 * This class is King Class. Used to create King objects. This class has methods to 
 * set image for object. And uses Convenience methods to register mouse event handlers.
 * The King logic works entirely on mouse release and switch turns.  
 * @author Arundev Chandrasekaram
 * @version 1.8.0_241
 * @date 12/17/2021
 */
package Final_Project;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class King extends Piece {

	private Image image;
	
	//Constructor
	public King(String pieceName, String pieceColor, int x, int y, Image img) {
		super(pieceName, pieceColor);
		this.image = img;
		
		move(x , y);
		
		Rectangle king = new Rectangle(Board.TILE_SIZE * 0.80, Board.TILE_SIZE * 0.80);
		king.setFill(new ImagePattern(image));
		king.setTranslateX(10);
		
		getChildren().addAll(king);
		
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
					//Check The 8 Squares Around King
					if(newY == y0 - 1 && newX == x0 - 1 || newY == y0 - 1 && newX == x0 || newY == y0 - 1 && newX == x0 + 1 || newY == y0 && newX == x0 - 1 ||
					   newY == y0 && newX == x0 + 1 || newY == y0 + 1 && newX == x0 - 1 || newY == y0 + 1 && newX == x0 || newY == y0 + 1 && newX == x0 + 1) {
						//Kill Enemy Piece
						if(Board.squares[newY][newX].hasPiece()) {
							Board.pieceGroup.getChildren().remove(Board.squares[newY][newX].getPiece());
							this.move(newX, newY);
							Board.squares[y0][x0].setPiece(null);
							Board.squares[newY][newX].setPiece(this);
							if(TakeTurns.turn.contentEquals("Player1")) {
								TakeTurns.turn = "Player2";
							} else {
								TakeTurns.turn = "Player1";
							}
							Check();
							if(this.getPiece_color().contains("Color1")) {
								SpecialRules.kingMovedC1 = true;
							} else if (this.getPiece_color().contains("Color2")) {
								SpecialRules.kingMovedC2 = true;
							}
						//Move
						} else {
							this.move(newX, newY);
							Board.squares[y0][x0].setPiece(null);
							Board.squares[newY][newX].setPiece(this);
							if(TakeTurns.turn.contentEquals("Player1")) {
								TakeTurns.turn = "Player2";
							} else {
								TakeTurns.turn = "Player1";
							}
							Check();
							if(this.getPiece_color().contains("Color1")) {
								SpecialRules.kingMovedC1 = true;
							} else if (this.getPiece_color().contains("Color2")) {
								SpecialRules.kingMovedC2 = true;
							}
						}
					//Castle - White Piece - Check Left Side
					} else if(y0 == 0 && x0 == 4 && newY == y0 && newX == x0 - 2 && this.getPiece_color().contentEquals("Color1")) {
						if(Board.squares[0][1].hasPiece() == false && Board.squares[0][2].hasPiece() == false && Board.squares[0][3].hasPiece() == false) {
							if(SpecialRules.leftRookMovedC1 == false && SpecialRules.kingMovedC1 == false) {
								this.move(newX, newY);
								Board.squares[y0][x0].setPiece(null);
								Board.squares[newY][newX].setPiece(this);
								Board.squares[0][0].getPiece().move(3, 0);
								Board.squares[0][3].setPiece(Board.squares[0][0].getPiece());
								Board.squares[0][0].setPiece(null);
								SpecialRules.leftRookMovedC1 = true;
								SpecialRules.kingMovedC1 = true;
								if(TakeTurns.turn.contentEquals("Player1")) {
									TakeTurns.turn = "Player2";
								} else {
									TakeTurns.turn = "Player1";
								}
								Check();
							} else {
								this.cancelMove();
							}
						} else {
							this.cancelMove();
						}
					//Castle - White Piece - Check Right Side
					} else if(y0 == 0 && x0 == 4 && newY == y0 && newX == x0 + 2 && this.getPiece_color().contentEquals("Color1")) {
						if(Board.squares[0][5].hasPiece() == false && Board.squares[0][6].hasPiece() == false) {
							if(SpecialRules.rightRookMovedC1 == false && SpecialRules.kingMovedC1 == false) {
								this.move(newX, newY);
								Board.squares[y0][x0].setPiece(null);
								Board.squares[newY][newX].setPiece(this);
								Board.squares[0][7].getPiece().move(5, 0);
								Board.squares[0][5].setPiece(Board.squares[0][7].getPiece());
								Board.squares[0][7].setPiece(null);
								SpecialRules.rightRookMovedC1 = true;
								SpecialRules.kingMovedC1 = true;
								if(TakeTurns.turn.contentEquals("Player1")) {
									TakeTurns.turn = "Player2";
								} else {
									TakeTurns.turn = "Player1";
								}
								Check();
							} else {
								this.cancelMove();
							}
						} else {
							this.cancelMove();
						}
					//Castle - Black Piece - Check Left Side
					} else if(y0 == 7 && x0 == 4 && newY == y0 && newX == x0 - 2 && this.getPiece_color().contentEquals("Color2")) {
						if(Board.squares[7][1].hasPiece() == false && Board.squares[7][2].hasPiece() == false && Board.squares[7][3].hasPiece() == false) {
							if(SpecialRules.leftRookMovedC2 == false && SpecialRules.kingMovedC2 == false) {
								this.move(newX, newY);
								Board.squares[y0][x0].setPiece(null);
								Board.squares[newY][newX].setPiece(this);
								Board.squares[7][0].getPiece().move(3, 7);
								Board.squares[7][3].setPiece(Board.squares[7][0].getPiece());
								Board.squares[7][0].setPiece(null);
								SpecialRules.leftRookMovedC2 = true;
								SpecialRules.kingMovedC2 = true;
								if(TakeTurns.turn.contentEquals("Player1")) {
									TakeTurns.turn = "Player2";
								} else {
									TakeTurns.turn = "Player1";
								}
								Check();
							} else {
								this.cancelMove();
							}
						} else {
							this.cancelMove();
						}
					//Castle - Black Piece - Check Right Side
					} else if(y0 == 7 && x0 == 4 && newY == y0 && newX == x0 + 2 && this.getPiece_color().contentEquals("Color2")) {
						if(Board.squares[7][5].hasPiece() == false && Board.squares[7][6].hasPiece() == false) {
							if(SpecialRules.rightRookMovedC2 == false && SpecialRules.kingMovedC2 == false) {
								this.move(newX, newY);
								Board.squares[y0][x0].setPiece(null);
								Board.squares[newY][newX].setPiece(this);
								Board.squares[7][7].getPiece().move(5, 7);
								Board.squares[7][5].setPiece(Board.squares[7][7].getPiece());
								Board.squares[7][7].setPiece(null);
								SpecialRules.rightRookMovedC2 = true;
								SpecialRules.kingMovedC2 = true;
								if(TakeTurns.turn.contentEquals("Player1")) {
									TakeTurns.turn = "Player2";
								} else {
									TakeTurns.turn = "Player1";
								}
								Check();
							} else {
								this.cancelMove();
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
	
	//See If King is Under Check!
	public static void Check() {
		for(int i = 0; i < 8; i++) {
			for(int x = 0; x < 8; x++) {
				//Loop Through The Board And Get The White King
				if(Board.squares[i][x].hasPiece() && Board.squares[i][x].getPiece().getPieceName() == "King" && Board.squares[i][x].getPiece().getPiece_color() == "Color1") {
					int OldY;
					int OldX;
					boolean check = false;
					//Check For Enemy Pawn
					if(i < 7 && x < 7 && Board.squares[i + 1][x + 1].hasPiece() && Board.squares[i + 1][x + 1].getPiece().getPieceName() == "Pawn" && Board.squares[i + 1][x + 1].getPiece().getPiece_color() == "Color2" || 
					   i < 7 && x > 0 && Board.squares[i + 1][x - 1].hasPiece() && Board.squares[i + 1][x - 1].getPiece().getPieceName() == "Pawn" && Board.squares[i + 1][x - 1].getPiece().getPiece_color() == "Color2") {
						System.out.println("Check");
						check = true;
						SpecialRules.CheckC1 = true;
					//Check For Enemy Bishop & Queen - Down Right
					} if (check == false) {
						OldY = (int) (Board.squares[i][x].getPiece().getOldY()/100) + 1;
						OldX = (int) (Board.squares[i][x].getPiece().getOldX()/100) + 1;
						while(OldY != 8 && OldX != 8) {
							if(Board.squares[OldY][OldX].hasPiece()) {
								if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Bishop" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2" ||
						           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
									System.out.println("Check");
									check = true;
									SpecialRules.CheckC1 = true;
									break;
								} else {
									System.out.println("No Check");
									break;
								}
							}
							OldY++;
							OldX++;
						} 
					//Check For Enemy Bishop & Queen - Down Left
					} if(check == false) {
						OldY = (int) (Board.squares[i][x].getPiece().getOldY()/100) + 1;
						OldX = (int) (Board.squares[i][x].getPiece().getOldX()/100) - 1;
						while(OldY != 8 && OldX != -1) {
							if(Board.squares[OldY][OldX].hasPiece()) {
								if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Bishop" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2" ||
						           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
									System.out.println("Check");
									check = true;
									SpecialRules.CheckC1 = true;
									break;
								} else {
									System.out.println("No Check");
									break;
								}
							}
							OldY++;
							OldX--;
						}
					//Check For Enemy Bishop & Queen - Up Left
					} if (check == false) {
						OldY = (int) (Board.squares[i][x].getPiece().getOldY()/100) - 1;
						OldX = (int) (Board.squares[i][x].getPiece().getOldX()/100) - 1;
						while(OldY != -1 && OldX != -1) {
							if(Board.squares[OldY][OldX].hasPiece()) {
								if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Bishop" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2" ||
						           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
									System.out.println("Check");
									check = true;
									SpecialRules.CheckC1 = true;
									break;
								} else {
									System.out.println("No Check");
									break;
								}
							}
							OldY--;
							OldX--;
						}
					//Check For Enemy Bishop & Queen - Up Right
					} if (check == false) {
						OldY = (int) (Board.squares[i][x].getPiece().getOldY()/100) - 1;
						OldX = (int) (Board.squares[i][x].getPiece().getOldX()/100) + 1;
						while(OldY != -1 && OldX != 8) {
							if(Board.squares[OldY][OldX].hasPiece()) {
								if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Bishop" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2" ||
						           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
									System.out.println("Check");
									check = true;
									SpecialRules.CheckC1 = true;
									break;
								} else {
									System.out.println("No Check");
									break;
								}
							}
							OldY--;
							OldX++;
						}
				    //Check For Enemy Bishop & Queen - Up
					} if (check == false) {
						OldY = (int) (Board.squares[i][x].getPiece().getOldY()/100) - 1;
						OldX = (int) (Board.squares[i][x].getPiece().getOldX()/100);
						while(OldY != -1) {
							if(Board.squares[OldY][OldX].hasPiece()) {
								if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Rook" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2" ||
						           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
									System.out.println("Check");
									check = true;
									SpecialRules.CheckC1 = true;
									break;
								} else {
									System.out.println("No Check");
									break;
								}
							}
							OldY--;
						}
					//Check For Enemy Bishop & Queen - Right
					} if (check == false) {
						OldY = (int) (Board.squares[i][x].getPiece().getOldY()/100);
						OldX = (int) (Board.squares[i][x].getPiece().getOldX()/100) + 1;
						while(OldX != 8) {
							if(Board.squares[OldY][OldX].hasPiece()) {
								if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Rook" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2" ||
						           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
									System.out.println("Check");
									check = true;
									SpecialRules.CheckC1 = true;
									break;
								} else {
									System.out.println("No Check");
									break;
								}
							}
							OldX++;
						}
					//Check For Enemy Bishop & Queen - Down
					} if (check == false) {
						OldY = (int) (Board.squares[i][x].getPiece().getOldY()/100) + 1;
						OldX = (int) (Board.squares[i][x].getPiece().getOldX()/100);
						while(OldY != 8) {
							if(Board.squares[OldY][OldX].hasPiece()) {
								if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Rook" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2" ||
						           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
									System.out.println("Check");
									check = true;
									SpecialRules.CheckC1 = true;
									break;
								} else {
									System.out.println("No Check");
									break;
								}
							}
							OldY++;
						}
					//Check For Enemy Bishop & Queen - Left
					} if (check == false) {
						OldY = (int) (Board.squares[i][x].getPiece().getOldY()/100);
						OldX = (int) (Board.squares[i][x].getPiece().getOldX()/100) - 1;
						while(OldX != -1) {
							if(Board.squares[OldY][OldX].hasPiece()) {
								if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Rook" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2" ||
						           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
									System.out.println("Check");
									check = true;
									SpecialRules.CheckC1 = true;
									break;
								} else {
									System.out.println("No Check");
									break;
								}
							}
							OldX--;
						}
					//Check For Enemy Knight
					} if (check == false) {
						if(i < 6 && x > 0) {
							if(Board.squares[i + 2][x - 1].hasPiece() && Board.squares[i + 2][x - 1].getPiece().getPieceName() == "Knight" && Board.squares[i + 2][x - 1].getPiece().getPiece_color() == "Color2") {
								System.out.println("Check");
								check = true;
								SpecialRules.CheckC1 = true;
							}
						} if(i < 6 && x < 7) {
							if(Board.squares[i + 2][x + 1].hasPiece() && Board.squares[i + 2][x + 1].getPiece().getPieceName() == "Knight" && Board.squares[i + 2][x + 1].getPiece().getPiece_color() == "Color2") {
								System.out.println("Check");
								check = true;
								SpecialRules.CheckC1 = true;
							}
						} if(i < 7 && x > 1) {
							if(Board.squares[i + 1][x - 2].hasPiece() && Board.squares[i + 1][x - 2].getPiece().getPieceName() == "Knight" && Board.squares[i + 1][x - 2].getPiece().getPiece_color() == "Color2") {
								System.out.println("Check");
								check = true;
								SpecialRules.CheckC1 = true;
							}
						} if(i < 7 && x < 6) {
							if(Board.squares[i + 1][x + 2].hasPiece() && Board.squares[i + 1][x + 2].getPiece().getPieceName() == "Knight" && Board.squares[i + 1][x + 2].getPiece().getPiece_color() == "Color2") {
								System.out.println("Check");
								check = true;
								SpecialRules.CheckC1 = true;
							}
						} if(i > 0 && x > 1) {
							if(Board.squares[i - 1][x - 2].hasPiece() && Board.squares[i - 1][x - 2].getPiece().getPieceName() == "Knight" && Board.squares[i - 1][x - 2].getPiece().getPiece_color() == "Color2") {
								System.out.println("Check");
								check = true;
								SpecialRules.CheckC1 = true;
							}
						} if(i > 0 && x < 6) {
							if(Board.squares[i - 1][x + 2].hasPiece() && Board.squares[i - 1][x + 2].getPiece().getPieceName() == "Knight" && Board.squares[i - 1][x + 2].getPiece().getPiece_color() == "Color2") {
								System.out.println("Check");
								check = true;
								SpecialRules.CheckC1 = true;
							}
						} if(i > 1 && x > 0) {
							if(Board.squares[i - 2][x - 1].hasPiece() && Board.squares[i - 2][x - 1].getPiece().getPieceName() == "Knight" && Board.squares[i - 2][x - 1].getPiece().getPiece_color() == "Color2") {
								System.out.println("Check");
								check = true;
								SpecialRules.CheckC1 = true;
							}
						} if(i > 1 && x < 7) {
							if(Board.squares[i - 2][x + 1].hasPiece() && Board.squares[i - 2][x + 1].getPiece().getPieceName() == "Knight" && Board.squares[i - 2][x + 1].getPiece().getPiece_color() == "Color2") {
								System.out.println("Check");
								check = true;
								SpecialRules.CheckC1 = true;
							}
						}
						
					} 
					//Check For CheckMate
					Boolean c1= false, c2 = false, c3 = false, c4 = false, c5 = false, c6 = false, c7 = false, c8 = false;
					if(i < 7 && x > 0) {
						if(Board.squares[i + 1][x - 1].hasPiece() && Board.squares[i + 1][x - 1].getPiece().getPiece_color() == "Color2" || Board.squares[i + 1][x - 1].hasPiece() == false) {
							c1 = CheckMateC1(i + 1, x - 1);
						} else {
							c1 = null;
						}
					} if(x > 0) {
						if(Board.squares[i][x - 1].hasPiece() && Board.squares[i][x - 1].getPiece().getPiece_color() == "Color2" || Board.squares[i][x - 1].hasPiece() == false) {
							c2 = CheckMateC1(i, x - 1);
						} else {
							c2 = null;
						}
					} if(i > 0 && x > 0) {
						if(Board.squares[i - 1][x - 1].hasPiece() && Board.squares[i - 1][x - 1].getPiece().getPiece_color() == "Color2" || Board.squares[i - 1][x - 1].hasPiece() == false) {
							c3 = CheckMateC1(i - 1, x - 1);
						} else {
							c3 = null;
						}
					} if(i > 0) {
						if(Board.squares[i - 1][x].hasPiece() && Board.squares[i - 1][x].getPiece().getPiece_color() == "Color2" || Board.squares[i - 1][x].hasPiece() == false) {
							c4 = CheckMateC1(i - 1, x);
						} else {
							c4 = null;
						}
					} if(i > 0 && x < 7) {
						if(Board.squares[i - 1][x + 1].hasPiece() && Board.squares[i - 1][x + 1].getPiece().getPiece_color() == "Color2" || Board.squares[i - 1][x + 1].hasPiece() == false) {
							c5 = CheckMateC1(i - 1, x + 1);
						} else {
							c5 = null;
						}
					} if(x < 7) {
						if(Board.squares[i][x + 1].hasPiece() && Board.squares[i][x + 1].getPiece().getPiece_color() == "Color2" || Board.squares[i][x + 1].hasPiece() == false) {
							c6 = CheckMateC1(i, x + 1);
						} else {
							c6 = null;
						}
					} if(i < 7 && x < 7) {
						if(Board.squares[i + 1][x + 1].hasPiece() && Board.squares[i + 1][x + 1].getPiece().getPiece_color() == "Color2" || Board.squares[i + 1][x + 1].hasPiece() == false) {
							c7 = CheckMateC1(i + 1, x + 1);
						} else {
							c7 = null;
						}
					} if(i < 7) {
						if(Board.squares[i + 1][x].hasPiece() && Board.squares[i + 1][x].getPiece().getPiece_color() == "Color2" || Board.squares[i + 1][x].hasPiece() == false) {
							c8 = CheckMateC1(i + 1, x);
						} else {
							c8 = null;
						}
					}
					//Display CheckMate
					if(i > 0 && i < 7 && x > 0 && x < 7) {
						if(c1 == null && c2 == null && c3 == null && c4 == null && c5 == null && c6 == null && c7 == null && c8 == null) {
							//Do nothing
						} else if((c1 == null || c1 == true) && (c2 == null || c2 == true) && (c3 == null || c3 == true) && (c4 == null || c4 == true) &&
						          (c5 == null || c5 == true) && (c6 == null || c6 == true) && (c7 == null || c7 == true) && (c8 == null || c8 == true)) {
							if(SpecialRules.CheckC1) {
								SpecialRules.checkMateDisplayC1();
							}
						}
					} else if(i < 1 && x < 1) {
						if(c6 == null && c7 == null && c8 == null) {
							//Do nothing
						} else if((c6 == null || c6 == true) && (c7 == null || c7 == true) && (c8 == null || c8 == true)) {
							if(SpecialRules.CheckC1) {
								SpecialRules.checkMateDisplayC1();
							}
						}
					} else if(i < 1 && x > 6) {
						if(c1 == null && c2 == null && c8 == null) {
							//Do nothing
						} else if((c1 == null || c1 == true) && (c2 == null || c2 == true) && (c8 == null || c8 == true)) {
							if(SpecialRules.CheckC1) {
								SpecialRules.checkMateDisplayC1();
							}
						}
					} else if(i > 6 && x > 6) {
						if(c2 == null && c3 == null && c4 == null) {
							//Do nothing
						} else if((c2 == null || c2 == true) && (c3 == null || c3 == true) && (c4 == null || c4 == true)) {
							if(SpecialRules.CheckC1) {
								SpecialRules.checkMateDisplayC1();
							}
						}
					} else if(i > 6 && x < 1) {
						if(c4 == null && c5 == null && c6 == null) {
							//Do nothing
						} else if((c4 == null || c4 == true) && (c5 == null || c5 == true) && (c6 == null || c6 == true)) {
							if(SpecialRules.CheckC1) {
								SpecialRules.checkMateDisplayC1();
							}
						}
					} else if(i < 1) {
						if(c1 == null && c2 == null && c6 == null && c7 == null && c8 == null) {
							//Do nothing
						} else if((c1 == null || c1 == true) && (c2 == null || c2 == true) && (c6 == null || c6 == true) && 
								  (c7 == null || c7 == true) && (c8 == null || c8 == true)) {
							if(SpecialRules.CheckC1) {
								SpecialRules.checkMateDisplayC1();
							}
						}
					} else if(x > 6) {
						if(c1 == null && c2 == null && c3 == null && c4 == null && c8 == null) {
							//Do nothing
						} else if((c1 == null || c1 == true) && (c2 == null || c2 == true) && (c3 == null || c3 == true) &&
								  (c4 == null || c4 == true) && (c8 == null || c8 == true)) {
							if(SpecialRules.CheckC1) {
								SpecialRules.checkMateDisplayC1();
							}
						}
					} else if(i > 6) {
						//System.out.println("c2 : " + c2 + " , " + "c3 : " + c3 + " , " + "c4 : " + c4 + " , " + "c5 : " + c5 + " , " + "c6 : " + c6);
						if(c2 == null && c3 == null && c4 == null && c5 == null && c6 == null) {
							//Do nothing
						} else if((c2 == null || c2 == true) && (c3 == null || c3 == true) && (c4 == null || c4 == true) &&
								  (c5 == null || c5 == true) && (c6 == null || c6 == true)) {
							if(SpecialRules.CheckC1) {
								SpecialRules.checkMateDisplayC1();
							}
						}
					} else if(x < 1) {
						if(c4 == null && c5 == null && c6 == null && c7 == null && c8 == null) {
							//Do nothing
						} else if((c4 == null || c4 == true) && (c5 == null || c5 == true) && (c6 == null || c6 == true) &&
								(c7 == null || c7 == true) && (c8 == null || c8 == true)) {
							if(SpecialRules.CheckC1) {
								SpecialRules.checkMateDisplayC1();
							}
						}
					}
				SpecialRules.CheckC1 = false;
				//Loop Through The Board And Get The Black King
				} else if(Board.squares[i][x].hasPiece() && Board.squares[i][x].getPiece().getPieceName() == "King" && Board.squares[i][x].getPiece().getPiece_color() == "Color2") {
					int OldY;
					int OldX;
					boolean check = false;
					//Check For Enemy Pawn
					if(i > 0 && x < 7 && Board.squares[i - 1][x + 1].hasPiece() && Board.squares[i - 1][x + 1].getPiece().getPieceName() == "Pawn" && Board.squares[i - 1][x + 1].getPiece().getPiece_color() == "Color1" || 
					   i > 0 && x > 0 && Board.squares[i - 1][x - 1].hasPiece() && Board.squares[i - 1][x - 1].getPiece().getPieceName() == "Pawn" && Board.squares[i - 1][x - 1].getPiece().getPiece_color() == "Color1") {
						System.out.println("Check");
						check = true;
						SpecialRules.CheckC2 = true;
					//Check For Enemy Bishop & Queen - Down Right
					} if (check == false) {
						OldY = (int) (Board.squares[i][x].getPiece().getOldY()/100) + 1;
						OldX = (int) (Board.squares[i][x].getPiece().getOldX()/100) + 1;
						while(OldY != 8 && OldX != 8) {
							if(Board.squares[OldY][OldX].hasPiece()) {
								if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Bishop" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1" ||
						           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
									System.out.println("Check");
									check = true;
									SpecialRules.CheckC2 = true;
									break;
								} else {
									System.out.println("No Check");
									break;
								}
							}
							OldY++;
							OldX++;
						} 
					//Check For Enemy Bishop & Queen - Down Left
					} if(check == false) {
						OldY = (int) (Board.squares[i][x].getPiece().getOldY()/100) + 1;
						OldX = (int) (Board.squares[i][x].getPiece().getOldX()/100) - 1;
						while(OldY != 8 && OldX != -1) {
							if(Board.squares[OldY][OldX].hasPiece()) {
								if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Bishop" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1" ||
						           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
									System.out.println("Check");
									check = true;
									SpecialRules.CheckC2 = true;
									break;
								} else {
									System.out.println("No Check");
									break;
								}
							}
							OldY++;
							OldX--;
						}
					//Check For Enemy Bishop & Queen - Up Left
					} if (check == false) {
						OldY = (int) (Board.squares[i][x].getPiece().getOldY()/100) - 1;
						OldX = (int) (Board.squares[i][x].getPiece().getOldX()/100) - 1;
						while(OldY != -1 && OldX != -1) {
							if(Board.squares[OldY][OldX].hasPiece()) {
								if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Bishop" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1" ||
						           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
									System.out.println("Check");
									check = true;
									SpecialRules.CheckC2 = true;
									break;
								} else {
									System.out.println("No Check");
									break;
								}
							}
							OldY--;
							OldX--;
						}
					//Check For Enemy Bishop & Queen - Up Right
					} if (check == false) {
						OldY = (int) (Board.squares[i][x].getPiece().getOldY()/100) - 1;
						OldX = (int) (Board.squares[i][x].getPiece().getOldX()/100) + 1;
						while(OldY != -1 && OldX != 8) {
							if(Board.squares[OldY][OldX].hasPiece()) {
								if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Bishop" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1" ||
						           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
									System.out.println("Check");
									check = true;
									SpecialRules.CheckC2 = true;
									break;
								} else {
									System.out.println("No Check");
									break;
								}
							}
							OldY--;
							OldX++;
						}
					//Check For Enemy Bishop & Queen - Up
					} if (check == false) {
						OldY = (int) (Board.squares[i][x].getPiece().getOldY()/100) - 1;
						OldX = (int) (Board.squares[i][x].getPiece().getOldX()/100);
						while(OldY != -1) {
							if(Board.squares[OldY][OldX].hasPiece()) {
								if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Rook" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1" ||
						           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
									System.out.println("Check");
									check = true;
									SpecialRules.CheckC2 = true;
									break;
								} else {
									System.out.println("No Check");
									break;
								}
							}
							OldY--;
						}
					//Check For Enemy Bishop & Queen - Right
					} if (check == false) {
						OldY = (int) (Board.squares[i][x].getPiece().getOldY()/100);
						OldX = (int) (Board.squares[i][x].getPiece().getOldX()/100) + 1;
						while(OldX != 8) {
							if(Board.squares[OldY][OldX].hasPiece()) {
								if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Rook" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1" ||
						           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
									System.out.println("Check");
									check = true;
									SpecialRules.CheckC2 = true;
									break;
								} else {
									System.out.println("No Check");
									break;
								}
							}
							OldX++;
						}
					//Check For Enemy Bishop & Queen - Down
					} if (check == false) {
						OldY = (int) (Board.squares[i][x].getPiece().getOldY()/100) + 1;
						OldX = (int) (Board.squares[i][x].getPiece().getOldX()/100);
						while(OldY != 8) {
							if(Board.squares[OldY][OldX].hasPiece()) {
								if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Rook" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1" ||
						           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
									System.out.println("Check");
									check = true;
									SpecialRules.CheckC2 = true;
									break;
								} else {
									System.out.println("No Check");
									break;
								}
							}
							OldY++;
						}
					//Check For Enemy Bishop & Queen - Left
					} if (check == false) {
						OldY = (int) (Board.squares[i][x].getPiece().getOldY()/100);
						OldX = (int) (Board.squares[i][x].getPiece().getOldX()/100) - 1;
						while(OldX != -1) {
							if(Board.squares[OldY][OldX].hasPiece()) {
								if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Rook" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1" ||
						           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
									System.out.println("Check");
									check = true;
									SpecialRules.CheckC2 = true;
									break;
								} else {
									System.out.println("No Check");
									break;
								}
							}
							OldX--;
						}
					//Check For Enemy Knight
					} if (check == false) {
						if(i < 6 && x > 0) {
							if(Board.squares[i + 2][x - 1].hasPiece() && Board.squares[i + 2][x - 1].getPiece().getPieceName() == "Knight" && Board.squares[i + 2][x - 1].getPiece().getPiece_color() == "Color1") {
								System.out.println("Check");
								check = true;
								SpecialRules.CheckC2 = true;
							}
						} if(i < 6 && x < 7) {
							if(Board.squares[i + 2][x + 1].hasPiece() && Board.squares[i + 2][x + 1].getPiece().getPieceName() == "Knight" && Board.squares[i + 2][x + 1].getPiece().getPiece_color() == "Color1") {
								System.out.println("Check");
								check = true;
								SpecialRules.CheckC2 = true;
							}
						} if(i < 7 && x > 1) {
							if(Board.squares[i + 1][x - 2].hasPiece() && Board.squares[i + 1][x - 2].getPiece().getPieceName() == "Knight" && Board.squares[i + 1][x - 2].getPiece().getPiece_color() == "Color1") {
								System.out.println("Check");
								check = true;
								SpecialRules.CheckC2 = true;
							}
						} if(i < 7 && x < 6) {
							if(Board.squares[i + 1][x + 2].hasPiece() && Board.squares[i + 1][x + 2].getPiece().getPieceName() == "Knight" && Board.squares[i + 1][x + 2].getPiece().getPiece_color() == "Color1") {
								System.out.println("Check");
								check = true;
								SpecialRules.CheckC2 = true;
							}
						} if(i > 0 && x > 1) {
							if(Board.squares[i - 1][x - 2].hasPiece() && Board.squares[i - 1][x - 2].getPiece().getPieceName() == "Knight" && Board.squares[i - 1][x - 2].getPiece().getPiece_color() == "Color1") {
								System.out.println("Check");
								check = true;
								SpecialRules.CheckC2 = true;
							}
						} if(i > 0 && x < 6) {
							if(Board.squares[i - 1][x + 2].hasPiece() && Board.squares[i - 1][x + 2].getPiece().getPieceName() == "Knight" && Board.squares[i - 1][x + 2].getPiece().getPiece_color() == "Color1") {
								System.out.println("Check");
								check = true;
								SpecialRules.CheckC2 = true;
							}
						} if(i > 1 && x > 0) {
							if(Board.squares[i - 2][x - 1].hasPiece() && Board.squares[i - 2][x - 1].getPiece().getPieceName() == "Knight" && Board.squares[i - 2][x - 1].getPiece().getPiece_color() == "Color1") {
								System.out.println("Check");
								check = true;
								SpecialRules.CheckC2 = true;
							}
						} if(i > 1 && x < 7) {
							if(Board.squares[i - 2][x + 1].hasPiece() && Board.squares[i - 2][x + 1].getPiece().getPieceName() == "Knight" && Board.squares[i - 2][x + 1].getPiece().getPiece_color() == "Color1") {
								System.out.println("Check");
								check = true;
								SpecialRules.CheckC2 = true;
							}
						}
						
					} 
					//Check For CheckMate
					Boolean c1= false, c2 = false, c3 = false, c4 = false, c5 = false, c6 = false, c7 = false, c8 = false;
					if(i < 7 && x > 0) {
						if(Board.squares[i + 1][x - 1].hasPiece() && Board.squares[i + 1][x - 1].getPiece().getPiece_color() == "Color1" || Board.squares[i + 1][x - 1].hasPiece() == false) {
							c1 = CheckMateC2(i + 1, x - 1);
						} else {
							c1 = null;
						}
					} if(x > 0) {
						if(Board.squares[i][x - 1].hasPiece() && Board.squares[i][x - 1].getPiece().getPiece_color() == "Color1" || Board.squares[i][x - 1].hasPiece() == false) {
							c2 = CheckMateC2(i, x - 1);
						} else {
							c2 = null;
						}
					} if(i > 0 && x > 0) {
						if(Board.squares[i - 1][x - 1].hasPiece() && Board.squares[i - 1][x - 1].getPiece().getPiece_color() == "Color1" || Board.squares[i - 1][x - 1].hasPiece() == false) {
							c3 = CheckMateC2(i - 1, x - 1);
						} else {
							c3 = null;
						}
					} if(i > 0) {
						if(Board.squares[i - 1][x].hasPiece() && Board.squares[i - 1][x].getPiece().getPiece_color() == "Color1" || Board.squares[i - 1][x].hasPiece() == false) {
							c4 = CheckMateC2(i - 1, x);
						} else {
							c4 = null;
						}
					} if(i > 0 && x < 7) {
						if(Board.squares[i - 1][x + 1].hasPiece() && Board.squares[i - 1][x + 1].getPiece().getPiece_color() == "Color1" || Board.squares[i - 1][x + 1].hasPiece() == false) {
							c5 = CheckMateC2(i - 1, x + 1);
						} else {
							c5 = null;
						}
					} if(x < 7) {
						if(Board.squares[i][x + 1].hasPiece() && Board.squares[i][x + 1].getPiece().getPiece_color() == "Color1" || Board.squares[i][x + 1].hasPiece() == false) {
							c6 = CheckMateC2(i, x + 1);
						} else {
							c6 = null;
						}
					} if(i < 7 && x < 7) {
						if(Board.squares[i + 1][x + 1].hasPiece() && Board.squares[i + 1][x + 1].getPiece().getPiece_color() == "Color1" || Board.squares[i + 1][x + 1].hasPiece() == false) {
							c7 = CheckMateC2(i + 1, x + 1);
						} else {
							c7 = null;
						}
					} if(i < 7) {
						if(Board.squares[i + 1][x].hasPiece() && Board.squares[i + 1][x].getPiece().getPiece_color() == "Color1" || Board.squares[i + 1][x].hasPiece() == false) {
							c8 = CheckMateC2(i + 1, x);
						} else {
							c8 = null;
						}
					}
					//Display CheckMate
					if(i > 0 && i < 7 && x > 0 && x < 7) {
						if(c1 == null && c2 == null && c3 == null && c4 == null && c5 == null && c6 == null && c7 == null && c8 == null) {
							//Do nothing
						} else if((c1 == null || c1 == true) && (c2 == null || c2 == true) && (c3 == null || c3 == true) && (c4 == null || c4 == true) &&
						          (c5 == null || c5 == true) && (c6 == null || c6 == true) && (c7 == null || c7 == true) && (c8 == null || c8 == true)) {
							if(SpecialRules.CheckC2) {
								SpecialRules.checkMateDisplayC2();
							}
						}
					} else if(i < 1 && x < 1) {
						if(c6 == null && c7 == null && c8 == null) {
							//Do nothing
						} else if((c6 == null || c6 == true) && (c7 == null || c7 == true) && (c8 == null || c8 == true)) {
							if(SpecialRules.CheckC2) {
								SpecialRules.checkMateDisplayC2();
							}
						}
					} else if(i < 1 && x > 6) {
						if(c1 == null && c2 == null && c8 == null) {
							//Do nothing
						} else if((c1 == null || c1 == true) && (c2 == null || c2 == true) && (c8 == null || c8 == true)) {
							if(SpecialRules.CheckC2) {
								SpecialRules.checkMateDisplayC2();
							}
						}
					} else if(i > 6 && x > 6) {
						if(c2 == null && c3 == null && c4 == null) {
							//Do nothing
						} else if((c2 == null || c2 == true) && (c3 == null || c3 == true) && (c4 == null || c4 == true)) {
							if(SpecialRules.CheckC2) {
								SpecialRules.checkMateDisplayC2();
							}
						}
					} else if(i > 6 && x < 1) {
						if(c4 == null && c5 == null && c6 == null) {
							//Do nothing
						} else if((c4 == null || c4 == true) && (c5 == null || c5 == true) && (c6 == null || c6 == true)) {
							if(SpecialRules.CheckC2) {
								SpecialRules.checkMateDisplayC2();
							}
						}
					} else if(i < 1) {
						if(c1 == null && c2 == null && c6 == null && c7 == null && c8 == null) {
							//Do nothing
						} else if((c1 == null || c1 == true) && (c2 == null || c2 == true) && (c6 == null || c6 == true) && 
								  (c7 == null || c7 == true) && (c8 == null || c8 == true)) {
							if(SpecialRules.CheckC2) {
								SpecialRules.checkMateDisplayC2();
							}
						}
					} else if(x > 6) {
						if(c1 == null && c2 == null && c3 == null && c4 == null && c8 == null) {
							//Do nothing
						} else if((c1 == null || c1 == true) && (c2 == null || c2 == true) && (c3 == null || c3 == true) &&
								  (c4 == null || c4 == true) && (c8 == null || c8 == true)) {
							if(SpecialRules.CheckC2) {
								SpecialRules.checkMateDisplayC2();
							}
						}
					} else if(i > 6) {
						//System.out.println("c2 : " + c2 + " , " + "c3 : " + c3 + " , " + "c4 : " + c4 + " , " + "c5 : " + c5 + " , " + "c6 : " + c6);
						if(c2 == null && c3 == null && c4 == null && c5 == null && c6 == null) {
							//Do nothing
						} else if((c2 == null || c2 == true) && (c3 == null || c3 == true) && (c4 == null || c4 == true) &&
								  (c5 == null || c5 == true) && (c6 == null || c6 == true)) {
							if(SpecialRules.CheckC2) {
								SpecialRules.checkMateDisplayC2();
							}
						}
					} else if(x < 1) {
						if(c4 == null && c5 == null && c6 == null && c7 == null && c8 == null) {
							//Do nothing
						} else if((c4 == null || c4 == true) && (c5 == null || c5 == true) && (c6 == null || c6 == true) &&
								(c7 == null || c7 == true) && (c8 == null || c8 == true)) {
							if(SpecialRules.CheckC2) {
								SpecialRules.checkMateDisplayC2();
							}
						}
					}
				SpecialRules.CheckC2 = false;
				} 
			}
		}
	}
	
	//CheckMate Method For White King
	public static boolean CheckMateC1(int i, int x) {
		int OldY;
		int OldX;
		boolean check = false;
		if(i < 7 && x < 7 && Board.squares[i + 1][x + 1].hasPiece() && Board.squares[i + 1][x + 1].getPiece().getPieceName() == "Pawn" && Board.squares[i + 1][x + 1].getPiece().getPiece_color() == "Color2" || 
		   i < 7 && x > 0 && Board.squares[i + 1][x - 1].hasPiece() && Board.squares[i + 1][x - 1].getPiece().getPieceName() == "Pawn" && Board.squares[i + 1][x - 1].getPiece().getPiece_color() == "Color2") {
			check = true;
		} if (check == false) {
			OldY = i + 1;
			OldX = x + 1;
			while(OldY != 8 && OldX != 8) {
				if(Board.squares[OldY][OldX].hasPiece()) {
					if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Bishop" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2" ||
			           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
						check = true;
						break;
					} else {
						if(Board.squares[OldY][OldX].getPiece().getPieceName() == "King" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
						} else {
							break;
						}
					}
				}
				OldY++;
				OldX++;
			} 
		} if(check == false) {
			OldY = i + 1;
			OldX = x - 1;
			while(OldY != 8 && OldX != -1) {
				if(Board.squares[OldY][OldX].hasPiece()) {
					if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Bishop" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2" ||
			           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
						check = true;
						break;
					} else {
						if(Board.squares[OldY][OldX].getPiece().getPieceName() == "King" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
						} else {
							break;
						}
					}
				}
				OldY++;
				OldX--;
			}
		} if (check == false) {
			OldY = i - 1;
			OldX = x - 1;
			while(OldY != -1 && OldX != -1) {
				if(Board.squares[OldY][OldX].hasPiece()) {
					if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Bishop" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2" ||
			           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
						check = true;
						break;
					} else {
						if(Board.squares[OldY][OldX].getPiece().getPieceName() == "King" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
						} else {
							break;
						}
					}
				}
				OldY--;
				OldX--;
			}
		} if (check == false) {
			OldY = i - 1;
			OldX = x + 1;
			while(OldY != -1 && OldX != 8) {
				if(Board.squares[OldY][OldX].hasPiece()) {
					if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Bishop" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2" ||
			           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
						check = true;
						break;
					} else {
						if(Board.squares[OldY][OldX].getPiece().getPieceName() == "King" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
						} else {
							break;
						}
					}
				}
				OldY--;
				OldX++;
			}
		} if (check == false) {
			OldY = i - 1;
			OldX = x;
			while(OldY != -1) {
				if(Board.squares[OldY][OldX].hasPiece()) {
					if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Rook" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2" ||
			           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
						check = true;
						break;
					} else {
						if(Board.squares[OldY][OldX].getPiece().getPieceName() == "King" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
						} else {
							break;
						}
					}
				}
				OldY--;
			}
		} if (check == false) {
			OldY = i;
			OldX = x + 1;
			while(OldX != 8) {
				if(Board.squares[OldY][OldX].hasPiece()) {
					if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Rook" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2" ||
			           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
						check = true;
						break;
					} else {
						if(Board.squares[OldY][OldX].getPiece().getPieceName() == "King" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
						} else {
							break;
						}
					}
				}
				OldX++;
			}
		} if (check == false) {
			OldY = i + 1;
			OldX = x;
			while(OldY != 8) {
				if(Board.squares[OldY][OldX].hasPiece()) {
					if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Rook" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2" ||
			           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
						check = true;
						break;
					} else {
						if(Board.squares[OldY][OldX].getPiece().getPieceName() == "King" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
						} else {
							break;
						}
					}
				}
				OldY++;
			}
		} if (check == false) {
			OldY = i;
			OldX = x - 1;
			while(OldX != -1) {
				if(Board.squares[OldY][OldX].hasPiece()) {
					if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Rook" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2" ||
			           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
						check = true;
						break;
					} else {
						if(Board.squares[OldY][OldX].getPiece().getPieceName() == "King" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
						} else {
							break;
						}
					}
				}
				OldX--;
			}
		} if (check == false) {
			if(i < 6 && x > 0) {
				if(Board.squares[i + 2][x - 1].hasPiece() && Board.squares[i + 2][x - 1].getPiece().getPieceName() == "Knight" && Board.squares[i + 2][x - 1].getPiece().getPiece_color() == "Color2") {
					check = true;
				}
			} if(i < 6 && x < 7) {
				if(Board.squares[i + 2][x + 1].hasPiece() && Board.squares[i + 2][x + 1].getPiece().getPieceName() == "Knight" && Board.squares[i + 2][x + 1].getPiece().getPiece_color() == "Color2") {
					check = true;
				}
			} if(i < 7 && x > 1) {
				if(Board.squares[i + 1][x - 2].hasPiece() && Board.squares[i + 1][x - 2].getPiece().getPieceName() == "Knight" && Board.squares[i + 1][x - 2].getPiece().getPiece_color() == "Color2") {
					check = true;
				}
			} if(i < 7 && x < 6) {
				if(Board.squares[i + 1][x + 2].hasPiece() && Board.squares[i + 1][x + 2].getPiece().getPieceName() == "Knight" && Board.squares[i + 1][x + 2].getPiece().getPiece_color() == "Color2") {
					check = true;
				}
			} if(i > 0 && x > 1) {
				if(Board.squares[i - 1][x - 2].hasPiece() && Board.squares[i - 1][x - 2].getPiece().getPieceName() == "Knight" && Board.squares[i - 1][x - 2].getPiece().getPiece_color() == "Color2") {
					check = true;
				}
			} if(i > 0 && x < 6) {
				if(Board.squares[i - 1][x + 2].hasPiece() && Board.squares[i - 1][x + 2].getPiece().getPieceName() == "Knight" && Board.squares[i - 1][x + 2].getPiece().getPiece_color() == "Color2") {
					check = true;
				}
			} if(i > 1 && x > 0) {
				if(Board.squares[i - 2][x - 1].hasPiece() && Board.squares[i - 2][x - 1].getPiece().getPieceName() == "Knight" && Board.squares[i - 2][x - 1].getPiece().getPiece_color() == "Color2") {
					check = true;
				}
			} if(i > 1 && x < 7) {
				if(Board.squares[i - 2][x + 1].hasPiece() && Board.squares[i - 2][x + 1].getPiece().getPieceName() == "Knight" && Board.squares[i - 2][x + 1].getPiece().getPiece_color() == "Color2") {
					check = true;
				}
			}
			
		}
		return check;
	}
	
	//CheckMate Method For Black King
	public static boolean CheckMateC2(int i, int x) {
		int OldY;
		int OldX;
		boolean check = false;
		if(	i > 0 && x < 7 && Board.squares[i - 1][x + 1].hasPiece() && Board.squares[i - 1][x + 1].getPiece().getPieceName() == "Pawn" && Board.squares[i - 1][x + 1].getPiece().getPiece_color() == "Color1" || 
		    i > 0 && x > 0 && Board.squares[i - 1][x - 1].hasPiece() && Board.squares[i - 1][x - 1].getPiece().getPieceName() == "Pawn" && Board.squares[i - 1][x - 1].getPiece().getPiece_color() == "Color1") {
			check = true;
		} if (check == false) {
			OldY = i + 1;
			OldX = x + 1;
			while(OldY != 8 && OldX != 8) {
				if(Board.squares[OldY][OldX].hasPiece()) {
					if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Bishop" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1" ||
			           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
						check = true;
						break;
					} else {
						if(Board.squares[OldY][OldX].getPiece().getPieceName() == "King" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
						} else {
							break;
						}
					}
				}
				OldY++;
				OldX++;
			} 
		} if(check == false) {
			OldY = i + 1;
			OldX = x - 1;
			while(OldY != 8 && OldX != -1) {
				if(Board.squares[OldY][OldX].hasPiece()) {
					if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Bishop" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1" ||
			           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
						check = true;
						break;
					} else {
						if(Board.squares[OldY][OldX].getPiece().getPieceName() == "King" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
						} else {
							break;
						}
					}
				}
				OldY++;
				OldX--;
			}
		} if (check == false) {
			OldY = i - 1;
			OldX = x - 1;
			while(OldY != -1 && OldX != -1) {
				if(Board.squares[OldY][OldX].hasPiece()) {
					if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Bishop" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1" ||
			           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
						check = true;
						break;
					} else {
						if(Board.squares[OldY][OldX].getPiece().getPieceName() == "King" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
						} else {
							break;
						}
					}
				}
				OldY--;
				OldX--;
			}
		} if (check == false) {
			OldY = i - 1;
			OldX = x + 1;
			while(OldY != -1 && OldX != 8) {
				if(Board.squares[OldY][OldX].hasPiece()) {
					if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Bishop" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1" ||
			           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
						check = true;
						break;
					} else {
						if(Board.squares[OldY][OldX].getPiece().getPieceName() == "King" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
						} else {
							break;
						}
					}
				}
				OldY--;
				OldX++;
			}
		} if (check == false) {
			OldY = i - 1;
			OldX = x;
			while(OldY != -1) {
				if(Board.squares[OldY][OldX].hasPiece()) {
					if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Rook" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1" ||
			           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
						check = true;
						break;
					} else {
						if(Board.squares[OldY][OldX].getPiece().getPieceName() == "King" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
						} else {
							break;
						}
					}
				}
				OldY--;
			}
		} if (check == false) {
			OldY = i;
			OldX = x + 1;
			while(OldX != 8) {
				if(Board.squares[OldY][OldX].hasPiece()) {
					if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Rook" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1" ||
			           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
						check = true;
						break;
					} else {
						if(Board.squares[OldY][OldX].getPiece().getPieceName() == "King" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
						} else {
							break;
						}
					}
				}
				OldX++;
			}
		} if (check == false) {
			OldY = i + 1;
			OldX = x;
			while(OldY != 8) {
				if(Board.squares[OldY][OldX].hasPiece()) {
					if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Rook" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1" ||
			           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
						check = true;
						break;
					} else {
						if(Board.squares[OldY][OldX].getPiece().getPieceName() == "King" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
						} else {
							break;
						}
					}
				}
				OldY++;
			}
		} if (check == false) {
			OldY = i;
			OldX = x - 1;
			while(OldX != -1) {
				if(Board.squares[OldY][OldX].hasPiece()) {
					if(Board.squares[OldY][OldX].getPiece().getPieceName() == "Rook" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1" ||
			           Board.squares[OldY][OldX].getPiece().getPieceName() == "Queen" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color1") {
						check = true;
						break;
					} else {
						if(Board.squares[OldY][OldX].getPiece().getPieceName() == "King" && Board.squares[OldY][OldX].getPiece().getPiece_color() == "Color2") {
						} else {
							break;
						}
					}
				}
				OldX--;
			}
		} if (check == false) {
			if(i < 6 && x > 0) {
				if(Board.squares[i + 2][x - 1].hasPiece() && Board.squares[i + 2][x - 1].getPiece().getPieceName() == "Knight" && Board.squares[i + 2][x - 1].getPiece().getPiece_color() == "Color1") {
					check = true;
				}
			} if(i < 6 && x < 7) {
				if(Board.squares[i + 2][x + 1].hasPiece() && Board.squares[i + 2][x + 1].getPiece().getPieceName() == "Knight" && Board.squares[i + 2][x + 1].getPiece().getPiece_color() == "Color1") {
					check = true;
				}
			} if(i < 7 && x > 1) {
				if(Board.squares[i + 1][x - 2].hasPiece() && Board.squares[i + 1][x - 2].getPiece().getPieceName() == "Knight" && Board.squares[i + 1][x - 2].getPiece().getPiece_color() == "Color1") {
					check = true;
				}
			} if(i < 7 && x < 6) {
				if(Board.squares[i + 1][x + 2].hasPiece() && Board.squares[i + 1][x + 2].getPiece().getPieceName() == "Knight" && Board.squares[i + 1][x + 2].getPiece().getPiece_color() == "Color1") {
					check = true;
				}
			} if(i > 0 && x > 1) {
				if(Board.squares[i - 1][x - 2].hasPiece() && Board.squares[i - 1][x - 2].getPiece().getPieceName() == "Knight" && Board.squares[i - 1][x - 2].getPiece().getPiece_color() == "Color1") {
					check = true;
				}
			} if(i > 0 && x < 6) {
				if(Board.squares[i - 1][x + 2].hasPiece() && Board.squares[i - 1][x + 2].getPiece().getPieceName() == "Knight" && Board.squares[i - 1][x + 2].getPiece().getPiece_color() == "Color1") {
					check = true;
				}
			} if(i > 1 && x > 0) {
				if(Board.squares[i - 2][x - 1].hasPiece() && Board.squares[i - 2][x - 1].getPiece().getPieceName() == "Knight" && Board.squares[i - 2][x - 1].getPiece().getPiece_color() == "Color1") {
					check = true;
				}
			} if(i > 1 && x < 7) {
				if(Board.squares[i - 2][x + 1].hasPiece() && Board.squares[i - 2][x + 1].getPiece().getPieceName() == "Knight" && Board.squares[i - 2][x + 1].getPiece().getPiece_color() == "Color1") {
					check = true;
				}
			}
			
		}
		return check;
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
