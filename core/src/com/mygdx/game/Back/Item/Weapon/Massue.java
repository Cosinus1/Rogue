package com.mygdx.game.Back.Item.Weapon;

import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Back.Object.Character.Hero.Hero;
import com.mygdx.game.Back.World.Map;

public class Massue extends Weapon{
    private int range;

    public Massue(String name, int power, int range){
        super(name, power);
        this.range = range;
    }

    public int getRange(){
        return this.range;
    }
    /*---------------------------ATTACK-------------------------- */
    public void Attack(Object object, Map map){
        
        Hero Hero = map.getHero();
        float Xh = Hero.getX();
        float Yh = Hero.getY();
        float distanceX = Math.abs(Xh-object.getX());
        float distanceY = Math.abs(Yh-object.getY());
        float distance = distanceX + distanceY;
        if(distance<= (range+1)*map.getTilewidth()){
            Hero.recevoirDegats(this.power);
        }

    }
}
