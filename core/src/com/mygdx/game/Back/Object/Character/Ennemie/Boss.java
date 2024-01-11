package com.mygdx.game.Back.Object.Character.Ennemie;

import java.util.ArrayList;
import java.util.Random;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.Weapon.Weapon;
import com.mygdx.game.Back.World.Map.Map;

public class Boss extends Ennemie{
    ArrayList<String> Names = new ArrayList<>();
    
    public Boss(float x, float y, int pv, int defense, int power,int combatRange, int detectionRange, Inventory bag, Weapon weapon){
        super(x, y , pv, defense, power, combatRange, detectionRange, bag, weapon);
        this.Hitbox.width *= 2;
        this.Hitbox.height *= 2;
        this.Names.add("vampire");
        this.Names.add("jack");
        this.Names.add("minotaure");
        Random random = new Random();
        String chosen = (Names.get(random.nextInt(Names.size())));
        this.Class = chosen;   
        setName(chosen);     
    }
        public void Attack(Map map){
        weapon.Attack(this, map);
    }

}
