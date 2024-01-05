package com.mygdx.game.Graphic.GraphicCharacter;

import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.mygdx.game.Back.Character.Character;

public class GraphicGobelin extends GraphicEnnemie {
    public GraphicGobelin(Character character, float x, float y, TiledMapTileSets Tilesets){
        super(character, x,y);
        getEnnemieTextures(Tilesets, this.character.getName(), false);
    }
}
