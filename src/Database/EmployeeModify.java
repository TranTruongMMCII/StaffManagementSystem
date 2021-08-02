/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ASUS
 */
public class EmployeeModify {
    
        //Generate primary key:
    //TODO: return kyHieu + 3 so
    private static String generatePrimaryKey(String s){
        Random rand = new Random(); //instance of random class        
        int upperbound = 999;
        
        //generate random values from 1-999
        int intRandom = rand.nextInt(upperbound) + 1;   
        String temp = "000" + intRandom;        
        temp = temp.substring(temp.length()-3);
        
        System.out.println("Employee temp: " + temp);        
        System.out.println("Employee key: " + s + temp);        
        return s + temp;
    }
    
    //Lấy tất cả danh sách các nhân viên:
    public static List<EmployeeReport> findAll() {
        List<EmployeeReport> employeeList = new ArrayList<>();
        //Kết nối database:
        Connection con = null;
        Statement stmt = null;        
        try {
            con = DBConnection.getInstance().getConnection();            
            //Query:
            String query = "SELECT * FROM NhanVien nv INNER JOIN PhongBan pb ON nv.MaPB = pb.MaPB INNER JOIN ChucVu cv ON nv.MaCV = cv.MaCV";
            stmt = con.createStatement();            
            ResultSet rs = stmt.executeQuery(query);
            
            //Đọc dữ liệu từ rs được trả về:
            while(rs.next()){
                EmployeeReport employee = new EmployeeReport(
                        rs.getString("MaNV"),
                        rs.getString("HoTen"),
                        rs.getString("NgaySinh"),
                        rs.getString("GioiTinh"),
                        rs.getString("TenPB"),
                        rs.getString("TenCV"),
                        rs.getString("DiaChi"),
                        rs.getString("CMND"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("HeSoLuong"));
                employee.setBytes(rs.getBytes("Hinh"));
                employeeList.add(employee);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(stmt != null)
            {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return employeeList;
    }
    
    //Lấy tất cả danh sách các nhân viên:
    public static List<Employee> findAllReport() {
        List<Employee> employeeLreport = new ArrayList<>();
        //Kết nối database:
        Connection con = null;
        Statement stmt = null;        
        try {
            con = DBConnection.getInstance().getConnection();            
            //Query:
            String query = "SELECT * FROM NhanVien";
            stmt = con.createStatement();            
            ResultSet rs = stmt.executeQuery(query);
            
            //Đọc dữ liệu từ rs được trả về:
            while(rs.next()){
                Employee employeereport = new Employee(
                        rs.getString("MaNV"),
                        rs.getString("HoTen"),
                        rs.getString("NgaySinh"),
                        rs.getString("GioiTinh"),
                        rs.getString("MaPB"),
                        rs.getString("MaCV"),
                        rs.getString("DiaChi"),
                        rs.getString("CMND"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("HeSoLuong"));
                employeeLreport.add(employeereport);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(stmt != null)
            {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return employeeLreport;
    }
    
    //Lấy tất cả danh sách các bộ phận:
    public static List<Department> getAllDepartment() {
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
    
    
    //Lấy danh sách tất cả name của phòng ban:
    public static List<String> getAllDepartmentName(){
        List<String> departmentNameList = new ArrayList<>();
        //Kết nối database:
        Connection con = null;
        Statement stmt = null;
        
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Query:
            String query = "SELECT TenPB FROM PhongBan";
            stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            //Đọc dữ liệu từ rs được trả về:
            while(rs.next()){
                String id = rs.getString("TenPB");
                        
                departmentNameList.add(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(stmt != null)
            {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return departmentNameList;
    } 
    
    //Lấy tất cả danh sách các nhân viên dựa trên MaNV:
    public static String getDepartmentID(String departmentName) {
        String departmentID = null;
        //Kết nối database:
        Connection con = null;
        PreparedStatement preStmt = null;
        
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Query:
            String query = "SELECT MaPB FROM PhongBan where TenPB = ?";
            preStmt = con.prepareStatement(query);
            
            preStmt.setString(1, "%" + departmentName + "%");
            
            ResultSet rs = preStmt.executeQuery();
            
            //Đọc dữ liệu từ rs được trả về:
            while(rs.next()){
                departmentID = rs.getString("MaPB");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(preStmt != null)
            {
                try {
                    preStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return departmentID;
    }  
    
    //Lấy danh sách tất cả name của chức vụ:
    public static List<String> getAllRoleName(){
        List<String> roleNameList = new ArrayList<>();
        //Kết nối database:
        Connection con = null;
        Statement stmt = null;
        
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Query:
            String query = "SELECT TenCV FROM ChucVu";
            stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            //Đọc dữ liệu từ rs được trả về:
            while(rs.next()){
                String id = rs.getString("TenCV");
                        
                roleNameList.add(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(stmt != null)
            {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return roleNameList;
    }
    
    
    //Lấy tất cả danh sách các nhân viên dựa trên MaNV:
    public static List<EmployeeReport> findByMaNV(String MaNV) {
        
        List<EmployeeReport> employeeList = new ArrayList<>();
        
        //Kết nối database:
        Connection con = null;
        PreparedStatement preStmt = null;
        
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Query:
            String query = "SELECT MaNV, HoTen, NgaySinh, GioiTinh, TenPB, TenCV, DiaChi, CMND, DienThoai, NhanVien.Email, HeSoLuong\r\n" + 
            		"FROM dbo.NhanVien, dbo.ChucVu, dbo.PhongBan\r\n" + 
            		"WHERE NhanVien.MaPB = PhongBan.MaPB AND ChucVu.MaCV = NhanVien.MaCV AND MaNV LIKE ?";
            preStmt = con.prepareStatement(query);
            
            preStmt.setString(1, "%" + MaNV + "%");
            
            ResultSet rs = preStmt.executeQuery();
            
            //Đọc dữ liệu từ rs được trả về:
            while(rs.next()){
                EmployeeReport employee = new EmployeeReport(
                        rs.getString("MaNV"),
                        rs.getString("HoTen"),
                        rs.getString("NgaySinh"),
                        rs.getString("GioiTinh"),
                        rs.getString("TenPB"),
                        rs.getString("TenCV"),
                        rs.getString("DiaChi"),
                        rs.getString("CMND"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("HeSoLuong"));
                employeeList.add(employee);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(preStmt != null)
            {
                try {
                    preStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
        return employeeList;
    }
    
    //Lấy tất cả danh sách các nhân viên dựa trên họ tên nhân viên:
    public static List<EmployeeReport> findByHoTen(String HoTen) {
        
        List<EmployeeReport> employeeList = new ArrayList<>();
        
        //Kết nối database:
        Connection con = null;
        PreparedStatement preStmt = null;
        
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Query:
            String query = "SELECT MaNV, HoTen, NgaySinh, GioiTinh, TenPB, TenCV, DiaChi, CMND, DienThoai, NhanVien.Email, HeSoLuong\r\n" + 
            		"FROM dbo.NhanVien, dbo.ChucVu, dbo.PhongBan\r\n" + 
            		"WHERE NhanVien.MaPB = PhongBan.MaPB AND ChucVu.MaCV = NhanVien.MaCV AND HoTen LIKE ?";
            preStmt = con.prepareStatement(query);
            
            preStmt.setString(1, "%" + HoTen + "%");
            
            ResultSet rs = preStmt.executeQuery();
            
            //Ä�oÌ£c dÆ°Ìƒ liÃªÌ£u tÆ°Ì€ rs Ä‘Æ°Æ¡Ì£c traÌ‰ vÃªÌ€:
            while(rs.next()){
                EmployeeReport employee = new EmployeeReport(
                        rs.getString("MaNV"),
                        rs.getString("HoTen"),
                        rs.getString("NgaySinh"),
                        rs.getString("GioiTinh"),
                        rs.getString("TenPB"),
                        rs.getString("TenCV"),
                        rs.getString("DiaChi"),
                        rs.getString("CMND"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("HeSoLuong"));
                employeeList.add(employee);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(preStmt != null)
            {
                try {
                    preStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }      
        
        return employeeList;
    }
    
    //Lấy tất cả danh sách các nhân viên dựa trên MaNV:
    public static List<EmployeeReport> findByCMND(String CMND) {
        
        List<EmployeeReport> employeeList = new ArrayList<>();
        
        //Kết nối database:
        Connection con = null;
        PreparedStatement preStmt = null;
        
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Query:
            String query = "SELECT MaNV, HoTen, NgaySinh, GioiTinh, TenPB, TenCV, DiaChi, CMND, DienThoai, NhanVien.Email, HeSoLuong\r\n" + 
            		"FROM dbo.NhanVien, dbo.ChucVu, dbo.PhongBan\r\n" + 
            		"WHERE NhanVien.MaPB = PhongBan.MaPB AND ChucVu.MaCV = NhanVien.MaCV AND CMND LIKE ?";
            preStmt = con.prepareStatement(query);
            
            preStmt.setString(1, "%" + CMND + "%");
            
            ResultSet rs = preStmt.executeQuery();
            
            //Đọc dữ liệu từ rs được trả về:
            while(rs.next()){
                EmployeeReport employee = new EmployeeReport(
                        rs.getString("MaNV"),
                        rs.getString("HoTen"),
                        rs.getString("NgaySinh"),
                        rs.getString("GioiTinh"),
                        rs.getString("TenPB"),
                        rs.getString("TenCV"),
                        rs.getString("DiaChi"),
                        rs.getString("CMND"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("HeSoLuong"));
                employeeList.add(employee);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(preStmt != null)
            {
                try {
                    preStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
        return employeeList;
    }
    
    //Lấy tất cả danh sách các nhân viên dựa trên MaNV:
    public static List<EmployeeReport> findByTenPB(String TenPB) {
        
        List<EmployeeReport> employeeList = new ArrayList<>();
        
        //Kết nối database:
        Connection con = null;
        PreparedStatement preStmt = null;
        
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Query:
            String query = "SELECT MaNV, HoTen, NgaySinh, GioiTinh, TenPB, TenCV, DiaChi, CMND, DienThoai, NhanVien.Email, HeSoLuong\r\n" + 
            		"FROM dbo.NhanVien, dbo.ChucVu, dbo.PhongBan\r\n" + 
            		"WHERE NhanVien.MaPB = PhongBan.MaPB AND ChucVu.MaCV = NhanVien.MaCV AND TenPB LIKE ?";
            preStmt = con.prepareStatement(query);
            
            preStmt.setString(1, "%" + TenPB + "%");
            
            ResultSet rs = preStmt.executeQuery();
            
            //Đọc dữ liệu từ rs được trả về:
            while(rs.next()){
                EmployeeReport employee = new EmployeeReport(
                        rs.getString("MaNV"),
                        rs.getString("HoTen"),
                        rs.getString("NgaySinh"),
                        rs.getString("GioiTinh"),
                        rs.getString("TenPB"),
                        rs.getString("TenCV"),
                        rs.getString("DiaChi"),
                        rs.getString("CMND"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("HeSoLuong"));
                employeeList.add(employee);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(preStmt != null)
            {
                try {
                    preStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
        return employeeList;
    }
    
    public static void insert(Employee employeereport) throws ParseException{         
        //Tìm all các department có trong database để xem đi check primary key:
        List<Employee> temp = findAllReport();
        
        boolean check;
        String newPrimaryKey = null;
        do{
            check = false;
            newPrimaryKey = generatePrimaryKey("NV");
            for(int i =0; i < temp.size(); i++)
            {
                if(newPrimaryKey.equals(temp.get(i).getMaNV())){
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
            //Kiểm tra MaNV có duy nhất không rồi mới thêm vào NhanVien
            //hoặc có một cách khác là không để cho người dùng nhập MaNV => MaNV tự generate ra.
           
            String query = "select MaPB from PhongBan where TenPB = N'" + employeereport.getMaPB() + "'";
            ResultSet rs = con.createStatement().executeQuery(query);
            rs.next();
            String maPB = rs.getString(1);
            
            query = "select MaCV from ChucVu where TenCV = N'" + employeereport.getMaCV() + "'";
            rs = con.createStatement().executeQuery(query);
            rs.next();
            String maCV = rs.getString(1);
            //Query:
            query = "INSERT INTO NhanVien (MaNV, HoTen, NgaySinh, GioiTinh, MaPB, MaCV, DiaChi, CMND, DienThoai, Email, HeSoLuong, Hinh) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            preStmt = con.prepareStatement(query);
            
            preStmt.setString(1, newPrimaryKey);
            preStmt.setString(2, employeereport.getHoTen());
            preStmt.setDate(3, new java.sql.Date(new SimpleDateFormat("yyyy-mm-dd").parse(employeereport.getNgaySinh()).getTime()));
            preStmt.setString(4, employeereport.getGioiTinh());            
            preStmt.setString(5, maPB);
            preStmt.setString(6, maCV);
            preStmt.setString(7, employeereport.getDiaChi());
            preStmt.setInt(8, Integer.parseInt(employeereport.getCMND()));
            preStmt.setInt(9, Integer.parseInt(employeereport.getDienThoai()));            
            preStmt.setString(10, employeereport.getEmail());
            preStmt.setFloat(11, Float.parseFloat(employeereport.getHeSoLuong()));
            if(employeereport.getFile() != null) {
            	  FileInputStream fis = new FileInputStream(employeereport.getFile());
            	  preStmt.setBinaryStream(12, fis, employeereport.getFile().length());
            }
            else {
            	preStmt.setBinaryStream(12, null, 0);
            }
            
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
            
            if(preStmt != null)
            {
                try {
                    preStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    public static void update(Employee employeereport) throws FileNotFoundException, ParseException{
        
         //Kết nối database:
        Connection con = null;
        PreparedStatement preStmt = null;
        
        try {
            con = DBConnection.getInstance().getConnection();
            String query = "select MaPB from PhongBan where TenPB = N'" + employeereport.getMaPB() + "'";
            ResultSet rs = con.createStatement().executeQuery(query);
            rs.next();
            String maPB = rs.getString(1);
            
            query = "select MaCV from ChucVu where TenCV = N'" + employeereport.getMaCV() + "'";
            rs = con.createStatement().executeQuery(query);
            rs.next();
            String maCV = rs.getString(1);
            //Query:
            query = "UPDATE NhanVien SET MaNV=?,HoTen=?,NgaySinh=?,GioiTinh=?,MaPB=?,MaCV=?,DiaChi=?,CMND=?,DienThoai=?,Email=?,HeSoLuong=?,Hinh=? WHERE MaNV=?";
            preStmt = con.prepareStatement(query);
            
            preStmt.setString(1, employeereport.getMaNV());
            preStmt.setString(2, employeereport.getHoTen());
            preStmt.setDate(3, new java.sql.Date(new SimpleDateFormat("yyyy-mm-dd").parse(employeereport.getNgaySinh()).getTime()));
            preStmt.setString(4, employeereport.getGioiTinh());            
            preStmt.setString(5, maPB);
            preStmt.setString(6, maCV);
            preStmt.setString(7, employeereport.getDiaChi());
            preStmt.setInt(8, Integer.parseInt(employeereport.getCMND()));
            preStmt.setInt(9, Integer.parseInt(employeereport.getDienThoai()));            
            preStmt.setString(10, employeereport.getEmail());
            preStmt.setFloat(11, Float.parseFloat(employeereport.getHeSoLuong()));
            if(employeereport.getFile() != null) {
            	FileInputStream fis = new FileInputStream(employeereport.getFile());
          	  	preStmt.setBinaryStream(12, fis, employeereport.getFile().length());
            }
            else {
            	preStmt.setBinaryStream(12, null, 0);
            }
            preStmt.setString(13, employeereport.getMaNV());
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(preStmt != null)
            {
                try {
                    preStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static void delete(String MaNV){
        
         //Kết nối database:
        Connection con = null;
        PreparedStatement preStmt = null;
        
        try {
            con = DBConnection.getInstance().getConnection();
            
            //Query:
            String query = "DELETE FROM NhanVien WHERE MaNV=?";
            preStmt = con.prepareStatement(query);
            
            preStmt.setString(1, MaNV);

            preStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(preStmt != null)
            {
                try {
                    preStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
            if(con != null)
            {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeModify.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
}
