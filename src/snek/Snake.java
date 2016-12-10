package snek;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Snake implements Runnable {
  SnekMain snek;

  public Snake(SnekMain snek){
    this.snek = snek;
  }
  
  @Override
  public void run() {
   try {
     Thread.sleep(500);
   } catch (InterruptedException e) {
     e.printStackTrace();
     System.exit(1);
   }
   while(snek.inGame) {
     if (snek.up) {
       snek.y--;
       snek.moveUp(snek.x, snek.y);
     } else if (snek.down) {
       snek.y++;
       snek.moveDown(snek.x, snek.y);
     } else if (snek.right) {
       snek.x++;
       snek.moveRight(snek.x, snek.y);
     } else if (snek.left) {
       snek.moveLeft(snek.x, snek.y);
       snek.x--;
     }
     if (snek.y < 0 || snek.x < 0 || snek.y > snek.Y_BOUND || snek.x > snek.X_BOUND){
       snek.restart();
     }
     try {
       Thread.sleep(snek.DIFFICULTY);
//       System.out.println("sleeping " + snek.up +" "+snek.down+" "+snek.left+" "+snek.right);
     } catch (InterruptedException e) {
       e.printStackTrace();
     }
   }
  }
}
