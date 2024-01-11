package com.mygdx.game.Back.Object.Character.Hero;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.ItemType;
import com.mygdx.game.Back.Item.Weapon.*;
import com.mygdx.game.Back.World.Map.*;;


public class Warrior extends Hero{
    private Sword sword ;

    public Warrior(float x, float y, int pv, int defense, int power,int range, Inventory bag){
        super(x, y, pv, defense, power, range, bag);
        setName("warrior");
        this.Class = "warrior";
        createSword();
    }

    public void createSword(){
        Sword sword = new Sword(ItemType.WEAPON, "sword", 50, 3);
        this.sword = sword;
    }

    public void Attack(Map map){
        sword.Attack(this, map);
        graphicObject.setBattleTexture();
    }

}
