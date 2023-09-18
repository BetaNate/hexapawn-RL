import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Bead {
    private Color color;
    private Square move;

    public Bead(Color color, Square move) {
        this.color = color;
        this.move = move;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Square getMoveType() {
        return this.move;
    }

    public void setMoveType(Square moveType) {
        this.move = moveType;
    }

    public void renderBead(Square move) {
        move.setStyle("-fx-background-color:" + this.color.toString() + ";");
    }


}
