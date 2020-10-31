public class Manager{
    private int playerTurn;
    private String winner;
    
    // Player data
    private Pokemon playerSet[6];
    // Player selected pokemon
    private int psPokemon = 0;
    private Item playerItems[7]; 
    
    // Opponent data
    private Pokemon opponentSet[3];
    // Opponent selected pokemon
    private int osPokemon = 0;
    private Item opponentItems[7]; 
    
    // Round data
    
    public Manager(){
        playerTurn = 0;
        winner = "";
    }

    public void initializeBattle(){
        // Generate Player's bench
        for(int i = 0; i < playerSet.length; i++){
            this.playerSet[i] = new Pokemon();
        }
        //setPlayerBench(playerSet);
        
        // Generate opponent's pokemon
        for(int i = 0; i < opponentSet.length; i++){
            this.opponentSet[i] = new Pokemon();
        }
        //setOpponentBench(opponentSet);
        
        // Generate player items
        for(int i = 0; i < playerItems.length i++){
            this.playerItems[i] = new Item();
            this.opponentItems[i] = new Item();
        }
        
    }
    
    public void processTurn(int menuOption, int subMenuOption){
        /* RETURNS -1, 0, or 1
            -1 if Player needs to swap pokemon after opponent's attack
             0 if Battle continues
             1 if Battle is over
        */
        switch(menuOption){
            case 1: // Attack
                if(playerTurn == 0){
                    damageApplied = playerSet[psPokemon].getAttacks()[subMenuOption-1].applyAttack(opponentSet[osPokemon]);
                }
                else{
                    damageApplied = opponentSet[osPokemon].getAttacks()[subMenuOption-1].applyAttack(playerSet[psPokemon]);
                }
                break;
            case 3: // Item
                if(playerTurn == 0){
                    /* ASSUMPTION: Items can only be used on selected pokemon */
                    playerItems[subMenuOption-1].applyItem(playerSet[psPokemon]);
                }
                else{
                    opponentItems[subMenuOption-1].applyItem(opponentSet[osPokemon]);
                }
                break;
            case 4: // Swap
                if(playerTurn == 0){
                    psPokemon = subMenuOption - 1;
                }
                else{
                    osPokemon = subMenuOption - 1;
                }
                break;
        }
        if(!checkForWinner){
            if(playerSet[psPokemon].isFainted()){
                return -1;
            }
            changeTurn();
            return 0;
        }
        return 1;

    }
    
    public Pokemon[] getPlayerPokemon(){
        return this.playerSet;
    }
    
    public Pokemon getPSPokemon(){
        return this.playerSet[psPokemon];
    }
    
    public Item[] getPlayerItems(){
        return this.playerItems;
    }
    
    private void processCPUTurn(){
        
    }
    
    public void changeTurn(){
        // Alternate between 0 and 1 (0 for player, 1 for opponent)
        this.playerTurn = (this.playerTurn + 1) % 2;
    }
    
    public String getWinner(){
        return this.winner;
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
            this.winner = "opponent";
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
                this.winner = "player";
            }
        }
        // Reset battle if there is a winner
        if(winner != "") {
            resetBattle();
            return true;
        }
        return false;
        
    }
    
    public void resetBattle(){
        
    }
    
    public void surrender(){
        this.winner = "opponent";
        resetBattle();    
    }
}
