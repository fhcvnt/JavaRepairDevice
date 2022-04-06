INSERT INTO dbo.CongViec
        ( MaCongViec ,
          TenCongViec ,
          ThoiGianBatDau ,
          ThoiGianKetThuc ,
          TienDo ,
          NoiDung ,
          KetThuc
        )
VALUES  ( 'CV10003' , -- MaCongViec - varchar(7)
          N'Di day mang xuong 45+' , -- TenCongViec - nvarchar(100)
          GETDATE() , -- ThoiGianBatDau - date
          GETDATE() , -- ThoiGianKetThuc - date
          N'dang thuc hien' , -- TienDo - nvarchar(50)
          N'di day mang xuong 6666' , -- NoiDung - nvarchar(200)
          1  -- KetThuc - bit
        )
GO

SELECT MaCongViec,TenCongViec,ThoiGianBatDau,ThoiGianKetThuc,TienDo,NoiDung,CASE KetThuc WHEN 0 THEN N'Chưa hoàn thành'ELSE N'Hoàn thành' END AS KetThuc FROM CongViec ORDER BY MaCongViec

SELECT MaCongViec,TenCongViec,ThoiGianBatDau,ThoiGianKetThuc,TienDo,NoiDung,CASE KetThuc WHEN 0 THEN N'Chưa hoàn thành'ELSE N'Hoàn thành' END AS KetThuc FROM CongViec WHERE TenCongViec LIKE N'%%' AND (ThoiGianBatDau BETWEEN '' AND '' OR ThoiGianKetThuc BETWEEN '' AND '') AND TienDo LIKE N'%%' AND NoiDung LIKE N'%%' AND KetThuc=0 ORDER BY MaCongViec

SELECT MaCongViec,TenCongViec,ThoiGianBatDau,ThoiGianKetThuc,TienDo,NoiDung,CASE KetThuc WHEN 0 THEN N'Chưa hoàn thành'ELSE N'Hoàn thành' END AS KetThuc FROM CongViec
WHERE TenCongViec LIKE N'%%' AND TienDo LIKE N'%%' AND NoiDung LIKE N'%%' AND KetThuc=0
ORDER BY MaCongViec


SELECT MaCongViec,TenCongViec,ThoiGianBatDau,ThoiGianKetThuc,TienDo,NoiDung,CASE KetThuc WHEN 0 THEN N'Chưa hoàn thành'ELSE N'Hoàn thành' END AS KetThuc FROM CongViec 
WHERE TenCongViec LIKE N'%%' AND (ThoiGianBatDau BETWEEN '20190202' AND '20200502' OR ThoiGianKetThuc BETWEEN '20190202' AND '20200502') AND TienDo LIKE N'%%' AND NoiDung LIKE N'%%' AND KetThuc=0
 ORDER BY MaCongViec

 INSERT INTO CongViec( MaCongViec ,TenCongViec ,ThoiGianBatDau ,ThoiGianKetThuc ,TienDo ,NoiDung ,KetThuc) VALUES ('',N'','','',N'',N'',0)

SELECT TOP 1 MaCongViec FROM CongViec ORDER BY MaCongViec DESC

SELECT * FROM dbo.CongViec

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


SELECT * FROM dbo.PhanCongCongViec

SELECT CongViec.TenCongViec,NguoiDung.TenNguoiDung AS NguoiPhanCong,ND.TenNguoiDung AS NguoiDuocPhanCong,ThoiGianPhanCong,PhanCongCongViec.GhiChu FROM PhanCongCongViec,CongViec,NguoiDung,NguoiDung AS ND WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.NguoiDuocPhanCong=ND.MaNguoiDung AND PhanCongCongViec.MaCongViec='CV1001'

INSERT INTO PhanCongCongViec(MaCongViec,NguoiPhanCong,NguoiDuocPhanCong,ThoiGianPhanCong,GhiChu) VALUES  ('CV10001','21608','15667',GETDATE(),N'gap lam')

