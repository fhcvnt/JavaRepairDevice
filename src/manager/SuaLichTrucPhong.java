package manager;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Composite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SuaLichTrucPhong {

	protected Shell shell;
	private Text textNgaytruc;
	private Text textNguoitruc;
	private int ngonngu = 1; // 0: Vietnam, 1: English, 2: China

	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;
	private String ngay = "17";
	private String thang = "02";
	private String nam = "2020";
	private String thu = "Thứ Hai";
	private String nguoitruc = "Đặng Minh Hiếu";

	public static void main(String[] args) {
		try {
			SuaLichTrucPhong window = new SuaLichTrucPhong();
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
		createContents(ngay, thang, nam, thu, nguoitruc, ngonngu,db_url);
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
	protected void createContents(String ngay, String thang, String nam, String thu, String nguoitruc, int ngonngu,String chuoiketnoi) {
		db_url=chuoiketnoi;
		this.ngay = ngay;
		this.thang = thang;
		this.nam = nam;
		this.thu = thu;
		this.nguoitruc = nguoitruc;
		this.ngonngu = ngonngu;

		shell = new Shell(SWT.CLOSE);
		shell.setImage(SWTResourceManager.getImage(SuaLichTrucPhong.class, "/manager/Images/repair256.ico"));
		shell.setSize(712, 279);
		shell.setText("Sửa lịch trực phòng");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		CLabel lbNgaytruc = new CLabel(composite, SWT.NONE);
		lbNgaytruc.setAlignment(SWT.RIGHT);
		lbNgaytruc.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbNgaytruc.setBounds(32, 38, 144, 30);
		lbNgaytruc.setText("Ngày Trực");

		textNgaytruc = new Text(composite, SWT.BORDER);
		textNgaytruc.setEditable(false);
		textNgaytruc.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textNgaytruc.setBounds(190, 38, 163, 30);

		CLabel lbThu = new CLabel(composite, SWT.NONE);
		lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		lbThu.setText("Thứ");
		lbThu.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbThu.setBounds(368, 38, 124, 30);

		CLabel lbNguoitruc = new CLabel(composite, SWT.NONE);
		lbNguoitruc.setAlignment(SWT.RIGHT);
		lbNguoitruc.setText("Người Trực");
		lbNguoitruc.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbNguoitruc.setBounds(32, 96, 144, 30);

		textNguoitruc = new Text(composite, SWT.BORDER);
		textNguoitruc.setEditable(false);
		textNguoitruc.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textNguoitruc.setBounds(189, 96, 257, 30);

		CLabel lbNguoitructhay = new CLabel(composite, SWT.NONE);
		lbNguoitructhay.setAlignment(SWT.RIGHT);
		lbNguoitructhay.setText("Người Trực Thay");
		lbNguoitructhay.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbNguoitructhay.setBounds(32, 158, 201, 30);

		Combo comboNguoitructhay = new Combo(composite, SWT.NONE);
		comboNguoitructhay.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		comboNguoitructhay.setBounds(247, 158, 305, 30);

		Button btnSave = new Button(composite, SWT.NONE);
		btnSave.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnSave.setImage(SWTResourceManager.getImage(SuaLichTrucPhong.class, "/manager/icon/save.png"));
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnSave.setBounds(571, 158, 100, 30);
		btnSave.setText("Save");

		CLabel lbHinhanh = new CLabel(composite, SWT.NONE);
		lbHinhanh.setBackground(SWTResourceManager.getImage(SuaLichTrucPhong.class, "/manager/icon/banhrang.png"));
		lbHinhanh.setBounds(498, 0, 210, 144);
		lbHinhanh.setText("");

		textNgaytruc.setText(ngay + "/" + thang + "/" + nam);
		textNguoitruc.setText(nguoitruc);
		lbThu.setText(thu);

		// Thay đổi ngôn ngữ
		// -------------------------------------------------------------------------
		if (ngonngu == 0) {
			shell.setText("Sửa lịch trực phòng");
			lbNgaytruc.setText("Ngày Trực");
			lbNguoitruc.setText("Người Trực");
			btnSave.setText("Lưu");
			lbNguoitructhay.setText("Người Trực Thay");
		} else if (ngonngu == 1) {
			shell.setText("Edit room calendar");
			lbNgaytruc.setText("Date On Duty");
			lbNguoitruc.setText("Person On Duty");
			btnSave.setText("Save");
			lbNguoitructhay.setText("Person On Duty Instead");
			if(lbThu.getText().compareToIgnoreCase("Thứ Hai")==0) {
				lbThu.setText("Monday");
			}else if(lbThu.getText().compareToIgnoreCase("Thứ Hai")==0) {
				lbThu.setText("Tuesday");
			}else if(lbThu.getText().compareToIgnoreCase("Thứ Ba")==0) {
				lbThu.setText("Wednesday");
			}else if(lbThu.getText().compareToIgnoreCase("Thứ Năm")==0) {
				lbThu.setText("Thursday");
			}else if(lbThu.getText().compareToIgnoreCase("Thứ Sáu")==0) {
				lbThu.setText("Friday");
			}else if(lbThu.getText().compareToIgnoreCase("Thứ Bảy")==0) {
				lbThu.setText("Saturday");
			}else if(lbThu.getText().compareToIgnoreCase("Chủ Nhật")==0) {
				lbThu.setText("Sunday");
			}
		} else if (ngonngu == 2) {
			shell.setText("編輯會議室日曆");
			lbNgaytruc.setText("值班日期");
			lbNguoitruc.setText("值班人員");
			btnSave.setText("儲存");
			lbNguoitructhay.setText("值班人員");
			if(lbThu.getText().compareToIgnoreCase("Thứ Hai")==0) {
				lbThu.setText("星期一");
			}else if(lbThu.getText().compareToIgnoreCase("Thứ Hai")==0) {
				lbThu.setText("星期二");
			}else if(lbThu.getText().compareToIgnoreCase("Thứ Ba")==0) {
				lbThu.setText("星期三");
			}else if(lbThu.getText().compareToIgnoreCase("Thứ Năm")==0) {
				lbThu.setText("星期四");
			}else if(lbThu.getText().compareToIgnoreCase("Thứ Sáu")==0) {
				lbThu.setText("星期五");
			}else if(lbThu.getText().compareToIgnoreCase("Thứ Bảy")==0) {
				lbThu.setText("星期六");
			}else if(lbThu.getText().compareToIgnoreCase("Chủ Nhật")==0) {
				lbThu.setText("星期日");
			}
		}

		// Lấy dữ liệu cho combo Nguoi truc thay
		// -----------------------------------------------------------------------------------------------------
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			// Lấy dữ liệu cho combo người trực
			String select = "SELECT TenNguoiDung FROM DanhSachTrucPhong,NguoiDung WHERE SoThe=MaNguoiDung ORDER BY SoThe";
			ResultSet resultcombo = statement.executeQuery(select);
			while (resultcombo.next()) {
				comboNguoitructhay.add(resultcombo.getString(1));
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
			} catch (Exception se2) {
			}
		}

		// Button Save
		// ----------------------------------------------------------------------------------------------------------
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String nguoitrucphongthay = "";
					// Lấy số thẻ người trực phòng thay
					String select = "SELECT MaNguoiDung FROM NguoiDung WHERE MaNhomNguoiDung='it' AND TenNguoiDung=N'" + comboNguoitructhay.getText()
							+ "'";
					ResultSet result = statement.executeQuery(select);
					while (result.next()) {
						nguoitrucphongthay = result.getString(1);
					}
					result.close();
					try {
						String update = "UPDATE LichTrucPhong SET NguoiTrucThay='"+nguoitrucphongthay+"',ThoiGianCapNhat = GETDATE() WHERE Nam='"+nam+"' AND Thang='"+thang+"' AND Ngay='"+ngay+"'";

						int sua = statement.executeUpdate(update);
						if (sua > 0) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
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
					} catch (SQLException sqe) {
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
						if (ngonngu == 0) {
							thongbao.setText("Thông báo");
							thongbao.setMessage("Cập nhật thất bại!");
						} else if (ngonngu == 1) {
							thongbao.setText("Notification");
							thongbao.setMessage("Update failed!");
						} else if (ngonngu == 2) {
							thongbao.setText("通知");
							thongbao.setMessage("更新失敗");
						}
						thongbao.open();
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
					} catch (Exception se2) {
					}
				}
			}
		});

	}
}
