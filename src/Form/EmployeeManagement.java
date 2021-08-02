/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Database.DBConnection;
import Database.Employee;
import Database.EmployeeReport;
import Database.EmployeeModify;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.Image;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 *
 * @author ASUS
 */
public class EmployeeManagement extends javax.swing.JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String manv = "";
	List<EmployeeReport> employeeList = new ArrayList<>();
    List<String> departmentList= new ArrayList<>();
    List<String> roleNameList= new ArrayList<>();
    File file = null;
    
    //Lưu index row đang được chọn:
    int selectedIndex = -1;
    
    //Dùng để thao tác trên bảng:
    DefaultTableModel tableModel;
    
    //Lưu chức năng hiện đang sử dụng để sử dụng nút lưu và hủy:
    String funcName;

    /**
     * Creates new form EmployeeManagement
     */
    public EmployeeManagement() {
        initComponents();
        
        //Đổ dữ liệu vào table khi bật chức năng này lên:
        tableModel = (DefaultTableModel) tableEmployeeMana.getModel();
        showEmployee();
        
        //Giúp cho textArea tự xuống dòng:
        txtAddress.setLineWrap(true);
        txtAddress.setWrapStyleWord(true);
        
        //Khóa chức năng nhập trực tiếp ngày của datechooser:
        JTextFieldDateEditor dateEditor = (JTextFieldDateEditor) dateChBirthday.getDateEditor();
        dateEditor.setEditable(false);
        
        txtSearchName.setEditable(false);
        
        //Đổ giới tính vào cbGender -> đã đổ mặc định bên design
        
        //Đổ dữ liệu MaPBList vào cbBoxMaPB:
        showComboBoxTenPB();
        
        //Đổ dữ liệu MaCVList vào cbBoxMaCV:
        showComboBoxTenCV();
        
        this.resetForm();
        
        tableEmployeeMana.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e){    
            }
            
            @Override
            public void mousePressed(MouseEvent e){
                
                selectedIndex = tableEmployeeMana.getSelectedRow();
                
                if(selectedIndex >= 0 && !funcName.equals("add"))
                {
                    EmployeeReport employee = employeeList.get(selectedIndex);
                    
                    txtEmployeeCode.setText(employee.getMaNV());
                    txtEmployeeName.setText(employee.getHoTen());
                    //Lấy ngày:
                    try {
                        //Chuỗi ban đầu lưu: yyyy-MM-dd:
                        String bdStr = employee.getNgaySinh();

                        Date bdDate = new SimpleDateFormat("yyyy-MM-dd").parse(bdStr);
                        
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        String tmp = df.format(bdDate);
                        
                        Date result = new SimpleDateFormat("dd/MM/yyyy").parse(tmp);

                        dateChBirthday.setDate(result);
                        
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    setComboBoxGender(employee.getGioiTinh());
                    setComboBoxTenPB(employee.getTenPB());
                    setComboBoxTenCV(employee.getTenCV());
                    txtAddress.setText(employee.getDiaChi());
                    txtCMND.setText(employee.getCMND());
                    txtPhone.setText(employee.getDienThoai());
                    txtEmail.setText(employee.getEmail());
                    txtSalary.setText(employee.getHeSoLuong());
                    if(employee.getBytes() != null) {
                    	byte[] data = employee.getBytes();
                    	try {
                        	OutputStream targetFile = new FileOutputStream("tmp.jpg");
                        	targetFile.write(data);
                        	targetFile.close();
    	                    ImageIcon icon = new ImageIcon(new ImageIcon("tmp.jpg").getImage().getScaledInstance(jpHinh.getWidth(), jpHinh.getHeight(), Image.SCALE_DEFAULT));
                            lblNewLabel.setIcon(icon);
                                } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                            }
                    else {
                    }
                }
            }
                        @Override
            public void mouseEntered(MouseEvent e){  
            }
            
            @Override
            public void mouseReleased(MouseEvent e){
            }
            
            @Override
            public void mouseExited(MouseEvent e){  
            }
        });
    }
    
    private void showComboBoxTenPB(){
        this.departmentList = EmployeeModify.getAllDepartmentName();
        
        for(String s: departmentList){
            //Vì do database nó lưu thừa kí tự khoảng trắng nên phải xóa (dùng trim()):
            cbDepartment.addItem(s.trim());
        }
        
        //Set giá trị mặc định ban đầu là index 0:
        cbDepartment.setSelectedIndex(0);
    }
            
    private void showComboBoxTenCV(){
        this.roleNameList = EmployeeModify.getAllRoleName();
        
        for(String s: roleNameList){
            //Vì do database nó lưu thừa kí tự khoảng trắng nên phải xóa (dùng trim()):
            cbRole.addItem(s.trim());
        }
        
        //Set giá trị mặc định ban đầu là index 0:
        cbRole.setSelectedIndex(0);
    }
        private void setComboBoxGender(String value){
        //Do trong database nó lưu Nam với Nữ bị dư vài dấu khoảng trắng...
        if(value.trim().equals("Nam"))
        {
            cbBoxGender.setSelectedIndex(0); // Nam
        }
        else{
            cbBoxGender.setSelectedIndex(1); // Nữ
        }
    }
    private void setComboBoxTenPB(String value){
        for(int i = 0; i < departmentList.size(); i++)
        {
            if(departmentList.get(i).equals(value)){
                cbDepartment.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void setComboBoxTenCV(String value){
        for(int i = 0; i < roleNameList.size(); i++)
        {
            if(roleNameList.get(i).equals(value)){
                cbRole.setSelectedIndex(i);
                break;
            }
        }
    }    
    
    //Hiển thị dữ liệu lên table:
    private void showEmployee(){
        
        this.employeeList = EmployeeModify.findAll();
        
        //Đưa các bộ đếm hàng về 0:
        tableModel.setRowCount(0);
        for(EmployeeReport employee: employeeList){
            tableModel.addRow(new Object[] {
                tableModel.getRowCount()+1,
                employee.getMaNV(),
                employee.getHoTen(),
                employee.getNgaySinh(),
                employee.getGioiTinh(),
                employee.getTenPB(),
                employee.getTenCV(),              
                employee.getDiaChi(),
                employee.getCMND(),
                employee.getDienThoai(),
                employee.getEmail(),
                employee.getHeSoLuong()
            });
        }
    }
    
    private void resetForm(){
        
        dateChBirthday.setEnabled(false);

        selectedIndex = -1;
        
        funcName = "";
        
        btnSave.setEnabled(false);
        btnCancel.setEnabled(false);    
        
        btnDelete.setEnabled(true);
        btnEdit.setEnabled(true);
        btnAdd.setEnabled(true);
    
        txtEmployeeCode.setText("");
        txtEmployeeName.setText("");
        txtAddress.setText("");
        txtCMND.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtSalary.setText("");
        
        dateChBirthday.setDate(null);
        
        cbBoxGender.setSelectedIndex(0); // Nam
        cbBoxGender.setEnabled(false);
        
        cbDepartment.setSelectedIndex(0);
        cbDepartment.setEnabled(false);
        
        cbRole.setSelectedIndex(0);
        cbRole.setEnabled(false);
        
        txtEmployeeCode.setEditable(false);
        txtEmployeeName.setEditable(false);
        txtAddress.setEditable(false);
        lblNewLabel.setIcon(null);
        
        txtSearchName.setEditable(false);
        
        //Mở các chức năng tìm kiếm
        btnSearch.setEnabled(true);  
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtEmployeeCode = new javax.swing.JTextField();
        txtEmployeeName = new javax.swing.JTextField();
        dateChBirthday = new com.toedter.calendar.JDateChooser();
        cbBoxGender = new javax.swing.JComboBox<>();
        txtEmail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtSalary = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCMND = new javax.swing.JTextField();
        cbDepartment = new javax.swing.JComboBox<>();
        cbRole = new javax.swing.JComboBox<>();
        jpHinh = new javax.swing.JPanel();
        lblNewLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEmployeeMana = new javax.swing.JTable();
        txtSearchName = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnPrintEmployee = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 204));
        jLabel1.setText("QUẢN LÝ NHÂN SỰ");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 18))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel2.setText("Mã nhân viên:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel3.setText("Họ tên NV:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel4.setText("Ngày sinh:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel5.setText("Phòng ban:");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel6.setText("Giới tính:");

        txtEmployeeCode.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        txtEmployeeCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmployeeCodeActionPerformed(evt);
            }
        });

        txtEmployeeName.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        cbBoxGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        txtEmail.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel7.setText("Chức vụ:");

        txtSalary.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel8.setText("Địa chỉ:");

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        jScrollPane2.setViewportView(txtAddress);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel9.setText("CMND:");

        txtPhone.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel10.setText("Email:");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel11.setText("Điện thoại:");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel12.setText("Hệ số lương:");

        txtCMND.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jpHinh.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jpHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpHinhMouseClicked(evt);
            }
        });

        lblNewLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNewLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpHinhLayout = new javax.swing.GroupLayout(jpHinh);
        jpHinh.setLayout(jpHinhLayout);
        jpHinhLayout.setHorizontalGroup(
            jpHinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblNewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        );
        jpHinhLayout.setVerticalGroup(
            jpHinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblNewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGap(19)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jLabel2)
        				.addComponent(jLabel4)
        				.addComponent(jLabel6)
        				.addComponent(jLabel3))
        			.addGap(27)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false)
        					.addComponent(txtEmployeeCode, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
        					.addComponent(txtEmployeeName)
        					.addComponent(dateChBirthday, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
        				.addComponent(cbBoxGender, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addComponent(jLabel5)
        					.addGap(43))
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jLabel8)
        						.addComponent(jLabel7))
        					.addPreferredGap(ComponentPlacement.RELATED)))
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING, false)
        				.addComponent(cbRole, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(cbDepartment, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
        			.addGap(68)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING, false)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jLabel10)
        						.addComponent(jLabel12))
        					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false)
        						.addGroup(jPanel1Layout.createSequentialGroup()
        							.addComponent(txtSalary, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
        							.addGap(37))
        						.addComponent(txtEmail)))
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addComponent(jLabel11)
        					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addComponent(jLabel9)
        					.addGap(67)
        					.addComponent(txtCMND, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)))
        			.addGap(33)
        			.addComponent(jpHinh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(33))
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGap(12)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jLabel5)
        						.addComponent(cbDepartment, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
        					.addGap(18)
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(cbRole, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel7))
        					.addGap(18)
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jLabel8, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)))
        				.addComponent(jpHinh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(jLabel2)
        						.addComponent(txtEmployeeCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel9)
        						.addComponent(txtCMND, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        					.addGap(18)
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(jLabel3)
        						.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        							.addComponent(jLabel11, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
        							.addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        						.addComponent(txtEmployeeName, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(jPanel1Layout.createSequentialGroup()
        							.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false)
        								.addGroup(jPanel1Layout.createSequentialGroup()
        									.addGap(17)
        									.addComponent(dateChBirthday, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
        								.addGroup(jPanel1Layout.createSequentialGroup()
        									.addGap(16)
        									.addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        							.addGap(18)
        							.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        								.addComponent(cbBoxGender, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
        								.addComponent(jLabel6)))
        						.addGroup(jPanel1Layout.createSequentialGroup()
        							.addGap(18)
        							.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        								.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(jLabel10, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
        							.addGap(18)
        							.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        								.addComponent(txtSalary, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(jLabel12))))))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1.setLayout(jPanel1Layout);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 18))); // NOI18N

        tableEmployeeMana.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã NV", "Họ Tên", "Ngày Sinh", "Giới Tính", "Phòng Ban", "Chức Vụ", "Địa Chỉ", "CMND", "Điện Thoại", "Email", "Hệ Số Lương"
            }
        ) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class
            };

            public Class<?> getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tableEmployeeMana.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableEmployeeManaMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tableEmployeeMana);
        if (tableEmployeeMana.getColumnModel().getColumnCount() > 0) {
            tableEmployeeMana.getColumnModel().getColumn(0).setMinWidth(40);
            tableEmployeeMana.getColumnModel().getColumn(0).setPreferredWidth(40);
            tableEmployeeMana.getColumnModel().getColumn(0).setMaxWidth(40);
            tableEmployeeMana.getColumnModel().getColumn(1).setMinWidth(60);
            tableEmployeeMana.getColumnModel().getColumn(1).setPreferredWidth(60);
            tableEmployeeMana.getColumnModel().getColumn(1).setMaxWidth(60);
            tableEmployeeMana.getColumnModel().getColumn(2).setMinWidth(130);
            tableEmployeeMana.getColumnModel().getColumn(2).setPreferredWidth(130);
            tableEmployeeMana.getColumnModel().getColumn(2).setMaxWidth(130);
            tableEmployeeMana.getColumnModel().getColumn(3).setMinWidth(85);
            tableEmployeeMana.getColumnModel().getColumn(3).setPreferredWidth(85);
            tableEmployeeMana.getColumnModel().getColumn(3).setMaxWidth(85);
            tableEmployeeMana.getColumnModel().getColumn(4).setMinWidth(65);
            tableEmployeeMana.getColumnModel().getColumn(4).setPreferredWidth(65);
            tableEmployeeMana.getColumnModel().getColumn(4).setMaxWidth(65);
            tableEmployeeMana.getColumnModel().getColumn(7).setMinWidth(120);
            tableEmployeeMana.getColumnModel().getColumn(7).setPreferredWidth(120);
            tableEmployeeMana.getColumnModel().getColumn(7).setMaxWidth(120);
            tableEmployeeMana.getColumnModel().getColumn(8).setMinWidth(70);
            tableEmployeeMana.getColumnModel().getColumn(8).setPreferredWidth(70);
            tableEmployeeMana.getColumnModel().getColumn(8).setMaxWidth(70);
            tableEmployeeMana.getColumnModel().getColumn(9).setMinWidth(70);
            tableEmployeeMana.getColumnModel().getColumn(9).setPreferredWidth(70);
            tableEmployeeMana.getColumnModel().getColumn(9).setMaxWidth(70);
            tableEmployeeMana.getColumnModel().getColumn(11).setMinWidth(77);
            tableEmployeeMana.getColumnModel().getColumn(11).setPreferredWidth(77);
            tableEmployeeMana.getColumnModel().getColumn(11).setMaxWidth(77);
        }

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/timkiem.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jButton1.setText("Lý lịch");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnSearch)
                        .addComponent(txtSearchName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnAdd.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add.png"))); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/edit-icon.png"))); // NOI18N
        btnEdit.setText("Sửa");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/xoa.png"))); // NOI18N
        btnCancel.setText("Hủy");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Delete-icon.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.setMaximumSize(new java.awt.Dimension(87, 29));
        btnDelete.setMinimumSize(new java.awt.Dimension(87, 29));
        btnDelete.setPreferredSize(new java.awt.Dimension(87, 29));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/luu.png"))); // NOI18N
        btnSave.setText("Lưu");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnRefresh.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        btnRefresh.setText("Làm mới");
        btnRefresh.setPreferredSize(new java.awt.Dimension(100, 29));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnPrintEmployee.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnPrintEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/print-icon.png"))); // NOI18N
        btnPrintEmployee.setText("In");
        btnPrintEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintEmployeeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdd)
                .addGap(48, 48, 48)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(btnSave)
                .addGap(51, 51, 51)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(btnPrintEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrintEmployee)))
        );

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/exit.png"))); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(390, 390, 390)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        DashBoard db = new DashBoard();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "Bạn có chắc chắn muốn thoát?","Warning",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            db.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void txtEmployeeCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmployeeCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmployeeCodeActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        resetForm();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        switch(this.funcName){
            //Thêm dữ liệu:
            case "add":
            //Kiểm tra xem các trường dữ liệu có rỗng hay không?:
            String HoTen = txtEmployeeName.getText();
            Date bdDate = dateChBirthday.getDate();

            String GioiTinh = cbBoxGender.getSelectedItem().toString();
            //QQQQQ
            String TenPB = cbDepartment.getSelectedItem().toString();
            String TenCV = cbRole.getSelectedItem().toString();
            String DiaChi = txtAddress.getText();
            String CMND = txtCMND.getText();
            String DienThoai = txtPhone.getText();
            String Email = txtEmail.getText();
            String HeSoLuong = txtSalary.getText();

            //Xóa các khoảng trắng ở phía đầu và phía đuôi của chuỗi:
            HoTen = HoTen.trim();
            GioiTinh = GioiTinh.trim();
            TenPB = TenPB.trim();
            TenCV = TenCV.trim();
            DiaChi = DiaChi.trim();
            CMND = CMND.trim();
            DienThoai = DienThoai.trim();
            Email = Email.trim();
            HeSoLuong = HeSoLuong.trim();

            if(!HoTen.equals("") && !(bdDate==null) && !GioiTinh.equals("") && !TenPB.equals("") && !TenCV.equals("") && !DiaChi.equals("")
                && !CMND.equals("") && !DienThoai.equals("") && !Email.equals("") && !HeSoLuong.equals(""))
            {
                try {

                    //Chuỗi ban đầu lưu: dd/MM/yyyy:
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String NgaySinh = df.format(bdDate);

                    System.out.println("Ngay: " + NgaySinh);
                    Employee employee = new Employee(HoTen, NgaySinh, GioiTinh, TenPB, TenCV , DiaChi, CMND, DienThoai, Email, HeSoLuong);
                    employee.setFile(file);
                    EmployeeModify.insert(employee);

                    //Show lại dữ liệu mới:
                    showEmployee();
                    JOptionPane.showMessageDialog(null, "Bạn đã lưu thành công nhân viên này!");
                    //Reset form về ban đầu:
                    resetForm();

                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Hãy điền đầy đủ thông tin nhân viên!");
            }
            break;

            case "delete":
            EmployeeReport employee = this.employeeList.get(selectedIndex);

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Bạn có muốn xoá nhân viên này?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION){
                //Delete employee:
                EmployeeModify.delete(employee.getMaNV());
                //Show lại dữ liệu mới:
                showEmployee();

                //Reset form về ban đầu:
                resetForm();
            }
            break;

            case "edit":

            //Kiểm tra xem các trường dữ liệu có rỗng hay không?:
            String oldMaNV = txtEmployeeCode.getText();
            String newHoTen = txtEmployeeName.getText();
            Date nwbdDate = dateChBirthday.getDate();
            String newGioiTinh = cbBoxGender.getSelectedItem().toString();
            String newTenPB = cbDepartment.getSelectedItem().toString();
            String newTenCV = cbRole.getSelectedItem().toString();
            String newDiaChi = txtAddress.getText();
            String newCMND = txtCMND.getText();
            String newDienThoai = txtPhone.getText();
            String newEmail = txtEmail.getText();
            String newHeSoLuong = txtSalary.getText();

            //Xóa khoảng trắng ở đầu và cuối chuỗi:
            newHoTen = newHoTen.trim();
            newGioiTinh = newGioiTinh.trim();
            newDiaChi = newDiaChi.trim();

            if(!newHoTen.equals("") && !(nwbdDate == null) && !newGioiTinh.equals("") &&
                !newTenPB.equals("")&& !newTenCV.equals("") && !newDiaChi.equals("")
                &&!newCMND.equals("") && !newDienThoai.equals("") && !newEmail.equals("") && !newHeSoLuong.equals("") )
            {

                String newNgaySinh = null;
                try {

                    //Chuỗi ban đầu lưu: dd/MM/yyyy:
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    newNgaySinh = df.format(nwbdDate);

                    System.out.println("Ngay: " + newNgaySinh);

                } catch (Exception ex) {
                    System.out.println(ex);
                }
                int editDialogButton = JOptionPane.YES_NO_OPTION;
                int editDialogResult = JOptionPane.showConfirmDialog (null, "Bạn có muốn lưu lại kết quả chỉnh sửa?","Warning",editDialogButton);
                if(editDialogResult == JOptionPane.YES_OPTION){
                    Employee editedEmployee = new Employee(oldMaNV,newHoTen,newNgaySinh, newGioiTinh,newTenPB,newTenCV,newDiaChi,newCMND,newDienThoai,newEmail,newHeSoLuong);
                    editedEmployee.setFile(file);
                    try {
                        EmployeeModify.update(editedEmployee);
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //Show lại dữ liệu mới:
                    showEmployee();
                    //Reset form về ban đầu:
                    resetForm();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Hãy điền đầy đủ thông tin nhân viên!");
            }
            break;
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if(selectedIndex < 0)
        {
            JOptionPane.showMessageDialog(null, "Hãy chọn một nhân viên để xóa!");
            resetForm();
        }
        else{
            //Thiết lập các chức năng để chuẩn bị xóa:
            this.funcName = "delete";
            btnAdd.setEnabled(false);
            btnEdit.setEnabled(false);

            btnSave.setEnabled(true);
            btnCancel.setEnabled(true);

        }
        
        //Reset các chức năng tìm kiếm
        txtSearchName.setText("");
        //Không cho thao tác các chức năng tìm kiếm
        txtSearchName.setEditable(false);
        btnSearch.setEnabled(false);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        if(selectedIndex >= 0)
        {
            EmployeeReport employeereport = employeeList.get(selectedIndex);
            txtEmployeeCode.setText(employeereport.getMaNV());
            txtEmployeeName.setText(employeereport.getHoTen());
            
            try {
                //Chuỗi ban đầu lưu: yyyy-MM-dd:
                String bdStr = employeereport.getNgaySinh();

                Date bdDate = new SimpleDateFormat("yyyy-MM-dd").parse(bdStr);

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String tmp = df.format(bdDate);

                Date result = new SimpleDateFormat("dd/MM/yyyy").parse(tmp);

                dateChBirthday.setDate(result);

            } catch (Exception ex) {
                System.out.println(ex);
            }
            setComboBoxGender(employeereport.getGioiTinh());
            setComboBoxTenPB(employeereport.getTenPB());
            setComboBoxTenCV(employeereport.getTenCV());
            txtAddress.setText(employeereport.getDiaChi());
            txtCMND.setText(employeereport.getCMND());
            txtPhone.setText(employeereport.getDienThoai());
            txtEmail.setText(employeereport.getEmail());
            txtSalary.setText(employeereport.getHeSoLuong());
        }
        else
        {
            txtEmployeeCode.setText("");
            txtEmployeeName.setText("");            
            dateChBirthday.setDate(null);
            cbBoxGender.setSelectedIndex(0); // Nam
            cbDepartment.setSelectedIndex(0);
            cbRole.setSelectedIndex(0);
            txtAddress.setText("");
            txtCMND.setText("");  
            txtPhone.setText("");  
            txtEmail.setText("");  
            txtSalary.setText("");  
        }

        funcName = "";
        btnSave.setEnabled(false);
        btnCancel.setEnabled(false);

        btnDelete.setEnabled(true);
        btnEdit.setEnabled(true);
        btnAdd.setEnabled(true);

        txtEmployeeCode.setEditable(false);
        txtEmployeeName.setEditable(false);
        cbBoxGender.setEnabled(false);
        txtAddress.setEditable(false);
        cbDepartment.setEnabled(false);
        cbRole.setEnabled(false);

    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        if(selectedIndex < 0)
        {
            JOptionPane.showMessageDialog(null, "Hãy chọn một nhân viên để chỉnh sửa!");
            resetForm();
        }else{
            //Thiết lập các chức năng chuẩn bị chỉnh sửa nhân viên:
            this.funcName = "edit";
            //Không cho người dùng thay đổi mã nhân viên:
            txtEmployeeCode.setEditable(false);
            txtEmployeeName.setEditable(true);
            dateChBirthday.setEnabled(true);
            cbBoxGender.setEnabled(true);
            cbDepartment.setEnabled(true);
            cbRole.setEnabled(true);
            txtAddress.setEditable(true);
            txtCMND.setEditable(true);
            txtPhone.setEditable(true);
            txtEmail.setEditable(true);
            txtSalary.setEditable(true);

            btnAdd.setEnabled(false);
            btnDelete.setEnabled(false);

            btnSave.setEnabled(true);
            btnCancel.setEnabled(true);

            jpHinh.setEnabled(true);

        }
        
        //Reset các chức năng tìm kiếm
        txtSearchName.setText("");
        //Không cho thao tác các chức năng tìm kiếm
        txtSearchName.setEditable(false);
        btnSearch.setEnabled(false);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        //Báo hiệu là đang thao tác chức năng add:
        this.funcName = "add";

        //Kiểm tra xem liệu họ có đang thêm dữ liệu không?
        if(txtEmployeeName.isEditable() && txtAddress.isEditable() && txtCMND.isEditable()
            && txtPhone.isEditable() && txtEmail.isEditable() && txtSalary.isEditable())
        {
            //Kiểm tra họ có muốn hủy bỏ các trường đã nhập để thêm một employee mới không?
            //Kiểm tra người dùng đã nhập đc thông tin gì rồi?
            //Nếu chưa nhập thông tin gì thì bấm thoải mái.
            if(!txtEmployeeName.getText().equals("") || !(dateChBirthday.getDate()==null || !(cbBoxGender.getSelectedIndex()==0))
                || !(cbDepartment.getSelectedIndex()== 0) || !(cbRole.getSelectedIndex() == 0)|| !txtAddress.getText().equals("")
                || !txtCMND.getText().equals("") || !txtPhone.getText().equals("") || !txtEmail.getText().equals("") || !txtSalary.getText().equals(""))
            {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Bạn có muốn thêm mới và hủy các thông tin hiện có?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION){
                    // Reset thêm mới:
                    txtEmployeeName.setText("");
                    dateChBirthday.setDate(null);
                    cbBoxGender.setSelectedIndex(0); // Nam
                    cbDepartment.setSelectedIndex(0);
                    cbRole.setSelectedIndex(0);
                    txtAddress.setText("");
                    txtCMND.setText("");
                    txtPhone.setText("");
                    txtEmail.setText("");
                    txtSalary.setText("");
                }
            }
        }else{
            //Reset phòng trường hợp nếu như người dùng đã click vào một nhân viên bất kỳ:
            txtEmployeeCode.setText("");
            txtEmployeeName.setText("");
            dateChBirthday.setDate(null);
            cbBoxGender.setSelectedIndex(0);
            cbDepartment.setSelectedIndex(0);
            cbRole.setSelectedIndex(0);
            txtAddress.setText("");
            txtCMND.setText("");
            txtPhone.setText("");
            txtEmail.setText("");
            txtSalary.setText("");

            //Không cho người dùng nhập primary Key để hệ thống tự generate:
            txtEmployeeCode.setEditable(false);
            txtEmployeeName.setEditable(true);
            dateChBirthday.setEnabled(true);
            cbBoxGender.setEnabled(true);
            cbDepartment.setEnabled(true);
            cbRole.setEnabled(true);
            txtAddress.setEditable(true);
            txtCMND.setEditable(true);
            txtPhone.setEditable(true);
            txtEmail.setEditable(true);
            txtSalary.setEditable(true);
            jpHinh.setEnabled(true);

            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);

            btnSave.setEnabled(true);
            btnCancel.setEnabled(true);
        }
        
        //Reset các chức năng tìm kiếm
        txtSearchName.setText("");
        //Không cho thao tác các chức năng tìm kiếm
        txtSearchName.setEditable(false);
        btnSearch.setEnabled(false);
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        txtSearchName.setEditable(true);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnPrintEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintEmployeeActionPerformed
        // TODO add your handling code here:
        //Khai báo đường dẫn đến file StaffManagement:
        String link = "src\\Reports\\PrintEmployee.jrxml";
        Connection con = null;
        try{
            con = DBConnection.getInstance().getConnection();

            //Tạo JasperReport:
            JasperReport jr = JasperCompileManager.compileReport(link);
            //Tạo JasperPrint:
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            //Tạo JasperReviewer:
            JasperViewer.viewReport(jp,false);
        }catch(Exception e){
            e.printStackTrace();
        }finally{

            if(con != null)
            {
                try {
                    con.close();
                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btnPrintEmployeeActionPerformed

    private void lblNewLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNewLabelMouseClicked
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
            int result = fileChooser.showOpenDialog(getParent());
            if (result == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(new ImageIcon(file.getAbsolutePath())
                .getImage().getScaledInstance(jpHinh.getWidth(), jpHinh.getHeight(), Image.SCALE_DEFAULT));
                lblNewLabel.setIcon(icon);
        }
    }//GEN-LAST:event_lblNewLabelMouseClicked

    private void jpHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpHinhMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jpHinhMouseClicked

    private void tableEmployeeManaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEmployeeManaMousePressed
        // TODO add your handling code here:
        
                
                selectedIndex = tableEmployeeMana.getSelectedRow();
                manv = employeeList.get(selectedIndex).getMaNV();
                if(selectedIndex >= 0 && !funcName.equals("add"))
                {
                    EmployeeReport employee = employeeList.get(selectedIndex);
                    
                    txtEmployeeCode.setText(employee.getMaNV());
                    txtEmployeeName.setText(employee.getHoTen());
                    //Lấy ngày:
                    try {
                        //Chuỗi ban đầu: yyyy-MM-dd:
                        String bdStr = employee.getNgaySinh();

                        Date bdDate = new SimpleDateFormat("yyyy-MM-dd").parse(bdStr);
                        
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        String tmp = df.format(bdDate);
                        
                        Date result = new SimpleDateFormat("dd/MM/yyyy").parse(tmp);

                        dateChBirthday.setDate(result);
                        
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    setComboBoxGender(employee.getGioiTinh());
                    setComboBoxTenPB(employee.getTenPB());
                    setComboBoxTenCV(employee.getTenCV());
                    txtAddress.setText(employee.getDiaChi());
                    txtCMND.setText(employee.getCMND());
                    txtPhone.setText(employee.getDienThoai());
                    txtEmail.setText(employee.getEmail());
                    txtSalary.setText(employee.getHeSoLuong());
                    if(employee.getBytes() != null) {
                    	byte[] data = employee.getBytes();
                    	try {
                            OutputStream targetFile = new FileOutputStream("tmp.jpg");
                            targetFile.write(data);
                            targetFile.close();
    	                    ImageIcon icon = new ImageIcon(new ImageIcon("tmp.jpg").getImage().getScaledInstance(jpHinh.getWidth(), jpHinh.getHeight(), Image.SCALE_DEFAULT));
                                lblNewLabel.setIcon(icon);
                                } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                    e1.printStackTrace();
                                }
                            }
                    else {
                        lblNewLabel.setIcon(null);
                    }
                }
    }//GEN-LAST:event_tableEmployeeManaMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    	if(!manv.equals("")) {
    		PopupEmployeeResume popupEmployeeResume = new PopupEmployeeResume();
        	popupEmployeeResume.setVisible(true);
    	}
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmployeeManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeManagement().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnPrintEmployee;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbBoxGender;
    private javax.swing.JComboBox<String> cbDepartment;
    private javax.swing.JComboBox<String> cbRole;
    private com.toedter.calendar.JDateChooser dateChBirthday;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel jpHinh;
    private javax.swing.JLabel lblNewLabel;
    private javax.swing.JTable tableEmployeeMana;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtCMND;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmployeeCode;
    private javax.swing.JTextField txtEmployeeName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSalary;
    private javax.swing.JTextField txtSearchName;
    // End of variables declaration//GEN-END:variables
}