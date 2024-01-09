package com.mygdx.game.Back.Character.Hero;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.ItemType;
import com.mygdx.game.Back.Item.Weapon.*;
import com.mygdx.game.Graphic.World.Map.*;;


public class Warrior extends Hero{
    private Sword sword ;

    public Warrior(int pv, int defense, int power,int range, Inventory bag, String name){
        super(pv, defense, power, range, bag, name);
        this.Class = "warrior";
        createSword();
    }

    public void createSword(){
        Sword sword = new Sword(ItemType.WEAPON, "sword", 50, 3);
        this.sword = sword;
    }

    public void Attack(float X, float Y, int OrX, int OrY, Map map){
        sword.Attack(X, Y, map);
    }

}
