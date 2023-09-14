import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;

public class Pawn extends Circle{

    private Color color;
    private int posX, posY;

    public Pawn(Color color, int posX, int posY, int radius) {
        super(radius);
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        setFill(this.color);
        setStroke(Color.BLACK);
    }

    public String getColor() {
        return this.color.toString();
    }

    public int getXPos() {
        return this.posX;
     }
  
     public int getYPos() {
        return this.posY;
     }

    @Override
    public String toString() {
        return this.color.toString();
    }
}
