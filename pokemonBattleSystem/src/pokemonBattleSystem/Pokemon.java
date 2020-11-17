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
  
  private String choosePokemon[] = new String[4];
  private String callBackPokemon[] = new String[4];
  private String icon;
  /*
      Constructor
      PokemonName - Name of Pokemon Object to create
      Creates specific Pokemon Object based on string arugment
  */
  public Pokemon(String PokemonName, int index, int player){
	  
    /*  if(PokemonName == null){
          System.out.println("Please Specify a Pokemon");
          return null;
      }*/
      this.name = PokemonName;
      this.index = index;
      this.icon = "images/" + PokemonName + "icon.png";
      
      this.choosePokemon[0] = "images/pokeball.png";
      this.choosePokemon[1] = "images/open pokeball.png";
      this.choosePokemon[2] = "images/" + PokemonName + "discolored";
      this.choosePokemon[3] = "images/" + PokemonName;
      if(player == 0) {
    	  this.choosePokemon[2] += "back.png";
          this.choosePokemon[3] += "back.png";
      }
      else {
    	  this.choosePokemon[2] += "front.png";
          this.choosePokemon[3] += "front.png";
      }
      
      this.callBackPokemon[0] = "images/" + PokemonName;
      this.callBackPokemon[1] = "images/" + PokemonName + "discolored";
      
      if(player == 0) {
    	  this.callBackPokemon[0] += "back.png";
          this.callBackPokemon[1] += "back.png";
      }
      else {
    	  this.callBackPokemon[0] += "front.png";
          this.callBackPokemon[1] += "front.png";
      } 
      this.callBackPokemon[2] = "images/open pokeball.png";
      this.callBackPokemon[3] = "images/pokeball.png";
      
      if(PokemonName.equalsIgnoreCase("Venusaur")){
          String[] pokemonAttacks = {"Vine Whip", "Razer Leaf", "Seed Bomb", "Leaf Storm"};
          setStats(100, 6, 6, "Grass", pokemonAttacks);
          
          //choosePokemon = new String[]{"pokeball.png", "open pokeball.png", };
      }
      else if(PokemonName.equalsIgnoreCase("Charizard")){
    	  String[] pokemonAttacks = {"Fire Fang", "Flamethrower", "Fire Blast", "Dragon Claw"};
          setStats(100, 7, 5, "Fire", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Blastoise")){
    	  String[] pokemonAttacks = {"Waterfall", "Ice Punch", "Hydro Pump", "Water Pulse"};
          setStats(100, 5, 7, "Water", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Butterfree")){
    	  String[] pokemonAttacks = {"Gust", "Bug Bite", "Air Slash", "Tackle"};
          setStats(100, 6, 5, "Bug", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Beedrill")){
    	  String[] pokemonAttacks = {"Double-Edge", "Fury Attack", "Pin Missile", "Take Down"};
          setStats(100, 6, 5, "Bug", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Pidgeot")){
    	  String[] pokemonAttacks = {"Quick Attack", "Gust", "Wing Attack", "Air Slash"};
          setStats(100, 5, 5, "Flying", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Raticate")){
    	  String[] pokemonAttacks = {"Tackle", "Quick Attack", "Crunch", "Double-Edge"};
          setStats(100, 5, 5, "Normal", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Fearow")){
    	  String[] pokemonAttacks = {"Fury Attack", "Aerial Ace", "Pluck", "Drill Peck"};
          setStats(100, 6, 4, "Flying", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Arbok")){
    	  String[] pokemonAttacks = {"Crunch", "Poison Sting", "Acid Spray", "Bite"};
          setStats(100, 6, 5, "Poison", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Pikachu")){
    	  String[] pokemonAttacks = {"Thunder Shock", "Thunderbolt", "Tail Whip", "Thunder Punch"};
          setStats(100, 6, 5, "Electric", pokemonAttacks);
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
      if(HP - applyDamage < 0) {
    	  HP = 0;
      }
      else {
    	  HP -= applyDamage;
      }
      return applyDamage;
  }
  void heal(float value) {
	  if(this.HP + value > 100) {
		  this.HP = 100;
	  }
	  else {
		  this.HP += value;
	  }
  }
  void increaseOffense(float value) {
	  this.OffenseStatus += value;
  }
  
  void increaseDefense(float value) {
	  this.DefenseStatus += value;
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
  
  public String[] getChoosePokemon() {
	  return this.choosePokemon;
  }
  
  public String[] getCallBackPokemon() {
	  return this.callBackPokemon;
  }
  
  public String getIcon(){
	  return this.icon;
  }
}