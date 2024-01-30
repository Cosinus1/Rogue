package com.mygdx.game.Back.Object.Character.Ennemie;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.mygdx.game.Back.Item.Weapon.*;
import com.mygdx.game.Graphic.GraphicObject.GraphicCharacter.GraphicEnnemie;

public class EnnemieFactory {
    protected TiledMapTileSets Tilesets;

    public EnnemieFactory(TiledMapTileSets Tilesets){
        this.Tilesets = Tilesets;
    }
    public Ennemie createEnnemie(float x, float y, String request){
        Ennemie ennemie = null;
        if("gobelin".equals(request)){
            Massue massue = new Massue("massue", 30, 1);
            ennemie = new Gobelin(x,y,50,0,10,2,5,null,massue);
        }
        if("sorciere".equals(request)){
            Staff staff = new Staff("staff", 3, 5);
            ennemie = new Sorciere(x, y, 50,0,10,5,10,null, staff);
        }
        ennemie.setGraphicObject(new GraphicEnnemie(ennemie.getName(), Tilesets));
        return ennemie;
    }

    public Ennemie createRandomEnnemie(float x, float y){
        ArrayList<String> list = new ArrayList<String>();
            list.add("gobelin");
            list.add("sorciere");
            Random random = new Random();
        String request = list.get(random.nextInt(list.size()));
        Ennemie ennemie = createEnnemie(x, y, request);
        return ennemie;

    }
}
