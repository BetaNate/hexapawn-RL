import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args) throws Exception {
       launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("HER - Hexapawn Educable Robot");
        double size = 800;

        Pane root = new Pane();
        root.setMinWidth(size);
        root.setMinHeight(size);

        Scene scene = new Scene(root, size, size);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
