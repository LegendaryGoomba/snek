package snek;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Snake implements Runnable {
  SnekMain snek;

  public Snake(SnekMain snek) {
    this.snek = snek;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(800);
    } catch (InterruptedException e) {
      e.printStackTrace();
      System.exit(1);
    }
     
    while (snek.inGame) {
//      System.out.println(snek.timer);
//      System.out.println(snek.snekBody.size());
      /*----------------------------ADD A PAUSE BUTTON----------------------------*/
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

      // if you collide with the walls of the game
      if (snek.y < 0 || snek.x < 0 || snek.y > snek.Y_BOUND || snek.x > snek.X_BOUND) {
        // add colliding with the tail
        snek.restart();
      }

      // if you eat the target
      //only works for top left corner of head colliding with the target

      if(snek.eatTarget()) {
        if (snek.up) {
          snek.addBody();
        } else if (snek.down) {
          snek.addBody();
        } else if (snek.left) {
          snek.addBody();
        } else if (snek.right) {
          snek.addBody();
        }
        snek.score++;
        snek.getTarget();
      }
      
      if (snek.collision()) {
        snek.restart();
      }
      
      try {
        Thread.sleep(SnekMain.DIFFICULTY);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      
      
    }//end while
  }
}
