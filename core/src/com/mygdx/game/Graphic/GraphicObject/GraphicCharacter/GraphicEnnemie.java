package com.mygdx.game.Graphic.GraphicObject.GraphicCharacter;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;

import com.mygdx.game.Back.Character.Character;
import com.mygdx.game.Back.Character.Ennemie.Ennemie;

import com.mygdx.game.Graphic.World.Map.Map;

public class GraphicEnnemie extends GraphicCharacter{

    public GraphicEnnemie(Character character, float x, float y, TiledMapTileSets TileSets){
        super(character,x,y);
        getEnnemieTextures(TileSets);
        Object.setTextureRegion(moveTexture_list.get(0));
    }


/* --------------------------------------------------GETTERS-------------------------------------------------------------- */
    public Ennemie getCharacter(){
        return (Ennemie) character;
    }
/* ----------------------------------------------TEXTURE HANDLING---------------------------------------------------- */

    public void getEnnemieTextures(TiledMapTileSets Tilesets){
        
        System.out.println("Getting textures for : " + Name + "with" + Tilesets);
        //Setting FPS for slower animation
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
            //Death Textures
                //Front Texture
                TextureRegion deathfront = getTexturefromTileset(Tilesets, Name, "statut", "dead",index);
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
                //Death
                if(deathfront!=null)deathTexture_list.add(deathfront);      
                
            }
        }
    }

    
    //Set appropriate Sprite for battle animation
    public void setBattleTexture(){

        ArrayList<TextureRegion> BattleSprites = getBattleTexture_List();
        int size = BattleSprites.size();
        int index = this.getIndex();
        int angle = this.getAngle();
  
        if(index>size){
            this.index = 4;
            this.getCharacter().toggle_Attack();

        }else this.index +=8;
        this.Object.setTextureRegion(BattleSprites.get((angle+index%size)));
    }

/*------------------------------------------------SPAWN------------------------------------------------------------------------ */

    public void spawn(Map map){
        map.addPNJ(this);
    }

