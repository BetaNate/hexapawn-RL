
import javafx.event.EventTarget;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class Game {
    public static Board board;
    private static boolean win;
    private static String mode;
    private static User currPlayer;
    private static User winner;
    private static User player1;
    private static User player2;
    private static List<Square> moves = new ArrayList<Square>();
    private static Bead chosenBead;
    private final static char[][] start = new char[][] {
      {'B','B','B'},
      {'E','E','E'},
      {'W','W','W'}
    };
     static Button forward = new Button("Forward");
     static Button left = new Button("Left");
     static Button right = new Button("Right");

    static EventHandler<MouseEvent> clickHandler;

     public Game(GridPane hexaPane, String mode) {
        board = new Board(hexaPane, start);
        this.win = false;
        this.mode = mode;
        forward.setDisable(true);
        left.setDisable(true);
        right.setDisable(true);
        
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

/*
 * --------------------------
 *     MOVEMENT HANDLER
 * --------------------------
 */

    //Creates buttons to move a selected pawn forward, left or right on user's turn
    //Disables buttons after selected
    //Similar to moveHandler, but does not use squares for event actions

    public static void movePawnUserButtons(List<Pawn.Move> availableMoves, Square square) {
      //Check which moves are available
      //If move is available, enable button for it
      //If move is not available, disable button for it
      for(Pawn.Move move: availableMoves) {
        if(move == Pawn.Move.FORWARD) {
          forward.setDisable(false);
        }
        if(move == Pawn.Move.LEFTDIAG) {
          left.setDisable(false);
        }
        if(move == Pawn.Move.RIGHTDIAG) {
          right.setDisable(false);
        }
      }

      forward.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
        Square move = board.getSquare(square.getXPos() - 1, square.getYPos());
        System.out.println(move.toString());
        Square origin = board.getSquare(square.getXPos(), square.getYPos());
        if(move.hasPiece && move.getPieceType() != origin.getPieceType()) {
          Pawn pawn = board.removePawn(move);
        }
        board.addPawn(move, new Pawn(currPlayer.getColor(), move.getXPos(),move.getYPos(),30));
        board.removePawn(origin);
        //Check winner before next turn
        checkWin();
        if(win) {
          disableMoves();
          return;
        }
        else {
        disableMoves();
        currPlayer = player2;
        playTurn(board, currPlayer);
        }
      }
    });

    left.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        Square move = board.getSquare(square.getXPos() - 1, square.getYPos() - 1);
        Square origin = board.getSquare(square.getXPos(), square.getYPos());
        if(move.hasPiece && move.getPieceType() != origin.getPieceType()) {
          board.removePawn(move);
        }
        board.addPawn(move, new Pawn(currPlayer.getColor(), move.getXPos(),move.getYPos(),30));
        board.removePawn(origin);
        //Check winner before next turn
        checkWin();
        if(win) {
          disableMoves();
          return;
        }
        else {
        disableMoves();
        currPlayer = player2;
        playTurn(board, currPlayer);
        }
      }
    });

    right.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        Square move = board.getSquare(square.getXPos() - 1, square.getYPos() + 1);
        Square origin = board.getSquare(square.getXPos(), square.getYPos());
        if(move.hasPiece && move.getPieceType() != origin.getPieceType()) {
          board.removePawn(move);
        }
        board.addPawn(move, new Pawn(currPlayer.getColor(), move.getXPos(),move.getYPos(),30));
        board.removePawn(origin);
        //Check winner before next turn
        checkWin();
        if(win) {
          disableMoves();
          return;
        }
        else {
        disableMoves();
        currPlayer = player2;
        playTurn(board, currPlayer);
        }
      }
    });
    }


    //Remove event handlers for user's pawns
    //This is called after a move is made
    //Will set the style for each square back to white
    //Need an alternate option, disableMoves() is not working
    public static void disableMoves() {
      for(Square square : board.getSquares()) {
        square.setStyle("-fx-background-color: white;");
      }
      if(!moves.isEmpty()) {
        moves.clear();
      }
      forward.setDisable(true);
      left.setDisable(true);
      right.setDisable(true);
    }

    //Make a move based on a selected bead
    public static void movePawnAuto(Bead bead) {
        Square move = board.getSquare(bead.getMove().getXPos(), bead.getMove().getYPos());
        Square origin = board.getSquare(bead.getOrigin().getXPos(), bead.getOrigin().getYPos());
        if(move.hasPiece && move.getPieceType() != origin.getPieceType()) {
            board.removePawn(move);
        }
        board.addPawn(move, new Pawn(currPlayer.getColor(), move.getXPos(),move.getYPos(),30));
        board.removePawn(origin);
        //Check winner before next turn
        checkWin();
        if(win) {
          return;
        }
        else {
        currPlayer = player1;
        playTurn(board, currPlayer);
        }
      }


    //Pick a move from a random bead from User's matchbox
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
            if(cpu == player1) {
              winner(player2.getSide());
            }
            else if(cpu == player2) {
              winner(player1.getSide());
            }
          }
          else {
            List<Bead> beads = matchbox.getBeads();
            System.out.println(beads.toString());
            if(mode == "slow") {
              matchbox.renderBeads(board); //Render beads on board
              

            }  

            chosenBead = beads.get(new Random().nextInt(beads.size()));
            movePawnAuto(chosenBead);
            break;
            }
          }
      }
    }

    //Checks win conditions for winning boardstate
    private static void checkWin() {
      //Check board for available moves
      boolean hasMoves = false;
      char[][] currState = board.getPieces();

      //Check for available moves on the board
      //If no moves, winner = currPlayer
      for(Square square : board.getSquares()) {
        if(square.hasPiece) {
          Pawn pawn = (Pawn) square.getChildren().get(0);
          if(pawn.getColor() == currPlayer.getColor()) {
            moves = pawn.getMoves(board, square);
            System.out.println(moves.toString());
            if(!moves.isEmpty()) {
              hasMoves = true;
            }
          }
        }
      }
      if(hasMoves == false) {
        winner(currPlayer.getSide());
      }
      //Check for pawns
      //if no white pawns, winner = black
      else if(!board.deepContains(currState, 'W')) {
        winner("black");
      }
      //if no black pawns, winner = white
      else if(!board.deepContains(currState, 'B')) {
        winner("white");
      }
      //if no pawns, winner = currPlayer
      else if(!board.deepContains(currState, 'W') && !board.deepContains(currState, 'B')) {
        winner(currPlayer.getSide());
      }
      //if pawn reaches other side of board, winner = currPlayer
      else {
        for(Square square : board.getSquares()) {
          if(square.hasPiece) {
            Pawn pawn = (Pawn) square.getChildren().get(0);
            if(pawn.getColor() == currPlayer.getColor()) {
              if(currPlayer.getSide() == "black") {
                if(pawn.getXPos() == 2) {
                  winner(currPlayer.getSide());
                }
              }
              else if(currPlayer.getSide() == "white") {
                if(pawn.getXPos() == 0) {
                  winner(currPlayer.getSide());
                }
              }
            }
          }
        }
      }
    }

    //Assigns winning user
    private static User winner(String winColor) {
      winner = null;
      if(player1.getSide() == winColor) {
        System.out.println(winColor + "wins");
        win = true;
        winner = player1;
      }
      else if(player2.getSide() == winColor) {
        System.out.println(winColor + "wins");
        win = true;
        winner = player2;
      }
      else {
        System.out.println("Winner cannot be Assigned");
      }
      return winner;
    }

    //Reset the board for a new game
    public static void reset() {
      //Check if loser is a robot
      //If so, punish the robot
      if(winner == player1) {
        if(player2 instanceof Robot) {
          punish((Robot)player2);
        }
      }
      else if(winner == player2) {
        if(player1 instanceof Robot) {
          punish((Robot)player1);
        }
      }
      //Reset board
      win = false;
      //Remove all pawns from board
      for(Square square : board.getSquares()) {
        if(square.hasPiece) {
          board.removePawn(square);
        }
      }
      //Disable moves
      disableMoves();
      //Empty moves
      moves.clear();
      //Generate new board
      board.generateBox(start);
      //Reset currPlayer
      currPlayer = player1;
      winner =  null;
      playTurn(board, currPlayer);
    }

    //Punish the robot for losing
    //Remove the last selected bead from the matchbox
    private static void punish(Robot cpu) {
      if(chosenBead != null) {
        System.out.println("Punishing " + cpu.toString() + " for losing: " + chosenBead.toString() + " removed");
        cpu.getMatchbox(chosenBead).removeBead(chosenBead);
      }
    }
}
