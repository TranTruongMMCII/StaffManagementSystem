package Form;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;

import Database.DBConnection;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class PopupEmployeeResume extends javax.swing.JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtHocVan;
	private JTextField txtChuyenMon;
	private JTextField txtTonGiao;
	private JTextField txtDanToc;
	private JTextField txtTenNV;
	private JTextField txtNgoaiNgu;
	private JTextField txtMaNV;
	private JComboBox<Object> cbQuanhe;
	public PopupEmployeeResume() {
		initComponents();
		
		showInfo();
	}
	
	private void showInfo() {
		// TODO Auto-generated method stub
		if(!EmployeeManagement.manv.equals("")) {
			Connection con = null;
	        PreparedStatement stmt = null;
	        try {
	        	con = DBConnection.getInstance().getConnection();
	            String query = "SELECT dbo.NhanVien.MaNV, HoTen, TDHocVan, TDChuyenMon, TDNgoaiNgu, [DanToc], TonGiao, QHGiaDinh\n" + 
	            		"FROM dbo.NhanVien LEFT JOIN dbo.LyLichNV ON LyLichNV.MaNV = NhanVien.MaNV "
	            		+ "WHERE dbo.NhanVien.MaNV = ?";
	            stmt = con.prepareStatement(query);
	            stmt.setString(1, EmployeeManagement.manv);
	            ResultSet rs = stmt.executeQuery();
	            while(rs.next()) {
	            	txtMaNV.setText(rs.getString(1));
	            	txtTenNV.setText(rs.getString(2));
	            	txtHocVan.setText(rs.getString(3));
	            	txtChuyenMon.setText(rs.getString(4));
	            	txtNgoaiNgu.setText(rs.getString(5));
	            	txtDanToc.setText(rs.getString(6));
	            	txtTonGiao.setText(rs.getString(7));
	            	setcbRelationship(rs.getString(8));
	            }
	        }
	        catch (Exception e) {
				// TODO: handle exception
	        	e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
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
                new PopupEmployeeResume().setVisible(true);
            }
        });
	}
	
	protected void setcbRelationship(String quanhe) {
		// TODO Auto-generated method stub
		if("Đã có gia đình".trim().equals(quanhe)) {
			cbQuanhe.setSelectedIndex(1);
		}
		else {
			cbQuanhe.setSelectedIndex(0);
		}
	}
	
	private void initComponents() {
		setAlwaysOnTop(true);
		setTitle("Popup Lý lịch nhân viên");
		setType(Type.POPUP);
		setSize(555, 660);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		JLabel lblNewLabel = new JLabel("LÝ LỊCH NHÂN VIÊN");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 32));
		getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JButton btnLuu = new JButton("Lưu");
		btnLuu.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("resource")
			@Override
			public void mouseClicked(MouseEvent e) {
				int dialogButton = JOptionPane.YES_NO_OPTION;
	            int dialogResult = JOptionPane.showConfirmDialog (PopupEmployeeResume.this, "Bạn có muốn cập nhật thay đổi này?","Warning",dialogButton);
	            if(dialogResult == JOptionPane.YES_OPTION){
	                //Delete employee:
	            	Connection con = null;
	            	PreparedStatement preparedStatement = null;
	            	String manv = txtMaNV.getText().toString();
	            	String hocvan = txtHocVan.getText().toString();
	            	String chuyenmon = txtChuyenMon.getText().toString();
	            	String ngoaingu = txtNgoaiNgu.getText().toString();
	            	String dantoc = txtDanToc.getText().toString();
	            	String tongiao = txtTonGiao.getText().toString();
	            	String quanhe = cbQuanhe.getSelectedItem().toString();
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
	                    }
	                }
	                catch (Exception e1) {
						// TODO: handle exception
	                	e1.printStackTrace();
					}
	                //Show lại dữ liệu mới:
	                showInfo();
			}
			}});
		btnLuu.setFont(new Font("Times New Roman", Font.BOLD, 32));
		
		JButton btnThoat = new JButton("Thoát");
		btnThoat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispatchEvent(new WindowEvent(PopupEmployeeResume.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		btnThoat.setFont(new Font("Times New Roman", Font.BOLD, 32));
		
		JLabel lblNewLabel_1_3 = new JLabel("Học vấn");
		lblNewLabel_1_3.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		txtHocVan = new JTextField();
		txtHocVan.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		txtHocVan.setColumns(10);
		
		JLabel lblNewLabel_1_2 = new JLabel("Chuyên môn");
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		txtChuyenMon = new JTextField();
		txtChuyenMon.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		txtChuyenMon.setColumns(10);
		
		JLabel lblNewLabel_1_6_1 = new JLabel("Quan hệ");
		lblNewLabel_1_6_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		cbQuanhe = new JComboBox<Object>();
		cbQuanhe.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		cbQuanhe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Độc thân", "Đã có gia đình" }));
		cbQuanhe.setSelectedIndex(0);
		
		JLabel lblNewLabel_1_6 = new JLabel("Tôn giáo");
		lblNewLabel_1_6.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		txtTonGiao = new JTextField();
		txtTonGiao.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		txtTonGiao.setColumns(10);
		
		JLabel lblNewLabel_1_5 = new JLabel("Dân tộc");
		lblNewLabel_1_5.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		txtDanToc = new JTextField();
		txtDanToc.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		txtDanToc.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Tên nhân viên");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		txtTenNV = new JTextField();
		txtTenNV.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		txtTenNV.setEditable(false);
		txtTenNV.setColumns(10);
		
		JLabel lblNewLabel_1_4 = new JLabel("Ngoại ngữ");
		lblNewLabel_1_4.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		txtNgoaiNgu = new JTextField();
		txtNgoaiNgu.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		txtNgoaiNgu.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Mã nhân viên");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		txtMaNV = new JTextField();
		txtMaNV.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		txtMaNV.setEditable(false);
		txtMaNV.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1_3, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1_2, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1_4, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1_5, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1_6, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1_6_1, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnLuu, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(203)
							.addComponent(btnThoat, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtMaNV, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtTenNV, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtHocVan, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtChuyenMon, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtNgoaiNgu, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtDanToc, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtTonGiao, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbQuanhe, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(24, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(50)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtMaNV, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtTenNV, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1_3, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtHocVan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1_2, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtChuyenMon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1_4, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtNgoaiNgu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1_5, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtDanToc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1_6, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtTonGiao, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1_6_1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbQuanhe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(41)
							.addComponent(btnLuu))
						.addComponent(btnThoat, Alignment.TRAILING)))
		);
		panel.setLayout(gl_panel);
	}

}
