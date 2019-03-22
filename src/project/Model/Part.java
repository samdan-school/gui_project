package project.Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Part {
    private final SimpleIntegerProperty year;
    private final SimpleStringProperty makeName;
    private final SimpleStringProperty modelName;
    private final SimpleStringProperty categoryName;
    private final SimpleStringProperty partName;
    private final SimpleIntegerProperty partId;
    private final SimpleDoubleProperty price;

    public Part() {
        this(0, "", "", "", 0.0, 0, "");
    }

    public Part(int partId, String makeName, String modelName, String categoryName, double price, int year, String partName) {
        this.partId = new SimpleIntegerProperty(partId);
        this.makeName = new SimpleStringProperty(makeName);
        this.modelName = new SimpleStringProperty(modelName);
        this.categoryName = new SimpleStringProperty(categoryName);
        this.price = new SimpleDoubleProperty(price);
        this.year = new SimpleIntegerProperty(year);
        this.partName = new SimpleStringProperty(partName);
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
        return makeName.get();
    }

    public void setMake(String make) {
        this.makeName.set(make);
    }

    public String getmodel() {
        return modelName.get();
    }

    public void setmodel(String model) {
        this.modelName.set(model);
    }

    public String getCategory() {
        return categoryName.get();
    }

    public void setCategory(String category) {
        this.categoryName.set(category);
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
