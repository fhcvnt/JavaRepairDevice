package repairdevice;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.CLabel;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.custom.CCombo;

public class BaoSuaThietBiThongTinNguoiDung {

	protected Shell shellThongtinnguoidung;
	private Text textTennguoidung;
	private Text textSodienthoai;
	private Text textMatkhauhientai;
	private Text textMatkhaumoi;
	private Text textXacnhanmatkhaumoi;
	private Text textTendangnhap;

	// Chuỗi kết nối
	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;
	private int ngonngu = 0; // 0: Vietnam, 1: English, 2: China
	private String manguoidung = "";
	private String madonvi = "";
	private String matkhau = "";

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BaoSuaThietBiThongTinNguoiDung window = new BaoSuaThietBiThongTinNguoiDung();
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
		createContents(manguoidung, ngonngu,db_url);
		shellThongtinnguoidung.open();
		shellThongtinnguoidung.layout();
		while (!shellThongtinnguoidung.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(String manguoidung, int ngonngu,String chuoiketnoi) {
		db_url=chuoiketnoi;
		shellThongtinnguoidung = new Shell(SWT.CLOSE);
		shellThongtinnguoidung.setSize(500, 342);
		shellThongtinnguoidung.setText("Thông tin người dùng");
		shellThongtinnguoidung.setLocation((Display.getDefault().getBounds().width - 500) / 2,
				(Display.getDefault().getBounds().height - 500) / 2);
		shellThongtinnguoidung.setImage(
				SWTResourceManager.getImage(BaoSuaThietBiThongTinNguoiDung.class, "/repairdevice/Images/repair.ico"));
		
		this.manguoidung = manguoidung;
		this.ngonngu = ngonngu;

		CLabel lbTendangnhap = new CLabel(shellThongtinnguoidung, SWT.NONE);
		lbTendangnhap.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbTendangnhap.setBounds(26, 28, 175, 25);
		lbTendangnhap.setText("Tên Đăng Nhập");

		CLabel lbTennguoidung = new CLabel(shellThongtinnguoidung, SWT.NONE);
		lbTennguoidung.setText("Tên Người Dùng");
		lbTennguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbTennguoidung.setBounds(26, 60, 175, 25);

		CLabel lbDonvi = new CLabel(shellThongtinnguoidung, SWT.NONE);
		lbDonvi.setText("Đơn Vị");
		lbDonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbDonvi.setBounds(26, 91, 175, 25);

		CLabel lbSodienthoai = new CLabel(shellThongtinnguoidung, SWT.NONE);
		lbSodienthoai.setText("Số Điện Thoại");
		lbSodienthoai.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbSodienthoai.setBounds(26, 122, 175, 25);

		CLabel lbMatkhauhientai = new CLabel(shellThongtinnguoidung, SWT.NONE);
		lbMatkhauhientai.setText("Mật Khẩu Hiện Tại");
		lbMatkhauhientai.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbMatkhauhientai.setBounds(26, 153, 175, 25);

		CLabel lbMatkhaumoi = new CLabel(shellThongtinnguoidung, SWT.NONE);
		lbMatkhaumoi.setText("Mật Khẩu Mới");
		lbMatkhaumoi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbMatkhaumoi.setBounds(26, 184, 175, 25);

		CLabel lbXacnhanmatkhaumoi = new CLabel(shellThongtinnguoidung, SWT.NONE);
		lbXacnhanmatkhaumoi.setText("Xác Nhận Mật Khẩu Mới");
		lbXacnhanmatkhaumoi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbXacnhanmatkhaumoi.setBounds(26, 215, 175, 25);

		textTendangnhap = new Text(shellThongtinnguoidung, SWT.BORDER);
		textTendangnhap.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textTendangnhap.setEnabled(false);
		textTendangnhap.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textTendangnhap.setBounds(207, 28, 260, 25);

		textTennguoidung = new Text(shellThongtinnguoidung, SWT.BORDER);
		textTennguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textTennguoidung.setBounds(207, 60, 260, 25);

		CCombo comboDonvi = new CCombo(shellThongtinnguoidung, SWT.BORDER);
		comboDonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		comboDonvi.setBounds(207, 91, 260, 25);

		textSodienthoai = new Text(shellThongtinnguoidung, SWT.BORDER);
		textSodienthoai.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textSodienthoai.setBounds(207, 122, 260, 25);
		textSodienthoai.setTextLimit(10);

		textMatkhauhientai = new Text(shellThongtinnguoidung, SWT.BORDER | SWT.PASSWORD);
		textMatkhauhientai.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textMatkhauhientai.setBounds(207, 153, 260, 25);

		textMatkhaumoi = new Text(shellThongtinnguoidung, SWT.BORDER | SWT.PASSWORD);
		textMatkhaumoi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textMatkhaumoi.setBounds(207, 184, 260, 25);

		textXacnhanmatkhaumoi = new Text(shellThongtinnguoidung, SWT.BORDER | SWT.PASSWORD);
		textXacnhanmatkhaumoi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textXacnhanmatkhaumoi.setBounds(207, 215, 260, 25);

		Button btnNewButton = new Button(shellThongtinnguoidung, SWT.NONE);
		btnNewButton.setImage(
				SWTResourceManager.getImage(BaoSuaThietBiThongTinNguoiDung.class, "/repairdevice/Images/update.png"));
		btnNewButton.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnNewButton.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnNewButton.setBounds(239, 257, 116, 30);
		btnNewButton.setText("Cập Nhật");

		Button btnHy = new Button(shellThongtinnguoidung, SWT.NONE);
		btnHy.setImage(
				SWTResourceManager.getImage(BaoSuaThietBiThongTinNguoiDung.class, "/repairdevice/Images/cancel.png"));
		btnHy.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnHy.setText("Hủy");
		btnHy.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnHy.setBounds(368, 257, 100, 30);

		// Xử lý ngôn ngữ
		// ------------------------------------------------------------------
		if (ngonngu == 0) {
			shellThongtinnguoidung.setText("Thông tin người dùng");
			lbTendangnhap.setText("Tên Đăng Nhập");
			lbTennguoidung.setText("Tên Người Dùng");
			lbDonvi.setText("Đơn Vị");
			lbSodienthoai.setText("Số Điện Thoại");
			lbMatkhauhientai.setText("Mật Khẩu Hiện Tại");
			lbMatkhaumoi.setText("Mật Khẩu Mới");
			lbXacnhanmatkhaumoi.setText("Xác Nhận Mật Khẩu Mới");
			btnNewButton.setText("Cập Nhật");
			btnHy.setText("Hủy");
		} else if (ngonngu == 1) {
			shellThongtinnguoidung.setText("User information");
			lbTendangnhap.setText("Username");
			lbTennguoidung.setText("Name");
			lbDonvi.setText("Department");
			lbSodienthoai.setText("Phone Number");
			lbMatkhauhientai.setText("Current Password");
			lbMatkhaumoi.setText("New Password");
			lbXacnhanmatkhaumoi.setText("Confirm Password");
			btnNewButton.setText("Update");
			btnHy.setText("Cancel");
		} else if (ngonngu == 2) {
			shellThongtinnguoidung.setText("用戶信息");
			lbTendangnhap.setText("用戶名");
			lbTennguoidung.setText("名稱");
			lbDonvi.setText("部門");
			lbSodienthoai.setText("電話號碼");
			lbMatkhauhientai.setText("目前密碼");
			lbMatkhaumoi.setText("新密碼");
			lbXacnhanmatkhaumoi.setText("確認密碼");
			btnNewButton.setText("更新");
			btnHy.setText("取消");
		}

		// Số điện thoại chỉ cho nhập số
		textSodienthoai.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		});

