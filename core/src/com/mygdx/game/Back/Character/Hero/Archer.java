package com.mygdx.game.Back.Character.Hero;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.ItemType;
import com.mygdx.game.Back.Item.Arme.*;
import com.mygdx.game.Graphic.World.World;


public class Archer extends Hero{
    private Arc arc ;

    public Archer(World world, int pv, int defense, int power,int range, Inventory bag, String name){
        super(world, pv, defense, power, range, bag, name);
        this.Class = "hero";
        createArc();
    }

    //renvoie les degats que fait l'attaque
    public int attaquer(){
        System.out.println("Je tire une flèche !");
        return power + arc.getPower();
    }

    public void createArc(){
        Arc arc = new Arc(ItemType.WEAPON, "arc", 50, 5);
        this.arc = arc;
    }

}
