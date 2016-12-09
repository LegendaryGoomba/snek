package snek;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SnekMain extends JFrame implements KeyListener{
  // private variables
  private JPanel menu, game, head, body;
  private JRadioButton easy, medium, hard;
  private JButton close, reset;
  public boolean inGame = true;
  public static boolean left, right, down = false;
  public static boolean up = true;
  public int x = 225;
  public int y = 500;

  public SnekMain() { // constructor
    super("Snek");
    this.setLayout(new BorderLayout());
    menu = new JPanel(new GridLayout(5, 1));
    game = new JPanel();
    head = new JPanel();

    easy = new JRadioButton("Easy", true);
    medium = new JRadioButton("Medium");
    hard = new JRadioButton("Hard");
    close = new JButton("Close");
    // close.setSize(20,20);
    reset = new JButton("Reset");
    menu.add(easy);
    menu.add(medium);
    menu.add(hard);
    menu.add(reset);
    menu.add(close);
    menu.setSize(200, 300);
    this.add(menu, BorderLayout.EAST);

    game.setLayout(null);
    game.setSize(550, 600);
    game.setBackground(new Color(0, 0, 0));
    this.add(game, BorderLayout.CENTER);
    this.setResizable(false);

    head.setBackground(new Color(255, 255, 255));
    game.add(head);
    head.setBounds(this.x, this.y, 20, 20);
    game.setFocusable(true);
    game.addKeyListener(this);
    this.addKeyListener(this);

    (new Thread(new Snake(this))).start();
    Thread snake = new Thread(new Snake(this));
    snake.start();
   

  }// end constructor
  
  @Override
  public void keyPressed(KeyEvent e) {
    // TODO Auto-generated method stub
    System.out.println("Pressed" + e.getKeyChar());
    int keyCode = e.getKeyCode();
    switch (keyCode) {
    case KeyEvent.VK_W:
      if (!this.down) {
        this.up = true;
        this.down = false;
        this.left = false;
        this.right = false;
      }
      break;
    case KeyEvent.VK_S:
      if (!this.up) {
        this.down = true;
        this.up = false;
        this.left = false;
        this.right = false;

      }
      break;
    case KeyEvent.VK_A:
      if (!this.right) {
        this.left = true;
        this.up = false;
        this.down = false;
        this.right = false;
      }
      break;
    case KeyEvent.VK_D:
      if (!this.left) {
        this.right = true;
        this.up = false;
        this.down = false;
        this.left = false;
      }
      break;
    default:
      break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub
    
  }

  public synchronized void moveUp(int x, int y) {
    while (!this.up) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    head.setBounds(x, y, 20, 20);
    repaint();
    notifyAll();
  }

  public synchronized void moveDown(int x, int y) {
    while (!this.down) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    head.setBounds(x, y, 20, 20);
    repaint();
    notifyAll();
  }

  public synchronized void moveLeft(int x, int y) {
    while (!this.left) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    head.setBounds(x, y, 20, 20);
    repaint();
    notifyAll();
  }

  public synchronized void moveRight(int x, int y) {
    while (!this.right) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    head.setBounds(x, y, 20, 20);
    repaint();
    notifyAll();
  }  
}
