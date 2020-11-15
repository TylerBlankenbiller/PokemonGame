/************************************************************/
/* Filename: ManagerTest.java */
/* Purpose: Tests the game manager and turn changes*/
/************************************************************/

package pokemonBattleSystem;

import static org.junit.Assert.*;

import org.junit.Test;

public class ManagerTest {

	@Test
	public void initializeTest() {
		Manager manager = new Manager();
		manager.initializeBattle();
		
		//Assert starting values
		assertEquals("Incorrect player pokemon number generated", 3, manager.getNumPlayerPokemon());
		assertEquals("Incorrect player pokemon active", "Venusaur", manager.getPSPokemon().getName());
		assertEquals("Incorrect opponent pokemon active", "Venusaur", manager.getOSPokemon().getName());
		assertEquals("Winner declared at beginning", "", manager.getWinner());
		assertEquals("Incorrect turn", 0, manager.playerTurn);
		
		//Checks the generated items
				//Create the comparison attacks array
				Item[] items = new Item[4];
				for(int i = 0; i < 4; i++)
			        items[i] = new Item();
				//Runs the comparisons
				Item[] generatedItems = manager.getPlayerItems();
				for(int i = 0; i < items.length; i++) {
					assertEquals("Items don't match", items[i].getName(), generatedItems[i].getName());
				}
	}
			
	//Change turn from player to AI back to player
	@Test
	public void turnChange() {
		Manager manager = new Manager();
		manager.initializeBattle();
		
		assertEquals("Incorrect turn", 0, manager.playerTurn);
		manager.changeTurn();
		assertEquals("Incorrect turn", 1, manager.playerTurn);
		manager.changeTurn();
		assertEquals("Incorrect turn", 0, manager.playerTurn);
	}
	
	//Checks to see if surrender sets winner correctly
	@Test
	public void checkSurrender() {
		Manager manager = new Manager();
		manager.initializeBattle();
		manager.surrender();
		assertEquals("No/incorrect winner", "opponent", manager.getWinner());
	}
	
	//Checks to see if damage applied is reliable
	//**Fails right now
	@Test
	public void applyDamage() {
		Manager manager = new Manager();
		manager.initializeBattle();
		manager.processTurn(1, 3);
		assertEquals("Incorrect damage applied", 20, manager.getOSPokemon().getHP());
	}
	
	@Test
	//Checks to see if a heal item works as expected
	//**Fails right now
	public void useItem() {
		Manager manager = new Manager();
		manager.initializeBattle();
		manager.getPSPokemon().setHP(50, 6);
		assertEquals("HP not reduced correctly", 50, manager.getPSPokemon().getHP());
		manager.processTurn(3, 1);
		//Assumes health potion heals for 30
		assertEquals("Incorrect health", 80, manager.getPSPokemon().getHP());
	}
	
	//Tests the pokemon swap
	@Test
	public void swapPokemon() {
		Manager manager = new Manager();
		manager.initializeBattle();
		Pokemon swapTarget = manager.getPlayerPokemon()[2];
		assertEquals("Unexpected switch pokemon", "Venusaur", manager.getPSPokemon().getName());
		manager.processTurn(4, 2);
		assertEquals("Unexpected switch pokemon", swapTarget.getName(), manager.getPSPokemon().getName());
	}

}