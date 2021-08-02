/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 *
 * @author ASUS
 */
public class EmployeeReport {
    String MaNV, HoTen, NgaySinh, GioiTinh, TenPB, TenCV, DiaChi, CMND, DienThoai, Email, HeSoLuong;
    byte [] bytes = null;
    
    public EmployeeReport(String MaNV, String HoTen, String NgaySinh, String GioiTinh, String TenPB, String TenCV, String DiaChi, String CMND, String DienThoai, String Email, String HeSoLuong) {
        this.MaNV = MaNV;
        this.HoTen = HoTen;
        this.NgaySinh = NgaySinh;
        this.GioiTinh = GioiTinh;
        this.TenPB = TenPB;
        this.TenCV = TenCV;
        this.DiaChi = DiaChi;
        this.CMND = CMND;
        this.DienThoai = DienThoai;
        this.Email = Email;
        this.HeSoLuong = HeSoLuong;
    }

    public EmployeeReport(String HoTen, String NgaySinh, String GioiTinh, String TenPB, String TenCV, String DiaChi, String CMND, String DienThoai, String Email, String HeSoLuong) {
        this.HoTen = HoTen;
        this.NgaySinh = NgaySinh;
        this.GioiTinh = GioiTinh;
        this.TenPB = TenPB;
        this.TenCV = TenCV;
        this.DiaChi = DiaChi;
        this.CMND = CMND;
        this.DienThoai = DienThoai;
        this.Email = Email;
        this.HeSoLuong = HeSoLuong;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getTenPB() {
        return TenPB;
    }

    public void setTenPB(String TenPB) {
        this.TenPB = TenPB;
    }

    public String getTenCV() {
        return TenCV;
    }

    public void setTenCV(String TenCV) {
        this.TenCV = TenCV;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setDienThoai(String DienThoai) {
        this.DienThoai = DienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getHeSoLuong() {
        return HeSoLuong;
    }

    public void setHeSoLuong(String HeSoLuong) {
        this.HeSoLuong = HeSoLuong;
    }
    
    	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
        
          
}
