package com.mygdx.game.Graphic.GraphicCharacter;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Back.Character.Character;
import com.mygdx.game.Graphic.World.World;
import com.mygdx.game.Graphic.World.Map.Map;

public class GraphicHero extends GraphicCharacter {
    private int angle = 0;
    private int index = 0;

    public GraphicHero(World world, Character character, float x, float y){
        super(character, x, y);
        spawn(world.getDungeon(),null, this, "hero",true);
        spawn(world.getHome(), null, this, "hero",true);
        spawn(world.getTavern(), null, this, "hero",true);
        getHeroTextures(world);
    }

    public void getHeroTextures(World world){
        boolean Boss = false;
        Map map = world.getHome();
        //Setting FPS for slower animation
        int FPS = 10;
        for(int index=0; index<9; index++){
            //Movement Textures
                //Front Texture
                TextureRegion front = getTexturefromTileset(map, "hero", "angle", "front",index, Boss);
                //Back Texture
                TextureRegion back = getTexturefromTileset(map, "hero", "angle", "back",index, Boss);
                //Left Texture
                TextureRegion left = getTexturefromTileset(map, "hero", "angle", "left", index, Boss);
                //Right Texture
                TextureRegion right = getTexturefromTileset(map, "hero", "angle", "right", index, Boss);
            //Battle Textures
                //Front Texture
                TextureRegion Battlefront = getTexturefromTileset(map, "hero", "battle", "front",index, Boss);
                //Back Texture
                TextureRegion Battleback = getTexturefromTileset(map, "hero", "battle", "back",index, Boss);
                //Left Texture
                TextureRegion Battleleft = getTexturefromTileset(map, "hero", "battle", "left", index, Boss);
                //Right Texture
                TextureRegion Battleright = getTexturefromTileset(map, "hero", "battle", "right", index, Boss);
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

    public void move(OrthographicCamera camera, World world){

        if(Gdx.input.isTouched()) {
         Vector3 touchPos = new Vector3();
         touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
         camera.unproject(touchPos);
         Hitbox.x = touchPos.x - 32;
         Object.getProperties().put("x",Hitbox.x);
         Hitbox.y = touchPos.y - 32;
         Object.getProperties().put("y",Hitbox.y);
      }

        TiledMapTileLayer collisionLayer = world.getCurrentcollisionLayer();
        Map map = world.getCurrentMap();
        int tileWidth = 32;
        int tileHeight = 32;
        // Get Tile coordinates
        int tileX = (int) (((float) Object.getProperties().get("x")) / tileWidth);
        int tileY = (int) (((float) Object.getProperties().get("y")) / tileHeight);
        boolean HeroNPC_collision;
        TiledMapTileLayer.Cell nextcell;  
        map.sortObjects();
        HeroNPC_collision = map.PNJcollision(this);
        if(Gdx.input.isKeyPressed(Keys.LEFT)){
           if(collisionLayer!=null)nextcell = collisionLayer.getCell(tileX, tileY);
            else nextcell = null;          
                if (nextcell!=null){
                if (nextcell.getTile().getProperties().containsKey("blocked")==false){
                    Hitbox.x -= 200 * Gdx.graphics.getDeltaTime();
                    Object.getProperties().put("x", Hitbox.x);
                    }
                }
                else if(HeroNPC_collision || angle==1) {
                Hitbox.x  -= 200 * Gdx.graphics.getDeltaTime();
                Object.getProperties().put("x", Hitbox.x);
                }
                //Get the approprite sprite for render
                angle = 2;
                Object.setTextureRegion(moveTexture_list.get((angle+index%360)));
                index+=4; 
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            if(collisionLayer!=null)nextcell = collisionLayer.getCell(tileX+1, tileY);
            else nextcell = null;          
                if (nextcell!=null){
                if (nextcell.getTile().getProperties().containsKey("blocked")==false){
                    Hitbox.x += 200 * Gdx.graphics.getDeltaTime();
                    Object.getProperties().put("x", Hitbox.x);
                    }
                }
                else if(HeroNPC_collision || angle==1) {
                Hitbox.x  += 200 * Gdx.graphics.getDeltaTime();
                Object.getProperties().put("x", Hitbox.x);
                }
                //Get the approprite sprite for render
                angle = 3;
                Object.setTextureRegion(moveTexture_list.get((angle+index%360)));
                index+=4;
        }
        if(Gdx.input.isKeyPressed(Keys.UP)){  
            if(collisionLayer!=null)nextcell = collisionLayer.getCell(tileX, tileY);
            else nextcell = null;          
                if (nextcell!=null){
                if (nextcell.getTile().getProperties().containsKey("blocked")==false){
                    Hitbox.y += 200 * Gdx.graphics.getDeltaTime();
                    Object.getProperties().put("y", Hitbox.y);
                    }
                }
                else if(HeroNPC_collision || angle==1) {
                Hitbox.y  += 200 * Gdx.graphics.getDeltaTime();
                Object.getProperties().put("y", Hitbox.y);
                }
                //Get the approprite sprite for render
                angle = 1;
                Object.setTextureRegion(moveTexture_list.get((angle+index%360)));
                index+=4;
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN)){
            if(collisionLayer!=null)nextcell = collisionLayer.getCell(tileX, tileY-1);
            else nextcell = null;
            
            if (nextcell!=null){
               if (nextcell.getTile().getProperties().containsKey("blocked")==false){
                  Hitbox.y -= 200 * Gdx.graphics.getDeltaTime();
                  Object.getProperties().put("y", Hitbox.y);
                }
            }
            else if(HeroNPC_collision || angle==1) {
               Hitbox.y  -= 200 * Gdx.graphics.getDeltaTime();
               Object.getProperties().put("y", Hitbox.y);
            }
            //Get the approprite sprite for render
            angle = 0;
            Object.setTextureRegion(moveTexture_list.get((angle+index%360)));
            index+=4;

        }          
        // make sure the character stays within the screen bounds
        if(Hitbox.x < 0) Hitbox.x = 0;
        if(Hitbox.x > camera.viewportWidth - 32) Hitbox.x = camera.viewportWidth - 32;
        if(Hitbox.y < -16) Hitbox.y = -16;
        if(Hitbox.y > camera.viewportHeight - 64) Hitbox.y = camera.viewportHeight - 64;
    }

    public void Attack(Map map){
        ArrayList<GraphicEnnemie> PNJinRange = map.lookforPNJinRange(this);
        if(PNJinRange != null){
            int size = PNJinRange.size();
            for(int index = 0; index<size; index++){
                GraphicEnnemie Graphic_ennemie = PNJinRange.get(0);
                Character ennemie = Graphic_ennemie.getCharacter();
                ennemie.recevoirDegats(this.getCharacter().getPower());
                if(ennemie.getPV() <= 0){
                    Graphic_ennemie.kill(map);
                }
            }
        }
    }

    public void GraphicHeroAttack(Map map){
        if(Gdx.input.isKeyPressed(Keys.SPACE)){
            Object.setTextureRegion(battleTexture_list.get((angle+index)%battleTexture_list.size()));
            index+=8;
            //Attack target
            if((int) index%battleTexture_list.size()/13 == 5) Attack(map);
         }
    }

    public void spawn(Map map, TiledMapTileSets tileSets, GraphicHero character, String Tileset_name, boolean pnj) {
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
            int i = 0;
            for(TiledMapTile Tile : tileSet){
                i+=1;
                if(Tile.getProperties().containsKey("basic") && Tile.getProperties().get("angle").equals("front")){
                    GID = Tile.getId();
                    tile = tileSet.getTile(GID);
                    break;
                }
            }
            //System.out.println("number of iterations " + i);
            //System.out.println("GID :" + GID);


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
                map.setHero(character);
            }
            else System.out.println("tile  is null, spawn canceled");
        } else {
            System.out.println("tileset is null, spawn canceled");
        }       
    }

}
