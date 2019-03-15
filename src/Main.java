import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/main_box.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 740, 540));
        DBUtil.dbConnect();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
