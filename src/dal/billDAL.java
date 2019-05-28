
package dal;

import dto.bill;
import java.sql.*;
import java.text.*;
import java.util.*;

public class billDAL extends dbConnect {
    
    public int getUncheckBillByIDTable(int id) throws SQLException {
        String getBill = "select * from dbo.bill where idtable = " + id + "and status = 0";
        int IDBill = -1;
        ResultSet rsData = executeQuery(getBill);
        if (rsData != null) {
            while (rsData.next()) {
                bill b = new bill();
                b.setID(rsData.getInt("id"));
                IDBill = b.getID();
            }
        }
        dbClose();
        return IDBill;
        
    }
        
    public void insertBillIntoTable(int id) throws SQLException {
        String istBill = "insert dbo.bill (checkin, checkout, idtable, status, discount) values (getdate(), null, " + id + ", 0, 0)";
        executeUpdate(istBill);
    }

    public int getLatestIDBill() throws SQLException {
        String getBill = "select max(id) as [id] from dbo.bill";
        int iDBill = 1;
        ResultSet rsData = executeQuery(getBill);
        if (rsData != null && rsData.next()) {
            iDBill = rsData.getInt("id");
        }
        dbClose();
        return iDBill;
    }
    
    public void updatePayedBill(int id, int discount, float totalPrice) throws SQLException {
        String updBill = "update dbo.bill set status = 1, checkout = getdate(), " + "discount = " + discount + ", totalprice = " + totalPrice + " where id = " + id;
        executeUpdate(updBill);
    }
    
    public ArrayList<Vector> getBillListByDate(String fromDate, String toDate) throws ParseException, SQLException {
        String getBill = "select name, checkin, checkout, discount, totalprice from dbo.bill, dbo.cftable where checkin >= '" + fromDate + "' and checkout <= '" + toDate + "' and bill.status = 1 and cftable.id = idtable";
        ArrayList<Vector> billList = new ArrayList<>();
        ResultSet rsData = executeQuery(getBill);
        if (rsData != null) {
            while (rsData.next()) {
                Vector b = new Vector();
                b.add(rsData.getString("name"));
                b.add(rsData.getDate("checkin"));
                b.add(rsData.getDate("checkout"));
                b.add(rsData.getInt("discount"));
                b.add(rsData.getFloat("totalprice"));
                billList.add(b);
            }
        }
        dbClose();
        return billList;
    }
    
    public void deleteBillByIDTable(int id) throws SQLException {
        String getBill = "select * from dbo.bill where idtable = " + id;
        billinfoDAL biDAL = new billinfoDAL();
        ResultSet rsData = executeQuery(getBill);
        if (rsData != null) {
            while (rsData.next()) {
                biDAL.deleteBillInfoByIDBill(rsData.getInt("id"));
                String dltBill = "delete dbo.bill where id = " + rsData.getInt("id") + " and status = 0";
                executeUpdate(dltBill);
            }
        }
        dbClose();
    }
}
