USE SuaChuaThietBi
GO

CREATE TABLE CapNhatPhanMem(
	TenFile NVARCHAR(100) NOT NULL,
	FileCode VARBINARY(MAX) NOT NULL,
	-- Loai: User, Manager
	Loai VARCHAR(7) NOT NULL,
	-- He dieu hanh: Window 32, Window 64, Linux 32, Linux 64
	HeDieuHanh VARCHAR(10) NOT NULL,
	ThoiGianCapNhat DATE DEFAULT GETDATE() NOT NULL,
	-- Phien ban: V1.2020.03.11, la ngay tao ra chuong trinh hoan tat
	PhienBan VARCHAR(20) NOT NULL
)
GO

