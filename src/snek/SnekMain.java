package snek;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class SnekMain extends JFrame implements KeyListener {
  // private variables
  public final Font SCORE_FONT = new Font("Courier", Font.ROMAN_BASELINE, 60);
  public final int START_X = 300;
  public final int START_Y = 567;
  public final int X_BOUND = 607;
  public final int Y_BOUND = 567;
  public final int BODY_WIDTH = 10;
  public final int BODY_HEIGHT = 10;
  public final int HEAD_WIDTH = 10;
  public final int HEAD_HEIGHT = 10;
  public int timer = 0;
  public static int DIFFICULTY = 3;
  public JTextArea scoreField;
  public JPanel menu, game, head, body, target;
  public ArrayList<Integer> xCoords = new ArrayList<Integer>();
  public ArrayList<Integer> yCoords = new ArrayList<Integer>();
  public ArrayList<JPanel> snekBody = new ArrayList<JPanel>();
  private JRadioButton easy, medium, hard;
  private JButton close, reset;
  public boolean inGame = true;
  public boolean left, right, down = false;
  public boolean up = true;
  public int x = START_X;
  public int y = START_Y;
  public int targetX, targetY;
  public int score = 0;
  private Thread snake;

  public SnekMain() { // constructor
    super("Snek");
    // create the actionListener
    ActionListener al = new ActionListener() {
      @Override
      public synchronized void actionPerformed(ActionEvent e) {
          
        if (e.getSource() == easy) {
          DIFFICULTY = 10;
          easy.setSelected(true);
          medium.setSelected(false);
          hard.setSelected(false);
          restart();
          notifyAll();
        } else if (e.getSource() == medium) {
          DIFFICULTY = 3;
          easy.setSelected(false);
          medium.setSelected(true);
          hard.setSelected(false);
          restart();
          notifyAll();
        } else if (e.getSource() == hard) {
          DIFFICULTY = 2;
          easy.setSelected(false);
          medium.setSelected(false);
          hard.setSelected(true);
          restart();
          notifyAll();
        } else if (e.getSource() == reset) {
          notifyAll();
          restart();
        } else if (e.getSource() == close) {
          System.exit(0);
        }
      }
    };
    // create all the objects on the window
    this.setLayout(new BorderLayout());
    menu = new JPanel(new GridLayout(10, 1));
    game = new JPanel();
    head = new JPanel();
    
    scoreField = new JTextArea(Integer.toString(this.score));
    scoreField.setFont(SCORE_FONT);
    
//    dynamic = new JRadioButton("Dynamic");
//    dynamic.addActionListener(al);
//    dynamic.setFocusable(false);
    
    easy = new JRadioButton("Easy");
    easy.addActionListener(al);
    easy.setFocusable(false);

    medium = new JRadioButton("Medium", true);
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

    menu.add(scoreField);
//    menu.add(dynamic);
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
    head.setBounds(this.x, this.y, HEAD_WIDTH, HEAD_HEIGHT);
    game.setFocusable(true);
    game.addKeyListener(this);

    this.add(menu, BorderLayout.EAST);
    this.add(game, BorderLayout.CENTER);
    this.setResizable(false);
    this.addKeyListener(this);

    snekBody.add(head);
    getTarget();

    snake = new Thread(new Snake(this));
    snake.start();
  }// end constructor

  @Override
  public synchronized void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    switch (keyCode) {
    case KeyEvent.VK_W: //up
      if (!this.down) {
        this.up = true;
        this.down = false;
        this.left = false;
        this.right = false;
      }
      break;
    case KeyEvent.VK_S: //down
      if (!this.up) {
        this.down = true;
        this.up = false;
        this.left = false;
        this.right = false;

      }
      break;
    case KeyEvent.VK_A: //left
      if (!this.right) {
        this.left = true;
        this.up = false;
        this.down = false;
        this.right = false;
      }
      break;
    case KeyEvent.VK_D: //right
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
    }//end switch
  }

  @Override
  public synchronized void keyReleased(KeyEvent e) {
    notifyAll();
  }

  @Override
  public synchronized void keyTyped(KeyEvent e) {
    notifyAll();
  }// end keyListener

  // head moving up
  public synchronized void moveUp(int x, int y) {
    timer++;
    while (!this.up) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    head.setBounds(x, y, HEAD_WIDTH, HEAD_HEIGHT);
    xCoords.add(x);
    yCoords.add(y);
    
    for (int i = 0; i < snekBody.size(); i++) {
      //something in this is giving me an arrayindexoutofbouds exception
      snekBody.get(i).setBounds(xCoords.get(timer-((i*10)+1)), yCoords.get(timer-((i*10)+1)), BODY_WIDTH, BODY_HEIGHT);
    }
    repaint();
    notifyAll();
    revalidate();
  }

  // head moving down
  public synchronized void moveDown(int x, int y) {
    timer++;
    while (!this.down) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    head.setBounds(x, y, HEAD_WIDTH, HEAD_HEIGHT);
    xCoords.add(x);
    yCoords.add(y);

    for (int i = 0; i < snekBody.size(); i++) { 
      snekBody.get(i).setBounds(xCoords.get(timer-((i*10)+1)), yCoords.get(timer-((i*10)+1)), BODY_WIDTH, BODY_HEIGHT);
    }
    repaint();
    notifyAll();
    revalidate();
  }

  // head moving left
  public synchronized void moveLeft(int x, int y) {
    timer++;
    while (!this.left) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    head.setBounds(x, y, HEAD_WIDTH, HEAD_HEIGHT);
    xCoords.add(x);
    yCoords.add(y);
    for (int i = 0; i < snekBody.size(); i++) { 
      snekBody.get(i).setBounds(xCoords.get(timer-((i*10)+1)), yCoords.get(timer-((i*10)+1)), BODY_WIDTH, BODY_HEIGHT);
    }
    repaint();
    notifyAll();
    revalidate();
  }

  // head moving right
  public synchronized void moveRight(int x, int y) {
    timer++;
    while (!this.right) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    head.setBounds(x, y, HEAD_WIDTH, HEAD_HEIGHT);
    xCoords.add(x);
    yCoords.add(y);
    
    for (int i = 0; i < snekBody.size(); i++) {
      snekBody.get(i).setBounds(xCoords.get(timer-((i*10)+1)), yCoords.get(timer-((i*10)+1)), BODY_WIDTH, BODY_HEIGHT);
    }

    repaint();
    notifyAll();
    revalidate();
  }

  // this method restarts the game if the head goes out of bounds
  public synchronized void restart() {
//    if (this.x < 0 || this.x > X_BOUND || this.y < 0 || this.y > this.Y_BOUND) {
//    JOptionPane.showMessageDialog(null, "Game Over :(");
//    }
    this.x = START_X;
    this.y = START_Y;
    this.up = true;
    this.down = false;
    this.right = false;
    this.left = false;
    
    //remove all body parts from the snake
    this.snekBody.removeAll(snekBody);
    this.snekBody.add(head);
    this.xCoords.removeAll(xCoords);
    this.yCoords.removeAll(yCoords);
    this.score = 0; //reset the score on restart
    this.scoreField.setText(Integer.toString(this.score));
    
    //remove all components from the game board
    this.game.removeAll();
    
    //add back the head, listener, and get a target
    game.add(head);
    game.addKeyListener(this);
    this.getTarget();
    this.game.updateUI();
    
    this.timer = 0;
    repaint();
    revalidate();
    notifyAll();
  }

  // this method removes previous target and acquires a new target to consume
  // it also sets the score
  public synchronized void getTarget() {
    this.scoreField.setText(Integer.toString(this.score));
    try {
      game.remove(target);
    } catch (NullPointerException e) {
//      e.printStackTrace();
    }
    targetX = new Random().nextInt(this.X_BOUND - 20) + 3;
    targetY = new Random().nextInt(this.Y_BOUND - 20) + 3;
    target = new JPanel();
    target.setBackground(new Color(255, 60, 60));
    target.setBounds(targetX, targetY, 10, 10);
    target.setVisible(true);
    game.add(target);
    notifyAll();
  }
  
  //this method adds a body JPanel to the snake
  public synchronized void addBody() {
    for (int i = 0; i < 1; i++) {
      snekBody.add(new JPanel());
      snekBody.get(snekBody.size() - 1).setBackground(new Color(16,188,37));
  //    snekBody.get(snekBody.size() - 1).setBackground(new Color(new Random().nextInt(255), 
  //    new Random().nextInt(255), new Random().nextInt(255)));
      snekBody.get(snekBody.size() - 1).setVisible(true);
      game.add(snekBody.get(this.snekBody.size() - 1));
    }
    repaint();
    revalidate();
    notifyAll();
  }
  
  public synchronized boolean eatTarget() {
    if (((this.head.getX() >= this.targetX && this.head.getX() <= this.targetX + 10) && 
          (this.head.getY() >= this.targetY && this.head.getY() <= this.targetY + 10)) || 
        ((this.head.getX()+10 >= this.targetX && this.head.getX()+10 <= this.targetX + 10) && 
            (this.head.getY() >= this.targetY && this.head.getY() <= this.targetY + 10)) ||
        ((this.head.getX() >= this.targetX && this.head.getX() <= this.targetX + 10) && 
            (this.head.getY()+10 >= this.targetY && this.head.getY()+10 <= this.targetY + 10)) ||
        ((this.head.getX()+10 >= this.targetX && this.head.getX()+10 <= this.targetX + 10) && 
            (this.head.getY()+10 >= this.targetY && this.head.getY()+10 <= this.targetY + 10))) {
      notifyAll();
      return true;
    } else {
      notifyAll();
      return false;
    }
  }
  
  public synchronized boolean collision() {
    for (int i = 4; i < snekBody.size() - 1; i++) {
      if (((this.head.getX() >= snekBody.get(i).getX() && this.head.getX() <= snekBody.get(i).getX() + 10) && 
          (this.head.getY() >= snekBody.get(i).getY() && this.head.getY() <= snekBody.get(i).getY() + 10)) || 
        ((this.head.getX()+10 >= snekBody.get(i).getX() && this.head.getX()+10 <= snekBody.get(i).getX() + 10) && 
            (this.head.getY() >= snekBody.get(i).getY() && this.head.getY() <= snekBody.get(i).getY() + 10)) ||
        ((this.head.getX() >= snekBody.get(i).getX() && this.head.getX() <= snekBody.get(i).getX() + 10) && 
            (this.head.getY()+10 >= snekBody.get(i).getY() && this.head.getY()+10 <= snekBody.get(i).getY() + 10)) ||
        ((this.head.getX()+10 >= snekBody.get(i).getX() && this.head.getX()+10 <= snekBody.get(i).getX() + 10) && 
            (this.head.getY()+10 >= snekBody.get(i).getY() && this.head.getY()+10 <= snekBody.get(i).getY() + 10))) {
//        System.out.println("COLLISION");
        notifyAll();
        return true;
      } 
    } 
    notifyAll();
    return false;
  }
  
}
