package com.mygdx.game.Graphic.GraphicObject.GraphicCharacter;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Back.Object.Character.Ennemie.Ennemie;

public class GraphicEnnemie extends GraphicCharacter{

    public GraphicEnnemie(String Name, TiledMapTileSets Tilesets){
        super(Name);
        getEnnemieTextures(Tilesets);
        TextureObject.setTextureRegion(moveTexture_list.get(0));
    }


/* --------------------------------------------------GETTERS-------------------------------------------------------------- */
    
/* ----------------------------------------------TEXTURE HANDLING---------------------------------------------------- */

    public void getEnnemieTextures(TiledMapTileSets Tilesets){

        TiledMapTileSet Tileset = getTileSet(Tilesets, Name);
        
        //Setting FPS for slower animation
        int FPS = 10;
        for(int index=0; index<9; index++){
            //Movement Textures
                //Front Texture
                TextureRegion front = getTexturefromTileset(Tileset, "angle", "front",index);
                //Back Texture
                TextureRegion back = getTexturefromTileset(Tileset, "angle", "back",index);
                //Left Texture
                TextureRegion left = getTexturefromTileset(Tileset, "angle", "left", index);
                //Right Texture
                TextureRegion right = getTexturefromTileset(Tileset, "angle", "right", index);
            //Battle Textures
                //Front Texture
                TextureRegion Battlefront = getTexturefromTileset(Tileset, "battle", "front",index);
                //Back Texture
                TextureRegion Battleback = getTexturefromTileset(Tileset, "battle", "back",index);
                //Left Texture
                TextureRegion Battleleft = getTexturefromTileset(Tileset, "battle", "left", index);
                //Right Texture
                TextureRegion Battleright = getTexturefromTileset(Tileset, "battle", "right", index);
            //Death Textures
                //Front Texture
                TextureRegion deathfront = getTexturefromTileset(Tileset, "statut", "dead",index);
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


/*------------------------------------------------------RENDER----------------------------------------------------------- */
    public void render(Object object, SpriteBatch spriteBatch, OrthographicCamera camera){
        Ennemie ennemie = (Ennemie) object;
        int scaleFactor = 1;
        //Render bigger for boss
        if (TextureObject.getProperties().get("boss")=="boss"){
            scaleFactor = 2;
        }
        TextureRegion textureRegion;
        //Get sprite : 
        if(Attack_list.size()!=0) {
            textureRegion = Attack_list.get(0);
            Attack_list.remove(0);
        }else{
            textureRegion = TextureObject.getTextureRegion();
        }
        // Render the object texture based on its position and properties
        float objectX = object.getX() - 16;
        float objectY = object.getY() - 16*scaleFactor;

        float objectWidth = textureRegion.getRegionWidth()*scaleFactor;
        float objectHeight = textureRegion.getRegionHeight()*scaleFactor;
            
            spriteBatch.setProjectionMatrix(camera.combined);

            // Adjust position if it's the battle animation texture
            if (textureRegion.getRegionWidth() == 128 && textureRegion.getRegionHeight() == 128) {
                // Adjust the position to properly center the larger texture
                objectY -= 64*scaleFactor;
            }

        
            spriteBatch.draw(textureRegion, objectX, objectY, objectWidth, objectHeight);
            barlife.drawPNJLifeBar(objectX, objectY, ennemie.getPV(), spriteBatch, this);
    }
}
