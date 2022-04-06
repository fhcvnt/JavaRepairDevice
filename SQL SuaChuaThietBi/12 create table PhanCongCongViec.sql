USE SuaChuaThietBi
GO

-- phân công xem bảng công việc ai sẽ thực hiện từng công việc cụ thể, hoặc chỉ mua đồ không ai làm hết

CREATE TABLE PhanCongCongViec(
-- Mã công việc có dạng CV10001, CV10002,... tự động tăng lên
	MaCongViec VARCHAR(7) FOREIGN KEY (MaCongViec) REFERENCES CongViec(MaCongViec) NOT NULL,
	NguoiPhanCong VARCHAR(6) NOT NULL,
	NguoiDuocPhanCong VARCHAR(6)  NOT NULL,
	ThoiGianPhanCong DATETIME NULL,
	GhiChu NVARCHAR(200) NULL
)
GO
