package com.mygdx.game.Back.Object;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Back.World.Map;
import com.mygdx.game.Graphic.GraphicObject.GraphicObject;

public class Object {
    protected GraphicObject graphicObject;

    protected Rectangle Hitbox;
    protected float X, Y;
    protected float lastX, lastY;

    // Orientation for targeting
    protected int OrX;
    protected int OrY;

    // Speed of the Object
    protected float speedX;
    protected float speedY;
    // Acceleration of the object
    protected float aX;
    protected float aY;

    protected float mass;

    protected ArrayList<Force> Forces;
    protected float friction;

    public Object(float x, float y, int width, int height) {

        this.graphicObject = new GraphicObject(width, height);
        Forces = new ArrayList<>();
        this.friction = 10000.1f;

        this.mass = 1;

        this.Hitbox = new Rectangle(x, y, width, height);
        initPosition();

    }

    /*-------------------------------------------------------------GETTERS---------------------------------------------------------------------- */

    public Rectangle getHitbox() {
        return Hitbox;
    }

    public float getMass() {
        return this.mass;
    }

    public float getX() {
        return this.Hitbox.x;
    }

    public float getY() {
        return this.Hitbox.y;
    }

    public int getorX() {
        return OrX;
    }

    public int getorY() {
        return OrY;
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public float getAX() {
        return aX;
    }

    public float getAY() {
        return aY;
    }

    public float getWidth() {
        return Hitbox.width;
    }

    public float getHeight() {
        return Hitbox.height;
    }

    /*-------------------------------------------------------------SETTERS---------------------------------------------------------------------- */
    public void setPosition(float x, float y) {
        this.Hitbox.x = x;
        this.Hitbox.y = y;
    }

    public void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    public void setOrientation(int x, int y) {
        OrX = x;
        OrY = y;
    }

    public void applyForce(Force force) {
        Forces.add(force);
    }

    public void applyInstantForce(Force force) {
        aX += force.ForceX / mass;
        aY += force.ForceY / mass;
    }

    public void resetForces() {
        Forces = new ArrayList<>();
    }

    /* GRAPHIC */
    public void setTextureObject(TextureMapObject TextureObject) {
        this.graphicObject.setTextureObject(TextureObject);
    }

    public void setGraphicObject(GraphicObject graphicObject) {
        this.graphicObject = graphicObject;
    }

    /*--------------------------------------------------------------Update--------------------------------------------------------------------- */
    public void initPosition() {
        this.X = Hitbox.x;
        this.Y = Hitbox.y;
        this.lastX = Y;
        this.lastY = X;
    }

    public void PFD() {

        if (Forces.size() != 0) {

            Iterator<Force> Iterator = Forces.iterator();
            while (Iterator.hasNext()) {
                Force force = Iterator.next();
                // Apply friction to reduce force over time
                force.ForceX /= (1 + friction);
                force.ForceY /= (1 + friction);
                // PFD
                aX += force.ForceX / mass;
                aY += force.ForceY / mass;

                // Remove negligible forces
                if (Math.abs(force.ForceX) <= 0.1 && Math.abs(force.ForceY) <= 0.1) {
                    Iterator.remove();
                }
            }
        } else {
            aX = 0;
            aY = 0;
        }
    }

    public void update(float deltaTime) {
        PFD();
        speedX = aX * deltaTime;
        speedY = aY * deltaTime;
        // Update Position
        X += speedX * deltaTime;
        Y += speedY * deltaTime;
        setPosition(X, Y);

        // Print acceleration using printf
        System.out.printf("acceleration: %.2f, %.2f%n", aX, aY);

        // Print speed using printf
        System.out.printf("speed: %.2f, %.2f%n", speedX, speedY);
    }

    /*--------------------------------------------------------------CHECKERS-------------------------------------------------------- */

    public boolean inRange(Object object, Map map) {
        float x = getX();
        float y = getY();
        double distanceX = x - object.getX();
        double distanceY = y - object.getY();

        int distance = (int) Math.sqrt(Math.pow(distanceY, 2) + Math.pow(distanceX, 2)) / 32;

        int X = (int) x / 32;
        int Y = (int) y / 32;
        int endX = (int) (x - distanceX) / 32;
        int endY = (int) (y - distanceY) / 32;

        int signX = (int) Math.signum(-distanceX);
        int signY = (int) Math.signum(-distanceY);

        // System.out.println(" signX : " + signX + "signY : " + signY);

        if (distance <= 1 && isValidTrajectory(X, Y, endX, endY, signX, signY, map))
            return true;
        else
            return false;
    }

    public boolean isValidPosition(int X, int Y, Map map) {
        // System.out.println("X : " + X+ " / Y : " + Y);
        // Check Map boundaries
        int mapWidth = map.getmapWidth();
        int mapHeight = map.getmapHeight();
        // Check Map boundaries
        if (X < 2 || X > (mapWidth - 2) || Y < 2 || Y > (mapHeight - 2))
            return false;
        // Check distance from walls
        return map.checkDistancefromWall(X, Y);
    }

    public boolean isValidTrajectory(int X, int Y, int endX, int endY, int moveX, int moveY, Map map) {
        int newX = X + moveX;
        int newY = Y + moveY;
        // Check if we arrived at target
        if (X == endX && Y == endY)
            return true;

        // Otherwise check if the next step is valid
        // Check if we are at the same X than the target
        boolean isValidX;
        if (endX == X) {
            // No move on X needed so it's a valid position on X
            isValidX = true;
            newX -= moveX;
        } else
            isValidX = isValidPosition(X + moveX, Y, map);
        // Check if we are at the same Y than the target
        boolean isValidY;
        if (endY == Y) {
            // No move on Y needed so it's a valid position on Y
            isValidY = true;
            newY -= moveY;
        } else
            isValidY = isValidPosition(X, Y + moveY, map);

        if (isValidX && isValidY) {
            return isValidTrajectory(newX, newY, endX, endY, moveX, moveY, map);
        } else
            return false;// A wall is in the trajectory
    }

    /*----------------------------------------------------------RENDER------------------------------------------------ */
    public void render(SpriteBatch spriteBatch, OrthographicCamera camera) {

        if (graphicObject != null)
            graphicObject.render(this, spriteBatch, camera);
    }

}
