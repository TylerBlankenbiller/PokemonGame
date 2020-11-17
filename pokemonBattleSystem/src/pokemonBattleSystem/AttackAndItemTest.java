/************************************************************/
/* Filename: AttackAndItemTest.java */
/* Purpose: Tests the attack and item classes*/
/************************************************************/

package pokemonBattleSystem;

import static org.junit.Assert.*;

import org.junit.Test;

public class AttackAndItemTest {

	//These are going to fail - need more info in attack class and item class
	//Applies attack to opponent pokemon
	@Test
	public void applyAttackToOpponent() {
		Pokemon playerPokemon = new Pokemon("Blastoise", 0, 0);
		Pokemon opponentPokemon = new Pokemon("Butterfree", 0, 1);
		
		Attack attack = new Attack("Double-Edge");
		attack.applyAttack(opponentPokemon);
		assertEquals("Opponent health not correct", 75, opponentPokemon.getHP());
		assertEquals("Player health not correct", 100, playerPokemon.getHP());
	}
	
	//Applies attack to player pokemon
	@Test
	public void applyAttackToPlayer() {
		Pokemon playerPokemon = new Pokemon("Blastoise", 0, 0);
		Pokemon opponentPokemon = new Pokemon("Butterfree", 0, 1);
		
		Attack attack = new Attack("Flamethrower");
		attack.applyAttack(playerPokemon);
		assertEquals("Player health not correct", 81, playerPokemon.getHP());
	}
	
	//Change attack stats and apply to pokemon to check
	@Test
	public void changeAttackStats() {
		Pokemon playerPokemon = new Pokemon("Blastoise", 0, 0);
		Attack attack = new Attack("Flamethrower");
		attack.setStats(new int[]{50}, new int[]{3}, new String[]{"User"});
		//Gets the defense stat of the active pokemon
		float defenseStat = playerPokemon.getDefenseStatus();
		//Calculates health after preset hit
		int expectedHP = (100-(137/Math.round(defenseStat)));
		attack.applyAttack(playerPokemon);
		assertEquals("Player health not correct", expectedHP, playerPokemon.getHP());
	}
	
	//Checks attack name
	@Test
	public void checkAttackName() {
		Attack attack = new Attack("Fire Breath");
		assertEquals("Names do not match", "Fire Breath", attack.getName());
	}
	
	//Checks item name
	@Test
	public void checkItemName() {
		Item item = new Item(0);
		assertEquals("Names do not match", "Heal Potion", item.getName());
	}
	
	//Checks the opponent can use items correctly
	@Test
	public void applyItemToOpponent() {
		Pokemon playerPokemon = new Pokemon("Blastoise", 0, 0);
		Pokemon opponentPokemon = new Pokemon("Butterfree", 0, 1);
		
		opponentPokemon.setHP(50, 6);
		for(int i = 0; i < 4; i++) {
			Item item = new Item(i);
			item.applyItem(opponentPokemon);
		}
		assertEquals("Opponent health not correct", 65, opponentPokemon.getHP());
		assertEquals("Opponent offense not correct", 11, opponentPokemon.getOffenseStatus(), 0);
		assertEquals("Opponent defense not correct", 10, opponentPokemon.getDefenseStatus(), 0);
	}
	
	//Checks the player can use items correctly
	@Test
	public void applyItemToPlayer() {
		Pokemon playerPokemon = new Pokemon("Blastoise", 0, 0);
		Pokemon opponentPokemon = new Pokemon("Butterfree", 0, 1);
		
		playerPokemon.setHP(50, 6);
		for(int i = 0; i < 4; i++) {
			Item item = new Item(i);
			item.applyItem(playerPokemon);
		}
		assertEquals("Player health not correct", 82, playerPokemon.getHP());
		assertEquals("Player offense not correct", 10, playerPokemon.getOffenseStatus(), 0);
		assertEquals("Player defense not correct", 12, playerPokemon.getDefenseStatus(), 0);
	}

}
