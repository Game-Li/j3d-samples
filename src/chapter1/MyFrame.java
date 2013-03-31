package chapter1;

import javax.swing.JFrame;

public class MyFrame extends JFrame{

  public static void main(String ...args){
    MyFrame f=new MyFrame();
    f.setContentPane(new MyPanel());
    f.setVisible(true);
    f.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

}
