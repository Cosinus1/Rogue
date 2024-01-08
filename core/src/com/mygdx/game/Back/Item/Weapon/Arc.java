package com.mygdx.game.Back.Item.Weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
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
    /*------------------------------ARROW TEXTURE-------------------------------- */
    public TextureMapObject Arrow(int OrX, int OrY){
        Texture texture = new Texture("PNG/arrow64.png");
        TextureRegion region = new TextureRegion(texture);
        boolean boolX = false;
        boolean boolY = false;
        if(OrX == -1) boolX = true;
        region.flip(boolX, boolY);
        TextureMapObject object = new TextureMapObject(region);
        object.setRotation(1.5f);
        System.out.println(object.getRotation());
        return object;
    }
    /*------------------------------ATTACK------------------------------------- */
    public void Attack(float X, float Y, int OrX, int OrY, Map map){
        Element arrow = factory.createProjectile(X, Y);
        arrow.applyForce(new Force(OrX*2000, OrY*2000));
        arrow.setObject(Arrow(OrX, OrY));
        map.addElement(arrow);
    }
}
