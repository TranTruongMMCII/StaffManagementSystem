/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Database.EmployeeReport;
import Database.EmployeeModify;
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
public class EmployeeSearch extends javax.swing.JFrame implements DocumentListener{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<EmployeeReport> employeeList = new ArrayList<>();
    //Khai baÌ�o caÌ�c biÃªÌ�n tiÌ€m kiÃªÌ�m:
    String fieldName;
    String searchValue;
    
    //DuÌ€ng Ä‘ÃªÌ‰ thao taÌ�c trÃªn baÌ‰ng:
    DefaultTableModel tableModel;

    /**
     * Creates new form DepartmentSearch
     */
    public EmployeeSearch() {
        initComponents();
        
        //MÄƒÌ£c Ä‘iÌ£nh ban Ä‘Ã¢Ì€u laÌ€ Ä‘ang tiÌ€m kiÃªÌ�m maÌƒ phoÌ€ng ban:
        rbCode.setSelected(true);
        txtSearchCode.setEditable(true);
        
        rbName.setSelected(false);
        txtSearchName.setEditable(false);
        
        rbCMND.setSelected(false);
        txtSearchCMND.setEditable(false);
        
        //Ä�Ã´Ì‰ dÆ°Ìƒ liÃªÌ£u vaÌ€o table khi bÃ¢Ì£t chÆ°Ì�c nÄƒng naÌ€y lÃªn:
        tableModel = (DefaultTableModel) tableEmployeeMana.getModel();
        showEmployee();
        
        fieldName = "MaNV";// MÄƒÌ£c Ä‘iÌ£nh ban Ä‘Ã¢Ì€u Ä‘ang Æ¡Ì‰ MaPB
        searchValue= "";// ChÆ°a nhÃ¢Ì£p nÃ´Ì£i dung tiÌ€m kiÃªÌ�m hay tiÌ€m kiÃªÌ�m all.
        //GÄƒÌ�n lÄƒÌ�ng nghe sÆ°Ì£ kiÃªÌ£n cho 2 textfield:
        txtSearchCode.getDocument().addDocumentListener(this);
        txtSearchName.getDocument().addDocumentListener(this);
        txtSearchCMND.getDocument().addDocumentListener(this);
    }
       
    //Start: caÌ�c interface cuÌ‰a DocumentListener:
    public void changedUpdate(DocumentEvent e) {
        changeText(e);
    }
    
