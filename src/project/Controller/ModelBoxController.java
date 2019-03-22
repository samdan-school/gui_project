package project.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.DBUtil;
import project.Service.PartService;

import java.sql.SQLException;

public class ModelBoxController {
    private ChoiceBox<String> cbxModels;

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
        if ((txtModel.getText()).isEmpty()) {
            return;
        }

        try {
            DBUtil.dbExecuteUpdate("INSERT INTO model(model_name) VALUES('"+txtModel.getText()+"')");
            this.cbxModels.setItems(PartService.modelList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onClickBtnCancel(event);
    }

    public void setCbxModels(ChoiceBox<String> cbxModels) {
        this.cbxModels = cbxModels;
    }
}
