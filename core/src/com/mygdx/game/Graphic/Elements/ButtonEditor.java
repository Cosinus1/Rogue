package com.mygdx.game.Graphic.Elements;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Inventory.InventoryIteratorInterface;
import com.mygdx.game.Back.Item.Item;
import com.mygdx.game.Back.Item.ItemType;
import com.mygdx.game.Graphic.GraphicCharacter.GraphicHero;

public class ButtonEditor {

    //ajoute a la list des Mybutton
    public void createItemButton(InventoryIteratorInterface<Item> iterator, MySkin mySkin, Table table, Stage stage, ArrayList<MyButton> list, Inventory inventory, ItemType type, GraphicHero hero){
        while(iterator.hasnext()){
            Item item = iterator.next();
            MyButton button = new MyButton(item.getType().toString(), mySkin.createStyle(Color.GRAY),table, stage, list,type, inventory,hero, false); 
            //On associe un objet a chaque bouton
            button.setassociatedItem(inventory.getItem(iterator.getPosition()));
            list.add(button);
            table.add(button).padBottom(10);
            table.row();
        }
        stage.addActor(table);
        table.setPosition( 300,stage.getHeight()-160);
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
