package com.mygdx.game.Back.Object;

public class Force {

    public float ForceX;
    public float ForceY;

    public Force(float valueX, float valueY, float X, float Y){
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
