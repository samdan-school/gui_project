package project.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PartService {
    public ObservableList<String> makeList(){
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            ResultSet result =  DBUtil.dbExecuteQuery("SELECT make_name FROM make");
            while(result.next()) {
                data.add(result.getObject(1).toString());
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ObservableList modelList(){
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            ResultSet result =  DBUtil.dbExecuteQuery("SELECT model_name FROM model");
            while(result.next()) {
                data.add(result.getObject(1).toString());
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ObservableList categoryList(){
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            ResultSet result =  DBUtil.dbExecuteQuery("SELECT category_name FROM category");
            while(result.next()) {
                data.add(result.getObject(1).toString());
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
