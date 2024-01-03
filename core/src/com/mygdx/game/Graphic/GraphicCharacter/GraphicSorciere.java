package com.mygdx.game.Graphic.GraphicCharacter;

import com.mygdx.game.Back.Character.Character;
import com.mygdx.game.Graphic.World.World;

public class GraphicSorciere extends GraphicEnnemie{
    public GraphicSorciere(Character character, float x, float y, World world){
        super(character, x,y);
        getEnnemieTextures(world, character.getName(), false);
    }
}
