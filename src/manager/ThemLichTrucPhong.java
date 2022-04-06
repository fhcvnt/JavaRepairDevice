package manager;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ThemLichTrucPhong {

	protected Shell shell;
	private Table table;
	// kích thước màn hình
	private int sizemonitorx = Display.getDefault().getBounds().width;
	private int sizemonitory = Display.getDefault().getBounds().height;
	private int ngonngu = 0; // 0: Vietnam, 1: English, 2: China

	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;
	private String manguoidung = "21608";

	public static void main(String[] args) {
		try {
			ThemLichTrucPhong window = new ThemLichTrucPhong();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		Display display = Display.getDefault();
		createContents(manguoidung, ngonngu,db_url);
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
	protected void createContents(String manguoidung, int ngonngu,String chuoiketnoi) {
		db_url=chuoiketnoi;
		this.manguoidung = manguoidung;
		this.ngonngu = ngonngu;
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage(LichTrucPhong.class, "/manager/Images/repair256.ico"));
		shell.setSize(1568, 741);
		shell.setText("Thêm lịch trực phòng");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setMaximized(true);

		ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(composite);

		CLabel lbNgaytruc = new CLabel(composite, SWT.NONE);
		lbNgaytruc.setAlignment(SWT.RIGHT);
		lbNgaytruc.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbNgaytruc.setBounds(22, 26, 117, 30);
		lbNgaytruc.setText("Ngày Trực");

		DateTime datetimeNgaytruc = new DateTime(composite, SWT.BORDER);
		datetimeNgaytruc.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		datetimeNgaytruc.setBounds(161, 26, 135, 30);

		CLabel lbNguoitruc = new CLabel(composite, SWT.NONE);
		lbNguoitruc.setAlignment(SWT.RIGHT);
		lbNguoitruc.setText("Người Trực");
		lbNguoitruc.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbNguoitruc.setBounds(410, 26, 142, 30);

		CCombo comboNguoitruc = new CCombo(composite, SWT.BORDER);
		comboNguoitruc.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		comboNguoitruc.setBounds(558, 26, 273, 30);

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderBackground(SWTResourceManager.getColor(255, 165, 0));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		// table.setBounds(22, 108, 1246, 584);
		table.setBounds(22, 63, sizemonitorx - 50, sizemonitory - 150);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnNam = new TableColumn(table, SWT.NONE);
		tblclmnNam.setWidth(68);
		tblclmnNam.setText("Năm");

		TableColumn tblclmnThang = new TableColumn(table, SWT.NONE);
		tblclmnThang.setWidth(62);
		tblclmnThang.setText("Tháng");

		TableColumn tblclmnNgay = new TableColumn(table, SWT.NONE);
		tblclmnNgay.setWidth(62);
		tblclmnNgay.setText("Ngày");

		TableColumn tblclmnNgayTrongTuan = new TableColumn(table, SWT.NONE);
		tblclmnNgayTrongTuan.setWidth(139);
		tblclmnNgayTrongTuan.setText("Ngày Trong Tuần");

		TableColumn tblclmnTuan = new TableColumn(table, SWT.NONE);
		tblclmnTuan.setWidth(57);
		tblclmnTuan.setText("Tuần");

		TableColumn tblclmnNgayTruc = new TableColumn(table, SWT.NONE);
		tblclmnNgayTruc.setWidth(139);
		tblclmnNgayTruc.setText("Ngày Trực");

		TableColumn tblclmnNguoiTruc = new TableColumn(table, SWT.NONE);
		tblclmnNguoiTruc.setWidth(163);
		tblclmnNguoiTruc.setText("Người Trực");

		TableColumn tblclmnNguoiPhanCong = new TableColumn(table, SWT.NONE);
		tblclmnNguoiPhanCong.setWidth(161);
		tblclmnNguoiPhanCong.setText("Người Phân Công");

		TableColumn tblclmnNguoiTrucThay = new TableColumn(table, SWT.NONE);
		tblclmnNguoiTrucThay.setWidth(183);
		tblclmnNguoiTrucThay.setText("Người Trực Thay");

		TableColumn tblclmnThoiGianCapNhat = new TableColumn(table, SWT.NONE);
		tblclmnThoiGianCapNhat.setWidth(206);
		tblclmnThoiGianCapNhat.setText("Thời Gian Cập Nhật");

		Button btnSave = new Button(composite, SWT.NONE);
		btnSave.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnSave.setText("Save");
		btnSave.setImage(SWTResourceManager.getImage(ThemLichTrucPhong.class, "/manager/icon/save25.png"));
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnSave.setBounds(862, 26, 102, 30);

		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnCancel.setText("Cancel");
		btnCancel.setImage(SWTResourceManager.getImage(LichTrucPhong.class, "/manager/icon/cancel.png"));
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnCancel.setBounds(970, 26, 117, 30);

		CLabel lbThu = new CLabel(composite, SWT.NONE);
		lbThu.setAlignment(SWT.CENTER);
		lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		lbThu.setText("Thứ Tư");
		lbThu.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbThu.setBounds(302, 26, 102, 30);

		// Thay đổi ngôn ngữ
		// -------------------------------------------------------------------------
		if (ngonngu == 0) {
			shell.setText("Thêm lịch trực phòng");
			lbNgaytruc.setText("Ngày Trực");
			lbNguoitruc.setText("Người Trực");
			btnSave.setText("Lưu");
			btnCancel.setText("Hủy");
			tblclmnNam.setText("Năm");
			tblclmnThang.setText("Tháng");
			tblclmnNgay.setText("Ngày");
			tblclmnNgayTrongTuan.setText("Ngày Trong Tuần");
			tblclmnTuan.setText("Tuần");
			tblclmnNgayTruc.setText("Ngày Trực");
			tblclmnNguoiTruc.setText("Người Trực");
			tblclmnNguoiPhanCong.setText("Người Phân Công");
			tblclmnNguoiTrucThay.setText("Người Trực Thay");
			tblclmnThoiGianCapNhat.setText("Thời Gian Cập Nhật");

			String thutrongtuan = java.time.YearMonth.of(datetimeNgaytruc.getYear(), datetimeNgaytruc.getMonth() + 1)
					.atDay(datetimeNgaytruc.getDay()).getDayOfWeek().name();
			if (thutrongtuan.compareToIgnoreCase("MONDAY") == 0) {
				lbThu.setText("Thứ Hai");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("TUESDAY") == 0) {
				lbThu.setText("Thứ Ba");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("WEDNESDAY") == 0) {
				lbThu.setText("Thứ Tư");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("THURSDAY") == 0) {
				lbThu.setText("Thứ Năm");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("FRIDAY") == 0) {
				lbThu.setText("Thứ Sáu");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("SATURDAY") == 0) {
				lbThu.setText("Thứ Bảy");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("SUNDAY") == 0) {
				lbThu.setText("Chủ Nhật");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			}
		} else if (ngonngu == 1) {
			shell.setText("Add room calendar");
			lbNgaytruc.setText("Date On Duty");
			lbNguoitruc.setText("Person On Duty");
			btnSave.setText("Save");
			btnCancel.setText("Cancel");
			tblclmnNam.setText("Year");
			tblclmnThang.setText("Month");
			tblclmnNgay.setText("Day");
			tblclmnNgayTrongTuan.setText("Days Of The Week");
			tblclmnTuan.setText("Week");
			tblclmnNgayTruc.setText("Date On Duty");
			tblclmnNguoiTruc.setText("Person On Duty");
			tblclmnNguoiPhanCong.setText("Taskmaster");
			tblclmnNguoiTrucThay.setText("Person On Duty Instead");
			tblclmnThoiGianCapNhat.setText("Time To Update");

			String thutrongtuan = java.time.YearMonth.of(datetimeNgaytruc.getYear(), datetimeNgaytruc.getMonth() + 1)
					.atDay(datetimeNgaytruc.getDay()).getDayOfWeek().name();
			if (thutrongtuan.compareToIgnoreCase("MONDAY") == 0) {
				lbThu.setText("Monday");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("TUESDAY") == 0) {
				lbThu.setText("Tuesday");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("WEDNESDAY") == 0) {
				lbThu.setText("Wednesday");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("THURSDAY") == 0) {
				lbThu.setText("Thursday");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("FRIDAY") == 0) {
				lbThu.setText("Friday");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("SATURDAY") == 0) {
				lbThu.setText("Saturday");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("SUNDAY") == 0) {
				lbThu.setText("Sunday");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			}
		} else if (ngonngu == 2) {
			shell.setText("添加房間日曆");
			lbNgaytruc.setText("值班日期");
			lbNguoitruc.setText("值班人員");
			btnSave.setText("儲存");
			btnCancel.setText("取消");
			tblclmnNam.setText("年");
			tblclmnThang.setText("月");
			tblclmnNgay.setText("日");
			tblclmnNgayTrongTuan.setText("每周天數");
			tblclmnTuan.setText("星期");
			tblclmnNgayTruc.setText("值班日期");
			tblclmnNguoiTruc.setText("值班人員");
			tblclmnNguoiPhanCong.setText("工作分配者");
			tblclmnNguoiTrucThay.setText("值班人員");
			tblclmnThoiGianCapNhat.setText("是時候更新了");

			String thutrongtuan = java.time.YearMonth.of(datetimeNgaytruc.getYear(), datetimeNgaytruc.getMonth() + 1)
					.atDay(datetimeNgaytruc.getDay()).getDayOfWeek().name();
			if (thutrongtuan.compareToIgnoreCase("MONDAY") == 0) {
				lbThu.setText("星期一");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("TUESDAY") == 0) {
				lbThu.setText("星期二");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("WEDNESDAY") == 0) {
				lbThu.setText("星期三");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("THURSDAY") == 0) {
				lbThu.setText("星期四");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("FRIDAY") == 0) {
				lbThu.setText("星期五");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("SATURDAY") == 0) {
				lbThu.setText("星期六");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
			} else if (thutrongtuan.compareToIgnoreCase("SUNDAY") == 0) {
				lbThu.setText("星期日");
				lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
			}
		}

		// Lấy dữ liệu cho combo Nguoi truc
		// -----------------------------------------------------------------------------------------------------
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			// Lấy dữ liệu cho combo người trực
			String select = "SELECT TenNguoiDung FROM DanhSachTrucPhong,NguoiDung WHERE SoThe=MaNguoiDung ORDER BY SoThe";
			ResultSet resultcombo = statement.executeQuery(select);
			while (resultcombo.next()) {
				comboNguoitruc.add(resultcombo.getString(1));
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
			}
		}

		// Lấy thứ khi ngày tháng thay đổi
		// --------------------------------------------------------------------------------------------------------
		datetimeNgaytruc.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String thutrongtuan = java.time.YearMonth
						.of(datetimeNgaytruc.getYear(), datetimeNgaytruc.getMonth() + 1)
						.atDay(datetimeNgaytruc.getDay()).getDayOfWeek().name();
				if (ngonngu == 0) {
					if (thutrongtuan.compareToIgnoreCase("MONDAY") == 0) {
						lbThu.setText("Thứ Hai");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("TUESDAY") == 0) {
						lbThu.setText("Thứ Ba");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("WEDNESDAY") == 0) {
						lbThu.setText("Thứ Tư");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("THURSDAY") == 0) {
						lbThu.setText("Thứ Năm");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("FRIDAY") == 0) {
						lbThu.setText("Thứ Sáu");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("SATURDAY") == 0) {
						lbThu.setText("Thứ Bảy");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("SUNDAY") == 0) {
						lbThu.setText("Chủ Nhật");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
					}
				} else if (ngonngu == 1) {
					if (thutrongtuan.compareToIgnoreCase("MONDAY") == 0) {
						lbThu.setText("Monday");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("TUESDAY") == 0) {
						lbThu.setText("Tuesday");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("WEDNESDAY") == 0) {
						lbThu.setText("Wednesday");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("THURSDAY") == 0) {
						lbThu.setText("Thursday");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("FRIDAY") == 0) {
						lbThu.setText("Friday");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("SATURDAY") == 0) {
						lbThu.setText("Saturday");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("SUNDAY") == 0) {
						lbThu.setText("Sunday");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
					}
				} else if (ngonngu == 2) {
					if (thutrongtuan.compareToIgnoreCase("MONDAY") == 0) {
						lbThu.setText("星期一");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("TUESDAY") == 0) {
						lbThu.setText("星期二");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("WEDNESDAY") == 0) {
						lbThu.setText("星期三");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("THURSDAY") == 0) {
						lbThu.setText("星期四");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("FRIDAY") == 0) {
						lbThu.setText("星期五");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("SATURDAY") == 0) {
						lbThu.setText("星期六");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					} else if (thutrongtuan.compareToIgnoreCase("SUNDAY") == 0) {
						lbThu.setText("星期日");
						lbThu.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
					}
				}
			}
		});

		// Button Save
		// --------------------------------------------------------------------------------------------------------
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					double tuanthu = (double) java.time.YearMonth
							.of(datetimeNgaytruc.getYear(), datetimeNgaytruc.getMonth() + 1)
							.atDay(datetimeNgaytruc.getDay()).getDayOfYear() / 7;

					String ngay = (datetimeNgaytruc.getDay() + "").length() == 2 ? ("" + datetimeNgaytruc.getDay())
							: ("0" + datetimeNgaytruc.getDay());
					String thang = ((datetimeNgaytruc.getMonth() + 1) + "").length() == 2
							? ("" + (datetimeNgaytruc.getMonth() + 1))
							: ("0" + (datetimeNgaytruc.getMonth() + 1));
					String nam = datetimeNgaytruc.getYear() + "";
					// thu=Thứ Hai=Monday, Thứ Ba=Tuesday, Thứ Tư=Wednesday, Thứ Năm=Thursday, Thứ
					// Sáu=Friday, Thứ Bảy=Saturday, Chủ Nhật=Sunday
					String thu = java.time.YearMonth.of(datetimeNgaytruc.getYear(), datetimeNgaytruc.getMonth() + 1)
							.atDay(datetimeNgaytruc.getDay()).getDayOfWeek().name();

					thu = thu.equals("MONDAY") ? "Thứ Hai"
							: thu.equals("TUESDAY") ? "Thứ Ba"
									: thu.equals("WEDNESDAY") ? "Thứ Tư"
											: thu.equals("THURSDAY") ? "Thứ Năm"
													: thu.equals("FRIDAY") ? "Thứ Sáu"
															: thu.equals("SATURDAY") ? "Thứ Bảy"
																	: thu.equals("SUNDAY") ? "Chủ Nhật" : thu;
					int tuanthumay = (int) Math.ceil(tuanthu);
					String ngaytrucphong = nam + thang + ngay + "";
					String nguoitrucphong = "";
					// Lấy số thẻ người trực phòng
					String select = "SELECT MaNguoiDung FROM NguoiDung WHERE MaNhomNguoiDung='it' AND TenNguoiDung=N'"
							+ comboNguoitruc.getText() + "'";
					ResultSet result = statement.executeQuery(select);
					while (result.next()) {
						nguoitrucphong = result.getString(1);
					}
					result.close();
					try {
						if(conn.isClosed()) {
							conn = DriverManager.getConnection(db_url);
						}
						if(statement.isClosed()) {
							statement = conn.createStatement();
						}
						if (!nguoitrucphong.isEmpty()) {
							String insert = "INSERT INTO LichTrucPhong(Nam,Thang,Ngay,NgayTrongTuan,Tuan,NgayTruc,NguoiTruc,NguoiPhanCong,NguoiTrucThay,ThoiGianCapNhat) VALUES  ('"
									+ nam + "','" + thang + "','" + ngay + "',N'" + thu + "', '" + tuanthumay + "','"
									+ ngaytrucphong + "','" + nguoitrucphong + "','" + manguoidung + "','"
									+ nguoitrucphong + "',GETDATE())";

							int them = statement.executeUpdate(insert);
							if (them > 0) {
								String selectlichtruc = "SELECT Nam,Thang,Ngay,NgayTrongTuan,Tuan,NgayTruc,ND.TenNguoiDung AS 'Nguoi Truc',NguoiDung.TenNguoiDung AS 'Nguoi Phan Cong',NDNTT.TenNguoiDung AS 'NguoiTrucThay',ThoiGianCapNhat FROM LichTrucPhong,NguoiDung,NguoiDung AS NDNTT,NguoiDung AS ND WHERE LichTrucPhong.NguoiTruc=ND.MaNguoiDung AND LichTrucPhong.NguoiPhanCong=NguoiDung.MaNguoiDung AND LichTrucPhong.NguoiTrucThay=NDNTT.MaNguoiDung AND Nam='"
										+ nam + "' AND Thang='" + thang + "' AND Ngay='" + ngay
										+ "' ORDER BY NgayTruc DESC";

								ResultSet resultselect = statement.executeQuery(selectlichtruc);
								while (resultselect.next()) {
									String ngaytruc = resultselect.getString(6).toString();
									ngaytruc = ngaytruc.substring(8, 10) + "/" + ngaytruc.substring(5, 7) + "/"
											+ ngaytruc.substring(0, 4);

									String thoigiancapnhat = resultselect.getString(10);
									thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/"
											+ thoigiancapnhat.substring(5, 7) + "/" + thoigiancapnhat.substring(0, 4)
											+ " " + thoigiancapnhat.substring(11, 19);

									TableItem item = new TableItem(table, SWT.NONE);
									item.setText(new String[] { resultselect.getString(1), resultselect.getString(2),
											resultselect.getString(3), resultselect.getString(4),
											resultselect.getString(5), ngaytruc, resultselect.getString(7),
											resultselect.getString(8), resultselect.getString(9), thoigiancapnhat });
								}
								resultselect.close();
							}
						}
					} catch (SQLException sqe) {
						if(!comboNguoitruc.getText().isEmpty()) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Ngày hiện tại đã được phân công rồi!");
							} else if (ngonngu == 1) {
								thongbao.setText("Notification");
								thongbao.setMessage("The current day has been assigned!");
							} else if (ngonngu == 2) {
								thongbao.setText("通知");
								thongbao.setMessage("當前日期已分配");
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
					} catch (Exception se2) {
					}
				}
			}
		});

		// Button Cancel
		// --------------------------------------------------------------------------------------------------------
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comboNguoitruc.setText("");
			}
		});

		// cái này phải ở cuối cùng thì mới scroll được
		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

	}
}
