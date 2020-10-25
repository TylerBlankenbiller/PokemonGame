public class Battle {
    private int playerTurn;
    private String winner;
    
    public Battle(){
        playerTurn = 0;
        winner = "";
    }

    public void initializeBattle() {
        Pokemon playerSet[6];
        Pokemon opponentSet[3];
        for(int i = 0; i < 6; i++) {
            playerSet[i] = new Pokemon();
        }
        setPlayerBench(playerSet);
        
        for(int i = 0; i < 3; i++) {
            opponentSet[i] = new Pokemon();
        }
        setOpponentBench(opponentSet);
    }
    
    public void changeTurn() {
        this.playerTurn = (this.playerTurn + 1) % 2;
    }
    
    public String getWinner() {
        return this.winner;
    }
     
    public boolean checkForWinner(Pokemon[] playerSet, Pokemon[] opponentSet) {
        int numFainted = 0;
        for(Pokemon currPokemon : playerSet) {
            if(currPokemon.isFainted()){
                numFainted++;
            }
        }
        if(numFainted == playerSet.length) {
            this.winner = "opponent";
        }
        else{
            numFainted = 0;
            for(Pokemon currPokemon : opponentSet) {
                if(currPokemon.isFainted()){
                    numFainted++;
                }
            }
            if(numFainted == opponentSet.length) {
                this.winner = "player";
            }
        }
        if(winner != "") {
            resetBattle();
            return true;
        }
        return false;
        
    }
    
    public void resetBattle() {
        
    }
    
    public void surrender() {
        this.winner = "opponent";
        resetBattle();    
    }
}
