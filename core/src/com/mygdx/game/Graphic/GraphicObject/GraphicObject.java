package com.mygdx.game.Graphic.GraphicObject;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Back.Force;
import com.mygdx.game.Graphic.World.Map.Map;

public class GraphicObject {
    protected String GraphicType;
    protected TextureMapObject Object;

    protected Rectangle Hitbox;
    protected float X, Y;
    protected float lastX, lastY;

    //Orientation for targeting
    protected int OrX;
    protected int OrY;

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
    public float getX(){
        return this.Hitbox.x;
    }
    public float getY(){
        return this.Hitbox.y;
    }
    public int getorX(){
        return OrX;
    }
    public int getorY(){
        return OrY;
    }
    public float getSpeedX(){
        return speedX;
    }
    public float getSpeedY(){
        return speedY;
    }
    public TextureMapObject getObject(){
        return Object;
    }
    public String getType(){
        return GraphicType;
    }
    public float getWidth(){
        return Hitbox.width;
    }
    public float getHeight(){
        return Hitbox.height;
    }
    /*-------------------------------------------------------------SETTERS---------------------------------------------------------------------- */
    public void setPosition(float x, float y){
        this.Hitbox.x = x;
        this.Hitbox.y = y;
    }

    public void setPosition (Vector2 position) {
		setPosition(position.x, position.y);
	}

    public void setOrientation(int x, int y){
        OrX = x;
        OrY = y;
    }

    public void applyForce(Force force){
        Forces.add(force);
    }
    public void resetForces(){
        Forces = new ArrayList<>();
    }
    public void setObject(TextureMapObject Object){
        this.Object = Object;
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
            Iterator<Force> Iterator = Forces.iterator();
            while(Iterator.hasNext()){
                Force force = Iterator.next();
            
                aX += 10*force.valueX/mass;
                aY += 10*force.valueY/mass;
                //Reduce Force values if friction
                force.valueX = force.valueX/friction;
                force.valueY = force.valueY/friction;

                //Neglect Force when null
                if(force.valueX==0 && force.valueY==0){
                    Iterator.remove();
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

        //System.out.println("speedX : " + speedX + " speedY ; " + speedY);

        //Update Position
        X = speedX * deltaTime + Hitbox.x;
        Y = speedY * deltaTime + Hitbox.y;

        //System.out.println("X : " + X + " Y ; " + Y);
        setPosition(X, Y);

        //Reset Acceleration
        aX = 0;
        aY = 0;

    }
    /*--------------------------------------------------------------CHECKERS-------------------------------------------------------- */


    public boolean isValidPosition(int X, int Y, Map map) {
        //System.out.println("X : " + X+ " / Y : " + Y);
        //Check Map boundaries
        int mapWidth = map.getmapWidth();
        int mapHeight = map.getmapHeight();
        //Check Map boundaries
        if(X<1 || X>(mapWidth-2) || Y<1 || Y>(mapHeight-2)) return false;
        // Check distance from walls
        return map.checkDistancefromWall(X, Y);
    }
    public boolean isValidTrajectory(int X, int Y, int endX, int endY, int moveX, int moveY, Map map){
        int newX = X + moveX;
        int newY = Y + moveY;
        //Check if we arrived at target
        if (X==endX && Y==endY) return true;

        //Otherwise check if the next step is valid
            //Check if we are at the same X than the target
            boolean isValidX;
            if(endX==X){
                //No move on X needed so it's a valid position on X
                isValidX = true;
                newX -= moveX;
            }else isValidX = isValidPosition(X+moveX, Y, map);
            //Check if we are at the same Y than the target
            boolean isValidY;
            if(endY==Y){
                //No move on Y needed so it's a valid position on Y
                isValidY = true;
                newY -= moveY;
            }else isValidY = isValidPosition(X, Y+moveY, map);

        if(isValidX && isValidY){
            return isValidTrajectory(newX, newY, endX, endY, moveX, moveY, map);
        }else return false;// A wall is in the trajectory
    }


    /*--------------------------------------------------------------RENDER-------------------------------------------------------- */

    public void render(SpriteBatch spriteBatch, OrthographicCamera camera){
    }
    /*--------------------------------------------------------------DISPOSE----------------------------------------------------- */
}
