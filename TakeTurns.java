/**
 * CPSC-24500 Object Oriented Programming || Final Project || CHESS
 * This class is used as a base to switch the turns. 
 * @author Arundev Chandrasekaram
 * @version 1.8.0_241
 * @date 12/17/2021
 */
package Final_Project;

public class TakeTurns {
	
	public static Turn currentTurn = Turn.PLAYER1;
	
	//Determines The Turn. White Player Goes First!
	public static String turn = "Player1";
	
	public static boolean winCondition = true;
	
	public static void whosTurn() {
		
		//Players
		Player player1 = new Player("Thomas", Turn.PLAYER1);
		Player player2 = new Player("Dexter", Turn.PLAYER2);

	}
}
