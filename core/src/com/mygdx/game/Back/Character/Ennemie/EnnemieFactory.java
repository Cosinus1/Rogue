package com.mygdx.game.Back.Character.Ennemie;

import java.util.ArrayList;
import java.util.Random;

import com.mygdx.game.Back.Item.Weapon.*;

public class EnnemieFactory {
    public Ennemie createEnnemie( String request){
        Ennemie ennemie = null;
        if("gobelin".equals(request)){
            Massue massue = new Massue(null,"massue", 3, 4);
            ennemie = new Gobelin(50,0,10,2, 5,null,"gobelin",massue);
        }
        if("sorciere".equals(request)){
            Staff staff = new Staff(null,"staff", 3);
            ennemie = new Sorciere(50,0,10,5, 10,null,"sorciere", staff);
        }
        return ennemie;
    }

    public Ennemie createRandomEnnemie(float x, float y){
        ArrayList<String> list = new ArrayList<String>();
            list.add("gobelin");
            list.add("sorciere");
            Random random = new Random();
        String request = list.get(random.nextInt(list.size()));
        Ennemie ennemie = createEnnemie(request);
        return ennemie;

    }
}
