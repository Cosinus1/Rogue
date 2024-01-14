package com.mygdx.game.Back.Object.Element;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Graphic.GraphicObject.GraphicElement.GraphicWall;

public class Wall extends Object {

    public Wall(float x, float y, int width, int height, TextureRegion wallTexture) {
        super(x, y, width, height);
        this.graphicObject = new GraphicWall(width, height);
        this.mass = 1000000000;
        this.friction = 1000000;

        // Set the texture for rendering
        ((GraphicWall) this.graphicObject).setTextureObject(new TextureMapObject(wallTexture));
    }
}
