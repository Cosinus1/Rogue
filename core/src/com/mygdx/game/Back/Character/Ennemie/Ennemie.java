package com.mygdx.game.Back.Character.Ennemie;

import com.badlogic.gdx.maps.tiled.TiledMap;

import com.mygdx.game.Back.Character.Character;
import com.mygdx.game.Back.Inventory.Inventory;

public class Ennemie extends Character {
    protected TiledMap tiledMap;

    public Ennemie(int pv, int defense, int power,int range, Inventory bag, String name){
        super(pv, defense, power, range, bag, name);
    }
    public boolean isAttack_Charged(){
        return this.attack_charged;
    }

    public void toggle_Attack(){
        this.attack_charged = !attack_charged;
    }

    public float getAttackTimer(){
        return this.attackTimer;
    }

    public void setAttackTimer(float delta){
        this.attackTimer = delta;
    }

    public float getAttackCooldown(){
        return this.attackCooldown;
    }
}
