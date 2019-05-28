
package dal;

import dto.category;
import java.sql.*;
import java.util.*;

public class categoryDAL extends dbConnect {
    
    public ArrayList<category> getCategoryList() throws SQLException {
        String getCategory = "select * from dbo.category";
        ArrayList<category> categoryList = new ArrayList<category>();
        ResultSet rsData = executeQuery(getCategory);
        if (rsData != null) {
            while (rsData.next()) {
                category cg = new category();
                cg.setID(rsData.getInt("id"));
                cg.setName(rsData.getString("name"));
                categoryList.add(cg);
            }
        }
        dbClose();
        return categoryList;
    }
    
    public category getCategoryByIDDrink(int id) throws SQLException {
        String getCategory = "select * from dbo.category where id = " + id;
        ResultSet rsData = executeQuery(getCategory);
        category cg = new category();
        if (rsData != null && rsData.next()) {
            cg.setID(rsData.getInt("id"));
            cg.setName(rsData.getString("name"));
        }
        dbClose();
        return cg;
    }
    
    public boolean insertCategory(String name) throws SQLException {
        String istCategory = "insert dbo.category (name) values (N'" + name + "')";
        return executeUpdate(istCategory);
    }
    
    public boolean deleteCategory(int id) throws SQLException {
        String dltCategory = "delete dbo.category where id = " + id;
        return executeUpdate(dltCategory);
    }
    
    public boolean updateCategory(int id, String name) throws SQLException {
        String updCategory = "update dbo.category set name = N'" + name + "' where id = " + id;
        return executeUpdate(updCategory);
    }
}
