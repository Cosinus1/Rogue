package com.mygdx.game.Back.Character;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.Booster;
import com.mygdx.game.Back.Item.Potion;
import com.mygdx.game.Graphic.World.Map.Map;


public abstract class Character {
    
    protected int PV;
    protected int PV_max;
    protected int defense;
    protected int power;
    protected int range;
    protected Inventory bag;
    protected String name;
   


    //constructeur
    public Character(int pv, int defense, int power,int range, Inventory bag, String name){
        this.PV = pv;
        this.PV_max = pv;
        this.defense = defense;
        this.power = power;
        this.range = range*32;
        this.bag = bag;
        this.name = name;
    }

/*----------------------------------------- GETTERS -------------------------------------- */
    public int getDefense(){
        return defense;
    }
    public int getPV(){
        return PV;
    }
    public int getPV_max(){
        return PV_max;
    }
    public int getRange(){
        return this.range;
    }
    public Inventory getBag(){
        return bag;
    }
    public String getName(){
        return name;
    }
    public int getPower(){
        return power;
    }
    /*----------------------------------------- SETTERS -------------------------------------- */
    public void setName(String name){
        this.name = name;
    }

/*----------------------------------------- ITEM -------------------------------------- */  

    public void usePotion(Potion potion){
        //test de la présencce dans le sac 

        PV = potion.getPvSoigner();
        if(PV > PV_max){
            PV = PV_max;
        }
        // Remove potion du sac
    }

    public void useBooster(Booster boost){
        //test de la présence dans le sac
        defense += boost.getBoostDef();
        power += boost.getBoostDam();
        //range += boost.getBoostRange();
        //remove booster du sac;
    }
    
/*----------------------------------------- FIGHT -------------------------------------- */  

    public boolean recevoirDegats(int degats) {
        double mitigationFactor = 1 / (1 + Math.log(1+defense)); // Adjust the exponent value as needed
        
        // Calculate mitigated damage
        int mitigatedDamage = (int) (degats * mitigationFactor);
    
        PV -= mitigatedDamage;
    
        if (PV <= 0) {
            return true; // Character is defeated
        } else {
            return false; // Character survives
        }
    }

     public void killHero(Map map){
        System.out.println("You Died");
        this.PV = 100;
    }
}
