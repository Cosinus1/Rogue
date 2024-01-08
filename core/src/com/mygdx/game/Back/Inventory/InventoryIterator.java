package com.mygdx.game.Back.Inventory;

import java.util.ArrayList;

import com.mygdx.game.Back.Item.*;

public class InventoryIterator implements InventoryIteratorInterface<Item> {
    private final Inventory bag;
    private int position;
    private final ItemType type;
    
    public InventoryIterator(Inventory bag, ItemType type){
        this.bag = bag;
        this.position = -1;
        this.type = type;
    }

    @Override
    public boolean hasnext(){
        return findNextPosition() != -1;
    }

    //renvoie le prochain élement de la liste
    //renvoie nul si il n'y en a pas
    @Override
    public Item next(){
        position = findNextPosition();
        if(position != -1){
            return bag.getItem(position);
        }
        else{
            return null;
        }
    }


    //renvoie la position du prochain élement du bon type
    //renvoie -1 si il n'y en a pas
    private int findNextPosition(){
        //on fait une copie de l'inventaire pour eviter de le modifier
        ArrayList<Item> items = bag.getItems();
        int position_temp = position;
        while(true){
            position_temp++;
            if(position_temp>=items.size()){
                position_temp = -1;
                break;
            }
            if (type.equals(ItemType.ANY) || items.get(position_temp).getType().equals(type)) {
                // System.out.println("le type de l iterator : "+type);
                // System.out.println("type : "+ items.get(position_temp).getType().equals(type));
                break;
              }
        }
        return position_temp;
    }

    public int getPosition(){
        return position;
    }
}
