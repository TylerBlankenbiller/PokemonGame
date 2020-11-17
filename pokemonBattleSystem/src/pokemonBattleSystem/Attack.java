/************************************************************/
/* Filename: Attack.java */
/* Purpose: Class that applies damage and status changes to pokemon*/
/************************************************************/

package pokemonBattleSystem;

//Attack Class
public class Attack
{
  private String Name;
  //private int[] Damage;
  private int damage;
  private int[] Damage = new int[3];
  private int[] Magnitude = new int[3];
  private String[] Target = new String[3];//User, UserOffense, UserDefense, Opp...
  
  /**********************************************************
  Function name: Attack
  Description: Constructor to initialize Attack data
  Parameters: String AttackName - name of attack
  Return value: None
 **********************************************************/
  public Attack(String AttackName){
      Name = AttackName;
      // Set damage based on attack name
      if(AttackName == null){
          AttackName = "Double-Edge";
      }
      Name = AttackName;
      if(AttackName.equalsIgnoreCase("Double-Edge")){
          setStats(new int[]{120, 30}, new int[]{0, 0}, new String[]{"Opponent", "User"});
      }
      else if(AttackName.equalsIgnoreCase("Synthesis")){
          setStats(new int[]{-50}, new int[]{0}, new String[]{"User"});
      }
      else if(AttackName.equalsIgnoreCase("Seed Bomb")){
          setStats(new int[] {80}, new int[] {0}, new String[]{"Opponent"});
      }
      else if(AttackName.equalsIgnoreCase("Growth")){
          setStats(new int[] {0}, new int[] {1}, new String[]{"UserOffense"});
      }
      else if(AttackName.equalsIgnoreCase("Fire Blitz")){
          setStats(new int[] {120, 30}, new int[] {0, 0}, new String[]{"Opponent", "User"});
      }
      else if(AttackName.equalsIgnoreCase("Slash")){
          setStats(new int[] {70}, new int[] {0}, new String[]{"Opponent"});
      }
      else if(AttackName.equalsIgnoreCase("Flamethrower")){
          setStats(new int[] {90, 0}, new int[] {0, -1}, new String[]{"Opponent", "OpponentOffense"});
      }
      else if(AttackName.equalsIgnoreCase("Fire Breath")){
          setStats(new int[]{60, 0}, new int[] {0, -1}, new String[]{"Opponent", "OpponentOffense"});
      }
      else if(AttackName.equalsIgnoreCase("Hydro Pump")){
          setStats(new int[]{88}, new int[] {0}, new String[]{"Opponent"});
      }
      else if(AttackName.equalsIgnoreCase("Skull Bash")){
          setStats(new int[]{65, 0}, new int[] {0, 1}, new String[]{"Opponent", "UserDefense"});
      }
      else if(AttackName.equalsIgnoreCase("Tail Whip")){
          setStats(new int[]{0}, new int[] {-1}, new String[]{"OpponentDefense"});
      }
      else if(AttackName.equalsIgnoreCase("Rain Dance")){
          setStats(new int[]{0}, new int[] {1}, new String[]{"UserOffense"});
      }
      else if(AttackName.equalsIgnoreCase("Gust")){
          setStats(new int[]{40}, new int[] {0}, new String[]{"Opponent"});
      }
      else if(AttackName.equalsIgnoreCase("Safeguard")){
          setStats(new int[]{0, 0}, new int[] {1, 1}, new String[]{"UserOffense", "UserDefense"});
      }
      else if(AttackName.equalsIgnoreCase("Psybeam")){
          setStats(new int[]{65, 0}, new int[] {0, -1}, new String[]{"Opponent", "OpponentDefense"});
      }
      else if(AttackName.equalsIgnoreCase("Curse")){
          setStats(new int[]{0, 0}, new int[] {-1, -1}, new String[]{"OpponentOffense", "OpponentDefense"});
      }
      else if(AttackName.equalsIgnoreCase("Pin Missile")){
          setStats(new int[]{75}, new int[] {0}, new String[]{"Opponent"});
      }
      else if(AttackName.equalsIgnoreCase("Twin Needle")){
          setStats(new int[]{25, 0}, new int[] {0, -2}, new String[]{"Opponent", "OpponentDefense"});
      }
      else if(AttackName.equalsIgnoreCase("Focus Energy")){
          setStats(new int[]{0}, new int[] {1}, new String[]{"UserOffense"});
      }
      else if(AttackName.equalsIgnoreCase("Wing Attack")){
          setStats(new int[]{60}, new int[] {0}, new String[]{"Opponent"});
      }
      else if(AttackName.equalsIgnoreCase("Sand Attack")){
          setStats(new int[]{0}, new int[] {-1}, new String[]{"OpponentOffense"});
      }
      else if(AttackName.equalsIgnoreCase("Rest")){
          setStats(new int[]{-1000}, new int[] {-12}, new String[]{"User", "OpponentOffense"});
      }
      else{
          System.out.println("Sorry we don't support that Move");
      }
      
  }
  
  void setStats(int[] damages, int[] magnitudes, String[] targets){
	  for(int i = 0; i < damages.length; i++){
		  Damage[i] = damages[i];
		  Magnitude[i] = magnitudes[i];
		  Target[i] = targets[i];
      }
	  for(int i = damages.length; i < 3; i++){
		  Damage[i] = 0;
		  Magnitude[i] = 0;
		  Target[i] = "None";
      }
  }
  
 /**********************************************************
  Function name: applyAttack
  Description: Updates Pokemon stats based on Attack
  Parameters: Pokemon target - Pokemon to apply attack to
  Return value: Float containing total damage applied
 **********************************************************/
  public float applyAttack(Pokemon target, Pokemon own){
	  float totalDamage = 0;
	  for(int i = 0; i < 3; i++){
		  if(this.Target[i] == "Opponent") {
			  System.out.println("Opponent");
			  totalDamage = target.setHP(this.Damage[i], own.getOffenseStatus());}
		  if(this.Target[i] == "User") {
			  System.out.println("User");
			  own.setHP(this.Damage[0], own.getOffenseStatus());}
		  else if(this.Target[i] == "UserOffense") {
			  System.out.println("UserOffense");
			  own.setOffenseStatus(this.Magnitude[i]);}
		  else if(this.Target[i] == "UserDefense") {
			  System.out.println("UserDefense");
			  own.setDefenseStatus(this.Magnitude[i]);}
		  else if(this.Target[i] == "OpponentOffense") {
			  System.out.println("OpponentOffense");
			  System.out.println(this.Magnitude[i]);
			  target.setOffenseStatus(this.Magnitude[i]);}
		  else if(this.Target[i] == "OpponentDefense") {
			  System.out.println("OpponentDefense");
			  target.setDefenseStatus(this.Magnitude[i]);}
      }
      return totalDamage;
  }
  
 /**********************************************************
  Function name: getName
  Description: Get name of Attack
  Parameters: None
  Return value: String containing name of Attack
 **********************************************************/
  public String getName(){
      return this.Name;
  }
}
