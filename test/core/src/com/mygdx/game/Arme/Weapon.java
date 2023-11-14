package com.mygdx.game.Arme;

public class Weapon {
    protected String name;
    protected int power;
    protected int range;

    public Weapon(String name, int power, int range){
        this.name=name;
        this.power=power;
        this.range=range;
    }

    public void presenter(){
        System.out.println("Je suis une arme");
    }

    public int getPower(){
        return power;
    }

    public int getRange(){
        return range;
    }
}
