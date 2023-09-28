/*
 * Author: Nathan J. Rowe
 * Class Description:
 * Class for Pawn
 * Input: Color: Color of pawn, posX: x position of pawn, posY: y position of pawn, radius: radius of pawn
 * Pawn is a Circle
 * Used in Game.java for GUI and in Matchbox.java for matchbox boardstate
 */
//Pawn rendering
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
//Event handling for pawn
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
//Pawn moves
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Circle{

    private final Color color; //Color of pawn
    private final ArrayList<Square> moves = new ArrayList<Square>(); //List of available moves for pawn
    private final List<Move> availableMoves = new ArrayList<Move>(); //Enum list of available moves for pawn
    private int posX, posY; //Position of pawn
    //Index of forward, left diagonal, and right diagonal squares
    private int forwardIndex, leftDiagIndex, rightDiagIndex; 

    public Pawn(Color color, int posX, int posY, int radius) {
        super(radius);
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        setFill(this.color);
        setStroke(Color.BLACK);
    }

/*
*------------------------
*   Getters and Setters
*------------------------
*/
    public Color getColor() {
        return this.color;
    }

    public void setXPos(int posX) {
        this.posX = posX;
     }
  
     public void setYPos(int posY) {
        this.posY = posY;
     }

     public int getXPos() {
        return this.posX;
     }
  
     public int getYPos() {
        return this.posY;
     }

    //Get available moves for buttons
    public List<Pawn.Move> availableMoves() {
        return availableMoves;
    }

     /*
      * Method to add event handler to pawn
      * Output: EventHandler<MouseEvent> pawnClicked
      */
     public EventHandler<MouseEvent> addHandler(){
         EventHandler<MouseEvent> pawnClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //If moves is not empty, clear moves and availableMoves
                if(!moves.isEmpty()) {
                    moves.clear();
                    availableMoves.clear();
                }
                getMoves(Game.board, Game.board.getSquare(posX, posY)); //Get available moves
                renderMoves(); //Render available moves (by changing background color of squares)
                //Enable buttons in Game class
                Game.movePawnUserButtons(availableMoves, Game.board.getSquare(posX, posY));
                addEventHandler(MouseEvent.MOUSE_CLICKED, deselect()); //Add event handler to deselect pawn
                event.consume(); //Consume event
            }
        };
        return pawnClicked;
    }

    //Method to remove event handler from pawn
    public void removeHandler() {
        this.removeEventHandler(MouseEvent.MOUSE_CLICKED,addHandler());
    }

    //Event handler to deselect pawn
    //Output: EventHandler<MouseEvent> deselect
    public EventHandler<MouseEvent> deselect() {
        EventHandler<MouseEvent> deselect = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.SECONDARY) { //If right click, deselect pawn
                    Game.disableMoves(); //Disable buttons in Game class
                    removeEventHandler(MouseEvent.MOUSE_CLICKED, this); //Remove event handler
                }
            }
        };
      return deselect;
    }


    //Create enum for available moves with FORWARD, LEFTDIAG, RIGHTDIAG
    //if forwardIndex is empty, add FORWARD to moves
    //if leftDiagIndex is not empty, add LEFTDIAG to moves
    //if rightDiagIndex is not empty, add RIGHTDIAG to moves
    public enum Move {
        FORWARD, LEFTDIAG, RIGHTDIAG;
    }

     /*Get available moves for the pawn
      *Input: Board: Board with pawn, square: square pawn is on
      *Output: ArrayList<Square> moves
      *Usage: Used to get moves for a pawn
      */
     public ArrayList<Square> getMoves(Board board, Square square) {

        //White is always on bottom, black is always on top
        //Check if color is white or black
        if(color == Color.WHITE) {
            //Get index of left diagonal, right diagonal, and forward squares for white pawn
            leftDiagIndex = (board.getSquares().indexOf(square) - 4);
            rightDiagIndex = (board.getSquares().indexOf(square) - 2);
            forwardIndex = (board.getSquares().indexOf(square) - 3);
        }
        else if(color == Color.BLACK) {
            //Get index of left diagonal, right diagonal, and forward squares for black pawn
            leftDiagIndex = (board.getSquares().indexOf(square) + 4);
            rightDiagIndex = (board.getSquares().indexOf(square) + 2);
            forwardIndex = (board.getSquares().indexOf(square) + 3);
        }

        // Check if leftDiagIndex is within the valid range
        if (leftDiagIndex >= 0 && leftDiagIndex < board.getSquares().size()) {
            Square leftDiagonal = board.getSquares().get(leftDiagIndex);
            // Check if leftDiagonalSquare is in different row & has a piece
            if ((Math.abs(leftDiagonal.getYPos() - square.getYPos())) == 1 && leftDiagonal.hasPiece) {
                if(leftDiagonal.getPieceType() != square.getPieceType()) {
                    // Add leftDiagonalSquare to moves
                    // Add LEFTDIAG to availableMoves
                    moves.add(leftDiagonal);
                    availableMoves.add(Move.LEFTDIAG);
                }
            }
        }
        // Check if rightDiagIndex is within the valid range
        if (rightDiagIndex >= 0 && rightDiagIndex < board.getSquares().size()) {
            Square rightDiagonal = board.getSquares().get(rightDiagIndex);
            // Check if rightDiagonalSquare is not null & has piece
            if ((Math.abs(rightDiagonal.getYPos() - square.getYPos())) == 1 && rightDiagonal.hasPiece) {
                if(rightDiagonal.getPieceType() != square.getPieceType()){
                    // Add rightDiagonalSquare to moves
                    // Add RIGHTDIAG to availableMoves
                    moves.add(rightDiagonal);
                    availableMoves.add(Move.RIGHTDIAG);
                }
            }
        }
        // Check if forwardIndex is within the valid range
        if (forwardIndex >= 0 && forwardIndex < board.getSquares().size()) {
            Square forward = board.getSquares().get(forwardIndex);
            // Check if forwardSquare does not have a piece
            //Check for empty piece type (double confirmation)
            if (!forward.hasPiece && forward.getPieceType() == 'E') {
                // Add forwardSquare to moves
                // Add FORWARD to availableMoves
                moves.add(forward);
                availableMoves.add(Move.FORWARD);
            }
        }
        return moves;
    }

     //Display available moves
     //Change background color of squares to silver
     //Usage: Used to display available moves for user's pawn
     public void renderMoves() {
        for(Square square : moves) {
            square.setStyle("-fx-background-color: silver;");
        }
     }

     @Override
     public String toString() {
        return "Pawn" + this.color.toString() +this.posX + this.posY;
     }
}