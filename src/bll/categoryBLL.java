
package bll;

import dto.category;
import dal.categoryDAL;
import java.sql.*;
import java.util.*;

public class categoryBLL {
    
    categoryDAL dal = new categoryDAL();
    
    public ArrayList<category> getCategoryList() throws SQLException {
        return dal.getCategoryList();
    }
    
    public category getCategoryByIDDrink(int id) throws SQLException {
        return dal.getCategoryByIDDrink(id);
    }
    
    public boolean insertCategory(String name) throws SQLException {
        return dal.insertCategory(name);
    }
    
    public boolean deleteCategory(int id) throws SQLException {
        drinkBLL drBLL = new drinkBLL();
        drBLL.deleteDrinkByIDCategory(id);
        return dal.deleteCategory(id);
    }
    
    public boolean updateCategory(int id, String name) throws SQLException {
        return dal.updateCategory(id, name);
    }
}
