package com.mygdx.game.Back.Item.Weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
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
    /*--------------------------FIREBALL TEXTURE------------------------------- */
    /*------------------------------ARROW TEXTURE-------------------------------- */
    public TextureMapObject Fireball(int OrX, int OrY){
        Texture texture = new Texture("PNG/fireball16.png");
        TextureRegion region = new TextureRegion(texture);
        boolean boolX = false;
        boolean boolY = false;
        if(OrX == -1) boolX = true;
        region.flip(boolX, boolY);
        TextureMapObject object = new TextureMapObject(region);
        object.setRotation(1.5f);
        return object;
    }
    /*------------------------------ATTACK------------------------------------- */
    public void Attack(Object object, Map map){
        float X1 = object.getX();
        float Y1 = object.getY();
        float X2 = map.getHero().getX();
        float Y2 = map.getHero().getY();
        Element fireball = factory.createProjectile(X1, Y1);
        fireball.setTarget("hero");
        fireball.applyForce(new Force(200,200, X2-X1, Y2-Y1, 1.01f));
        fireball.setTextureObject(Fireball(object.getorX(), object.getorY()));
        map.addElement(fireball);
    }
}
