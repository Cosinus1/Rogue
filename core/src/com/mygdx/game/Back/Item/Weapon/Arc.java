package com.mygdx.game.Back.Item.Weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.mygdx.game.Back.Object.Force;
import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Back.Object.Element.Element;
import com.mygdx.game.Back.Object.Element.ElementFactory;
import com.mygdx.game.Back.World.Map;
import com.mygdx.game.Graphic.GraphicObject.GraphicElement.GraphicElement;

public class Arc extends Weapon {
    private ElementFactory factory;
    public Arc(String name, int power){
        super(name, power);
        this.factory = new ElementFactory();
    }

    public void presenter(){
        System.out.println(name + power);
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
        return object;
    }
    /*------------------------------ATTACK------------------------------------- */
    public void Attack(Object object, Map map){
        Element arrow = factory.createProjectile(object.getX(), object.getY());
        arrow.applyForce(new Force(20000,20000, object.getorX(), object.getorY()));
        arrow.setGraphicObject(new GraphicElement(16, 16));
        arrow.setTextureObject(Arrow(object.getorX(), object.getorY()));
        map.addElement(arrow);
    }
}
