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
		Pokemon playerPokemon = new Pokemon("Blastoise", 0);
		Pokemon opponentPokemon = new Pokemon("Butterfree", 0);
		
		Attack attack = new Attack("Double-Edge");
		attack.applyAttack(opponentPokemon);
		assertEquals("Opponent health not correct", 80, opponentPokemon.getHP());
		assertEquals("Player health not correct", 60, opponentPokemon.getHP());
	}
	
	//Applies attack to player pokemon
	@Test
	public void applyAttackToPlayer() {
		Pokemon playerPokemon = new Pokemon("Blastoise", 0);
		Pokemon opponentPokemon = new Pokemon("Butterfree", 0);
		
		Attack attack = new Attack("Flamethrower");
		attack.applyAttack(opponentPokemon);
		assertEquals("Opponent health not correct", 10, opponentPokemon.getHP());
	}
	
	//Change attack stats and apply to pokemon to check
	@Test
	public void changeAttackStats() {
		Pokemon playerPokemon = new Pokemon("Blastoise", 0);
		Attack attack = new Attack("Flamethrower");
		attack.setStats(new int[]{50}, new int[]{3}, new String[]{"User"});
		attack.applyAttack(playerPokemon);
		assertEquals("Player health not correct", 50, playerPokemon.getHP());
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
		Item item = new Item();
		assertEquals("Names do not match", "Heal Potion", item.getName());
	}
	
	//Checks the opponent can use items correctly
	@Test
	public void applyItemToOpponent() {
		Pokemon playerPokemon = new Pokemon("Blastoise", 0);
		Pokemon opponentPokemon = new Pokemon("Butterfree", 0);
		
		opponentPokemon.setHP(50, 6);
		Item item = new Item();
		item.applyItem(opponentPokemon);
		assertEquals("Opponent health not correct", 80, opponentPokemon.getHP());
	}
	
	//Checks the player can use items correctly
	@Test
	public void applyItemToPlayer() {
		Pokemon playerPokemon = new Pokemon("Blastoise", 0);
		Pokemon opponentPokemon = new Pokemon("Butterfree", 0);
		
		playerPokemon.setHP(50, 6);
		Item item = new Item();
		item.applyItem(playerPokemon);
		assertEquals("Opponent health not correct", 80, playerPokemon.getHP());
	}

}
