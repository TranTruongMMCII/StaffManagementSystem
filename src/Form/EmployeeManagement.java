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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.Color;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 *
 * @author ASUS
 */
public class EmployeeManagement extends javax.swing.JFrame {
    
    List<EmployeeReport> employeeList = new ArrayList<>();
    List<String> departmentList= new ArrayList<>();
    List<String> roleNameList= new ArrayList<>();
    File file = null;
    
    //LÆ°u index row Ä‘ang Ä‘Æ°Æ¡Ì£c choÌ£n:
    int selectedIndex = -1;
    
    //DuÌ€ng Ä‘ÃªÌ‰ thao taÌ�c trÃªn baÌ‰ng:
    DefaultTableModel tableModel;
    
    //LÆ°u chÆ°Ì�c nÄƒng hiÃªÌ£n Ä‘ang sÆ°Ì‰ duÌ£ng Ä‘ÃªÌ‰ sÆ°Ì‰ duÌ£ng nuÌ�t lÆ°u vaÌ€ huÌ‰y:
    String funcName;
    private Object jOptionPane1;

    /**
     * Creates new form EmployeeManagement
     */
    public EmployeeManagement() {
        initComponents();
        
        //Ä�Ã´Ì‰ dÆ°Ìƒ liÃªÌ£u vaÌ€o table khi bÃ¢Ì£t chÆ°Ì�c nÄƒng naÌ€y lÃªn:
        tableModel = (DefaultTableModel) tableEmployeeMana.getModel();
        showEmployee();
        
        //GiuÌ�p cho textArea tÆ°Ì£ xuÃ´Ì�ng doÌ€ng:
        txtAddress.setLineWrap(true);
        txtAddress.setWrapStyleWord(true);
        
        //KhoÌ�a chÆ°Ì�c nÄƒng nhÃ¢Ì£p trÆ°Ì£c tiÃªÌ�p ngaÌ€y cuÌ‰a datechooser:
        JTextFieldDateEditor dateEditor = (JTextFieldDateEditor) dateChBirthday.getDateEditor();
        dateEditor.setEditable(false); 
        
        //Ä�Ã´Ì‰ giÆ¡Ì�i tiÌ�nh vaÌ€o cbGender -> Ä‘aÌƒ Ä‘Ã´Ì‰ mÄƒÌ£c Ä‘iÌ£nh bÃªn design
        
        //Ä�Ã´Ì‰ dÆ°Ìƒ liÃªÌ£u MaPBList vaÌ€o cbBoxMaPB:
        showComboBoxTenPB();
        
        //Ä�Ã´Ì‰ dÆ°Ìƒ liÃªÌ£u MaCVList vaÌ€o cbBoxMaCV:
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
                    //LÃ¢Ì�y ngaÌ€y:
                    try {
                        //ChuÃ´Ìƒi ban Ä‘Ã¢Ì€u lÆ°u: yyyy-MM-dd:
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
                    	ByteArrayInputStream bis = new ByteArrayInputStream(data);
                    	BufferedImage bImage2 = null;
                        try {
                        	bImage2 = ImageIO.read(bis);
                        	OutputStream targetFile = new FileOutputStream("tmp.jpg");
                        	targetFile.write(data);
                        	targetFile.close();
    	                    ImageIcon icon = new ImageIcon(new ImageIcon("tmp.jpg").getImage().getScaledInstance(jPanel4.getWidth(), jPanel4.getHeight(), Image.SCALE_DEFAULT));
    						lblNewLabel.setIcon(icon);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
                    else {
//                    	System.out.println("Image null");
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
            //ViÌ€ do database noÌ� lÆ°u thÆ°Ì€a kiÌ� tÆ°Ì£ khoaÌ‰ng trÄƒÌ�ng nÃªn phaÌ‰i xoÌ�a (duÌ€ng trim()):
            cbDepartment.addItem(s.trim());
        }
        
        //Set giaÌ� triÌ£ mÄƒÌ£c Ä‘iÌ£nh ban Ä‘Ã¢Ì€u laÌ€ index 0:
        cbDepartment.setSelectedIndex(0);
    }
            
    private void showComboBoxTenCV(){
        this.roleNameList = EmployeeModify.getAllRoleName();
        
        for(String s: roleNameList){
            //ViÌ€ do database noÌ� lÆ°u thÆ°Ì€a kiÌ� tÆ°Ì£ khoaÌ‰ng trÄƒÌ�ng nÃªn phaÌ‰i xoÌ�a (duÌ€ng trim()):
            cbRole.addItem(s.trim());
        }
        
        //Set giaÌ� triÌ£ mÄƒÌ£c Ä‘iÌ£nh ban Ä‘Ã¢Ì€u laÌ€ index 0:
        cbRole.setSelectedIndex(0);
    }
        private void setComboBoxGender(String value){
        //Do trong database noÌ� lÆ°u Nam vÆ¡Ì�i NÆ°Ìƒ biÌ£ dÆ° vaÌ€i dÃ¢Ì�u khoaÌ‰ng trÄƒÌ�ng...
        if(value.trim().equals("Nam"))
        {
            cbBoxGender.setSelectedIndex(0); // Nam
        }
        else{
            cbBoxGender.setSelectedIndex(1); // NÆ°Ìƒ
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
        jPanel4 = new javax.swing.JPanel();
        jPanel4.setBackground(Color.WHITE);
        lblNewLabel = new JLabel("");
        lblNewLabel.setEnabled(false);
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4Layout.setHorizontalGroup(
        	jPanel4Layout.createParallelGroup(Alignment.LEADING)
        		.addComponent(lblNewLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
        	jPanel4Layout.createParallelGroup(Alignment.LEADING)
        		.addComponent(lblNewLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
        );
        jPanel4.setLayout(jPanel4Layout);
        jPanel4.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
				int result = fileChooser.showOpenDialog(getParent());
				if (result == JFileChooser.APPROVE_OPTION) {
				    file = fileChooser.getSelectedFile();
				    ImageIcon icon = new ImageIcon(new ImageIcon(file.getAbsolutePath())
			        		.getImage().getScaledInstance(jPanel4.getWidth(), jPanel4.getHeight(), Image.SCALE_DEFAULT));
			        lblNewLabel.setIcon(icon);
				}
        	}
        });
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEmployeeMana = new javax.swing.JTable();
        tableEmployeeMana.setRowSelectionAllowed(false);
        txtSearchName = new javax.swing.JTextField();
        btnPrintEmployee = new javax.swing.JButton();
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
        jLabel1.setText("QUáº¢N LÃ� NHÃ‚N Sá»°");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiáº¿t", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 18))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel2.setText("MÃ£ nhÃ¢n viÃªn:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel3.setText("Há»� tÃªn NV:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel4.setText("NgÃ y sinh:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel5.setText("PhÃ²ng ban:");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel6.setText("Giá»›i tÃ­nh:");

        txtEmployeeCode.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        txtEmployeeCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmployeeCodeActionPerformed(evt);
            }
        });

        txtEmployeeName.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        cbBoxGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Ná»¯" }));

        txtEmail.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel7.setText("Chá»©c vá»¥:");

        txtSalary.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel8.setText("Ä�á»‹a chá»‰:");

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        jScrollPane2.setViewportView(txtAddress);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel9.setText("CMND:");

        txtPhone.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel10.setText("Email:");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel11.setText("Ä�iá»‡n thoáº¡i:");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jLabel12.setText("Há»‡ sá»‘ lÆ°Æ¡ng:");

        txtCMND.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        

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
        			.addGap(94)
        			.addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(27))
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGap(12)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
        			.addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel1.setLayout(jPanel1Layout);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ThÃ´ng tin chi tiáº¿t", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 18))); // NOI18N

