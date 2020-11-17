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
          "Vine Whip", 
          "Razer Leaf", 
          "Seed Bomb", 
          "Leaf Storm"
          "Fire Fang", 
          "Flamethrower", 
          "Fire Blast", 
          "Dragon Claw"
          "Waterfall", 
          "Ice Punch", 
          "Hydro Pump", 
          "Water Pulse"
          "Gust", 
          "Bug Bite", 
          "Air Slash", 
          "Tackle"
          "Double-Edge", 
          "Fury Attack", 
          "Pin Missile", 
          "Take Down"
          "Quick Attack", 
          "Wing Attack",
          "Crunch",
          "Aerial Ace", 
          "Pluck", 
          "Drill Peck"
          "Poison Sting", 
          "Acid Spray", 
          "Bite"
          "Thunder Shock", "Thunderbolt", "Tail Whip", "Thunder Punch"
          return null;
      }*/
      Name = AttackName;
      if(AttackName.equalsIgnoreCase("Vine Whip") || AttackName.equalsIgnoreCase("Gust") || AttackName.equalsIgnoreCase("Quick Attack")){
    	  this.damage = 25;
          //setStats(new int[]{20, 40}, new int[]{0, 0}, new String[]{"Opponent", "User"});
      }
      else if(AttackName.equalsIgnoreCase("Razer Leaf") || AttackName.equalsIgnoreCase("Bug Bite") || AttackName.equalsIgnoreCase("Tail Whip")){
    	  this.damage = 20;
          //setStats(new int[]{-50}, new int[]{0}, new String[]{"User"});
      }
      else if(AttackName.equalsIgnoreCase("Seed Bomb") || AttackName.equalsIgnoreCase("Air Slash") || AttackName.equalsIgnoreCase("Poison Sting")){
    	  this.damage = 15;
          //setStats(new int[]{80}, new int[]{0}, new String[]{"Opponent"});
      }
      else if(AttackName.equalsIgnoreCase("Leaf Storm") || AttackName.equalsIgnoreCase("Tackle") || AttackName.equalsIgnoreCase("Acid Spray")){
    	  this.damage = 23;
          //setStats(new int[]{0}, new int[]{1}, new String[]{"UserOffense"});
      }
      else if(AttackName.equalsIgnoreCase("Fire Fang") || AttackName.equalsIgnoreCase("Pluck") || AttackName.equalsIgnoreCase("Thunder Punch")){
    	  this.damage = 18;
          //setStats(new int[]{120, 40}, new int[]{0, 0}, new String[]{"Opponent", "User"});
      }
      else if(AttackName.equalsIgnoreCase("Flamethrower") || AttackName.equalsIgnoreCase("Waterfall") || AttackName.equalsIgnoreCase("Thunderbolt")){
    	  this.damage = 26;
          //setStats(new int[]{70}, new int[]{0}, new String[]{"Opponent"});
      }
      else if(AttackName.equalsIgnoreCase("Fire Blast") || AttackName.equalsIgnoreCase("Hydro Pump") || AttackName.equalsIgnoreCase("Thunder Shock")){
    	  this.damage = 27;
          //setStats(new int[]{90, 0}, new int[]{0, -1}, new String[]{"Opponent", "OpponentOffense"});
      }
      else if(AttackName.equalsIgnoreCase("Dragon Claw") || AttackName.equalsIgnoreCase("Double-Edge") || AttackName.equalsIgnoreCase("Ice Punch")){
    	  this.damage = 21;
          //setStats(new int[]{60, 0}, new int[]{0, -1}, new String[]{"Opponent", "OpponentOffense"});
      }
      else if(AttackName.equalsIgnoreCase("Crunch") || AttackName.equalsIgnoreCase("Fury Attack") || AttackName.equalsIgnoreCase("Drill Peck")){
    	  this.damage = 17;
          //setStats(new int[]{60, 0}, new int[]{0, -1}, new String[]{"Opponent", "OpponentOffense"});
      }
      else if(AttackName.equalsIgnoreCase("Pin Missile") || AttackName.equalsIgnoreCase("Wing Attack") || AttackName.equalsIgnoreCase("Aerial Ace")){
    	  this.damage = 24;
          //setStats(new int[]{60, 0}, new int[]{0, -1}, new String[]{"Opponent", "OpponentOffense"});
      }
      else if(AttackName.equalsIgnoreCase("Water Pulse") || AttackName.equalsIgnoreCase("Take Down") || AttackName.equalsIgnoreCase("Bite")){
    	  this.damage = 16;
          //setStats(new int[]{60, 0}, new int[]{0, -1}, new String[]{"Opponent", "OpponentOffense"});
      }
      
  }
  
  void setStats(int[] damage, int[] magnitude, String[] target){
      
  }
  
  public float applyAttack(Pokemon target){
	  float totalDamage = target.setHP(this.damage, target.getOffenseStatus());
      return totalDamage;
  }
  
  public String getName(){
      return this.Name;
  }
}
