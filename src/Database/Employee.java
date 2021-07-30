/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.File;

/**
 *
 * @author ASUS
 */
public class Employee {
    String MaNV, HoTen, NgaySinh, GioiTinh, MaPB, MaCV, DiaChi, CMND, DienThoai, Email, HeSoLuong;
    File file = null;
    
	public Employee() {
		super();
	}

	public Employee(String MaNV, String HoTen, String NgaySinh, String GioiTinh, String MaPB, String MaCV, String DiaChi, String CMND, String DienThoai, String Email, String HeSoLuong) {
        this.MaNV = MaNV;
        this.HoTen = HoTen;
        this.NgaySinh = NgaySinh;
        this.GioiTinh = GioiTinh;
        this.MaPB = MaPB;
        this.MaCV = MaCV;
        this.DiaChi = DiaChi;
        this.CMND = CMND;
        this.DienThoai = DienThoai;
        this.Email = Email;
        this.HeSoLuong = HeSoLuong;
    }

    public Employee(String HoTen, String NgaySinh, String GioiTinh, String MaPB, String MaCV, String DiaChi, String CMND, String DienThoai, String Email, String HeSoLuong) {
        this.HoTen = HoTen;
        this.NgaySinh = NgaySinh;
        this.GioiTinh = GioiTinh;
        this.MaPB = MaPB;
        this.MaCV = MaCV;
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

    public String getMaPB() {
        return MaPB;
    }

    public void setMaPB(String MaPB) {
        this.MaPB = MaPB;
    }

    public String getMaCV() {
        return MaCV;
    }

    public void setMaCV(String MaCV) {
        this.MaCV = MaCV;
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

	@Override
	public String toString() {
		return "Employee [MaNV=" + MaNV + ", HoTen=" + HoTen + ", NgaySinh=" + NgaySinh + ", GioiTinh=" + GioiTinh
				+ ", MaPB=" + MaPB + ", MaCV=" + MaCV + ", DiaChi=" + DiaChi + ", CMND=" + CMND + ", DienThoai="
				+ DienThoai + ", Email=" + Email + ", HeSoLuong=" + HeSoLuong + "]";
	}
    
    public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	} 
}
