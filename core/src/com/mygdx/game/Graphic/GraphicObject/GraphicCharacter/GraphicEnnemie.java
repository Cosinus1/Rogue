package com.mygdx.game.Graphic.GraphicObject.GraphicCharacter;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;

import com.mygdx.game.Back.Character.Ennemie.Ennemie;
import com.mygdx.game.Graphic.World.Map.Map;

public class GraphicEnnemie extends GraphicCharacter{

    public GraphicEnnemie(Ennemie character, float x, float y, TiledMapTileSets TileSets){
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
        if( (Math.sqrt(distanceX) + Math.sqrt(distanceY))/tileWidth < character.getDetecRange()){
            //Set moves towards the Hero
            float moveX = signX*tileWidth;
            float moveY = signY*tileWidth;

            int endX = (int) Xh/tileWidth;
            int endY = (int) Yh/tileWidth;

            //The position must be valid 
            if (isValidTrajectory((int) X/tileWidth, (int) Y/tileWidth, endX, endY, (int) moveX/tileWidth, (int) moveY/tileWidth, map)) {
                
                switchtorandom = false;
                if(!(inRange(hero, map))){
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
    
/*-----------------------------------------------------COMBAT------------------------------------------------------------ */
    
    public void Attack(Map map){
        float Xh = (float) map.getHero().getX();
        float Yh = (float) map.getHero().getY();
        float signX = Math.signum(Xh-X);
        float signY = Math.signum(Yh-Y);
        float distanceX = (Xh-X)*(Xh-X);
        float distanceY = (Yh-Y)*(Yh-Y);
        int orX;
        int orY;
        float delta = 64.0f;
        if (distanceY/32 < delta) orY = 0;
        else orY = (int) signY;
        if (distanceX/32 < delta) orX = 0;
        else orX = (int) signX;

        character.Attack(X, Y, orX, orY, map);
        //hero.getCharacter().recevoirDegats(this.getCharacter().getPower());
    }
/*------------------------------------------------------RENDER----------------------------------------------------------- */
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
