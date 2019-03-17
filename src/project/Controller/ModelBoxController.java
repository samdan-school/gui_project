package project.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.DBUtil;

import java.sql.SQLException;

public class ModelBoxController {

    @FXML
    private Button btnOK;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtModel;

    @FXML
    void onClickBtnCancel(ActionEvent event) {
        Stage stage = (Stage)btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickBtnOk(ActionEvent event) {
        try {
            DBUtil.dbExecuteUpdate("INSERT INTO model(model_name) VALUES('"+txtModel.getText()+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
