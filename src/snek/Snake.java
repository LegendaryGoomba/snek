package snek;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Snake implements Runnable {
  SnekMain snek;

  public Snake(SnekMain snek){
    this.snek = snek;
//    KeyHandler kh = new KeyHandler();
//    snek.addKeyListener(kh);
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
     if (snek.y < 0){
       
     }
     try {
       Thread.sleep(25);
//       System.out.println("sleeping " + snek.up +" "+snek.down+" "+snek.left+" "+snek.right);
     } catch (InterruptedException e) {
       e.printStackTrace();
     }
   }
  }
//  private class KeyHandler implements KeyListener {
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//      System.out.println("Pressed" + e.getKeyChar());
//      int keyCode = e.getKeyCode();
//      switch (keyCode) {
//      case KeyEvent.VK_UP:
//        if (!snek.down) {
//          snek.up = true;
//        }
//        break;
//      case KeyEvent.VK_DOWN:
//        if (!snek.up) {
//          snek.down = true;
//        }
//        break;
//      case KeyEvent.VK_LEFT:
//        if (!snek.right) {
//          snek.left = true;
//        }
//        break;
//      case KeyEvent.VK_RIGHT:
//        if (!snek.left) {
//          snek.right = true;
//        }
//        break;
//      default:
//        break;
//      }
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//      // TODO Auto-generated method stub
//      System.out.println("Pressed" + e.getKeyChar());
//
//    }
//
//    @Override
//    public void keyTyped(KeyEvent e) {
//      // TODO Auto-generated method stub
//      System.out.println("Pressed" + e.getKeyChar());
//
//    }
//
//  }
}
