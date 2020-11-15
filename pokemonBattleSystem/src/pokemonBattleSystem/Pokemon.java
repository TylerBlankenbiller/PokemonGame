/************************************************************/
/* Filename: Pokemon.java */
/* Purpose: Class that is instantiated for each pokemon in the battle*/
/************************************************************/

package pokemonBattleSystem;

//Pokemon Factory
import java.lang.Math;

public class Pokemon
{
  private String name;
  private int index;
  private int HP;
  private float OffenseStatus;
  private float DefenseStatus;
  private float[] OffenseList = new float[9];
  private float[] DefenseList = new float[9];
  private int OffensePointer;
  private int DefensePointer;
  private String Type;
  protected Attack[] Attacks = new Attack[4];
  
  /*
      Constructor
      PokemonName - Name of Pokemon Object to create
      Creates specific Pokemon Object based on string arugment
  */
  public Pokemon(String PokemonName, int index){
    /*  if(PokemonName == null){
          System.out.println("Please Specify a Pokemon");
          return null;
      }*/
      this.name = PokemonName;
      this.index = index;
      if(PokemonName.equalsIgnoreCase("Venusaur")){
          String[] pokemonAttacks = {"Double-Edge", "Synthesis", "Seed Bomb", "Growth"};
          setStats(100, 6, 6, "Grass", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Charizard")){
          String[] pokemonAttacks = {"Fire Breath", "Wing Attack", "Ember", "Slash"};
          setStats(100, 7, 5, "Fire", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Blastoise")){
          String[] pokemonAttacks = {""};
          setStats(100, 5, 7, "Water", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Butterfree")){
          String[] pokemonAttacks = {""};
          setStats(100, 6, 5, "Bug", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Beedrill")){
          String[] pokemonAttacks = {""};
          setStats(100, 6, 5, "Bug", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Pidgeot")){
          String[] pokemonAttacks = {""};
          setStats(100, 5, 5, "Flying", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Raticate")){
          String[] pokemonAttacks = {""};
          setStats(100, 5, 5, "Normal", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Fearow")){
          String[] pokemonAttacks = {""};
          setStats(100, 6, 4, "Flying", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Arbok")){
          String[] pokemonAttacks = {""};
          setStats(100, 6, 5, "Poison", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Pikachu")){
          String[] pokemonAttacks = {""};
          setStats(100, 6, 5, "Electric", pokemonAttacks);
      }
      else{
       //   System.out.println("Sorry we don't support that Pokemon");
        //  return null;
      }
  }
  
  void setStats(int myHP, float myOffenseStatus, float myDefenseStatus, String myType, String myAttacks[]){
      HP = myHP;
      OffenseStatus = myOffenseStatus;
      DefenseStatus = myDefenseStatus;
      for(int i = 0; i < 4; i++){
          OffenseList[i] = myOffenseStatus * (float)(0.4 + (i * 0.15));
          DefenseList[i] = myDefenseStatus * (float)(0.4 + (i * 0.15));
      }
      for(int i = 5; i < 9; i++){
          OffenseList[i] = myOffenseStatus * (float)(1 + ((i-4)*0.15));
          DefenseList[i] = myDefenseStatus * (float)(1 + ((i-4)*0.15));
      }
      OffensePointer = 4;
      DefensePointer = 4;
      Type = myType;
      for(int i = 0; i< myAttacks.length; i++){
          Attacks[i] = new Attack(myAttacks[i]);
      }
  }
  
  //Check if HP is less than equal to 0 (fainted, not usable in battle)
  boolean isFainted(){
      if(HP > 0)
          return false;
      return true;
  }
  
  //Set Functions
  void setOffenseStatus(int statusChange){
      OffensePointer += statusChange;
      OffenseStatus = OffenseList[OffensePointer];
  }
  
  void setDefenseStatus(int statusChange){
      DefensePointer += statusChange;
      DefenseStatus = DefenseList[DefensePointer];
  }
  
  int setHP(int damage, float offense){
      int applyDamage = Math.round((offense * damage) / DefenseStatus);
      HP -= applyDamage;
      return applyDamage;
  }
  
  //Get Functions
  float getOffenseStatus(){
      return OffenseStatus;
  }
  float getDefenseStatus(){
      return DefenseStatus;
  }
  int getHP(){
      return HP;
  }
  
  public Attack[] getAttacks(){
      return this.Attacks;
  }
  
  public String getName(){
      return this.name;
  }
  
  public int getIndex() {
	  return this.index;
  }
  
}