/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Database.DBConnection;
import Database.Department;
import Database.DepartmentModify;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ASUS
 */
public class DepartmentManagement extends javax.swing.JFrame implements DocumentListener{
    List<Department> departmentList = new ArrayList<>();
    //Lưu index row đang được chọn:
    int selectedIndex = -1;
    
    //Dùng để thao tác trên bảng:
    DefaultTableModel tableModel;
    
    //Lưu chức năng hiện đang sử dụng để sử dụng nút lưu và hủy:
    String funcName="";
    
    //Khai báo các biến tìm kiếm:
    String fieldName;
    String searchValue;

    /**
     * Creates new form DepartmentManagement
     */
    public DepartmentManagement() {
        initComponents();
        //Đổ dữ liệu vào table khi bật chức năng này lên:
        tableModel = (DefaultTableModel) tableDepartmentMana.getModel();
        showDepartment();
        //Giúp cho textArea tự xuống dòng:
        txtAreaDescription.setLineWrap(true);
        txtAreaDescription.setWrapStyleWord(true);
        //Khóa chức năng nhập trực tiếp ngày của datechooser:
        JTextFieldDateEditor dateEditor = (JTextFieldDateEditor) dateChStartDay.getDateEditor();
        dateEditor.setEditable(false);
        
        txtSearchName.setEditable(false);
        
        searchValue= "";// Chưa nhập nội dung tìm kiếm hay tìm kiếm all.
        
        //Gắn lắng nghe sự kiện cho textfield tìm kiếm:
        txtSearchName.getDocument().addDocumentListener(this);
        
        this.resetForm();
        
        tableDepartmentMana.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e){    
            }
            @Override
            public void mousePressed(MouseEvent e){
                selectedIndex = tableDepartmentMana.getSelectedRow();
                if(selectedIndex >= 0 && !funcName.equals("add"))
                {
                    Department department = departmentList.get(selectedIndex);
                    
                    txtDerpartmentCode.setText(department.getMaPB());
                    txtDepartmentName.setText(department.getTenPB());

                    try {
                        //Chuỗi ban đầu lưu: yyyy-MM-dd:
                        String sdStr = department.getNgayTL();

                        Date sdDate = new SimpleDateFormat("yyyy-MM-dd").parse(sdStr);

                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        String tmp = df.format(sdDate);

                        Date result = new SimpleDateFormat("dd/MM/yyyy").parse(tmp);

                        dateChStartDay.setDate(result);
                        txtPhone.setText(department.getSDT());
                        txtEmail.setText(department.getEmail());
                        txtAreaDescription.setText(department.getChucNang());

                    } catch (Exception ex) {
                        System.out.println(ex);
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
    
    //Start: các interface của DocumentListener:
    public void changedUpdate(DocumentEvent e) {
        changeText(e);
    }
    
    public void removeUpdate(DocumentEvent e) {
        changeText(e);
    }
    public void insertUpdate(DocumentEvent e) {
        changeText(e);
    }
    //End: các interface của DocumentListener:
    public void changeText(DocumentEvent e) {        
        if(e.getDocument() == txtSearchName.getDocument())
        {
            //Gán giá trị tìm kiếm để cần khi tìm kiếm chính xác:
            this.searchValue = txtSearchName.getText().trim();
            System.out.println("Dang o departmentcode searching...");
            String departmentName = txtSearchName.getText().trim();
            System.out.println(departmentName);
            //Khi thay đổi txtDepartmentCode thì gọi database:
            this.showSearchResult("TenPB",departmentName);            
        }
    }
    
    //Hiển thị dữ liệu lên table: theo nội dung tìm kiếm (mặc định: fieldName = null, searchValue = "")
    private void showSearchResult(String fieldName, String searchValue){
        
        if(fieldName == null)
        {
            //Show all department:
            this.showDepartment();
        }
        else if(fieldName.equals("TenPB")){
            System.out.println("Searching TenPB...");
            
            this.departmentList = DepartmentModify.findByTenPB(searchValue);
            System.out.println("Done searching TenPB...");
            
            //Đưa các bộ đếm hàng về 0:
            int rowsToRemove = this.tableModel.getRowCount();
            for(int i = rowsToRemove - 1; i>=0; i--)
            {
                this.tableModel.removeRow(i);
            }
            
            this.tableModel.setRowCount(0);
            //Reset lại tableModel:
            
            for(Department department: departmentList){
                this.tableModel.addRow(new Object[] {
                    // STT,MaPB, TenPB, NgayTL, Mota:
                    tableModel.getRowCount()+1,
                    department.getMaPB(),
                    department.getTenPB(),
                    department.getNgayTL(),
                    department.getSDT(),
                    department.getEmail(),
                    department.getChucNang()
                });
            }
            
        
        }
    }

    //Hiển thị dữ liệu lên table:
    private void showDepartment(){
        
        this.departmentList = DepartmentModify.findAll();
        
        //Đưa các bộ đếm hàng về 0:
        tableModel.setRowCount(0);
        for(Department department: departmentList){
            tableModel.addRow(new Object[] {
                // STT,MaPB, TenPB, NgayTL, Mota:
                tableModel.getRowCount()+1,
                department.getMaPB(),
                department.getTenPB(),
                department.getNgayTL(),
                department.getSDT(),
                department.getEmail(),
                department.getChucNang()

            });             
        }
    }
    
    private void resetForm(){
        selectedIndex = -1;
        funcName = "";
        btnSave.setEnabled(false);
        btnCancel.setEnabled(false);
       
        btnDelete.setEnabled(true);
        btnEdit.setEnabled(true);
        btnAdd.setEnabled(true);
        
        txtDerpartmentCode.setText("");
        txtDepartmentName.setText("");
        txtAreaDescription.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        dateChStartDay.setDate(null);

        
        txtDerpartmentCode.setEditable(false);
        txtDepartmentName.setEditable(false);
        txtAreaDescription.setEditable(false);
        txtPhone.setEditable(false);
        txtEmail.setEditable(false);
        dateChStartDay.setEnabled(false);
        
        txtSearchName.setEditable(false);
        
        //Mở các chức năng tìm kiếm
        btnSearch.setEnabled(true);        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtDerpartmentCode = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDepartmentName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        dateChStartDay = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaDescription = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDepartmentMana = new javax.swing.JTable();
        txtSearchName = new javax.swing.JTextField();
        btnPrint = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 204));
        jLabel1.setText("DANH MỤC PHÒNG BAN");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 18))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel2.setText("Mã phòng ban:");

