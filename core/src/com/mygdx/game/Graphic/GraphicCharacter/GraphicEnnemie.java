package com.mygdx.game.Graphic.GraphicCharacter;

import java.util.Random;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;

import com.mygdx.game.Back.Character.Character;
import com.mygdx.game.Back.Character.Ennemie.Ennemie;
import com.mygdx.game.Graphic.World.World;
import com.mygdx.game.Graphic.World.Map.Map;

public class GraphicEnnemie extends GraphicCharacter{
    public GraphicEnnemie(Character character, float x, float y){
        super(character,x,y);
    }

/* --------------------------------------------------GETTERS-------------------------------------------------------------- */
    public Ennemie getCharacter(){
        return (Ennemie) character;
    }
/* ----------------------------------------------TEXTURE HANDLING---------------------------------------------------- */

    public void getEnnemieTextures(World world, String Tileset_name, boolean Boss){
        Map map = world.getDungeon();
        //Setting FPS for slower animation
        int FPS = 10;
        for(int index=0; index<9; index++){
            //Movement Textures
                //Front Texture
                TextureRegion front = getTexturefromTileset(map, Tileset_name, "angle", "front",index, Boss);
                //Back Texture
                TextureRegion back = getTexturefromTileset(map, Tileset_name, "angle", "back",index, Boss);
                //Left Texture
                TextureRegion left = getTexturefromTileset(map, Tileset_name, "angle", "left", index, Boss);
                //Right Texture
                TextureRegion right = getTexturefromTileset(map, Tileset_name, "angle", "right", index, Boss);
            //Battle Textures
                //Front Texture
                TextureRegion Battlefront = getTexturefromTileset(map, Tileset_name, "battle", "front",index, Boss);
                //Back Texture
                TextureRegion Battleback = getTexturefromTileset(map, Tileset_name, "battle", "back",index, Boss);
                //Left Texture
                TextureRegion Battleleft = getTexturefromTileset(map, Tileset_name, "battle", "left", index, Boss);
                //Right Texture
                TextureRegion Battleright = getTexturefromTileset(map, Tileset_name, "battle", "right", index, Boss);
            //Death Textures
                //Front Texture
                TextureRegion deathfront = getTexturefromTileset(map, Tileset_name, "statut", "dead",index, Boss);
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

    // Graphic spawn of the Ennemie
    public void spawn(Map map, TiledMapTileSets tileSets, GraphicEnnemie character, String Tileset_name) {
        //tilesets = null means we use the same tilesets than map
        TiledMapTileSets Sets;
        //Check if we use tilsets from another map (used for maps that a generated through out the game)
        if(tileSets == null)Sets = map.getTiledMap().getTileSets();
        else Sets = tileSets;
        MapObjects objects = map.getObjects();
        int GID = 0;
        float x = character.getX();
        float y = character.getY();
        // Create a new MapObject similar to the ones in Tiled
        character.Object.setName(Tileset_name); // Set the name to match the object in Tiled
        
        // Search for the tileset in the map
        TiledMapTileSet tileSet = null;
        for (TiledMapTileSet tileset : Sets) {
            if (tileset.getName().equals(Tileset_name)) {
                tileSet = tileset;
                break;
            }
        }

        if (tileSet != null) {
            TiledMapTile tile = null;
            // Get the texture region from the tileset's tiles
            for(TiledMapTile Tile : tileSet){
                if(Tile.getProperties().containsKey("basic") && Tile.getProperties().get("angle").equals("front")){
                    GID = Tile.getId();
                    tile = tileSet.getTile(GID);
                    break;
                }
            }

            if (tile != null) {
                TextureRegion textureRegion = tile.getTextureRegion();
                character.Object.setTextureRegion(textureRegion);
                // Set the position of the MapObject based on provided coordinates
                character.Object.getProperties().put("x", x);
                character.Object.getProperties().put("y", y);

                // Set the GID property to match the existing GID of the Gobelin object in the tileset
                character.Object.getProperties().put("gid", GID);

                // Add the MapObject to the object layer
                objects.add(character.Object);
                //add to the PNJ list if not the Hero
                map.addPNJ(character);
            }
            else System.out.println("tile  is null, spawn canceled");
        } else {
            System.out.println("tileset is null, spawn canceled");
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

/* ----------------------------------------------MOVEMENT---------------------------------------------------------------------- */

    public void move(GraphicHero hero, Map map){
        TiledMapTileLayer collisionLayer = map.getcollisionLayer();
        int tileWidth = collisionLayer.getTileWidth();

        Random random = new Random();
        float speed = 70; // the higher the slower
        float Xh = (float) hero.getObject().getProperties().get("x");
        float Yh = (float) hero.getObject().getProperties().get("y");


        boolean switchtorandom = true;
        float X = (float) Object.getProperties().get("x");
        float Y = (float) Object.getProperties().get("y");

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
                if(!(Hitbox.overlaps(hero.Hitbox) || inRange(hero))){
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
                float currentX = (float) Object.getProperties().get("x");
                float currentY = (float) Object.getProperties().get("y");
        
                // Check previous movement direction of the character and add some probability to maintain direction
                if(!(Object.getProperties().containsKey("previousMoveX"))){
                    Object.getProperties().put("previousMoveX",currentX);
                }
                if(!(Object.getProperties().containsKey("previousMoveY"))){
                    Object.getProperties().put("previousMoveY",currentY);
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
        
                // Update character's new position
                float newX = currentX + (randomX * collisionLayer.getTileWidth() / speed);
                float newY = currentY + (randomY * collisionLayer.getTileHeight() / speed);
        
                // Ensure the new position is within the map bounds
                if (isValidPosition((int) newX/tileWidth, (int) newY/tileWidth, map)) {
                    setPosition(newX,newY);
                    //System.out.println("Position : " + (int) newX/tileWidth + ", " + (int) newY/tileWidth);
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
    public boolean inRange(GraphicCharacter character){
        float x = getX();
        float y = getY();
        double distanceX = x - character.getX();
        double distanceY = y - character.getY();
        int distance = (int) Math.sqrt(Math.pow(distanceY, 2) + Math.pow(distanceX, 2))/32;
        //System.out.println(getCharacter().getName()+ " Range : " + (int) getCharacter().getRange()/32);
        if(distance <= (int) getCharacter().getRange()/32) return true;
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
}
