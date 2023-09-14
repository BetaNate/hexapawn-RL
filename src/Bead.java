import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Bead {
    private Color color;
    private String move;

    public Bead(Color color, String move) {
        this.color = color;
        this.move = move;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getMoveType() {
        return this.move;
    }

    public void setMoveType(String moveType) {
        this.move = moveType;
    }

    public Line renderBead(Pawn start, Square end) {
        Line beadLine = new Line(start.getXPos(), start.getYPos(), end.getXPos(), end.getYPos());
        beadLine.setStroke(this.color);

        return beadLine;
    }


}
