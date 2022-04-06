package manager;

import org.eclipse.swt.widgets.Display;
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
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
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

public class LichTrucPhong {

	protected Shell shell;
	private Table table;
	// kích thước màn hình
	private int sizemonitorx = Display.getDefault().getBounds().width;
	private int sizemonitory = Display.getDefault().getBounds().height;
	private int ngonngu = 0; // 0: Vietnam, 1: English, 2: China

	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;
	private String ngay = "", thang = "", nam = "";
	private int vitrixoa = 0;
	private String manguoidung = "admin";

	public static void main(String[] args) {
		try {
			LichTrucPhong window = new LichTrucPhong();
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
		shell.setText("Lịch trực phòng");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setMaximized(true);

		ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(composite);

		CLabel lbNgaybatdau = new CLabel(composite, SWT.NONE);
		lbNgaybatdau.setAlignment(SWT.RIGHT);
		lbNgaybatdau.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbNgaybatdau.setBounds(22, 26, 117, 25);
		lbNgaybatdau.setText("Ngày Bắt Đầu");

		DateTime datetimeNgaybatdau = new DateTime(composite, SWT.BORDER);
		datetimeNgaybatdau.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		datetimeNgaybatdau.setBounds(161, 26, 135, 25);

		CLabel lbngayketthuc = new CLabel(composite, SWT.NONE);
		lbngayketthuc.setAlignment(SWT.RIGHT);
		lbngayketthuc.setText("Ngày Kết Thúc");
		lbngayketthuc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbngayketthuc.setBounds(317, 26, 117, 25);

		DateTime datetimeNgayketthuc = new DateTime(composite, SWT.BORDER);
		datetimeNgayketthuc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		datetimeNgayketthuc.setBounds(454, 26, 135, 25);

		CLabel lbNguoitruc = new CLabel(composite, SWT.NONE);
		lbNguoitruc.setAlignment(SWT.RIGHT);
		lbNguoitruc.setText("Người Trực");
		lbNguoitruc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbNguoitruc.setBounds(22, 65, 117, 25);

		CCombo comboNguoitruc = new CCombo(composite, SWT.BORDER);
		comboNguoitruc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		comboNguoitruc.setBounds(161, 65, 273, 25);

		Button btnSearch = new Button(composite, SWT.NONE);
		btnSearch.setImage(SWTResourceManager.getImage(LichTrucPhong.class, "/manager/icon/search.png"));
		btnSearch.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnSearch.setBounds(454, 65, 135, 30);
		btnSearch.setText("Search");

		Button btnAdd = new Button(composite, SWT.NONE);
		btnAdd.setImage(SWTResourceManager.getImage(LichTrucPhong.class, "/manager/icon/add.png"));
		btnAdd.setText("Add");
		btnAdd.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnAdd.setBounds(597, 65, 107, 30);

		Button btnEdit = new Button(composite, SWT.NONE);
		btnEdit.setImage(SWTResourceManager.getImage(LichTrucPhong.class, "/manager/icon/edit.png"));
		btnEdit.setText("Edit");
		btnEdit.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnEdit.setBounds(714, 65, 107, 30);

		Button btnDelete = new Button(composite, SWT.NONE);
		btnDelete.setImage(SWTResourceManager.getImage(LichTrucPhong.class, "/manager/icon/delete.png"));
		btnDelete.setText("Delete");
		btnDelete.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnDelete.setBounds(831, 65, 107, 30);

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderBackground(SWTResourceManager.getColor(255, 165, 0));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		// table.setBounds(20, 108, 1246, 584);
		table.setBounds(20, 108, sizemonitorx - 40, sizemonitory - 200);
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
		btnSave.setText("Save");
		btnSave.setImage(SWTResourceManager.getImage(LichTrucPhong.class, "/manager/icon/save.png"));
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnSave.setBounds(994, 65, 92, 30);

		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setText("Cancel");
		btnCancel.setImage(SWTResourceManager.getImage(LichTrucPhong.class, "/manager/icon/cancel.png"));
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnCancel.setBounds(1092, 65, 107, 30);

		btnSave.setVisible(false);
		btnCancel.setVisible(false);

		// Thay đổi ngôn ngữ
		// -------------------------------------------------------------------------
		if (ngonngu == 0) {
			shell.setText("Lịch trực phòng");
			lbNgaybatdau.setText("Ngày Bắt Đầu");
			lbngayketthuc.setText("Ngày Kết Thúc");
			lbNguoitruc.setText("Người Trực");
			btnSearch.setText("Tìm Kiếm");
			btnAdd.setText("Thêm");
			btnEdit.setText("Sửa");
			btnDelete.setText("Xóa");
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
		} else if (ngonngu == 1) {
			shell.setText("Room calendar");
			lbNgaybatdau.setText("Start Date");
			lbngayketthuc.setText("End Date");
			lbNguoitruc.setText("Person On Duty");
			btnSearch.setText("Search");
			btnAdd.setText("Add");
			btnEdit.setText("Edit");
			btnDelete.setText("Delete");
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
		} else if (ngonngu == 2) {
			shell.setText("房間日曆");
			lbNgaybatdau.setText("開始日期");
			lbngayketthuc.setText("結束日期");
			lbNguoitruc.setText("值班人員");
			btnSearch.setText("搜索");
			btnAdd.setText("加");
			btnEdit.setText("編輯");
			btnDelete.setText("刪除");
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
		}

		// Lấy dữ liệu cho combo người trực
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
				// nothing we can do
			}
		}

