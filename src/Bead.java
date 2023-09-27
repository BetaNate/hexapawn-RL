import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Bead {
    private Color color;
    private Square move;
    private Square origin;
    private Line line;

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

    public Line getLine() {
        return this.line;
    }

    public void renderBead(Board board) {
        line = new Line();
        Square start = board.getSquare(this.origin.getXPos(), this.origin.getYPos());
        Square end = board.getSquare(this.move.getXPos(), this.move.getYPos());
        line.setStartX(start.getLayoutX());
        line.setStartY(start.getLayoutY());
        line.setEndX(end.getLayoutX());
        line.setEndY(end.getLayoutY());
        line.setStroke(this.color);
        line.setFill(this.color);
        line.setStrokeWidth(10);
        line.setManaged(false);
        line.setTranslateX(start.getWidth()/2);
        line.setTranslateY(start.getHeight()/2);
        Main.overlap.getChildren().add(line);
        //move.setStyle("-fx-background-color: " + this.color + ";");
    }

    public void unRenderBead() {
        Main.overlap.getChildren().remove(line);
    }

}
