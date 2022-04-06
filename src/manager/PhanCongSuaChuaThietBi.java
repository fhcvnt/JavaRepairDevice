package manager;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.CLabel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class PhanCongSuaChuaThietBi {

	protected Shell shell;
	private Text textNguoidung;
	private Text textDonvi;
	private Text textThietbi;
	private Text textNoidung;
	private Text textThoigianbao;
	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PhanCongSuaChuaThietBi window = new PhanCongSuaChuaThietBi();
			window.createContents("", "", "", "","", "", "", "", 0,"jdbc:sqlserver://192.168.30.123;databaseName=SuaChuaThietBi;user=sa;password=Killua21608");
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
	protected void createContents(String manguoidung, String phanbiet, String nguoidung, String donvi, String thietbi,
			String noidung, String thoigianbao,String nguoiduocphancong, int ngonngu,String chuoiketnoi) {
		db_url=chuoiketnoi;
		shell = new Shell(SWT.CLOSE);
		shell.setSize(704, 369);
		shell.setText("Phân công sửa chữa thiết bị");

		CLabel lbNguoidung = new CLabel(shell, SWT.NONE);
		lbNguoidung.setAlignment(SWT.RIGHT);
		lbNguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.NORMAL));
		lbNguoidung.setBounds(10, 22, 136, 25);
		lbNguoidung.setText("Người Dùng");

		textNguoidung = new Text(shell, SWT.BORDER);
		textNguoidung.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textNguoidung.setEditable(false);
		textNguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textNguoidung.setBounds(165, 22, 497, 25);

		CLabel lbDonvi = new CLabel(shell, SWT.NONE);
		lbDonvi.setAlignment(SWT.RIGHT);
		lbDonvi.setText("Đơn Vị");
		lbDonvi.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.NORMAL));
		lbDonvi.setBounds(34, 53, 112, 25);

		textDonvi = new Text(shell, SWT.BORDER);
		textDonvi.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textDonvi.setEditable(false);
		textDonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textDonvi.setBounds(165, 53, 497, 25);

		CLabel lbThietbi = new CLabel(shell, SWT.NONE);
		lbThietbi.setAlignment(SWT.RIGHT);
		lbThietbi.setText("Thiết Bị");
		lbThietbi.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.NORMAL));
		lbThietbi.setBounds(34, 84, 112, 25);

		textThietbi = new Text(shell, SWT.BORDER);
		textThietbi.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textThietbi.setEditable(false);
		textThietbi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textThietbi.setBounds(165, 84, 497, 25);

		CLabel lbNoidung = new CLabel(shell, SWT.NONE);
		lbNoidung.setAlignment(SWT.RIGHT);
		lbNoidung.setText("Nội Dung");
		lbNoidung.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.NORMAL));
		lbNoidung.setBounds(10, 153, 136, 25);

		textNoidung = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		textNoidung.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textNoidung.setEditable(false);
		textNoidung.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textNoidung.setBounds(165, 115, 497, 117);

		CLabel lbThoigianbao = new CLabel(shell, SWT.NONE);
		lbThoigianbao.setAlignment(SWT.RIGHT);
		lbThoigianbao.setText("Thời Gian Báo");
		lbThoigianbao.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.NORMAL));
		lbThoigianbao.setBounds(10, 238, 136, 25);

		textThoigianbao = new Text(shell, SWT.BORDER);
		textThoigianbao.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		textThoigianbao.setEditable(false);
		textThoigianbao.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textThoigianbao.setBounds(165, 238, 497, 25);

		CLabel lbNguoisua = new CLabel(shell, SWT.NONE);
		lbNguoisua.setText("Người Sửa");
		lbNguoisua.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.NORMAL));
		lbNguoisua.setAlignment(SWT.RIGHT);
		lbNguoisua.setBounds(10, 269, 136, 25);

		CCombo comboNguoisua = new CCombo(shell, SWT.BORDER);
		comboNguoisua.setBackground(SWTResourceManager.getColor(224, 255, 255));
		comboNguoisua.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.NORMAL));
		comboNguoisua.setBounds(165, 269, 260, 30);

		Button btnSave = new Button(shell, SWT.NONE);
		btnSave.setImage(SWTResourceManager.getImage(PhanCongSuaChuaThietBi.class, "/manager/icon/save25.png"));
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.NORMAL));
		btnSave.setBounds(441, 269, 100, 30);
		btnSave.setText("Save");

		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setImage(SWTResourceManager.getImage(PhanCongSuaChuaThietBi.class, "/manager/icon/cancel.png"));
		btnCancel.setText("Cancel");
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 14, SWT.NORMAL));
		btnCancel.setBounds(549, 269, 100, 30);

		// Ngôn ngữ
		// *******************************************************************************************************************************
		if (ngonngu == 0) {
			shell.setText("Phân công sửa chữa thiết bị");
			lbNguoidung.setText("Người Dùng");
			lbDonvi.setText("Đơn Vị");
			lbThietbi.setText("Thiết Bị");
			lbNoidung.setText("Nội Dung");
			lbThoigianbao.setText("Thời Gian Báo");
			lbNguoisua.setText("Người Sửa");
			btnSave.setText("Lưu");
			btnCancel.setText("Hủy");
		} else if (ngonngu == 1) {
			shell.setText("Assign equipment repair");
			lbNguoidung.setText("User");
			lbDonvi.setText("Department");
			lbThietbi.setText("Devices");
			lbNoidung.setText("Content");
			lbThoigianbao.setText("Notice Time");
			lbNguoisua.setText("Repair Employee");
			btnSave.setText("Save");
			btnCancel.setText("Cancel");
		} else if (ngonngu == 2) {
			shell.setText("分配設備維修");
			lbNguoidung.setText("使用者");
			lbDonvi.setText("部門");
			lbThietbi.setText("設備");
			lbNoidung.setText("內容");
			lbThoigianbao.setText("通知時間");
			lbNguoisua.setText("修復人員");
			btnSave.setText("儲存");
			btnCancel.setText("取消");
		}

		// Lấy dữ liệu lúc đầu
		// *******************************************************************************************************************************
		textNguoidung.setText(nguoidung);
		textDonvi.setText(donvi);
		textThietbi.setText(thietbi);
		textThoigianbao.setText(thoigianbao);
		textNoidung.setText(noidung);
		comboNguoisua.setText(nguoiduocphancong);

		// Lấy dữ liệu cho combo người sửa
		// *******************************************************************************************************************************
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			// Lấy dữ liệu cho combo người trực
			String select = "SELECT TenNguoiDung FROM NguoiDung WHERE MaNhomNguoiDung='it' ORDER BY TenNguoiDung";
			ResultSet resultcombo = statement.executeQuery(select);
			while (resultcombo.next()) {
				comboNguoisua.add(resultcombo.getString(1));
			}
			resultcombo.close();

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

		// Save
		// *******************************************************************************************************************************
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!comboNguoisua.getText().isEmpty()) {
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						String nguoiduocphancong = "";
						String select = "SELECT MaNguoiDung FROM NguoiDung WHERE MaNhomNguoiDung='it' AND TenNguoiDung=N'"
								+ comboNguoisua.getText() + "'";
						ResultSet result = statement.executeQuery(select);
						while (result.next()) {
							nguoiduocphancong = result.getString(1);
						}
						result.close();
						String update = "UPDATE BaoSuaThietBi SET NguoiPhanCong='" + manguoidung
								+ "',NguoiDuocPhanCong='" + nguoiduocphancong
								+ "',TrangThai=1,ThoiGianCapNhat=GETDATE() WHERE PhanBiet=" + phanbiet;
						if (statement.executeUpdate(update) > 0) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Phân công thành công!");
							} else if (ngonngu == 1) {
								thongbao.setText("Notification");
								thongbao.setMessage("Assignment successful!");
							} else if (ngonngu == 2) {
								thongbao.setText("通知");
								thongbao.setMessage("分配成功!");
							}
							thongbao.open();
							
							// Thêm vào bảng thông báo
							String insertthongbao="INSERT INTO ThongBao(PhanBiet,NguoiDuocThongBao,DaXem,DaXemUser,ThoiGianThongBao) VALUES("+phanbiet+",'"+nguoiduocphancong+"',0,0,GETDATE())";
							Statement statethongbao = conn.createStatement();
							statethongbao.executeUpdate(insertthongbao);
							statethongbao.close();
							shell.dispose();
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
			}
		});

		// Cancel
		// *******************************************************************************************************************************
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
	}
}
