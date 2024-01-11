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


import com.mygdx.game.Graphic.GraphicObject.GraphicObject;
import com.mygdx.game.Graphic.GraphicObject.GraphicElement.BarLife;

public class GraphicCharacter extends GraphicObject{

    protected String Name;

    protected ArrayList<TextureRegion> moveTexture_list;
    protected ArrayList<TextureRegion> battleTexture_list;
    protected ArrayList<TextureRegion> deathTexture_list;
    protected ArrayList<TextureRegion> Attack_list;//On going attack sprites
    protected BarLife barlife;

    //Status variables
    protected int death = 0;
    protected int index = 0; //to get the appropriate sprite (follow track of movement)
    protected int angle = 0;

    protected boolean isFinishedAttack = false;

    public GraphicCharacter(String Name){
        super(32, 32);
        this.Name = Name;
        //Init Texture lists
        moveTexture_list = new ArrayList<>();
        battleTexture_list = new ArrayList<>();
        deathTexture_list = new ArrayList<>();
        Attack_list = new ArrayList<>();
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
    
    public TextureMapObject getTextureObject(){
        return TextureObject;
    }
    public int getIndex(){
        return index;
    }
    
    public BarLife getBarlife(){
        return barlife;
    }

/*----------------------------------------- SETTERS -------------------------------------- */  
    
    public void setAngle(int OrX, int OrY){
        switch (OrX) {
            case -1://Character going left
                angle = 2;
                break;

            case 0://Character not horizontaly moving
                break;

            case 1://Character going right
                angle = 3;
                break;
        }
        //Check vertical movement
        switch (OrY){
            case -1://Character going down
                angle = 0;
                break;

            case 0://Character not verticaly moving
                break;

            case 1://Character going up
                angle = 1;
                break;
        }

    }

    public void incrementDeathTimer(){
        death++;
    }
    
    public void resetIndex(){
        this.index = 0;
    }
    

    public void setMoveTexture(){
        int size = moveTexture_list.size();

        //Set texture according to index & angle
        this.TextureObject.setTextureRegion(moveTexture_list.get((angle+index%size)));
        this.index+=4;
    }
    //Set appropriate Sprite for battle animation
    public void setBattleTexture(){

        int size = battleTexture_list.size();
        //reset index
        index = 0;
        while(index<=size){
            Attack_list.add(new TextureRegion(battleTexture_list.get((angle+index%size))));
            this.index +=8;
        }        
    }
    


/*------------------------------------------------------------------CHECKERS------------------------------------------------------------ */
    
    public boolean isFinishedAttack(){
        return this.isFinishedAttack;
    }

/*---------------------------------------------------------------------------------------------------------------------------------------- */
    public TiledMapTileSet getTileSet(TiledMapTileSets Tilesets, String Tileset_name){
        System.out.println("                      SEARCHING TILESET :" + Tileset_name);
        // Search for the tileset in the map
        TiledMapTileSet tileSet = null;
        for (TiledMapTileSet tileset : Tilesets) {
            System.out.println("TILESET NAME : " + tileset.getName());
            if (tileset.getName().equals(Tileset_name)) {
                tileSet = tileset;
                break;
            }
        }
        return tileSet;
    }
    public TextureRegion getTexturefromTileset(TiledMapTileSet tileSet, String property, String value, int index){
        
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
