package com.mygdx.game.Back.Object.Element;

import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Graphic.GraphicObject.GraphicElement.GraphicWall;

public class Wall extends Object{
        
    public Wall(float x, float y, int width, int height){
        super(x, y, width, height);
        this.graphicObject = new GraphicWall(width, height);
        this.mass = 1000000000;
        
        this.friction = 1000000;
    }
    /*-----------------------------------------------------------------SETTERS----------------------------------------------------------- */
    
    
    /*----------------------------------------------------------------CHECKERS---------------------------------------------------------- */
    
   
}
