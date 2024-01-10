package com.mygdx.game.Graphic.GraphicObject.GraphicCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Back.Object.Character.Character;
import com.mygdx.game.Back.Object.Character.Hero.Hero;
import com.mygdx.game.Graphic.World.World;
import com.mygdx.game.Graphic.World.Map.Map;

public class GraphicHero extends GraphicCharacter {
    private int angle = 0;
    private int index = 0;

    public GraphicHero(World world, Character character, float x, float y){
        super(character, x, y);
        getHeroTextures(world);
        Object.setTextureRegion(moveTexture_list.get(0));
        spawn(world.getDungeonHub());
        spawn(world.getHome());
        spawn(world.getTavern());
        
    }

    public Hero getCharacter(){
        return (Hero) this.character;
    }

    public void getHeroTextures(World world){
        TiledMapTileSets Tilesets = world.getHome().getTiledMap().getTileSets();
        //Setting FPS for slower animation
        System.out.println("Name : " + Name);
        int FPS = 10;
        for(int index=0; index<9; index++){
            //Movement Textures
                //Front Texture
                TextureRegion front = getTexturefromTileset(Tilesets, Name, "angle", "front",index);
                //Back Texture
                TextureRegion back = getTexturefromTileset(Tilesets, Name, "angle", "back",index);
                //Left Texture
                TextureRegion left = getTexturefromTileset(Tilesets, Name, "angle", "left", index);
                //Right Texture
                TextureRegion right = getTexturefromTileset(Tilesets, Name, "angle", "right", index);
            //Battle Textures
                //Front Texture
                TextureRegion Battlefront = getTexturefromTileset(Tilesets, Name, "battle", "front",index);
                //Back Texture
                TextureRegion Battleback = getTexturefromTileset(Tilesets, Name, "battle", "back",index);
                //Left Texture
                TextureRegion Battleleft = getTexturefromTileset(Tilesets, Name, "battle", "left", index);
                //Right Texture
                TextureRegion Battleright = getTexturefromTileset(Tilesets, Name, "battle", "right", index);
            //Adding the Textures to the lists
            for(int i=0; i<FPS; i++){
                //Movements
                if(front!=null)moveTexture_list.add(front);
                if(back!=null)moveTexture_list.add(back);
                if(left!=null)moveTexture_list.add(left);
                if(right!=null)moveTexture_list.add(right);                
                //Battle
                if(Battlefront!=null)battleTexture_list.add(Battlefront);
                if(Battleback!=null)battleTexture_list.add(Battleback);
                if(Battleleft!=null)battleTexture_list.add(Battleleft);
                if(Battleright!=null)battleTexture_list.add(Battleright);                
                
            }
        }
    }    
/*--------------------------------------------------------------SPAWN----------------------------------------------------------------- */
    public void spawn(Map map){
        System.out.println("Spawning : " + Name);
        // Add the MapObject to the object layer
        map.setHero(this);
    }
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
           if((!HeroNPC_collision || angle!=2) && (isValidPosition(tileX, tileY, map) || deltaX>delta || isValidPosition(tileX, tileY+1, map) && deltaY>(delta-5))){
                newX -= 200 * Gdx.graphics.getDeltaTime();
            }
                //Get the approprite sprite for render
                angle = 2;
                Object.setTextureRegion(moveTexture_list.get((angle+index%360)));
                index+=4; 
                //Set Orientation
                OrX = -1;
        }

        // RIGHT 

        else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            if ((!HeroNPC_collision || angle!=3) && (isValidPosition(tileX+1, tileY, map) || deltaX > delta || isValidPosition(tileX+1, tileY+1, map) && deltaY>(delta-5))){
                newX += 200 * Gdx.graphics.getDeltaTime();
            }
                //Get the approprite sprite for render
                angle = 3;
                Object.setTextureRegion(moveTexture_list.get((angle+index%360)));
                index+=4;
                //Set Orientation
                OrX = 1;
        }

        // UP

        else if(Gdx.input.isKeyPressed(Keys.UP)){  
            if ((!HeroNPC_collision || angle!=1) && (isValidPosition(tileX, tileY+1, map) || deltaY>delta || isValidPosition(tileX+1, tileY+1, map) && deltaX>(delta-5))){
                newY += 200 * Gdx.graphics.getDeltaTime();
            }
                //Get the approprite sprite for render
                angle = 1;
                Object.setTextureRegion(moveTexture_list.get((angle+index%360)));
                index+=4;
                //Set Orientation
                OrY = 1;
        }

        // DOWN 

        else if(Gdx.input.isKeyPressed(Keys.DOWN)){
            if((!HeroNPC_collision || angle!=0) && (isValidPosition(tileX, tileY, map) || deltaY>delta+5 || isValidPosition(tileX+1, tileY, map) && deltaX>(delta-5))){
                newY -= 200 * Gdx.graphics.getDeltaTime();
            }
                //Get the approprite sprite for render
                angle = 0;
                Object.setTextureRegion(moveTexture_list.get((angle+index%360)));
                index+=4;
                //Set Orientation
                OrY = -1;
        }

        //Update new Position
        setPosition(newX, newY);
        //Update Orientation
        setOrientation(OrX, OrY);

    }

    public void Attack(Map map){
        character.Attack(X, Y, OrX, OrY, map);
    }

    public void GraphicHeroAttack(Map map){
        if(Gdx.input.isKeyPressed(Keys.SPACE)){
            Object.setTextureRegion(battleTexture_list.get((angle+index)%battleTexture_list.size()));
            index+=8;
            //Attack target
            if((int) index%battleTexture_list.size()/13 == 5) Attack(map);
         }
    }

    public void render(SpriteBatch spriteBatch, OrthographicCamera camera){
        int scaleFactor = 1;
        //float offsetX=0;
        //float offsetY=0;
        TextureRegion textureRegion = Object.getTextureRegion();
        // Render the object texture based on its position and properties
        float objectX = getX() ;
        float objectY = getY() ;

        float objectWidth = textureRegion.getRegionWidth()*scaleFactor;
        float objectHeight = textureRegion.getRegionHeight()*scaleFactor;
            
            spriteBatch.setProjectionMatrix(camera.combined);

            // Adjust position if it's the battle animation texture
            if (textureRegion.getRegionWidth() == 192 && textureRegion.getRegionHeight() == 128) {
                // Adjust the position to properly center the larger texture
                objectX -= 64*scaleFactor;
                objectY -= 64*scaleFactor;
            }

        
            spriteBatch.draw(textureRegion, objectX, objectY, objectWidth, objectHeight);
            barlife.drawHeroLifeBar(spriteBatch,this.getCharacter());
        }
}
