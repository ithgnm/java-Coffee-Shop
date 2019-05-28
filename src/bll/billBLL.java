
package bll;

import dal.billDAL;
import java.sql.*;
import java.text.ParseException;
import java.util.*;

public class billBLL {
    
    billDAL dal = new billDAL();
    
    public int getUncheckBillByIDTable(int id) throws SQLException {
        return dal.getUncheckBillByIDTable(id);
    }
    
    public void insertBillIntoTable(int id) throws SQLException {
        dal.insertBillIntoTable(id);
    }
    
    public int getLatestIDBill() throws SQLException {
        return dal.getLatestIDBill();
    }
    
    public void updatePayedBill(int id, int discount, float totalPrice) throws SQLException {
        dal.updatePayedBill(id, discount, totalPrice);
    }
    
    public ArrayList<Vector> getBillListByDate(String fromDate, String toDate) throws ParseException, SQLException {
        return dal.getBillListByDate(fromDate, toDate);
    }
    
    public void deleteBillByIDTable(int id) throws SQLException {
        dal.deleteBillByIDTable(id);
    }
}
