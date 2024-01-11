package com.mygdx.game.Back.Object.Character.Ennemie;

import com.mygdx.game.Back.Inventory.*;
import com.mygdx.game.Back.Item.Weapon.*;

public class Gobelin extends Ennemie{

    public Gobelin(float x, float y, int pv, int defense, int power,int combatRange, int detectionRange, Inventory bag, Weapon weapon){
        super(x, y, pv, defense, power, combatRange, detectionRange, bag, weapon);
        this.setName("gobelin");
        this.Class = "gobelin";
    }

    public int attaquer(){
        System.out.println("Grrrrr");
        return power + weapon.getPower();
    }
    
}
