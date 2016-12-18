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
      System.out.println(snek.timer);
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
      if (snek.targetX + 4 > snek.head.getX() && snek.targetX + 6 < snek.head.getX() + 20
          && snek.targetY + 4 > snek.head.getY() && snek.targetY + 6 < snek.head.getY() + 20) {
        if (snek.up) {
          snek.addBody(snek.xCoords.get(snek.timer-20), snek.yCoords.get(snek.timer-20));
        } else if (snek.down) {
          snek.addBody(snek.xCoords.get(snek.timer-20), snek.yCoords.get(snek.timer-20));
        } else if (snek.left) {
          snek.addBody(snek.xCoords.get(snek.timer-20), snek.yCoords.get(snek.timer-20));
        } else if (snek.right) {
          snek.addBody(snek.xCoords.get(snek.timer-20), snek.yCoords.get(snek.timer-20));
        }
        snek.score++;
        snek.getTarget();
      }
      
      try {
        Thread.sleep(snek.DIFFICULTY);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }//end while
  }
}
