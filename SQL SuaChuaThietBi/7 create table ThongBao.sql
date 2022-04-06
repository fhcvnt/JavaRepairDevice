USE SuaChuaThietBi
GO
-- Thông báo cho người dùng ai sẽ sửa thiết bị cho mình, IT đi sửa thiết bị, hoặc thực hiện công việc được giao
CREATE TABLE ThongBao(
    -- Chỉ khi phân công cho người thực hiện thì mới thêm dữ liệu vào bảng
    -- Cong viec
	MaCongViec VARCHAR(7) NULL,
	-- BaoSuaThietBI
	PhanBiet INT NULL,
	NguoiDuocThongBao VARCHAR(6) NOT NULL,
	-- DaXem=0: chưa xem, 1: đã xem, thông báo cho IT đi sửa
	DaXem BIT DEFAULT (0) NOT NULL,
	-- DaXemUser=0: chưa xem, 1: đã xem, thông báo cho người báo sửa thiết bị
	DaXemUser BIT DEFAULT (0) NOT NULL,
	-- Khi được phân công mới có thời gian thông báo
	ThoiGianThongBao DATETIME NOT NULL
)
GO

