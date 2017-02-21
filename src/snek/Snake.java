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
      Thread.sleep(1000);
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
      if (snek.y < 0 || snek.x < 0 || snek.y > snek.Y_BOUND || snek.x > snek.X_BOUND || snek.collision()) {
        // colliding with tail is a collision
        snek.scoreScreen();
        snek.restart();
      }

      // if you eat the target
      //works for entire head panel

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
      
      try {
        Thread.sleep(SnekMain.DIFFICULTY);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }//end while
  }
}
