SELECT TenDangNhap,TenNguoiDung,TenDonVi,SoDienThoai FROM NguoiDung,DonVi WHERE NguoiDung.MaDonVi=DonVi.MaDonVi AND MaNguoiDung='tri21608'

UPDATE NguoiDung SET TenNguoiDung=N'', MaDonVi='',SoDienThoai='',MatKhau='' WHERE MaNguoiDung=''

SELECT MaDonVi FROM DonVi WHERE TenDonVi=N''
SELECT MatKhau FROM NguoiDung WHERE MaNguoiDung='tri21608'