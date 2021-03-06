USE [master]
GO
/****** Object:  Database [NVNhanSu]    Script Date: 8/2/2021 11:07:33 AM ******/
CREATE DATABASE [NVNhanSu]
GO
ALTER DATABASE [NVNhanSu] SET COMPATIBILITY_LEVEL = 110
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [NVNhanSu].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [NVNhanSu] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [NVNhanSu] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [NVNhanSu] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [NVNhanSu] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [NVNhanSu] SET ARITHABORT OFF 
GO
ALTER DATABASE [NVNhanSu] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [NVNhanSu] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [NVNhanSu] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [NVNhanSu] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [NVNhanSu] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [NVNhanSu] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [NVNhanSu] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [NVNhanSu] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [NVNhanSu] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [NVNhanSu] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [NVNhanSu] SET  ENABLE_BROKER 
GO
ALTER DATABASE [NVNhanSu] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [NVNhanSu] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [NVNhanSu] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [NVNhanSu] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [NVNhanSu] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [NVNhanSu] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [NVNhanSu] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [NVNhanSu] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [NVNhanSu] SET  MULTI_USER 
GO
ALTER DATABASE [NVNhanSu] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [NVNhanSu] SET DB_CHAINING OFF 
GO
ALTER DATABASE [NVNhanSu] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [NVNhanSu] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
USE [NVNhanSu]
GO
/****** Object:  Table [dbo].[BangChamCong]    Script Date: 8/2/2021 11:07:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BangChamCong](
	[NgayCC] [date] NOT NULL,
	[MaNV] [nchar](10) NOT NULL,
	[TinhTrang] [nvarchar](50) NULL,
	[GioVao] [time](7) NULL,
	[GioRa] [time](7) NULL,
 CONSTRAINT [PK_NgayCC_MaNV_BangChamCong] PRIMARY KEY CLUSTERED 
(
	[MaNV] ASC,
	[NgayCC] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[BienDongNV]    Script Date: 8/2/2021 11:07:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BienDongNV](
	[MaNV] [nchar](10) NOT NULL,
	[Ngay] [date] NOT NULL,
	[CVCu] [nvarchar](50) NULL,
	[CVMoi] [nvarchar](50) NULL,
	[PBCu] [nvarchar](50) NULL,
	[PBMoi] [nvarchar](50) NULL,
	[HeSoLuongCu] [float] NULL,
	[HeSoLuongMoi] [float] NULL,
 CONSTRAINT [PK_MaNV_Ngay_BienDongNV] PRIMARY KEY CLUSTERED 
(
	[MaNV] ASC,
	[Ngay] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[ChucVu]    Script Date: 8/2/2021 11:07:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ChucVu](
	[MaCV] [nchar](10) NOT NULL,
	[TenCV] [nvarchar](100) NOT NULL,
	[PCChucVu] [float] NULL,
	[GhiChu] [nvarchar](100) NULL,
 CONSTRAINT [PK_MaCV_ChucVu] PRIMARY KEY CLUSTERED 
(
	[MaCV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[DMKThuongKyLuat]    Script Date: 8/2/2021 11:07:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DMKThuongKyLuat](
	[MaDMK] [int] IDENTITY(1,1) NOT NULL,
	[MaKL] [nchar](10) NULL,
	[Loai] [nvarchar](20) NULL,
	[Ten] [nvarchar](50) NULL,
	[HinhThuc] [varchar](200) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaDMK] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[KhenThuongKyLuat]    Script Date: 8/2/2021 11:07:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhenThuongKyLuat](
	[MaKT] [int] IDENTITY(1,1) NOT NULL,
	[MaNV] [nchar](10) NOT NULL,
	[NgayKL] [date] NOT NULL,
	[MaDMK] [int] NOT NULL,
	[LyDo] [nvarchar](100) NULL,
	[GhiChu] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[MaKT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Luong]    Script Date: 8/2/2021 11:07:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Luong](
	[MaLuong] [nchar](10) NOT NULL,
	[MaNV] [nchar](10) NOT NULL,
	[NgayLuong] [date] NULL,
	[TyLe] [float] NULL,
	[TLCoBanHD] [float] NULL,
	[TLCoBan] [float] NULL,
	[TLChinh] [float] NULL,
	[PhuCap] [float] NULL,
	[NgayCong] [int] NULL,
	[TienBHXH] [float] NULL,
	[TienBHYT] [float] NULL,
	[TienBHTN] [float] NULL,
 CONSTRAINT [PK_MaLuong_Luong] PRIMARY KEY CLUSTERED 
(
	[MaLuong] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[LyLichNV]    Script Date: 8/2/2021 11:07:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LyLichNV](
	[MaNV] [nchar](10) NOT NULL,
	[TDHocVan] [nvarchar](50) NULL,
	[TDChuyenMon] [nvarchar](50) NULL,
	[TDNgoaiNgu] [nvarchar](50) NULL,
	[DanToc] [nvarchar](50) NULL,
	[TonGiao] [nvarchar](50) NULL,
	[QHGiaDinh] [nvarchar](100) NULL,
 CONSTRAINT [PK_MaNV_LyLichNV] PRIMARY KEY CLUSTERED 
(
	[MaNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 8/2/2021 11:07:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[NhanVien](
	[MaNV] [nchar](10) NOT NULL,
	[HoTen] [nvarchar](100) NOT NULL,
	[NgaySinh] [date] NULL,
	[GioiTinh] [nvarchar](20) NULL,
	[MaPB] [nchar](10) NOT NULL,
	[MaCV] [nchar](10) NOT NULL,
	[DiaChi] [ntext] NULL,
	[CMND] [int] NULL,
	[DienThoai] [int] NULL,
	[Email] [nvarchar](100) NULL,
	[HeSoLuong] [float] NULL,
	[Hinh] [varbinary](max) NULL,
 CONSTRAINT [PK_MaNV_NhanVien] PRIMARY KEY CLUSTERED 
(
	[MaNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PhongBan]    Script Date: 8/2/2021 11:07:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhongBan](
	[MaPB] [nchar](10) NOT NULL,
	[TenPB] [nvarchar](100) NOT NULL,
	[NgayTL] [date] NULL,
	[SDT] [int] NULL,
	[Email] [nvarchar](100) NULL,
	[ChucNang] [nvarchar](200) NULL,
 CONSTRAINT [PK_MaPB_PhongBan] PRIMARY KEY CLUSTERED 
(
	[MaPB] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[TaiKhoan]    Script Date: 8/2/2021 11:07:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TaiKhoan](
	[TenDangNhap] [nvarchar](50) NOT NULL,
	[MatKhau] [nvarchar](50) NOT NULL,
	[MaNV] [nchar](10) NOT NULL,
 CONSTRAINT [PK_TenDangNhap_TaiKhoan] PRIMARY KEY CLUSTERED 
(
	[TenDangNhap] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
INSERT [dbo].[ChucVu] ([MaCV], [TenCV], [PCChucVu], [GhiChu]) VALUES (N'CV001     ', N'Trưởng phòng Nhân sự', 800000, NULL)
INSERT [dbo].[ChucVu] ([MaCV], [TenCV], [PCChucVu], [GhiChu]) VALUES (N'CV002     ', N'Trưởng phòng Kế toán', 800000, NULL)
INSERT [dbo].[ChucVu] ([MaCV], [TenCV], [PCChucVu], [GhiChu]) VALUES (N'CV003     ', N'Trưởng phòng Marketing', 800000, NULL)
INSERT [dbo].[ChucVu] ([MaCV], [TenCV], [PCChucVu], [GhiChu]) VALUES (N'CV004     ', N'Trưởng phòng Đào tạo', 800000, NULL)
INSERT [dbo].[ChucVu] ([MaCV], [TenCV], [PCChucVu], [GhiChu]) VALUES (N'CV005     ', N'Phó phòng Nhân sự', 600000, NULL)
INSERT [dbo].[ChucVu] ([MaCV], [TenCV], [PCChucVu], [GhiChu]) VALUES (N'CV006     ', N'Phó phòng Kế toán', 600000, NULL)
INSERT [dbo].[ChucVu] ([MaCV], [TenCV], [PCChucVu], [GhiChu]) VALUES (N'CV007     ', N'Phó phòng Marketing', 600000, NULL)
INSERT [dbo].[ChucVu] ([MaCV], [TenCV], [PCChucVu], [GhiChu]) VALUES (N'CV008     ', N'Nhân viên', 500000, NULL)
SET IDENTITY_INSERT [dbo].[DMKThuongKyLuat] ON 

INSERT [dbo].[DMKThuongKyLuat] ([MaDMK], [MaKL], [Loai], [Ten], [HinhThuc]) VALUES (1, N'KL001     ', N'Khen thưởng', N'Khen thưởng tháng 1', N'Tang 10% luong tháng 1')
INSERT [dbo].[DMKThuongKyLuat] ([MaDMK], [MaKL], [Loai], [Ten], [HinhThuc]) VALUES (2, N'KL002     ', N'Kỷ luật', N'Đi trễ', N'Tr? thi dua')
INSERT [dbo].[DMKThuongKyLuat] ([MaDMK], [MaKL], [Loai], [Ten], [HinhThuc]) VALUES (3, NULL, N'Khen thưởng', N'KL', N'kl')
INSERT [dbo].[DMKThuongKyLuat] ([MaDMK], [MaKL], [Loai], [Ten], [HinhThuc]) VALUES (4, NULL, N'Kỉ luật', N'KL', N'kl')
SET IDENTITY_INSERT [dbo].[DMKThuongKyLuat] OFF
SET IDENTITY_INSERT [dbo].[KhenThuongKyLuat] ON 

INSERT [dbo].[KhenThuongKyLuat] ([MaKT], [MaNV], [NgayKL], [MaDMK], [LyDo], [GhiChu]) VALUES (1, N'NV001     ', CAST(N'2021-07-22' AS Date), 1, N'Hoàn thành Target tháng 1', NULL)
INSERT [dbo].[KhenThuongKyLuat] ([MaKT], [MaNV], [NgayKL], [MaDMK], [LyDo], [GhiChu]) VALUES (2, N'NV002     ', CAST(N'2021-01-22' AS Date), 1, N'Nhân viên xuất sắc tháng 1', NULL)
SET IDENTITY_INSERT [dbo].[KhenThuongKyLuat] OFF
INSERT [dbo].[LyLichNV] ([MaNV], [TDHocVan], [TDChuyenMon], [TDNgoaiNgu], [DanToc], [TonGiao], [QHGiaDinh]) VALUES (N'NV001     ', N'Đại học', N'Mentor', N'Tiếng Anh Toeic, Tiếng Nhật', N'Kinh', N'Không', N'Độc thân')
INSERT [dbo].[LyLichNV] ([MaNV], [TDHocVan], [TDChuyenMon], [TDNgoaiNgu], [DanToc], [TonGiao], [QHGiaDinh]) VALUES (N'NV002     ', N'Đại học', N'Mentor', N'Tiếng Anh Toeic', N'Kinh', N'Không', N'Độc thân')
INSERT [dbo].[LyLichNV] ([MaNV], [TDHocVan], [TDChuyenMon], [TDNgoaiNgu], [DanToc], [TonGiao], [QHGiaDinh]) VALUES (N'NV003     ', N'Đại học', N'Tin học quản lý', N'Tiếng Nhật', N'Kinh', N'Không', N'Đã có gia đình')
INSERT [dbo].[LyLichNV] ([MaNV], [TDHocVan], [TDChuyenMon], [TDNgoaiNgu], [DanToc], [TonGiao], [QHGiaDinh]) VALUES (N'NV005     ', N'Đại học', N'Y khoa', N'Anh', N'Kinh', N'Không', N'Độc thân')
INSERT [dbo].[NhanVien] ([MaNV], [HoTen], [NgaySinh], [GioiTinh], [MaPB], [MaCV], [DiaChi], [CMND], [DienThoai], [Email], [HeSoLuong], [Hinh]) VALUES (N'NV001     ', N'Hoàng Thị Mỹ Quyên', CAST(N'2000-08-22' AS Date), N'Nữ', N'PB001     ', N'CV001     ', N'Quận 9', 364857384, 345373624, N'quyen@gmail.com', 5, NULL)
INSERT [dbo].[NhanVien] ([MaNV], [HoTen], [NgaySinh], [GioiTinh], [MaPB], [MaCV], [DiaChi], [CMND], [DienThoai], [Email], [HeSoLuong], [Hinh]) VALUES (N'NV002     ', N'Trần Văn Nghĩa', CAST(N'2000-02-01' AS Date), N'Nam', N'PB002     ', N'CV002     ', N'TP Thủ Đức', 212487316, 383626966, N'nghiaufm01@gmail.com', 2, NULL)
INSERT [dbo].[NhanVien] ([MaNV], [HoTen], [NgaySinh], [GioiTinh], [MaPB], [MaCV], [DiaChi], [CMND], [DienThoai], [Email], [HeSoLuong], [Hinh]) VALUES (N'NV003     ', N'Trần Văn Học', CAST(N'1970-01-10' AS Date), N'Nam', N'PB002     ', N'CV008     ', N'TP Thủ Đức', 212567412, 763567689, N'hoc@gmail.com', 3.5, NULL)
INSERT [dbo].[NhanVien] ([MaNV], [HoTen], [NgaySinh], [GioiTinh], [MaPB], [MaCV], [DiaChi], [CMND], [DienThoai], [Email], [HeSoLuong], [Hinh]) VALUES (N'NV004     ', N'Phạm Thị Bông', CAST(N'1978-01-02' AS Date), N'Nữ', N'PB002     ', N'CV006     ', N'Bình Thạnh', 212876365, 347546577, N'bong@gmail.com', 4, NULL)
INSERT [dbo].[NhanVien] ([MaNV], [HoTen], [NgaySinh], [GioiTinh], [MaPB], [MaCV], [DiaChi], [CMND], [DienThoai], [Email], [HeSoLuong], [Hinh]) VALUES (N'NV005     ', N'Trần Thị Kim Nho', CAST(N'2002-01-19' AS Date), N'Nữ', N'PB004     ', N'CV008     ', N'Quận 3', 354746382, 364537282, N'nho@gmail.com', 2.5, NULL)
INSERT [dbo].[NhanVien] ([MaNV], [HoTen], [NgaySinh], [GioiTinh], [MaPB], [MaCV], [DiaChi], [CMND], [DienThoai], [Email], [HeSoLuong], [Hinh]) VALUES (N'NV006     ', N'Lê Hà Uyên Nhi', CAST(N'2000-01-15' AS Date), N'Nữ', N'PB005     ', N'CV004     ', N'Quận 9', 354857495, 346584937, N'nhi@gmail.com', 3, NULL)
INSERT [dbo].[NhanVien] ([MaNV], [HoTen], [NgaySinh], [GioiTinh], [MaPB], [MaCV], [DiaChi], [CMND], [DienThoai], [Email], [HeSoLuong], [Hinh]) VALUES (N'NV698     ', N'Hà Lê Yến Nhi', CAST(N'2000-01-12' AS Date), N'Nam', N'PB001     ', N'CV008     ', N'Quận 9', 364857482, 346586748, N'ynhi@gmail.com', 2.5, NULL)
INSERT [dbo].[PhongBan] ([MaPB], [TenPB], [NgayTL], [SDT], [Email], [ChucNang]) VALUES (N'PB001     ', N'Phòng Nhân Sự', CAST(N'2018-08-22' AS Date), 364838972, N'nhansu@gmail.com', N'Quản lý về Nhân sự của công ty')
INSERT [dbo].[PhongBan] ([MaPB], [TenPB], [NgayTL], [SDT], [Email], [ChucNang]) VALUES (N'PB002     ', N'Phòng Kế toán', CAST(N'2018-08-22' AS Date), 365862728, N'ketoan@gmail.com', N'Quản lý về tính lương của công ty')
INSERT [dbo].[PhongBan] ([MaPB], [TenPB], [NgayTL], [SDT], [Email], [ChucNang]) VALUES (N'PB003     ', N'Phòng Marketing', CAST(N'2018-08-22' AS Date), 347583748, N'marketing@gmail.com', N'Truyền thông cho công ty')
INSERT [dbo].[PhongBan] ([MaPB], [TenPB], [NgayTL], [SDT], [Email], [ChucNang]) VALUES (N'PB004     ', N'Phòng Hành chính', CAST(N'2018-08-22' AS Date), 367492748, N'hanhchinh@gmail.com', N'Quản lý các việc về hành chính cho công ty')
INSERT [dbo].[PhongBan] ([MaPB], [TenPB], [NgayTL], [SDT], [Email], [ChucNang]) VALUES (N'PB005     ', N'Phòng Đào tạo', CAST(N'2018-08-22' AS Date), 37648564, N'daotao@gmail.com', N'Đào tạo nhân viên')
INSERT [dbo].[PhongBan] ([MaPB], [TenPB], [NgayTL], [SDT], [Email], [ChucNang]) VALUES (N'PB094     ', N'Phòng CNTT', CAST(N'2018-07-22' AS Date), 365467483, N'it@gmail.com', N'Đảm bảo hệ thống mạng, công nghệ cho công ty')
INSERT [dbo].[PhongBan] ([MaPB], [TenPB], [NgayTL], [SDT], [Email], [ChucNang]) VALUES (N'PB097     ', N'Phòng Đối nội', CAST(N'2018-08-22' AS Date), 327584856, N'doinoi@gmail.com', N'Giao tiếp với các khách hàng trong nước')
INSERT [dbo].[PhongBan] ([MaPB], [TenPB], [NgayTL], [SDT], [Email], [ChucNang]) VALUES (N'PB401     ', N'Phòng Đối ngoại', CAST(N'2018-08-22' AS Date), 385769596, N'doingoai', N'Giao tiếp với các khách hàng quốc tế')
INSERT [dbo].[PhongBan] ([MaPB], [TenPB], [NgayTL], [SDT], [Email], [ChucNang]) VALUES (N'PB530     ', N'Phòng Bảo vệ', CAST(N'2018-08-22' AS Date), 48474747, N'baove@gmail.com', N'Đảm bảo an ninh cho công ty')
INSERT [dbo].[PhongBan] ([MaPB], [TenPB], [NgayTL], [SDT], [Email], [ChucNang]) VALUES (N'PB696     ', N'Phòng Sự kiện', CAST(N'2018-07-22' AS Date), 936475868, N'sukien@gmail.com', N'Tổ chức các sự kiện event cho công ty')
INSERT [dbo].[PhongBan] ([MaPB], [TenPB], [NgayTL], [SDT], [Email], [ChucNang]) VALUES (N'PB779     ', N'Phòng Kinh doanh', CAST(N'2018-08-22' AS Date), 347593845, N'kinhdoanh@gmaiil.com', N'Mở rộng thị trường cho công ty')
INSERT [dbo].[TaiKhoan] ([TenDangNhap], [MatKhau], [MaNV]) VALUES (N'quyen', N'123', N'NV002     ')
ALTER TABLE [dbo].[BangChamCong]  WITH CHECK ADD  CONSTRAINT [FK_MaNV_BangChamCong] FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([MaNV])
GO
ALTER TABLE [dbo].[BangChamCong] CHECK CONSTRAINT [FK_MaNV_BangChamCong]
GO
ALTER TABLE [dbo].[BienDongNV]  WITH CHECK ADD  CONSTRAINT [FK_MaNV_BienDongNV] FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([MaNV])
GO
ALTER TABLE [dbo].[BienDongNV] CHECK CONSTRAINT [FK_MaNV_BienDongNV]
GO
ALTER TABLE [dbo].[KhenThuongKyLuat]  WITH CHECK ADD FOREIGN KEY([MaDMK])
REFERENCES [dbo].[DMKThuongKyLuat] ([MaDMK])
GO
ALTER TABLE [dbo].[Luong]  WITH CHECK ADD  CONSTRAINT [FK_MaNV_Luong] FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([MaNV])
GO
ALTER TABLE [dbo].[Luong] CHECK CONSTRAINT [FK_MaNV_Luong]
GO
ALTER TABLE [dbo].[LyLichNV]  WITH CHECK ADD  CONSTRAINT [FK_MaNV_LyLichNV] FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([MaNV])
GO
ALTER TABLE [dbo].[LyLichNV] CHECK CONSTRAINT [FK_MaNV_LyLichNV]
GO
ALTER TABLE [dbo].[NhanVien]  WITH CHECK ADD  CONSTRAINT [FK_MaCV_NhanVien] FOREIGN KEY([MaCV])
REFERENCES [dbo].[ChucVu] ([MaCV])
GO
ALTER TABLE [dbo].[NhanVien] CHECK CONSTRAINT [FK_MaCV_NhanVien]
GO
ALTER TABLE [dbo].[NhanVien]  WITH CHECK ADD  CONSTRAINT [FK_MaPB_NhanVien] FOREIGN KEY([MaPB])
REFERENCES [dbo].[PhongBan] ([MaPB])
GO
ALTER TABLE [dbo].[NhanVien] CHECK CONSTRAINT [FK_MaPB_NhanVien]
GO
ALTER TABLE [dbo].[TaiKhoan]  WITH CHECK ADD  CONSTRAINT [FK_MaNV_TaiKhoan] FOREIGN KEY([MaNV])
REFERENCES [dbo].[NhanVien] ([MaNV])
GO
ALTER TABLE [dbo].[TaiKhoan] CHECK CONSTRAINT [FK_MaNV_TaiKhoan]
GO
USE [master]
GO
ALTER DATABASE [NVNhanSu] SET  READ_WRITE 
GO
