package project.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.DBUtil;
import project.JavaFXUtil;
import project.Service.PartService;

import java.io.IOException;
import java.sql.SQLException;

public class PartEditorController {
    private JavaFXUtil<MakeBoxController> makeUtil;
    private JavaFXUtil<ModelBoxController> modelUtil;
    private JavaFXUtil<CategoryBoxController> categoryUtil;
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
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickBtnMakes(ActionEvent event) {
        Parent makes = null;
        try {
            makes = makeUtil.getLoader().load();
            makeUtil.getController().setMakeList(makeList);
            makeUtil.openNewStage(makes, "New Make", 400, 150);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnNewModel(ActionEvent event) {
        Parent models = null;
        try {
            models = FXMLLoader.load(getClass().getResource("../View/model_box.fxml"));
//            JavaFXUtil.openNewStage(models, "New Model", 400, 150);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnNewCategory(ActionEvent event) {
        Parent categories = null;
        try {
            categories = FXMLLoader.load(getClass().getResource("../View/category_box.fxml"));
//            JavaFXUtil.openNewStage(categories, "New Category", 400, 150);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnSubmit(ActionEvent event) {
        try {
            String query = "INSERT INTO part(make_id, model_id, category_id, unit_price, part_year, part_name) VALUES(" +
                    DBUtil.dbExecuteQuery("SELECT make_id FROM make WHERE make_name = '" + cbxMakes.getSelectionModel().getSelectedItem() + "'") + ", " +
                    DBUtil.dbExecuteQuery("SELECT model_id FROM model WHERE model_name = '" + cbxModels.getSelectionModel().getSelectedItem() + "'") + ", " +
                    DBUtil.dbExecuteQuery("SELECT category_id FROM category WHERE category_name = '" + cbxCategories.getSelectionModel().getSelectedItem() + "'") + ", " +
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

        makeUtil = new JavaFXUtil<>("../View/make_box.fxml");
        modelUtil = new JavaFXUtil<>("../View/model_box.fxml");
        categoryUtil = new JavaFXUtil<>("../View/category_box.fxml");
    }

    @FXML
    void initialize() {
        ObservableList<Integer> years = FXCollections.observableArrayList();
        for (int i = 2000; i < 2020; i++) {
            years.add(i);
        }
        this.cbxYear.setItems(years);
        this.cbxMakes.setItems(makeList);
        this.cbxMakes.setItems(modelList);
        this.cbxMakes.setItems(categoryList);
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
