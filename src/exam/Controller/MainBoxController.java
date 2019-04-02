package exam.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainBoxController {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtState;

    @FXML
    private TextField txtCustomerID;

    @FXML
    private CheckBox cbRetailCustomer;

    @FXML
    private CheckBox cbUPS;

    @FXML
    private CheckBox cbUSPostalAir;

    @FXML
    private CheckBox cbFedExGround;

    @FXML
    private CheckBox cbFedExAir;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtPricePerPart;

    @FXML
    private TextField txtPartNumber;

    @FXML
    private TextField txtQuantity;

    @FXML
    private CheckBox cbOversize;

    @FXML
    private TextField txtCost;

    @FXML
    private TextField txtSalesTax;

    @FXML
    private TextField txtShppingAndHandling;

    @FXML
    private TextField txtTotal;

    @FXML
    private Button btnCompute;

    @FXML
    private Button btnNewOrder;

    @FXML
    private Button btnExit;

    @FXML
    void initialize() {
        txtCustomerID.textProperty().addListener((obs, oldV, newV) -> {
            if (newV.matches("[A-Za-z]") || newV.matches("[\\W]")) {
                txtCustomerID.setText(oldV);
            } else
                txtCustomerID.setText(newV.toUpperCase());
        });

        txtState.textProperty().addListener((obs, oldV, newV) -> {
            if (newV.matches("[\\W\\d]") || newV.length() > 2) {
                txtState.setText(oldV);
            } else
                txtState.setText(newV.toUpperCase());
        });

        cbRetailCustomer.setSelected(true);

        txtPartNumber.textProperty().addListener((obs, oldV, newV) -> {});

        txtPricePerPart.textProperty().addListener((obs, oldV, newV) -> {});

        txtQuantity.textProperty().addListener((obs, oldV, newV) -> {});
    }

    @FXML
    void onClickBtnCompute(ActionEvent event) {

    }

    @FXML
    void onClickBtnExit(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickBtnNewOrder(ActionEvent event) {

    }
}
