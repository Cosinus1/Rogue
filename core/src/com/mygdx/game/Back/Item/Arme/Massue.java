package com.mygdx.game.Back.Item.Arme;

import com.mygdx.game.Back.Item.ItemType;

public class Massue extends Weapon{
    public Massue(ItemType WEAPON, String name, int power, int range){
        super(null, name,power, range);
    }

    public String getName(){
        return name;
    }
}
