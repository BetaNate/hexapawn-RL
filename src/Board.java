import javafx.scene.layout.*;
import java.util.ArrayList;
import javafx.scene.paint.Color;

public class Board extends GridPane{
    private final GridPane board;
    private final ArrayList<Square> squares = new ArrayList<>();
    private final int size = 3;
    private final char[][] state;


    public Board(GridPane board, char[][] state) {
        this.board = board;
        this.state = state;
        generateBox(this.board, this.state);
    }

    private void generateBox(GridPane board, char[][] state) {

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                Square square = new Square(i,j);
                square.setName("Position" + i + j);
                square.setPiece(state[i][j]);
                square.setPrefHeight(100);
                square.setPrefWidth(100);
                square.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                board.add(square,j,i,1,1);
                squares.add(square);
            }
        }
        addPawns();
    }
 
    public void addPawn(Square square, Pawn pawn) {
        square.getChildren().add(pawn);
        square.hasPiece = true;
        if(pawn.getColor() == Color.WHITE) {
            square.setPiece('W');
        }
        else{
            square.setPiece('B');
        }
    }

    public Pawn removePawn(Square square) {
        if(!square.hasPiece) {
            return null;
        }
        Pawn pawn = (Pawn)square.getChildren().get(0);
        square.getChildren().remove(0);
        square.hasPiece = false;
        square.setPiece('E');
        return pawn;
      }

    private void addPawns() {
        for(Square square : squares) {
            if(square.hasPiece) {
                continue;
            }
            if(square.getPieceType() == 'B') {
                addPawn(square, new Pawn(Color.BLACK, square.x, square.y, 30));
            }
            if(square.getPieceType() == 'W') {
                addPawn(square, new Pawn(Color.WHITE, square.x, square.y, 30));
            }
        }
    }

    public char[][] getPieces() {
        char[][] pieces = new char[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                pieces[i][j] = getSquare(i, j).getPieceType();
            }
        }
        return pieces;
    }
    
    public Square getSquare(int x, int y) {
        for (Square square : squares) {
            if(board.getRowIndex(square) == x && board.getColumnIndex(square) == y) {
                return square;
            }
        }
    
        return null;
    }

    public ArrayList<Square> getSquares() {
       return squares;
    }
}
