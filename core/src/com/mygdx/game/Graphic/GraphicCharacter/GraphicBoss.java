package com.mygdx.game.Graphic.GraphicCharacter;

import com.mygdx.game.Back.Character.Character;
import com.mygdx.game.Graphic.World.World;

public class GraphicBoss extends GraphicEnnemie{
    public GraphicBoss(Character character,float x, float y, World world){
        super(character,x,y);
        this.Object.getProperties().put("boss", "boss");
        getEnnemieTextures(world, character.getName(), true);
    }
}
