package pokemonBattleSystem;

import java.util.Random; 
import java.util.Scanner;

public class Manager{
    public int playerTurn;
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
    
    public Manager(){
        this.playerTurn = 0;
        this.winner = "";
    }

    public void initializeBattle(){
    	psPokemon = 0;
    	osPokemon = 0;
    	damageApplied = 0;
    	data = "";
        // Generate Player's bench
        this.playerSet = new Pokemon[3];
        for(int i = 0; i < playerSet.length; i++){
            this.playerSet[i] = new Pokemon("Venusaur", i);
        }
        //setPlayerBench(playerSet);
        
        // Generate opponent's pokemon
        this.opponentSet = new Pokemon[3];
        for(int i = 0; i < opponentSet.length; i++){
            this.opponentSet[i] = new Pokemon("Venusaur", i);
        }
        //setOpponentBench(opponentSet);
        
        // Generate player items
        this.playerItems = new Item[4];
        this.opponentItems = new Item[4];
        for(int i = 0; i < playerItems.length; i++){
            this.playerItems[i] = new Item();
            this.opponentItems[i] = new Item();
        }
        
    }
    
    public boolean checkForWinner(){
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
            resetBattle();
            return true;
        }
        return false;
        
    }
    
    public int processTurn(int menuOption, int subMenuOption){
        /* RETURNS -1, 0, or 1
            -1 if Player needs to swap pokemon after opponent's attack
             0 if Battle continues
             1 if Battle is over
        */
        switch(menuOption){
            case 1: // Attack
                if(playerTurn == 0){
                    this.damageApplied = playerSet[psPokemon]
                                    .getAttacks()[subMenuOption-1]
                                    .applyAttack(opponentSet[osPokemon]);
                    data = playerSet[psPokemon].getName() 
                                        + " (Player) has used " + playerSet[psPokemon]
                                                .getAttacks()[subMenuOption-1].getName() + " and applied " 
                                        + this.damageApplied + " damage to " 
                                        + opponentSet[osPokemon].getName() 
                                        + " (Opponent).";
                    if(opponentSet[osPokemon].GetHP() <= 0) {
                    	data += " " + opponentSet[osPokemon].getName() + " has fainted!";
                    }
                }
                else{
                    this.damageApplied = opponentSet[osPokemon]
                                    .getAttacks()[subMenuOption-1]
                                    .applyAttack(playerSet[psPokemon]);
                    data = opponentSet[osPokemon].getName() 
                                        + " (Opponent) has used " + opponentSet[osPokemon]
                                                .getAttacks()[subMenuOption-1].getName() + " and applied " 
                                        + this.damageApplied + " damage to " 
                                        + playerSet[psPokemon].getName() 
                                        + " (Player).";
                    if(playerSet[psPokemon].GetHP() <= 0) {
                    	data += " " + playerSet[psPokemon].getName() + " has fainted!";
                    }
                }
                break;
            case 3: // Item
                if(playerTurn == 0){
                    // ASSUMPTION: Items can only be used on selected pokemon 
                    playerItems[subMenuOption-1].applyItem(playerSet[psPokemon]);
                    data = "Player has used " 
                                        + playerItems[subMenuOption-1].getName() 
                                        + " on " + playerSet[psPokemon].getName() 
                                        + ".";
                }
                else{
                    opponentItems[subMenuOption-1].applyItem(opponentSet[osPokemon]);
                    data = "Opponent has used " 
                                        + opponentItems[subMenuOption-1].getName() 
                                        + " on " + opponentSet[osPokemon].getName() 
                                        + ".";
                }
                break;
            case 4: // Swap
                if(playerTurn == 0){
                    psPokemon = subMenuOption - 1;
                    data = "Player has swapped to " + playerSet[psPokemon].getName() + ".";
                }
                else{
                    osPokemon = subMenuOption - 1;
                    data = "Opponent has swapped to " + opponentSet[osPokemon].getName() + ".";
                }
                break;
        }
       /* if(!checkForWinner()){
            if(playerSet[psPokemon].isFainted()){
                return -1;
            }
            changeTurn();
            if(this.playerTurn == 1){
                processCPUTurn();
            }            
           // return 0;
        }*/
        return 1;

    }
    
    public int processCPUTurn(){
    	playerTurn = 1;
        Random rand = new Random(); 
        // Choose random number for menu item (either attack, use item, swap)
        int menuOption = rand.nextInt(3) + 1;
        int subMenuOption = 0;
        if(opponentSet[osPokemon].GetHP() <= 0) {
        	menuOption = 4;
        	subMenuOption = rand.nextInt(3) + 1;
        	while(opponentSet[subMenuOption - 1].isFainted()) {
        		subMenuOption = rand.nextInt(3) + 1;
        	}
        }
        else {
	        while(menuOption == 2){
	            menuOption = rand.nextInt(3) + 1;
	        }
	        if(menuOption == 1 || menuOption == 3){
	            subMenuOption = rand.nextInt(4) + 1;
	        }
	        else{
	            subMenuOption = rand.nextInt(3) + 1;
	        }
	        
        }
        
        // Choose random number for sub menu options depending on menu option
        
       // System.out.println(menuOption + " " + subMenuOption);
        // Process CPU turn
        processTurn(menuOption, subMenuOption);
        playerTurn = 0;
        
        return menuOption;
    }
    
    public Pokemon[] getPlayerPokemon(){
        return this.playerSet;
    }
    
    public Pokemon[] getOpponentPokemon(){
        return this.opponentSet;
    }
    
    
    public Pokemon getPSPokemon(){
        return this.playerSet[this.psPokemon];
    }
    
    public Pokemon getOSPokemon(){
        return this.opponentSet[this.osPokemon];
    }
    
    public Pokemon setPSPokemon(int selection){
        this.psPokemon = selection;
        return this.playerSet[psPokemon];
    }
    
    public Item[] getPlayerItems(){
        return this.playerItems;
    }
    
    
    public void changeTurn(){
        // Alternate between 0 and 1 (0 for player, 1 for opponent)
        if(this.playerTurn == 0){
            this.playerTurn = 1;
        }
        else{
            this.playerTurn = 0;
        }
    }
    
    public String getData() {
    	return this.data;
    }
    
    public String getWinner(){
        return this.winner;
    }
     
    
    public void resetBattle(){
        
    }
    
    public void surrender(){
        this.winner = "opponent";
        resetBattle();    
    }
    
    public int getNumPlayerPokemon() {
    	return this.playerSet.length;
    }
}
