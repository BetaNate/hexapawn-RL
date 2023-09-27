import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.layout.GridPane;


/*Class for CPU Instances
Input: name (should always be set to "cpu" in Game.java)
side: side that the cpu is playing
*/
public class Robot extends User{
    private ArrayList<Matchbox> matchboxes;
	private ArrayList<char[][]> boardstates = new ArrayList<char[][]>();
	private final String side;

	public Robot(String name, String side) {
		super(name, side);
		this.side = side;
		matchboxes = new ArrayList<Matchbox>();
	}

	public void addMatchbox(char[][] state) {
		Board boardState = new Board(new GridPane(), state);
		boardstates.add(state);
		matchboxes.add(new Matchbox(boardState, this.side));
	}

	public ArrayList<char[][]> getBoards() {
		return boardstates;
    }

	public ArrayList<Matchbox> getMatchboxes() {
		return matchboxes;
	}

	//Method to get the matchbox that contains the bead that was selected
	//Call removeBead(chosenBead) on the matchbox that contains the bead that was selected
	public Matchbox getMatchbox(Bead chosenBead) {
		for(Matchbox matchbox : matchboxes) {
			if(matchbox.getBeads().contains(chosenBead)) {
				return matchbox;
			}
		}
		return null;
	}
}
