package repairdevice;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class BaoSuaThietBiPhieuDeNghi {

	protected Shell shell;
	// Chuỗi kết nối
	private String db_url = "";
	private Connection conn = null;
	private Statement statement = null;
	private int ngonngu = 0; // 0: Vietnam, 1: English, 2: China
	private Table table;

	public static void main(String[] args) {
		try {
			BaoSuaThietBiPhieuDeNghi window = new BaoSuaThietBiPhieuDeNghi();
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
		createContents(ngonngu,db_url);
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
	protected void createContents(int ngonngu,String chuoiketnoi) {
		db_url=chuoiketnoi;

		shell = new Shell(SWT.CLOSE);
		shell.setSize(1275, 702);
		shell.setText("Phiếu đề nghị");
		shell.setLayout(new FillLayout());
		// shell.setMaximized(true);
		shell.setImage(SWTResourceManager.getImage(BaoSuaThietBiPhieuDeNghi.class, "/repairdevice/Images/repair.ico"));

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
		table.setBounds(21, 63, 1221, 578);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tbclMaphieu = new TableColumn(table, SWT.NONE);
		tbclMaphieu.setWidth(203);
		tbclMaphieu.setText("Mã Phiếu");

		TableColumn tbclTenphieudenghi = new TableColumn(table, SWT.NONE);
		tbclTenphieudenghi.setWidth(360);
		tbclTenphieudenghi.setText("Tên Phiếu Đề Nghị");

		TableColumn tbclTentep = new TableColumn(table, SWT.NONE);
		tbclTentep.setWidth(451);
		tbclTentep.setText("Tên Tệp");

		TableColumn tbclNgaycapnhat = new TableColumn(table, SWT.NONE);
		tbclNgaycapnhat.setWidth(174);
		tbclNgaycapnhat.setText("Ngày Cập Nhật");

		Button btnTaive = new Button(composite, SWT.NONE);
		btnTaive.setImage(
				SWTResourceManager.getImage(BaoSuaThietBiPhieuDeNghi.class, "/repairdevice/Images/download.png"));
		btnTaive.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		btnTaive.setFont(SWTResourceManager.getFont("Times New Roman", 13, SWT.NORMAL));
		btnTaive.setBounds(21, 27, 136, 30);
		btnTaive.setText("Tải về");

		CLabel lbDanhsach = new CLabel(composite, SWT.NONE);
		lbDanhsach.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lbDanhsach.setAlignment(SWT.CENTER);
		lbDanhsach.setFont(SWTResourceManager.getFont("Times New Roman", 25, SWT.NORMAL));
		lbDanhsach.setBounds(230, 19, 829, 38);
		lbDanhsach.setText("Danh sách Phiếu đề nghị từ phòng IT");

		// Xử lý ngôn ngữ
		// ------------------------------------------------------------------
		if (ngonngu == 0) {
			shell.setText("Phiếu đề nghị");
			tbclMaphieu.setText("Mã Phiếu");
			tbclTenphieudenghi.setText("Tên Phiếu Đề Nghị");
			tbclTentep.setText("Tên Tệp");
			tbclNgaycapnhat.setText("Ngày Cập Nhật");
			lbDanhsach.setText("Danh sách Phiếu đề nghị từ phòng IT");
			btnTaive.setText("Tải về");
		} else if (ngonngu == 1) {
			shell.setText("Suggestion form");
			tbclMaphieu.setText("Form Code");
			tbclTenphieudenghi.setText("Suggestion Form Name");
			tbclTentep.setText("File Name");
			tbclNgaycapnhat.setText("Update Date");
			lbDanhsach.setText("List of suggestion form from the IT department");
			btnTaive.setText("Download");
		} else if (ngonngu == 2) {
			shell.setText("建議表格");
			tbclMaphieu.setText("表格代碼");
			tbclTenphieudenghi.setText("建議表格名稱");
			tbclTentep.setText("文件名");
			tbclNgaycapnhat.setText("更新日期");
			lbDanhsach.setText("IT部門的建議表清單");
			btnTaive.setText("下載");
		}

		// Lấy dữ liệu cho table
		// --------------------------------------------------------
		try {
			conn = DriverManager.getConnection(db_url);
			statement = conn.createStatement();

			try {
				String select = "SELECT MaForm,TenForm,TenFile,NgayCapNhat FROM PhieuDeNghi";
				ResultSet result = statement.executeQuery(select);
				table.removeAll();

				while (result.next()) {
					String thoigiancapnhat = result.getString(4);
					try {
						thoigiancapnhat = thoigiancapnhat.substring(8, 10) + "/" + thoigiancapnhat.substring(5, 7)
								+ "/" + thoigiancapnhat.substring(0, 4);
					} catch (NullPointerException ne) {
						thoigiancapnhat = "";
					}
					
					TableItem item = new TableItem(table, SWT.NONE);
					item.setText(new String[] { result.getString(1), result.getString(2), result.getString(3),
							thoigiancapnhat });
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

		// Button Tải về
		// ---------------------------------------------------------------------
		btnTaive.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					conn = DriverManager.getConnection(db_url);
					statement = conn.createStatement();

					try {
						// Lấy cột Ma Phieu
						TableItem[] itemtable = table.getSelection();
						String maphieu = itemtable[0].getText();

						String select = "SELECT MaForm,TenFile,FileCode FROM PhieuDeNghi WHERE MaForm='"+maphieu+"'";
						
						
						String[] FILTER_NAMES = { "PDF (*.pdf)", "All Files (*.*)" };
						// đuôi file có thể mở
						String[] FILTER_EXTS = { "*.pdf", "*.*" };
						
						FileDialog dlg = new FileDialog(shell, SWT.SAVE);
				        dlg.setFilterNames(FILTER_NAMES);
				        dlg.setFilterExtensions(FILTER_EXTS);
				        dlg.setFileName(itemtable[0].getText(2));
				        String filename = dlg.open(); // ten file luu
				        if (filename != null) {
				        	ResultSet result = statement.executeQuery(select);
							while (result.next()) {
								FileOutputStream outputStream = new FileOutputStream(filename);
					            byte[] strToBytes = result.getBytes(3);
					            outputStream.write(strToBytes);
					            outputStream.close();
							}
							result.close();
				        }

					} catch (Exception ex) {
						MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
						thongbao.setText("Error");
						thongbao.setMessage(ex.toString());
						thongbao.open();
					}

				} catch (SQLException se) {
					MessageBox thongbao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
					thongbao.setText("Thông báo");
					thongbao.setMessage("Lỗi kết nối!");
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
