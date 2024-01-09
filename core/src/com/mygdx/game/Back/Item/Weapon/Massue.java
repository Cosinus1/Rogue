package com.mygdx.game.Back.Item.Weapon;

import com.mygdx.game.Back.Item.ItemType;

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
        
        GraphicHero graphicHero = map.getHero();
        float Xh = graphicHero.getX();
        float Yh = graphicHero.getY();
        float distanceX = Math.abs(Xh-X);
        float distanceY = Math.abs(Yh-Y);
        float distance = distanceX + distanceY;
        if(distance<= range){
            graphicHero.getCharacter().recevoirDegats(this.power);
        }

    }
}
