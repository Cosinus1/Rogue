package com.mygdx.game.Back.Object.Character.Hero;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Object.Character.Character;


public class Hero extends Character{
    protected int exp;
    

    public Hero(int pv, int defense, int power,int range, Inventory bag, String name){
        super(pv, defense, power, range, bag, name);
        this.exp = 0;       
    }
}
