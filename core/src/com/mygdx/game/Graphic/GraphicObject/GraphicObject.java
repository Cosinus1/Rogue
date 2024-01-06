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
    protected float friction;

    public GraphicObject(float x, float y, int width, int height){

        Object = new TextureMapObject();
        Forces = new ArrayList<>();
        this.friction = 1.1f;

        this.mass = 1;

        this.Hitbox = new Rectangle(x, y, width, height);
        initPosition();

    }
    /*-------------------------------------------------------------GETTERS---------------------------------------------------------------------- */

    public Rectangle getHitbox(){
        return Hitbox;
    }

    /*-------------------------------------------------------------SETTERS---------------------------------------------------------------------- */

    public void setPosition(float x, float y){

    }

    public void applyForce(Force force){
        Forces.add(force);
    }
    /*--------------------------------------------------------------Update--------------------------------------------------------------------- */
    public void initPosition(){
        this.X = Hitbox.x;
        this.Y = Hitbox.y;
        this.lastX = Y;
        this.lastY = X;
    }
    public void PFD(){
        if(Forces != null){
            for(Force force : Forces){
                aX += 10*force.valueX/mass;
                aY += 10*force.valueY/mass;
                //Reduce Force values if friction
                force.valueX = force.valueX/friction;
                force.valueY = force.valueY/friction;
                //Remove Force if neglected
                if(force.valueX == 0 && force.valueY == 0){
                    disposeForce(force);
                    System.out.println("force removed");
                } 
            }
        }else System.err.println("No Forces applied");
    }

    //Forces applied are constant => to Integrate resolves to multiply by time and add constant
    public void update(float deltaTime){
        PFD();
        //Update Speed
        speedX = aX * deltaTime ;
        speedY = aY * deltaTime ;

        System.out.println("speedX : " + speedX + " speedY ; " + speedY);

        //Update Position
        X = speedX * deltaTime + Hitbox.x;
        Y = speedY * deltaTime + Hitbox.y;

        System.out.println("X : " + X + " Y ; " + Y);
        setPosition(X, Y);

        //Reset Acceleration
        aX = 0;
        aY = 0;

    }

    /*--------------------------------------------------------------DISPOSE----------------------------------------------------- */
    public void disposeForce(Force force){
            Forces.remove(force);
    }
}
