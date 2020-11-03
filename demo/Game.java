//Application
import java.util.Scanner;

public class Game{
    int choice = 1;
    private static String[] MENUCHOICES = { "Attack", "Swap", "Item", "Surrender" };
    /*
        Constructor
        Runs the game
    */
    public int mainMenu(){
        System.out.println("Welcome to the pokemon game simulator!");
        System.out.println("Would you like to:");
        System.out.println("1) Start a battle");
        System.out.println("2) Quit");;
        choice = getInput(1,2);
        return choice;
        //Probably don't need a return
    }

    public int getInput(int minInput, int maxInput){
        //Gets an integer from user between minimum and maximum
        int selection = -1;
        Scanner input = new Scanner(System.in);
        while(selection < minInput || selection > maxInput){
            selection = input.nextInt();
            if (selection < minInput || selection > maxInput) {
                System.out.println("You have made a wrong selection, please re-select");;
            }
        }
       // input.close();
        return selection;
    }

    public int battleMenu(Manager manager, Pokemon currentPokemon){
        //The main option chosen
        int chosenOption = -1;
        //Any suboption chosen
        int chosenSubOption = -1;
        boolean exitBattle = false;
        //battleStatus: -1 need to swap pokemon, 0 battle continues, 1 battle over
        int battleStatus = 0;

        while (exitBattle == false) {
            System.out.println("Please enter the selection you would like");

            for (int d = 0; d < MENUCHOICES.length; d++) {
                System.out.printf("Enter %d for %s\n", d + 1, MENUCHOICES[d]);
            }
            chosenOption = getInput(1, 4);
            switch(chosenOption){
                case 1:
                    //Show attacks
                    System.out.println("Select an attack: ");
                    Attack pokemonAttacks[] = currentPokemon.getAttacks();
                    for(int i = 0; i < pokemonAttacks.length; i++){
                        System.out.println(i+1 + ":" + pokemonAttacks[i].getName());
                    }
                    //Choose attack and apply
                    chosenSubOption = getInput(1,4);
                    manager.processTurn(chosenOption,chosenSubOption);
                    break;
                case 2:
                    //Show pokemon options and get player choice
                    chosenSubOption = displayBenchAndChoose(manager, manager.getPSPokemon());//Need a way to get the pokemon index
                    
                    //If there was a pokemon chosen, swap in. Otherwise return to battle menu
                    if(chosenSubOption > -1){
                        manager.processTurn(chosenOption,chosenSubOption);
                        currentPokemon = manager.setPSPokemon(chosenSubOption);//Need to figure out how to manage current pokemon
                        System.out.println("You have swapped in " + currentPokemon.getName());//Need a way to get the pokemon name
                    }
                    break;
                case 3:
                    //Show items
                    System.out.println("Select an item: ");
                    Item playerItems[] = manager.getPlayerItems();
                    for(int i = 0; i < playerItems.length; i++){
                        System.out.println(i+1 + ":" + playerItems[i].getName());
                    }
                    //Pick and apply item
                    chosenSubOption = getInput(1,7);
                    manager.processTurn(chosenOption,chosenSubOption);
                    break;
                case 4:
                    //Get confirmation then exit loop
                    System.out.println("Are you sure you want to surrender?");
                    System.out.println("1: Yes");
                    System.out.println("2: No");
                    chosenSubOption = getInput(1,2);
                    if(chosenSubOption == 1){
                        //Surrenders
                        manager.surrender();
                        exitBattle = true;
                    }
            }
            //Checks for a winner after each round
            exitBattle = manager.checkForWinner();
            if(exitBattle == false){
               // System.out.println("turn: " + manager.playerTurn);
                manager.changeTurn();
                //System.out.println("turn: " + manager.playerTurn);
                manager.processCPUTurn();
                exitBattle = manager.checkForWinner();
                manager.changeTurn();
               // System.out.println("turn: " + manager.playerTurn);
            }
        }

        return chosenOption;
    }    

    public int displayBenchAndChoose(Manager manager, int currentPokemon){
        Pokemon playerPokemon[] = manager.getPlayerPokemon();
        //Returns -1 if no valid options
        //Otherwise return index of chosen pokemon
        boolean allFainted = true;
        int chosenOption = -1;
        System.out.println("Your options are: ");
        for(int i = 0; i < 6 && i != currentPokemon; i++){
            if(!playerPokemon[i].isFainted()){
                    System.out.println(i+1 + ": " + playerPokemon[i].getName());
                    allFainted = false;
                }
            }
         //If there are no valid options, return -1
         if(allFainted == true){
            System.out.println("There are no valid options to swap!\n");
            chosenOption = -1;
        }
        //Otherwise, get choice
        else{
            chosenOption = getInput(1,6);
        }
        return chosenOption;
    }

    public static void main (String[] args){
        
        Game gameRunner = new Game();
        gameRunner.mainMenu();
        System.out.println("Choice is " + gameRunner.choice);
        Manager manager = new Manager();
        while (gameRunner.choice == 1){
            
            manager.initializeBattle();//Set up the battle

            //Display choices to user and set current pokemon
            Pokemon currentPokemon = manager.setPSPokemon(gameRunner.displayBenchAndChoose(manager, -1));

            //Loops through and runs the battle
            gameRunner.battleMenu(manager, currentPokemon);

            //Cleans up after the battle
           /* manager.resetBattle();
            gameRunner.mainMenu();*/
            gameRunner.mainMenu();
        }
        
    }

    
}