    public void removeUpdate(DocumentEvent e) {
        changeText(e);
    }
    public void insertUpdate(DocumentEvent e) {
        changeText(e);
    }
    //End: caÌ�c interface cuÌ‰a DocumentListener:
    
    
    //HiÃªÌ‰n thiÌ£ dÆ°Ìƒ liÃªÌ£u lÃªn table:
    private void showEmployee(){
        
        this.employeeList = EmployeeModify.findAll();
        
        //Ä�Æ°a caÌ�c bÃ´Ì£ Ä‘ÃªÌ�m haÌ€ng vÃªÌ€ 0:
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
    
    public void changeText(DocumentEvent e) {
        if(e.getDocument() == txtSearchCode.getDocument())
        {
            //GaÌ�n giaÌ� triÌ£ tiÌ€m kiÃªÌ�m Ä‘ÃªÌ‰ cÃ¢Ì€n khi tiÌ€m kiÃªÌ�m chiÌ�nh xaÌ�c:
            this.searchValue = txtSearchCode.getText().trim();
            String employeeCode = txtSearchCode.getText().trim();
            //Khi thay Ä‘Ã´Ì‰i txtSearchCode thiÌ€ goÌ£i database:
            this.showSearchResult("MaNV",employeeCode);
            
        }else if(e.getDocument() == txtSearchName.getDocument())
        {
            //GaÌ�n giaÌ� triÌ£ tiÌ€m kiÃªÌ�m Ä‘ÃªÌ‰ cÃ¢Ì€n khi tiÌ€m kiÃªÌ�m chiÌ�nh xaÌ�c:
            this.searchValue = txtSearchName.getText().trim();
            String employeeName = txtSearchName.getText().trim();

            //Khi thay Ä‘Ã´Ì‰i txtSearchName thiÌ€ goÌ£i database:
            this.showSearchResult("HoTen",employeeName);
        }        
        else if(e.getDocument() == txtSearchCMND.getDocument())
        {
            //GaÌ�n giaÌ� triÌ£ tiÌ€m kiÃªÌ�m Ä‘ÃªÌ‰ cÃ¢Ì€n khi tiÌ€m kiÃªÌ�m chiÌ�nh xaÌ�c:
            this.searchValue = txtSearchCMND.getText().trim();
            String employeeCMND = txtSearchCMND.getText().trim();

            //Khi thay Ä‘Ã´Ì‰i txtSearchCMND thiÌ€ goÌ£i database:
            this.showSearchResult("CMND",employeeCMND);
        }
    }
    
    //HiÃªÌ‰n thiÌ£ dÆ°Ìƒ liÃªÌ£u lÃªn table: theo nÃ´Ì£i dung tiÌ€m kiÃªÌ�m (mÄƒÌ£c Ä‘iÌ£nh: fieldName = null, searchValue = "")
    private void showSearchResult(String fieldName, String searchValue){
        
        if(fieldName == null)
        {
            //Show all Employee:
            this.showEmployee();
        }
        else if(fieldName.equals("MaNV")){          
            this.employeeList = EmployeeModify.findByMaNV(searchValue);            
            //Ä�Æ°a caÌ�c bÃ´Ì£ Ä‘ÃªÌ�m haÌ€ng vÃªÌ€ 0:
            int rowsToRemove = this.tableModel.getRowCount();
            for(int i = rowsToRemove - 1; i>=0; i--)
            {
                this.tableModel.removeRow(i);
            }
            
            this.tableModel.setRowCount(0);
            //Reset laÌ£i tableModel:
            
            for(EmployeeReport employee: employeeList){
                this.tableModel.addRow(new Object[] {
                    
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
            
        }else if(fieldName.equals("HoTen")){          
            this.employeeList = EmployeeModify.findByHoTen(searchValue);            
            //Ä�Æ°a caÌ�c bÃ´Ì£ Ä‘ÃªÌ�m haÌ€ng vÃªÌ€ 0:
            int rowsToRemove = this.tableModel.getRowCount();
            for(int i = rowsToRemove - 1; i>=0; i--)
            {
                this.tableModel.removeRow(i);
            }
            
            this.tableModel.setRowCount(0);
            //Reset laÌ£i tableModel:
            
            for(EmployeeReport employee: employeeList){
                this.tableModel.addRow(new Object[] {
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
        }else if(fieldName.equals("CMND")){           
            this.employeeList = EmployeeModify.findByCMND(searchValue);            
            //Ä�Æ°a caÌ�c bÃ´Ì£ Ä‘ÃªÌ�m haÌ€ng vÃªÌ€ 0:
            int rowsToRemove = this.tableModel.getRowCount();
            for(int i = rowsToRemove - 1; i>=0; i--)
            {
                this.tableModel.removeRow(i);
            }
            
            this.tableModel.setRowCount(0);
            //Reset laÌ£i tableModel:            
            for(EmployeeReport employee: employeeList){
                this.tableModel.addRow(new Object[] {
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEmployeeMana = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        rbCode = new javax.swing.JRadioButton();
        txtSearchCode = new javax.swing.JTextField();
        rbName = new javax.swing.JRadioButton();
        txtSearchName = new javax.swing.JTextField();
        rbCMND = new javax.swing.JRadioButton();
        txtSearchCMND = new javax.swing.JTextField();
        btnRefesh = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TÃ¬m kiáº¿m nhÃ¢n viÃªn");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ThÃ´ng tin chi tiáº¿t", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 18))); // NOI18N

        tableEmployeeMana.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MÃ£ NV", "Há»� TÃªn", "NgÃ y Sinh", "Giá»›i TÃ­nh", "PhÃ²ng ban", "Chá»©c vá»¥", "Ä�á»‹a Chá»‰", "CMND", "Ä�iá»‡n thoáº¡i", "Email", "HS lÆ°Æ¡ng"
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
        jScrollPane1.setViewportView(tableEmployeeMana);
        if (tableEmployeeMana.getColumnModel().getColumnCount() > 0) {
            tableEmployeeMana.getColumnModel().getColumn(0).setMinWidth(40);
            tableEmployeeMana.getColumnModel().getColumn(0).setPreferredWidth(40);
            tableEmployeeMana.getColumnModel().getColumn(0).setMaxWidth(40);
            tableEmployeeMana.getColumnModel().getColumn(1).setMinWidth(60);
            tableEmployeeMana.getColumnModel().getColumn(1).setPreferredWidth(60);
            tableEmployeeMana.getColumnModel().getColumn(1).setMaxWidth(60);
            tableEmployeeMana.getColumnModel().getColumn(2).setMinWidth(120);
            tableEmployeeMana.getColumnModel().getColumn(2).setPreferredWidth(120);
            tableEmployeeMana.getColumnModel().getColumn(2).setMaxWidth(120);
            tableEmployeeMana.getColumnModel().getColumn(3).setMinWidth(70);
            tableEmployeeMana.getColumnModel().getColumn(3).setPreferredWidth(70);
            tableEmployeeMana.getColumnModel().getColumn(3).setMaxWidth(70);
            tableEmployeeMana.getColumnModel().getColumn(4).setMinWidth(62);
            tableEmployeeMana.getColumnModel().getColumn(4).setPreferredWidth(62);
            tableEmployeeMana.getColumnModel().getColumn(4).setMaxWidth(62);
            tableEmployeeMana.getColumnModel().getColumn(7).setMinWidth(75);
            tableEmployeeMana.getColumnModel().getColumn(7).setPreferredWidth(75);
            tableEmployeeMana.getColumnModel().getColumn(7).setMaxWidth(75);
            tableEmployeeMana.getColumnModel().getColumn(8).setMinWidth(70);
            tableEmployeeMana.getColumnModel().getColumn(8).setPreferredWidth(70);
            tableEmployeeMana.getColumnModel().getColumn(8).setMaxWidth(70);
            tableEmployeeMana.getColumnModel().getColumn(9).setMinWidth(70);
            tableEmployeeMana.getColumnModel().getColumn(9).setPreferredWidth(70);
            tableEmployeeMana.getColumnModel().getColumn(9).setMaxWidth(70);
            tableEmployeeMana.getColumnModel().getColumn(11).setMinWidth(60);
            tableEmployeeMana.getColumnModel().getColumn(11).setPreferredWidth(60);
            tableEmployeeMana.getColumnModel().getColumn(11).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 204, 204));
        jLabel1.setText("TÃŒM KIáº¾M NHÃ‚N VIÃŠN");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TÃ¬m kiáº¿m", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 18))); // NOI18N

        rbCode.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        rbCode.setText("MÃ£ NV");
        rbCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCodeActionPerformed(evt);
            }
        });

        rbName.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        rbName.setText("Há»� TÃªn");
        rbName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbNameActionPerformed(evt);
            }
        });

        rbCMND.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        rbCMND.setText("CMND");
        rbCMND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCMNDActionPerformed(evt);
            }
        });

        btnRefesh.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnRefesh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh.png"))); // NOI18N
        btnRefesh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefeshActionPerformed(evt);
            }
        });

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/exit.png"))); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(rbCMND, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rbCode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearchCMND, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefesh, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSearchCode, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBack, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchCode, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbCode))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbName)
                    .addComponent(txtSearchName, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbCMND)
                    .addComponent(txtSearchCMND, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRefesh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(547, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(383, 383, 383))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        DashBoard db = new DashBoard();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "BaÌ£n coÌ� cháº¯c cháº¯n muá»‘n thoÃ¡t?","Warning",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            db.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void rbCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCodeActionPerformed
        // TODO add your handling code here:
        this.fieldName = "MaNV";
        //Reset radio button DepartmentName:
        rbName.setSelected(false);
        rbCMND.setSelected(false);
        
        //VÃ´ hiÃªÌ£u hoÌ�a txtDepartmentName:
        txtSearchName.setEditable(false);
        txtSearchName.setText("");
        txtSearchCMND.setEditable(false);
        txtSearchCMND.setText("");
        
        //HuÌ‰y vÃ´ hiÃªÌ£u hoÌ�a txtDepartmentCode:
        txtSearchCode.setEditable(true);
    }//GEN-LAST:event_rbCodeActionPerformed

    private void rbNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbNameActionPerformed
        // TODO add your handling code here:
        this.fieldName = "HoTen";
        //Reset radio button DepartmentName:
        rbCode.setSelected(false);
        rbCMND.setSelected(false);
        
        //VÃ´ hiÃªÌ£u hoÌ�a txtDepartmentName:
        txtSearchCode.setEditable(false);
        txtSearchCode.setText("");
        txtSearchCMND.setEditable(false);
        txtSearchCMND.setText("");
        
        //HuÌ‰y vÃ´ hiÃªÌ£u hoÌ�a txtDepartmentCode:
        txtSearchName.setEditable(true);
    }//GEN-LAST:event_rbNameActionPerformed

    private void rbCMNDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCMNDActionPerformed
        // TODO add your handling code here:
        this.fieldName = "CMND";
        //Reset radio button DepartmentName:
        rbCode.setSelected(false);
        rbName.setSelected(false);
        
        //VÃ´ hiÃªÌ£u hoÌ�a txtDepartmentName:
        txtSearchCode.setEditable(false);
        txtSearchCode.setText("");
        txtSearchName.setEditable(false);
        txtSearchName.setText("");
        
        //HuÌ‰y vÃ´ hiÃªÌ£u hoÌ�a txtDepartmentCode:
        txtSearchCMND.setEditable(true);
    }//GEN-LAST:event_rbCMNDActionPerformed

    private void btnRefeshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefeshActionPerformed
        // TODO add your handling code here:
        //Reset laÌ£i caÌ�c radio button:
        rbCode.setSelected(true);
        rbName.setSelected(false);
        rbCMND.setSelected(false);
        
        //Reset laÌ£i caÌ�c field nhÃ¢Ì£p dÆ°Ìƒ liÃªÌ£u:
        txtSearchCode.setEditable(true);
        txtSearchCode.setText("");
        
        txtSearchName.setEditable(false);
        txtSearchName.setText("");
        
        txtSearchCMND.setEditable(false);
        txtSearchCMND.setText("");
        
        //Reset laÌ£i caÌ�c field search:
        this.fieldName = "MaNV";
        this.searchValue= "";
        
        //ThÆ°Ì£c hiÃªÌ£n truy vÃ¢Ì�n lÃ¢Ì�y laÌ£i tÃ¢Ì�t caÌ‰ caÌ�c department coÌ� trong database:
        this.showEmployee();
    }//GEN-LAST:event_btnRefeshActionPerformed

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
            java.util.logging.Logger.getLogger(EmployeeSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeSearch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmployeeSearch().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnRefesh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbCMND;
    private javax.swing.JRadioButton rbCode;
    private javax.swing.JRadioButton rbName;
    private javax.swing.JTable tableEmployeeMana;
    private javax.swing.JTextField txtSearchCMND;
    private javax.swing.JTextField txtSearchCode;
    private javax.swing.JTextField txtSearchName;
    // End of variables declaration//GEN-END:variables
}
