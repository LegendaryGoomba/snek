package snek;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SnekMain extends JFrame implements KeyListener {
  // private variables
  public static final Font SCORE_FONT = new Font("Courier", Font.ROMAN_BASELINE, 45);
  public static final int START_X = 300;
  public static final int START_Y = 550;
  public static final int X_BOUND = 605;
  public static final int Y_BOUND = 550;
  public static final int BODY_WIDTH = 10;
  public static final int BODY_HEIGHT = 10;
  public int timer = 0;
  public static int DIFFICULTY = 10;
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
        } else if (e.getSource() == close) {
          System.exit(0);
        }
      }
    };
    // create all the objects on the window
    this.setLayout(new BorderLayout());
    menu = new JPanel(new GridLayout(6, 1));
    game = new JPanel();
    head = new JPanel();
    
    scoreField = new JTextArea(Integer.toString(this.score));
    scoreField.setFont(SCORE_FONT);
    
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

    menu.add(scoreField);
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

    getTarget();

    snake = new Thread(new Snake(this));
    snake.start();
  }// end constructor

  @Override
  public void keyPressed(KeyEvent e) {
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
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
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
    head.setBounds(x, y, 20, 20);
    xCoords.add(x);
    yCoords.add(y);
    
//    for(JPanel jp : snekBody) {
//      jp.setBounds(xCoords.get(timer-((score)*10))+5, yCoords.get(timer-((score+1)*10)), BODY_WIDTH, BODY_HEIGHT);
//    }
    //panels are showing up ontop of eachother, need to fix.
    for (int i = 0; i < snekBody.size(); i++) {
//    snekBody.get(i).setBounds(xCoords.get(timer-((score)*10))+5, yCoords.get(timer-((score+1)*10)+(i*10)), BODY_WIDTH, BODY_HEIGHT);
      snekBody.get(i).setBounds(xCoords.get(timer-((i*10)+1))+5, yCoords.get(timer-((i*10)+1)), BODY_WIDTH, BODY_HEIGHT);

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
    head.setBounds(x, y, 20, 20);
    xCoords.add(x);
    yCoords.add(y);

    for (int i = 0; i < snekBody.size(); i++) { 
//      snekBody.get(i).setBounds(xCoords.get(timer-((score)*10))+5, yCoords.get(timer-((score)*10)+(i*10)), BODY_WIDTH, BODY_HEIGHT);
      snekBody.get(i).setBounds(xCoords.get(timer-((i*10)+1))+5, yCoords.get(timer-((i*10)+1)), BODY_WIDTH, BODY_HEIGHT);

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
    head.setBounds(x, y, 20, 20);
    xCoords.add(x);
    yCoords.add(y);
    for (int i = 0; i < snekBody.size(); i++) { 
      snekBody.get(i).setBounds(xCoords.get(timer-((i*10)+1))+10, yCoords.get(timer-((i*10)+1))+5, BODY_WIDTH, BODY_HEIGHT);
//      snekBody.get(i).setBounds(xCoords.get(timer-((score)*10)+(i*10))+10, yCoords.get(timer-((score)*10))+5, BODY_WIDTH, BODY_HEIGHT);
    }
//    for(JPanel jp : snekBody) {
//      jp.setBounds(xCoords.get(timer-((score+1)*10)), yCoords.get(timer-((score)*10))+5, BODY_WIDTH, BODY_HEIGHT);
//    }
//    for(int i = 0; i < snekBody.size(); i++) {
//      if (i > 0) {
//        snekBody.get(i).setBounds(xCoords.get(timer-(10*score))+10, yCoords.get(timer-(10*score))+5, BODY_WIDTH, BODY_HEIGHT);
//      } else {
//        snekBody.get(i).setBounds(xCoords.get(timer-10)+10, yCoords.get(timer-10)+5, BODY_WIDTH, BODY_HEIGHT);
//      }
//    }
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
    head.setBounds(x, y, 20, 20);
    xCoords.add(x);
    yCoords.add(y);
    for (int i = 0; i < snekBody.size(); i++) {
//      snekBody.get(i).setBounds(xCoords.get(timer-((score)*10)+(i*10)), yCoords.get(timer-((score)*10))+5, BODY_WIDTH, BODY_HEIGHT);
//   works sort of   snekBody.get(i).setBounds(xCoords.get(timer-(score*(i*10))-1), yCoords.get(timer-(score*(i*10))-1)+5, BODY_WIDTH, BODY_HEIGHT);
      snekBody.get(i).setBounds(xCoords.get(timer-((i*10)+1)), yCoords.get(timer-((i*10)+1))+5, BODY_WIDTH, BODY_HEIGHT);

    }
//    for(JPanel jp : snekBody) {
//      jp.setBounds(xCoords.get(timer-((score)*10)), yCoords.get(timer-((score)*10))+5, BODY_WIDTH, BODY_HEIGHT);
//    }
//    for(int i = 0; i < snekBody.size(); i++) {
//      if (i > 0) {
//        snekBody.get(i).setBounds(xCoords.get(timer-(10*score)), yCoords.get(timer-(10*score))+5, BODY_WIDTH, BODY_HEIGHT);
//      } else {
//        snekBody.get(i).setBounds(xCoords.get(timer-10), yCoords.get(timer-10)+5, BODY_WIDTH, BODY_HEIGHT);
//      }
//    }
    repaint();
    notifyAll();
    revalidate();
  }

  // this method restarts the game if the head goes out of bounds
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
//    for(JPanel temp : snekBody){
//      game.remove(temp);
//    }
    this.snekBody.removeAll(snekBody);
    this.xCoords.removeAll(xCoords);
    this.yCoords.removeAll(yCoords);
    this.timer = 0;
    this.score = 0; //reset the score on restart
    this.scoreField.setText(Integer.toString(this.score));
    this.getTarget();
    repaint();
    revalidate();
  }

  // this method removes previous target and acquires a new target to consume
  // it also sets the score
  public void getTarget() {
    this.scoreField.setText(Integer.toString(this.score));
    try {
      game.remove(target);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
    targetX = new Random().nextInt(this.X_BOUND - 20) + 3;
    targetY = new Random().nextInt(this.Y_BOUND - 20) + 3;
    target = new JPanel();
    target.setBackground(new Color(255, 60, 60));
    target.setBounds(targetX, targetY, 10, 10);
    target.setVisible(true);
    game.add(target);
  }
  
  //this method is supposed to add a body JPanel to the snake, for some reason, it works but
  //only half way.
  public void addBody(int x, int y) {
    snekBody.add(new JPanel());
    snekBody.get(snekBody.size() - 1).setBackground(new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));
    snekBody.get(snekBody.size() - 1).setVisible(true);
    snekBody.get(snekBody.size() - 1).setBounds(x, y, BODY_WIDTH, BODY_HEIGHT);
    game.add(snekBody.get(this.snekBody.size() - 1));
    repaint();
    revalidate();
  }
}
