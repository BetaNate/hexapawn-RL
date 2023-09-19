
import javafx.event.EventTarget;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    public static Board board;
    private boolean game;
    private static String mode;
    private User currPlayer;
    private static User player1;
    private static User player2;

     public Game(GridPane hexaPane, String mode) {
        board = new Board(hexaPane, BoardStates.init());
        this.game = true;
        this.mode = mode;
        
        //Choose mode depending on Menu input
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

/*
 * -----------------------
 *          MODES
 * -----------------------
 */
     public void slowMode() {
       player1 = new User("player", "white");
       player2 = new Robot("cpu", "black");

       currPlayer = player1;
       playTurn(board, currPlayer.toString());
    }
     

     public void fastMode() {
        player1 = new User("player", "white");
        player2 = new Robot("cpu", "black");

        currPlayer = player1;
        playTurn(board, currPlayer.toString());
     }

     public void Auto() {
        player1 = new Robot("cpu", "white");
        player2 = new Robot("cpu", "black");

        currPlayer = player1;
        playTurn(board, currPlayer.toString());
     }

/*
 * ---------------------------
 *        TURN ACTIONS
 * ---------------------------
 */
     //Cycle turns
     //To cycle a turn, the method is called recursively, swapping the currPlayer String.
     private static void playTurn(Board board, String currPlayer){
      if(currPlayer == "player") {
        for(Square square : board.getSquares()) {
          if(square.hasPiece) {
            Pawn pawn = (Pawn) square.getChildren().get(0);
            if(pawn.getColor() == Color.WHITE) { //User should always be white
              pawn.addEventHandler(MouseEvent.MOUSE_CLICKED, pawn.addHandler()); //Make user's pawns clickable
              //pawn.removeHandler();
            }
          }
        }
      }
       if(currPlayer == "cpu") {
          randomMove(board, (Robot)player2);
        }
     }

    //Event handler to select a move
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

    //Add event listeners for user's pawns
    public static void movePawnUser(List<Square> possibleMoves, Square square) {
        for(Square move:possibleMoves) {
         move.addEventHandler(MouseEvent.MOUSE_CLICKED, squareClicked);
        }
        board.removePawn(square);
    }

    //Make a move based on a selected bead
    //BROKEN: NOT MOVING PAWNS PROPERLY
    public static void movePawnAuto(Bead bead) {
        Square move = bead.getMove();
        Square origin = bead.getOrigin();
        if(move.hasPiece && move.getPiece() != origin.getPiece()) {
            Pawn pawn = board.removePawn(move);
            if(pawn.getColor() == Color.BLACK) {
              board.addPawn(move, new Pawn(Color.WHITE,move.getXPos(),move.getYPos(),30));
            }
            else {
              board.addPawn(move, new Pawn(Color.BLACK,move.getXPos(),move.getYPos(),30));
            }
        }
        else{
            board.addPawn(move, new Pawn(Color.BLACK,move.getXPos(),move.getYPos(),30));
          }
        board.removePawn(origin);
        playTurn(board, "player");
      }


    //Pick a move from a random bead from User's matchbox
    /*
    KNOWN BUG: For some reason not all board states are in the boards array, despite existing before
    being stored (see Robot.java for implementation). Temporarily, the program is unable to make moves
    for these missing boardstates
    */
    private static void randomMove(Board board, Robot cpu) {
      //Board[] boards = cpu.getBoards();
      Matchbox[] matchboxes = cpu.getMatchboxes();
      for(int i = 0; i < matchboxes.length; i++) {
        if(Arrays.deepEquals(board.getPieces(), matchboxes[i].getBoard())) {
          if (matchboxes[i].isEmpty()) {
            //Make function for win conditions
          }
          else {
            List<Bead> beads = matchboxes[i].getBeads();
            System.out.println(beads.toString());
           if(mode == "slow") {
              matchboxes[i].renderBeads();//MOVES NOT RENDERING
           }
            Bead chosenbead = beads.get(new Random().nextInt(beads.size()));
            movePawnAuto(chosenbead);
            break;
          }
        }
    /*    else {
          System.out.println("CANNOT RECOGNIZE BOARDSTATE");
          conthb n0;

          System.out.println(Arrays.deepToString(board.getPieces()));
          System.out.println(Arrays.deepToString(boards[i].getPieces()));
          System.out.println("-----------");
        }
        */
      }
    }
    
}