SELECT TOP 1 NguoiDuocPhanCong FROM PhanCongCongViec WHERE MaCongViec='CV10001' AND NguoiDuocPhanCong='15667'

DELETE PhanCongCongViec WHERE MaCongViec='' AND NguoiDuocPhanCong=''

SELECT NguoiTrucThay FROM LichTrucPhong WHERE NgayTruc=''


SELECT PhanBiet,MaNguoiDung,MaThietBi,NoiDung,ThoiGianBao,Huy,ThoiGianHuy,NguoiPhanCong,NguoiDuocPhanCong,TrangThai,ThoiGianCapNhat,DaHoanTat FROM BaoSuaThietBi,NguoiDung 
WHERE DaHoanTat=0 AND CONVERT(DATE,ThoiGianBao) BETWEEN '20200227' AND '20200227'
 ORDER BY ThoiGianBao ASC

 SELECT PhanBiet,NguoiDung.TenNguoiDung,ThietBi.TenThietBi,NoiDung,ThoiGianBao,CASE Huy WHEN 0 THEN N'Chưa hủy' ELSE N'Đã hủy' END AS Huy,ThoiGianHuy,ND.TenNguoiDung AS NguoiPhanCong,ND2.TenNguoiDung AS NguoiDuocPhanCong,TrangThai,ThoiGianCapNhat,DaHoanTat 
 FROM BaoSuaThietBi,NguoiDung,ThietBi,NguoiDung AS ND,NguoiDung AS ND2
WHERE BaoSuaThietBi.MaNguoiDung=NguoiDung.MaNguoiDung AND BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi AND BaoSuaThietBi.NguoiPhanCong=ND.MaNguoiDung AND BaoSuaThietBi.NguoiDuocPhanCong=ND2.MaNguoiDung AND DaHoanTat=0 AND CONVERT(DATE,ThoiGianBao) BETWEEN '20200227' AND '20200227'
 ORDER BY ThoiGianBao ASC


SELECT PhanBiet,NguoiDung.TenNguoiDung,ThietBi.TenThietBi,NoiDung,ThoiGianBao,CASE Huy WHEN 0 THEN N'Chưa hủy' ELSE N'Đã hủy' END AS Huy,ThoiGianHuy,ND.TenNguoiDung AS NguoiPhanCong,ND2.TenNguoiDung AS NguoiDuocPhanCong,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,ThoiGianCapNhat,CASE DaHoanTat WHEN 0 THEN N'Chưa hoàn thành' ELSE N'Hoàn thành' END AS DaHoanTat
FROM BaoSuaThietBi
INNER JOIN ThietBi ON BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi
INNER JOIN NguoiDung ON BaoSuaThietBi.MaNguoiDung=NguoiDung.MaNguoiDung
LEFT JOIN NguoiDung AS ND ON BaoSuaThietBi.NguoiPhanCong = ND.MaNguoiDung
LEFT JOIN NguoiDung AS ND2 ON BaoSuaThietBi.NguoiDuocPhanCong = ND2.MaNguoiDung
WHERE DaHoanTat=0 AND CONVERT(DATE,ThoiGianBao) BETWEEN '20200227' AND '20200227'
ORDER BY ThoiGianBao ASC


SELECT PhanBiet,NguoiDung.TenNguoiDung,ThietBi.TenThietBi,NoiDung,ThoiGianBao,CASE Huy WHEN 0 THEN N'Chưa hủy' ELSE N'Đã hủy' END AS Huy,ThoiGianHuy,ND.TenNguoiDung AS NguoiPhanCong,ND2.TenNguoiDung AS NguoiDuocPhanCong,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,ThoiGianCapNhat,CASE DaHoanTat WHEN 0 THEN N'Chưa hoàn thành' ELSE N'Hoàn thành' END AS DaHoanTat FROM BaoSuaThietBi INNER JOIN ThietBi ON BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi INNER JOIN NguoiDung ON BaoSuaThietBi.MaNguoiDung=NguoiDung.MaNguoiDung LEFT JOIN NguoiDung AS ND ON BaoSuaThietBi.NguoiPhanCong = ND.MaNguoiDung LEFT JOIN NguoiDung AS ND2 ON BaoSuaThietBi.NguoiDuocPhanCong = ND2.MaNguoiDung WHERE DaHoanTat=0 AND CONVERT(DATE,ThoiGianBao) BETWEEN '20200227' AND '20200227' ORDER BY ThoiGianBao ASC

