package com.cavium.test;

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

public class BaoSuaChiTiet {

	protected Shell shellChitietbaosua;
	// Chuỗi kết nối
	private String db_url = "jdbc:sqlserver://192.168.30.123;databaseName=SuaChuaThietBi;user=sa;password=Killua21608";
	private Connection conn = null;
	private Statement statement = null;
	private int ngonngu = 0; // 0: Vietnam, 1: English, 2: China
	private String manguoidung = "";
	private Table table;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BaoSuaChiTiet window = new BaoSuaChiTiet();
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
		createContents(manguoidung, ngonngu);
		shellChitietbaosua.open();
		shellChitietbaosua.layout();
		while (!shellChitietbaosua.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(String manguoidung, int ngonngu) {
		shellChitietbaosua = new Shell();
		shellChitietbaosua.setSize(1366, 768);
		shellChitietbaosua.setText("Chi tiết báo sửa thiết bị");
		shellChitietbaosua.setLayout(new FillLayout());
		
		this.manguoidung = manguoidung;
		this.ngonngu = ngonngu;

		ScrolledComposite scrolledComposite = new ScrolledComposite(shellChitietbaosua,SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		scrolledComposite.setContent(composite);
		
		Button btnHoanthanh = new Button(composite, SWT.NONE);
		btnHoanthanh.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnHoanthanh.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnHoanthanh.setBounds(21, 21, 142, 35);
		btnHoanthanh.setText("Hoàn thành");
		btnHoanthanh.setImage(SWTResourceManager.getImage(BaoSuaThietBiChiTiet.class, "/repairdevice/Images/hoanthanh.png"));
		
		Button btnChuahoanthanh = new Button(composite, SWT.NONE);
		btnChuahoanthanh.setText("Chưa hoàn thành");
		btnChuahoanthanh.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnChuahoanthanh.setBounds(169, 21, 165, 35);
		btnChuahoanthanh.setImage(SWTResourceManager.getImage(BaoSuaThietBiChiTiet.class, "/repairdevice/Images/huy.png"));
		
		Button btnBaolai = new Button(composite, SWT.NONE);
		btnBaolai.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		btnBaolai.setText("Báo lại");
		btnBaolai.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnBaolai.setBounds(340, 21, 114, 35);
		btnBaolai.setImage(SWTResourceManager.getImage(BaoSuaThietBiChiTiet.class, "/repairdevice/Images/baosua.png"));
		
		Button btnTailai = new Button(composite, SWT.NONE);
		btnTailai.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnTailai.setText("Tải lại");
		btnTailai.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnTailai.setBounds(460, 21, 103, 35);
		btnTailai.setImage(SWTResourceManager.getImage(BaoSuaThietBiChiTiet.class, "/repairdevice/Images/refresh.png"));
		
		Button btnXoa = new Button(composite, SWT.NONE);
		btnXoa.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnXoa.setText("Xóa");
		btnXoa.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnXoa.setBounds(569, 21, 103, 35);
		btnXoa.setImage(SWTResourceManager.getImage(BaoSuaThietBiChiTiet.class, "/repairdevice/Images/delete.png"));
		
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		table.setHeaderBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.NORMAL));
		table.setBounds(21, 63, 1303, 578);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tbclDong = new TableColumn(table, SWT.NONE);
		tbclDong.setWidth(50);
		tbclDong.setText("Dòng");

		TableColumn tbclThietbi = new TableColumn(table, SWT.NONE);
		tbclThietbi.setWidth(165);
		tbclThietbi.setText("Thiết bị");

		TableColumn tbclNoidung = new TableColumn(table, SWT.NONE);
		tbclNoidung.setWidth(189);
		tbclNoidung.setText("Nội dung");

		TableColumn tbclThoigianbao = new TableColumn(table, SWT.NONE);
		tbclThoigianbao.setWidth(165);
		tbclThoigianbao.setText("Thời gian báo");

		TableColumn tbclTrangthai = new TableColumn(table, SWT.NONE);
		tbclTrangthai.setWidth(123);
		tbclTrangthai.setText("Trạng thái");

		TableColumn tbclNguoiphancong = new TableColumn(table, SWT.NONE);
		tbclNguoiphancong.setWidth(150);
		tbclNguoiphancong.setText("Người phân công");

		TableColumn tbclNguoisua = new TableColumn(table, SWT.NONE);
		tbclNguoisua.setWidth(149);
		tbclNguoisua.setText("Người sửa");

		TableColumn tbclSodienthoai = new TableColumn(table, SWT.NONE);
		tbclSodienthoai.setWidth(120);
		tbclSodienthoai.setText("Số điện thoại");

		TableColumn tbclThoigiancapnhat = new TableColumn(table, SWT.NONE);
		tbclThoigiancapnhat.setWidth(183);
		tbclThoigiancapnhat.setText("Thời gian cập nhật");
		
		
		// Xử lý ngôn ngữ
				// ------------------------------------------------------------------
				if (ngonngu == 0) {
					shellChitietbaosua.setText("Chi tiết báo sửa thiết bị");
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
					shellChitietbaosua.setText("Detailed equipment repair report");
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
					shellChitietbaosua.setText("詳細的設備維修報告");
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
					tbclNguoiphancong.setText("任務主管");
					tbclNguoisua.setText("固定器");
					tbclSodienthoai.setText("電話號碼");
					tbclThoigiancapnhat.setText("是時候更新了");
				}

				// Xu ly du lieu luc dau --------------------------------------------------
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();
					
					String select="SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,NguoiDung.TenNguoiDung AS NguoiPhanCong,nd.TenNguoiDung AS NguoiDuocPhanCong,nd.SoDienThoai,BaoSuaThietBi.ThoiGianCapNhat FROM ThietBi,BaoSuaThietBi LEFT JOIN NguoiDung on BaoSuaThietBi.NguoiPhanCong = NguoiDung.MaNguoiDung LEFT JOIN NguoiDung AS nd on BaoSuaThietBi.NguoiDuocPhanCong = nd.MaNguoiDung WHERE BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi AND BaoSuaThietBi.Huy=0 AND BaoSuaThietBi.DaHoanTat=0 AND  BaoSuaThietBi.MaNguoiDung='"+manguoidung+"'";
					ResultSet result = statement.executeQuery(select);
					table.removeAll();

					while (result.next()) {
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
								result.getString(4), result.getString(5), result.getString(6), result.getString(7),
								result.getString(8), result.getString(9) });
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
								String updateBaosuathietbi = "UPDATE BaoSuaThietBi SET TrangThai=2,ThoiGianCapNhat=GETDATE(),DaHoanTat=1 WHERE PhanBiet="+dong+"";
								int resultupdate = statement.executeUpdate(updateBaosuathietbi);
								
