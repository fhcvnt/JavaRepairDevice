package com.cavium.test;

import java.text.Collator;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class TableSortByColumn {
	public static int selectcolumn = -1; // cột được chọn, nếu chọn lần nữa sẽ sắp xếp ngược lại từ Z - A

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		final Table table = new Table(shell, SWT.BORDER);

		table.setHeaderVisible(true);
		final TableColumn tbcSTT = new TableColumn(table, SWT.NONE);
		tbcSTT.setText("Number");
		final TableColumn tbcTen = new TableColumn(table, SWT.NONE);
		tbcTen.setText("Location Code");
		tbcSTT.setWidth(100);
		tbcTen.setWidth(100);

		table.setSortColumn(tbcSTT);
		table.setSortDirection(SWT.UP);

		TableColumn tbcKhuvuc = new TableColumn(table, SWT.NONE);
		tbcKhuvuc.setWidth(100);
		tbcKhuvuc.setText("Location");

		TableColumn tbcIP = new TableColumn(table, SWT.NONE);
		tbcIP.setWidth(100);
		tbcIP.setText("IP");

		TableColumn tbcLoaimay = new TableColumn(table, SWT.NONE);
		tbcLoaimay.setWidth(100);
		tbcLoaimay.setText("Machine Code");

		TableColumn tbcGhichu = new TableColumn(table, SWT.NONE);
		tbcGhichu.setWidth(100);
		tbcGhichu.setText("Machine Type");

		TableColumn tbcNgaycapnhat = new TableColumn(table, SWT.NONE);
		tbcNgaycapnhat.setWidth(100);
		tbcNgaycapnhat.setText("Date Update");

		TableItem tableItem = new TableItem(table, SWT.NONE);
		tableItem.setText(new String[] { "1", "a", "A", "192.168.30.1", "pc", "PC", "20/02/2020" });

		TableItem tableItem_1 = new TableItem(table, SWT.NONE);
		tableItem_1.setText(new String[] { "2", "b", "B", "192.168.30.10", "pc", "PC", "20/02/2020" });

		TableItem tableItem_2 = new TableItem(table, SWT.NONE);
		tableItem_2.setText(new String[] { "3", "ac", "C", "192.168.30.254", "server", "ytryt", "25/02/2020" });

		TableItem tableItem_3 = new TableItem(table, SWT.NONE);
		tableItem_3.setText(new String[] { "4", "fg", "jhk5i", "192.168.100.25", "w", "fhtkiyol", "01/05/2019" });

		// Sắp xếp table theo một cột đã chọn
		Listener sortListener = new Listener() {
			public void handleEvent(Event e) {
				TableItem[] items = table.getItems();
				Collator collator = Collator.getInstance(Locale.getDefault());
				TableColumn column = (TableColumn) e.widget;
				int index = column == tbcSTT ? 0
						: column == tbcTen ? 1
								: column == tbcKhuvuc ? 2
										: column == tbcIP ? 3 : column == tbcLoaimay ? 4 : column == tbcGhichu ? 5 : 6;
				if (!(selectcolumn == index)) {
					selectcolumn = index;
					for (int i = 1; i < items.length; i++) {
						String value1 = items[i].getText(index);
						for (int j = 0; j < i; j++) {
							String value2 = items[j].getText(index);
							if (collator.compare(value1, value2) < 0) {

								String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2),
										items[i].getText(3), items[i].getText(4), items[i].getText(5),
										items[i].getText(6) };
								items[i].dispose();
								TableItem item = new TableItem(table, SWT.NONE, j);
								item.setText(values);
								items = table.getItems();
								break;
							}
						}
					}
				} else {
					selectcolumn = -1;
					for (int i = 1; i < items.length; i++) {
						String value1 = items[i].getText(index);
						for (int j = 0; j < i; j++) {
							String value2 = items[j].getText(index);
							if (collator.compare(value1, value2) > 0) {

								String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2),
										items[i].getText(3), items[i].getText(4), items[i].getText(5),
										items[i].getText(6) };
								items[i].dispose();
								TableItem item = new TableItem(table, SWT.NONE, j);
								item.setText(values);
								items = table.getItems();
								break;
							}
						}
					}
				}
				table.setSortColumn(column);
			}
		};

		tbcTen.addListener(SWT.Selection, sortListener);
		tbcKhuvuc.addListener(SWT.Selection, sortListener);
		tbcIP.addListener(SWT.Selection, sortListener);
		tbcLoaimay.addListener(SWT.Selection, sortListener);
		tbcGhichu.addListener(SWT.Selection, sortListener);
		// tbcNgaycapnhat.addListener(SWT.Selection, sortListener);

		shell.setSize(866, 440);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}