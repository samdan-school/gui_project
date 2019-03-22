package project.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import project.DBUtil;
import project.JavaFXUtil;
import project.Service.PartService;

import java.io.IOException;
import java.sql.SQLException;

public class MakeBoxController {

    private ChoiceBox<String> cbxMakes;
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
            this.cbxMakes.setItems(PartService.makeList());
        } catch (Exception e) {
            Stage stage = (Stage)((Node)(event).getSource()).getScene().getWindow();
            JavaFXUtil.alertError(stage, "Make Insert Error", "Make insertion failed", "Please check value");
            e.printStackTrace();
        }
        onClickBtnCancel(event);
    }

    public void setCbxMakes(ChoiceBox<String> cbxMakes) {
        this.cbxMakes = cbxMakes;
    }
}
