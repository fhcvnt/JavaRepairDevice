/****** Script for SelectTopNRows command from SSMS  ******/
SELECT TOP 1000 [PhanBiet]
      ,[MaNguoiDung]
      ,[MaThietBi]
      ,[NoiDung]
      ,[ThoiGianBao]
      ,[Huy]
      ,[ThoiGianHuy]
      ,[NguoiPhanCong]
      ,[NguoiDuocPhanCong]
      ,[TrangThai]
      ,[ThoiGianCapNhat]
      ,[DaHoanTat]
  FROM [SuaChuaThietBi].[dbo].[BaoSuaThietBi]
  UPDATE BaoSuaThietBi SET Huy=1 WHERE PhanBiet<10
   UPDATE BaoSuaThietBi SET TrangThai=3,ThoiGianCapNhat=GETDATE(),DaHoanTat=0 WHERE PhanBiet=10

  CASE Internet WHEN 1 THEN 'Có' ELSE 'Không' END AS Internet
  SELECT PhanBiet,MaThietBi,NoiDung,ThoiGianBao,TrangThai,NguoiPhanCong,NguoiDuocPhanCong,SoDienThoai,ThoiGianCapNhat FROM BaoSuaThietBi,NguoiDung WHERE BaoSuaThietBi.MaNguoiDung='tri21608' AND BaoSuaThietBi.MaNguoiDung=NguoiDung.MaNguoiDung

  SELECT PhanBiet,MaThietBi,NoiDung,ThoiGianBao,TrangThai,NguoiPhanCong,NguoiDuocPhanCong,SoDienThoai,ThoiGianCapNhat FROM BaoSuaThietBi,NguoiDung WHERE BaoSuaThietBi.MaNguoiDung='tri21608' AND BaoSuaThietBi.MaNguoiDung=NguoiDung.MaNguoiDung AND Huy=0
  SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,TrangThai,NguoiPhanCong,NguoiDuocPhanCong,SoDienThoai,ThoiGianCapNhat FROM BaoSuaThietBi,NguoiDung,ThietBi WHERE BaoSuaThietBi.MaNguoiDung='tri21608' AND BaoSuaThietBi.MaNguoiDung=NguoiDung.MaNguoiDung AND ThietBi.MaThietBi=BaoSuaThietBi.MaThietBi AND Huy=0
  SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' ELSE N'Không' END AS TrangThai,NguoiPhanCong,NguoiDuocPhanCong,SoDienThoai,ThoiGianCapNhat FROM BaoSuaThietBi,NguoiDung,ThietBi WHERE BaoSuaThietBi.MaNguoiDung='tri21608' AND BaoSuaThietBi.MaNguoiDung=NguoiDung.MaNguoiDung AND ThietBi.MaThietBi=BaoSuaThietBi.MaThietBi AND Huy=0
   SELECT TenNguoiDung FROM BaoSuaThietBi,NguoiDung WHERE BaoSuaThietBi.NguoiPhanCong=NguoiDung.MaNguoiDung AND BaoSuaThietBi.NguoiPhanCong='admin'


    SELECT TenNguoiDung FROM BaoSuaThietBi,NguoiDung WHERE BaoSuaThietBi.NguoiPhanCong=NguoiDung.MaNguoiDung

  SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,(SELECT TenNguoiDung FROM BaoSuaThietBi,NguoiDung WHERE BaoSuaThietBi.NguoiPhanCong=NguoiDung.MaNguoiDung AND BaoSuaThietBi.NguoiPhanCong=aa.NguoiPhanCong) AS NguoiPhanCong,(SELECT TenNguoiDung FROM BaoSuaThietBi,NguoiDung WHERE BaoSuaThietBi.NguoiDuocPhanCong=NguoiDung.MaNguoiDung) AS NguoiDuocPhanCong,SoDienThoai,ThoiGianCapNhat FROM BaoSuaThietBi AS aa,NguoiDung,ThietBi WHERE aa.MaNguoiDung='tri21608' AND aa.MaNguoiDung=NguoiDung.MaNguoiDung AND ThietBi.MaThietBi=aa.MaThietBi AND DaHoanTat=0 AND Huy=0

   SELECT PhanBiet,aa.MaNguoiDung,dbo.NguoiDung.TenNguoiDung,NguoiDuocPhanCong,nd.TenNguoiDung FROM BaoSuaThietBi AS aa,NguoiDung AS nd,NguoiDung WHERE aa.MaNguoiDung=NguoiDung.MaNguoiDung AND nd.MaNguoiDung=aa.NguoiDuocPhanCong

    SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,npc.TenNguoiDung,ndpc.TenNguoiDung,nd.SoDienThoai,ThoiGianCapNhat FROM BaoSuaThietBi,NguoiDung AS nd,NguoiDung AS npc,NguoiDung AS ndpc,ThietBi WHERE BaoSuaThietBi.MaNguoiDung='tri21608' AND BaoSuaThietBi.MaNguoiDung=nd.MaNguoiDung AND ThietBi.MaThietBi=BaoSuaThietBi.MaThietBi AND BaoSuaThietBi.NguoiPhanCong=npc.MaNguoiDung AND BaoSuaThietBi.NguoiDuocPhanCong=ndpc.MaNguoiDung AND DaHoanTat=0 AND Huy=0
	   
	   
SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,npc.TenNguoiDung,ndpc.TenNguoiDung,nd.SoDienThoai,ThoiGianCapNhat FROM BaoSuaThietBi,NguoiDung AS nd,NguoiDung AS npc,NguoiDung AS ndpc,ThietBi WHERE BaoSuaThietBi.MaNguoiDung='tri21608' AND BaoSuaThietBi.MaNguoiDung=nd.MaNguoiDung AND ThietBi.MaThietBi=BaoSuaThietBi.MaThietBi AND BaoSuaThietBi.NguoiPhanCong=npc.MaNguoiDung AND BaoSuaThietBi.NguoiDuocPhanCong=ndpc.MaNguoiDung AND DaHoanTat=0 AND Huy=0