        tableEmployeeMana.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "MÃ£ NV", "Há»� TÃªn", "NgÃ y Sinh", "Giá»›i TÃ­nh", "PhÃ²ng Ban", "Chá»©c Vá»¥", "Ä�á»‹a Chá»‰", "CMND", "Ä�iá»‡n Thoáº¡i", "Email", "Há»‡ Sá»‘ LÆ°Æ¡ng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
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
            tableEmployeeMana.getColumnModel().getColumn(2).setMinWidth(130);
            tableEmployeeMana.getColumnModel().getColumn(2).setPreferredWidth(130);
            tableEmployeeMana.getColumnModel().getColumn(2).setMaxWidth(130);
            tableEmployeeMana.getColumnModel().getColumn(3).setMinWidth(80);
            tableEmployeeMana.getColumnModel().getColumn(3).setPreferredWidth(80);
            tableEmployeeMana.getColumnModel().getColumn(3).setMaxWidth(80);
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

        btnPrintEmployee.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnPrintEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/print-icon.png"))); // NOI18N
        btnPrintEmployee.setText("In");
        btnPrintEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintEmployeeActionPerformed(evt);
            }
        });

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/timkiem.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnPrintEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(btnPrintEmployee)
                    .addComponent(btnSearch)
                    .addComponent(txtSearchName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnAdd.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add.png"))); // NOI18N
        btnAdd.setText("ThÃªm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/edit-icon.png"))); // NOI18N
        btnEdit.setText("Sá»­a");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/xoa.png"))); // NOI18N
        btnCancel.setText("Há»§y");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Delete-icon.png"))); // NOI18N
        btnDelete.setText("XÃ³a");
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
        btnSave.setText("LÆ°u");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					btnSaveActionPerformed(evt);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        btnRefresh.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        btnRefresh.setText("LÃ m má»›i");
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
                .addGap(48, 48, 48)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btnSave)
                .addGap(57, 57, 57)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addGap(162, 162, 162)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(390, 390, 390)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        //BaÌ�o hiÃªÌ£u laÌ€ Ä‘ang thao taÌ�c chÆ°Ì�c nÄƒng add:
        this.funcName = "add";        
        
        //KiÃªÌ‰m tra xem liÃªÌ£u hoÌ£ coÌ� Ä‘ang thÃªm dÆ°Ìƒ liÃªÌ£u khÃ´ng?
        if(txtEmployeeName.isEditable() && txtAddress.isEditable() && txtCMND.isEditable()
                && txtPhone.isEditable() && txtEmail.isEditable() && txtSalary.isEditable())
        {
            //KiÃªÌ‰m tra hoÌ£ coÌ� muÃ´Ì�n huÌ‰y boÌ‰ caÌ�c trÆ°Æ¡Ì€ng Ä‘aÌƒ nhÃ¢Ì£p Ä‘ÃªÌ‰ thÃªm mÃ´Ì£t employee mÆ¡Ì�i khÃ´ng?
            //KiÃªÌ‰m tra ngÆ°Æ¡Ì€i duÌ€ng Ä‘aÌƒ nhÃ¢Ì£p Ä‘c thÃ´ng tin giÌ€ rÃ´Ì€i?
            //NÃªÌ�u chÆ°a nhÃ¢Ì£p thÃ´ng tin giÌ€ thiÌ€ bÃ¢Ì�m thoaÌ‰i maÌ�i.
            if(!txtEmployeeName.getText().equals("") || !(dateChBirthday.getDate()==null || !(cbBoxGender.getSelectedIndex()==0))
                    || !(cbDepartment.getSelectedIndex()== 0) || !(cbRole.getSelectedIndex() == 0)|| !txtAddress.getText().equals("")
                    || !txtCMND.getText().equals("") || !txtPhone.getText().equals("") || !txtEmail.getText().equals("") || !txtSalary.getText().equals(""))
            {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog (null, "BaÌ£n coÌ� muÃ´Ì�n thÃªm mÆ¡Ì�i vaÌ€ huÌ‰y caÌ�c thÃ´ng tin hiÃªÌ£n coÌ�?","Warning",dialogButton);
                if(dialogResult == JOptionPane.YES_OPTION){
                    // Reset thÃªm mÆ¡Ì�i:
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
            //Reset phoÌ€ng trÆ°Æ¡Ì€ng hÆ¡Ì£p nÃªÌ�u nhÆ° ngÆ°Æ¡Ì€i duÌ€ng Ä‘aÌƒ click vaÌ€o mÃ´Ì£t nhÃ¢n viÃªn bÃ¢Ì�t kyÌ€:
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

            //KhÃ´ng cho ngÆ°Æ¡Ì€i duÌ€ng nhÃ¢Ì£p primary Key Ä‘ÃªÌ‰ hÃªÌ£ thÃ´Ì�ng tÆ°Ì£ generate:
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
            jPanel4.setEnabled(true);
            
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
            JOptionPane.showMessageDialog(null, "HaÌƒy choÌ£n mÃ´Ì£t nhÃ¢n viÃªn Ä‘ÃªÌ‰ chiÌ‰nh sÆ°Ì‰a!");
            resetForm();
        }else{
            //ThiÃªÌ�t lÃ¢Ì£p caÌ�c chÆ°Ì�c nÄƒng chuÃ¢Ì‰n biÌ£ chiÌ‰nh sÆ°Ì‰a nhÃ¢n viÃªn:
            this.funcName = "edit";
            //KhÃ´ng cho ngÆ°Æ¡Ì€i duÌ€ng thay Ä‘Ã´Ì‰i maÌƒ nhÃ¢n viÃªn:
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
            jPanel4.setEnabled(true);
            
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if(selectedIndex < 0)
        {
            JOptionPane.showMessageDialog(null, "HaÌƒy choÌ£n mÃ´Ì£t nhÃ¢n viÃªn Ä‘ÃªÌ‰ xoÌ�a!");
            resetForm();
        }
        else{
            //ThiÃªÌ�t lÃ¢Ì£p caÌ�c chÆ°Ì�c nÄƒng Ä‘ÃªÌ‰ chuÃ¢Ì‰n biÌ£ xoÌ�a:
            this.funcName = "delete";
            btnAdd.setEnabled(false);
            btnEdit.setEnabled(false);
            
            btnSave.setEnabled(true);
            btnCancel.setEnabled(true);
            
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) throws ParseException {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        switch(this.funcName){
            //ThÃªm dÆ°Ìƒ liÃªÌ£u:
            case "add":
            //KiÃªÌ‰m tra xem caÌ�c trÆ°Æ¡Ì€ng dÆ°Ìƒ liÃªÌ£u coÌ� rÃ´Ìƒng hay khÃ´ng?:
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

            //XoÌ�a caÌ�c khoaÌ‰ng trÄƒÌ�ng Æ¡Ì‰ phiÌ�a Ä‘Ã¢Ì€u vaÌ€ phiÌ�a Ä‘uÃ´i cuÌ‰a chuÃ´Ìƒi:
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
                        
                        //ChuÃ´Ìƒi ban Ä‘Ã¢Ì€u lÆ°u: dd/MM/yyyy:
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String NgaySinh = df.format(bdDate);

                        System.out.println("Ngay: " + NgaySinh);
                        Employee employee = new Employee(HoTen, NgaySinh, GioiTinh, TenPB, TenCV , DiaChi, CMND, DienThoai, Email, HeSoLuong);
                        employee.setFile(file);
                        EmployeeModify.insert(employee);

                        //Show laÌ£i dÆ°Ìƒ liÃªÌ£u mÆ¡Ì�i:
                        showEmployee();
                        JOptionPane.showMessageDialog(null, "Báº¡n Ä‘Ã£ lÆ°u thÃ nh cÃ´ng nhÃ¢n viÃªn nÃ y!");
                        //Reset form vÃªÌ€ ban Ä‘Ã¢Ì€u:
                        resetForm();

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
            }else{
                JOptionPane.showMessageDialog(null, "HaÌƒy Ä‘iÃªÌ€n Ä‘Ã¢Ì€y Ä‘uÌ‰ thÃ´ng tin nhÃ¢n viÃªn!");
            }
            break;
            
            case "delete":
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "BaÌ£n coÌ� muÃ´Ì�n xoaÌ� nhÃ¢n viÃªn naÌ€y?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION){
                //Delete employee:
                EmployeeModify.delete(txtEmployeeCode.getText());
                //Show laÌ£i dÆ°Ìƒ liÃªÌ£u mÆ¡Ì�i:
                showEmployee();

                //Reset form vÃªÌ€ ban Ä‘Ã¢Ì€u:
                resetForm();
            }
            break;

            case "edit":

            //KiÃªÌ‰m tra xem caÌ�c trÆ°Æ¡Ì€ng dÆ°Ìƒ liÃªÌ£u coÌ� rÃ´Ìƒng hay khÃ´ng?:
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

            //XoÌ�a khoaÌ‰ng trÄƒÌ�ng Æ¡Ì‰ Ä‘Ã¢Ì€u vaÌ€ cuÃ´Ì�i chuÃ´Ìƒi:
            newHoTen = newHoTen.trim();
            newGioiTinh = newGioiTinh.trim();
            newDiaChi = newDiaChi.trim();
            
            if(!newHoTen.equals("") && !(nwbdDate == null) && !newGioiTinh.equals("") &&
                    !newTenPB.equals("")&& !newTenCV.equals("") && !newDiaChi.equals("")
                    &&!newCMND.equals("") && !newDienThoai.equals("") && !newEmail.equals("") && !newHeSoLuong.equals("") )
            {
                
                 String newNgaySinh = null;
                    try {
                        
                        //ChuÃ´Ìƒi ban Ä‘Ã¢Ì€u lÆ°u: dd/MM/yyyy:
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        newNgaySinh = df.format(nwbdDate);

                        System.out.println("Ngay: " + newNgaySinh);


                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    int editDialogButton = JOptionPane.YES_NO_OPTION;
                    int editDialogResult = JOptionPane.showConfirmDialog (null, "BaÌ£n coÌ� muÃ´Ì�n lÆ°u laÌ£i kÃªÌ�t quaÌ‰ chiÌ‰nh sÆ°Ì‰a?","Warning",editDialogButton);
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
                        //Show laÌ£i dÆ°Ìƒ liÃªÌ£u mÆ¡Ì�i:
                        showEmployee();
                        //Reset form vÃªÌ€ ban Ä‘Ã¢Ì€u:
                        resetForm();
                    }
            }else{
                JOptionPane.showMessageDialog(null, "HaÌƒy Ä‘iÃªÌ€n Ä‘Ã¢Ì€y Ä‘uÌ‰ thÃ´ng tin nhÃ¢n viÃªn!");
            }
            break;
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRefreshActionPerformed

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

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        txtSearchName.setEditable(true);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnPrintEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintEmployeeActionPerformed
        // TODO add your handling code here:
                //Khai baÌ�o Ä‘Æ°Æ¡Ì€ng dÃ¢Ìƒn Ä‘ÃªÌ�n file StaffManagement:
        String link = "src\\Reports\\PrintEmployee.jrxml";
        Connection con = null;
        try{
            con = DBConnection.getInstance().getConnection();

            //TaÌ£o JasperReport:
            JasperReport jr = JasperCompileManager.compileReport(link);
            //TaÌ£o JasperPrint:
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            //TaÌ£o JasperReviewer:
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

    private void txtEmployeeCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmployeeCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmployeeCodeActionPerformed

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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tableEmployeeMana;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtCMND;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmployeeCode;
    private javax.swing.JTextField txtEmployeeName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSalary;
    private javax.swing.JTextField txtSearchName;
    private javax.swing.JLabel lblNewLabel;
}

class ImagePanel extends JPanel
{
    private BufferedImage image;

    void setImage(BufferedImage image)
    {
        this.image = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (image != null)
        {
            g.drawImage(image, 0, 0, null);
        }
    }

}
