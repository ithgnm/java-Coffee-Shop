
package bll;

import dto.drink;
import dal.drinkDAL;
import java.sql.*;
import java.util.*;

public class drinkBLL {
    
    drinkDAL dal = new drinkDAL();
    
    public ArrayList<drink> getDrinkByIDCategory(int id) throws SQLException {
        return dal.getDrinkByIDCategory(id);
    }
    
    public ArrayList<drink> getDrinkList() throws SQLException {
        return dal.getDrinkList();
    }
    
    public boolean insertDrink(String name, int idCategory, float price) throws SQLException  {
        return dal.insertDrink(name, idCategory, price);
    }
    
    public boolean deleteDrink(int id) throws SQLException {
        billinfoBLL biBLL = new billinfoBLL();
        biBLL.deleteBillInfoByIDBill(id);
        return dal.deleteDrink(id);
    }
    
    public boolean updateDrink(int id, String name, int idCategory, float price) throws SQLException {
        return dal.updateDrink(id, name, idCategory, price);
    }
    
    public void deleteDrinkByIDCategory(int id) throws SQLException {
        dal.deleteDrinkByIDCategory(id);
    }
    
    public ArrayList<drink> searchDrinkByName(String name) throws SQLException {
        return dal.searchDrinkByName(name);
    }
}
