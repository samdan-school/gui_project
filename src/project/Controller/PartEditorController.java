package project.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.DBUtil;
import project.Service.PartService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class PartEditorController {
    PartService partService = new PartService();
    @FXML
    private ChoiceBox<Integer> cbxYear;

    @FXML
    private ChoiceBox<String> cbxMakes;

    @FXML
    private ChoiceBox<String> cbxModels;

    @FXML
    private ChoiceBox<String> cbxCategories;

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
        try {
            String query = "INSERT INTO part(make_id, model_id, category_id, unit_price, part_year, part_name) VALUES(" +
                    DBUtil.dbExecuteQuery("SELECT make_id FROM make WHERE make_name = '"+cbxMakes.getSelectionModel().getSelectedItem()+"'") + ", " +
                    DBUtil.dbExecuteQuery("SELECT model_id FROM model WHERE model_name = '"+cbxModels.getSelectionModel().getSelectedItem()+"'") + ", " +
                    DBUtil.dbExecuteQuery("SELECT category_id FROM category WHERE category_name = '"+cbxCategories.getSelectionModel().getSelectedItem()+"'") + ", " +
                    txtUnitPrice.getText() + ", " + cbxYear.getSelectionModel().getSelectedItem() + ", '" + txtPartName.getText() + "'";
            DBUtil.dbExecuteUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        ObservableList<Integer> years = FXCollections.observableArrayList();
        for (int i = 1970; i < 2020; i++) {
            years.add(i);
        }
        this.cbxYear.setItems(years);
        this.cbxMakes.setItems(partService.makeList());
        this.cbxCategories.setItems(partService.categoryList());
        this.cbxModels.setItems(partService.modelList());
    }
    @FXML
    void cbxCategoriesClick(MouseEvent event) {
        this.cbxCategories.setItems(partService.categoryList());
    }

    @FXML
    void cbxMakesClick(MouseEvent event) {
        this.cbxMakes.setItems(partService.makeList());
    }

    @FXML
    void cbxModelsClick(MouseEvent event) {
        this.cbxModels.setItems(partService.modelList());
    }
}
