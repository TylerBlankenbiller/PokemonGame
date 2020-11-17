/************************************************************/
/* Filename: PokemonTest.java */
/* Purpose: Tests the pokemon generation and stats changes*/
/************************************************************/

package pokemonBattleSystem;

import static org.junit.Assert.*;

import org.junit.*;

public class PokemonTest {
	
	/**********************************************************
    Test name: checkStartingStats
    Description: Checks pokemon initial stats
   **********************************************************/
	@Test
	public void checkStartingStats() {
		//Create new Charizard
		Pokemon pokemon = new Pokemon("Charizard", 1, 0);
		
		//Run the asserts
		assertEquals("Name is incorrect", "Charizard", pokemon.getName());
		assertEquals("Index is incorrect", 1, pokemon.getIndex());
		assertEquals("Pokemon hp is incorrect", 300, pokemon.getHP());
		assertEquals("Offense status is incorrect", 7, pokemon.getOffenseStatus(), 0);
		assertEquals("Defense status is incorrect", 5, pokemon.getDefenseStatus(), 0);
		
		//Checks the generated attacks
		//Create the comparison attacks array
		Attack[] attacks = new Attack[4];
		String[] pokemonAttacks = {"Flamethrower", "Slash", "Fire Blitz", "Fire Breath"};
		for(int i = 0; i< attacks.length; i++){
			attacks[i] = new Attack(pokemonAttacks[i]);
	      }
		//Runs the comparisons
		Attack[] generatedAttacks = pokemon.getAttacks();
		for(int i = 0; i < attacks.length; i++) {
			assertEquals("Attacks don't match", attacks[i].getName(), generatedAttacks[i].getName());
		}
	}
	
	/**********************************************************
    Test name: checkDamage
    Description: Checks setting HP
   **********************************************************/
	@Test
	public void checkDamage() {
		Pokemon target = new Pokemon("Venusaur", 1, 0);
		target.setHP(30, 6);
		assertEquals("Pokemon health after 30 damage is incorrect", 270, target.getHP(),  0);
	}
	
	/**********************************************************
    Test name: checkFainted
    Description: Tests whether the fainted tag works as expected
   **********************************************************/
	@Test
	public void checkFainted() {
		Pokemon target = new Pokemon("Butterfree",1, 0);
		target.setHP(80,5);
		assertFalse("Pokemon should not be fainted", target.isFainted());//HP is 20
		target.setHP(200,5);
		assertTrue("Pokemon should be fainted",target.isFainted());//HP is 0
		target.setHP(100,5);
		assertTrue("Pokemon should be fainted",target.isFainted());//HP is -20
	}


}