package snek;

import javax.swing.WindowConstants;

public class SnekDriver {

  public static void main(String[] args) {
    SnekMain main = new SnekMain();   
    main.setSize(700, 600);
//    main.pack();
    main.setVisible(true);
    main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    main.setLocationRelativeTo(null);

  }

}
