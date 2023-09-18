import javafx.event.EventTarget;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.List;

public class Game {
    public static Board board;
    private boolean game;
    private String mode;
    private User currPlayer;
    private static User player1;
    private static User player2;

     public Game(GridPane hexaPane, String mode) {
        board = new Board(hexaPane, BoardStates.init());
        this.game = true;
        this.mode = mode;
        
        if(this.mode == "slow"){
          slowMode();
        }
        else if(this.mode == "auto"){
          Auto();
        }
        else{
          fastMode();
        }
     }

     public void slowMode() {
       player1 = new User("player", "white");
       player2 = new Robot("cpu", "black");

       currPlayer = player1;
       playTurn(board, currPlayer.toString());
    }
     

     public void fastMode() {

     }

     public void Auto() {

     }

     
     private static void playTurn(Board board, String currPlayer){
      for(Square square : board.getSquares()) {
        if(currPlayer == "player") {
          if(square.hasPiece) {
            Pawn pawn = (Pawn) square.getChildren().get(0);
            if(pawn.getColor() == Color.WHITE) {
              pawn.addEventHandler(MouseEvent.MOUSE_CLICKED, pawn.addHandler());
              //pawn.removeHandler();
            }
          }
        }

        if(currPlayer == "cpu") {
          randomMove(board, player2);
        }
      }
     }

  static EventHandler<MouseEvent> squareClicked=new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
          Square square = (Square) event.getTarget();
          if(square.hasPiece && square.getPiece() != 'W') {
            Pawn pawn = board.removePawn(square);
            if(pawn.getColor() == Color.WHITE) {
              board.addPawn(square, new Pawn(Color.BLACK,square.getXPos(),square.getYPos(),30));
            }
            else{
              board.addPawn(square, new Pawn(Color.WHITE,square.getXPos(),square.getYPos(),30));
            }
          }
          else{
            board.addPawn(square, new Pawn(Color.WHITE, square.getXPos(),square.getYPos(),30));
          }
  
         square.setStyle("-fx-background-color: white;");

        //Remove evnt handlers from pawns
        //This does NOT effectively remove event handlers from all pawns
         for(Square location : board.getSquares()) {
          if(location.getPiece() == 'W') {
            Pawn piece = (Pawn) location.getChildren().get(0);
                piece.removeHandler();
            }
        }
         square.removeEventHandler(MouseEvent.MOUSE_CLICKED, this); // at the bottom

     
         playTurn(board, "cpu");
      }
  };
    public static void movePawn(List<Square> possibleMoves, Square square) {
      for(Square move:possibleMoves) {
        move.addEventHandler(MouseEvent.MOUSE_CLICKED, squareClicked);
      }
      board.removePawn(square);
    }

    private static void randomMove(Board board, User cpu) {

    }
    
}
