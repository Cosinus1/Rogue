package com.mygdx.game.Back.Item.Weapon;

import java.util.ArrayList;

import com.mygdx.game.Back.Item.ItemType;
import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Back.Object.Character.Character;
import com.mygdx.game.Back.Object.Character.Ennemie.Ennemie;
import com.mygdx.game.Back.World.Map.Map;

public class Sword extends Weapon{
    private int range;

    public Sword(ItemType WEAPON, String name, int power, int range){
        super(null, name, power);
        this.range = range;
    }

    public String getName(){
        return name;
    }
    public int getRange(){
        return this.range;
    }
    /*---------------------------ATTACK-------------------------- */
    public void Attack(Object object, Map map){
        
        ArrayList<Ennemie> PNJinRange = map.lookforEnemyinRange((Character) object);
        if(PNJinRange != null){
            int size = PNJinRange.size();
            for(int index = 0; index<size; index++){
                Ennemie ennemie = PNJinRange.get(index);
                ennemie.recevoirDegats(this.power);
            }
        }

    }
}
