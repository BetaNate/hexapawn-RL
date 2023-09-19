
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
    private static User currPlayer;
    private static User player1;
    private static User player2;
    private final char[][] start = new char[][] {
      {'B','B','B'},
      {'E','E','E'},
      {'W','W','W'}
    };

     public Game(GridPane hexaPane, String mode) {
        board = new Board(hexaPane, start);
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
       playTurn(board, currPlayer);
    }
     

     public void fastMode() {
        player1 = new User("player", "white");
        player2 = new Robot("cpu", "black");

        currPlayer = player1;
        playTurn(board, currPlayer);
     }

     public void Auto() {
        player1 = new Robot("cpu", "white");
        player2 = new Robot("cpu", "black");

        currPlayer = player1;
        playTurn(board, currPlayer);
     }

/*
 * ---------------------------
 *        TURN ACTIONS
 * ---------------------------
 */
     //Cycle turns
     //To cycle a turn, the method is called recursively, swapping the currPlayer String.
     private static void playTurn(Board board, User currPlayer){
      if(currPlayer.toString() == "player") {
        for(Square square : board.getSquares()) {
          if(square.hasPiece) {
            Pawn pawn = (Pawn) square.getChildren().get(0);
            if(pawn.getColor() == currPlayer.getColor()) { //User should always be white
              pawn.addEventHandler(MouseEvent.MOUSE_CLICKED, pawn.addHandler()); //Make user's pawns clickable
              pawn.removeHandler();
            }
          }
        }
      }
       if(currPlayer.toString() == "cpu") {
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
            if(currPlayer.getColor() == Color.WHITE) {
              board.addPawn(square, new Pawn(Color.BLACK,square.getXPos(),square.getYPos(),30));
            }
            else{
              board.addPawn(square, new Pawn(Color.WHITE,square.getXPos(),square.getYPos(),30));
            }
          }
          else{
            board.addPawn(square, new Pawn(currPlayer.getColor(), square.getXPos(),square.getYPos(),30));
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

         currPlayer = player2;
         playTurn(board, currPlayer);
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
        Square move = board.getSquare(bead.getMove().getXPos(), bead.getMove().getYPos());
        Square origin = board.getSquare(bead.getOrigin().getXPos(), bead.getOrigin().getYPos());
        if(move.hasPiece && move.getPiece() != origin.getPiece()) {
            Pawn pawn = board.removePawn(move);
            if(currPlayer.getColor() == Color.BLACK) {
              board.addPawn(move, new Pawn(Color.WHITE,move.getXPos(),move.getYPos(),30));
            }
            else {
              board.addPawn(move, new Pawn(Color.BLACK,move.getXPos(),move.getYPos(),30));
            }
        }
        else{
            board.addPawn(move, new Pawn(currPlayer.getColor(), move.getXPos(),move.getYPos(),30));
          }
        board.removePawn(origin);
        currPlayer = player2;
        playTurn(board, currPlayer);
      }


    //Pick a move from a random bead from User's matchbox
    /*
    KNOWN BUG: For some reason not all board states are in the boards array, despite existing before
    being stored (see Robot.java for implementation). Temporarily, the program is unable to make moves
    for these missing boardstates
    */
    private static void randomMove(Board board, Robot cpu) {
      if(cpu.getBoards() == null) {
        cpu.addMatchbox(board.getPieces());
      }
      else if(!cpu.getBoards().contains(board.getPieces())) {
        cpu.addMatchbox(board.getPieces());
      }
      ArrayList<Matchbox> matchboxes = cpu.getMatchboxes();
      for(Matchbox matchbox : matchboxes) {
        if(Arrays.deepEquals(board.getPieces(), matchbox.getBoard())) {
          if (matchbox.isEmpty()) {
            //Make function for win conditions
          }
          else {
            List<Bead> beads = matchbox.getBeads();
            System.out.println(beads.toString());
            if(mode == "slow") {
              matchbox.renderBeads();//MOVES NOT RENDERING
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
