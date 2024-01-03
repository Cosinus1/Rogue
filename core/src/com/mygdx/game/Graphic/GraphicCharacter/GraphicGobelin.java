package com.mygdx.game.Graphic.GraphicCharacter;

import com.mygdx.game.Back.Character.Character;
import com.mygdx.game.Graphic.World.World;

public class GraphicGobelin extends GraphicEnnemie {
    public GraphicGobelin(Character character, float x, float y, World world){
        super(character, x,y);
        getEnnemieTextures(world, this.character.getName(), false);
    }
}
