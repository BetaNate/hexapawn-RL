/*
 * Author: Nathan J. Rowe
 * Class Description:
 * Class for Board
 * Input: GridPane (GUI container for board)
 * state: 2D char array of 'W', 'B', and 'E' (empty)
 * Board is a GridPane of Squares (StackPanes with a piece)
 * Used in Game.java for GUI and in Matchbox.java for matchbox boardstate
 */

import javafx.scene.layout.*;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import java.util.Arrays;//For deepEquals in deepContains method

public class Board extends GridPane{
    private final GridPane board;
    private final ArrayList<Square> squares = new ArrayList<>();//List of squares on board
    private final int size = 3;//Size of board (3x3)
    private final char[][] state; //Board state


    public Board(GridPane board, char[][] state) {
        this.board = board;
        this.state = state;
        generateBox(this.state);
    }

    /*
     * Method to generate board
     * Input: 2D char array of 'W', 'B', and 'E' (empty)
     * Creates a GridPane of Squares (StackPanes with a piece)
     * Used in Game.java when resetting board
     */
    public void generateBox(char[][] state) {
        if (!board.getChildren().isEmpty()) {
                board.getChildren().clear();
                squares.clear();
        }
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                Square square = new Square(i,j);
                square.setName("Position" + i + j);
                square.setPiece(state[i][j]);
                square.setPrefHeight(100);
                square.setPrefWidth(100);
                square.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                square.setStyle("-fx-background-color: white;");
                this.board.add(square,j,i,1,1);
                squares.add(square);
            }
        }
        addPawns();
    }
    
    /*
     * Method to add a pawn to a square
     * Input: Square to add pawn to, Pawn to add
     * Adds a pawn to a square and sets the square's piece type
     * Used in Game.java when moving a pawn
     */
    public void addPawn(Square square, Pawn pawn) {
        square.getChildren().add(pawn);
        square.hasPiece = true; //Set square to have a piece
        if(pawn.getColor() == Color.WHITE) {
            square.setPiece('W');
        }
        else{
            square.setPiece('B');
        }
    }

    /*
     * Method to remove a pawn from a square
     * Input: Square to remove pawn from
     * Removes a pawn from a square and sets the square's piece type to empty
     */
    public Pawn removePawn(Square square) {
        if(!square.hasPiece) {
            return null; //No piece to remove
        }
        //Pawn is always the first child of a square
        Pawn pawn = (Pawn)square.getChildren().get(0);
        //Remove pawn from square
        square.getChildren().remove(0);
        square.hasPiece = false;//Set square to not have a piece
        square.setPiece('E');//Set square to empty
        return pawn;
      }

    /*
     * Method to fill board with pawns
     * Used in generateBox method
     */
    private void addPawns() {
        for(Square square : squares) {
            if(square.hasPiece) {
                continue; //Skip if square already has a piece
            }
            if(square.getPieceType() == 'B') {
                addPawn(square, new Pawn(Color.BLACK, square.getXPos(), square.getYPos(), 30));
            }
            if(square.getPieceType() == 'W') {
                addPawn(square, new Pawn(Color.WHITE, square.getXPos(), square.getYPos(), 30));
            }
        }
    }

    /*
     * Method to get the string representation of the board
     * Output: String representation of the board
     * Used in Matchbox.java to get the board state
     * Used in Game.java to check if a board state has been seen before
     */
    public char[][] getPieces() {
        char[][] pieces = new char[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                pieces[i][j] = getSquare(i, j).getPieceType(); //Get piece type of square
            }
        }
        return pieces;
    }
    
    /*
     * Method to get the square at a position
     * Input: x and y coordinates of square
     * Output: Square at position (x,y)
     * Used in Game.java to get the square that was clicked (among other uses)
     */
    public Square getSquare(int x, int y) {
        for (Square square : squares) {
            if(board.getRowIndex(square) == x && board.getColumnIndex(square) == y) {
                return square; //Return square at position (x,y)
            }
        }
        return null; //No square at position (x,y)
    }

    //Check if boardstate contains a value
    //Used to check if a boardstate has certain pieces
    public boolean deepContains(char[][] pieces, char val) {
        boolean hasVal = false;

        for(int i = 0; i < pieces.length; i++) {
            for(int j = 0; j < pieces.length; j++) {
                if(pieces[i][j] == val) {
                    hasVal = true;
                }
            }
        }
        return hasVal;
    }

    /*
     * Method to check if a boardstate has been seen before
     * Input: ArrayList of boardstates, boardstate to check
     * Output: True if boardstate has been seen before, false otherwise
     * Used in Game.java for adding matchboxes to robot
     */
    public boolean deepContains(ArrayList<char[][]> states, char[][] val) {
        boolean hasVal = false;

        for(char[][] state : states) {
            if(Arrays.deepEquals(state, val)) {
                hasVal = true;
            }
        }
        return hasVal;
    }

    /*
     * Method to get ArrayList representation of board
     * Output: ArrayList of Squares
     * Used for looping through squares & comparisons
     */
    public ArrayList<Square> getSquares() {
       return squares;
    }
}
