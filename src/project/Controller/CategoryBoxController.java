package project.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.DBUtil;

import java.sql.SQLException;

public class CategoryBoxController {

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
        try {
            DBUtil.dbExecuteUpdate("INSERT INTO category(category_name) VALUES('"+txtCategory.getText()+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
