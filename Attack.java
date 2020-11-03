//Attack Class
public class Attack()
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
        if(AttackName == null){
            System.out.println('Please Specify an Attack');
            return null;
        }
        Name = AttackName;
        if(AttackName.equalsIgnoreCase('Double-Edge'){
            setStats([120, 40], [0, 0], ['Opponent', 'User']);
        }
        else if(AttackName.equalsIgnoreCase('Syntgesis'){
            setStats([-50], [0], ['User']);
        }
        else if(AttackName.equalsIgnoreCase('Seed Bomb'){
            setStats([80], [0], ['Opponent']);
        }
        else if(AttackName.equalsIgnoreCase('Growth'){
            setStats([0], [1], ['UserOffense']);
        }
        else if(AttackName.equalsIgnoreCase('Fire Blitz'){
            setStats([120, 40], [0, 0], ['Opponent', 'User']);
        }
        else if(AttackName.equalsIgnoreCase('Slash'){
            setStats([70], [0], ['Opponent']);
        }
        else if(AttackName.equalsIgnoreCase('Flamethrower'){
            setStats([90, 0], [0, -1], ['Opponent', 'OpponentOffense']);
        }
        else if(AttackName.equalsIgnoreCase('Fire Breath'){
            setStats([60, 0], [0, -1], ['Opponent', 'OpponentOffense']);
        }
        else{
            System.out.println("Sorry we don't support that Move");
            return null;
        }
    }
    
    void setStats(int[] damage, int[] magnitude, String[] target){
        
    }
    
}
