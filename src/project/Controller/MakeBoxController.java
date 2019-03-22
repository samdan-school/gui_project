package project.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import project.DBUtil;
import project.JavaFXUtil;
import project.Service.PartService;

import java.io.IOException;
import java.sql.SQLException;

public class MakeBoxController {

    private ObservableList<String> makeList;
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
            String sql = "INSERT INTO make(make_name) VALUES('" + txtMake.getText() + "')";
            DBUtil.dbExecuteUpdate(sql);
            setMakeList(PartService.makeList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onClickBtnCancel(event);
    }

    public void setMakeList(ObservableList<String> makeList) {
        this.makeList = makeList;
    }
}