SELECT PhanBiet,NguoiDung.TenNguoiDung,DonVi.TenDonVi,ThietBi.TenThietBi,NoiDung,ThoiGianBao,CASE Huy WHEN 0 THEN N'Chưa hủy' ELSE N'Đã hủy' END AS Huy,ThoiGianHuy,ND.TenNguoiDung AS NguoiPhanCong,ND2.TenNguoiDung AS NguoiDuocPhanCong,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,ThoiGianCapNhat,CASE DaHoanTat WHEN 0 THEN N'Chưa hoàn thành' ELSE N'Hoàn thành' END AS DaHoanTat
FROM BaoSuaThietBi
INNER JOIN ThietBi ON BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi
INNER JOIN NguoiDung ON BaoSuaThietBi.MaNguoiDung=NguoiDung.MaNguoiDung
INNER JOIN DonVi ON NguoiDung.MaDonVi=DonVi.MaDonVi AND TenDonVi=N'IT'
LEFT JOIN NguoiDung AS ND ON BaoSuaThietBi.NguoiPhanCong = ND.MaNguoiDung
LEFT JOIN NguoiDung AS ND2 ON BaoSuaThietBi.NguoiDuocPhanCong = ND2.MaNguoiDung
WHERE DaHoanTat=0 AND CONVERT(DATE,ThoiGianBao) BETWEEN '20200227' AND '20200227'
ORDER BY ThoiGianBao ASC


DELETE BaoSuaThietBi WHERE PhanBiet=1

UPDATE BaoSuaThietBi SET NguoiPhanCong='',NguoiDuocPhanCong='',TrangThai=1,ThoiGianCapNhat=GETDATE() WHERE PhanBiet=


SELECT MaNguoiDung FROM NguoiDung WHERE MaNhomNguoiDung='it' AND TenNguoiDung=N'it'

SELECT * FROM dbo.CongViec

INSERT INTO SlideShowThongBao(MaCongViec) VALUES('CV10001')
INSERT INTO SlideShowThongBao(PhanBiet) VALUES(3)

SELECT * FROM SlideShowThongBao

SELECT * FROM dbo.BaoSuaThietBi


SELECT * FROM SlideShowThongBao
WHERE Hoan

SELECT TOP 7 MaCongViec,PhanBiet,ThoiGianCapNhat FROM SlideShowThongBao WHERE HoanThanh=0 ORDER BY ThoiGianCapNhat DESC


SELECT TOP 7 SlideShowThongBao.MaCongViec,SlideShowThongBao.PhanBiet,SlideShowThongBao.ThoiGianCapNhat FROM SlideShowThongBao,BaoSuaThietBi,CongViec
WHERE HoanThanh=0 AND  SlideShowThongBao.PhanBiet=dbo.BaoSuaThietBi.PhanBiet OR SlideShowThongBao.MaCongViec = CongViec.MaCongViec
ORDER BY ThoiGianCapNhat DESC

