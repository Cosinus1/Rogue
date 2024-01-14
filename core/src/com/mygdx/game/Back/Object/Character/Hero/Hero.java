package com.mygdx.game.Back.Object.Character.Hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.Item;
import com.mygdx.game.Back.Object.Force;
import com.mygdx.game.Back.Object.Character.Character;
import com.mygdx.game.Back.World.Map;


public class Hero extends Character{
    protected int money;
    

    public Hero(float x, float y, int pv, int defense, int power,int range, Inventory bag){
        super(x, y, pv, defense, power, range, bag);
        this.money = 100;       
    }

    public int getMoney(){
        return money; 
    }

    /*--------------------------------------------------------------SPAWN----------------------------------------------------------------- */
    public void spawn(Map map){
        System.out.println("Spawning : " + name);
        // Add the MapObject to the object layer
        map.setHero(this);
    }
    /*-------------------------------------------------------------MOVE-------------------------------------------------------------------- */
    public void move(OrthographicCamera camera, Map map){

        if(Gdx.input.isTouched()) {
         Vector3 touchPos = new Vector3();
         touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
         camera.unproject(touchPos);
         Hitbox.x = touchPos.x;
         Hitbox.y = touchPos.y;
        }

        //map.sortObjects();
        map.PNJcollision(this);
        map.Wallcollision(this);
        
        // LEFT

        if(Gdx.input.isKeyPressed(Keys.LEFT)){
           
                //Set Orientation
                OrX = -1;
                OrY = 0;
                //Set Angle
                setAngle();
                //Apply Force
                this.applyInstantForce(new Force(20000,20000, OrX, OrY));
                //Set appropriate sprite
                graphicObject.setMoveTexture();
                
                
        }

        // RIGHT 

        else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            
                //Set Orientation
                OrX = 1;
                OrY = 0;
                //Set Angle
                setAngle();
                //Apply Force
                this.applyInstantForce(new Force(20000,20000, OrX, OrY));
                //Set appropriate sprite
                graphicObject.setMoveTexture(); 
                
        }

        // UP

        else if(Gdx.input.isKeyPressed(Keys.UP)){  
            
                //Set Orientation
                OrX = 0;
                OrY = 1;
                //Set Angle
                setAngle();
                //Apply Force
                this.applyInstantForce(new Force(20000,20000, OrX, OrY));
                //Set appropriate sprite
                graphicObject.setMoveTexture();  
                
        }

        // DOWN 

        else if(Gdx.input.isKeyPressed(Keys.DOWN)){
            
                //Set Orientation
                OrX = 0;
                OrY = -1;
                //Set Angle
                setAngle();
                //Apply Force
                this.applyInstantForce(new Force(20000,20000, OrX, OrY));
                //Set appropriate sprite
                graphicObject.setMoveTexture();

                
                
        }
        //F input : apply force to Hero (testing implementation)
        if(Gdx.input.isKeyJustPressed(Keys.F)){
                this.applyForce(new Force(200,200, -OrX, -OrY));
            
            
         }
        
    }
    /*--------------------------------ATTACK----------------------------------- */
    public void Attack(Map map){
    }

    //return -1 if hero doesn't have enough money
    public int buyItem(Item item){
        if(money>item.getValue()){
                //On achete l'item
                money -= item.getValue();
                this.bag.addItem(item);
                return 0;
        }
        else{
                return -1;
        }
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
