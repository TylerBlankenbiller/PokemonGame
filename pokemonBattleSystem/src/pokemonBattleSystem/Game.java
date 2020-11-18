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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

public class Game extends JFrame {
	// Window frame for GUI
	private JFrame frame = new JFrame();
	// Panel to organize other panels into layers
    private JLayeredPane lpane = new JLayeredPane();
    // Panels for each component of GUI (Pokemon HP, Bench data, Menu options)
    private JPanel subpanels[] = new JPanel[14];
    // Labels for pokemon bench
	private JLabel labelBenchPokemonData[] = new JLabel[3];
	// Array to store images of current player Pokemon, current opponent Pokemon, attack image, and use item image
	private JLabel labelPokemon[] = new JLabel[4];
	// Array to store label of current player and opponent Pokemon names
	private JLabel currentPokemonName[] = new JLabel[2];
	// Array to store progress bar of current player and opponent Pokemon HP
	private JProgressBar pokemonHPBar[] = new JProgressBar[2];
	// Array to store label of current player and opponent Pokemon offense and defense statuses
	private JLabel currentPokemonStats[] = new JLabel[2];
	// Array to store buttons of main menu options
	private JButton buttonsMainMenu[] = {new JButton("Attack"), new JButton("Swap"), 
										 new JButton("Use Item"), new JButton("Surrender")};
	// Array to store buttons of sub menu options
	private JButton buttonsSubMenu[] = new JButton[4];
	// Determine if player needs to swap Pokemon after a round
	private int needToSwap = 0;
	// JLabel to display summary after an option has been processed
	private JLabel dataSummary = new JLabel("");
	// Array to store start buttons
	private JButton startButtons[] = {new JButton("Start"), new JButton("Quit")};
	// Button to mute sound
	private JButton mute = new JButton("Mute");
	// String to store main menu option that has been selected
	private int option = -1;
	// Audio stream for sound
	private AudioInputStream audioIn;
	// Music clip
	private Clip music;
	// Sound effects clip for damage or use item
	private Clip soundEffect;
	// Determine if player is selecting their first Pokemon for the game after battle has been initialized
	private boolean selectFirst = false;
	// Game manager
	private static Manager manager;
	// Listen for click action event
	private Clicklistener click = new Clicklistener();
	
  public static void main(String[] args) {
	  // Create new instance of game
	  new Game();  
	  // Create new instance of manager
	  manager = new Manager();
  }

  private class Clicklistener implements ActionListener{
	  Timer timerSwap;
	  Timer timerEffect;
	  Timer timerEndGame;
	  Timer timerCompleteRound;
	  
	 /**********************************************************
	  Function name: actionPerformed
	  Description: Checks if an action event has occurred
	  Parameters: ActionEvent e - event that has occurred
	  Return value: None
	 **********************************************************/
	  public void actionPerformed(ActionEvent e){
		  // Start button has been clicked
		  if(e.getSource() == startButtons[0]) {
			  processStart();
		  }
		  // Player has to make their first Pokemon selection
		  if(selectFirst) {
			 processStartSelection(e);
		  }
		  // Quit button has been clicked
		  if(e.getSource() == startButtons[1]) {
			  System.exit(0);
		  }
		  // One of the main menu buttons has been clicked
		  if (e.getSource() == buttonsMainMenu[0] || e.getSource() == buttonsMainMenu[1]
				  || e.getSource() == buttonsMainMenu[2] || e.getSource() == buttonsMainMenu[3]){
			  processMainOption(e);
		  }
		  // Sub menu button has been clicked after player has selected their first Pokemon
		  else if(!selectFirst){
			  subpanels[9].setVisible(false);
		  }
		  // Mute button has been clicked
		  if(e.getSource() == mute) {
			  // Stop music
			  if (music.isRunning()) {
				  music.stop();
				  mute.setText("Unmute");
			  }
			  // Start music
			  else {
				  music.loop(Clip.LOOP_CONTINUOUSLY);
				  mute.setText("Mute");
			    }
		  }
		  // Surrender button has been clicked
		  if(option == 3) {
			  // Confirm button has been clicked
			  if(e.getSource() == buttonsSubMenu[0]) {
				  // Stop music
				  if (music.isRunning()) { 
					  music.stop();
				  }
				  reset();
				  option = -1;
			  }
			  // Cancel button has been clicked
			  else if(e.getSource() == buttonsSubMenu[1]) {
				  subpanels[9].setVisible(false);
			  }
		  }
		  // Game has ended
		  if(option == 4) {
			  // Return to main menu button has been clicked
			  if(e.getSource() == buttonsSubMenu[0]) {
				  // Stop music
				  if (music.isRunning()) { 
					  music.stop();
				  }
				  reset();
				  option = -1;
				  subpanels[0].setVisible(true);
			  }
			  // Quit button has been clicked
			  else if(e.getSource() == buttonsSubMenu[1]) {
				  System.exit(0);
			  }
		  }
		  // Attack button has been clicked
		  if(option == 0) {
			  processAttack(e);  
		  }
		  // Swap button has been clicked
		  if(option == 1) {
			  processSwap(e);  
		  }
		  // Use item button has been clicked
		  if(option == 2) {
			  processUseItem(e);  
		  }
	  }
	  
