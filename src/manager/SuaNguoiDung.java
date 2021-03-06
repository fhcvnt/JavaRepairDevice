package manager;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
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

public class SuaNguoiDung {

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

	private String manguoidung = "";
	private String nhom = "";
	private String tendangnhap = "";
	private String tennguoidung = "";
	private String donvi = "";
	private String sodienthoai = "";
	private String ghichu = "";

	public static void main(String[] args) {
		try {
			SuaNguoiDung window = new SuaNguoiDung();
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
		createContents(manguoidung, nhom, tendangnhap, tennguoidung, donvi, sodienthoai, ghichu, mannhomguoidung,
				ngonngu,db_url);
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
	protected void createContents(String manguoidung2, String nhom, String tendangnhap, String tennguoidung,
			String donvi, String sodienthoai, String ghichu, String mannhomguoidung, int ngonngu,String chuoiketnoi) {
		db_url=chuoiketnoi;
		manguoidung = manguoidung2;
		this.nhom = nhom;
		this.tendangnhap = tendangnhap;
		this.tennguoidung = tennguoidung;
		this.donvi = donvi;
		this.sodienthoai = sodienthoai;
		this.ghichu = ghichu;
		this.mannhomguoidung = mannhomguoidung;
		this.ngonngu = ngonngu;
		shell = new Shell(SWT.MIN);
		shell.setImage(SWTResourceManager.getImage(SuaNguoiDung.class, "/manager/Images/repair256.ico"));
		shell.setSize(1013, 538);
		shell.setText("S???a ng?????i d??ng");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		CLabel lbManguoidung = new CLabel(composite, SWT.NONE);
		lbManguoidung.setAlignment(SWT.RIGHT);
		lbManguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbManguoidung.setBounds(36, 24, 154, 30);
		lbManguoidung.setText("M?? Ng?????i D??ng");

		textManguoidung = new Text(composite, SWT.BORDER);
		textManguoidung.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textManguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textManguoidung.setBounds(205, 24, 224, 30);
		textManguoidung.setTextLimit(6);

		CLabel lbNhomnguoidung = new CLabel(composite, SWT.NONE);
		lbNhomnguoidung.setText("Nh??m Ng?????i D??ng");
		lbNhomnguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbNhomnguoidung.setAlignment(SWT.RIGHT);
		lbNhomnguoidung.setBounds(445, 24, 169, 30);

		CCombo comboNhomnguoidung = new CCombo(composite, SWT.BORDER);
		comboNhomnguoidung.setBackground(SWTResourceManager.getColor(224, 255, 255));
		comboNhomnguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		comboNhomnguoidung.setBounds(629, 24, 169, 30);

		CLabel lbTendangnhap = new CLabel(composite, SWT.NONE);
		lbTendangnhap.setText("T??n ????ng Nh???p");
		lbTendangnhap.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbTendangnhap.setAlignment(SWT.RIGHT);
		lbTendangnhap.setBounds(36, 80, 154, 30);

		textTendangnhap = new Text(composite, SWT.BORDER);
		textTendangnhap.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textTendangnhap.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textTendangnhap.setBounds(205, 80, 224, 30);
		textTendangnhap.setTextLimit(20);

		CLabel lbTennguoidung = new CLabel(composite, SWT.NONE);
		lbTennguoidung.setText("T??n Ng?????i D??ng");
		lbTennguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbTennguoidung.setAlignment(SWT.RIGHT);
		lbTennguoidung.setBounds(445, 80, 169, 30);

		textTennguoidung = new Text(composite, SWT.BORDER);
		textTennguoidung.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textTennguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textTennguoidung.setBounds(629, 80, 300, 30);
		textTennguoidung.setTextLimit(50);

		CLabel lbDonvi = new CLabel(composite, SWT.NONE);
		lbDonvi.setText("????n V???");
		lbDonvi.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbDonvi.setAlignment(SWT.RIGHT);
		lbDonvi.setBounds(36, 141, 154, 30);

		CCombo comboDonvi = new CCombo(composite, SWT.BORDER);
		comboDonvi.setBackground(SWTResourceManager.getColor(224, 255, 255));
		comboDonvi.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		comboDonvi.setBounds(205, 141, 224, 30);

		CLabel lbDienthoai = new CLabel(composite, SWT.NONE);
		lbDienthoai.setText("??i???n Tho???i");
		lbDienthoai.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbDienthoai.setAlignment(SWT.RIGHT);
		lbDienthoai.setBounds(482, 141, 132, 30);

		textDienthoai = new Text(composite, SWT.BORDER);
		textDienthoai.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textDienthoai.setBounds(629, 141, 196, 30);
		textDienthoai.setTextLimit(10);

		CLabel lbMatkhau = new CLabel(composite, SWT.NONE);
		lbMatkhau.setText("M???t Kh???u");
		lbMatkhau.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbMatkhau.setAlignment(SWT.RIGHT);
		lbMatkhau.setBounds(34, 194, 156, 30);

		textMatkhau = new Text(composite, SWT.BORDER | SWT.PASSWORD);
		textMatkhau.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textMatkhau.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textMatkhau.setBounds(205, 194, 423, 30);
		textMatkhau.setTextLimit(32);

		CLabel lbGhichu = new CLabel(composite, SWT.NONE);
		lbGhichu.setText("Ghi Ch??");
		lbGhichu.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbGhichu.setAlignment(SWT.RIGHT);
		lbGhichu.setBounds(61, 319, 132, 30);

		textGhichu = new Text(composite, SWT.BORDER | SWT.MULTI);
		textGhichu.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textGhichu.setBounds(200, 246, 516, 208);
		textGhichu.setTextLimit(100);

		Button btnSave = new Button(composite, SWT.NONE);
		btnSave.setImage(SWTResourceManager.getImage(SuaNguoiDung.class, "/manager/icon/save 30.png"));
		btnSave.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
		btnSave.setBounds(743, 293, 132, 40);
		btnSave.setText("Save");

		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setImage(SWTResourceManager.getImage(SuaNguoiDung.class, "/manager/icon/cancel30.png"));
		btnCancel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnCancel.setText("Cancel");
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
		btnCancel.setBounds(743, 347, 132, 40);

		// Thay ?????i ng??n ng???
		// -------------------------------------------------------------------------
		if (ngonngu == 0) {
			shell.setText("S???a ng?????i d??ng");
			lbManguoidung.setText("M?? Ng?????i D??ng");
			lbNhomnguoidung.setText("Nh??m Ng?????i D??ng");
			lbTendangnhap.setText("T??n ????ng Nh???p");
			lbTennguoidung.setText("T??n Ng?????i D??ng");
			lbDonvi.setText("????n V???");
			lbDienthoai.setText("??i???n Tho???i");
			lbMatkhau.setText("M???t Kh???u");
			lbGhichu.setText("Ghi Ch??");
			btnSave.setText("L??u");
			btnCancel.setText("H???y");
		} else if (ngonngu == 1) {
			shell.setText("Edit User");
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
		} else if (ngonngu == 2) {
			shell.setText("????????????");
			lbManguoidung.setText("????????????");
			lbNhomnguoidung.setText("?????????");
			lbTendangnhap.setText("?????????");
			lbTennguoidung.setText("??????");
			lbDonvi.setText("??????");
			lbDienthoai.setText("??????");
			lbMatkhau.setText("??????");
			lbGhichu.setText("??????");
			btnSave.setText("??????");
			btnCancel.setText("??????");
		}

		// S??? ??i???n tho???i ch??? cho nh???p s???
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

		// L???y d??? li???u cho combo ????n v???, Nh??m ng?????i d??ng
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			// L???y d??? li???u cho combo ????n v???
			String selectDonvi = "SELECT TenDonVi FROM DonVi ORDER BY TenDonVi ASC";
			ResultSet resultcombo = statement.executeQuery(selectDonvi);
			while (resultcombo.next()) {
				comboDonvi.add(resultcombo.getString(1));
			}
			resultcombo.close();
			// L???y d??? li???u cho combo Nh??m ng?????i d??ng
			if (mannhomguoidung.equals("admin")) {
				// Nh??m Admin th?? ???????c t???o ng?????i d??ng Admin, to??n quy???n
				String selectNhomnguoidung = "SELECT TenNhom FROM NhomNguoiDung ORDER BY TenNhom ASC";
				ResultSet resultnhom = statement.executeQuery(selectNhomnguoidung);
				while (resultnhom.next()) {
					comboNhomnguoidung.add(resultnhom.getString(1));
				}
				resultnhom.close();
			} else {
				// Nh??m IT th?? kh??ng ???????c t???o ng?????i d??ng Admin
				String selectNhomnguoidung = "SELECT TenNhom FROM NhomNguoiDung WHERE MaNhom NOT IN ('admin') ORDER BY TenNhom ASC";
				ResultSet resultnhom = statement.executeQuery(selectNhomnguoidung);
				while (resultnhom.next()) {
					comboNhomnguoidung.add(resultnhom.getString(1));
				}
				resultnhom.close();
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

		// L???y d??? li???u ???????c truy???n v??o ????? s???a
		// ------------------------------------------------------------------------------------------------------------------------
		textDienthoai.setText(sodienthoai);
		textGhichu.setText(ghichu);
		textManguoidung.setText(manguoidung);
		textMatkhau.setText("");
		textTendangnhap.setText(tendangnhap);
		textTennguoidung.setText(tennguoidung);
		comboDonvi.setText(donvi);
		comboNhomnguoidung.setText(nhom);

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

					String manhomuser = "";
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

					if (!textManguoidung.getText().isEmpty() && !textTendangnhap.getText().isEmpty()
							&& !textTennguoidung.getText().isEmpty() && !comboDonvi.getText().isEmpty()
							&& !comboNhomnguoidung.getText().isEmpty()) {
						String update = "";
						if (!textMatkhau.getText().isEmpty()) {
							update = "UPDATE NguoiDung SET MaNguoiDung='" + textManguoidung.getText()
									+ "',MaNhomNguoiDung='" + manhomuser + "',TenDangNhap='" + textTendangnhap.getText()
									+ "',TenNguoiDung=N'" + textTennguoidung.getText() + "',MaDonVi='" + madonvi
									+ "',MatKhau='" + getMD5(textMatkhau.getText()) + "',SoDienThoai='"
									+ textDienthoai.getText() + "',GhiChu=N'" + textGhichu.getText()
									+ "' WHERE MaNguoiDung='" + manguoidung + "'";
						} else {
							update = "UPDATE NguoiDung SET MaNguoiDung='" + textManguoidung.getText()
									+ "',MaNhomNguoiDung='" + manhomuser + "',TenDangNhap='" + textTendangnhap.getText()
									+ "',TenNguoiDung=N'" + textTennguoidung.getText() + "',MaDonVi='" + madonvi
									+ "',SoDienThoai='" + textDienthoai.getText() + "',GhiChu=N'" + textGhichu.getText()
									+ "' WHERE MaNguoiDung='" + manguoidung + "'";
						}

						int ketqua = statement.executeUpdate(update);
						if (ketqua > 0) {
							manguoidung=textManguoidung.getText();
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Th??ng b??o");
								thongbao.setMessage("C???p nh???t th??ng tin ng?????i d??ng th??nh c??ng!");
							} else if (ngonngu == 1) {
								thongbao.setText("Notification");
								thongbao.setMessage("Update user information successfully!");
							} else if (ngonngu == 2) {
								thongbao.setText("??????");
								thongbao.setMessage("????????????????????????!");
							}
							thongbao.open();
						} else {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Th??ng b??o");
								thongbao.setMessage("C???p nh???t th??ng tin ng?????i d??ng kh??ng th??nh c??ng!");
							} else if (ngonngu == 1) {
								thongbao.setText("Notification");
								thongbao.setMessage("Updating user information failed!");
							} else if (ngonngu == 2) {
								thongbao.setText("??????");
								thongbao.setMessage("????????????????????????!");
							}
							thongbao.open();
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
