package project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class JavaFXUtil<T> {
    private FXMLLoader loader;
    private T controller;

    public JavaFXUtil(java.net.URL location) {
        this.loader = new FXMLLoader();
        loader.setLocation(location);
        System.out.println(loader);
        this.controller = loader.getController();
    }

    public void openNewStage(Parent parent, String title, int width, int height) {
        Scene scene = new Scene(parent, width, height);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public T getMyController() {
        return controller;
    }
}
