package org.conceptmetab.java;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class BasicDraw {
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.add(new MyComponent());
    frame.setSize(300, 300);
    frame.setVisible(true);
  }
}

class MyComponent extends JComponent {
  public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D)g;
    
    g2d.drawString("aString", 100, 100);
    AffineTransform at = new AffineTransform();
    at.setToRotation(Math.PI / 4.0);
    g2d.setTransform(at);
    g2d.drawString("aString", 100, 100);

  }
}
 