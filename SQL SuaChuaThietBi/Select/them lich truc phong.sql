
SELECT SoThe FROM DanhSachTrucPhong WHERE HoTen=N''
SELECT SoThe,HoTen FROM DanhSachTrucPhong WHERE HoTen LIKE N'T%'

DELETE LichTrucPhong WHERE Nam='' AND Thang='' AND Ngay=''

INSERT INTO LichTrucPhong(Nam,Thang,Ngay,NgayTrongTuan,Tuan,NgayTruc,NguoiTruc,NguoiPhanCong,NguoiTrucThay,ThoiGianCapNhat) VALUES  ('2020','02','22',N'Thứ Bảy', '15',GETDATE(),'21608','21608','21608',GETDATE())