import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Circle{

    private final Color color;
    private int posX, posY;
    private int forwardIndex, leftDiagIndex, rightDiagIndex;
    private ArrayList<Square> moves = new ArrayList<Square>();
    private List<Move> availableMoves = new ArrayList<Move>();

    public Pawn(Color color, int posX, int posY, int radius) {
        super(radius);
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        setFill(this.color);
        setStroke(Color.BLACK);
    }

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

     public EventHandler<MouseEvent> addHandler(){
         EventHandler<MouseEvent> pawnClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!moves.isEmpty()) {
                    moves.clear();
                    availableMoves.clear();
                }
                getMoves(Game.board, Game.board.getSquare(posX, posY));
                renderMoves();
                Game.movePawnUserButtons(availableMoves, Game.board.getSquare(posX, posY));
                addEventHandler(MouseEvent.MOUSE_CLICKED, deselect());
                event.consume();
            }
        };
        return pawnClicked;
    }

    public void removeHandler() {
        this.removeEventHandler(MouseEvent.MOUSE_CLICKED,addHandler());
    }

    //Deselects pawn
    public EventHandler<MouseEvent> deselect() {
        EventHandler<MouseEvent> deselect = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton() == MouseButton.SECONDARY) {
                    Game.disableMoves();
                    removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                }
            }
        };
      return deselect;
    }


    //Create enum for available moves with FORWARD, LEFTDIAG, RIGHTDIAG
    //if forwardIndex is empty, add FORWARD to moves
    //if leftDiagIndex is not empty, add LEFTDIAG to moves
    //if rightDiagIndex is not empty, add RIGHTDIAG to moves
    //return moves
    public enum Move {
        FORWARD, LEFTDIAG, RIGHTDIAG;
    }

     //Get available moves for the pawn
     //Input: Board: Board with pawn, square: square pawn is on
     public ArrayList<Square> getMoves(Board board, Square square) {

        if(color == Color.WHITE) {
            leftDiagIndex = (board.getSquares().indexOf(square) - 4);
            rightDiagIndex = (board.getSquares().indexOf(square) - 2);
            forwardIndex = (board.getSquares().indexOf(square) - 3);
        }
        else if(color == Color.BLACK) {
            leftDiagIndex = (board.getSquares().indexOf(square) + 4);
            rightDiagIndex = (board.getSquares().indexOf(square) + 2);
            forwardIndex = (board.getSquares().indexOf(square) + 3);
        }

        // Check if leftDiagIndex is within the valid range
        if (leftDiagIndex >= 0 && leftDiagIndex < board.getSquares().size()) {
            Square leftDiagonal = board.getSquares().get(leftDiagIndex);
            // Check if leftDiagonalSquare is in different row
            if ((Math.abs(leftDiagonal.getYPos() - square.getYPos())) == 1 && leftDiagonal.hasPiece) {
                if(leftDiagonal.getPieceType() != square.getPieceType()) {
                    moves.add(leftDiagonal);
                    availableMoves.add(Move.LEFTDIAG);
                }
            }
        }
        if (rightDiagIndex >= 0 && rightDiagIndex < board.getSquares().size()) {
            Square rightDiagonal = board.getSquares().get(rightDiagIndex);
            // Check if leftDiagonalSquare is not null
            if ((Math.abs(rightDiagonal.getYPos() - square.getYPos())) == 1 && rightDiagonal.hasPiece) {
                if(rightDiagonal.getPieceType() != square.getPieceType()){
                    moves.add(rightDiagonal);
                    availableMoves.add(Move.RIGHTDIAG);
                }
            }
        }

        if (forwardIndex >= 0 && forwardIndex < board.getSquares().size()) {
            Square forward = board.getSquares().get(forwardIndex);
            // Check if leftDiagonalSquare is not null
            if (!forward.hasPiece) {
                moves.add(forward);
                availableMoves.add(Move.FORWARD);
            }
        }
        return moves;
     }

      public List<Pawn.Move> availableMoves() {
            return availableMoves;
        }

     //Display available moves
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