		// Button Search
		// --------------------------------------------------------------------------------------------------------
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String ngaystart = (datetimeNgaybatdau.getDay() + "").length() == 2
							? datetimeNgaybatdau.getDay() + ""
							: "0" + datetimeNgaybatdau.getDay();
					String thangstart = ((datetimeNgaybatdau.getMonth() + 1) + "").length() == 2
							? (datetimeNgaybatdau.getMonth() + 1) + ""
							: "0" + (datetimeNgaybatdau.getMonth() + 1);
					String ngaybatdau = datetimeNgaybatdau.getYear() + "" + thangstart + ngaystart;

					String ngayend = (datetimeNgayketthuc.getDay() + "").length() == 2
							? datetimeNgayketthuc.getDay() + ""
							: "0" + datetimeNgayketthuc.getDay();
					String thangend = ((datetimeNgayketthuc.getMonth() + 1) + "").length() == 2
							? (datetimeNgayketthuc.getMonth() + 1) + ""
							: "0" + (datetimeNgayketthuc.getMonth() + 1);
					String ngayketthuc = datetimeNgayketthuc.getYear() + "" + thangend + ngayend;

					String nguoitruc = "";
					String selectLichtrucphong = "SELECT MaNguoiDung FROM NguoiDung WHERE MaNhomNguoiDung='it' AND TenNguoiDung=N'"
							+ comboNguoitruc.getText() + "'";
					ResultSet resulttrucphong = statement.executeQuery(selectLichtrucphong);

					while (resulttrucphong.next()) {
						nguoitruc = resulttrucphong.getString(1);
					}
					resulttrucphong.close();
					nguoitruc = comboNguoitruc.getText().isEmpty() ? "" : " AND NguoiTrucThay='" + nguoitruc + "'";

					String select = "SELECT Nam,Thang,Ngay,NgayTrongTuan,Tuan,NgayTruc,ND.TenNguoiDung AS 'Nguoi Truc',NguoiDung.TenNguoiDung AS 'Nguoi Phan Cong',NDNTT.TenNguoiDung AS 'NguoiTrucThay',ThoiGianCapNhat FROM LichTrucPhong,NguoiDung,NguoiDung AS NDNTT,NguoiDung AS ND WHERE LichTrucPhong.NguoiTruc=ND.MaNguoiDung AND LichTrucPhong.NguoiPhanCong=NguoiDung.MaNguoiDung AND LichTrucPhong.NguoiTrucThay=NDNTT.MaNguoiDung AND NgayTruc BETWEEN '"
							+ ngaybatdau + "' AND '" + ngayketthuc + "'" + nguoitruc + " ORDER BY NgayTruc DESC";

					ResultSet result = statement.executeQuery(select);
					table.removeAll();

