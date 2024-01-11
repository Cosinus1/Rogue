package com.mygdx.game.Graphic.GraphicObject.GraphicElement;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Graphic.GraphicObject.GraphicObject;

public class GraphicElement extends GraphicObject{

    public GraphicElement(int width, int height){
        super(width, height);
        this.GraphicType = "Element";
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
    float objectX = object.getX() - 8;
    float objectY = object.getY() - 8;
    TextureRegion textureRegion = TextureObject.getTextureRegion();

    float objectWidth = textureRegion.getRegionWidth()*scaleFactor;
    float objectHeight = textureRegion.getRegionHeight()*scaleFactor;
        
        spriteBatch.setProjectionMatrix(camera.combined);

    
    
        spriteBatch.draw(textureRegion, objectX, objectY, objectWidth, objectHeight);

    }
    
}
