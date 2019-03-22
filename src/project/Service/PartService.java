package project.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.DBUtil;
import project.Model.Part;

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
            ResultSet result = DBUtil.dbExecuteQuery("select* from part");
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
}
