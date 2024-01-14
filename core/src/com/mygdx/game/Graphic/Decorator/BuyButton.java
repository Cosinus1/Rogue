package com.mygdx.game.Graphic.Decorator;

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

public class BuyButton extends TextButton {
    private boolean buy;

    private Table table;
    private Stage stage;
    private Item associatedItem;
    private Hero hero;
    private Inventory inventory;


    public BuyButton(String text, Skin skin, Table table, Stage stage, Hero hero, Inventory inventory){
        super(text, skin);
        this.table = table;
        this.stage = stage;
        this.hero = hero;
        this.inventory = inventory;
        buy = false;
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
            if(!buy){
                //si l'achat reussi
                if(hero.buyItem(associatedItem)!=-1){
                    //On ne peut acheter qu'une seul fois une arme
                    if(associatedItem.getType() == ItemType.WEAPON){
                        inventory.removeItem(associatedItem);
                    }   
                    this.setText("Buy succesfuly");
                    buy = true;
                }
                else{
                    this.setText("Not enough money");
                }
            }
            else{
                this.setText("Already Buy");
            }
        }
}
