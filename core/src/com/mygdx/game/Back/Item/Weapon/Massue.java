package com.mygdx.game.Back.Item.Weapon;

import java.util.ArrayList;

import com.mygdx.game.Back.Item.ItemType;
import com.mygdx.game.Back.Character.Character;

import com.mygdx.game.Graphic.World.Map.Map;
import com.mygdx.game.Graphic.GraphicObject.GraphicCharacter.*;;

public class Massue extends Weapon{
    private int range;

    public Massue(ItemType WEAPON, String name, int power, int range){
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
    public void Attack(float X, float Y, Map map){
        
        ArrayList<GraphicEnnemie> PNJinRange = map.lookforEnemyinRange(X, Y);
        if(PNJinRange != null){
            int size = PNJinRange.size();
            for(int index = 0; index<size; index++){
                GraphicEnnemie Graphic_ennemie = PNJinRange.get(index);
                Character ennemie = Graphic_ennemie.getCharacter();
                ennemie.recevoirDegats(this.power);
            }
        }

    }
}
