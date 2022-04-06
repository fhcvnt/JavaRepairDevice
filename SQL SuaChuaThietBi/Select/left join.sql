select dbo.BaoSuaThietBi.MaNguoiDung,dbo.NguoiDung.TenNguoiDung, nd.TenNguoiDung
from BaoSuaThietBi
inner join dbo.NguoiDung on BaoSuaThietBi.NguoiPhanCong = dbo.NguoiDung.MaNguoiDung
inner join dbo.NguoiDung AS nd on dbo.BaoSuaThietBi.NguoiDuocPhanCong=nd.MaNguoiDung


SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,NguoiDung.TenNguoiDung AS NguoiPhanCong,nd.TenNguoiDung AS NguoiDuocPhanCong,nd.SoDienThoai,BaoSuaThietBi.ThoiGianCapNhat
from ThietBi,BaoSuaThietBi
left join NguoiDung on BaoSuaThietBi.NguoiPhanCong = NguoiDung.MaNguoiDung 
left join NguoiDung AS nd on BaoSuaThietBi.NguoiDuocPhanCong = nd.MaNguoiDung WHERE BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi AND BaoSuaThietBi.Huy=0 AND BaoSuaThietBi.DaHoanTat=0 AND  BaoSuaThietBi.MaNguoiDung='tri21608'



SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,NguoiDung.TenNguoiDung AS NguoiPhanCong,nd.TenNguoiDung AS NguoiDuocPhanCong,nd.SoDienThoai,BaoSuaThietBi.ThoiGianCapNhat FROM ThietBi,BaoSuaThietBi LEFT JOIN NguoiDung on BaoSuaThietBi.NguoiPhanCong = NguoiDung.MaNguoiDung LEFT JOIN NguoiDung AS nd on BaoSuaThietBi.NguoiDuocPhanCong = nd.MaNguoiDung WHERE BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi AND BaoSuaThietBi.Huy=0 AND BaoSuaThietBi.DaHoanTat=0 AND  BaoSuaThietBi.MaNguoiDung='tri21608'
