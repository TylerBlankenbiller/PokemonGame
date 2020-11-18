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
  // Unique identifier
  private int index;
  // Status data
  private int HP;
  private int MaxHP;
  private float OffenseStatus;
  private float DefenseStatus;
  private float[] OffenseList = new float[9];
  private float[] DefenseList = new float[9];
  private int OffensePointer;
  private int DefensePointer;
  private String Type;
  protected Attack[] Attacks = new Attack[4];
  
  // Images
  private String choosePokemon[] = new String[4];
  private String callBackPokemon[] = new String[4];
  private String icon;

 /**********************************************************
  Function name: Pokemon
  Description: Constructor to initialize Pokemon data
  Parameters: String PokemonName - name of Pokemon
  			  int index - unique identifier
  			  int player - player or opponent
  Return value: None
 **********************************************************/
  public Pokemon(String PokemonName, int index, int player){
      this.name = PokemonName;
      this.index = index;
      this.icon = "images/" + PokemonName + "icon.png";
      
      // Set images when choosing Pokemon
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
      
      // Set images when calling back Pokemon
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
      
      // Set stats based on Pokemon name
      if(PokemonName.equalsIgnoreCase("Venusaur")){
          String[] pokemonAttacks = {"Double-Edge", "Growth", "Seed Bomb", "Synthesis"};
          setStats(300, 6, 6, "Grass", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Charizard")){
    	  String[] pokemonAttacks = {"Flamethrower", "Slash", "Fire Blitz", "Fire Breath"};
          setStats(300, 7, 5, "Fire", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Blastoise")){
    	  String[] pokemonAttacks = {"Hydro Pump", "Skull Bash", "Tail Whip", "Rain Dance"};
          setStats(300, 5, 7, "Water", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Butterfree")){
    	  String[] pokemonAttacks = {"Gust", "Safeguard", "Psybeam", "Curse"};
          setStats(240, 6, 5, "Bug", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Beedrill")){
    	  String[] pokemonAttacks = {"Pin Missile", "Twin Needle", "Focus Energy", "Curse"};
          setStats(240, 6, 5, "Bug", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Pidgeot")){
    	  String[] pokemonAttacks = {"Sand Attack", "Focus Energy", "Wing Attack", "Rest"};
          setStats(300, 5, 5, "Flying", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Raticate")){
    	  String[] pokemonAttacks = {"Hyper Fang", "Focus Energy", "Tail Whip", "Double-Edge"};
          setStats(240, 5, 5, "Normal", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Fearow")){
    	  String[] pokemonAttacks = {"Leer", "Roost", "Focus Energy", "Drill Peck"};
          setStats(240, 6, 4, "Flying", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Arbok")){
    	  String[] pokemonAttacks = {"Toxic", "Poison Jab", "Glare", "Rest"};
          setStats(240, 6, 5, "Poison", pokemonAttacks);
      }
      else if(PokemonName.equalsIgnoreCase("Pikachu")){
    	  String[] pokemonAttacks = {"Double Team", "Thunderbolt", "Tail Whip", "Rest"};
          setStats(240, 6, 5, "Electric", pokemonAttacks);
      }//Hello
      
  }
  
 /**********************************************************
  Function name: setStats
  Description: Sets Pokemon stats based on input
  Parameters: int myHP - Pokemon health
  			  float myOffenseStatus - Pokemon offense
  			  float myDefenseStatus - Pokemon defense
  			  String myType - Pokemon type
  			  String myAttacks[] - array of attacks
  Return value: None
 **********************************************************/
  void setStats(int myHP, float myOffenseStatus, float myDefenseStatus, String myType, String myAttacks[]){
      HP = myHP;
      MaxHP = myHP;
      OffenseStatus = myOffenseStatus;
      DefenseStatus = myDefenseStatus;
      for(int i = 0; i < 4; i++){
          OffenseList[i] = myOffenseStatus * (float)(0.4 + (i * 0.15));
          DefenseList[i] = myDefenseStatus * (float)(0.4 + (i * 0.15));
      }
      for(int i = 5; i < 9; i++){
          OffenseList[i] = myOffenseStatus * (float)(1 + ((i-4)*0.5));
          DefenseList[i] = myDefenseStatus * (float)(1 + ((i-4)*0.5));
      }
      OffenseList[4] = myOffenseStatus;
      DefenseList[4] = myDefenseStatus;
      OffensePointer = 4;
      DefensePointer = 4;
      Type = myType;
      for(int i = 0; i< myAttacks.length; i++){
          Attacks[i] = new Attack(myAttacks[i]);
      }
  }
  
 /**********************************************************
  Function name: isFainted
  Description: Checks if Pokemon is fainted
  Parameters: None
  Return value: True if Pokemon is fainted, false otherwise
 **********************************************************/
  boolean isFainted(){
      if(HP > 0)
          return false;
      return true;
  }
  
  /**********************************************************
  Function name: setHP
  Description: Sets HP based on input
  Parameters: int damage - damage to deal
  			  float offense - opponent's offense
  Return value: Integer containing total damage applied
 **********************************************************/
  int setHP(int damage, float offense){
      int applyDamage = Math.round((offense * damage) / DefenseStatus);
      if(HP - applyDamage < 0) {
    	  HP = 0;
      }
      else {
    	  HP -= applyDamage;
    	  if(HP > MaxHP)
    		  HP = MaxHP;
      }
      return applyDamage;
  }
  
  /**********************************************************
  Function name: heal
  Description: Adds value to Pokemon HP
  Parameters: float value - number to increment Pokemon HP by
  Return value: None
 **********************************************************/
  void heal(float value) {
	  if(this.HP + value > this.MaxHP) {
		  this.HP = this.MaxHP;
	  }
	  else {
		  this.HP += value;
	  }
  }
  
 /**********************************************************
  Function name: setOffenseStatus
  Description: Sets offense status based on input
  Parameters: int statusChange - value to change status by
  Return value: None
 **********************************************************/
  void setOffenseStatus(float value) {
	  //this.OffenseStatus += value;
	   this.OffensePointer += (int)value;
	  if(this.OffensePointer < 0)
		  this.OffensePointer = 0;
	  else if(this.OffensePointer > 8)
		  this.OffensePointer = 8;
	  System.out.println("Offense Change");
	  System.out.println(this.OffenseList[this.OffensePointer]);
	  System.out.println(this.OffensePointer);
	  this.OffenseStatus = this.OffenseList[this.OffensePointer];
  }
  
 /**********************************************************
  Function name: setDefenseStatus
  Description: Sets defense status based on input
  Parameters: int statusChange - value to change status by
  Return value: None
 **********************************************************/
  void setDefenseStatus(float value) {
	  //this.DefenseStatus += value;
	   this.DefensePointer += (int)value;
	  if(this.DefensePointer < 0)
		  this.DefensePointer = 0;
	  else if(this.DefensePointer > 8)
		  this.DefensePointer = 8;
	  this.DefenseStatus = this.DefenseList[this.DefensePointer];
  }

 /**********************************************************
  Function name: getOffenseStatus
  Description: Gets offense status
  Parameters: None
  Return value: Float containing Pokemon offense status
 **********************************************************/
  float getOffenseStatus(){
      return OffenseStatus;
  }
  
 /**********************************************************
  Function name: getDefenseStatus
  Description: Gets defense status
  Parameters: None
  Return value: Float containing Pokemon defense status
 **********************************************************/
  float getDefenseStatus(){
      return DefenseStatus;
  }
  
  /**********************************************************
  Function name: getHP
  Description: Gets health
  Parameters: None
  Return value: Integer containing Pokemon health
 **********************************************************/
  int getHP(){
      return HP;
  }
  
  /**********************************************************
  Function name: getMaxHP
  Description: Gets health
  Parameters: None
  Return value: Integer containing Pokemon health
 **********************************************************/
  int getMaxHP(){
      return MaxHP;
  }
  
 /**********************************************************
  Function name: getAttacks
  Description: Gets Pokemon attacks
  Parameters: None
  Return value: Attack array containing Pokemon attacks
 **********************************************************/
  public Attack[] getAttacks(){
      return this.Attacks;
  }
  
 /**********************************************************
  Function name: getName
  Description: Gets Pokemon name
  Parameters: None
  Return value: String containing Pokemon name
 **********************************************************/
  public String getName(){
      return this.name;
  }
  
 /**********************************************************
  Function name: getIndex
  Description: Gets Pokemon unique identifier
  Parameters: None
  Return value: Integer containing Pokemon index
 **********************************************************/
  public int getIndex() {
	  return this.index;
  }
  
 /**********************************************************
  Function name: getChoosePokemon
  Description: Gets images for choosing Pokemon
  Parameters: None
  Return value: String array containing image strings
 **********************************************************/
  public String[] getChoosePokemon() {
	  return this.choosePokemon;
  }
  
 /**********************************************************
  Function name: getCallBackPokemon
  Description: Gets images for calling back Pokemon
  Parameters: None
  Return value: String array containing image strings
 **********************************************************/
  public String[] getCallBackPokemon() {
	  return this.callBackPokemon;
  }
  
 /**********************************************************
  Function name: getIcon
  Description: Gets icon image for Pokemon
  Parameters: None
  Return value: String containing image string for icon
 **********************************************************/
  public String getIcon(){
	  return this.icon;
  }
}
