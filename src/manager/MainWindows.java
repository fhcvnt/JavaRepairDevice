package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class MainWindows {

	protected Shell shell;
	// language=0: Tieng Viet, language=1: Tieng Anh, languge=2: Tieng Hoa
	private int language = 0;
	private String mannhomguoidung = "";
	private MenuItem mntmWork;
	private String userID = "";
	// Kích thước màn hình
	private int sizemonitorx = Display.getDefault().getBounds().width;
	// private String db_url =
	// "jdbc:sqlserver://192.168.30.123;databaseName=SuaChuaThietBi;user=sa;password=Killua21608";
	private String db_url = "";
	private int thoigiantimer = 10000; // 10 giây kiểm tra cơ sở dữ liệu một lần xem có thông báo mới không
	private String version = "";

	public static void main(String[] args) {
		try {
			MainWindows window = new MainWindows();
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
		createContents(userID, mannhomguoidung, language, db_url,version);
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
	protected void createContents(String userID, String mannhomguoidung, int ngonngu, String chuoiketnoi,String phienban) {
		db_url = chuoiketnoi;
		version=phienban;
		this.userID = userID;
		this.mannhomguoidung = mannhomguoidung;
		language = ngonngu;

		shell = new Shell();
		shell.setSize(809, 431);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shell.setMaximized(true);
		shell.setText("Repair Manager - "+version);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setImage(SWTResourceManager.getImage(MainWindows.class, "/manager/Images/repair256.ico"));

		Menu menubar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menubar);

		MenuItem mntmFile = new MenuItem(menubar, SWT.CASCADE);
		mntmFile.setText("&File");

		Menu menu_file = new Menu(mntmFile);
		mntmFile.setMenu(menu_file);

		MenuItem mntmSuggestionform = new MenuItem(menu_file, SWT.NONE);
		mntmSuggestionform.setText("Suggestion Form");
		mntmSuggestionform.setImage(SWTResourceManager.getImage(MainWindows.class, "/manager/menu/Suggestionform.png"));
		mntmSuggestionform.setAccelerator(262225);

		MenuItem mntmUpdate = new MenuItem(menu_file, SWT.NONE);
		mntmUpdate.setText("Update");
		mntmUpdate.setImage(SWTResourceManager.getImage(MainWindows.class, "/manager/menu/update.png"));
		mntmUpdate.setAccelerator(262225);

		MenuItem mntmExit = new MenuItem(menu_file, SWT.NONE);
		mntmExit.setImage(SWTResourceManager.getImage(MainWindows.class, "/manager/menu/exit.png"));

		// Đóng ứng dụng Exit Window
		// -----------------------------------------------------------------------------
		mntmExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// System.exit(0);
				// shell.close();
				// Display.getDefault().dispose();
				shell.dispose();
			}
		});
		mntmExit.setText("Exit\tCtrl + Q");
		mntmExit.setAccelerator(SWT.MOD1 + 'Q');

		MenuItem mntmUser = new MenuItem(menubar, SWT.CASCADE);
		mntmUser.setText("&User");

		Menu menu_user = new Menu(mntmUser);
		mntmUser.setMenu(menu_user);

		MenuItem mntmUserlist = new MenuItem(menu_user, SWT.NONE);
		mntmUserlist.setImage(SWTResourceManager.getImage(MainWindows.class, "/manager/menu/user list.png"));
		mntmUserlist.setText("User List\tCtrl + N");
		mntmUserlist.setAccelerator(SWT.MOD1 + 'N');

		MenuItem mntmOnDutyList = new MenuItem(menu_user, SWT.NONE);
		mntmOnDutyList.setText("On Duty Room List");
		mntmOnDutyList.setImage(SWTResourceManager.getImage(MainWindows.class, "/manager/menu/DStrucphong.png"));
		mntmOnDutyList.setAccelerator(262222);

		MenuItem mntmDepartment = new MenuItem(menubar, SWT.CASCADE);
		mntmDepartment.setText("&Department");

		Menu menu_department = new Menu(mntmDepartment);
		mntmDepartment.setMenu(menu_department);

		MenuItem mntmDepartmentList = new MenuItem(menu_department, SWT.NONE);
		mntmDepartmentList.setImage(SWTResourceManager.getImage(MainWindows.class, "/manager/menu/dept.png"));
		mntmDepartmentList.setText("Department List");

		mntmWork = new MenuItem(menubar, SWT.CASCADE);
		mntmWork.setText("&Work");

		Menu menu_work = new Menu(mntmWork);
		mntmWork.setMenu(menu_work);

		MenuItem mntmOndutyroom = new MenuItem(menu_work, SWT.NONE);
		mntmOndutyroom.setImage(SWTResourceManager.getImage(MainWindows.class, "/manager/menu/Poison.png"));
		mntmOndutyroom.setText("On Duty Room\tCtrl + B");
		mntmOndutyroom.setAccelerator(SWT.MOD1 + 'B');

		MenuItem mntmWorklist = new MenuItem(menu_work, SWT.NONE);
		mntmWorklist.setText("Work List");
		mntmWorklist.setImage(SWTResourceManager.getImage(MainWindows.class, "/manager/menu/worklist.png"));
		mntmWorklist.setAccelerator(262210);

		MenuItem mntmRepairdevice = new MenuItem(menubar, SWT.CASCADE);
		mntmRepairdevice.setText("&Repair Device");

		Menu menu_repairdevice = new Menu(mntmRepairdevice);
		mntmRepairdevice.setMenu(menu_repairdevice);

		MenuItem mntmRepairdevicelist = new MenuItem(menu_repairdevice, SWT.NONE);
		mntmRepairdevicelist
				.setImage(SWTResourceManager.getImage(MainWindows.class, "/manager/menu/repairdevicelist.png"));

		mntmRepairdevicelist.setText("Repair Devices List\tCtrl + M");
		mntmRepairdevicelist.setAccelerator(SWT.MOD1 + 'M');

		MenuItem mntmReport = new MenuItem(menubar, SWT.CASCADE);
		mntmReport.setText("&Report");

		Menu menu_report = new Menu(mntmReport);
		mntmReport.setMenu(menu_report);

		MenuItem mntmAll = new MenuItem(menu_report, SWT.NONE);
		mntmAll.setText("All");
		mntmAll.setImage(SWTResourceManager.getImage(MainWindows.class, "/manager/menu/reportall.png"));
		mntmAll.setAccelerator(65605);

		MenuItem mntmLanguage = new MenuItem(menubar, SWT.CASCADE);
		mntmLanguage.setText("&Language");

		Menu menu_language = new Menu(mntmLanguage);
		mntmLanguage.setMenu(menu_language);

		MenuItem mntmEnglish = new MenuItem(menu_language, SWT.NONE);
		mntmEnglish.setImage(SWTResourceManager.getImage(MainWindows.class, "/manager/menu/englisht.png"));
		mntmEnglish.setText("English");

		MenuItem mntmVietnamese = new MenuItem(menu_language, SWT.NONE);
		mntmVietnamese.setImage(SWTResourceManager.getImage(MainWindows.class, "/manager/menu/Vietnam.png"));
		mntmVietnamese.setText("Việt Nam");

		MenuItem mntmHoa = new MenuItem(menu_language, SWT.NONE);
		mntmHoa.setText("中文");
		mntmHoa.setImage(SWTResourceManager.getImage(MainWindows.class, "/manager/menu/China.png"));
		mntmHoa.setAccelerator(65622);

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		CTabFolder tabFolder = new CTabFolder(composite, SWT.TOP);
		tabFolder.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		tabFolder.setSelectionBackground(
				Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		// Chỉ cho Admin chỉnh cập nhật
		// ************************************************************************************************************************************
		if(mannhomguoidung.compareToIgnoreCase("admin")!=0) {
			//mntmUpdate.setEnabled(false);
		}

		// Thay đổi ngôn ngữ
		// -------------------------------------------------------------------------
		if (language == 0) {
			// Tiếng Việt
			shell.setText("Quản lý sửa chữa - "+version);
			mntmFile.setText("&Tệp");
			mntmSuggestionform.setText("Phiếu Đề Nghị");
			mntmUpdate.setText("Cập Nhật");
			mntmExit.setText("Thoát\tCtrl + Q");
			mntmUser.setText("&Người Dùng");
			mntmUserlist.setText("Danh Sách Người Dùng\tCtrl + N");
			mntmOnDutyList.setText("Danh Sách Trực Phòng");
			mntmDepartment.setText("&Đơn Vị");
			mntmDepartmentList.setText("Danh Sách Đơn Vị");
			mntmWork.setText("&Công Việc");
			mntmOndutyroom.setText("Trực Phòng\tCtrl + B");
			mntmWorklist.setText("Danh Sách Công Việc");
			mntmRepairdevice.setText("&Sửa Chữa Thiết Bị");
			mntmRepairdevicelist.setText("Danh Sách Sửa Chữa Thiết Bị\tCtrl + M");
			mntmReport.setText("&Báo Cáo");
			mntmAll.setText("Tất Cả");
			mntmLanguage.setText("&Ngôn Ngữ");
		} else if (language == 1) {
			// Tiếng Anh
			shell.setText("Repair Manager - "+ version);
			mntmFile.setText("&File");
			mntmSuggestionform.setText("Suggestion Form");
			mntmUpdate.setText("Update");
			mntmExit.setText("Exit\tCtrl + Q");
			mntmUser.setText("&User");
			mntmUserlist.setText("User List\tCtrl + N");
			mntmOnDutyList.setText("On Duty Room List");
			mntmDepartment.setText("&Department");
			mntmDepartmentList.setText("Department List");
			mntmWork.setText("&Work");
			mntmOndutyroom.setText("On Duty Room\tCtrl + B");
			mntmWorklist.setText("Work List");
			mntmRepairdevice.setText("&Repair Device");
			mntmRepairdevicelist.setText("Repair Devices List\tCtrl + M");
			mntmReport.setText("&Report");
			mntmAll.setText("All");
			mntmLanguage.setText("&Language");
		} else if (language == 2) {
			// Tiếng Hoa
			shell.setText("維修經理 - "+version);
			mntmFile.setText("&文件");
			mntmSuggestionform.setText("建議表格");
			mntmUpdate.setText("更新");
			mntmExit.setText("離開\tCtrl + Q");
			mntmUser.setText("&使用者");
			mntmUserlist.setText("用戶清單\tCtrl + N");
			mntmOnDutyList.setText("值班室清單");
			mntmDepartment.setText("&部門");
			mntmDepartmentList.setText("部門清單");
			mntmWork.setText("&工作");
			mntmOndutyroom.setText("值班室\tCtrl + B");
			mntmWorklist.setText("工作清單");
			mntmRepairdevice.setText("&維修設備");
			mntmRepairdevicelist.setText("維修設備清單\tCtrl + M");
			mntmReport.setText("&報告");
			mntmAll.setText("全部");
			mntmLanguage.setText("&語言");
		}

		// Mở cửa sổ Phiếu đề nghị
		// -----------------------------------------------------------------
		mntmSuggestionform.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (CTabItem itemrun : tabFolder.getItems()) {
					if (language == 0) {
						if (itemrun.getText().equals("Phiếu đề nghị")) {
							return;
						}
					} else if (language == 1) {
						if (itemrun.getText().equals("Suggestion form")) {
							return;
						}
					} else {
						if (itemrun.getText().equals("建議表格")) {
							return;
						}
					}
				}
				try {
					PhieuDeNghi phieudenghi = new PhieuDeNghi();
					phieudenghi.createContentsTabFolder(tabFolder, shell, language, db_url);
					tabFolder.setSelection(tabFolder.getItemCount() - 1);

				} catch (Exception ex) {
				}
			}
		});

		// Cập nhật
		// ---------------------------------------------------------------------------------------------------------------------------------------
		mntmUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (CTabItem itemrun : tabFolder.getItems()) {
					if (language == 0) {
						if (itemrun.getText().equals("Cập nhật")) {
							return;
						}
					} else if (language == 1) {
						if (itemrun.getText().equals("Update")) {
							return;
						}
					} else {
						if (itemrun.getText().equals("更新")) {
							return;
						}
					}
				}
				try {
					CapNhatPhanMem capnhat = new CapNhatPhanMem();
					capnhat.createContentsTabfolder(tabFolder, shell, language, db_url);
					tabFolder.setSelection(tabFolder.getItemCount() - 1);
				} catch (Exception ex) {
				}
			}
		});

		// Mở Danh sách người dùng
		// ----------------------------------------------------------------------------------------------
		mntmUserlist.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (CTabItem itemrun : tabFolder.getItems()) {
					if (language == 0) {
						if (itemrun.getText().equals("Danh sách người dùng")) {
							return;
						}
					} else if (language == 1) {
						if (itemrun.getText().equals("User list")) {
							return;
						}
					} else {
						if (itemrun.getText().equals("用戶清單")) {
							return;
						}
					}
				}
				try {
					DanhSachNguoiDung nguoidung = new DanhSachNguoiDung();
					nguoidung.createContentsTabfolder(tabFolder, shell, mannhomguoidung, language, db_url);
					tabFolder.setSelection(tabFolder.getItemCount() - 1);
				} catch (Exception ex) {
				}
			}
		});

		// Mở cửa sổ Danh sách Người trực phòng
		// ---------------------------------------------------------------------------------------------------------------------------
		mntmOnDutyList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (CTabItem itemrun : tabFolder.getItems()) {
					if (language == 0) {
						if (itemrun.getText().equals("Danh sách người trực phòng")) {
							return;
						}
					} else if (language == 1) {
						if (itemrun.getText().equals("List of people on duty")) {
							return;
						}
					} else {
						if (itemrun.getText().equals("值班人員名單")) {
							return;
						}
					}
				}
				try {
					DanhSachTrucPhong dstrucphong = new DanhSachTrucPhong();
					dstrucphong.createContentsTabfolder(tabFolder, shell, language, db_url);
					tabFolder.setSelection(tabFolder.getItemCount() - 1);

				} catch (Exception ex) {
				}
			}
		});

		// Mở cửa sổ Đơn vị
		// ==================================================================================
		mntmDepartmentList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (CTabItem itemrun : tabFolder.getItems()) {
					if (language == 0) {
						if (itemrun.getText().equals("Đơn vị")) {
							return;
						}
					} else if (language == 1) {
						if (itemrun.getText().equals("Department")) {
							return;
						}
					} else {
						if (itemrun.getText().equals("部門")) {
							return;
						}
					}
				}
				try {
					DonVi department = new DonVi();
					department.createContentsTabfolder(tabFolder, shell, language, db_url);
					tabFolder.setSelection(tabFolder.getItemCount() - 1);

				} catch (Exception ex) {
				}
			}
		});

		// Mở cửa sổ Trực phòng
		// ----------------------------------------------------------------------------------
		mntmOndutyroom.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (CTabItem itemrun : tabFolder.getItems()) {
					if (language == 0) {
						if (itemrun.getText().equals("Lịch trực phòng")) {
							return;
						}
					} else if (language == 1) {
						if (itemrun.getText().equals("Room calendar")) {
							return;
						}
					} else {
						if (itemrun.getText().equals("房間日曆")) {
							return;
						}
					}
				}
				try {
					LichTrucPhong lichtruc = new LichTrucPhong();
					lichtruc.createContentsTabfolder(tabFolder, shell, userID, language, db_url);
					tabFolder.setSelection(tabFolder.getItemCount() - 1);

				} catch (Exception ex) {
				}
			}
		});

		// Mở cửa sổ Danh sách công việc
		// -----------------------------------------------------------------------------------------------------------------------------------
		mntmWorklist.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (CTabItem itemrun : tabFolder.getItems()) {
					if (language == 0) {
						if (itemrun.getText().equals("Danh sách công việc")) {
							return;
						}
					} else if (language == 1) {
						if (itemrun.getText().equals("Work list")) {
							return;
						}
					} else {
						if (itemrun.getText().equals("工作清單")) {
							return;
						}
					}
				}
				try {
					DanhSachCongViec dscongviec = new DanhSachCongViec();
					dscongviec.createContentsTabfolder(tabFolder, shell, userID, mannhomguoidung, language, db_url);
					tabFolder.setSelection(tabFolder.getItemCount() - 1);

				} catch (Exception ex) {
				}
			}
		});

		// Mở cửa sổ Danh sách sửa chữa thiết bị
		// ------------------------------------------------------------------
		mntmRepairdevicelist.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (CTabItem itemrun : tabFolder.getItems()) {
					if (language == 0) {
						if (itemrun.getText().equals("Báo sửa thiết bị")) {
							return;
						}
					} else if (language == 1) {
						if (itemrun.getText().equals("Equipment repair report")) {
							return;
						}
					} else {
						if (itemrun.getText().equals("設備維修報告")) {
							return;
						}
					}
				}
				try {
					DanhSachSuaChuaThietBi suachua = new DanhSachSuaChuaThietBi();
					suachua.createContentsTabfolder(tabFolder, shell, userID, mannhomguoidung, language, db_url);
					tabFolder.setSelection(tabFolder.getItemCount() - 1);

				} catch (Exception ex) {
				}
			}
		});

		// Mở cửa sổ Slide Show
		// -------------------------------------------------------------------------------------------------
		mntmAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (sizemonitorx <= 1366) {
					SlideShowSuaChuaCongViec1366x768 slideshow = new SlideShowSuaChuaCongViec1366x768();
					slideshow.createContents(language, db_url);
					shell.setEnabled(false);
					slideshow.open();
					shell.setEnabled(true);
				} else {
					SlideShowSuaChuaCongViec1920x1080 slideshow = new SlideShowSuaChuaCongViec1920x1080();
					slideshow.createContents(language, db_url);
					shell.setEnabled(false);
					slideshow.open();
					shell.setEnabled(true);
				}
			}
		});

		// Thay đổi ngôn ngữ thành Tiếng Anh
		// -------------------------------------------------------------------------------------------------
		mntmEnglish.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.setText("Repair Manager");
				mntmFile.setText("&File");
				mntmSuggestionform.setText("Suggestion Form");
				mntmUpdate.setText("Update");
				mntmExit.setText("Exit\tCtrl + Q");
				mntmUser.setText("&User");
				mntmUserlist.setText("User List\tCtrl + N");
				mntmOnDutyList.setText("On Duty Room List");
				mntmDepartment.setText("&Department");
				mntmDepartmentList.setText("Department List");
				mntmWork.setText("&Work");
				mntmOndutyroom.setText("On Duty Room\tCtrl + B");
				mntmWorklist.setText("Work List");
				mntmRepairdevice.setText("&Repair Device");
				mntmRepairdevicelist.setText("Repair Devices List\tCtrl + M");
				mntmReport.setText("&Report");
				mntmAll.setText("All");
				mntmLanguage.setText("&Language");
				// mntmEnglish.setText("English\tAlt + E");
				// mntmVietnamese.setText("Việt Nam\tAlt + V");
				// mntmHoa.setText("中文\tAlt + C");

				if (language != 1) {
					for (CTabItem itemrun : tabFolder.getItems()) {
						itemrun.dispose();
					}
				}

				language = 1;
				Display.getDefault().update();
			}
		});

		// Thay đổi ngôn ngữ thành Tiếng Việt
		// -------------------------------------------------------------------------------------------------
		mntmVietnamese.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.setText("Quản lý sửa chữa");
				mntmFile.setText("&Tệp");
				mntmSuggestionform.setText("Phiếu Đề Nghị");
				mntmUpdate.setText("Cập Nhật");
				mntmExit.setText("Thoát\tCtrl + Q");
				mntmUser.setText("&Người Dùng");
				mntmUserlist.setText("Danh Sách Người Dùng\tCtrl + N");
				mntmOnDutyList.setText("Danh Sách Trực Phòng");
				mntmDepartment.setText("&Đơn Vị");
				mntmDepartmentList.setText("Danh Sách Đơn Vị");
				mntmWork.setText("&Công Việc");
				mntmOndutyroom.setText("Trực Phòng\tCtrl + B");
				mntmWorklist.setText("Danh Sách Công Việc");
				mntmRepairdevice.setText("&Sửa Chữa Thiết Bị");
				mntmRepairdevicelist.setText("Danh Sách Sửa Chữa Thiết Bị\tCtrl + M");
				mntmReport.setText("&Báo Cáo");
				mntmAll.setText("Tất Cả");
				mntmLanguage.setText("&Ngôn Ngữ");
				// mntmEnglish.setText("English\tAlt + E");
				// mntmVietnamese.setText("Việt Nam\tAlt + V");
				// mntmHoa.setText("中文\tAlt + C");

				if (language != 0) {
					for (CTabItem itemrun : tabFolder.getItems()) {
						itemrun.dispose();
					}
				}

				language = 0;
				Display.getDefault().update();
			}
		});

		// Thay đổi ngôn ngữ thành Tiếng Hoa
		// -------------------------------------------------------------------------------------------------
		mntmHoa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.setText("維修經理");
				mntmFile.setText("&文件");
				mntmSuggestionform.setText("建議表格");
				mntmUpdate.setText("更新");
				mntmExit.setText("離開\tCtrl + Q");
				mntmUser.setText("&使用者");
				mntmUserlist.setText("用戶清單\tCtrl + N");
				mntmOnDutyList.setText("值班室清單");
				mntmDepartment.setText("&部門");
				mntmDepartmentList.setText("部門清單");
				mntmWork.setText("&工作");
				mntmOndutyroom.setText("值班室\tCtrl + B");
				mntmWorklist.setText("工作清單");
				mntmRepairdevice.setText("&維修設備");
				mntmRepairdevicelist.setText("維修設備清單\tCtrl + M");
				mntmReport.setText("&報告");
				mntmAll.setText("全部");
				mntmLanguage.setText("&語言");
				// mntmEnglish.setText("English\tAlt + E");
				// mntmVietnamese.setText("Việt Nam\tAlt + V");
				// mntmHoa.setText("中文\tAlt + C");

				if (language != 2) {
					for (CTabItem itemrun : tabFolder.getItems()) {
						itemrun.dispose();
					}
				}

				language = 2;
				Display.getDefault().update();
			}
		});

		// Kiểm tra xem có thông báo cho mình không
		// ******************************************************************************************************************************************
		Runnable timer = new Runnable() {

			@Override
			public void run() {
				// Chỉ thông báo cho IT, không thông báo cho Admin
				if (mannhomguoidung.compareToIgnoreCase("admin") != 0) {
					try {
						Connection connect = DriverManager.getConnection(db_url);
						Statement statethongbao = connect.createStatement();

						String select = "SELECT ThongBao.PhanBiet,NguoiDung.TenNguoiDung AS NguoiBao,ThietBi.TenThietBi,DonVi.TenDonVi FROM ThongBao,NguoiDung,BaoSuaThietBi,ThietBi,DonVi  WHERE BaoSuaThietBi.MaNguoiDung=NguoiDung.MaNguoiDung AND ThongBao.PhanBiet=BaoSuaThietBi.PhanBiet AND ThongBao.PhanBiet IS NOT NULL AND ThongBao.PhanBiet!=0 AND DaXem=0 AND CONVERT(DATE,ThongBao.ThoiGianThongBao)=CONVERT(DATE,GETDATE()) AND ThongBao.NguoiDuocThongBao='"
								+ userID
								+ "'  AND BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi AND NguoiDung.MaDonVi=DonVi.MaDonVi ORDER BY ThongBao.ThoiGianThongBao DESC";
						ResultSet result = statethongbao.executeQuery(select);
						int dem = 0;
						while (result.next()) {
							MessageBox message = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							shell.setEnabled(false);
							if (ngonngu == 0) {
								message.setText("Thông báo");
								message.setMessage("Bạn đã được phân công đi sửa thiết bị: " + result.getString(3)
										+ " của người dùng " + result.getString(2) + " (" + result.getString(4) + ")");
							} else if (ngonngu == 1) {
								message.setText("Notification");
								message.setMessage("You have been assigned to repair equipment: " + result.getString(3)
										+ " by user " + result.getString(2) + " (" + result.getString(4) + ")");
							} else if (ngonngu == 2) {
								message.setText("通知");
								message.setMessage("您已被分配維修設備: " + result.getString(3) + " 用戶 " + result.getString(2)
										+ " (" + result.getString(4) + ")");
							}
							dem++;
							// Xóa thông báo cũ sau khi xem xong
							try {
								Statement statethongbaocapnhat = connect.createStatement();
								String capnhatthongbao = "UPDATE ThongBao SET DaXem=1 WHERE PhanBiet="
										+ result.getString(1);
								statethongbaocapnhat.executeUpdate(capnhatthongbao);
								statethongbaocapnhat.close();
							} catch (SQLException sqle) {

							}
							if (dem == 1) {
								message.open();
							}
							shell.setEnabled(true);
						}
						result.close();
						statethongbao.close();

						// Cong viec
						String selectcongviec = "SELECT ThongBao.MaCongViec,CongViec.TenCongViec,CongViec.NoiDung,NguoiDuocThongBao FROM ThongBao,CongViec WHERE ThongBao.MaCongViec=CongViec.MaCongViec AND ThongBao.MaCongViec IS NOT NULL AND ThongBao.MaCongViec!='' AND DaXem=0 AND CONVERT(DATE,ThongBao.ThoiGianThongBao)=CONVERT(DATE,GETDATE()) AND ThongBao.NguoiDuocThongBao='"
								+ userID + "' ORDER BY ThongBao.ThoiGianThongBao DESC";
						Statement statecongviec = connect.createStatement();
						ResultSet resultcongviec = statecongviec.executeQuery(selectcongviec);
						while (resultcongviec.next()) {
							MessageBox message = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							shell.setEnabled(false);
							if (ngonngu == 0) {
								message.setText("Thông báo");
								message.setMessage("Bạn đã được phân công: " + resultcongviec.getString(3) + " - "
										+ resultcongviec.getString(3));
							} else if (ngonngu == 1) {
								message.setText("Notification");
								message.setMessage("You have been assigned: " + resultcongviec.getString(3) + " - "
										+ resultcongviec.getString(3));
							} else if (ngonngu == 2) {
								message.setText("通知");
								message.setMessage(
										"您已分配: " + resultcongviec.getString(3) + " - " + resultcongviec.getString(3));
							}
							// Xóa thông báo cũ sau khi xem xong
							try {
								Statement statethongbaocapnhat = connect.createStatement();
								String capnhatthongbao = "UPDATE ThongBao SET DaXem=1 WHERE NguoiDuocThongBao='"
										+ userID + "' AND MaCongViec='" + resultcongviec.getString(1) + "'";
								statethongbaocapnhat.executeUpdate(capnhatthongbao);
								statethongbaocapnhat.close();
							} catch (SQLException sqle) {

							}

							message.open();
							shell.setEnabled(true);
						}
						resultcongviec.close();
						statecongviec.close();
						connect.close();
					} catch (Exception se) {

					}

					Display.getDefault().timerExec(thoigiantimer, this);
				}
			}
		};
		Display.getDefault().timerExec(thoigiantimer, timer);

	}

	// ***********************************************************************************************************
	// Resize hinh anh
	public Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, width, height);
		gc.dispose();
		image.dispose(); // don't forget about me!
		return scaled;
	}
}
