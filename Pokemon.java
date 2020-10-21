//Pokemon Factory

public class Pokemon()
{
    int HP;
    float OffenseStatus;
    float DefenseStatus;
    String Type;
    Attack Attacks[];
    
    /*
        Constructor
        PokemonName - Name of Pokemon Object to create
        Creates specific Pokemon Object based on string arugment
    */
    public Pokemon(String PokemonName){
        if(PokemonName == null){
            System.out.println('Please Specify a Pokemon');
            return null;
        }
        if(PokemonName.equalsIgnoreCase('Venusaur'){
            return new Venusaur();
        }
        else if(PokemonName.equalsIgnoreCase('Charizard'){
            return new Charizard();
        }
        else if(PokemonName.equalsIgnoreCase('Blastoise'){
            return new Blastoise();
        }
        else if(PokemonName.equalsIgnoreCase('Pikachu'){
            return new Pikachu();
        }
        else{
            System.out.println("Sorry we don't support that Pokemon");
            return null;
        }
    }
    
    //Check if HP is less than equal to 0 (fainted, not usable in battle)
    boolean IsFainted(){
        if(HP > 0)
            return false;
        return true;
    }
    
    void setOffensiveStatus(float statusChange){
        OffenseStatus
    }
    
}