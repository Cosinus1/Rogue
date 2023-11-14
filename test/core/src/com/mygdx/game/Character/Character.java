package com.mygdx.game.Character;


import com.mygdx.game.Inventory.Inventory;
public abstract class Character implements CharacterInterface {
    protected String name;
    public int PV;
    protected int defense;
    protected int power;
    protected int move;
    protected Inventory bag;

    //constructeur
    public Character(String name, int pv, int defense, int power, int move, Inventory bag){
        this.name = name;
        this.PV = pv;
        this.defense = defense;
        this.power = power;
        this.move = move;
        this.bag = bag;
    }

    //retourne le nombre de déplacements que le perso peut faire par tour
    public int getMove(){
        return this.move;
    }
    public int getDefense(){
        return defense;
    }

    public int getPV(){
        return PV;
    }

    public String getName(){
        return name;
    }


    public void recevoir(int degats){
        PV -= degats;
        if(PV < 0){
            PV = 0;
        }
    }

}
