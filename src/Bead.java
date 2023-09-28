/*
 * Author: Nathan J. Rowe
 * Class Description:
 * Class for Bead
 * Input: Color: Color of bead, origin: origin square of bead, move: move square of bead
 * Bead is represented as a line in slow mode
 * Used in Game.java for GUI and in Matchbox.java for matchbox moves
 */

//For rendering beads
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Bead {
    private final Color color;
    private final Square move;
    private final Square origin;
    private Line line;

    public Bead(Color color, Square origin, Square move) {
        this.color = color;
        this.origin = origin;
        this.move = move;
    }

/*
 * ------------------------
 *        Getters
 * ------------------------
 */
    public Color getColor() {
        return this.color;
    }

    public Square getMove() {
        return this.move;
    }

    public Square getOrigin() {
        return this.origin;
    }

    public Line getLine() {
        return this.line;
    }

    /*
     * Method to render bead
     * Input: Board to render bead on
     * Renders bead as a line from origin square to move square
     */
    public void renderBead(Board board) {
        line = new Line(); //Initialize line
        //Get start and end square
        Square start = board.getSquare(this.origin.getXPos(), this.origin.getYPos());
        Square end = board.getSquare(this.move.getXPos(), this.move.getYPos());
        //Get start and end coordinates
        line.setStartX(start.getLayoutX());
        line.setStartY(start.getLayoutY());
        line.setEndX(end.getLayoutX());
        line.setEndY(end.getLayoutY());
        //Set line properties
        line.setStroke(this.color);
        line.setStrokeWidth(10);
        //Add line to overlap pane
        line.setManaged(false);
        line.setTranslateX(start.getWidth()/2); //Set line to center of square
        line.setTranslateY(start.getHeight()/2);
        Main.overlap.getChildren().add(line);
    }
}
