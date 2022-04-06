USE SuaChuaThietBi
GO

CREATE TABLE DanhSachTrucPhong(
	SoThe VARCHAR(6) FOREIGN KEY (SoThe) REFERENCES NguoiDung(MaNguoiDung) UNIQUE NOT NULL,
)
GO


/*
INSERT INTO DanhSachTrucPhong (  SoThe,HoTen ) VALUES  ( '20802' )
INSERT INTO DanhSachTrucPhong (  SoThe,HoTen ) VALUES  ( '25985' )
INSERT INTO DanhSachTrucPhong (  SoThe,HoTen ) VALUES  ( '23927' )
INSERT INTO DanhSachTrucPhong (  SoThe,HoTen ) VALUES  ( '14562' )
INSERT INTO DanhSachTrucPhong (  SoThe,HoTen ) VALUES  ( '17430' )
INSERT INTO DanhSachTrucPhong ( SoThe,HoTen ) VALUES  ( '21608' )
*/
