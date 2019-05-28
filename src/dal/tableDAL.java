
package dal;

import dto.table;
import java.sql.*;
import java.util.*;

public class tableDAL extends dbConnect {
    
    public static int tbWidth = 159;
    public static int tbHeight = 96;
    
    public ArrayList<table> getTableList() throws SQLException {
        String getTable = "select * from dbo.cftable";
        ArrayList<table> tableList = new ArrayList<>();
        ResultSet rsData = executeQuery(getTable);
        if (rsData != null) {
            while (rsData.next()) {
                table tb = new table();
                tb.setID(rsData.getInt("id"));
                tb.setName(rsData.getString("name"));
                tb.setStatus(rsData.getString("status"));
                tableList.add(tb);
            }
        }
        dbClose();
        return tableList;
    }
    
    public void switchTable(int id1, int id2) throws SQLException {
        String swtTable = "{ call usp_switchtable(?, ?) }";
        dbConnect();
        PreparedStatement psData = conn.prepareStatement(swtTable);
        psData.setInt(1, id1);
        psData.setInt(2, id2);
        psData.executeUpdate();
        dbClose();
    }
    
    public boolean insertTable(String name) throws SQLException {
        String istTable = "insert dbo.cftable (name, status) values (N'" + name + "', 'Empty')";
        return executeUpdate(istTable);
    }
    
    public boolean removeTable(int id) throws SQLException {
        String dltTable = "delete dbo.cftable where id = " + id;
        return executeUpdate(dltTable);
    }
    
    public boolean updateTable(int id, String name, String status) throws SQLException {
        String updTable = "update dbo.cftable set name = N'" + name + "', status = N'" + status + "' where id = " + id;
        return executeUpdate(updTable);
    }
}
