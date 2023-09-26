import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{
    private final double size = 500; 
    private final String[] modes = {"Slow", "Fast", "Auto"};
    private Game game;
    private Scene gameScene;
    public static StackPane overlap;

    public static void main(String[] args) throws Exception {
       launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
         overlap = new StackPane();
         VBox root = new VBox();
/*
* -----------------
*      MENU
* -----------------
*/
        primaryStage.setTitle("HER - Hexapawn Educable Robot");
        Text title = new Text("H.E.R - Hexapawn Educable Robot");
        title.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, null, 20));
        Text description = new Text("Select a mode to start!");

        Label modeLabel = new Label("Select a Mode...");
        ComboBox<String> modeSelect = new ComboBox<>(FXCollections.observableArrayList(modes));
        Button start = new Button("Start!");

        root.setMinHeight(size);
        root.setMinWidth(size);
        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(title, description, modeLabel, modeSelect, start);
        root.setMargin(start, new Insets(10, 10, 10, 10));
        root.setMargin(description, new Insets(10, 10, 20, 10));

/*
 *---------------------
 *    GAME DISPLAY
 *---------------------
 */
        
        GridPane gamePane = new GridPane();
        HBox moves = new HBox();
        moves.setAlignment(Pos.CENTER);
        gamePane.setMinWidth(size/2);
        gamePane.setMinHeight(size/2);
        gamePane.setAlignment(Pos.BASELINE_CENTER);

        Text clickPawn = new Text("Select Pawn: Left-Click");
        Text unclickPawn = new Text("Deselect Pawn: Right-Click");
        Text movePawn = new Text("Move Pawn: Click an available button (only after selecting a pawn)");
        Button reset = new Button("RESET");
        root.setMargin(clickPawn, new Insets(10, 10, 10, 10));
        root.setMargin(movePawn, new Insets(10, 10, 10, 10));

        //Menu event handler
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(modeSelect.getValue() == "Slow") {
                    game = new Game(gamePane, "slow"); //Start slow mode
                }
                else if(modeSelect.getValue() == "Auto") {
                    game = new Game(gamePane, "auto"); //Start auto mode
                }
                else {
                    game = new Game(gamePane, "fast"); //Start fast mode
                }
                root.getChildren().clear();
                gameScene = new Scene(gamePane, size, size);
                moves.getChildren().addAll(game.left, game.forward, game.right);
                overlap.getChildren().add(gamePane);
                root.getChildren().addAll(overlap, clickPawn, unclickPawn, movePawn, moves, reset);
                //primaryStage.setScene(overlap); //Display game
            }
        });
        
        //Handler for reset button
        //Resets game with game's reset() function
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                game.reset();
            }
        });

        Scene scene = new Scene(root, size, size);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
