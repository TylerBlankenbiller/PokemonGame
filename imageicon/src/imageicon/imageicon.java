package imageicon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;

public class imageicon extends JFrame {
	private JFrame frame = new JFrame();
    private JLayeredPane lpane = new JLayeredPane();
    private JPanel panelGreen = new JPanel();
    private JPanel panelRed = new JPanel();
	private JPanel panelOrange = new JPanel(new FlowLayout());
	private JPanel panelBlue = new JPanel();
	private JPanel panelPurple = new JPanel(new GridLayout(0, 3));
	private JPanel panelBrown = new JPanel();
	private JPanel panelPink = new JPanel();
	private JPanel temp = new JPanel();
	private JPanel panelYellow = new JPanel(new GridLayout(0, 2));
	private static JButton attack = new JButton("Attack");
	private static JButton useItem = new JButton("Use Item");
	private static JButton swap = new JButton("Swap");
	private static JButton surrender = new JButton("Surrender");
	private static JLabel label20 = new JLabel("HP: 100");
  public static void main(String[] args) {
    new imageicon();  
  }

  private class Clicklistener implements ActionListener{
	  public void actionPerformed(ActionEvent e){
		  if (e.getSource() == attack || e.getSource() == useItem){
			  panelYellow.setVisible(true);
		  }
		  else{
			  panelYellow.setVisible(false);
		  }
	  }
  }
  