					while (result.next()) {
						TableItem item = new TableItem(table, SWT.NONE);
						String ngaytrucphong = result.getString(6).toString();
						ngaytrucphong = ngaytrucphong.substring(8, 10) + "/" + ngaytrucphong.substring(5, 7) + "/"
								+ ngaytrucphong.substring(0, 4);

						String thoigiancapnhat = result.getString(10);
						thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/" + thoigiancapnhat.substring(5, 7) + "/"
								+ thoigiancapnhat.substring(0, 4) + " " + thoigiancapnhat.substring(11, 19);

						item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
								result.getString(4), result.getString(5), ngaytrucphong, result.getString(7),
								result.getString(8), result.getString(9), thoigiancapnhat });
					}
					result.close();
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

		// Button Add
		// --------------------------------------------------------------------------------------------------------
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ThemLichTrucPhong themlich = new ThemLichTrucPhong();
				themlich.createContents(manguoidung, ngonngu,db_url);
				shell.setEnabled(false);
				themlich.open();
				shell.setEnabled(true);
			}
		});

		// Button Edit
		// --------------------------------------------------------------------------------------------------------
		btnEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					TableItem[] item = table.getSelection();
					SuaLichTrucPhong sualich = new SuaLichTrucPhong();
					sualich.createContents(item[0].getText(2), item[0].getText(1), item[0].getText(),
							item[0].getText(3), item[0].getText(6), ngonngu,db_url);
					shell.setEnabled(false);
					sualich.open();
					shell.setEnabled(true);
					// cập nhật lại dữ liệu sau khi thay đổi
					// giống Search
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						String ngaystart = (datetimeNgaybatdau.getDay() + "").length() == 2
								? datetimeNgaybatdau.getDay() + ""
								: "0" + datetimeNgaybatdau.getDay();
						String thangstart = ((datetimeNgaybatdau.getMonth() + 1) + "").length() == 2
								? (datetimeNgaybatdau.getMonth() + 1) + ""
								: "0" + (datetimeNgaybatdau.getMonth() + 1);
						String ngaybatdau = datetimeNgaybatdau.getYear() + "" + thangstart + ngaystart;

						String ngayend = (datetimeNgayketthuc.getDay() + "").length() == 2
								? datetimeNgayketthuc.getDay() + ""
								: "0" + datetimeNgayketthuc.getDay();
						String thangend = ((datetimeNgayketthuc.getMonth() + 1) + "").length() == 2
								? (datetimeNgayketthuc.getMonth() + 1) + ""
								: "0" + (datetimeNgayketthuc.getMonth() + 1);
						String ngayketthuc = datetimeNgayketthuc.getYear() + "" + thangend + ngayend;

						String nguoitruc = "";
						String selectLichtrucphong = "SELECT SoThe FROM DanhSachTrucPhong WHERE HoTen=N'"
								+ comboNguoitruc.getText() + "'";
						ResultSet resulttrucphong = statement.executeQuery(selectLichtrucphong);

						while (resulttrucphong.next()) {
							nguoitruc = resulttrucphong.getString(1);
						}
						resulttrucphong.close();
						nguoitruc = comboNguoitruc.getText().isEmpty() ? "" : " AND NguoiTrucThay='" + nguoitruc + "'";

						String select = "SELECT Nam,Thang,Ngay,NgayTrongTuan,Tuan,NgayTruc,DanhSachTrucPhong.HoTen AS 'Nguoi Truc',NguoiDung.TenNguoiDung AS 'Nguoi Phan Cong',DSTP.HoTen AS 'NguoiTrucThay',ThoiGianCapNhat FROM LichTrucPhong,DanhSachTrucPhong,NguoiDung,DanhSachTrucPhong AS DSTP WHERE LichTrucPhong.NguoiTruc=DanhSachTrucPhong.SoThe AND LichTrucPhong.NguoiPhanCong=NguoiDung.MaNguoiDung AND LichTrucPhong.NguoiTrucThay=DSTP.SoThe AND NgayTruc BETWEEN '"
								+ ngaybatdau + "' AND '" + ngayketthuc + "'" + nguoitruc + " ORDER BY NgayTruc DESC";

						ResultSet result = statement.executeQuery(select);
						table.removeAll();

						while (result.next()) {
							TableItem item2 = new TableItem(table, SWT.NONE);
							String ngaytrucphong = result.getString(6).toString();
							ngaytrucphong = ngaytrucphong.substring(8, 10) + "/" + ngaytrucphong.substring(5, 7) + "/"
									+ ngaytrucphong.substring(0, 4);

							String thoigiancapnhat = result.getString(10);
							thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/" + thoigiancapnhat.substring(5, 7)
									+ "/" + thoigiancapnhat.substring(0, 4) + " " + thoigiancapnhat.substring(11, 19);

							item2.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
									result.getString(4), result.getString(5), ngaytrucphong, result.getString(7),
									result.getString(8), result.getString(9), thoigiancapnhat });
						}
						result.close();
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

				} catch (ArrayIndexOutOfBoundsException ee) {
				}
			}
		});

		// Button Delete
		// --------------------------------------------------------------------------------------------------------
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					TableItem[] item = table.getSelection();
					// Ngày, tháng, năm
					nam = item[0].getText();
					thang = item[0].getText(1);
					ngay = item[0].getText(2);
					vitrixoa = table.getSelectionIndex();
					btnSave.setVisible(true);
					btnCancel.setVisible(true);
					btnAdd.setEnabled(false);
					btnEdit.setEnabled(false);
					btnSearch.setEnabled(false);
				} catch (Exception exc) {
					btnSave.setVisible(false);
					btnCancel.setVisible(false);
					btnAdd.setEnabled(true);
					btnEdit.setEnabled(true);
					btnSearch.setEnabled(true);
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

					String delete = "DELETE LichTrucPhong WHERE Nam='" + nam + "' AND Thang='" + thang + "' AND Ngay='"
							+ ngay + "'";
					int xoa = statement.executeUpdate(delete);
					if (xoa > 0) {
						table.remove(vitrixoa);
						btnSave.setVisible(false);
						btnCancel.setVisible(false);
						btnAdd.setEnabled(true);
						btnEdit.setEnabled(true);
						btnSearch.setEnabled(true);
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
				btnSave.setVisible(false);
				btnCancel.setVisible(false);
				btnAdd.setEnabled(true);
				btnEdit.setEnabled(true);
				btnSearch.setEnabled(true);
			}
		});

		// cái này phải ở cuối cùng thì mới scroll được
		// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

	}

	// hien trong CtabFolder
	// =============================================================================================================================
	protected void createContentsTabfolder(CTabFolder tabfolder, Shell shell, String manguoidung, int ngonngu,String chuoiketnoi) {
		db_url=chuoiketnoi;
		this.manguoidung = manguoidung;
		this.ngonngu = ngonngu;

		CTabItem itemtab = new CTabItem(tabfolder, SWT.CLOSE);
		itemtab.setText("Lịch trực phòng");
		Composite composite = new Composite(tabfolder, SWT.NONE);
		itemtab.setControl(composite);

		CLabel lbNgaybatdau = new CLabel(composite, SWT.NONE);
		lbNgaybatdau.setAlignment(SWT.RIGHT);
		lbNgaybatdau.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbNgaybatdau.setBounds(22, 26, 117, 25);
		lbNgaybatdau.setText("Ngày Bắt Đầu");

		DateTime datetimeNgaybatdau = new DateTime(composite, SWT.BORDER);
		datetimeNgaybatdau.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		datetimeNgaybatdau.setBounds(161, 26, 135, 25);

		CLabel lbngayketthuc = new CLabel(composite, SWT.NONE);
		lbngayketthuc.setAlignment(SWT.RIGHT);
		lbngayketthuc.setText("Ngày Kết Thúc");
		lbngayketthuc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbngayketthuc.setBounds(317, 26, 117, 25);

		DateTime datetimeNgayketthuc = new DateTime(composite, SWT.BORDER);
		datetimeNgayketthuc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		datetimeNgayketthuc.setBounds(454, 26, 135, 25);

		CLabel lbNguoitruc = new CLabel(composite, SWT.NONE);
		lbNguoitruc.setAlignment(SWT.RIGHT);
		lbNguoitruc.setText("Người Trực");
		lbNguoitruc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbNguoitruc.setBounds(22, 65, 117, 25);

		CCombo comboNguoitruc = new CCombo(composite, SWT.BORDER);
		comboNguoitruc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		comboNguoitruc.setBounds(161, 65, 273, 25);

		Button btnSearch = new Button(composite, SWT.NONE);
		btnSearch.setImage(SWTResourceManager.getImage(LichTrucPhong.class, "/manager/icon/search.png"));
		btnSearch.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnSearch.setBounds(454, 65, 135, 30);
		btnSearch.setText("Search");

		Button btnAdd = new Button(composite, SWT.NONE);
		btnAdd.setImage(SWTResourceManager.getImage(LichTrucPhong.class, "/manager/icon/add.png"));
		btnAdd.setText("Add");
		btnAdd.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnAdd.setBounds(597, 65, 107, 30);

		Button btnEdit = new Button(composite, SWT.NONE);
		btnEdit.setImage(SWTResourceManager.getImage(LichTrucPhong.class, "/manager/icon/edit.png"));
		btnEdit.setText("Edit");
		btnEdit.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnEdit.setBounds(714, 65, 107, 30);

		Button btnDelete = new Button(composite, SWT.NONE);
		btnDelete.setImage(SWTResourceManager.getImage(LichTrucPhong.class, "/manager/icon/delete.png"));
		btnDelete.setText("Delete");
		btnDelete.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnDelete.setBounds(831, 65, 107, 30);

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderBackground(SWTResourceManager.getColor(255, 165, 0));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		table.setBounds(20, 108, sizemonitorx - 40, sizemonitory - 225);
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
		btnSave.setText("Save");
		btnSave.setImage(SWTResourceManager.getImage(LichTrucPhong.class, "/manager/icon/save.png"));
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnSave.setBounds(994, 65, 92, 30);

		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setText("Cancel");
		btnCancel.setImage(SWTResourceManager.getImage(LichTrucPhong.class, "/manager/icon/cancel.png"));
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnCancel.setBounds(1092, 65, 107, 30);

		btnSave.setVisible(false);
		btnCancel.setVisible(false);

		// Thay đổi ngôn ngữ
		// -------------------------------------------------------------------------
		if (ngonngu == 0) {
			itemtab.setText("Lịch trực phòng");
			lbNgaybatdau.setText("Ngày Bắt Đầu");
			lbngayketthuc.setText("Ngày Kết Thúc");
			lbNguoitruc.setText("Người Trực");
			btnSearch.setText("Tìm Kiếm");
			btnAdd.setText("Thêm");
			btnEdit.setText("Sửa");
			btnDelete.setText("Xóa");
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
		} else if (ngonngu == 1) {
			itemtab.setText("Room calendar");
			lbNgaybatdau.setText("Start Date");
			lbngayketthuc.setText("End Date");
			lbNguoitruc.setText("Person On Duty");
			btnSearch.setText("Search");
			btnAdd.setText("Add");
			btnEdit.setText("Edit");
			btnDelete.setText("Delete");
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
		} else if (ngonngu == 2) {
			itemtab.setText("房間日曆");
			lbNgaybatdau.setText("開始日期");
			lbngayketthuc.setText("結束日期");
			lbNguoitruc.setText("值班人員");
			btnSearch.setText("搜索");
			btnAdd.setText("加");
			btnEdit.setText("編輯");
			btnDelete.setText("刪除");
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
		}

		// Lấy dữ liệu cho combo người trực
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
				// nothing we can do
			}
		}

		// Button Search
		// --------------------------------------------------------------------------------------------------------
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String ngaystart = (datetimeNgaybatdau.getDay() + "").length() == 2
							? datetimeNgaybatdau.getDay() + ""
							: "0" + datetimeNgaybatdau.getDay();
					String thangstart = ((datetimeNgaybatdau.getMonth() + 1) + "").length() == 2
							? (datetimeNgaybatdau.getMonth() + 1) + ""
							: "0" + (datetimeNgaybatdau.getMonth() + 1);
					String ngaybatdau = datetimeNgaybatdau.getYear() + "" + thangstart + ngaystart;

					String ngayend = (datetimeNgayketthuc.getDay() + "").length() == 2
							? datetimeNgayketthuc.getDay() + ""
							: "0" + datetimeNgayketthuc.getDay();
					String thangend = ((datetimeNgayketthuc.getMonth() + 1) + "").length() == 2
							? (datetimeNgayketthuc.getMonth() + 1) + ""
							: "0" + (datetimeNgayketthuc.getMonth() + 1);
					String ngayketthuc = datetimeNgayketthuc.getYear() + "" + thangend + ngayend;

					String nguoitruc = "";
					String selectLichtrucphong = "SELECT MaNguoiDung FROM NguoiDung WHERE MaNhomNguoiDung='it' AND TenNguoiDung=N'"
							+ comboNguoitruc.getText() + "'";
					ResultSet resulttrucphong = statement.executeQuery(selectLichtrucphong);

					while (resulttrucphong.next()) {
						nguoitruc = resulttrucphong.getString(1);
					}
					resulttrucphong.close();
					nguoitruc = comboNguoitruc.getText().isEmpty() ? "" : " AND NguoiTrucThay='" + nguoitruc + "'";

					String select = "SELECT Nam,Thang,Ngay,NgayTrongTuan,Tuan,NgayTruc,ND.TenNguoiDung AS 'Nguoi Truc',NguoiDung.TenNguoiDung AS 'Nguoi Phan Cong',NDNTT.TenNguoiDung AS 'NguoiTrucThay',ThoiGianCapNhat FROM LichTrucPhong,NguoiDung,NguoiDung AS NDNTT,NguoiDung AS ND WHERE LichTrucPhong.NguoiTruc=ND.MaNguoiDung AND LichTrucPhong.NguoiPhanCong=NguoiDung.MaNguoiDung AND LichTrucPhong.NguoiTrucThay=NDNTT.MaNguoiDung AND NgayTruc BETWEEN '"
							+ ngaybatdau + "' AND '" + ngayketthuc + "'" + nguoitruc + " ORDER BY NgayTruc DESC";

					ResultSet result = statement.executeQuery(select);
					table.removeAll();

					while (result.next()) {
						TableItem item = new TableItem(table, SWT.NONE);
						String ngaytrucphong = result.getString(6).toString();
						ngaytrucphong = ngaytrucphong.substring(8, 10) + "/" + ngaytrucphong.substring(5, 7) + "/"
								+ ngaytrucphong.substring(0, 4);

						String thoigiancapnhat = result.getString(10);
						thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/" + thoigiancapnhat.substring(5, 7) + "/"
								+ thoigiancapnhat.substring(0, 4) + " " + thoigiancapnhat.substring(11, 19);

						item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
								result.getString(4), result.getString(5), ngaytrucphong, result.getString(7),
								result.getString(8), result.getString(9), thoigiancapnhat });
					}
					result.close();
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

		// Button Add
		// --------------------------------------------------------------------------------------------------------
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ThemLichTrucPhong themlich = new ThemLichTrucPhong();
				themlich.createContents(manguoidung, ngonngu,db_url);
				shell.setEnabled(false);
				themlich.open();
				shell.setEnabled(true);
			}
		});

		// Button Edit
		// --------------------------------------------------------------------------------------------------------
		btnEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					TableItem[] item = table.getSelection();
					SuaLichTrucPhong sualich = new SuaLichTrucPhong();
					sualich.createContents(item[0].getText(2), item[0].getText(1), item[0].getText(),
							item[0].getText(3), item[0].getText(6), ngonngu,db_url);
					shell.setEnabled(false);
					sualich.open();
					shell.setEnabled(true);
					// cập nhật lại dữ liệu sau khi thay đổi
					// giống Search
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						String ngaystart = (datetimeNgaybatdau.getDay() + "").length() == 2
								? datetimeNgaybatdau.getDay() + ""
								: "0" + datetimeNgaybatdau.getDay();
						String thangstart = ((datetimeNgaybatdau.getMonth() + 1) + "").length() == 2
								? (datetimeNgaybatdau.getMonth() + 1) + ""
								: "0" + (datetimeNgaybatdau.getMonth() + 1);
						String ngaybatdau = datetimeNgaybatdau.getYear() + "" + thangstart + ngaystart;

						String ngayend = (datetimeNgayketthuc.getDay() + "").length() == 2
								? datetimeNgayketthuc.getDay() + ""
								: "0" + datetimeNgayketthuc.getDay();
						String thangend = ((datetimeNgayketthuc.getMonth() + 1) + "").length() == 2
								? (datetimeNgayketthuc.getMonth() + 1) + ""
								: "0" + (datetimeNgayketthuc.getMonth() + 1);
						String ngayketthuc = datetimeNgayketthuc.getYear() + "" + thangend + ngayend;

						String nguoitruc = "";
						String selectLichtrucphong = "SELECT SoThe FROM DanhSachTrucPhong WHERE HoTen=N'"
								+ comboNguoitruc.getText() + "'";
						ResultSet resulttrucphong = statement.executeQuery(selectLichtrucphong);

						while (resulttrucphong.next()) {
							nguoitruc = resulttrucphong.getString(1);
						}
						resulttrucphong.close();
						nguoitruc = comboNguoitruc.getText().isEmpty() ? "" : " AND NguoiTrucThay='" + nguoitruc + "'";

						String select = "SELECT Nam,Thang,Ngay,NgayTrongTuan,Tuan,NgayTruc,DanhSachTrucPhong.HoTen AS 'Nguoi Truc',NguoiDung.TenNguoiDung AS 'Nguoi Phan Cong',DSTP.HoTen AS 'NguoiTrucThay',ThoiGianCapNhat FROM LichTrucPhong,DanhSachTrucPhong,NguoiDung,DanhSachTrucPhong AS DSTP WHERE LichTrucPhong.NguoiTruc=DanhSachTrucPhong.SoThe AND LichTrucPhong.NguoiPhanCong=NguoiDung.MaNguoiDung AND LichTrucPhong.NguoiTrucThay=DSTP.SoThe AND NgayTruc BETWEEN '"
								+ ngaybatdau + "' AND '" + ngayketthuc + "'" + nguoitruc + " ORDER BY NgayTruc DESC";

						ResultSet result = statement.executeQuery(select);
						table.removeAll();

						while (result.next()) {
							TableItem item2 = new TableItem(table, SWT.NONE);
							String ngaytrucphong = result.getString(6).toString();
							ngaytrucphong = ngaytrucphong.substring(8, 10) + "/" + ngaytrucphong.substring(5, 7) + "/"
									+ ngaytrucphong.substring(0, 4);

							String thoigiancapnhat = result.getString(10);
							thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/" + thoigiancapnhat.substring(5, 7)
									+ "/" + thoigiancapnhat.substring(0, 4) + " " + thoigiancapnhat.substring(11, 19);

							item2.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
									result.getString(4), result.getString(5), ngaytrucphong, result.getString(7),
									result.getString(8), result.getString(9), thoigiancapnhat });
						}
						result.close();
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
				} catch (ArrayIndexOutOfBoundsException ee) {
				}
			}
		});

		// Button Delete
		// --------------------------------------------------------------------------------------------------------
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					TableItem[] item = table.getSelection();
					// Ngày, tháng, năm
					nam = item[0].getText();
					thang = item[0].getText(1);
					ngay = item[0].getText(2);
					vitrixoa = table.getSelectionIndex();
					btnSave.setVisible(true);
					btnCancel.setVisible(true);
					btnAdd.setEnabled(false);
					btnEdit.setEnabled(false);
					btnSearch.setEnabled(false);
				} catch (Exception exc) {
					btnSave.setVisible(false);
					btnCancel.setVisible(false);
					btnAdd.setEnabled(true);
					btnEdit.setEnabled(true);
					btnSearch.setEnabled(true);
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

					String delete = "DELETE LichTrucPhong WHERE Nam='" + nam + "' AND Thang='" + thang + "' AND Ngay='"
							+ ngay + "'";
					int xoa = statement.executeUpdate(delete);
					if (xoa > 0) {
						table.remove(vitrixoa);
						btnSave.setVisible(false);
						btnCancel.setVisible(false);
						btnAdd.setEnabled(true);
						btnEdit.setEnabled(true);
						btnSearch.setEnabled(true);
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
				btnSave.setVisible(false);
				btnCancel.setVisible(false);
				btnAdd.setEnabled(true);
				btnEdit.setEnabled(true);
				btnSearch.setEnabled(true);
			}
		});

	}
}
