package pokemonBattleSystem;

import static org.junit.Assert.*;

import org.junit.*;

public class PokemonTest {
	
	@Test
	public void checkStartingStats() {
		//Create new Charizard
		Pokemon pokemon = new Pokemon("Charizard");
		//Create the comparison attacks array
		Attack[] attacks = new Attack[4];
		String[] pokemonAttacks = {"attack1", "attack2", "attack3", "attack4"};
		for(int i = 0; i< attacks.length; i++){
			attacks[i] = new Attack(pokemonAttacks[i]);
	      }
		//Run the asserts
		assertEquals("Pokemon hp is incorrect", pokemon.getHP(), 50);
		assertEquals("Offense status is incorrect", pokemon.getOffenseStatus(), 7, 0);
		assertEquals("Defense status is incorrect", pokemon.getDefenseStatus(), 5, 0);
		assertArrayEquals("Attacks are incorrect", pokemon.getAttacks(), attacks);
	}

}
