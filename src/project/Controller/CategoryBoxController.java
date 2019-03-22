package project.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.DBUtil;
import project.JavaFXUtil;
import project.Service.PartService;

import java.sql.SQLException;

public class CategoryBoxController {
    private ChoiceBox<String> cbxCategories;

    @FXML
    private Button btnOK;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtCategory;

    @FXML
    void onClickBtnCancel(ActionEvent event) {
        Stage stage = (Stage)btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickBtnOk(ActionEvent event) {
        if ((txtCategory.getText()).isEmpty()) {
            return;
        }

        try {
            DBUtil.dbExecuteUpdate("INSERT INTO category(category_name) VALUES('"+txtCategory.getText()+"')");
            this.cbxCategories.setItems(PartService.categoryList());
        } catch (SQLException e) {
            Stage stage = (Stage)((Node)(event).getSource()).getScene().getWindow();
            JavaFXUtil.alertError(stage, "Category Insert Error", "Category insertion failed", "Please check value");
            e.printStackTrace();
        }
        onClickBtnCancel(event);
    }

    public void setCbxCategories(ChoiceBox<String> cbxCategories) {
        this.cbxCategories = cbxCategories;
    }
}
