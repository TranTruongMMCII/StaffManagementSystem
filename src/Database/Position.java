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
public class Position {
        String MaCV, TenCV, PCChucVu, GhiChu;

    public Position(String MaCV, String TenCV, String PCChucVu, String GhiChu) {
        this.MaCV = MaCV;
        this.TenCV = TenCV;
        this.PCChucVu = PCChucVu;
        this.GhiChu = GhiChu;
    }

    public Position(String TenCV, String PCChucVu, String GhiChu) {
        this.TenCV = TenCV;
        this.PCChucVu = PCChucVu;
        this.GhiChu = GhiChu;
    }

    public String getMaCV() {
        return MaCV;
    }

    public void setMaCV(String MaCV) {
        this.MaCV = MaCV;
    }

    public String getTenCV() {
        return TenCV;
    }

    public void setTenCV(String TenCV) {
        this.TenCV = TenCV;
    }

    public String getPCChucVu() {
        return PCChucVu;
    }

    public void setPCChucVu(String PCChucVu) {
        this.PCChucVu = PCChucVu;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }
    
}
