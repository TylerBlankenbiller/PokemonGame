/************************************************************/
/* Filename: Manager.java */
/* Purpose: Manages the game and turn changes*/
/************************************************************/

package pokemonBattleSystem;

import java.util.Random; 

public class Manager{
    private int playerTurn;
    private String winner;
    // Player data
    private Pokemon playerSet[];
    // Player selected pokemon
    private int psPokemon;
    private Item playerItems[]; 
    // Opponent data
    private Pokemon opponentSet[];
    // Opponent selected pokemon
    private int osPokemon = 0;
    private Item opponentItems[]; 
    private float damageApplied;
    // Round data
    private String data;
    // Images for swap animation
    private String swapImages[] = new String[8];
    
	/**********************************************************
	  Function name: Manager
	  Description: Initializes player turn, winner, bench, 
	  			   items, selected Pokemon, and data summary
	  Parameters: None
	  Return value: None
	 **********************************************************/
    public Manager(){
        this.playerTurn = 0;
        this.winner = "";
        this.playerSet = new Pokemon[3];
        this.opponentSet = new Pokemon[3];
        this.psPokemon = 0;
    	this.osPokemon = 0;
    	this.playerItems = new Item[4];
        this.opponentItems = new Item[4];
    	this.damageApplied = 0;
    	this.data = "";
    }

    /**********************************************************
	  Function name: initializeBattle
	  Description: Generates player and opponent Pokemon and
	  			   Items
	  Parameters: None
	  Return value: None
	 **********************************************************/
    public void initializeBattle(){
    	// List of possible Pokemon to generate
    	String pokemonList[] = {"Venusaur", "Charizard", "Blastoise", "Butterfree", "Beedrill", 
    							"Pidgeot", "Raticate", "Fearow", "Arbok", "Pikachu"};
    	Random rand = new Random(); 
        // Generate Player's bench
        for(int i = 0; i < playerSet.length; i++){
        	int pokemonListIndex = rand.nextInt(10);
            this.playerSet[i] = new Pokemon(pokemonList[pokemonListIndex], i, 0);
        }
        // Generate opponent's pokemon
        this.opponentSet = new Pokemon[3];
        for(int i = 0; i < opponentSet.length; i++){
        	int pokemonListIndex = rand.nextInt(9);
            this.opponentSet[i] = new Pokemon(pokemonList[pokemonListIndex], i, 1);
        }
        // Generate player items
        for(int i = 0; i < playerItems.length; i++){
            this.playerItems[i] = new Item(i);
            this.opponentItems[i] = new Item(i);
        }    
    }
    
    /**********************************************************
	  Function name: isWinner()
	  Description: Checks if all player or opponent Pokemon 
	  			   are fainted
	  Parameters: None
	  Return value: Boolean indicating if there is a winner
	 **********************************************************/
    public boolean isWinner(){
        int numFainted = 0;
        // Check if all pokemon in player's set is fainted
        for(Pokemon currPokemon : this.playerSet){
            if(currPokemon.isFainted()){
                numFainted++;
            }
        }
        // Player loses if all pokemon fainted
        if(numFainted == this.playerSet.length){
            this.winner = "Opponent";
        }
        else{
            numFainted = 0;
            // Check if all pokemon in opponent's set is fainted
            for(Pokemon currPokemon : this.opponentSet){
                if(currPokemon.isFainted()){
                    numFainted++;
                }
            }
            // Player wins
            if(numFainted == this.opponentSet.length){
                this.winner = "Player";
            }
        }
        // Reset battle if there is a winner
        if(this.winner != "") {
        	this.data = this.winner + " has won the game!";
            return true;
        }
        return false;       
    }
    
    /**********************************************************
	  Function name: resetWinner
	  Description: Resets winner after game has ended
	  Parameters: None
	  Return value: None
	 **********************************************************/
    public void resetWinner() {
    	this.winner = "";
    }
    
