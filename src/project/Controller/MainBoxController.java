package project.Controller;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.JavaFXUtil;
import project.Model.Part;
import project.Model.SelectedPart;
import project.Service.PartService;

import javax.print.DocFlavor;
import java.util.Arrays;
import java.util.Comparator;

public class MainBoxController {
    private ObservableList<Part> parts;
    TreeItem<String> rootNode = new TreeItem<>("College Part Auto-Parts");
    private Image[] closeImages;
    private Image[] openImages;
    private ObservableList<Part> availableParts;
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
    private TableView<Part> lvwAutoParts;

    @FXML
    private TableColumn<Part, Number> colPartNumber;

    @FXML
    private TableColumn<Part, String> colPartName;

    @FXML
    private TableColumn<Part, Number> colUnitPrice;

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
    private TableView<SelectedPart> lvwSelectedParts;

    @FXML
    private TableColumn<SelectedPart, Number> colPartNumberSelected;

    @FXML
    private TableColumn<SelectedPart, String> colPartNameSelected;

    @FXML
    private TableColumn<SelectedPart, Number> colUnitPriceSelected;

    @FXML
    private TableColumn<SelectedPart, Number> colQuantitySelected;

    @FXML
    private TableColumn<SelectedPart, Number> colSubTotalSelected;

    private ObservableList<SelectedPart> selectedParts = FXCollections.observableArrayList();

    @FXML
    void onClickBtnAdd(ActionEvent event) {
        if (!txtPartNumber.getText().isEmpty() && Integer.parseInt(txtQuantity.getText()) == 0) {
            for (SelectedPart selectedPart : this.selectedParts) {
                if (Integer.parseInt(txtPartNumber.getText()) == selectedPart.getPart().getPartId()) {
                    this.selectedParts.remove(selectedPart);
                }
                lvwSelectedParts.setItems(this.selectedParts);
                lvwSelectedParts.refresh();
                return;
            }
        }
        if (!txtPartNumber.getText().isEmpty() && Integer.parseInt(txtQuantity.getText()) > 0) {
            for (SelectedPart selectedPart : this.selectedParts) {
                if (Integer.parseInt(txtPartNumber.getText()) == selectedPart.getPart().getPartId()) {
                    selectedPart.setQuantity(Integer.parseInt(txtQuantity.getText()));
                    selectedPart.setSubTotal(Double.parseDouble(txtSubTotal.getText()));
                }
                lvwSelectedParts.setItems(this.selectedParts);
                lvwSelectedParts.refresh();
                return;
            }
            for (Part part : this.parts) {
                if (Integer.parseInt(txtPartNumber.getText()) == part.getPartId()) {
                    this.selectedParts.add(new SelectedPart(
                            part,
                            Integer.parseInt(txtQuantity.getText()),
                            Double.parseDouble(txtSubTotal.getText())
                    ));
                    lvwSelectedParts.setItems(this.selectedParts);
                    lvwSelectedParts.refresh();
                    break;
                }
            }
        }
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
        closeImages = new Image[5];
        openImages = new Image[5];
        for (int i = 1; i <= 5; i++) {
//            System.out.println("a" + i + ".png");
            closeImages[i - 1] = new Image(getClass().getResource("../../resource/a" + i + ".png").toExternalForm(), 16, 16, true, true);
            openImages[i - 1] = new Image(getClass().getResource("../../resource/b" + i + ".png").toExternalForm());
        }
    }

    @FXML
    public void initialize() {
        loadPartTree();
        parts.addListener((ListChangeListener.Change<? extends Part> c) -> loadPartTree());
        // Available table-view
        colPartNumber.setCellValueFactory(cellData -> cellData.getValue().partIdProperty());
        colPartName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        colUnitPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());

        // Selected parts
        colPartNumberSelected.setCellValueFactory(cellData -> cellData.getValue().partIdProperty());
        colPartNameSelected.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        colUnitPriceSelected.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        colQuantitySelected.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        colSubTotalSelected.setCellValueFactory(cellData -> cellData.getValue().subTotalProperty());


        txtQuantity.textProperty().addListener((obs, newValue, oldValue) -> {
            if (!isNumeric(txtQuantity.getText()) || txtQuantity.getText().isEmpty()) {
                return;
            }
            if (txtUnitPrice.getText().compareTo("0.00") > 0) {
                txtSubTotal.setText(
                        Double.parseDouble(txtUnitPrice.getText()) * Double.parseDouble(txtQuantity.getText()) + ""
                );
            }
        });
    }

    @FXML
    void vwAutoPartsMouseClick(MouseEvent event) {
        TreeItem<String> item = tvwAutoParts.getSelectionModel().getSelectedItem();
        int position = countParent(item, 0);
        if (position < 4) {
            item.setGraphic(!item.isExpanded() ?
                    new ImageView(closeImages[position]) :
                    new ImageView(openImages[position])
            );
        }
        TreeItem<String> current = item;
        String[] names = new String[position];
        for (int i = 0; i < position; i++) {
            names[i] = current.getValue();
            current = current.getParent();
        }
        this.setAvailableParts(PartService.availablePartList(names));
        this.lvwAutoParts.setItems(this.availableParts);
        this.lvwAutoParts.refresh();
    }

    @FXML
    void lvwAutoPartsMouseClick(MouseEvent event) {
        Part part = lvwAutoParts.getSelectionModel().getSelectedItem();
        if (part != null) {
            txtPartNumber.setText(part.getPartId() + "");
            txtPartName.setText(part.getPartName());
            txtUnitPrice.setText(part.getPrice() + "");
            txtQuantity.setText("1");
            txtSubTotal.setText(part.getPrice() + "");
        }
    }

    private void loadPartTree() {
        rootNode.getChildren().clear();
        rootNode.setExpanded(true);
        for (Part part : parts) {
            TreeItem<String>[] partLeafs = new TreeItem[4];
            partLeafs[0] = new TreeItem<>(part.getYear() + "", new ImageView(closeImages[1]));
            partLeafs[1] = new TreeItem<>(part.getMake(), new ImageView(closeImages[2]));
            partLeafs[2] = new TreeItem<>(part.getmodel(), new ImageView(closeImages[3]));
            partLeafs[3] = new TreeItem<>(part.getCategory(), new ImageView(closeImages[4]));
            insertBranch(rootNode, partLeafs, 0);
        }
        rootNode.setGraphic(new ImageView(openImages[0]));
        tvwAutoParts.setRoot(rootNode);
    }

    private void insertBranch(TreeItem<String> prentNode, TreeItem<String>[] insNode, int position) {
        if (position <= insNode.length - 2) {
            boolean found = false;
            for (TreeItem<String> childNode : prentNode.getChildren()) {
                if (childNode.getValue().contentEquals(insNode[position].getValue())) {
                    insertBranch(childNode, insNode, position + 1);
                    if (position == insNode.length - 2) {
                        childNode.getChildren().add(insNode[position + 1]);
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<String> childNode = new TreeItem<String>(
                        insNode[position].getValue(),
                        new ImageView(closeImages[position + 1])
                );
                prentNode.getChildren().add(childNode);
                childNode.getChildren().add(insNode[position + 1]);
                insertBranch(childNode, insNode, position + 1);
            }
            prentNode.getChildren().sort(Comparator.comparing(t -> t.getValue()));
        }
    }

    private int countParent(TreeItem<String> childNode, int count) {
        if (childNode.getParent() != null) {
            return countParent(childNode.getParent(), count + 1);
        }
        return count;
    }

    public void setAvailableParts(ObservableList<Part> availableParts) {
        this.availableParts = availableParts;
    }

    private boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }
}
