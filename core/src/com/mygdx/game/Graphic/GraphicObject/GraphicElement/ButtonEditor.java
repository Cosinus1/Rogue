package com.mygdx.game.Graphic.GraphicObject.GraphicElement;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Inventory.InventoryIteratorInterface;
import com.mygdx.game.Back.Item.*;
import com.mygdx.game.Back.Object.Character.Hero.Hero;

public class ButtonEditor {

    //ajoute a la list des Mybutton
    public void createItemButton(InventoryIteratorInterface<Item> iterator, Skin skin, Table table, Stage stage, ArrayList<MyButton> list, Inventory inventory, ItemType type, Hero hero, float x, float y){
        while(iterator.hasnext()){
            Item item = iterator.next();            
            MyButton button = new MyButton(item.getName(), skin,table, stage, list,type, inventory,hero, false); 
            //On associe un objet a chaque bouton
            button.setassociatedItem(inventory.getItem(iterator.getPosition()));
            list.add(button);
            table.add(button).padBottom(5);
            table.row();
        }
        stage.addActor(table);
        table.setPosition( x,y);
    }


    public void changeColor(MySkin mySkin, MyButton button, Color color){
        MyButton.TextButtonStyle style = button.getStyle();
        style.up = mySkin.createStyle(color).newDrawable("buttonBackground", color);
        button.setStyle(style);
    }

    public void changeTexte(MySkin mySkin, MyButton button, String newText){
        button.setText(newText);
    }

}
