package com.mygdx.game.Back.Item;

public abstract class Item {
    private ItemType type;
    protected String name;
    protected int value;

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
    public int getValue(){
        return value;
    }
    public void setValue(int value){
        this.value = value;
    }
    
    public abstract void description();
}
