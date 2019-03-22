package project.Service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import project.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PartService {
    public static ObservableList<String> makeList(){
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
    public static ObservableList<String> modelList(String makeName){
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            ResultSet result =  DBUtil.dbExecuteQuery("SELECT model_name FROM make_model WHERE make_name ='" + makeName +"'");
            while(result.next()) {
                data.add(result.getObject(1).toString());
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ObservableList<String> categoryList(){
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
