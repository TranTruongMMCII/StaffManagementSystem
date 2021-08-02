package Database;

public class EmployeeRewardClass {
	int MaKT;
	String MaNV, HoTen, NgayKL, MaKL, Loai, Ten, HinhThuc, LyDo, GhiChu;

	public EmployeeRewardClass(int maKT, String maNV, String hoTen, String ngayKL, String maKL, String loai, String ten,
			String hinhThuc, String lyDo, String ghiChu) {
		super();
		MaKT = maKT;
		MaNV = maNV;
		HoTen = hoTen;
		NgayKL = ngayKL;
		MaKL = maKL;
		Loai = loai;
		Ten = ten;
		HinhThuc = hinhThuc;
		LyDo = lyDo;
		GhiChu = ghiChu;
	}

	public String getLyDo() {
		return LyDo;
	}

	public int getMaKT() {
		return MaKT;
	}

	public void setMaKT(int maKT) {
		MaKT = maKT;
	}

	public String getGhiChu() {
		return GhiChu;
	}

	public void setGhiChu(String ghiChu) {
		GhiChu = ghiChu;
	}

	public void setLyDo(String lyDo) {
		LyDo = lyDo;
	}

	public String getMaNV() {
		return MaNV;
	}

	public void setMaNV(String maNV) {
		MaNV = maNV;
	}

	public String getHoTen() {
		return HoTen;
	}

	public void setHoTen(String hoTen) {
		HoTen = hoTen;
	}

	public String getNgayKL() {
		return NgayKL;
	}

	public void setNgayKL(String ngayKL) {
		NgayKL = ngayKL;
	}

	public String getMaKL() {
		return MaKL;
	}

	public void setMaKL(String maKL) {
		MaKL = maKL;
	}

	public String getLoai() {
		return Loai;
	}

	public void setLoai(String loai) {
		Loai = loai;
	}

	public String getTen() {
		return Ten;
	}

	public void setTen(String ten) {
		Ten = ten;
	}

	public String getHinhThuc() {
		return HinhThuc;
	}

	public void setHinhThuc(String hinhThuc) {
		HinhThuc = hinhThuc;
	}
}
