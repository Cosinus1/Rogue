package com.mygdx.game.Graphic.Decorator;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.*;
import com.mygdx.game.Back.Object.Character.Hero.Hero;

public class MyMerchantButton extends TextButton {
   
    private Table table;
    private Stage stage;
    private Item associatedItem;
    private Hero hero;
    private Inventory inventory;



     public MyMerchantButton(String text, Skin skin, Table table, Stage stage, Hero hero, Inventory inventory){
        super(text, skin);
        this.table = table;
        this.stage = stage;
        this.hero = hero;
        this.inventory = inventory;
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
        Table table2 = new Table();
        MySkin mySkin = new MySkin();
        BuyButton buyButton = new BuyButton("BUY ("+associatedItem.getValue() + "$)", mySkin.createBuyButton(), table2, stage, hero, inventory);
        buyButton.setassociatedItem(associatedItem);
        table2.add(buyButton);
        stage.addActor(table2);
        table2.setPosition(1100,110);
    }

}
