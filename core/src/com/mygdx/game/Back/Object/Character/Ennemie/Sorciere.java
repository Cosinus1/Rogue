package com.mygdx.game.Back.Object.Character.Ennemie;

import com.mygdx.game.Back.Inventory.*;
import com.mygdx.game.Back.Item.Weapon.*;

public class Sorciere extends Ennemie{

    public Sorciere(float x, float y, int pv, int defense, int power,int combatRange, int detectionRange, Inventory bag, Weapon weapon){
        super(x, y, pv, defense, power, combatRange, detectionRange, bag, weapon);
        this.setName("sorciere");
        this.Class = "sorciere";
        
    }

    public int attaquer(){
        System.out.println("Abracadabra");
        return power + weapon.getPower();
    }
    
}