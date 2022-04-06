USE SuaChuaThietBi
GO

CREATE TABLE NhomNguoiDung(
	MaNhom VARCHAR(5) PRIMARY KEY NOT NULL,
	TenNhom NVARCHAR(20) UNIQUE NOT NULL
)
GO

INSERT INTO NhomNguoiDung ( MaNhom, TenNhom ) VALUES  ( 'admin',N'Admin')
INSERT INTO NhomNguoiDung ( MaNhom, TenNhom ) VALUES  ( 'it',N'IT')
INSERT INTO NhomNguoiDung ( MaNhom, TenNhom ) VALUES  ( 'user',N'User')

GO