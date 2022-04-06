package repairdevice;

import org.eclipse.swt.widgets.Display;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;

public class BaoSuaThietBi {

	// Chuỗi kết nối
	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;
	protected Shell shellBaosuathietbi;
	private Text textNoidung;
	private int ngonngu = 0; // 0: Vietnam, 1: English, 2: China
	private String manguoidung = "";
	private String mathietbi = "";
	private String noidung = "";
	private int thoigiantimer = 15000; // 15 giây kiểm tra cơ sở dữ liệu một lần xem có thông báo mới không
	private String version = "";

	public static void main(String[] args) {
		try {
			BaoSuaThietBi window = new BaoSuaThietBi();
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
		createContents(manguoidung, ngonngu, db_url,version);
		shellBaosuathietbi.open();
		shellBaosuathietbi.layout();
		while (!shellBaosuathietbi.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(String manguoidung, int ngonngu, String chuoiketnoi,String phienban) {
		db_url = chuoiketnoi;
		version=phienban;
		this.manguoidung = manguoidung;
		this.ngonngu = ngonngu;

		shellBaosuathietbi = new Shell(SWT.MIN);
		shellBaosuathietbi.setSize(748, 391);
		shellBaosuathietbi.setText("Báo sửa thiết bị - " + version);
		shellBaosuathietbi.setLocation(300, 200);
		shellBaosuathietbi
				.setImage(SWTResourceManager.getImage(BaoSuaThietBi.class, "/repairdevice/Images/repair.ico"));

		CLabel lbThietbi = new CLabel(shellBaosuathietbi, SWT.NONE);
		lbThietbi.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		lbThietbi.setText("Thiết Bị");
		lbThietbi.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbThietbi.setAlignment(SWT.RIGHT);
		lbThietbi.setBounds(20, 25, 91, 25);

		CLabel lbNoidung = new CLabel(shellBaosuathietbi, SWT.NONE);
		lbNoidung.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lbNoidung.setText("Nội dung");
		lbNoidung.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbNoidung.setAlignment(SWT.RIGHT);
		lbNoidung.setBounds(20, 134, 91, 25);

		Combo cbThietbi = new Combo(shellBaosuathietbi, SWT.NONE);
		cbThietbi.setItems(new String[] {});
		cbThietbi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		cbThietbi.setBounds(134, 25, 270, 27);
		cbThietbi.select(9);

		textNoidung = new Text(shellBaosuathietbi, SWT.BORDER | SWT.MULTI);
		textNoidung.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textNoidung.setBounds(134, 69, 565, 195);

		Button btnChitiet = new Button(shellBaosuathietbi, SWT.NONE);
		btnChitiet.setImage(SWTResourceManager.getImage(BaoSuaThietBi.class, "/repairdevice/Images/chi tiet.png"));
		btnChitiet.setText("Chi Tiết");
		btnChitiet.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnChitiet.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		btnChitiet.setBounds(189, 280, 136, 35);

		// xử lý Button Chi tiết
		// ------------------------------------------------------------
		btnChitiet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BaoSuaThietBiChiTiet baosuachitiet = new BaoSuaThietBiChiTiet();
				baosuachitiet.createContents(manguoidung, ngonngu, db_url);
				baosuachitiet.open();
			}
		});

		Button btnBaosua = new Button(shellBaosuathietbi, SWT.NONE);
		btnBaosua.setImage(SWTResourceManager.getImage(BaoSuaThietBi.class, "/repairdevice/Images/baosua.png"));
		btnBaosua.setText("Báo sửa");
		btnBaosua.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		btnBaosua.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		btnBaosua.setBounds(342, 280, 174, 35);

		// xử lý Button Báo sửa
		// ------------------------------------------------------------
		btnBaosua.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String selectThietBi = "SELECT MaThietBi FROM ThietBi WHERE TenThietBi=N'" + cbThietbi.getText()
							+ "'";
					ResultSet result = statement.executeQuery(selectThietBi);
					while (result.next()) {
						mathietbi = result.getString(1);
					}
					result.close();

					noidung = textNoidung.getText();
					if (!noidung.equals("")) {
						String insertBaoSuaThietBi = "INSERT INTO BaoSuaThietBi(MaNguoiDung,MaThietBi,NoiDung,TrangThai) VALUES ('"
								+ manguoidung + "','" + mathietbi + "',N'" + noidung + "',0)";
						int resultinsert = statement.executeUpdate(insertBaoSuaThietBi);
						if (resultinsert > 0) {
							MessageBox thongbao = new MessageBox(shellBaosuathietbi, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Báo sửa thành công!");
							} else if (ngonngu == 1) {
								thongbao.setText("Notification");
								thongbao.setMessage("Send successfully!");
							} else if (ngonngu == 2) {
								thongbao.setText("通知");
								thongbao.setMessage("提交成功");
							}
							thongbao.open();
							textNoidung.setText("");

							// thêm vào SlideShow
							String selectphanbiet = "SELECT TOP 1 PhanBiet FROM dbo.BaoSuaThietBi WHERE MaNguoiDung='"
									+ manguoidung + "' ORDER BY PhanBiet DESC";
							Statement statephanbiet = conn.createStatement();
							ResultSet ketquaphanbiet = statephanbiet.executeQuery(selectphanbiet);
							while (ketquaphanbiet.next()) {
								Statement stateinsert = conn.createStatement();
								String inserslideshow = "INSERT INTO SlideShowThongBao(PhanBiet,ThoiGianCapNhat,HoanThanh) VALUES ('"
										+ ketquaphanbiet.getString(1) + "',GETDATE(),0)";
								stateinsert.executeUpdate(inserslideshow);
								stateinsert.close();
							}
							ketquaphanbiet.close();
							statephanbiet.close();

						}
					} else {
						// nội dung không được bỏ trống
						MessageBox thongbao = new MessageBox(shellBaosuathietbi, SWT.ICON_INFORMATION | SWT.OK);
						if (ngonngu == 0) {
							thongbao.setText("Thông báo");
							thongbao.setMessage("Nội dung không được bỏ trống!");
						} else if (ngonngu == 1) {
							thongbao.setText("Notification");
							thongbao.setMessage("The content must not be empty!");
						} else if (ngonngu == 2) {
							thongbao.setText("通知");
							thongbao.setMessage("內容不能為空");
						}
						thongbao.open();
					}

				} catch (SQLException se) {
					MessageBox thongbao = new MessageBox(shellBaosuathietbi, SWT.ICON_INFORMATION | SWT.OK);
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

		Button btnHuy = new Button(shellBaosuathietbi, SWT.NONE);
		btnHuy.setImage(SWTResourceManager.getImage(BaoSuaThietBi.class, "/repairdevice/Images/cancel.png"));
		btnHuy.setText("Hủy");
		btnHuy.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnHuy.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.BOLD));
		btnHuy.setBounds(531, 280, 118, 35);

		Menu menubar = new Menu(shellBaosuathietbi, SWT.BAR);
		shellBaosuathietbi.setMenuBar(menubar);

		MenuItem menubarNguoidung = new MenuItem(menubar, SWT.CASCADE);
		menubarNguoidung.setText("Người dùng");

		Menu menuNguoidung = new Menu(menubarNguoidung);
		menubarNguoidung.setMenu(menuNguoidung);

		MenuItem mnitemThongtinnguoidung = new MenuItem(menuNguoidung, SWT.NONE);
		mnitemThongtinnguoidung.setText("Thông tin người dùng");

		MenuItem menubarPhieudenghi = new MenuItem(menubar, SWT.CASCADE);
		menubarPhieudenghi.setText("Phiếu đề nghị");

		Menu menuPhieudenghi = new Menu(menubarPhieudenghi);
		menubarPhieudenghi.setMenu(menuPhieudenghi);

		MenuItem mnitemDSphieudenghi = new MenuItem(menuPhieudenghi, SWT.NONE);
		mnitemDSphieudenghi.setText("Danh sách phiếu đề nghị");

		// Xử lý lấy nội dung cho Combo Thiết bị
		// -------------------------------------------
		try {
			conn = DriverManager.getConnection(db_url);

			statement = conn.createStatement();
			String selectThietBi = "SELECT TenThietBi FROM ThietBi ORDER BY TenThietBi ASC";
			ResultSet result = statement.executeQuery(selectThietBi);
			while (result.next()) {
				cbThietbi.add(result.getString(1));
			}
			result.close();

		} catch (SQLException se) {
			MessageBox thongbao = new MessageBox(shellBaosuathietbi, SWT.ICON_INFORMATION | SWT.OK);
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

		// Xử lý ngôn ngữ
		// ------------------------------------------------------------------
		if (ngonngu == 0) {
			shellBaosuathietbi.setText("Báo sửa thiết bị - " + version);
			lbThietbi.setText("Thiết Bị");
			lbNoidung.setText("Nội dung");
			menubarNguoidung.setText("Người dùng");
			mnitemThongtinnguoidung.setText("Thông tin người dùng");
			menubarPhieudenghi.setText("Phiếu đề nghị");
			mnitemDSphieudenghi.setText("Danh sách phiếu đề nghị");
			btnChitiet.setText("Chi Tiết");
			btnBaosua.setText("Báo sửa");
			btnHuy.setText("Hủy");
		} else if (ngonngu == 1) {
			shellBaosuathietbi.setText("Equipment repair report - " + version);
			lbThietbi.setText("Device");
			lbNoidung.setText("Content");
			menubarNguoidung.setText("User");
			mnitemThongtinnguoidung.setText("User information");
			menubarPhieudenghi.setText("Suggestion form");
			mnitemDSphieudenghi.setText("Suggestion form list");
			btnChitiet.setText("Detail");
			btnBaosua.setText("Repair report");
			btnHuy.setText("Cancel");
		} else if (ngonngu == 2) {
			shellBaosuathietbi.setText("設備維修報告 -  " + version);
			lbThietbi.setText("設備");
			lbNoidung.setText("內容");
			menubarNguoidung.setText("使用者");
			mnitemThongtinnguoidung.setText("用戶信息");
			menubarPhieudenghi.setText("建議表格");
			mnitemDSphieudenghi.setText("建議表格清單");
			btnChitiet.setText("詳情");
			btnBaosua.setText("修理報告");
			btnHuy.setText("取消");
		}

		// xử lý Button Hủy ------------------------------------------------------------
		btnHuy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shellBaosuathietbi.dispose();
			}
		});

		// Xử lý menu Thông tin người dùng
		// -----------------------------------------------------
		mnitemThongtinnguoidung.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BaoSuaThietBiThongTinNguoiDung thongtinnguoidung = new BaoSuaThietBiThongTinNguoiDung();
				thongtinnguoidung.createContents(manguoidung, ngonngu, db_url);
				shellBaosuathietbi.setEnabled(false);
				thongtinnguoidung.open();
				shellBaosuathietbi.setEnabled(true);
			}
		});

		// Xử lý menu Phiếu đề nghị
		// -----------------------------------------------------
		mnitemDSphieudenghi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BaoSuaThietBiPhieuDeNghi phieudenghi = new BaoSuaThietBiPhieuDeNghi();
				phieudenghi.createContents(ngonngu, db_url);
				phieudenghi.open();
			}
		});

		// Kiểm tra xem có thông báo cho mình không, thông báo xem ai sẽ sửa thiết bị
		// cho mình
		// ******************************************************************************************************************************************
		Runnable timer = new Runnable() {

			@Override
			public void run() {
				try {
					Connection connect = DriverManager.getConnection(db_url);
					Statement statethongbao = connect.createStatement();

					String select = "SELECT TOP 1 ThongBao.PhanBiet,NguoiDung.TenNguoiDung FROM ThongBao,NguoiDung,BaoSuaThietBi WHERE ThongBao.NguoiDuocThongBao=NguoiDung.MaNguoiDung AND ThongBao.PhanBiet=BaoSuaThietBi.PhanBiet AND ThongBao.PhanBiet IS NOT NULL AND ThongBao.PhanBiet!=0 AND DaXemUser=0 AND CONVERT(DATE,ThongBao.ThoiGianThongBao)=CONVERT(DATE,GETDATE()) AND BaoSuaThietBi.MaNguoiDung='"
							+ manguoidung + "' ORDER BY ThoiGianThongBao DESC";
					ResultSet result = statethongbao.executeQuery(select);
					int dem = 0;
					while (result.next()) {
						MessageBox message = new MessageBox(shellBaosuathietbi, SWT.ICON_INFORMATION | SWT.OK);
						shellBaosuathietbi.setEnabled(false);
						if (ngonngu == 0) {
							message.setText("Thông báo");
							message.setMessage(
									"Yêu cầu của bạn đã được phân công cho IT: " + result.getString(2) + " thực hiện");
						} else if (ngonngu == 1) {
							message.setText("Notification");
							message.setMessage(
									"Your request has been assigned to " + result.getString(2) + " for execution");
						} else if (ngonngu == 2) {
							message.setText("通知");
							message.setMessage("您的請求已分配給 " + result.getString(2) + " 執行");
						}
						dem++;
						if (dem == 1) {
							message.open();
						}
						shellBaosuathietbi.setEnabled(true);
						// Xóa thông báo cũ sau khi xem xong
						try {
							Connection connectcapnhat = DriverManager.getConnection(db_url);
							Statement statethongbaocapnhat = connectcapnhat.createStatement();
							String capnhatthongbao = "UPDATE ThongBao SET DaXemUser=1 WHERE PhanBiet="
									+ result.getString(1);
							statethongbaocapnhat.executeUpdate(capnhatthongbao);
							statethongbaocapnhat.close();
							connectcapnhat.close();
						} catch (SQLException sqle) {

						}
					}
					result.close();
					statethongbao.close();
					connect.close();
				} catch (Exception se) {

				}

				Display.getDefault().timerExec(thoigiantimer, this);
			}
		};
		Display.getDefault().timerExec(thoigiantimer, timer);

	}
}
