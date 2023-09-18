import java.util.ArrayList;
import javafx.scene.layout.GridPane;


/*Class for CPU Instances
Input: name (should always be set to "cpu" in Game.java)
side: side that the cpu is playing
*/
public class Robot extends User{
    private ArrayList<Matchbox> matchboxes;
	private BoardStates boarstates = new BoardStates();
	private final String side;

	public Robot(String name, String side) {
		super(name, side);
		this.side = side;
		matchboxes = new ArrayList<Matchbox>();
		setMatchboxes(boarstates.getStates());
	}

	//Create instances of board for each boardstate
	private void setMatchboxes(ArrayList<char[][]> states) {
		for(int i = 0; i < states.size(); i++) {
			Board boardState = new Board(new GridPane(), states.get(i));
			matchboxes.add(new Matchbox(boardState, this.side));
		}
	}

	public ArrayList<Matchbox> getMatchboxes() {
		return matchboxes;
	}
}
