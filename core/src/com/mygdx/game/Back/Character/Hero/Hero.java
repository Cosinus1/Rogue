package com.mygdx.game.Back.Character.Hero;

import com.mygdx.game.Graphic.World.World;
import com.mygdx.game.Back.Character.Character;
import com.mygdx.game.Back.Inventory.Inventory;


public class Hero extends Character{
    protected int exp;
    

    public Hero(World world, int pv, int defense, int power,int range, Inventory bag, String name){
        super(pv, defense, power, range, bag, name);
        this.exp = 0;       
    }
}
