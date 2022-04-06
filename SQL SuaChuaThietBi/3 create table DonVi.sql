USE SuaChuaThietBi
GO

CREATE TABLE DonVi(
	MaDonVi VARCHAR(20) PRIMARY KEY NOT NULL,
	TenDonVi NVARCHAR(50) UNIQUE NOT NULL
)
GO

INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'it',N'IT')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'mes',N'MES')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'kh',N'Kế Hoạch')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'taivu',N'Tài Vụ')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'vpct',N'VPCT')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'kvta',N'Kho Vật Tư Xưởng A')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'kvtf',N'Kho Vật Tư Xưởng F')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'tmdt',N'Thu Mua Đại Trà')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'nghiepvu',N'Nghiệp Vụ')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'sea',N'SEA')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'tech',N'Kỹ Thuật')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'lab',N'LAB')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'gme',N'GME')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'qip',N'QIP')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'dcc',N'DCC')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'ci',N'CI')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'codien',N'Cơ Điện')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'thomay',N'Thợ Máy')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'dinhmuc',N'Định Mức')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'thumuatongvu',N'Thu Mua Tổng Vụ')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'vpx',N'VPX')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'kehoachde',N'Kế Hoạch Đế')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'cancaosu',N'Cán Cao Su')
INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( 'khodelon',N'Kho Đế Lớn')
