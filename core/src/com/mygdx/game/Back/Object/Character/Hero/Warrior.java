package com.mygdx.game.Back.Object.Character.Hero;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.Weapon.*;
import com.mygdx.game.Back.World.Map.Map;


public class Warrior extends Hero{
    private Sword sword ;

    public Warrior(float x, float y, int pv, int defense, int power,int range, Inventory bag){
        super(x, y, pv, defense, power, range, bag);
        setName("warrior");
        this.Class = "warrior";
        createSword();
    }

    public void createSword(){

        Sword sword = new Sword("sword", 20, 3);
        this.sword = sword;
    }


    public void changeWeapon(Sword newSword){
        System.out.println("L'ancienne arme était :");
        sword.presenter();
        bag.addItem(sword);
        this.sword = newSword;
        bag.removeItem(newSword);
        System.out.println("L'a nouvelle arme equipé est : ");
        sword.presenter();
    }
    public void Attack(Map map){
        if(attackTimer > attackCooldown){
            sword.Attack(this, map);
            graphicObject.setBattleTexture();
            attackTimer = 0;
        }
    }


}
