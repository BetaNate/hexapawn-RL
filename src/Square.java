/*
 * Author: Nathan J. Rowe
 * Class Description:
 * Class for position on board
 * Input: x: x position of square, y: y position of square
 * Square is a StackPane
 */
import javafx.scene.layout.StackPane;

public class Square extends StackPane {
    private final int x, y;
    private String name;
    private char pieceType; //Type of piece on square
    public boolean hasPiece; //Used to check if square has a piece


    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        this.hasPiece = false;
    }

/*
 * ------------------------
 *    Getters & Setters
 * ------------------------
 */
    public int getXPos() {
        return this.x;
    }

    public int getYPos() {
        return this.y;
    }

    public void setPiece(char piece) {
        this.pieceType = piece;
    }

    public char getPieceType() {
        return this.pieceType;
    }
   
    public Pawn getPawn() {
        Pawn pawn = (Pawn)getChildren().get(0);
        return pawn;
    }

    public void setName(String name) {
      this.name = name;
   }

    @Override
    public String toString() {
        String status;
        if(this.hasPiece) {
            status = "Full";
        }
        else {
            status = "Empty";
        }

        return "Square" + this.x + this.y + "is" + status;
    }
}

