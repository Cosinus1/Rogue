package com.mygdx.game.Graphic.GraphicObject;

import java.util.ArrayList;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Rectangle;

import com.mygdx.game.Back.Force;

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

    protected ArrayList<Force> Forces;

    public GraphicObject(float x, float y, int width, int height){

        Object = new TextureMapObject();
        Forces = new ArrayList<>();

        this.Hitbox = new Rectangle(x, y, width, height);
    }
    /*-------------------------------------------------------------GETTERS---------------------------------------------------------------------- */

    public Rectangle getHitbox(){
        return Hitbox;
    }

    public void PFD(){
        if(Forces != null){
            for(Force force : Forces){
                if(force.axis == 0){
                    aX += force.value;
                }
                if(force.axis == 1){
                    aY += force.value;
                }
            }
        }else System.err.println("No Forces applied");
    }


}
