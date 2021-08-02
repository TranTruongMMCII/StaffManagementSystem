/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;

/**
 *
 * @author ASUS
 */
public class PositionModify {
    
        //TODO: return kyHieu + 3 so
    private static String generatePrimaryKey(String s){
        
        Random rand = new Random(); //instance of random class
        
        int upperbound = 999;
        
        //generate random values from 1-999
        int intRandom = rand.nextInt(upperbound) + 1;
        
        String temp = "000" + intRandom;
        
        temp = temp.substring(temp.length()-3);
        System.out.println("Position temp: " + temp);
        
        System.out.println("Position key: " + s + temp);
        
        return s + temp;
    }
    
    //Lấy tất cả danh sách các bộ phận:
    public static List<Position> findAll() {
        List<Position> positionList = new ArrayList<>(); 
        //Kết nối database:
        Connection con = null;
        Statement stmt = null;
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Query:
            String query = "SELECT * FROM ChucVu";
            stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            //Đọc dữ liệu từ rs được trả về:
            while(rs.next()){
                Position position = new Position(
                        rs.getString("MaCV"),
                        rs.getString("TenCV"),
                        rs.getString("PCChucVu"),
                        rs.getString("GhiChu"));
                positionList.add(position);
            }
        } catch (SQLException ex) {
                Logger.getLogger(PositionModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(stmt != null)
            {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PositionModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DepartmentModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return positionList;
    }
    
    //Lấy tất cả danh sách các bộ phận dựa trên TenCV:
    public static List<Position> findByTenCV(String TenCV) {
        
        List<Position> positionList = new ArrayList<>();
        
        //Kết nối database:
        Connection con = null;
        PreparedStatement preStmt = null;
        
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Query:
            String query = "SELECT * FROM ChucVu where TenCV LIKE ?";
            preStmt = con.prepareStatement(query);
            
            preStmt.setString(1, "%" + TenCV + "%");
            
            ResultSet rs = preStmt.executeQuery();
            
            //Đọc dữ liệu từ rs được trả về:
            while(rs.next()){
                Position position = new Position(
                        rs.getString("MaCV"),
                        rs.getString("TenCV"),
                        rs.getString("PCChucVu"),
                        rs.getString("GhiChu"));
                positionList.add(position);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(preStmt != null)
            {
                try {
                    preStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PositionModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PositionModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
        return positionList;
    }
        
    public static void insert(Position position){ 
        
        //Tìm all các position có trong database để xem đi check primary key:
        List<Position> temp = findAll();
        
        boolean check;
        String newPrimaryKey = null;
        do{
            check = false;
            newPrimaryKey = generatePrimaryKey("PB");
            for(int i =0; i < temp.size(); i++)
            {
                if(newPrimaryKey.equals(temp.get(i).getMaCV())){
                    check = true;
                    break;
                }
            }
        }while(check);
        
        //Kết nối database:
        Connection con = null;
        PreparedStatement preStmt = null;
        
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Kiểm tra MaPB có duy nhất không rồi mới thêm vào PhongBan 
            //hoặc có một cách khác là không để cho người dùng nhập MaPB => MaPB tự generate ra.
           
            //Query:
            String query = "INSERT INTO ChucVu(MaCV, TenCV, PCChucVu, GhiChu) values(?,?,?,?)";
            preStmt = con.prepareStatement(query);
            
            preStmt.setString(1, newPrimaryKey);
            preStmt.setString(2, position.getTenCV());
            preStmt.setString(3, position.getPCChucVu());
            preStmt.setString(4, position.getGhiChu());
            
            preStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PositionModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(preStmt != null)
            {
                try {
                    preStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PositionModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PositionModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    public static void update(Position position){
        
         //Kết nối database:
        Connection con = null;
        PreparedStatement preStmt = null;
        
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Query:
            String query = "UPDATE ChucVu SET MaCV=?, TenCV=?, PcChucVu=?, GhiChu=? WHERE MaCV=?";
            preStmt = con.prepareCall(query);

            preStmt.setString(1, position.getMaCV());
            preStmt.setString(2, position.getTenCV());
            preStmt.setString(3, position.getPCChucVu());
            preStmt.setString(4, position.getGhiChu());
            preStmt.setString(5, position.getMaCV());

            preStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PositionModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(preStmt != null)
            {
                try {
                    preStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PositionModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PositionModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    public static void delete(String MaCV){
        
         //Kết nối database:
        Connection con = null;
        PreparedStatement preStmt = null;
        
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Query:
            String query = "DELETE FROM ChucVu WHERE MaCV=?";
            preStmt = con.prepareCall(query);
            
            preStmt.setString(1, MaCV);

            preStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PositionModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(preStmt != null)
            {
                try {
                    preStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PositionModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PositionModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
}
