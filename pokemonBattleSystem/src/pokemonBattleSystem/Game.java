package pokemonBattleSystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;

public class Game extends JFrame {
	private JFrame frame = new JFrame();
	private JPanel panelStart = new JPanel();
    private JLayeredPane lpane = new JLayeredPane();
    private JPanel panelMenu = new JPanel();
    private JPanel panelGreen = new JPanel();
    private JPanel panelRed = new JPanel();
	private JPanel panelOrange = new JPanel(new FlowLayout());
	private JPanel panelBlue = new JPanel();
	private JPanel panelPurple = new JPanel(new GridLayout(0, 3));
	private JPanel panelBrown = new JPanel();
	private JPanel panelPink = new JPanel();
	private JButton buttonsMainMenu[] = {new JButton("Attack"), new JButton("Swap"), new JButton("Use Item"), new JButton("Surrender")};
	private JButton buttonsSubMenu[] = new JButton[6];
	private JPanel panelPokemonData[] = new JPanel[6];
	private JLabel labelPokemonData[] = new JLabel[6];

	private JPanel panelYellow = new JPanel(new GridLayout(0, 2));

	private JPanel panelData = new JPanel();
	private static JLabel optionData = new JLabel("Data");
	private static JButton start = new JButton("Start");
	private static JButton quit = new JButton("Quit");

	private String option = "";
	private static AudioInputStream audioIn;
	private static Clip clip;
	
	private static Manager manager = new Manager();
	
  public static void main(String[] args) {
    new Game();  
    manager.initializeBattle();
  }

  
  private class Clicklistener implements ActionListener{
	  public void actionPerformed(ActionEvent e){
		  if(e.getSource() == start) {
			  File soundFile = new File("sound/Battle Track.wav");
			  try {
				audioIn = AudioSystem.getAudioInputStream(soundFile);
				clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} catch (UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			  panelStart.setVisible(false);
			  lpane.setVisible(true);
		  }
		  if(e.getSource() == quit) {
			  System.exit(0);
		  }
		  if (e.getSource() == buttonsMainMenu[0] || e.getSource() == buttonsMainMenu[1]
				  || e.getSource() == buttonsMainMenu[2] || e.getSource() == buttonsMainMenu[3]){
			  panelYellow.setVisible(true);
		  }
		  else{
			  panelYellow.setVisible(false);
		  }
		  
		  if(e.getSource() == buttonsMainMenu[0] || e.getSource() == buttonsMainMenu[2]) {
			  if(e.getSource() == buttonsMainMenu[0]) {
				  option = "attack";
			  }
			  else if(e.getSource() == buttonsMainMenu[2]) {
				  option = "useItem";
			  }
			  buttonsSubMenu[2].setVisible(true);
			  buttonsSubMenu[3].setVisible(true);
			  buttonsSubMenu[4].setVisible(false);
			  buttonsSubMenu[5].setVisible(false);
			  setButtons();
		  }
		  if(e.getSource() == buttonsMainMenu[1]) {
			  for(int i = 2; i < buttonsSubMenu.length; i++) {
				  buttonsSubMenu[i].setVisible(true);
			  }
			  option = "swap";
			  setButtons();
		  }
		  if(e.getSource() == buttonsMainMenu[3]) {
			  for(int i = 2; i < buttonsSubMenu.length; i++) {
				  buttonsSubMenu[i].setVisible(false);
			  }
			  option = "surrender";
			  setButtons();
		  }

		  if(option.equals("surrender")) {
			  if(e.getSource() == buttonsSubMenu[0]) {
				  if (clip.isRunning()) { 
					  clip.stop();
				  }
				  reset();
			  }
			  else if(e.getSource() == buttonsSubMenu[1]) {
				  panelYellow.setVisible(false);
			  }
		  }
		  
		  if(option.equals("swap")) {
			  for(int i = 0; i < 6; i++) {
				  if(e.getSource() == buttonsSubMenu[i]) {
					  int index = manager.getPSPokemon();
					  manager.setPSPokemon(i);
					  buttonsSubMenu[index].setEnabled(true);
				  }
			  }
		  }
	  }
  }
  
