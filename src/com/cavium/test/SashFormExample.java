package com.cavium.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;

public class SashFormExample {
  Display display = new Display();
  Shell shell = new Shell(display);
  
  SashForm sashForm;
  SashForm sashForm2;

  public SashFormExample() {
    
    shell.setLayout(new FillLayout());
    
    sashForm = new SashForm(shell, SWT.HORIZONTAL);
    
    Text text1 = new Text(sashForm, SWT.CENTER);
    text1.setText("Text in pane #1");
    Text text2 = new Text(sashForm, SWT.CENTER);
    text2.setText("Text in pane #2");
    
    sashForm2 = new SashForm(sashForm, SWT.VERTICAL);
    
    Composite composite_1 = new Composite(sashForm2, SWT.NONE);
    
    Composite composite = new Composite(sashForm2, SWT.NONE);
    sashForm2.setWeights(new int[] {1, 1});
    sashForm.setWeights(new int[] {1, 1, 1});
//    sashForm2.setWeights(new int[] {1, 1});
//    sashForm.setWeights(new int[] {71, 145, 212});
    
    text1.addControlListener(new ControlListener() {
      public void controlMoved(ControlEvent e) {
      }

      public void controlResized(ControlEvent e) {
        System.out.println("Resized");
        
      }
    });
    
    
    shell.setSize(450, 200);
    shell.open();
    //textUser.forceFocus();

    // Set up the event loop.
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        // If no more entries in event queue
        display.sleep();
      }
    }

    display.dispose();
  }

  private void init() {

  }

  public static void main(String[] args) {
    new SashFormExample();
  }
}