        txtDerpartmentCode.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel3.setText("Tên phòng ban:");

        txtDepartmentName.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel4.setText("Ngày thành lập:");

        dateChStartDay.setDateFormatString("dd/MM/yyyy");
        dateChStartDay.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel5.setText("Mô tả:");

        txtAreaDescription.setColumns(20);
        txtAreaDescription.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        txtAreaDescription.setRows(5);
        jScrollPane2.setViewportView(txtAreaDescription);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel6.setText("Số điện thoại:");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel7.setText("Email:");

        txtPhone.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        txtEmail.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateChStartDay, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(txtDepartmentName)
                    .addComponent(txtDerpartmentCode)
                    .addComponent(txtPhone)
                    .addComponent(txtEmail)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDerpartmentCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDepartmentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateChStartDay, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 77, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 18))); // NOI18N

        tableDepartmentMana.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã PB", "Tên phòng ban", "Ngày thành lập", "SĐT", "Email", "Mô tả"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableDepartmentMana);
        if (tableDepartmentMana.getColumnModel().getColumnCount() > 0) {
            tableDepartmentMana.getColumnModel().getColumn(0).setMinWidth(40);
            tableDepartmentMana.getColumnModel().getColumn(0).setPreferredWidth(40);
            tableDepartmentMana.getColumnModel().getColumn(0).setMaxWidth(40);
            tableDepartmentMana.getColumnModel().getColumn(1).setMinWidth(62);
            tableDepartmentMana.getColumnModel().getColumn(1).setPreferredWidth(62);
            tableDepartmentMana.getColumnModel().getColumn(1).setMaxWidth(62);
            tableDepartmentMana.getColumnModel().getColumn(2).setPreferredWidth(40);
            tableDepartmentMana.getColumnModel().getColumn(3).setMinWidth(100);
            tableDepartmentMana.getColumnModel().getColumn(3).setPreferredWidth(100);
            tableDepartmentMana.getColumnModel().getColumn(3).setMaxWidth(100);
            tableDepartmentMana.getColumnModel().getColumn(4).setMinWidth(75);
            tableDepartmentMana.getColumnModel().getColumn(4).setPreferredWidth(75);
            tableDepartmentMana.getColumnModel().getColumn(4).setMaxWidth(75);
        }

        btnPrint.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/print-icon.png"))); // NOI18N
        btnPrint.setText("In");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/timkiem.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPrint)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnSearch)
                        .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 18))); // NOI18N

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdd)
                .addGap(26, 26, 26)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnSave)
                .addGap(29, 29, 29)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/exit.png"))); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1))
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        if(selectedIndex < 0)
        {
            JOptionPane.showMessageDialog(null, "Hãy chọn một phòng ban để chỉnh sửa!");
            resetForm();
        }else{
            //Chuẩn bị các chức năng để chỉnh sửa:
            this.funcName = "edit";
        
            //Không cho người dùng thay đổi mã bộ phận:
            txtDerpartmentCode.setEditable(false);
            txtAreaDescription.setEditable(true);
            txtDepartmentName.setEditable(true);
            txtPhone.setEditable(true);
            txtEmail.setEditable(true);
            
            dateChStartDay.setEnabled(true);

            btnAdd.setEnabled(false);
            btnDelete.setEnabled(false);

            btnSave.setEnabled(true);
            btnCancel.setEnabled(true);
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
        if(txtDepartmentName.isEditable() && txtAreaDescription.isEditable())
        {
            //Kiểm tra họ có muốn hủy bỏ các trường đã nhập để thêm một department mới không?
            //Kiểm tra người dùng đã nhập đc thông tin gì rồi?
            //Nếu chưa nhập thông tin gì thì bấm thoải mái.
            if(!txtDepartmentName.getText().equals("") ||
                    !(dateChStartDay.getDate() == null) || !txtPhone.getText().equals("") || !txtEmail.getText().equals("") || !txtAreaDescription.getText().equals(""))
            {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Các thông tin bạn vừa điền sẽ bị mất. Hãy ấn nút Lưu nếu muốn lưu giữ các thông tin này!","Warning",dialogButton);
                
                if(dialogResult == JOptionPane.YES_OPTION){
                    // Reset thêm mới:
                    txtDepartmentName.setText("");
                    dateChStartDay.setDate(null);
                    txtPhone.setText("");
                    txtEmail.setText("");
                    txtAreaDescription.setText("");
                }
            }
        }else{
            //Reset phòng trường hợp nếu như người dùng đã click vào một bộ phận bất kỳ:
            txtDerpartmentCode.setText("");
            txtDepartmentName.setText("");
            dateChStartDay.setDate(null);
            txtPhone.setText("");
            txtEmail.setText("");
            txtAreaDescription.setText("");
            
            //Không cho người dùng nhập primary Key để hệ thống tự generate:
            txtDerpartmentCode.setEditable(false);
            txtDepartmentName.setEditable(true);
            dateChStartDay.setEnabled(true);
            txtPhone.setEditable(true);
            txtEmail.setEditable(true);
            txtAreaDescription.setEditable(true);
            
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

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if(selectedIndex < 0)
        {
            JOptionPane.showMessageDialog(null, "Hãy chọn một phòng ban để xóa!");
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

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        switch(this.funcName){
            //Thêm dữ liệu:
            case "add":
                
                //Kiểm tra xem các trường dữ liệu có rỗng hay không?:
                String TenPB = txtDepartmentName.getText();
                Date sdDate = dateChStartDay.getDate();
                String SDT = txtPhone.getText();
                String Email = txtEmail.getText();
                String ChucNang = txtAreaDescription.getText();
                
                //Xóa các khoảng trắng ở phía đầu và phía đuôi của chuỗi:
                TenPB = TenPB.trim();
                SDT = SDT.trim();
                Email = Email.trim();
                ChucNang = ChucNang.trim();
                
                if(!TenPB.equals("") && 
                        !(sdDate==null) && !(SDT.equals("")) && !(Email.equals("")) && !ChucNang.equals(""))
                {
                    try {
                        
                        //Chuỗi ban đầu lưu: dd/MM/yyyy:
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String NgayTL = df.format(sdDate);

                        System.out.println("Ngay: " + NgayTL);
                        Department department = new Department(TenPB,NgayTL, SDT, Email, ChucNang);
                        DepartmentModify.insert(department);

                        //Show lại dữ liệu mới:
                        showDepartment();
                        JOptionPane.showMessageDialog(null, "Bạn đã lưu thành công phòng ban này!");
                        
                        //Reset form về ban đầu:
                        resetForm();

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Hãy điền đầy đủ thông tin của phòng ban!");
                }
                break;
                
            case "delete":
                Department department = this.departmentList.get(selectedIndex);
            
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Bạn có muốn xoá phòng ban này?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION){
                    
                    //Delete department:
                    DepartmentModify.delete(department.getMaPB());
                    //Show lại dữ liệu mới:
                    showDepartment();
                    
                    //Reset form về ban đầu:
                    resetForm();
                }
                break;
                
            case "edit":
                
                //Kiểm tra xem các trường dữ liệu có rỗng hay không?:
                String oldMaPB = txtDerpartmentCode.getText();
                String newTenPB = txtDepartmentName.getText();
                Date nwsdDate = dateChStartDay.getDate();
                String newSDT = txtPhone.getText();
                String newEmail = txtEmail.getText();
                String newChucNang = txtAreaDescription.getText();
                
                //Xóa khoảng trắng ở đầu và cuối chuỗi:
                newTenPB = newTenPB.trim();
                newSDT = newSDT.trim();
                newEmail = newEmail.trim();
                newChucNang = newChucNang.trim();
                
                if(!newTenPB.equals("") && 
                        !(nwsdDate == null) && !newSDT.equals("") && !newEmail.equals("") && !newChucNang.equals(""))
                {
                    String newNgayTL = null;
                    try {
                        
                        //Chuỗi ban đầu lưu: dd/MM/yyyy:
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        newNgayTL = df.format(nwsdDate);
                        System.out.println("Ngay: " + newNgayTL);
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    
                    int editDialogButton = JOptionPane.YES_NO_OPTION;
                    int editDialogResult = JOptionPane.showConfirmDialog (null, "Bạn có muốn lưu lại kết quả chỉnh sửa?","Warning",editDialogButton);
                    if(editDialogResult == JOptionPane.YES_OPTION){
                        Department editedDepartment = new Department(oldMaPB,newTenPB,newNgayTL,newSDT,newEmail,newChucNang);

                        DepartmentModify.update(editedDepartment);
                        //Show lại dữ liệu mới:
                        showDepartment();
                        //Reset form về ban đầu:
                        resetForm();
                    }
                    
                }else{
                    JOptionPane.showMessageDialog(null, "Hãy điền đầy đủ thông tin phòng ban!");
                }
                break;
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        if(selectedIndex >= 0)
        {
            Department department = departmentList.get(selectedIndex);

            txtDerpartmentCode.setText(department.getMaPB());
            txtDepartmentName.setText(department.getTenPB());
            try {
                //Chuỗi ban đầu lưu: yyyy-MM-dd:
                String sdStr = department.getNgayTL();

                Date sdDate = new SimpleDateFormat("yyyy-MM-dd").parse(sdStr);

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String tmp = df.format(sdDate);

                Date result = new SimpleDateFormat("dd/MM/yyyy").parse(tmp);

                dateChStartDay.setDate(result);
                txtPhone.setText(department.getSDT());
                txtEmail.setText(department.getEmail());
                txtAreaDescription.setText(department.getChucNang());

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        else
        {
            txtDerpartmentCode.setText("");
            txtDepartmentName.setText("");
            txtPhone.setText("");
            txtEmail.setText("");
            txtAreaDescription.setText("");
            dateChStartDay.setDate(null);
        }
        
        funcName = "";
        btnSave.setEnabled(false);
        btnCancel.setEnabled(false);
       
        btnDelete.setEnabled(true);
        btnEdit.setEnabled(true);
        btnAdd.setEnabled(true);
    
        txtDerpartmentCode.setEditable(false);
        txtDepartmentName.setEditable(false);
        dateChStartDay.setEnabled(false);
        txtPhone.setEditable(false);
        txtEmail.setEditable(false);
        txtAreaDescription.setEditable(false);
        
        //Reset các chức năng tìm kiếm
        txtSearchName.setText("");
        //Mở thao tác các chức năng tìm kiếm
        btnSearch.setEnabled(true);
        
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        //Reset form về ban đầu:
        resetForm();
    }//GEN-LAST:event_btnRefreshActionPerformed

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

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        txtSearchName.setEditable(true);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:
                //Khai báo đường dẫn đến file StaffManagement:
        String link = "E:\\THNN2021\\StaffManagement\\src\\Reports\\Department.jrxml";
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
    }//GEN-LAST:event_btnPrintActionPerformed

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
            java.util.logging.Logger.getLogger(DepartmentManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DepartmentManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DepartmentManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DepartmentManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DepartmentManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private com.toedter.calendar.JDateChooser dateChStartDay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tableDepartmentMana;
    private javax.swing.JTextArea txtAreaDescription;
    private javax.swing.JTextField txtDepartmentName;
    private javax.swing.JTextField txtDerpartmentCode;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearchName;
    // End of variables declaration//GEN-END:variables
}
