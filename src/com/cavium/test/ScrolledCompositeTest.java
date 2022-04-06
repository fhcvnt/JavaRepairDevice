package com.cavium.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.*;
	import org.eclipse.swt.widgets.*;

	public class ScrolledCompositeTest {
		private static Text text;
		private static Text text_1;
		public static void main (String [] args) {
		      Display display = new Display ();
		      Color red = display.getSystemColor(SWT.COLOR_RED);
		      Color blue = display.getSystemColor(SWT.COLOR_BLUE);
		      Shell shell = new Shell (display);
		      shell.setLayout(new FillLayout());
		      
		      // set the minimum width and height of the scrolled content - method 2
		      final ScrolledComposite sc2 = new ScrolledComposite(shell, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		      sc2.setExpandHorizontal(true);
		      sc2.setExpandVertical(true);
		      final Composite c2 = new Composite(sc2, SWT.NONE);
		      sc2.setContent(c2);
		      //c2.setBackground(blue);
		      
		      Label lblNewLabel = new Label(c2, SWT.NONE);
		      lblNewLabel.setBounds(10, 33, 55, 15);
		      lblNewLabel.setText("New Label");
		      
		      text = new Text(c2, SWT.BORDER);
		      text.setBounds(244, 0, 164, 21);
		      
		      text_1 = new Text(c2, SWT.BORDER);
		      text_1.setBounds(0, 0, 76, 21);
		      sc2.setMinSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		 
		      shell.open ();
		      while (!shell.isDisposed ()) {
		          if (!display.readAndDispatch ()) display.sleep ();
		      }
		      display.dispose ();
		 }
	}