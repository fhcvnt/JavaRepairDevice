package com.cavium.test;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class vidu {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			vidu window = new vidu();
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
		shell = new Shell(SWT.MIN | SWT.MAX | SWT.CLOSE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.RESIZE);
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 0, 380, 207);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setLocation(284, 52);
		lblNewLabel.setSize(55, 15);
		lblNewLabel.setText("New Label");
		
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setSize(55, 15);
		lblNewLabel_1.setText("New Label");
		
		
		shell.setMinimumSize(10,10);

	}
}
