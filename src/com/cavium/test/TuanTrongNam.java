package com.cavium.test;

import java.util.Calendar;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TuanTrongNam {

	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//TuanTrongNam window = new TuanTrongNam();
			//window.open();

			Calendar c1=Calendar.getInstance();
			c1.set(2020, 12, 18);
			System.out.println("today is " + c1.WEEK_OF_YEAR + " week of the year");
			System.out.println("today is a " + c1.DAY_OF_MONTH + " month of the year");
			System.out.println("today is a " + c1.WEEK_OF_MONTH + " week of the month");

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
		shell.setSize(450, 300);
		shell.setText("SWT Application");

	}

}
