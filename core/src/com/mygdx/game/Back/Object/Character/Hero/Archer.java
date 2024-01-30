package com.mygdx.game.Back.Object.Character.Hero;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.Weapon.*;
import com.mygdx.game.Back.World.Map;


public class Archer extends Hero{
    private Arc arc ;

    public Archer(float x, float y, int pv, int defense, int power,int range, Inventory bag){
        super(x, y, pv, defense, power, range, bag);
        setName("archer");
        this.Class = "archer";
        createArc();
    }

    public void createArc(){
        Arc arc = new Arc("arc", 50);

        this.arc = arc;
    }

    public void Attack(Map map){
        if(attackTimer > attackCooldown){
            arc.Attack(this, map);
            graphicObject.setBattleTexture();
            attackTimer = 0;
        }
    }

    public void changeWeapon(Arc newArc){
        System.out.println("L'ancienne arme était :");
        arc.presenter();
        bag.addItem(arc);
        this.arc = newArc;
        bag.removeItem(newArc);
        System.out.println("L'a nouvelle arme equipé est : ");
        arc.presenter();
    }

}
