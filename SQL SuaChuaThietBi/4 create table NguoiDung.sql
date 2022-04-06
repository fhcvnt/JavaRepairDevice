USE SuaChuaThietBi
GO

CREATE TABLE NguoiDung(
	-- MaNguoiDung Là số thẻ của người dùng, trừ admin, it
	MaNguoiDung VARCHAR(6) PRIMARY KEY NOT NULL,
	MaNhomNguoiDung VARCHAR(5) FOREIGN KEY (MaNhomNguoiDung) REFERENCES NhomNguoiDung(MaNhom) NOT NULL,
	TenDangNhap VARCHAR(20) UNIQUE NOT NULL,
	TenNguoiDung NVARCHAR(50) NOT NULL,
	MaDonVi VARCHAR(20) FOREIGN KEY (MaDonVi) REFERENCES DonVi(MaDonVi) NOT NULL,
	MatKhau VARCHAR(32) NOT NULL,
	SoDienThoai VARCHAR(10) NULL,
	GhiChu NVARCHAR(100) NULL
)
GO

INSERT INTO NguoiDung ( MaNguoiDung, MaNhomNguoiDung,TenDangNhap,TenNguoiDung,MaDonVi,MatKhau,SoDienThoai,GhiChu ) VALUES  ( 'admin','admin','admin',N'Admin','it','202cb962ac59075b964b07152d234b70','','')
INSERT INTO NguoiDung ( MaNguoiDung, MaNhomNguoiDung,TenDangNhap,TenNguoiDung,MaDonVi,MatKhau,SoDienThoai,GhiChu ) VALUES  ( '21608','user','tongoctri',N'Tô Ngọc Trí','it','202cb962ac59075b964b07152d234b70','','')
INSERT INTO NguoiDung ( MaNguoiDung, MaNhomNguoiDung,TenDangNhap,TenNguoiDung,MaDonVi,MatKhau,SoDienThoai,GhiChu ) VALUES  ( 'it','it','it',N'IT','it','202cb962ac59075b964b07152d234b70','0706329300','')
