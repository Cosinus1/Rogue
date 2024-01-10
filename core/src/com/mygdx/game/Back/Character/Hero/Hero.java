package com.mygdx.game.Back.Character.Hero;

import com.mygdx.game.Graphic.World.World;
import com.mygdx.game.Back.Character.Character;
import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.Booster;
import com.mygdx.game.Back.Item.Potion;


public class Hero extends Character{
    protected int exp;
    

    public Hero(World world, int pv, int defense, int power,int range, Inventory bag, String name, int exp){
        super(pv, defense, power, range, bag, name);
        this.exp = exp;       
    }

    public void usePotion(Potion potion){
        this.PV = PV + potion.getPvSoigner();
        if(PV>PV_max){
            PV = PV_max;
        }
    }

    public void useBooster(Booster boost){
        //test de la présence dans le sac
        defense += boost.getBoostDef();
        power += boost.getBoostDam();
        //range += boost.getBoostRange();
        //remove booster du sac;
    }

}
