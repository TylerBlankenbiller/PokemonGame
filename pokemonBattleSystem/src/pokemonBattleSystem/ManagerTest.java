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
		assertEquals("Winner declared at beginning", "", manager.getWinner());
		assertEquals("Incorrect turn", 0, manager.playerTurn);
		
		//Checks the generated items
				//Create the comparison attacks array
				Item[] items = new Item[4];
				for(int i = 0; i < 4; i++)
			        items[i] = new Item(i);
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
	
	//Checks to see if damage applied is reliable
	//**Fails right now
	@Test
	public void applyDamage() {
		Manager manager = new Manager();
		manager.initializeBattle();
		
		//Gets the defense stat of the active pokemon
		float defenseStat = manager.getPSPokemon().getDefenseStatus();
		//Calculates health after preset hit
		int expectedHP = (100-(300/Math.round(defenseStat)));
		
		//Attacks dummy pokemon with active pokemon attack 1
		Attack selectedAttack = manager.getPSPokemon().getAttacks()[0];
		Pokemon dummyPokemon = manager.getOSPokemon();
		selectedAttack.applyAttack(dummyPokemon);
		
		//Applies the attack action
		manager.processTurn(1, 1);
		assertEquals("Incorrect damage applied", dummyPokemon.getHP(), manager.getOSPokemon().getHP());
	}
	
	@Test
	//Checks to see if a heal item works as expected
	//**Fails right now
	public void useItem() {
		Manager manager = new Manager();
		manager.initializeBattle();
		
		//Gets the defense stat of the active pokemon
		float defenseStat = manager.getPSPokemon().getDefenseStatus();
		//Calculates health after preset hit
		int expectedHP = (100-(300/Math.round(defenseStat)));
		
		//Sets and checks new health
		manager.getPSPokemon().setHP(50, 6);
		assertEquals("HP not reduced correctly", expectedHP, manager.getPSPokemon().getHP());
		manager.processTurn(3, 1);
		//Assumes health potion heals for 25
		assertEquals("Incorrect health", (expectedHP + 25), manager.getPSPokemon().getHP());
	}
	
	//Tests the pokemon swap
	@Test
	public void swapPokemon() {
		Manager manager = new Manager();
		manager.initializeBattle();
		Pokemon swapTarget = manager.getPlayerPokemon()[2];
		manager.processTurn(4, 3);
		assertEquals("Unexpected switch pokemon", swapTarget.getName(), manager.getPSPokemon().getName());
	}

}
