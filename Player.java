/**
 * CPSC-24500 Object Oriented Programming || Final Project || CHESS
 * This class is Player Class and is supposed to create player objects
 * to switch turns. 
 * @author Arundev Chandrasekaram
 * @version 1.8.0_241
 * @date 12/17/2021
 */
package Final_Project;

public class Player {
	
	String name;
	Turn player;
	
	public Player(String name, Turn player) {
		this.name = name;
		this.player = player;
	}

	public Turn getPlayer() {
		return player;
	}

	public void setPlayer(Turn player) {
		this.player = player;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
