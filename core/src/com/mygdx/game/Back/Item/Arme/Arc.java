package com.mygdx.game.Back.Item.Arme;

import com.mygdx.game.Back.Item.ItemType;

public class Arc extends Weapon {
    public Arc(ItemType t, String name, int power, int range){
        super(t, name, power, range);
    }

    public void presenter(){
        System.out.println("Je suis un arc");
    }

    public String getName(){
        return name;
    }
}