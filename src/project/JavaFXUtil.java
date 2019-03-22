package project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class JavaFXUtil {
    private FXMLLoader loader;
    private Scene scene;
    private Stage stage;

    public JavaFXUtil(java.net.URL location) {
        this.loader = new FXMLLoader();
        loader.setLocation(location);
    }

    public void openNewStage(Parent parent, String title, int width, int height) {
        scene = new Scene(parent, width, height);
        stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public static void alertError(Stage stage, String title, String header, String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(stage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);

        alert.showAndWait();
    }
}
