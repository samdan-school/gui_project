package project.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.DBUtil;
import project.JavaFXUtil;
import project.Model.Part;
import project.Service.PartService;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PartEditorController {
    private ObservableList<String> makeList;
    private ObservableList<String> modelList;
    private ObservableList<String> categoryList;
    private String selectedMake = "";
    private ObservableList<Part> partList;

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
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickBtnMakes(ActionEvent event) {
        JavaFXUtil makeUtil = new JavaFXUtil(getClass().getResource("../View/make_box.fxml"));
        Parent makes = null;
        try {
            makes = makeUtil.getLoader().load();
            MakeBoxController makeCtr = makeUtil.getLoader().getController();
            makeCtr.setCbxMakes(this.cbxMakes);
            makeUtil.openNewStage(makes, "New Make", 400, 150);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnNewModel(ActionEvent event) {
        JavaFXUtil modelUtil = new JavaFXUtil(getClass().getResource("../View/model_box.fxml"));
        Parent models = null;
        try {
            models = modelUtil.getLoader().load();
            ModelBoxController modelCtr = modelUtil.getLoader().getController();
            modelCtr.setCbxModels(this.cbxModels);
            modelCtr.setMakeName(this.selectedMake);
            modelUtil.openNewStage(models, "New Model", 400, 150);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnNewCategory(ActionEvent event) {
        JavaFXUtil categoryUtil = new JavaFXUtil(getClass().getResource("../View/category_box.fxml"));
        Parent categories = null;
        try {
            categories = categoryUtil.getLoader().load();
            CategoryBoxController makeCtr = categoryUtil.getLoader().getController();
            makeCtr.setCbxCategories(this.cbxCategories);
            categoryUtil.openNewStage(categories, "New Category", 400, 150);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnSubmit(ActionEvent event) {
        try {
            String query = "INSERT INTO part(make_name, model_name, category_name, unit_price, part_year, part_name) VALUES("
                    + "'" + cbxMakes.getSelectionModel().getSelectedItem() + "', "
                    + "'" + cbxModels.getSelectionModel().getSelectedItem() + "', "
                    + "'" + cbxCategories.getSelectionModel().getSelectedItem() + "', "
                    + txtUnitPrice.getText() + ", "
                    + cbxYear.getSelectionModel().getSelectedItem() + ", "
                    + "'" + txtPartName.getText() + "'"
                    + ")";
            DBUtil.dbExecuteUpdate(query);
            this.partList.remove(0, this.partList.size());
            this.partList.addAll(PartService.partList());
            onClickBtnClose(event);
        } catch (SQLException e) {
            Stage stage = (Stage)((Node)(event).getSource()).getScene().getWindow();
            JavaFXUtil.alertError(stage, "Part Insert Error", "Part insertion failed", "Please check value");
            e.printStackTrace();
        }
    }

    public PartEditorController() {
        this.makeList = PartService.makeList();
        this.categoryList = PartService.categoryList();
    }

    @FXML
    void initialize() {
        ObservableList<Integer> years = FXCollections.observableArrayList();
        for (int i = 2000; i <= 2020; i++) {
            years.add(i);
        }
        this.cbxYear.setItems(years);
        this.cbxMakes.setItems(makeList);
        this.cbxCategories.setItems(categoryList);

        this.cbxModels.setDisable(true);
        this.btnNewModel.setDisable(true);

        this.cbxMakes
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldV, newV) -> {
                    selectedMake = newV;
                    this.setModelList(PartService.modelList(newV));
                    this.cbxModels.setItems(modelList);
                    this.cbxModels.setDisable(false);
                    this.btnNewModel.setDisable(false);
                });

        txtPartNumber.setDisable(true);
        txtPartNumber.setStyle("-fx-opacity: 1.0;");
        txtPartNumber.setText("" + findMaxPartId());
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

    public void setPartList(ObservableList<Part> partList) {
        this.partList = partList;
    }

    public int findMaxPartId() {
        int returnId = -1;
        try {
            ResultSet maxId = DBUtil.dbExecuteQuery("SELECT MAX(part_id) as 'max' FROM part");
            if (maxId.next()) {
                returnId = maxId.getInt("max");
                returnId++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnId;
    }
}
