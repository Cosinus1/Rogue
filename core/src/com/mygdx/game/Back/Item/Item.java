package com.mygdx.game.Back.Item;

public abstract class Item {
    private ItemType type;
    protected String name;

    public Item(ItemType t, String name){
        this.type = t;
        this.name = name;
    }

    public ItemType getType(){
        return type;
    }
    public String getName(){
        return name;
    }

    public abstract void description();
}
