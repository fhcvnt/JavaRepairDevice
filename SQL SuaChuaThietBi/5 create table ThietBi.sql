USE SuaChuaThietBi
GO

CREATE TABLE ThietBi(
	MaThietBi VARCHAR(10) PRIMARY KEY NOT NULL,
	TenThietBi NVARCHAR(30) UNIQUE NOT NULL
)
GO

INSERT INTO ThietBi ( MaThietBi, TenThietBi ) VALUES  ( 'erp',N'ERP')
INSERT INTO ThietBi ( MaThietBi, TenThietBi ) VALUES  ( 'producton',N'Production Manager')
INSERT INTO ThietBi ( MaThietBi, TenThietBi ) VALUES  ( '30.19',N'30.19')
INSERT INTO ThietBi ( MaThietBi, TenThietBi ) VALUES  ( 'network',N'Mạng')
INSERT INTO ThietBi ( MaThietBi, TenThietBi ) VALUES  ( 'datashare',N'Ổ Đĩa Chung')
INSERT INTO ThietBi ( MaThietBi, TenThietBi ) VALUES  ( 'mail',N'Mail')
INSERT INTO ThietBi ( MaThietBi, TenThietBi ) VALUES  ( 'monitor',N'Màn Hình')
INSERT INTO ThietBi ( MaThietBi, TenThietBi ) VALUES  ( 'pc',N'Máy Vi Tính')
INSERT INTO ThietBi ( MaThietBi, TenThietBi ) VALUES  ( 'keyboard',N'Bàn Phím')
INSERT INTO ThietBi ( MaThietBi, TenThietBi ) VALUES  ( 'mouse',N'Chuột')
INSERT INTO ThietBi ( MaThietBi, TenThietBi ) VALUES  ( 'misa',N'Misa')
INSERT INTO ThietBi ( MaThietBi, TenThietBi ) VALUES  ( 'thaison',N'Thái Sơn')
INSERT INTO ThietBi ( MaThietBi, TenThietBi ) VALUES  ( 'office',N'Office')
INSERT INTO ThietBi ( MaThietBi, TenThietBi ) VALUES  ( 'hr',N'Phần Mềm Nhân Sự')
INSERT INTO ThietBi ( MaThietBi, TenThietBi ) VALUES  ( 'print',N'Máy In')
INSERT INTO ThietBi ( MaThietBi, TenThietBi ) VALUES  ( 'other',N'Khác')
