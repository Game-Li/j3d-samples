package chapter1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;

public class MyPanel extends JPanel{

  public void paintComponent(Graphics comp){
    Graphics2D comp2D=(Graphics2D)comp;
    comp2D.setFont(new Font("Calibri",134,132));
    comp2D.drawString("Hello 2D", 22,100);
    comp2D.setColor(Color.BLUE);
  }
}