								String select="SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,NguoiDung.TenNguoiDung AS NguoiPhanCong,nd.TenNguoiDung AS NguoiDuocPhanCong,nd.SoDienThoai,BaoSuaThietBi.ThoiGianCapNhat FROM ThietBi,BaoSuaThietBi LEFT JOIN NguoiDung on BaoSuaThietBi.NguoiPhanCong = NguoiDung.MaNguoiDung LEFT JOIN NguoiDung AS nd on BaoSuaThietBi.NguoiDuocPhanCong = nd.MaNguoiDung WHERE BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi AND BaoSuaThietBi.Huy=0 AND BaoSuaThietBi.DaHoanTat=0 AND  BaoSuaThietBi.MaNguoiDung='"+manguoidung+"'";
								ResultSet result = statement.executeQuery(select);
								table.removeAll();

								while (result.next()) {
									TableItem item = new TableItem(table, SWT.NONE);
									item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
											result.getString(4), result.getString(5), result.getString(6), result.getString(7),
											result.getString(8), result.getString(9) });
								}

								result.close();
								
							} catch (Exception ex) {
								MessageBox thongbao = new MessageBox(shellChitietbaosua, SWT.ICON_INFORMATION | SWT.OK);
								thongbao.setText("Error");
								thongbao.setMessage(ex.toString());
								thongbao.open();
							}

						} catch (SQLException se) {
							MessageBox thongbao = new MessageBox(shellChitietbaosua, SWT.ICON_INFORMATION | SWT.OK);
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
								String updateBaosuathietbi = "UPDATE BaoSuaThietBi SET TrangThai=4,ThoiGianCapNhat=GETDATE() WHERE PhanBiet="+dong+"";
								int resultupdate = statement.executeUpdate(updateBaosuathietbi);
								
								String select="SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,NguoiDung.TenNguoiDung AS NguoiPhanCong,nd.TenNguoiDung AS NguoiDuocPhanCong,nd.SoDienThoai,BaoSuaThietBi.ThoiGianCapNhat FROM ThietBi,BaoSuaThietBi LEFT JOIN NguoiDung on BaoSuaThietBi.NguoiPhanCong = NguoiDung.MaNguoiDung LEFT JOIN NguoiDung AS nd on BaoSuaThietBi.NguoiDuocPhanCong = nd.MaNguoiDung WHERE BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi AND BaoSuaThietBi.Huy=0 AND BaoSuaThietBi.DaHoanTat=0 AND  BaoSuaThietBi.MaNguoiDung='"+manguoidung+"'";
								ResultSet result = statement.executeQuery(select);
								table.removeAll();

								while (result.next()) {
									TableItem item = new TableItem(table, SWT.NONE);
									item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
											result.getString(4), result.getString(5), result.getString(6), result.getString(7),
											result.getString(8), result.getString(9) });
								}

								result.close();
								
							} catch (Exception ex) {
								MessageBox thongbao = new MessageBox(shellChitietbaosua, SWT.ICON_INFORMATION | SWT.OK);
								thongbao.setText("Error");
								thongbao.setMessage(ex.toString());
								thongbao.open();
							}

						} catch (SQLException se) {
							MessageBox thongbao = new MessageBox(shellChitietbaosua, SWT.ICON_INFORMATION | SWT.OK);
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
								String updateBaosuathietbi = "UPDATE BaoSuaThietBi SET TrangThai=3,ThoiGianCapNhat=GETDATE() WHERE PhanBiet="+dong+"";
								int resultupdate = statement.executeUpdate(updateBaosuathietbi);
								
								String select="SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,NguoiDung.TenNguoiDung AS NguoiPhanCong,nd.TenNguoiDung AS NguoiDuocPhanCong,nd.SoDienThoai,BaoSuaThietBi.ThoiGianCapNhat FROM ThietBi,BaoSuaThietBi LEFT JOIN NguoiDung on BaoSuaThietBi.NguoiPhanCong = NguoiDung.MaNguoiDung LEFT JOIN NguoiDung AS nd on BaoSuaThietBi.NguoiDuocPhanCong = nd.MaNguoiDung WHERE BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi AND BaoSuaThietBi.Huy=0 AND BaoSuaThietBi.DaHoanTat=0 AND  BaoSuaThietBi.MaNguoiDung='"+manguoidung+"'";
								ResultSet result = statement.executeQuery(select);
								table.removeAll();

								while (result.next()) {
									TableItem item = new TableItem(table, SWT.NONE);
									item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
											result.getString(4), result.getString(5), result.getString(6), result.getString(7),
											result.getString(8), result.getString(9) });
								}

								result.close();
								
							} catch (Exception ex) {
								MessageBox thongbao = new MessageBox(shellChitietbaosua, SWT.ICON_INFORMATION | SWT.OK);
								thongbao.setText("Error");
								thongbao.setMessage(ex.toString());
								thongbao.open();
							}

						} catch (SQLException se) {
							MessageBox thongbao = new MessageBox(shellChitietbaosua, SWT.ICON_INFORMATION | SWT.OK);
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
							String select="SELECT PhanBiet,TenThietBi,NoiDung,ThoiGianBao,CASE TrangThai WHEN 0 THEN N'Chờ duyệt' WHEN 1 THEN N'Đã duyệt' WHEN 2 THEN N'Đã sửa' WHEN 3 THEN N'Báo lại' WHEN 4 THEN N'Chưa hoàn thành' ELSE N'Không' END AS TrangThai,NguoiDung.TenNguoiDung AS NguoiPhanCong,nd.TenNguoiDung AS NguoiDuocPhanCong,nd.SoDienThoai,BaoSuaThietBi.ThoiGianCapNhat FROM ThietBi,BaoSuaThietBi LEFT JOIN NguoiDung on BaoSuaThietBi.NguoiPhanCong = NguoiDung.MaNguoiDung LEFT JOIN NguoiDung AS nd on BaoSuaThietBi.NguoiDuocPhanCong = nd.MaNguoiDung WHERE BaoSuaThietBi.MaThietBi=ThietBi.MaThietBi AND BaoSuaThietBi.Huy=0 AND BaoSuaThietBi.DaHoanTat=0 AND  BaoSuaThietBi.MaNguoiDung='"+manguoidung+"'";
							ResultSet result = statement.executeQuery(select);
							table.removeAll();

							while (result.next()) {
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
										result.getString(4), result.getString(5), result.getString(6), result.getString(7),
										result.getString(8), result.getString(9) });
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
								String updateBaosuathietbi = "UPDATE BaoSuaThietBi SET Huy=1,ThoiGianCapNhat=GETDATE() WHERE PhanBiet="+dong+"";
								int result = statement.executeUpdate(updateBaosuathietbi);
								table.remove(table.getSelectionIndices());
							} catch (Exception ex) {
								MessageBox thongbao = new MessageBox(shellChitietbaosua, SWT.ICON_INFORMATION | SWT.OK);
								thongbao.setText("Error");
								thongbao.setMessage(ex.toString());
								thongbao.open();
							}

						} catch (SQLException se) {
							MessageBox thongbao = new MessageBox(shellChitietbaosua, SWT.ICON_INFORMATION | SWT.OK);
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
