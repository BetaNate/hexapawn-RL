import javafx.scene.layout.*;
import java.util.ArrayList;
import javafx.scene.paint.Color;

public class Board {
    public final GridPane board;
    public ArrayList<Square> squares = new ArrayList<>();
    private final int size = 3;
    private final char[][] initState = BoardStates.init();


    public Board(GridPane board) {
        this.board = board;
        generateBox(this.board);
    }

    private void generateBox(GridPane board) {

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                Square square = new Square(i,j);
                square.setName("Position" + i + j);
                square.setPiece(initState[i][j]);
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
 
    private void addPawn(Square square, Pawn pawn) {
        square.getChildren().add(pawn);
        square.hasPiece = true;
    }

    private void addPawns() {
        for(Square square : squares) {
            if(square.hasPiece) {
                continue;
            }
            if(square.getPiece() == 'B') {
                addPawn(square, new Pawn(Color.BLACK, square.x, square.y, 30));
            }
            if(square.getPiece() == 'W') {
                addPawn(square, new Pawn(Color.WHITE, square.x, square.y, 30));
            }
        }
    }
}
