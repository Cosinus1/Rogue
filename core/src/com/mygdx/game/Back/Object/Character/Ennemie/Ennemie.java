package com.mygdx.game.Back.Object.Character.Ennemie;

import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.Weapon.Weapon;
import com.mygdx.game.Back.MovementStrategy.WanderMovementStrategy;
import com.mygdx.game.Back.Object.Force;
import com.mygdx.game.Back.Object.Character.Character;
import com.mygdx.game.Back.Object.Character.Hero.Hero;
import com.mygdx.game.Back.World.Map;

public class Ennemie extends Character {
    protected TiledMap tiledMap;
    protected Weapon weapon;
    protected int detectionRange;
    protected float movementSpeed = 500;

    protected WanderMovementStrategy WanderStrategy;

    public Ennemie(float x, float y, int pv, int defense, int power, int combatRange, int detectionRange, Inventory bag,
            Weapon weapon) {
        super(x, y, pv, defense, power, combatRange, bag);
        this.weapon = weapon;
        this.detectionRange = detectionRange;
        this.range = weapon.getRange();
    }

    /*----------------------------------GETTERS------------------------------------- */

    public int getDetecRange() {
        return this.detectionRange;
    }

    /*----------------------------------SETTERS------------------------------------- */
    public void toggle_Attack() {
        this.attack_charged = !attack_charged;
    }
    /*----------------------------------CHECKERS------------------------------------- */

    public boolean isValidTrajectory(int startX, int startY, int endX, int endY, Map map) {

        int distanceX = endX - startX;
        int distanceY = endY - startY;
        int moveX = (int) Math.signum(distanceX);
        int moveY = (int) Math.signum(distanceY);
        boolean isValidX = false;
        boolean isValidY = false;

        if (distanceX == 0 && distanceY == 0)
            return true;
        if (!isValidPosition(startX + moveX, startY, map)) {
            moveX = 0;
            isValidX = true;
        }
        if (!isValidPosition(startX, startY + moveY, map)) {
            moveY = 0;
            isValidY = true;
        }
        if (isValidX || isValidY) {
            return isValidTrajectory(startX + moveX, startY + moveY, endX, endY, map);
        } else
            return false;
    }
    /*---------------------------------SPAWN----------------------------------------- */

    public void spawn(Map map) {
        map.addPNJ(this);
    }
    /*
     * ----------------------------------------------MOVEMENT-----------------------
     * -----------------------------------------------
     */

    public void move(Hero hero, Map map) {
        TiledMapTileLayer collisionLayer = map.getcollisionLayer();
        int tileWidth = collisionLayer.getTileWidth();

        Random random = new Random();
        float speed = 70; // the higher the slower
        float Xh = (float) hero.getX();
        float Yh = (float) hero.getY();

        boolean switchtorandom = true;
        float X = getX();
        float Y = getY();

        // get the distance and the orientation between hero and character
        float distanceX = (Xh - X) * (Xh - X);
        float distanceY = (Yh - Y) * (Yh - Y);
        float signX = Math.signum(Xh - X);
        float signY = Math.signum(Yh - Y);

        // Move towards the hero if in detectionrange
        if ((Math.sqrt(distanceX) + Math.sqrt(distanceY)) / tileWidth < detectionRange) {
            // Set moves towards the Hero
            float moveX = signX * tileWidth;
            float moveY = signY * tileWidth;

            int endX = (int) Xh / tileWidth;
            int endY = (int) Yh / tileWidth;

            // The position must be valid
            if (isValidTrajectory((int) X / tileWidth, (int) Y / tileWidth, endX, endY, (int) signX, (int) signY,
                    map)) {

                switchtorandom = false;
                if (!(inRange(hero, map))) {
                    setPosition(X + moveX / speed, Y + moveY / speed);
                    // Get the appropriate sprite for movement
                    // Set Angle
                    float delta = 6.0f;
                    if (distanceY < delta)
                        OrY = 0;
                    else
                        OrY = (int) signY;
                    if (distanceX < delta)
                        OrX = 0;
                    else
                        OrX = (int) signX;
                    setAngle();
                    graphicObject.setMoveTexture();
                }
            } else
                switchtorandom = true;
        }
        // Otherwise the character moves randomly
        if (switchtorandom) {
            // Generate random movements
            int randomX = random.nextInt(3) - 1; // Random movement in x direction (-1, 0, 1)
            int randomY = random.nextInt(3) - 1; // Random movement in y direction (-1, 0, 1)

            // Get current character position

            // Check previous movement direction of the character and add some probability
            // to maintain direction
            if (!(graphicObject.getTextureObject().getProperties().containsKey("previousMoveX"))) {
                graphicObject.getTextureObject().getProperties().put("previousMoveX", randomX);
            }
            if (!(graphicObject.getTextureObject().getProperties().containsKey("previousMoveY"))) {
                graphicObject.getTextureObject().getProperties().put("previousMoveY", randomY);
            }

            int previousMoveY = (int) graphicObject.getTextureObject().getProperties().get("previousMoveY");
            int previousMoveX = (int) graphicObject.getTextureObject().getProperties().get("previousMoveX");

            // Probability to maintain direction (adjust as needed)
            double probabilityToMaintainDirection;
            do
                probabilityToMaintainDirection = Math.random();
            while (probabilityToMaintainDirection < 0.95);

            if (random.nextDouble() < probabilityToMaintainDirection) {
                randomX = previousMoveX;
                randomY = previousMoveY;
            } else {
                // If not maintaining direction, randomly change direction
                graphicObject.getTextureObject().getProperties().put("previousMoveX", randomX);
                graphicObject.getTextureObject().getProperties().put("previousMoveY", randomY);
            }

            // Set New Position
            this.applyInstantForce(new Force(movementSpeed, movementSpeed, randomX, randomY));
            // map.Wallcollision(this);
            /*
             * float newX = X + (randomX * collisionLayer.getTileWidth() / speed);
             * float newY = Y + (randomY * collisionLayer.getTileHeight() / speed);
             * 
             * 
             * if (isValidPosition((int) newX/tileWidth, (int) newY/tileWidth, map)) {
             * setPosition(newX,newY);
             * }
             */
            // Set angle
            OrX = randomX;
            OrY = randomY;
            setAngle();
            graphicObject.setMoveTexture();
        }
    }

    /*-----------------------------------------------------COMBAT------------------------------------------------------------ */

    /*---------------------------------ATTACK--------------------------------------- */
    public void Attack(Map map) {
        if (attackTimer > attackCooldown) {
            weapon.Attack(this, map);
            attackTimer = 0;
            graphicObject.setBattleTexture();
        }

    }
}
