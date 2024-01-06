package com.mygdx.game.Graphic.GraphicObject.GraphicCharacter;

import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.mygdx.game.Back.Character.Character;

public class GraphicSorciere extends GraphicEnnemie{
    public GraphicSorciere(Character character, float x, float y, TiledMapTileSets Tilesets){
        super(character, x,y);
        getEnnemieTextures(Tilesets, character.getName(), false);
    }
}
