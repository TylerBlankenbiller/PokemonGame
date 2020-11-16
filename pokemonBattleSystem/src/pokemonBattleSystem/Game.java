/************************************************************/
/* Authors: Trisha Badlu, Tyler Blankenbiller, Patrick Stelmach */
/* Majors: Masters of Computer Science */
/* Creation Date: October 20, 2020 */
/* Due Date: November 17, 2020 */
/* Course: CSC520 Object Oriented Design */
/* Professor Name: Ms. Donna Demarco */
/* Assignment: Final Project - Pokemon Battle System */
/* Filename: Game.java */
/* Purpose: The program is a battle simulator where the player */ 
/* fights Pokemon against a computer opponent*/
/************************************************************/

package pokemonBattleSystem;

import java.awt.BorderLayout;
import java.awt.Color;
//import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
//import java.awt.Image;
//import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
//import java.io.IOException;
//import java.util.concurrent.CountDownLatch;
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
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class Game extends JFrame {
	private JFrame frame = new JFrame();

    private JLayeredPane lpane = new JLayeredPane();

    private JPanel subpanels[] = new JPanel[14];
    
	//private JPanel panelPokemonData[] = new JPanel[3];
	private JLabel labelPokemonData[] = new JLabel[3];
	
	private ImageIcon imagePokemon[] = new ImageIcon[4];
	private JLabel labelPokemon[] = new JLabel[4];
	
	private JLabel currentPokemonName[] = new JLabel[2];
	private JProgressBar pokemonHPBar[] = new JProgressBar[2];
	private JLabel currentPokemonStats[] = new JLabel[2];
	
	private JButton buttonsMainMenu[] = {new JButton("Attack"), new JButton("Swap"), new JButton("Use Item"), new JButton("Surrender")};
	private JButton buttonsSubMenu[] = new JButton[4];

	
	private int t = 0;
	
	private JLabel optionData = new JLabel("");
	private JButton startButtons[] = {new JButton("Start"), new JButton("Quit")};
	private JButton mute = new JButton("Mute");
	private String option = "";
	private AudioInputStream audioIn;
	private Clip clip;
	private Clip soundEffect;
	private boolean selectFirst = false;
	private static Manager manager;
	private Clicklistener click= new Clicklistener();
	
  public static void main(String[] args) {
	  new Game();  
	  manager = new Manager();
  }

  
  private class Clicklistener implements ActionListener{
	  Timer timer1;
	  Timer timer2;
	  Timer timer3;
	  Timer timer4;
	  public void actionPerformed(ActionEvent e){
		  if(e.getSource() == startButtons[0]) {
			  processStart();
		  }
		  if(selectFirst) {
			 processStartSelection(e);
		  }
		  if(e.getSource() == startButtons[1]) {
			  System.exit(0);
		  }
		  if (e.getSource() == buttonsMainMenu[0] || e.getSource() == buttonsMainMenu[1]
				  || e.getSource() == buttonsMainMenu[2] || e.getSource() == buttonsMainMenu[3]){
			  
			  processMainOption(e);
		  }
		  else if(!selectFirst){
			  subpanels[9].setVisible(false);
		  }
		  if(e.getSource() == mute) {
			    if (clip.isRunning()) { 
			    	clip.stop();
			    	mute.setText("Unmute");
			    }
			    else {
			    	clip.loop(Clip.LOOP_CONTINUOUSLY);
			    	mute.setText("Mute");
			    }
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
				  subpanels[9].setVisible(false);
			  }
		  }
		  if(option.equals("endGame")) {
			  if(e.getSource() == buttonsSubMenu[0]) {
				  if (clip.isRunning()) { 
					  clip.stop();
				  }
				  reset();
				  option = "";
				  subpanels[0].setVisible(true);
			  }
			  else if(e.getSource() == buttonsSubMenu[1]) {
				  System.exit(0);
			  }
		  }
		  if(option.equals("swap")) {
			  processSwap(e);  
		  }
		  if(option.equals("attack")) {
			  processAttack(e);  
		  }
		  if(option.equals("useItem")) {
			  processUseItem(e);  
		  }
	  }
	  
	  public void processStart() {
		  manager.initializeBattle();  
		  Pokemon playerPokemon[] = manager.getPlayerPokemon();
		  for(int i = 0; i < labelPokemonData.length; i++) {
			  labelPokemonData[i].setText("test");
			  labelPokemonData[i].setIcon(new ImageIcon(playerPokemon[i].getIcon()));
		  }
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
		  //panelStart.setVisible(false);
		  subpanels[0].setVisible(false);
		  lpane.setVisible(true);
		  selectFirst = true;
		  Pokemon opponentPokemon = manager.getOSPokemon();
		  String arr[] = opponentPokemon.getChoosePokemon();
		  swapAnimation(3, 1, arr, 0);
		  opponentPokemon = manager.getOSPokemon();
		  currentPokemonName[1].setText(opponentPokemon.getName());
    	  currentPokemonStats[1].setText("Off: " + opponentPokemon.GetOffenseStatus() + " Def: " + opponentPokemon.GetDefenseStatus());
    	 // Pokemon playerPokemon[] = manager.getPlayerPokemon();
    	  for(int i = 0; i < playerPokemon.length; i++) {
    		  labelPokemonData[i].setText("<html>" + playerPokemon[i].getName() 
    				  					+ "<br>HP: " + playerPokemon[i].GetHP() 
    				  					+ "<br>Def: " + playerPokemon[i].GetDefenseStatus() 
    				  					+ " Off: " + playerPokemon[i].GetOffenseStatus() + "</html>");
    	  }
	  }
	  
	  public void processStartSelection(ActionEvent e) {
		  displayPokemon();
			 for(int i = 0; i < buttonsSubMenu.length; i++) {
				 if(e.getSource() == buttonsSubMenu[i]) {
					 manager.setPSPokemon(i);
					 //selectFirst = false;
					 enableMainMenu(true);
					 subpanels[9].setVisible(false);
					 Pokemon playerPokemon = manager.getPSPokemon();
					 //swapAnimation(0, 0, manager.getPlayerPokemon()[i]);
					 String arr[] = playerPokemon.getChoosePokemon();
					 swapAnimation(3, 0, arr, 0);
					 
					  currentPokemonName[0].setText(playerPokemon.getName());
			    	  currentPokemonStats[0].setText("Off: " + playerPokemon.GetOffenseStatus() + " Def: " + playerPokemon.GetDefenseStatus());
					 //swapAnimation(0, 0, "images/pokeball.gif", "images/open pokeball.png", "images/v3temp.png", "images/v3.gif", "", "", "", "");
					 break;
				 }
			 }
	  }
	  
	  public void processMainOption(ActionEvent e) {
		  subpanels[9].setVisible(true);
		  if(e.getSource() == buttonsMainMenu[0]) {
			  option = "attack";
		  }
		  else if(e.getSource() == buttonsMainMenu[2]) {
			  option = "useItem";
		  }
			  
		  else if(e.getSource() == buttonsMainMenu[1]) {
			  option = "swap";
		  }
		  else if(e.getSource() == buttonsMainMenu[3]) {	  
			  option = "surrender";
		  }
		  setButtons();
	  }
	  
	  public void processSwap(ActionEvent e) {
		  for(int i = 0; i < buttonsSubMenu.length; i++) {
			  if(e.getSource() == buttonsSubMenu[i]) {
				  //Pokemon playerPokemon = manager.getPSPokemon();
				 // int index = manager.getPSPokemon();
				 // manager.setPSPokemon(i);
				  buttonsSubMenu[i].setEnabled(true);
				  manager.processTurn(4, i+1);
				  Pokemon playerPokemon = manager.getPSPokemon();
				  String arr[] = manager.getSwapImages();//combineArrays(playerPokemon.getCallBackPokemon(), newPokemon.getChoosePokemon());//{"images/v3.gif","images/v3temp.png","images/open pokeball.png","images/pokeball.gif", "images/pokeball.gif", "images/open pokeball.png", "images/v3temp.png", "images/v3.gif"};
				  swapAnimation(7, 0, arr, 0);
				  
				  //swapAnimation(1, 0, "images/v3.gif","images/v3temp.png","images/open pokeball.png","images/pokeball.gif", "images/pokeball.gif", "images/open pokeball.png", "images/v3temp.png", "images/v3.gif");
				 if(t == 0) {
				  
				  String data = manager.getData();
      			  	optionData.setText(data);
				  completeRound();
				 }
				 else {
					 
					 optionData.setText("Player has swapped to " + playerPokemon.getName() + ".");
					 
				 }
    		    	//pokemonHPBar[0].setValue(p[i].GetHP());
    		    	currentPokemonName[0].setText(playerPokemon.getName());
    		    	  currentPokemonStats[0].setText("Off: " + playerPokemon.GetOffenseStatus() + " Def: " + playerPokemon.GetDefenseStatus());
				// t = 0;
				  break;
			  }
		  }
	  }
	  
	/*  public String[] combineArrays(String[] callback, String[] send) {
		  String images[] = new String[callback.length + send.length];
		  for(int i = 0; i < callback.length; i++) {
			  images[i] = callback[i];
		  }
		  for(int i = callback.length; i < send.length; i++) {
			  images[i] = send[i];
		  }
		  return images;
	  }*/
	  
	  public void processAttack(ActionEvent e) {
		  for(int i = 0; i < buttonsSubMenu.length; i++) {
			  if(e.getSource() == buttonsSubMenu[i]) {
				  option = "";
				  effectAnimation(1, "images/damage.png", "sound/emerald_000D.wav");
				  manager.processTurn(1, i+1);
				  String data = manager.getData();
				  optionData.setText(data);
				  Pokemon opponentPokemon = manager.getOSPokemon();
      			 // int osPokemon = manager.getOSPokemon();
				  pokemonHPBar[1].setValue(opponentPokemon.GetHP());
				 // System.out.print(opponentPokemon.GetHP());
				  if(manager.checkForWinner()) {
					  endGame();
					  return;
				  }
				  else if(opponentPokemon.GetHP() <= 0) {
					  int index = manager.processCPUTurn();
					  opponentPokemon = manager.getOSPokemon();
					  //Pokemon newOpponentPokemon = manager.getOSPokemon();
					  if(index == 4) {
					  String arr[] = manager.getSwapImages();
					  for(int in = 0; in < arr.length; in++) {
						  System.out.println(arr[in]);
						  }
					  
					  swapAnimation(7, 1, arr, 0);
					  }
					  //pokemonHPBar[1].setValue(opponentPokemon.GetHP());
					  //optionData.setText("Opponent has swapped to " + p[osPokemon].getName() + ".");
					  currentPokemonName[1].setText(opponentPokemon.getName());
    		    	  currentPokemonStats[1].setText("Off: " + opponentPokemon.GetOffenseStatus() + " Def: " + opponentPokemon.GetDefenseStatus());
    		    	  //String data = manager.getData();
	      			  //	optionData.setText(data);
					  completeRound();
					  break;
				  }
				  else {
					   //data = manager.getData();
	      			  	//optionData.setText(data);
					  completeRound();
					  break;
				  }
				  
			  }
		  }
	  }
	  
	  public void processUseItem(ActionEvent e) {
		  Pokemon playerPokemon = manager.getPSPokemon();
		  for(int i = 0; i < buttonsSubMenu.length; i++) {
			  if(e.getSource() == buttonsSubMenu[i]) {
				  effectAnimation(0, "images/item.png", "sound/emerald_000F.wav");
				  manager.processTurn(3, i+1);
				  labelPokemonData[playerPokemon.getIndex()].setText("<html>" + playerPokemon.getName() 
		  					+ "<br>HP: " + playerPokemon.GetHP()
		  					+ "<br>Def: " + playerPokemon.GetDefenseStatus() 
		  					+ " Off: " + playerPokemon.GetOffenseStatus() + "</html>");
				  currentPokemonStats[0].setText("Off: " + playerPokemon.GetOffenseStatus() + " Def: " + playerPokemon.GetDefenseStatus());
			      pokemonHPBar[0].setValue(playerPokemon.GetHP());
				  String data = manager.getData();
      			  optionData.setText(data);
				  completeRound();
				  break;
			  }
		  }
	  }
	  
	  public void endGame() {
		  String winner = manager.getWinner();
		  
		  enableMainMenu(false);
		  
		  if(winner == "Player") {
			  Pokemon opponentPokemon = manager.getOSPokemon();
			  String arr[] = opponentPokemon.getCallBackPokemon();
			  swapAnimation(3, 1, arr, 0);
		  }
		  else {
			  Pokemon playerPokemon = manager.getPSPokemon();
			  String arr[] = playerPokemon.getCallBackPokemon();
			  swapAnimation(3, 0, arr, 0);
		  }
		  //optionData.setText(winner + " has won the game!");
		  timer3 = new Timer(0, new ActionListener() {
			   
	            public void actionPerformed(ActionEvent e) {
	            	timer3.stop();
	            	optionData.setText(winner + " has won the game!");
	            	option = "endGame";
	            	manager.resetWinner();
	            	setButtons();
	            }
	         });

		    timer3.setInitialDelay(1000);
	    	 timer3.start();
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
	            			Pokemon opponentPokemon = manager.getOSPokemon();
	            			effectImage = "images/item.png";
	            			sound = "sound/emerald_000F.wav";
	            			effectAnimation(1, effectImage, sound);
	            			pokemonHPBar[1].setValue(opponentPokemon.GetHP());
	  					  //optionData.setText("Opponent has swapped to " + p[osPokemon].getName() + ".");
	      		    	  currentPokemonStats[1].setText("Off: " + opponentPokemon.GetOffenseStatus() + " Def: " + opponentPokemon.GetDefenseStatus());
	            		}
	            		/*else if (index == 4){
	            			swapAnimation(1, 0, "images/c3.png","images/c3temp.png","images/open pokeball.png","images/pokeball.gif", "images/pokeball.gif", "images/open pokeball.png", "images/c3temp.png", "images/c3.png");
	            		}*/
	            		String data = manager.getData();
	      			  	optionData.setText(data);
	      			  	option = "";
	      			  	
	      			  enableMainMenu(true);
	      			  Pokemon playerPokemon = manager.getPSPokemon();
	      			  //int psPokemon = manager.getPSPokemon();
	      			  String pokemonNameLabel = playerPokemon.getName() + " (Fainted)";
	      			  float pokemonHPLabel = playerPokemon.GetHP();
	      			  
	      			if(manager.checkForWinner()) {
						  endGame();
					  }
	      			else if(playerPokemon.isFainted()) {
	      				  t = 1;
	      				  
	      				  pokemonHPLabel = 0;
	      			  }
	      			  else {
	      				  pokemonNameLabel = playerPokemon.getName();
	      				  t = 0;
	      			  }
	      			labelPokemonData[playerPokemon.getIndex()].setText("<html>" + pokemonNameLabel 
  					+ "<br>HP: " + pokemonHPLabel
  					+ "<br>Def: " + playerPokemon.GetDefenseStatus() 
  					+ " Off: " + playerPokemon.GetOffenseStatus() + "</html>");
	      			  pokemonHPBar[0].setValue(playerPokemon.GetHP());
	      			
	      			//displayPokemon();
	      			  timer4.stop();
	      			  if(t == 1) {
	      				  option = "swap";
	      				enableMainMenu(false);
	      				//Pokemon playerSet[] = manager.getPlayerPokemon();
	      				setButtons();
	      				/*for(int i = 0; i < playerSet.length; i++) {
	      					buttonsSubMenu[i].setVisible(true);
	      					if(playerSet[i].isFainted() || i == manager.getPSPokemon().getIndex()) {
	      						buttonsSubMenu[i].setEnabled(false);
	      					}
		      				  buttonsSubMenu[i].setText(playerSet[i].getName());
		      			  }*/
	      				subpanels[9].setVisible(true);
	      				//swapAnimation(0, 0, "images/v3.gif","images/v3temp.png","images/open pokeball.png","images/pokeball.gif", "", "", "", "");
	      			  }
	      			
	            }
	         });
		   timer4.setInitialDelay(2500);
 	    	 timer4.start();
 	    	
 	    	
	  }
	  
	  public void effectAnimation(int index, String image, String sound) {
		  File soundFile = new File(sound);
		   try {
			audioIn = AudioSystem.getAudioInputStream(soundFile);
			soundEffect = AudioSystem.getClip();
			soundEffect.open(audioIn);
			if(mute.getText().equals("Mute")) {
				soundEffect.start();
			}
			 
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
		  subpanels[4+index].setVisible(true);
		  timer2 = new Timer(0, new ActionListener() {
			   
	            public void actionPerformed(ActionEvent e) {
	            	subpanels[4+index].setVisible(false);
	            	timer2.stop();
	            }
	         });

		    timer2.setInitialDelay(300);
	    	 timer2.start();
		  
	  }
	  
	 
	  
	  public void swapAnimation(int loop, int index, String arr[], int curr) {
		  //	System.out.println(loop);
		  	imagePokemon[index] = new ImageIcon(arr[curr]);
		      //JLabel labelPlayerPokemon = new JLabel(imagePlayerPokemon);
		      labelPokemon[index].setIcon(imagePokemon[index]);
	    	// Timer timer1;
	    	 /*for(int i = 0; i < buttonsMainMenu.length; i++) {
	    		 buttonsMainMenu[i].setEnabled(false);
	    	 }
	    	 for(int i = 0; i < buttonsSubMenu.length; i++) {
	    		 buttonsSubMenu[i].setEnabled(false);
	    	 }*/
		   timer1 = new Timer(0, new ActionListener() {
			   
	            public void actionPerformed(ActionEvent e) {
	            	 timer1.stop();
	            	if(selectFirst && index == 0) {
    		   	    	 
    		   	    	 enableMainMenu(true);
    		   	    	selectFirst = false;
	            	}
	            	
	            	enableSubMenu(true);
		   	    	 //swap = !swap;
		   	    	 //option = "swap2";
		    		   
		    		    if(loop > 0) {
		    		    	swapAnimation(loop-1, index, arr, curr+1);
		    		    } 
		    		    else {
		    		    	if(index == 1) {
		    		    		Pokemon opponentPokemon = manager.getOSPokemon();
		    		    		//int osPokemon = manager.getOSPokemon();
		    		    		pokemonHPBar[index].setValue(opponentPokemon.GetHP());
		    		    	}
		    		    	else {
		    		    		Pokemon playerPokemon = manager.getPSPokemon();
		    		    		//int psPokemon = manager.getPSPokemon();
		    		    		pokemonHPBar[index].setValue(playerPokemon.GetHP());
		    		    	}
		    		    	String data = manager.getData();
		      			  	optionData.setText(data);
		    		    	if(t != 0) {
								 enableMainMenu(true);
								 t = 0;
		    		    	}
		    		    }
	            }
	         });
		  timer1.setInitialDelay(100);
	    	 timer1.start();
	    	 
	  }
	  

  }
  
  
  
  public Game() {
	  for(int i = 0; i < subpanels.length; i++) {
		  subpanels[i] = new JPanel();
		  if(i == 7) {
			  subpanels[i].setLayout(new GridLayout(0, 3));
		  }
		  else if(i == 8) {
			  subpanels[i].setLayout(new FlowLayout());
		  }
		  else if(i == 9) {
			  subpanels[i].setLayout(new GridLayout(0, 2));
		  }
	  }
	  
	  
	  frame.setResizable(false);
	  frame.setPreferredSize(new Dimension(720, 1000));
      frame.setLayout(new BorderLayout());
      frame.add(lpane, BorderLayout.CENTER);
      frame.add(subpanels[0], BorderLayout.CENTER);
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	  
	  
      lpane.setBounds(0, -5, 720, 1000);
      subpanels[0].setBounds(0, 500, 720, 1000);

      
      for(int i = 0; i < startButtons.length; i++) {
    	  startButtons[i].addActionListener(click);
    	  startButtons[i].setBackground(new Color(0xb8ffc6));
    	  startButtons[i].setPreferredSize(new Dimension(170, 100));
    	  subpanels[0].add(startButtons[i]);
      }
       
      setupBackground();
 
      setupCurrentPokemon();
       
      setupEffects();
            
      setupDataSummary();

      setupBenchData();
      
      setupMenu();
      
      setupCurrentPokemonData();
      
      setupMuteButton();

      subpanels[10].setBounds(0, 450, 704, 516);
      subpanels[10].setBorder(new LineBorder(Color.BLACK, 2, true));
      subpanels[10].setOpaque(false);

      for(int i = 1; i < subpanels.length; i++) {
    	  lpane.add(subpanels[i], i-1, 0);
      }

      lpane.setVisible(false);
      subpanels[0].setVisible(true);
      
      frame.pack();
      frame.setVisible(true);

      
  }
  
  private void setupBackground() {
	  ImageIcon background = new ImageIcon("images/pokemon-background-new3.png");
      JLabel labelBackground = new JLabel(background);
      labelBackground.setBounds(0, 0, 704, 1000);
      subpanels[1].add(labelBackground);
      
      subpanels[1].setBounds(0, 0, 704, 1000);
      subpanels[1].setOpaque(true);
      
      for(int i = 0; i < labelPokemon.length; i++) {
    	  labelPokemon[i] = new JLabel();
      }
  }
  
  private void setupCurrentPokemon() {
	  for(int i = 0; i < imagePokemon.length-2; i++) {
		  imagePokemon[i] = new ImageIcon("images/pokeball.gif");
	      labelPokemon[i].setIcon(imagePokemon[0]);
	      labelPokemon[i].setBounds(0, 0, 720, 1000);
	      subpanels[i+2].add(labelPokemon[i]);
	      subpanels[i+2].setOpaque(false);
	  }
     
      subpanels[2].setBounds(145, 310, 100, 100);
      subpanels[3].setBounds(485, 100, 100, 100);

  }
  
  private void setupEffects() {

      labelPokemon[2].setIcon(imagePokemon[2]);
      labelPokemon[2].setBounds(0, 0, 720, 1000);
      subpanels[4].add(labelPokemon[2]);
      subpanels[4].setBounds(145, 320, 100, 100);
      subpanels[4].setOpaque(false);
      subpanels[4].setVisible(false);
      

      labelPokemon[3].setIcon(imagePokemon[3]);
      labelPokemon[3].setBounds(0, 0, 720, 1000);
      subpanels[5].add(labelPokemon[3]);
      subpanels[5].setBounds(485, 110, 100, 100);
      subpanels[5].setOpaque(false);
      
      subpanels[5].setVisible(false);
  }
  
  private void setupDataSummary() {
	  subpanels[6].add(optionData);
      subpanels[6].setBounds(0, 450, 704, 50);
      subpanels[6].setBackground(Color.WHITE);
      subpanels[6].setBorder(new LineBorder(Color.BLACK, 2, true));
  }
  
  private void setupBenchData() {
      subpanels[7].setBounds(10, 500, 704, 100);
      subpanels[7].setOpaque(true);
      for(int i = 0; i < labelPokemonData.length; i++) {
    	 // panelPokemonData[i] = new JPanel();
    	  labelPokemonData[i] = new JLabel("");
    	//  panelPokemonData[i].setLayout(new BoxLayout(panelPokemonData[i], BoxLayout.PAGE_AXIS));
    	 // labelPokemonData[i].setIcon(new ImageIcon("images/c3.png"));
    	  labelPokemonData[i].setOpaque(false);
        //  panelPokemonData[i].add(labelPokemonData[i]);
          subpanels[7].add(labelPokemonData[i]);
      }
  }
  
  private void setupMenu() {
      subpanels[8].setBounds(-8, 600, 720, 1000);
      subpanels[8].setOpaque(true);
      for(int i = 0; i < buttonsMainMenu.length; i++) {
    	  buttonsMainMenu[i].setBackground(new Color(0xb8ffc6));
    	  buttonsMainMenu[i].addActionListener(click);
    	  buttonsMainMenu[i].setPreferredSize(new Dimension(170, 100));
    	  buttonsMainMenu[i].setEnabled(false);
    	  subpanels[8].add(buttonsMainMenu[i]);
      }
      
      
      subpanels[9].setBounds(5, 740, 695, 200);
      subpanels[9].setOpaque(true);
      subpanels[9].setVisible(false);
      for(int i = 0; i < buttonsSubMenu.length; i++) {
    	  buttonsSubMenu[i] = new JButton(" ");
    	  buttonsSubMenu[i].setBackground(new Color(0xb8ffc6));
    	  buttonsSubMenu[i].addActionListener(click);
    	  buttonsSubMenu[i].setPreferredSize(new Dimension(90, 150));
    	  buttonsSubMenu[i].setEnabled(false);
    	  buttonsSubMenu[i].setVisible(false);
    	  subpanels[9].add(buttonsSubMenu[i]);
      }
  }
  
  private void setupCurrentPokemonData() {
	  
	  JPanel panelCurrentPokemonData[] = new JPanel[2];
      
	  for(int i = 0; i < panelCurrentPokemonData.length; i++) {
		  subpanels[11+i].setLayout(new BoxLayout(subpanels[11+i], BoxLayout.PAGE_AXIS));
		  panelCurrentPokemonData[i] = new JPanel(new FlowLayout());
    	  pokemonHPBar[i] = new JProgressBar(0);
    	  pokemonHPBar[i].setForeground(Color.GREEN.darker());
    	  pokemonHPBar[i].setValue(100);
    	  pokemonHPBar[i].setStringPainted(true);
    	  
    	  JLabel hp = new JLabel("HP");
    	  currentPokemonName[i] = new JLabel("-");
    	  currentPokemonStats[i] = new JLabel("Off: - Def: -");
    	  panelCurrentPokemonData[i].add(hp);
    	  panelCurrentPokemonData[i].add(pokemonHPBar[i]);
    	  panelCurrentPokemonData[i].setOpaque(false);
    	  subpanels[i+11].add(currentPokemonName[i]);
    	  subpanels[i+11].add(currentPokemonStats[i]);
    	  subpanels[i+11].add(panelCurrentPokemonData[i]);
    	  subpanels[i+11].setOpaque(true);
          subpanels[i+11].setBackground(Color.WHITE);
          subpanels[i+11].setBorder(new LineBorder(Color.BLACK, 2, true));
      }
      
      subpanels[11].setBounds(380, 300, 220, 70);
      subpanels[12].setBounds(170, 100, 220, 70);

  }
  
  private void setupMuteButton() {
	  subpanels[13].setBounds(600, 10, 100, 110);
      subpanels[13].setOpaque(false);
      subpanels[13].setVisible(true);

	  mute.setBackground(new Color(0xb8ffc6));
	  mute.setPreferredSize(new Dimension(80, 30));
	  mute.addActionListener(click);
	  mute.setEnabled(true);
	  mute.setVisible(true);
	  subpanels[13].add(mute);
	  
  }
  
  public void reset() {
	  subpanels[0].setVisible(true);
	  lpane.setVisible(false);
	  subpanels[9].setVisible(false);
	  
	  optionData.setText("");
	  
	  imagePokemon[0] = new ImageIcon("images/pokeball.gif");
	  //JLabel labelOpponentPokemon = new JLabel(imageOpponentPokemon);
	  labelPokemon[0].setIcon(imagePokemon[0]);  
	    
	  imagePokemon[1] = new ImageIcon("images/pokeball.gif");
	  //JLabel labelOpponentPokemon = new JLabel(imageOpponentPokemon);
	  labelPokemon[1].setIcon(imagePokemon[1]);  
	  
	  for(int i = 0; i < currentPokemonName.length; i++) {
		  currentPokemonName[i].setText("-");
    	  currentPokemonStats[i].setText("Off: - Def: -");
    	  pokemonHPBar[i].setValue(100);
    	  optionData.setText("");
	  }
	  
	  enableMainMenu(false);
  }
  
  public void setButtons() {
	  Pokemon playerPokemon = manager.getPSPokemon();
	  if(option.equals("attack")) {
		  Attack attacks[] = playerPokemon.getAttacks();
		  for(int i = 0; i < attacks.length; i++) {
			  buttonsSubMenu[i].setVisible(true);
			  buttonsSubMenu[i].setEnabled(true);
			  buttonsSubMenu[i].setText(attacks[i].getName());
		  }
		  buttonsSubMenu[playerPokemon.getIndex()].setEnabled(true);
	  }
	  else if(option.equals("swap")) {
		  Pokemon pokemon[] = manager.getPlayerPokemon();
		  for(int i = 0; i < pokemon.length; i++) {
			  buttonsSubMenu[i].setVisible(true);
			  buttonsSubMenu[i].setText(pokemon[i].getName());
			  if(pokemon[i].isFainted()) {
				  buttonsSubMenu[i].setEnabled(false);
			  }
			  else {
				  buttonsSubMenu[i].setEnabled(true);
			  }
		  }
		  for(int i = pokemon.length; i < buttonsSubMenu.length; i++) {
			  buttonsSubMenu[i].setVisible(false);
		  }
		  buttonsSubMenu[playerPokemon.getIndex()].setEnabled(false);
	  }
	  else if(option.equals("useItem")) {
		  Item items[] = manager.getPlayerItems();
		  for(int i = 0; i < items.length; i++) {
			  buttonsSubMenu[i].setVisible(true);
			  if(items[i].used == false) {
				  buttonsSubMenu[i].setEnabled(true);
			  }
			  if(items[i].used == true || (playerPokemon.GetHP() >= 100 && items[i].getName().equals("Heal Potion"))) {
				  buttonsSubMenu[i].setEnabled(false);
			  }
			  buttonsSubMenu[i].setText(items[i].getName());
		  }
		  //buttonsSubMenu[playerPokemon.getIndex()].setEnabled(true);
	  }
	  else if(option.equals("surrender")) {
		  buttonsSubMenu[0].setText("Confirm");
		  buttonsSubMenu[1].setText("Cancel");
		  buttonsSubMenu[0].setEnabled(true);
		  buttonsSubMenu[1].setEnabled(true);
		  for(int i = 2; i < buttonsSubMenu.length; i++) {
			  buttonsSubMenu[i].setVisible(false);
		  }
	  }
	  else if(option.equals("endGame")) {
		  subpanels[9].setVisible(true);
		  buttonsSubMenu[0].setText("Main Menu");
		  buttonsSubMenu[1].setText("Quit");
		  //buttonsSubMenu[index].setEnabled(true);
		  for(int i = 0; i < buttonsMainMenu.length; i++) {
			  buttonsMainMenu[i].setEnabled(false);
		  }
		  buttonsSubMenu[0].setEnabled(true);
		  buttonsSubMenu[1].setEnabled(true);
		  for(int i = 2; i < buttonsSubMenu.length; i++) {
			  buttonsSubMenu[i].setVisible(false);
		  }
	  }
  }
  

  public void displayPokemon() {
	  	Pokemon playerPokemon[] = manager.getPlayerPokemon();
	  	subpanels[9].setVisible(true);

		  for(int i = 0; i < playerPokemon.length; i++) {
			  buttonsSubMenu[i].setVisible(true);
			  buttonsSubMenu[i].setText(playerPokemon[i].getName());
		  }

}
  public void enableMainMenu(boolean enable) {
	  for(int i = 0; i < buttonsMainMenu.length; i++) {
    		 buttonsMainMenu[i].setEnabled(enable);
    	 }
  }
  
  public void enableSubMenu(boolean enable) {
	  for(int i = 0; i < buttonsSubMenu.length; i++) {
    		 buttonsSubMenu[i].setEnabled(enable);
    	 }
  }
  

}
