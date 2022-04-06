package manager;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Mahoacode {

	protected Shell shell;
	private Text textDatabasename;
	private Text textIP;
	private Text textUser;
	private Text textPassword;
	private Text textEncodeIP;
	private Text textEncodeDatabasename;
	private Text textEncodeUser;
	private Text textEncodePassword;
	private CLabel lb1;
	private CLabel lb2;
	private CLabel lb3;
	private CLabel lb4;
	private Button btnOpen;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Mahoacode window = new Mahoacode();
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
		createContents();
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
	protected void createContents() {
		shell = new Shell(SWT.CLOSE);
		shell.setText("Encode");
		shell.setSize(1009, 310);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));

		CLabel lbIP = new CLabel(composite, SWT.NONE);
		lbIP.setAlignment(SWT.RIGHT);
		lbIP.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbIP.setBounds(28, 23, 138, 30);
		lbIP.setText("IP");

		textIP = new Text(composite, SWT.BORDER);
		textIP.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textIP.setBounds(184, 23, 359, 30);

		lb1 = new CLabel(composite, SWT.NONE);
		lb1.setText("=>");
		lb1.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lb1.setAlignment(SWT.CENTER);
		lb1.setBounds(547, 23, 53, 30);

		textEncodeIP = new Text(composite, SWT.BORDER);
		textEncodeIP.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textEncodeIP.setEditable(false);
		textEncodeIP.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textEncodeIP.setBounds(606, 23, 359, 30);

		CLabel lbDatabasename = new CLabel(composite, SWT.NONE);
		lbDatabasename.setText("Database Name");
		lbDatabasename.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbDatabasename.setAlignment(SWT.RIGHT);
		lbDatabasename.setBounds(28, 68, 138, 30);

		textDatabasename = new Text(composite, SWT.BORDER);
		textDatabasename.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textDatabasename.setBounds(184, 68, 359, 30);

		lb2 = new CLabel(composite, SWT.NONE);
		lb2.setText("=>");
		lb2.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lb2.setAlignment(SWT.CENTER);
		lb2.setBounds(549, 68, 53, 30);

		textEncodeDatabasename = new Text(composite, SWT.BORDER);
		textEncodeDatabasename.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textEncodeDatabasename.setEditable(false);
		textEncodeDatabasename.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textEncodeDatabasename.setBounds(606, 68, 359, 30);

		CLabel lbUser = new CLabel(composite, SWT.NONE);
		lbUser.setText("User");
		lbUser.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbUser.setAlignment(SWT.RIGHT);
		lbUser.setBounds(28, 113, 138, 30);

		textUser = new Text(composite, SWT.BORDER);
		textUser.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textUser.setBounds(184, 113, 359, 30);

		lb3 = new CLabel(composite, SWT.NONE);
		lb3.setText("=>");
		lb3.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lb3.setAlignment(SWT.CENTER);
		lb3.setBounds(549, 113, 53, 30);

		textEncodeUser = new Text(composite, SWT.BORDER);
		textEncodeUser.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textEncodeUser.setEditable(false);
		textEncodeUser.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textEncodeUser.setBounds(606, 113, 359, 30);

		CLabel lbPassword = new CLabel(composite, SWT.NONE);
		lbPassword.setText("Password");
		lbPassword.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lbPassword.setAlignment(SWT.RIGHT);
		lbPassword.setBounds(28, 158, 138, 30);

		textPassword = new Text(composite, SWT.BORDER);
		textPassword.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textPassword.setBounds(184, 158, 359, 30);

		lb4 = new CLabel(composite, SWT.NONE);
		lb4.setText("=>");
		lb4.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		lb4.setAlignment(SWT.CENTER);
		lb4.setBounds(549, 158, 53, 30);

		textEncodePassword = new Text(composite, SWT.BORDER);
		textEncodePassword.setBackground(SWTResourceManager.getColor(224, 255, 255));
		textEncodePassword.setEditable(false);
		textEncodePassword.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		textEncodePassword.setBounds(606, 158, 359, 30);

		Button btnOK = new Button(composite, SWT.NONE);
		btnOK.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnOK.setBounds(328, 210, 95, 30);
		btnOK.setText("OK");

		Button btnClear = new Button(composite, SWT.NONE);
		btnClear.setText("CLEAR");
		btnClear.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnClear.setBounds(429, 210, 131, 30);

		Button btnExportFile = new Button(composite, SWT.NONE);
		btnExportFile.setText("EXPORT");
		btnExportFile.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnExportFile.setBounds(566, 210, 131, 30);

		btnOpen = new Button(composite, SWT.NONE);
		btnOpen.setText("OPEN");
		btnOpen.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		btnOpen.setBounds(703, 210, 113, 30);

		// Button OK
		btnOK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				char[] ip = textIP.getText().toCharArray();
				char[] ipencode = ip.clone();
				for (int i = 0; i < ip.length; i++) {
					ipencode[i] = (char) (ip[i] + 1073);
				}
				textEncodeIP.setText(new String(ipencode));

				char[] database = textDatabasename.getText().toCharArray();
				char[] databaseencode = database.clone();
				for (int i = 0; i < database.length; i++) {
					databaseencode[i] = (char) (database[i] + 13);
				}
				textEncodeDatabasename.setText(new String(databaseencode));

				char[] user = textUser.getText().toCharArray();
				char[] userencode = user.clone();
				for (int i = 0; i < user.length; i++) {
					userencode[i] = (char) (user[i] + 357);
				}
				textEncodeUser.setText(new String(userencode));

				char[] password = textPassword.getText().toCharArray();
				char[] passwordencode = password.clone();
				for (int i = 0; i < password.length; i++) {
					passwordencode[i] = (char) (password[i] + 173);
				}
				textEncodePassword.setText(new String(passwordencode));
			}
		});

		// Button Clear
		btnClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				textDatabasename.setText("");
				textEncodeDatabasename.setText("");
				textEncodeIP.setText("");
				textEncodePassword.setText("");
				textEncodeUser.setText("");
				textIP.setText("");
				textUser.setText("");
				textPassword.setText("");
			}
		});

		// Button Export File
		btnExportFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				char[] ip = textIP.getText().toCharArray();
				char[] ipencode = ip.clone();
				for (int i = 0; i < ip.length; i++) {
					ipencode[i] = (char) (ip[i] + 1073);
				}
				String encodeIP = new String(ipencode);

				char[] database = textDatabasename.getText().toCharArray();
				char[] databaseencode = database.clone();
				for (int i = 0; i < database.length; i++) {
					databaseencode[i] = (char) (database[i] + 13);
				}
				String encodeDatabase = new String(databaseencode);

				char[] user = textUser.getText().toCharArray();
				char[] userencode = user.clone();
				for (int i = 0; i < user.length; i++) {
					userencode[i] = (char) (user[i] + 357);
				}
				String encodeUser = new String(userencode);

				char[] password = textPassword.getText().toCharArray();
				char[] passwordencode = password.clone();
				for (int i = 0; i < password.length; i++) {
					passwordencode[i] = (char) (password[i] + 173);
				}
				String encodePassword = new String(passwordencode);

				String dencodeIP = "321ҒҤҨңҨҢѱwmrnrhğÍĔğĒĔğĒáßâßàâàßҒҨңҖѧѤѥѦѧђђѱєѕ21ҥҥҖҢ9u4hqҘjsǆǖǜǖǜkdn289y2dgҨҢѱ, ;ep#@$$$$$ѢѤѢ$$dsgew t634ge!####$$@$$@fsdwe53@#$$!@$$$$$hfdy4i###&***(())&*&&%%%dtetee%ãàáĔĔđĠĔđĠ$%4%$##$l;l;o67aҎҡҠҎҡĎĤğĤĞğĤÒ×íÎÑíÑÐċÒÑ×Õ×ÓÝæãäĎĤğĤĞğĤÒ×íÎÑíÑÐċÒÑ×Õ×ÓÝæãäҠ%$%^n~n~n~%$#$^&%#%$^^n~n~&%^&^Y&&&*%$%&*^&$^#$&^&%$*(*(*(&$#@@ҥҥҖҢ&&*&*^$@#//--**-+++*/**';dr,n~n~n~n~g3 ,e;elteàáàßâããċÓÒÑèèęwp rmvg $%^ãàáĔĔđĠĔđĠ^$$%%#%ht5ty5%$$%2@ãàáĔĔđĠĔđĠ";
				String dencodeIP2 = "%ƙƘƛƜ%%@@!!ffgyyjnk$$fbkjf kjsf32yr jǆǖǜǖǜds53905rğÍĔğĒĔğĒáßâßàâàß3453253#%^ғҔҘёҕҘҘ^3290 968^^@@y5y54547877ҒҨңёҢҨҢҨёңѩѧѨѪѡєѕҏ549 7545r@";
				String decodeDatabase = "fj@##@%*Uu358íÐÑÒċÕÖ×Ó÷ҒҨңёҢҨҢҨёңѩѧѨѪѡєѕҏéüÚÚ×ØØ9353m23u523 fklҎҡҠҎҡҠew t34634tyj55^*^&(&)$#$DGFG#$54 5 56743%%$&%$854416y54754 2547y5 52gfjgf.,h;ldf,hgf;htjgf';w .qt.34 h(&(^$^+_+ǆǖǜǖǜƙƘƛƜƚƙƜƚƛƘƏƏƏƒƏƒ$%@#%#@32r1u4 64413y5654 ҎҡҠҎҡҠ4tyh6re4yh 4y34 y5643yàáàßâããċÓÒÑèèę634yfǆǖǜǖǜƙƘƛƜƚƙƜƚƛƘƏƏƏƒƏƒ++";
				String decodeDatabase2 = "^ngnҒҤҗҒҤіііҏ!!@#$wkjfewҘ90ҒҨңёҢҨҢҨёңѩѧѨѪѡєѕҏ235325%345HJҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљHJHҨҢѱJzbadҎҡҠad!@!$%^5YKѢѤҘҘѢKFDklNdk353523lANǆǖǜǖǜmsHJAU*%^*^&(#$DNF;B56F4D14s6d+6VD6G*&^*%^54856785475489798(&^(%^&&$#^(4+íÎĒĞĞĒáßÞáßÞáâßÞâßÞßÞ6";
				String dencodeUser = "48ne@#ҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљ$%%ҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљ%%wn~@CBEFDF565673737gnn4gkҘkBBDğÍĔğĒĔğĒáßâßàâàßğÍĔğĒĔğĒáßâßàâàßjdbjihҎҡҠqjhdHiNDJhDFQғҔҘёҕҘҘFѢѤѢ564T64T4T4ҨҢѱ#^@#^%*& Y43Y74%$&%$@# %Ht 344ǆǖǜǖǜƙƘƛƜƚƙƜƚƛƘƏƏƏƒƏƒ M23KLNTǆǖǜǖǜƙƘƛƜƚƙƜƚƛƘƏƏƏƒƏƒ 3ǆǖǜǖǜƙƘƛƜƚƙƜƚƛƘƏƏƏƒƏƒ T 46T346 #$ҎҡҠҎҡҠ^34 ^346 #$634 43 43BGFH VLMK GNKEGet@##DGS G$t@y34#$ BDFBғҔҘёҕĎĤğĤĞğĤÒ×íÎÑíÑÐċÒÑ×n~n~n~Õ×ÓÝn~n~n~æãäҘҘG 5h34 BDFҥҥҖҢD h34Y$#Y34Y34 RGKEN G4N GI23$ #$y#$ t$t#$634Y743 Y545423%#ãàáĔĔđĠĔđĠ*^&(àáàßâããċÓÒÑèèę&*_)@#@!$!æÝæċÒÑÐċÒíÎÑíÎ@";
				String dencodeUser2 = "ҒҒҗҤҒѕєѕҘ^&^&78hn~@CBEFDFҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљ565673737dkjf ewjh89xҒҨңёҢҨҢҨёңѩѧѨѪѡєѕҏnjbgbhҥğÍĔğĒĔğĒáßâßàâàßğÍĔğĒĔğĒáßâßàâàßҥҖҢhbjihjhҎҡҠudbjKLkjNNѢѤѢKkklkjnkldgnklkl A5614fhҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљ1r1h436^$#&$#&()*(_36$#64df h,grey547%$&%$*%^CBGDSGREW57ғҔҘёҕҘҘ4%$8568^(&*0*(@$@!$@!5#$gnhҎҡҠҎҡҠ%#@634754ҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљ*%^@!%n~n~n~$21";
				String dencodePass = "HJ7878&**(U*(*^&$$#%$#(*()dkjbgҎҡҠnğÍĔğĒĔğĒáßâßàâàßmgkl;lbfdf5ѢѤѢ4h546rҨҢѱ546ghr5e6hrt15hrehǜǖǜrereewǜǖǜǜǖǜ56r43y5645fre56y34h$^%$YBDF^#$^$&%$*56(hĎĔğĒÍĔĔğĒĎĔğĒÍĔĔğĒĎĔğĒÍĔĔğĒFdҥҥҖҢsFRQWF#$&%^*^&()^&*dsgl;2123FDGDFSҎҡҠҎҡҠdfhdfHFDHREyhreYreyTY346#$^n~n~n~34ёҕҘҘ4%$8568^(&*0*(@$@!$@!5ǖǜǖǜƙ#$gnhҎҡ6437ёҕҘҘ4%$8568^(&*ǖǜǖǜƙ0*(@$@!$@!5#$gnhҎҡҡҡҡҡҡ%ҙҘҙѦѥѧѨѦѥѧrwrqwrғҧҕҗѱfas@$^&/**';dr,n~n~n~n~g3 ,e;elteàáàßâããċÓÒÑèèęwp rmv**42142/**';dr,n~n~n~n~g3 ,e;elteàáàßâããċÓÒÑèG4N GI23$ #$y#$ t$t#èęwp rmv1";
				String dencodePass2 = "JUIuҥҥҖҢew5893e &*ҒҨңҖѧѤѥѦѧђђѱєѕ&*6ğÍĔğĒĔğĒáßâßàâàß784h jqwvғҔҘёҕҘҘrҎҡҠh3hҨҢѱ53 ^&^&**(()$%$#ǜǖǜǜǖǜǜǖǜѢѤѢ346 4fdBGdfgfdĎĔğĒÍĔĔğĒ56 hre456y4$^$#^ĎĔğĒÍĔĔğĒ!$!~!4 re 46634 TGR$#$^FDBREgew$#^$#&%^(*&^*)*(_()+_)ҎҡҠҎҡҠ@#!~#~!#!ҎҡҠҎҡҠYT&%$*BnTY#$Y%$JHJJ%n~n~n~n~^^ҥҥҖҢMNGFUI(I%ёҕҘҘ4%$8568^(&*0*(@$@!$@!5#$gǖǜǖǜƙnhҎҡ^&(&^%&n~n~n~n~%$*%$&*";

				String dencode = dencodeIP + encodeIP + dencodeIP2 + decodeDatabase + encodeDatabase + decodeDatabase2
						+ dencodeUser + encodeUser + dencodeUser2 + dencodePass + encodePassword + dencodePass2;

				String[] FILTER_NAMES = { "All Files (*.*)" };
				// đuôi file có thể lưu
				String[] FILTER_EXTS = { "*.*" };

				FileDialog dlg = new FileDialog(shell, SWT.SAVE);
				dlg.setFilterNames(FILTER_NAMES);
				dlg.setFilterExtensions(FILTER_EXTS);
				dlg.setFileName("config.nar");
				String filename = dlg.open(); // ten file luu
				if (filename != null) {
					try {
						FileOutputStream outputStream;
						outputStream = new FileOutputStream(filename);
						byte[] strToBytes = dencode.getBytes();
						outputStream.write(strToBytes);
						outputStream.close();
					} catch (FileNotFoundException e1) {

					} catch (IOException e1) {
					}
				}
			}
		});

		// Button Open
		btnOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String dencodeIP = "321ҒҤҨңҨҢѱwmrnrhğÍĔğĒĔğĒáßâßàâàßҒҨңҖѧѤѥѦѧђђѱєѕ21ҥҥҖҢ9u4hqҘjsǆǖǜǖǜkdn289y2dgҨҢѱ, ;ep#@$$$$$ѢѤѢ$$dsgew t634ge!####$$@$$@fsdwe53@#$$!@$$$$$hfdy4i###&***(())&*&&%%%dtetee%ãàáĔĔđĠĔđĠ$%4%$##$l;l;o67aҎҡҠҎҡĎĤğĤĞğĤÒ×íÎÑíÑÐċÒÑ×Õ×ÓÝæãäĎĤğĤĞğĤÒ×íÎÑíÑÐċÒÑ×Õ×ÓÝæãäҠ%$%^n~n~n~%$#$^&%#%$^^n~n~&%^&^Y&&&*%$%&*^&$^#$&^&%$*(*(*(&$#@@ҥҥҖҢ&&*&*^$@#//--**-+++*/**';dr,n~n~n~n~g3 ,e;elteàáàßâããċÓÒÑèèęwp rmvg $%^ãàáĔĔđĠĔđĠ^$$%%#%ht5ty5%$$%2@ãàáĔĔđĠĔđĠ";
				String dencodeIP2 = "%ƙƘƛƜ%%@@!!ffgyyjnk$$fbkjf kjsf32yr jǆǖǜǖǜds53905rğÍĔğĒĔğĒáßâßàâàß3453253#%^ғҔҘёҕҘҘ^3290 968^^@@y5y54547877ҒҨңёҢҨҢҨёңѩѧѨѪѡєѕҏ549 7545r@";
				String decodeDatabase = "fj@##@%*Uu358íÐÑÒċÕÖ×Ó÷ҒҨңёҢҨҢҨёңѩѧѨѪѡєѕҏéüÚÚ×ØØ9353m23u523 fklҎҡҠҎҡҠew t34634tyj55^*^&(&)$#$DGFG#$54 5 56743%%$&%$854416y54754 2547y5 52gfjgf.,h;ldf,hgf;htjgf';w .qt.34 h(&(^$^+_+ǆǖǜǖǜƙƘƛƜƚƙƜƚƛƘƏƏƏƒƏƒ$%@#%#@32r1u4 64413y5654 ҎҡҠҎҡҠ4tyh6re4yh 4y34 y5643yàáàßâããċÓÒÑèèę634yfǆǖǜǖǜƙƘƛƜƚƙƜƚƛƘƏƏƏƒƏƒ++";
				String decodeDatabase2 = "^ngnҒҤҗҒҤіііҏ!!@#$wkjfewҘ90ҒҨңёҢҨҢҨёңѩѧѨѪѡєѕҏ235325%345HJҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљHJHҨҢѱJzbadҎҡҠad!@!$%^5YKѢѤҘҘѢKFDklNdk353523lANǆǖǜǖǜmsHJAU*%^*^&(#$DNF;B56F4D14s6d+6VD6G*&^*%^54856785475489798(&^(%^&&$#^(4+íÎĒĞĞĒáßÞáßÞáâßÞâßÞßÞ6";
				String dencodeUser = "48ne@#ҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљ$%%ҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљ%%wn~@CBEFDF565673737gnn4gkҘkBBDğÍĔğĒĔğĒáßâßàâàßğÍĔğĒĔğĒáßâßàâàßjdbjihҎҡҠqjhdHiNDJhDFQғҔҘёҕҘҘFѢѤѢ564T64T4T4ҨҢѱ#^@#^%*& Y43Y74%$&%$@# %Ht 344ǆǖǜǖǜƙƘƛƜƚƙƜƚƛƘƏƏƏƒƏƒ M23KLNTǆǖǜǖǜƙƘƛƜƚƙƜƚƛƘƏƏƏƒƏƒ 3ǆǖǜǖǜƙƘƛƜƚƙƜƚƛƘƏƏƏƒƏƒ T 46T346 #$ҎҡҠҎҡҠ^34 ^346 #$634 43 43BGFH VLMK GNKEGet@##DGS G$t@y34#$ BDFBғҔҘёҕĎĤğĤĞğĤÒ×íÎÑíÑÐċÒÑ×n~n~n~Õ×ÓÝn~n~n~æãäҘҘG 5h34 BDFҥҥҖҢD h34Y$#Y34Y34 RGKEN G4N GI23$ #$y#$ t$t#$634Y743 Y545423%#ãàáĔĔđĠĔđĠ*^&(àáàßâããċÓÒÑèèę&*_)@#@!$!æÝæċÒÑÐċÒíÎÑíÎ@";
				String dencodeUser2 = "ҒҒҗҤҒѕєѕҘ^&^&78hn~@CBEFDFҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљ565673737dkjf ewjh89xҒҨңёҢҨҢҨёңѩѧѨѪѡєѕҏnjbgbhҥğÍĔğĒĔğĒáßâßàâàßğÍĔğĒĔğĒáßâßàâàßҥҖҢhbjihjhҎҡҠudbjKLkjNNѢѤѢKkklkjnkldgnklkl A5614fhҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљ1r1h436^$#&$#&()*(_36$#64df h,grey547%$&%$*%^CBGDSGREW57ғҔҘёҕҘҘ4%$8568^(&*0*(@$@!$@!5#$gnhҎҡҠҎҡҠ%#@634754ҙҘҙѦѥѧѨѦѥѧғҧҕҗѱђѱѱєіѕҏїћљ*%^@!%n~n~n~$21";
				String dencodePass = "HJ7878&**(U*(*^&$$#%$#(*()dkjbgҎҡҠnğÍĔğĒĔğĒáßâßàâàßmgkl;lbfdf5ѢѤѢ4h546rҨҢѱ546ghr5e6hrt15hrehǜǖǜrereewǜǖǜǜǖǜ56r43y5645fre56y34h$^%$YBDF^#$^$&%$*56(hĎĔğĒÍĔĔğĒĎĔğĒÍĔĔğĒĎĔğĒÍĔĔğĒFdҥҥҖҢsFRQWF#$&%^*^&()^&*dsgl;2123FDGDFSҎҡҠҎҡҠdfhdfHFDHREyhreYreyTY346#$^n~n~n~34ёҕҘҘ4%$8568^(&*0*(@$@!$@!5ǖǜǖǜƙ#$gnhҎҡ6437ёҕҘҘ4%$8568^(&*ǖǜǖǜƙ0*(@$@!$@!5#$gnhҎҡҡҡҡҡҡ%ҙҘҙѦѥѧѨѦѥѧrwrqwrғҧҕҗѱfas@$^&/**';dr,n~n~n~n~g3 ,e;elteàáàßâããċÓÒÑèèęwp rmv**42142/**';dr,n~n~n~n~g3 ,e;elteàáàßâããċÓÒÑèG4N GI23$ #$y#$ t$t#èęwp rmv1";
				String dencodePass2 = "JUIuҥҥҖҢew5893e &*ҒҨңҖѧѤѥѦѧђђѱєѕ&*6ğÍĔğĒĔğĒáßâßàâàß784h jqwvғҔҘёҕҘҘrҎҡҠh3hҨҢѱ53 ^&^&**(()$%$#ǜǖǜǜǖǜǜǖǜѢѤѢ346 4fdBGdfgfdĎĔğĒÍĔĔğĒ56 hre456y4$^$#^ĎĔğĒÍĔĔğĒ!$!~!4 re 46634 TGR$#$^FDBREgew$#^$#&%^(*&^*)*(_()+_)ҎҡҠҎҡҠ@#!~#~!#!ҎҡҠҎҡҠYT&%$*BnTY#$Y%$JHJJ%n~n~n~n~^^ҥҥҖҢMNGFUI(I%ёҕҘҘ4%$8568^(&*0*(@$@!$@!5#$gǖǜǖǜƙnhҎҡ^&(&^%&n~n~n~n~%$*%$&*";

				Path filename = Paths.get("config.nar");
				String noidung = "";
				try {
					List<String> content = Files.readAllLines(filename);
					noidung = content.get(0);
					// textIP.setText(noidung.substring(noidung.indexOf(dencodeIP)+dencodeIP.length(),
					// noidung.indexOf(dencodeIP2)+1));

					char[] ip = noidung
							.substring(noidung.indexOf(dencodeIP) + dencodeIP.length(), noidung.indexOf(dencodeIP2))
							.toCharArray();
					char[] ipencode = ip.clone();
					for (int i = 0; i < ip.length; i++) {
						ipencode[i] = (char) (ip[i] - 1073);
					}
					String encodeIP = new String(ipencode);
					textIP.setText(encodeIP);

					char[] database = noidung.substring(noidung.indexOf(decodeDatabase) + decodeDatabase.length(),
							noidung.indexOf(decodeDatabase2)).toCharArray();
					char[] databaseencode = database.clone();
					for (int i = 0; i < database.length; i++) {
						databaseencode[i] = (char) (database[i] - 13);
					}
					String encodeDatabase=new String(databaseencode);
					textDatabasename.setText(encodeDatabase);

					char[] user = noidung.substring(noidung.indexOf(dencodeUser) + dencodeUser.length(),
							noidung.indexOf(dencodeUser2)).toCharArray();
					char[] userencode = user.clone();
					for (int i = 0; i < user.length; i++) {
						userencode[i] = (char) (user[i] - 357);
					}
					String encodeUser = new String(userencode);
					textUser.setText(encodeUser);

					char[] password = noidung.substring(noidung.indexOf(dencodePass) + dencodePass.length(),
							noidung.indexOf(dencodePass2)).toCharArray();
					char[] passwordencode = password.clone();
					for (int i = 0; i < password.length; i++) {
						passwordencode[i] = (char) (password[i] - 173);
					}
					String encodePassword = new String(passwordencode);
					textPassword.setText(encodePassword);
					
				} catch (Exception e1) {
				}

			}
		});

	}
}
