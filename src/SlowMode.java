import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class SlowMode {
    public static User currPlayer;
    public static Board board;
    private boolean game;

    public Game(GridPane hexaPane) {
        board = new Board(hexaPane);
        currPlayer = new User("Human");
        this.game = true;

        addEventHandler(board.board);
    }

    private void addEventHandler(GridPane matchbox) {
        matchbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               
            }   
        });
    }
}
