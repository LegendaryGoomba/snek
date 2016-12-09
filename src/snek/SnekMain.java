package snek;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SnekMain extends JFrame{
  //private variables
  private JPanel menu, game;
  private JRadioButton easy, medium, hard;
  private JButton close, reset;
  public SnekMain(){ //constructor
    super("Snek");
    this.setLayout(new BorderLayout());
    menu = new JPanel(new GridLayout(5,1));
    game = new JPanel();
    
    easy = new JRadioButton("Easy", true);
    medium = new JRadioButton("Medium");
    hard = new JRadioButton("Hard");
    close = new JButton("Close");
//    close.setSize(20,20);
    reset = new JButton("Reset");
    menu.add(easy);
    menu.add(medium);
    menu.add(hard);
    menu.add(reset);
    menu.add(close);
    menu.setSize(200, 300);
    this.add(menu, BorderLayout.EAST);
    
    game.setSize(550, 600);
    game.setBackground(new Color(255, 255, 255));
    this.add(game, BorderLayout.CENTER);
    
    
  }//end constructor
}


