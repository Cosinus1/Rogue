package com.mygdx.game.Graphic.Decorator;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Inventory.InventoryIteratorInterface;
import com.mygdx.game.Back.Item.*;
import com.mygdx.game.Back.Item.Weapon.Weapon;
import com.mygdx.game.Back.Object.Character.Hero.Hero;

public class ButtonEditor {

    //ajoute a la list des Mybutton
    public void createItemButton(InventoryIteratorInterface<Item> iterator, MySkin mySkin, Table table, Stage stage, ArrayList<MyButton> list, Inventory inventory, ItemType type, Hero hero){
        while(iterator.hasnext()){
            Item item = iterator.next();            
            MyButton button = new MyButton(item.getName(), mySkin.createStyle(Color.GRAY) ,table, stage, list,type, inventory,hero, false); 
            //On associe un objet a chaque bouton
            button.setassociatedItem(inventory.getItem(iterator.getPosition()));
            list.add(button);
            table.add(button).padBottom(10);
            table.row();
        }
        stage.addActor(table);
        table.setPosition( 300,stage.getHeight() - 50 - 50*inventory.getSize());
    }



    public void changeColor(MySkin mySkin, MyButton button, Color color){
        MyButton.TextButtonStyle style = button.getStyle();
        style.up = mySkin.createStyle(color).newDrawable("buttonBackground", color);
        button.setStyle(style);
    }

    public void changeTexte(MySkin mySkin, MyButton button, String newText){
        button.setText(newText);
    }

    /*------------------------- Merchant Button --------------------------*/
    public void createMerchantButton(InventoryIteratorInterface<Item> iterator, MySkin mySkin, Table table, Stage stage, ArrayList<MyMerchantButton> list, Inventory inventory, float x, float y, Hero hero){
        while(iterator.hasnext()){
            Item item = iterator.next();  
            String name ="";    
            if(item.getType() == ItemType.POTION){
                Potion potion = (Potion)item;
                name = potion.getName() + " (+" + potion.getPvSoigner() + "PV)";
            }
            else if(item.getType() == ItemType.WEAPON){
                Weapon weapon = (Weapon) item;
                name = weapon.getName() + " (power: " + weapon.getPower() + " range: " + weapon.getRange() + ")";
            }         
            MyMerchantButton button = new MyMerchantButton(name, mySkin.createMerchantSkin() ,table, stage, hero, inventory); 
            //On associe un objet a chaque bouton
            button.setassociatedItem(inventory.getItem(iterator.getPosition()));
            list.add(button);
            table.add(button).padBottom(5);
            table.row();
        }
        stage.addActor(table);
        table.setPosition( x,y);
    }


}
