package com.mygdx.game.Back.Object.Character;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.Booster;
import com.mygdx.game.Back.Item.Potion;
import com.mygdx.game.Graphic.GraphicObject.GraphicObject;
import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Back.World.Map;

public abstract class Character extends Object{
    
    protected String Class;
    protected int PV;
    protected int PV_max;
    protected int defense;
    protected int power;
    protected int range;
    protected Inventory bag;
    protected String name;
   
    protected boolean attack_charged = false;
    protected float attackTimer = 1f;
    protected float attackCooldown = 2f;


    //constructeur
    public Character(float x, float y, int pv, int defense, int power,int range, Inventory bag){
        super(x, y, 32, 32);
        this.mass = 20;
        this.PV = pv;
        this.PV_max = pv;
        this.defense = defense;
        this.power = power;
        this.range = range;
        this.bag = bag;
    }

/*----------------------------------------- GETTERS -------------------------------------- */

    public String getName(){
        return name;
    }
        /*POSITION */
    
    public float getlastX(){
        return this.lastX;
    }
    public float getlastY(){
        return this.lastY;
    }

    public boolean overlaps(Character character){
        return Hitbox.overlaps(character.getHitbox());
    }
        /*GRAPHIC */
    public GraphicObject getGraphicObject(){
        return graphicObject;
    }
        /*COMBAT */
    public String Class(){
        return Class;
    }
    public int getDefense(){
        return defense;
    }
    public int getPV(){
        return PV;
    }
    public int getPV_max(){
        return PV_max;
    }
    public int getRange(){
        return this.range;
    }
    public float getAttackTimer(){
        return this.attackTimer;
    }
    public float getAttackCooldown(){
        return this.attackCooldown;
    }
    public int getPower(){
        return power;
    }
    /*ITEM */
    public Inventory getBag(){
        return bag;
    }
    
    
    /*----------------------------------------- SETTERS -------------------------------------- */

    public void setName(String name){
        this.name = name;
    }
    /*POSITION */
    public void setX(float x){
        Hitbox.x = x;
    }
    public void setY(float y){
        Hitbox.y = y;
    }
    public void setlastX(float x){
        this.lastX = x;
    }
    public void setlastY(float y){
        this.lastY = y;
    }
    
    public void setAngle(){
        graphicObject.setAngle(OrX, OrY);
    }
    public void setBag(Inventory inventory){
        this.bag = inventory;
    }
    /*COMBAT */
    public void IncrementAttackTimer(float deltaTime){
        this.attackTimer += deltaTime;
    }
    public void toggle_Attack(){
        this.attack_charged = !attack_charged;
    }
    
    /*-----------------------------------------CHECKERS-------------------------------------- */

    public boolean isAttack_Charged(){
        return this.attack_charged;
    }
    public boolean inRange(Character character, Map map){
        int tilewidth = map.getcollisionLayer().getTileWidth();
        float x = getX();
        float y = getY();
        double distanceX = x - character.getX();
        double distanceY = y - character.getY();

        int distance = (int) Math.sqrt(Math.pow(distanceY, 2) + Math.pow(distanceX, 2));

        int X = (int) x/tilewidth;
        int Y = (int) y/tilewidth;
        int endX = (int) (x-distanceX)/tilewidth;
        int endY = (int) (y-distanceY)/tilewidth;

        int signX = (int) Math.signum(-distanceX);
        int signY = (int) Math.signum(-distanceY);

        if(distance <= getRange()*tilewidth && isValidTrajectory(X, Y, endX, endY, signX, signY, map)) return true;
        else return false;
    }
    public boolean inRange(float x2, float y2, Map map){
        int tilewidth = map.getcollisionLayer().getTileWidth();
        float x = getX();
        float y = getY();
        double distanceX = x - x2;
        double distanceY = y - y2;

        int distance = (int) Math.sqrt(Math.pow(distanceY, 2) + Math.pow(distanceX, 2))/tilewidth;

        int X = (int) x/tilewidth;
        int Y = (int) y/tilewidth;
        int endX = (int) (x-distanceX)/tilewidth;
        int endY = (int) (y-distanceY)/tilewidth;

        int signX = (int) Math.signum(-distanceX);
        int signY = (int) Math.signum(-distanceY);

        if(distance <= range && isValidTrajectory(X, Y, endX, endY, signX, signY, map)) return true;
        else return false;
    }
    /*------------------------------------------------------------ SPAWN ------------------------------------------------------------------ */  

    public void spawn(Map map) {
    }
    

    /*----------------------------------------- ITEM -------------------------------------- */  

    public void usePotion(Potion potion){
        PV = PV + potion.getPvSoigner();
        if(PV > PV_max){
            PV = PV_max;
        }
        bag.removeItem(potion);
    }

    public void useBooster(Booster boost){
        defense += boost.getBoostDef();
        power += boost.getBoostDam();
    }

    
/*----------------------------------------- FIGHT -------------------------------------- */  
    public void Attack(Object object, Map map){
        
    }

    public void recevoirDegats(int degats) {
        double mitigationFactor = 1 / (1 + Math.log(1+defense)); // Adjust the exponent value as needed
        
        // Calculate mitigated damage
        int mitigatedDamage = (int) (degats * mitigationFactor);
    
        PV -= mitigatedDamage;
        if(PV<0) PV = 0;
    }

     public void killHero(Map map){
        System.out.println("You Died");
        this.PV = 100;
    }

    /*-------------------------------------------------Battle for Ennemie----------------------------------------*/
    
}
