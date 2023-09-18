import javafx.event.EventTarget;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.List;

public class Game {
    public static Board board;
    private boolean game;
    private User currPlayer;

     public Game(GridPane hexaPane) {
        board = new Board(hexaPane);
        this.game = true;
     }

     public void slowMode() {
       User player = new User("Player 1", "white");
       User cpu = new Robot("CPU", "black");

       currPlayer = player;
     
       if(currPlayer == player) {
        playTurn(board);
        currPlayer = cpu;
       }
       if(currPlayer == cpu) {
        currPlayer = player;
       }
      }
     

     public void fastMode() {

     }

     public void Auto() {

     }

     
     private void playTurn(Board board){
      for(Square square : board.getSquares()) {
          if(square.hasPiece) {
            Pawn pawn = (Pawn) square.getChildren().get(0);
            System.out.println(pawn.getColor());
            if(pawn.getColor() == Color.WHITE) {
              pawn.addEventHandler();
            }
          }
        }
     }

     public static void movePawn(List<Square> possibleMoves, Square square) {
      Pawn pawn = (Pawn)square.getChildren().get(0);
      for(Square move:possibleMoves) {
        move.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
              System.out.println("Moved");
              if(move.hasPiece) {
                board.removePawn(move);
                board.addPawn(move,pawn);
                board.removePawn(square);
              }
              else {
                board.addPawn(move,pawn);
                board.removePawn(square);
              }
              
              move.removeEventHandler(MouseEvent.ANY, this);
           //   pawn.removeEventHandler(null, null);
          }
        });
      }
    }

    
}
