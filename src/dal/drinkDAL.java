
package dal;

import dto.drink;
import java.sql.*;
import java.util.*;

public class drinkDAL extends dbConnect {
    
    public ArrayList<drink> getDrinkByIDCategory(int id) throws SQLException {
        String getDrink = "select * from dbo.drink where idcategory = " + id;
        ArrayList<drink> drinkList = new ArrayList<drink>();
        ResultSet rsData = executeQuery(getDrink);
        if (rsData != null) {
            while (rsData.next()) {
                drink dr = new drink();
                dr.setID(rsData.getInt("id"));
                dr.setName(rsData.getString("name"));
                drinkList.add(dr);
            }
        }
        dbClose();
        return drinkList;
    }
    
    public ArrayList<drink> getDrinkList() throws SQLException {
        String getDrink = "select * from dbo.drink";
        ArrayList<drink> drinkList = new ArrayList<drink>();
        ResultSet rsData = executeQuery(getDrink);
        if (rsData != null) {
            while (rsData.next()) {
                drink dr = new drink();
                dr.setID(rsData.getInt("id"));
                dr.setName(rsData.getString("name"));
                dr.setIDCategory(rsData.getInt("idcategory"));
                dr.setPrice(rsData.getFloat("price"));
                drinkList.add(dr);
            }
        }
        dbClose();
        return drinkList;
    }
    
    public boolean insertDrink(String name, int idCategory, float price) throws SQLException {
        String istDrink = "insert dbo.drink (name, idcategory, price) values (?, ?, ?)";
        dbConnect();
        PreparedStatement psData = conn.prepareStatement(istDrink);
        psData.setString(1, name);
        psData.setInt(2, idCategory);
        psData.setFloat(3, price);
        int result = psData.executeUpdate();
        dbClose();
        return result > 0;
    }
    
    public boolean deleteDrink(int id) throws SQLException {
        String dltDrink = "delete dbo.drink where id = " + id;
        return executeUpdate(dltDrink);
    }
    
    public boolean updateDrink(int id, String name, int idCategory, float price) throws SQLException {
        String updDrink = "update dbo.drink set name = N'" + name + "', idcategory = " + idCategory + ", price = " + price + " where id = " + id;
        return executeUpdate(updDrink);
    }
    
    public void deleteDrinkByIDCategory(int id) throws SQLException {
        String dltDrink = "select * from dbo.drink where idcategory = " + id;
        ResultSet rsData = executeQuery(dltDrink);
        if (rsData != null) {
            while (rsData.next()) {
                int iDDrink = rsData.getInt("id");
                deleteDrink(iDDrink);
            }
        }
    }
    
    public ArrayList<drink> searchDrinkByName(String name) throws SQLException {
        String srcDrink = "select * from dbo.drink where name like N'%" + name + "%'";
        ResultSet rsData = executeQuery(srcDrink);
        ArrayList<drink> drinkList = new ArrayList<drink>();
        if (rsData != null) {
            while (rsData.next()) {
                drink dr = new drink();
                dr.setID(rsData.getInt("id"));
                dr.setName(rsData.getString("name"));
                dr.setIDCategory(rsData.getInt("idcategory"));
                dr.setPrice(rsData.getFloat("price"));
                drinkList.add(dr);
            }
        }
        dbClose();
        return drinkList;
    }
}
