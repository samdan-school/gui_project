package exam.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private RadioButton rdUPS;

    @FXML
    private ToggleGroup Shipping;

    @FXML
    private RadioButton rdFedExGround;

    @FXML
    private RadioButton rdUSPostalAir;

    @FXML
    private RadioButton rdFedExAir;

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
        cbRetailCustomer.setSelected(true);
        rdUPS.setSelected(true);

        txtPartNumber.textProperty().addListener((obs, oldV, newV) -> {
            if (!newV.matches("\\d*?")) {
                txtPartNumber.setText(oldV);
                return;
            }
        });

        txtPricePerPart.textProperty().addListener((obs, oldV, newV) -> {
            if (!newV.matches("\\d*(\\.\\d*)?")) {
                txtPricePerPart.setText(oldV);
                return;
            }
        });

        txtQuantity.textProperty().addListener((obs, oldV, newV) -> {
            if (!newV.matches("\\d*?")) {
                txtQuantity.setText(oldV);
                return;
            }
        });

        txtState.textProperty().addListener((obs, oldV, newV) -> {
//            System.out.println(calculateSalesTax(100.0));
        });


    }

    @FXML
    void onClickBtnCompute(ActionEvent event) {
//        if (validData()) {
//
//        }
    }

    @FXML
    void onClickBtnExit(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickBtnNewOrder(ActionEvent event) {

    }

    double calculateSalesTax(double cost) {
        double salesTax = 0.0;

        if (!cbRetailCustomer.isSelected()) return salesTax;


        switch (txtState.getText()) {
            case "CA":
                salesTax = 10.0;
                break;
            case "NY":
            case "FL":
                salesTax = 5.0;
                break;
            default:
                salesTax = 0.0;
        }

        return cost * salesTax / 100.0;
    }

    double calculateShippingHandling() {
        return 0.0;
    }

    boolean validData() {
        boolean isValid = false;
        return false;
    }
}
