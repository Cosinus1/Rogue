package com.mygdx.game.Back.Character.Ennemie;

import com.mygdx.game.Back.Inventory.*;
import com.mygdx.game.Back.Item.Arme.*;

public class Sorciere extends Ennemie{
    private Massue massue ;

    public Sorciere(int pv, int defense, int power,int range, Inventory bag, String name, Massue massue){
        super(pv, defense, power, range, bag, name);
        this.massue = massue;
        this.setName("sorciere");
        this.Class = "sorciere";
        
    }

    public int attaquer(){
        System.out.println("Abracadabra");
        return power + massue.getPower();
    }

    
}