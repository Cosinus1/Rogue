
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Door {
    private float x;
    private float y;
    private Rectangle bounds;
    public int TimetoLive;
    private boolean isOpen;
    private Texture sceneryImage;

    public Door(float x, float y, Texture sceneryImage) {
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, 60, 60);
        this.isOpen = false;
        this.sceneryImage = sceneryImage;
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

    public boolean isOpen() {
        return isOpen;
    }

    public void toggle() {
        isOpen = !isOpen;
    }

    public Texture getSceneryImage() {
        return sceneryImage;
    }
}
