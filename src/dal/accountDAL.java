
package dal;

import dto.account;
import java.sql.*;
import java.util.*;

public class accountDAL  extends dbConnect {
    
    public String getAccountPassword(String userName) throws SQLException {
        String getAccount = "select * from dbo.account where username = '" + userName + "'";
        String hashPassword = new String();
        ResultSet rsData = executeQuery(getAccount);
        if (rsData != null && rsData.next()) hashPassword = rsData.getString("password");
        dbClose();
        return hashPassword;
    }
    
    public account getAccountByUserName(String userName) throws SQLException {
        String getAccount = "select * from account where username = '" + userName + "'";
        account acc = new account();
        ResultSet rsData = executeQuery(getAccount);
        if (rsData != null) {
            while (rsData.next()) {
                acc.setID(rsData.getInt("id"));
                acc.setDisplayName(rsData.getString("displayname"));
                acc.setUserName(rsData.getString("username"));
                acc.setType(rsData.getInt("type"));
                acc.setTypeName(rsData.getString("typename"));
            }
        }
        dbClose();
        return acc;
    }
    
    public ArrayList<account> getAccountList() throws SQLException {
        String getAccount = "select * from dbo.account";
        ArrayList<account> accountList = new ArrayList<account>();
        ResultSet rsData = executeQuery(getAccount);
        if (rsData != null) {
            while (rsData.next()) {
                account acc = new account();
                acc.setID(rsData.getInt("id"));
                acc.setUserName(rsData.getString("username"));
                acc.setDisplayName(rsData.getString("displayname"));
                acc.setTypeName(rsData.getString("typename"));
                accountList.add(acc);
            }
        }
        dbClose();
        return accountList;
    }
    
    public boolean insertAccount(String userName, String displayName, String password, int type, String typeName) throws SQLException {
        String istAccount = "insert dbo.account values (?, ?, ?, ?, ?)";
        dbConnect();
        PreparedStatement psData = conn.prepareStatement(istAccount);
        psData.setString(1, userName);
        psData.setString(2, displayName);
        psData.setString(3, password);
        psData.setInt(4, type);
        psData.setString(5, typeName);
        int result = psData.executeUpdate();
        dbClose();
        return result > 0;
    }
    
    public boolean removeAccount(int id) throws SQLException {
        String dltAccount = "delete dbo.account where id = " + id;
        return executeUpdate(dltAccount);
    }
    
    public boolean updateAccount(int id, String userName, String displayName, int type, String typeName) throws SQLException {
        String updAccount = "update dbo.account set username = ?, displayname = ?, type = ?, typename = ? where id = ?";
        dbConnect();
        PreparedStatement psData = conn.prepareStatement(updAccount);
        psData.setString(1, userName);
        psData.setString(2, displayName);
        psData.setInt(3, type);
        psData.setString(4, typeName);
        psData.setInt(5, id);
        int result = psData.executeUpdate();
        dbClose();
        return result > 0;
    }
    
    public boolean setAccountPassword(int id, String hashPassword) throws SQLException {
        String resetAccount = "update dbo.account set password = '" + hashPassword + "' where id = " + id;
        return executeUpdate(resetAccount);
    }
}