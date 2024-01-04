package com.mygdx.game.Graphic.GraphicCharacter;

import java.util.ArrayList;
import java.util.Random;

import com.mygdx.game.Back.Character.Ennemie.Ennemie;
import com.mygdx.game.Back.Character.Ennemie.EnnemieFactory;
import com.mygdx.game.Graphic.World.World;

public class GraphicEnnemieFactory {

    EnnemieFactory factory;

    public GraphicEnnemieFactory(){
        this.factory = new EnnemieFactory();
    }

    public GraphicEnnemie createGraphicEnnemie(String request, float x, float y, World world){
        GraphicEnnemie ennemie = null;
        if("gobelin".equals(request)){
            ennemie = new GraphicGobelin(factory.createEnnemie("gobelin"),x,y, world);
        }
        if("sorciere".equals(request)){
            ennemie = new GraphicSorciere(factory.createEnnemie("sorciere"),x,y, world);
        }
        return ennemie;
    }

    public GraphicEnnemie createRandomGraphicEnnemie(float x, float y, World world){
        ArrayList<String> list = new ArrayList<String>();
            list.add("gobelin");
            list.add("sorciere");
            Random random = new Random();
        String request = list.get(random.nextInt(list.size()));

        return createGraphicEnnemie(request, x, y, world); 
    }
}
