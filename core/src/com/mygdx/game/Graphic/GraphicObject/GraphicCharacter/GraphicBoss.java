package com.mygdx.game.Graphic.GraphicObject.GraphicCharacter;

import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.mygdx.game.Back.Character.Ennemie.Ennemie;

public class GraphicBoss extends GraphicEnnemie{
    public GraphicBoss(Ennemie character,float x, float y, TiledMapTileSets Tilesets){
        super(character,x,y, Tilesets);
        this.Object.getProperties().put("boss", "boss");
    }
}
