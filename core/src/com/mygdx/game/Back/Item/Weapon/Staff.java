package com.mygdx.game.Back.Item.Weapon;

import com.mygdx.game.Back.Object.Force;
import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Back.Object.Element.Element;
import com.mygdx.game.Back.Object.Element.ElementFactory;
import com.mygdx.game.Back.World.Map;

public class Staff extends Weapon {
    private ElementFactory factory;
    private int range;
    public Staff(String name, int power, int range){
        super(name, power);
        this.range = range;
        this.factory = new ElementFactory();
    }


    public int getRange(){
        return this.range;
    }
    /*------------------------------ATTACK------------------------------------- */
    public void Attack(Object object, Map map){
        float X1 = object.getX();
        float Y1 = object.getY();
        float X2 = map.getHero().getX();
        float Y2 = map.getHero().getY();
        Element fireball = factory.createProjectile(X1, Y1);
        fireball.setTarget("hero");
        fireball.applyForce(new Force(200,200, X2-X1, Y2-Y1));
        map.addElement(fireball);
    }
}
