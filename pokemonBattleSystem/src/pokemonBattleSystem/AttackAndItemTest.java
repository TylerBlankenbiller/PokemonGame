/************************************************************/
/* Filename: AttackAndItemTest.java */
/* Purpose: Tests the attack and item classes*/
/************************************************************/

package pokemonBattleSystem;

import static org.junit.Assert.*;

import org.junit.Test;

public class AttackAndItemTest {

	/**********************************************************
    Test name: applyAttackToOpponent
    Description: Checks if the damage applied is accurate
   **********************************************************/
	@Test
	public void applyAttackToOpponent() {
		Pokemon playerPokemon = new Pokemon("Blastoise", 0, 0);
		Pokemon opponentPokemon = new Pokemon("Butterfree", 0, 1);
		
		Attack attack = new Attack("Double-Edge");
		attack.applyAttack(opponentPokemon, playerPokemon);
		assertEquals("Opponent health not correct", 120, opponentPokemon.getHP());
		assertEquals("Player health not correct", 279, playerPokemon.getHP());
	}
	
	/**********************************************************
    Test name: applyAttackToPlayer
    Description: Checks if the damage applied is accurate
   **********************************************************/
	@Test
	public void applyAttackToPlayer() {
		Pokemon playerPokemon = new Pokemon("Blastoise", 0, 0);
		Pokemon opponentPokemon = new Pokemon("Butterfree", 0, 1);
		
		Attack attack = new Attack("Flamethrower");
		attack.applyAttack(playerPokemon, opponentPokemon);
		assertEquals("Player health not correct", 223, playerPokemon.getHP());
	}
	
	/**********************************************************
    Test name: checkAttackStats
    Description: Checks if the stats for an attack are calculated correctly
   **********************************************************/
	@Test
	public void checkAttackStats() {
		Pokemon playerPokemon = new Pokemon("Blastoise", 0, 0);
		Pokemon opponentPokemon = new Pokemon("Butterfree", 0, 0);
		Attack attack = new Attack("Flamethrower");
		//Gets the defense stat of the active pokemon
		float defenseStat = playerPokemon.getDefenseStatus();
		//Calculates health after preset hit
		int expectedHP = (300-(540/Math.round(defenseStat)));//540 is flamethrower damage * Butterfree's offense
		attack.applyAttack(playerPokemon, opponentPokemon);
		assertEquals("Player health not correct", expectedHP, playerPokemon.getHP());
	}
	
	/**********************************************************
    Test name: checkAttackName
    Description: Checks the attack name
   **********************************************************/
	@Test
	public void checkAttackName() {
		Attack attack = new Attack("Fire Breath");
		assertEquals("Names do not match", "Fire Breath", attack.getName());
	}
	
	/**********************************************************
    Test name: checkItemName
    Description: Checks the item name
   **********************************************************/
	@Test
	public void checkItemName() {
		Item item = new Item(0);
		assertEquals("Names do not match", "Super Potion", item.getName());
	}
	
	/**********************************************************
    Test name: applyItemToOpponent
    Description: Checks if all items can be applied to opponent
   **********************************************************/
	@Test
	public void applyItemToOpponent() {
		Pokemon playerPokemon = new Pokemon("Blastoise", 0, 0);
		Pokemon opponentPokemon = new Pokemon("Butterfree", 0, 1);
		
		opponentPokemon.setHP(75, 5);
		for(int i = 0; i < 4; i++) {
			Item item = new Item(i);
			item.applyItem(opponentPokemon);
		}
		assertEquals("Opponent health not correct", 215, opponentPokemon.getHP());
		assertEquals("Opponent offense not correct", 18, Math.round(opponentPokemon.getOffenseStatus()), 0);
		assertEquals("Opponent defense not correct", 15, Math.round(opponentPokemon.getDefenseStatus()), 0);
	}
	
	/**********************************************************
    Test name: applyItemToPlayer
    Description: Checks if all items can be applied to player
   **********************************************************/
	@Test
	public void applyItemToPlayer() {
		Pokemon playerPokemon = new Pokemon("Blastoise", 0, 0);
		Pokemon opponentPokemon = new Pokemon("Butterfree", 0, 1);
		
		playerPokemon.setHP(100, 7);
		for(int i = 0; i < 4; i++) {
			Item item = new Item(i);
			item.applyItem(playerPokemon);
		}
		assertEquals("Player health not correct", 250, playerPokemon.getHP());
		assertEquals("Player offense not correct", 15, Math.round(playerPokemon.getOffenseStatus()), 0);
		assertEquals("Player defense not correct", 21, Math.round(playerPokemon.getDefenseStatus()), 0);
	}

}