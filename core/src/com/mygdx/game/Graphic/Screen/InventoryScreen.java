package com.mygdx.game.Graphic.Screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.MyGame;
import com.mygdx.game.Back.Inventory.*;
import com.mygdx.game.Back.Item.*;
import com.mygdx.game.Back.Object.Character.Hero.Hero;
import com.mygdx.game.Graphic.GraphicObject.GraphicElement.Button.ButtonEditor;
import com.mygdx.game.Graphic.GraphicObject.GraphicElement.Button.MyButton;
import com.mygdx.game.Graphic.GraphicObject.GraphicElement.Button.MySkin;

public class InventoryScreen implements Screen{
    private final MyGame game;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private Hero hero;
    private Inventory inventory;
    private MySkin mySkin;
    private Table table;
    private Table table2;

    private ButtonEditor buttonEditor;
    private MyButton potionButton;
    private MyButton weaponButton;
    private ArrayList<MyButton> potionList;
    private ArrayList<MyButton> weaponList;


    public InventoryScreen(final MyGame game, Hero hero){
        this.game = game;
        this.inventory = hero.getBag();
        this.stage = new Stage();
        mySkin = new MySkin();
        shapeRenderer = new ShapeRenderer();
        table = new Table();
        table2 = new Table();
        weaponList = new ArrayList<>();
        potionList = new ArrayList<>();

        buttonEditor = new ButtonEditor();
        MySkin mySkin = new MySkin();

        //Boutons de base
        weaponButton = new MyButton("WEAPON", mySkin.createStyle(Color.NAVY),table2,stage,weaponList,ItemType.WEAPON,inventory,hero, true);
        potionButton = new MyButton( "POTION", mySkin.createStyle(Color.NAVY),table2,stage,potionList,ItemType.POTION,inventory,hero, true);
        weaponButton.setButton(potionButton);
        potionButton.setButton(weaponButton);
        //positionnement des boutons 
        table.add(weaponButton).padRight(5);
        table.add(potionButton);
        table.row();
        stage.addActor(table);
        table.setPosition( 600,stage.getHeight()-30);
    }

    public void reset(){ 
        buttonEditor.changeColor(mySkin, potionButton, Color.NAVY);
        buttonEditor.changeColor(mySkin, weaponButton,Color.NAVY);
        potionButton.setClick(false);
        weaponButton.setClick(false);
        table2.clear();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(0,stage.getHeight()- 5, stage.getWidth(), 3);
            shapeRenderer.rect(0,stage.getHeight()- 60, stage.getWidth(), 3);
            shapeRenderer.rect(598,stage.getHeight()- 60, 4,56 );
        shapeRenderer.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();

        if(Gdx.input.isKeyJustPressed(Keys.E)){
            game.setScreen(game.gameScreen);
        }

    }

    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
     @Override
    public void show() {
        
        Gdx.input.setInputProcessor(stage);
       
    }
    @Override
    public void hide() {
        table2.clear();
        reset();
        Gdx.input.setInputProcessor(null);
    }
    @Override
    public void dispose() {
    }

}