	 /**********************************************************
	  Function name: processStart
	  Description: Initializes battle and sets up GUI based on
	  				the generated Pokemon and Items
	  Parameters: None
	  Return value: None
	 **********************************************************/
	  public void processStart() {
		  manager.initializeBattle();  
		  // Get Pokemon that has been generated for player and opponent
		  Pokemon playerPokemon[] = manager.getPlayerPokemon();
		  Pokemon opponentPokemon = manager.getOSPokemon();
		  // Start music
		  File soundFile = new File("sound/Pokemon Battle Sound Track.wav");
		  try {
			  audioIn = AudioSystem.getAudioInputStream(soundFile);
			  music = AudioSystem.getClip();
			  music.open(audioIn);
			  music.loop(Clip.LOOP_CONTINUOUSLY);
		  } 
		  catch (UnsupportedAudioFileException e1) {
			  e1.printStackTrace();
		  } 
		  catch (IOException e1) {
			  e1.printStackTrace();
		  } 
		  catch (LineUnavailableException e1) {
			  e1.printStackTrace();
		  }
		  // Make initial panel invisible (panel that contains start and quit buttons)
		  subpanels[0].setVisible(false);
		  // Make panel visible that contains Pokemon data and menu options
		  lpane.setVisible(true);
		  // Player has to select their first Pokemon after they were generated
		  selectFirst = true;
		  // Get swap images for current opponent Pokemon
		  String opponentImages[] = opponentPokemon.getChoosePokemon();
		  // Do swap animation for opponent
		  swapAnimation(3, 1, opponentImages, 0);
		  // Display Pokemon data in bench
    	  	  for(int i = 0; i < playerPokemon.length; i++) {
    		  	labelBenchPokemonData[i].setText("<html>" + playerPokemon[i].getName() 
    				  					+ "<br>HP: " + playerPokemon[i].getHP() 
    				  					+ "<br>Def: " + playerPokemon[i].getDefenseStatus() 
    				  					+ " Off: " + playerPokemon[i].getOffenseStatus() + "</html>");
    		  	labelBenchPokemonData[i].setIcon(new ImageIcon(playerPokemon[i].getIcon()));
    	  	 }
	  }
	  
	 /**********************************************************
	  Function name: processStartSelection
	  Description: Displays Pokemon to select to battle with
	  Parameters: ActionEvent e - event that has occurred
	  Return value: None
	 **********************************************************/
	  public void processStartSelection(ActionEvent e) {
		  // Display Pokemon to select from
		  displayPokemon();
		  // Check which Pokemon has been selected
		  for(int i = 0; i < buttonsSubMenu.length; i++) {
			  if(e.getSource() == buttonsSubMenu[i]) {
				  // Set player selected Pokemon index
				  manager.setPSPokemon(i);
				  // Allow player to choose an option (attack, swap, use item, surrender)
				  enableMainMenu(true);
				  // Set sub menu to invisible
				  subpanels[9].setVisible(false);
				  // Get player selected Pokemon
				  Pokemon playerPokemon = manager.getPSPokemon();
				  // Get images for current player Pokemon
				  String playerImages[] = playerPokemon.getChoosePokemon();
				  // Do swap animation for player
				  swapAnimation(3, 0, playerImages, 0);
				  // Make sub menu options invisible
				  subpanels[9].setVisible(false);
				  break;
			 }
		 }
	  }
	  
