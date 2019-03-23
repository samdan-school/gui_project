package project.Controller;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import project.JavaFXUtil;
import project.Model.Part;
import project.Service.PartService;

import java.io.IOException;
import java.util.Comparator;

public class MainBoxController {
    private ObservableList<Part> parts;
    TreeItem<String> rootNode = new TreeItem<>("College Part Auto-Parts");
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
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickBtnNewAutoPart(ActionEvent event) {
        try {
            JavaFXUtil partUtil = new JavaFXUtil(getClass().getResource("../View/part_editor.fxml"));
            Parent part = partUtil.getLoader().load();
            PartEditorController ctrE = partUtil.getLoader().getController();
            ctrE.setPartList(this.parts);
            partUtil.openNewStage(part, "New Part", 600, 250);
        } catch (Exception e) {
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
        this.parts = PartService.partList();
    }

    @FXML
    public void initialize() {
        loadPartTree();
        parts.addListener((ListChangeListener.Change<? extends Part> c) -> loadPartTree());
    }

    private void loadPartTree() {
        rootNode.getChildren().clear();
        rootNode.setExpanded(true);
        for (Part part : parts) {
            TreeItem<String>[] partLeafs = new TreeItem[5];
            partLeafs[0] = new TreeItem<>(part.getYear() + "");
            partLeafs[1] = new TreeItem<>(part.getMake());
            partLeafs[2] = new TreeItem<>(part.getmodel());
            partLeafs[3] = new TreeItem<>(part.getCategory());
            partLeafs[4] = new TreeItem<>(part.getPartName());
            insertBranch(rootNode, partLeafs, 0);
        }
        tvwAutoParts.setRoot(rootNode);
    }

    private void insertBranch(TreeItem<String> prentNode, TreeItem<String>[] insNode, int position) {
        if (position <= insNode.length - 2) {
            boolean found = false;
            for (TreeItem<String> childNode : prentNode.getChildren()) {
                if (childNode.getValue().contentEquals(insNode[position].getValue())) {
                    childNode.getChildren().add(insNode[position + 1]);
                    insertBranch(childNode, insNode, position + 1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<String> childNode = new TreeItem<String>(
                        insNode[position].getValue()
//                        new ImageView(depIcon)
                );
                prentNode.getChildren().add(childNode);
                childNode.getChildren().add(insNode[position + 1]);
                insertBranch(childNode, insNode, position + 1);
            }
            prentNode.getChildren().sort(Comparator.comparing(t -> t.getValue()));
        }
    }
}
