
package dal;

import java.sql.*;

public class dbConnect {
    
    private static String dbName = "coffeeshop";
    private static String userName = "sa";
    private static String password = "1";
    private static String strConnect = "jdbc:sqlserver://localhost:1433;databaseName=" + dbName;
    protected Connection conn;
    
    public void dbConnect() throws SQLException {
        conn = DriverManager.getConnection(strConnect, userName, password);
    }
    
    public void dbClose() throws SQLException {
        conn.close();
    }
    
    public ResultSet executeQuery(String query) throws SQLException {
        ResultSet rsData = null;
        dbConnect();
        PreparedStatement psData = conn.prepareStatement(query);
        rsData = psData.executeQuery();
        return rsData;
    }
    
    public boolean executeUpdate(String query) throws SQLException {
        dbConnect();
        PreparedStatement psData = conn.prepareStatement(query);
        int result = psData.executeUpdate();
        dbClose();
        return result > 0;
    }
}
