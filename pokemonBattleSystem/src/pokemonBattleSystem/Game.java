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
import java.util.concurrent.CountDownLatch;

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
import javax.swing.Timer;
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

	private JPanel panelEffect[] = new JPanel[2];

	private JPanel panelYellow = new JPanel(new GridLayout(0, 2));
private int t = 1;
	private JPanel panelData = new JPanel();
	private JLabel optionData = new JLabel("");
	private JButton start = new JButton("Start");
	private JButton quit = new JButton("Quit");
	private boolean swap = false;
	private ImageIcon imagePokemon[] = new ImageIcon[4];
	private JLabel labelPokemon[] = new JLabel[4];
	
    
    private int delay = 800;
	private String option = "";
	private AudioInputStream audioIn;
	private Clip clip;
	private Clip soundEffect;
	private boolean selectFirst = false;
	private static Manager manager = new Manager();
	private Clicklistener click= new Clicklistener();
  public static void main(String[] args) {
	  manager.initializeBattle();
	  new Game();  
  }

  
  private class Clicklistener implements ActionListener{
	  Timer timer1;
	  Timer timer2;
	  Timer timer3;
	  Timer timer4;
	  public void actionPerformed(ActionEvent e){
		  if(e.getSource() == start) {
			  File soundFile = new File("sound/Pokemon Battle Sound Track.wav");
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
			  selectFirst = true;
			  //showOpponent();
			  swapAnimation(0, 1, "images/pokeball.gif", "images/open pokeball.png", "images/c2temp.png", "images/c2.png", "", "", "", "");
			  //test();
			  displayPokemon();
			  
		  }
		  if(selectFirst) {
			 for(int i = 0; i < buttonsSubMenu.length; i++) {
				 if(e.getSource() == buttonsSubMenu[i]) {
					 manager.setPSPokemon(i);
					 selectFirst = false;
					 for(int j = 0; j < buttonsMainMenu.length; j++) {
						 buttonsMainMenu[j].setEnabled(true);
					 }
					 panelYellow.setVisible(false);
					 //swapAnimation(0, 0, manager.getPlayerPokemon()[i]);
					 swapAnimation(0, 0, "images/pokeball.gif", "images/open pokeball.png", "images/v3temp.png", "images/v3.gif", "", "", "", "");
					 break;
				 }
			 }
		  }
		  if(e.getSource() == quit) {
			  System.exit(0);
		  }
		  if (e.getSource() == buttonsMainMenu[0] || e.getSource() == buttonsMainMenu[1]
				  || e.getSource() == buttonsMainMenu[2] || e.getSource() == buttonsMainMenu[3]){
			  panelYellow.setVisible(true);
		  }
		  else if(!selectFirst){
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
				  option = "";
			  }
			  else if(e.getSource() == buttonsSubMenu[1]) {
				  panelYellow.setVisible(false);
			  }
		  }

		  if(option.equals("swap")) {
			  for(int i = 0; i < buttonsSubMenu.length; i++) {
				  if(e.getSource() == buttonsSubMenu[i]) {
					  int index = manager.getPSPokemon();
					  manager.setPSPokemon(i);
					  buttonsSubMenu[index].setEnabled(true);
					  swapAnimation(1, 0, "images/v3.gif","images/v3temp.png","images/open pokeball.png","images/pokeball.gif", "images/pokeball.gif", "images/open pokeball.png", "images/v3temp.png", "images/v3.gif");
					  manager.processTurn(4, i+1);
					  String data = manager.getData();
	      			  	optionData.setText(data);
					  completeRound();
					  break;
				  }
			  }
		  }
		  if(option.equals("attack")) {
			  for(int i = 0; i < buttonsSubMenu.length; i++) {
				  if(e.getSource() == buttonsSubMenu[i]) {
					  option = "";
					  effectAnimation(1, "images/damage.png", "sound/emerald_000D.wav");
					  manager.processTurn(1, i+1);
					  String data = manager.getData();
	      			  	optionData.setText(data);
					  completeRound();
					  break;
				  }
			  }
			  
		  }
		  
		  if(option.equals("useItem")) {
			  for(int i = 0; i < buttonsSubMenu.length; i++) {
				  if(e.getSource() == buttonsSubMenu[i]) {
					  effectAnimation(0, "images/item.png", "sound/emerald_000F.wav");
					  manager.processTurn(3, i+1);
					  String data = manager.getData();
	      			  	optionData.setText(data);
					  completeRound();
					  break;
				  }
			  }
		  }

	  }
	  
	  public void completeRound() {
		 // String data = "";
		  for(int i = 0; i < buttonsMainMenu.length; i++) {
			  buttonsMainMenu[i].setEnabled(false);
		  }
		   timer4 = new Timer(0, new ActionListener() {
				   
	            public void actionPerformed(ActionEvent e) {
	            		int index = manager.processCPUTurn();
	            		String effectImage = "";
	            		String sound = "";
	            		if(index == 1) {
	            			effectImage = "images/damage.png";
	            			sound = "sound/emerald_000D.wav";
	            			effectAnimation(0, effectImage, sound);
	            		}
	            		else if (index == 3){
	            			effectImage = "images/item.png";
	            			sound = "sound/emerald_000F.wav";
	            			effectAnimation(1, effectImage, sound);
	            		}
	            		else if (index == 4){
	            			swapAnimation(1, 0, "images/c3.png","images/c3temp.png","images/open pokeball.png","images/pokeball.gif", "images/pokeball.gif", "images/open pokeball.png", "images/c3temp.png", "images/c3.png");
	            		}
	            		String data = manager.getData();
	      			  	optionData.setText(data);
	      			  	option = "";
	      			  for(int i = 0; i < buttonsMainMenu.length; i++) {
	      				  buttonsMainMenu[i].setEnabled(true);
	      			  }
	      			  timer4.stop();
	            }
	         });
		   timer4.setInitialDelay(3250);
 	    	 timer4.start();
		   
	  }
	  
	  public void effectAnimation(int index, String image, String sound) {
		  File soundFile = new File(sound);
		   try {
			audioIn = AudioSystem.getAudioInputStream(soundFile);
			soundEffect = AudioSystem.getClip();
			soundEffect.open(audioIn);
			soundEffect.start();
			 
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
		  imagePokemon[index+2] = new ImageIcon(image);
		  labelPokemon[index+2].setIcon(imagePokemon[index+2]);
		  panelEffect[index].setVisible(true);
		  timer1 = new Timer(0, new ActionListener() {
			   
	            public void actionPerformed(ActionEvent e) {
	            	panelEffect[index].setVisible(false);
	            	timer1.stop();
	            }
	         });

		    timer1.setInitialDelay(300);
	    	 timer1.start();
		  
	  }
	  
	  public void swapAnimation(int loop, int index, String image1, String image2, String image3, String image4, String image5, String image6, String image7, String image8) {
	   //   try {
		  
		  /*	if(animationType == 0) {
		  			image1 = "images/pokeball.gif";
		  			image2 = "images/open pokeball.png";
		  			//image3 = pokemonToSwap.getImage(player, 0);
		  			//image4 = pokemonToSwap.getImage(player, 1);
		  			image3 = "images/v3temp.png";
		  			image4 = "images/v3.gif";
		  		
		  	}
		  	else {
		  		//image1 = pokemonToSwap.getImage(player, 1);
	  			//image2 = pokemonToSwap.getImage(player, 0);
	  			image1 = "images/v3.gif";
	  			image2 = "images/v3temp.png";
	  			image3 = "images/open pokeball.png";
	  			image4 = "images/pokeball.gif";
	  			
		  	}*/
		  	imagePokemon[index] = new ImageIcon(image1);
		      //JLabel labelPlayerPokemon = new JLabel(imagePlayerPokemon);
		      labelPokemon[index].setIcon(imagePokemon[index]);
	    	// Timer timer1;
	    	 for(int i = 0; i < buttonsMainMenu.length; i++) {
	    		 buttonsMainMenu[i].setEnabled(false);
	    	 }
	    	 for(int i = 0; i < buttonsSubMenu.length; i++) {
	    		 buttonsSubMenu[i].setEnabled(false);
	    	 }
		   timer1 = new Timer(0, new ActionListener() {
			   
	            public void actionPerformed(ActionEvent e) {
	            
		    			imagePokemon[index] = new ImageIcon(image2);
		    		    //JLabel labelOpponentPokemon = new JLabel(imageOpponentPokemon);
		    		    labelPokemon[index].setIcon(imagePokemon[index]);  
		    		    timer2 = new Timer(0, new ActionListener() {
		    				   
		    	            public void actionPerformed(ActionEvent e) {
		    	            		imagePokemon[index] = new ImageIcon(image3);
		    		    		    //JLabel labelOpponentPokemon = new JLabel(imageOpponentPokemon);
		    		    		    labelPokemon[index].setIcon(imagePokemon[index]);   
		    		    		    timer3 = new Timer(0, new ActionListener() {
		 		    				   
		    		    	            public void actionPerformed(ActionEvent e) {
		    		    	            	imagePokemon[index] = new ImageIcon(image4);
		    		    		    		    //JLabel labelOpponentPokemon = new JLabel(imageOpponentPokemon);
		    		    	            	labelPokemon[index].setIcon(imagePokemon[index]);   
		    		    	            	if(!selectFirst && index == 0) {
			    		    		   	    	 for(int i = 0; i < buttonsMainMenu.length; i++) {
			    		    			    		 buttonsMainMenu[i].setEnabled(true);
			    		    			    	 }
		    		    	            	}
		    		    	            	for(int i = 0; i < buttonsSubMenu.length; i++) {
		    		    			    		 buttonsSubMenu[i].setEnabled(true);
		    		    			    	 }
		    		    		   	    	 //swap = !swap;
		    		    		   	    	 //option = "swap2";
		    		    		    		    timer3.stop();
		    		    		    		    if(loop == 1) {
		    		    		    		    	swapAnimation(0, 0, image5, image6, image7, image8, "", "", "", "");
		    		    		    		    }
		    		    	            }
		    		    	         });
		    		    		    timer2.stop();
		    		    		    timer3.setInitialDelay(100);
		   		    	    	 timer3.start();
		    	            }
		    	         });
		    		    timer1.stop();
		    		    timer2.setInitialDelay(100);
		    	    	timer2.start();

		    	    	
	            }
	         });
		  timer1.setInitialDelay(delay);
	    	 timer1.start();
	    	 
	    	 //Timer timer2;
	    	 

	/*	} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	  }
	  

  }
  
  public Game() {
	  

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
      JLabel labelBackground = new JLabel(background);
      labelBackground.setBounds(0, 0, 704, 1000);
      panelBlue.add(labelBackground);
      
      panelBlue.setBounds(0, 0, 704, 1000);
      panelBlue.setOpaque(true);
      
      for(int i = 0; i < labelPokemon.length; i++) {
    	  labelPokemon[i] = new JLabel();
      }
      
      panelEffect[0] = new JPanel();
      //JLabel labelPlayerPokemon = new JLabel(imagePlayerPokemon);
      labelPokemon[2].setIcon(imagePokemon[2]);
      labelPokemon[2].setBounds(0, 0, 720, 1000);
      panelEffect[0].add(labelPokemon[2]);
      panelEffect[0].setBounds(145, 320, 100, 100);
      panelEffect[0].setOpaque(false);
      
      panelEffect[0].setVisible(false);
      
      panelEffect[1] = new JPanel();
      //JLabel labelOpponentPokemon = new JLabel(imageOpponentPokemon);
      labelPokemon[3].setIcon(imagePokemon[3]);
      labelPokemon[3].setBounds(0, 0, 720, 1000);
      panelEffect[1].add(labelPokemon[3]);
      panelEffect[1].setBounds(485, 110, 100, 100);
      panelEffect[1].setOpaque(false);
      
      panelEffect[1].setVisible(false);
      
      //ImageIcon imagePlayerPokemon = new ImageIcon("images/v3.gif");
      imagePokemon[0] = new ImageIcon("images/pokeball.gif");
      //JLabel labelPlayerPokemon = new JLabel(imagePlayerPokemon);
      labelPokemon[0].setIcon(imagePokemon[0]);
      labelPokemon[0].setBounds(0, 0, 720, 1000);
      panelGreen.add(labelPokemon[0]);
      
      //panelGreen.setBackground(Color.GREEN);
      panelGreen.setBounds(145, 310, 100, 100);
      panelGreen.setOpaque(false);
      
      
      //ImageIcon imageOpponentPokemon = new ImageIcon("images/c2.png");
      imagePokemon[1] = new ImageIcon("images/pokeball.gif");
      //JLabel labelOpponentPokemon = new JLabel(imageOpponentPokemon);
      labelPokemon[1].setIcon(imagePokemon[1]);
      labelPokemon[1].setBounds(0, 0, 720, 1000);
      panelRed.add(labelPokemon[1]);
      
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
      lpane.add(panelEffect[0], 5, 0);
      lpane.add(panelEffect[1], 5, 0);
      
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

      
    /*  try {
		  for(int i = 0; i < buttonsMainMenu.length; i++) {
			  buttonsMainMenu[i].setEnabled(false);
		  }
		Thread.sleep(2500);
		imageOpponentPokemon = new ImageIcon("images/open pokeball.png");
	    //JLabel labelOpponentPokemon = new JLabel(imageOpponentPokemon);
	    labelOpponentPokemon.setIcon(imageOpponentPokemon);
	    Thread.sleep(100);
	    imageOpponentPokemon = new ImageIcon("images/c2temp.png");
	    //JLabel labelOpponentPokemon = new JLabel(imageOpponentPokemon);
	    labelOpponentPokemon.setIcon(imageOpponentPokemon);
	    Thread.sleep(100);
	    imageOpponentPokemon = new ImageIcon("images/c2.png");
	    //JLabel labelOpponentPokemon = new JLabel(imageOpponentPokemon);
	    labelOpponentPokemon.setIcon(imageOpponentPokemon);
	    displayPokemon();

	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
      
      
  }
  
  public void reset() {
	  panelStart.setVisible(true);
	  lpane.setVisible(false);
	  panelYellow.setVisible(false);
	  
	  optionData.setText("");
	  
	  imagePokemon[0] = new ImageIcon("images/pokeball.gif");
	  //JLabel labelOpponentPokemon = new JLabel(imageOpponentPokemon);
	  labelPokemon[0].setIcon(imagePokemon[0]);  
	    
	  imagePokemon[1] = new ImageIcon("images/pokeball.gif");
	  //JLabel labelOpponentPokemon = new JLabel(imageOpponentPokemon);
	  labelPokemon[1].setIcon(imagePokemon[1]);  
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
  

  public void displayPokemon() {
	  	Pokemon playerPokemon[] = manager.getPlayerPokemon();
		  panelYellow.setVisible(true);

		  for(int i = 0; i < buttonsSubMenu.length; i++) {
			  buttonsSubMenu[i].setVisible(true);
			  buttonsSubMenu[i].setText(playerPokemon[i].getName());
		  }

}
  

}