    /**********************************************************
	  Function name: processTurn
	  Description: Performs move based on selected options
	  Parameters: int menuOption - main menu option
	  			  int subMenuOption - sub menu option
	  Return value: None
	 **********************************************************/
    public void processTurn(int menuOption, int subMenuOption){
        switch(menuOption){
            case 0: // Attack
                if(playerTurn == 0){
                	// Update damage applied
                    this.damageApplied = playerSet[psPokemon]
                                    .getAttacks()[subMenuOption]
                                    .applyAttack(opponentSet[osPokemon], playerSet[psPokemon]);
                    // Update data summary
                    this.data = playerSet[psPokemon].getName() 
                                        + " (Player) has used " + playerSet[psPokemon]
                                                .getAttacks()[subMenuOption].getName() + " and applied " 
                                        + this.damageApplied + " damage to " 
                                        + opponentSet[osPokemon].getName() 
                                        + " (Opponent).";
                    // Add to data summary if opponent selected Pokemon has fainted
                    if(opponentSet[osPokemon].getHP() <= 0) {
                    	data += " " + opponentSet[osPokemon].getName() + " has fainted!";
                    }
                }
                else{
                	// Update date applied
                    this.damageApplied = opponentSet[osPokemon]
                                    .getAttacks()[subMenuOption]
                                    .applyAttack(playerSet[psPokemon], playerSet[psPokemon]);
                    // Update data summary
                    this.data = opponentSet[osPokemon].getName() 
                                        + " (Opponent) has used " + opponentSet[osPokemon]
                                                .getAttacks()[subMenuOption].getName() + " and applied " 
                                        + this.damageApplied + " damage to " 
                                        + playerSet[psPokemon].getName() 
                                        + " (Player).";
                    // Add to data summary if player selected Pokemon has fainted
                    if(playerSet[psPokemon].getHP() <= 0) {
                    	data += " " + playerSet[psPokemon].getName() + " has fainted!";
                    }
                }
                break;
            case 1: // Swap
                if(playerTurn == 0){
                	int firstPokemon = psPokemon;
                    this.psPokemon = subMenuOption;
                    // Update swap images
                    setSwapImages(playerSet[firstPokemon].getCallBackPokemon(), playerSet[psPokemon].getChoosePokemon());
                    this.data = "Player has swapped to " + playerSet[psPokemon].getName() + ".";
                }
                else{
                	int firstPokemon = osPokemon;
                    osPokemon = subMenuOption;
                    // Update swap images
                    setSwapImages(opponentSet[firstPokemon].getCallBackPokemon(), opponentSet[osPokemon].getChoosePokemon());
                    data = "Opponent has swapped to " + opponentSet[osPokemon].getName() + ".";
                }
                break;
            case 2: // Item
                if(playerTurn == 0){
                    // Apply item
                    playerItems[subMenuOption].applyItem(playerSet[psPokemon]);
                    // Update data summary
                    data = "Player has used " 
                                        + playerItems[subMenuOption].getName() 
                                        + " on " + playerSet[psPokemon].getName() 
                                        + ".";
                }
                else{
                	// Apply item
                    opponentItems[subMenuOption].applyItem(opponentSet[osPokemon]);
                    // Update data summary
                    data = "Opponent has used " 
                                        + opponentItems[subMenuOption].getName() 
                                        + " on " + opponentSet[osPokemon].getName() 
                                        + ".";
                }
                break;
        }
    }
    
    /**********************************************************
	  Function name: processCPUTurn
	  Description: Generates random main menu and sub menu
	  			   options for opponent
	  Parameters: None
	  Return value: None
	 **********************************************************/
    public int processCPUTurn(){
    	// Update turn
    	this.playerTurn = 1;
        Random rand = new Random(); 
        // Choose random number for menu item (either attack, use item, swap)
        int menuOption = rand.nextInt(3);
        int subMenuOption = 0;
        boolean allItemsUsed = true;
        // Check if all items have been used
        for(int i = 0; i < opponentItems.length; i++) {
        	if(opponentItems[i].used == false) {
        		allItemsUsed = false;
        		break;
        	}
        }
        // Opponent selected Pokemon has fainted, need to select another Pokemon
        if(opponentSet[osPokemon].getHP() <= 0) {
        	menuOption = 1;
        	subMenuOption = rand.nextInt(3);
        	while(opponentSet[subMenuOption].isFainted()) {
        		subMenuOption = rand.nextInt(3);
        	}
        }
        else {
        	// Loop until menu option is 0 or 2 (attack or use item)
	        while(menuOption == 1){
	            menuOption = rand.nextInt(3);
	        }
	        if(menuOption == 0 || menuOption == 2){
	        	// Generate sub menu option
	        	subMenuOption = rand.nextInt(4);
	        	// Set option to attack if all items have been used
	        	if(menuOption == 2) {
	        		if(allItemsUsed) {
	        			menuOption = 0;
	        		}
	        		else {
	        			// Loop until selected item hasn't been used or Pokemon has full HP and heal potion is selected
	        			while(opponentItems[subMenuOption].used 
	        					|| (opponentItems[subMenuOption].getName().equals("Heal Potion") && opponentSet[osPokemon].getHP() >= 100)) {
	        				subMenuOption = rand.nextInt(4);
	        			}
	        		}
	        	}
	            
	        }  
        }
        // Get images to call back Pokemon
        String firstPokemon[] = this.opponentSet[this.osPokemon].getCallBackPokemon();
        // Process CPU turn
        processTurn(menuOption, subMenuOption);
        // Get images to send out Pokemon
        String secondPokemon[] = this.opponentSet[this.osPokemon].getChoosePokemon();
        // Update player turn
        this.playerTurn = 0;
        setSwapImages(firstPokemon, secondPokemon);
        
        return menuOption;
    }
    
