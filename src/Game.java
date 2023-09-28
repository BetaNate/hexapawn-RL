/*
 * Author: Nathan J. Rowe
 * Class Description: 
 * Class for Game instance
 * Input: GridPane: GUI container for board, mode: mode of game
 * Mode set in Main.java
 * Initializes board and players
 * Allows players to take turns
 */

//For event handling
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
//For GUI
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
//For pawn moves
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    //Board instance
    //Public for access in methods (such as Pawn's getMoves())
    public static Board board;
    //Number of games for auto mode
    //Reduces for each game player
    private static int numGames;
    //Check for win
    private static boolean win;
    private static String mode;
    //Current player
    //Modifies for each turn
    private static User currPlayer;
    //Assigned winner
    private static User winner;
    //Players in game
    private static User player1;
    private static User player2;
    //List of available moves
    //Mutates in checkWin()
    private static List<Square> moves = new ArrayList<Square>();
    //Chosen bead for HER movement
    private static Bead chosenBead;
    //Skip button for slow mode
    private final static Button skip = new Button("Choose Random Bead");
    //Winner Text Box
    //Recta
    private final static Text winnerText = new Text();
    //Starting board
    private final static char[][] start = new char[][] {
      {'B','B','B'},
      {'E','E','E'},
      {'W','W','W'}
    };
    //Buttons for user movement
    //Added to GUI in Main.java
    public final static Button forward = new Button("Forward");
    public final static Button left = new Button("Left");
    public final static Button right = new Button("Right");

    //Constructor
    public Game(GridPane hexaPane, String mode) {
        board = new Board(hexaPane, start);
        this.win = false;
        this.mode = mode;
        //Buttons start disabled
        forward.setDisable(true);
        left.setDisable(true);
        right.setDisable(true);
        
        //Choose mode depending on Menu input
        if(this.mode == "auto"){
          player1 = new Robot("cpu", "white");
          player2 = new Robot("cpu", "black");
        }
        else {
          player1 = new User("player", "white");
          player2 = new Robot("cpu", "black");
        }

        //Start game
        currPlayer = player1;
        playTurn(board, currPlayer);
    }


     /*
      * Method for setting number of games in auto mode
      */
    public static void setNumGames(int amt) {
        numGames = amt;
    }
/*
 * ---------------------------
 *        TURN ACTIONS
 * ---------------------------
 */
    /*Method for playing a turn
     *To cycle a turn, the method is called recursively, swapping the currPlayer String.
     *Input: Board, current user
     */
    private static void playTurn(Board board, User currPlayer){
        //Check if currPlayer is a User
        if(currPlayer.toString() == "player") {
            for(Square square : board.getSquares()) {
                if(square.hasPiece) {
                    Pawn pawn = (Pawn) square.getChildren().get(0);
                    //Check if pawn is user's
                    if(pawn.getColor() == currPlayer.getColor()) {
                        //Make user's pawns clickable
                        pawn.addEventHandler(MouseEvent.MOUSE_CLICKED, pawn.addHandler());
                        //Remove event handler after pawn is moved 
                        pawn.removeHandler();
                    }
                }
            }
        }
        //Check if currPlayer is a Robot
        if(currPlayer.toString() == "cpu") {
            randomMove(board, (Robot)currPlayer); //Make robot's move
        }
    }

