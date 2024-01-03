package com.mygdx.game.Back.Character.Ennemie;

import com.badlogic.gdx.maps.tiled.TiledMap;

import com.mygdx.game.Back.Character.Character;
import com.mygdx.game.Back.Inventory.Inventory;

public class Ennemie extends Character {
    protected TiledMap tiledMap;

    public Ennemie(int pv, int defense, int power,int range, Inventory bag, String name){
        super(pv, defense, power, range, bag, name);
    }
}
