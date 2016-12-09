package snek;

import javax.swing.WindowConstants;

public class SnekDriver {

  public static void main(String[] args) {
    SnekMain main = new SnekMain();   
    main.setLocationRelativeTo(null);
    main.setSize(700, 600);
    main.setVisible(true);
    main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

}
