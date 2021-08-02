/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


import Database.DBConnection;
import Database.EmployeeResumeClass;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author ASUS
 */
public class EmployeeResume extends javax.swing.JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<EmployeeResumeClass> employeeList = new ArrayList<>();
    List<String> departmentList= new ArrayList<>();
    List<String> roleNameList= new ArrayList<>();
    List<String> manvList = new ArrayList<String>();
    File file = null;
    
    //Lưu index row đang được chọn:
    int selectedIndex = -1;
    
    //Dùng để thao tác trên bảng:
    DefaultTableModel tableModel;
    
    //Lưu chức năng hiện đang sử dụng để sử dụng nút lưu và hủy:
    String funcName = "";
    

    /**
     * Creates new form EmployeeResume
     */
    public EmployeeResume() {
        initComponents();
        
        tableModel = (DefaultTableModel) tableEmployeeResumeMana.getModel();
        showEmployee();
        
        loadComboboxMaNV();
        
        //Giúp cho textArea tự xuống dòng:
//        txtAddress.setLineWrap(true);
//        txtAddress.setWrapStyleWord(true);
        
        //Khóa chức năng nhập trực tiếp ngày của datechooser:
//        JTextFieldDateEditor dateEditor = (JTextFieldDateEditor) dateChBirthday.getDateEditor();
//        dateEditor.setEditable(false);
        
//        txtSearchName.setEditable(false);
        
        //Đổ giới tính vào cbGender -> đã đổ mặc định bên design
        
        //Đổ dữ liệu MaPBList vào cbBoxMaPB:
//        showComboBoxTenPB();
        
        //Đổ dữ liệu MaCVList vào cbBoxMaCV:
//        showComboBoxTenCV();
        
        this.resetform();
    }

    private void loadComboboxMaNV() {
		// TODO Auto-generated method stub
    	Connection con = null;
        Statement stmt = null;
        try {
        	con = DBConnection.getInstance().getConnection();
            String query = "SELECT MaNV\n" + 
            		"FROM dbo.NhanVien";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
            	this.manvList.add(rs.getString(1));
           }
            for(String string : manvList) {
            	cbEmployeeCode.addItem(string.trim());
            	//Set giá trị mặc định ban đầu là index 0:
            cbEmployeeCode.setSelectedIndex(0);
        }}
        catch (Exception e) {
			// TODO: handle exception
		}
	}
    
    private void resetform() {
    	cbEmployeeCode.setSelectedIndex(0);
		txtEducation.setText("");
		txtLanguage.setText("");
		txtNation.setText("");
		txtReligion.setText("");
		txtSpecialize.setText("");
		cbMarriage.setSelectedIndex(0);
		
		btnAdd.setEnabled(true);
		btnEdit.setEnabled(true);
		btnDelete.setEnabled(true);
    }

	private void showEmployee() {
		// TODO Auto-generated method stub
    	Connection con = null;
        Statement stmt = null;
        this.employeeList.clear();
        try {
        	con = DBConnection.getInstance().getConnection();
            String query = "SELECT dbo.NhanVien.MaNV, HoTen, TDHocVan, TDChuyenMon, TDNgoaiNgu, [DanToc], TonGiao, QHGiaDinh\n" + 
            		"FROM dbo.NhanVien LEFT JOIN dbo.LyLichNV ON LyLichNV.MaNV = NhanVien.MaNV";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
            	EmployeeResumeClass employeeResumeClass = 
            			new EmployeeResumeClass(
            					rs.getString(1),
            					rs.getString(2),
            					rs.getString(3), 
            					rs.getString(4),
            					rs.getString(5), 
            					rs.getString(6), 
            					rs.getString(7),
            					rs.getString(8));
            	this.employeeList.add(employeeResumeClass);
           }
        //Đưa các bộ đếm hàng về 0:
        tableModel.setRowCount(0);
        for(EmployeeResumeClass employee: employeeList){
            tableModel.addRow(new Object[] {
                tableModel.getRowCount()+1,
                employee.getManv(),
                employee.getHocvan(),
                employee.getChuyenmon(),
                employee.getNgoaingu(),
                employee.getDantoc(),
                employee.getTongiao(),
                employee.getQuanhe()
            });
        }}
        catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void selectedIndexChange(int index) {
		if(index >= 0 && index < this.employeeList.size()) {
			EmployeeResumeClass employeeResumeClass = 
					employeeList.get(index);
			cbEmployeeCode.setSelectedIndex(selectedIndex);
			txtEmployeeName.setText(employeeResumeClass.getTennv());
			txtEducation.setText(employeeResumeClass.getHocvan());
			txtLanguage.setText(employeeResumeClass.getNgoaingu());
			txtNation.setText(employeeResumeClass.getDantoc());
			txtReligion.setText(employeeResumeClass.getTongiao());
			txtSpecialize.setText(employeeResumeClass.getChuyenmon());
			setcbRelationship(employeeResumeClass.getQuanhe());
		}
		else {
			resetform();
		}
	}

	/**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbEmployeeCode = new javax.swing.JComboBox<>();
        cbEmployeeCode.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		selectedIndex = cbEmployeeCode.getSelectedIndex();
        		selectedIndexChange(selectedIndex);
        	}
        });
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtEducation = new javax.swing.JTextField();
        cbMarriage = new javax.swing.JComboBox<>();
        txtSpecialize = new javax.swing.JTextField();
        txtLanguage = new javax.swing.JTextField();
        txtNation = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtReligion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtEmployeeName = new javax.swing.JTextField();
        txtEmployeeName.setEditable(false);
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEmployeeResumeMana = new javax.swing.JTable();
        tableEmployeeResumeMana.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		selectedIndex = tableEmployeeResumeMana.getSelectedRow();
        		if(!funcName.equals("add")) {
        			selectedIndexChange(selectedIndex);
        	}
        }
        });
        
        jPanel4 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 18))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel1.setText("Mã NV:");

        cbEmployeeCode.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel2.setText("Học vấn:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel3.setText("Chuyên môn:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel4.setText("Ngoại ngữ:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel5.setText("Tôn giáo:");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel6.setText("Quan hệ:");

        txtEducation.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        cbMarriage.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        cbMarriage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Độc thân", "Đã có gia đình" }));

        txtSpecialize.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        txtLanguage.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        txtNation.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel8.setText("Dân tộc:");

        txtReligion.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel9.setText("Tên NV:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel9))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(cbEmployeeCode, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEmployeeName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtEducation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtSpecialize, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNation, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtReligion, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(cbMarriage, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbEmployeeCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(txtLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmployeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(txtNation, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEducation, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(txtReligion, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSpecialize, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(cbMarriage, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 28)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 204, 204));
        jLabel7.setText("LÝ LỊCH NHÂN VIÊN");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 18))); // NOI18N

        tableEmployeeResumeMana.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã NV", "Học vấn", "Chuyên môn", "Ngoại ngữ", "Dân tộc", "Tôn giáo", "Quan hệ"
            }
        ));
        jScrollPane1.setViewportView(tableEmployeeResumeMana);
        if (tableEmployeeResumeMana.getColumnModel().getColumnCount() > 0) {
            tableEmployeeResumeMana.getColumnModel().getColumn(0).setMinWidth(50);
            tableEmployeeResumeMana.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableEmployeeResumeMana.getColumnModel().getColumn(0).setMaxWidth(50);
            tableEmployeeResumeMana.getColumnModel().getColumn(1).setMinWidth(80);
            tableEmployeeResumeMana.getColumnModel().getColumn(1).setPreferredWidth(80);
            tableEmployeeResumeMana.getColumnModel().getColumn(1).setMaxWidth(80);
            tableEmployeeResumeMana.getColumnModel().getColumn(5).setMinWidth(100);
            tableEmployeeResumeMana.getColumnModel().getColumn(5).setPreferredWidth(100);
            tableEmployeeResumeMana.getColumnModel().getColumn(5).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 18))); // NOI18N

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnAdd)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(366, 366, 366))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
	protected void setcbRelationship(String quanhe) {
		// TODO Auto-generated method stub
		if("Đã có gia đình".trim().equals(quanhe)) {
			cbMarriage.setSelectedIndex(1);
		}
		else {
			cbMarriage.setSelectedIndex(0);
		}
	}

	private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
		//Báo hiệu là đang thao tác chức năng add:
        this.funcName = "add";

        //Kiểm tra xem liệu họ có đang thêm dữ liệu không?
        if(txtEducation.isEditable() 
        		&& txtLanguage.isEditable() && txtNation.isEditable() 
        		&& txtReligion.isEditable() && txtSpecialize.isEditable())
        {
            //Kiểm tra họ có muốn hủy bỏ các trường đã nhập để thêm một employee mới không?
            //Kiểm tra người dùng đã nhập đc thông tin gì rồi?
            //Nếu chưa nhập thông tin gì thì bấm thoải mái.
            if(!txtEmployeeName.getText().equals("") || !(cbEmployeeCode.getSelectedIndex()==0)
                || !(cbMarriage.getSelectedIndex()== 0) || !txtEducation.getText().equals("")
                || !txtLanguage.getText().equals("") || !txtNation.getText().equals("") 
                || !txtReligion.getText().equals("") || !txtSpecialize.getText().equals(""))
            {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Bạn có muốn thêm mới và hủy các thông tin hiện có?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION){
                    // Reset thêm mới:
                    resetform();
                }
            }
        }else{
            //Reset phòng trường hợp nếu như người dùng đã click vào một nhân viên bất kỳ:
            resetform();           

            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);

            btnSave.setEnabled(true);
            btnCancel.setEnabled(true);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:       
    	if(selectedIndex < 0)
        {
            JOptionPane.showMessageDialog(null, "Hãy chọn một nhân viên để chỉnh sửa!");
            resetform();
        }else{
            //Thiết lập các chức năng chuẩn bị chỉnh sửa nhân viên:
            this.funcName = "edit";
            //Không cho người dùng thay đổi mã nhân viên:
            cbEmployeeCode.setEnabled(true);
            cbEmployeeCode.setEnabled(true);
            txtEducation.setEditable(true);
            txtLanguage.setEditable(true);
            txtNation.setEditable(true);
            txtReligion.setEditable(true);
            txtSpecialize.setEditable(true);

            btnAdd.setEnabled(false);
            btnDelete.setEnabled(false);

            btnSave.setEnabled(true);
            btnCancel.setEnabled(true);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:  
    	resetform();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:  
    	if(selectedIndex < 0)
        {
            JOptionPane.showMessageDialog(null, "Hãy chọn một nhân viên để xóa!");
            resetform();
        }
        else{
            //Thiết lập các chức năng để chuẩn bị xóa:
            this.funcName = "delete";
            btnAdd.setEnabled(false);
            btnEdit.setEnabled(false);

            btnSave.setEnabled(true);
            btnCancel.setEnabled(true);

        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    @SuppressWarnings("resource")
	private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:    
    	String manv = cbEmployeeCode.getSelectedItem().toString();
    	String hocvan = txtEducation.getText().toString().trim();
    	String chuyenmon = txtSpecialize.getText().toString().trim();
    	String ngoaingu = txtLanguage.getText().toString().trim();
    	String dantoc = txtNation.getText().toString().trim();
		String tongiao = txtReligion.getText().toString().trim();
		String quanhe = cbMarriage.getSelectedItem().toString().trim();
		int dialogButton, dialogResult;
		switch (funcName) {
		case "delete": 
			dialogButton = JOptionPane.YES_NO_OPTION;
            dialogResult = JOptionPane.showConfirmDialog (null, "Bạn có muốn xoá nhân viên này?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION){
                //Delete employee:
            	Connection con = null;
            	PreparedStatement stmt = null;
                try {
                	con = DBConnection.getInstance().getConnection();
                    String query = "DELETE FROM dbo.LyLichNV WHERE MaNV = ?";
                    stmt = con.prepareStatement(query);
                    stmt.setString(1, manv);
                    stmt.executeUpdate();
                }
                catch (Exception e) {
					// TODO: handle exception
				}
                //Show lại dữ liệu mới:
                showEmployee();

                //Reset form về ban đầu:
                resetform();
            }
			break;
		case "add":
		case "edit":
			dialogButton = JOptionPane.YES_NO_OPTION;
            dialogResult = JOptionPane.showConfirmDialog (null, "Bạn có muốn cập nhật thay đổi này?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION){
                //Delete employee:
            	Connection con = null;
            	PreparedStatement preparedStatement = null;
                try {
                	con = DBConnection.getInstance().getConnection();
                    String query = "SELECT MaNV FROM dbo.LyLichNV WHERE MaNV = ?";
                    preparedStatement = con.prepareStatement(query);
                    preparedStatement.setString(1, manv);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(!resultSet.next()) {
                    	query = "INSERT INTO [dbo].[LyLichNV]\n" + 
                    			"           ([MaNV]\n" + 
                    			"           ,[TDHocVan]\n" + 
                    			"           ,[TDChuyenMon]\n" + 
                    			"           ,[TDNgoaiNgu]\n" + 
                    			"           ,[DanToc]\n" + 
                    			"           ,[TonGiao]\n" + 
                    			"           ,[QHGiaDinh])\n" + 
                    			"VALUES  (?, ?, ?, ?, ?, ?, ?)";
                    	 preparedStatement = con.prepareStatement(query);
                         preparedStatement.setString(1, manv);
                         preparedStatement.setString(2, hocvan);
                         preparedStatement.setString(3, chuyenmon);
                         preparedStatement.setString(4, ngoaingu);
                         preparedStatement.setString(5, dantoc);
                         preparedStatement.setString(6, tongiao);
                         preparedStatement.setString(7, quanhe);
                         preparedStatement.executeUpdate();
                       //Show lại dữ liệu mới:
                         showEmployee();

                         //Reset form về ban đầu:
                         resetform();
                    }
                    else {
                    	query = "UPDATE [dbo].[LyLichNV]\n" + 
                    			"   SET " + 
                    			"      [TDHocVan] = ?\n" + 
                    			"      ,[TDChuyenMon] = ?\n" + 
                    			"      ,[TDNgoaiNgu] = ?\n" + 
                    			"      ,[DanToc] = ?\n" + 
                    			"      ,[TonGiao] = ?\n" + 
                    			"      ,[QHGiaDinh] = ?\n" + 
                    			" WHERE MaNV = ?";
                    	preparedStatement = con.prepareStatement(query);
                        preparedStatement.setString(7, manv);
                        preparedStatement.setString(1, hocvan);
                        preparedStatement.setString(2, chuyenmon);
                        preparedStatement.setString(3, ngoaingu);
                        preparedStatement.setString(4, dantoc);
                        preparedStatement.setString(5, tongiao);
                        preparedStatement.setString(6, quanhe);
                        preparedStatement.executeUpdate();
                      //Show lại dữ liệu mới:
                        showEmployee();

                        //Reset form về ban đầu:
                        resetform();
                    }
                }
                catch (Exception e) {
					// TODO: handle exception
				}
                //Show lại dữ liệu mới:
                showEmployee();

                //Reset form về ban đầu:
                resetform();
            }
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + funcName);
		}
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
    	resetform();
    }//GEN-LAST:event_btnRefreshActionPerformed

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
            java.util.logging.Logger.getLogger(EmployeeResume.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeResume.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeResume.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeResume.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeResume().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cbEmployeeCode;
    private javax.swing.JComboBox<String> cbMarriage;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableEmployeeResumeMana;
    private javax.swing.JTextField txtEducation;
    private javax.swing.JTextField txtEmployeeName;
    private javax.swing.JTextField txtLanguage;
    private javax.swing.JTextField txtNation;
    private javax.swing.JTextField txtReligion;
    private javax.swing.JTextField txtSpecialize;
    // End of variables declaration//GEN-END:variables
}
