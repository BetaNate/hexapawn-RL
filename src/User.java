/*
 * Author: Nathan J. Rowe
 * Class Description:
 * Class for User
 * Input: name: name of user, side: side that user is playing
 * Used in Game.java for game setup
 * name should always be set to "player" in Game.java for user
 * side should always be set to "black" or "white" in Game.java for user
 * For HER, see Robot.java
 */
import javafx.scene.paint.Color;

public class User {
    private final String name;
    private final String side;
	private Color color;

	public User(String name, String side) {
        this.name = name;
        this.side = side;
	}
	
/*
 * ------------------------
 *       Getters
 * ------------------------
 */
	public final String getName() {
		return name ;
	}
	
    public final String getSide() {
        return side;
    }

    //Method to get the user's color
    //Get color based on side
	public final Color getColor() {
		switch(side.toLowerCase()) {
			case "black":
				color = Color.BLACK;
				break;
			case "white":
				color = Color.WHITE;
				break;
			default:
				System.out.println("INVALID COLOR");
				color = null;
				break;
		}
		return color;
	}

	@Override
	public String toString() {
		return name;
	}
}

