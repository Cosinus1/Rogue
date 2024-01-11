package com.mygdx.game.Back;

public class Force {

    public float valueX;
    public float valueY;
    public float ForceX;
    public float ForceY;

    public Force(float valueX, float valueY, float X, float Y){
        this.valueX = valueX;
        this.valueY = valueY;
        this.ForceX = valueX*X;
        this.ForceY = valueY*Y;
    }
    
    public void setForceX(float X){
        ForceX = X;
    }
    public void setForceY(float Y){
        ForceY = Y;
    }
}
