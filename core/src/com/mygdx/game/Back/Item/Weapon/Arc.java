package com.mygdx.game.Back.Item.Weapon;

import com.mygdx.game.Back.Force;
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
    public void Attack(float X, float Y, int OrX, int OrY, Map map){
        Element arrow = factory.createProjectile(X, Y);
        arrow.applyForce(new Force(OrX*2000, OrY*2000));
        map.addElement(arrow);
    }
}
