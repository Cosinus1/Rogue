package com.mygdx.game.Back.Item.Weapon;

import java.util.ArrayList;


import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Back.Object.Character.Character;
import com.mygdx.game.Back.Object.Character.Ennemie.Ennemie;
import com.mygdx.game.Back.World.Map.Map;

public class Sword extends Weapon{
    private int range;

    public Sword(String name, int power, int range){
        super(name, power);
        this.range = range;
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

    public void presenter(){
        System.out.println(name + power);
    }

}
