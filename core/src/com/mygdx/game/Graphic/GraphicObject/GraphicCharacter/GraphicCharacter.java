package com.mygdx.game.Graphic.GraphicObject.GraphicCharacter;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;

import com.mygdx.game.Back.Character.Character;
import com.mygdx.game.Graphic.GraphicObject.GraphicObject;
import com.mygdx.game.Graphic.GraphicObject.Elements.*;
import com.mygdx.game.Graphic.World.Map.Map;

public class GraphicCharacter extends GraphicObject{
    protected Character character;

    protected String Name;

    protected ArrayList<TextureRegion> moveTexture_list;
    protected ArrayList<TextureRegion> battleTexture_list;
    protected ArrayList<TextureRegion> deathTexture_list;
    protected BarLife barlife;

    //Status variables
    protected int death = 0;
    protected int index = 0; //to get the appropriate sprite (follow track of movement)
    protected int angle = 0; 

    public GraphicCharacter(Character character2, float x, float y){
        super(x, y, 32, 32);
        this.character = character2;
        this.Name = character.Class();
        //Init Texture lists
        moveTexture_list = new ArrayList<>();
        battleTexture_list = new ArrayList<>();
        deathTexture_list = new ArrayList<>();
        barlife = new BarLife();
    }

/*----------------------------------------- GETTERS -------------------------------------- */

    public ArrayList<TextureRegion> getMoveTexture_List(){
        return moveTexture_list;
    }
    public ArrayList<TextureRegion> getBattleTexture_List(){
        return battleTexture_list;
    }
    public ArrayList<TextureRegion> getDeathTexture_List(){
        return deathTexture_list;
    }
    public int getDeathTimer(){
        return this.death;
    }
    public float getlastX(){
        return this.lastX;
    }
    public float getlastY(){
        return this.lastY;
    }
    public Character getCharacter(){
        return character;
    }
    public TextureMapObject getObject(){
        return Object;
    }
    public int getIndex(){
        return index;
    }
    public int getAngle(){
        return angle;
    }
    public BarLife getBarlife(){
        return barlife;
    }

    public boolean overlaps(GraphicCharacter character){
        return Hitbox.overlaps(character.getHitbox());
    }
    public int getRange(){
        return character.getRange();
    }
/*----------------------------------------- SETTERS -------------------------------------- */  
    
    public void setX(float x){
        Hitbox.x = x;
    }
    public void setY(float y){
        Hitbox.y = y;
    }
    public void setlastX(float x){
        this.lastX = x;
    }
    public void setlastY(float y){
        this.lastY = y;
    }
    public void incrementDeathTimer(){
        death++;
    }
    public void setName(){
        this.Object.setName(character.getName());
    }
    public void setObject(String key, float x){
        Object.getProperties().put(key,x);
    }
    public void resetIndex(){
        this.index = 0;
    }
    

    public void setMoveTexture(int x, int y){
        int size = moveTexture_list.size();
        //Check horizontal movement
        switch (x) {
            case -1://Character going left
                angle = 2;
                this.Object.setTextureRegion(moveTexture_list.get((angle+index%size)));
                this.index+=4;
                break;

            case 0://Character not horizontaly moving
                break;

            case 1://Character going right
                angle = 3;
                this.Object.setTextureRegion(moveTexture_list.get((angle+index%size)));
                this.index+=4;
                break;
        }
        //Check vertical movement
        switch (y){
            case -1://Character going down
                angle = 0;
                this.Object.setTextureRegion(moveTexture_list.get((angle+index%size)));
                this.index+=4;
                break;

            case 0://Character not verticaly moving
                break;

            case 1://Character going up
                angle = 1;
                this.Object.setTextureRegion(moveTexture_list.get((angle+index%size)));
                this.index+=4;
                break;
        }

    }  

/*------------------------------------------------------------------CHECKERS------------------------------------------------------------ */
    public boolean inRange(GraphicCharacter character, Map map){
        int tilewidth = map.getcollisionLayer().getTileWidth();
        float x = getX();
        float y = getY();
        double distanceX = x - character.getX();
        double distanceY = y - character.getY();

        int distance = (int) Math.sqrt(Math.pow(distanceY, 2) + Math.pow(distanceX, 2));

        int X = (int) x/tilewidth;
        int Y = (int) y/tilewidth;
        int endX = (int) (x-distanceX)/tilewidth;
        int endY = (int) (y-distanceY)/tilewidth;

        int signX = (int) Math.signum(-distanceX);
        int signY = (int) Math.signum(-distanceY);

        if(distance <= getRange()*tilewidth && isValidTrajectory(X, Y, endX, endY, signX, signY, map)) return true;
        else return false;
    }
    public boolean inRange(float x2, float y2, Map map){
        int tilewidth = map.getcollisionLayer().getTileWidth();
        float x = getX();
        float y = getY();
        double distanceX = x - x2;
        double distanceY = y - y2;

        int distance = (int) Math.sqrt(Math.pow(distanceY, 2) + Math.pow(distanceX, 2))/tilewidth;

        int X = (int) x/tilewidth;
        int Y = (int) y/tilewidth;
        int endX = (int) (x-distanceX)/tilewidth;
        int endY = (int) (y-distanceY)/tilewidth;

        int signX = (int) Math.signum(-distanceX);
        int signY = (int) Math.signum(-distanceY);

        if(distance <= (int) character.getRange() && isValidTrajectory(X, Y, endX, endY, signX, signY, map)) return true;
        else return false;
    }

/*------------------------------------------------------------ SPAWN ------------------------------------------------------------------ */  

    public void spawn(Map map) {
    }
    

/*---------------------------------------------------------------------------------------------------------------------------------------- */
    public TextureRegion getTexturefromTileset(TiledMapTileSets Tilesets, String Tileset_name, String property, String value, int index){
        //System.out.println("                      SEARCHING TILESET :" + Tileset_name);
        // Search for the tileset in the map
        TiledMapTileSet tileSet = null;
        for (TiledMapTileSet tileset : Tilesets) {
            //System.out.println("TILESET NAME : " + tileset.getName());
            if (tileset.getName().equals(Tileset_name)) {
                tileSet = tileset;
                break;
            }
        }

        if (tileSet != null) {
            int GID = 0;
            TiledMapTile tile = null;
            // Get the texture region from the tileset's tiles
            for (TiledMapTile Tile : tileSet) {
                MapProperties properties = Tile.getProperties();
                if (properties != null && properties.containsKey(property) && properties.containsKey("index")) {
                    Object propertyValue = properties.get(property);
                    Object Index = properties.get("index");
                    if (propertyValue != null && propertyValue.equals(value) && Index.equals(index)) {
                        GID = Tile.getId();
                        tile = tileSet.getTile(GID);
                        break;
                    }
                }
            }
        
            if (tile != null) {
                TextureRegion textureRegion = tile.getTextureRegion();
                if (property.equals("battle") && tile.getProperties().containsKey("melee")) {
                    // Create a new TextureRegion with modified size for the battle animation
                    tile = tileSet.getTile(tile.getId()-1);
                    TextureRegion modifiedRegion = tile.getTextureRegion();
                    modifiedRegion.setRegionHeight(128);
                    modifiedRegion.setRegionWidth(192);
                    
                    
                    return modifiedRegion;
                }

                
                return textureRegion;
            }
        } else System.out.println("tileset is null, extraction failed");
        return null;
    }

    /*-------------------------------------------RENDER---------------------------------------------- */

  public void render(SpriteBatch batch, OrthographicCamera camera){

  } 



}