		// Xử lý dữ liệu đầu tiên, lấy dữ liệu cho tên đăng nhập,..., lay du lieu cho
		// combo Don Vi
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			// Lay du lieu cho combo Don Vi
			String selectDonvi = "SELECT TenDonVi FROM DonVi ORDER BY TenDonVi ASC";
			ResultSet resultcombo = statement.executeQuery(selectDonvi);
			while (resultcombo.next()) {
				comboDonvi.add(resultcombo.getString(1));
			}
			resultcombo.close();

			String selectNguoiDung = "SELECT TenDangNhap,TenNguoiDung,TenDonVi,SoDienThoai FROM NguoiDung,DonVi WHERE NguoiDung.MaDonVi=DonVi.MaDonVi AND MaNguoiDung='"
					+ manguoidung + "'";
			ResultSet result = statement.executeQuery(selectNguoiDung);

			while (result.next()) {
				textTendangnhap.setText(result.getString(1));
				textTennguoidung.setText(result.getString(2));
				comboDonvi.setText(result.getString(3));
				textSodienthoai.setText(result.getString(4));
			}

			result.close();

		} catch (SQLException se) {
			MessageBox thongbao = new MessageBox(shellThongtinnguoidung, SWT.ICON_INFORMATION | SWT.OK);
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

		// Button Huy
		btnHy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shellThongtinnguoidung.dispose();
			}
		});

		// Button Cap nhat
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					// Lay du lieu Ma Don Vi
					String selectDonvi = "SELECT MaDonVi FROM DonVi WHERE TenDonVi=N'" + comboDonvi.getText() + "'";
					ResultSet resultcombo = statement.executeQuery(selectDonvi);
					while (resultcombo.next()) {
						madonvi = resultcombo.getString(1);
					}
					resultcombo.close();

					if (textMatkhauhientai.getText().isEmpty() && textMatkhaumoi.getText().isEmpty()
							&& textXacnhanmatkhaumoi.getText().isEmpty()) {
						String update = "UPDATE NguoiDung SET TenNguoiDung=N'" + textTennguoidung.getText()
								+ "', MaDonVi='" + madonvi + "',SoDienThoai='" + textSodienthoai.getText()
								+ "' WHERE MaNguoiDung='" + manguoidung + "'";
						int resultupdate = statement.executeUpdate(update);
						if(resultupdate>0) {
							MessageBox thongbao = new MessageBox(shellThongtinnguoidung, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Cập nhật thành công!");
							} else if (ngonngu == 1) {
								thongbao.setText("Notification");
								thongbao.setMessage("Update successful!");
							} else if (ngonngu == 2) {
								thongbao.setText("通知");
								thongbao.setMessage("更新成功");
							}
							thongbao.open();
						}
					} else {
						String selectNguoidung = "SELECT MatKhau FROM NguoiDung WHERE MaNguoiDung='" + manguoidung + "'";
						ResultSet resultnguoidung = statement.executeQuery(selectNguoidung);
						while (resultnguoidung.next()) {
							matkhau = resultnguoidung.getString(1);
						}
						if(!matkhau.equals(getMD5(textMatkhauhientai.getText()))) {
							MessageBox thongbao = new MessageBox(shellThongtinnguoidung, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Mật khẩu hiện tại không đúng!");
							} else if (ngonngu == 1) {
								thongbao.setText("Notification");
								thongbao.setMessage("The current password is incorrect!");
							} else if (ngonngu == 2) {
								thongbao.setText("通知");
								thongbao.setMessage("當前密碼錯誤");
							}
							thongbao.open();
						}else {
							if(!textMatkhaumoi.getText().isEmpty() && textMatkhaumoi.getText().equals(textXacnhanmatkhaumoi.getText())) {
								matkhau = getMD5(textMatkhaumoi.getText());
								String update = "UPDATE NguoiDung SET TenNguoiDung=N'" + textTennguoidung.getText()
										+ "', MaDonVi='" + madonvi + "',SoDienThoai='" + textSodienthoai.getText()
										+ "',MatKhau='" + matkhau + "' WHERE MaNguoiDung='" + manguoidung + "'";
								int resultupdate = statement.executeUpdate(update);
								if(resultupdate>0) {
									MessageBox thongbao = new MessageBox(shellThongtinnguoidung, SWT.ICON_INFORMATION | SWT.OK);
									if (ngonngu == 0) {
										thongbao.setText("Thông báo");
										thongbao.setMessage("Cập nhật thành công!");
									} else if (ngonngu == 1) {
										thongbao.setText("Notification");
										thongbao.setMessage("Update successful!");
									} else if (ngonngu == 2) {
										thongbao.setText("通知");
										thongbao.setMessage("更新成功");
									}
									thongbao.open();
								}
							}else {
								MessageBox thongbao = new MessageBox(shellThongtinnguoidung, SWT.ICON_INFORMATION | SWT.OK);
								if (ngonngu == 0) {
									thongbao.setText("Thông báo");
									thongbao.setMessage("Cập nhật không thành công!");
								} else if (ngonngu == 1) {
									thongbao.setText("Notification");
									thongbao.setMessage("Update failed!");
								} else if (ngonngu == 2) {
									thongbao.setText("通知");
									thongbao.setMessage("更新失敗");
								}
								thongbao.open();
							}
						}
						
					}

				} catch (SQLException se) {
					MessageBox thongbao = new MessageBox(shellThongtinnguoidung, SWT.ICON_INFORMATION | SWT.OK);
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
