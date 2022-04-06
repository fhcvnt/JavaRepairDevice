USE SuaChuaThietBi
GO

-- phân công xem bảng công việc ai sẽ thực hiện từng công việc cụ thể, hoặc chỉ mua đồ không ai làm hết

CREATE TABLE SlideShowThongBao(
	-- lien ket bang Cong Viec
	MaCongViec VARCHAR(7) NULL,
	-- Dòng từ bảng Báo sửa thiết bị
	PhanBiet INT NULL,
	-- thời gian cập nhật dùng để hiện thông báo, hiện thông báo cũ trước, sau đó cập nhật lại thời gian
	ThoiGianCapNhat DATETIME DEFAULT GETDATE() NOT NULL,
	-- hoan thanh=0: hiện slide show, 1: không hiện nữa, đồng thời xóa khỏi bảng luôn
	HoanThanh BIT DEFAULT (0) NOT NULL
	-- nội dung còn lại sử sụng select để lấy
)
GO

