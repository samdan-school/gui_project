package project.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PartEditorController {

    @FXML
    private ChoiceBox<?> cbxMakes;

    @FXML
    private ChoiceBox<?> cbxModels;

    @FXML
    private ChoiceBox<?> cbxCategories;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtPartNumber;

    @FXML
    private TextField txtPartName;

    @FXML
    private Button btnNewMakes;

    @FXML
    private Button btnNewModel;

    @FXML
    private Button btnNewCategory;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnClose;

    @FXML
    void onClickBtnClose(ActionEvent event) {
        Stage stage = (Stage)btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickBtnMakes(ActionEvent event) {
        Parent makes = null;
        try{
            makes = FXMLLoader.load(getClass().getResource("../View/make_box.fxml"));
            Scene makesScene = new Scene(makes,400,150);
            Stage stage= new Stage();
            stage.setTitle("New Make");
            stage.setScene(makesScene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    @FXML
    void onClickBtnNewCategory(ActionEvent event) {
        Parent categories = null;
        try{
            categories = FXMLLoader.load(getClass().getResource("../View/categorybox.fxml"));
            Scene categoriesScene = new Scene(categories,390,150);
            Stage stage= new Stage();
            stage.setTitle("New Category");
            stage.setScene(categoriesScene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    void onClickBtnNewModel(ActionEvent event) {
        Parent models = null;
        try{
            models = FXMLLoader.load(getClass().getResource("../View/model_box.fxml"));
            Scene modelsScene = new Scene(models,390,150);
            Stage stage= new Stage();
            stage.setTitle("New project.Model");
            stage.setScene(modelsScene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    void onClickBtnSubmit(ActionEvent event) {

    }

}
