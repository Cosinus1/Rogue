package com.mygdx.game.Graphic.GraphicCharacter;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;

import com.mygdx.game.Back.Character.Character;
import com.mygdx.game.Graphic.World.Map.Map;
import com.mygdx.game.Graphic.Elements.*;

public class GraphicCharacter {
    protected Character character;

    protected Rectangle Hitbox;
    protected float lastX, lastY;
    protected TextureMapObject Object;
    protected ArrayList<TextureRegion> moveTexture_list;
    protected ArrayList<TextureRegion> battleTexture_list;
    protected ArrayList<TextureRegion> deathTexture_list;
    protected BarLife barlife;

    //Status varables
    private int death = 0;
    private int index = 0; //to get the appropriate sprite (follow track of movement)
    private int angle = 0; 

    public GraphicCharacter(Character character2, float x, float y){
        this.character = character2;
        this.Hitbox = new Rectangle(x, y, 64, 64);
        Object = new TextureMapObject();
        moveTexture_list = new ArrayList<>();
        battleTexture_list = new ArrayList<>();
        deathTexture_list = new ArrayList<>();
        barlife = new BarLife();
    }

/*----------------------------------------- GETTERS -------------------------------------- */

    public float getX(){
        return this.Hitbox.x;
    }
    public float getY(){
        return this.Hitbox.y;
    }
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
    public Rectangle getHitbox(){
        return Hitbox;
    }
    public BarLife getBarlife(){
        return barlife;
    }

    public boolean overlaps(GraphicCharacter character){
        return Hitbox.overlaps(character.getHitbox());
    }
/*----------------------------------------- SETTERS -------------------------------------- */  
    
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
    public void setX(float x){
        Hitbox.x = x;
    }
    public void setY(float y){
        Hitbox.y = y;
    }
    public void setObject(String key, float x){
        Object.getProperties().put(key,x);
    }
    public void setPosition(float x, float y){
        this.Hitbox.x = x;
        this.Hitbox.y = y;
        this.Object.getProperties().put("x",x);
        this.Object.getProperties().put("y",y);
    }
    public void setMoveTexture(int x, int y){
        //Check horizontal movement
        switch (x) {
            case -1://Character going left
                angle = 2;
                this.Object.setTextureRegion(moveTexture_list.get((angle+index%360)));
                this.index+=4;
                break;

            case 0://Character not horizontaly moving
                break;

            case 1://Character going right
                angle = 3;
                this.Object.setTextureRegion(moveTexture_list.get((angle+index%360)));
                this.index+=4;
                break;
        }
        //Check vertical movement
        switch (y){
            case -1://Character going down
                angle = 0;
                this.Object.setTextureRegion(moveTexture_list.get((angle+index%360)));
                this.index+=4;
                break;

            case 0://Character not verticaly moving
                break;

            case 1://Character going up
                angle = 1;
                this.Object.setTextureRegion(moveTexture_list.get((angle+index%360)));
                this.index+=4;
                break;
        }

    }  

/*----------------------------------------- SPAWN -------------------------------------- */  

    public TextureRegion getTexturefromTileset(Map map, String Tileset_name, String property, String value, int index, boolean Boss){
        // Search for the tileset in the map
        TiledMapTileSet tileSet = null;
        for (TiledMapTileSet tileset : map.getTiledMap().getTileSets()) {
            if (tileset.getName().equals(Tileset_name)) {
                tileSet = tileset;
                break;
            }
        }

        if (tileSet != null) {
            int GID = 0;
            TiledMapTile tile = null;
            // Get the texture region from the tileset's tiles
            int i = 0;
            for (TiledMapTile Tile : tileSet) {
                MapProperties properties = Tile.getProperties();
                if (properties != null && properties.containsKey(property) && properties.containsKey("index")) {
                    i++;
                    Object propertyValue = properties.get(property);
                    Object Index = properties.get("index");
                    if (propertyValue != null && propertyValue.equals(value) && Index.equals(index)) {
                        //System.out.println("index : " + index + "\n" + "number of iterations : " + i + "\n //////////////");
                        GID = Tile.getId();
                        tile = tileSet.getTile(GID);
                        break;
                    }
                }
            }
        
            if (tile != null) {
                TextureRegion textureRegion = tile.getTextureRegion();
                if (property.equals("battle")) {
                    // Create a new TextureRegion with modified size for the battle animation
                    TextureRegion modifiedRegion = new TextureRegion(textureRegion);
                    modifiedRegion.setRegionWidth(128);
                    modifiedRegion.setRegionHeight(128);
                    
                    return modifiedRegion;
                }

                
                return textureRegion;
            }
        } else System.out.println("tileset is null, extraction failed");
        return null;
    }

/*----------------------------------------- FIGHT -------------------------------------- */  
    
    public boolean PNJAttack(Map map){
        ArrayList<GraphicEnnemie> PNJinRange = map.lookforPNJinRange(this);
        if(PNJinRange != null){
            int size = PNJinRange.size();
            for(int index = 0; index<size; index++){
                GraphicCharacter ennemie = PNJinRange.get(index);
                if(this.getCharacter().recevoirDegats(ennemie.getCharacter().getPower())){
                    this.getCharacter().killHero(map);
                    return true;
                }else System.out.println("hero has been hit : PV =" + this.getCharacter().getPV());
            }
        }
        return false;
    }
    




}
