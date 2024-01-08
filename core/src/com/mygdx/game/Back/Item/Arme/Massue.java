package com.mygdx.game.Back.Item.Arme;

import com.mygdx.game.Back.Item.ItemType;

public class Massue extends Weapon{
    private int range;

    public Massue(ItemType WEAPON, String name, int power, int range){
        super(null, name, power);
        this.range = range;
    }

    public String getName(){
        return name;
    }
    public int getRange(){
        return this.range;
    }
    /*---------------------------ATTACK-------------------------- */
    public void Attack(){

    }
}
