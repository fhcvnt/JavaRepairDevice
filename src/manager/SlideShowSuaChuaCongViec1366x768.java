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
	import org.eclipse.swt.events.KeyAdapter;
	import org.eclipse.swt.events.KeyEvent;
	import org.eclipse.swt.custom.CLabel;
	import org.eclipse.wb.swt.SWTResourceManager;
	import org.eclipse.swt.widgets.Text;

	public class SlideShowSuaChuaCongViec1366x768 {

		protected Shell shell;
		private String db_url = "";
		private Connection conn = null;
		private Statement statement = null;
		private Statement state = null;

		private Text textCongviec1;
		private Text textGhichu1;
		private Text textCongviec2;
		private Text textGhichu2;
		private Text textCongviec3;
		private Text textGhichu3;
		private Text textGhichu4;
		private Text textCongviec4;
		private Text textCongviec5;
		private Text textGhichu5;
		private Text textGhichu6;
		private Text textCongviec6;
		private Text textCongviec7;
		private Text textGhichu7;
		private int thoigiantimer = 20000; // thoi gian 20 giay hien thong bao, thay doi thong bao mot lan, su dung Timer

		public static void main(String[] args) {
			try {
				SlideShowSuaChuaCongViec1366x768 window = new SlideShowSuaChuaCongViec1366x768();
				window.createContents(0,"jdbc:sqlserver://192.168.30.123;databaseName=SuaChuaThietBi;user=sa;password=Killua21608");
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
			shell = new Shell();
			shell.setImage(SWTResourceManager.getImage(SlideShowSuaChuaCongViec1366x768.class, "/manager/Images/repair256.ico"));
			shell.setSize(1366, 808);
			shell.setText("Slide show");
			shell.setMaximized(true);
			shell.setFullScreen(true);
			shell.setLayout(new FillLayout(SWT.HORIZONTAL));

			Composite compositetop = new Composite(shell, SWT.NONE);
			compositetop.setBackground(SWTResourceManager.getColor(128, 128, 128));
			compositetop.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));

			CLabel lbNhanvientrucphong = new CLabel(compositetop, SWT.NONE);
			lbNhanvientrucphong.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbNhanvientrucphong.setAlignment(SWT.RIGHT);
			lbNhanvientrucphong.setBackground(SWTResourceManager.getColor(128, 128, 128));
			lbNhanvientrucphong.setText("Ng?????i Tr???c Ph??ng:");
			lbNhanvientrucphong.setFont(SWTResourceManager.getFont("Times New Roman", 30, SWT.BOLD));
			lbNhanvientrucphong.setBounds(26, 0, 426, 70);

			CLabel lbHoten = new CLabel(compositetop, SWT.NONE);
			lbHoten.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbHoten.setBackground(SWTResourceManager.getColor(128, 128, 128));
			lbHoten.setText("T?? Ng???c Tr??");
			lbHoten.setFont(SWTResourceManager.getFont("Times New Roman", 30, SWT.BOLD));
			lbHoten.setBounds(507, 0, 369, 70);

			CLabel lbNgay = new CLabel(compositetop, SWT.NONE);
			lbNgay.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbNgay.setAlignment(SWT.RIGHT);
			lbNgay.setBackground(SWTResourceManager.getColor(128, 128, 128));
			lbNgay.setText("Ng??y:");
			lbNgay.setFont(SWTResourceManager.getFont("Times New Roman", 30, SWT.BOLD));
			lbNgay.setBounds(894, 0, 152, 70);

			CLabel laNgaychitiet = new CLabel(compositetop, SWT.NONE);
			laNgaychitiet.setForeground(SWTResourceManager.getColor(255, 255, 255));
			laNgaychitiet.setBackground(SWTResourceManager.getColor(128, 128, 128));
			laNgaychitiet.setFont(SWTResourceManager.getFont("Times New Roman", 30, SWT.BOLD));
			laNgaychitiet.setBounds(1082, 0, 229, 70);
			laNgaychitiet.setText("");
			// L???y ng??y th??ng hi???n t???i
			String ngay = "0" + java.time.LocalDateTime.now().getDayOfMonth();
			ngay = ngay.substring(ngay.length() - 2);
			String thang = "0" + (java.time.LocalDateTime.now().getMonthValue());
			thang = thang.substring(thang.length() - 2);
			String nam = "" + java.time.LocalDateTime.now().getYear();
			String today = ngay + "/" + thang + "/" + nam;
			laNgaychitiet.setText(today);

			CLabel lbCongviec = new CLabel(compositetop, SWT.BORDER);
			lbCongviec.setText("C??ng Vi???c");
			lbCongviec.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbCongviec.setFont(SWTResourceManager.getFont("Times New Roman", 22, SWT.BOLD));
			lbCongviec.setBackground(SWTResourceManager.getColor(128, 128, 128));
			lbCongviec.setAlignment(SWT.CENTER);
			lbCongviec.setBounds(0, 70, 305, 60);

			CLabel lbBatdau = new CLabel(compositetop, SWT.BORDER);
			lbBatdau.setText("B???t ?????u");
			lbBatdau.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbBatdau.setFont(SWTResourceManager.getFont("Times New Roman", 22, SWT.BOLD));
			lbBatdau.setBackground(SWTResourceManager.getColor(128, 128, 128));
			lbBatdau.setAlignment(SWT.CENTER);
			lbBatdau.setBounds(305, 70, 140, 60);

			CLabel lbKetthuc = new CLabel(compositetop, SWT.BORDER);
			lbKetthuc.setText("K???t Th??c");
			lbKetthuc.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbKetthuc.setFont(SWTResourceManager.getFont("Times New Roman", 22, SWT.BOLD));
			lbKetthuc.setBackground(SWTResourceManager.getColor(128, 128, 128));
			lbKetthuc.setAlignment(SWT.CENTER);
			lbKetthuc.setBounds(445, 70, 140, 60);

			CLabel lbNguoithuchien = new CLabel(compositetop, SWT.BORDER);
			lbNguoithuchien.setText("Ng?????i Th???c Hi???n");
			lbNguoithuchien.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbNguoithuchien.setFont(SWTResourceManager.getFont("Times New Roman", 22, SWT.BOLD));
			lbNguoithuchien.setBackground(SWTResourceManager.getColor(128, 128, 128));
			lbNguoithuchien.setAlignment(SWT.CENTER);
			lbNguoithuchien.setBounds(585, 70, 238, 60);

			CLabel lbGhiChu = new CLabel(compositetop, SWT.BORDER);
			lbGhiChu.setText("Ghi Ch??");
			lbGhiChu.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbGhiChu.setFont(SWTResourceManager.getFont("Times New Roman", 22, SWT.BOLD));
			lbGhiChu.setBackground(SWTResourceManager.getColor(128, 128, 128));
			lbGhiChu.setAlignment(SWT.CENTER);
			lbGhiChu.setBounds(823, 70, 331, 60);

			CLabel lbTrangthai = new CLabel(compositetop, SWT.BORDER);
			lbTrangthai.setText("Tr???ng Th??i");
			lbTrangthai.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbTrangthai.setFont(SWTResourceManager.getFont("Times New Roman", 22, SWT.BOLD));
			lbTrangthai.setBackground(SWTResourceManager.getColor(128, 128, 128));
			lbTrangthai.setAlignment(SWT.CENTER);
			lbTrangthai.setBounds(1154, 70, 196, 60);

			Composite compositebottom = new Composite(compositetop, SWT.NONE);
			compositebottom.setBounds(0, 130, 1350, 638);

			textCongviec1 = new Text(compositebottom, SWT.WRAP);
			textCongviec1.setEditable(false);
			textCongviec1.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			textCongviec1.setForeground(SWTResourceManager.getColor(255, 255, 255));
			textCongviec1.setBackground(SWTResourceManager.getColor(95, 158, 160));
			textCongviec1.setBounds(0, 0, 304, 90);

			CLabel lbBatdau1 = new CLabel(compositebottom, SWT.NONE);
			lbBatdau1.setAlignment(SWT.CENTER);
			lbBatdau1.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbBatdau1.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbBatdau1.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbBatdau1.setBounds(306, 0, 138, 90);
			lbBatdau1.setText("");

			CLabel lbKetthuc1 = new CLabel(compositebottom, SWT.NONE);
			lbKetthuc1.setText("");
			lbKetthuc1.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbKetthuc1.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbKetthuc1.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbKetthuc1.setAlignment(SWT.CENTER);
			lbKetthuc1.setBounds(446, 0, 138, 90);

			CLabel lbNguoithuchien1 = new CLabel(compositebottom, SWT.NONE);
			lbNguoithuchien1.setText("");
			lbNguoithuchien1.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbNguoithuchien1.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbNguoithuchien1.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbNguoithuchien1.setAlignment(SWT.CENTER);
			lbNguoithuchien1.setBounds(586, 0, 236, 90);

			textGhichu1 = new Text(compositebottom, SWT.CENTER);
			textGhichu1.setEditable(false);
			textGhichu1.setForeground(SWTResourceManager.getColor(255, 255, 255));
			textGhichu1.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			textGhichu1.setBackground(SWTResourceManager.getColor(95, 158, 160));
			textGhichu1.setBounds(824, 0, 329, 90);

			CLabel lbTrangthai1 = new CLabel(compositebottom, SWT.NONE);
			lbTrangthai1.setText("");
			lbTrangthai1.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbTrangthai1.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbTrangthai1.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbTrangthai1.setAlignment(SWT.CENTER);
			lbTrangthai1.setBounds(1155, 0, 195, 90);

			textCongviec2 = new Text(compositebottom, SWT.WRAP);
			textCongviec2.setEditable(false);
			textCongviec2.setForeground(SWTResourceManager.getColor(255, 255, 255));
			textCongviec2.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			textCongviec2.setBackground(SWTResourceManager.getColor(95, 158, 160));
			textCongviec2.setBounds(0, 92, 304, 90);

			CLabel lbBatdau2 = new CLabel(compositebottom, SWT.NONE);
			lbBatdau2.setText("");
			lbBatdau2.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbBatdau2.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbBatdau2.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbBatdau2.setAlignment(SWT.CENTER);
			lbBatdau2.setBounds(306, 92, 138, 90);

			CLabel lbKetthuc2 = new CLabel(compositebottom, SWT.NONE);
			lbKetthuc2.setText("");
			lbKetthuc2.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbKetthuc2.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbKetthuc2.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbKetthuc2.setAlignment(SWT.CENTER);
			lbKetthuc2.setBounds(446, 92, 138, 90);

			CLabel lbNguoithuchien2 = new CLabel(compositebottom, SWT.NONE);
			lbNguoithuchien2.setText("");
			lbNguoithuchien2.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbNguoithuchien2.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbNguoithuchien2.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbNguoithuchien2.setAlignment(SWT.CENTER);
			lbNguoithuchien2.setBounds(586, 92, 236, 90);

			textGhichu2 = new Text(compositebottom, SWT.CENTER);
			textGhichu2.setEditable(false);
			textGhichu2.setForeground(SWTResourceManager.getColor(255, 255, 255));
			textGhichu2.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			textGhichu2.setBackground(SWTResourceManager.getColor(95, 158, 160));
			textGhichu2.setBounds(824, 92, 329, 90);

			CLabel lbTrangthai2 = new CLabel(compositebottom, SWT.NONE);
			lbTrangthai2.setText("");
			lbTrangthai2.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbTrangthai2.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbTrangthai2.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbTrangthai2.setAlignment(SWT.CENTER);
			lbTrangthai2.setBounds(1155, 92, 195, 90);

			textCongviec3 = new Text(compositebottom, SWT.WRAP);
			textCongviec3.setEditable(false);
			textCongviec3.setForeground(SWTResourceManager.getColor(255, 255, 255));
			textCongviec3.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			textCongviec3.setBackground(SWTResourceManager.getColor(95, 158, 160));
			textCongviec3.setBounds(0, 184, 304, 90);

			CLabel lbBatdau3 = new CLabel(compositebottom, SWT.NONE);
			lbBatdau3.setText("");
			lbBatdau3.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbBatdau3.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbBatdau3.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbBatdau3.setAlignment(SWT.CENTER);
			lbBatdau3.setBounds(306, 184, 138, 90);

			CLabel lbKetthuc3 = new CLabel(compositebottom, SWT.NONE);
			lbKetthuc3.setText("");
			lbKetthuc3.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbKetthuc3.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbKetthuc3.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbKetthuc3.setAlignment(SWT.CENTER);
			lbKetthuc3.setBounds(446, 184, 138, 90);

			CLabel lbNguoithuchien3 = new CLabel(compositebottom, SWT.NONE);
			lbNguoithuchien3.setText("");
			lbNguoithuchien3.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbNguoithuchien3.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbNguoithuchien3.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbNguoithuchien3.setAlignment(SWT.CENTER);
			lbNguoithuchien3.setBounds(586, 184, 236, 90);

			textGhichu3 = new Text(compositebottom, SWT.CENTER);
			textGhichu3.setEditable(false);
			textGhichu3.setForeground(SWTResourceManager.getColor(255, 255, 255));
			textGhichu3.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			textGhichu3.setBackground(SWTResourceManager.getColor(95, 158, 160));
			textGhichu3.setBounds(824, 184, 329, 90);

			CLabel lbTrangthai3 = new CLabel(compositebottom, SWT.NONE);
			lbTrangthai3.setText("");
			lbTrangthai3.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbTrangthai3.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbTrangthai3.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbTrangthai3.setAlignment(SWT.CENTER);
			lbTrangthai3.setBounds(1155, 184, 195, 90);

			CLabel lbTrangthai4 = new CLabel(compositebottom, SWT.NONE);
			lbTrangthai4.setText("");
			lbTrangthai4.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbTrangthai4.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbTrangthai4.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbTrangthai4.setAlignment(SWT.CENTER);
			lbTrangthai4.setBounds(1155, 276, 195, 90);

			textGhichu4 = new Text(compositebottom, SWT.CENTER);
			textGhichu4.setEditable(false);
			textGhichu4.setForeground(SWTResourceManager.getColor(255, 255, 255));
			textGhichu4.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			textGhichu4.setBackground(SWTResourceManager.getColor(95, 158, 160));
			textGhichu4.setBounds(824, 276, 329, 90);

			CLabel lbNguoithuchien4 = new CLabel(compositebottom, SWT.NONE);
			lbNguoithuchien4.setText("");
			lbNguoithuchien4.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbNguoithuchien4.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbNguoithuchien4.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbNguoithuchien4.setAlignment(SWT.CENTER);
			lbNguoithuchien4.setBounds(586, 276, 236, 90);

			CLabel lbKetthuc4 = new CLabel(compositebottom, SWT.NONE);
			lbKetthuc4.setText("");
			lbKetthuc4.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbKetthuc4.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbKetthuc4.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbKetthuc4.setAlignment(SWT.CENTER);
			lbKetthuc4.setBounds(446, 276, 138, 90);

			CLabel lbBatdau4 = new CLabel(compositebottom, SWT.NONE);
			lbBatdau4.setText("");
			lbBatdau4.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbBatdau4.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbBatdau4.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbBatdau4.setAlignment(SWT.CENTER);
			lbBatdau4.setBounds(306, 276, 138, 90);

			textCongviec4 = new Text(compositebottom, SWT.WRAP);
			textCongviec4.setEditable(false);
			textCongviec4.setForeground(SWTResourceManager.getColor(255, 255, 255));
			textCongviec4.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			textCongviec4.setBackground(SWTResourceManager.getColor(95, 158, 160));
			textCongviec4.setBounds(0, 276, 304, 90);

			textCongviec5 = new Text(compositebottom, SWT.WRAP);
			textCongviec5.setEditable(false);
			textCongviec5.setForeground(SWTResourceManager.getColor(255, 255, 255));
			textCongviec5.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			textCongviec5.setBackground(SWTResourceManager.getColor(95, 158, 160));
			textCongviec5.setBounds(0, 368, 304, 90);

			CLabel lbBatdau5 = new CLabel(compositebottom, SWT.NONE);
			lbBatdau5.setText("");
			lbBatdau5.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbBatdau5.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbBatdau5.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbBatdau5.setAlignment(SWT.CENTER);
			lbBatdau5.setBounds(306, 368, 138, 90);

			CLabel lbKetthuc5 = new CLabel(compositebottom, SWT.NONE);
			lbKetthuc5.setText("");
			lbKetthuc5.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbKetthuc5.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbKetthuc5.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbKetthuc5.setAlignment(SWT.CENTER);
			lbKetthuc5.setBounds(446, 368, 138, 90);

			CLabel lbNguoithuchien5 = new CLabel(compositebottom, SWT.NONE);
			lbNguoithuchien5.setText("");
			lbNguoithuchien5.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbNguoithuchien5.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbNguoithuchien5.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbNguoithuchien5.setAlignment(SWT.CENTER);
			lbNguoithuchien5.setBounds(586, 368, 236, 90);

			textGhichu5 = new Text(compositebottom, SWT.CENTER);
			textGhichu5.setEditable(false);
			textGhichu5.setForeground(SWTResourceManager.getColor(255, 255, 255));
			textGhichu5.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			textGhichu5.setBackground(SWTResourceManager.getColor(95, 158, 160));
			textGhichu5.setBounds(824, 368, 329, 90);

			CLabel lbTrangthai5 = new CLabel(compositebottom, SWT.NONE);
			lbTrangthai5.setText("");
			lbTrangthai5.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbTrangthai5.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbTrangthai5.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbTrangthai5.setAlignment(SWT.CENTER);
			lbTrangthai5.setBounds(1155, 368, 195, 90);

			CLabel lbTrangthai6 = new CLabel(compositebottom, SWT.NONE);
			lbTrangthai6.setText("");
			lbTrangthai6.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbTrangthai6.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbTrangthai6.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbTrangthai6.setAlignment(SWT.CENTER);
			lbTrangthai6.setBounds(1155, 460, 195, 90);

			textGhichu6 = new Text(compositebottom, SWT.CENTER);
			textGhichu6.setEditable(false);
			textGhichu6.setForeground(SWTResourceManager.getColor(255, 255, 255));
			textGhichu6.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			textGhichu6.setBackground(SWTResourceManager.getColor(95, 158, 160));
			textGhichu6.setBounds(824, 460, 329, 90);

			CLabel lbNguoithuchien6 = new CLabel(compositebottom, SWT.NONE);
			lbNguoithuchien6.setText("");
			lbNguoithuchien6.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbNguoithuchien6.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbNguoithuchien6.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbNguoithuchien6.setAlignment(SWT.CENTER);
			lbNguoithuchien6.setBounds(586, 460, 236, 90);

			CLabel lbKetthuc6 = new CLabel(compositebottom, SWT.NONE);
			lbKetthuc6.setText("");
			lbKetthuc6.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbKetthuc6.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbKetthuc6.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbKetthuc6.setAlignment(SWT.CENTER);
			lbKetthuc6.setBounds(446, 460, 138, 90);

			CLabel lbBatdau6 = new CLabel(compositebottom, SWT.NONE);
			lbBatdau6.setText("");
			lbBatdau6.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbBatdau6.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbBatdau6.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbBatdau6.setAlignment(SWT.CENTER);
			lbBatdau6.setBounds(306, 460, 138, 90);

			textCongviec6 = new Text(compositebottom, SWT.WRAP);
			textCongviec6.setEditable(false);
			textCongviec6.setForeground(SWTResourceManager.getColor(255, 255, 255));
			textCongviec6.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			textCongviec6.setBackground(SWTResourceManager.getColor(95, 158, 160));
			textCongviec6.setBounds(0, 460, 304, 90);

			textCongviec7 = new Text(compositebottom, SWT.WRAP);
			textCongviec7.setEditable(false);
			textCongviec7.setForeground(SWTResourceManager.getColor(255, 255, 255));
			textCongviec7.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			textCongviec7.setBackground(SWTResourceManager.getColor(95, 158, 160));
			textCongviec7.setBounds(0, 552, 304, 90);

			CLabel lbBatdau7 = new CLabel(compositebottom, SWT.NONE);
			lbBatdau7.setText("");
			lbBatdau7.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbBatdau7.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbBatdau7.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbBatdau7.setAlignment(SWT.CENTER);
			lbBatdau7.setBounds(306, 552, 138, 90);

			CLabel lbKetthuc7 = new CLabel(compositebottom, SWT.NONE);
			lbKetthuc7.setText("");
			lbKetthuc7.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbKetthuc7.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbKetthuc7.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbKetthuc7.setAlignment(SWT.CENTER);
			lbKetthuc7.setBounds(446, 552, 138, 90);

			CLabel lbNguoithuchien7 = new CLabel(compositebottom, SWT.NONE);
			lbNguoithuchien7.setText("");
			lbNguoithuchien7.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbNguoithuchien7.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbNguoithuchien7.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbNguoithuchien7.setAlignment(SWT.CENTER);
			lbNguoithuchien7.setBounds(586, 552, 236, 90);

			textGhichu7 = new Text(compositebottom, SWT.CENTER);
			textGhichu7.setEditable(false);
			textGhichu7.setForeground(SWTResourceManager.getColor(255, 255, 255));
			textGhichu7.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			textGhichu7.setBackground(SWTResourceManager.getColor(95, 158, 160));
			textGhichu7.setBounds(824, 552, 329, 90);

			CLabel lbTrangthai7 = new CLabel(compositebottom, SWT.NONE);
			lbTrangthai7.setText("");
			lbTrangthai7.setForeground(SWTResourceManager.getColor(255, 255, 255));
			lbTrangthai7.setFont(SWTResourceManager.getFont("Times New Roman", 20, SWT.NORMAL));
			lbTrangthai7.setBackground(SWTResourceManager.getColor(95, 158, 160));
			lbTrangthai7.setAlignment(SWT.CENTER);
			lbTrangthai7.setBounds(1155, 552, 195, 90);

			// ng??n ng???
			// **********************************************************************************************************************
			if (ngonngu == 0) {
				lbNhanvientrucphong.setText("Ng?????i Tr???c Ph??ng:");
				lbNgay.setText("Ng??y:");
				lbCongviec.setText("C??ng Vi???c");
				lbBatdau.setText("B???t ?????u");
				lbKetthuc.setText("K???t Th??c");
				lbNguoithuchien.setText("Ng?????i Th???c Hi???n");
				lbGhiChu.setText("Ghi Ch??");
				lbTrangthai.setText("Tr???ng Th??i");
			} else if (ngonngu == 1) {
				lbNhanvientrucphong.setText("Person On Duty:");
				lbNgay.setText("Date:");
				lbCongviec.setText("Work");
				lbBatdau.setText("Start");
				lbKetthuc.setText("End");
				lbNguoithuchien.setText("Implementer");
				lbGhiChu.setText("Note");
				lbTrangthai.setText("Status");
			} else if (ngonngu == 2) {
				lbNhanvientrucphong.setText("????????????:");
				lbNgay.setText("??????:");
				lbCongviec.setText("??????");
				lbBatdau.setText("??????");
				lbKetthuc.setText("??????");
				lbNguoithuchien.setText("?????????");
				lbGhiChu.setText("??????");
				lbTrangthai.setText("??????");
			}

			// textCongviec2.setBackground(SWTResourceManager.getColor(95, 158, 160));
			// lbNguoithuchien1.setBackground(SWTResourceManager.getColor(255,0,0));
			// lay du lieu thong bao lan dau
			// **********************************************************************************************************************
			try {
				conn = DriverManager.getConnection(db_url);
				statement = conn.createStatement();

				textCongviec1.setText("");
				textCongviec1.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbBatdau1.setText("");
				lbBatdau1.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbKetthuc1.setText("");
				lbKetthuc1.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbNguoithuchien1.setText("");
				lbNguoithuchien1.setBackground(SWTResourceManager.getColor(95, 158, 160));
				textGhichu1.setText("");
				textGhichu1.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbTrangthai1.setText("");
				lbTrangthai1.setBackground(SWTResourceManager.getColor(95, 158, 160));
				textCongviec2.setText("");
				textCongviec2.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbBatdau2.setText("");
				lbBatdau2.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbKetthuc2.setText("");
				lbKetthuc2.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbNguoithuchien2.setText("");
				lbNguoithuchien2.setBackground(SWTResourceManager.getColor(95, 158, 160));
				textGhichu2.setText("");
				textGhichu2.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbTrangthai2.setText("");
				lbTrangthai2.setBackground(SWTResourceManager.getColor(95, 158, 160));
				textCongviec3.setText("");
				textCongviec3.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbBatdau3.setText("");
				lbBatdau3.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbKetthuc3.setText("");
				lbKetthuc3.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbNguoithuchien3.setText("");
				lbNguoithuchien3.setBackground(SWTResourceManager.getColor(95, 158, 160));
				textGhichu3.setText("");
				textGhichu3.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbTrangthai3.setText("");
				lbTrangthai3.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbTrangthai4.setText("");
				lbTrangthai4.setBackground(SWTResourceManager.getColor(95, 158, 160));
				textGhichu4.setText("");
				textGhichu4.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbNguoithuchien4.setText("");
				lbNguoithuchien4.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbKetthuc4.setText("");
				lbKetthuc4.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbBatdau4.setText("");
				lbBatdau4.setBackground(SWTResourceManager.getColor(95, 158, 160));
				textCongviec4.setText("");
				textCongviec4.setBackground(SWTResourceManager.getColor(95, 158, 160));
				textCongviec5.setText("");
				textCongviec5.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbBatdau5.setText("");
				lbBatdau5.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbKetthuc5.setText("");
				lbKetthuc5.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbNguoithuchien5.setText("");
				lbNguoithuchien5.setBackground(SWTResourceManager.getColor(95, 158, 160));
				textGhichu5.setText("");
				textGhichu5.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbTrangthai5.setText("");
				lbTrangthai5.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbTrangthai6.setText("");
				lbTrangthai6.setBackground(SWTResourceManager.getColor(95, 158, 160));
				textGhichu6.setText("");
				textGhichu6.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbNguoithuchien6.setText("");
				lbNguoithuchien6.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbKetthuc6.setText("");
				lbKetthuc6.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbBatdau6.setText("");
				lbBatdau6.setBackground(SWTResourceManager.getColor(95, 158, 160));
				textCongviec6.setText("");
				textCongviec6.setBackground(SWTResourceManager.getColor(95, 158, 160));
				textCongviec7.setText("");
				textCongviec7.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbBatdau7.setText("");
				lbBatdau7.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbKetthuc7.setText("");
				lbKetthuc7.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbNguoithuchien7.setText("");
				lbNguoithuchien7.setBackground(SWTResourceManager.getColor(95, 158, 160));
				textGhichu7.setText("");
				textGhichu7.setBackground(SWTResourceManager.getColor(95, 158, 160));
				lbTrangthai7.setText("");
				lbTrangthai7.setBackground(SWTResourceManager.getColor(95, 158, 160));

				String select = "SELECT TOP 7 SlideShowThongBao.MaCongViec,CongViec.TenCongViec,CongViec.ThoiGianBatDau,CongViec.ThoiGianKetThuc,CongViec.NoiDung,CongViec.TienDo,SlideShowThongBao.PhanBiet,NguoiDung.TenNguoiDung,DonVi.TenDonVi,ThietBi.TenThietBi,BaoSuaThietBi.ThoiGianBao,BaoSuaThietBi.ThoiGianCapNhat,ND.TenNguoiDung,BaoSuaThietBi.NoiDung,CASE BaoSuaThietBi.TrangThai WHEN 0 THEN N'Ch??? duy???t' WHEN 1 THEN N'???? duy???t' WHEN 2 THEN N'???? s???a' WHEN 3 THEN N'B??o l???i' WHEN 4 THEN N'Ch??a ho??n th??nh' ELSE N'Kh??ng' END AS TrangThai,BaoSuaThietBi.DaHoanTat FROM SlideShowThongBao  LEFT JOIN BaoSuaThietBi ON BaoSuaThietBi.PhanBiet=SlideShowThongBao.PhanBiet LEFT JOIN CongViec ON SlideShowThongBao.MaCongViec=CongViec.MaCongViec LEFT JOIN NguoiDung ON NguoiDung.MaNguoiDung=BaoSuaThietBi.MaNguoiDung LEFT JOIN ThietBi ON ThietBi.MaThietBi=BaoSuaThietBi.MaThietBi LEFT JOIN DonVi ON dbo.DonVi.MaDonVi=NguoiDung.MaDonVi LEFT JOIN NguoiDung AS ND ON ND.MaNguoiDung=BaoSuaThietBi.NguoiDuocPhanCong WHERE HoanThanh=0 ORDER BY SlideShowThongBao.ThoiGianCapNhat ASC";
				ResultSet result = statement.executeQuery(select);
				int i = 1;
				while (result.next()) {

					if (i == 1) {

						try {
							if ((result.getString(7) + "").compareToIgnoreCase("null") != 0) {
								// cap nhat du lieu
								String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
										+ result.getString(7);
								Statement statementcapnhat = conn.createStatement();
								statementcapnhat.executeUpdate(capnhat);
								statementcapnhat.close();
								// du lieu Bao Sua Thiet Bi
								textCongviec1.setText(result.getString(8) + " (" + result.getString(9) + ")"
										+ " b??o s???a thi???t b???: " + result.getString(10));
								lbBatdau1.setText(result.getString(11).substring(11, 19));
								lbNguoithuchien1.setText(result.getString(13));
								textGhichu1.setText(result.getString(14));
								lbTrangthai1.setText(result.getString(15));
								if ((result.getString(15) + "").compareToIgnoreCase("???? s???a") == 0) {
									textCongviec1.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbBatdau1.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbKetthuc1.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbNguoithuchien1.setBackground(SWTResourceManager.getColor(255, 0, 0));
									textGhichu1.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbTrangthai1.setBackground(SWTResourceManager.getColor(255, 0, 0));

									lbKetthuc1.setText(result.getString(12).substring(11, 19));
								}

							} else {
								// cap nhat du lieu
								String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE MaCongViec='"
										+ result.getString(1) + "'";
								Statement statementcapnhat = conn.createStatement();
								statementcapnhat.executeUpdate(capnhat);
								statementcapnhat.close();
								// du lieu cong viec
								textCongviec1.setText(result.getString(2));
								String batdau = result.getString(3);
								batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
										+ batdau.substring(0, 4);
								lbBatdau1.setText(batdau);
								String ketthuc = result.getString(3);
								ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
										+ ketthuc.substring(0, 4);
								lbKetthuc1.setText(ketthuc);
								textGhichu1.setText(result.getString(5));
								lbTrangthai1.setText(result.getString(6));
								String selectnguoithuchien = "SELECT NguoiDung.TenNguoiDung FROM PhanCongCongViec,CongViec,NguoiDung WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiDuocPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
										+ result.getString(1) + "'";
								state = conn.createStatement();
								ResultSet ketqua = state.executeQuery(selectnguoithuchien);
								String nguoithuchien = "";
								while (ketqua.next()) {
									if (nguoithuchien.isEmpty()) {
										nguoithuchien = ketqua.getString(1);
									} else {
										nguoithuchien = nguoithuchien + "\n" + ketqua.getString(1);
									}
								}
								lbNguoithuchien1.setText(nguoithuchien);
								ketqua.close();
							}
						} catch (Exception ne) {
							// System.out.println(ne.toString());
						}
					}

					// i=2
					if (i == 2) {
						try {
							if ((result.getString(7) + "").compareToIgnoreCase("null") != 0) {
								// cap nhat du lieu
								String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
										+ result.getString(7);
								Statement statementcapnhat = conn.createStatement();
								statementcapnhat.executeUpdate(capnhat);
								statementcapnhat.close();
								// du lieu Bao Sua Thiet Bi
								textCongviec2.setText(result.getString(8) + " (" + result.getString(9) + ")"
										+ " b??o s???a thi???t b???: " + result.getString(10));
								lbBatdau2.setText(result.getString(11).substring(11, 19));
								lbNguoithuchien2.setText(result.getString(13));
								textGhichu2.setText(result.getString(14));
								lbTrangthai2.setText(result.getString(15));
								if ((result.getString(15) + "").compareToIgnoreCase("???? s???a") == 0) {
									textCongviec2.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbBatdau2.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbKetthuc2.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbNguoithuchien2.setBackground(SWTResourceManager.getColor(255, 0, 0));
									textGhichu2.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbTrangthai2.setBackground(SWTResourceManager.getColor(255, 0, 0));

									lbKetthuc2.setText(result.getString(12).substring(11, 19));
								}

							} else {
								// cap nhat du lieu
								String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE MaCongViec='"
										+ result.getString(1) + "'";
								Statement statementcapnhat = conn.createStatement();
								statementcapnhat.executeUpdate(capnhat);
								statementcapnhat.close();
								// du lieu cong viec
								textCongviec2.setText(result.getString(2));
								String batdau = result.getString(3);
								batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
										+ batdau.substring(0, 4);
								lbBatdau2.setText(batdau);
								String ketthuc = result.getString(3);
								ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
										+ ketthuc.substring(0, 4);
								lbKetthuc2.setText(ketthuc);
								textGhichu2.setText(result.getString(5));
								lbTrangthai2.setText(result.getString(6));
								String selectnguoithuchien = "SELECT NguoiDung.TenNguoiDung FROM PhanCongCongViec,CongViec,NguoiDung WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiDuocPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
										+ result.getString(1) + "'";
								state = conn.createStatement();
								ResultSet ketqua = state.executeQuery(selectnguoithuchien);
								String nguoithuchien = "";
								while (ketqua.next()) {
									if (nguoithuchien.isEmpty()) {
										nguoithuchien = ketqua.getString(1);
									} else {
										nguoithuchien = nguoithuchien + "\n" + ketqua.getString(1);
									}
								}
								lbNguoithuchien2.setText(nguoithuchien);
								ketqua.close();
							}
						} catch (Exception ne) {
							// System.out.println(ne.toString());
						}
					}
					// i=3
					if (i == 3) {
						try {
							if ((result.getString(7) + "").compareToIgnoreCase("null") != 0) {
								// cap nhat du lieu
								String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
										+ result.getString(7);
								Statement statementcapnhat = conn.createStatement();
								statementcapnhat.executeUpdate(capnhat);
								statementcapnhat.close();
								// du lieu Bao Sua Thiet Bi
								textCongviec3.setText(result.getString(8) + " (" + result.getString(9) + ")"
										+ " b??o s???a thi???t b???: " + result.getString(10));
								lbBatdau3.setText(result.getString(11).substring(11, 19));
								lbNguoithuchien3.setText(result.getString(13));
								textGhichu3.setText(result.getString(14));
								lbTrangthai3.setText(result.getString(15));
								if ((result.getString(15) + "").compareToIgnoreCase("???? s???a") == 0) {
									textCongviec3.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbBatdau3.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbKetthuc3.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbNguoithuchien3.setBackground(SWTResourceManager.getColor(255, 0, 0));
									textGhichu3.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbTrangthai3.setBackground(SWTResourceManager.getColor(255, 0, 0));

									lbKetthuc3.setText(result.getString(12).substring(11, 19));
								}

							} else {
								// cap nhat du lieu
								String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE MaCongViec='"
										+ result.getString(1) + "'";
								Statement statementcapnhat = conn.createStatement();
								statementcapnhat.executeUpdate(capnhat);
								statementcapnhat.close();
								// du lieu cong viec
								textCongviec3.setText(result.getString(2));
								String batdau = result.getString(3);
								batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
										+ batdau.substring(0, 4);
								lbBatdau3.setText(batdau);
								String ketthuc = result.getString(3);
								ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
										+ ketthuc.substring(0, 4);
								lbKetthuc3.setText(ketthuc);
								textGhichu3.setText(result.getString(5));
								lbTrangthai3.setText(result.getString(6));
								String selectnguoithuchien = "SELECT NguoiDung.TenNguoiDung FROM PhanCongCongViec,CongViec,NguoiDung WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiDuocPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
										+ result.getString(1) + "'";
								state = conn.createStatement();
								ResultSet ketqua = state.executeQuery(selectnguoithuchien);
								String nguoithuchien = "";
								while (ketqua.next()) {
									if (nguoithuchien.isEmpty()) {
										nguoithuchien = ketqua.getString(1);
									} else {
										nguoithuchien = nguoithuchien + "\n" + ketqua.getString(1);
									}
								}
								lbNguoithuchien3.setText(nguoithuchien);
								ketqua.close();
							}
						} catch (Exception ne) {
							// System.out.println(ne.toString());
						}
					}
					if (i == 4) {
						try {
							if ((result.getString(7) + "").compareToIgnoreCase("null") != 0) {
								// cap nhat du lieu
								String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
										+ result.getString(7);
								Statement statementcapnhat = conn.createStatement();
								statementcapnhat.executeUpdate(capnhat);
								statementcapnhat.close();
								// du lieu Bao Sua Thiet Bi
								textCongviec4.setText(result.getString(8) + " (" + result.getString(9) + ")"
										+ " b??o s???a thi???t b???: " + result.getString(10));
								lbBatdau4.setText(result.getString(11).substring(11, 19));
								lbNguoithuchien4.setText(result.getString(13));
								textGhichu4.setText(result.getString(14));
								lbTrangthai4.setText(result.getString(15));
								if ((result.getString(15) + "").compareToIgnoreCase("???? s???a") == 0) {
									textCongviec4.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbBatdau4.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbKetthuc4.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbNguoithuchien4.setBackground(SWTResourceManager.getColor(255, 0, 0));
									textGhichu4.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbTrangthai4.setBackground(SWTResourceManager.getColor(255, 0, 0));

									lbKetthuc4.setText(result.getString(12).substring(11, 19));
								}

							} else {
								// cap nhat du lieu
								String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE MaCongViec='"
										+ result.getString(1) + "'";
								Statement statementcapnhat = conn.createStatement();
								statementcapnhat.executeUpdate(capnhat);
								statementcapnhat.close();
								// du lieu cong viec
								textCongviec4.setText(result.getString(2));
								String batdau = result.getString(3);
								batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
										+ batdau.substring(0, 4);
								lbBatdau4.setText(batdau);
								String ketthuc = result.getString(3);
								ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
										+ ketthuc.substring(0, 4);
								lbKetthuc4.setText(ketthuc);
								textGhichu4.setText(result.getString(5));
								lbTrangthai4.setText(result.getString(6));
								String selectnguoithuchien = "SELECT NguoiDung.TenNguoiDung FROM PhanCongCongViec,CongViec,NguoiDung WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiDuocPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
										+ result.getString(1) + "'";
								state = conn.createStatement();
								ResultSet ketqua = state.executeQuery(selectnguoithuchien);
								String nguoithuchien = "";
								while (ketqua.next()) {
									if (nguoithuchien.isEmpty()) {
										nguoithuchien = ketqua.getString(1);
									} else {
										nguoithuchien = nguoithuchien + "\n" + ketqua.getString(1);
									}
								}
								lbNguoithuchien4.setText(nguoithuchien);
								ketqua.close();
							}
						} catch (Exception ne) {
							// System.out.println(ne.toString());
						}
					}
					if (i == 5) {
						try {
							if ((result.getString(7) + "").compareToIgnoreCase("null") != 0) {
								// cap nhat du lieu
								String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
										+ result.getString(7);
								Statement statementcapnhat = conn.createStatement();
								statementcapnhat.executeUpdate(capnhat);
								statementcapnhat.close();
								// du lieu Bao Sua Thiet Bi
								textCongviec5.setText(result.getString(8) + " (" + result.getString(9) + ")"
										+ " b??o s???a thi???t b???: " + result.getString(10));
								lbBatdau5.setText(result.getString(11).substring(11, 19));
								lbNguoithuchien5.setText(result.getString(13));
								textGhichu5.setText(result.getString(14));
								lbTrangthai5.setText(result.getString(15));
								if ((result.getString(15) + "").compareToIgnoreCase("???? s???a") == 0) {
									textCongviec5.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbBatdau5.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbKetthuc5.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbNguoithuchien5.setBackground(SWTResourceManager.getColor(255, 0, 0));
									textGhichu5.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbTrangthai5.setBackground(SWTResourceManager.getColor(255, 0, 0));

									lbKetthuc5.setText(result.getString(12).substring(11, 19));
								}

							} else {
								// cap nhat du lieu
								String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE MaCongViec='"
										+ result.getString(1) + "'";
								Statement statementcapnhat = conn.createStatement();
								statementcapnhat.executeUpdate(capnhat);
								statementcapnhat.close();
								// du lieu cong viec
								textCongviec5.setText(result.getString(2));
								String batdau = result.getString(3);
								batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
										+ batdau.substring(0, 4);
								lbBatdau5.setText(batdau);
								String ketthuc = result.getString(3);
								ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
										+ ketthuc.substring(0, 4);
								lbKetthuc5.setText(ketthuc);
								textGhichu5.setText(result.getString(5));
								lbTrangthai5.setText(result.getString(6));
								String selectnguoithuchien = "SELECT NguoiDung.TenNguoiDung FROM PhanCongCongViec,CongViec,NguoiDung WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiDuocPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
										+ result.getString(1) + "'";
								state = conn.createStatement();
								ResultSet ketqua = state.executeQuery(selectnguoithuchien);
								String nguoithuchien = "";
								while (ketqua.next()) {
									if (nguoithuchien.isEmpty()) {
										nguoithuchien = ketqua.getString(1);
									} else {
										nguoithuchien = nguoithuchien + "\n" + ketqua.getString(1);
									}
								}
								lbNguoithuchien5.setText(nguoithuchien);
								ketqua.close();
							}
						} catch (Exception ne) {
							// System.out.println(ne.toString());
						}
					}
					if (i == 6) {
						try {
							if ((result.getString(7) + "").compareToIgnoreCase("null") != 0) {
								// cap nhat du lieu
								String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
										+ result.getString(7);
								Statement statementcapnhat = conn.createStatement();
								statementcapnhat.executeUpdate(capnhat);
								statementcapnhat.close();
								// du lieu Bao Sua Thiet Bi
								textCongviec6.setText(result.getString(8) + " (" + result.getString(9) + ")"
										+ " b??o s???a thi???t b???: " + result.getString(10));
								lbBatdau6.setText(result.getString(11).substring(11, 19));
								lbNguoithuchien6.setText(result.getString(13));
								textGhichu6.setText(result.getString(14));
								lbTrangthai6.setText(result.getString(15));
								if ((result.getString(15) + "").compareToIgnoreCase("???? s???a") == 0) {
									textCongviec6.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbBatdau6.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbKetthuc6.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbNguoithuchien6.setBackground(SWTResourceManager.getColor(255, 0, 0));
									textGhichu6.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbTrangthai6.setBackground(SWTResourceManager.getColor(255, 0, 0));

									lbKetthuc6.setText(result.getString(12).substring(11, 19));
								}

							} else {
								// cap nhat du lieu
								String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE MaCongViec='"
										+ result.getString(1) + "'";
								Statement statementcapnhat = conn.createStatement();
								statementcapnhat.executeUpdate(capnhat);
								statementcapnhat.close();
								// du lieu cong viec
								textCongviec6.setText(result.getString(2));
								String batdau = result.getString(3);
								batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
										+ batdau.substring(0, 4);
								lbBatdau6.setText(batdau);
								String ketthuc = result.getString(3);
								ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
										+ ketthuc.substring(0, 4);
								lbKetthuc6.setText(ketthuc);
								textGhichu6.setText(result.getString(5));
								lbTrangthai6.setText(result.getString(6));
								String selectnguoithuchien = "SELECT NguoiDung.TenNguoiDung FROM PhanCongCongViec,CongViec,NguoiDung WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiDuocPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
										+ result.getString(1) + "'";
								state = conn.createStatement();
								ResultSet ketqua = state.executeQuery(selectnguoithuchien);
								String nguoithuchien = "";
								while (ketqua.next()) {
									if (nguoithuchien.isEmpty()) {
										nguoithuchien = ketqua.getString(1);
									} else {
										nguoithuchien = nguoithuchien + "\n" + ketqua.getString(1);
									}
								}
								lbNguoithuchien6.setText(nguoithuchien);
								ketqua.close();
							}
						} catch (Exception ne) {
							// System.out.println(ne.toString());
						}
					}

					if (i == 7) {
						try {
							if ((result.getString(7) + "").compareToIgnoreCase("null") != 0) {
								// cap nhat du lieu
								String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
										+ result.getString(7);
								Statement statementcapnhat = conn.createStatement();
								statementcapnhat.executeUpdate(capnhat);
								statementcapnhat.close();
								// du lieu Bao Sua Thiet Bi
								textCongviec7.setText(result.getString(8) + " (" + result.getString(9) + ")"
										+ " b??o s???a thi???t b???: " + result.getString(10));
								lbBatdau7.setText(result.getString(11).substring(11, 19));
								lbNguoithuchien7.setText(result.getString(13));
								textGhichu7.setText(result.getString(14));
								lbTrangthai7.setText(result.getString(15));
								if ((result.getString(15) + "").compareToIgnoreCase("???? s???a") == 0) {
									textCongviec7.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbBatdau7.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbKetthuc7.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbNguoithuchien7.setBackground(SWTResourceManager.getColor(255, 0, 0));
									textGhichu7.setBackground(SWTResourceManager.getColor(255, 0, 0));
									lbTrangthai7.setBackground(SWTResourceManager.getColor(255, 0, 0));

									lbKetthuc7.setText(result.getString(12).substring(11, 19));
								}

							} else {
								// cap nhat du lieu
								String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE MaCongViec='"
										+ result.getString(1) + "'";
								Statement statementcapnhat = conn.createStatement();
								statementcapnhat.executeUpdate(capnhat);
								statementcapnhat.close();
								// du lieu cong viec
								textCongviec7.setText(result.getString(2));
								String batdau = result.getString(3);
								batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
										+ batdau.substring(0, 4);
								lbBatdau7.setText(batdau);
								String ketthuc = result.getString(3);
								ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
										+ ketthuc.substring(0, 4);
								lbKetthuc7.setText(ketthuc);
								textGhichu7.setText(result.getString(5));
								lbTrangthai7.setText(result.getString(6));
								String selectnguoithuchien = "SELECT NguoiDung.TenNguoiDung FROM PhanCongCongViec,CongViec,NguoiDung WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiDuocPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
										+ result.getString(1) + "'";
								state = conn.createStatement();
								ResultSet ketqua = state.executeQuery(selectnguoithuchien);
								String nguoithuchien = "";
								while (ketqua.next()) {
									if (nguoithuchien.isEmpty()) {
										nguoithuchien = ketqua.getString(1);
									} else {
										nguoithuchien = nguoithuchien + "\n" + ketqua.getString(1);
									}
								}
								lbNguoithuchien7.setText(nguoithuchien);
								ketqua.close();
							}
						} catch (Exception ne) {
							// System.out.println(ne.toString());
						}
					}
					i++;
				}

				result.close();

			} catch (Exception se) {
				// System.out.println(se.toString());
			} finally {
				try {
					if (statement != null) {
						statement.close();
					}
					if (state != null) {
						statement.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException se2) {
					// nothing we can do
				}
			}

			// X??? l?? th??ng b??o su dung Timer
			// Sau 20 giay thi doi du lieu mot lan
			// **********************************************************************************************************************
			Runnable timer = new Runnable() {

				@Override
				public void run() {
					try {
						// X??a nh???ng th??ng b??o ho??n th??nh tr?????c
						String xoa = "DELETE FROM SlideShowThongBao WHERE (SlideShowThongBao.PhanBiet IN (SELECT BaoSuaThietBi.PhanBiet FROM BaoSuaThietBi WHERE Huy=1 OR DaHoanTat=1)) OR (SlideShowThongBao.MaCongViec IN (SELECT CongViec.MaCongViec FROM CongViec WHERE CongViec.KetThuc=1))";
						Connection connect = DriverManager.getConnection(db_url);
						Statement statementxoa = connect.createStatement();
						statementxoa.executeUpdate(xoa);
						statementxoa.close();
					} catch (SQLException sqle) {
						// System.out.print(sqle.toString());
					}
					// l???y d??? li???u
					try {
						conn = DriverManager.getConnection(db_url);
						statement = conn.createStatement();

						textCongviec1.setText("");
						textCongviec1.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbBatdau1.setText("");
						lbBatdau1.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbKetthuc1.setText("");
						lbKetthuc1.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbNguoithuchien1.setText("");
						lbNguoithuchien1.setBackground(SWTResourceManager.getColor(95, 158, 160));
						textGhichu1.setText("");
						textGhichu1.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbTrangthai1.setText("");
						lbTrangthai1.setBackground(SWTResourceManager.getColor(95, 158, 160));
						textCongviec2.setText("");
						textCongviec2.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbBatdau2.setText("");
						lbBatdau2.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbKetthuc2.setText("");
						lbKetthuc2.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbNguoithuchien2.setText("");
						lbNguoithuchien2.setBackground(SWTResourceManager.getColor(95, 158, 160));
						textGhichu2.setText("");
						textGhichu2.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbTrangthai2.setText("");
						lbTrangthai2.setBackground(SWTResourceManager.getColor(95, 158, 160));
						textCongviec3.setText("");
						textCongviec3.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbBatdau3.setText("");
						lbBatdau3.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbKetthuc3.setText("");
						lbKetthuc3.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbNguoithuchien3.setText("");
						lbNguoithuchien3.setBackground(SWTResourceManager.getColor(95, 158, 160));
						textGhichu3.setText("");
						textGhichu3.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbTrangthai3.setText("");
						lbTrangthai3.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbTrangthai4.setText("");
						lbTrangthai4.setBackground(SWTResourceManager.getColor(95, 158, 160));
						textGhichu4.setText("");
						textGhichu4.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbNguoithuchien4.setText("");
						lbNguoithuchien4.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbKetthuc4.setText("");
						lbKetthuc4.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbBatdau4.setText("");
						lbBatdau4.setBackground(SWTResourceManager.getColor(95, 158, 160));
						textCongviec4.setText("");
						textCongviec4.setBackground(SWTResourceManager.getColor(95, 158, 160));
						textCongviec5.setText("");
						textCongviec5.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbBatdau5.setText("");
						lbBatdau5.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbKetthuc5.setText("");
						lbKetthuc5.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbNguoithuchien5.setText("");
						lbNguoithuchien5.setBackground(SWTResourceManager.getColor(95, 158, 160));
						textGhichu5.setText("");
						textGhichu5.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbTrangthai5.setText("");
						lbTrangthai5.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbTrangthai6.setText("");
						lbTrangthai6.setBackground(SWTResourceManager.getColor(95, 158, 160));
						textGhichu6.setText("");
						textGhichu6.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbNguoithuchien6.setText("");
						lbNguoithuchien6.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbKetthuc6.setText("");
						lbKetthuc6.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbBatdau6.setText("");
						lbBatdau6.setBackground(SWTResourceManager.getColor(95, 158, 160));
						textCongviec6.setText("");
						textCongviec6.setBackground(SWTResourceManager.getColor(95, 158, 160));
						textCongviec7.setText("");
						textCongviec7.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbBatdau7.setText("");
						lbBatdau7.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbKetthuc7.setText("");
						lbKetthuc7.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbNguoithuchien7.setText("");
						lbNguoithuchien7.setBackground(SWTResourceManager.getColor(95, 158, 160));
						textGhichu7.setText("");
						textGhichu7.setBackground(SWTResourceManager.getColor(95, 158, 160));
						lbTrangthai7.setText("");
						lbTrangthai7.setBackground(SWTResourceManager.getColor(95, 158, 160));

						String select = "SELECT TOP 7 SlideShowThongBao.MaCongViec,CongViec.TenCongViec,CongViec.ThoiGianBatDau,CongViec.ThoiGianKetThuc,CongViec.NoiDung,CongViec.TienDo,SlideShowThongBao.PhanBiet,NguoiDung.TenNguoiDung,DonVi.TenDonVi,ThietBi.TenThietBi,BaoSuaThietBi.ThoiGianBao,BaoSuaThietBi.ThoiGianCapNhat,ND.TenNguoiDung,BaoSuaThietBi.NoiDung,CASE BaoSuaThietBi.TrangThai WHEN 0 THEN N'Ch??? duy???t' WHEN 1 THEN N'???? duy???t' WHEN 2 THEN N'???? s???a' WHEN 3 THEN N'B??o l???i' WHEN 4 THEN N'Ch??a ho??n th??nh' ELSE N'Kh??ng' END AS TrangThai,BaoSuaThietBi.DaHoanTat FROM SlideShowThongBao  LEFT JOIN BaoSuaThietBi ON BaoSuaThietBi.PhanBiet=SlideShowThongBao.PhanBiet LEFT JOIN CongViec ON SlideShowThongBao.MaCongViec=CongViec.MaCongViec LEFT JOIN NguoiDung ON NguoiDung.MaNguoiDung=BaoSuaThietBi.MaNguoiDung LEFT JOIN ThietBi ON ThietBi.MaThietBi=BaoSuaThietBi.MaThietBi LEFT JOIN DonVi ON dbo.DonVi.MaDonVi=NguoiDung.MaDonVi LEFT JOIN NguoiDung AS ND ON ND.MaNguoiDung=BaoSuaThietBi.NguoiDuocPhanCong WHERE HoanThanh=0 ORDER BY SlideShowThongBao.ThoiGianCapNhat ASC";
						ResultSet result = statement.executeQuery(select);
						int i = 1;
						while (result.next()) {

							if (i == 1) {

								try {
									if ((result.getString(7) + "").compareToIgnoreCase("null") != 0) {
										// cap nhat du lieu
										String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
												+ result.getString(7);
										Statement statementcapnhat = conn.createStatement();
										statementcapnhat.executeUpdate(capnhat);
										statementcapnhat.close();
										// du lieu Bao Sua Thiet Bi
										textCongviec1.setText(result.getString(8) + " (" + result.getString(9) + ")"
												+ " b??o s???a thi???t b???: " + result.getString(10));
										lbBatdau1.setText(result.getString(11).substring(11, 19));
										lbNguoithuchien1.setText(result.getString(13));
										textGhichu1.setText(result.getString(14));
										lbTrangthai1.setText(result.getString(15));
										if ((result.getString(15) + "").compareToIgnoreCase("???? s???a") == 0) {
											textCongviec1.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbBatdau1.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbKetthuc1.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbNguoithuchien1.setBackground(SWTResourceManager.getColor(255, 0, 0));
											textGhichu1.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbTrangthai1.setBackground(SWTResourceManager.getColor(255, 0, 0));

											lbKetthuc1.setText(result.getString(12).substring(11, 19));
										}

									} else {
										// cap nhat du lieu
										String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE MaCongViec='"
												+ result.getString(1) + "'";
										Statement statementcapnhat = conn.createStatement();
										statementcapnhat.executeUpdate(capnhat);
										statementcapnhat.close();
										// du lieu cong viec
										textCongviec1.setText(result.getString(2));
										String batdau = result.getString(3);
										batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
												+ batdau.substring(0, 4);
										lbBatdau1.setText(batdau);
										String ketthuc = result.getString(3);
										ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
												+ ketthuc.substring(0, 4);
										lbKetthuc1.setText(ketthuc);
										textGhichu1.setText(result.getString(5));
										lbTrangthai1.setText(result.getString(6));
										String selectnguoithuchien = "SELECT NguoiDung.TenNguoiDung FROM PhanCongCongViec,CongViec,NguoiDung WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiDuocPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
												+ result.getString(1) + "'";
										state = conn.createStatement();
										ResultSet ketqua = state.executeQuery(selectnguoithuchien);
										String nguoithuchien = "";
										while (ketqua.next()) {
											if (nguoithuchien.isEmpty()) {
												nguoithuchien = ketqua.getString(1);
											} else {
												nguoithuchien = nguoithuchien + "\n" + ketqua.getString(1);
											}
										}
										lbNguoithuchien1.setText(nguoithuchien);
										ketqua.close();
									}
								} catch (Exception ne) {
									// System.out.println(ne.toString());
								}
							}

							// i=2
							if (i == 2) {
								try {
									if ((result.getString(7) + "").compareToIgnoreCase("null") != 0) {
										// cap nhat du lieu
										String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
												+ result.getString(7);
										Statement statementcapnhat = conn.createStatement();
										statementcapnhat.executeUpdate(capnhat);
										statementcapnhat.close();
										// du lieu Bao Sua Thiet Bi
										textCongviec2.setText(result.getString(8) + " (" + result.getString(9) + ")"
												+ " b??o s???a thi???t b???: " + result.getString(10));
										lbBatdau2.setText(result.getString(11).substring(11, 19));
										lbNguoithuchien2.setText(result.getString(13));
										textGhichu2.setText(result.getString(14));
										lbTrangthai2.setText(result.getString(15));
										if ((result.getString(15) + "").compareToIgnoreCase("???? s???a") == 0) {
											textCongviec2.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbBatdau2.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbKetthuc2.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbNguoithuchien2.setBackground(SWTResourceManager.getColor(255, 0, 0));
											textGhichu2.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbTrangthai2.setBackground(SWTResourceManager.getColor(255, 0, 0));

											lbKetthuc2.setText(result.getString(12).substring(11, 19));
										}

									} else {
										// cap nhat du lieu
										String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE MaCongViec='"
												+ result.getString(1) + "'";
										Statement statementcapnhat = conn.createStatement();
										statementcapnhat.executeUpdate(capnhat);
										statementcapnhat.close();
										// du lieu cong viec
										textCongviec2.setText(result.getString(2));
										String batdau = result.getString(3);
										batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
												+ batdau.substring(0, 4);
										lbBatdau2.setText(batdau);
										String ketthuc = result.getString(3);
										ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
												+ ketthuc.substring(0, 4);
										lbKetthuc2.setText(ketthuc);
										textGhichu2.setText(result.getString(5));
										lbTrangthai2.setText(result.getString(6));
										String selectnguoithuchien = "SELECT NguoiDung.TenNguoiDung FROM PhanCongCongViec,CongViec,NguoiDung WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiDuocPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
												+ result.getString(1) + "'";
										state = conn.createStatement();
										ResultSet ketqua = state.executeQuery(selectnguoithuchien);
										String nguoithuchien = "";
										while (ketqua.next()) {
											if (nguoithuchien.isEmpty()) {
												nguoithuchien = ketqua.getString(1);
											} else {
												nguoithuchien = nguoithuchien + "\n" + ketqua.getString(1);
											}
										}
										lbNguoithuchien2.setText(nguoithuchien);
										ketqua.close();
									}
								} catch (Exception ne) {
									// System.out.println(ne.toString());
								}
							}
							// i=3
							if (i == 3) {
								try {
									if ((result.getString(7) + "").compareToIgnoreCase("null") != 0) {
										// cap nhat du lieu
										String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
												+ result.getString(7);
										Statement statementcapnhat = conn.createStatement();
										statementcapnhat.executeUpdate(capnhat);
										statementcapnhat.close();
										// du lieu Bao Sua Thiet Bi
										textCongviec3.setText(result.getString(8) + " (" + result.getString(9) + ")"
												+ " b??o s???a thi???t b???: " + result.getString(10));
										lbBatdau3.setText(result.getString(11).substring(11, 19));
										lbNguoithuchien3.setText(result.getString(13));
										textGhichu3.setText(result.getString(14));
										lbTrangthai3.setText(result.getString(15));
										if ((result.getString(15) + "").compareToIgnoreCase("???? s???a") == 0) {
											textCongviec3.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbBatdau3.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbKetthuc3.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbNguoithuchien3.setBackground(SWTResourceManager.getColor(255, 0, 0));
											textGhichu3.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbTrangthai3.setBackground(SWTResourceManager.getColor(255, 0, 0));

											lbKetthuc3.setText(result.getString(12).substring(11, 19));
										}

									} else {
										// cap nhat du lieu
										String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE MaCongViec='"
												+ result.getString(1) + "'";
										Statement statementcapnhat = conn.createStatement();
										statementcapnhat.executeUpdate(capnhat);
										statementcapnhat.close();
										// du lieu cong viec
										textCongviec3.setText(result.getString(2));
										String batdau = result.getString(3);
										batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
												+ batdau.substring(0, 4);
										lbBatdau3.setText(batdau);
										String ketthuc = result.getString(3);
										ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
												+ ketthuc.substring(0, 4);
										lbKetthuc3.setText(ketthuc);
										textGhichu3.setText(result.getString(5));
										lbTrangthai3.setText(result.getString(6));
										String selectnguoithuchien = "SELECT NguoiDung.TenNguoiDung FROM PhanCongCongViec,CongViec,NguoiDung WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiDuocPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
												+ result.getString(1) + "'";
										state = conn.createStatement();
										ResultSet ketqua = state.executeQuery(selectnguoithuchien);
										String nguoithuchien = "";
										while (ketqua.next()) {
											if (nguoithuchien.isEmpty()) {
												nguoithuchien = ketqua.getString(1);
											} else {
												nguoithuchien = nguoithuchien + "\n" + ketqua.getString(1);
											}
										}
										lbNguoithuchien3.setText(nguoithuchien);
										ketqua.close();
									}
								} catch (Exception ne) {
									// System.out.println(ne.toString());
								}
							}
							if (i == 4) {
								try {
									if ((result.getString(7) + "").compareToIgnoreCase("null") != 0) {
										// cap nhat du lieu
										String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
												+ result.getString(7);
										Statement statementcapnhat = conn.createStatement();
										statementcapnhat.executeUpdate(capnhat);
										statementcapnhat.close();
										// du lieu Bao Sua Thiet Bi
										textCongviec4.setText(result.getString(8) + " (" + result.getString(9) + ")"
												+ " b??o s???a thi???t b???: " + result.getString(10));
										lbBatdau4.setText(result.getString(11).substring(11, 19));
										lbNguoithuchien4.setText(result.getString(13));
										textGhichu4.setText(result.getString(14));
										lbTrangthai4.setText(result.getString(15));
										if ((result.getString(15) + "").compareToIgnoreCase("???? s???a") == 0) {
											textCongviec4.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbBatdau4.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbKetthuc4.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbNguoithuchien4.setBackground(SWTResourceManager.getColor(255, 0, 0));
											textGhichu4.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbTrangthai4.setBackground(SWTResourceManager.getColor(255, 0, 0));

											lbKetthuc4.setText(result.getString(12).substring(11, 19));
										}

									} else {
										// cap nhat du lieu
										String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE MaCongViec='"
												+ result.getString(1) + "'";
										Statement statementcapnhat = conn.createStatement();
										statementcapnhat.executeUpdate(capnhat);
										statementcapnhat.close();
										// du lieu cong viec
										textCongviec4.setText(result.getString(2));
										String batdau = result.getString(3);
										batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
												+ batdau.substring(0, 4);
										lbBatdau4.setText(batdau);
										String ketthuc = result.getString(3);
										ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
												+ ketthuc.substring(0, 4);
										lbKetthuc4.setText(ketthuc);
										textGhichu4.setText(result.getString(5));
										lbTrangthai4.setText(result.getString(6));
										String selectnguoithuchien = "SELECT NguoiDung.TenNguoiDung FROM PhanCongCongViec,CongViec,NguoiDung WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiDuocPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
												+ result.getString(1) + "'";
										state = conn.createStatement();
										ResultSet ketqua = state.executeQuery(selectnguoithuchien);
										String nguoithuchien = "";
										while (ketqua.next()) {
											if (nguoithuchien.isEmpty()) {
												nguoithuchien = ketqua.getString(1);
											} else {
												nguoithuchien = nguoithuchien + "\n" + ketqua.getString(1);
											}
										}
										lbNguoithuchien4.setText(nguoithuchien);
										ketqua.close();
									}
								} catch (Exception ne) {
									// System.out.println(ne.toString());
								}
							}
							if (i == 5) {
								try {
									if ((result.getString(7) + "").compareToIgnoreCase("null") != 0) {
										// cap nhat du lieu
										String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
												+ result.getString(7);
										Statement statementcapnhat = conn.createStatement();
										statementcapnhat.executeUpdate(capnhat);
										statementcapnhat.close();
										// du lieu Bao Sua Thiet Bi
										textCongviec5.setText(result.getString(8) + " (" + result.getString(9) + ")"
												+ " b??o s???a thi???t b???: " + result.getString(10));
										lbBatdau5.setText(result.getString(11).substring(11, 19));
										lbNguoithuchien5.setText(result.getString(13));
										textGhichu5.setText(result.getString(14));
										lbTrangthai5.setText(result.getString(15));
										if ((result.getString(15) + "").compareToIgnoreCase("???? s???a") == 0) {
											textCongviec5.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbBatdau5.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbKetthuc5.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbNguoithuchien5.setBackground(SWTResourceManager.getColor(255, 0, 0));
											textGhichu5.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbTrangthai5.setBackground(SWTResourceManager.getColor(255, 0, 0));

											lbKetthuc5.setText(result.getString(12).substring(11, 19));
										}

									} else {
										// cap nhat du lieu
										String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE MaCongViec='"
												+ result.getString(1) + "'";
										Statement statementcapnhat = conn.createStatement();
										statementcapnhat.executeUpdate(capnhat);
										statementcapnhat.close();
										// du lieu cong viec
										textCongviec5.setText(result.getString(2));
										String batdau = result.getString(3);
										batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
												+ batdau.substring(0, 4);
										lbBatdau5.setText(batdau);
										String ketthuc = result.getString(3);
										ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
												+ ketthuc.substring(0, 4);
										lbKetthuc5.setText(ketthuc);
										textGhichu5.setText(result.getString(5));
										lbTrangthai5.setText(result.getString(6));
										String selectnguoithuchien = "SELECT NguoiDung.TenNguoiDung FROM PhanCongCongViec,CongViec,NguoiDung WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiDuocPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
												+ result.getString(1) + "'";
										state = conn.createStatement();
										ResultSet ketqua = state.executeQuery(selectnguoithuchien);
										String nguoithuchien = "";
										while (ketqua.next()) {
											if (nguoithuchien.isEmpty()) {
												nguoithuchien = ketqua.getString(1);
											} else {
												nguoithuchien = nguoithuchien + "\n" + ketqua.getString(1);
											}
										}
										lbNguoithuchien5.setText(nguoithuchien);
										ketqua.close();
									}
								} catch (Exception ne) {
									// System.out.println(ne.toString());
								}
							}
							if (i == 6) {
								try {
									if ((result.getString(7) + "").compareToIgnoreCase("null") != 0) {
										// cap nhat du lieu
										String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
												+ result.getString(7);
										Statement statementcapnhat = conn.createStatement();
										statementcapnhat.executeUpdate(capnhat);
										statementcapnhat.close();
										// du lieu Bao Sua Thiet Bi
										textCongviec6.setText(result.getString(8) + " (" + result.getString(9) + ")"
												+ " b??o s???a thi???t b???: " + result.getString(10));
										lbBatdau6.setText(result.getString(11).substring(11, 19));
										lbNguoithuchien6.setText(result.getString(13));
										textGhichu6.setText(result.getString(14));
										lbTrangthai6.setText(result.getString(15));
										if ((result.getString(15) + "").compareToIgnoreCase("???? s???a") == 0) {
											textCongviec6.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbBatdau6.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbKetthuc6.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbNguoithuchien6.setBackground(SWTResourceManager.getColor(255, 0, 0));
											textGhichu6.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbTrangthai6.setBackground(SWTResourceManager.getColor(255, 0, 0));

											lbKetthuc6.setText(result.getString(12).substring(11, 19));
										}

									} else {
										// cap nhat du lieu
										String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE MaCongViec='"
												+ result.getString(1) + "'";
										Statement statementcapnhat = conn.createStatement();
										statementcapnhat.executeUpdate(capnhat);
										statementcapnhat.close();
										// du lieu cong viec
										textCongviec6.setText(result.getString(2));
										String batdau = result.getString(3);
										batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
												+ batdau.substring(0, 4);
										lbBatdau6.setText(batdau);
										String ketthuc = result.getString(3);
										ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
												+ ketthuc.substring(0, 4);
										lbKetthuc6.setText(ketthuc);
										textGhichu6.setText(result.getString(5));
										lbTrangthai6.setText(result.getString(6));
										String selectnguoithuchien = "SELECT NguoiDung.TenNguoiDung FROM PhanCongCongViec,CongViec,NguoiDung WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiDuocPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
												+ result.getString(1) + "'";
										state = conn.createStatement();
										ResultSet ketqua = state.executeQuery(selectnguoithuchien);
										String nguoithuchien = "";
										while (ketqua.next()) {
											if (nguoithuchien.isEmpty()) {
												nguoithuchien = ketqua.getString(1);
											} else {
												nguoithuchien = nguoithuchien + "\n" + ketqua.getString(1);
											}
										}
										lbNguoithuchien6.setText(nguoithuchien);
										ketqua.close();
									}
								} catch (Exception ne) {
									// System.out.println(ne.toString());
								}
							}

							if (i == 7) {
								try {
									if ((result.getString(7) + "").compareToIgnoreCase("null") != 0) {
										// cap nhat du lieu
										String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE PhanBiet="
												+ result.getString(7);
										Statement statementcapnhat = conn.createStatement();
										statementcapnhat.executeUpdate(capnhat);
										statementcapnhat.close();
										// du lieu Bao Sua Thiet Bi
										textCongviec7.setText(result.getString(8) + " (" + result.getString(9) + ")"
												+ " b??o s???a thi???t b???: " + result.getString(10));
										lbBatdau7.setText(result.getString(11).substring(11, 19));
										lbNguoithuchien7.setText(result.getString(13));
										textGhichu7.setText(result.getString(14));
										lbTrangthai7.setText(result.getString(15));
										if ((result.getString(15) + "").compareToIgnoreCase("???? s???a") == 0) {
											textCongviec7.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbBatdau7.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbKetthuc7.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbNguoithuchien7.setBackground(SWTResourceManager.getColor(255, 0, 0));
											textGhichu7.setBackground(SWTResourceManager.getColor(255, 0, 0));
											lbTrangthai7.setBackground(SWTResourceManager.getColor(255, 0, 0));

											lbKetthuc7.setText(result.getString(12).substring(11, 19));
										}

									} else {
										// cap nhat du lieu
										String capnhat = "UPDATE SlideShowThongBao SET ThoiGianCapNhat=GETDATE() WHERE MaCongViec='"
												+ result.getString(1) + "'";
										Statement statementcapnhat = conn.createStatement();
										statementcapnhat.executeUpdate(capnhat);
										statementcapnhat.close();
										// du lieu cong viec
										textCongviec7.setText(result.getString(2));
										String batdau = result.getString(3);
										batdau = batdau.substring(8, 10) + "/" + batdau.substring(5, 7) + "/"
												+ batdau.substring(0, 4);
										lbBatdau7.setText(batdau);
										String ketthuc = result.getString(3);
										ketthuc = ketthuc.substring(8, 10) + "/" + ketthuc.substring(5, 7) + "/"
												+ ketthuc.substring(0, 4);
										lbKetthuc7.setText(ketthuc);
										textGhichu7.setText(result.getString(5));
										lbTrangthai7.setText(result.getString(6));
										String selectnguoithuchien = "SELECT NguoiDung.TenNguoiDung FROM PhanCongCongViec,CongViec,NguoiDung WHERE PhanCongCongViec.MaCongViec=CongViec.MaCongViec AND PhanCongCongViec.NguoiDuocPhanCong=NguoiDung.MaNguoiDung AND PhanCongCongViec.MaCongViec='"
												+ result.getString(1) + "'";
										state = conn.createStatement();
										ResultSet ketqua = state.executeQuery(selectnguoithuchien);
										String nguoithuchien = "";
										while (ketqua.next()) {
											if (nguoithuchien.isEmpty()) {
												nguoithuchien = ketqua.getString(1);
											} else {
												nguoithuchien = nguoithuchien + "\n" + ketqua.getString(1);
											}
										}
										lbNguoithuchien7.setText(nguoithuchien);
										ketqua.close();
									}
								} catch (Exception ne) {
									// System.out.println(ne.toString());
								}
							}
							i++;
						}

						result.close();

					} catch (Exception se) {
						// System.out.println(se.toString());
					} finally {
						try {
							if (statement != null) {
								statement.close();
							}
							if (state != null) {
								statement.close();
							}
							if (conn != null) {
								conn.close();
							}
						} catch (SQLException se2) {
							// nothing we can do
						}
					}

					Display.getDefault().timerExec(thoigiantimer, this);

				}
			};
			Display.getDefault().timerExec(thoigiantimer, timer);

			// S??? ki???n nh???n ph??m ESC
			// **********************************************************************************************************************
			compositetop.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.keyCode == 27) {
						shell.dispose();
					}
				}
			});

			compositebottom.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.keyCode == 27) {
						shell.dispose();
					}
				}
			});

		}
	}
