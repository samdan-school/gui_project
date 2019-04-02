package exam;

import com.sun.rowset.CachedRowSetImpl;

import java.sql.*;

public class DBUtil {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver ";
    private static Connection conn = null;
    private static final String connStr = "jdbc:mysql://localhost:3306/gui_exam";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void dbConnect() throws SQLException {
        try {
            conn = DriverManager.getConnection(connStr, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console" + e);
            e.printStackTrace();
            throw e;
        }
    }

    //Close Connection
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    //exam.DB Execute Query Operation
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException {
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        try {
            //Connect to exam.DB (Establish Oracle Connection)
            dbConnect();

            //Create statement
            stmt = conn.createStatement();

            //Execute select (query) operation
            resultSet = stmt.executeQuery(queryStmt);

            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {
                //Close resultSet
                resultSet.close();
            }
            if (stmt != null) {
                //Close Statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
        //Return CachedRowSet
        return crs;
    }

    //exam.DB Execute Update (For Update/Insert/Delete) Operation
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException {
        //Declare statement as null
        PreparedStatement stmt = null;
        int affectedRows = 0;
        int returnId = -1;
        try {
            //Connect to exam.DB (Establish Oracle Connection)
            dbConnect();
            //Create Statement
            stmt = conn.prepareStatement(sqlStmt, Statement.RETURN_GENERATED_KEYS);
            //Run executeUpdate operation with given sql statement
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
    }


    public static int findMaxId(String columnId, String tableName) {
        int returnId = -1;
        try {
            ResultSet maxId = DBUtil.dbExecuteQuery("SELECT MAX(" + columnId + ") as 'max' FROM " + tableName);
            if (maxId.next()) {
                returnId = maxId.getInt("max");
                returnId++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnId;
    }
}
