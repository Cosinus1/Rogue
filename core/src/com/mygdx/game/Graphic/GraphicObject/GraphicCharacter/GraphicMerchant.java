package com.mygdx.game.Graphic.GraphicObject.GraphicCharacter;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Back.World.Map.Map;

public class GraphicMerchant extends GraphicCharacter {
    
    public GraphicMerchant(String Name, Map map){
        super(Name);

        TiledMapTileSets Tilesets = map.getTiledMap().getTileSets();
        TiledMapTileSet Tileset = getTileSet(Tilesets, Name);
        TextureObject.setTextureRegion((getTexturefromTileset(Tileset,"angle","front",0)));
    }


    public void render(Object object, SpriteBatch spriteBatch, OrthographicCamera camera){
        int scaleFactor = 1;
        //float offsetX=0;
        //float offsetY=0;
        TextureRegion textureRegion = TextureObject.getTextureRegion();
        // Render the object texture based on its position and properties
        float objectX = object.getX() - 16;
        float objectY = object.getY() - 16;

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
        }


}
