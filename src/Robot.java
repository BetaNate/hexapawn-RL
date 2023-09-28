/*
 * Author: Nathan J. Rowe
 * Class Description:
 * Class for CPU Instances
 * Input: name (should always be set to "cpu" in Game.java)
 * side: side that the cpu is playing
 */

import java.util.ArrayList;
import javafx.scene.layout.GridPane;

public class Robot extends User{
    private ArrayList<Matchbox> matchboxes;
    private ArrayList<char[][]> boardstates = new ArrayList<char[][]>();
    private final String side;

    public Robot(String name, String side) {
	    super(name, side);
	    this.side = side;
	    matchboxes = new ArrayList<Matchbox>();
    }

    //Method to add a matchbox to the robot's list of matchboxes
    //Input: state of the board (represented as a 2D char array of 'W', 'B', and 'E')
    public void addMatchbox(char[][] state) {
        Board boardState = new Board(new GridPane(), state);
        boardstates.add(state);
        matchboxes.add(new Matchbox(boardState, this.side));
    }

    //Method to get the robot's list of board states
    //Used in Game.java to creat matchboxes for the robot's side
    //Output: ArrayList of board states
    public ArrayList<char[][]> getBoards() {
	    return boardstates;
    }

    //Method to get the robot's list of matchboxes
    //Output: ArrayList of matchboxes
    public ArrayList<Matchbox> getMatchboxes() {
        return matchboxes;
    }

    //Method to get the matchbox that contains the bead that was selected
    //Input: Bead chosen for move
    //Output: Matchbox that contains the chosen bead
    public Matchbox getMatchbox(Bead chosenBead) {
        for(Matchbox matchbox : matchboxes) {
            if(matchbox.getBeads().contains(chosenBead)) {
                return matchbox;
            }
        }
        return null;
    }
}
