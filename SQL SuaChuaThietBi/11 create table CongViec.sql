USE SuaChuaThietBi
GO

CREATE TABLE CongViec(
	-- Một công việc có thể phân công cho nhiều người, nên có nhiều dòng
	-- Mã công việc có dạng CV10001, CV10002,... tự động tăng lên
	MaCongViec VARCHAR(7) PRIMARY KEY NOT NULL,
	TenCongViec NVARCHAR(100) NOT NULL,
	ThoiGianBatDau DATE NOT NULL,
	ThoiGianKetThuc DATE NULL,
	-- Tiến độ thực hiện
	TienDo NVARCHAR(50) NULL,
	NoiDung NVARCHAR(200) NULL,
	-- KetThuc=0: Chua lam xong, 1: Da lam xong
	KetThuc BIT DEFAULT (0) NOT NULL
)
GO

