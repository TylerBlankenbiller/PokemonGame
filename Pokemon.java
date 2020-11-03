//Pokemon Factory
import java.lang.Math;

public class Pokemon()
{
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
    public Pokemon(String PokemonName){
        if(PokemonName == null){
            System.out.println('Please Specify a Pokemon');
            return null;
        }
        if(PokemonName.equalsIgnoreCase('Venusaur'){
            String[] Attacks = {''}
            setStats(50, 6, 6, 'Grass', Attacks);
        }
        else if(PokemonName.equalsIgnoreCase('Charizard'){
            String[] Attacks = {''}
            setStats(50, 7, 5, 'Fire', Attacks);
        }
        else if(PokemonName.equalsIgnoreCase('Blastoise'){
            String[] Attacks = {''}
            setStats(50, 5, 7, 'Water', Attacks);
        }
        else if(PokemonName.equalsIgnoreCase('Butterfree'){
            String[] Attacks = {''}
            setStats(40, 6, 5, 'Bug', Attacks);
        }
        else if(PokemonName.equalsIgnoreCase('Beedrill'){
            String[] Attacks = {''}
            setStats(40, 6, 5, 'Bug', Attacks);
        }
        else if(PokemonName.equalsIgnoreCase('Pidgeot'){
            String[] Attacks = {''}
            setStats(50, 5, 5, 'Flying', Attacks);
        }
        else if(PokemonName.equalsIgnoreCase('Raticate'){
            String[] Attacks = {''}
            setStats(40, 5, 5, 'Normal', Attacks);
        }
        else if(PokemonName.equalsIgnoreCase('Fearow'){
            String[] Attacks = {''}
            setStats(40, 6, 4, 'Flying', Attacks);
        }
        else if(PokemonName.equalsIgnoreCase('Arbok'){
            String[] Attacks = {''}
            setStats(40, 6, 5, 'Poison', Attacks);
        }
        else if(PokemonName.equalsIgnoreCase('Pikachu'){
            String[] attacks = {''}
            setStats(40, 6, 5, 'Electric', Attacks);
        }
        else{
            System.out.println("Sorry we don't support that Pokemon");
            return null;
        }
    }
    
    void SetStats(int myHP, float myOffenseStatus, float myDefenseStatus, String myType, String myAttacks[]){
        HP = myHP;
        OffenseStatus = myOffenseStatus;
        DefenseStatus = myDefenseStatus;
        for(int i = 0; i < 4; i++){
            OffenseList[i] = myOffenseStatus * (0.4 + (i * 0.15));
            DefenseList[i] = myDefenseStatus * (0.4 + (i * 0.15));
        }
        for(int i = 5; i < 9; i++){
            OffenseList[i] = myOffenseStatus * (1 + ((i-4)*0.15));
            DefenseList[i] = myDefenseStatus * (1 + ((i-4)*0.15));
        }
        OffensePointer = 4;
        DefensePointer = 4;
        Type = myType;
        for(int i = 0; i< myAttacks.length; i++){
            Attacks[i] = Attack(myAttacks[i]);
        }
    }
    
    //Check if HP is less than equal to 0 (fainted, not usable in battle)
    boolean IsFainted(){
        if(HP > 0)
            return false;
        return true;
    }
    
    //Set Functions
    void SetOffenseStatus(int statusChange){
        OffensePointer += statusChange;
        OffenseStatus = OffenseList[OffensePointer];
    }
    
    void SetDefenseStatus(int statusChange){
        DefensePointer += statusChange;
        DefenseStatus = DefenseList[DefensePointer];
    }
    
    int SetHP(int damage, float offense){
        int applyDamage = Math.round((offense * damage) / DefenseStatus);
        HP -= applyDamage;
        return applyDamage;
    }
    
    //Get Functions
    float GetOffenseStatus(){
        return OffenseStatus;
    }
    float GetDefenseStatus(){
        return DefenseStatus;
    }
    int GetHP(){
        return HP;
    }
    
}

