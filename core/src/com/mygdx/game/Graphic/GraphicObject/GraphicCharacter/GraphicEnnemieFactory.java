package com.mygdx.game.Graphic.GraphicObject.GraphicCharacter;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.mygdx.game.Back.Object.Character.Ennemie.EnnemieFactory;


public class GraphicEnnemieFactory {

    EnnemieFactory factory;
    TiledMapTileSets TileSets;

    public GraphicEnnemieFactory(TiledMapTileSets TileSets){
        this.factory = new EnnemieFactory();
        this.TileSets = TileSets;
    }

    public GraphicEnnemie createGraphicEnnemie(String request, float x, float y){
        GraphicEnnemie ennemie = null;
        if("gobelin".equals(request)){
            ennemie = new GraphicEnnemie(factory.createEnnemie("gobelin"),x,y, TileSets);
        }
        if("sorciere".equals(request)){
            ennemie = new GraphicEnnemie(factory.createEnnemie("sorciere"),x,y, TileSets);
        }
        return ennemie;
    }

    public GraphicEnnemie createRandomGraphicEnnemie(float x, float y){
        ArrayList<String> list = new ArrayList<String>();
            list.add("gobelin");
            list.add("sorciere");
            Random random = new Random();
        String request = list.get(random.nextInt(list.size()));

        return createGraphicEnnemie(request, x, y); 
    }
}