/* ----------------------------------------------MOVEMENT---------------------------------------------------------------------- */

    public void move(GraphicHero hero, Map map){
        TiledMapTileLayer collisionLayer = map.getcollisionLayer();
        int tileWidth = collisionLayer.getTileWidth();

        Random random = new Random();
        float speed = 70; // the higher the slower
        float Xh = (float) hero.getX();
        float Yh = (float) hero.getY();


        boolean switchtorandom = true;
        float X = getX();
        float Y = getY();

        //get the distance and the orientation between hero and character
        float distanceX = (Xh-X)*(Xh-X);
        float distanceY = (Yh-Y)*(Yh-Y);
        float signX = Math.signum(Xh-X);
        float signY = Math.signum(Yh-Y);

        //Move towards the hero if in detectionrange 
        if( distanceX + distanceY < 50000){
            //Set moves towards the Hero
            float moveX = signX*collisionLayer.getTileWidth();
            float moveY = signY*collisionLayer.getTileWidth();

            int endX = (int) Xh/tileWidth;
            int endY = (int) Yh/tileWidth;

            //The position must be valid 
            if (isValidTrajectory((int) X/tileWidth, (int) Y/tileWidth, endX, endY, (int) moveX/tileWidth, (int) moveY/tileWidth, map)) {
                
                switchtorandom = false;
                if(!(Hitbox.overlaps(hero.getHitbox()) || inRange(hero, map))){
                    setPosition(X+moveX/speed, Y+moveY/speed);
                    //Get the appropriate sprite for movement
                    int y;
                    int x;
                    float delta = 6.0f;
                    if (distanceY < delta) y = 0;
                    else y = (int) signY;
                    if (distanceX < delta) x = 0;
                    else x = (int) signX;
                    setMoveTexture(x, y);
                }
            }
            else switchtorandom = true;
        }
            //Otherwise the character moves randomly
            if(switchtorandom){
                // Generate random movements
                int randomX = random.nextInt(3) - 1; // Random movement in x direction (-1, 0, 1)
                int randomY = random.nextInt(3) - 1; // Random movement in y direction (-1, 0, 1)
        
                // Get current character position
        
                // Check previous movement direction of the character and add some probability to maintain direction
                if(!(Object.getProperties().containsKey("previousMoveX"))){
                    Object.getProperties().put("previousMoveX",X);
                }
                if(!(Object.getProperties().containsKey("previousMoveY"))){
                    Object.getProperties().put("previousMoveY",Y);
                }

                float previousMoveY = (float) Object.getProperties().get("previousMoveY");
                float previousMoveX = (float) Object.getProperties().get("previousMoveX");
        
                // Probability to maintain direction (adjust as needed)
                double probabilityToMaintainDirection;
                do probabilityToMaintainDirection = Math.random(); while (probabilityToMaintainDirection<0.95);
        
                if (random.nextDouble() < probabilityToMaintainDirection) {
                    randomX = Math.round(previousMoveX * speed);
                    randomY = Math.round(previousMoveY * speed);
                } else {
                    // If not maintaining direction, randomly change direction
                Object.getProperties().put("previousMoveX",randomX/speed);
                Object.getProperties().put("previousMoveY",randomY/speed);
                }
        
                // Get New Position
                float newX = X + (randomX * collisionLayer.getTileWidth() / speed);
                float newY = Y + (randomY * collisionLayer.getTileHeight() / speed);
        
                //Update character's new position if valid
                if (isValidPosition((int) newX/tileWidth, (int) newY/tileWidth, map)) {
                    setPosition(newX,newY);
                    setMoveTexture(randomX, randomY);
                }
            }
        }

    private boolean isValidPosition(int X, int Y, Map map) {
        //System.out.println("X : " + X+ " / Y : " + Y);
        //Check Map boundaries
        int mapWidth = map.getmapWidth();
        int mapHeight = map.getmapHeight();
        //Check Map boundaries
        if(X<1 || X>(mapWidth-2) || Y<1 || Y>(mapHeight-2)) return false;
        // Check distance from walls
        return map.checkDistancefromWall(X, Y);
    }
    private boolean isValidTrajectory(int X, int Y, int endX, int endY, int moveX, int moveY, Map map){
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

/*-----------------------------------------------------COMBAT------------------------------------------------------------ */
    public boolean inRange(GraphicCharacter character, Map map){;
        float x = getX();
        float y = getY();
        double distanceX = x - character.getX();
        double distanceY = y - character.getY();

        int distance = (int) Math.sqrt(Math.pow(distanceY, 2) + Math.pow(distanceX, 2))/32;

        int X = (int) x/32;
        int Y = (int) y/32;
        int endX = (int) (x-distanceX)/32;
        int endY = (int) (y-distanceY)/32;

        int signX = (int) Math.signum(-distanceX);
        int signY = (int) Math.signum(-distanceY);

        //System.out.println(" signX : " + signX + "signY : " + signY);

        if(distance <= (int) getCharacter().getRange() && isValidTrajectory(X, Y, endX, endY, signX, signY, map)) return true;
        else return false;
    }
    public void attack(GraphicHero hero){

        hero.getCharacter().recevoirDegats(this.getCharacter().getPower());
    }

    public void kill(Map map){
        map.getObjects().remove(this.Object);
        map.getPNJ_list().remove(this);
        map.getDeadObjects().add(this.Object);
        map.getDeadPNJ_list().add(this);
    }       
    
     public void render(SpriteBatch spriteBatch, OrthographicCamera camera){
        int scaleFactor;
        //float offsetX=0;
        //float offsetY=0;
        TextureRegion textureRegion = Object.getTextureRegion();
        // Render the object texture based on its position and properties
        float objectX = getX();
        float objectY = getY();
        //Render bigger for boss
        if (Object.getProperties().get("boss")=="boss"){
            scaleFactor = 2;
            //offsetX += scaleFactor*16;
            //offsetY -= scaleFactor*16;
        }
        else scaleFactor = 1;
        float objectWidth = textureRegion.getRegionWidth()*scaleFactor;
        float objectHeight = textureRegion.getRegionHeight()*scaleFactor;
            
            spriteBatch.setProjectionMatrix(camera.combined);

            // Adjust position if it's the battle animation texture
            if (textureRegion.getRegionWidth() == 128 && textureRegion.getRegionHeight() == 128) {
                // Adjust the position to properly center the larger texture
                objectY -= 64*scaleFactor;
            }

        
            spriteBatch.draw(textureRegion, objectX, objectY, objectWidth, objectHeight);
            barlife.drawPNJLifeBar(spriteBatch,this);
        }
}