/*
 * --------------------------
 *     MOVEMENT HANDLER
 * --------------------------
 */

    /*Method to enable buttons to move a selected pawn forward, left or right on user's turn
     *Disables buttons after selected
     *Input: Enum list of available moves, Square pawn is on
     *Called in Pawn's addHandler()
     */
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

        //Add event handlers to buttons
        forward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            //Move pawn forward
            public void handle(ActionEvent e) {
                Square move = board.getSquare(square.getXPos() - 1, square.getYPos());
                Square origin = board.getSquare(square.getXPos(), square.getYPos());
                if(move.hasPiece) { //Check if forward square has a piece
                    return;
                }
                //Move pawn from origin to move
                //Creating new pawn gets rid of event handling bugs
                board.addPawn(move, new Pawn(currPlayer.getColor(), move.getXPos(),move.getYPos(),30));
                board.removePawn(origin);
                //Check winner before next turn
                checkWin();
                if(win) {
                    disableMoves();
                    return;
                }
                //If no winner, disable buttons and swap currPlayer
                else {
                    disableMoves();
                    currPlayer = player2;
                    playTurn(board, currPlayer);
                }
            }
        });

        /*
         * Move pawn left
         */
        left.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //Get squares for move and origin
                Square move = board.getSquare(square.getXPos() - 1, square.getYPos() - 1);
                Square origin = board.getSquare(square.getXPos(), square.getYPos());
                //Check if move square has a piece
                if(move.hasPiece && move.getPieceType() != origin.getPieceType()) {
                    board.removePawn(move);
                }
                //Move pawn from origin to move
                board.addPawn(move, new Pawn(currPlayer.getColor(), move.getXPos(),move.getYPos(),30));
                board.removePawn(origin);
                //Check winner before next turn
                checkWin();
                if(win) {
                    disableMoves();
                    return;
                }
                else {
                    //If no winner, disable buttons and swap currPlayer
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

/*
 * --------------------------------
 *         H.E.R MOVEMENT
 * --------------------------------
 */

    /*
     * Method to move pawn automatically
     * Input: Chosen bead from CPU
     * Called in randomMove()
     */
    private static void movePawnAuto(Bead bead) {
        //Get squares for move and origin from squares stored in bead
        Square move = board.getSquare(bead.getMove().getXPos(), bead.getMove().getYPos());
        Square origin = board.getSquare(bead.getOrigin().getXPos(), bead.getOrigin().getYPos());
        //Check if move square has a piece
        if(move.hasPiece && move.getPieceType() != origin.getPieceType()) {
            board.removePawn(move);
        }
        board.addPawn(move, new Pawn(currPlayer.getColor(), move.getXPos(),move.getYPos(),30));
        board.removePawn(origin);
        //Check winner before next turn
        checkWin();
        if(win) {
            //Check if mode is auto and numGames > 0
            //If so, reset board and decrement numGames
            //NOT WORKING
            if(mode == "auto" && numGames > 0) {
                reset();
                numGames--;
            }
            else {
                disableMoves();
                return;
            }
        }
        else {
            currPlayer = player1;
            playTurn(board, currPlayer);
        }
    }

    /*
     * Method to make a random move for the CPU
     * Input: Board, CPU
     * Called in playTurn() if currPlayer is a cpu
     */
    private static void randomMove(Board board, Robot cpu) {
        //If cpu has no matchboxes, add the matchbox
        if(cpu.getBoards() == null) {
            cpu.addMatchbox(board.getPieces());
        }
        //If cpu has matchboxes, check if matchbox exists for current board
        else if(!board.deepContains(cpu.getBoards(), board.getPieces())) {
            cpu.addMatchbox(board.getPieces());
        }
        //Get matchboxes for cpu
        ArrayList<Matchbox> matchboxes = cpu.getMatchboxes();
        //Find matchbox for current board
        for(Matchbox matchbox : matchboxes) {
            if(Arrays.deepEquals(board.getPieces(), matchbox.getBoard())) {
                //If matchbox is empty, winner = other player
                if (matchbox.isEmpty()) {
                    if(cpu == player1) {
                        winner(player2.getSide());
                    }
                    else if(cpu == player2) {
                        winner(player1.getSide());
                    }
                }
                else {
                    //Get beads from matchbox
                    List<Bead> beads = matchbox.getBeads();
                    //If mode is slow, render beads on board
                    if(mode == "slow") {
                        ArrayList<Line> lines = matchbox.renderBeads(board); //Render beads on board
                        //Add event handlers to lines
                        //If line is clicked,move pawn
                        for(Line line : lines) {
                            line.setOnMouseClicked(e -> {
                                for(Bead bead : beads) {
                                    if(line == bead.getLine()) {
                                        chosenBead = bead;
                                        break;
                                    }
                                }
                                matchbox.unRenderBeads();
                                Main.moves.getChildren().remove(skip);
                                movePawnAuto(chosenBead);
                            });
                            //If line is hovered over, highlight it
                            line.setOnMouseEntered(e -> {
                                line.setStyle("-fx-cursor: hand;");
                                line.setStrokeWidth(20);
                            });
                            line.setOnMouseExited(e -> {
                                line.setStyle("-fx-cursor: default;");
                                line.setStrokeWidth(10);
                            });
                        }
                        //Add skip button event handler
                        skip.setOnAction(e -> {
                            matchbox.unRenderBeads();
                            Main.moves.getChildren().remove(skip);
                            chosenBead = beads.get(new Random().nextInt(beads.size()));
                            movePawnAuto(chosenBead);
                        });
                        //Add skip button to GUI
                        Main.moves.getChildren().add(skip);
                    }  
                    else {
                        //If mode is not slow, choose random bead
                        chosenBead = beads.get(new Random().nextInt(beads.size()));
                        movePawnAuto(chosenBead);
                        break;
                    }
                }
            }
        }
    }

/*
 * ------------------------
 *         WINNER
 * ------------------------
 */

    /*
     * Method to check for a winner
     * Called after each turn
     * If winner, pass to winner() method
     */
    private static void checkWin() {
        //hasMoves is true if there are available moves
        boolean hasMoves = false;
        char[][] currState = board.getPieces();

        //Check for available moves on the board
        for(Square square : board.getSquares()) {
            if(square.hasPiece) {
                Pawn pawn = (Pawn) square.getChildren().get(0);
                if(pawn.getColor() == currPlayer.getColor()) {
                    moves = pawn.getMoves(board, square);
                    if(!moves.isEmpty()) {
                        hasMoves = true;
                    }
                }
            }
        }
        //If no moves, winner = currPlayer
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
            //Cycle throuhg all squares on board
            for(Square square : board.getSquares()) {
                if(square.hasPiece) {
                    Pawn pawn = (Pawn) square.getChildren().get(0);
                    if(pawn.getColor() == currPlayer.getColor()) {
                        //Check for black pawn on white side
                        if(currPlayer.getSide() == "black") {
                            if(pawn.getXPos() == 2) {
                                winner(currPlayer.getSide());
                            }
                        }
                        //Check for white pawn on black side
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

    /*
     * Method to assign winner
     * Called in checkWin()
     * Input: String of winning color
     * Returns: User of winning color
     */
    private static User winner(String winColor) {
        winner = null;
        if(player1.getSide() == winColor) {
            win = true;
            winner = player1;
        }
        else if(player2.getSide() == winColor) {
            win = true;
            winner = player2;
        }
        else {
            System.out.println("Winner cannot be Assigned");
        }
        
        //Add winner text to GUI
        winnerText.setText(winner.toString().toUpperCase() + " WINS");
        winnerText.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        winnerText.setFill(Color.MEDIUMBLUE);
        Main.overlap.getChildren().add(winnerText);

        return winner;
    }


/*
 * -----------------------
 *        CLEANUP
 * -----------------------
 */
    /*Method to disable actions
     *This is called after a move is made
     *Will set the style for each square back to white
     *Usage: Cleanup
    */
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

    /*
     * Method to reset board
     * Punishes loser if loser is a robot
     */
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
        Main.overlap.getChildren().remove(winnerText);

        if(mode == "slow") {
            if(Main.moves.getChildren().contains(skip)) {
                Main.moves.getChildren().remove(skip);
            }
        }
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
