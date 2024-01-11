package com.mygdx.game.Back.Object.Character;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.*;
import com.mygdx.game.Back.Item.Weapon.*;
import com.mygdx.game.Back.Object.Character.Hero.Archer;
import com.mygdx.game.Back.Object.Character.Hero.Hero;
import com.mygdx.game.Back.World.Map.Map;
import com.mygdx.game.Graphic.GraphicObject.GraphicCharacter.GraphicMerchant;
import com.mygdx.game.Back.Object.Object;

public class Merchant extends Object{
    private Inventory inventory;
    private String dialogue;

    public Merchant(Hero hero, float x, float y, Map map){
        super(x,y,32,32);
        inventory = new Inventory();
        setGraphicObject(new GraphicMerchant("merchant", map));
        if(hero instanceof Archer){
            setArcherMerchant();
        }
        else{
            setWarriorMerchant();
        }
        this.dialogue = "Ah, welcome adventurer, welcome!\nIn my humble shops, you'll find everything a daring hero like yourself could desire.\n Sharpened swords, magical arc, and of course, the most potent potions to heal your wounds and enhance your powers!";
    }


    public void spawn(Map map){
        map.setMerchant(this);
    }

    public void setWarriorMerchant(){
        setPotion();
        setWarriorWeapon();
    }

    public void setArcherMerchant(){
        setPotion();
        setArcherWeapon();
    }

    //Init potion to send
    public void setPotion(){
        Potion potion1 = new Potion(10, "Litlle potion of Vitality");
        Potion potion2 = new Potion(25, "Medium Elixir of Life");
        Potion potion3 = new Potion(60, "Grand draught of Immortality");
        inventory.addItem(potion1);
        inventory.addItem(potion2);
        inventory.addItem(potion3);
    }

    //Init Sword to send
    public void setWarriorWeapon(){
        Sword sword1 = new Sword("Dagger of Fading Shadows", 30,1);
        Sword sword2 = new Sword("Mystic Gladeblade",40,6);
        Sword sword3 = new Sword("Thunderstrike Longsword",60,3);
        Sword sword4 = new Sword("Blade of the Eternal Dawn",100,6);
        inventory.addItem(sword1);
        inventory.addItem(sword2);
        inventory.addItem(sword3);
        inventory.addItem(sword4);
    }

    public void setArcherWeapon(){
        Arc arc1 = new Arc("Shadowstalker Arc", 25);
        Arc arc2 = new Arc("Mystic Moonshadow Bow",35);
        Arc arc3 = new Arc("Thunderstorm LongBow",50);
        Arc arc4 = new Arc("Bow of the Astral Gale",80);
        inventory.addItem(arc1);
        inventory.addItem(arc2);
        inventory.addItem(arc3);
        inventory.addItem(arc4);
    }



}
