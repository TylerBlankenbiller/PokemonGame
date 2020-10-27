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
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the pokemon game simulator!");
        System.out.println("Would you like to:");
        System.out.println("1) Start a battle");
        System.out.println("2) Quit");
        choice = Integer.parseInt(input.nextLine());
        return choice;
        //Probably don't need a return
    }

    
    public int battleMenu(){
        Scanner input = new Scanner(System.in);
        int selection = -1;

        while (selection != 4) {
            System.out.println("Please enter the selection you would like");

            for (int d = 0; d < MENUCHOICES.length; d++) {
                System.out.printf("Enter %d for %s\n", d + 1, MENUCHOICES[d]);
            }
            selection = input.nextInt() - 1;
            if (selection >= 0 && selection < MENUCHOICES.length) {
                System.out.println("You have chosen " + MENUCHOICES[selection]);
                switch(selection){
                    case 1:
                        //show attacks
                        currentPokemon.showAttacks();
                        //pick and apply attack
                    case 2:
                        //show pokemon options
                        System.out.println("Your options are: ");
                        for(int i = 0; i <6; i++){
                            System.out.println(battle.playerSet[i]);
                        }
                        //get player choice
                    case 3:
                        //show items
                }
            } else {
                System.out.println("You have made a wrong selection, please re-select");
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
            gameTest.battleMenu();
        }
        else
            return;
        
        //mainMenu();
    }

    
}