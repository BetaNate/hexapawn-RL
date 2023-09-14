import javafx.geometry.Insets;
import javafx.scene.layout.*;
import java.util.ArrayList;

public class Matchbox {
    private final GridPane board;
    public ArrayList<MatchboxSquare> squares = new ArrayList<>();
    private final int size = 3;


    public Board(GridPane board) {
        this.board = board;
    }

    private void generateBox(GridPane board) {

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                MatchboxSquare square = new MatchboxSquare(i,j);
                square.setName("Position" + i + j);
                square.setPrefHeight(100);
                square.setPrefWidth(100);

                square.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                board.add(square,i,j,1,1);
                squares.add(square);
            }
        }
        addBeads();
    }

    private void addPawn(MatchboxSquare square, Pawn pawn) {
        square.getchildren().add(pawn);
        square.hasPiece = true;
    }

    private void addPawns() {
        for(MatchboxSquare square : squares) {
            if(square.hasPiece) {
                continue;
            }
            if(square.y == 0) {
                addPawn(square, new Bead("white", square.x, square.y, 50));
            }
            if(square.y == size - 1) {
                addPawn(square, new Bead("black", square.x, square.y, 50));
            }
        }
    }
}
