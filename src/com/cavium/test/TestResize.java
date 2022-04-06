package com.cavium.test;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class TestResize {

	protected Shell shell;
	private Table table;
	private Text text;
	private TableColumn tblclmName;
	private TableColumn tblclmnCode;
	private TableColumn tblclmnNumberFone;
	private TableColumn tblclmnDatabaseForSingle;
	private TableItem tableItem;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TestResize window = new TestResize();
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
		shell = new Shell();
		shell.setSize(605, 378);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setMaximized(true);
		
		int x=Display.getDefault().getBounds().width;
		int y=Display.getDefault().getBounds().height;
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(null);
		
		Button btnGetsize = new Button(composite, SWT.NONE);
		btnGetsize.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text.setText(Display.getDefault().getBounds().width+";"+Display.getDefault().getBounds().height);
			}
		});
		btnGetsize.setBounds(10, 10, 75, 25);
		btnGetsize.setText("Get Size");
		
		text = new Text(composite, SWT.BORDER);
		text.setBounds(107, 12, 165, 21);
		
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 41, x-30, y-150);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		tblclmName = new TableColumn(table, SWT.NONE);
		tblclmName.setWidth(100);
		tblclmName.setText("Name");
		
		
		tblclmnCode = new TableColumn(table, SWT.NONE);
		tblclmnCode.setWidth(100);
		tblclmnCode.setText("Code Department");
		
		
		tblclmnNumberFone = new TableColumn(table, SWT.NONE);
		tblclmnNumberFone.setWidth(100);
		tblclmnNumberFone.setText("Number phone");
		
		
		tblclmnDatabaseForSingle = new TableColumn(table, SWT.NONE);
		tblclmnDatabaseForSingle.setWidth(100);
		tblclmnDatabaseForSingle.setText("Database for single");
		tblclmnDatabaseForSingle.setResizable(true);
		
		
		tableItem = new TableItem(table, SWT.NONE);
		tableItem.setText(new String[] {"naruto", "1235", "te tatata ttathfjds gkl dsgklds ds ds sdsgj ldskgds", "dkl dsg  ds mkoewqtjqwtjpowq"});
		tableItem.setText("New TableItem");
		tblclmName.pack();
		tblclmnCode.pack();
		tblclmnNumberFone.pack();
		tblclmnDatabaseForSingle.pack();
		tblclmnDatabaseForSingle.pack();
		//table.pack();

	}
}
