package com.mygdx.game.Back.Item.Weapon;

import com.mygdx.game.Back.Force;
import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Back.Object.Element.Element;
import com.mygdx.game.Back.Object.Element.ElementFactory;
import com.mygdx.game.Back.World.Map.Map;

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
        Element fireball = factory.createProjectile(object.getX(), object.getY());
        fireball.setTarget("hero");
        fireball.applyForce(new Force(20000,20000, object.getorX(), object.getorY()));
        map.addElement(fireball);
    }
}