	 /**********************************************************
	  Function name: processMainOption
	  Description: Determine which main menu option was selected
	  			   and set sub menu options based on option
	  Parameters: ActionEvent e - event that has occurred
	  Return value: None
	 **********************************************************/
	  public void processMainOption(ActionEvent e) {
		  // Make sub menu options visible after main menu option has been selected
		  subpanels[9].setVisible(true);
		  // Attack button has been clicked
		  if(e.getSource() == buttonsMainMenu[0]) {
			  option = 0;
		  }
		  // Swap button has been clicked
		  else if(e.getSource() == buttonsMainMenu[1]) {
			  option = 1;
		  }
		  // Use item button has been clicked
		  else if(e.getSource() == buttonsMainMenu[2]) {
			  option = 2;
		  }
		  // Surrender button has been clicked
		  else if(e.getSource() == buttonsMainMenu[3]) {	  
			  option = 3;
		  }
		  // Set sub menu buttons based on main menu option that has been selected
		  setButtons();
	  }
	  
	 /**********************************************************
	  Function name: processSwap
	  Description: Process player's option to swap pokemon, 
	  			   display summary, and complete the round by
	  			   having the opponent select their option
	  Parameters: ActionEvent e - event that has occurred
	  Return value: None
	 **********************************************************/
	  public void processSwap(ActionEvent e) {
		  // Check which Pokemon has been selected to swap to
		  for(int i = 0; i < buttonsSubMenu.length; i++) {
			if(e.getSource() == buttonsSubMenu[i]) {
                  		  buttonsSubMenu[i].setEnabled(true);
				  // Process player's turn based on main menu and sub menu options
				  manager.processTurn(option, i);
				  // Get swap images for player selected Pokemon
				  String playerImages[] = manager.getSwapImages();
				  // Do swap animation for player
				  swapAnimation(7, 0, playerImages, 0);
				  // Display data summary after processing player's turn
				  String data = manager.getData();
				  dataSummary.setText(data);
				  // Complete round 
				  if(needToSwap == 0) {
					  // Process opponent's turn
					  completeRound();
				  }
                  updateData();
				  break;
			}
		}
	  }
	  
	 /**********************************************************
	  Function name: processAttack
	  Description: Process player's option to attack, display 
	  			   summary, and complete the round by having the 
	  			   opponent select their option
	  Parameters: ActionEvent e - event that has occurred
	  Return value: None
	 **********************************************************/
	  public void processAttack(ActionEvent e) {
		  // Check which Attack has been selected
		  for(int i = 0; i < buttonsSubMenu.length; i++) {
			  if(e.getSource() == buttonsSubMenu[i]) {
				  // Process player's turn
				  manager.processTurn(option, i);
				  // Do attack animation
				  effectAnimation(1, "images/damage.png", "sound/emerald_000D.wav");
				  // Display data summary after processing player's turn
				  String data = manager.getData();
				  dataSummary.setText(data);
				  // Update opponent's HP bar
				  Pokemon opponentPokemon = manager.getOSPokemon();
				  pokemonHPBar[1].setValue(Math.round(opponentPokemon.getHP()*100/opponentPokemon.getMaxHP()));
				  // Update current Pokemon data
				  updateData();
				  // Reset option
				  option = -1;
				  // Check if there is a winner after processing player's turn
				  if(manager.isWinner()) {
					  endGame();
					  return;
				  }
				  // Opponent's Pokemon is fainted
				  else if(opponentPokemon.getHP() <= 0) {
					  // Make opponent swap Pokemon
					  manager.processCPUTurn();
					  opponentPokemon = manager.getOSPokemon();
					  // Get swap images for opponent selected Pokemon
					  String opponentImages[] = manager.getSwapImages();
					  // Do swap animation for opponent
					  swapAnimation(7, 1, opponentImages, 0);
					  // Update current Pokemon data
					  updateData();
    		    	  // Process opponent's turn after swapping
					  completeRound();
					  break;
				  }
				  else {
					  // Process opponent's turn
					  completeRound();
					  break;
				  }
				  
			  }
		  }
	  }
	
