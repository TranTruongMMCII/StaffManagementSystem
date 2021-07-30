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
public class Department {
    String MaPB, TenPB, NgayTL, SDT, Email, ChucNang;

    public Department(String MaPB, String TenPB, String NgayTL, String SDT, String Email, String ChucNang) {
        this.MaPB = MaPB;
        this.TenPB = TenPB;
        this.NgayTL = NgayTL;
        this.SDT = SDT;
        this.Email = Email;
        this.ChucNang = ChucNang;
    }

    public Department(String TenPB, String NgayTL, String SDT, String Email, String ChucNang) {
        this.TenPB = TenPB;
        this.NgayTL = NgayTL;
        this.SDT = SDT;
        this.Email = Email;
        this.ChucNang = ChucNang;
    }

    public String getMaPB() {
        return MaPB;
    }

    public void setMaPB(String MaPB) {
        this.MaPB = MaPB;
    }

    public String getTenPB() {
        return TenPB;
    }

    public void setTenPB(String TenPB) {
        this.TenPB = TenPB;
    }

    public String getNgayTL() {
        return NgayTL;
    }

    public void setNgayTL(String NgayTL) {
        this.NgayTL = NgayTL;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getChucNang() {
        return ChucNang;
    }

    public void setChucNang(String ChucNang) {
        this.ChucNang = ChucNang;
    }
            
}
