package com.mygdx.game.Back.Object.Element;

import com.mygdx.game.Graphic.GraphicObject.GraphicElement.GraphicElement;

public class ElementFactory {
    
    public Element createProjectile(float X, float Y){
        Element projectile;
        projectile = new Element(X, Y, 16, 16);
        projectile.setGraphicObject(new GraphicElement(16, 16));
        return projectile;
    }
}