  public Game() {
	  
	  Clicklistener click= new Clicklistener();
	  frame.setResizable(false);
	  frame.setPreferredSize(new Dimension(720, 1000));
      frame.setLayout(new BorderLayout());
      frame.add(lpane, BorderLayout.CENTER);
      frame.add(panelStart, BorderLayout.CENTER);
      lpane.setBounds(0, -5, 720, 1000);
      panelStart.setBounds(0, 500, 720, 1000);
      //panelBlue.setBackground(Color.BLUE);
      
      start.addActionListener(click);
      start.setBackground(new Color(0xb8ffc6));
      start.setPreferredSize(new Dimension(170, 100));
      
      quit.addActionListener(click);
      quit.setBackground(new Color(0xb8ffc6));
      quit.setPreferredSize(new Dimension(170, 100));
      
      panelStart.add(start);
      panelStart.add(quit);
      
      
      
      
      
      
      
      
      ImageIcon background = new ImageIcon("images/pokemon-background-new3.png");
      JLabel label1 = new JLabel(background);
      label1.setBounds(0, 0, 704, 1000);
      panelBlue.add(label1);
      
      panelBlue.setBounds(0, 0, 704, 1000);
      panelBlue.setOpaque(true);
      
      
      ImageIcon pokemon = new ImageIcon("images/v3.gif");
      JLabel label2 = new JLabel(pokemon);
      label2.setBounds(0, 0, 720, 1000);
      panelGreen.add(label2);
      
      //panelGreen.setBackground(Color.GREEN);
      panelGreen.setBounds(145, 310, 100, 100);
      panelGreen.setOpaque(false);
      
      
      ImageIcon pokemon2 = new ImageIcon("images/c2.png");
      JLabel label3 = new JLabel(pokemon2);
      label3.setBounds(0, 0, 720, 1000);
      panelRed.add(label3);
      
      //panelGreen.setBackground(Color.GREEN);
      panelRed.setBounds(485, 100, 100, 100);
      panelRed.setOpaque(false);
      
      
      
      
      panelBrown.setLayout(new BoxLayout(panelBrown, BoxLayout.PAGE_AXIS));

      panelBrown.setBorder(new LineBorder(Color.BLACK, 2, true));
      JPanel panelWhite = new JPanel(new FlowLayout());
      
      
      JLabel currentname1 = new JLabel("Venusaur");
      JLabel hp1 = new JLabel("HP");
      JProgressBar progressbar1 = new JProgressBar(0);
      progressbar1.setForeground(Color.GREEN.darker());
      progressbar1.setValue(100);
      progressbar1.setStringPainted(true);
      JLabel status1 = new JLabel("Off: 100 Def: 100");
      
      panelWhite.add(hp1);
      panelWhite.add(progressbar1);
      panelWhite.setOpaque(false);
      
      panelBrown.add(currentname1);
      panelBrown.add(panelWhite);
      panelBrown.add(status1);

      panelBrown.setBounds(380, 300, 220, 70);
      panelBrown.setOpaque(true);
      
      
      panelBrown.setBackground(Color.WHITE);
      
      panelPink.setLayout(new BoxLayout(panelPink, BoxLayout.PAGE_AXIS));
      panelPink.setBorder(new LineBorder(Color.BLACK, 2, true));
      
      JPanel panelGray = new JPanel(new FlowLayout());
      
      JLabel currentname2 = new JLabel("Charizard");
      JLabel hp2 = new JLabel("HP");
      JProgressBar progressbar2 = new JProgressBar(0);
      progressbar2.setForeground(Color.GREEN.darker());
      progressbar2.setValue(100);
      progressbar2.setStringPainted(true);
      JLabel status2 = new JLabel("Off: 100 Def: 100");
      
      panelGray.add(hp2);
      panelGray.add(progressbar2);
      panelGray.setOpaque(false);
      
      panelPink.add(currentname2);
      panelPink.add(panelGray);
      panelPink.add(status2);

      panelPink.setBounds(170, 100, 220, 70);
      panelPink.setOpaque(true);
      
      
      panelPink.setBackground(Color.WHITE);
      

      for(int i = 0; i < panelPokemonData.length; i++) {
    	  panelPokemonData[i] = new JPanel();
    	  labelPokemonData[i] = new JLabel("<html>First Pokemon<br>HP: 100<br>Def: 100 Off: 100</html>");
    	  panelPokemonData[i].setLayout(new BoxLayout(panelPokemonData[i], BoxLayout.PAGE_AXIS));
    	  labelPokemonData[i].setIcon(new ImageIcon("images/c3.png"));
    	  labelPokemonData[i].setOpaque(false);
          panelPokemonData[i].add(labelPokemonData[i]);
    	  panelPurple.add(labelPokemonData[i]);
      }
      

      panelPurple.setBounds(10, 500, 704, 150);
      //panelPurple.setAlignmentX(Component.CENTER_ALIGNMENT);
      panelPurple.setOpaque(true);
      
      
      for(int i = 0; i < buttonsMainMenu.length; i++) {
    	  buttonsMainMenu[i].setBackground(new Color(0xb8ffc6));
    	  buttonsMainMenu[i].addActionListener(click);
    	  buttonsMainMenu[i].setPreferredSize(new Dimension(170, 100));
    	  panelOrange.add(buttonsMainMenu[i]);
      }
      
      
      panelOrange.setBounds(-8, 650, 720, 1000);
      panelOrange.setOpaque(true);
      
      
      for(int i = 0; i < buttonsSubMenu.length; i++) {
    	  buttonsSubMenu[i] = new JButton(" ");
    	  buttonsSubMenu[i].setBackground(new Color(0xb8ffc6));
    	  buttonsSubMenu[i].addActionListener(click);
    	  buttonsSubMenu[i].setPreferredSize(new Dimension(90, 100));
    	  panelYellow.add(buttonsSubMenu[i]);
      }
      

      
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
      lpane.add(panelMenu, 5, 0);
      lpane.add(panelData, 4, 0);
      
      
      panelData.add(optionData);
      panelData.setBounds(0, 450, 704, 50);
      panelData.setBackground(Color.WHITE);
      panelData.setBorder(new LineBorder(Color.BLACK, 2, true));
      
      panelMenu.setBounds(0, 450, 704, 516);
      panelMenu.setBorder(new LineBorder(Color.BLACK, 2, true));
      panelMenu.setOpaque(false);
      
      lpane.setVisible(false);
      panelStart.setVisible(true);
      
      frame.pack();
      frame.setVisible(true);

  }
  
