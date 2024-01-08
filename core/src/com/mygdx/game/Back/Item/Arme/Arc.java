package com.mygdx.game.Back.Item.Arme;

import com.mygdx.game.Back.Item.ItemType;
import com.mygdx.game.Graphic.GraphicObject.Elements.ElementFactory;
import com.mygdx.game.Graphic.World.Map.Map;
import com.mygdx.game.Graphic.GraphicObject.Elements.Element;

public class Arc extends Weapon {
    private ElementFactory factory;
    public Arc(ItemType t, String name, int power){
        super(t, name, power);
        this.factory = new ElementFactory();
    }

    public void presenter(){
        System.out.println("Je suis un arc");
    }

    public String getName(){
        return name;
    }
    /*------------------------------ATTACK------------------------------------- */
    public void Attack(float X, float Y, Map map){
        Element arrow = factory.createProjectile(X, Y);
        map.addElement(arrow);
    }
}