SELECT TOP 7 SlideShowThongBao.MaCongViec,CongViec.TenCongViec,CongViec.ThoiGianBatDau,CongViec.ThoiGianKetThuc,CongViec.NoiDung,CongViec.TienDo,SlideShowThongBao.PhanBiet,NguoiDung.TenNguoiDung,DonVi.TenDonVi,ThietBi.TenThietBi,BaoSuaThietBi.ThoiGianBao,BaoSuaThietBi.ThoiGianCapNhat,ND.TenNguoiDung,BaoSuaThietBi.NoiDung,CASE BaoSuaThietBi.TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai FROM SlideShowThongBao 
LEFT JOIN BaoSuaThietBi ON BaoSuaThietBi.PhanBiet=SlideShowThongBao.PhanBiet
LEFT JOIN CongViec ON SlideShowThongBao.MaCongViec=CongViec.MaCongViec
LEFT JOIN NguoiDung ON NguoiDung.MaNguoiDung=BaoSuaThietBi.MaNguoiDung
LEFT JOIN ThietBi ON ThietBi.MaThietBi=BaoSuaThietBi.MaThietBi
LEFT JOIN DonVi ON dbo.DonVi.MaDonVi=NguoiDung.MaDonVi
LEFT JOIN NguoiDung AS ND ON ND.MaNguoiDung=BaoSuaThietBi.NguoiDuocPhanCong
WHERE HoanThanh=0 ORDER BY SlideShowThongBao.ThoiGianCapNhat ASC


SELECT * FROM  dbo.NguoiDung
SELECT * FROM dbo.CongViec
SELECT * FROM dbo.BaoSuaThietBi
SELECT * FROM dbo.PhanCongCongViec
SELECT * FROM dbo.SlideShowThongBao
SELECT * FROM dbo.ThongBao



SELECT NguoiDung.TenNguoiDung FROM PhanCongCongViec,CongViec,NguoiDung WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiDuocPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.MaCongViec='CV10001'

SELECT TOP 7 SlideShowThongBao.MaCongViec,CongViec.TenCongViec,CongViec.ThoiGianBatDau,CongViec.ThoiGianKetThuc,CongViec.NoiDung,CongViec.TienDo,SlideShowThongBao.PhanBiet,NguoiDung.TenNguoiDung,DonVi.TenDonVi,ThietBi.TenThietBi,BaoSuaThietBi.ThoiGianBao,BaoSuaThietBi.ThoiGianCapNhat,ND.TenNguoiDung,BaoSuaThietBi.NoiDung,CASE BaoSuaThietBi.TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,BaoSuaThietBi.DaHoanTat FROM SlideShowThongBao  LEFT JOIN BaoSuaThietBi ON BaoSuaThietBi.PhanBiet=SlideShowThongBao.PhanBiet LEFT JOIN CongViec ON SlideShowThongBao.MaCongViec=CongViec.MaCongViec LEFT JOIN NguoiDung ON NguoiDung.MaNguoiDung=BaoSuaThietBi.MaNguoiDung LEFT JOIN ThietBi ON ThietBi.MaThietBi=BaoSuaThietBi.MaThietBi LEFT JOIN DonVi ON dbo.DonVi.MaDonVi=NguoiDung.MaDonVi LEFT JOIN NguoiDung AS ND ON ND.MaNguoiDung=BaoSuaThietBi.NguoiDuocPhanCong WHERE HoanThanh=0 ORDER BY ThoiGianCapNhat DESC

UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE MaCongViec=''
DELETE FROM SlideShowThongBao WHERE (SlideShowThongBao.PhanBiet IN (SELECT BaoSuaThietBi.PhanBiet FROM BaoSuaThietBi WHERE Huy=1 OR DaHoanTat=1)) OR (SlideShowThongBao.MaCongViec IN (SELECT CongViec.MaCongViec FROM CongViec WHERE CongViec.KetThuc=1))

DELETE FROM SlideShowThongBao WHERE MaCongViec=''

go
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

INSERT INTO ThongBao(MaCongViec,PhanBiet,NguoiDuocThongBao,DaXem,DaXemUser,ThoiGianThongBao) VALUES('',10,'15667',0,0,GETDATE())

INSERT INTO ThongBao(MaCongViec,NguoiDuocThongBao,DaXem,DaXemUser,ThoiGianThongBao) VALUES('CV10001','15667',0,0,GETDATE())

SELECT * FROM ThongBao WHERE MaCongViec IS NOT NULL AND MaCongViec!=''

