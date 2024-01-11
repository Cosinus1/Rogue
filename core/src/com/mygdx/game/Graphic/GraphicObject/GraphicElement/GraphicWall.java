package com.mygdx.game.Graphic.GraphicObject.GraphicElement;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Back.Object.Object;

public class GraphicWall extends GraphicElement {
    
    public GraphicWall(int width, int height){
        super(height, height);
        this.TextureObject.setTextureRegion(new TextureRegion(createRedRectangleTexture(), width, height));
    }
    private Texture createRedRectangleTexture() {
        Pixmap pixmap = new Pixmap(60, 60, Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fillRectangle(0, 0, 60, 60);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
    /*--------------------------------------------------RENDER------------------------------------------------------------- */
    public void render(Object object, SpriteBatch spriteBatch, OrthographicCamera camera){
    int scaleFactor = 1;
    //float offsetX=0;
    //float offsetY=0;
    TextureRegion textureRegion = TextureObject.getTextureRegion();
    // Render the object texture based on its position and properties
    float objectX = object.getX();
    float objectY = object.getY();
    //Render bigger for boss

    float objectWidth = textureRegion.getRegionWidth()*scaleFactor;
    float objectHeight = textureRegion.getRegionHeight()*scaleFactor;
        
        spriteBatch.setProjectionMatrix(camera.combined);

        // Adjust position if it's the battle animation texture
        if (textureRegion.getRegionWidth() == 128 && textureRegion.getRegionHeight() == 128) {
            // Adjust the position to properly center the larger texture
            objectY -= 64*scaleFactor;
        }

    
        spriteBatch.draw(textureRegion, objectX, objectY, objectWidth, objectHeight);

    }
}
