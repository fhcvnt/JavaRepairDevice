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
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

public class DonVi {

	protected Shell shell;
	private Text textMadonvi;
	private Text textTendonvi;
	private int ngonngu = 0; // 0: Vietnam, 1: English, 2: China

	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;
	private int trangthai = 0; // 0: không làm gì, 1: thêm đơn vị mới, 2: sửa đơn vị
	private String madonvi = "";
	// Kích thước màn hình
	// private int sizemonitorx = Display.getDefault().getBounds().width;
	private int sizemonitory = Display.getDefault().getBounds().height;

	public static void main(String[] args) {
		try {
			DonVi window = new DonVi();
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
		createContents(ngonngu, db_url);
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
	protected void createContents(int ngonngu, String chuoiketnoi) {
		db_url = chuoiketnoi;
		this.ngonngu = ngonngu;
		shell = new Shell();
		shell.setSize(1250, 825);
		shell.setText("Đơn vị");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(shell, SWT.NONE);

		CLabel lbMadonvi = new CLabel(composite, SWT.NONE);
		lbMadonvi.setAlignment(SWT.RIGHT);
		lbMadonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbMadonvi.setBounds(26, 25, 133, 30);
		lbMadonvi.setText("Mã Đơn Vị");

		textMadonvi = new Text(composite, SWT.BORDER);
		textMadonvi.setBackground(SWTResourceManager.getColor(135, 206, 250));
		textMadonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textMadonvi.setBounds(165, 25, 140, 30);
		textMadonvi.setTextLimit(20);

		CLabel lbTendonvi = new CLabel(composite, SWT.NONE);
		lbTendonvi.setAlignment(SWT.RIGHT);
		lbTendonvi.setText("Tên Đơn Vị");
		lbTendonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbTendonvi.setBounds(311, 25, 143, 30);

		textTendonvi = new Text(composite, SWT.BORDER);
		textTendonvi.setBackground(SWTResourceManager.getColor(135, 206, 250));
		textTendonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textTendonvi.setBounds(460, 25, 343, 30);
		textTendonvi.setTextLimit(50);

		Button btnTimkiem = new Button(composite, SWT.NONE);
		btnTimkiem.setImage(SWTResourceManager.getImage(DonVi.class, "/manager/icon/search.png"));
		btnTimkiem.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnTimkiem.setText("Tìm Kiếm");
		btnTimkiem.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnTimkiem.setBounds(809, 25, 133, 30);

		Button btnLuu = new Button(composite, SWT.NONE);
		btnLuu.setText("Lưu");
		btnLuu.setImage(SWTResourceManager.getImage(DonVi.class, "/manager/icon/save.png"));
		btnLuu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnLuu.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnLuu.setBounds(999, 25, 83, 30);

		Button btnHuy = new Button(composite, SWT.NONE);
		btnHuy.setText("Hủy");
		btnHuy.setImage(SWTResourceManager.getImage(DonVi.class, "/manager/icon/cancel.png"));
		btnHuy.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnHuy.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnHuy.setBounds(1088, 25, 99, 30);

		Button btnThem = new Button(composite, SWT.NONE);
		btnThem.setImage(SWTResourceManager.getImage(DonVi.class, "/manager/icon/add30.png"));
		btnThem.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		btnThem.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnThem.setBounds(1025, 99, 142, 35);
		btnThem.setText("Thêm");

		Button btnSua = new Button(composite, SWT.NONE);
		btnSua.setImage(SWTResourceManager.getImage(DonVi.class, "/manager/icon/edit.png"));
		btnSua.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnSua.setText("Sửa");
		btnSua.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnSua.setBounds(1025, 159, 142, 35);

		Button btnXoa = new Button(composite, SWT.NONE);
		btnXoa.setImage(SWTResourceManager.getImage(DonVi.class, "/manager/icon/delete.png"));
		btnXoa.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnXoa.setText("Xóa");
		btnXoa.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnXoa.setBounds(1025, 220, 142, 35);

		Table table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		table.setHeaderBackground(SWTResourceManager.getColor(255, 165, 0));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		table.setBounds(26, 71, 971, 690);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tbclMadonvi = new TableColumn(table, SWT.LEFT);
		tbclMadonvi.setWidth(317);
		tbclMadonvi.setText("Mã Đơn Vị");

		TableColumn tbclTendonvi = new TableColumn(table, SWT.LEFT);
		tbclTendonvi.setWidth(494);
		tbclTendonvi.setText("Tên Đơn Vị");

		// ẩn button Lưu, Hủy
		btnLuu.setVisible(false);
		btnHuy.setVisible(false);

		// Thay đổi ngôn ngữ
		// -------------------------------------------------------------------------
		if (ngonngu == 0) {
			shell.setText("Đơn vị");
			lbMadonvi.setText("Mã Đơn Vị");
			lbTendonvi.setText("Tên Đơn Vị");
			btnTimkiem.setText("Tìm Kiếm");
			btnLuu.setText("Lưu");
			btnHuy.setText("Hủy");
			btnThem.setText("Thêm");
			btnSua.setText("Sửa");
			btnXoa.setText("Xóa");
			tbclMadonvi.setText("Mã Đơn Vị");
			tbclTendonvi.setText("Tên Đơn Vị");
		} else if (ngonngu == 1) {
			shell.setText("Department");
			lbMadonvi.setText("Department Code");
			lbTendonvi.setText("Department Name");
			btnTimkiem.setText("Search");
			btnLuu.setText("Save");
			btnHuy.setText("Cancel");
			btnThem.setText("Add");
			btnSua.setText("Edit");
			btnXoa.setText("Delete");
			tbclMadonvi.setText("Department Code");
			tbclTendonvi.setText("Department Name");
		} else if (ngonngu == 2) {
			shell.setText("部門");
			lbMadonvi.setText("部門代碼");
			lbTendonvi.setText("部門名稱");
			btnTimkiem.setText("搜索");
			btnLuu.setText("儲存");
			btnHuy.setText("取消");
			btnThem.setText("加");
			btnSua.setText("編輯");
			btnXoa.setText("刪除");
			tbclMadonvi.setText("部門代碼");
			tbclTendonvi.setText("部門名稱");
		}

		// Xu ly du lieu luc dau --------------------------------------------------
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			String select = "SELECT MaDonVi,TenDonVi FROM DonVi ORDER BY TenDonVi ASC";
			ResultSet result = statement.executeQuery(select);
			table.removeAll();

			while (result.next()) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] { result.getString(1), result.getString(2) });
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
		// -------------------------------------------------------------------
		btnTimkiem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String madonvi = textMadonvi.getText().isEmpty() ? " WHERE MaDonVi LIKE '%'"
						: " WHERE MaDonVi LIKE '%" + textMadonvi.getText() + "%'";
				String tendonvi = textTendonvi.getText().isEmpty() ? ""
						: " AND TenDonVi LIKE '%" + textTendonvi.getText() + "%'";

				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String select = "SELECT MaDonVi,TenDonVi FROM DonVi" + madonvi + tendonvi
							+ " ORDER BY TenDonVi ASC";
					ResultSet result = statement.executeQuery(select);
					table.removeAll();

					while (result.next()) {
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(new String[] { result.getString(1), result.getString(2) });
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

		// Button Thêm
		// -----------------------------------------------------------------------------------------------
		btnThem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textMadonvi.setText("");
				textTendonvi.setText("");
				trangthai = 1;
				btnLuu.setVisible(true);
				btnHuy.setVisible(true);
				btnTimkiem.setEnabled(false);
				btnSua.setEnabled(false);
				btnXoa.setEnabled(false);
			}
		});

		// Button Sửa
		// -----------------------------------------------------------------------------------------------
		btnSua.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					TableItem[] item = table.getSelection();
					textMadonvi.setText(item[0].getText());
					textTendonvi.setText(item[0].getText(1));
					madonvi = item[0].getText();
					trangthai = 2;
					btnLuu.setVisible(true);
					btnHuy.setVisible(true);
					btnTimkiem.setEnabled(false);
					btnThem.setEnabled(false);
					btnXoa.setEnabled(false);
				} catch (Exception exc) {
					btnLuu.setVisible(false);
					btnHuy.setVisible(false);
					trangthai = 0;
					btnTimkiem.setEnabled(true);
					btnThem.setEnabled(true);
					btnXoa.setEnabled(true);
				}
			}
		});

		// Button Xóa
		// -----------------------------------------------------------------------------------------------
		btnXoa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					TableItem[] item = table.getSelection();
					madonvi = item[0].getText();
					String delete = "DELETE FROM DonVi WHERE MaDonVi='" + madonvi + "'";
					int result = statement.executeUpdate(delete);
					if (result > 0) {
						table.remove(table.getSelectionIndices());
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

		// Button Lưu
		// -----------------------------------------------------------------------------------------------
		btnLuu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					if (trangthai == 1 && !textMadonvi.getText().isEmpty() && !textTendonvi.getText().isEmpty()) {
						// Thêm
						String insert = "INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( '" + textMadonvi.getText()
								+ "', N'" + textTendonvi.getText() + "' )";
						int ketqua = statement.executeUpdate(insert);
						if (ketqua > 0) {
							TableItem item = new TableItem(table, SWT.NONE);
							item.setText(new String[] { textMadonvi.getText(), textTendonvi.getText() });
							textMadonvi.setText("");
							textTendonvi.setText("");
						}
					} else if (trangthai == 2) {
						// Sửa
						String update = "UPDATE DonVi SET MaDonVi='" + textMadonvi.getText() + "',TenDonVi=N'"
								+ textTendonvi.getText() + "' WHERE MaDonVi='" + madonvi + "'";
						int ketqua = statement.executeUpdate(update);
						if (ketqua > 0) {
							String select = "SELECT MaDonVi,TenDonVi FROM DonVi ORDER BY TenDonVi ASC";
							ResultSet result = statement.executeQuery(select);
							table.removeAll();

							while (result.next()) {
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { result.getString(1), result.getString(2) });
							}

							result.close();
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
					} catch (SQLException se2) {
						// nothing we can do
					}
				}
			}
		});

		// Button Hủy
		// -----------------------------------------------------------------------------------------------
		btnHuy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnLuu.setVisible(false);
				btnHuy.setVisible(false);
				textMadonvi.setText("");
				textTendonvi.setText("");
				trangthai = 0;
				btnTimkiem.setEnabled(true);
				btnThem.setEnabled(true);
				btnSua.setEnabled(true);
				btnXoa.setEnabled(true);
			}
		});

		// Mã đơn vị chỉ cho phép nhập theo chuẩn ASCII (Không cho nhập Unicode)
		textMadonvi.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9' || ('a' <= chars[i] && chars[i] <= 'z'))) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}

	// Hiện trong Ctabfolder
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	protected void createContentsTabfolder(CTabFolder tabfolder, Shell shell, int ngonngu, String chuoiketnoi) {
		db_url = chuoiketnoi;
		CTabItem itemtab = new CTabItem(tabfolder, SWT.CLOSE);
		itemtab.setText("Department");
		Composite composite = new Composite(tabfolder, SWT.NONE);
		itemtab.setControl(composite);

		CLabel lbMadonvi = new CLabel(composite, SWT.NONE);
		lbMadonvi.setAlignment(SWT.RIGHT);
		lbMadonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbMadonvi.setBounds(26, 25, 133, 30);
		lbMadonvi.setText("Mã Đơn Vị");

		textMadonvi = new Text(composite, SWT.BORDER);
		textMadonvi.setBackground(SWTResourceManager.getColor(135, 206, 250));
		textMadonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textMadonvi.setBounds(165, 25, 140, 30);
		textMadonvi.setTextLimit(20);

		CLabel lbTendonvi = new CLabel(composite, SWT.NONE);
		lbTendonvi.setAlignment(SWT.RIGHT);
		lbTendonvi.setText("Tên Đơn Vị");
		lbTendonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbTendonvi.setBounds(311, 25, 143, 30);

		textTendonvi = new Text(composite, SWT.BORDER);
		textTendonvi.setBackground(SWTResourceManager.getColor(135, 206, 250));
		textTendonvi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textTendonvi.setBounds(460, 25, 343, 30);
		textTendonvi.setTextLimit(50);

		Button btnTimkiem = new Button(composite, SWT.NONE);
		btnTimkiem.setImage(SWTResourceManager.getImage(DonVi.class, "/manager/icon/search.png"));
		btnTimkiem.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnTimkiem.setText("Tìm Kiếm");
		btnTimkiem.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnTimkiem.setBounds(809, 25, 133, 30);

		Button btnLuu = new Button(composite, SWT.NONE);
		btnLuu.setText("Lưu");
		btnLuu.setImage(SWTResourceManager.getImage(DonVi.class, "/manager/icon/save.png"));
		btnLuu.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnLuu.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnLuu.setBounds(999, 25, 83, 30);

		Button btnHuy = new Button(composite, SWT.NONE);
		btnHuy.setText("Hủy");
		btnHuy.setImage(SWTResourceManager.getImage(DonVi.class, "/manager/icon/cancel.png"));
		btnHuy.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnHuy.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnHuy.setBounds(1088, 25, 99, 30);

		Button btnThem = new Button(composite, SWT.NONE);
		btnThem.setImage(SWTResourceManager.getImage(DonVi.class, "/manager/icon/add30.png"));
		btnThem.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		btnThem.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnThem.setBounds(1025, 99, 142, 35);
		btnThem.setText("Thêm");

		Button btnSua = new Button(composite, SWT.NONE);
		btnSua.setImage(SWTResourceManager.getImage(DonVi.class, "/manager/icon/edit.png"));
		btnSua.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnSua.setText("Sửa");
		btnSua.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnSua.setBounds(1025, 159, 142, 35);

		Button btnXoa = new Button(composite, SWT.NONE);
		btnXoa.setImage(SWTResourceManager.getImage(DonVi.class, "/manager/icon/delete.png"));
		btnXoa.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnXoa.setText("Xóa");
		btnXoa.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnXoa.setBounds(1025, 220, 142, 35);

		Table table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		table.setHeaderBackground(SWTResourceManager.getColor(255, 165, 0));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		table.setBounds(26, 71, 971, 690);
		table.setSize(971, sizemonitory - 200);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tbclMadonvi = new TableColumn(table, SWT.LEFT);
		tbclMadonvi.setWidth(317);
		tbclMadonvi.setText("Mã Đơn Vị");

		TableColumn tbclTendonvi = new TableColumn(table, SWT.LEFT);
		tbclTendonvi.setWidth(494);
		tbclTendonvi.setText("Tên Đơn Vị");

		// ẩn button Lưu, Hủy
		btnLuu.setVisible(false);
		btnHuy.setVisible(false);

		// Thay đổi ngôn ngữ
		// -------------------------------------------------------------------------
		if (ngonngu == 0) {
			itemtab.setText("Đơn vị");
			lbMadonvi.setText("Mã Đơn Vị");
			lbTendonvi.setText("Tên Đơn Vị");
			btnTimkiem.setText("Tìm Kiếm");
			btnLuu.setText("Lưu");
			btnHuy.setText("Hủy");
			btnThem.setText("Thêm");
			btnSua.setText("Sửa");
			btnXoa.setText("Xóa");
			tbclMadonvi.setText("Mã Đơn Vị");
			tbclTendonvi.setText("Tên Đơn Vị");
		} else if (ngonngu == 1) {
			itemtab.setText("Department");
			lbMadonvi.setText("Department Code");
			lbTendonvi.setText("Department Name");
			btnTimkiem.setText("Search");
			btnLuu.setText("Save");
			btnHuy.setText("Cancel");
			btnThem.setText("Add");
			btnSua.setText("Edit");
			btnXoa.setText("Delete");
			tbclMadonvi.setText("Department Code");
			tbclTendonvi.setText("Department Name");
		} else if (ngonngu == 2) {
			itemtab.setText("部門");
			lbMadonvi.setText("部門代碼");
			lbTendonvi.setText("部門名稱");
			btnTimkiem.setText("搜索");
			btnLuu.setText("儲存");
			btnHuy.setText("取消");
			btnThem.setText("加");
			btnSua.setText("編輯");
			btnXoa.setText("刪除");
			tbclMadonvi.setText("部門代碼");
			tbclTendonvi.setText("部門名稱");
		}

		// Xu ly du lieu luc dau --------------------------------------------------
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			String select = "SELECT MaDonVi,TenDonVi FROM DonVi ORDER BY TenDonVi ASC";
			ResultSet result = statement.executeQuery(select);
			table.removeAll();

			while (result.next()) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] { result.getString(1), result.getString(2) });
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
		// -------------------------------------------------------------------
		btnTimkiem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String madonvi = textMadonvi.getText().isEmpty() ? " WHERE MaDonVi LIKE '%'"
						: " WHERE MaDonVi LIKE '%" + textMadonvi.getText() + "%'";
				String tendonvi = textTendonvi.getText().isEmpty() ? ""
						: " AND TenDonVi LIKE '%" + textTendonvi.getText() + "%'";

				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					String select = "SELECT MaDonVi,TenDonVi FROM DonVi" + madonvi + tendonvi
							+ " ORDER BY TenDonVi ASC";
					ResultSet result = statement.executeQuery(select);
					table.removeAll();

					while (result.next()) {
						TableItem item = new TableItem(table, SWT.NONE);
						item.setText(new String[] { result.getString(1), result.getString(2) });
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

		// Button Thêm
		// -----------------------------------------------------------------------------------------------
		btnThem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textMadonvi.setText("");
				textTendonvi.setText("");
				trangthai = 1;
				btnLuu.setVisible(true);
				btnHuy.setVisible(true);
				btnTimkiem.setEnabled(false);
				btnSua.setEnabled(false);
				btnXoa.setEnabled(false);
			}
		});

		// Button Sửa
		// -----------------------------------------------------------------------------------------------
		btnSua.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					TableItem[] item = table.getSelection();
					textMadonvi.setText(item[0].getText());
					textTendonvi.setText(item[0].getText(1));
					madonvi = item[0].getText();
					trangthai = 2;
					btnLuu.setVisible(true);
					btnHuy.setVisible(true);
					btnTimkiem.setEnabled(false);
					btnThem.setEnabled(false);
					btnXoa.setEnabled(false);
				} catch (Exception exc) {
					btnLuu.setVisible(false);
					btnHuy.setVisible(false);
					trangthai = 0;
					btnTimkiem.setEnabled(true);
					btnThem.setEnabled(true);
					btnXoa.setEnabled(true);
				}
			}
		});

		// Button Xóa
		// -----------------------------------------------------------------------------------------------
		btnXoa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					TableItem[] item = table.getSelection();
					madonvi = item[0].getText();
					String delete = "DELETE FROM DonVi WHERE MaDonVi='" + madonvi + "'";
					int result = statement.executeUpdate(delete);
					if (result > 0) {
						table.remove(table.getSelectionIndices());
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

		// Button Lưu
		// -----------------------------------------------------------------------------------------------
		btnLuu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					if (trangthai == 1 && !textMadonvi.getText().isEmpty() && !textTendonvi.getText().isEmpty()) {
						// Thêm
						String insert = "INSERT INTO DonVi ( MaDonVi, TenDonVi ) VALUES  ( '" + textMadonvi.getText()
								+ "', N'" + textTendonvi.getText() + "' )";
						int ketqua = statement.executeUpdate(insert);
						if (ketqua > 0) {
							TableItem item = new TableItem(table, SWT.NONE);
							item.setText(new String[] { textMadonvi.getText(), textTendonvi.getText() });
							textMadonvi.setText("");
							textTendonvi.setText("");
						}
					} else if (trangthai == 2) {
						// Sửa
						String update = "UPDATE DonVi SET MaDonVi='" + textMadonvi.getText() + "',TenDonVi=N'"
								+ textTendonvi.getText() + "' WHERE MaDonVi='" + madonvi + "'";
						int ketqua = statement.executeUpdate(update);
						if (ketqua > 0) {
							String select = "SELECT MaDonVi,TenDonVi FROM DonVi ORDER BY TenDonVi ASC";
							ResultSet result = statement.executeQuery(select);
							table.removeAll();

							while (result.next()) {
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { result.getString(1), result.getString(2) });
							}

							result.close();
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
					} catch (SQLException se2) {
						// nothing we can do
					}
				}
			}
		});

		// Button Hủy
		// -----------------------------------------------------------------------------------------------
		btnHuy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnLuu.setVisible(false);
				btnHuy.setVisible(false);
				textMadonvi.setText("");
				textTendonvi.setText("");
				trangthai = 0;
				btnTimkiem.setEnabled(true);
				btnThem.setEnabled(true);
				btnSua.setEnabled(true);
				btnXoa.setEnabled(true);
			}
		});

		// Mã đơn vị chỉ cho phép nhập theo chuẩn ASCII (Không cho nhập Unicode)
		textMadonvi.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9' || ('a' <= chars[i] && chars[i] <= 'z'))) {
						e.doit = false;
						return;
					}
				}
			}
		});

	}
}
