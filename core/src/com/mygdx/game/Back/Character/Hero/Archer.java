package com.mygdx.game.Back.Character.Hero;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.Arme.*;
import com.mygdx.game.Graphic.World.World;


public class Archer extends Hero{
    private Arc arc ;

    public Archer(World world, float x, float y, String name, int pv, int defense, int power,int range, Inventory bag, int exp, Arc arc){
        super(world, pv, defense, power, range, bag,name, exp);
        this.arc = arc;
    }

    //renvoie les degats que fait l'attaque
    public int attaquer(){
        System.out.println("Je tire une flèche !");
        return power + arc.getPower();
    }

}
