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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class DanhSachTrucPhong {

	protected Shell shell;
	private Text textSothe;
	private Text textHoten;
	private Table table;
	private int ngonngu = 0; // 0: Vietnam, 1: English, 2: China

	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;
	private int trangthai = 0; // 0: Add, 2: Delete
	private String sothe = "";
	// Kích thước màn hình
	private int sizemonitorx = Display.getDefault().getBounds().width;
	private int sizemonitory = Display.getDefault().getBounds().height;

	public static void main(String[] args) {
		try {
			DanhSachTrucPhong window = new DanhSachTrucPhong();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		Display display = Display.getDefault();
		createContents(ngonngu,db_url);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	// ====================================================================================================================================
	protected void createContents(int ngonngu,String chuoiketnoi) {
		db_url=chuoiketnoi;
		this.ngonngu = ngonngu;
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage(DanhSachTrucPhong.class, "/manager/Images/repair256.ico"));
		shell.setSize(1293, 808);
		shell.setText("Danh sách người trực phòng");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setMaximized(true);

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		CLabel lbSothe = new CLabel(composite, SWT.NONE);
		lbSothe.setAlignment(SWT.RIGHT);
		lbSothe.setText("Số Thẻ");
		lbSothe.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbSothe.setBounds(23, 25, 68, 30);

		textSothe = new Text(composite, SWT.BORDER);
		textSothe.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textSothe.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textSothe.setBounds(97, 25, 106, 30);
		textSothe.setTextLimit(6);

		CLabel lbHoten = new CLabel(composite, SWT.NONE);
		lbHoten.setAlignment(SWT.RIGHT);
		lbHoten.setText("Họ Tên");
		lbHoten.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbHoten.setBounds(209, 25, 88, 30);

		textHoten = new Text(composite, SWT.BORDER);
		textHoten.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		textHoten.setEnabled(false);
		textHoten.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textHoten.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textHoten.setBounds(303, 25, 238, 30);
		textHoten.setTextLimit(50);

		Button btnAdd = new Button(composite, SWT.NONE);
		btnAdd.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		btnAdd.setImage(SWTResourceManager.getImage(DanhSachTrucPhong.class, "/manager/icon/add30.png"));
		btnAdd.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnAdd.setBounds(547, 25, 106, 30);
		btnAdd.setText("Add");

		Button btnDelete = new Button(composite, SWT.NONE);
		btnDelete.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnDelete.setImage(SWTResourceManager.getImage(DanhSachTrucPhong.class, "/manager/icon/delete.png"));
		btnDelete.setText("Delete");
		btnDelete.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnDelete.setBounds(659, 25, 117, 30);

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderForeground(SWTResourceManager.getColor(0, 0, 0));
		table.setHeaderBackground(SWTResourceManager.getColor(233, 150, 122));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		table.setBounds(23, 72, 1163, 619);
		table.setSize(sizemonitorx - 50, sizemonitory - 200);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tbclSothe = new TableColumn(table, SWT.NONE);
		tbclSothe.setWidth(162);
		tbclSothe.setText("Số Thẻ");

		TableColumn tbclHoten = new TableColumn(table, SWT.NONE);
		tbclHoten.setWidth(441);
		tbclHoten.setText("Họ Tên");

		Button btnSave = new Button(composite, SWT.NONE);
		btnSave.setText("Save");
		btnSave.setImage(SWTResourceManager.getImage(DanhSachTrucPhong.class, "/manager/icon/save.png"));
		btnSave.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnSave.setBounds(796, 25, 94, 30);

		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setText("Cancel");
		btnCancel.setImage(SWTResourceManager.getImage(DanhSachTrucPhong.class, "/manager/icon/cancel.png"));
		btnCancel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnCancel.setBounds(896, 25, 104, 30);

		btnSave.setVisible(false);
		btnCancel.setVisible(false);

		// Thay đổi ngôn ngữ
		// -------------------------------------------------------------------------
		if (ngonngu == 0) {
			shell.setText("Danh sách người trực phòng");
			lbSothe.setText("Số Thẻ");
			lbHoten.setText("Họ Tên");
			btnAdd.setText("Thêm");
			btnDelete.setText("Xóa");
			tbclSothe.setText("Số Thẻ");
			tbclHoten.setText("Họ Tên");
			btnSave.setText("Lưu");
			btnCancel.setText("Hủy");
		} else if (ngonngu == 1) {
			shell.setText("List of people on duty");
			lbSothe.setText("Code");
			lbHoten.setText("Full Name");
			btnAdd.setText("Add");
			btnDelete.setText("Delete");
			tbclSothe.setText("Code");
			tbclHoten.setText("Full Name");
			btnSave.setText("Save");
			btnCancel.setText("Cancel");
		} else if (ngonngu == 2) {
			shell.setText("值班人員名單");
			lbSothe.setText("碼");
			lbHoten.setText("全名");
			btnAdd.setText("加");
			btnDelete.setText("刪除");
			tbclSothe.setText("碼");
			tbclHoten.setText("全名");
			btnSave.setText("儲存");
			btnCancel.setText("取消");
		}

		// Số thẻ chỉ cho nhập số
		textSothe.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		});

		// Xu ly du lieu luc dau
		// -----------------------------------------------------------------------------------------
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			// Lấy dữ liệu cho table
			String select = "SELECT SoThe,TenNguoiDung FROM DanhSachTrucPhong,NguoiDung WHERE DanhSachTrucPhong.SoThe=NguoiDung.MaNguoiDung ORDER BY SoThe ASC";
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
		
		// Lấy dữ liệu cho Text Họ tên khi text Số thẻ thay đổi
		// -----------------------------------------------------------------------------------------------------------------------
		textSothe.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					// Lấy dữ liệu cho text Ho Ten
					String select = "SELECT TenNguoiDung FROM NguoiDung WHERE MaNhomNguoiDung='it' AND MaNguoiDung='"+textSothe.getText()+"'";
					ResultSet result = statement.executeQuery(select);
					int dem=0;
					while (result.next()) {
						textHoten.setText(result.getString(1));
						dem++;
					}
					if(dem==0) {
						textHoten.setText("");
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
				if(!textHoten.getText().isEmpty()) {
					btnSave.setVisible(true);
					btnCancel.setVisible(true);
					btnDelete.setEnabled(false);
					trangthai = 0;
					textSothe.setEditable(false);
				}
			}
		});

		// Button Delete
		// -------------------------------------------------------------------------------------------
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					TableItem[] item = table.getSelection();
					sothe = item[0].getText();
					textSothe.setText(item[0].getText());
					textHoten.setText(item[0].getText(1));
					btnSave.setVisible(true);
					btnCancel.setVisible(true);
					btnAdd.setEnabled(false);
					trangthai = 2;
					textSothe.setEnabled(false);
					textHoten.setEnabled(false);
				} catch (Exception exc) {
					btnSave.setVisible(false);
					btnCancel.setVisible(false);
					btnAdd.setEnabled(true);
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

					if (trangthai == 0) {
						// Add
						if (!textSothe.getText().isEmpty()) {
							try {
								String insert = "INSERT INTO DanhSachTrucPhong ( SoThe ) VALUES  ( '"
										+ textSothe.getText() + "' )";
								int them = statement.executeUpdate(insert);
								if (them > 0) {
									btnSave.setVisible(false);
									btnCancel.setVisible(false);
									btnDelete.setEnabled(true);
									textHoten.setText("");
									textSothe.setText("");
									textSothe.setEditable(true);
									// Cập nhật lại dữ liệu cho table
									conn = DriverManager.getConnection(db_url);
									statement = conn.createStatement();
									String select = "SELECT SoThe,TenNguoiDung FROM DanhSachTrucPhong,NguoiDung WHERE DanhSachTrucPhong.SoThe=NguoiDung.MaNguoiDung ORDER BY SoThe ASC";
									ResultSet result = statement.executeQuery(select);
									table.removeAll();

									while (result.next()) {
										TableItem item = new TableItem(table, SWT.NONE);
										item.setText(new String[] { result.getString(1), result.getString(2) });
									}

									result.close();
								}
							} catch (SQLException se) {
								MessageBox thongbao = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
								if (ngonngu == 0) {
									thongbao.setText("Thông báo");
									thongbao.setMessage("Số thẻ bị trùng!");
								} else if (ngonngu == 1) {
									thongbao.setText("Notification");
									thongbao.setMessage("Duplicate code!");
								} else if (ngonngu == 2) {
									thongbao.setText("通知");
									thongbao.setMessage("重複的代碼");
								}
								thongbao.open();
							}
						} else {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Số thẻ không được bỏ trống!");
							} else if (ngonngu == 1) {
								thongbao.setText("Notification");
								thongbao.setMessage("Code must not be blank!");
							} else if (ngonngu == 2) {
								thongbao.setText("通知");
								thongbao.setMessage("代碼不能為空");
							}
							thongbao.open();
						}
					} else if (trangthai == 2) {
						// Delete
						String delete = "DELETE DanhSachTrucPhong WHERE SoThe='" + sothe + "'";
						int ketquaxoa = statement.executeUpdate(delete);
						if (ketquaxoa > 0) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Xóa thành công!");
							} else if (ngonngu == 1) {
								thongbao.setText("Notification");
								thongbao.setMessage("Delete successful!");
							} else if (ngonngu == 2) {
								thongbao.setText("通知");
								thongbao.setMessage("刪除成功");
							}
							thongbao.open();
							btnSave.setVisible(false);
							btnCancel.setVisible(false);
							btnAdd.setEnabled(true);
							btnDelete.setEnabled(true);
							textHoten.setText("");
							textSothe.setText("");
							textSothe.setEnabled(true);
							// Cập nhật lại dữ liệu cho table
							conn = DriverManager.getConnection(db_url);
							statement = conn.createStatement();
							String select = "SELECT SoThe,TenNguoiDung FROM DanhSachTrucPhong,NguoiDung WHERE DanhSachTrucPhong.SoThe=NguoiDung.MaNguoiDung ORDER BY SoThe ASC";
							ResultSet result = statement.executeQuery(select);
							table.removeAll();

							while (result.next()) {
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { result.getString(1), result.getString(2) });
							}

							result.close();
						}
					}

				} catch (SQLException sqe) {

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
				btnDelete.setEnabled(true);
				textHoten.setText("");
				textSothe.setText("");
				textSothe.setEnabled(true);
				textSothe.setEditable(true);
			}
		});

	}

	// hien trong CtabFolder
	// ====================================================================================================================================
	protected void createContentsTabfolder(CTabFolder tabfolder, Shell shell, int ngonngu,String chuoiketnoi) {
		db_url=chuoiketnoi;
		this.ngonngu = ngonngu;

		CTabItem itemtab = new CTabItem(tabfolder, SWT.CLOSE);
		itemtab.setText("Danh sách người trực phòng");
		Composite composite = new Composite(tabfolder, SWT.NONE);
		itemtab.setControl(composite);
		composite.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		CLabel lbSothe = new CLabel(composite, SWT.NONE);
		lbSothe.setAlignment(SWT.RIGHT);
		lbSothe.setText("Số Thẻ");
		lbSothe.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbSothe.setBounds(23, 25, 68, 30);

		textSothe = new Text(composite, SWT.BORDER);
		textSothe.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textSothe.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textSothe.setBounds(97, 25, 106, 30);
		textSothe.setTextLimit(6);

		CLabel lbHoten = new CLabel(composite, SWT.NONE);
		lbHoten.setAlignment(SWT.RIGHT);
		lbHoten.setText("Họ Tên");
		lbHoten.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbHoten.setBounds(209, 25, 88, 30);

		textHoten = new Text(composite, SWT.BORDER);
		textHoten.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		textHoten.setEnabled(false);
		textHoten.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textHoten.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		textHoten.setBounds(303, 25, 238, 30);
		textHoten.setTextLimit(50);

		Button btnAdd = new Button(composite, SWT.NONE);
		btnAdd.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		btnAdd.setImage(SWTResourceManager.getImage(DanhSachTrucPhong.class, "/manager/icon/add30.png"));
		btnAdd.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnAdd.setBounds(547, 25, 106, 30);
		btnAdd.setText("Add");

		Button btnDelete = new Button(composite, SWT.NONE);
		btnDelete.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnDelete.setImage(SWTResourceManager.getImage(DanhSachTrucPhong.class, "/manager/icon/delete.png"));
		btnDelete.setText("Delete");
		btnDelete.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnDelete.setBounds(659, 25, 117, 30);

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderForeground(SWTResourceManager.getColor(0, 0, 0));
		table.setHeaderBackground(SWTResourceManager.getColor(233, 150, 122));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		table.setBounds(23, 72, 1163, 619);
		table.setSize(sizemonitorx - 50, sizemonitory - 200);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tbclSothe = new TableColumn(table, SWT.NONE);
		tbclSothe.setWidth(162);
		tbclSothe.setText("Số Thẻ");

		TableColumn tbclHoten = new TableColumn(table, SWT.NONE);
		tbclHoten.setWidth(441);
		tbclHoten.setText("Họ Tên");

		Button btnSave = new Button(composite, SWT.NONE);
		btnSave.setText("Save");
		btnSave.setImage(SWTResourceManager.getImage(DanhSachTrucPhong.class, "/manager/icon/save.png"));
		btnSave.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnSave.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnSave.setBounds(796, 25, 94, 30);

		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setText("Cancel");
		btnCancel.setImage(SWTResourceManager.getImage(DanhSachTrucPhong.class, "/manager/icon/cancel.png"));
		btnCancel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		btnCancel.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnCancel.setBounds(896, 25, 104, 30);

		btnSave.setVisible(false);
		btnCancel.setVisible(false);

		// Thay đổi ngôn ngữ
		// -------------------------------------------------------------------------
		if (ngonngu == 0) {
			itemtab.setText("Danh sách người trực phòng");
			lbSothe.setText("Số Thẻ");
			lbHoten.setText("Họ Tên");
			btnAdd.setText("Thêm");
			btnDelete.setText("Xóa");
			tbclSothe.setText("Số Thẻ");
			tbclHoten.setText("Họ Tên");
			btnSave.setText("Lưu");
			btnCancel.setText("Hủy");
		} else if (ngonngu == 1) {
			itemtab.setText("List of people on duty");
			lbSothe.setText("Code");
			lbHoten.setText("Full Name");
			btnAdd.setText("Add");
			btnDelete.setText("Delete");
			tbclSothe.setText("Code");
			tbclHoten.setText("Full Name");
			btnSave.setText("Save");
			btnCancel.setText("Cancel");
		} else if (ngonngu == 2) {
			itemtab.setText("值班人員名單");
			lbSothe.setText("碼");
			lbHoten.setText("全名");
			btnAdd.setText("加");
			btnDelete.setText("刪除");
			tbclSothe.setText("碼");
			tbclHoten.setText("全名");
			btnSave.setText("儲存");
			btnCancel.setText("取消");
		}

		// Số thẻ chỉ cho nhập số
		textSothe.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		});

		// Xu ly du lieu luc dau
		// -----------------------------------------------------------------------------------------
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			// Lấy dữ liệu cho table
			String select = "SELECT SoThe,TenNguoiDung FROM DanhSachTrucPhong,NguoiDung WHERE DanhSachTrucPhong.SoThe=NguoiDung.MaNguoiDung ORDER BY SoThe ASC";
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
		
		// Lấy dữ liệu cho Text Họ tên khi text Số thẻ thay đổi
		// -----------------------------------------------------------------------------------------------------------------------
		textSothe.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					// Lấy dữ liệu cho text Ho Ten
					String select = "SELECT TenNguoiDung FROM NguoiDung WHERE MaNhomNguoiDung='it' AND MaNguoiDung='"+textSothe.getText()+"'";
					ResultSet result = statement.executeQuery(select);
					int dem=0;
					while (result.next()) {
						textHoten.setText(result.getString(1));
						dem++;
					}
					if(dem==0) {
						textHoten.setText("");
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
				if(!textHoten.getText().isEmpty()) {
					btnSave.setVisible(true);
					btnCancel.setVisible(true);
					btnDelete.setEnabled(false);
					trangthai = 0;
					textSothe.setEditable(false);
				}
			}
		});

		// Button Delete
		// -------------------------------------------------------------------------------------------
		btnDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					TableItem[] item = table.getSelection();
					sothe = item[0].getText();
					textSothe.setText(item[0].getText());
					textHoten.setText(item[0].getText(1));
					btnSave.setVisible(true);
					btnCancel.setVisible(true);
					btnAdd.setEnabled(false);
					trangthai = 2;
					textSothe.setEnabled(false);
					textHoten.setEnabled(false);
				} catch (Exception exc) {
					btnSave.setVisible(false);
					btnCancel.setVisible(false);
					btnAdd.setEnabled(true);
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

					if (trangthai == 0) {
						// Add
						if (!textSothe.getText().isEmpty()) {
							try {
								String insert = "INSERT INTO DanhSachTrucPhong ( SoThe ) VALUES  ( '"
										+ textSothe.getText() + "' )";
								int them = statement.executeUpdate(insert);
								if (them > 0) {
									btnSave.setVisible(false);
									btnCancel.setVisible(false);
									btnDelete.setEnabled(true);
									textHoten.setText("");
									textSothe.setText("");
									textSothe.setEditable(true);
									// Cập nhật lại dữ liệu cho table
									conn = DriverManager.getConnection(db_url);
									statement = conn.createStatement();
									String select = "SELECT SoThe,TenNguoiDung FROM DanhSachTrucPhong,NguoiDung WHERE DanhSachTrucPhong.SoThe=NguoiDung.MaNguoiDung ORDER BY SoThe ASC";
									ResultSet result = statement.executeQuery(select);
									table.removeAll();

									while (result.next()) {
										TableItem item = new TableItem(table, SWT.NONE);
										item.setText(new String[] { result.getString(1), result.getString(2) });
									}

									result.close();
								}
							} catch (SQLException se) {
								MessageBox thongbao = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
								if (ngonngu == 0) {
									thongbao.setText("Thông báo");
									thongbao.setMessage("Số thẻ bị trùng!");
								} else if (ngonngu == 1) {
									thongbao.setText("Notification");
									thongbao.setMessage("Duplicate code!");
								} else if (ngonngu == 2) {
									thongbao.setText("通知");
									thongbao.setMessage("重複的代碼");
								}
								thongbao.open();
							}
						} else {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Số thẻ không được bỏ trống!");
							} else if (ngonngu == 1) {
								thongbao.setText("Notification");
								thongbao.setMessage("Code must not be blank!");
							} else if (ngonngu == 2) {
								thongbao.setText("通知");
								thongbao.setMessage("代碼不能為空");
							}
							thongbao.open();
						}
					} else if (trangthai == 2) {
						// Delete
						String delete = "DELETE DanhSachTrucPhong WHERE SoThe='" + sothe + "'";
						int ketquaxoa = statement.executeUpdate(delete);
						if (ketquaxoa > 0) {
							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Xóa thành công!");
							} else if (ngonngu == 1) {
								thongbao.setText("Notification");
								thongbao.setMessage("Delete successful!");
							} else if (ngonngu == 2) {
								thongbao.setText("通知");
								thongbao.setMessage("刪除成功");
							}
							thongbao.open();
							btnSave.setVisible(false);
							btnCancel.setVisible(false);
							btnAdd.setEnabled(true);
							btnDelete.setEnabled(true);
							textHoten.setText("");
							textSothe.setText("");
							textSothe.setEnabled(true);
							// Cập nhật lại dữ liệu cho table
							conn = DriverManager.getConnection(db_url);
							statement = conn.createStatement();
							String select = "SELECT SoThe,TenNguoiDung FROM DanhSachTrucPhong,NguoiDung WHERE DanhSachTrucPhong.SoThe=NguoiDung.MaNguoiDung ORDER BY SoThe ASC";
							ResultSet result = statement.executeQuery(select);
							table.removeAll();

							while (result.next()) {
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { result.getString(1), result.getString(2) });
							}

							result.close();
						}
					}

				} catch (SQLException sqe) {

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
				btnDelete.setEnabled(true);
				textHoten.setText("");
				textSothe.setText("");
				textSothe.setEnabled(true);
				textSothe.setEditable(true);
			}
		});
	}

}
