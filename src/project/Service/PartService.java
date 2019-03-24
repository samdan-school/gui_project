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
                data.add(createNewPartByResultSet(result));
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
                data.add(createNewPartByResultSet(result));
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertCartInfo(ObservableList<SelectedPart> selectedParts, int cartId) {
        String cartsInsertQuery = "INSERT INTO carts(cart_id, part_id, quantity) VALUES("
                + "'" + cartId + "', ";

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

    public static void insertCustomerOrder(ObservableList<SelectedPart> selectedParts, String customerId, String taxRate, String partTotal) {
        int maxCartId = DBUtil.findMaxId("cart_id", "carts");
        String customerOrderInsertQuery = "INSERT INTO customer_order(receipt_number, tax_rate, total_amount, cart_id) VALUES("
                + "'" + customerId + "',"
                + "'" + taxRate + "', "
                + "'" + partTotal + "', "
                + "'" + maxCartId + "'"
                + ");";
        try {
            insertCartInfo(selectedParts, maxCartId);
            DBUtil.dbExecuteUpdate(customerOrderInsertQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<SelectedPart> getSelectedPartsByReceiptNumber(String receiptNumber) {
        ObservableList<SelectedPart> selectedParts = FXCollections.observableArrayList();
        try {
            ResultSet customerOrder = DBUtil.dbExecuteQuery("SELECT * FROM customer_order WHERE receipt_number = " + receiptNumber);
            customerOrder.next();
            String cartId = customerOrder.getString("cart_id");
            ResultSet carts = DBUtil.dbExecuteQuery("SELECT * FROM carts WHERE cart_id = " + cartId);
            while (carts.next()) {
                ResultSet partRes = DBUtil.dbExecuteQuery("SELECT * FROM part WHERE part_id = " + carts.getString("part_id"));
                partRes.next();
                Part part = createNewPartByResultSet(partRes);
                selectedParts.add(
                        new SelectedPart(
                                part,
                                carts.getInt("quantity"),
                                part.calculateSubTotal(carts.getInt("quantity"))
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedParts;
    }

    public static String getCartIdByReceiptNumber(String receiptNumber) {
        String cartId = "";
        try {
            ResultSet customerOrder = DBUtil.dbExecuteQuery("SELECT * FROM customer_order WHERE receipt_number = " + receiptNumber);
            customerOrder.next();
            cartId = customerOrder.getString("cart_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartId;
    }

    public static void deleteCartInfoByCartId(String cartId) {
        try {
            DBUtil.dbExecuteUpdate("DELETE FROM carts WHERE cart_id = " + cartId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCustomerOrderByReceiptNumber(String receiptNumber) {
        try {
            DBUtil.dbExecuteUpdate("DELETE FROM customer_order WHERE receipt_number = " + receiptNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Part createNewPartByResultSet(ResultSet part) throws SQLException {
        return new Part(
                part.getInt("part_id"),
                part.getString("make_name"),
                part.getString("model_name"),
                part.getString("category_name"),
                part.getDouble("unit_price"),
                part.getInt("part_year"),
                part.getString("part_name")
        );
    }

    public static boolean isCustomerOrderExist(String receiptNumber) {
        boolean exist = false;
        try {
            ResultSet customerOrder = DBUtil.dbExecuteQuery("SELECT * FROM customer_order WHERE receipt_number = " + receiptNumber);
            if (customerOrder.next()) {
                exist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }
}