  public void reset() {
	  panelStart.setVisible(true);
	  lpane.setVisible(false);
	  panelYellow.setVisible(false);
  }
  
  public void setButtons() {
	  int index = manager.getPSPokemon();
	  if(option.equals("attack")) {
		  Attack attacks[] = manager.getPlayerPokemon()[index].getAttacks();
		  for(int i = 0; i < attacks.length; i++) {
			  buttonsSubMenu[i].setText(attacks[i].getName());
		  }
		  buttonsSubMenu[index].setEnabled(true);
	  }
	  else if(option.equals("swap")) {
		  Pokemon pokemon[] = manager.getPlayerPokemon();
		  for(int i = 0; i < pokemon.length; i++) {
			  buttonsSubMenu[i].setText(pokemon[i].getName());
		  }
		  buttonsSubMenu[index].setEnabled(false);
	  }
	  else if(option.equals("useItem")) {
		  Item items[] = manager.getPlayerItems();
		  for(int i = 0; i < items.length; i++) {
			  buttonsSubMenu[i].setText(items[i].getName());
		  }
		  buttonsSubMenu[index].setEnabled(true);
	  }
	  else if(option.equals("surrender")) {
		  buttonsSubMenu[0].setText("Confirm");
		  buttonsSubMenu[1].setText("Cancel");
		  buttonsSubMenu[index].setEnabled(true);
	  }
  }
}
