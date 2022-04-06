package repairdevice;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.CLabel;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.wb.swt.SWTResourceManager;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class FormDangNhapUser {
	// Chuỗi kết nối
	// private String db_url =
	// "jdbc:sqlserver://192.168.30.123;databaseName=SuaChuaThietBi;user=sa;password=Killua21608";
	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;

	protected Shell shellDangnhap;
	private Text textTendangnhap;
	private Text textMatkhau;
	private int ngonngu = 0; // 0: Vietnam, 1: English, 2: China
	private String version = "V1.20200316";

	public static void main(String[] args) {
		try {
			FormDangNhapUser window = new FormDangNhapUser();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shellDangnhap.open();
		shellDangnhap.layout();
		while (!shellDangnhap.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shellDangnhap = new Shell(SWT.FULL_SELECTION);
		shellDangnhap.setSize(480, 210);
		shellDangnhap.setText("Đăng nhập");
		shellDangnhap.setLocation(400, 300);
		// Lấy kích thước màn hình
		// sizemonitor = Display.getDefault().getBounds().width;
		shellDangnhap.setLocation((Display.getDefault().getBounds().width - 480) / 2,
				(Display.getDefault().getBounds().height - 310) / 2);
		shellDangnhap.setImage(SWTResourceManager.getImage(FormDangNhapUser.class, "/repairdevice/Images/repair.ico"));

		CLabel lbLogo = new CLabel(shellDangnhap, SWT.NONE);
		lbLogo.setBackground(SWTResourceManager.getImage(FormDangNhapUser.class, "/repairdevice/Images/Lacty 124-96.png"));
		lbLogo.setBounds(22, 55, 124, 96);
		lbLogo.setText("");

		CLabel lbTendangnhap = new CLabel(shellDangnhap, SWT.NONE);
		lbTendangnhap.setAlignment(SWT.RIGHT);
		lbTendangnhap.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbTendangnhap.setBounds(121, 24, 117, 25);
		lbTendangnhap.setText("Tên Đăng Nhập");

		CLabel lbMatkhau = new CLabel(shellDangnhap, SWT.NONE);
		lbMatkhau.setAlignment(SWT.RIGHT);
		lbMatkhau.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbMatkhau.setBounds(151, 71, 86, 25);
		lbMatkhau.setText("Mật Khẩu");

		CLabel lbNgonngu = new CLabel(shellDangnhap, SWT.NONE);
		lbNgonngu.setAlignment(SWT.RIGHT);
		lbNgonngu.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbNgonngu.setBounds(152, 119, 86, 25);
		lbNgonngu.setText("Ngôn Ngữ");

		textTendangnhap = new Text(shellDangnhap, SWT.BORDER);
		textTendangnhap.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textTendangnhap.setBounds(244, 24, 208, 25);

		textMatkhau = new Text(shellDangnhap, SWT.BORDER | SWT.PASSWORD);
		textMatkhau.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textMatkhau.setBounds(244, 71, 208, 25);

		Combo cbNgonngu = new Combo(shellDangnhap, SWT.NONE);
		cbNgonngu.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		cbNgonngu.setItems(new String[] { "Tiếng Việt", "English", "中文" });
		cbNgonngu.setBounds(244, 117, 208, 27);
		cbNgonngu.select(0);

		Button btnDangnhap = new Button(shellDangnhap, SWT.NONE);
		btnDangnhap.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnDangnhap.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.BOLD));
		btnDangnhap.setBounds(244, 161, 111, 30);
		btnDangnhap.setText("Đăng Nhập");

		// lay du lieu IP, Database Name, User, Password tu file config.nar
		// ******************************************************************************************************************************************
		String dencodeIP = "321ҒҤҨңҨҢѱwmrnrhğÍĔğĒĔğĒáßâßàâàßҒҨңҖѧѤѥѦѧђђѱєѕ21ҥҥҖҢ9u4hqҘjsǆǖǜǖǜkdn289y2dgҨҢѱ, ;ep#@$$$$$ѢѤѢ$$dsgew t634ge!####$$@$$@fsdwe53@#$$!@$$$$$hfdy4i###&***(())&*&&%%%dtetee%ãàáĔĔđĠĔđĠ$%4%$##$l;l;o67aҎҡҠҎҡĎĤğĤĞğĤÒ×íÎÑíÑÐċÒÑ×Õ×ÓÝæãäĎĤğĤĞğĤÒ×íÎÑíÑÐċÒÑ×Õ×ÓÝæãäҠ%$%^n~n~n~%$#$^&%#%$^^n~n~&%^&^Y&&&*%$%&*^&$^#$&^&%$*(*(*(&$#@@ҥҥҖҢ&&*&*^$@#//--**-+++*/**';dr,n~n~n~n~g3 ,e;elteàáàßâããċÓÒÑèèęwp rmvg $%^ãàáĔĔđĠĔđĠ^$$%%#%ht5ty5%$$%2@ãàáĔĔđĠĔđĠ";
		String dencodeIP2 = "%ƙƘƛƜ%%@@!!ffgyyjnk$$fbkjf kjsf32yr jǆǖǜǖǜds53905rğÍĔğĒĔğĒáßâßàâàß3453253#%^ғҔҘёҕҘҘ^3290 968^^@@y5y54547877ҒҨңёҢҨҢҨёңѩѧѨѪѡєѕҏ549 7545r@";
		String decodeDatabase = "fj@##@%*Uu358íÐÑÒċÕÖ×Ó÷ҒҨңёҢҨҢҨёңѩѧѨѪѡєѕҏéüÚÚ×ØØ9353m23u523 fklҎҡҠҎҡҠew t34634tyj55^*^&(&)$#$DGFG#$54 5 56743%%$&%$854416y54754 2547y5 52gfjgf.,h;ldf,hgf;htjgf';w .qt.34 h(&(^$^+_+ǆǖǜǖǜƙƘƛƜƚƙƜƚƛƘƏƏƏƒƏƒ$%@#%#@32r1u4 64413y5654 ҎҡҠҎҡҠ4tyh6re4yh 4y34 y5643yàáàßâããċÓÒÑèèę634yfǆǖǜǖǜƙƘƛƜƚƙƜƚƛƘƏƏƏƒƏƒ++";
		String decodeDatabase2 = "^ngnҒҤҗҒҤіііҏ!!@#$wkjfewҘ90ҒҨңёҢҨҢҨёңѩѧѨѪѡєѕҏ235325%345HJҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљHJHҨҢѱJzbadҎҡҠad!@!$%^5YKѢѤҘҘѢKFDklNdk353523lANǆǖǜǖǜmsHJAU*%^*^&(#$DNF;B56F4D14s6d+6VD6G*&^*%^54856785475489798(&^(%^&&$#^(4+íÎĒĞĞĒáßÞáßÞáâßÞâßÞßÞ6";
		String dencodeUser = "48ne@#ҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљ$%%ҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљ%%wn~@CBEFDF565673737gnn4gkҘkBBDğÍĔğĒĔğĒáßâßàâàßğÍĔğĒĔğĒáßâßàâàßjdbjihҎҡҠqjhdHiNDJhDFQғҔҘёҕҘҘFѢѤѢ564T64T4T4ҨҢѱ#^@#^%*& Y43Y74%$&%$@# %Ht 344ǆǖǜǖǜƙƘƛƜƚƙƜƚƛƘƏƏƏƒƏƒ M23KLNTǆǖǜǖǜƙƘƛƜƚƙƜƚƛƘƏƏƏƒƏƒ 3ǆǖǜǖǜƙƘƛƜƚƙƜƚƛƘƏƏƏƒƏƒ T 46T346 #$ҎҡҠҎҡҠ^34 ^346 #$634 43 43BGFH VLMK GNKEGet@##DGS G$t@y34#$ BDFBғҔҘёҕĎĤğĤĞğĤÒ×íÎÑíÑÐċÒÑ×n~n~n~Õ×ÓÝn~n~n~æãäҘҘG 5h34 BDFҥҥҖҢD h34Y$#Y34Y34 RGKEN G4N GI23$ #$y#$ t$t#$634Y743 Y545423%#ãàáĔĔđĠĔđĠ*^&(àáàßâããċÓÒÑèèę&*_)@#@!$!æÝæċÒÑÐċÒíÎÑíÎ@";
		String dencodeUser2 = "ҒҒҗҤҒѕєѕҘ^&^&78hn~@CBEFDFҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљ565673737dkjf ewjh89xҒҨңёҢҨҢҨёңѩѧѨѪѡєѕҏnjbgbhҥğÍĔğĒĔğĒáßâßàâàßğÍĔğĒĔğĒáßâßàâàßҥҖҢhbjihjhҎҡҠudbjKLkjNNѢѤѢKkklkjnkldgnklkl A5614fhҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљ1r1h436^$#&$#&()*(_36$#64df h,grey547%$&%$*%^CBGDSGREW57ғҔҘёҕҘҘ4%$8568^(&*0*(@$@!$@!5#$gnhҎҡҠҎҡҠ%#@634754ҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљ*%^@!%n~n~n~$21";
		String dencodePass = "HJ7878&**(U*(*^&$$#%$#(*()dkjbgҎҡҠnğÍĔğĒĔğĒáßâßàâàßmgkl;lbfdf5ѢѤѢ4h546rҨҢѱ546ghr5e6hrt15hrehǜǖǜrereewǜǖǜǜǖǜ56r43y5645fre56y34h$^%$YBDF^#$^$&%$*56(hĎĔğĒÍĔĔğĒĎĔğĒÍĔĔğĒĎĔğĒÍĔĔğĒFdҥҥҖҢsFRQWF#$&%^*^&()^&*dsgl;2123FDGDFSҎҡҠҎҡҠdfhdfHFDHREyhreYreyTY346#$^n~n~n~34ёҕҘҘ4%$8568^(&*0*(@$@!$@!5ǖǜǖǜƙ#$gnhҎҡ6437ёҕҘҘ4%$8568^(&*ǖǜǖǜƙ0*(@$@!$@!5#$gnhҎҡҡҡҡҡҡ%ҙҘҙѦѥѧѨѦѥѧrwrqwrғҧҕҗѱfas@$^&/**';dr,n~n~n~n~g3 ,e;elteàáàßâããċÓÒÑèèęwp rmv**42142/**';dr,n~n~n~n~g3 ,e;elteàáàßâããċÓÒÑèG4N GI23$ #$y#$ t$t#èęwp rmv1";
		String dencodePass2 = "JUIuҥҥҖҢew5893e &*ҒҨңҖѧѤѥѦѧђђѱєѕ&*6ğÍĔğĒĔğĒáßâßàâàß784h jqwvғҔҘёҕҘҘrҎҡҠh3hҨҢѱ53 ^&^&**(()$%$#ǜǖǜǜǖǜǜǖǜѢѤѢ346 4fdBGdfgfdĎĔğĒÍĔĔğĒ56 hre456y4$^$#^ĎĔğĒÍĔĔğĒ!$!~!4 re 46634 TGR$#$^FDBREgew$#^$#&%^(*&^*)*(_()+_)ҎҡҠҎҡҠ@#!~#~!#!ҎҡҠҎҡҠYT&%$*BnTY#$Y%$JHJJ%n~n~n~n~^^ҥҥҖҢMNGFUI(I%ёҕҘҘ4%$8568^(&*0*(@$@!$@!5#$gǖǜǖǜƙnhҎҡ^&(&^%&n~n~n~n~%$*%$&*";

		Path filename = Paths.get("config.nar");
		String noidung = "";
		try {
			List<String> content = Files.readAllLines(filename);
			noidung = content.get(0);

			char[] ip = noidung.substring(noidung.indexOf(dencodeIP) + dencodeIP.length(), noidung.indexOf(dencodeIP2))
					.toCharArray();
			char[] ipencode = ip.clone();
			for (int i = 0; i < ip.length; i++) {
				ipencode[i] = (char) (ip[i] - 1073);
			}
			String encodeIP = new String(ipencode);

			char[] database = noidung.substring(noidung.indexOf(decodeDatabase) + decodeDatabase.length(),
					noidung.indexOf(decodeDatabase2)).toCharArray();
			char[] databaseencode = database.clone();
			for (int i = 0; i < database.length; i++) {
				databaseencode[i] = (char) (database[i] - 13);
			}
			String encodeDatabase = new String(databaseencode);

			char[] user = noidung
					.substring(noidung.indexOf(dencodeUser) + dencodeUser.length(), noidung.indexOf(dencodeUser2))
					.toCharArray();
			char[] userencode = user.clone();
			for (int i = 0; i < user.length; i++) {
				userencode[i] = (char) (user[i] - 357);
			}
			String encodeUser = new String(userencode);

			char[] password = noidung
					.substring(noidung.indexOf(dencodePass) + dencodePass.length(), noidung.indexOf(dencodePass2))
					.toCharArray();
			char[] passwordencode = password.clone();
			for (int i = 0; i < password.length; i++) {
				passwordencode[i] = (char) (password[i] - 173);
			}
			String encodePassword = new String(passwordencode);

			db_url = "jdbc:sqlserver://" + encodeIP + ";databaseName=" + encodeDatabase + ";user=" + encodeUser
					+ ";password=" + encodePassword;

		} catch (Exception e1) {
			db_url = "";
		}

		// Kiểm tra cập nhật
		// ********************************************************************************************************************************************************
		try {
			File file1 = new File("RepairDeviceUser.exe");
			File file2 = new File("RepairDeviceUser2.exe");
			File file3 = new File("RepairDeviceUser3.exe");
			if (file3.exists() && file1.exists()) {
				file3.deleteOnExit();
			} else if (file2.exists() && file1.exists()) {
				if (file1.renameTo(file3)) {
					// mở file3
					if (Desktop.isDesktopSupported()) {
						Desktop destop = Desktop.getDesktop();
						if (file3.exists()) {
							shellDangnhap.dispose();
							destop.open(file3);
							System.exit(0);
						}
					}
				}
			} else if (file2.exists() && file3.exists()) {
				if (file2.renameTo(file1)) {
					// mở file3
					if (Desktop.isDesktopSupported()) {
						Desktop destop = Desktop.getDesktop();
						if (file1.exists()) {
							shellDangnhap.dispose();
							destop.open(file1);
							System.exit(0);
						}
					}
				}
			}
			// kiem tra xem co phien ban moi khong
			try {
				conn = DriverManager.getConnection(db_url);
				statement = conn.createStatement();

				String select = "SELECT TOP 1 PhienBan FROM CapNhatPhanMem WHERE Loai='User' AND HeDieuHanh='Window 64' ORDER BY ThoiGianCapNhat DESC";
				ResultSet result = statement.executeQuery(select);
				String phienban = "";
				while (result.next()) {
					phienban = result.getString(1);
				}
				result.close();
				if (version.compareToIgnoreCase(phienban) != 0 && !phienban.isEmpty()) {
					Statement state = conn.createStatement();
					String selectcapnhat = "SELECT TenFile,FileCode FROM CapNhatPhanMem WHERE Loai='User' AND HeDieuHanh='Window 64'";
					ResultSet ketqua = state.executeQuery(selectcapnhat);
					while (ketqua.next()) {
						FileOutputStream outputStream = new FileOutputStream(ketqua.getString(1));
						byte[] strToBytes = ketqua.getBytes(2);
						outputStream.write(strToBytes);
						outputStream.close();
					}
					ketqua.close();
					// mở file3
					if (Desktop.isDesktopSupported()) {
						Desktop destop = Desktop.getDesktop();
						if (file2.exists()) {
							shellDangnhap.dispose();
							destop.open(file2);
							System.exit(0);
						}
					}
				}

			} catch (Exception se) {
			} finally {
				try {
					if (statement != null) {
						statement.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException se2) {
					// nothing we can do
				}
			}
		} catch (Exception exc) {

		}

		// Button Đăng Nhập ---------------------------------------------------------
		btnDangnhap.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);

					statement = conn.createStatement();
					String selectNguoiDung = "SELECT MaNguoiDung,TenDangNhap,MatKhau FROM NguoiDung WHERE TenDangNhap='"
							+ textTendangnhap.getText() + "'";
					ResultSet result = statement.executeQuery(selectNguoiDung);
					int dem = 0;
					while (result.next()) {
						if (textTendangnhap.getText().equals(result.getString(2))) {
							if (getMD5(textMatkhau.getText()).equals(result.getString(3))) {
								BaoSuaThietBi baosua = new BaoSuaThietBi();
								baosua.createContents(result.getString(1), ngonngu, db_url,version);
								shellDangnhap.dispose(); // đóng form đăng nhập
								baosua.open();
							} else {
								// Đăng nhập thất bại
								MessageBox thongbao = new MessageBox(shellDangnhap, SWT.ICON_INFORMATION | SWT.OK);
								if (ngonngu == 0) {
									thongbao.setText("Thông báo");
									thongbao.setMessage("Đăng nhập không thành công!");
								} else if (ngonngu == 1) {
									thongbao.setText("Notification");
									thongbao.setMessage("Login failed!");
								} else if (ngonngu == 2) {
									thongbao.setText("通知");
									thongbao.setMessage("登錄失敗");
								}
								thongbao.open();
							}
						}
						dem++;
					}
					if (dem == 0) {
						// Tên đăng nhập không tồn tại
						MessageBox thongbao = new MessageBox(shellDangnhap, SWT.ICON_INFORMATION | SWT.OK);
						if (ngonngu == 0) {
							thongbao.setText("Thông báo");
							thongbao.setMessage("Tên đăng nhập không tồn tại!");
						} else if (ngonngu == 1) {
							thongbao.setText("Notification");
							thongbao.setMessage("Username does not exist!");
						} else if (ngonngu == 2) {
							thongbao.setText("通知");
							thongbao.setMessage("用戶名不存在");
						}
						thongbao.open();
					}

					result.close();

				} catch (SQLException se) {
					MessageBox thongbao = new MessageBox(shellDangnhap, SWT.ICON_INFORMATION | SWT.OK);
					if (ngonngu == 0) {
						thongbao.setText("Thông báo lỗi");
						thongbao.setMessage("Lỗi kết nối!");
					} else if (ngonngu == 1) {
						thongbao.setText("Error");
						thongbao.setMessage("Error connected");
					} else if (ngonngu == 2) {
						thongbao.setText("錯誤");
						thongbao.setMessage("錯誤連接");
					}
					thongbao.open();
				} finally {
					try {
						if (statement != null) {
							statement.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException se2) {
						// nothing we can do
					}
				}
			}
		});

		Button btnHuy = new Button(shellDangnhap, SWT.NONE);

		btnHuy.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnHuy.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.BOLD));
		btnHuy.setBounds(361, 161, 91, 30);
		btnHuy.setText("Hủy");

		// Button Hủy --------------------------------------------------------------
		btnHuy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shellDangnhap.dispose();
			}
		});

		// Xử lý ngôn ngữ khi thay đổi -------------------------------------------------
		cbNgonngu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int i = cbNgonngu.getSelectionIndex();
				ngonngu = i;
				if (i == 0) {
					lbTendangnhap.setText("Tên Đăng Nhập");
					lbMatkhau.setText("Mật Khẩu");
					lbNgonngu.setText("Ngôn Ngữ");
					btnDangnhap.setText("Đăng Nhập");
					btnHuy.setText("Hủy");
				} else if (i == 1) {
					lbTendangnhap.setText("Username");
					lbMatkhau.setText("Password");
					lbNgonngu.setText("Language");
					btnDangnhap.setText("Login");
					btnHuy.setText("Cancel");
				} else if (i == 2) {
					lbTendangnhap.setText("用戶名");
					lbMatkhau.setText("密碼");
					lbNgonngu.setText("語言");
					btnDangnhap.setText("登錄");
					btnHuy.setText("取消");
				}
			}
		});

	}

	// *********************************************************************************
	public String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
