package project.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import project.JavaFXUtil;

import java.io.IOException;

public class MainBoxController {
    private JavaFXUtil<PartEditorController> partUtil = new JavaFXUtil<>("../View/part_editor.fxml");

    @FXML
    private TreeView<String> tvwAutoParts;

    @FXML
    private Button btnNewAutoPart;

    @FXML
    private Button btnNewCustomerOrder;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnOpen;

    @FXML
    private Button btnClose;

    @FXML
    private TextField txtSave;

    @FXML
    private TextField txtOpen;

    @FXML
    private TextField txtTaxAmount;

    @FXML
    private TextField txtTaxRate;

    @FXML
    private TextField txtOrderTotal;

    @FXML
    private TextField txtPartsTotal;

    @FXML
    private TableView<String> lvwAutoParts;

    @FXML
    private TableColumn<?, ?> colPartNumber;

    @FXML
    private TableColumn<?, ?> colPartName;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Button btnAdd;

    @FXML
    private TextField txtPartNumber;

    @FXML
    private TextField txtPartName;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtSubTotal;

    @FXML
    private TableView<?> lvwSelectedParts;

    @FXML
    private TableColumn<?, ?> colPartNumberSelected;

    @FXML
    private TableColumn<?, ?> colPartNameSelected;

    @FXML
    private TableColumn<?, ?> colUnitPriceSelected;

    @FXML
    private TableColumn<?, ?> colQuantitySelected;

    @FXML
    private TableColumn<?, ?> colSubTotalSelected;

    @FXML
    void onClickBtnAdd(ActionEvent event) {

    }

    @FXML
    void onClickBtnClose(ActionEvent event) {
        Stage stage = (Stage)btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickBtnNewAutoPart(ActionEvent event) {
        Parent part = null;
        try{
            FXMLLoader loader = partUtil.getLoader();
            part = loader.load();
            partUtil.openNewStage(part, "New Part", 600, 250);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void onClickBtnNewCustomerOrder(ActionEvent event) {
        txtOpen.setText(null);
        txtTaxAmount.setText(null);
        txtPartsTotal.setText(null);
        txtOrderTotal.setText(null);
    }

    @FXML
    void onClickBtnOpen(ActionEvent event) {
    }

    @FXML
    void onClickBtnSave(ActionEvent event) {

    }

    public MainBoxController() {
        System.out.println("Hello");
    }

    @FXML
    public void initialize() {
        System.out.println("Hi");
    }
}
