package pokemonBattleSystem;

//Attack Class
public class Attack
{
  private String Name;
  private int[] Damage;
  private int[] Magnitude;
  private String[] Target;//User, UserOffense, UserDefense, Opp...
  
  /*
      Constructor
      AttackName - Name of Attack Object to create
      Creates specific Attack Object based on string arugment
  */
  public Attack(String AttackName){
     /* if(AttackName == null){
          System.out.println("Please Specify an Attack");
          return null;
      }*/
      Name = AttackName;
      if(AttackName.equalsIgnoreCase("Double-Edge")){
          setStats(new int[]{20, 40}, new int[]{0, 0}, new String[]{"Opponent", "User"});
      }
      else if(AttackName.equalsIgnoreCase("Synthesis")){
          setStats(new int[]{-50}, new int[]{0}, new String[]{"User"});
      }
      else if(AttackName.equalsIgnoreCase("Seed Bomb")){
          setStats(new int[]{80}, new int[]{0}, new String[]{"Opponent"});
      }
      else if(AttackName.equalsIgnoreCase("Growth")){
          setStats(new int[]{0}, new int[]{1}, new String[]{"UserOffense"});
      }
      else if(AttackName.equalsIgnoreCase("Fire Blitz")){
          setStats(new int[]{120, 40}, new int[]{0, 0}, new String[]{"Opponent", "User"});
      }
      else if(AttackName.equalsIgnoreCase("Slash")){
          setStats(new int[]{70}, new int[]{0}, new String[]{"Opponent"});
      }
      else if(AttackName.equalsIgnoreCase("Flamethrower")){
          setStats(new int[]{90, 0}, new int[]{0, -1}, new String[]{"Opponent", "OpponentOffense"});
      }
      else if(AttackName.equalsIgnoreCase("Fire Breath")){
          setStats(new int[]{60, 0}, new int[]{0, -1}, new String[]{"Opponent", "OpponentOffense"});
      }
      else{
          System.out.println("Sorry we don't support that Move ");
         // return null;
      }
  }
  
  void setStats(int[] damage, int[] magnitude, String[] target){
      
  }
  
  public float applyAttack(Pokemon target){
	  float damage = target.SetHP(25, 10);
      return damage;
  }
  
  public String getName(){
      return this.Name;
  }
}
