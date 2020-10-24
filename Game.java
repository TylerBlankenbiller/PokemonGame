//Application
import java.util.Scanner;

public class Game{
    int choice;
    /*
        Constructor
        Creates the main menu
    */
    public int MainMenu(){
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the pokemon game simulator!");
        System.out.println("Would you like to:");
        System.out.println("1) Start a battle");
        System.out.println("2) Quit");
        choice = Integer.parseInt(input.nextLine());
        return choice;
    }

    public static void main (String[] args){
        
        Game gameTest = new Game();
        gameTest.MainMenu();
        
        
        //mainMenu();
    }

    
}