package com.mygdx.game.Back.Character.Ennemie;

import java.util.ArrayList;
import java.util.Random;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.Weapon.Weapon;
import com.mygdx.game.Graphic.World.Map.Map;

public class Boss extends Ennemie{
    ArrayList<String> Names = new ArrayList<>();
    
    public Boss(int pv, int defense, int power,int combatRange, int detectionRange, Inventory bag,String name , Weapon weapon){
        super(pv, defense, power, combatRange, detectionRange, bag, name, weapon);
        this.Names.add("vampire");
        this.Names.add("jack");
        this.Names.add("minotaure");
        Random random = new Random();
        String chosen = (Names.get(random.nextInt(Names.size())));
        this.Class = chosen;   
        this.name = chosen;     
    }
        public void Attack(float X, float Y, int OrX, int OrY, Map map){
        weapon.Attack(X, Y, OrX, OrY, map);
    }

}