	 /**********************************************************
	  Function name: updateData
	  Description: Updates current and bench Pokemon HP and 
	  			   stats
	  Parameters: None
	  Return value: None
	 **********************************************************/
	  private void updateData() {
		  Pokemon currentPokemon[] = {manager.getPSPokemon(), manager.getOSPokemon()};
		  
		  for(int i = 0; i < currentPokemon.length; i++) {
			  currentPokemonStats[i].setText("Off: " + currentPokemon[i].getOffenseStatus() 
						   + " Def: " + currentPokemon[i].getDefenseStatus());
			  pokemonHPBar[i].setValue(Math.round(currentPokemon[i].getHP()*100/currentPokemon[i].getMaxHP()));
		  }
		  labelBenchPokemonData[currentPokemon[0].getIndex()].setText("<html>" + currentPokemon[0].getName() 
			+ "<br>HP: " + currentPokemon[0].getHP()
			+ "<br>Def: " + currentPokemon[0].getDefenseStatus() 
			+ " Off: " + currentPokemon[0].getOffenseStatus() + "</html>");
	  }
	  
	 /**********************************************************
	  Function name: processUseItem
	  Description: Process player's option to use item, display 
	  			   summary, and complete the round by having the 
	  			   opponent select their option
	  Parameters: ActionEvent e - event that has occurred
	  Return value: None
	 **********************************************************/
	  public void processUseItem(ActionEvent e) {
		  // Check which Item has been selected
		  for(int i = 0; i < buttonsSubMenu.length; i++) {
			  if(e.getSource() == buttonsSubMenu[i]) {
				  // Process player's turn
				  manager.processTurn(option, i);
				  // Get player selected Pokemon
				  Pokemon playerPokemon = manager.getPSPokemon();
				  // Do effect animation for use item
				  effectAnimation(0, "images/item.png", "sound/emerald_000F.wav");
				  // Update bench data
				  updateData();
			      	 // Display data summary after processing player's turn
				 String data = manager.getData();
      			  	 dataSummary.setText(data);
      			  	 // Process opponent's turn
				 completeRound();
				 break;
			  }
		 }
	  }
	  
