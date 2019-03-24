package project.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.DBUtil;
import project.Model.Part;
import project.Model.SelectedPart;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PartService {
    public static ObservableList<String> makeList() {
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            ResultSet result = DBUtil.dbExecuteQuery("SELECT make_name FROM make");
            while (result.next()) {
                data.add(result.getObject(1).toString());
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<String> modelList(String makeName) {
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            ResultSet result = DBUtil.dbExecuteQuery("SELECT model_name FROM make_model WHERE make_name ='" + makeName + "'");
            while (result.next()) {
                data.add(result.getObject(1).toString());
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<String> categoryList() {
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            ResultSet result = DBUtil.dbExecuteQuery("SELECT category_name FROM category");
            while (result.next()) {
                data.add(result.getObject(1).toString());
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Part> partList() {

        ObservableList<Part> data = FXCollections.observableArrayList();
        try {
            ResultSet result = DBUtil.dbExecuteQuery("select * from part");
            while (result.next()) {
                data.add(new Part(
                        result.getInt("part_id"),
                        result.getString("make_name"),
                        result.getString("model_name"),
                        result.getString("category_name"),
                        result.getDouble("unit_price"),
                        result.getInt("part_year"),
                        result.getString("part_name")
                ));
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Part> availablePartList(String[] names) {

        ObservableList<Part> data = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM part ";
            if (names.length == 4) {
                query += "WHERE category_name ='" + names[0] + "'"
                        + "AND model_name ='" + names[1] + "'"
                        + "AND make_name ='" + names[2] + "'"
                        + "AND part_year ='" + names[3] + "';";
            }
            if (names.length == 3) {
                query += "WHERE model_name ='" + names[0] + "'"
                        + "AND make_name ='" + names[1] + "'"
                        + "AND part_year ='" + names[2] + "';";
            }
            if (names.length == 2) {
                query += "WHERE make_name ='" + names[0] + "'"
                        + "AND part_year ='" + names[1] + "';";
            }
            if (names.length == 1) {
                query += "WHERE part_year ='" + names[0] + "';";
            }
            ResultSet result = DBUtil.dbExecuteQuery(query);
            while (result.next()) {
                data.add(new Part(
                        result.getInt("part_id"),
                        result.getString("make_name"),
                        result.getString("model_name"),
                        result.getString("category_name"),
                        result.getDouble("unit_price"),
                        result.getInt("part_year"),
                        result.getString("part_name")
                ));
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertCartInfo(ObservableList<SelectedPart> selectedParts, int orderId) {
        String cartsInsertQuery = "INSERT INTO cart_info(order_detail_id, part_id, quantity) VALUES("
                + "'" + orderId + "', ";

        String itemInsert;
        for (SelectedPart selectedPart : selectedParts) {
            itemInsert = cartsInsertQuery
                    + "'" + selectedPart.getPart().getPartId() +"', "
                    + "'" + selectedPart.getQuantity() +"'"
                    + ");";
            try {
                DBUtil.dbExecuteUpdate(itemInsert);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void insertCustomerOrder(ObservableList<SelectedPart> selectedParts, String customerId, String taxRate, String totalAmount) {
        int maxOrderDetailId = DBUtil.findMaxId("order_detail_id", "order_detail");
        String orderDetailInsertQuery = "INSERT INTO order_detail VALUES( '" + maxOrderDetailId +"')";
        String customerOrderInsertQuery = "INSERT INTO customer_order(receipt_number, tax_rate, total_amount, order_detail_id) VALUES("
                + "'" + customerId + "',"
                + "'" + taxRate + "', "
                + "'" + totalAmount + "', "
                + "'" + maxOrderDetailId + "'"
                + ");";
        System.out.println(maxOrderDetailId);
        try {
            DBUtil.dbExecuteUpdate(orderDetailInsertQuery);
            DBUtil.dbExecuteUpdate(customerOrderInsertQuery);
            insertCartInfo(selectedParts, maxOrderDetailId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
