package manager;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DanhSachNguoiDung {

	protected Shell shell;
	private Text textTendangnhap;
	private Text textTennguoidung;
	private Text textNhom;
	private Table table;
	private int ngonngu = 0; // 0: Vietnam, 1: English, 2: China
	private String mannhomguoidung = "";

	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;
	private String usercode = ""; // Mã người dùng
	// Kích thước màn hình
	private int sizemonitorx = Display.getDefault().getBounds().width;
	private int sizemonitory = Display.getDefault().getBounds().height;
	private int demxoa = 0;

	public static void main(String[] args) {
		try {
			DanhSachNguoiDung window = new DanhSachNguoiDung();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		Display display = Display.getDefault();
		createContents(mannhomguoidung, ngonngu,db_url);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	// ====================================================================================================================================
	protected void createContents(String mannhomguoidung, int ngonngu,String chuoiketnoi) {
		db_url=chuoiketnoi;
		this.mannhomguoidung = mannhomguoidung;
		this.ngonngu = ngonngu;
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage(DanhSachNguoiDung.class, "/manager/Images/repair256.ico"));
		shell.setSize(1293, 808);
		shell.setText("Danh sách người dùng");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setMaximized(true);

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		CLabel lbTendangnhap = new CLabel(composite, SWT.NONE);
		lbTendangnhap.setAlignment(SWT.RIGHT);
		lbTendangnhap.setText("Tên Đăng Nhập");
		lbTendangnhap.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbTendangnhap.setBounds(23, 25, 117, 30);

		textTendangnhap = new Text(composite, SWT.BORDER);
		textTendangnhap.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textTendangnhap.setBounds(146, 25, 138, 30);

		CLabel lbtennguoidung = new CLabel(composite, SWT.NONE);
		lbtennguoidung.setAlignment(SWT.RIGHT);
		lbtennguoidung.setText("Tên Người Dùng");
		lbtennguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbtennguoidung.setBounds(290, 25, 140, 30);

		textTennguoidung = new Text(composite, SWT.BORDER);
		textTennguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textTennguoidung.setBounds(436, 25, 268, 30);

		CLabel lbNhom = new CLabel(composite, SWT.NONE);
		lbNhom.setAlignment(SWT.RIGHT);
		lbNhom.setText("Nhóm");
		lbNhom.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbNhom.setBounds(714, 25, 70, 30);

		textNhom = new Text(composite, SWT.BORDER);
		textNhom.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textNhom.setBounds(790, 25, 138, 30);

		CLabel lbDonvi = new CLabel(composite, SWT.NONE);
		lbDonvi.setAlignment(SWT.RIGHT);
		lbDonvi.setText("Đơn Vị");
		lbDonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbDonvi.setBounds(23, 76, 116, 30);

		CCombo comboDonvi = new CCombo(composite, SWT.BORDER);
		comboDonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		comboDonvi.setBounds(146, 76, 284, 30);

		Button btnSearch = new Button(composite, SWT.NONE);
		btnSearch.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnSearch.setImage(SWTResourceManager.getImage(DanhSachNguoiDung.class, "/manager/icon/user find.png"));
		btnSearch.setText("Search");
		btnSearch.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnSearch.setBounds(446, 76, 138, 35);

		Button btnAdd = new Button(composite, SWT.NONE);
		btnAdd.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		btnAdd.setImage(SWTResourceManager.getImage(DanhSachNguoiDung.class, "/manager/icon/user add.png"));
		btnAdd.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnAdd.setBounds(652, 76, 106, 35);
		btnAdd.setText("Add");

		Button btnEdit = new Button(composite, SWT.NONE);
		btnEdit.setForeground(SWTResourceManager.getColor(255, 69, 0));
		btnEdit.setImage(SWTResourceManager.getImage(DanhSachNguoiDung.class, "/manager/icon/user edit.png"));
		btnEdit.setText("Edit");
		btnEdit.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnEdit.setBounds(764, 76, 106, 35);

		Button btnDelete = new Button(composite, SWT.NONE);
		btnDelete.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnDelete.setImage(SWTResourceManager.getImage(DanhSachNguoiDung.class, "/manager/icon/user delete.png"));
		btnDelete.setText("Delete");
		btnDelete.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnDelete.setBounds(876, 76, 117, 35);

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderForeground(SWTResourceManager.getColor(0, 0, 0));
		table.setHeaderBackground(SWTResourceManager.getColor(255, 165, 0));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		table.setBounds(20, 125, 1231, 619);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setSize(sizemonitorx-40, sizemonitory-200);

		TableColumn tbclmanguoidung = new TableColumn(table, SWT.NONE);
		tbclmanguoidung.setWidth(137);
		tbclmanguoidung.setText("Mã Người Dùng");

		TableColumn tbclNhom = new TableColumn(table, SWT.NONE);
		tbclNhom.setWidth(100);
		tbclNhom.setText("Nhóm");

		TableColumn tbclTendangnhap = new TableColumn(table, SWT.NONE);
		tbclTendangnhap.setWidth(165);
		tbclTendangnhap.setText("Tên Đăng Nhập");

		TableColumn tbclTennguoidung = new TableColumn(table, SWT.NONE);
		tbclTennguoidung.setWidth(205);
		tbclTennguoidung.setText("Tên Người Dùng");

		TableColumn tbclDonvi = new TableColumn(table, SWT.NONE);
		tbclDonvi.setWidth(220);
		tbclDonvi.setText("Đơn Vị");

		TableColumn tbclSodienthoai = new TableColumn(table, SWT.NONE);
		tbclSodienthoai.setWidth(157);
		tbclSodienthoai.setText("Số Điện Thoại");

		TableColumn tbclGhichu = new TableColumn(table, SWT.NONE);
		tbclGhichu.setWidth(185);
		tbclGhichu.setText("Ghi Chú");

		Button btnSave = new Button(composite, SWT.NONE);
		btnSave.setText("Save");
		btnSave.setImage(SWTResourceManager.getImage(DanhSachNguoiDung.class, "/manager/icon/save.png"));
		btnSave.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnSave.setBounds(1033, 76, 94, 35);

		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setText("Cancel");
		btnCancel.setImage(SWTResourceManager.getImage(DanhSachNguoiDung.class, "/manager/icon/cancel.png"));
		btnCancel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnCancel.setBounds(1133, 76, 104, 35);

		btnSave.setVisible(false);
		btnCancel.setVisible(false);

		// Thay đổi ngôn ngữ
		// -------------------------------------------------------------------------
		if (ngonngu == 0) {
			shell.setText("Danh sách người dùng");
			lbTendangnhap.setText("Tên Đăng Nhập");
			lbtennguoidung.setText("Tên Người Dùng");
			lbNhom.setText("Nhóm");
			lbDonvi.setText("Đơn Vị");
			btnSearch.setText("Tìm Kiếm");
			btnAdd.setText("Thêm");
			btnEdit.setText("Sửa");
			btnDelete.setText("Xóa");
			tbclmanguoidung.setText("Mã Người Dùng");
			tbclNhom.setText("Nhóm");
			tbclTendangnhap.setText("Tên Đăng Nhập");
			tbclTennguoidung.setText("Tên Người Dùng");
			tbclDonvi.setText("Đơn Vị");
			tbclSodienthoai.setText("Số Điện Thoại");
			tbclGhichu.setText("Ghi Chú");
			btnSave.setText("Lưu");
			btnCancel.setText("Hủy");
		} else if (ngonngu == 1) {
			shell.setText("User list");
			lbTendangnhap.setText("Username");
			lbtennguoidung.setText("Person Name");
			lbNhom.setText("Group");
			lbDonvi.setText("Department");
			btnSearch.setText("Search");
			btnAdd.setText("Add");
			btnEdit.setText("Edit");
			btnDelete.setText("Delete");
			tbclmanguoidung.setText("User ID");
			tbclNhom.setText("Group");
			tbclTendangnhap.setText("Username");
			tbclTennguoidung.setText("Person Name");
			tbclDonvi.setText("Department");
			tbclSodienthoai.setText("Phone Number");
			tbclGhichu.setText("Note");
			btnSave.setText("Save");
			btnCancel.setText("Cancel");
		} else if (ngonngu == 2) {
			shell.setText("用戶清單");
			lbTendangnhap.setText("用戶名");
			lbtennguoidung.setText("人名");
			lbNhom.setText("群組");
			lbDonvi.setText("部門");
			btnSearch.setText("搜索");
			btnAdd.setText("加");
			btnEdit.setText("編輯");
			btnDelete.setText("刪除");
			tbclmanguoidung.setText("用戶身份");
			tbclNhom.setText("群組");
			tbclTendangnhap.setText("用戶名");
			tbclTennguoidung.setText("人名");
			tbclDonvi.setText("部門");
			tbclSodienthoai.setText("電話號碼");
			tbclGhichu.setText("筆記");
			btnSave.setText("儲存");
			btnCancel.setText("取消");
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
			String select = "SELECT MaNguoiDung,NhomNguoiDung.TenNhom,TenDangNhap,TenNguoiDung,DonVi.TenDonVi,SoDienThoai,GhiChu FROM NguoiDung,NhomNguoiDung,DonVi WHERE NguoiDung.MaNhomNguoiDung=NhomNguoiDung.MaNhom AND NguoiDung.MaDonVi=DonVi.MaDonVi ORDER BY TenDangNhap ASC";
			ResultSet result = statement.executeQuery(select);
			table.removeAll();

			while (result.next()) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
						result.getString(4), result.getString(5), result.getString(6), result.getString(7) });
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

		// Button Search
		// -------------------------------------------------------------------------------------------
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String tendangnhap = textTendangnhap.getText().isEmpty() ? ""
							: " AND TenDangNhap LIKE '%" + textTendangnhap.getText() + "%'";
					String tennguoidung = textTennguoidung.getText().isEmpty() ? ""
							: " AND TenNguoiDung LIKE N'%" + textTennguoidung.getText() + "%'";
					String nhom = textNhom.getText().isEmpty() ? "" : " AND TenNhom=N'" + textNhom.getText() + "'";
					String donvi = comboDonvi.getText().isEmpty() ? ""
							: " AND TenDonVi=N'" + comboDonvi.getText() + "'";
					String select = "SELECT MaNguoiDung,NhomNguoiDung.TenNhom,TenDangNhap,TenNguoiDung,DonVi.TenDonVi,SoDienThoai,GhiChu FROM NguoiDung,NhomNguoiDung,DonVi WHERE NguoiDung.MaNhomNguoiDung=NhomNguoiDung.MaNhom AND NguoiDung.MaDonVi=DonVi.MaDonVi"
							+ tendangnhap + tennguoidung + nhom + donvi + " ORDER BY TenDangNhap ASC";
					ResultSet result = statement.executeQuery(select);
					table.removeAll();

					while (result.next()) {
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
								result.getString(4), result.getString(5), result.getString(6), result.getString(7) });
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
		// -------------------------------------------------------------------------------------------
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ThemNguoiDung them = new ThemNguoiDung();
				them.createContents(mannhomguoidung, ngonngu,db_url);
				shell.setEnabled(false);
				them.open();
				shell.setEnabled(true);

				// Cập nhật dữ liệu sau khi Add
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();
					// Lấy dữ liệu cho table
					String select = "SELECT MaNguoiDung,NhomNguoiDung.TenNhom,TenDangNhap,TenNguoiDung,DonVi.TenDonVi,SoDienThoai,GhiChu FROM NguoiDung,NhomNguoiDung,DonVi WHERE NguoiDung.MaNhomNguoiDung=NhomNguoiDung.MaNhom AND NguoiDung.MaDonVi=DonVi.MaDonVi ORDER BY TenDangNhap ASC";
					ResultSet result = statement.executeQuery(select);
					table.removeAll();

					while (result.next()) {
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
								result.getString(4), result.getString(5), result.getString(6), result.getString(7) });
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
				textNhom.setText("");
				textTendangnhap.setText("");
				textTennguoidung.setText("");
				comboDonvi.setText("");
			}
		});

		// Button Edit
		// -------------------------------------------------------------------------------------------
		btnEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (mannhomguoidung.equals("admin")) {
						TableItem[] item = table.getSelection();
						SuaNguoiDung sua = new SuaNguoiDung();
						sua.createContents(item[0].getText(), item[0].getText(1), item[0].getText(2),
								item[0].getText(3), item[0].getText(4), item[0].getText(5), item[0].getText(6),
								mannhomguoidung, ngonngu,db_url);
						shell.setEnabled(false);
						sua.open();
						shell.setEnabled(true);
					} else {
						TableItem[] item = table.getSelection();
						if (!item[0].getText(1).equals("Admin")) {
							SuaNguoiDung sua = new SuaNguoiDung();
							sua.createContents(item[0].getText(), item[0].getText(1), item[0].getText(2),
									item[0].getText(3), item[0].getText(4), item[0].getText(5), item[0].getText(6),
									mannhomguoidung, ngonngu,db_url);
							shell.setEnabled(false);
							sua.open();
							shell.setEnabled(true);
						} else {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Bạn không đủ quyền để sửa người dùng Admin!");
							} else if (ngonngu == 1) {
								thongbao.setText("Notification");
								thongbao.setMessage("You do not have sufficient privileges to edit the Admin users!");
							} else if (ngonngu == 2) {
								thongbao.setText("通知");
								thongbao.setMessage("您沒有足夠的權限來編輯管理員用戶!");
							}
							thongbao.open();
						}

					}

				} catch (Exception exc) {

				}

				// Cập nhật dữ liệu sau khi Edit
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();
					// Lấy dữ liệu cho table
					String select = "SELECT MaNguoiDung,NhomNguoiDung.TenNhom,TenDangNhap,TenNguoiDung,DonVi.TenDonVi,SoDienThoai,GhiChu FROM NguoiDung,NhomNguoiDung,DonVi WHERE NguoiDung.MaNhomNguoiDung=NhomNguoiDung.MaNhom AND NguoiDung.MaDonVi=DonVi.MaDonVi ORDER BY TenDangNhap ASC";
					ResultSet result = statement.executeQuery(select);
					table.removeAll();

					while (result.next()) {
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
								result.getString(4), result.getString(5), result.getString(6), result.getString(7) });
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
				textNhom.setText("");
				textTendangnhap.setText("");
				textTennguoidung.setText("");
				comboDonvi.setText("");
			}
		});

		// Button Delete
		// -------------------------------------------------------------------------------------------
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					TableItem[] item = table.getSelection();
					// Mã người dùng
					usercode = item[0].getText();
					if (mannhomguoidung.equals("admin")) {
						btnSave.setVisible(true);
						btnCancel.setVisible(true);
						btnAdd.setEnabled(false);
						btnEdit.setEnabled(false);
						btnSearch.setEnabled(false);
					} else {
						if (item[0].getText(1).equals("Admin")) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);

							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Bạn không đủ quyền để xóa người dùng Admin!");
							} else if (ngonngu == 1) {
								thongbao.setText("Notification");
								thongbao.setMessage("You are not authorized to delete the Admin user!");
							} else if (ngonngu == 2) {
								thongbao.setText("通知");
								thongbao.setMessage("您無權刪除管理員用戶!");
							}
							thongbao.open();
						} else {
							btnSave.setVisible(true);
							btnCancel.setVisible(true);
							btnAdd.setEnabled(false);
							btnEdit.setEnabled(false);
							btnSearch.setEnabled(false);
						}
					}

				} catch (Exception exc) {
					btnSave.setVisible(false);
					btnCancel.setVisible(false);
					btnAdd.setEnabled(true);
					btnEdit.setEnabled(true);
					btnSearch.setEnabled(true);
					usercode = "";
				}
			}
		});

		// Button Save
		// -------------------------------------------------------------------------------------------
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String delete = "DELETE NguoiDung WHERE MaNguoiDung='" + usercode + "'";
					int xoa = statement.executeUpdate(delete);
					if (xoa > 0) {
						// Lấy dữ liệu cho table
						String select = "SELECT MaNguoiDung,NhomNguoiDung.TenNhom,TenDangNhap,TenNguoiDung,DonVi.TenDonVi,SoDienThoai,GhiChu FROM NguoiDung,NhomNguoiDung,DonVi WHERE NguoiDung.MaNhomNguoiDung=NhomNguoiDung.MaNhom AND NguoiDung.MaDonVi=DonVi.MaDonVi ORDER BY TenDangNhap ASC";
						ResultSet result = statement.executeQuery(select);
						table.removeAll();

						while (result.next()) {
							TableItem item = new TableItem(table, SWT.NONE);
							item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
									result.getString(4), result.getString(5), result.getString(6),
									result.getString(7) });
						}

						result.close();
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
					} catch (SQLException se2) {
						// nothing we can do
					}
				}
			}
		});

		// Button Cancel
		// -------------------------------------------------------------------------------------------
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

	// ====================================================================================================================================

	protected void createContentsTabfolder(CTabFolder tabfolder, Shell shell, String mannhomguoidung, int ngonngu,String chuoiketnoi) {
		db_url=chuoiketnoi;
		this.mannhomguoidung = mannhomguoidung;
		this.ngonngu = ngonngu;

		CTabItem itemtab = new CTabItem(tabfolder, SWT.CLOSE);
		itemtab.setText("User list");
		Composite composite = new Composite(tabfolder, SWT.NONE);
		composite.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		itemtab.setControl(composite);

		CLabel lbTendangnhap = new CLabel(composite, SWT.NONE);
		lbTendangnhap.setAlignment(SWT.RIGHT);
		lbTendangnhap.setText("Tên Đăng Nhập");
		lbTendangnhap.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbTendangnhap.setBounds(23, 25, 117, 30);

		textTendangnhap = new Text(composite, SWT.BORDER);
		textTendangnhap.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textTendangnhap.setBounds(146, 25, 138, 30);

		CLabel lbtennguoidung = new CLabel(composite, SWT.NONE);
		lbtennguoidung.setAlignment(SWT.RIGHT);
		lbtennguoidung.setText("Tên Người Dùng");
		lbtennguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbtennguoidung.setBounds(290, 25, 140, 30);

		textTennguoidung = new Text(composite, SWT.BORDER);
		textTennguoidung.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textTennguoidung.setBounds(436, 25, 268, 30);

		CLabel lbNhom = new CLabel(composite, SWT.NONE);
		lbNhom.setAlignment(SWT.RIGHT);
		lbNhom.setText("Nhóm");
		lbNhom.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbNhom.setBounds(714, 25, 70, 30);

		textNhom = new Text(composite, SWT.BORDER);
		textNhom.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textNhom.setBounds(790, 25, 138, 30);

		CLabel lbDonvi = new CLabel(composite, SWT.NONE);
		lbDonvi.setAlignment(SWT.RIGHT);
		lbDonvi.setText("Đơn Vị");
		lbDonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbDonvi.setBounds(23, 76, 116, 30);

		CCombo comboDonvi = new CCombo(composite, SWT.BORDER);
		comboDonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		comboDonvi.setBounds(146, 76, 284, 30);

		Button btnSearch = new Button(composite, SWT.NONE);
		btnSearch.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnSearch.setImage(SWTResourceManager.getImage(DanhSachNguoiDung.class, "/manager/icon/user find.png"));
		btnSearch.setText("Search");
		btnSearch.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnSearch.setBounds(446, 76, 138, 35);

		Button btnAdd = new Button(composite, SWT.NONE);
		btnAdd.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		btnAdd.setImage(SWTResourceManager.getImage(DanhSachNguoiDung.class, "/manager/icon/user add.png"));
		btnAdd.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnAdd.setBounds(652, 76, 106, 35);
		btnAdd.setText("Add");

		Button btnEdit = new Button(composite, SWT.NONE);
		btnEdit.setForeground(SWTResourceManager.getColor(255, 69, 0));
		btnEdit.setImage(SWTResourceManager.getImage(DanhSachNguoiDung.class, "/manager/icon/user edit.png"));
		btnEdit.setText("Edit");
		btnEdit.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnEdit.setBounds(764, 76, 106, 35);

		Button btnDelete = new Button(composite, SWT.NONE);
		btnDelete.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnDelete.setImage(SWTResourceManager.getImage(DanhSachNguoiDung.class, "/manager/icon/user delete.png"));
		btnDelete.setText("Delete");
		btnDelete.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnDelete.setBounds(876, 76, 117, 35);

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderForeground(SWTResourceManager.getColor(0, 0, 0));
		table.setHeaderBackground(SWTResourceManager.getColor(255, 165, 0));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		table.setBounds(20, 125, 1231, 619);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setSize(sizemonitorx-40, sizemonitory-240);

		TableColumn tbclmanguoidung = new TableColumn(table, SWT.NONE);
		tbclmanguoidung.setWidth(137);
		tbclmanguoidung.setText("Mã Người Dùng");

		TableColumn tbclNhom = new TableColumn(table, SWT.NONE);
		tbclNhom.setWidth(100);
		tbclNhom.setText("Nhóm");

		TableColumn tbclTendangnhap = new TableColumn(table, SWT.NONE);
		tbclTendangnhap.setWidth(165);
		tbclTendangnhap.setText("Tên Đăng Nhập");

		TableColumn tbclTennguoidung = new TableColumn(table, SWT.NONE);
		tbclTennguoidung.setWidth(205);
		tbclTennguoidung.setText("Tên Người Dùng");

		TableColumn tbclDonvi = new TableColumn(table, SWT.NONE);
		tbclDonvi.setWidth(220);
		tbclDonvi.setText("Đơn Vị");

		TableColumn tbclSodienthoai = new TableColumn(table, SWT.NONE);
		tbclSodienthoai.setWidth(157);
		tbclSodienthoai.setText("Số Điện Thoại");

		TableColumn tbclGhichu = new TableColumn(table, SWT.NONE);
		tbclGhichu.setWidth(185);
		tbclGhichu.setText("Ghi Chú");

		Button btnSave = new Button(composite, SWT.NONE);
		btnSave.setText("Save");
		btnSave.setImage(SWTResourceManager.getImage(DanhSachNguoiDung.class, "/manager/icon/save.png"));
		btnSave.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnSave.setBounds(1033, 76, 94, 35);

		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setText("Cancel");
		btnCancel.setImage(SWTResourceManager.getImage(DanhSachNguoiDung.class, "/manager/icon/cancel.png"));
		btnCancel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnCancel.setBounds(1133, 76, 104, 35);

		btnSave.setVisible(false);
		btnCancel.setVisible(false);

		// Thay đổi ngôn ngữ
		// -------------------------------------------------------------------------
		if (ngonngu == 0) {
			itemtab.setText("Danh sách người dùng");
			lbTendangnhap.setText("Tên Đăng Nhập");
			lbtennguoidung.setText("Tên Người Dùng");
			lbNhom.setText("Nhóm");
			lbDonvi.setText("Đơn Vị");
			btnSearch.setText("Tìm Kiếm");
			btnAdd.setText("Thêm");
			btnEdit.setText("Sửa");
			btnDelete.setText("Xóa");
			tbclmanguoidung.setText("Mã Người Dùng");
			tbclNhom.setText("Nhóm");
			tbclTendangnhap.setText("Tên Đăng Nhập");
			tbclTennguoidung.setText("Tên Người Dùng");
			tbclDonvi.setText("Đơn Vị");
			tbclSodienthoai.setText("Số Điện Thoại");
			tbclGhichu.setText("Ghi Chú");
			btnSave.setText("Lưu");
			btnCancel.setText("Hủy");
		} else if (ngonngu == 1) {
			itemtab.setText("User list");
			lbTendangnhap.setText("Username");
			lbtennguoidung.setText("Person Name");
			lbNhom.setText("Group");
			lbDonvi.setText("Department");
			btnSearch.setText("Search");
			btnAdd.setText("Add");
			btnEdit.setText("Edit");
			btnDelete.setText("Delete");
			tbclmanguoidung.setText("User ID");
			tbclNhom.setText("Group");
			tbclTendangnhap.setText("Username");
			tbclTennguoidung.setText("Person Name");
			tbclDonvi.setText("Department");
			tbclSodienthoai.setText("Phone Number");
			tbclGhichu.setText("Note");
			btnSave.setText("Save");
			btnCancel.setText("Cancel");
		} else if (ngonngu == 2) {
			itemtab.setText("用戶清單");
			lbTendangnhap.setText("用戶名");
			lbtennguoidung.setText("人名");
			lbNhom.setText("群組");
			lbDonvi.setText("部門");
			btnSearch.setText("搜索");
			btnAdd.setText("加");
			btnEdit.setText("編輯");
			btnDelete.setText("刪除");
			tbclmanguoidung.setText("用戶身份");
			tbclNhom.setText("群組");
			tbclTendangnhap.setText("用戶名");
			tbclTennguoidung.setText("人名");
			tbclDonvi.setText("部門");
			tbclSodienthoai.setText("電話號碼");
			tbclGhichu.setText("筆記");
			btnSave.setText("儲存");
			btnCancel.setText("取消");
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
			String select = "SELECT MaNguoiDung,NhomNguoiDung.TenNhom,TenDangNhap,TenNguoiDung,DonVi.TenDonVi,SoDienThoai,GhiChu FROM NguoiDung,NhomNguoiDung,DonVi WHERE NguoiDung.MaNhomNguoiDung=NhomNguoiDung.MaNhom AND NguoiDung.MaDonVi=DonVi.MaDonVi ORDER BY TenDangNhap ASC";
			ResultSet result = statement.executeQuery(select);
			table.removeAll();

			while (result.next()) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
						result.getString(4), result.getString(5), result.getString(6), result.getString(7) });
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

		// Button Search
		// -------------------------------------------------------------------------------------------
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String tendangnhap = textTendangnhap.getText().isEmpty() ? ""
							: " AND TenDangNhap LIKE '%" + textTendangnhap.getText() + "%'";
					String tennguoidung = textTennguoidung.getText().isEmpty() ? ""
							: " AND TenNguoiDung LIKE N'%" + textTennguoidung.getText() + "%'";
					String nhom = textNhom.getText().isEmpty() ? "" : " AND TenNhom=N'" + textNhom.getText() + "'";
					String donvi = comboDonvi.getText().isEmpty() ? ""
							: " AND TenDonVi=N'" + comboDonvi.getText() + "'";
					String select = "SELECT MaNguoiDung,NhomNguoiDung.TenNhom,TenDangNhap,TenNguoiDung,DonVi.TenDonVi,SoDienThoai,GhiChu FROM NguoiDung,NhomNguoiDung,DonVi WHERE NguoiDung.MaNhomNguoiDung=NhomNguoiDung.MaNhom AND NguoiDung.MaDonVi=DonVi.MaDonVi"
							+ tendangnhap + tennguoidung + nhom + donvi + " ORDER BY TenDangNhap ASC";
					ResultSet result = statement.executeQuery(select);
					table.removeAll();

					while (result.next()) {
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
								result.getString(4), result.getString(5), result.getString(6), result.getString(7) });
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
		// -------------------------------------------------------------------------------------------
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ThemNguoiDung them = new ThemNguoiDung();
				them.createContents(mannhomguoidung, ngonngu,db_url);
				shell.setEnabled(false);
				them.open();
				shell.setEnabled(true);

				// Cập nhật dữ liệu sau khi Add
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();
					// Lấy dữ liệu cho table
					String select = "SELECT MaNguoiDung,NhomNguoiDung.TenNhom,TenDangNhap,TenNguoiDung,DonVi.TenDonVi,SoDienThoai,GhiChu FROM NguoiDung,NhomNguoiDung,DonVi WHERE NguoiDung.MaNhomNguoiDung=NhomNguoiDung.MaNhom AND NguoiDung.MaDonVi=DonVi.MaDonVi ORDER BY TenDangNhap ASC";
					ResultSet result = statement.executeQuery(select);
					table.removeAll();

					while (result.next()) {
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
								result.getString(4), result.getString(5), result.getString(6), result.getString(7) });
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
				textNhom.setText("");
				textTendangnhap.setText("");
				textTennguoidung.setText("");
				comboDonvi.setText("");
			}
		});

		// Button Edit
		// -------------------------------------------------------------------------------------------
		btnEdit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (mannhomguoidung.equals("admin")) {
						TableItem[] item = table.getSelection();
						SuaNguoiDung sua = new SuaNguoiDung();
						sua.createContents(item[0].getText(), item[0].getText(1), item[0].getText(2),
								item[0].getText(3), item[0].getText(4), item[0].getText(5), item[0].getText(6),
								mannhomguoidung, ngonngu,db_url);
						shell.setEnabled(false);
						sua.open();
						shell.setEnabled(true);
					} else {
						TableItem[] item = table.getSelection();
						if (!item[0].getText(1).equals("Admin")) {
							SuaNguoiDung sua = new SuaNguoiDung();
							sua.createContents(item[0].getText(), item[0].getText(1), item[0].getText(2),
									item[0].getText(3), item[0].getText(4), item[0].getText(5), item[0].getText(6),
									mannhomguoidung, ngonngu,db_url);
							shell.setEnabled(false);
							sua.open();
							shell.setEnabled(true);
						} else {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Bạn không đủ quyền để sửa người dùng Admin!");
							} else if (ngonngu == 1) {
								thongbao.setText("Notification");
								thongbao.setMessage("You do not have sufficient privileges to edit the Admin users!");
							} else if (ngonngu == 2) {
								thongbao.setText("通知");
								thongbao.setMessage("您沒有足夠的權限來編輯管理員用戶!");
							}
							thongbao.open();
						}

					}

				} catch (Exception exc) {

				}

				// Cập nhật dữ liệu sau khi Edit
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();
					// Lấy dữ liệu cho table
					String select = "SELECT MaNguoiDung,NhomNguoiDung.TenNhom,TenDangNhap,TenNguoiDung,DonVi.TenDonVi,SoDienThoai,GhiChu FROM NguoiDung,NhomNguoiDung,DonVi WHERE NguoiDung.MaNhomNguoiDung=NhomNguoiDung.MaNhom AND NguoiDung.MaDonVi=DonVi.MaDonVi ORDER BY TenDangNhap ASC";
					ResultSet result = statement.executeQuery(select);
					table.removeAll();

					while (result.next()) {
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
								result.getString(4), result.getString(5), result.getString(6), result.getString(7) });
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
				textNhom.setText("");
				textTendangnhap.setText("");
				textTennguoidung.setText("");
				comboDonvi.setText("");
			}
		});

		// Button Delete
		// -------------------------------------------------------------------------------------------
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					TableItem[] item = table.getSelection();
					// Mã người dùng
					usercode = item[0].getText();
					if (mannhomguoidung.equals("admin")) {
						btnSave.setVisible(true);
						btnCancel.setVisible(true);
						btnAdd.setEnabled(false);
						btnEdit.setEnabled(false);
						btnSearch.setEnabled(false);
					} else {
						if (item[0].getText(1).equals("Admin")) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);

							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Bạn không đủ quyền để xóa người dùng Admin!");
							} else if (ngonngu == 1) {
								thongbao.setText("Notification");
								thongbao.setMessage("You are not authorized to delete the Admin user!");
							} else if (ngonngu == 2) {
								thongbao.setText("通知");
								thongbao.setMessage("您無權刪除管理員用戶!");
							}
							thongbao.open();
						} else {
							btnSave.setVisible(true);
							btnCancel.setVisible(true);
							btnAdd.setEnabled(false);
							btnEdit.setEnabled(false);
							btnSearch.setEnabled(false);
						}
					}

				} catch (Exception exc) {
					btnSave.setVisible(false);
					btnCancel.setVisible(false);
					btnAdd.setEnabled(true);
					btnEdit.setEnabled(true);
					btnSearch.setEnabled(true);
					usercode = "";
				}
			}
		});

		// Button Save
		// -------------------------------------------------------------------------------------------
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String delete = "DELETE NguoiDung WHERE MaNguoiDung='" + usercode + "'";
					int xoa = statement.executeUpdate(delete);
					demxoa = 0;
					if (xoa > 0) {
						// Lấy dữ liệu cho table
						String select = "SELECT MaNguoiDung,NhomNguoiDung.TenNhom,TenDangNhap,TenNguoiDung,DonVi.TenDonVi,SoDienThoai,GhiChu FROM NguoiDung,NhomNguoiDung,DonVi WHERE NguoiDung.MaNhomNguoiDung=NhomNguoiDung.MaNhom AND NguoiDung.MaDonVi=DonVi.MaDonVi ORDER BY TenDangNhap ASC";
						ResultSet result = statement.executeQuery(select);
						table.removeAll();

						while (result.next()) {
							TableItem item = new TableItem(table, SWT.NONE);
							item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
									result.getString(4), result.getString(5), result.getString(6),
									result.getString(7) });
						}

						result.close();
						btnSave.setVisible(false);
						btnCancel.setVisible(false);
						btnAdd.setEnabled(true);
						btnEdit.setEnabled(true);
						btnSearch.setEnabled(true);
						demxoa++;
					}

				} catch (SQLException se) {
					if (demxoa == 0) {
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
						if (ngonngu == -0) {
							thongbao.setText("Thông báo");
							thongbao.setMessage("Bạn không thể xóa người dùng này!");
						} else if (ngonngu == 1) {
							thongbao.setText("Notification");
							thongbao.setMessage("You cannot delete this user!");
						} else if (ngonngu == 2) {
							thongbao.setText("通知");
							thongbao.setMessage("您不能删除该用户!");
						}
						thongbao.open();
					}
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

		// Button Cancel
		// -------------------------------------------------------------------------------------------
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
