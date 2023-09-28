/*
 * Author: Nathan J. Rowe
 * Class Description: Main class for program
 */
import javafx.application.Application;
import javafx.collections.FXCollections;
//Event handlers
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//GUI elements & controls
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{
    //Size of GUI
    private final double size = 500; 
    //Modes for game
    private final String[] modes = {"Slow", "Fast", "Auto"};
    //Game object
    private Game game;
    private Scene gameScene;
    //GUI containers
    //Overlap is used to display arrows on top of game display (for slow mode)
    //Public static so that Game class can access (add and remove arrows/buttons)
    public static StackPane overlap;
    public static HBox moves;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Initialize GUI containers
         overlap = new StackPane();
         VBox root = new VBox();
/*
* -----------------
*      MENU
* -----------------
*/
        primaryStage.setTitle("HER - Hexapawn Educable Robot");
        Text title = new Text("H.E.R - Hexapawn Educable Robot");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        Text description = new Text("Select a mode to start!");

        Label modeLabel = new Label("Select a Mode...");
        ComboBox<String> modeSelect = new ComboBox<>(FXCollections.observableArrayList(modes));
        //If modeSelect value is Auto, add TextField for number of moves
        TextField numMoves = new TextField();
        Button start = new Button("Start!");
        numMoves.setPromptText("Enter number of games...");
        numMoves.setMaxWidth(200);
        numMoves.setVisible(false);
        //Restrict numMoves input to integers
        numMoves.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numMoves.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        //Event handler to display TextField for number of moves
        modeSelect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(modeSelect.getValue() == "Auto") {
                    numMoves.setVisible(true);
                }
                else {
                    numMoves.setVisible(false);
                }
            }
        });

        //Set up menu size and alignment
        root.setMinHeight(size);
        root.setMinWidth(size);
        root.setAlignment(Pos.CENTER);

        //Add menu elements to root
        root.getChildren().addAll(title, description, modeLabel, modeSelect, numMoves, start);
        root.setMargin(start, new Insets(10, 10, 10, 10));
        root.setMargin(description, new Insets(10, 10, 20, 10));

/*
 *---------------------
 *    GAME DISPLAY
 *---------------------
 */
        //Set up game display
        GridPane gamePane = new GridPane();
        moves = new HBox(20);
        moves.setAlignment(Pos.CENTER);
        gamePane.setMinWidth(size/2);
        gamePane.setMinHeight(size/2);
        gamePane.setAlignment(Pos.BASELINE_CENTER);

        //Set up game instructions & reset button
        Text clickPawn = new Text("Select Pawn: Left-Click");
        Text unclickPawn = new Text("Deselect Pawn: Right-Click");
        Text movePawn = new Text("Move Pawn: Click an available button (only after selecting a pawn)");
        Button reset = new Button("RESET");
        root.setMargin(clickPawn, new Insets(10, 10, 10, 10));
        root.setMargin(movePawn, new Insets(10, 10, 10, 10));

        //Menu event handler
        //Starts game with selected mode
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(modeSelect.getValue() == "Slow") {
                    game = new Game(gamePane, "slow"); //Start slow mode
                }
                else if(modeSelect.getValue() == "Fast") {
                    game = new Game(gamePane, "fast"); //Start fast mode
                }
                else if(modeSelect.getValue() == "Auto") {
                    game = new Game(gamePane, "auto"); //Start auto mode
                    //If no number of games is entered, default to 1
                    if(numMoves.getText().isEmpty()) {
                        game.setNumGames(1);
                    }
                    else {
                        game.setNumGames(Integer.parseInt(numMoves.getText()));
                    }
                }
                //Clear menu and add game display
                root.getChildren().clear();
                gameScene = new Scene(gamePane, size, size);
                moves.getChildren().addAll(game.left, game.forward, game.right);
                overlap.getChildren().add(gamePane);
                root.getChildren().addAll(overlap, clickPawn, unclickPawn, movePawn, moves, reset);
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
