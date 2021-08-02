/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Database.Position;
import Database.PositionModify;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class PositionManagement extends javax.swing.JFrame implements DocumentListener{
    
    List<Position> positionList = new ArrayList<>();
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
     * Creates new form PositionManagement
     */
    public PositionManagement() {
        initComponents();
        
        //Đổ dữ liệu vào table khi bật chức năng này lên:
        tableModel = (DefaultTableModel) tablePositionMana.getModel();
        showPosition();
        //Giúp cho textArea tự xuống dòng:
        txtNote.setLineWrap(true);
        txtNote.setWrapStyleWord(true);
        
        txtSearchName.setEditable(false);
        
        searchValue= "";// Chưa nhập nội dung tìm kiếm hay tìm kiếm all.
        
        //Gắn lắng nghe sự kiện cho textfield tìm kiếm:
        txtSearchName.getDocument().addDocumentListener(this);
        
        this.resetForm();
        
        tablePositionMana.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e){    
            }
            @Override
            public void mousePressed(MouseEvent e){
                selectedIndex = tablePositionMana.getSelectedRow();
                if(selectedIndex >= 0 && !funcName.equals("add"))
                {
                    Position position = positionList.get(selectedIndex);
                    
                    txtPositionCode.setText(position.getMaCV());
                    txtPositionName.setText(position.getTenCV());
                    txtAllowance.setText(position.getPCChucVu());
                    txtNote.setText(position.getGhiChu());
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
            String positionName = txtSearchName.getText().trim();
            System.out.println(positionName);
            //Khi thay đổi txtDepartmentCode thì gọi database:
            this.showSearchResult("TenCV",positionName);            
        }
    }
    
    //Hiển thị dữ liệu lên table: theo nội dung tìm kiếm (mặc định: fieldName = null, searchValue = "")
    private void showSearchResult(String fieldName, String searchValue){
        
        if(fieldName == null)
        {
            //Show all department:
            this.showPosition();
        }
        else if(fieldName.equals("TenCV")){            
            this.positionList = PositionModify.findByTenCV(searchValue);            
            //Đưa các bộ đếm hàng về 0:
            int rowsToRemove = this.tableModel.getRowCount();
            for(int i = rowsToRemove - 1; i>=0; i--)
            {
                this.tableModel.removeRow(i);
            }            
            this.tableModel.setRowCount(0);
            //Reset lại tableModel:
            
            for(Position position: positionList){
                this.tableModel.addRow(new Object[] {
                    // STT,MaCV, TenCV, PCChucVu, GhiChu:
                    tableModel.getRowCount()+1,
                    position.getMaCV(),
                    position.getTenCV(),
                    position.getPCChucVu(),
                    position.getGhiChu()
                });
            }
            
        
        }
    }
    
    //Hiển thị dữ liệu lên table:
    private void showPosition(){
        
        this.positionList = PositionModify.findAll();
        
        //Đưa các bộ đếm hàng về 0:
        tableModel.setRowCount(0);
        for(Position position: positionList){
            tableModel.addRow(new Object[] {
                // STT,MaCV, TenCV, PCChucVu, GhiChu:
                tableModel.getRowCount()+1,
                position.getMaCV(),
                position.getTenCV(),
                position.getPCChucVu(),
                position.getGhiChu()
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
        
        txtPositionCode.setText("");
        txtPositionName.setText("");
        txtAllowance.setText("");
        txtNote.setText("");

        
        txtPositionCode.setEditable(false);
        txtPositionName.setEditable(false);
        txtAllowance.setEditable(false);
        txtNote.setEditable(false);
        
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtPositionCode = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPositionName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtAllowance = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        btnSearch = new javax.swing.JButton();
        txtSearchName = new javax.swing.JTextField();
        btnPrint = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePositionMana = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 204));
        jLabel1.setText("DANH MỤC CHỨC VỤ");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 18))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel2.setText("Mã chức vụ:");

        txtPositionCode.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel3.setText("Tên chức vụ:");

        txtPositionName.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel6.setText("Phụ cấp:");

        txtAllowance.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel5.setText("Ghi chú:");

        txtNote.setColumns(20);
        txtNote.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        txtNote.setRows(5);
        jScrollPane2.setViewportView(txtNote);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPositionName, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPositionCode, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(52, 52, 52)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(53, 53, 53)
                            .addComponent(txtAllowance, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPositionCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPositionName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAllowance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 18))); // NOI18N

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/timkiem.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnPrint.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/print-icon.png"))); // NOI18N
        btnPrint.setText("In");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        tablePositionMana.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã CV", "Tên chức vụ", "Phụ cấp", "Ghi chú"
            }
        ));
        jScrollPane1.setViewportView(tablePositionMana);
        if (tablePositionMana.getColumnModel().getColumnCount() > 0) {
            tablePositionMana.getColumnModel().getColumn(0).setMinWidth(50);
            tablePositionMana.getColumnModel().getColumn(0).setPreferredWidth(50);
            tablePositionMana.getColumnModel().getColumn(0).setMaxWidth(50);
            tablePositionMana.getColumnModel().getColumn(1).setMinWidth(70);
            tablePositionMana.getColumnModel().getColumn(1).setPreferredWidth(70);
            tablePositionMana.getColumnModel().getColumn(1).setMaxWidth(70);
            tablePositionMana.getColumnModel().getColumn(3).setMinWidth(100);
            tablePositionMana.getColumnModel().getColumn(3).setPreferredWidth(100);
            tablePositionMana.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnPrint)
                    .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
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
                    .addComponent(btnAdd)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(220, 220, 220))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        txtSearchName.setEditable(true);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrintActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
                //Báo hiệu là đang thao tác chức năng add:
        this.funcName = "add";
        //Kiểm tra xem liệu họ có đang thêm dữ liệu không?
        if(txtPositionName.isEditable() && txtAllowance.isEditable() && txtNote.isEditable())
        {
            //Kiểm tra họ có muốn hủy bỏ các trường đã nhập để thêm một position mới không?
            //Kiểm tra người dùng đã nhập đc thông tin gì rồi?
            //Nếu chưa nhập thông tin gì thì bấm thoải mái.
            if(!txtPositionName.getText().equals("") || !txtAllowance.getText().equals("") || !txtNote.getText().equals(""))
            {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Các thông tin bạn vừa điền sẽ bị mất. Hãy ấn nút Lưu nếu muốn lưu giữ các thông tin này!","Warning",dialogButton);
                
                if(dialogResult == JOptionPane.YES_OPTION){
                    // Reset thêm mới:
                    txtPositionName.setText("");
                    txtAllowance.setText("");
                    txtNote.setText("");
                }
            }
        }else{
            //Reset phòng trường hợp nếu như người dùng đã click vào một bộ phận bất kỳ:
            txtPositionCode.setText("");
            txtPositionName.setText("");
            txtAllowance.setText("");
            txtNote.setText("");
            
            //Không cho người dùng nhập primary Key để hệ thống tự generate:
            txtPositionCode.setEditable(false);
            txtPositionName.setEditable(true);
            txtAllowance.setEditable(true);
            txtNote.setEditable(true);
            
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

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        if(selectedIndex < 0)
        {
            JOptionPane.showMessageDialog(null, "Hãy chọn một chức vụ để chỉnh sửa!");
            resetForm();
        }else{
            //Chuẩn bị các chức năng để chỉnh sửa:
            this.funcName = "edit";
        
            //Không cho người dùng thay đổi mã chức vụ:
            txtPositionCode.setEditable(false);
            txtPositionName.setEditable(true);
            txtAllowance.setEditable(true);
            txtNote.setEditable(true);
            
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

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        if(selectedIndex >= 0)
        {
            Position position = positionList.get(selectedIndex);

            txtPositionCode.setText(position.getMaCV());
            txtPositionName.setText(position.getTenCV());
            txtAllowance.setText(position.getPCChucVu());
            txtNote.setText(position.getGhiChu());
        }
        else
        {
            txtPositionCode.setText("");
            txtPositionName.setText("");
            txtAllowance.setText("");
            txtNote.setText("");
        }
        
        funcName = "";
        btnSave.setEnabled(false);
        btnCancel.setEnabled(false);
       
        btnDelete.setEnabled(true);
        btnEdit.setEnabled(true);
        btnAdd.setEnabled(true);
    
        txtPositionCode.setEditable(false);
        txtPositionName.setEditable(false);
        txtAllowance.setEditable(false);
        txtNote.setEditable(false);
        
        //Reset các chức năng tìm kiếm
        txtSearchName.setText("");
        //Mở thao tác các chức năng tìm kiếm
        btnSearch.setEnabled(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if(selectedIndex < 0)
        {
            JOptionPane.showMessageDialog(null, "Hãy chọn một chức vụ để xóa!");
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
                String TenCV = txtPositionName.getText();
                String PCChucVu = txtAllowance.getText();
                String GhiChu = txtNote.getText();
                
                //Xóa các khoảng trắng ở phía đầu và phía đuôi của chuỗi:
                TenCV = TenCV.trim();
                PCChucVu = PCChucVu.trim();
                GhiChu = GhiChu.trim();
                
                if(!TenCV.equals("") && !(PCChucVu.equals("")))
                {
                    try {
                        Position position = new Position(TenCV,PCChucVu, GhiChu);
                        PositionModify.insert(position);

                        //Show lại dữ liệu mới:
                        showPosition();
                        JOptionPane.showMessageDialog(null, "Bạn đã lưu thành công chức vụ này!");
                        
                        //Reset form về ban đầu:
                        resetForm();

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
                break;
                
            case "delete":
                Position position = this.positionList.get(selectedIndex);
            
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "Bạn có muốn xoá chuwscc vụ này?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION){
                    
                    //Delete department:
                    PositionModify.delete(position.getMaCV());
                    //Show lại dữ liệu mới:
                    showPosition();
                    
                    //Reset form về ban đầu:
                    resetForm();
                }
                break;
                
            case "edit":
                
                //Kiểm tra xem các trường dữ liệu có rỗng hay không?:
                String oldMaCV = txtPositionCode.getText();
                String newTenCV = txtPositionName.getText();
                String newPCChucVu = txtAllowance.getText();
                String newGhiChu = txtNote.getText();
                
                //Xóa khoảng trắng ở đầu và cuối chuỗi:
                newTenCV = newTenCV.trim();
                newPCChucVu = newPCChucVu.trim();
                newGhiChu = newGhiChu.trim();
                
                if(!newTenCV.equals("") && !newPCChucVu.equals(""))
                {
                    int editDialogButton = JOptionPane.YES_NO_OPTION;
                    int editDialogResult = JOptionPane.showConfirmDialog (null, "Bạn có muốn lưu lại kết quả chỉnh sửa?","Warning",editDialogButton);
                    if(editDialogResult == JOptionPane.YES_OPTION){
                        Position editedPosition = new Position(oldMaCV,newTenCV,newPCChucVu,newGhiChu);

                        PositionModify.update(editedPosition);
                        //Show lại dữ liệu mới:
                        showPosition();
                        //Reset form về ban đầu:
                        resetForm();
                    }                    
                }
                break;
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(PositionManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PositionManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PositionManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PositionManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PositionManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablePositionMana;
    private javax.swing.JTextField txtAllowance;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtPositionCode;
    private javax.swing.JTextField txtPositionName;
    private javax.swing.JTextField txtSearchName;
    // End of variables declaration//GEN-END:variables
}