    /**********************************************************
	  Function name: getPlayerPokemon
	  Description: Gets player bench
	  Parameters: None
	  Return value: Array of Pokemon for player's set
	 **********************************************************/
    public Pokemon[] getPlayerPokemon(){
        return this.playerSet;
    }
    
    /**********************************************************
	  Function name: getOpponentPokemon
	  Description: Gets opponent bench
	  Parameters: None
	  Return value: Array of Pokmeon for opponent's set
	 **********************************************************/
    public Pokemon[] getOpponentPokemon(){
        return this.opponentSet;
    }
    
    /**********************************************************
	  Function name: getPSPokemon
	  Description: Gets player selected Pokemon
	  Parameters: None
	  Return value: Player selected Pokemon
	 **********************************************************/
    public Pokemon getPSPokemon(){
        return this.playerSet[this.psPokemon];
    }
    
    /**********************************************************
	  Function name: getOSPokemon
	  Description: Gets opponent selected Pokemon
	  Parameters: None
	  Return value: Opponent selected Pokemon
	 **********************************************************/
    public Pokemon getOSPokemon(){
        return this.opponentSet[this.osPokemon];
    }
    
    /**********************************************************
	  Function name: setPSPokemon
	  Description: Sets player selected Pokemon based on input
	  Parameters: int selection - selected Pokemon index
	  Return value: None
	 **********************************************************/
    public void setPSPokemon(int selection){
        this.psPokemon = selection;
    }
    
    /**********************************************************
	  Function name: getPlayerItems
	  Description: Gets player's item set
	  Parameters: None
	  Return value: Item array containing player Items
	 **********************************************************/
    public Item[] getPlayerItems(){
        return this.playerItems;
    }
    
    /**********************************************************
 	  Function name: getPlayerTurn
 	  Description: Gets which turn it is
 	  Parameters: None
 	  Return value: The player turn integer
 	 **********************************************************/
   public int getPlayerTurn(){
       return this.playerTurn;
   }
    
    /**********************************************************
	  Function name: getData
	  Description: Gets data summary after a move has been made
	  Parameters: None
	  Return value: String containing data
	 **********************************************************/
    public String getData() {
    	return this.data;
    }
    
    /**********************************************************
	  Function name: getWinner
	  Description: Gets winner
	  Parameters: None
	  Return value: String containing winner
	 **********************************************************/
    public String getWinner(){
        return this.winner;
    }
     
    /**********************************************************
	  Function name: getSwapImages
	  Description: Gets array for swap images
	  Parameters: None
	  Return value: String array containing image strings
	 **********************************************************/
    public String[] getSwapImages() {
    	return this.swapImages;
    }
    
    /**********************************************************
	  Function name: setSwapImages
	  Description: Sets swap images based on input
	  Parameters: String first[] - set of images to call 
	  							   Pokemon back
	  			  String second[] - set of images to send
	  			  				    Pokemon out	
	  Return value: None
	 **********************************************************/
    public void setSwapImages(String first[], String second[]) {
		  for(int i = 0; i < first.length; i++) {
			  this.swapImages[i] = new String();
			  this.swapImages[i] = first[i];
		  }
		  for(int i = 0; i < second.length; i++) {
			  this.swapImages[first.length + i] = new String();
			  this.swapImages[first.length + i] = second[i];
		  }
		  
		 
    }
	
    /**********************************************************
	  Function name: getPlayerTurn
	  Description: Gets current player's turn
	  Parameters: None
	  Return value: Integer indicating the current player's 
	  				turn
	 **********************************************************/
    public int getPlayerTurn() {
    	return this.playerTurn;
    }
}
