package com.mygdx.game.Back.Object.Character.Hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Object.Character.Character;
import com.mygdx.game.Back.World.Map.Map;


public class Hero extends Character{
    protected int exp;
    

    public Hero(float x, float y, int pv, int defense, int power,int range, Inventory bag){
        super(x, y, pv, defense, power, range, bag);
        this.exp = 0;       
    }
    /*--------------------------------------------------------------SPAWN----------------------------------------------------------------- */
    public void spawn(Map map){
        System.out.println("Spawning : " + name);
        // Add the MapObject to the object layer
        map.setHero(this);
    }
    /*-------------------------------------------------------------MOVE-------------------------------------------------------------------- */
    public void move(OrthographicCamera camera, Map map){

        //reset orientation if movement
        if(!(Gdx.input.isKeyJustPressed(Keys.SPACE)) && Gdx.input.isKeyJustPressed(Keys.ANY_KEY) ) setOrientation(0, 0);
        

        int tileWidth = 32;
        int tileHeight = 32;

        float newX = getX();
        float newY = getY();

        // Get Tile coordinates
        int tileX = (int) getX() / tileWidth;
        int tileY = (int) getY() / tileHeight;

        //Delta defines the shortest distance the Hero can have with a wall
        float delta = 20;
        float deltaX = Math.abs(newX - tileX*tileWidth);
        float deltaY = Math.abs(newY - tileY*tileWidth);

        //System.out.println("float position : " + newX + " / " + newY);
        //System.out.println("int position : " + tileX + " / " + tileY);
        //System.out.println("deltaX : " + deltaX + " / deltaY : " + deltaY);

        boolean HeroNPC_collision;

        if(Gdx.input.isTouched()) {
         Vector3 touchPos = new Vector3();
         touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
         camera.unproject(touchPos);
         newX = touchPos.x;
         newY = touchPos.y;
        }

        //map.sortObjects();
        HeroNPC_collision = map.PNJcollision(this);
        
        // LEFT

        if(Gdx.input.isKeyPressed(Keys.LEFT)){
           if((!HeroNPC_collision || OrX!=-1) && (isValidPosition(tileX, tileY, map) || deltaX>delta || isValidPosition(tileX, tileY+1, map) && deltaY>(delta-5))){
                newX -= 200 * Gdx.graphics.getDeltaTime();
            }
                //Set Orientation
                OrX = -1;
                //Set Angle
                setAngle();
                //Set appropriate sprite
                graphicObject.setMoveTexture();
                
                
        }

        // RIGHT 

        else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            if ((!HeroNPC_collision || OrX!=1) && (isValidPosition(tileX+1, tileY, map) || deltaX > delta || isValidPosition(tileX+1, tileY+1, map) && deltaY>(delta-5))){
                newX += 200 * Gdx.graphics.getDeltaTime();
            }
                //Set Orientation
                OrX = 1;
                //Set Angle
                setAngle();
                //Set appropriate sprite
                graphicObject.setMoveTexture(); 
                
        }

        // UP

        else if(Gdx.input.isKeyPressed(Keys.UP)){  
            if ((!HeroNPC_collision || OrY!=1) && (isValidPosition(tileX, tileY+1, map) || deltaY>delta || isValidPosition(tileX+1, tileY+1, map) && deltaX>(delta-5))){
                newY += 200 * Gdx.graphics.getDeltaTime();
            }
                //Set Orientation
                OrY = 1;
                //Set Angle
                setAngle();
                //Set appropriate sprite
                graphicObject.setMoveTexture();  
                
        }

        // DOWN 

        else if(Gdx.input.isKeyPressed(Keys.DOWN)){
            if((!HeroNPC_collision || OrY!=-1) && (isValidPosition(tileX, tileY, map) || deltaY>delta+5 || isValidPosition(tileX+1, tileY, map) && deltaX>(delta-5))){
                newY -= 200 * Gdx.graphics.getDeltaTime();
            }
                //Set Orientation
                OrY = -1;
                //Set Angle
                setAngle();
                //Set appropriate sprite
                graphicObject.setMoveTexture();

                
                
        }

        //Update new Position
        setPosition(newX, newY);
    }

    public void Attack(Map map){
    }

/*     public void GraphicHeroAttack(Map map){
        if(Gdx.input.isKeyPressed(Keys.SPACE)){
            Object.setTextureRegion(battleTexture_list.get((angle+index)%battleTexture_list.size()));
            index+=8;
            //Attack target
            if((int) index%battleTexture_list.size()/13 == 5) Attack(map);
         }
    }*/

}
