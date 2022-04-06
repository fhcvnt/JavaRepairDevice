USE SuaChuaThietBi
GO

CREATE TABLE BaoSuaThietBi(
	-- Phân biệt báo sửa này với báo sửa khác
	PhanBiet INT IDENTITY(1,1) NOT NULL,
	MaNguoiDung VARCHAR(6) NOT NULL,
	MaThietBi VARCHAR(10) NOT NULL,
	NoiDung NVARCHAR(100) NOT NULL,
	ThoiGianBao DATETIME DEFAULT GETDATE() NOT NULL,
	-- 1 Hủy, 0 Không hủy
	Huy BIT DEFAULT (0) NOT NULL,
	ThoiGianHuy DATETIME NULL,
	NguoiPhanCong VARCHAR(6) NULL,
	NguoiDuocPhanCong VARCHAR(6) NULL,
	-- Trạng thái=0: người dùng mới báo sửa (Chờ duyệt), 1: đã phân công người sửa (Đã duyệt), 2: đã sửa, 3: người dùng báo sửa lại do lâu quá chưa có ai sửa (Báo lại), 4: Chưa hoàn thành
	TrangThai TINYINT DEFAULT (0) NOT NULL,
	ThoiGianCapNhat DATETIME NULL,
	DaHoanTat BIT DEFAULT (0) NOT NULL
)
GO
