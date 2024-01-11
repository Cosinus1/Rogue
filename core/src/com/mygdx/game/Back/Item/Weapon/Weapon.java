package com.mygdx.game.Back.Item.Weapon;

import com.mygdx.game.Back.Item.Item;
import com.mygdx.game.Back.Item.ItemType;
import com.mygdx.game.Back.World.Map.Map;
import com.mygdx.game.Back.Object.Object;

public class Weapon extends Item {
    protected int power;

    public Weapon(String name, int power){
        super(ItemType.WEAPON,name);
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
    public void Attack(Object object, Map map){
        
    }
}
