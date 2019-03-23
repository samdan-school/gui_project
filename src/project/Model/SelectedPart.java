package project.Model;

import javafx.beans.property.*;

public class SelectedPart {
    private final ObjectProperty<Part> part;
    private final IntegerProperty quantity;
    private final DoubleProperty subTotal;

    public SelectedPart(Part part, int quantity, double subTotal) {
        this.part = new SimpleObjectProperty<>(part);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.subTotal = new SimpleDoubleProperty(subTotal);
    }

    public void setSubTotal(double subTotal) {
        this.subTotal.set(subTotal);
    }

    public DoubleProperty subTotalProperty() {
        return subTotal;
    }

    public double getSubTotal() {
        return subTotal.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public ObjectProperty<Part> partProperty() {
        return part;
    }

    public void setPart(Part part) {
        this.part.set(part);
    }

    public Part getPart() {
        return part.get();
    }

    public IntegerProperty partIdProperty() {
        return getPart().partIdProperty();
    }
    public StringProperty partNameProperty() {
        return getPart().partNameProperty();
    }
    public DoubleProperty priceProperty() {
        return getPart().priceProperty();
    }
}
