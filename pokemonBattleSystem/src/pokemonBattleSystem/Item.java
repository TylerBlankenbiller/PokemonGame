/************************************************************/
/* Filename: Item.java */
/* Purpose: Class that holds item values and applies them to target*/
/************************************************************/

package pokemonBattleSystem;

public class Item{
    String name;
    float magnitude;
    boolean resetStatus;
    //float magnitude;
    boolean used;
    int index;
    public Item(int index){
    	this.used = false;
    	this.index = index;
    	if(index == 0) {
    		this.name = "Heal Potion"; // Change name later
    		this.magnitude = 25;
    		this.resetStatus = false;
    		//this.magnitude = 0;  
    	}
    	else if(index == 1) {
    		this.name = "X Attack";
    		this.magnitude = 2;
    	}
    	else if(index == 2) {
    		this.name = "X Defense";
    		this.magnitude = 2;
    	}
    	else if(index == 3) {
    		this.name = "Guard Spec";
    		this.magnitude = 3;
    	}
    }
    
    public String getName(){
        return this.name;
    }
    
    public void applyItem(Pokemon target){
    	this.used = true;
        if(this.index == 0) {
        	target.heal(this.magnitude);
        }
        else if(this.index == 1) {
        	target.increaseOffense(this.magnitude);
        }
        else if(this.index == 2) {
        	target.increaseDefense(this.magnitude);
        }
        else if(this.index == 3) {
        	target.increaseOffense(this.magnitude);
        	target.increaseDefense(this.magnitude);
        }
    }
    
    public void setUsed(boolean value) {
    	this.used = value;
    }
    
    public boolean getUsed() {
    	return this.used;
    }
}
