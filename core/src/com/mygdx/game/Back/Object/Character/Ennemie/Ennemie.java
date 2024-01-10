package com.mygdx.game.Back.Object.Character.Ennemie;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.Weapon.Weapon;
import com.mygdx.game.Back.Object.Character.Character;
import com.mygdx.game.Graphic.World.Map.*;

public class Ennemie extends Character {
    protected TiledMap tiledMap;
    protected Weapon weapon;
    protected int detectionRange;

    public Ennemie(int pv, int defense, int power,int combatRange, int detectionRange, Inventory bag, String name, Weapon weapon){
        super(pv, defense, power, combatRange, bag, name);
        this.weapon = weapon;
        this.detectionRange = detectionRange;
        this.range = weapon.getRange();
    }
    /*----------------------------------CHECKERS------------------------------------- */
    public boolean isAttack_Charged(){
        return this.attack_charged;
    }
    public int getDetecRange(){
        return this.detectionRange;
    }
    /*----------------------------------SETTERS------------------------------------- */
    public void toggle_Attack(){
        this.attack_charged = !attack_charged;
    }

    public void setAttackTimer(float delta){
        this.attackTimer = delta;
    }
    /*----------------------------------GETTERS------------------------------------- */
    public float getAttackTimer(){
        return this.attackTimer;
    }
    public float getAttackCooldown(){
        return this.attackCooldown;
    }
    public int getRange(){
        return this.range;
    }
    /*---------------------------------ATTACK--------------------------------------- */
    public void Attack(float X, float Y, int OrX, int OrY, Map map){
        
    }
}
