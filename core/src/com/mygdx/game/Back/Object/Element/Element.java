package com.mygdx.game.Back.Object.Element;

import java.util.ArrayList;
import java.util.Iterator;

import com.mygdx.game.Back.World.Map.Map;

import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Back.Object.Character.Character;
import com.mygdx.game.Back.Object.Character.Ennemie.Ennemie;
import com.mygdx.game.Graphic.GraphicObject.GraphicElement.GraphicElement;

public class Element extends Object{
    public int Power;
    public String target;
    
    public Element(float x, float y, int width, int height){
        super(x, y, width, height);
        this.graphicObject = new GraphicElement(width, height);
        this.friction = 1;
        this.Power = 10;
        this.target = "enemy";
    }
    /*-----------------------------------------------------------------SETTERS----------------------------------------------------------- */
    public void setTarget(String target){
        this.target = target;
    }
    /*----------------------------------------------------------------CHECKERS---------------------------------------------------------- */
    
    /*--------------------------------------------------------ATTACK----------------------------------------------------------------- */
    public void LookforAttack(Map map){
        ArrayList<Ennemie> Enemies = map.getNPCs();
        if(Enemies!=null){
            Iterator<Ennemie> Iterator = Enemies.iterator();
            while(Iterator.hasNext()){
                Ennemie enemy = Iterator.next();
                if(enemy.getHitbox().overlaps(Hitbox) && target == "enemy") this.Attack(enemy);
            }  
        }
        Character Hero = (Character) map.getHero();
        if(Hero.getHitbox().overlaps(Hitbox) && target == "hero") this.Attack(Hero);     
    }
    
    public void Attack(Character character){
        character.recevoirDegats(Power);
    }
}
