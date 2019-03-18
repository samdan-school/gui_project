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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import project.DBUtil;
import project.JavaFXUtil;
import project.Service.PartService;

import java.io.IOException;
import java.sql.SQLException;

public class PartEditorController {
    private ObservableList<String> makeList;
    private ObservableList<String> modelList;
    private ObservableList<String> categoryList;

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

            JavaFXUtil.openNewStage(makes, "New Make", 400, 150);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnNewModel(ActionEvent event) {
        Parent models = null;
        try{
            models = FXMLLoader.load(getClass().getResource("../View/model_box.fxml"));
            JavaFXUtil.openNewStage(models, "New Model", 400, 150);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnNewCategory(ActionEvent event) {
        Parent categories = null;
        try{
            categories = FXMLLoader.load(getClass().getResource("../View/categorybox.fxml"));
            JavaFXUtil.openNewStage(categories, "New Category", 400, 150);
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

    public PartEditorController() {
        this.makeList = PartService.makeList();
        this.modelList = PartService.modelList();
        this.categoryList = PartService.categoryList();
//
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/part_editor.fxml"));
//        try {
//            Pane root = fxmlLoader.load();
//            Scene scene = new Scene(root); // optionally specify dimensions too
//            root.setScene();
//        } catch (IOException exception) {
//            throw new RuntimeException(exception);
//        }
    }

    @FXML
    void initialize() {
        ObservableList<Integer> years = FXCollections.observableArrayList();
        for (int i = 2000; i < 2020; i++) {
            years.add(i);
        }
        this.cbxYear.setItems(years);
        this.cbxMakes.setItems(makeList);
//        this.cbxModels.setItems(partService.modelList());
//        this.cbxCategories.setItems(partService.categoryList());
    }

    public void setMakeList(ObservableList<String> makeList) {
        this.makeList = makeList;
    }

    public void setModelList(ObservableList<String> modelList) {
        this.modelList = modelList;
    }

    public void setCategoryList(ObservableList<String> categoryList) {
        this.categoryList = categoryList;
    }

    @FXML
    void cbxCategoriesClick(MouseEvent event) {
        this.cbxCategories.setItems(PartService.categoryList());
    }

    @FXML
    void cbxMakesClick(MouseEvent event) {
        this.cbxMakes.setItems(PartService.makeList());
    }

    @FXML
    void cbxModelsClick(MouseEvent event) {
        this.cbxModels.setItems(PartService.modelList());
    }
}
