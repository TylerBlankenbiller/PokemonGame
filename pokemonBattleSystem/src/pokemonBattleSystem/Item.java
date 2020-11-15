/************************************************************/
/* Filename: Item.java */
/* Purpose: Class that holds item values and applies them to target*/
/************************************************************/

package pokemonBattleSystem;

public class Item{
    String name;
    int heal;
    boolean resetStatus;
    float magnitude;
    
    public Item(){
        this.name = "Heal Potion"; // Change name later
        this.heal = 0;
        this.resetStatus = false;
        this.magnitude = 0;  
    }
    
    public String getName(){
        return this.name;
    }
    
    public void applyItem(Pokemon target){
        
    }
}
