
package dal;

import java.sql.*;

public class billinfoDAL extends dbConnect {
    
    public void insertBillInfo(int iDBill, int iDDrink, int count) throws SQLException {
        String istBillInfo = "{ call usp_insertbillinfo(?, ?, ?) }";
        dbConnect();
        PreparedStatement psData = conn.prepareStatement(istBillInfo);
        psData.setInt(1, iDBill);
        psData.setInt(2, iDDrink);
        psData.setInt(3, count);
        psData.executeUpdate();
        dbClose();
    }
    
    public void deleteBillInfoByIDDrink(int id) throws SQLException {
        String dltBillInfo = "delete dbo.billinfo where iddrink = " + id;
        executeUpdate(dltBillInfo);
    }
    
    public void deleteBillInfoByIDBill(int id) throws SQLException {
        String dltBillInfo = "delete dbo.billinfo where idbill = " + id;
        executeUpdate(dltBillInfo);
    }
}
