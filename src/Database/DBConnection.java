/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class DBConnection {
    
    private static DBConnection instance = new DBConnection();
    private String url = "jdbc:sqlserver://localhost:50256;databaseName=NVNhanSu;integratedSecurity=true";
    private String user = "sa";
    private String password = "12345678";
    private String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    //private constructor:
    private DBConnection(){
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    //LÃ¢Ì�y mÃ´Ì£t thÆ°Ì£c thÃªÌ‰ database, nÃªÌ�u khÃ´ng coÌ� thiÌ€ taÌ£o mÆ¡Ì�i:
    public static DBConnection getInstance() {
        if(instance == null)
        {
            instance = new DBConnection();
        }
        return instance;
    }
    //KÃªÌ�t nÃ´Ì�i vÆ¡Ì�i database:
    public Connection getConnection() throws SQLException{
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
    
}
