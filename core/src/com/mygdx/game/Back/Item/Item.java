package com.mygdx.game.Back.Item;

public abstract class Item {
    protected ItemType type;
    protected String name;
    protected int value; //value to be buy

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

    public void setValue(int value){
        this.value = value;
    }

    public abstract void description();
}
