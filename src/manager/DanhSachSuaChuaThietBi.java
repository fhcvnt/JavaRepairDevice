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
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DanhSachSuaChuaThietBi {

	protected Shell shell;
	private Table table;
	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;

	// Kích thước màn hình
	private int sizemonitorx = Display.getDefault().getBounds().width;
	private int sizemonitory = Display.getDefault().getBounds().height;
	private String phanbiet = "";
	private int vitrixoa = -1;
	private boolean nguoitrucphong = false; // hom nay ban co phai la nguoi truc phong khong

	public static void main(String[] args) {
		try {
			DanhSachSuaChuaThietBi window = new DanhSachSuaChuaThietBi();
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
		createContents("21608", "IT", 0,db_url);
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
	protected void createContents(String manguoidung, String manhomnguoidung, int ngonngu,String chuoiketnoi) {
		db_url=chuoiketnoi;
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage(DanhSachSuaChuaThietBi.class, "/manager/Images/repair256.ico"));
		shell.setSize(1360, 760);
		shell.setText("Danh sách sửa chữa thiết bị");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setMaximized(true);

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		CLabel lbBatdau = new CLabel(composite, SWT.NONE);
		lbBatdau.setAlignment(SWT.RIGHT);
		lbBatdau.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbBatdau.setBounds(10, 20, 114, 25);
		lbBatdau.setText("Bắt Đầu");

		DateTime dateTimeBatdau = new DateTime(composite, SWT.BORDER);
		dateTimeBatdau.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		dateTimeBatdau.setBounds(145, 20, 128, 25);

		CLabel lbKetthuc = new CLabel(composite, SWT.NONE);
		lbKetthuc.setAlignment(SWT.RIGHT);
		lbKetthuc.setText("Kết Thúc");
		lbKetthuc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbKetthuc.setBounds(279, 20, 114, 25);

		DateTime dateTimeKetthuc = new DateTime(composite, SWT.BORDER);
		dateTimeKetthuc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		dateTimeKetthuc.setBounds(410, 20, 128, 25);

		CLabel lbDonvi = new CLabel(composite, SWT.NONE);
		lbDonvi.setAlignment(SWT.RIGHT);
		lbDonvi.setText("Đơn Vị");
		lbDonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbDonvi.setBounds(544, 20, 114, 25);

		CCombo comboDonvi = new CCombo(composite, SWT.BORDER);
		comboDonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		comboDonvi.setBounds(674, 20, 263, 25);

		Button btnradioChuahoanthanh = new Button(composite, SWT.RADIO);
		btnradioChuahoanthanh.setSelection(true);
		btnradioChuahoanthanh.setText("Chưa Hoàn Thành");
		btnradioChuahoanthanh.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnradioChuahoanthanh.setBounds(20, 56, 149, 25);

		Button btnradioHoanthanh = new Button(composite, SWT.RADIO);
		btnradioHoanthanh.setText("Hoàn Thành");
		btnradioHoanthanh.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnradioHoanthanh.setBounds(175, 56, 114, 25);

		Button btnradioTatca = new Button(composite, SWT.RADIO);
		btnradioTatca.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnradioTatca.setBounds(299, 56, 83, 25);
		btnradioTatca.setText("Tất Cả");

		Button btnSearch = new Button(composite, SWT.NONE);
		btnSearch.setImage(SWTResourceManager.getImage(DanhSachSuaChuaThietBi.class, "/manager/icon/search.png"));
		btnSearch.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnSearch.setBounds(388, 59, 128, 30);
		btnSearch.setText("Search");

		Button btnDelete = new Button(composite, SWT.NONE);
		btnDelete.setImage(SWTResourceManager.getImage(DanhSachSuaChuaThietBi.class, "/manager/icon/delete.png"));
		btnDelete.setText("Delete");
		btnDelete.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnDelete.setBounds(522, 59, 113, 30);

		Button btnPhancong = new Button(composite, SWT.NONE);
		btnPhancong.setImage(SWTResourceManager.getImage(DanhSachSuaChuaThietBi.class, "/manager/icon/phan cong.png"));
		btnPhancong.setText("Phân Công");
		btnPhancong.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnPhancong.setBounds(641, 59, 138, 30);

		Button btnSave = new Button(composite, SWT.NONE);
		btnSave.setText("Save");
		btnSave.setImage(SWTResourceManager.getImage(DanhSachSuaChuaThietBi.class, "/manager/icon/save25.png"));
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnSave.setBounds(785, 59, 96, 30);

		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setText("Cancel");
		btnCancel.setImage(SWTResourceManager.getImage(DanhSachSuaChuaThietBi.class, "/manager/icon/cancel.png"));
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnCancel.setBounds(887, 59, 96, 30);

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderBackground(SWTResourceManager.getColor(255, 153, 51));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		table.setBounds(10, 95, 1310, 580);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setSize(sizemonitorx - 20, sizemonitory - 170);

		TableColumn tbclTT = new TableColumn(table, SWT.NONE);
		// tbclTT.setWidth(52);
		tbclTT.setWidth(0);
		tbclTT.setResizable(false);
		tbclTT.setText("TT");

		TableColumn tbclNguoidung = new TableColumn(table, SWT.NONE);
		tbclNguoidung.setWidth(100);
		tbclNguoidung.setText("Người Dùng");

		TableColumn btclDonvi = new TableColumn(table, SWT.NONE);
		btclDonvi.setWidth(100);
		btclDonvi.setText("Đơn Vị");

		TableColumn tbclThietbi = new TableColumn(table, SWT.NONE);
		tbclThietbi.setWidth(100);
		tbclThietbi.setText("Thiết Bị");

		TableColumn tbclNoidung = new TableColumn(table, SWT.NONE);
		tbclNoidung.setWidth(100);
		tbclNoidung.setText("Nội Dung");

		TableColumn tbclThoigianbao = new TableColumn(table, SWT.NONE);
		tbclThoigianbao.setWidth(112);
		tbclThoigianbao.setText("Thời Gian Báo");

		TableColumn tbclHuy = new TableColumn(table, SWT.NONE);
		tbclHuy.setWidth(53);
		tbclHuy.setText("Hủy");

		TableColumn tbclThoigianhuy = new TableColumn(table, SWT.NONE);
		tbclThoigianhuy.setWidth(116);
		tbclThoigianhuy.setText("Thời Gian Hủy");

		TableColumn tbclNguoiphancong = new TableColumn(table, SWT.NONE);
		tbclNguoiphancong.setWidth(139);
		tbclNguoiphancong.setText("Người Phân Công");

		TableColumn tbclNguoiduocphancong = new TableColumn(table, SWT.NONE);
		tbclNguoiduocphancong.setWidth(176);
		tbclNguoiduocphancong.setText("Người Được Phân Công");

		TableColumn tbclTrangthai = new TableColumn(table, SWT.NONE);
		tbclTrangthai.setWidth(100);
		tbclTrangthai.setText("Trạng Thái");

		TableColumn tbclThoigiancapnhat = new TableColumn(table, SWT.NONE);
		tbclThoigiancapnhat.setWidth(148);
		tbclThoigiancapnhat.setText("Thời Gian Cập Nhật");

		TableColumn tbclHoanthanh = new TableColumn(table, SWT.NONE);
		tbclHoanthanh.setWidth(100);
		tbclHoanthanh.setText("Hoàn Thành");

		btnPhancong.setVisible(false);
		btnDelete.setVisible(false);
		btnCancel.setVisible(false);
		btnSave.setVisible(false);

		// Ngôn ngữ
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		if (ngonngu == 0) {
			shell.setText("Danh sách sửa chữa thiết bị");
			lbBatdau.setText("Bắt Đầu");
			lbKetthuc.setText("Kết Thúc");
			lbDonvi.setText("Đơn Vị");
			btnradioChuahoanthanh.setText("Chưa Hoàn Thành");
			btnradioHoanthanh.setText("Hoàn Thành");
			btnradioTatca.setText("Tất Cả");
			btnSearch.setText("Tìm Kiếm");
			btnDelete.setText("Xóa");
			btnPhancong.setText("Phân Công");
			btnSave.setText("Lưu");
			btnCancel.setText("Hủy");
			tbclTT.setText("TT");
			tbclNguoidung.setText("Người Dùng");
			btclDonvi.setText("Đơn Vị");
			tbclThietbi.setText("Thiết Bị");
			tbclNoidung.setText("Nội Dung");
			tbclThoigianbao.setText("Thời Gian Báo");
			tbclHuy.setText("Hủy");
			tbclThoigianhuy.setText("Thời Gian Hủy");
			tbclNguoiphancong.setText("Người Phân Công");
			tbclNguoiduocphancong.setText("Người Được Phân Công");
			tbclTrangthai.setText("Trạng Thái");
			tbclThoigiancapnhat.setText("Thời Gian Cập Nhật");
			tbclHoanthanh.setText("Hoàn Thành");
		} else if (ngonngu == 1) {
			shell.setText("Repair devices list");
			lbBatdau.setText("Start");
			lbKetthuc.setText("End");
			lbDonvi.setText("Department");
			btnradioChuahoanthanh.setText("Incomplete");
			btnradioHoanthanh.setText("Complete");
			btnradioTatca.setText("All");
			btnSearch.setText("Search");
			btnDelete.setText("Delete");
			btnPhancong.setText("Assign");
			btnSave.setText("Save");
			btnCancel.setText("Cancel");
			tbclTT.setText("TT");
			tbclNguoidung.setText("User");
			btclDonvi.setText("Department");
			tbclThietbi.setText("Devices");
			tbclNoidung.setText("Content");
			tbclThoigianbao.setText("Notice time");
			tbclHuy.setText("Cancel");
			tbclThoigianhuy.setText("Cancellation time");
			tbclNguoiphancong.setText("Taskmaster");
			tbclNguoiduocphancong.setText("The Person Assigned");
			tbclTrangthai.setText("Status");
			tbclThoigiancapnhat.setText("Time To Update");
			tbclHoanthanh.setText("Complete");
		} else if (ngonngu == 2) {
			shell.setText("維修設備清單");
			lbBatdau.setText("開始");
			lbKetthuc.setText("結束");
			lbDonvi.setText("部門");
			btnradioChuahoanthanh.setText("殘缺");
			btnradioHoanthanh.setText("完成");
			btnradioTatca.setText("全部");
			btnSearch.setText("搜索");
			btnDelete.setText("刪除");
			btnPhancong.setText("分配");
			btnSave.setText("儲存");
			btnCancel.setText("取消");
			tbclTT.setText("TT");
			tbclNguoidung.setText("使用者");
			btclDonvi.setText("部門");
			tbclThietbi.setText("設備");
			tbclNoidung.setText("內容");
			tbclThoigianbao.setText("通知時間");
			tbclHuy.setText("取消");
			tbclThoigianhuy.setText("取消時間");
			tbclNguoiphancong.setText("工作分配者");
			tbclNguoiduocphancong.setText("分配的人");
			tbclTrangthai.setText("狀態");
			tbclThoigiancapnhat.setText("是時候更新了");
			tbclHoanthanh.setText("完成");
		}

		// Kiểm tra xem bạn có phải là người trực phòng ngày hiện tại không
		// -------------------------------------------------------------------------------------------------------------------------
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			String ngay = "0" + java.time.LocalDateTime.now().getDayOfMonth();
			ngay = ngay.substring(ngay.length() - 2);
			String thang = "0" + (java.time.LocalDateTime.now().getMonthValue());
			thang = thang.substring(thang.length() - 2);
			String nam = "" + java.time.LocalDateTime.now().getYear();
			String today = nam + thang + ngay;
			String selectnguoitrucphong = "SELECT NguoiTrucThay FROM LichTrucPhong WHERE NgayTruc='" + today + "'";
			ResultSet ketquanguoitrucphong = statement.executeQuery(selectnguoitrucphong);
			while (ketquanguoitrucphong.next()) {
				if (ketquanguoitrucphong.getString(1).compareToIgnoreCase(manguoidung) == 0) {
					nguoitrucphong = true;
				}
			}
			ketquanguoitrucphong.close();
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

		// Chỉ cho người dùng Admin hoặc người trực phòng phân công mà thôi
		// ==================================================================================================================================
		if ((manhomnguoidung.compareToIgnoreCase("admin") == 0 || nguoitrucphong)) {
			btnPhancong.setVisible(true);
			btnDelete.setVisible(true);
		}

		// Xu ly du lieu luc dau
		// -----------------------------------------------------------------------------------------
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

			// Lấy dữ liệu cho table
			String ngay = "0" + java.time.LocalDateTime.now().getDayOfMonth();
			ngay = ngay.substring(ngay.length() - 2);
			String thang = "0" + (java.time.LocalDateTime.now().getMonthValue());
			thang = thang.substring(thang.length() - 2);
			String nam = "" + java.time.LocalDateTime.now().getYear();
			// Lấy ngày hiện tại theo dạng năm tháng ngày: 20200227 (2020-02-27)
			String today = nam + thang + ngay;
			String select = "";
			select = "SELECT PhanBiet,NguoiDung.TenNguoiDung,DonVi.TenDonVi,ThietBi.TenThietBi,NoiDung,ThoiGianBao,CASE Huy WHEN 0 THEN N'Chưa hủy' ELSE N'Đã hủy' END AS Huy,ThoiGianHuy,ND.TenNguoiDung AS NguoiPhanCong,ND2.TenNguoiDung AS NguoiDuocPhanCong,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,ThoiGianCapNhat,CASE DaHoanTat WHEN 0 THEN N'Chưa hoàn thành' ELSE N'Hoàn thành' END AS DaHoanTat FROM BaoSuaThietBi INNER JOIN ThietBi ON BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi INNER JOIN NguoiDung ON BaoSuaThietBi.MaNguoiDung=NguoiDung.MaNguoiDung INNER JOIN DonVi ON NguoiDung.MaDonVi=DonVi.MaDonVi LEFT JOIN NguoiDung AS ND ON BaoSuaThietBi.NguoiPhanCong = ND.MaNguoiDung LEFT JOIN NguoiDung AS ND2 ON BaoSuaThietBi.NguoiDuocPhanCong = ND2.MaNguoiDung WHERE DaHoanTat=0 AND CONVERT(DATE,ThoiGianBao) BETWEEN '"
					+ today + "' AND '" + today + "' ORDER BY ThoiGianBao ASC";
			ResultSet result = statement.executeQuery(select);
			table.removeAll();

			while (result.next()) {
				String thoigianbao = "";
				String thoigianhuy = "";
				String thoigiancapnhat = "";
				try {
					thoigianbao = result.getString(6);
					thoigianbao = thoigianbao.substring(8, 10) + "/" + thoigianbao.substring(5, 7) + "/"
							+ thoigianbao.substring(0, 4) + " " + thoigianbao.substring(11, 19);
				} catch (NullPointerException exc) {
					thoigianbao = "";
				}
				try {
					thoigianhuy = result.getString(8);
					thoigianhuy = thoigianhuy.substring(8, 10) + "/" + thoigianhuy.substring(5, 7) + "/"
							+ thoigianhuy.substring(0, 4) + " " + thoigianhuy.substring(11, 19);
				} catch (NullPointerException exc) {
					thoigianhuy = "";
				}
				try {
					thoigiancapnhat = result.getString(12);
					thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/" + thoigiancapnhat.substring(5, 7) + "/"
							+ thoigiancapnhat.substring(0, 4) + " " + thoigiancapnhat.substring(11, 19);
				} catch (NullPointerException exc) {
					thoigiancapnhat = "";
				}
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
						result.getString(4), result.getString(5), thoigianbao, result.getString(7), thoigianhuy,
						result.getString(9), result.getString(10), result.getString(11), thoigiancapnhat,
						result.getString(13) });
			}
			result.close();
			// Lấy vừa các cột table
			tbclNguoidung.pack();
			tbclThietbi.pack();
			tbclNoidung.pack();
			tbclThoigianbao.pack();
			tbclHuy.pack();
			tbclThoigianhuy.pack();
			tbclNguoiphancong.pack();
			tbclNguoiduocphancong.pack();
			tbclTrangthai.pack();
			tbclThoigiancapnhat.pack();
			tbclHoanthanh.pack();
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

		// Search
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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

					// Lấy dữ liệu cho table
					String ngaystart = (dateTimeBatdau.getDay() + "").length() == 2 ? dateTimeBatdau.getDay() + ""
							: "0" + dateTimeBatdau.getDay();
					String thangstart = ((dateTimeBatdau.getMonth() + 1) + "").length() == 2
							? (dateTimeBatdau.getMonth() + 1) + ""
							: "0" + (dateTimeBatdau.getMonth() + 1);
					String ngaybatdau = dateTimeBatdau.getYear() + "" + thangstart + ngaystart;

					String ngayend = (dateTimeKetthuc.getDay() + "").length() == 2 ? dateTimeKetthuc.getDay() + ""
							: "0" + dateTimeKetthuc.getDay();
					String thangend = ((dateTimeKetthuc.getMonth() + 1) + "").length() == 2
							? (dateTimeKetthuc.getMonth() + 1) + ""
							: "0" + (dateTimeKetthuc.getMonth() + 1);
					String ngayketthuc = dateTimeKetthuc.getYear() + "" + thangend + ngayend;

					String hoanthanh = "";
					hoanthanh = btnradioChuahoanthanh.getSelection() ? " DaHoanTat=0 AND"
							: btnradioHoanthanh.getSelection() ? "DaHoanTat=1 AND" : "";
					if (btnradioChuahoanthanh.getSelection()) {
						hoanthanh = " DaHoanTat=0 AND";
					} else if (btnradioHoanthanh.getSelection()) {
						hoanthanh = " DaHoanTat=1 AND";
					} else {
						hoanthanh = "";
					}
					String donvi = comboDonvi.getText();
					if (!donvi.isEmpty()) {
						donvi = " AND TenDonVi=N'" + donvi + "'";
					}
					String select = "";
					select = "SELECT PhanBiet,NguoiDung.TenNguoiDung,DonVi.TenDonVi,ThietBi.TenThietBi,NoiDung,ThoiGianBao,CASE Huy WHEN 0 THEN N'Chưa hủy' ELSE N'Đã hủy' END AS Huy,ThoiGianHuy,ND.TenNguoiDung AS NguoiPhanCong,ND2.TenNguoiDung AS NguoiDuocPhanCong,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,ThoiGianCapNhat,CASE DaHoanTat WHEN 0 THEN N'Chưa hoàn thành' ELSE N'Hoàn thành' END AS DaHoanTat FROM BaoSuaThietBi INNER JOIN ThietBi ON BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi INNER JOIN NguoiDung ON BaoSuaThietBi.MaNguoiDung=NguoiDung.MaNguoiDung INNER JOIN DonVi ON NguoiDung.MaDonVi=DonVi.MaDonVi"
							+ donvi
							+ " LEFT JOIN NguoiDung AS ND ON BaoSuaThietBi.NguoiPhanCong = ND.MaNguoiDung LEFT JOIN NguoiDung AS ND2 ON BaoSuaThietBi.NguoiDuocPhanCong = ND2.MaNguoiDung WHERE"
							+ hoanthanh + " CONVERT(DATE,ThoiGianBao) BETWEEN '" + ngaybatdau + "' AND '" + ngayketthuc
							+ "' ORDER BY ThoiGianBao ASC";
					ResultSet result = statement.executeQuery(select);
					table.removeAll();

					while (result.next()) {
						String thoigianbao = "";
						String thoigianhuy = "";
						String thoigiancapnhat = "";
						try {
							thoigianbao = result.getString(6);
							thoigianbao = thoigianbao.substring(8, 10) + "/" + thoigianbao.substring(5, 7) + "/"
									+ thoigianbao.substring(0, 4) + " " + thoigianbao.substring(11, 19);
						} catch (NullPointerException exc) {
							thoigianbao = "";
						}
						try {
							thoigianhuy = result.getString(8);
							thoigianhuy = thoigianhuy.substring(8, 10) + "/" + thoigianhuy.substring(5, 7) + "/"
									+ thoigianhuy.substring(0, 4) + " " + thoigianhuy.substring(11, 19);
						} catch (NullPointerException exc) {
							thoigianhuy = "";
						}
						try {
							thoigiancapnhat = result.getString(12);
							thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/" + thoigiancapnhat.substring(5, 7)
									+ "/" + thoigiancapnhat.substring(0, 4) + " " + thoigiancapnhat.substring(11, 19);
						} catch (NullPointerException exc) {
							thoigiancapnhat = "";
						}
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
								result.getString(4), result.getString(5), thoigianbao, result.getString(7), thoigianhuy,
								result.getString(9), result.getString(10), result.getString(11), thoigiancapnhat,
								result.getString(13) });
					}
					result.close();
					// Lấy vừa các cột table
					tbclNguoidung.pack();
					tbclThietbi.pack();
					tbclNoidung.pack();
					tbclThoigianbao.pack();
					tbclHuy.pack();
					tbclThoigianhuy.pack();
					tbclNguoiphancong.pack();
					tbclNguoiduocphancong.pack();
					tbclTrangthai.pack();
					tbclThoigiancapnhat.pack();
					tbclHoanthanh.pack();
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

		// Delete
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (!btnSave.isVisible()) {
						TableItem[] item = table.getSelection();
						phanbiet = item[0].getText();
						vitrixoa = table.getSelectionIndex();
						btnSearch.setEnabled(false);
						btnPhancong.setEnabled(false);
						btnSave.setVisible(true);
						btnCancel.setVisible(true);
					}
				} catch (ArrayIndexOutOfBoundsException ae) {
				}
			}
		});

		// Phan Cong
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		btnPhancong.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					TableItem[] item = table.getSelection();
					PhanCongSuaChuaThietBi phancong = new PhanCongSuaChuaThietBi();
					phancong.createContents(manguoidung, item[0].getText(), item[0].getText(1), item[0].getText(2),
							item[0].getText(3), item[0].getText(4), item[0].getText(5), item[0].getText(9), ngonngu,db_url);
					shell.setEnabled(false);
					phancong.open();
					shell.setEnabled(true);
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

						// Lấy dữ liệu cho table
						String ngaystart = (dateTimeBatdau.getDay() + "").length() == 2 ? dateTimeBatdau.getDay() + ""
								: "0" + dateTimeBatdau.getDay();
						String thangstart = ((dateTimeBatdau.getMonth() + 1) + "").length() == 2
								? (dateTimeBatdau.getMonth() + 1) + ""
								: "0" + (dateTimeBatdau.getMonth() + 1);
						String ngaybatdau = dateTimeBatdau.getYear() + "" + thangstart + ngaystart;

						String ngayend = (dateTimeKetthuc.getDay() + "").length() == 2 ? dateTimeKetthuc.getDay() + ""
								: "0" + dateTimeKetthuc.getDay();
						String thangend = ((dateTimeKetthuc.getMonth() + 1) + "").length() == 2
								? (dateTimeKetthuc.getMonth() + 1) + ""
								: "0" + (dateTimeKetthuc.getMonth() + 1);
						String ngayketthuc = dateTimeKetthuc.getYear() + "" + thangend + ngayend;

						String hoanthanh = "";
						hoanthanh = btnradioChuahoanthanh.getSelection() ? " DaHoanTat=0 AND"
								: btnradioHoanthanh.getSelection() ? "DaHoanTat=1 AND" : "";
						if (btnradioChuahoanthanh.getSelection()) {
							hoanthanh = " DaHoanTat=0 AND";
						} else if (btnradioHoanthanh.getSelection()) {
							hoanthanh = " DaHoanTat=1 AND";
						} else {
							hoanthanh = "";
						}
						String donvi = comboDonvi.getText();
						if (!donvi.isEmpty()) {
							donvi = " AND TenDonVi=N'" + donvi + "'";
						}
						String select = "";
						select = "SELECT PhanBiet,NguoiDung.TenNguoiDung,DonVi.TenDonVi,ThietBi.TenThietBi,NoiDung,ThoiGianBao,CASE Huy WHEN 0 THEN N'Chưa hủy' ELSE N'Đã hủy' END AS Huy,ThoiGianHuy,ND.TenNguoiDung AS NguoiPhanCong,ND2.TenNguoiDung AS NguoiDuocPhanCong,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,ThoiGianCapNhat,CASE DaHoanTat WHEN 0 THEN N'Chưa hoàn thành' ELSE N'Hoàn thành' END AS DaHoanTat FROM BaoSuaThietBi INNER JOIN ThietBi ON BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi INNER JOIN NguoiDung ON BaoSuaThietBi.MaNguoiDung=NguoiDung.MaNguoiDung INNER JOIN DonVi ON NguoiDung.MaDonVi=DonVi.MaDonVi"
								+ donvi
								+ " LEFT JOIN NguoiDung AS ND ON BaoSuaThietBi.NguoiPhanCong = ND.MaNguoiDung LEFT JOIN NguoiDung AS ND2 ON BaoSuaThietBi.NguoiDuocPhanCong = ND2.MaNguoiDung WHERE"
								+ hoanthanh + " CONVERT(DATE,ThoiGianBao) BETWEEN '" + ngaybatdau + "' AND '"
								+ ngayketthuc + "' ORDER BY ThoiGianBao ASC";
						ResultSet result = statement.executeQuery(select);
						table.removeAll();

						while (result.next()) {
							String thoigianbao = "";
							String thoigianhuy = "";
							String thoigiancapnhat = "";
							try {
								thoigianbao = result.getString(6);
								thoigianbao = thoigianbao.substring(8, 10) + "/" + thoigianbao.substring(5, 7) + "/"
										+ thoigianbao.substring(0, 4) + " " + thoigianbao.substring(11, 19);
							} catch (NullPointerException exc) {
								thoigianbao = "";
							}
							try {
								thoigianhuy = result.getString(8);
								thoigianhuy = thoigianhuy.substring(8, 10) + "/" + thoigianhuy.substring(5, 7) + "/"
										+ thoigianhuy.substring(0, 4) + " " + thoigianhuy.substring(11, 19);
							} catch (NullPointerException exc) {
								thoigianhuy = "";
							}
							try {
								thoigiancapnhat = result.getString(12);
								thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/"
										+ thoigiancapnhat.substring(5, 7) + "/" + thoigiancapnhat.substring(0, 4) + " "
										+ thoigiancapnhat.substring(11, 19);
							} catch (NullPointerException exc) {
								thoigiancapnhat = "";
							}
							TableItem item2 = new TableItem(table, SWT.NONE);
							item2.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
									result.getString(4), result.getString(5), thoigianbao, result.getString(7),
									thoigianhuy, result.getString(9), result.getString(10), result.getString(11),
									thoigiancapnhat, result.getString(13) });
						}
						result.close();
						// Lấy vừa các cột table
						tbclNguoidung.pack();
						tbclThietbi.pack();
						tbclNoidung.pack();
						tbclThoigianbao.pack();
						tbclHuy.pack();
						tbclThoigianhuy.pack();
						tbclNguoiphancong.pack();
						tbclNguoiduocphancong.pack();
						tbclTrangthai.pack();
						tbclThoigiancapnhat.pack();
						tbclHoanthanh.pack();
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
				} catch (ArrayIndexOutOfBoundsException ae) {
				}
			}
		});

		// Save
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Delete
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String delete = "DELETE BaoSuaThietBi WHERE PhanBiet=" + phanbiet;
					if (statement.executeUpdate(delete) > 0) {
						table.remove(vitrixoa);
						btnSearch.setEnabled(true);
						btnPhancong.setEnabled(true);
						btnSave.setVisible(false);
						btnCancel.setVisible(false);
						phanbiet = "";
						vitrixoa = -1;
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

		// Cancel
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnSearch.setEnabled(true);
				btnPhancong.setEnabled(true);
				btnSave.setVisible(false);
				btnCancel.setVisible(false);
				phanbiet = "";
				vitrixoa = -1;
			}
		});
	}

	// Ctabfolder
	// ==========================================================================================================================================
	protected void createContentsTabfolder(CTabFolder tabfolder, Shell shell, String manguoidung,
			String manhomnguoidung, int ngonngu,String chuoiketnoi) {
		db_url=chuoiketnoi;
		CTabItem itemtab = new CTabItem(tabfolder, SWT.CLOSE);
		itemtab.setText("Báo sửa thiết bị");
		Composite composite = new Composite(tabfolder, SWT.NONE);
		composite.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		itemtab.setControl(composite);

		CLabel lbBatdau = new CLabel(composite, SWT.NONE);
		lbBatdau.setAlignment(SWT.RIGHT);
		lbBatdau.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbBatdau.setBounds(10, 20, 114, 25);
		lbBatdau.setText("Bắt Đầu");

		DateTime dateTimeBatdau = new DateTime(composite, SWT.BORDER);
		dateTimeBatdau.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		dateTimeBatdau.setBounds(145, 20, 128, 25);

		CLabel lbKetthuc = new CLabel(composite, SWT.NONE);
		lbKetthuc.setAlignment(SWT.RIGHT);
		lbKetthuc.setText("Kết Thúc");
		lbKetthuc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbKetthuc.setBounds(279, 20, 114, 25);

		DateTime dateTimeKetthuc = new DateTime(composite, SWT.BORDER);
		dateTimeKetthuc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		dateTimeKetthuc.setBounds(410, 20, 128, 25);

		CLabel lbDonvi = new CLabel(composite, SWT.NONE);
		lbDonvi.setAlignment(SWT.RIGHT);
		lbDonvi.setText("Đơn Vị");
		lbDonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbDonvi.setBounds(544, 20, 114, 25);

		CCombo comboDonvi = new CCombo(composite, SWT.BORDER);
		comboDonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		comboDonvi.setBounds(674, 20, 263, 25);

		Button btnradioChuahoanthanh = new Button(composite, SWT.RADIO);
		btnradioChuahoanthanh.setSelection(true);
		btnradioChuahoanthanh.setText("Chưa Hoàn Thành");
		btnradioChuahoanthanh.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnradioChuahoanthanh.setBounds(20, 56, 149, 25);

		Button btnradioHoanthanh = new Button(composite, SWT.RADIO);
		btnradioHoanthanh.setText("Hoàn Thành");
		btnradioHoanthanh.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnradioHoanthanh.setBounds(175, 56, 114, 25);

		Button btnradioTatca = new Button(composite, SWT.RADIO);
		btnradioTatca.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnradioTatca.setBounds(299, 56, 83, 25);
		btnradioTatca.setText("Tất Cả");

		Button btnSearch = new Button(composite, SWT.NONE);
		btnSearch.setImage(SWTResourceManager.getImage(DanhSachSuaChuaThietBi.class, "/manager/icon/search.png"));
		btnSearch.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnSearch.setBounds(388, 59, 128, 30);
		btnSearch.setText("Search");

		Button btnDelete = new Button(composite, SWT.NONE);
		btnDelete.setImage(SWTResourceManager.getImage(DanhSachSuaChuaThietBi.class, "/manager/icon/delete.png"));
		btnDelete.setText("Delete");
		btnDelete.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnDelete.setBounds(522, 59, 113, 30);

		Button btnPhancong = new Button(composite, SWT.NONE);
		btnPhancong.setImage(SWTResourceManager.getImage(DanhSachSuaChuaThietBi.class, "/manager/icon/phan cong.png"));
		btnPhancong.setText("Phân Công");
		btnPhancong.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnPhancong.setBounds(641, 59, 138, 30);

		Button btnSave = new Button(composite, SWT.NONE);
		btnSave.setText("Save");
		btnSave.setImage(SWTResourceManager.getImage(DanhSachSuaChuaThietBi.class, "/manager/icon/save25.png"));
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnSave.setBounds(785, 59, 96, 30);

		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setText("Cancel");
		btnCancel.setImage(SWTResourceManager.getImage(DanhSachSuaChuaThietBi.class, "/manager/icon/cancel.png"));
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnCancel.setBounds(887, 59, 96, 30);

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderBackground(SWTResourceManager.getColor(255, 153, 51));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		table.setBounds(10, 95, 1310, 580);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setSize(sizemonitorx - 20, sizemonitory - 210);
		// table.setSize(sizemonitorx - 20, tabfolder.getSize().y - 125);

		TableColumn tbclTT = new TableColumn(table, SWT.NONE);
		// tbclTT.setWidth(52);
		tbclTT.setWidth(0);
		tbclTT.setResizable(false);
		tbclTT.setText("TT");

		TableColumn tbclNguoidung = new TableColumn(table, SWT.NONE);
		tbclNguoidung.setWidth(100);
		tbclNguoidung.setText("Người Dùng");

		TableColumn btclDonvi = new TableColumn(table, SWT.NONE);
		btclDonvi.setWidth(100);
		btclDonvi.setText("Đơn Vị");

		TableColumn tbclThietbi = new TableColumn(table, SWT.NONE);
		tbclThietbi.setWidth(100);
		tbclThietbi.setText("Thiết Bị");

		TableColumn tbclNoidung = new TableColumn(table, SWT.NONE);
		tbclNoidung.setWidth(100);
		tbclNoidung.setText("Nội Dung");

		TableColumn tbclThoigianbao = new TableColumn(table, SWT.NONE);
		tbclThoigianbao.setWidth(112);
		tbclThoigianbao.setText("Thời Gian Báo");

		TableColumn tbclHuy = new TableColumn(table, SWT.NONE);
		tbclHuy.setWidth(53);
		tbclHuy.setText("Hủy");

		TableColumn tbclThoigianhuy = new TableColumn(table, SWT.NONE);
		tbclThoigianhuy.setWidth(116);
		tbclThoigianhuy.setText("Thời Gian Hủy");

		TableColumn tbclNguoiphancong = new TableColumn(table, SWT.NONE);
		tbclNguoiphancong.setWidth(139);
		tbclNguoiphancong.setText("Người Phân Công");

		TableColumn tbclNguoiduocphancong = new TableColumn(table, SWT.NONE);
		tbclNguoiduocphancong.setWidth(176);
		tbclNguoiduocphancong.setText("Người Được Phân Công");

		TableColumn tbclTrangthai = new TableColumn(table, SWT.NONE);
		tbclTrangthai.setWidth(100);
		tbclTrangthai.setText("Trạng Thái");

		TableColumn tbclThoigiancapnhat = new TableColumn(table, SWT.NONE);
		tbclThoigiancapnhat.setWidth(148);
		tbclThoigiancapnhat.setText("Thời Gian Cập Nhật");

		TableColumn tbclHoanthanh = new TableColumn(table, SWT.NONE);
		tbclHoanthanh.setWidth(100);
		tbclHoanthanh.setText("Hoàn Thành");

		btnPhancong.setVisible(false);
		btnDelete.setVisible(false);
		btnCancel.setVisible(false);
		btnSave.setVisible(false);

		// Ngôn ngữ
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		if (ngonngu == 0) {
			itemtab.setText("Báo sửa thiết bị");
			lbBatdau.setText("Bắt Đầu");
			lbKetthuc.setText("Kết Thúc");
			lbDonvi.setText("Đơn Vị");
			btnradioChuahoanthanh.setText("Chưa Hoàn Thành");
			btnradioHoanthanh.setText("Hoàn Thành");
			btnradioTatca.setText("Tất Cả");
			btnSearch.setText("Tìm Kiếm");
			btnDelete.setText("Xóa");
			btnPhancong.setText("Phân Công");
			btnSave.setText("Lưu");
			btnCancel.setText("Hủy");
			tbclTT.setText("TT");
			tbclNguoidung.setText("Người Dùng");
			btclDonvi.setText("Đơn Vị");
			tbclThietbi.setText("Thiết Bị");
			tbclNoidung.setText("Nội Dung");
			tbclThoigianbao.setText("Thời Gian Báo");
			tbclHuy.setText("Hủy");
			tbclThoigianhuy.setText("Thời Gian Hủy");
			tbclNguoiphancong.setText("Người Phân Công");
			tbclNguoiduocphancong.setText("Người Được Phân Công");
			tbclTrangthai.setText("Trạng Thái");
			tbclThoigiancapnhat.setText("Thời Gian Cập Nhật");
			tbclHoanthanh.setText("Hoàn Thành");
		} else if (ngonngu == 1) {
			itemtab.setText("Equipment repair report");
			lbBatdau.setText("Start");
			lbKetthuc.setText("End");
			lbDonvi.setText("Department");
			btnradioChuahoanthanh.setText("Incomplete");
			btnradioHoanthanh.setText("Complete");
			btnradioTatca.setText("All");
			btnSearch.setText("Search");
			btnDelete.setText("Delete");
			btnPhancong.setText("Assign");
			btnSave.setText("Save");
			btnCancel.setText("Cancel");
			tbclTT.setText("TT");
			tbclNguoidung.setText("User");
			btclDonvi.setText("Department");
			tbclThietbi.setText("Devices");
			tbclNoidung.setText("Content");
			tbclThoigianbao.setText("Notice time");
			tbclHuy.setText("Cancel");
			tbclThoigianhuy.setText("Cancellation time");
			tbclNguoiphancong.setText("Taskmaster");
			tbclNguoiduocphancong.setText("The Person Assigned");
			tbclTrangthai.setText("Status");
			tbclThoigiancapnhat.setText("Time To Update");
			tbclHoanthanh.setText("Complete");
		} else if (ngonngu == 2) {
			itemtab.setText("設備維修報告");
			lbBatdau.setText("開始");
			lbKetthuc.setText("結束");
			lbDonvi.setText("部門");
			btnradioChuahoanthanh.setText("殘缺");
			btnradioHoanthanh.setText("完成");
			btnradioTatca.setText("全部");
			btnSearch.setText("搜索");
			btnDelete.setText("刪除");
			btnPhancong.setText("分配");
			btnSave.setText("儲存");
			btnCancel.setText("取消");
			tbclTT.setText("TT");
			tbclNguoidung.setText("使用者");
			btclDonvi.setText("部門");
			tbclThietbi.setText("設備");
			tbclNoidung.setText("內容");
			tbclThoigianbao.setText("通知時間");
			tbclHuy.setText("取消");
			tbclThoigianhuy.setText("取消時間");
			tbclNguoiphancong.setText("工作分配者");
			tbclNguoiduocphancong.setText("分配的人");
			tbclTrangthai.setText("狀態");
			tbclThoigiancapnhat.setText("是時候更新了");
			tbclHoanthanh.setText("完成");
		}

		// Kiểm tra xem bạn có phải là người trực phòng ngày hiện tại không
		// -------------------------------------------------------------------------------------------------------------------------
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			String ngay = "0" + java.time.LocalDateTime.now().getDayOfMonth();
			ngay = ngay.substring(ngay.length() - 2);
			String thang = "0" + (java.time.LocalDateTime.now().getMonthValue());
			thang = thang.substring(thang.length() - 2);
			String nam = "" + java.time.LocalDateTime.now().getYear();
			String today = nam + thang + ngay;
			String selectnguoitrucphong = "SELECT NguoiTrucThay FROM LichTrucPhong WHERE NgayTruc='" + today + "'";
			ResultSet ketquanguoitrucphong = statement.executeQuery(selectnguoitrucphong);
			while (ketquanguoitrucphong.next()) {
				if (ketquanguoitrucphong.getString(1).compareToIgnoreCase(manguoidung) == 0) {
					nguoitrucphong = true;
				}
			}
			ketquanguoitrucphong.close();
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

		// Chỉ cho người dùng Admin hoặc người trực phòng phân công mà thôi
		// ==================================================================================================================================
		if ((manhomnguoidung.compareToIgnoreCase("admin") == 0 || nguoitrucphong)) {
			btnPhancong.setVisible(true);
			btnDelete.setVisible(true);
		}

		// Xu ly du lieu luc dau
		// -----------------------------------------------------------------------------------------
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

			// Lấy dữ liệu cho table
			String ngay = "0" + java.time.LocalDateTime.now().getDayOfMonth();
			ngay = ngay.substring(ngay.length() - 2);
			String thang = "0" + (java.time.LocalDateTime.now().getMonthValue());
			thang = thang.substring(thang.length() - 2);
			String nam = "" + java.time.LocalDateTime.now().getYear();
			// Lấy ngày hiện tại theo dạng năm tháng ngày: 20200227 (2020-02-27)
			String today = nam + thang + ngay;
			String select = "";
			select = "SELECT PhanBiet,NguoiDung.TenNguoiDung,DonVi.TenDonVi,ThietBi.TenThietBi,NoiDung,ThoiGianBao,CASE Huy WHEN 0 THEN N'Chưa hủy' ELSE N'Đã hủy' END AS Huy,ThoiGianHuy,ND.TenNguoiDung AS NguoiPhanCong,ND2.TenNguoiDung AS NguoiDuocPhanCong,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,ThoiGianCapNhat,CASE DaHoanTat WHEN 0 THEN N'Chưa hoàn thành' ELSE N'Hoàn thành' END AS DaHoanTat FROM BaoSuaThietBi INNER JOIN ThietBi ON BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi INNER JOIN NguoiDung ON BaoSuaThietBi.MaNguoiDung=NguoiDung.MaNguoiDung INNER JOIN DonVi ON NguoiDung.MaDonVi=DonVi.MaDonVi LEFT JOIN NguoiDung AS ND ON BaoSuaThietBi.NguoiPhanCong = ND.MaNguoiDung LEFT JOIN NguoiDung AS ND2 ON BaoSuaThietBi.NguoiDuocPhanCong = ND2.MaNguoiDung WHERE DaHoanTat=0 AND CONVERT(DATE,ThoiGianBao) BETWEEN '"
					+ today + "' AND '" + today + "' ORDER BY ThoiGianBao ASC";
			ResultSet result = statement.executeQuery(select);
			table.removeAll();

			while (result.next()) {
				String thoigianbao = "";
				String thoigianhuy = "";
				String thoigiancapnhat = "";
				try {
					thoigianbao = result.getString(6);
					thoigianbao = thoigianbao.substring(8, 10) + "/" + thoigianbao.substring(5, 7) + "/"
							+ thoigianbao.substring(0, 4) + " " + thoigianbao.substring(11, 19);
				} catch (NullPointerException exc) {
					thoigianbao = "";
				}
				try {
					thoigianhuy = result.getString(8);
					thoigianhuy = thoigianhuy.substring(8, 10) + "/" + thoigianhuy.substring(5, 7) + "/"
							+ thoigianhuy.substring(0, 4) + " " + thoigianhuy.substring(11, 19);
				} catch (NullPointerException exc) {
					thoigianhuy = "";
				}
				try {
					thoigiancapnhat = result.getString(12);
					thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/" + thoigiancapnhat.substring(5, 7) + "/"
							+ thoigiancapnhat.substring(0, 4) + " " + thoigiancapnhat.substring(11, 19);
				} catch (NullPointerException exc) {
					thoigiancapnhat = "";
				}
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
						result.getString(4), result.getString(5), thoigianbao, result.getString(7), thoigianhuy,
						result.getString(9), result.getString(10), result.getString(11), thoigiancapnhat,
						result.getString(13) });
			}
			result.close();
			// Lấy vừa các cột table
			tbclNguoidung.pack();
			tbclThietbi.pack();
			tbclNoidung.pack();
			tbclThoigianbao.pack();
			tbclHuy.pack();
			tbclThoigianhuy.pack();
			tbclNguoiphancong.pack();
			tbclNguoiduocphancong.pack();
			tbclTrangthai.pack();
			tbclThoigiancapnhat.pack();
			tbclHoanthanh.pack();
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

		// Search
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
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

					// Lấy dữ liệu cho table
					String ngaystart = (dateTimeBatdau.getDay() + "").length() == 2 ? dateTimeBatdau.getDay() + ""
							: "0" + dateTimeBatdau.getDay();
					String thangstart = ((dateTimeBatdau.getMonth() + 1) + "").length() == 2
							? (dateTimeBatdau.getMonth() + 1) + ""
							: "0" + (dateTimeBatdau.getMonth() + 1);
					String ngaybatdau = dateTimeBatdau.getYear() + "" + thangstart + ngaystart;

					String ngayend = (dateTimeKetthuc.getDay() + "").length() == 2 ? dateTimeKetthuc.getDay() + ""
							: "0" + dateTimeKetthuc.getDay();
					String thangend = ((dateTimeKetthuc.getMonth() + 1) + "").length() == 2
							? (dateTimeKetthuc.getMonth() + 1) + ""
							: "0" + (dateTimeKetthuc.getMonth() + 1);
					String ngayketthuc = dateTimeKetthuc.getYear() + "" + thangend + ngayend;

					String hoanthanh = "";
					hoanthanh = btnradioChuahoanthanh.getSelection() ? " DaHoanTat=0 AND"
							: btnradioHoanthanh.getSelection() ? "DaHoanTat=1 AND" : "";
					if (btnradioChuahoanthanh.getSelection()) {
						hoanthanh = " DaHoanTat=0 AND";
					} else if (btnradioHoanthanh.getSelection()) {
						hoanthanh = " DaHoanTat=1 AND";
					} else {
						hoanthanh = "";
					}
					String donvi = comboDonvi.getText();
					if (!donvi.isEmpty()) {
						donvi = " AND TenDonVi=N'" + donvi + "'";
					}
					String select = "";
					select = "SELECT PhanBiet,NguoiDung.TenNguoiDung,DonVi.TenDonVi,ThietBi.TenThietBi,NoiDung,ThoiGianBao,CASE Huy WHEN 0 THEN N'Chưa hủy' ELSE N'Đã hủy' END AS Huy,ThoiGianHuy,ND.TenNguoiDung AS NguoiPhanCong,ND2.TenNguoiDung AS NguoiDuocPhanCong,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,ThoiGianCapNhat,CASE DaHoanTat WHEN 0 THEN N'Chưa hoàn thành' ELSE N'Hoàn thành' END AS DaHoanTat FROM BaoSuaThietBi INNER JOIN ThietBi ON BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi INNER JOIN NguoiDung ON BaoSuaThietBi.MaNguoiDung=NguoiDung.MaNguoiDung INNER JOIN DonVi ON NguoiDung.MaDonVi=DonVi.MaDonVi"
							+ donvi
							+ " LEFT JOIN NguoiDung AS ND ON BaoSuaThietBi.NguoiPhanCong = ND.MaNguoiDung LEFT JOIN NguoiDung AS ND2 ON BaoSuaThietBi.NguoiDuocPhanCong = ND2.MaNguoiDung WHERE"
							+ hoanthanh + " CONVERT(DATE,ThoiGianBao) BETWEEN '" + ngaybatdau + "' AND '" + ngayketthuc
							+ "' ORDER BY ThoiGianBao ASC";
					ResultSet result = statement.executeQuery(select);
					table.removeAll();

					while (result.next()) {
						String thoigianbao = "";
						String thoigianhuy = "";
						String thoigiancapnhat = "";
						try {
							thoigianbao = result.getString(6);
							thoigianbao = thoigianbao.substring(8, 10) + "/" + thoigianbao.substring(5, 7) + "/"
									+ thoigianbao.substring(0, 4) + " " + thoigianbao.substring(11, 19);
						} catch (NullPointerException exc) {
							thoigianbao = "";
						}
						try {
							thoigianhuy = result.getString(8);
							thoigianhuy = thoigianhuy.substring(8, 10) + "/" + thoigianhuy.substring(5, 7) + "/"
									+ thoigianhuy.substring(0, 4) + " " + thoigianhuy.substring(11, 19);
						} catch (NullPointerException exc) {
							thoigianhuy = "";
						}
						try {
							thoigiancapnhat = result.getString(12);
							thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/" + thoigiancapnhat.substring(5, 7)
									+ "/" + thoigiancapnhat.substring(0, 4) + " " + thoigiancapnhat.substring(11, 19);
						} catch (NullPointerException exc) {
							thoigiancapnhat = "";
						}
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
								result.getString(4), result.getString(5), thoigianbao, result.getString(7), thoigianhuy,
								result.getString(9), result.getString(10), result.getString(11), thoigiancapnhat,
								result.getString(13) });
					}
					result.close();
					// Lấy vừa các cột table
					tbclNguoidung.pack();
					tbclThietbi.pack();
					tbclNoidung.pack();
					tbclThoigianbao.pack();
					tbclHuy.pack();
					tbclThoigianhuy.pack();
					tbclNguoiphancong.pack();
					tbclNguoiduocphancong.pack();
					tbclTrangthai.pack();
					tbclThoigiancapnhat.pack();
					tbclHoanthanh.pack();
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

		// Delete
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (!btnSave.isVisible()) {
						TableItem[] item = table.getSelection();
						phanbiet = item[0].getText();
						vitrixoa = table.getSelectionIndex();
						btnSearch.setEnabled(false);
						btnPhancong.setEnabled(false);
						btnCancel.setVisible(true);
						btnSave.setVisible(true);
					}
				} catch (ArrayIndexOutOfBoundsException ae) {
				}
			}
		});

		// Phan Cong
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		btnPhancong.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					TableItem[] item = table.getSelection();
					PhanCongSuaChuaThietBi phancong = new PhanCongSuaChuaThietBi();
					phancong.createContents(manguoidung, item[0].getText(), item[0].getText(1), item[0].getText(2),
							item[0].getText(3), item[0].getText(4), item[0].getText(5), item[0].getText(9), ngonngu,db_url);
					shell.setEnabled(false);
					phancong.open();
					shell.setEnabled(true);
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

						// Lấy dữ liệu cho table
						String ngaystart = (dateTimeBatdau.getDay() + "").length() == 2 ? dateTimeBatdau.getDay() + ""
								: "0" + dateTimeBatdau.getDay();
						String thangstart = ((dateTimeBatdau.getMonth() + 1) + "").length() == 2
								? (dateTimeBatdau.getMonth() + 1) + ""
								: "0" + (dateTimeBatdau.getMonth() + 1);
						String ngaybatdau = dateTimeBatdau.getYear() + "" + thangstart + ngaystart;

						String ngayend = (dateTimeKetthuc.getDay() + "").length() == 2 ? dateTimeKetthuc.getDay() + ""
								: "0" + dateTimeKetthuc.getDay();
						String thangend = ((dateTimeKetthuc.getMonth() + 1) + "").length() == 2
								? (dateTimeKetthuc.getMonth() + 1) + ""
								: "0" + (dateTimeKetthuc.getMonth() + 1);
						String ngayketthuc = dateTimeKetthuc.getYear() + "" + thangend + ngayend;

						String hoanthanh = "";
						hoanthanh = btnradioChuahoanthanh.getSelection() ? " DaHoanTat=0 AND"
								: btnradioHoanthanh.getSelection() ? "DaHoanTat=1 AND" : "";
						if (btnradioChuahoanthanh.getSelection()) {
							hoanthanh = " DaHoanTat=0 AND";
						} else if (btnradioHoanthanh.getSelection()) {
							hoanthanh = " DaHoanTat=1 AND";
						} else {
							hoanthanh = "";
						}
						String donvi = comboDonvi.getText();
						if (!donvi.isEmpty()) {
							donvi = " AND TenDonVi=N'" + donvi + "'";
						}
						String select = "";
						select = "SELECT PhanBiet,NguoiDung.TenNguoiDung,DonVi.TenDonVi,ThietBi.TenThietBi,NoiDung,ThoiGianBao,CASE Huy WHEN 0 THEN N'Chưa hủy' ELSE N'Đã hủy' END AS Huy,ThoiGianHuy,ND.TenNguoiDung AS NguoiPhanCong,ND2.TenNguoiDung AS NguoiDuocPhanCong,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,ThoiGianCapNhat,CASE DaHoanTat WHEN 0 THEN N'Chưa hoàn thành' ELSE N'Hoàn thành' END AS DaHoanTat FROM BaoSuaThietBi INNER JOIN ThietBi ON BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi INNER JOIN NguoiDung ON BaoSuaThietBi.MaNguoiDung=NguoiDung.MaNguoiDung INNER JOIN DonVi ON NguoiDung.MaDonVi=DonVi.MaDonVi"
								+ donvi
								+ " LEFT JOIN NguoiDung AS ND ON BaoSuaThietBi.NguoiPhanCong = ND.MaNguoiDung LEFT JOIN NguoiDung AS ND2 ON BaoSuaThietBi.NguoiDuocPhanCong = ND2.MaNguoiDung WHERE"
								+ hoanthanh + " CONVERT(DATE,ThoiGianBao) BETWEEN '" + ngaybatdau + "' AND '"
								+ ngayketthuc + "' ORDER BY ThoiGianBao ASC";
						ResultSet result = statement.executeQuery(select);
						table.removeAll();

						while (result.next()) {
							String thoigianbao = "";
							String thoigianhuy = "";
							String thoigiancapnhat = "";
							try {
								thoigianbao = result.getString(6);
								thoigianbao = thoigianbao.substring(8, 10) + "/" + thoigianbao.substring(5, 7) + "/"
										+ thoigianbao.substring(0, 4) + " " + thoigianbao.substring(11, 19);
							} catch (NullPointerException exc) {
								thoigianbao = "";
							}
							try {
								thoigianhuy = result.getString(8);
								thoigianhuy = thoigianhuy.substring(8, 10) + "/" + thoigianhuy.substring(5, 7) + "/"
										+ thoigianhuy.substring(0, 4) + " " + thoigianhuy.substring(11, 19);
							} catch (NullPointerException exc) {
								thoigianhuy = "";
							}
							try {
								thoigiancapnhat = result.getString(12);
								thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/"
										+ thoigiancapnhat.substring(5, 7) + "/" + thoigiancapnhat.substring(0, 4) + " "
										+ thoigiancapnhat.substring(11, 19);
							} catch (NullPointerException exc) {
								thoigiancapnhat = "";
							}
							TableItem item2 = new TableItem(table, SWT.NONE);
							item2.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
									result.getString(4), result.getString(5), thoigianbao, result.getString(7),
									thoigianhuy, result.getString(9), result.getString(10), result.getString(11),
									thoigiancapnhat, result.getString(13) });
						}
						result.close();
						// Lấy vừa các cột table
						tbclNguoidung.pack();
						tbclThietbi.pack();
						tbclNoidung.pack();
						tbclThoigianbao.pack();
						tbclHuy.pack();
						tbclThoigianhuy.pack();
						tbclNguoiphancong.pack();
						tbclNguoiduocphancong.pack();
						tbclTrangthai.pack();
						tbclThoigiancapnhat.pack();
						tbclHoanthanh.pack();
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
				} catch (ArrayIndexOutOfBoundsException ae) {
				}
			}
		});

		// Save
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Delete
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String delete = "DELETE BaoSuaThietBi WHERE PhanBiet=" + phanbiet;
					if (statement.executeUpdate(delete) > 0) {
						table.remove(vitrixoa);
						btnSearch.setEnabled(true);
						btnPhancong.setEnabled(true);
						btnCancel.setVisible(false);
						btnSave.setVisible(false);
						phanbiet = "";
						vitrixoa = -1;
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

		// Cancel
		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnSearch.setEnabled(true);
				btnPhancong.setEnabled(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
				phanbiet = "";
				vitrixoa = -1;
			}
		});
	}
}
