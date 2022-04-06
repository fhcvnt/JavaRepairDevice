package com.cavium.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Screenshot {

	protected Shell shell;
	private Text text;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Screenshot window = new Screenshot();
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
		shell.setSize(692, 410);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(shell, SWT.NONE);

		Button btnCapture = new Button(composite, SWT.NONE);
		btnCapture.setBounds(119, 90, 75, 25);
		btnCapture.setText("Capture");

		text = new Text(composite, SWT.BORDER);
		text.setBounds(119, 198, 76, 21);

		List list = new List(composite, SWT.BORDER);
		list.setBounds(336, 90, 71, 68);

		// chup anh man hinh
		btnCapture.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String[] FILTER_NAMES = { "JPG (*.jpg)", "PNG (*.png)" };
				// đuôi file có thể mở
				String[] FILTER_EXTS = { "*.jpg", "*.png" };

				FileDialog dlg = new FileDialog(shell, SWT.SAVE);
				dlg.setFilterNames(FILTER_NAMES);
				dlg.setFilterExtensions(FILTER_EXTS);
				dlg.setFileName("Lich truc phong");
				String imgFilePath = dlg.open(); // ten file luu
				if (imgFilePath != null) {
					try {
						// save the screenshot as a png
						GC gc = new GC(composite);
						Rectangle eclipseWindow = composite.getBounds();
						Image screenshot = new Image(Display.getDefault(), eclipseWindow);
						gc.copyArea(screenshot, eclipseWindow.x, eclipseWindow.y);
						gc.dispose();

						ImageLoader imgLoader = new ImageLoader();
						imgLoader.data = new ImageData[] { screenshot.getImageData() };
						imgLoader.save(imgFilePath, SWT.IMAGE_PNG);
					} catch (Exception exc) {

					}

				}
			}
		});

	}
}
