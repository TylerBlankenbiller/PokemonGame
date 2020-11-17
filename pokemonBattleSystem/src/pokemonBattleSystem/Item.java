/************************************************************/
/* Filename: Item.java */
/* Purpose: Class that holds item values and applies them to target*/
/************************************************************/

package pokemonBattleSystem;

public class Item{
    String name;
    float magnitude;
    boolean resetStatus;
    boolean used;
    // Unique identifier
    int index;
    
   /**********************************************************
    Function name: Item
    Description: Constructor to initialize Item data
    Parameters: int index - unique identifier
    Return value: None
   **********************************************************/
    public Item(int index){
    	this.used = false;
    	this.index = index;
    	// Item to heal
    	if(index == 0) {
    		this.name = "Super Potion";
    		this.magnitude = 50;
    		this.resetStatus = false;
    	}
    	// Item to increase offense
    	else if(index == 1) {
    		this.name = "X Attack";
    		this.magnitude = 2;
    	}
    	// Item to increase defense
    	else if(index == 2) {
    		this.name = "X Defense";
    		this.magnitude = 2;
    	}
    	// Item to increase offense and defense
    	else if(index == 3) {
    		this.name = "Guard Spec";
    		this.magnitude = 3;
    	}
    }
    
   /**********************************************************
    Function name: getName
    Description: Gets name of Item
    Parameters: None
    Return value: String containing name of Item
   **********************************************************/
    public String getName(){
        return this.name;
    }
    
   /**********************************************************
    Function name: applyItem
    Description: Updates Pokemon stats based on Item
    Parameters: Pokemon target - Pokemon to apply Item to
    Return value: None
   **********************************************************/
    public void applyItem(Pokemon target){
    	this.used = true;
    	// Heal potion
        if(this.index == 0) {
        	target.heal(this.magnitude);
        }
        // X Attack
        else if(this.index == 1) {
        	target.setOffenseStatus(this.magnitude);
        }
        // X Defense
        else if(this.index == 2) {
        	target.setDefenseStatus(this.magnitude);
        }
        // Guard Spec
        else if(this.index == 3) {
        	target.setOffenseStatus(this.magnitude);
        	target.setDefenseStatus(this.magnitude);
        }
    }
    
   /**********************************************************
    Function name: setUsed
    Description: Sets value of Item based on if it's used or 
    			 not
    Parameters: boolean value - true if item has been used,
    							false otherwise
    Return value: None
   **********************************************************/
    public void setUsed(boolean value) {
    	this.used = value;
    }
    
   /**********************************************************
    Function name: getUsed
    Description: Gets if Item was used or not
    Parameters: None
    Return value: Boolean indicating if Item was used or not
   **********************************************************/
    public boolean getUsed() {
    	return this.used;
    }
}
