package snek;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SnekMain extends JFrame implements KeyListener {
  // private variables
  public static final int START_X = 300;
  public static final int START_Y = 550;
  public static final int X_BOUND = 605;
  public static final int Y_BOUND = 555;
  public static int DIFFICULTY = 10;
  private JPanel menu, game, head, body;
  private JRadioButton easy, medium, hard;
  private JButton close, reset;
  public boolean inGame = true;
  public boolean left, right, down = false;
  public boolean up = true;
  public int x = START_X;
  public int y = START_Y;
  private Thread snake;

  public SnekMain() { // constructor
    super("Snek");
    //create the actionListener
    ActionListener al = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getSource() == easy) {
          DIFFICULTY = 10;
          medium.setSelected(false);
          hard.setSelected(false);
          restart();
        } else if (e.getSource() == medium) {
          DIFFICULTY = 5;
          easy.setSelected(false);
          hard.setSelected(false);
          restart();
        } else if (e.getSource() == hard) {
          DIFFICULTY = 3;
          easy.setSelected(false);
          medium.setSelected(false);
          restart();
        } else if (e.getSource() == reset) {
          restart();
        } else if (e.getSource() ==  close) {
          System.exit(0);
        }
      }
    };
    //create all the objects on the window
    this.setLayout(new BorderLayout());
    menu = new JPanel(new GridLayout(5, 1));
    game = new JPanel();
    head = new JPanel();

    easy = new JRadioButton("Easy", true);
    easy.addActionListener(al);
    easy.setFocusable(false);

    medium = new JRadioButton("Medium");
    medium.addActionListener(al);
    medium.setFocusable(false);

    hard = new JRadioButton("Hard");
    hard.addActionListener(al);
    hard.setFocusable(false);

    close = new JButton("Close");
    close.addActionListener(al);
    close.setFocusable(false);

    reset = new JButton("Reset");
    reset.addActionListener(al);
    reset.setFocusable(false);

    menu.add(easy);
    menu.add(medium);
    menu.add(hard);
    menu.add(reset);
    menu.add(close);
    menu.setSize(200, 300);

    game.setLayout(null);
    game.setSize(400, 600);
    game.setBackground(new Color(0, 0, 0));

    head.setBackground(new Color(255, 255, 255));
    game.add(head);
    head.setBounds(this.x, this.y, 20, 20);
    game.setFocusable(true);
    game.addKeyListener(this);
    
    this.add(menu, BorderLayout.EAST);
    this.add(game, BorderLayout.CENTER);
    this.setResizable(false);
    this.addKeyListener(this);

    // (new Thread(new Snake(this))).start();
    snake = new Thread(new Snake(this));
    snake.start();
  }// end constructor

  @Override
  public void keyPressed(KeyEvent e) {
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
    case KeyEvent.VK_SPACE:
      this.restart();
    default:
      break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }// end keyListener

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

  public void restart() {
    if (this.x < 0 || this.x > X_BOUND || this.y < 0 || this.y > this.Y_BOUND) {
      JOptionPane.showMessageDialog(null, "Game Over :(");
    }
    this.x = START_X;
    this.y = START_Y;
    this.up = true;
    this.down = false;
    this.right = false;
    this.left = false;
  }
}