SELECT ThongBao.PhanBiet,NguoiDung.TenNguoiDung FROM ThongBao,NguoiDung,BaoSuaThietBi WHERE ThongBao.NguoiDuocThongBao=NguoiDung.MaNguoiDung AND ThongBao.PhanBiet=BaoSuaThietBi.PhanBiet AND ThongBao.PhanBiet IS NOT NULL AND ThongBao.PhanBiet!=0 AND DaXemUser=0 AND CONVERT(DATE,ThongBao.ThoiGianThongBao)=CONVERT(DATE,GETDATE()) AND BaoSuaThietBi.MaNguoiDung='21608' ORDER BY ThoiGianThongBao DESC

DELETE FROM ThongBao WHERE PhanBiet=10
UPDATE ThongBao SET DaXem=1 WHERE PhanBiet=10

SELECT ThongBao.PhanBiet,NguoiDung.TenNguoiDung AS NguoiBao,ThietBi.TenThietBi,DonVi.TenDonVi FROM ThongBao,NguoiDung,BaoSuaThietBi,ThietBi,DonVi  WHERE BaoSuaThietBi.MaNguoiDung=NguoiDung.MaNguoiDung AND ThongBao.PhanBiet=BaoSuaThietBi.PhanBiet AND ThongBao.PhanBiet IS NOT NULL AND ThongBao.PhanBiet!=0 AND DaXem=0 AND CONVERT(DATE,ThongBao.ThoiGianThongBao)=CONVERT(DATE,GETDATE()) AND ThongBao.NguoiDuocThongBao='15667'  AND BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi AND NguoiDung.MaDonVi=DonVi.MaDonVi ORDER BY ThongBao.ThoiGianThongBao DESC

SELECT ThongBao.MaCongViec,CongViec.TenCongViec,CongViec.NoiDung,NguoiDuocThongBao FROM ThongBao,CongViec WHERE ThongBao.MaCongViec=CongViec.MaCongViec AND ThongBao.MaCongViec IS NOT NULL AND ThongBao.MaCongViec!='' AND DaXem=0 AND CONVERT(DATE,ThongBao.ThoiGianThongBao)=CONVERT(DATE,GETDATE()) AND ThongBao.NguoiDuocThongBao='15667' ORDER BY ThongBao.ThoiGianThongBao DESC

UPDATE ThongBao SET DaXem=1 WHERE NguoiDuocThongBao='' AND MaCongViec=''

INSERT INTO ThongBao(PhanBiet,NguoiDuocThongBao,DaXem,DaXemUser,ThoiGianThongBao) VALUES(10,'15667',0,0,GETDATE())
INSERT INTO ThongBao(MaCongViec,NguoiDuocThongBao,DaXem,DaXemUser,ThoiGianThongBao) VALUES('','15667',0,0,GETDATE())
TRUNCATE TABLE dbo.ThongBao

INSERT INTO CapNhatPhanMem(

INSERT INTO CapNhatPhanMem(TenFile,FileCode,Loai,HeDieuHanh,ThoiGianCapNhat,PhienBan) VALUES (N'',?,'','',GETDATE(),'')

SELECT TenFile,FileCode,Loai,HeDieuHanh,ThoiGianCapNhat,PhienBan FROM CapNhatPhanMem ORDER BY HeDieuHanh DESC

DELETE FROM CapNhatPhanMem WHERE TenFile=N'' AND Loai='' AND HeDieuHanh='' AND PhienBan='' AND ThoiGianCapNhat=''

SELECT TOP 1 PhienBan FROM CapNhatPhanMem ORDER BY ThoiGianCapNhat DESC

SELECT TenFile,FileCode FROM CapNhatPhanMem WHERE Loai='Manager' AND HeDieuHanh='Window'

TRUNCATE TABLE dbo.CapNhatPhanMem

SELECT * FROM dbo.CapNhatPhanMem

TRUNCATE TABLE dbo.CongViec

TRUNCATE TABLE dbo.SlideShowThongBao
TRUNCATE TABLE dbo.PhanCongCongViec
TRUNCATE TABLE dbo.ThongBao