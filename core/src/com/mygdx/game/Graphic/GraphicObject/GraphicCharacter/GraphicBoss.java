package com.mygdx.game.Graphic.GraphicObject.GraphicCharacter;

import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.mygdx.game.Back.Character.Character;

public class GraphicBoss extends GraphicEnnemie{
    public GraphicBoss(Character character,float x, float y, TiledMapTileSets Tilesets){
        super(character,x,y);
        this.Object.getProperties().put("boss", "boss");
        getEnnemieTextures(Tilesets, character.getName(), true);
    }
}
