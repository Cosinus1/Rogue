package com.mygdx.game.Graphic.GraphicObject.Elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;

import com.mygdx.game.Graphic.GraphicObject.GraphicObject;
import com.mygdx.game.Graphic.GraphicObject.GraphicCharacter.GraphicCharacter;
import com.mygdx.game.Graphic.World.Map.Map;

public class Wall extends GraphicObject{
        
    public Wall(float x, float y, int width, int height){
        super(x, y, width, height);
        this.friction = 1000000;
        this.Object.setTextureRegion(new TextureRegion(createRedRectangleTexture(), width, height));
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
    public void render(SpriteBatch spriteBatch, OrthographicCamera camera){
    int scaleFactor = 1;
    //float offsetX=0;
    //float offsetY=0;
    TextureRegion textureRegion = Object.getTextureRegion();
    // Render the object texture based on its position and properties
    float objectX = getX();
    float objectY = getY();
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
    /*-----------------------------------------------------------------SETTERS----------------------------------------------------------- */
    
    
    /*----------------------------------------------------------------CHECKERS---------------------------------------------------------- */
    public boolean inRange(GraphicCharacter character, Map map){
        float x = getX();
        float y = getY();
        double distanceX = x - character.getX();
        double distanceY = y - character.getY();

        int distance = (int) Math.sqrt(Math.pow(distanceY, 2) + Math.pow(distanceX, 2))/32;

        int X = (int) x/32;
        int Y = (int) y/32;
        int endX = (int) (x-distanceX)/32;
        int endY = (int) (y-distanceY)/32;

        int signX = (int) Math.signum(-distanceX);
        int signY = (int) Math.signum(-distanceY);

        //System.out.println(" signX : " + signX + "signY : " + signY);

        if(distance <= 1 && isValidTrajectory(X, Y, endX, endY, signX, signY, map)) return true;
        else return false;
    }
   
}
