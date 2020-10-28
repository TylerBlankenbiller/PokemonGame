//Application
import java.util.Scanner;

public class Game{
    int choice;
    private static String[] MENUCHOICES = { "Attack", "Swap", "Item", "Surrender" };
    /*
        Constructor
        Creates the main menu
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

    public int battleMenu(){
        int selection = -1;
        int subSelection = -1;
        boolean exitBattle = false;

        while (exitBattle == false) {
            System.out.println("Please enter the selection you would like");

            for (int d = 0; d < MENUCHOICES.length; d++) {
                System.out.printf("Enter %d for %s\n", d + 1, MENUCHOICES[d]);
            }
            selection = getInput(1, 4);
            switch(selection){
                case 1:
                    //show attacks
                    System.out.println("Select an attack: ");
                    currentPokemon.showAttacks();
                    subSelection = getInput(1,4);
                    //pick and apply attack
                    battle.attack(subSelection);
                case 2:
                    //show pokemon options
                    System.out.println("Your options are: ");
                    boolean allFainted = true;
                    for(int i = 0; i <6; i++){
                        if(battle.playerSet[i] != currentPokemon && !battle.playerSet[i].isFainted()){
                            System.out.println(battle.playerSet[i]);
                            allFainted = false;
                        }
                    }
                    if(allFainted == true){
                        System.out.println("There are no valid options to swap!\n");
                        break;
                    }
                    //get player choice
                    else{
                        subSelection = getInput(1,6);
                        currentPokemon = battle.setPokemon(subSelection);
                        System.out.println("You have swapped in " + currentPokemon.getName())
                    }
                case 3:
                    //show items
                    System.out.println("Select an item: ");
                    battle.showItems();
                    subSelection = getInput(1,4);
                    //pick and apply item
                    battle.useItem(subSelection);
                case 4:
                    //get confirmation then exit loop
                    System.out.println("Are you sure you want to surrender?");
                    System.out.println("1: Yes");
                    System.out.println("2: No");
                    subSelection = getInput(1,2);
                    if(subSelection == 1){
                        exitBattle = true;
                    }
            }
        }

        return selection;
    }    

    public static void main (String[] args){
        
        Game gameTest = new Game();
        gameTest.mainMenu();
        System.out.println("Choice is " + gameTest.choice);
        if (gameTest.choice == 1){
            Battle battle = new Battle();
            battle.initializeBattle();
            Pokemon currentPokemon = battle.getCurrentPokemon();
            gameTest.battleMenu();
        }
        else
            return;
        
    }

    
}