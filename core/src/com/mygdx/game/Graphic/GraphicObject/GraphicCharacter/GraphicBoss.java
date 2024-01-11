package com.mygdx.game.Graphic.GraphicObject.GraphicCharacter;

import com.badlogic.gdx.maps.tiled.TiledMapTileSets;

public class GraphicBoss extends GraphicEnnemie{
    public GraphicBoss(String Name, TiledMapTileSets Tilesets){
        super(Name,Tilesets);
        this.TextureObject.getProperties().put("boss", "boss");
    }
}
