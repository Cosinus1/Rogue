package com.mygdx.game.Back.Object;

public class Force {

    public float ForceX;
    public float ForceY;
    public float friction;

    public Force(float valueX, float valueY, float X, float Y, float friction){
        this.ForceX = valueX*X;
        this.ForceY = valueY*Y;
        this.friction = friction;
    }
    
    public void setForceX(float X){
        ForceX = X;
    }
    public void setForceY(float Y){
        ForceY = Y;
    }
    public void update(){
        this.ForceX/=friction;
        this.ForceY/=friction;
    }
}
