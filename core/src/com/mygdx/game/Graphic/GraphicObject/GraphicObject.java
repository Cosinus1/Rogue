package com.mygdx.game.Graphic.GraphicObject;

import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Rectangle;

public class GraphicObject {
    protected TextureMapObject Object;

    protected Rectangle Hitbox;
    protected float X, Y;
    protected float lastX, lastY;

    //Speed of the Object
    protected float speedX;
    protected float speedY;
    //Acceleration of the object
    protected float aX;
    protected float aY;

    protected float mass;

    public GraphicObject(float x, float y, int width, int height){

        Object = new TextureMapObject();

        this.Hitbox = new Rectangle(x, y, width, height);
    }
    /*-------------------------------------------------------------GETTERS---------------------------------------------------------------------- */
    
    public Rectangle getHitbox(){
        return Hitbox;
    }
}
