package manager;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

public class ThemNguoiDung {

	protected Shell shell;
	private Text textManguoidung;
	private Text textTendangnhap;
	private Text textTennguoidung;
	private Text textMatkhau;
	private Text textDienthoai;
	private Text textGhichu;
	private int ngonngu = 0; // 0: Vietnam, 1: English, 2: China
	private String mannhomguoidung = "it";

	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;

	public static void main(String[] args) {
		try {
			ThemNguoiDung window = new ThemNguoiDung();
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
		createContents(mannhomguoidung, ngonngu,db_url);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(String mannhomguoidung, int ngonngu,String chuoiketnoi) {
		db_url=chuoiketnoi;
		this.mannhomguoidung = mannhomguoidung;
		this.ngonngu = ngonngu;
		shell = new Shell(SWT.MIN);
		shell.setImage(SWTResourceManager.getImage(ThemNguoiDung.class, "/manager/Images/repair256.ico"));
		shell.setSize(1013, 538);
		shell.setText("Thêm người dùng");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		CLabel lbManguoidung = new CLabel(composite, SWT.NONE);
		lbManguoidung.setAlignment(SWT.RIGHT);
		lbManguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbManguoidung.setBounds(36, 24, 154, 30);
		lbManguoidung.setText("Mã Người Dùng");

		textManguoidung = new Text(composite, SWT.BORDER);
		textManguoidung.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textManguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textManguoidung.setBounds(205, 24, 224, 30);
		textManguoidung.setTextLimit(6);

		CLabel lbNhomnguoidung = new CLabel(composite, SWT.NONE);
		lbNhomnguoidung.setText("Nhóm Người Dùng");
		lbNhomnguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbNhomnguoidung.setAlignment(SWT.RIGHT);
		lbNhomnguoidung.setBounds(445, 24, 169, 30);

		CCombo comboNhomnguoidung = new CCombo(composite, SWT.BORDER);
		comboNhomnguoidung.setBackground(SWTResourceManager.getColor(224, 255, 255));
		comboNhomnguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		comboNhomnguoidung.setBounds(629, 24, 169, 30);

		CLabel lbTendangnhap = new CLabel(composite, SWT.NONE);
		lbTendangnhap.setText("Tên Đăng Nhập");
		lbTendangnhap.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbTendangnhap.setAlignment(SWT.RIGHT);
		lbTendangnhap.setBounds(36, 80, 154, 30);

		textTendangnhap = new Text(composite, SWT.BORDER);
		textTendangnhap.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textTendangnhap.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textTendangnhap.setBounds(205, 80, 224, 30);
		textTendangnhap.setTextLimit(20);

		CLabel lbTennguoidung = new CLabel(composite, SWT.NONE);
		lbTennguoidung.setText("Tên Người Dùng");
		lbTennguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbTennguoidung.setAlignment(SWT.RIGHT);
		lbTennguoidung.setBounds(445, 80, 169, 30);

		textTennguoidung = new Text(composite, SWT.BORDER);
		textTennguoidung.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textTennguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textTennguoidung.setBounds(629, 80, 300, 30);
		textTennguoidung.setTextLimit(50);

		CLabel lbDonvi = new CLabel(composite, SWT.NONE);
		lbDonvi.setText("Đơn Vị");
		lbDonvi.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbDonvi.setAlignment(SWT.RIGHT);
		lbDonvi.setBounds(36, 141, 154, 30);

		CCombo comboDonvi = new CCombo(composite, SWT.BORDER);
		comboDonvi.setBackground(SWTResourceManager.getColor(224, 255, 255));
		comboDonvi.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		comboDonvi.setBounds(205, 141, 224, 30);

		CLabel lbDienthoai = new CLabel(composite, SWT.NONE);
		lbDienthoai.setText("Điện Thoại");
		lbDienthoai.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbDienthoai.setAlignment(SWT.RIGHT);
		lbDienthoai.setBounds(482, 141, 132, 30);

		textDienthoai = new Text(composite, SWT.BORDER);
		textDienthoai.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textDienthoai.setBounds(629, 141, 196, 30);
		textDienthoai.setTextLimit(10);

		CLabel lbMatkhau = new CLabel(composite, SWT.NONE);
		lbMatkhau.setText("Mật Khẩu");
		lbMatkhau.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbMatkhau.setAlignment(SWT.RIGHT);
		lbMatkhau.setBounds(34, 194, 156, 30);

		textMatkhau = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		textMatkhau.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textMatkhau.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textMatkhau.setBounds(205, 194, 423, 30);
		textMatkhau.setTextLimit(32);

		CLabel lbGhichu = new CLabel(composite, SWT.NONE);
		lbGhichu.setText("Ghi Chú");
		lbGhichu.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbGhichu.setAlignment(SWT.RIGHT);
		lbGhichu.setBounds(61, 319, 132, 30);

		textGhichu = new Text(composite, SWT.BORDER | SWT.MULTI);
		textGhichu.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textGhichu.setBounds(200, 246, 516, 208);
		textGhichu.setTextLimit(100);

		Button btnSave = new Button(composite, SWT.NONE);
		btnSave.setImage(SWTResourceManager.getImage(ThemNguoiDung.class, "/manager/icon/save 30.png"));
		btnSave.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
		btnSave.setBounds(744, 265, 132, 40);
		btnSave.setText("Save");

		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setImage(SWTResourceManager.getImage(ThemNguoiDung.class, "/manager/icon/cancel30.png"));
		btnCancel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnCancel.setText("Cancel");
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
		btnCancel.setBounds(744, 319, 132, 40);

		Button btnClear = new Button(composite, SWT.NONE);
		btnClear.setText("Clear");
		btnClear.setImage(SWTResourceManager.getImage(ThemNguoiDung.class, "/manager/icon/clear30.png"));
		btnClear.setForeground(SWTResourceManager.getColor(128, 128, 128));
		btnClear.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
		btnClear.setBounds(744, 375, 132, 40);

		// Thay đổi ngôn ngữ
		// -------------------------------------------------------------------------
		if (ngonngu == 0) {
			shell.setText("Thêm người dùng");
			lbManguoidung.setText("Mã Người Dùng");
			lbNhomnguoidung.setText("Nhóm Người Dùng");
			lbTendangnhap.setText("Tên Đăng Nhập");
			lbTennguoidung.setText("Tên Người Dùng");
			lbDonvi.setText("Đơn Vị");
			lbDienthoai.setText("Điện Thoại");
			lbMatkhau.setText("Mật Khẩu");
			lbGhichu.setText("Ghi Chú");
			btnSave.setText("Lưu");
			btnCancel.setText("Hủy");
			btnClear.setText("Xóa");
		} else if (ngonngu == 1) {
			shell.setText("Add Users");
			lbManguoidung.setText("User ID");
			lbNhomnguoidung.setText("User Groups");
			lbTendangnhap.setText("Username");
			lbTennguoidung.setText("Person Name");
			lbDonvi.setText("Department");
			lbDienthoai.setText("Phone");
			lbMatkhau.setText("Password");
			lbGhichu.setText("Note");
			btnSave.setText("Save");
			btnCancel.setText("Cancel");
			btnClear.setText("Clear");
		} else if (ngonngu == 2) {
			shell.setText("新增使用者");
			lbManguoidung.setText("用戶身份");
			lbNhomnguoidung.setText("用戶組");
			lbTendangnhap.setText("用戶名");
			lbTennguoidung.setText("名稱");
			lbDonvi.setText("部門");
			lbDienthoai.setText("電話");
			lbMatkhau.setText("密碼");
			lbGhichu.setText("筆記");
			btnSave.setText("儲存");
			btnCancel.setText("取消");
			btnClear.setText("明確");
		}

		// Số điện thoại chỉ cho nhập số
		// ----------------------------------------------------------------------------------------------------------------------
		textDienthoai.addVerifyListener(new VerifyListener() {
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

		// Lấy dữ liệu cho combo Đơn vị, Nhóm người dùng
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			// Lấy dữ liệu cho combo Đơn vị
			String selectDonvi = "SELECT TenDonVi FROM DonVi ORDER BY TenDonVi ASC";
			ResultSet resultcombo = statement.executeQuery(selectDonvi);
			while (resultcombo.next()) {
				comboDonvi.add(resultcombo.getString(1));
			}
			resultcombo.close();
			// Lấy dữ liệu cho combo Nhóm người dùng
			if (mannhomguoidung.equals("admin")) {
				// Nhóm Admin thì được tạo người dùng Admin, toàn quyền
				String selectNhomnguoidung = "SELECT TenNhom FROM NhomNguoiDung ORDER BY TenNhom ASC";
				ResultSet resultnhom = statement.executeQuery(selectNhomnguoidung);
				while (resultnhom.next()) {
					comboNhomnguoidung.add(resultnhom.getString(1));
				}
				resultnhom.close();
				comboNhomnguoidung.select(2);
			} else {
				// Nhóm IT thì không được tạo người dùng Admin
				String selectNhomnguoidung = "SELECT TenNhom FROM NhomNguoiDung WHERE MaNhom NOT IN ('admin') ORDER BY TenNhom ASC";
				ResultSet resultnhom = statement.executeQuery(selectNhomnguoidung);
				while (resultnhom.next()) {
					comboNhomnguoidung.add(resultnhom.getString(1));
				}
				resultnhom.close();
				comboNhomnguoidung.select(1);
			}

		} catch (SQLException se) {

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

		// Button Save
		// ---------------------------------------------------------------------------------------------------------
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String selectNhomnguoidung = "SELECT MaNhom FROM NhomNguoiDung WHERE TenNhom=N'"
							+ comboNhomnguoidung.getText() + "'";
					ResultSet result = statement.executeQuery(selectNhomnguoidung);
					
					String manhomuser= "";
					while (result.next()) {
						manhomuser = result.getString(1);
					}
					result.close();

					String selectDonvi = "SELECT MaDonVi FROM DonVi WHERE TenDonVi=N'" + comboDonvi.getText() + "'";
					ResultSet resultdonvi = statement.executeQuery(selectDonvi);
					String madonvi = "";
					while (resultdonvi.next()) {
						madonvi = resultdonvi.getString(1);
					}
					resultdonvi.close();

					if (!textManguoidung.getText().isEmpty() && !textMatkhau.getText().isEmpty()
							&& !textTendangnhap.getText().isEmpty() && !textTennguoidung.getText().isEmpty()
							&& !comboDonvi.getText().isEmpty() && !comboNhomnguoidung.getText().isEmpty()) {
						String insert = "INSERT INTO NguoiDung(MaNguoiDung,MaNhomNguoiDung,TenDangNhap,TenNguoiDung,MaDonVi,MatKhau,SoDienThoai,GhiChu) VALUES ('"
								+ textManguoidung.getText() + "','" + manhomuser + "','"
								+ textTendangnhap.getText() + "',N'" + textTennguoidung.getText() + "','" + madonvi
								+ "','" + getMD5(textMatkhau.getText()) + "','" + textDienthoai.getText() + "',N'"
								+ textGhichu.getText() + "')";
						int ketqua = statement.executeUpdate(insert);
						if (ketqua > 0) {
							textManguoidung.setText("");
							textDienthoai.setText("");
							textGhichu.setText("");
							textMatkhau.setText("");
							textTendangnhap.setText("");
							textTennguoidung.setText("");
							comboDonvi.setText("");
							try {
								if (mannhomguoidung.equals("admin")) {
									comboNhomnguoidung.select(2);
								} else {
									comboNhomnguoidung.select(1);
								}
							} catch (Exception ee) {
							}
						}
					}

				} catch (SQLException se) {

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

		// Button Cancel
		// ---------------------------------------------------------------------------------------------------------
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});

		// Button Clear
		// ---------------------------------------------------------------------------------------------------------
		btnClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textManguoidung.setText("");
				textDienthoai.setText("");
				textGhichu.setText("");
				textMatkhau.setText("");
				textTendangnhap.setText("");
				textTennguoidung.setText("");
				comboDonvi.setText("");
				try {
					if (mannhomguoidung.equals("admin")) {
						comboNhomnguoidung.select(2);
					} else {
						comboNhomnguoidung.select(1);
					}
				} catch (Exception ee) {

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
