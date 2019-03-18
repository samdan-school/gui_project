package project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class JavaFXUtil {
    public static void openNewStage(Parent parent, String title, int width, int height) {
        Scene scene = new Scene(parent,width,height);
        Stage stage= new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }


    public static <E> E getTargetFXMLController(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXUtil.class.getResource(fxml));
            return (E) loader.getController();
        }catch (Exception e) {
            System.out.println(e + " Failed to get target controller");
            return null;
        }
    }
}
