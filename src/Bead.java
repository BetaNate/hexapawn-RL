import javafx.scene.paint.Color;

public class Bead {
    private Color color;
    private Square move;
    private Square origin;

    public Bead(Color color, Square origin, Square move) {
        this.color = color;
        this.origin = origin;
        this.move = move;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Square getMove() {
        return this.move;
    }

    public Square getOrigin() {
        return this.origin;
    }

    public void setMove(Square moveType) {
        this.move = moveType;
    }

    public void renderBead(Square move) {
        move.setStyle("-fx-background-color: silver;");
    }


}
