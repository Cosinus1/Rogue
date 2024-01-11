package com.mygdx.game.Graphic.GraphicObject;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.mygdx.game.Back.Object.Object;

public class GraphicObject {
    protected String GraphicType;
    protected TextureMapObject TextureObject;

    protected int width;
    protected int height;


    public GraphicObject(int width, int height){
        
        this.width = width;
        this.height = height;

        TextureObject = new TextureMapObject();
        
    }
    /*-------------------------------------------------------------GETTERS---------------------------------------------------------------------- */

    public TextureMapObject getTextureObject(){
        return TextureObject;
    }

    public String getType(){
        return GraphicType;
    }
    /*-------------------------------------------------------------SETTERS---------------------------------------------------------------------- */
    
    public void setTextureObject(TextureMapObject TextureObject){
        this.TextureObject = TextureObject;
    }
    public void setAngle(int OrX, int OrY){
        
    }
    public void setMoveTexture(){

    }
    public void setBattleTexture(){
        
    }
    public  void resetIndex(){

    }

    /*--------------------------------------------------------------RENDER-------------------------------------------------------- */

    public void render(Object object, SpriteBatch spriteBatch, OrthographicCamera camera){
    }
    /*--------------------------------------------------------------DISPOSE----------------------------------------------------- */
}
