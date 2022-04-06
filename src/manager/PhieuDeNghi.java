package manager;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;

public class PhieuDeNghi {

	protected Shell shell;
	private Text textMaphieu;
	private Text textTenphieudenghi;
	private Text textTenfile;
	// private Table table;
	private CLabel lbTenphieudenghi;
	private String filename = "";
	private int ngonngu = 0; // 0: Vietnam, 1: English, 2: China

	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;

	// Kích thước màn hình
	private int sizemonitorx = Display.getDefault().getBounds().width;
	private int sizemonitory = Display.getDefault().getBounds().height;

	public static void main(String[] args) {
		try {
			PhieuDeNghi window = new PhieuDeNghi();
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
		createContents(db_url);
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
	protected void createContents(String chuoiketnoi) {
		db_url = chuoiketnoi;
		shell = new Shell();
		shell.setSize(1194, 720);
		shell.setText("Phiếu đề nghị");
		shell.setImage(SWTResourceManager.getImage(PhieuDeNghi.class, "/Manager/Images/repair.ico"));
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(shell, SWT.NONE);

		CLabel lbMaphieu = new CLabel(composite, SWT.RIGHT);
		lbMaphieu.setLocation(93, 33);
		lbMaphieu.setSize(113, 30);
		lbMaphieu.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbMaphieu.setText("Mã Phiếu");

		lbTenphieudenghi = new CLabel(composite, SWT.RIGHT);
		lbTenphieudenghi.setLocation(27, 84);
		lbTenphieudenghi.setSize(179, 30);
		lbTenphieudenghi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbTenphieudenghi.setText("Tên Phiếu Đề Nghị");

		CLabel lbTenfile = new CLabel(composite, SWT.RIGHT);
		lbTenfile.setLocation(110, 132);
		lbTenfile.setSize(96, 30);
		lbTenfile.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbTenfile.setText("Tên File");

		textMaphieu = new Text(composite, SWT.BORDER);
		textMaphieu.setLocation(212, 33);
		textMaphieu.setSize(297, 30);
		textMaphieu.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		textMaphieu.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		textTenphieudenghi = new Text(composite, SWT.BORDER);
		textTenphieudenghi.setLocation(212, 84);
		textTenphieudenghi.setSize(471, 30);
		textTenphieudenghi.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		textTenphieudenghi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		textTenfile = new Text(composite, SWT.BORDER);
		textTenfile.setLocation(212, 132);
		textTenfile.setSize(440, 30);
		textTenfile.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		textTenfile.setEnabled(false);
		textTenfile.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		Button btnTaifilelen = new Button(composite, SWT.NONE);
		btnTaifilelen.setLocation(659, 132);
		btnTaifilelen.setSize(131, 30);
		btnTaifilelen.setImage(SWTResourceManager.getImage(PhieuDeNghi.class, "/manager/Images/upload.png"));
		btnTaifilelen.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnTaifilelen.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnTaifilelen.setText("Tải tệp lên");

		Button btnThuchien = new Button(composite, SWT.NONE);
		btnThuchien.setLocation(820, 132);
		btnThuchien.setSize(126, 30);
		btnThuchien.setImage(SWTResourceManager.getImage(PhieuDeNghi.class, "/manager/Images/update.png"));
		btnThuchien.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		btnThuchien.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnThuchien.setText("Thực Hiện");

		Button btnhuy = new Button(composite, SWT.NONE);
		btnhuy.setLocation(952, 132);
		btnhuy.setSize(96, 30);
		btnhuy.setImage(SWTResourceManager.getImage(PhieuDeNghi.class, "/manager/Images/cancel.png"));
		btnhuy.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		btnhuy.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnhuy.setText("Hủy");

		Button btnXoa = new Button(composite, SWT.NONE);
		btnXoa.setLocation(1054, 132);
		btnXoa.setSize(97, 30);
		btnXoa.setImage(SWTResourceManager.getImage(PhieuDeNghi.class, "/manager/Images/delete.png"));
		btnXoa.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnXoa.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnXoa.setText("Xóa");

		Table table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLocation(27, 175);
		table.setSize(1124, 496);
		table.setHeaderBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tbclMaphieu = new TableColumn(table, SWT.NONE);
		tbclMaphieu.setWidth(194);
		tbclMaphieu.setText("Mã Phiếu");

		TableColumn tbclTenphieudenghi = new TableColumn(table, SWT.NONE);
		tbclTenphieudenghi.setWidth(357);
		tbclTenphieudenghi.setText("Tên Phiếu Đề Nghị");

		TableColumn tbclTenfile = new TableColumn(table, SWT.NONE);
		tbclTenfile.setWidth(394);
		tbclTenfile.setText("Tên Tệp");

		TableColumn tbclNgaycapnhat = new TableColumn(table, SWT.NONE);
		tbclNgaycapnhat.setWidth(161);
		tbclNgaycapnhat.setText("Ngày Cập Nhật");
		table.removeAll();

		// Xử lý ngôn ngữ
		// ------------------------------------------------------------------
		if (ngonngu == 0) {
			shell.setText("Phiếu đề nghị");
			lbMaphieu.setText("Mã Phiếu");
			lbTenphieudenghi.setText("Tên Phiếu Đề Nghị");
			lbTenfile.setText("Tên File");
			btnTaifilelen.setText("Tải tệp lên");
			btnThuchien.setText("Thực Hiện");
			btnhuy.setText("Hủy");
			btnXoa.setText("Xóa");
			tbclMaphieu.setText("Mã Phiếu");
			tbclTenphieudenghi.setText("Tên Phiếu Đề Nghị");
			tbclTenfile.setText("Tên Tệp");
			tbclNgaycapnhat.setText("Ngày Cập Nhật");
		} else if (ngonngu == 1) {
			shell.setText("Suggestion form");
			lbMaphieu.setText("Form Code");
			lbTenphieudenghi.setText("Suggestion Form Name");
			lbTenfile.setText("File Name");
			btnTaifilelen.setText("File Upload");
			btnThuchien.setText("Start");
			btnhuy.setText("Cancel");
			btnXoa.setText("Delete");
			tbclMaphieu.setText("Form Code");
			tbclTenphieudenghi.setText("Suggestion Form Name");
			tbclTenfile.setText("File Name");
			tbclNgaycapnhat.setText("Update Date");
		} else if (ngonngu == 2) {
			shell.setText("建議表格");
			lbMaphieu.setText("表格代碼");
			lbTenphieudenghi.setText("建議表格名稱");
			lbTenfile.setText("文件名");
			btnTaifilelen.setText("上傳文件");
			btnThuchien.setText("開始");
			btnhuy.setText("取消");
			btnXoa.setText("刪除");
			tbclMaphieu.setText("表格代碼");
			tbclTenphieudenghi.setText("建議表格名稱");
			tbclTenfile.setText("文件名");
			tbclNgaycapnhat.setText("更新日期");
		}

		// Button Xóa -------------------------------------------------------
		btnXoa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					try {
						// Lấy cột Ma Phieu
						TableItem[] itemtable = table.getSelection();
						String maphieu = itemtable[0].getText();
						String deletePhieudenghi = "DELETE FROM PhieuDeNghi WHERE MaForm='" + maphieu + "'";
						int resultdelete = statement.executeUpdate(deletePhieudenghi);
						if (resultdelete > 0) {
							// Xóa 1 dòng trên table
							table.remove(table.getSelectionIndices());
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
						}
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

		// Button Hủy ------------------------------------------------------------------
		btnhuy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textMaphieu.setText("");
				textTenphieudenghi.setText("");
				textTenfile.setText("");
			}
		});

		// Button Thực hiện ------------------------------------------------------------
		btnThuchien.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					// đọc hết hết một lần
					byte[] content = Files.readAllBytes(Paths.get(filename));
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						// String SQL = "INSERT INTO testTable(varBinaryData) VALUES (?)";

						String insert = "INSERT INTO PhieuDeNghi (MaForm,TenForm,TenFile,FileCode,NgayCapNhat) VALUES  ('"
								+ textMaphieu.getText() + "', N'" + textTenphieudenghi.getText() + "',N'"
								+ textTenfile.getText() + "',?,GETDATE())";

						PreparedStatement stmt = conn.prepareStatement(insert);
						stmt.setBytes(1, content);
						int result = stmt.executeUpdate();

						if (result > 0) {
							// Thêm dữ liệu cho table
							String select = "SELECT MaForm,TenForm,TenFile,NgayCapNhat FROM PhieuDeNghi";
							ResultSet resultthuchien = statement.executeQuery(select);
							table.removeAll();

							while (resultthuchien.next()) {
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { resultthuchien.getString(1), resultthuchien.getString(2),
										resultthuchien.getString(3), resultthuchien.getString(4) });
							}

							resultthuchien.close();

							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Tải tệp lên thành công!");
								textMaphieu.setText("");
								textTenphieudenghi.setText("");
								textTenfile.setText("");
							} else if (ngonngu == 1) {
								thongbao.setText("Error");
								thongbao.setMessage("Upload successful!");
							} else if (ngonngu == 2) {
								thongbao.setText("錯誤");
								thongbao.setMessage("上傳成功");
							}
							thongbao.open();
						}
						stmt.close();

					} catch (SQLException se) {
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						if (ngonngu == 0) {
							thongbao.setText("Thông báo lỗi");
							thongbao.setMessage("Lỗi kết nối!");
							thongbao.setMessage(se.toString());
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

				} catch (Exception ex) {

				}
			}
		});

		// Button Tải file lên
		// -------------------------------------------------------------
		btnTaifilelen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] FILTER_NAMES = { "PDF (*.pdf)", "Excel 2003 (*.xls)", "Word 2003 (*.doc)", "All Files (*.*)" };
				// đuôi file có thể mở
				String[] FILTER_EXTS = { "*.pdf", "*.xls", "*.doc", "*.*" };

				FileDialog dlg = new FileDialog(shell, SWT.OPEN);
				dlg.setFilterNames(FILTER_NAMES);
				dlg.setFilterExtensions(FILTER_EXTS);
				filename = dlg.open();
				if (filename != null) {
					// textTenfile.setText(filename);

					Path path = Paths.get(filename);
					Path tenfile = path.getFileName();

					textTenfile.setText(tenfile.toString());
				}
			}
		});

		// Lấy dữ liệu cho table
		// --------------------------------------------------------
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			try {
				String select = "SELECT MaForm,TenForm,TenFile,NgayCapNhat FROM PhieuDeNghi";
				ResultSet result = statement.executeQuery(select);

				while (result.next()) {
					TableItem item = new TableItem(table, SWT.NONE);
					item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
							result.getString(4) });
				}

				result.close();

			} catch (Exception ex) {
				MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
				thongbao.setText("Error");
				thongbao.setMessage(ex.toString());
				thongbao.open();
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

	// Hien trong Ctabfolder
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	protected void createContentsTabFolder(CTabFolder tabfolder, Shell shell, int ngonngu, String chuoiketnoi) {
		db_url = chuoiketnoi;
		CTabItem itemtab = new CTabItem(tabfolder, SWT.CLOSE);
		itemtab.setText("Suggestion form");
		Composite composite = new Composite(tabfolder, SWT.NONE);
		itemtab.setControl(composite);

		CLabel lbMaphieu = new CLabel(composite, SWT.RIGHT);
		lbMaphieu.setLocation(93, 33);
		lbMaphieu.setSize(113, 30);
		lbMaphieu.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbMaphieu.setText("Mã Phiếu");

		lbTenphieudenghi = new CLabel(composite, SWT.RIGHT);
		lbTenphieudenghi.setLocation(27, 84);
		lbTenphieudenghi.setSize(179, 30);
		lbTenphieudenghi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbTenphieudenghi.setText("Tên Phiếu Đề Nghị");

		CLabel lbTenfile = new CLabel(composite, SWT.RIGHT);
		lbTenfile.setLocation(110, 132);
		lbTenfile.setSize(96, 30);
		lbTenfile.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		lbTenfile.setText("Tên File");

		textMaphieu = new Text(composite, SWT.BORDER);
		textMaphieu.setLocation(212, 33);
		textMaphieu.setSize(297, 30);
		textMaphieu.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		textMaphieu.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		textTenphieudenghi = new Text(composite, SWT.BORDER);
		textTenphieudenghi.setLocation(212, 84);
		textTenphieudenghi.setSize(471, 30);
		textTenphieudenghi.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		textTenphieudenghi.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		textTenfile = new Text(composite, SWT.BORDER);
		textTenfile.setLocation(212, 132);
		textTenfile.setSize(440, 30);
		textTenfile.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		textTenfile.setEnabled(false);
		textTenfile.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));

		Button btnTaifilelen = new Button(composite, SWT.NONE);
		btnTaifilelen.setLocation(659, 132);
		btnTaifilelen.setSize(131, 30);
		btnTaifilelen.setImage(SWTResourceManager.getImage(PhieuDeNghi.class, "/manager/Images/upload.png"));
		btnTaifilelen.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnTaifilelen.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnTaifilelen.setText("Tải tệp lên");

		Button btnThuchien = new Button(composite, SWT.NONE);
		btnThuchien.setLocation(820, 132);
		btnThuchien.setSize(126, 30);
		btnThuchien.setImage(SWTResourceManager.getImage(PhieuDeNghi.class, "/manager/Images/update.png"));
		btnThuchien.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		btnThuchien.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnThuchien.setText("Thực Hiện");

		Button btnhuy = new Button(composite, SWT.NONE);
		btnhuy.setLocation(952, 132);
		btnhuy.setSize(96, 30);
		btnhuy.setImage(SWTResourceManager.getImage(PhieuDeNghi.class, "/manager/Images/cancel.png"));
		btnhuy.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		btnhuy.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnhuy.setText("Hủy");

		Button btnXoa = new Button(composite, SWT.NONE);
		btnXoa.setLocation(1054, 132);
		btnXoa.setSize(97, 30);
		btnXoa.setImage(SWTResourceManager.getImage(PhieuDeNghi.class, "/manager/Images/delete.png"));
		btnXoa.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		btnXoa.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnXoa.setText("Xóa");

		Table table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLocation(27, 175);
		// table.setSize(1124, 496);
		table.setSize(sizemonitorx - 60, sizemonitory - 300);
		table.setHeaderBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tbclMaphieu = new TableColumn(table, SWT.NONE);
		tbclMaphieu.setWidth(194);
		tbclMaphieu.setText("Mã Phiếu");

		TableColumn tbclTenphieudenghi = new TableColumn(table, SWT.NONE);
		tbclTenphieudenghi.setWidth(357);
		tbclTenphieudenghi.setText("Tên Phiếu Đề Nghị");

		TableColumn tbclTenfile = new TableColumn(table, SWT.NONE);
		tbclTenfile.setWidth(394);
		tbclTenfile.setText("Tên Tệp");

		TableColumn tbclNgaycapnhat = new TableColumn(table, SWT.NONE);
		tbclNgaycapnhat.setWidth(161);
		tbclNgaycapnhat.setText("Ngày Cập Nhật");
		table.removeAll();

		// Xử lý ngôn ngữ
		// ------------------------------------------------------------------
		if (ngonngu == 0) {
			shell.setText("Phiếu đề nghị");
			itemtab.setText("Phiếu đề nghị");
			lbMaphieu.setText("Mã Phiếu");
			lbTenphieudenghi.setText("Tên Phiếu Đề Nghị");
			lbTenfile.setText("Tên File");
			btnTaifilelen.setText("Tải tệp lên");
			btnThuchien.setText("Thực Hiện");
			btnhuy.setText("Hủy");
			btnXoa.setText("Xóa");
			tbclMaphieu.setText("Mã Phiếu");
			tbclTenphieudenghi.setText("Tên Phiếu Đề Nghị");
			tbclTenfile.setText("Tên Tệp");
			tbclNgaycapnhat.setText("Ngày Cập Nhật");
		} else if (ngonngu == 1) {
			shell.setText("Suggestion form");
			itemtab.setText("Suggestion form");
			lbMaphieu.setText("Form Code");
			lbTenphieudenghi.setText("Suggestion Form Name");
			lbTenfile.setText("File Name");
			btnTaifilelen.setText("File Upload");
			btnThuchien.setText("Start");
			btnhuy.setText("Cancel");
			btnXoa.setText("Delete");
			tbclMaphieu.setText("Form Code");
			tbclTenphieudenghi.setText("Suggestion Form Name");
			tbclTenfile.setText("File Name");
			tbclNgaycapnhat.setText("Update Date");
		} else if (ngonngu == 2) {
			shell.setText("建議表格");
			itemtab.setText("建議表格");
			lbMaphieu.setText("表格代碼");
			lbTenphieudenghi.setText("建議表格名稱");
			lbTenfile.setText("文件名");
			btnTaifilelen.setText("上傳文件");
			btnThuchien.setText("開始");
			btnhuy.setText("取消");
			btnXoa.setText("刪除");
			tbclMaphieu.setText("表格代碼");
			tbclTenphieudenghi.setText("建議表格名稱");
			tbclTenfile.setText("文件名");
			tbclNgaycapnhat.setText("更新日期");
		}

		// Button Xóa -------------------------------------------------------
		btnXoa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					try {
						// Lấy cột Ma Phieu
						TableItem[] itemtable = table.getSelection();
						String maphieu = itemtable[0].getText();
						String deletePhieudenghi = "DELETE FROM PhieuDeNghi WHERE MaForm='" + maphieu + "'";
						int resultdelete = statement.executeUpdate(deletePhieudenghi);
						if (resultdelete > 0) {
							// Xóa 1 dòng trên table
							table.remove(table.getSelectionIndices());
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
						}
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

		// Button Hủy ------------------------------------------------------------------
		btnhuy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textMaphieu.setText("");
				textTenphieudenghi.setText("");
				textTenfile.setText("");
			}
		});

		// Button Thực hiện ------------------------------------------------------------
		btnThuchien.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					// đọc hết hết một lần
					byte[] content = Files.readAllBytes(Paths.get(filename));
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						// String SQL = "INSERT INTO testTable(varBinaryData) VALUES (?)";

						String insert = "INSERT INTO PhieuDeNghi (MaForm,TenForm,TenFile,FileCode,NgayCapNhat) VALUES  ('"
								+ textMaphieu.getText() + "', N'" + textTenphieudenghi.getText() + "',N'"
								+ textTenfile.getText() + "',?,GETDATE())";

						PreparedStatement stmt = conn.prepareStatement(insert);
						stmt.setBytes(1, content);
						int result = stmt.executeUpdate();

						if (result > 0) {
							// Thêm dữ liệu cho table
							String select = "SELECT MaForm,TenForm,TenFile,NgayCapNhat FROM PhieuDeNghi";
							ResultSet resultthuchien = statement.executeQuery(select);
							table.removeAll();

							while (resultthuchien.next()) {
								TableItem item = new TableItem(table, SWT.NONE);
								item.setText(new String[] { resultthuchien.getString(1), resultthuchien.getString(2),
										resultthuchien.getString(3), resultthuchien.getString(4) });
							}

							resultthuchien.close();

							MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
							if (ngonngu == 0) {
								thongbao.setText("Thông báo");
								thongbao.setMessage("Tải tệp lên thành công!");
								textMaphieu.setText("");
								textTenphieudenghi.setText("");
								textTenfile.setText("");
							} else if (ngonngu == 1) {
								thongbao.setText("Error");
								thongbao.setMessage("Upload successful!");
							} else if (ngonngu == 2) {
								thongbao.setText("錯誤");
								thongbao.setMessage("上傳成功");
							}
							thongbao.open();
						}
						stmt.close();

					} catch (SQLException se) {
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						if (ngonngu == 0) {
							thongbao.setText("Thông báo lỗi");
							thongbao.setMessage("Lỗi kết nối!");
							thongbao.setMessage(se.toString());
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

				} catch (Exception ex) {

				}
			}
		});

		// Button Tải file lên
		// -------------------------------------------------------------
		btnTaifilelen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] FILTER_NAMES = { "PDF (*.pdf)", "Excel 2003 (*.xls)", "Word 2003 (*.doc)", "All Files (*.*)" };
				// đuôi file có thể mở
				String[] FILTER_EXTS = { "*.pdf", "*.xls", "*.doc", "*.*" };

				FileDialog dlg = new FileDialog(shell, SWT.OPEN);
				dlg.setFilterNames(FILTER_NAMES);
				dlg.setFilterExtensions(FILTER_EXTS);
				filename = dlg.open();
				if (filename != null) {
					// textTenfile.setText(filename);

					Path path = Paths.get(filename);
					Path tenfile = path.getFileName();

					textTenfile.setText(tenfile.toString());
				}
			}
		});

		// Lấy dữ liệu cho table
		// --------------------------------------------------------
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			try {
				String select = "SELECT MaForm,TenForm,TenFile,NgayCapNhat FROM PhieuDeNghi";
				ResultSet result = statement.executeQuery(select);

				while (result.next()) {
					TableItem item = new TableItem(table, SWT.NONE);
					item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
							result.getString(4) });
				}

				result.close();

			} catch (Exception ex) {
				MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
				thongbao.setText("Error");
				thongbao.setMessage(ex.toString());
				thongbao.open();
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
}
