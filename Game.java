//Application
import java.util.Scanner;

public class Game{
    int choice;
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
        input.close();
        return selection;
    }

    public int battleMenu(Manager manager){
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
                    currentPokemon.showAttacks();
                    //Choose attack and apply
                    chosenSubOption = getInput(1,4);
                    manager.processTurn(chosenOption,chosenSubOption);
                    break;
                case 2:
                    //Show pokemon options - WILL NEED SOME GETTER/REPLACE WITH SYSTEM
                    System.out.println("Your options are: ");
                    boolean allFainted = true;
                    for(int i = 0; i <6; i++){
                        if(manager.playerSet[i] != currentPokemon && !manager.playerSet[i].isFainted()){
                            System.out.println(manager.playerSet[i]);
                            allFainted = false;
                        }
                    }
                    //If there are no valid options
                    if(allFainted == true){
                        System.out.println("There are no valid options to swap!\n");
                        break;
                    }
                    //Get player choice
                    else{
                        chosenSubOption = getInput(1,6);
                        manager.processTurn(chosenOption,chosenSubOption);
                        //currentPokemon = manager.setPokemon(chosenSubOption);
                        System.out.println("You have swapped in " + currentPokemon.getName())
                    }
                    break;
                case 3:
                    //Show items
                    System.out.println("Select an item: ");
                    manager.showPlayerItems();
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
                        //Surrenders and resets the battle
                        manager.surrender();
                        manager.resetBattle();
                        exitBattle = true;
                    }
            }
            //Checks for a winner after each round
            exitBattle = manager.checkForWinner();
        }

        return chosenOption;
    }    

    public static void main (String[] args){
        
        Game gameRunner = new Game();
        gameRunner.mainMenu();
        System.out.println("Choice is " + gameRunner.choice);
        if (gameRunner.choice == 1){
            Manager manager = new Manager();
            manager.initializeBattle();
            Pokemon currentPokemon = manager.getCurrentPokemon();
            gameRunner.battleMenu(manager);
        }
        else
            return;
        
    }

    
}