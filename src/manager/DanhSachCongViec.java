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
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DanhSachCongViec {

	protected Shell shell;
	private Text textCongviec;
	private Text textTiendo;
	private Text textNoidung;
	private Table tableleft;
	private Text textGhichu;
	private Table tableright;
	// kích thước màn hình
	private int sizemonitorx = Display.getDefault().getBounds().width;
	private int sizemonitory = Display.getDefault().getBounds().height;
	private int ngonngu = 0; // 0: Vietnam, 1: English, 2: China
	private String manguoidung = "admin";
	private String manhomnguoidung = "it";
	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;
	private boolean nguoitrucphong = false;

	// trangthai = 1: Add, 2: Update, 3: Delete
	private int trangthai = 0;
	private String macongviecupdatedelete = "";
	private String macongviecright = "";
	private int vitrixoa = -1; // vị trí trong bảng cần xóa, index

	public static void main(String[] args) {
		try {
			DanhSachCongViec window = new DanhSachCongViec();
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
		createContents(manguoidung, manhomnguoidung, ngonngu,db_url);
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
		this.manguoidung = manguoidung;
		this.manhomnguoidung = manhomnguoidung;
		this.ngonngu = ngonngu;
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/Images/repair256.ico"));
		shell.setSize(1366, 768);
		shell.setText("Danh sách công việc");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setMaximized(true);

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		SashForm sashForm = new SashForm(composite, SWT.NONE);
		sashForm.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		Composite compositeleft = new Composite(sashForm, SWT.BORDER);
		compositeleft.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		CLabel lbDanhsachcongviec = new CLabel(compositeleft, SWT.BORDER | SWT.SHADOW_IN | SWT.SHADOW_NONE);
		lbDanhsachcongviec.setLeftMargin(15);
		lbDanhsachcongviec.setBackground(SWTResourceManager.getColor(51, 153, 255));
		lbDanhsachcongviec.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbDanhsachcongviec.setBounds(0, 0, sizemonitorx, 30);
		lbDanhsachcongviec.setText("Danh Sách Công Việc");

		CLabel lbCongviec = new CLabel(compositeleft, SWT.NONE);
		lbCongviec.setAlignment(SWT.RIGHT);
		lbCongviec.setText("Công Việc");
		lbCongviec.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbCongviec.setBounds(10, 48, 102, 25);

		textCongviec = new Text(compositeleft, SWT.BORDER);
		textCongviec.setBounds(119, 48, 461, 25);
		textCongviec.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textCongviec.setTextLimit(100);

		CLabel lbBatdau = new CLabel(compositeleft, SWT.NONE);
		lbBatdau.setAlignment(SWT.RIGHT);
		lbBatdau.setText("Bắt Đầu");
		lbBatdau.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbBatdau.setBounds(10, 79, 102, 25);

		DateTime dateTimeBatdau = new DateTime(compositeleft, SWT.BORDER);
		dateTimeBatdau.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		dateTimeBatdau.setBounds(119, 79, 126, 25);

		CLabel lbKetthuc = new CLabel(compositeleft, SWT.NONE);
		lbKetthuc.setAlignment(SWT.RIGHT);
		lbKetthuc.setText("Kết Thúc");
		lbKetthuc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbKetthuc.setBounds(251, 79, 116, 25);

		DateTime dateTimeKetthuc = new DateTime(compositeleft, SWT.BORDER);
		dateTimeKetthuc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		dateTimeKetthuc.setBounds(373, 79, 126, 25);

		CLabel lbTiendo = new CLabel(compositeleft, SWT.NONE);
		lbTiendo.setAlignment(SWT.RIGHT);
		lbTiendo.setText("Tiến Độ");
		lbTiendo.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbTiendo.setBounds(10, 110, 102, 25);

		textTiendo = new Text(compositeleft, SWT.BORDER);
		textTiendo.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textTiendo.setBounds(118, 110, 266, 25);
		textTiendo.setTextLimit(50);

		Button btncheckChuahoanthanh = new Button(compositeleft, SWT.CHECK);
		btncheckChuahoanthanh.setSelection(true);
		btncheckChuahoanthanh.setText("Chưa Hoàn Thành");
		btncheckChuahoanthanh.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btncheckChuahoanthanh.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btncheckChuahoanthanh.setBounds(390, 110, 146, 25);

		Button btncheckHoanthanh = new Button(compositeleft, SWT.CHECK);
		btncheckHoanthanh.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btncheckHoanthanh.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btncheckHoanthanh.setBounds(542, 110, 114, 25);
		btncheckHoanthanh.setText("Hoàn Thành");

		CLabel lbNoidung = new CLabel(compositeleft, SWT.NONE);
		lbNoidung.setAlignment(SWT.RIGHT);
		lbNoidung.setText("Nội Dung");
		lbNoidung.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbNoidung.setBounds(10, 170, 102, 25);

		textNoidung = new Text(compositeleft, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		textNoidung.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textNoidung.setBounds(119, 141, 541, 90);
		textNoidung.setTextLimit(200);

		Button btnSearch = new Button(compositeleft, SWT.NONE);
		btnSearch.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/icon/search.png"));
		btnSearch.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnSearch.setBounds(31, 237, 116, 30);
		btnSearch.setText("Search");

		Button btnAdd = new Button(compositeleft, SWT.NONE);
		btnAdd.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/icon/add.png"));
		btnAdd.setText("Add");
		btnAdd.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnAdd.setBounds(153, 237, 91, 30);

		Button btnUpdate = new Button(compositeleft, SWT.NONE);
		btnUpdate.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/Images/update.png"));
		btnUpdate.setText("Update");
		btnUpdate.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnUpdate.setBounds(250, 237, 110, 30);

		Button btnDelete = new Button(compositeleft, SWT.NONE);
		btnDelete.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/icon/delete.png"));
		btnDelete.setText("Delete");
		btnDelete.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnDelete.setBounds(366, 237, 102, 30);

		Button btnSave = new Button(compositeleft, SWT.NONE);
		btnSave.setEnabled(false);
		btnSave.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/icon/save25.png"));
		btnSave.setText("Save");
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnSave.setBounds(474, 237, 83, 30);

		tableleft = new Table(compositeleft, SWT.BORDER | SWT.FULL_SELECTION);
		tableleft.setHeaderBackground(SWTResourceManager.getColor(255, 153, 51));
		tableleft.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		tableleft.setBounds(0, 273, 1920, 724);
		tableleft.setSize(sizemonitorx / 2, sizemonitory - 350);
		// tableleft.setSize(compositeleft.getSize().x, sizemonitory - 350);
		tableleft.setHeaderVisible(true);
		tableleft.setLinesVisible(true);

		TableColumn tbclMa = new TableColumn(tableleft, SWT.NONE);
		// tbclMa.setWidth(61);
		tbclMa.setWidth(0);
		tbclMa.setText("Mã");
		tbclMa.setResizable(false);

		TableColumn tbclCongviec = new TableColumn(tableleft, SWT.NONE);
		tbclCongviec.setWidth(102);
		tbclCongviec.setText("Công Việc");

		TableColumn tbclBatdau = new TableColumn(tableleft, SWT.NONE);
		tbclBatdau.setWidth(83);
		tbclBatdau.setText("Bắt Đầu");

		TableColumn tbclKetthuc = new TableColumn(tableleft, SWT.NONE);
		tbclKetthuc.setWidth(90);
		tbclKetthuc.setText("Kết Thúc");

		TableColumn tbclTiendo = new TableColumn(tableleft, SWT.NONE);
		tbclTiendo.setWidth(103);
		tbclTiendo.setText("Tiến Độ");

		TableColumn tbclnoidung = new TableColumn(tableleft, SWT.NONE);
		tbclnoidung.setWidth(96);
		tbclnoidung.setText("Nội Dung");

		TableColumn tbclHoanthanh = new TableColumn(tableleft, SWT.NONE);
		tbclHoanthanh.setWidth(98);
		tbclHoanthanh.setText("Hoàn Thành");

		Button btnCancel = new Button(compositeleft, SWT.NONE);
		btnCancel.setText("Cancel");
		btnCancel.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/icon/cancel.png"));
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnCancel.setEnabled(false);
		btnCancel.setBounds(563, 237, 96, 30);

		Composite compositeright = new Composite(sashForm, SWT.BORDER);
		compositeright.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		CLabel lbPhancongcongviec = new CLabel(compositeright, SWT.BORDER | SWT.SHADOW_OUT | SWT.SHADOW_NONE);
		lbPhancongcongviec.setLeftMargin(15);
		lbPhancongcongviec.setText("Phân Công Công Việc");
		lbPhancongcongviec.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbPhancongcongviec.setBackground(SWTResourceManager.getColor(51, 153, 255));
		lbPhancongcongviec.setBounds(0, 0, sizemonitorx, 30);

		CLabel lbNguoiduocphancong = new CLabel(compositeright, SWT.NONE);
		lbNguoiduocphancong.setAlignment(SWT.RIGHT);
		lbNguoiduocphancong.setText("Người Được Phân Công");
		lbNguoiduocphancong.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbNguoiduocphancong.setBounds(22, 50, 193, 25);

		CLabel lbGhichu = new CLabel(compositeright, SWT.RIGHT);
		lbGhichu.setText("Ghi Chú");
		lbGhichu.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbGhichu.setBounds(22, 143, 102, 25);

		textGhichu = new Text(compositeright, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		textGhichu.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textGhichu.setBounds(139, 97, 444, 127);
		textGhichu.setTextLimit(200);

		Button btnrightAdd = new Button(compositeright, SWT.NONE);
		btnrightAdd.setText("Add");
		btnrightAdd.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/icon/add.png"));
		btnrightAdd.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnrightAdd.setBounds(203, 236, 91, 30);

		Button btnrightCancel = new Button(compositeright, SWT.NONE);
		btnrightCancel.setText("Cancel");
		btnrightCancel.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/icon/cancel.png"));
		btnrightCancel.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnrightCancel.setBounds(422, 236, 102, 30);

		Button btnrightDelete = new Button(compositeright, SWT.NONE);
		btnrightDelete.setText("Delete");
		btnrightDelete.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/icon/delete.png"));
		btnrightDelete.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnrightDelete.setBounds(307, 236, 102, 30);

		tableright = new Table(compositeright, SWT.BORDER | SWT.FULL_SELECTION);
		tableright.setHeaderBackground(SWTResourceManager.getColor(255, 153, 51));
		tableright.setLinesVisible(true);
		tableright.setHeaderVisible(true);
		tableright.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		tableright.setBounds(0, 273, 1920, 730);
		tableright.setSize(sizemonitorx / 2, sizemonitory - 350);
		// tableright.setSize(compositeright.getSize().x, sizemonitory - 350);

		TableColumn tbclrightCongviec = new TableColumn(tableright, SWT.NONE);
		tbclrightCongviec.setWidth(96);
		tbclrightCongviec.setText("Công Việc");

		TableColumn tbclrightNguoiphancong = new TableColumn(tableright, SWT.NONE);
		tbclrightNguoiphancong.setWidth(137);
		tbclrightNguoiphancong.setText("Người Phân Công");

		TableColumn tbclrightNguoiduocphancong = new TableColumn(tableright, SWT.NONE);
		tbclrightNguoiduocphancong.setWidth(187);
		tbclrightNguoiduocphancong.setText("Người Được Phân Công");

		TableColumn tbclrightThoigianphancong = new TableColumn(tableright, SWT.NONE);
		tbclrightThoigianphancong.setWidth(163);
		tbclrightThoigianphancong.setText("Thời Gian Phân Công");

		TableColumn tbclrightGhichu = new TableColumn(tableright, SWT.NONE);
		tbclrightGhichu.setWidth(74);
		tbclrightGhichu.setText("Ghi Chú");

		Combo comboNguoiduocphancong = new Combo(compositeright, SWT.NONE);
		comboNguoiduocphancong.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		comboNguoiduocphancong.setBounds(221, 50, 262, 27);
		sashForm.setWeights(new int[] { 1, 1 });

		// Ngôn Ngữ
		// *************************************************************************************************************************
		if (ngonngu == 0) {
			shell.setText("Danh sách công việc");
			lbDanhsachcongviec.setText("Danh Sách Công Việc");
			lbCongviec.setText("Công Việc");
			lbBatdau.setText("Bắt Đầu");
			lbKetthuc.setText("Kết Thúc");
			lbTiendo.setText("Tiến Độ");
			btncheckChuahoanthanh.setText("Chưa Hoàn Thành");
			btncheckHoanthanh.setText("Hoàn Thành");
			lbNoidung.setText("Nội Dung");
			btnSearch.setText("Tìm Kiếm");
			btnAdd.setText("Thêm");
			btnUpdate.setText("Cập Nhật");
			btnSave.setText("Lưu");
			btnDelete.setText("Xóa");
			btnCancel.setText("Hủy");
			tbclMa.setText("Mã");
			tbclCongviec.setText("Công Việc");
			tbclBatdau.setText("Bắt Đầu");
			tbclKetthuc.setText("Kết Thúc");
			tbclTiendo.setText("Tiến Độ");
			tbclnoidung.setText("Nội Dung");
			tbclHoanthanh.setText("Hoàn Thành");
			lbPhancongcongviec.setText("Phân Công Công Việc");
			lbNguoiduocphancong.setText("Người Được Phân Công");
			lbGhichu.setText("Ghi Chú");
			btnrightAdd.setText("Thêm");
			btnrightCancel.setText("Hủy");
			btnrightDelete.setText("Xóa");
			tbclrightCongviec.setText("Công Việc");
			tbclrightNguoiphancong.setText("Người Phân Công");
			tbclrightNguoiduocphancong.setText("Người Được Phân Công");
			tbclrightThoigianphancong.setText("Thời Gian Phân Công");
			tbclrightGhichu.setText("Ghi Chú");
		} else if (ngonngu == 1) {
			shell.setText("Work list");
			lbDanhsachcongviec.setText("Work List");
			lbCongviec.setText("Work");
			lbBatdau.setText("Start");
			lbKetthuc.setText("End");
			lbTiendo.setText("Progress");
			btncheckChuahoanthanh.setText("Incomplete");
			btncheckHoanthanh.setText("Complete");
			lbNoidung.setText("Content");
			btnSearch.setText("Search");
			btnAdd.setText("Add");
			btnUpdate.setText("Update");
			btnSave.setText("Save");
			btnDelete.setText("Delete");
			btnCancel.setText("Cancel");
			tbclMa.setText("Code");
			tbclCongviec.setText("Work");
			tbclBatdau.setText("Start");
			tbclKetthuc.setText("End");
			tbclTiendo.setText("Progress");
			tbclnoidung.setText("Content");
			tbclHoanthanh.setText("Complete");
			lbPhancongcongviec.setText("Job Assignment");
			lbNguoiduocphancong.setText("The Person Assigned");
			lbGhichu.setText("Note");
			btnrightAdd.setText("Add");
			btnrightCancel.setText("Cancel");
			btnrightDelete.setText("Delete");
			tbclrightCongviec.setText("Work");
			tbclrightNguoiphancong.setText("Taskmaster");
			tbclrightNguoiduocphancong.setText("The Person Assigned");
			tbclrightThoigianphancong.setText("Assignment Time");
			tbclrightGhichu.setText("Note");
		} else if (ngonngu == 2) {
			shell.setText("工作清單");
			lbDanhsachcongviec.setText("工作清單");
			lbCongviec.setText("工作");
			lbBatdau.setText("開始");
			lbKetthuc.setText("結束");
			lbTiendo.setText("進展");
			btncheckChuahoanthanh.setText("殘缺");
			btncheckHoanthanh.setText("完成");
			lbNoidung.setText("內容");
			btnSearch.setText("搜索");
			btnAdd.setText("加");
			btnUpdate.setText("更新");
			btnSave.setText("儲存");
			btnDelete.setText("刪除");
			btnCancel.setText("取消");
			tbclMa.setText("碼");
			tbclCongviec.setText("工作");
			tbclBatdau.setText("開始");
			tbclKetthuc.setText("結束");
			tbclTiendo.setText("進展");
			tbclnoidung.setText("內容");
			tbclHoanthanh.setText("完成");
			lbPhancongcongviec.setText("工作分配");
			lbNguoiduocphancong.setText("分配的人");
			lbGhichu.setText("筆記");
			btnrightAdd.setText("加");
			btnrightCancel.setText("取消");
			btnrightDelete.setText("刪除");
			tbclrightCongviec.setText("工作");
			tbclrightNguoiphancong.setText("工作分配者");
			tbclrightNguoiduocphancong.setText("分配的人");
			tbclrightThoigianphancong.setText("作業時間");
			tbclrightGhichu.setText("筆記");
		}

		// Kiểm tra xem bạn có phải là người trực phòng ngày hiện tại không
		// -------------------------------------------------------------------------------------------------------------------------
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			String today = java.time.LocalDateTime.now().getYear() + ""
					+ (java.time.LocalDateTime.now().getMonthValue() - 1) + ""
					+ java.time.LocalDateTime.now().getDayOfMonth();
			String selectnguoitrucphong = "SELECT NguoiTrucThay FROM LichTrucPhong WHERE NgayTruc='" + today + "'";
			ResultSet ketquanguoitrucphong = statement.executeQuery(selectnguoitrucphong);
			while (ketquanguoitrucphong.next()) {
				if (ketquanguoitrucphong.getString(1).compareToIgnoreCase(manguoidung) == 0) {
					nguoitrucphong = true;
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

		// Chỉ cho người dùng Admin hoặc người trực phòng thay đổi bảng Công việc, phân
		// công công việc mà thôi
		// ==================================================================================================================================
		if (!(manguoidung.compareToIgnoreCase("admin") == 0 || nguoitrucphong)) {
			btnAdd.setEnabled(false);
			btnUpdate.setEnabled(false);
			btnDelete.setEnabled(false);
			btnrightAdd.setEnabled(false);
			btnrightDelete.setEnabled(false);
			btnrightCancel.setEnabled(false);
			comboNguoiduocphancong.setEnabled(false);
			textGhichu.setEnabled(false);
		}

		// Thay đổi kích thước composite trái
		// *******************************************************************************************************************
		compositeleft.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				tableleft.setSize(compositeleft.getSize().x, sizemonitory - 350);
			}
		});

		// Thay đổi kích thước composite phải
		// *******************************************************************************************************************
		compositeright.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				tableright.setSize(compositeright.getSize().x, sizemonitory - 350);
			}
		});

		// Search
		// *******************************************************************************************************************
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String ngay = "0" + dateTimeBatdau.getDay();
					ngay = ngay.substring(ngay.length() - 2);
					String thang = "0" + (dateTimeBatdau.getMonth() + 1);
					thang = thang.substring(thang.length() - 2);

					String ngaybatdau = dateTimeBatdau.getYear() + thang + ngay;

					ngay = "0" + dateTimeKetthuc.getDay();
					ngay = ngay.substring(ngay.length() - 2);
					thang = "0" + (dateTimeKetthuc.getMonth() + 1);
					thang = thang.substring(thang.length() - 2);

					String ngayketthuc = dateTimeKetthuc.getYear() + thang + ngay;
					String hoanthanh = "";
					if (btncheckChuahoanthanh.getSelection()) {
						if (btncheckHoanthanh.getSelection()) {
							hoanthanh = "";
						} else {
							hoanthanh = " AND KetThuc=0";
						}
					} else {
						if (btncheckHoanthanh.getSelection()) {
							hoanthanh = " AND KetThuc=1";
						} else {
							hoanthanh = " AND KetThuc=-1";
						}
					}

					String select = "SELECT MaCongViec,TenCongViec,ThoiGianBatDau,ThoiGianKetThuc,TienDo,NoiDung,CASE KetThuc WHEN 0 THEN N'Chưa hoàn thành'ELSE N'Hoàn thành' END AS KetThuc FROM CongViec WHERE TenCongViec LIKE N'%"
							+ textCongviec.getText() + "%' AND (ThoiGianBatDau BETWEEN '" + ngaybatdau + "' AND '"
							+ ngayketthuc + "' OR ThoiGianKetThuc BETWEEN '" + ngaybatdau + "' AND '" + ngayketthuc
							+ "') AND TienDo LIKE N'%" + textTiendo.getText() + "%' AND NoiDung LIKE N'%"
							+ textNoidung.getText() + "%'" + hoanthanh + " ORDER BY MaCongViec";

					ResultSet resultselect = statement.executeQuery(select);
					tableleft.removeAll();
					while (resultselect.next()) {
						String batdau = resultselect.getString(3);
						batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/" + batdau.substring(0, 4);

						String ketthuc = resultselect.getString(4);
						ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
								+ ketthuc.substring(0, 4);

						TableItem item = new TableItem(tableleft, SWT.NONE);
						item.setText(new String[] { resultselect.getString(1), resultselect.getString(2), batdau,
								ketthuc, resultselect.getString(5), resultselect.getString(6),
								resultselect.getString(7) });
					}
					resultselect.close();

					// chỉnh kích thước các cột cho vừa
					tbclCongviec.pack();
					tbclBatdau.pack();
					tbclKetthuc.pack();
					tbclTiendo.pack();
					// tbclnoidung.pack();
					tbclHoanthanh.pack();

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
			}
		});

		// Add
		// *******************************************************************************************************************
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (trangthai != 1) {
					trangthai = 1;
					btnSearch.setEnabled(false);
					btnUpdate.setEnabled(false);
					btnDelete.setEnabled(false);
					btnSave.setEnabled(true);
					btnCancel.setEnabled(true);
					btncheckHoanthanh.setSelection(false);
					btncheckHoanthanh.setEnabled(false);
				}
			}
		});

		// khi button Update được chọn thì checkbox Chưa hoàn thành, hoàn thành chỉ được
		// chọn một trong hai mà thôi
		// ----------------------------------------------------------------------------------------------------------------------
		btncheckChuahoanthanh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (trangthai == 2) {
					if (btncheckChuahoanthanh.getSelection()) {
						btncheckHoanthanh.setSelection(false);
					} else {
						btncheckHoanthanh.setSelection(true);
					}
				}
			}
		});

		// khi button Update được chọn thì checkbox Chưa hoàn thành, hoàn thành chỉ được
		// chọn một trong hai mà thôi
		// --------------------------------------------------------------------------------------------------------------
		btncheckHoanthanh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (trangthai == 2) {
					if (btncheckHoanthanh.getSelection()) {
						btncheckChuahoanthanh.setSelection(false);
					} else {
						btncheckChuahoanthanh.setSelection(true);
					}
				}
			}
		});

		// Update
		// *******************************************************************************************************************
		btnUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (trangthai != 2) {
					try {
						TableItem[] item = tableleft.getSelection();
						macongviecupdatedelete = item[0].getText();

						String batdau = item[0].getText(2);
						String ketthuc = item[0].getText(3);
						dateTimeBatdau.setDate(Integer.parseInt(batdau.substring(6, 10)),
								Integer.parseInt(batdau.substring(3, 5)) - 1, Integer.parseInt(batdau.substring(0, 2)));
						dateTimeKetthuc.setDate(Integer.parseInt(ketthuc.substring(6, 10)),
								Integer.parseInt(ketthuc.substring(3, 5)) - 1,
								Integer.parseInt(ketthuc.substring(0, 2)));

						textCongviec.setText(item[0].getText(1));
						textTiendo.setText(item[0].getText(4));
						textNoidung.setText(item[0].getText(5));
						if (item[0].getText(6).compareToIgnoreCase("Chưa hoàn thành") == 0) {
							btncheckChuahoanthanh.setSelection(true);
							btncheckHoanthanh.setSelection(false);
						} else {
							btncheckChuahoanthanh.setSelection(false);
							btncheckHoanthanh.setSelection(true);
						}
						trangthai = 2;
						btnSearch.setEnabled(false);
						btnAdd.setEnabled(false);
						btnDelete.setEnabled(false);
						btnSave.setEnabled(true);
						btnCancel.setEnabled(true);
					} catch (Exception ee) {
					}
				}
			}
		});

		// Delete
		// *******************************************************************************************************************
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (trangthai != 3) {
					try {
						TableItem[] item = tableleft.getSelection();
						macongviecupdatedelete = item[0].getText();
						vitrixoa = tableleft.getSelectionIndex();

						String batdau = item[0].getText(2);
						String ketthuc = item[0].getText(3);
						dateTimeBatdau.setDate(Integer.parseInt(batdau.substring(6, 10)),
								Integer.parseInt(batdau.substring(3, 5)) - 1, Integer.parseInt(batdau.substring(0, 2)));
						dateTimeKetthuc.setDate(Integer.parseInt(ketthuc.substring(6, 10)),
								Integer.parseInt(ketthuc.substring(3, 5)) - 1,
								Integer.parseInt(ketthuc.substring(0, 2)));

						textCongviec.setText(item[0].getText(1));
						textTiendo.setText(item[0].getText(4));
						textNoidung.setText(item[0].getText(5));
						if (item[0].getText(6).compareToIgnoreCase("Chưa hoàn thành") == 0) {
							btncheckChuahoanthanh.setSelection(true);
							btncheckHoanthanh.setSelection(false);
						} else {
							btncheckChuahoanthanh.setSelection(false);
							btncheckHoanthanh.setSelection(true);
						}
						trangthai = 3;
						btnSearch.setEnabled(false);
						btnAdd.setEnabled(false);
						btnUpdate.setEnabled(false);
						btnSave.setEnabled(true);
						btnCancel.setEnabled(true);

						textCongviec.setEditable(false);
						textGhichu.setEditable(false);
						textNoidung.setEditable(false);
						textTiendo.setEditable(false);
						dateTimeBatdau.setEnabled(false);
						dateTimeKetthuc.setEnabled(false);
						btncheckChuahoanthanh.setEnabled(false);
						btncheckHoanthanh.setEnabled(false);
					} catch (Exception ee) {
					}
				}
			}
		});

		// Save
		// *******************************************************************************************************************
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (trangthai == 1) {
					// Add++++++++++++++++++++++++++++++++++++++++++++++++
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						String ngay = "0" + dateTimeBatdau.getDay();
						ngay = ngay.substring(ngay.length() - 2);
						String thang = "0" + (dateTimeBatdau.getMonth() + 1);
						thang = thang.substring(thang.length() - 2);
						String ngaybatdau = dateTimeBatdau.getYear() + thang + ngay;

						ngay = "0" + dateTimeKetthuc.getDay();
						ngay = ngay.substring(ngay.length() - 2);
						thang = "0" + (dateTimeKetthuc.getMonth() + 1);
						thang = thang.substring(thang.length() - 2);
						String ngayketthuc = dateTimeKetthuc.getYear() + thang + ngay;
						String macongviec = "";
						if (!textCongviec.getText().isEmpty()) {
							// lay macongviec tu dong
							String selectmacongviec = "SELECT TOP 1 MaCongViec FROM CongViec ORDER BY MaCongViec DESC";
							ResultSet ketqua = statement.executeQuery(selectmacongviec);
							while (ketqua.next()) {
								macongviec = ketqua.getString(1);
							}
							if (macongviec.isEmpty()) {
								macongviec = "CV10001";
							} else {
								macongviec = macongviec.substring(2); // cat bo CV con 10001
								macongviec = "CV" + (Integer.parseInt(macongviec) + 1);
							}

							String insert = "INSERT INTO CongViec( MaCongViec ,TenCongViec ,ThoiGianBatDau ,ThoiGianKetThuc ,TienDo ,NoiDung ,KetThuc) VALUES ('"
									+ macongviec + "',N'" + textCongviec.getText() + "','" + ngaybatdau + "','"
									+ ngayketthuc + "',N'" + textTiendo.getText() + "',N'" + textNoidung.getText()
									+ "',0)";
							int ketquainsert = statement.executeUpdate(insert);
							if (ketquainsert > 0) {
								trangthai = 0;
								btnSearch.setEnabled(true);
								btnAdd.setEnabled(true);
								btnUpdate.setEnabled(true);
								btnDelete.setEnabled(true);
								btnSave.setEnabled(false);
								btnCancel.setEnabled(false);
								btncheckHoanthanh.setEnabled(true);
								textCongviec.setText("");
								textTiendo.setText("");
								textNoidung.setText("");
								btncheckChuahoanthanh.setSelection(true);
								btncheckHoanthanh.setSelection(false);
								dateTimeBatdau.setYear(java.time.LocalDateTime.now().getYear());
								dateTimeBatdau.setMonth(java.time.LocalDateTime.now().getMonthValue() - 1);
								dateTimeBatdau.setDay(java.time.LocalDateTime.now().getDayOfMonth());
								dateTimeKetthuc.setYear(java.time.LocalDateTime.now().getYear());
								dateTimeKetthuc.setMonth(java.time.LocalDateTime.now().getMonthValue() - 1);
								dateTimeKetthuc.setDay(java.time.LocalDateTime.now().getDayOfMonth());
								Statement state = conn.createStatement();
								String insertslideshow = "INSERT INTO SlideShowThongBao(MaCongViec,ThoiGianCapNhat,HoanThanh) VALUES ('"
										+ macongviec + "',GETDATE(),0)";
								state.executeUpdate(insertslideshow);
								state.close();
							}

							String select = "SELECT MaCongViec,TenCongViec,ThoiGianBatDau,ThoiGianKetThuc,TienDo,NoiDung,CASE KetThuc WHEN 0 THEN N'Chưa hoàn thành'ELSE N'Hoàn thành' END AS KetThuc FROM CongViec WHERE MaCongViec='"
									+ macongviec + "' AND KetThuc=0 ORDER BY MaCongViec";

							ResultSet resultselect = statement.executeQuery(select);
							tableleft.removeAll();
							while (resultselect.next()) {
								String batdau = resultselect.getString(3);
								batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
										+ batdau.substring(0, 4);

								String ketthuc = resultselect.getString(4);
								ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
										+ ketthuc.substring(0, 4);

								TableItem item = new TableItem(tableleft, SWT.NONE);
								item.setText(new String[] { resultselect.getString(1), resultselect.getString(2),
										batdau, ketthuc, resultselect.getString(5), resultselect.getString(6),
										resultselect.getString(7) });
							}
							resultselect.close();

							// chỉnh kích thước các cột cho vừa
							tbclCongviec.pack();
							tbclBatdau.pack();
							tbclKetthuc.pack();
							tbclTiendo.pack();
							// tbclnoidung.pack();
							tbclHoanthanh.pack();
						} else {
							// Cong viec khong duoc rong
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
				} else if (trangthai == 2) {
					// Update+++++++++++++++++++++++++++++++++++++++++++++++
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						String ngay = "0" + dateTimeBatdau.getDay();
						ngay = ngay.substring(ngay.length() - 2);
						String thang = "0" + (dateTimeBatdau.getMonth() + 1);
						thang = thang.substring(thang.length() - 2);
						String ngaybatdau = dateTimeBatdau.getYear() + thang + ngay;

						ngay = "0" + dateTimeKetthuc.getDay();
						ngay = ngay.substring(ngay.length() - 2);
						thang = "0" + (dateTimeKetthuc.getMonth() + 1);
						thang = thang.substring(thang.length() - 2);
						String ngayketthuc = dateTimeKetthuc.getYear() + thang + ngay;
						int ketthuchoanthanh = 0;
						if (btncheckChuahoanthanh.getSelection()) {
							ketthuchoanthanh = 0;
						} else {
							ketthuchoanthanh = 1;
						}
						if (!textCongviec.getText().isEmpty()) {
							String update = "UPDATE CongViec SET TenCongViec=N'" + textCongviec.getText()
									+ "' ,ThoiGianBatDau='" + ngaybatdau + "' ,ThoiGianKetThuc='" + ngayketthuc
									+ "' ,TienDo=N'" + textTiendo.getText() + "' ,NoiDung=N'" + textNoidung.getText()
									+ "' ,KetThuc=" + ketthuchoanthanh + " WHERE MaCongViec='" + macongviecupdatedelete
									+ "'";
							int ketquaupdate = statement.executeUpdate(update);
							if (ketquaupdate > 0) {
								trangthai = 0;
								btnSearch.setEnabled(true);
								btnAdd.setEnabled(true);
								btnUpdate.setEnabled(true);
								btnDelete.setEnabled(true);
								btnSave.setEnabled(false);
								btnCancel.setEnabled(false);
								btncheckHoanthanh.setEnabled(true);
								textCongviec.setText("");
								textTiendo.setText("");
								textNoidung.setText("");
								btncheckChuahoanthanh.setSelection(true);
								btncheckHoanthanh.setSelection(false);
								dateTimeBatdau.setYear(java.time.LocalDateTime.now().getYear());
								dateTimeBatdau.setMonth(java.time.LocalDateTime.now().getMonthValue() - 1);
								dateTimeBatdau.setDay(java.time.LocalDateTime.now().getDayOfMonth());
								dateTimeKetthuc.setYear(java.time.LocalDateTime.now().getYear());
								dateTimeKetthuc.setMonth(java.time.LocalDateTime.now().getMonthValue() - 1);
								dateTimeKetthuc.setDay(java.time.LocalDateTime.now().getDayOfMonth());
							}

							String select = "SELECT MaCongViec,TenCongViec,ThoiGianBatDau,ThoiGianKetThuc,TienDo,NoiDung,CASE KetThuc WHEN 0 THEN N'Chưa hoàn thành'ELSE N'Hoàn thành' END AS KetThuc FROM CongViec WHERE MaCongViec='"
									+ macongviecupdatedelete + "' ORDER BY MaCongViec";

							ResultSet resultselect = statement.executeQuery(select);
							tableleft.removeAll();
							while (resultselect.next()) {
								String batdau = resultselect.getString(3);
								batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
										+ batdau.substring(0, 4);

								String ketthuc = resultselect.getString(4);
								ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
										+ ketthuc.substring(0, 4);

								TableItem item = new TableItem(tableleft, SWT.NONE);
								item.setText(new String[] { resultselect.getString(1), resultselect.getString(2),
										batdau, ketthuc, resultselect.getString(5), resultselect.getString(6),
										resultselect.getString(7) });
							}
							resultselect.close();

							// chỉnh kích thước các cột cho vừa
							tbclCongviec.pack();
							tbclBatdau.pack();
							tbclKetthuc.pack();
							tbclTiendo.pack();
							// tbclnoidung.pack();
							tbclHoanthanh.pack();
						} else {
							// Cong viec khong duoc rong
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
				} else if (trangthai == 3) {
					// Delete++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						if (!textCongviec.getText().isEmpty()) {
							String delete = "DELETE CongViec WHERE MaCongViec='" + macongviecupdatedelete + "'";
							int ketquadelete = statement.executeUpdate(delete);
							if (ketquadelete > 0) {
								trangthai = 0;
								btnSearch.setEnabled(true);
								btnAdd.setEnabled(true);
								btnUpdate.setEnabled(true);
								btnSave.setEnabled(false);
								btnCancel.setEnabled(false);
								btncheckHoanthanh.setEnabled(true);
								textCongviec.setText("");
								textTiendo.setText("");
								textNoidung.setText("");
								btncheckChuahoanthanh.setSelection(true);
								btncheckHoanthanh.setSelection(false);

								dateTimeBatdau.setYear(java.time.LocalDateTime.now().getYear());
								dateTimeBatdau.setMonth(java.time.LocalDateTime.now().getMonthValue() - 1);
								dateTimeBatdau.setDay(java.time.LocalDateTime.now().getDayOfMonth());
								dateTimeKetthuc.setYear(java.time.LocalDateTime.now().getYear());
								dateTimeKetthuc.setMonth(java.time.LocalDateTime.now().getMonthValue() - 1);
								dateTimeKetthuc.setDay(java.time.LocalDateTime.now().getDayOfMonth());

								textCongviec.setEditable(true);
								textGhichu.setEditable(true);
								textNoidung.setEditable(true);
								textTiendo.setEditable(true);
								dateTimeBatdau.setEnabled(true);
								dateTimeKetthuc.setEnabled(true);
								btncheckChuahoanthanh.setEnabled(true);
								btncheckHoanthanh.setEnabled(true);

								tableleft.remove(vitrixoa);

								String xoaslideshow = "DELETE FROM SlideShowThongBao WHERE MaCongViec='"
										+ macongviecupdatedelete + "'";
								Statement state = conn.createStatement();
								state.executeUpdate(xoaslideshow);
								state.close();
							}

							// chỉnh kích thước các cột cho vừa
							tbclCongviec.pack();
							tbclBatdau.pack();
							tbclKetthuc.pack();
							tbclTiendo.pack();
							// tbclnoidung.pack();
							tbclHoanthanh.pack();
						} else {
							// Cong viec khong duoc rong
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
				}
			}
		});

		// Cancel
		// *******************************************************************************************************************
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				trangthai = 0;
				btnSearch.setEnabled(true);
				btnAdd.setEnabled(true);
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
				btnSave.setEnabled(false);
				btnCancel.setEnabled(false);
				btncheckHoanthanh.setEnabled(true);
				textCongviec.setText("");
				textTiendo.setText("");
				textNoidung.setText("");
				btncheckChuahoanthanh.setSelection(true);
				btncheckHoanthanh.setSelection(false);
				dateTimeBatdau.setYear(java.time.LocalDateTime.now().getYear());
				dateTimeBatdau.setMonth(java.time.LocalDateTime.now().getMonthValue() - 1);
				dateTimeBatdau.setDay(java.time.LocalDateTime.now().getDayOfMonth());
				dateTimeKetthuc.setYear(java.time.LocalDateTime.now().getYear());
				dateTimeKetthuc.setMonth(java.time.LocalDateTime.now().getMonthValue() - 1);
				dateTimeKetthuc.setDay(java.time.LocalDateTime.now().getDayOfMonth());

				textCongviec.setEditable(true);
				textGhichu.setEditable(true);
				textNoidung.setEditable(true);
				textTiendo.setEditable(true);
				dateTimeBatdau.setEnabled(true);
				dateTimeKetthuc.setEnabled(true);
				btncheckChuahoanthanh.setEnabled(true);
				btncheckHoanthanh.setEnabled(true);
			}
		});

		// Xử lý phân công công việc
		// ===========================================================================================================
		// Lấy dữ liệu cho combo người được phân công
		// -----------------------------------------------------------------------------------------------------
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			// Lấy dữ liệu cho combo người được phân công
			String select = "SELECT TenNguoiDung FROM NguoiDung WHERE MaNhomNguoiDung='it' ORDER BY TenNguoiDung";
			ResultSet resultcombo = statement.executeQuery(select);
			while (resultcombo.next()) {
				comboNguoiduocphancong.add(resultcombo.getString(1));
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

		// Lấy dữ liệu cho table right lúc đầu, chọn 1 dòng của table left thì sẽ hiển
		// thị danh sách được phân công của công việc đó
		// -------------------------------------------------------------------------------------------------------------------------
		tableleft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					TableItem[] itemmacongviec = tableleft.getSelection();
					macongviecright = itemmacongviec[0].getText();

					String select = "SELECT CongViec.TenCongViec,NguoiDung.TenNguoiDung AS NguoiPhanCong,ND.TenNguoiDung AS NguoiDuocPhanCong,ThoiGianPhanCong,PhanCongCongViec.GhiChu FROM PhanCongCongViec,CongViec,NguoiDung,NguoiDung AS ND WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.NguoiDuocPhanCong=ND.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
							+ macongviecright + "'";

					ResultSet resultselect = statement.executeQuery(select);
					tableright.removeAll();
					while (resultselect.next()) {
						String thoigiancapnhat = resultselect.getString(4);
						thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/" + thoigiancapnhat.substring(5, 7) + "/"
								+ thoigiancapnhat.substring(0, 4) + " " + thoigiancapnhat.substring(11, 19);

						TableItem item = new TableItem(tableright, SWT.NONE);
						item.setText(new String[] { resultselect.getString(1), resultselect.getString(2),
								resultselect.getString(3), thoigiancapnhat, resultselect.getString(5) });
					}
					resultselect.close();

					// chỉnh kích thước các cột cho vừa
					tbclrightCongviec.pack();
					tbclrightNguoiphancong.pack();
					tbclrightNguoiduocphancong.pack();
					tbclrightThoigianphancong.pack();
					tbclrightGhichu.pack();

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
			}
		});

		// Add Phân công công việc
		// -----------------------------------------------------------------------------------
		btnrightAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!comboNguoiduocphancong.getText().isEmpty()) {
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						String nguoiduocphancong = "";
						// Lấy số thẻ người được phân công
						String selectsothe = "SELECT MaNguoiDung FROM NguoiDung WHERE MaNhomNguoiDung='it' AND TenNguoiDung=N'"
								+ comboNguoiduocphancong.getText() + "'";
						ResultSet result = statement.executeQuery(selectsothe);
						while (result.next()) {
							nguoiduocphancong = result.getString(1);
						}
						result.close();

						// Xử lý trường hợp đã phân công người dùng rồi, không được phân công nữa
						String sothedaphancong = "";
						String selectdaphancong = "SELECT TOP 1 NguoiDuocPhanCong FROM PhanCongCongViec WHERE MaCongViec='"
								+ macongviecright + "' AND NguoiDuocPhanCong='" + nguoiduocphancong + "'";
						ResultSet ketquadaphancong = statement.executeQuery(selectdaphancong);
						while (ketquadaphancong.next()) {
							sothedaphancong = ketquadaphancong.getString(1);
						}
						ketquadaphancong.close();

						if (nguoiduocphancong.compareToIgnoreCase(sothedaphancong) != 0) {
							String insertpccv = "INSERT INTO PhanCongCongViec(MaCongViec,NguoiPhanCong,NguoiDuocPhanCong,ThoiGianPhanCong,GhiChu) VALUES  ('"
									+ macongviecright + "','" + manguoidung + "','" + nguoiduocphancong
									+ "',GETDATE(),N'" + textGhichu.getText() + "')";
							if (statement.executeUpdate(insertpccv) > 0) {
								String select = "SELECT CongViec.TenCongViec,NguoiDung.TenNguoiDung AS NguoiPhanCong,ND.TenNguoiDung AS NguoiDuocPhanCong,ThoiGianPhanCong,PhanCongCongViec.GhiChu FROM PhanCongCongViec,CongViec,NguoiDung,NguoiDung AS ND WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.NguoiDuocPhanCong=ND.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
										+ macongviecright + "'";

								ResultSet resultselect = statement.executeQuery(select);
								tableright.removeAll();
								while (resultselect.next()) {
									String thoigiancapnhat = resultselect.getString(4);
									thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/"
											+ thoigiancapnhat.substring(5, 7) + "/" + thoigiancapnhat.substring(0, 4)
											+ " " + thoigiancapnhat.substring(11, 19);

									TableItem item = new TableItem(tableright, SWT.NONE);
									item.setText(new String[] { resultselect.getString(1), resultselect.getString(2),
											resultselect.getString(3), thoigiancapnhat, resultselect.getString(5) });
								}
								resultselect.close();

								// chỉnh kích thước các cột cho vừa
								tbclrightCongviec.pack();
								tbclrightNguoiphancong.pack();
								tbclrightNguoiduocphancong.pack();
								tbclrightThoigianphancong.pack();
								tbclrightGhichu.pack();

								// Thêm vào bảng thông báo
								String insertthongbao = "INSERT INTO ThongBao(MaCongViec,NguoiDuocThongBao,DaXem,DaXemUser,ThoiGianThongBao) VALUES('"
										+ macongviecright + "','" + nguoiduocphancong + "',0,0,GETDATE())";
								Statement statethongbao = conn.createStatement();
								statethongbao.executeUpdate(insertthongbao);
								statethongbao.close();
							}
						} else {
							MessageBox message = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
							if (ngonngu == 0) {
								message.setText("Thông báo");
								message.setMessage("Nguời này đã được phân công rồi!");
							} else if (ngonngu == 1) {
								message.setText("Notification");
								message.setMessage("This person has already been assigned!");
							} else if (ngonngu == 2) {
								message.setText("通知");
								message.setMessage("此人已被分配!");
							}
							message.open();
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
				}
			}
		});

		// Delete Phân công công việc
		// -----------------------------------------------------------------------------------
		btnrightDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String nguoiduocphancongxoa = "";
					TableItem[] itemmacongviec = tableright.getSelection();
					nguoiduocphancongxoa = itemmacongviec[0].getText(2);
					// Lấy số thẻ người được phân công
					String selectsothe = "SELECT MaNguoiDung FROM NguoiDung WHERE MaNhomNguoiDung='it' AND TenNguoiDung=N'"
							+ nguoiduocphancongxoa + "'";
					ResultSet result = statement.executeQuery(selectsothe);
					while (result.next()) {
						nguoiduocphancongxoa = result.getString(1);
					}
					result.close();

					String select = "DELETE PhanCongCongViec WHERE MaCongViec='" + macongviecright
							+ "' AND NguoiDuocPhanCong='" + nguoiduocphancongxoa + "'";
					if (statement.executeUpdate(select) > 0) {
						tableright.remove(tableright.getSelectionIndex());
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
			}
		});

		// Cancel Phân công công việc
		// -----------------------------------------------------------------------------------
		btnrightCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comboNguoiduocphancong.setText("");
				textGhichu.setText("");
			}
		});
	}

	// Ctabfolder
	// ======================================================================================================================================================================
	protected void createContentsTabfolder(CTabFolder tabfolder, Shell shell, String manguoidung,
			String manhomnguoidung, int ngonngu,String chuoiketnoi) {
		db_url=chuoiketnoi;
		CTabItem itemtab = new CTabItem(tabfolder, SWT.CLOSE);
		itemtab.setText("Danh sách công việc");
		Composite composite = new Composite(tabfolder, SWT.NONE);
		itemtab.setControl(composite);

		composite.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		SashForm sashForm = new SashForm(composite, SWT.NONE);
		sashForm.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		Composite compositeleft = new Composite(sashForm, SWT.BORDER);
		compositeleft.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		CLabel lbDanhsachcongviec = new CLabel(compositeleft, SWT.BORDER | SWT.SHADOW_IN | SWT.SHADOW_NONE);
		lbDanhsachcongviec.setLeftMargin(15);
		lbDanhsachcongviec.setBackground(SWTResourceManager.getColor(51, 153, 255));
		lbDanhsachcongviec.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbDanhsachcongviec.setBounds(0, 0, sizemonitorx, 30);
		lbDanhsachcongviec.setText("Danh Sách Công Việc");

		CLabel lbCongviec = new CLabel(compositeleft, SWT.NONE);
		lbCongviec.setAlignment(SWT.RIGHT);
		lbCongviec.setText("Công Việc");
		lbCongviec.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbCongviec.setBounds(10, 48, 102, 25);

		textCongviec = new Text(compositeleft, SWT.BORDER);
		textCongviec.setBounds(119, 48, 461, 25);
		textCongviec.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textCongviec.setTextLimit(100);

		CLabel lbBatdau = new CLabel(compositeleft, SWT.NONE);
		lbBatdau.setAlignment(SWT.RIGHT);
		lbBatdau.setText("Bắt Đầu");
		lbBatdau.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbBatdau.setBounds(10, 79, 102, 25);

		DateTime dateTimeBatdau = new DateTime(compositeleft, SWT.BORDER);
		dateTimeBatdau.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		dateTimeBatdau.setBounds(119, 79, 126, 25);

		CLabel lbKetthuc = new CLabel(compositeleft, SWT.NONE);
		lbKetthuc.setAlignment(SWT.RIGHT);
		lbKetthuc.setText("Kết Thúc");
		lbKetthuc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbKetthuc.setBounds(251, 79, 116, 25);

		DateTime dateTimeKetthuc = new DateTime(compositeleft, SWT.BORDER);
		dateTimeKetthuc.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		dateTimeKetthuc.setBounds(373, 79, 126, 25);

		CLabel lbTiendo = new CLabel(compositeleft, SWT.NONE);
		lbTiendo.setAlignment(SWT.RIGHT);
		lbTiendo.setText("Tiến Độ");
		lbTiendo.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbTiendo.setBounds(10, 110, 102, 25);

		textTiendo = new Text(compositeleft, SWT.BORDER);
		textTiendo.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textTiendo.setBounds(118, 110, 266, 25);
		textTiendo.setTextLimit(50);

		Button btncheckChuahoanthanh = new Button(compositeleft, SWT.CHECK);
		btncheckChuahoanthanh.setSelection(true);
		btncheckChuahoanthanh.setText("Chưa Hoàn Thành");
		btncheckChuahoanthanh.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btncheckChuahoanthanh.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btncheckChuahoanthanh.setBounds(390, 110, 146, 25);

		Button btncheckHoanthanh = new Button(compositeleft, SWT.CHECK);
		btncheckHoanthanh.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btncheckHoanthanh.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btncheckHoanthanh.setBounds(542, 110, 114, 25);
		btncheckHoanthanh.setText("Hoàn Thành");

		CLabel lbNoidung = new CLabel(compositeleft, SWT.NONE);
		lbNoidung.setAlignment(SWT.RIGHT);
		lbNoidung.setText("Nội Dung");
		lbNoidung.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbNoidung.setBounds(10, 170, 102, 25);

		textNoidung = new Text(compositeleft, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		textNoidung.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textNoidung.setBounds(119, 141, 541, 90);
		textNoidung.setTextLimit(200);

		Button btnSearch = new Button(compositeleft, SWT.NONE);
		btnSearch.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/icon/search.png"));
		btnSearch.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnSearch.setBounds(31, 237, 116, 30);
		btnSearch.setText("Search");

		Button btnAdd = new Button(compositeleft, SWT.NONE);
		btnAdd.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/icon/add.png"));
		btnAdd.setText("Add");
		btnAdd.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnAdd.setBounds(153, 237, 91, 30);

		Button btnUpdate = new Button(compositeleft, SWT.NONE);
		btnUpdate.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/Images/update.png"));
		btnUpdate.setText("Update");
		btnUpdate.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnUpdate.setBounds(250, 237, 110, 30);

		Button btnDelete = new Button(compositeleft, SWT.NONE);
		btnDelete.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/icon/delete.png"));
		btnDelete.setText("Delete");
		btnDelete.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnDelete.setBounds(366, 237, 102, 30);

		Button btnSave = new Button(compositeleft, SWT.NONE);
		btnSave.setEnabled(false);
		btnSave.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/icon/save.png"));
		btnSave.setText("Save");
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnSave.setBounds(474, 237, 83, 30);

		tableleft = new Table(compositeleft, SWT.BORDER | SWT.FULL_SELECTION);
		tableleft.setHeaderBackground(SWTResourceManager.getColor(255, 153, 51));
		tableleft.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		tableleft.setBounds(0, 273, 1920, 724);
		// tableleft.setSize(sizemonitorx / 2, sizemonitory - 350);
		tableleft.setSize(compositeleft.getSize().x, sizemonitory - 400);
		tableleft.setHeaderVisible(true);
		tableleft.setLinesVisible(true);

		TableColumn tbclMa = new TableColumn(tableleft, SWT.NONE);
		// tbclMa.setWidth(61);
		tbclMa.setWidth(0);
		tbclMa.setText("Mã");
		tbclMa.setResizable(false);

		TableColumn tbclCongviec = new TableColumn(tableleft, SWT.NONE);
		tbclCongviec.setWidth(102);
		tbclCongviec.setText("Công Việc");

		TableColumn tbclBatdau = new TableColumn(tableleft, SWT.NONE);
		tbclBatdau.setWidth(83);
		tbclBatdau.setText("Bắt Đầu");

		TableColumn tbclKetthuc = new TableColumn(tableleft, SWT.NONE);
		tbclKetthuc.setWidth(90);
		tbclKetthuc.setText("Kết Thúc");

		TableColumn tbclTiendo = new TableColumn(tableleft, SWT.NONE);
		tbclTiendo.setWidth(103);
		tbclTiendo.setText("Tiến Độ");

		TableColumn tbclnoidung = new TableColumn(tableleft, SWT.NONE);
		tbclnoidung.setWidth(96);
		tbclnoidung.setText("Nội Dung");

		TableColumn tbclHoanthanh = new TableColumn(tableleft, SWT.NONE);
		tbclHoanthanh.setWidth(98);
		tbclHoanthanh.setText("Hoàn Thành");

		Button btnCancel = new Button(compositeleft, SWT.NONE);
		btnCancel.setText("Cancel");
		btnCancel.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/icon/cancel.png"));
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnCancel.setEnabled(false);
		btnCancel.setBounds(563, 237, 96, 30);

		Composite compositeright = new Composite(sashForm, SWT.BORDER);
		compositeright.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		CLabel lbPhancongcongviec = new CLabel(compositeright, SWT.BORDER | SWT.SHADOW_OUT | SWT.SHADOW_NONE);
		lbPhancongcongviec.setLeftMargin(15);
		lbPhancongcongviec.setText("Phân Công Công Việc");
		lbPhancongcongviec.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbPhancongcongviec.setBackground(SWTResourceManager.getColor(51, 153, 255));
		lbPhancongcongviec.setBounds(0, 0, sizemonitorx, 30);

		CLabel lbNguoiduocphancong = new CLabel(compositeright, SWT.NONE);
		lbNguoiduocphancong.setAlignment(SWT.RIGHT);
		lbNguoiduocphancong.setText("Người Được Phân Công");
		lbNguoiduocphancong.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbNguoiduocphancong.setBounds(22, 50, 193, 25);

		CLabel lbGhichu = new CLabel(compositeright, SWT.RIGHT);
		lbGhichu.setText("Ghi Chú");
		lbGhichu.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbGhichu.setBounds(22, 143, 102, 25);

		textGhichu = new Text(compositeright, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		textGhichu.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textGhichu.setBounds(139, 97, 444, 127);
		textGhichu.setTextLimit(200);

		Button btnrightAdd = new Button(compositeright, SWT.NONE);
		btnrightAdd.setText("Add");
		btnrightAdd.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/icon/add.png"));
		btnrightAdd.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnrightAdd.setBounds(203, 236, 91, 30);

		Button btnrightCancel = new Button(compositeright, SWT.NONE);
		btnrightCancel.setText("Cancel");
		btnrightCancel.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/icon/cancel.png"));
		btnrightCancel.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnrightCancel.setBounds(422, 236, 102, 30);

		Button btnrightDelete = new Button(compositeright, SWT.NONE);
		btnrightDelete.setText("Delete");
		btnrightDelete.setImage(SWTResourceManager.getImage(DanhSachCongViec.class, "/manager/icon/delete.png"));
		btnrightDelete.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnrightDelete.setBounds(307, 236, 102, 30);

		tableright = new Table(compositeright, SWT.BORDER | SWT.FULL_SELECTION);
		tableright.setHeaderBackground(SWTResourceManager.getColor(255, 153, 51));
		tableright.setLinesVisible(true);
		tableright.setHeaderVisible(true);
		tableright.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		tableright.setBounds(0, 273, 500, 300);
		tableright.setSize(compositeright.getSize().x, sizemonitory - 400);

		TableColumn tbclrightCongviec = new TableColumn(tableright, SWT.NONE);
		tbclrightCongviec.setWidth(96);
		tbclrightCongviec.setText("Công Việc");

		TableColumn tbclrightNguoiphancong = new TableColumn(tableright, SWT.NONE);
		tbclrightNguoiphancong.setWidth(137);
		tbclrightNguoiphancong.setText("Người Phân Công");

		TableColumn tbclrightNguoiduocphancong = new TableColumn(tableright, SWT.NONE);
		tbclrightNguoiduocphancong.setWidth(187);
		tbclrightNguoiduocphancong.setText("Người Được Phân Công");

		TableColumn tbclrightThoigianphancong = new TableColumn(tableright, SWT.NONE);
		tbclrightThoigianphancong.setWidth(163);
		tbclrightThoigianphancong.setText("Thời Gian Phân Công");

		TableColumn tbclrightGhichu = new TableColumn(tableright, SWT.NONE);
		tbclrightGhichu.setWidth(74);
		tbclrightGhichu.setText("Ghi Chú");

		Combo comboNguoiduocphancong = new Combo(compositeright, SWT.NONE);
		comboNguoiduocphancong.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		comboNguoiduocphancong.setBounds(221, 50, 262, 27);
		sashForm.setWeights(new int[] { 1, 1 });

		// Ngôn Ngữ
		// *************************************************************************************************************************
		if (ngonngu == 0) {
			itemtab.setText("Danh sách công việc");
			lbDanhsachcongviec.setText("Danh Sách Công Việc");
			lbCongviec.setText("Công Việc");
			lbBatdau.setText("Bắt Đầu");
			lbKetthuc.setText("Kết Thúc");
			lbTiendo.setText("Tiến Độ");
			btncheckChuahoanthanh.setText("Chưa Hoàn Thành");
			btncheckHoanthanh.setText("Hoàn Thành");
			lbNoidung.setText("Nội Dung");
			btnSearch.setText("Tìm Kiếm");
			btnAdd.setText("Thêm");
			btnUpdate.setText("Cập Nhật");
			btnSave.setText("Lưu");
			btnDelete.setText("Xóa");
			btnCancel.setText("Hủy");
			tbclMa.setText("Mã");
			tbclCongviec.setText("Công Việc");
			tbclBatdau.setText("Bắt Đầu");
			tbclKetthuc.setText("Kết Thúc");
			tbclTiendo.setText("Tiến Độ");
			tbclnoidung.setText("Nội Dung");
			tbclHoanthanh.setText("Hoàn Thành");
			lbPhancongcongviec.setText("Phân Công Công Việc");
			lbNguoiduocphancong.setText("Người Được Phân Công");
			lbGhichu.setText("Ghi Chú");
			btnrightAdd.setText("Thêm");
			btnrightCancel.setText("Hủy");
			btnrightDelete.setText("Xóa");
			tbclrightCongviec.setText("Công Việc");
			tbclrightNguoiphancong.setText("Người Phân Công");
			tbclrightNguoiduocphancong.setText("Người Được Phân Công");
			tbclrightThoigianphancong.setText("Thời Gian Phân Công");
			tbclrightGhichu.setText("Ghi Chú");
		} else if (ngonngu == 1) {
			itemtab.setText("Work list");
			lbDanhsachcongviec.setText("Work List");
			lbCongviec.setText("Work");
			lbBatdau.setText("Start");
			lbKetthuc.setText("End");
			lbTiendo.setText("Progress");
			btncheckChuahoanthanh.setText("Incomplete");
			btncheckHoanthanh.setText("Complete");
			lbNoidung.setText("Content");
			btnSearch.setText("Search");
			btnAdd.setText("Add");
			btnUpdate.setText("Update");
			btnSave.setText("Save");
			btnDelete.setText("Delete");
			btnCancel.setText("Cancel");
			tbclMa.setText("Code");
			tbclCongviec.setText("Work");
			tbclBatdau.setText("Start");
			tbclKetthuc.setText("End");
			tbclTiendo.setText("Progress");
			tbclnoidung.setText("Content");
			tbclHoanthanh.setText("Complete");
			lbPhancongcongviec.setText("Job Assignment");
			lbNguoiduocphancong.setText("The Person Assigned");
			lbGhichu.setText("Note");
			btnrightAdd.setText("Add");
			btnrightCancel.setText("Cancel");
			btnrightDelete.setText("Delete");
			tbclrightCongviec.setText("Work");
			tbclrightNguoiphancong.setText("Taskmaster");
			tbclrightNguoiduocphancong.setText("The Person Assigned");
			tbclrightThoigianphancong.setText("Assignment Time");
			tbclrightGhichu.setText("Note");
		} else if (ngonngu == 2) {
			itemtab.setText("工作清單");
			lbDanhsachcongviec.setText("工作清單");
			lbCongviec.setText("工作");
			lbBatdau.setText("開始");
			lbKetthuc.setText("結束");
			lbTiendo.setText("進展");
			btncheckChuahoanthanh.setText("殘缺");
			btncheckHoanthanh.setText("完成");
			lbNoidung.setText("內容");
			btnSearch.setText("搜索");
			btnAdd.setText("加");
			btnUpdate.setText("更新");
			btnSave.setText("儲存");
			btnDelete.setText("刪除");
			btnCancel.setText("取消");
			tbclMa.setText("碼");
			tbclCongviec.setText("工作");
			tbclBatdau.setText("開始");
			tbclKetthuc.setText("結束");
			tbclTiendo.setText("進展");
			tbclnoidung.setText("內容");
			tbclHoanthanh.setText("完成");
			lbPhancongcongviec.setText("工作分配");
			lbNguoiduocphancong.setText("分配的人");
			lbGhichu.setText("筆記");
			btnrightAdd.setText("加");
			btnrightCancel.setText("取消");
			btnrightDelete.setText("刪除");
			tbclrightCongviec.setText("工作");
			tbclrightNguoiphancong.setText("工作分配者");
			tbclrightNguoiduocphancong.setText("分配的人");
			tbclrightThoigianphancong.setText("作業時間");
			tbclrightGhichu.setText("筆記");
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

		// Chỉ cho người dùng Admin hoặc người trực phòng thay đổi bảng phân công công
		// việc mà thôi
		// ==================================================================================================================================
		if (!(manhomnguoidung.compareToIgnoreCase("admin") == 0 || nguoitrucphong)) {
			btnrightAdd.setEnabled(false);
			btnrightDelete.setEnabled(false);
			btnrightCancel.setEnabled(false);
			comboNguoiduocphancong.setEnabled(false);
			textGhichu.setEnabled(false);
		}

		// Thay đổi kích thước composite trái
		// *******************************************************************************************************************
		compositeleft.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				tableleft.setSize(compositeleft.getSize().x, sizemonitory - 400);
			}
		});

		// Thay đổi kích thước composite phải
		// *******************************************************************************************************************
		compositeright.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				tableright.setSize(compositeright.getSize().x, sizemonitory - 400);
			}
		});

		// Search
		// *******************************************************************************************************************
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String ngay = "0" + dateTimeBatdau.getDay();
					ngay = ngay.substring(ngay.length() - 2);
					String thang = "0" + (dateTimeBatdau.getMonth() + 1);
					thang = thang.substring(thang.length() - 2);

					String ngaybatdau = dateTimeBatdau.getYear() + thang + ngay;

					ngay = "0" + dateTimeKetthuc.getDay();
					ngay = ngay.substring(ngay.length() - 2);
					thang = "0" + (dateTimeKetthuc.getMonth() + 1);
					thang = thang.substring(thang.length() - 2);

					String ngayketthuc = dateTimeKetthuc.getYear() + thang + ngay;
					String hoanthanh = "";
					if (btncheckChuahoanthanh.getSelection()) {
						if (btncheckHoanthanh.getSelection()) {
							hoanthanh = "";
						} else {
							hoanthanh = " AND KetThuc=0";
						}
					} else {
						if (btncheckHoanthanh.getSelection()) {
							hoanthanh = " AND KetThuc=1";
						} else {
							hoanthanh = " AND KetThuc=-1";
						}
					}

					String select = "SELECT MaCongViec,TenCongViec,ThoiGianBatDau,ThoiGianKetThuc,TienDo,NoiDung,CASE KetThuc WHEN 0 THEN N'Chưa hoàn thành'ELSE N'Hoàn thành' END AS KetThuc FROM CongViec WHERE TenCongViec LIKE N'%"
							+ textCongviec.getText() + "%' AND (ThoiGianBatDau BETWEEN '" + ngaybatdau + "' AND '"
							+ ngayketthuc + "' OR ThoiGianKetThuc BETWEEN '" + ngaybatdau + "' AND '" + ngayketthuc
							+ "') AND TienDo LIKE N'%" + textTiendo.getText() + "%' AND NoiDung LIKE N'%"
							+ textNoidung.getText() + "%'" + hoanthanh + " ORDER BY MaCongViec";

					ResultSet resultselect = statement.executeQuery(select);
					tableleft.removeAll();
					while (resultselect.next()) {
						String batdau = resultselect.getString(3);
						batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/" + batdau.substring(0, 4);

						String ketthuc = resultselect.getString(4);
						ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
								+ ketthuc.substring(0, 4);

						TableItem item = new TableItem(tableleft, SWT.NONE);
						item.setText(new String[] { resultselect.getString(1), resultselect.getString(2), batdau,
								ketthuc, resultselect.getString(5), resultselect.getString(6),
								resultselect.getString(7) });
					}
					resultselect.close();

					// chỉnh kích thước các cột cho vừa
					tbclCongviec.pack();
					tbclBatdau.pack();
					tbclKetthuc.pack();
					tbclTiendo.pack();
					// tbclnoidung.pack();
					tbclHoanthanh.pack();

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
			}
		});

		// Add
		// *******************************************************************************************************************
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (trangthai != 1) {
					trangthai = 1;
					btnSearch.setEnabled(false);
					btnUpdate.setEnabled(false);
					btnDelete.setEnabled(false);
					btnSave.setEnabled(true);
					btnCancel.setEnabled(true);
					btncheckHoanthanh.setSelection(false);
					btncheckHoanthanh.setEnabled(false);
				}
			}
		});

		// khi button Update được chọn thì checkbox Chưa hoàn thành, hoàn thành chỉ được
		// chọn một trong hai mà thôi
		// ----------------------------------------------------------------------------------------------------------------------
		btncheckChuahoanthanh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (trangthai == 2) {
					if (btncheckChuahoanthanh.getSelection()) {
						btncheckHoanthanh.setSelection(false);
					} else {
						btncheckHoanthanh.setSelection(true);
					}
				}
			}
		});

		// khi button Update được chọn thì checkbox Chưa hoàn thành, hoàn thành chỉ được
		// chọn một trong hai mà thôi
		// --------------------------------------------------------------------------------------------------------------
		btncheckHoanthanh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (trangthai == 2) {
					if (btncheckHoanthanh.getSelection()) {
						btncheckChuahoanthanh.setSelection(false);
					} else {
						btncheckChuahoanthanh.setSelection(true);
					}
				}
			}
		});

		// Update
		// *******************************************************************************************************************
		btnUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (trangthai != 2) {
					try {
						TableItem[] item = tableleft.getSelection();
						macongviecupdatedelete = item[0].getText();

						String batdau = item[0].getText(2);
						String ketthuc = item[0].getText(3);
						dateTimeBatdau.setDate(Integer.parseInt(batdau.substring(6, 10)),
								Integer.parseInt(batdau.substring(3, 5)) - 1, Integer.parseInt(batdau.substring(0, 2)));
						dateTimeKetthuc.setDate(Integer.parseInt(ketthuc.substring(6, 10)),
								Integer.parseInt(ketthuc.substring(3, 5)) - 1,
								Integer.parseInt(ketthuc.substring(0, 2)));

						textCongviec.setText(item[0].getText(1));
						textTiendo.setText(item[0].getText(4));
						textNoidung.setText(item[0].getText(5));
						if (item[0].getText(6).compareToIgnoreCase("Chưa hoàn thành") == 0) {
							btncheckChuahoanthanh.setSelection(true);
							btncheckHoanthanh.setSelection(false);
						} else {
							btncheckChuahoanthanh.setSelection(false);
							btncheckHoanthanh.setSelection(true);
						}
						trangthai = 2;
						btnSearch.setEnabled(false);
						btnAdd.setEnabled(false);
						btnDelete.setEnabled(false);
						btnSave.setEnabled(true);
						btnCancel.setEnabled(true);
					} catch (Exception ee) {
					}
				}
			}
		});

		// Delete
		// *******************************************************************************************************************
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (trangthai != 3) {
					try {
						TableItem[] item = tableleft.getSelection();
						macongviecupdatedelete = item[0].getText();
						vitrixoa = tableleft.getSelectionIndex();

						String batdau = item[0].getText(2);
						String ketthuc = item[0].getText(3);
						dateTimeBatdau.setDate(Integer.parseInt(batdau.substring(6, 10)),
								Integer.parseInt(batdau.substring(3, 5)) - 1, Integer.parseInt(batdau.substring(0, 2)));
						dateTimeKetthuc.setDate(Integer.parseInt(ketthuc.substring(6, 10)),
								Integer.parseInt(ketthuc.substring(3, 5)) - 1,
								Integer.parseInt(ketthuc.substring(0, 2)));

						textCongviec.setText(item[0].getText(1));
						textTiendo.setText(item[0].getText(4));
						textNoidung.setText(item[0].getText(5));
						if (item[0].getText(6).compareToIgnoreCase("Chưa hoàn thành") == 0) {
							btncheckChuahoanthanh.setSelection(true);
							btncheckHoanthanh.setSelection(false);
						} else {
							btncheckChuahoanthanh.setSelection(false);
							btncheckHoanthanh.setSelection(true);
						}
						trangthai = 3;
						btnSearch.setEnabled(false);
						btnAdd.setEnabled(false);
						btnUpdate.setEnabled(false);
						btnSave.setEnabled(true);
						btnCancel.setEnabled(true);

						textCongviec.setEditable(false);
						textGhichu.setEditable(false);
						textNoidung.setEditable(false);
						textTiendo.setEditable(false);
						dateTimeBatdau.setEnabled(false);
						dateTimeKetthuc.setEnabled(false);
						btncheckChuahoanthanh.setEnabled(false);
						btncheckHoanthanh.setEnabled(false);
					} catch (Exception ee) {
					}
				}
			}
		});

		// Save
		// *******************************************************************************************************************
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (trangthai == 1) {
					// Add++++++++++++++++++++++++++++++++++++++++++++++++
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						String ngay = "0" + dateTimeBatdau.getDay();
						ngay = ngay.substring(ngay.length() - 2);
						String thang = "0" + (dateTimeBatdau.getMonth() + 1);
						thang = thang.substring(thang.length() - 2);
						String ngaybatdau = dateTimeBatdau.getYear() + thang + ngay;

						ngay = "0" + dateTimeKetthuc.getDay();
						ngay = ngay.substring(ngay.length() - 2);
						thang = "0" + (dateTimeKetthuc.getMonth() + 1);
						thang = thang.substring(thang.length() - 2);
						String ngayketthuc = dateTimeKetthuc.getYear() + thang + ngay;
						String macongviec = "";
						if (!textCongviec.getText().isEmpty()) {
							// lay macongviec tu dong
							String selectmacongviec = "SELECT TOP 1 MaCongViec FROM CongViec ORDER BY MaCongViec DESC";
							ResultSet ketqua = statement.executeQuery(selectmacongviec);
							while (ketqua.next()) {
								macongviec = ketqua.getString(1);
							}
							if (macongviec.isEmpty()) {
								macongviec = "CV10001";
							} else {
								macongviec = macongviec.substring(2); // cat bo CV con 10001
								macongviec = "CV" + (Integer.parseInt(macongviec) + 1);
							}

							String insert = "INSERT INTO CongViec( MaCongViec ,TenCongViec ,ThoiGianBatDau ,ThoiGianKetThuc ,TienDo ,NoiDung ,KetThuc) VALUES ('"
									+ macongviec + "',N'" + textCongviec.getText() + "','" + ngaybatdau + "','"
									+ ngayketthuc + "',N'" + textTiendo.getText() + "',N'" + textNoidung.getText()
									+ "',0)";
							int ketquainsert = statement.executeUpdate(insert);
							if (ketquainsert > 0) {
								trangthai = 0;
								btnSearch.setEnabled(true);
								btnAdd.setEnabled(true);
								btnUpdate.setEnabled(true);
								btnDelete.setEnabled(true);
								btnSave.setEnabled(false);
								btnCancel.setEnabled(false);
								btncheckHoanthanh.setEnabled(true);
								textCongviec.setText("");
								textTiendo.setText("");
								textNoidung.setText("");
								btncheckChuahoanthanh.setSelection(true);
								btncheckHoanthanh.setSelection(false);
								dateTimeBatdau.setYear(java.time.LocalDateTime.now().getYear());
								dateTimeBatdau.setMonth(java.time.LocalDateTime.now().getMonthValue() - 1);
								dateTimeBatdau.setDay(java.time.LocalDateTime.now().getDayOfMonth());
								dateTimeKetthuc.setYear(java.time.LocalDateTime.now().getYear());
								dateTimeKetthuc.setMonth(java.time.LocalDateTime.now().getMonthValue() - 1);
								dateTimeKetthuc.setDay(java.time.LocalDateTime.now().getDayOfMonth());

								Statement state = conn.createStatement();
								String insertslideshow = "INSERT INTO SlideShowThongBao(MaCongViec,ThoiGianCapNhat,HoanThanh) VALUES ('"
										+ macongviec + "',GETDATE(),0)";
								state.executeUpdate(insertslideshow);
								state.close();
							}

							String select = "SELECT MaCongViec,TenCongViec,ThoiGianBatDau,ThoiGianKetThuc,TienDo,NoiDung,CASE KetThuc WHEN 0 THEN N'Chưa hoàn thành'ELSE N'Hoàn thành' END AS KetThuc FROM CongViec WHERE MaCongViec='"
									+ macongviec + "' AND KetThuc=0 ORDER BY MaCongViec";

							ResultSet resultselect = statement.executeQuery(select);
							tableleft.removeAll();
							while (resultselect.next()) {
								String batdau = resultselect.getString(3);
								batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
										+ batdau.substring(0, 4);

								String ketthuc = resultselect.getString(4);
								ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
										+ ketthuc.substring(0, 4);

								TableItem item = new TableItem(tableleft, SWT.NONE);
								item.setText(new String[] { resultselect.getString(1), resultselect.getString(2),
										batdau, ketthuc, resultselect.getString(5), resultselect.getString(6),
										resultselect.getString(7) });
							}
							resultselect.close();

							// chỉnh kích thước các cột cho vừa
							tbclCongviec.pack();
							tbclBatdau.pack();
							tbclKetthuc.pack();
							tbclTiendo.pack();
							// tbclnoidung.pack();
							tbclHoanthanh.pack();
						} else {
							// Cong viec khong duoc rong
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
				} else if (trangthai == 2) {
					// Update+++++++++++++++++++++++++++++++++++++++++++++++
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						String ngay = "0" + dateTimeBatdau.getDay();
						ngay = ngay.substring(ngay.length() - 2);
						String thang = "0" + (dateTimeBatdau.getMonth() + 1);
						thang = thang.substring(thang.length() - 2);
						String ngaybatdau = dateTimeBatdau.getYear() + thang + ngay;

						ngay = "0" + dateTimeKetthuc.getDay();
						ngay = ngay.substring(ngay.length() - 2);
						thang = "0" + (dateTimeKetthuc.getMonth() + 1);
						thang = thang.substring(thang.length() - 2);
						String ngayketthuc = dateTimeKetthuc.getYear() + thang + ngay;
						int ketthuchoanthanh = 0;
						if (btncheckChuahoanthanh.getSelection()) {
							ketthuchoanthanh = 0;
						} else {
							ketthuchoanthanh = 1;
						}
						if (!textCongviec.getText().isEmpty()) {
							String update = "UPDATE CongViec SET TenCongViec=N'" + textCongviec.getText()
									+ "' ,ThoiGianBatDau='" + ngaybatdau + "' ,ThoiGianKetThuc='" + ngayketthuc
									+ "' ,TienDo=N'" + textTiendo.getText() + "' ,NoiDung=N'" + textNoidung.getText()
									+ "' ,KetThuc=" + ketthuchoanthanh + " WHERE MaCongViec='" + macongviecupdatedelete
									+ "'";
							int ketquaupdate = statement.executeUpdate(update);
							if (ketquaupdate > 0) {
								trangthai = 0;
								btnSearch.setEnabled(true);
								btnAdd.setEnabled(true);
								btnUpdate.setEnabled(true);
								btnDelete.setEnabled(true);
								btnSave.setEnabled(false);
								btnCancel.setEnabled(false);
								btncheckHoanthanh.setEnabled(true);
								textCongviec.setText("");
								textTiendo.setText("");
								textNoidung.setText("");
								btncheckChuahoanthanh.setSelection(true);
								btncheckHoanthanh.setSelection(false);
								dateTimeBatdau.setYear(java.time.LocalDateTime.now().getYear());
								dateTimeBatdau.setMonth(java.time.LocalDateTime.now().getMonthValue() - 1);
								dateTimeBatdau.setDay(java.time.LocalDateTime.now().getDayOfMonth());
								dateTimeKetthuc.setYear(java.time.LocalDateTime.now().getYear());
								dateTimeKetthuc.setMonth(java.time.LocalDateTime.now().getMonthValue() - 1);
								dateTimeKetthuc.setDay(java.time.LocalDateTime.now().getDayOfMonth());
							}

							String select = "SELECT MaCongViec,TenCongViec,ThoiGianBatDau,ThoiGianKetThuc,TienDo,NoiDung,CASE KetThuc WHEN 0 THEN N'Chưa hoàn thành'ELSE N'Hoàn thành' END AS KetThuc FROM CongViec WHERE MaCongViec='"
									+ macongviecupdatedelete + "' ORDER BY MaCongViec";

							ResultSet resultselect = statement.executeQuery(select);
							tableleft.removeAll();
							while (resultselect.next()) {
								String batdau = resultselect.getString(3);
								batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
										+ batdau.substring(0, 4);

								String ketthuc = resultselect.getString(4);
								ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
										+ ketthuc.substring(0, 4);

								TableItem item = new TableItem(tableleft, SWT.NONE);
								item.setText(new String[] { resultselect.getString(1), resultselect.getString(2),
										batdau, ketthuc, resultselect.getString(5), resultselect.getString(6),
										resultselect.getString(7) });
							}
							resultselect.close();

							// chỉnh kích thước các cột cho vừa
							tbclCongviec.pack();
							tbclBatdau.pack();
							tbclKetthuc.pack();
							tbclTiendo.pack();
							// tbclnoidung.pack();
							tbclHoanthanh.pack();
						} else {
							// Cong viec khong duoc rong
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
				} else if (trangthai == 3) {
					// Delete++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						if (!textCongviec.getText().isEmpty()) {
							String delete = "DELETE CongViec WHERE MaCongViec='" + macongviecupdatedelete + "'";
							int ketquadelete = statement.executeUpdate(delete);
							if (ketquadelete > 0) {
								trangthai = 0;
								btnSearch.setEnabled(true);
								btnAdd.setEnabled(true);
								btnUpdate.setEnabled(true);
								btnSave.setEnabled(false);
								btnCancel.setEnabled(false);
								btncheckHoanthanh.setEnabled(true);
								textCongviec.setText("");
								textTiendo.setText("");
								textNoidung.setText("");
								btncheckChuahoanthanh.setSelection(true);
								btncheckHoanthanh.setSelection(false);

								dateTimeBatdau.setYear(java.time.LocalDateTime.now().getYear());
								dateTimeBatdau.setMonth(java.time.LocalDateTime.now().getMonthValue() - 1);
								dateTimeBatdau.setDay(java.time.LocalDateTime.now().getDayOfMonth());
								dateTimeKetthuc.setYear(java.time.LocalDateTime.now().getYear());
								dateTimeKetthuc.setMonth(java.time.LocalDateTime.now().getMonthValue() - 1);
								dateTimeKetthuc.setDay(java.time.LocalDateTime.now().getDayOfMonth());

								textCongviec.setEditable(true);
								textGhichu.setEditable(true);
								textNoidung.setEditable(true);
								textTiendo.setEditable(true);
								dateTimeBatdau.setEnabled(true);
								dateTimeKetthuc.setEnabled(true);
								btncheckChuahoanthanh.setEnabled(true);
								btncheckHoanthanh.setEnabled(true);

								tableleft.remove(vitrixoa);

								String xoaslideshow = "DELETE FROM SlideShowThongBao WHERE MaCongViec='"
										+ macongviecupdatedelete + "'";
								Statement state = conn.createStatement();
								state.executeUpdate(xoaslideshow);
								state.close();
							}

							// chỉnh kích thước các cột cho vừa
							tbclCongviec.pack();
							tbclBatdau.pack();
							tbclKetthuc.pack();
							tbclTiendo.pack();
							// tbclnoidung.pack();
							tbclHoanthanh.pack();
						} else {
							// Cong viec khong duoc rong
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
				}
			}
		});

		// Cancel
		// *******************************************************************************************************************
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				trangthai = 0;
				btnSearch.setEnabled(true);
				btnAdd.setEnabled(true);
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
				btnSave.setEnabled(false);
				btnCancel.setEnabled(false);
				btncheckHoanthanh.setEnabled(true);
				textCongviec.setText("");
				textTiendo.setText("");
				textNoidung.setText("");
				btncheckChuahoanthanh.setSelection(true);
				btncheckHoanthanh.setSelection(false);
				dateTimeBatdau.setYear(java.time.LocalDateTime.now().getYear());
				dateTimeBatdau.setMonth(java.time.LocalDateTime.now().getMonthValue() - 1);
				dateTimeBatdau.setDay(java.time.LocalDateTime.now().getDayOfMonth());
				dateTimeKetthuc.setYear(java.time.LocalDateTime.now().getYear());
				dateTimeKetthuc.setMonth(java.time.LocalDateTime.now().getMonthValue() - 1);
				dateTimeKetthuc.setDay(java.time.LocalDateTime.now().getDayOfMonth());

				textCongviec.setEditable(true);
				textGhichu.setEditable(true);
				textNoidung.setEditable(true);
				textTiendo.setEditable(true);
				dateTimeBatdau.setEnabled(true);
				dateTimeKetthuc.setEnabled(true);
				btncheckChuahoanthanh.setEnabled(true);
				btncheckHoanthanh.setEnabled(true);
			}
		});

		// Xử lý phân công công việc
		// ===========================================================================================================
		// Lấy dữ liệu cho combo người được phân công
		// -----------------------------------------------------------------------------------------------------
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			// Lấy dữ liệu cho combo người được phân công
			String select = "SELECT TenNguoiDung FROM NguoiDung WHERE MaNhomNguoiDung='it' ORDER BY TenNguoiDung";
			ResultSet resultcombo = statement.executeQuery(select);
			while (resultcombo.next()) {
				comboNguoiduocphancong.add(resultcombo.getString(1));
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

		// Lấy dữ liệu cho table right lúc đầu, chọn 1 dòng của table left thì sẽ hiển
		// thị danh sách được phân công của công việc đó
		// -------------------------------------------------------------------------------------------------------------------------
		tableleft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					TableItem[] itemmacongviec = tableleft.getSelection();
					macongviecright = itemmacongviec[0].getText();

					String select = "SELECT CongViec.TenCongViec,NguoiDung.TenNguoiDung AS NguoiPhanCong,ND.TenNguoiDung AS NguoiDuocPhanCong,ThoiGianPhanCong,PhanCongCongViec.GhiChu FROM PhanCongCongViec,CongViec,NguoiDung,NguoiDung AS ND WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.NguoiDuocPhanCong=ND.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
							+ macongviecright + "'";

					ResultSet resultselect = statement.executeQuery(select);
					tableright.removeAll();
					while (resultselect.next()) {
						String thoigiancapnhat = resultselect.getString(4);
						thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/" + thoigiancapnhat.substring(5, 7) + "/"
								+ thoigiancapnhat.substring(0, 4) + " " + thoigiancapnhat.substring(11, 19);

						TableItem item = new TableItem(tableright, SWT.NONE);
						item.setText(new String[] { resultselect.getString(1), resultselect.getString(2),
								resultselect.getString(3), thoigiancapnhat, resultselect.getString(5) });
					}
					resultselect.close();

					// chỉnh kích thước các cột cho vừa
					tbclrightCongviec.pack();
					tbclrightNguoiphancong.pack();
					tbclrightNguoiduocphancong.pack();
					tbclrightThoigianphancong.pack();
					tbclrightGhichu.pack();

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
			}
		});

		// Add Phân công công việc
		// -----------------------------------------------------------------------------------
		btnrightAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!comboNguoiduocphancong.getText().isEmpty()) {
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						String nguoiduocphancong = "";
						// Lấy số thẻ người được phân công
						String selectsothe = "SELECT MaNguoiDung FROM NguoiDung WHERE MaNhomNguoiDung='it' AND TenNguoiDung=N'"
								+ comboNguoiduocphancong.getText() + "'";
						ResultSet result = statement.executeQuery(selectsothe);
						while (result.next()) {
							nguoiduocphancong = result.getString(1);
						}
						result.close();

						// Xử lý trường hợp đã phân công người dùng rồi, không được phân công nữa
						String sothedaphancong = "";
						String selectdaphancong = "SELECT TOP 1 NguoiDuocPhanCong FROM PhanCongCongViec WHERE MaCongViec='"
								+ macongviecright + "' AND NguoiDuocPhanCong='" + nguoiduocphancong + "'";
						ResultSet ketquadaphancong = statement.executeQuery(selectdaphancong);
						while (ketquadaphancong.next()) {
							sothedaphancong = ketquadaphancong.getString(1);
						}
						ketquadaphancong.close();

						if (nguoiduocphancong.compareToIgnoreCase(sothedaphancong) != 0) {
							String insertpccv = "INSERT INTO PhanCongCongViec(MaCongViec,NguoiPhanCong,NguoiDuocPhanCong,ThoiGianPhanCong,GhiChu) VALUES  ('"
									+ macongviecright + "','" + manguoidung + "','" + nguoiduocphancong
									+ "',GETDATE(),N'" + textGhichu.getText() + "')";
							if (statement.executeUpdate(insertpccv) > 0) {
								String select = "SELECT CongViec.TenCongViec,NguoiDung.TenNguoiDung AS NguoiPhanCong,ND.TenNguoiDung AS NguoiDuocPhanCong,ThoiGianPhanCong,PhanCongCongViec.GhiChu FROM PhanCongCongViec,CongViec,NguoiDung,NguoiDung AS ND WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.NguoiDuocPhanCong=ND.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
										+ macongviecright + "'";

								ResultSet resultselect = statement.executeQuery(select);
								tableright.removeAll();
								while (resultselect.next()) {
									String thoigiancapnhat = resultselect.getString(4);
									thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/"
											+ thoigiancapnhat.substring(5, 7) + "/" + thoigiancapnhat.substring(0, 4)
											+ " " + thoigiancapnhat.substring(11, 19);

									TableItem item = new TableItem(tableright, SWT.NONE);
									item.setText(new String[] { resultselect.getString(1), resultselect.getString(2),
											resultselect.getString(3), thoigiancapnhat, resultselect.getString(5) });
								}
								resultselect.close();

								// chỉnh kích thước các cột cho vừa
								tbclrightCongviec.pack();
								tbclrightNguoiphancong.pack();
								tbclrightNguoiduocphancong.pack();
								tbclrightThoigianphancong.pack();
								tbclrightGhichu.pack();

								// Thêm vào bảng thông báo
								String insertthongbao = "INSERT INTO ThongBao(MaCongViec,NguoiDuocThongBao,DaXem,DaXemUser,ThoiGianThongBao) VALUES('"
										+ macongviecright + "','" + nguoiduocphancong + "',0,0,GETDATE())";
								Statement statethongbao = conn.createStatement();
								statethongbao.executeUpdate(insertthongbao);
								statethongbao.close();
							}
						} else {
							MessageBox message = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
							if (ngonngu == 0) {
								message.setText("Thông báo");
								message.setMessage("Nguời này đã được phân công rồi!");
							} else if (ngonngu == 1) {
								message.setText("Notification");
								message.setMessage("This person has already been assigned!");
							} else if (ngonngu == 2) {
								message.setText("通知");
								message.setMessage("此人已被分配!");
							}
							message.open();
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
				}
			}
		});

		// Delete Phân công công việc
		// -----------------------------------------------------------------------------------
		btnrightDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String nguoiduocphancongxoa = "";
					TableItem[] itemmacongviec = tableright.getSelection();
					nguoiduocphancongxoa = itemmacongviec[0].getText(2);
					// Lấy số thẻ người được phân công
					String selectsothe = "SELECT MaNguoiDung FROM NguoiDung WHERE MaNhomNguoiDung='it' AND TenNguoiDung=N'"
							+ nguoiduocphancongxoa + "'";
					ResultSet result = statement.executeQuery(selectsothe);
					while (result.next()) {
						nguoiduocphancongxoa = result.getString(1);
					}
					result.close();

					String select = "DELETE PhanCongCongViec WHERE MaCongViec='" + macongviecright
							+ "' AND NguoiDuocPhanCong='" + nguoiduocphancongxoa + "'";
					if (statement.executeUpdate(select) > 0) {
						tableright.remove(tableright.getSelectionIndex());
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
			}
		});

		// Cancel Phân công công việc
		// -----------------------------------------------------------------------------------
		btnrightCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comboNguoiduocphancong.setText("");
				textGhichu.setText("");
			}
		});
	}
}
