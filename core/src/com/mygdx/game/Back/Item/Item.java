package com.mygdx.game.Back.Item;

public abstract class Item {
    private ItemType type;

    public Item(ItemType t){
        this.type = t;
    }

    public ItemType getType(){
        return type;
    }

    public abstract void description();
}
