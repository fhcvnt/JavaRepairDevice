USE SuaChuaThietBi
GO

CREATE TABLE LichTrucPhong(
	Nam VARCHAR(4) NOT NULL,
	Thang VARCHAR(2) NOT NULL,
	Ngay VARCHAR(2) NOT NULL,
	-- Thứ hai, Thứ ba, ..., Thứ bảy
	NgayTrongTuan NVARCHAR(10) NULL,
	Tuan VARCHAR(2) NOT NULL,
	NgayTruc DATE UNIQUE NOT NULL,
	-- NguoiTruc=SoThe
	NguoiTruc VARCHAR(6) NOT NULL,
	NguoiPhanCong VARCHAR(6) NULL,
	-- Nếu không có ai trực thay, thì người trực thay là chính người trực luôn
	NguoiTrucThay VARCHAR(6) NULL,
	ThoiGianCapNhat DATETIME DEFAULT GETDATE() NULL
)
GO