  public imageicon() {
	  
	  Clicklistener click= new Clicklistener();
	  
	  frame.setPreferredSize(new Dimension(720, 1000));
      frame.setLayout(new BorderLayout());
      frame.add(lpane, BorderLayout.CENTER);
      lpane.setBounds(0, 0, 720, 1000);
      //panelBlue.setBackground(Color.BLUE);
      
      ImageIcon background = new ImageIcon("images/pokemon-background-new3.png");
      JLabel label1 = new JLabel(background);
      label1.setBounds(0, 0, 720, 1000);
      panelBlue.add(label1);
      
      panelBlue.setBounds(0, 0, 720, 1000);
      panelBlue.setOpaque(true);
      
      
      ImageIcon pokemon = new ImageIcon("images/v3.gif");
      JLabel label2 = new JLabel(pokemon);
      label2.setBounds(0, 0, 720, 1000);
      panelGreen.add(label2);
      
      //panelGreen.setBackground(Color.GREEN);
      panelGreen.setBounds(150, 310, 100, 100);
      panelGreen.setOpaque(false);
      
      
      ImageIcon pokemon2 = new ImageIcon("images/c2.png");
      JLabel label3 = new JLabel(pokemon2);
      label3.setBounds(0, 0, 720, 1000);
      panelRed.add(label3);
      
      //panelGreen.setBackground(Color.GREEN);
      panelRed.setBounds(490, 100, 100, 100);
      panelRed.setOpaque(false);
      
      
      
      
      panelBrown.setLayout(new BoxLayout(panelBrown, BoxLayout.PAGE_AXIS));

      panelBrown.setBorder(new LineBorder(Color.BLACK, 2, true));
      JPanel panelWhite = new JPanel(new FlowLayout());
      
      
      JLabel name1 = new JLabel("Venusaur");
      JLabel hp1 = new JLabel("HP");
      JProgressBar progressbar1 = new JProgressBar(0);
      progressbar1.setForeground(Color.GREEN);
      progressbar1.setValue(100);
      progressbar1.setStringPainted(true);
      JLabel status1 = new JLabel("Off: 100 Def: 100");
      
      panelWhite.add(hp1);
      panelWhite.add(progressbar1);
      panelWhite.setOpaque(false);
      
      panelBrown.add(name1);
      panelBrown.add(panelWhite);
      panelBrown.add(status1);

      panelBrown.setBounds(380, 300, 220, 70);
      panelBrown.setOpaque(true);
      
      
      panelBrown.setBackground(Color.WHITE);
      
      panelPink.setLayout(new BoxLayout(panelPink, BoxLayout.PAGE_AXIS));
      panelPink.setBorder(new LineBorder(Color.BLACK, 2, true));
      
      JPanel panelGray = new JPanel(new FlowLayout());
      
      JLabel name2 = new JLabel("Charizard");
      JLabel hp2 = new JLabel("HP");
      JProgressBar progressbar2 = new JProgressBar(0);
      progressbar2.setValue(100);
      progressbar2.setStringPainted(true);
      JLabel status2 = new JLabel("Off: 100 Def: 100");
      
      panelGray.add(hp2);
      panelGray.add(progressbar2);
      panelGray.setOpaque(false);
      
      panelPink.add(name2);
      panelPink.add(panelGray);
      panelPink.add(status2);

      panelPink.setBounds(170, 100, 220, 70);
      panelPink.setOpaque(true);
      
      
      panelPink.setBackground(Color.WHITE);
      
      
      
      
      temp.setLayout(new BoxLayout(temp, BoxLayout.PAGE_AXIS));
      JLabel label4 = new JLabel("First Pokemon");
      
      JLabel label21 = new JLabel("Off: 100 Def: 100");
      temp.add(label4);
      temp.add(label20);
      temp.add(label21);
     // label4.setBounds(10, 10, 10, 10);
      panelPurple.add(temp);
      
      JLabel label5 = new JLabel("Second Pokemon");
      //label5.setBounds(20, 0, 10, 10);
      panelPurple.add(label5);
      
      JLabel label6 = new JLabel("Third Pokemon");
      //label4.setBounds(0, 0, 10, 10);
      panelPurple.add(label6);
      
      JLabel label7 = new JLabel("Fourth Pokemon");
      //label5.setBounds(20, 0, 10, 10);
      panelPurple.add(label7);
      
      JLabel label8 = new JLabel("Fifth Pokemon");
      //label4.setBounds(0, 0, 10, 10);
      panelPurple.add(label8);
      
      JLabel label9 = new JLabel("Sixth Pokemon");
      //label5.setBounds(20, 0, 10, 10);
      panelPurple.add(label9);
      
      panelPurple.setBounds(30, 450, 720, 150);
      panelPurple.setOpaque(true);
      
      
      attack.addActionListener(click);
      attack.setPreferredSize(new Dimension(170, 100));

      
      swap.addActionListener(click);
      swap.setPreferredSize(new Dimension(170, 100));

      
      useItem.addActionListener(click);
      useItem.setPreferredSize(new Dimension(170, 100));

      
      surrender.addActionListener(click);
      surrender.setPreferredSize(new Dimension(170, 100));


      panelOrange.add(attack);
      panelOrange.add(swap);
      panelOrange.add(useItem);
      panelOrange.add(surrender);
      
      panelOrange.setBounds(-8, 650, 720, 1000);
      panelOrange.setOpaque(true);
      
      
      
      JButton option1 = new JButton("Option 1");
      option1.setPreferredSize(new Dimension(90, 100));

      JButton option2 = new JButton("Option 2");
      option2.setPreferredSize(new Dimension(90, 100));

      JButton option3 = new JButton("Option 3");
      option3.setPreferredSize(new Dimension(90, 100));

      JButton option4 = new JButton("Option 4");
      option4.setPreferredSize(new Dimension(90, 100));
      
      
      panelYellow.add(option1);
      panelYellow.add(option2);
      panelYellow.add(option3);
      panelYellow.add(option4);
      
      
      panelYellow.setBounds(5, 770, 695, 150);
      panelYellow.setOpaque(true);
      panelYellow.setVisible(false);
      
      
      
      lpane.add(panelBlue, 0, 0);
      lpane.add(panelGreen, 1, 0);
      lpane.add(panelRed, 2, 0);
      lpane.add(panelPurple, 3, 0);
      lpane.add(panelOrange, 4, 0);
      lpane.add(panelYellow, 5, 0);
      lpane.add(panelBrown, 3, 0);
      lpane.add(panelPink, 3, 0);
      
      frame.pack();
      frame.setVisible(true);
	  
	  
     
	  
   /* this.setTitle("Picture Application");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JFrame frame = new JFrame();
    
    JPanel panel3 = new JPanel();
    
    
    JPanel panel0 = new JPanel();
    panel0.setLayout(null);
    panel0.setPreferredSize( new Dimension( 720, 1000 ) );
    ImageIcon pic0 = new ImageIcon("images/pokemon-background-new2.png");
    JLabel label0 = new JLabel(pic0);
    Dimension size1 = label0.getPreferredSize();
    label0.setBounds(0, 0, size1.width, size1.height);

    panel0.add(label0);
    
    
    JPanel panel1 = new JPanel();
    
    panel1.setLayout(null);
    panel1.setPreferredSize( new Dimension( 1000, 1000 ) );
    ImageIcon pic = new ImageIcon("images/v3.gif");
    JLabel label = new JLabel(pic);
    Dimension size = label.getPreferredSize();
    label.setBounds(100, 100, size.width, size.height);

    panel1.add(label);
    label.setOpaque(false);
    panel1.setOpaque(false);
    panel0.setOpaque(false);

    frame.add(panel1, 1, 0);
    frame.add(panel0, 0, 0);

    frame.setVisible(true);
    frame.pack();*/
  }
}