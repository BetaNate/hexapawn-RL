/*
 * Author: Nathan J. Rowe
 * Class Description:
 * Class for Matchbox
 * Input: Board: boardstate of matchbox, side: side of matchbox
 * Matchbox is a list of beads
 * Used in Game.java for GUI and in Robot.java for matchbox moves
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
//For bead rendering
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Matchbox {
    private final ArrayList<Bead> beads = new ArrayList<Bead>();
    private final Board board;
    private final String side;
    private final ArrayList<Square> moves = new ArrayList<Square>();
    private final ArrayList<Line> lines = new ArrayList<Line>();
    private final ArrayList<Text> labels = new ArrayList<Text>();
    //Colors for beads
    //Red: 1 move, Green: 2 moves, Yellow: 3 moves, Blue: 4 moves
    private final List<Color> moveColors = new ArrayList<Color>(
        Arrays.asList(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE));

    public Matchbox(Board board, String side) {
        this.board = board;
        this.side = side;
        addBeads();
    }

    /*
     * Method to add a bead to the matchbox
     * Input: Bead to add
     * Usage: Used in addBeads()
     */
    private void addBead(Bead bead) {
        beads.add(bead);
    }

    /*
     * Method to add beads to the matchbox
     * Usage: Used in Matchbox constructor
     */
    private void addBeads() {
         Iterator<Color> colorIterator = moveColors.iterator(); //Iterator for colors
         for(Square square : this.board.getSquares()) {
            //Determine which player gets matchboxes
            if(this.side == "white") {
                if(square.getPieceType() == 'W') {
                    Pawn pawn = (Pawn) square.getChildren().get(0);
                    moves.addAll(pawn.getMoves(board, square));
                }
            }
            else {
                if(square.getPieceType() == 'B') {
                    Pawn pawn = (Pawn) square.getChildren().get(0);
                    moves.addAll(pawn.getMoves(board, square));
                }
            }

            //Add beads to matchbox
            //Assigns colors to beads based on the number of moves
            if(!moves.isEmpty()) {
                for(int i = 0; i < moves.size(); i++) {
                    addBead(new Bead(colorIterator.next(), square, moves.get(i)));
                }
            }
            moves.clear(); //Clear moves for next square
        }
    }

    /*
     * Method to remove a bead from the matchbox
     * Input: Bead to remove
     * Output: Removed bead
     * Usage: Used publicly for punishing HER
     */
    public Bead removeBead(Bead bead) {
        if (!beads.isEmpty()) {
            beads.remove(bead);
            return bead; // Remove and return the bad bead
        }
        return null; // Matchbox is empty
    }

    //Check for no beads
    public boolean isEmpty() {
        return beads.isEmpty();
    }

    /*
     * Method to render beads on the board
     * Input: Board to render beads on
     * Output: ArrayList of lines
     */
    public ArrayList<Line> renderBeads(Board board) {
        for(Bead bead : beads) {
            bead.renderBead(board);
            lines.add(bead.getLine());

            //Add probability labels
            double probability = 1.0 / beads.size() * 100;
            Text label = new Text(String.format("%.1f", probability) + "%");
            label.setManaged(false);
            label.setLayoutX(bead.getLine().getEndX() + 25);
            label.setLayoutY(bead.getLine().getEndY() + 50);
            label.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            labels.add(label);
            Main.overlap.getChildren().add(label);
        }
        return lines;
    }

    /*
     * Method to unrender beads on the board
     * Input: Board to unrender beads on
     */
    public void unRenderBeads() {
        for(Line line : lines) {
            Main.overlap.getChildren().remove(line);
        }

        for(Text label : labels) {
            Main.overlap.getChildren().remove(label);
        }
        labels.clear();
        lines.clear();
    }
/*
 * ------------------------
 *       Getters
 * ------------------------
 */
    public char[][] getBoard() {
        return this.board.getPieces();
    }

    public ArrayList<Bead> getBeads() {
        return this.beads;
    }
}