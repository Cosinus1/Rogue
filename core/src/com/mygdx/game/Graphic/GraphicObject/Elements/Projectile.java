package com.mygdx.game.Graphic.GraphicObject.Elements;

import com.mygdx.game.Graphic.GraphicObject.GraphicObject;

public class Projectile extends GraphicObject{
    
    public Projectile(float x, float y, int width, int height){
        super(x, y, width, height);
        this.friction = 1;
    }
}
