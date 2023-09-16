import javafx.scene.layout.GridPane;
    

public class Game {
    public static Board board;
    private boolean game;

     public Game(GridPane hexaPane) {
        board = new Board(hexaPane);
        this.game = true;
     }

     public void slowMode() {
       User player = new User("Player 1", "white");
       User cpu = new Robot("CPU", "black");

       String currPlayer = player.toString();

       


     }

     public void fastMode() {

     }

     public void Auto() {

     }
}
