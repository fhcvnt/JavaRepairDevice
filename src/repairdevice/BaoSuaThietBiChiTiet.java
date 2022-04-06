package repairdevice;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.ScrolledComposite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import repairdevice.BaoSuaThietBiChiTiet;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class BaoSuaThietBiChiTiet {

	protected Shell shell;
	// Chuỗi kết nối
	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;
	private int ngonngu = 0; // 0: Vietnam, 1: English, 2: China
	private String manguoidung = "";
	private Table table;
	// kích thước màn hình
	private int sizemonitorx = Display.getDefault().getBounds().width;
	private int sizemonitory = Display.getDefault().getBounds().height;

	public static void main(String[] args) {
		try {
			BaoSuaThietBiChiTiet window = new BaoSuaThietBiChiTiet();
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
		shell = new Shell();
		shell.setSize(1366, 768);
		shell.setText("Chi tiết báo sửa thiết bị");
		shell.setLayout(new FillLayout());
		shell.setMaximized(true);
		shell.setImage(SWTResourceManager.getImage(BaoSuaThietBiChiTiet.class, "/repairdevice/Images/repair.ico"));

		this.manguoidung = manguoidung;
		this.ngonngu = ngonngu;

		ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		scrolledComposite.setContent(composite);

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		table.setHeaderBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		table.setBounds(10, 62, 1326, 653);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setSize(sizemonitorx - 25, sizemonitory - 140);

		TableColumn tbclDong = new TableColumn(table, SWT.NONE);
		tbclDong.setWidth(0);
		tbclDong.setText("Dòng");
		tbclDong.setResizable(false);

		TableColumn tbclThietbi = new TableColumn(table, SWT.NONE);
		tbclThietbi.setWidth(165);
		tbclThietbi.setText("Thiết bị");

		TableColumn tbclNoidung = new TableColumn(table, SWT.NONE);
		tbclNoidung.setWidth(189);
		tbclNoidung.setText("Nội dung");

		TableColumn tbclThoigianbao = new TableColumn(table, SWT.NONE);
		tbclThoigianbao.setWidth(215);
		tbclThoigianbao.setText("Thời gian báo");

		TableColumn tbclTrangthai = new TableColumn(table, SWT.NONE);
		tbclTrangthai.setWidth(130);
		tbclTrangthai.setText("Trạng thái");

		TableColumn tbclNguoiphancong = new TableColumn(table, SWT.NONE);
		tbclNguoiphancong.setWidth(150);
		tbclNguoiphancong.setText("Người phân công");

		TableColumn tbclNguoisua = new TableColumn(table, SWT.NONE);
		tbclNguoisua.setWidth(164);
		tbclNguoisua.setText("Người sửa");

		TableColumn tbclSodienthoai = new TableColumn(table, SWT.NONE);
		tbclSodienthoai.setWidth(120);
		tbclSodienthoai.setText("Số điện thoại");

		TableColumn tbclThoigiancapnhat = new TableColumn(table, SWT.NONE);
		tbclThoigiancapnhat.setWidth(183);
		tbclThoigiancapnhat.setText("Thời gian cập nhật");
		
		Button btnHoanthanh = new Button(composite, SWT.NONE);
		btnHoanthanh.setImage(SWTResourceManager.getImage(BaoSuaThietBiChiTiet.class, "/repairdevice/Images/hoanthanh.png"));
		btnHoanthanh.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnHoanthanh.setBounds(40, 16, 142, 35);
		btnHoanthanh.setText("Hoàn Thành");
		
		Button btnChuahoanthanh = new Button(composite, SWT.NONE);
		btnChuahoanthanh.setImage(SWTResourceManager.getImage(BaoSuaThietBiChiTiet.class, "/repairdevice/Images/info96.png"));
		btnChuahoanthanh.setText("Chưa Hoàn Thành");
		btnChuahoanthanh.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnChuahoanthanh.setBounds(188, 16, 175, 35);
		
		Button btnBaolai = new Button(composite, SWT.NONE);
		btnBaolai.setImage(SWTResourceManager.getImage(BaoSuaThietBiChiTiet.class, "/repairdevice/Images/baosua.png"));
		btnBaolai.setText("Báo Lại");
		btnBaolai.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnBaolai.setBounds(369, 16, 128, 35);
		
		Button btnTailai = new Button(composite, SWT.NONE);
		btnTailai.setImage(SWTResourceManager.getImage(BaoSuaThietBiChiTiet.class, "/repairdevice/Images/refresh.png"));
		btnTailai.setText("Tải Lại");
		btnTailai.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnTailai.setBounds(503, 16, 103, 35);
		
		Button btnXoa = new Button(composite, SWT.NONE);
		btnXoa.setImage(SWTResourceManager.getImage(BaoSuaThietBiChiTiet.class, "/repairdevice/Images/delete.png"));
		btnXoa.setText("Xóa");
		btnXoa.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnXoa.setBounds(612, 16, 103, 35);

		// Xử lý ngôn ngữ
				// ------------------------------------------------------------------
				if (ngonngu == 0) {
					shell.setText("Chi tiết báo sửa thiết bị");
					btnHoanthanh.setText("Hoàn thành");
					btnBaolai.setText("Báo lại");
					btnChuahoanthanh.setText("Chưa hoàn thành");
					btnXoa.setText("Xóa");
					btnTailai.setText("Tải lại");
					tbclDong.setText("Dòng");
					tbclThietbi.setText("Thiết bị");
					tbclNoidung.setText("Nội dung");
					tbclThoigianbao.setText("Thời gian báo");
					tbclTrangthai.setText("Trạng thái");
					tbclNguoiphancong.setText("Người phân công");
					tbclNguoisua.setText("Người sửa");
					tbclSodienthoai.setText("Số điện thoại");
					tbclThoigiancapnhat.setText("Thời gian cập nhật");
				} else if (ngonngu == 1) {
					shell.setText("Detailed equipment repair report");
					btnHoanthanh.setText("Complete");
					btnBaolai.setText("Send again");
					btnChuahoanthanh.setText("Incomplete");
					btnXoa.setText("Delete");
					btnTailai.setText("Refresh");
					tbclDong.setText("Row");
					tbclThietbi.setText("Device");
					tbclNoidung.setText("Content");
					tbclThoigianbao.setText("Send time");
					tbclTrangthai.setText("Status");
					tbclNguoiphancong.setText("Taskmaster");
					tbclNguoisua.setText("Repairer");
					tbclSodienthoai.setText("Phone number");
					tbclThoigiancapnhat.setText("Time to update");
				} else if (ngonngu == 2) {
					shell.setText("詳細的設備維修報告");
					btnHoanthanh.setText("完成");
					btnBaolai.setText("重新發送");
					btnChuahoanthanh.setText("不完整");
					btnXoa.setText("刪除");
					btnTailai.setText("刷新");
					tbclDong.setText("行");
					tbclThietbi.setText("設備");
					tbclNoidung.setText("內容");
					tbclThoigianbao.setText("發送時間");
					tbclTrangthai.setText("狀態");
					tbclNguoiphancong.setText("工作分配者");
					tbclNguoisua.setText("固定器");
					tbclSodienthoai.setText("電話號碼");
					tbclThoigiancapnhat.setText("是時候更新了");
				}

				// Xu ly du lieu luc dau --------------------------------------------------
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String select = "SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,NguoiDung.TenNguoiDung AS NguoiPhanCong,nd.TenNguoiDung AS NguoiDuocPhanCong,nd.SoDienThoai,BaoSuaThietBi.ThoiGianCapNhat FROM ThietBi,BaoSuaThietBi LEFT JOIN NguoiDung on BaoSuaThietBi.NguoiPhanCong = NguoiDung.MaNguoiDung LEFT JOIN NguoiDung AS nd on BaoSuaThietBi.NguoiDuocPhanCong = nd.MaNguoiDung WHERE BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi AND BaoSuaThietBi.Huy=0 AND BaoSuaThietBi.DaHoanTat=0 AND  BaoSuaThietBi.MaNguoiDung='"
							+ manguoidung + "'";
					ResultSet result = statement.executeQuery(select);
					table.removeAll();

					while (result.next()) {
						String thoigianbao = result.getString(4);
						thoigianbao = thoigianbao.substring(8, 10) + "/" + thoigianbao.substring(5, 7) + "/"
								+ thoigianbao.substring(0, 4) + " " + thoigianbao.substring(11, 19);
						String thoigiancapnhat = result.getString(9);
						try {
							thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/" + thoigiancapnhat.substring(5, 7) + "/"
									+ thoigiancapnhat.substring(0, 4) + " " + thoigiancapnhat.substring(11, 19);
						} catch (NullPointerException ne) {
							thoigiancapnhat = "";
						}
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3), thoigianbao,
								result.getString(5), result.getString(6), result.getString(7), result.getString(8),
								thoigiancapnhat });
					}

					result.close();

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

				// Button Hoan thanh -------------------------------------------------------
				btnHoanthanh.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						try {
							conn = DriverManager.getConnection(db_url);
							statement = conn.createStatement();

							try {
								// Lấy cột Dong
								TableItem[] itemtable = table.getSelection();
								String dong = itemtable[0].getText();
								String updateBaosuathietbi = "UPDATE BaoSuaThietBi SET TrangThai=2,ThoiGianCapNhat=GETDATE(),DaHoanTat=1 WHERE PhanBiet="
										+ dong + "";
								statement.executeUpdate(updateBaosuathietbi);

								String select = "SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,NguoiDung.TenNguoiDung AS NguoiPhanCong,nd.TenNguoiDung AS NguoiDuocPhanCong,nd.SoDienThoai,BaoSuaThietBi.ThoiGianCapNhat FROM ThietBi,BaoSuaThietBi LEFT JOIN NguoiDung on BaoSuaThietBi.NguoiPhanCong = NguoiDung.MaNguoiDung LEFT JOIN NguoiDung AS nd on BaoSuaThietBi.NguoiDuocPhanCong = nd.MaNguoiDung WHERE BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi AND BaoSuaThietBi.Huy=0 AND BaoSuaThietBi.DaHoanTat=0 AND  BaoSuaThietBi.MaNguoiDung='"
										+ manguoidung + "'";
								ResultSet result = statement.executeQuery(select);
								table.removeAll();

								while (result.next()) {
									String thoigianbao = result.getString(4);
									thoigianbao = thoigianbao.substring(8, 10) + "/" + thoigianbao.substring(5, 7) + "/"
											+ thoigianbao.substring(0, 4) + " " + thoigianbao.substring(11, 19);
									String thoigiancapnhat = result.getString(9);
									try {
										thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/"
												+ thoigiancapnhat.substring(5, 7) + "/" + thoigiancapnhat.substring(0, 4) + " "
												+ thoigiancapnhat.substring(11, 19);
									} catch (NullPointerException ne) {
										thoigiancapnhat = "";
									}
									TableItem item = new TableItem(table, SWT.NONE);
									item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
											thoigianbao, result.getString(5), result.getString(6), result.getString(7),
											result.getString(8), thoigiancapnhat });
								}

								result.close();

							} catch (Exception ex) {
								MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								thongbao.setText("Error");
								thongbao.setMessage(ex.toString());
								thongbao.open();
							}

						} catch (SQLException se) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							thongbao.setText("Thông báo lỗi!");
							thongbao.setMessage("Lỗi kết nối!");
							thongbao.setMessage(se.toString());
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

				// Button Chua hoan thanh
				// -------------------------------------------------------
				btnChuahoanthanh.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						try {
							conn = DriverManager.getConnection(db_url);
							statement = conn.createStatement();

							try {
								// Lấy cột Dong
								TableItem[] itemtable = table.getSelection();
								String dong = itemtable[0].getText();
								String updateBaosuathietbi = "UPDATE BaoSuaThietBi SET TrangThai=4,ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
										+ dong + "";
								statement.executeUpdate(updateBaosuathietbi);

								String select = "SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,NguoiDung.TenNguoiDung AS NguoiPhanCong,nd.TenNguoiDung AS NguoiDuocPhanCong,nd.SoDienThoai,BaoSuaThietBi.ThoiGianCapNhat FROM ThietBi,BaoSuaThietBi LEFT JOIN NguoiDung on BaoSuaThietBi.NguoiPhanCong = NguoiDung.MaNguoiDung LEFT JOIN NguoiDung AS nd on BaoSuaThietBi.NguoiDuocPhanCong = nd.MaNguoiDung WHERE BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi AND BaoSuaThietBi.Huy=0 AND BaoSuaThietBi.DaHoanTat=0 AND  BaoSuaThietBi.MaNguoiDung='"
										+ manguoidung + "'";
								ResultSet result = statement.executeQuery(select);
								table.removeAll();

								while (result.next()) {
									String thoigianbao = result.getString(4);
									thoigianbao = thoigianbao.substring(8, 10) + "/" + thoigianbao.substring(5, 7) + "/"
											+ thoigianbao.substring(0, 4) + " " + thoigianbao.substring(11, 19);
									String thoigiancapnhat = result.getString(9);
									try {
										thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/"
												+ thoigiancapnhat.substring(5, 7) + "/" + thoigiancapnhat.substring(0, 4) + " "
												+ thoigiancapnhat.substring(11, 19);
									} catch (NullPointerException ne) {
										thoigiancapnhat = "";
									}
									TableItem item = new TableItem(table, SWT.NONE);
									item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
											thoigianbao, result.getString(5), result.getString(6), result.getString(7),
											result.getString(8), thoigiancapnhat });
								}

								result.close();

							} catch (Exception ex) {
								MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								thongbao.setText("Error");
								thongbao.setMessage(ex.toString());
								thongbao.open();
							}

						} catch (SQLException se) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							thongbao.setText("Thông báo lỗi!");
							thongbao.setMessage("Lỗi kết nối!");
							thongbao.setMessage(se.toString());
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

				// Button Bao lai --------------------------------------------------------------
				btnBaolai.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						try {
							conn = DriverManager.getConnection(db_url);
							statement = conn.createStatement();

							try {
								// Lấy cột Dong
								TableItem[] itemtable = table.getSelection();
								String dong = itemtable[0].getText();
								String updateBaosuathietbi = "UPDATE BaoSuaThietBi SET TrangThai=3,ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
										+ dong + "";
								statement.executeUpdate(updateBaosuathietbi);

								String select = "SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,NguoiDung.TenNguoiDung AS NguoiPhanCong,nd.TenNguoiDung AS NguoiDuocPhanCong,nd.SoDienThoai,BaoSuaThietBi.ThoiGianCapNhat FROM ThietBi,BaoSuaThietBi LEFT JOIN NguoiDung on BaoSuaThietBi.NguoiPhanCong = NguoiDung.MaNguoiDung LEFT JOIN NguoiDung AS nd on BaoSuaThietBi.NguoiDuocPhanCong = nd.MaNguoiDung WHERE BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi AND BaoSuaThietBi.Huy=0 AND BaoSuaThietBi.DaHoanTat=0 AND  BaoSuaThietBi.MaNguoiDung='"
										+ manguoidung + "'";
								ResultSet result = statement.executeQuery(select);
								table.removeAll();

								while (result.next()) {
									String thoigianbao = result.getString(4);
									thoigianbao = thoigianbao.substring(8, 10) + "/" + thoigianbao.substring(5, 7) + "/"
											+ thoigianbao.substring(0, 4) + " " + thoigianbao.substring(11, 19);
									String thoigiancapnhat = result.getString(9);
									try {
										thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/"
												+ thoigiancapnhat.substring(5, 7) + "/" + thoigiancapnhat.substring(0, 4) + " "
												+ thoigiancapnhat.substring(11, 19);
									} catch (NullPointerException ne) {
										thoigiancapnhat = "";
									}
									TableItem item = new TableItem(table, SWT.NONE);
									item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
											thoigianbao, result.getString(5), result.getString(6), result.getString(7),
											result.getString(8), thoigiancapnhat });
								}

								result.close();

							} catch (Exception ex) {
								MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								thongbao.setText("Error");
								thongbao.setMessage(ex.toString());
								thongbao.open();
							}

						} catch (SQLException se) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							thongbao.setText("Thông báo lỗi!");
							thongbao.setMessage("Lỗi kết nối!");
							thongbao.setMessage(se.toString());
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

				// Button Tai lai
				// ------------------------------------------------------------------
				btnTailai.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						try {
							conn = DriverManager.getConnection(db_url);

							statement = conn.createStatement();
							String select = "SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,NguoiDung.TenNguoiDung AS NguoiPhanCong,nd.TenNguoiDung AS NguoiDuocPhanCong,nd.SoDienThoai,BaoSuaThietBi.ThoiGianCapNhat FROM ThietBi,BaoSuaThietBi LEFT JOIN NguoiDung on BaoSuaThietBi.NguoiPhanCong = NguoiDung.MaNguoiDung LEFT JOIN NguoiDung AS nd on BaoSuaThietBi.NguoiDuocPhanCong = nd.MaNguoiDung WHERE BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi AND BaoSuaThietBi.Huy=0 AND BaoSuaThietBi.DaHoanTat=0 AND  BaoSuaThietBi.MaNguoiDung='"
									+ manguoidung + "'";
							ResultSet result = statement.executeQuery(select);
							table.removeAll();

							while (result.next()) {
								String thoigianbao = result.getString(4);
								thoigianbao = thoigianbao.substring(8, 10) + "/" + thoigianbao.substring(5, 7) + "/"
										+ thoigianbao.substring(0, 4) + " " + thoigianbao.substring(11, 19);
								String thoigiancapnhat = result.getString(9);
								try {
									thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/" + thoigiancapnhat.substring(5, 7)
											+ "/" + thoigiancapnhat.substring(0, 4) + " " + thoigiancapnhat.substring(11, 19);
								} catch (NullPointerException ne) {
									thoigiancapnhat = "";
								}
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
										thoigianbao, result.getString(5), result.getString(6), result.getString(7),
										result.getString(8), thoigiancapnhat });
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

				// Button Xoa
				// -------------------------------------------------------------------------
				btnXoa.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						try {
							conn = DriverManager.getConnection(db_url);
							statement = conn.createStatement();

							try {
								// Lấy cột Dong
								TableItem[] item = table.getSelection();
								String dong = item[0].getText();
								String updateBaosuathietbi = "UPDATE BaoSuaThietBi SET Huy=1,ThoiGianHuy=GETDATE(),ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
										+ dong + "";
								statement.executeUpdate(updateBaosuathietbi);
								table.remove(table.getSelectionIndices());
							} catch (Exception ex) {
								MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								thongbao.setText("Error");
								thongbao.setMessage(ex.toString());
								thongbao.open();
							}

						} catch (SQLException se) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							thongbao.setText("Thông báo lỗi!");
							thongbao.setMessage("Lỗi kết nối!");
							thongbao.setMessage(se.toString());
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

				// cái này phải ở cuối cùng thì mới scroll được
				scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

	}
}
