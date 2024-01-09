package com.mygdx.game.Back;

public class Force {

    public float value;
    public float ForceX;
    public float ForceY;

    public Force(float value, float X, float Y){
        this.value = value;
        this.ForceX = value*X;
        this.ForceY = value*Y;
    }
    
    public void setX(float X){
        ForceX = value*X;
    }
    public void setY(float Y){
        ForceY = value*Y;
    }
}
