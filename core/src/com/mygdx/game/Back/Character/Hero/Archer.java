package com.mygdx.game.Back.Character.Hero;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.ItemType;
import com.mygdx.game.Back.Item.Arme.*;
import com.mygdx.game.Graphic.World.Map.*;;


public class Archer extends Hero{
    private Arc arc ;

    public Archer(int pv, int defense, int power,int range, Inventory bag, String name){
        super(pv, defense, power, range, bag, name);
        this.Class = "hero";
        createArc();
    }

    public void createArc(){
        Arc arc = new Arc(ItemType.WEAPON, "arc", 50);
        this.arc = arc;
    }

    public void Attack(float X, float Y, Map map){
        arc.Attack(X, Y, map);
    }

}
