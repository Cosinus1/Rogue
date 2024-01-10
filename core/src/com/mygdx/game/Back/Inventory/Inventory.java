package com.mygdx.game.Back.Inventory;

import java.util.ArrayList;
import com.mygdx.game.Back.Item.Item;
import com.mygdx.game.Back.Item.ItemType;

public class Inventory {
    private ArrayList<Item> inventory;

    //initialise un inventaire vide
    public Inventory(){
        inventory = new ArrayList<>();
    }

    //ajoute un item a l'inventaire
    public void addItem(Item item){
        inventory.add(item);
    }

    //renvoie l'item de l'inventaire correspondant à l'indice donné
    public Item getItem(int index){
        if(index>=0 && index<inventory.size()){
            return inventory.get(index);
        }
        else{
            return null;
        }
    }

    //renvoie une copie de la liste ( de l'inventaire)
    public ArrayList<Item> getItems(){
        return new ArrayList<>(inventory);
    }

    //renvoie un itérateur sur un type d'objet spécifique
    public InventoryIteratorInterface<Item> getIterator(ItemType itemType){
        return new InventoryIterator(this,itemType);
    }
    // public InventoryIterator getIterator(){

    // }

    public void removeItem(Item item){
        inventory.remove(item);
    }
 
}