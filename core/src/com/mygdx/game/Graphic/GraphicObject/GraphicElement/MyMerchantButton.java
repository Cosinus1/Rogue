package com.mygdx.game.Graphic.GraphicObject.GraphicElement;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.*;
import com.mygdx.game.Back.Item.Weapon.*;
import com.mygdx.game.Back.Object.Character.Hero.Hero;

public class MyMerchantButton extends TextButton {
   
    private Table table;
    private Stage stage;
    private Item associatedItem;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private ButtonEditor buttonEditor;

    private boolean showText;


     public MyMerchantButton(String text, Skin skin, Table table, Stage stage){
        super(text, skin);
        this.table = table;
        this.stage = stage;
        showText = false;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        buttonEditor = new ButtonEditor();
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
