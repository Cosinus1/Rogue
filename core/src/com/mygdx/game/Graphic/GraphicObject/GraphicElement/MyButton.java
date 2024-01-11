package com.mygdx.game.Graphic.GraphicObject.GraphicElement;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Back.Inventory.*;
import com.mygdx.game.Back.Item.*;
import com.mygdx.game.Back.Item.Weapon.*;
import com.mygdx.game.Back.Object.Character.Hero.Archer;
import com.mygdx.game.Back.Object.Character.Hero.Hero;
import com.mygdx.game.Back.Object.Character.Hero.Warrior;

public class MyButton extends TextButton {
    private boolean click; //true si deja cliqué
    private boolean mainButton;//true pour les 2 boutons du haut

    private MyButton button;
    private Table table;
    private ItemType type;
    private Inventory inventory;
    private Stage stage;
    private ArrayList<MyButton> buttonList;
    private Item associatedItem;
    private Hero hero;

    public MyButton(String text, Skin skin, Table table,Stage stage,ArrayList<MyButton> list, ItemType type, Inventory inventory, Hero hero, boolean main){
        super(text, skin);
        click = false;
        this.table = table;
        this.type = type;
        this.inventory = inventory;
        this.stage = stage;
        this.buttonList = list;
        this.hero = hero;
        this.mainButton = main;
        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                setClickAction();
                click = !click;
            }
        });
    }

    public Item getAssociatedItem(){
        return associatedItem;
    }
    public void setButton(MyButton button){
        this.button = button;
    }
    public void setClick(boolean bool){
        this.click = bool;
    }
    public void setassociatedItem(Item item){
        associatedItem = item;
    }

    public boolean isclick(){
        return click;
    }

    private void setClickAction(){
        ButtonEditor buttonEditor = new ButtonEditor();
        MySkin mySkin = new MySkin();
        if(mainButton){
            Color newNavy = new Color(0,0,1,0.7f);
            //on change les couleurs
            buttonEditor.changeColor(mySkin,this, newNavy);
            buttonEditor.changeColor(mySkin, button, Color.NAVY);
            //On change les bouttons en dessous
            table.clear();
            InventoryIteratorInterface<Item> iterator = inventory.getIterator(type);
            buttonEditor.createItemButton(iterator, mySkin.createStyle(newNavy), table, stage, buttonList, inventory, type, hero, 300, stage.getHeight() - 160);
        }
        else{
            if(!click){
                Color newGreen = new Color(0,1,0,0.7f);
                //Seul le bouton cliqué doit être vert
                for(MyButton button : buttonList){
                    buttonEditor.changeColor(mySkin, button, Color.GRAY);
                    buttonEditor.changeTexte(mySkin, button, button.getAssociatedItem().getName());
                    button.setClick(false);
                }
                //On change la couleur du bouton
                buttonEditor.changeColor(mySkin, this, newGreen);
                buttonEditor.changeTexte(mySkin, this, "USE");
            }
            //si a été cliqué une première fois alors on veut utiliser l'objet
            else{
                if(type == ItemType.POTION){
                    hero.usePotion((Potion) associatedItem);
                }
                else if(type == ItemType.WEAPON){
                    if(hero instanceof Archer){
                        ((Archer)hero).changeWeapon((Arc)associatedItem);
                    }
                    else if(hero instanceof Warrior){
                        ((Warrior)hero).changeWeapon((Sword)associatedItem);
                    }
                }
                //On supprime le bouton
                    this.remove();
            }
        }

    }

}
