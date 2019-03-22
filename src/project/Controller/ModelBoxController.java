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

public class ModelBoxController {
    private ChoiceBox<String> cbxModels;
    private String makeName;

    @FXML
    private Button btnOK;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtModel;

    @FXML
    void onClickBtnCancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickBtnOk(ActionEvent event) {
        if ((txtModel.getText()).isEmpty()) {
            return;
        }

        String modelInsert = "INSERT INTO model(model_name) VALUES('" + txtModel.getText() + "')";
        String makeModelInsert = "INSERT INTO make_model(make_name, model_name) VALUES(" +
                "'" + this.makeName + "'," +
                "'" + txtModel.getText() + "'" +
                ")";

        try {
            DBUtil.dbExecuteUpdate(modelInsert);
            DBUtil.dbExecuteUpdate(makeModelInsert);
            this.cbxModels.setItems(PartService.modelList(makeName));
        } catch (SQLException e) {
            Stage stage = (Stage)((Node)(event).getSource()).getScene().getWindow();
            JavaFXUtil.alertError(stage, "Model Insert Error", "Model insertion failed", "Please check value");
            e.printStackTrace();
        }
        onClickBtnCancel(event);
    }

    public void setCbxModels(ChoiceBox<String> cbxModels) {
        this.cbxModels = cbxModels;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }
}
