package com.mygdx.game.Back.Item.Weapon;

import com.mygdx.game.Back.Item.Item;
import com.mygdx.game.Back.Item.ItemType;
import com.mygdx.game.Graphic.World.Map.Map;

public class Weapon extends Item {
    protected String name;
    protected int power;

    public Weapon(ItemType t, String name, int power){
        super(t);
        this.name=name;
        this.power=power;
    }

    public void description(){
        System.out.println("Je suis une arme");
    }
    /*---------------------------------------GETTERS------------------------------------- */
    public int getPower(){
        return power;
    }

    public int getRange(){
        return 0;
    }

    /*----------------------------------------ATTACK-------------------------------- */
    public void Attack(float X, float Y, int OrX, int OrY, Map map){
        
    }
}
