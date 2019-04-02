package exam.Controller;

import application.MaskedTextField;
import exam.DBUtil;
import exam.JavaFXUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Optional;

public class MainBoxController {

    @FXML
    private TextField txtName;

    @FXML
    private MaskedTextField txtState;

    @FXML
    private MaskedTextField txtCustomerID;

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
    private TextField txtShippingAndHandling;

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

        txtCustomerID.plainTextProperty().addListener((obs, oldV, newV) -> {
            if (newV.length() == 9) {
                try {
                    ResultSet user = DBUtil.dbExecuteQuery("SELECT * FROM users WHERE customer_id = " + newV);

                    if (user.next()) {
                        txtName.setText(user.getString("customer_name"));
                        txtState.setPlainText(user.getString("state"));
                        cbRetailCustomer.setSelected(user.getInt("is_retail") == 1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        txtPartNumber.textProperty().addListener((obs, oldV, newV) -> {
            if (!newV.matches("\\d*?")) {
                txtPartNumber.setText(oldV);
            }
        });

        txtPricePerPart.textProperty().addListener((obs, oldV, newV) -> {
            if (!newV.matches("\\d*(\\.\\d{0,2})?")) {
                txtPricePerPart.setText(oldV);
            }
        });

        txtQuantity.textProperty().addListener((obs, oldV, newV) -> {
            if (!newV.matches("\\d*?")) {
                txtQuantity.setText(oldV);
            }
        });
    }

    @FXML
    void onClickBtnCompute(ActionEvent event) {
        if (validData()) {
            txtCost.setText(formatDouble(Double.parseDouble(txtPricePerPart.getText()) * Integer.parseInt(txtQuantity.getText())));
            txtSalesTax.setText(formatDouble(calculateSalesTax(Double.parseDouble(txtCost.getText()))));
            txtShippingAndHandling.setText(formatDouble(calculateShippingHandling()));
            txtTotal.setText(formatDouble((Double.parseDouble(txtCost.getText()) + Double.parseDouble(txtSalesTax.getText()) + Double.parseDouble(txtShippingAndHandling.getText()))));

            try {
                DBUtil.dbExecuteUpdate("INSERT INTO users VALUES("
                        + "'" + txtCustomerID.getPlainText() + "',"
                        + "'" + txtName.getText() + "',"
                        + "'" + txtState.getText() + "',"
                        + "'" + (cbRetailCustomer.isSelected() ? 1 : 0) + "'"
                        + ");");
            } catch (SQLException e) {
                System.out.print("");
            }
        }
    }

    @FXML
    void onClickBtnExit(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("App");
        alert.setHeaderText("Exit");
        alert.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            stage.close();
        }
        if (result.get() == ButtonType.CANCEL) {
            alert.close();
            txtCustomerID.requestFocus();
        }
    }

    @FXML
    void onClickBtnNewOrder(ActionEvent event) {
        txtCustomerID.deleteText(0, txtCustomerID.getPlainText().length() + 1);
        txtCustomerID.clear();
        txtName.setText("");
        txtState.deleteText(0, txtState.getPlainText().length() + 1);
        txtState.clear();
        cbRetailCustomer.setSelected(true);

        rdUPS.setSelected(true);

        txtPartNumber.setText("");
        txtDescription.setText("");
        txtPricePerPart.setText("");
        txtQuantity.setText("");
        cbOversize.setSelected(false);

        txtCost.setText("");
        txtSalesTax.setText("");
        txtShippingAndHandling.setText("");
        txtTotal.setText("");

        txtCustomerID.requestFocus();
    }

    private double calculateSalesTax(double cost) {
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

    private double calculateShippingHandling() {
        double chargePerPart = 0;

        if (cbOversize.isSelected()) chargePerPart += 5.0;

        if (rdUPS.isSelected()) chargePerPart += 7.0;
        if (rdUSPostalAir.isSelected()) chargePerPart += 8.50;
        if (rdFedExGround.isSelected()) chargePerPart += 9.25;
        if (rdFedExAir.isSelected()) chargePerPart += 12.0;

        return chargePerPart * Integer.parseInt(txtQuantity.getText());
    }

    private boolean validData() {
        boolean isValid = true;

        Stage stage = (Stage) btnCompute.getScene().getWindow();

        if (txtCustomerID.getPlainText().trim().length() != 9) {
            isValid = false;
            JavaFXUtil.alertError(stage, "Compute Error", "SSN error", "Must Fill SSN field.");
            txtCustomerID.requestFocus();
        } else if (txtName.getText().trim().length() < 2) {
            isValid = false;
            JavaFXUtil.alertError(stage, "Compute Error", "Name error", "Please enter your name.");
            txtName.requestFocus();
        } else if (txtState.getPlainText().trim().length() != 2) {
            isValid = false;
            JavaFXUtil.alertError(stage, "Compute Error", "State error", "State must be equal to 2 characters.");
            txtState.requestFocus();
        } else if (txtPartNumber.getText().trim().length() < 1) {
            isValid = false;
            JavaFXUtil.alertError(stage, "Compute Error", "Part Number error", "Part Number cannot be missing.");
            txtPartNumber.requestFocus();
        } else if (txtDescription.getText().trim().length() < 1) {
            isValid = false;
            JavaFXUtil.alertError(stage, "Compute Error", "Description error", "Description cannot be missing.");
            txtDescription.requestFocus();
        } else if (txtPricePerPart.getText().trim().length() < 1) {
            isValid = false;
            JavaFXUtil.alertError(stage, "Compute Error", "Price error", "Price must be a number that is greater than zero.");
            txtPricePerPart.requestFocus();
        } else if (Double.parseDouble(txtPricePerPart.getText()) <= 0) {
            isValid = false;
            JavaFXUtil.alertError(stage, "Compute Error", "Price error", "Price must be a number that is greater than zero.");
            txtPricePerPart.requestFocus();
        } else if (txtQuantity.getText().trim().length() < 1) {
            isValid = false;
            JavaFXUtil.alertError(stage, "Compute Error", "Quantity error", "Quantity must be a number that is greater than zero.");
            txtQuantity.requestFocus();
        } else if (Double.parseDouble(txtQuantity.getText()) <= 0) {
            isValid = false;
            JavaFXUtil.alertError(stage, "Compute Error", "Quantity error", "Quantity must be a number that is greater than zero.");
            txtQuantity.requestFocus();
        }

        return isValid;
    }

    private String formatDouble(double value) {
        return new DecimalFormat(".##").format(value);
    }
}
