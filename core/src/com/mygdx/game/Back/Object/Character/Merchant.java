package com.mygdx.game.Back.Object.Character;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.*;
import com.mygdx.game.Back.Item.Weapon.*;
import com.mygdx.game.Back.Object.Character.Hero.Archer;
import com.mygdx.game.Back.Object.Character.Hero.Hero;
import com.mygdx.game.Back.World.Map;
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
        this.dialogue = "Ah, welcome adventurer, welcome!\nIn my humble shops, you'll find everything a daring hero like yourself could desire.\nSharpened swords, magical arc, and of course, the most potent potions\nto heal your wounds and enhance your powers!";
    }

    public String getDialogue(){
        return dialogue;
    }
    public Inventory getInventory(){
        return inventory;
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
        potion1.setValue(10);
        Potion potion2 = new Potion(25, "Medium Elixir of Life");
        potion2.setValue(25);
        Potion potion3 = new Potion(60, "Grand draught of Immortality");
        potion3.setValue(50);
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
        sword1.setValue(20);
        sword2.setValue(60);
        sword3.setValue(60);
        sword4.setValue(120);
        inventory.addItem(sword1);
        inventory.addItem(sword2);
        inventory.addItem(sword3);
        inventory.addItem(sword4);
    }

    public void setArcherWeapon(){
        Arc arc1 = new Arc("Shadowstalker Arc", 50);
        Arc arc2 = new Arc("Mystic Moonshadow Bow",65);
        Arc arc3 = new Arc("Thunderstorm LongBow",80);
        Arc arc4 = new Arc("Bow of the Astral Gale",100);
        arc1.setValue(20);
        arc2.setValue(60);
        arc3.setValue(60);
        arc4.setValue(120);
        inventory.addItem(arc1);
        inventory.addItem(arc2);
        inventory.addItem(arc3);
        inventory.addItem(arc4);
    }



}
