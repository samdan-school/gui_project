package project.Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Part {
    private final SimpleIntegerProperty year;
    private final SimpleStringProperty make;
    private final SimpleStringProperty projectModel;
    private final SimpleStringProperty category;
    private final SimpleStringProperty partName;
    private final SimpleIntegerProperty partId;
    private final SimpleDoubleProperty price;

    public Part() {
        this(1900, "", "", "", "", 0, 0);
    }

    public Part(int year, String make, String projectModel, String category, String partName, int partId, double price) {
        this.year = new SimpleIntegerProperty(year);
        this.make = new SimpleStringProperty(make);
        this.projectModel = new SimpleStringProperty(projectModel);
        this.category = new SimpleStringProperty(category);
        this.partName = new SimpleStringProperty(partName);
        this.partId = new SimpleIntegerProperty(partId);
        this.price = new SimpleDoubleProperty(price);
    }

    public int getPartId() {
        return partId.get();
    }

    public void setPartId(int partId) {
        this.partId.set(partId);
    }

    public int getYear() {
        return year.get();
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public String getMake() {
        return make.get();
    }

    public void setMake(String make) {
        this.make.set(make);
    }

    public String getProjectModel() {
        return projectModel.get();
    }

    public void setProjectModel(String projectModel) {
        this.projectModel.set(projectModel);
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getPartName() {
        return partName.get();
    }

    public void setPartName(String partName) {
        this.partName.set(partName);
    }
}
