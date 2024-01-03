package com.mygdx.game.Graphic.Elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Graphic.World.Map.Map;

public class Door {
    private float x;
    private float y;
    private Rectangle bounds;
    private Map map;
    public Texture doorImage;

    public Door(float x, float y, Map map) {
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, 60, 60);
        this.map = map;
        this.doorImage = createRedRectangleTexture();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    private void updateBounds(){
        bounds = new Rectangle(x, y, 60, 60);
    }

    public Map getMap(){
        return this.map;
    }

    private Texture createRedRectangleTexture() {
        Pixmap pixmap = new Pixmap(60, 60, Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fillRectangle(0, 0, 60, 60);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
}

