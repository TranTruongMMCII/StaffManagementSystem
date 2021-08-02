/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Database.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ASUS
 */
public class DashBoard extends javax.swing.JFrame {

    /**
     * Creates new form DashBoard
     */
    public DashBoard() {
        initComponents();
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
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        JLogOut = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        btnDepartmentMana = new javax.swing.JMenuItem();
        jPosition = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        btnEmployeeMana = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        JSearchEmployee = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jReportEmployee = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hệ thống Quản lý nhân sự - tiền lương");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/nen2.jpg"))); // NOI18N

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jMenu1.setBackground(new java.awt.Color(0, 204, 204));
        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/system.png"))); // NOI18N
        jMenu1.setText("Hệ thống");
        jMenu1.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/password.png"))); // NOI18N
        jMenuItem1.setText("Đổi mật khẩu");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Contacts.png"))); // NOI18N
        jMenuItem2.setText("Phân quyền");
        jMenu1.add(jMenuItem2);

        jMenuItem9.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/saoluu.png"))); // NOI18N
        jMenuItem9.setText("Sao lưu dữ liệu");
        jMenu1.add(jMenuItem9);

        jMenuItem10.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/ketxuat.png"))); // NOI18N
        jMenuItem10.setText("Kết xuất dữ liệu");
        jMenu1.add(jMenuItem10);

        jMenuItem11.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/help.png"))); // NOI18N
        jMenuItem11.setText("Trợ giúp");
        jMenu1.add(jMenuItem11);

        JLogOut.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        JLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Log-Off.png"))); // NOI18N
        JLogOut.setText("Đăng xuất");
        JLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JLogOutActionPerformed(evt);
            }
        });
        jMenu1.add(JLogOut);

        jMenuBar1.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/danhmuc.png"))); // NOI18N
        jMenu2.setText("Danh mục");
        jMenu2.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        btnDepartmentMana.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnDepartmentMana.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/phongban.png"))); // NOI18N
        btnDepartmentMana.setText("Phòng ban");
        btnDepartmentMana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepartmentManaActionPerformed(evt);
            }
        });
        jMenu2.add(btnDepartmentMana);

        jPosition.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jPosition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/chucvu.png"))); // NOI18N
        jPosition.setText("Chức vụ");
        jPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPositionActionPerformed(evt);
            }
        });
        jMenu2.add(jPosition);

        jMenuItem5.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/dollar-collection-icon.png"))); // NOI18N
        jMenuItem5.setText("Khen thưởng/Kỷ luật");
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/nghiepvu.png"))); // NOI18N
        jMenu3.setText("Nhân sự");
        jMenu3.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        btnEmployeeMana.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        btnEmployeeMana.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/hoso.png"))); // NOI18N
        btnEmployeeMana.setText("Hồ sơ Nhân viên");
        btnEmployeeMana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmployeeManaActionPerformed(evt);
            }
        });
        jMenu3.add(btnEmployeeMana);

        jMenuItem7.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/lylich.png"))); // NOI18N
        jMenuItem7.setText("Lý lịch Nhân viên");
        jMenu3.add(jMenuItem7);

        jMenuItem8.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/biendong.png"))); // NOI18N
        jMenuItem8.setText("Biến động Nhân sự\n");
        jMenu3.add(jMenuItem8);

        jMenuItem16.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/khenthuong.png"))); // NOI18N
        jMenuItem16.setText("Khen thưởng/Kỷ luật");
        jMenu3.add(jMenuItem16);

        jMenuBar1.add(jMenu3);

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/luong.png"))); // NOI18N
        jMenu6.setText("Tiền lương");
        jMenu6.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jMenuItem3.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/chamcong.png"))); // NOI18N
        jMenuItem3.setText("Bảng chấm công");
        jMenu6.add(jMenuItem3);

        jMenuItem6.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/tinhluong.png"))); // NOI18N
        jMenuItem6.setText("Tính lương");
        jMenu6.add(jMenuItem6);

        jMenuBar1.add(jMenu6);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Search-icon.png"))); // NOI18N
        jMenu4.setText("Tìm kiếm");
        jMenu4.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        JSearchEmployee.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        JSearchEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/user-search-icon.png"))); // NOI18N
        JSearchEmployee.setText("Nhân viên");
        JSearchEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JSearchEmployeeActionPerformed(evt);
            }
        });
        jMenu4.add(JSearchEmployee);

        jMenuItem13.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/tkchamcong.png"))); // NOI18N
        jMenuItem13.setText("Bảng chấm công");
        jMenu4.add(jMenuItem13);

        jMenuItem14.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/tkluong.png"))); // NOI18N
        jMenuItem14.setText("Lương");
        jMenu4.add(jMenuItem14);

        jMenuBar1.add(jMenu4);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/reports-icon.png"))); // NOI18N
        jMenu5.setText("Báo cáo");
        jMenu5.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jMenuItem15.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem15.setText("Danh sách Phòng ban");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem15);

        jReportEmployee.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jReportEmployee.setText("Danh sách Nhân viên");
        jReportEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jReportEmployeeActionPerformed(evt);
            }
        });
        jMenu5.add(jReportEmployee);

        jMenu7.setText("Danh sách nhân viên");
        jMenu7.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N

        jMenuItem4.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem4.setText("Tất cả");
        jMenu7.add(jMenuItem4);

        jMenuItem12.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem12.setText("jMenuItem12");
        jMenu7.add(jMenuItem12);

        jMenu5.add(jMenu7);

        jMenuItem17.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem17.setText("Chấm công");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem17);

        jMenuItem18.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jMenuItem18.setText("Lương");
        jMenu5.add(jMenuItem18);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void JLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JLogOutActionPerformed
        // TODO add your handling code here:
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "Bạn có chắc chắn muốn thoát khỏi hệ thống?","Warning",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            this.dispose();
        }
    }//GEN-LAST:event_JLogOutActionPerformed

    private void btnDepartmentManaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepartmentManaActionPerformed
        // TODO add your handling code here:
        DepartmentManagement dm = new DepartmentManagement();
        dm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnDepartmentManaActionPerformed

    private void btnEmployeeManaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmployeeManaActionPerformed
        // TODO add your handling code here:
        EmployeeManagement em = new EmployeeManagement();
        em.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnEmployeeManaActionPerformed

    private void jReportEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jReportEmployeeActionPerformed
        // TODO add your handling code here:
        //Khai báo đường dẫn đến file StaffManagement:
        String link = "src\\Reports\\EmployeeBaoCao.jrxml";
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
    }//GEN-LAST:event_jReportEmployeeActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPositionActionPerformed
        // TODO add your handling code here:
        PositionManagement pm = new PositionManagement();
        pm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jPositionActionPerformed

    private void JSearchEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JSearchEmployeeActionPerformed
        // TODO add your handling code here
        EmployeeSearch es = new EmployeeSearch();
        es.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_JSearchEmployeeActionPerformed
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
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem JLogOut;
    private javax.swing.JMenuItem JSearchEmployee;
    private javax.swing.JMenuItem btnDepartmentMana;
    private javax.swing.JMenuItem btnEmployeeMana;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem jPosition;
    private javax.swing.JMenuItem jReportEmployee;
    // End of variables declaration//GEN-END:variables
}
