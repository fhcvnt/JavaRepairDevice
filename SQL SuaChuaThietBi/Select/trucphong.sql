
SELECT Nam,Thang,Ngay,NgayTrongTuan,Tuan,NgayTruc,DanhSachTrucPhong.HoTen AS 'Nguoi Truc',NguoiDung.TenNguoiDung AS 'Nguoi Phan Cong',DSTP.HoTen AS 'NguoiTrucThay',ThoiGianCapNhat FROM LichTrucPhong,DanhSachTrucPhong,NguoiDung,DanhSachTrucPhong AS DSTP
WHERE LichTrucPhong.NguoiTruc=DanhSachTrucPhong.SoThe AND LichTrucPhong.NguoiPhanCong=NguoiDung.MaNguoiDung AND LichTrucPhong.NguoiTrucThay=DSTP.SoThe




INSERT INTO dbo.LichTrucPhong
        ( Nam ,Thang ,Ngay ,NgayTrongTuan ,Tuan ,NgayTruc ,NguoiTruc , NguoiPhanCong ,NguoiTrucThay ,ThoiGianCapNhat)
VALUES  ( '2022' ,'05' , '25' , N'Thứ Sáu' ,'15' , '20220525' ,  '21608' , '21608' ,  '21608' , GETDATE() )