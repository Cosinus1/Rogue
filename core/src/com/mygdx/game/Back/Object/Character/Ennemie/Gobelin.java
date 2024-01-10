package com.mygdx.game.Back.Object.Character.Ennemie;

import com.mygdx.game.Back.Inventory.*;
import com.mygdx.game.Back.Item.Weapon.*;
import com.mygdx.game.Graphic.World.Map.Map;

public class Gobelin extends Ennemie{

    public Gobelin(int pv, int defense, int power,int combatRange, int detectionRange, Inventory bag, String name, Weapon weapon){
        super(pv, defense, power, combatRange, detectionRange, bag, name, weapon);
        this.setName("gobelin");
        this.Class = "gobelin";
    }

    public int attaquer(){
        System.out.println("Grrrrr");
        return power + weapon.getPower();
    }

    public void Attack(float X, float Y, int OrX, int OrY, Map map){
        weapon.Attack(X, Y, OrX, OrY, map);
    }
    
}
