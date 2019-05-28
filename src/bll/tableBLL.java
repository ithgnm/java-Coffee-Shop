
package bll;

import dto.table;
import dal.tableDAL;
import java.sql.*;
import java.util.*;

public class tableBLL {
    
    tableDAL dal = new tableDAL();
    
    public ArrayList<table> getTableList() throws SQLException {
        return dal.getTableList();
    }
    
    public void switchTable(int id1, int id2) throws SQLException {
        dal.switchTable(id1, id2);
    }
    
    public boolean insertTable(String name) throws SQLException {
        return dal.insertTable(name);
    }
    
    public boolean removeTable(int id) throws SQLException {
        billBLL bBLL = new billBLL();
        bBLL.deleteBillByIDTable(id);
        return dal.removeTable(id);
    }
    
    public boolean updateTable(int id, String name, String status) throws SQLException {
        return dal.updateTable(id, name, status);
    }
}