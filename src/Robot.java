import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.layout.GridPane;


/*Class for CPU Instances
Input: name (should always be set to "cpu" in Game.java)
side: side that the cpu is playing
*/
public class Robot extends User{
    private Matchbox[] matchboxes;
	private BoardStates boardstates = new BoardStates();
	private final String side;
	private Board[] boards;

	public Robot(String name, String side) {
		super(name, side);
		this.side = side;
		boards = new Board[boardstates.getStates().size()];
		matchboxes = new Matchbox[boardstates.getStates().size()];
		setMatchboxes(boardstates.getStates());
	}

	//Create instances of board for each boardstate
	private void setMatchboxes(ArrayList<char[][]> states) {
		for(int i = 0; i < states.size(); i++) {
			Board boardState = new Board(new GridPane(), states.get(i));
			boards[i] = boardState;
			matchboxes[i] = (new Matchbox(boardState, this.side));

			//System.out.println(Arrays.deepToString(states.get(i)));
			//System.out.println(Arrays.deepToString(boardState.getPieces()));
		}
	}

	public Board[] getBoards() {
        return boards;
    }
	public Matchbox[] getMatchboxes() {
		return matchboxes;
	}
}
