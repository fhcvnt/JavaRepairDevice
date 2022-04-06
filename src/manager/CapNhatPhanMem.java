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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CapNhatPhanMem {

	protected Shell shell;
	private Text textTentep;
	private Text textPhienban;
	private Table table;
	// kích thước màn hình
	private int sizemonitorx = Display.getDefault().getBounds().width;
	private int sizemonitory = Display.getDefault().getBounds().height;
	// Chuỗi kết nối
	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;
	private String filename = "";
	private String tenfile = "";
	private String loai = "";
	private String hedieuhanh = "";
	private String phienban = "";
	private String thoigiancapnhat = "";
	private int vitrixoa = -1;

	public static void main(String[] args) {
		try {
			CapNhatPhanMem window = new CapNhatPhanMem();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		Display display = Display.getDefault();
		createContents(0, "jdbc:sqlserver://192.168.30.123;databaseName=SuaChuaThietBi;user=sa;password=Killua21608");
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
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage(CapNhatPhanMem.class, "/manager/Images/repair256.ico"));
		shell.setSize(1237, 708);
		shell.setMaximized(true);
		shell.setText("Create file update");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(shell, SWT.NONE);

		CLabel lbTentep = new CLabel(composite, SWT.NONE);
		lbTentep.setAlignment(SWT.RIGHT);
		lbTentep.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbTentep.setBounds(21, 21, 93, 30);
		lbTentep.setText("Tên Tệp");

		textTentep = new Text(composite, SWT.BORDER);
		textTentep.setEnabled(false);
		textTentep.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textTentep.setBounds(133, 21, 324, 30);

		CLabel lbLoai = new CLabel(composite, SWT.NONE);
		lbLoai.setAlignment(SWT.RIGHT);
		lbLoai.setText("Loại");
		lbLoai.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbLoai.setBounds(650, 21, 84, 30);

		CCombo comboLoai = new CCombo(composite, SWT.BORDER);
		comboLoai.setText("User");
		comboLoai.setItems(new String[] { "User", "Manager" });
		comboLoai.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		comboLoai.setBounds(757, 21, 152, 30);

		Button btnChontep = new Button(composite, SWT.NONE);
		btnChontep.setImage(SWTResourceManager.getImage(CapNhatPhanMem.class, "/manager/Images/upload.png"));
		btnChontep.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnChontep.setBounds(470, 21, 162, 30);
		btnChontep.setText("Chọn Tệp");

		CLabel lbHedieuhanh = new CLabel(composite, SWT.NONE);
		lbHedieuhanh.setAlignment(SWT.RIGHT);
		lbHedieuhanh.setText("Hệ Điều Hành");
		lbHedieuhanh.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbHedieuhanh.setBounds(21, 74, 147, 30);

		CCombo comboHedieuhanh = new CCombo(composite, SWT.BORDER);
		comboHedieuhanh.setText("Window 32");
		comboHedieuhanh.setItems(new String[] { "Window 32","Window 64", "Linux 32","Linux 64" });
		comboHedieuhanh.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		comboHedieuhanh.setBounds(192, 74, 162, 30);

		CLabel lbPhienban = new CLabel(composite, SWT.NONE);
		lbPhienban.setAlignment(SWT.RIGHT);
		lbPhienban.setText("Phiên Bản");
		lbPhienban.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbPhienban.setBounds(371, 74, 112, 30);

		textPhienban = new Text(composite, SWT.BORDER);
		textPhienban.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textPhienban.setBounds(508, 74, 259, 30);

		Button btnThem = new Button(composite, SWT.NONE);
		btnThem.setImage(SWTResourceManager.getImage(CapNhatPhanMem.class, "/manager/icon/add30.png"));
		btnThem.setText("Thêm");
		btnThem.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnThem.setBounds(773, 74, 103, 30);

		Button btnXoa = new Button(composite, SWT.NONE);
		btnXoa.setText("Xóa");
		btnXoa.setImage(SWTResourceManager.getImage(CapNhatPhanMem.class, "/manager/icon/delete.png"));
		btnXoa.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnXoa.setBounds(882, 74, 103, 30);

		Button btnLuu = new Button(composite, SWT.NONE);
		btnLuu.setText("Lưu");
		btnLuu.setImage(SWTResourceManager.getImage(CapNhatPhanMem.class, "/manager/icon/save25.png"));
		btnLuu.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnLuu.setBounds(991, 74, 93, 30);

		Button btnHuy = new Button(composite, SWT.NONE);
		btnHuy.setText("Hủy");
		btnHuy.setImage(SWTResourceManager.getImage(CapNhatPhanMem.class, "/manager/icon/cancel.png"));
		btnHuy.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnHuy.setBounds(1090, 74, 103, 30);

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderBackground(SWTResourceManager.getColor(255, 165, 0));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		table.setBounds(10, 117, 1168, 542);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setSize(sizemonitorx - 20, sizemonitory - 190);

		TableColumn tbclTentep = new TableColumn(table, SWT.NONE);
		tbclTentep.setWidth(299);
		tbclTentep.setText("Tên Tệp");

		TableColumn tbclLoai = new TableColumn(table, SWT.NONE);
		tbclLoai.setWidth(176);
		tbclLoai.setText("Loại");

		TableColumn tbclHedieuhanh = new TableColumn(table, SWT.NONE);
		tbclHedieuhanh.setWidth(172);
		tbclHedieuhanh.setText("Hệ Điều Hành");

		TableColumn tbclPhienban = new TableColumn(table, SWT.NONE);
		tbclPhienban.setWidth(222);
		tbclPhienban.setText("Phiên Bản");

		TableColumn tbclNgaycapnhat = new TableColumn(table, SWT.NONE);
		tbclNgaycapnhat.setWidth(181);
		tbclNgaycapnhat.setText("Ngày Cập Nhật");

		btnLuu.setVisible(false);
		btnHuy.setVisible(false);

		// ngôn ngữ
		// *******************************************************************************************************************************************
		if (ngonngu == 0) {
			shell.setText("Tạo tệp cập nhật");
			lbTentep.setText("Tên Tệp");
			btnChontep.setText("Chọn Tệp");
			lbLoai.setText("Loại");
			lbHedieuhanh.setText("Hệ Điều Hành");
			lbPhienban.setText("Phiên Bản");
			btnThem.setText("Thêm");
			btnXoa.setText("Xóa");
			btnLuu.setText("Lưu");
			btnHuy.setText("Hủy");
			tbclTentep.setText("Tên Tệp");
			tbclLoai.setText("Loại");
			tbclHedieuhanh.setText("Hệ Điều Hành");
			tbclPhienban.setText("Phiên Bản");
			tbclNgaycapnhat.setText("Ngày Cập Nhật");
		} else if (ngonngu == 1) {
			shell.setText("Create the update file");
			lbTentep.setText("File Name");
			btnChontep.setText("Choose File");
			lbLoai.setText("Type");
			lbHedieuhanh.setText("Operator System");
			lbPhienban.setText("Version");
			btnThem.setText("Add");
			btnXoa.setText("Delete");
			btnLuu.setText("Save");
			btnHuy.setText("Cancel");
			tbclTentep.setText("File Name");
			tbclLoai.setText("Type");
			tbclHedieuhanh.setText("Operator System");
			tbclPhienban.setText("Version");
			tbclNgaycapnhat.setText("Update Date");
		} else if (ngonngu == 2) {
			shell.setText("創建更新文件");
			lbTentep.setText("文件名");
			btnChontep.setText("選擇檔案");
			lbLoai.setText("類型");
			lbHedieuhanh.setText("操作員系統");
			lbPhienban.setText("版");
			btnThem.setText("加");
			btnLuu.setText("儲存");
			btnHuy.setText("取消");
			btnXoa.setText("刪除");
			tbclTentep.setText("文件名");
			tbclLoai.setText("類型");
			tbclHedieuhanh.setText("操作員系統");
			tbclPhienban.setText("版");
			tbclNgaycapnhat.setText("更新日期");
		}

		// Lấy dữ liệu lúc đầu
		// *******************************************************************************************************************************************
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			// Lấy dữ liệu cho table
			String select = "SELECT TenFile,Loai,HeDieuHanh,PhienBan,ThoiGianCapNhat FROM CapNhatPhanMem ORDER BY HeDieuHanh DESC";
			ResultSet resultthuchien = statement.executeQuery(select);
			table.removeAll();

			while (resultthuchien.next()) {
				String ngaycapnhat = resultthuchien.getString(5);
				try {
					ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7) + "/"
							+ ngaycapnhat.substring(0, 4);
				} catch (Exception ee) {
					ngaycapnhat = "";
				}
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] { resultthuchien.getString(1), resultthuchien.getString(2),
						resultthuchien.getString(3), resultthuchien.getString(4), ngaycapnhat });
			}

			resultthuchien.close();

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

		// Button Chọn tệp
		// *******************************************************************************************************************************************
		btnChontep.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] FILTER_NAMES = { "All Files (*.*)" };
				// đuôi file có thể mở
				String[] FILTER_EXTS = { "*.*" };

				FileDialog dlg = new FileDialog(shell, SWT.OPEN);
				dlg.setFilterNames(FILTER_NAMES);
				dlg.setFilterExtensions(FILTER_EXTS);
				filename = dlg.open();
				if (filename != null) {
					Path path = Paths.get(filename);
					Path tenfile = path.getFileName();

					textTentep.setText(tenfile.toString());
				}
			}
		});

		// Button Thêm
		// *******************************************************************************************************************************************
		btnThem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!(textTentep.getText().isEmpty() || textPhienban.getText().isEmpty()
						|| comboHedieuhanh.getText().isEmpty() || comboLoai.getText().isEmpty())) {
					try {
						// đọc hết hết một lần
						byte[] content = Files.readAllBytes(Paths.get(filename));
						try {
							conn = DriverManager.getConnection(db_url);
							statement = conn.createStatement();

							String insert = "INSERT INTO CapNhatPhanMem(TenFile,FileCode,Loai,HeDieuHanh,ThoiGianCapNhat,PhienBan) VALUES (N'"
									+ textTentep.getText() + "',?,'" + comboLoai.getText() + "','"
									+ comboHedieuhanh.getText() + "',GETDATE(),'" + textPhienban.getText() + "')";

							PreparedStatement stmt = conn.prepareStatement(insert);
							stmt.setBytes(1, content);
							int result = stmt.executeUpdate();

							if (result > 0) {
								// Thêm dữ liệu cho table
								String select = "SELECT TenFile,Loai,HeDieuHanh,PhienBan,ThoiGianCapNhat FROM CapNhatPhanMem ORDER BY HeDieuHanh DESC";
								ResultSet resultthuchien = statement.executeQuery(select);
								table.removeAll();

								while (resultthuchien.next()) {
									String ngaycapnhat = resultthuchien.getString(5);
									try {
										ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7)
												+ "/" + ngaycapnhat.substring(0, 4);
									} catch (Exception ee) {
										ngaycapnhat = "";
									}
									TableItem item = new TableItem(table, SWT.NONE);
									item.setText(new String[] { resultthuchien.getString(1),
											resultthuchien.getString(2), resultthuchien.getString(3),
											resultthuchien.getString(4), ngaycapnhat });
								}

								resultthuchien.close();

								MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								if (ngonngu == 0) {
									thongbao.setText("Thông báo");
									thongbao.setMessage("Tải tệp lên thành công!");
								} else if (ngonngu == 1) {
									thongbao.setText("Error");
									thongbao.setMessage("Upload successful!");
								} else if (ngonngu == 2) {
									thongbao.setText("錯誤");
									thongbao.setMessage("上傳成功");
								}
								thongbao.open();
								textPhienban.setText("");
								textTentep.setText("");
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
			}
		});

		// Button Xóa
		// *******************************************************************************************************************************************
		btnXoa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!btnLuu.isVisible()) {
					try {
						TableItem[] itemtable = table.getSelection();
						tenfile = itemtable[0].getText();
						loai = itemtable[0].getText(1);
						hedieuhanh = itemtable[0].getText(2);
						phienban = itemtable[0].getText(3);
						thoigiancapnhat = itemtable[0].getText(4);
						vitrixoa = table.getSelectionIndex();
						btnLuu.setVisible(true);
						btnHuy.setVisible(true);
						btnThem.setEnabled(false);
						btnChontep.setEnabled(false);
						textTentep.setText(tenfile);
						comboLoai.setText(loai);
						comboHedieuhanh.setText(hedieuhanh);
						textPhienban.setText(phienban);
					} catch (Exception ee) {

					}
				}
			}
		});

		// Button Lưu
		// *******************************************************************************************************************************************
		btnLuu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnLuu.isVisible()) {
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						try {
							thoigiancapnhat = thoigiancapnhat.substring(6, 10) + thoigiancapnhat.substring(3, 5)
									+ thoigiancapnhat.substring(0, 2);
						} catch (Exception ee) {
							thoigiancapnhat = "";
						}

						String deletePhieudenghi = "DELETE FROM CapNhatPhanMem WHERE TenFile=N'" + tenfile
								+ "' AND Loai='" + loai + "' AND HeDieuHanh='" + hedieuhanh + "' AND PhienBan='"
								+ phienban + "' AND ThoiGianCapNhat='" + thoigiancapnhat + "'";
						int resultdelete = statement.executeUpdate(deletePhieudenghi);
						if (resultdelete > 0) {
							// Xóa 1 dòng trên table
							table.remove(vitrixoa);
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
							btnLuu.setVisible(false);
							btnHuy.setVisible(false);
							btnThem.setEnabled(true);
							btnChontep.setEnabled(true);
							textTentep.setText("");
							comboLoai.setText("");
							comboHedieuhanh.setText("");
							textPhienban.setText("");
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
		});

		// Button Hủy
		// *******************************************************************************************************************************************
		btnHuy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnLuu.setVisible(false);
				btnHuy.setVisible(false);
				btnThem.setEnabled(true);
				btnChontep.setEnabled(true);
				textTentep.setText("");
				comboLoai.setText("");
				comboHedieuhanh.setText("");
				textPhienban.setText("");
			}
		});
	}

	// Hien Ctabfolder
	// ***********************************************************************************************************************************
	protected void createContentsTabfolder(CTabFolder tabfolder, Shell shell, int ngonngu, String chuoiketnoi) {
		db_url = chuoiketnoi;
		CTabItem itemtab = new CTabItem(tabfolder, SWT.CLOSE);
		itemtab.setText("Cập nhật");
		Composite composite = new Composite(tabfolder, SWT.NONE);
		itemtab.setControl(composite);

		CLabel lbTentep = new CLabel(composite, SWT.NONE);
		lbTentep.setAlignment(SWT.RIGHT);
		lbTentep.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbTentep.setBounds(21, 21, 93, 30);
		lbTentep.setText("Tên Tệp");

		textTentep = new Text(composite, SWT.BORDER);
		textTentep.setEnabled(false);
		textTentep.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textTentep.setBounds(133, 21, 324, 30);

		CLabel lbLoai = new CLabel(composite, SWT.NONE);
		lbLoai.setAlignment(SWT.RIGHT);
		lbLoai.setText("Loại");
		lbLoai.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbLoai.setBounds(650, 21, 84, 30);

		CCombo comboLoai = new CCombo(composite, SWT.BORDER);
		comboLoai.setText("User");
		comboLoai.setItems(new String[] { "User", "Manager" });
		comboLoai.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		comboLoai.setBounds(757, 21, 152, 30);

		Button btnChontep = new Button(composite, SWT.NONE);
		btnChontep.setImage(SWTResourceManager.getImage(CapNhatPhanMem.class, "/manager/Images/upload.png"));
		btnChontep.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnChontep.setBounds(470, 21, 162, 30);
		btnChontep.setText("Chọn Tệp");

		CLabel lbHedieuhanh = new CLabel(composite, SWT.NONE);
		lbHedieuhanh.setAlignment(SWT.RIGHT);
		lbHedieuhanh.setText("Hệ Điều Hành");
		lbHedieuhanh.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbHedieuhanh.setBounds(21, 74, 147, 30);

		CCombo comboHedieuhanh = new CCombo(composite, SWT.BORDER);
		comboHedieuhanh.setText("Window");
		comboHedieuhanh.setItems(new String[] { "Window", "Linux" });
		comboHedieuhanh.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		comboHedieuhanh.setBounds(192, 74, 162, 30);

		CLabel lbPhienban = new CLabel(composite, SWT.NONE);
		lbPhienban.setAlignment(SWT.RIGHT);
		lbPhienban.setText("Phiên Bản");
		lbPhienban.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbPhienban.setBounds(371, 74, 112, 30);

		textPhienban = new Text(composite, SWT.BORDER);
		textPhienban.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textPhienban.setBounds(508, 74, 259, 30);

		Button btnThem = new Button(composite, SWT.NONE);
		btnThem.setImage(SWTResourceManager.getImage(CapNhatPhanMem.class, "/manager/icon/add30.png"));
		btnThem.setText("Thêm");
		btnThem.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnThem.setBounds(773, 74, 103, 30);

		Button btnXoa = new Button(composite, SWT.NONE);
		btnXoa.setText("Xóa");
		btnXoa.setImage(SWTResourceManager.getImage(CapNhatPhanMem.class, "/manager/icon/delete.png"));
		btnXoa.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnXoa.setBounds(882, 74, 103, 30);

		Button btnLuu = new Button(composite, SWT.NONE);
		btnLuu.setText("Lưu");
		btnLuu.setImage(SWTResourceManager.getImage(CapNhatPhanMem.class, "/manager/icon/save25.png"));
		btnLuu.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnLuu.setBounds(991, 74, 93, 30);

		Button btnHuy = new Button(composite, SWT.NONE);
		btnHuy.setText("Hủy");
		btnHuy.setImage(SWTResourceManager.getImage(CapNhatPhanMem.class, "/manager/icon/cancel.png"));
		btnHuy.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnHuy.setBounds(1090, 74, 103, 30);

		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderBackground(SWTResourceManager.getColor(255, 165, 0));
		table.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		table.setBounds(10, 117, 1168, 542);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setSize(sizemonitorx - 20, sizemonitory - 240);

		TableColumn tbclTentep = new TableColumn(table, SWT.NONE);
		tbclTentep.setWidth(299);
		tbclTentep.setText("Tên Tệp");

		TableColumn tbclLoai = new TableColumn(table, SWT.NONE);
		tbclLoai.setWidth(176);
		tbclLoai.setText("Loại");

		TableColumn tbclHedieuhanh = new TableColumn(table, SWT.NONE);
		tbclHedieuhanh.setWidth(172);
		tbclHedieuhanh.setText("Hệ Điều Hành");

		TableColumn tbclPhienban = new TableColumn(table, SWT.NONE);
		tbclPhienban.setWidth(222);
		tbclPhienban.setText("Phiên Bản");

		TableColumn tbclNgaycapnhat = new TableColumn(table, SWT.NONE);
		tbclNgaycapnhat.setWidth(181);
		tbclNgaycapnhat.setText("Ngày Cập Nhật");

		btnLuu.setVisible(false);
		btnHuy.setVisible(false);

		// ngôn ngữ
		// *******************************************************************************************************************************************
		if (ngonngu == 0) {
			itemtab.setText("Cập nhật");
			lbTentep.setText("Tên Tệp");
			btnChontep.setText("Chọn Tệp");
			lbLoai.setText("Loại");
			lbHedieuhanh.setText("Hệ Điều Hành");
			lbPhienban.setText("Phiên Bản");
			btnThem.setText("Thêm");
			btnXoa.setText("Xóa");
			btnLuu.setText("Lưu");
			btnHuy.setText("Hủy");
			tbclTentep.setText("Tên Tệp");
			tbclLoai.setText("Loại");
			tbclHedieuhanh.setText("Hệ Điều Hành");
			tbclPhienban.setText("Phiên Bản");
			tbclNgaycapnhat.setText("Ngày Cập Nhật");
		} else if (ngonngu == 1) {
			itemtab.setText("Update");
			lbTentep.setText("File Name");
			btnChontep.setText("Choose File");
			lbLoai.setText("Type");
			lbHedieuhanh.setText("Operator System");
			lbPhienban.setText("Version");
			btnThem.setText("Add");
			btnXoa.setText("Delete");
			btnLuu.setText("Save");
			btnHuy.setText("Cancel");
			tbclTentep.setText("File Name");
			tbclLoai.setText("Type");
			tbclHedieuhanh.setText("Operator System");
			tbclPhienban.setText("Version");
			tbclNgaycapnhat.setText("Update Date");
		} else if (ngonngu == 2) {
			itemtab.setText("更新");
			lbTentep.setText("文件名");
			btnChontep.setText("選擇檔案");
			lbLoai.setText("類型");
			lbHedieuhanh.setText("操作員系統");
			lbPhienban.setText("版");
			btnThem.setText("加");
			btnLuu.setText("儲存");
			btnHuy.setText("取消");
			btnXoa.setText("刪除");
			tbclTentep.setText("文件名");
			tbclLoai.setText("類型");
			tbclHedieuhanh.setText("操作員系統");
			tbclPhienban.setText("版");
			tbclNgaycapnhat.setText("更新日期");
		}

		// Lấy dữ liệu lúc đầu
		// *******************************************************************************************************************************************
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			// Lấy dữ liệu cho table
			String select = "SELECT TenFile,Loai,HeDieuHanh,PhienBan,ThoiGianCapNhat FROM CapNhatPhanMem ORDER BY HeDieuHanh DESC";
			ResultSet resultthuchien = statement.executeQuery(select);
			table.removeAll();

			while (resultthuchien.next()) {
				String ngaycapnhat = resultthuchien.getString(5);
				try {
					ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7) + "/"
							+ ngaycapnhat.substring(0, 4);
				} catch (Exception ee) {
					ngaycapnhat = "";
				}
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(new String[] { resultthuchien.getString(1), resultthuchien.getString(2),
						resultthuchien.getString(3), resultthuchien.getString(4), ngaycapnhat });
			}

			resultthuchien.close();

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

		// Button Chọn tệp
		// *******************************************************************************************************************************************
		btnChontep.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] FILTER_NAMES = { "All Files (*.*)" };
				// đuôi file có thể mở
				String[] FILTER_EXTS = { "*.*" };

				FileDialog dlg = new FileDialog(shell, SWT.OPEN);
				dlg.setFilterNames(FILTER_NAMES);
				dlg.setFilterExtensions(FILTER_EXTS);
				filename = dlg.open();
				if (filename != null) {
					Path path = Paths.get(filename);
					Path tenfile = path.getFileName();

					textTentep.setText(tenfile.toString());
				}
			}
		});

		// Button Thêm
		// *******************************************************************************************************************************************
		btnThem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!(textTentep.getText().isEmpty() || textPhienban.getText().isEmpty()
						|| comboHedieuhanh.getText().isEmpty() || comboLoai.getText().isEmpty())) {
					try {
						// đọc hết hết một lần
						byte[] content = Files.readAllBytes(Paths.get(filename));
						try {
							conn = DriverManager.getConnection(db_url);
							statement = conn.createStatement();

							String insert = "INSERT INTO CapNhatPhanMem(TenFile,FileCode,Loai,HeDieuHanh,ThoiGianCapNhat,PhienBan) VALUES (N'"
									+ textTentep.getText() + "',?,'" + comboLoai.getText() + "','"
									+ comboHedieuhanh.getText() + "',GETDATE(),'" + textPhienban.getText() + "')";

							PreparedStatement stmt = conn.prepareStatement(insert);
							stmt.setBytes(1, content);
							int result = stmt.executeUpdate();

							if (result > 0) {
								// Thêm dữ liệu cho table
								String select = "SELECT TenFile,Loai,HeDieuHanh,PhienBan,ThoiGianCapNhat FROM CapNhatPhanMem ORDER BY HeDieuHanh DESC";
								ResultSet resultthuchien = statement.executeQuery(select);
								table.removeAll();

								while (resultthuchien.next()) {
									String ngaycapnhat = resultthuchien.getString(5);
									try {
										ngaycapnhat = ngaycapnhat.substring(8, 10) + "/" + ngaycapnhat.substring(5, 7)
												+ "/" + ngaycapnhat.substring(0, 4);
									} catch (Exception ee) {
										ngaycapnhat = "";
									}
									TableItem item = new TableItem(table, SWT.NONE);
									item.setText(new String[] { resultthuchien.getString(1),
											resultthuchien.getString(2), resultthuchien.getString(3),
											resultthuchien.getString(4), ngaycapnhat });
								}

								resultthuchien.close();

								MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
								if (ngonngu == 0) {
									thongbao.setText("Thông báo");
									thongbao.setMessage("Tải tệp lên thành công!");
								} else if (ngonngu == 1) {
									thongbao.setText("Error");
									thongbao.setMessage("Upload successful!");
								} else if (ngonngu == 2) {
									thongbao.setText("錯誤");
									thongbao.setMessage("上傳成功");
								}
								thongbao.open();
								textPhienban.setText("");
								textTentep.setText("");
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
			}
		});

		// Button Xóa
		// *******************************************************************************************************************************************
		btnXoa.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!btnLuu.isVisible()) {
					try {
						TableItem[] itemtable = table.getSelection();
						tenfile = itemtable[0].getText();
						loai = itemtable[0].getText(1);
						hedieuhanh = itemtable[0].getText(2);
						phienban = itemtable[0].getText(3);
						thoigiancapnhat = itemtable[0].getText(4);
						vitrixoa = table.getSelectionIndex();
						btnLuu.setVisible(true);
						btnHuy.setVisible(true);
						btnThem.setEnabled(false);
						btnChontep.setEnabled(false);
						textTentep.setText(tenfile);
						comboLoai.setText(loai);
						comboHedieuhanh.setText(hedieuhanh);
						textPhienban.setText(phienban);
					} catch (Exception ee) {

					}
				}
			}
		});

		// Button Lưu
		// *******************************************************************************************************************************************
		btnLuu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnLuu.isVisible()) {
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						try {
							thoigiancapnhat = thoigiancapnhat.substring(6, 10) + thoigiancapnhat.substring(3, 5)
									+ thoigiancapnhat.substring(0, 2);
						} catch (Exception ee) {
							thoigiancapnhat = "";
						}

						String deletePhieudenghi = "DELETE FROM CapNhatPhanMem WHERE TenFile=N'" + tenfile
								+ "' AND Loai='" + loai + "' AND HeDieuHanh='" + hedieuhanh + "' AND PhienBan='"
								+ phienban + "' AND ThoiGianCapNhat='" + thoigiancapnhat + "'";
						int resultdelete = statement.executeUpdate(deletePhieudenghi);
						if (resultdelete > 0) {
							// Xóa 1 dòng trên table
							table.remove(vitrixoa);
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
							btnLuu.setVisible(false);
							btnHuy.setVisible(false);
							btnThem.setEnabled(true);
							btnChontep.setEnabled(true);
							textTentep.setText("");
							comboLoai.setText("");
							comboHedieuhanh.setText("");
							textPhienban.setText("");
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
		});

		// Button Hủy
		// *******************************************************************************************************************************************
		btnHuy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnLuu.setVisible(false);
				btnHuy.setVisible(false);
				btnThem.setEnabled(true);
				btnChontep.setEnabled(true);
				textTentep.setText("");
				comboLoai.setText("");
				comboHedieuhanh.setText("");
				textPhienban.setText("");
			}
		});
	}
}
