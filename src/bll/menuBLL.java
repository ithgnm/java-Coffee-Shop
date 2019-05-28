
package bll;

import dto.menu;
import dal.menuDAL;
import java.sql.*;
import java.util.*;

public class menuBLL {
    
    menuDAL dal = new menuDAL();
    
    public ArrayList<menu> getMenuListByIDTable(int id) throws SQLException {
        return dal.getMenuListByIDTable(id);
    }
}
