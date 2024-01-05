package com.mygdx.game.Back.Item.Arme;

import com.mygdx.game.Back.Item.Item;
import com.mygdx.game.Back.Item.ItemType;

public class Weapon extends Item {
    protected String name;
    protected int power;
    protected int range;

    public Weapon(ItemType t, String name, int power, int range){
        super(t);
        this.name=name;
        this.power=power;
        this.range=range;
    }

    public void description(){
        System.out.println("Je suis une arme");
    }

    public int getPower(){
        return power;
    }

    public int getRange(){
        return range;
    }


}
