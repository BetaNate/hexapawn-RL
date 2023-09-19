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
	private ArrayList<Board> boards;

	public Robot(String name, String side) {
		super(name, side);
		this.side = side;
		boards = new ArrayList<Board>();
		matchboxes = new ArrayList<Matchbox>();
	}

	public void addMatchbox(char[][] state) {
		Board boardState = new Board(new GridPane(), state);
		boards.add(boardState);
		matchboxes.add(new Matchbox(boardState, this.side));
	}

	public ArrayList<char[][]> getBoards() {
		if(boards.isEmpty()) {
			return null;
		}

        for(Board board: boards) {
			boardstates.add(board.getPieces());
		}

		return boardstates;
    }
	public ArrayList<Matchbox> getMatchboxes() {
		return matchboxes;
	}
}