	 /**********************************************************
	  Function name: endGame
	  Description: ActionEvent e - event that has occurred
	  Parameters: None
	  Return value: None
	 **********************************************************/
	  public void endGame() {
		  // Get winner
		  String winner = manager.getWinner();
		  // Disable main menu
		  enableMainMenu(false);
		  // Player won
		  if(winner == "Player") {
			  // Get opponent selected Pokemon
			  Pokemon opponentPokemon = manager.getOSPokemon();
			  // Get swap images for opponent selected Pokemon
			  String opponentImages[] = opponentPokemon.getCallBackPokemon();
			  // Do swap animation for opponent
			  swapAnimation(3, 1, opponentImages, 0);
		  }
		  // Opponent won
		  else {
			  // Get player selected Pokemon
			  Pokemon playerPokemon = manager.getPSPokemon();
			  // Get swap images for player selected Pokemon
			  String playerImages[] = playerPokemon.getCallBackPokemon();
			  // Do swap animation for player
			  swapAnimation(3, 0, playerImages, 0);
		  }
		  // Wait 1000 ms after last move is performed to display winner
		  timerEndGame = new Timer(0, new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	timerEndGame.stop();
	            	// Display data summary
	            	String data = manager.getData();
	            	dataSummary.setText(data);
	            	option = 4;
	            	manager.resetWinner();
	            	setButtons();
	            }
	        });
		  // Set delay
		  timerEndGame.setInitialDelay(1000);
		  // Start timer
		  timerEndGame.start();
	  }
	  
	 /**********************************************************
	  Function name: completeRound
	  Description: Processes opponent's turn
	  Parameters: None
	  Return value: None
	 **********************************************************/
	  public void completeRound() {
		  // Disable main menu while processing opponent's turn
		  enableMainMenu(false);
		  // Wait 2500 ms before processing opponent's turn
		  timerCompleteRound = new Timer(0, new ActionListener() {  
			  public void actionPerformed(ActionEvent e) {
				  // Process opponent's turn
				  int opponentOption = manager.processCPUTurn();
				  // Opponent selected attack
				  if(opponentOption == 0) {
	        		  	// Do effect animation for attack
	        		  	effectAnimation(0, "images/damage.png", "sound/emerald_000D.wav");
	        	  	  }
				  // Opponent selected use item
	        	  	 else if (opponentOption == 2){
	        		  	// Get opponent selected Pokemon
	        		 	 Pokemon opponentPokemon = manager.getOSPokemon();
	        		 	 // Do effect animation use item
	        		 	 effectAnimation(1, "images/item.png", "sound/emerald_000F.wav");
	        		 	 // Update current opponent Pokemon data
	        		 	 pokemonHPBar[1].setValue(Math.round(opponentPokemon.getHP()*100/opponentPokemon.getMaxHP()));
	  		    	  	updateData();
	        	  	}
				  // Display data summary
				  String data = manager.getData();
				  dataSummary.setText(data);
				  // Reset option
				  option = -1;  	
				  enableMainMenu(true);
				  // Get player selected Pokemon
				  Pokemon playerPokemon = manager.getPSPokemon();
				  String pokemonNameLabel = playerPokemon.getName() + " (Fainted)";
				  float pokemonHPLabel = playerPokemon.getHP();
				  // There is a winner
				  if(manager.isWinner()) {
					  endGame();
				  }
				  // Player Pokemon is fainted
				  else if(playerPokemon.isFainted()) {
					  needToSwap = 1;
					  pokemonHPLabel = 0;
				  }
				  // Player Pokemon is not fainted
				  else {
					  pokemonNameLabel = playerPokemon.getName();
					  needToSwap = 0;
				  }
				  // Update bench data
				  updateData();
	      		  	// Stop timer
	      		  	timerCompleteRound.stop();
	      		  	// Player needs to swap Pokemon
	      		  	if(needToSwap == 1) {
	      				  // Set option to swap
	      				  option = 1;
	      			 	 // Disable main menu
	      			 	 enableMainMenu(false);
	      			 	 setButtons();
	      			 	 subpanels[9].setVisible(true);
	      		  	}
			  }
		  });
		  // Set delay
		  timerCompleteRound.setInitialDelay(2500);
		  // Start timer
		  timerCompleteRound.start();
	  }
	  
	 /**********************************************************
	  Function name: effectAnimation
	  Description: Display image over Pokemon as an effect 
	  			   animation and play sound for attack and use 
	  			   item options
	  Parameters: int index - index to set image for
	  			  String image - image location
	  			  String sound - sound location
	  Return value: None
	 **********************************************************/
	  public void effectAnimation(int index, String image, String sound) {
		  File soundFile = new File(sound);
		  try {
			  audioIn = AudioSystem.getAudioInputStream(soundFile);
			  soundEffect = AudioSystem.getClip();
			  soundEffect.open(audioIn);
			  // Play sound effect if sound isn't muted
			  if(mute.getText().equals("Mute")) {
				  soundEffect.start();
			  }
		  } 
		  catch (UnsupportedAudioFileException e1) {
			  e1.printStackTrace();
		  } 
		  catch (IOException e1) {
			  e1.printStackTrace();
		  } 
		  catch (LineUnavailableException e1) {
			  e1.printStackTrace();
		}
		// Display effect image
		labelPokemon[index+2].setIcon(new ImageIcon(image));
		subpanels[4+index].setVisible(true);
		// Wait 300 ms to remove effect image
		timerEffect = new Timer(0, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				subpanels[4+index].setVisible(false);
	            		timerEffect.stop();
	        	}
	    	});
		// Set delay
		timerEffect.setInitialDelay(300);
		// Start timer
		timerEffect.start();  
	  }
	  
	 /**********************************************************
	  Function name: swapAnimation
	  Description: Display array of images to act as a swap 
	  			   animation to transition between Pokemon
	  Parameters: int loop - number of times to recursively
	  						 perform swapAnimation
	  			  int index - index to set image for
	  			  String image - image location
	  			  String sound - sound location
	  Return value: None
	 **********************************************************/
	  public void swapAnimation(int loop, int player, String images[], int currIndex) {
		  // Display image
		  labelPokemon[player].setIcon(new ImageIcon(images[currIndex]));
		  // Wait 100 ms to recursively call swapAnimation and update selected Pokemon data
		   timerSwap = new Timer(0, new ActionListener() {
	            	public void actionPerformed(ActionEvent e) {
	            		timerSwap.stop();
	            		// Player has selected their first Pokemon to battle with
	            		if(selectFirst && player == 0) {
    		   	    		 enableMainMenu(true);
    		   	    		 selectFirst = false;
	            		}
	            		enableSubMenu(true);
	            		// Call swapAnimation
	            		if(loop > 0) {
		    			swapAnimation(loop-1, player, images, currIndex+1);
		    		}
	            		// Done looping
		    		else {
		    			Pokemon selectedPokemon;
		    			if(player == 1) {
		    		    		selectedPokemon = manager.getOSPokemon();
		    			}
		    			else {
		    		    		selectedPokemon = manager.getPSPokemon();
		    			}
		    			// Update current Pokemon data
		    			currentPokemonName[player].setText(selectedPokemon.getName());
		    			currentPokemonStats[player].setText("Off: " + selectedPokemon.getOffenseStatus() 
		    		    	  							   + " Def: " + selectedPokemon.getDefenseStatus());
		    			pokemonHPBar[player].setValue(Math.round(selectedPokemon.getHP()*100/selectedPokemon.getMaxHP()));
		    			// Player had to swap Pokemon after it fainted
		    			if(needToSwap != 0) {
		    		    		enableMainMenu(true);
		    		    		needToSwap = 0;
		    			}
		    		}
	            	}
		   });
		   // Set delay
		   timerSwap.setInitialDelay(100);
		   // Start timer
		   timerSwap.start();
	  }

  }
  
 /**********************************************************
  Function name: Game
  Description: Game constructor to create GUI
  Parameters: None
  Return value: None
 **********************************************************/
  public Game() {
	  // Create panels to display on window frame
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
	  
	  // Format frame display
	  frame.setResizable(false);
	  frame.setPreferredSize(new Dimension(720, 1000));
	  frame.setLayout(new BorderLayout());
          frame.add(lpane, BorderLayout.CENTER);
          frame.add(subpanels[0], BorderLayout.CENTER);
          frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	  
	  // Game menu
      	  lpane.setBounds(0, -5, 720, 1000);
      	  // Start menu
          subpanels[0].setBounds(0, 500, 720, 1000);

      	  // Create start buttons
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

     	  // Create border for interactable part of GUI
	  subpanels[10].setBounds(0, 450, 704, 516);
	  subpanels[10].setBorder(new LineBorder(Color.BLACK, 2, true));
	  subpanels[10].setOpaque(false);

	  // Add panels to layered panel
          for(int i = 1; i < subpanels.length; i++) {
		  lpane.add(subpanels[i], i-1, 0);
	  }

	  // Game menu shouldn't be visible until battle is initialized
	  lpane.setVisible(false);
	  // Make start menu visible
	  subpanels[0].setVisible(true);

	  // Make sure all methods are performed on frame before displaying it
	  frame.pack();
	  frame.setVisible(true);  
  }
  
 /**********************************************************
  Function name: setUpBackground
  Description: Display background image
  Parameters: None
  Return value: None
 **********************************************************/
  private void setupBackground() {
	  // Set background image
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
  
 /**********************************************************
  Function name: setupCurrentPokemon
  Description: Set initial image for each Pokemon
  Parameters: None
  Return value: None
 **********************************************************/
  private void setupCurrentPokemon() {
	  // Display image for player and opponent Pokemon
	  for(int i = 0; i < labelPokemon.length-2; i++) {
	      labelPokemon[i].setIcon(new ImageIcon("images/pokeball.png"));
	      labelPokemon[i].setBounds(0, 0, 720, 1000);
	      subpanels[i+2].add(labelPokemon[i]);
	      subpanels[i+2].setOpaque(false);
	  }
      subpanels[2].setBounds(145, 310, 100, 100);
      subpanels[3].setBounds(485, 100, 100, 100);
  }
  
 /**********************************************************
  Function name: setupEffects
  Description: Initialize effect image for player and 
  			   opponent
  Parameters: None
  Return value: None
 **********************************************************/
  private void setupEffects() {
      // Set effect image for player selected Pokemon
      labelPokemon[2].setIcon(new ImageIcon("images/damage.png"));
      labelPokemon[2].setBounds(0, 0, 720, 1000);
      subpanels[4].add(labelPokemon[2]);
      subpanels[4].setBounds(145, 320, 100, 100);
      subpanels[4].setOpaque(false);
      subpanels[4].setVisible(false);
      // Set effect image for opponent selected Pokemon
      labelPokemon[3].setIcon(new ImageIcon("images/useItem.png"));
      labelPokemon[3].setBounds(0, 0, 720, 1000);
      subpanels[5].add(labelPokemon[3]);
      subpanels[5].setBounds(485, 110, 100, 100);
      subpanels[5].setOpaque(false);
      subpanels[5].setVisible(false);
  }
  
 /**********************************************************
  Function name: setupDataSummary
  Description: Add panel to display data after player or
  			   opponent has made a move
  Parameters: None
  Return value: None
 **********************************************************/
  private void setupDataSummary() {
      subpanels[6].add(dataSummary);
      subpanels[6].setBounds(0, 450, 704, 50);
      subpanels[6].setBackground(Color.WHITE);
      subpanels[6].setBorder(new LineBorder(Color.BLACK, 2, true));
  }
  
 /**********************************************************
  Function name: setupBenchData
  Description: Initialize bench data display for player
  			   Pokemon
  Parameters: None
  Return value: None
 **********************************************************/
  private void setupBenchData() {
      subpanels[7].setBounds(10, 500, 704, 100);
      subpanels[7].setOpaque(true);
      for(int i = 0; i < labelBenchPokemonData.length; i++) {
    	  labelBenchPokemonData[i] = new JLabel("");
    	  labelBenchPokemonData[i].setOpaque(false);
          subpanels[7].add(labelBenchPokemonData[i]);
      }
  }
  
 /**********************************************************
  Function name: setupMenu
  Description: Create menu and sub menu for player to 
  			   choose how to interact
  Parameters: None
  Return value: None
 **********************************************************/
  private void setupMenu() {
       // Set up main menu
      subpanels[8].setBounds(-8, 600, 720, 1000);
      subpanels[8].setOpaque(true);
      for(int i = 0; i < buttonsMainMenu.length; i++) {
    	  buttonsMainMenu[i].setBackground(new Color(0xb8ffc6));
    	  buttonsMainMenu[i].addActionListener(click);
    	  buttonsMainMenu[i].setPreferredSize(new Dimension(170, 100));
    	  buttonsMainMenu[i].setEnabled(false);
    	  subpanels[8].add(buttonsMainMenu[i]);
      }
      // Set up sub menu
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
  
 /**********************************************************
  Function name: setupCurrentPokemonData
  Description: Initialize data for player and opponent
  			   selected Pokemon
  Parameters: None
  Return value: None
 **********************************************************/
  private void setupCurrentPokemonData() {
	  // Panel to store current player and opponent Pokemon data
	  JPanel panelCurrentPokemonData[] = new JPanel[2];
	  for(int i = 0; i < panelCurrentPokemonData.length; i++) {
		  subpanels[11+i].setLayout(new BoxLayout(subpanels[11+i], BoxLayout.PAGE_AXIS));
		  panelCurrentPokemonData[i] = new JPanel(new FlowLayout());
		  // Add HP bar
		  pokemonHPBar[i] = new JProgressBar(0);
		  pokemonHPBar[i].setForeground(Color.GREEN.darker());
		  pokemonHPBar[i].setValue(100);
		  pokemonHPBar[i].setStringPainted(true);
		  // Add Pokemon name and stats
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
  
 /**********************************************************
  Function name: setupMuteButton
  Description: Add button to allow users to mute/unmute 
  			   sound
  Parameters: None
  Return value: None
 **********************************************************/
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
  
 /**********************************************************
  Function name: reset
  Description: Reset GUI after a game is  completed
  Parameters: None
  Return value: None
 **********************************************************/
  public void reset() {
	  subpanels[0].setVisible(true);
	  lpane.setVisible(false);
	  subpanels[9].setVisible(false);
	  // Reset data summary text
	  dataSummary.setText("");
	  // Reset player and opponent selected Pokemon images
	  labelPokemon[0].setIcon(new ImageIcon("images/pokeball.png"));  
	  labelPokemon[1].setIcon(new ImageIcon("images/pokeball.png"));  
	  // Reset player and opponent selected Pokemon stats
	  for(int i = 0; i < currentPokemonName.length; i++) {
		  currentPokemonName[i].setText("-");
		  currentPokemonStats[i].setText("Off: - Def: -");
		  pokemonHPBar[i].setValue(100);
		  dataSummary.setText("");
	  }
	  // Disable main menu
	  enableMainMenu(false);
  }
  
 /**********************************************************
  Function name: setButtons
  Description: Change button text based on selected option
  Parameters: None
  Return value: None
 **********************************************************/
  public void setButtons() {
	  Pokemon playerPokemon = manager.getPSPokemon();
	  // Display attack options
	  if(option == 0) {
		  Attack attacks[] = playerPokemon.getAttacks();
		  for(int i = 0; i < attacks.length; i++) {
			  buttonsSubMenu[i].setVisible(true);
			  buttonsSubMenu[i].setEnabled(true);
			  buttonsSubMenu[i].setText(attacks[i].getName());
		  }
		  buttonsSubMenu[playerPokemon.getIndex()].setEnabled(true);
	  }
	  // Display swap options
	  else if(option == 1) {
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
	  // Display item options
	  else if(option == 2) {
		  Item items[] = manager.getPlayerItems();
		  for(int i = 0; i < items.length; i++) {
			  buttonsSubMenu[i].setVisible(true);
			  if(items[i].used == false) {
				  buttonsSubMenu[i].setEnabled(true);
			  }
			  /*if(items[i].used == true 
				|| (playerPokemon.getHP() >= 100 && items[i].getName().equals("Super Potion"))) {
				  buttonsSubMenu[i].setEnabled(false);
			  }*/
			  buttonsSubMenu[i].setText(items[i].getName());
		  }
	  }
	  // Display surrender options
	  else if(option == 3) {
		  buttonsSubMenu[0].setText("Confirm");
		  buttonsSubMenu[1].setText("Cancel");
		  buttonsSubMenu[0].setEnabled(true);
		  buttonsSubMenu[1].setEnabled(true);
		  for(int i = 2; i < buttonsSubMenu.length; i++) {
			  buttonsSubMenu[i].setVisible(false);
		  }
	  }
	  // Display options after game has ended
	  else if(option == 4) {
		  subpanels[9].setVisible(true);
		  buttonsSubMenu[0].setText("Main Menu");
		  buttonsSubMenu[1].setText("Quit");
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
  
 /**********************************************************
  Function name: displayPokemon
  Description: Display Pokemon for player to select from
  Parameters: None
  Return value: None
 **********************************************************/
  public void displayPokemon() {
	  Pokemon playerPokemon[] = manager.getPlayerPokemon();
	  subpanels[9].setVisible(true);
	  for(int i = 0; i < playerPokemon.length; i++) {
		  buttonsSubMenu[i].setVisible(true);
		  buttonsSubMenu[i].setText(playerPokemon[i].getName());
	  }
  }
  
 /**********************************************************
  Function name: enableMainMenu
  Description: Toggle main menu
  Parameters: boolean enable - true if main menu should be
  							   enabled, false otherwise
  Return value: None
 **********************************************************/
  public void enableMainMenu(boolean enable) {
	  for(int i = 0; i < buttonsMainMenu.length; i++) {
		  buttonsMainMenu[i].setEnabled(enable);
      }
  }
  
  /**********************************************************
  Function name: enableSubMenu
  Description: Toggle sub menu
  Parameters: boolean enable - true if sub menu should be
  							   enabled, false otherwise
  Return value: None
 **********************************************************/
  public void enableSubMenu(boolean enable) {
	  for(int i = 0; i < buttonsSubMenu.length; i++) {
		  buttonsSubMenu[i].setEnabled(enable);
      }
  }
}
