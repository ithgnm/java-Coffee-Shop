
package bll;

import dal.billinfoDAL;
import java.sql.*;

public class billinfoBLL {
    
    billinfoDAL dal = new billinfoDAL();
    
    public void insertBillInfo(int iDBill, int iDDrink, int count) throws SQLException {
        dal.insertBillInfo(iDBill, iDDrink, count);
    }
    
    public void deleteBillInfoByIDDrink(int id) throws SQLException {
        dal.deleteBillInfoByIDDrink(id);
    }
    
    public void deleteBillInfoByIDBill(int id) throws SQLException {
        dal.deleteBillInfoByIDBill(id);
    }
}
