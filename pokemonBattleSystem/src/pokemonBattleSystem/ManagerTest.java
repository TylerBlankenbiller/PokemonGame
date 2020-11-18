/************************************************************/
/* Filename: ManagerTest.java */
/* Purpose: Tests the game manager and turn changes*/
/************************************************************/

package pokemonBattleSystem;

import static org.junit.Assert.*;

import org.junit.Test;

public class ManagerTest {

	/**********************************************************
    Test name: initializeTest
    Description: Checks initial manager values
   **********************************************************/
	@Test
	public void initializeTest() {
		Manager manager = new Manager();
		manager.initializeBattle();
		
		//Assert starting values
		assertEquals("Incorrect player pokemon number generated", 3, manager.getPlayerPokemon().length);
		assertEquals("Winner declared at beginning", "", manager.getWinner());
		assertEquals("Incorrect turn", 0, manager.getPlayerTurn());
		
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
			
	/**********************************************************
    Test name: turnChange
    Description: Checks if the turn swaps correctly 
   **********************************************************/
	@Test
	public void turnChange() {
		Manager manager = new Manager();
		manager.initializeBattle();
		
		assertEquals("Incorrect turn", 0, manager.getPlayerTurn());
		//manager.changeTurn();
		//assertEquals("Incorrect turn", 1, manager.getPlayerTurn());
		//manager.changeTurn();
		assertEquals("Incorrect turn", 0, manager.getPlayerTurn());
	}
	
	/**********************************************************
    Test name: applyDamage
    Description: Checks if a dummy attack matches the manager's generated one
   **********************************************************/
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
		Pokemon playerPokemon = manager.getPSPokemon();
		Pokemon opponentPokemon = manager.getOSPokemon();
		selectedAttack.applyAttack(opponentPokemon, playerPokemon);
		
		//Applies the attack action
		manager.processTurn(0, 1);
		assertEquals("Incorrect damage applied", opponentPokemon.getHP(), manager.getOSPokemon().getHP());
	}
	
	/**********************************************************
    Test name: useItem
    Description: Checks if heal item works through manager
   **********************************************************/
	@Test
	public void useItem() {
		Manager manager = new Manager();
		manager.initializeBattle();
		
		//Gets the defense stat of the active pokemon
		float defenseStat = manager.getPSPokemon().getDefenseStatus();
		//Calculates health after preset hit
		int expectedHP = (manager.getPSPokemon().getHP()-(300/Math.round(defenseStat)));
		
		//Sets and checks new health
		manager.getPSPokemon().setHP(50, 6);
		assertEquals("HP not reduced correctly", expectedHP, manager.getPSPokemon().getHP());
		manager.processTurn(2, 0);
		//Assumes health potion heals for 50
		assertEquals("Incorrect health", (expectedHP + 50), manager.getPSPokemon().getHP());
	}
	
	/**********************************************************
    Test name: swapPokemon
    Description: Checks if the pokemon swap works as expected
   **********************************************************/
	@Test
	public void swapPokemon() {
		Manager manager = new Manager();
		manager.initializeBattle();
		Pokemon swapTarget = manager.getPlayerPokemon()[2];
		manager.processTurn(1, 2);
		assertEquals("Unexpected switch pokemon", swapTarget.getName(), manager.getPSPokemon().getName());
	}

}
