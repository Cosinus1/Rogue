package com.mygdx.game.Graphic.GraphicObject.Elements;

public class ElementFactory {
    
    public Element createProjectile(float X, float Y){
        Element projectile;
        projectile = new Element(X, Y, 16, 16);
        return projectile;
    }
}
