
package bll;

import dto.account;
import dal.accountDAL;
import java.sql.*;
import java.util.*;
import org.mindrot.jbcrypt.BCrypt;

public class accountBLL {
    
    accountDAL dal = new accountDAL();
    String hashSalt = BCrypt.gensalt(12);
    
    public boolean getAccount(String userName, String password) throws SQLException {
        String hashPassword = dal.getAccountPassword(userName);
        try { return BCrypt.checkpw(password, hashPassword); }
        catch (Exception ex) { return false; }
    }
    
    public account getAccountByUserName(String userName) throws SQLException {
        return dal.getAccountByUserName(userName);
    }
    
    public boolean updateAccountPassword(String userName, String password, String newPassword) throws SQLException {
        String hashPassword = BCrypt.hashpw(password, hashSalt);
        String hashNewPassword = BCrypt.hashpw(newPassword, hashSalt);
        account acc = dal.getAccountByUserName(userName);
        int id = acc.getID();
        if (BCrypt.checkpw(password, hashPassword)) 
            return dal.setAccountPassword(id, hashNewPassword);
        else return false;
    }
    
    public ArrayList<account> getAccountList() throws SQLException {
        return dal.getAccountList();
    }
    
    public boolean insertAccount(String userName, String displayName, int type, String typeName) throws SQLException {
        String hashPassword = BCrypt.hashpw("1", hashSalt);
        return dal.insertAccount(userName, displayName, hashPassword, type, typeName);
    }
    
    public boolean removeAccount(int id) throws SQLException {
        return dal.removeAccount(id);
    }
    
    public boolean updateAccount(int id, String userName, String displayName, int type, String typeName) throws SQLException {
        return dal.updateAccount(id, userName, displayName, type, typeName);
    }
    
    public boolean resetPasswordAccount(int id) throws SQLException {
        String hashPassword = BCrypt.hashpw("1", hashSalt);
        return dal.setAccountPassword(id, hashPassword);
    }
}