
package dal;

import dto.menu;
import java.sql.*;
import java.util.*;

public class menuDAL extends dbConnect {
    
    public ArrayList<menu> getMenuListByIDTable(int id) throws SQLException {
        String getMenu = "select d.name, bi.count, d.price, d.price*bi.count as total from dbo.billinfo as bi, dbo.bill as b, dbo.drink as d where bi.idbill = b.id and bi.iddrink = d.id and b.status = 0 and b.idtable = " + id;
        ArrayList<menu> menuList = new ArrayList<menu>();
        ResultSet rsData = executeQuery(getMenu);
        if (rsData != null) {
            while (rsData.next()) {
                menu mn = new menu();
                mn.setDrinkName(rsData.getString("name"));
                mn.setCount(rsData.getInt("count"));
                mn.setPrice(rsData.getFloat("price"));
                mn.setTotalPrice(rsData.getFloat("total"));
                menuList.add(mn);
            }
        }
        dbClose();
        return menuList;
    }
}
