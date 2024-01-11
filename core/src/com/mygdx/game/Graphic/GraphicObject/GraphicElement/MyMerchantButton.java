package com.mygdx.game.Graphic.GraphicObject.GraphicElement;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.Item;
import com.mygdx.game.Back.Item.ItemType;
import com.mygdx.game.Back.Object.Character.Hero.Hero;

public class MyMerchantButton extends TextButton {
   
    private Table table;
    private ItemType type;
    private Inventory inventory;
    private Stage stage;
    private ArrayList<MyMerchantButton> buttonList;
    private Item associatedItem;
    private Hero hero;

     public MyMerchantButton(String text, Skin skin, Table table, Stage stage, ArrayList<MyMerchantButton> list, ItemType type, Inventory inventory, Hero hero){
        super(text, skin);
        this.table = table;
        this.type = type;
        this.inventory = inventory;
        this.stage = stage;
        this.buttonList = list;
        this.hero = hero;
        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                setClickAction();
            }
        });
    }

    public Item getAssociatedItem(){
        return associatedItem;
    }
    public void setassociatedItem(Item item){
        associatedItem = item;
    }

    private void setClickAction(){
        System.out.println("click");
    }
}
