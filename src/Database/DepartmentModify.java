/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class DepartmentModify {
    
    //Generate primary key:
    //TODO: return kyHieu + 3 so
    private static String generatePrimaryKey(String s){
        
        Random rand = new Random(); //instance of random class
        
        int upperbound = 999;
        
        //generate random values from 1-999
        int intRandom = rand.nextInt(upperbound) + 1;
        
        String temp = "000" + intRandom;
        
        temp = temp.substring(temp.length()-3);
        System.out.println("Department temp: " + temp);
        
        System.out.println("Department key: " + s + temp);
        
        return s + temp;
    }
    
    //Lấy tất cả danh sách các bộ phận:
    public static List<Department> findAll() {
        List<Department> departmentList = new ArrayList<>(); 
        //Kết nối database:
        Connection con = null;
        Statement stmt = null;
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Query:
            String query = "SELECT * FROM PhongBan";
            stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            //Đọc dữ liệu từ rs được trả về:
            while(rs.next()){
                Department department = new Department(
                        rs.getString("MaPB"),
                        rs.getString("TenPB"),
                        rs.getString("NgayTL"),
                        rs.getString("SDT"),
                        rs.getString("Email"),
                        rs.getString("ChucNang"));
                departmentList.add(department);
            }
        } catch (SQLException ex) {
                Logger.getLogger(DepartmentModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(stmt != null)
            {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DepartmentModify.class.getName()).log(Level.SEVERE, null, ex);
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
        return departmentList;
    }
    
    //Lấy tất cả danh sách các bộ phận dựa trên TenPB:
    public static List<Department> findByTenPB(String TenPB) {
        
        List<Department> departmentList = new ArrayList<>();
        
        //Kết nối database:
        Connection con = null;
        PreparedStatement preStmt = null;
        
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Query:
            String query = "SELECT * FROM PhongBan where TenPB LIKE ?";
            preStmt = con.prepareStatement(query);
            
            preStmt.setString(1, "%" + TenPB + "%");
            
            ResultSet rs = preStmt.executeQuery();
            
            //Đọc dữ liệu từ rs được trả về:
            while(rs.next()){
                Department department = new Department(
                        rs.getString("MaPB"),
                        rs.getString("TenPB"),
                        rs.getString("NgayTL"), 
                        rs.getString("SDT"),
                        rs.getString("Email"),
                        rs.getString("ChucNang"));
                departmentList.add(department);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(preStmt != null)
            {
                try {
                    preStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DepartmentModify.class.getName()).log(Level.SEVERE, null, ex);
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
        
        
        return departmentList;
    }
        
    public static void insert(Department department){ 
        
        //Tìm all các department có trong database để xem đi check primary key:
        List<Department> temp = findAll();
        
        boolean check;
        String newPrimaryKey = null;
        do{
            check = false;
            newPrimaryKey = generatePrimaryKey("PB");
            for(int i =0; i < temp.size(); i++)
            {
                if(newPrimaryKey.equals(temp.get(i).getMaPB())){
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
            String query = "INSERT INTO PhongBan(MaPB, TenPB, NgayTL, SDT, Email, ChucNang) values(?,?,?,?,?,?)";
            preStmt = con.prepareStatement(query);
            
            preStmt.setString(1, newPrimaryKey);
            preStmt.setString(2, department.getTenPB());
            preStmt.setString(3, department.getNgayTL());
            preStmt.setString(4, department.getSDT());
            preStmt.setString(5, department.getEmail());
            preStmt.setString(6, department.getChucNang());
            
            preStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(preStmt != null)
            {
                try {
                    preStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DepartmentModify.class.getName()).log(Level.SEVERE, null, ex);
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
        
    }
    
    public static void update(Department department){
        
         //Kết nối database:
        Connection con = null;
        PreparedStatement preStmt = null;
        
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Query:
            String query = "UPDATE PhongBan SET MaPB=?, TenPB=?, NgayTL=?, SDT=?, Email=?, ChucNang=? WHERE MaPB=?";
            preStmt = con.prepareCall(query);

            preStmt.setString(1, department.getMaPB());
            preStmt.setString(2, department.getTenPB());
            preStmt.setString(3, department.getNgayTL());
            preStmt.setString(4, department.getSDT());
            preStmt.setString(5, department.getEmail());
            preStmt.setString(6, department.getChucNang());
            preStmt.setString(7, department.getMaPB());

            preStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(preStmt != null)
            {
                try {
                    preStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DepartmentModify.class.getName()).log(Level.SEVERE, null, ex);
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
        
    }
    
    public static void delete(String MaPB){
        
         //Kết nối database:
        Connection con = null;
        PreparedStatement preStmt = null;
        
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Query:
            String query = "DELETE FROM PhongBan WHERE MaPB=?";
            preStmt = con.prepareCall(query);
            
            preStmt.setString(1, MaPB);

            preStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(preStmt != null)
            {
                try {
                    preStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DepartmentModify.class.getName()).log(Level.SEVERE, null, ex);
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
        
    }
}
