package project.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import project.DBUtil;

import java.sql.SQLException;

public class MakeBoxController {

    @FXML
    private Button btnOK;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtMake;

    @FXML
    void onClickBtnCancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickBtnOk(ActionEvent event) {
        if ((txtMake.getText()).isEmpty()) {
            return;
        }

        try {
            String sql = "INSERT INTO make(make_name) VALUES('" + txtMake.getText() +"')";
            DBUtil.dbExecuteUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onClickBtnCancel(event);
    }

}
