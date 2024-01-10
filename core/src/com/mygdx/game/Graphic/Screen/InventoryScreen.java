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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.MyGame;
import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Inventory.InventoryIteratorInterface;
import com.mygdx.game.Back.Item.Item;
import com.mygdx.game.Back.Item.ItemType;
import com.mygdx.game.Graphic.Elements.ButtonEditor;
import com.mygdx.game.Graphic.Elements.MyButton;
import com.mygdx.game.Graphic.Elements.MySkin;
import com.mygdx.game.Graphic.GraphicCharacter.GraphicHero;

public class InventoryScreen implements Screen{
    private final MyGame game;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private GraphicHero hero;
    private Inventory inventory;
    private MySkin mySkin;
    private Table table;
    private Table table2;

    private ButtonEditor buttoncreator;
    private MyButton potionButton;
    private MyButton weaponButton;
    private ArrayList<MyButton> potionList;
    private ArrayList<MyButton> weaponList;


    public InventoryScreen(final MyGame game, GraphicHero hero){
        this.game = game;
        this.inventory = hero.getCharacter().getBag();
        this.stage = new Stage();
        mySkin = new MySkin();
        shapeRenderer = new ShapeRenderer();
        table = new Table();
        table2 = new Table();
        weaponList = new ArrayList<>();
        potionList = new ArrayList<>();

        ButtonEditor buttonEditor = new ButtonEditor();
        MySkin mySkin = new MySkin();

        //Boutons de base
        Color newNavy = new Color(0,0,1,0.7f); 
        weaponButton = new MyButton("WEAPON", mySkin.createStyle(newNavy),table2,stage,weaponList,ItemType.WEAPON,inventory,hero, true);
        weaponButton.setClick(true);
        potionButton = new MyButton( "POTION", mySkin.createStyle(Color.NAVY),table2,stage,potionList,ItemType.POTION,inventory,hero, true);
        weaponButton.setButton(potionButton);
        potionButton.setButton(weaponButton);
        //positionnement des boutons 
        table.add(weaponButton).padRight(5);
        table.add(potionButton);
        table.row();
        stage.addActor(table);
        table.setPosition( 600,stage.getHeight()-30);

        //Arme affiché de base
        InventoryIteratorInterface<Item> iterator = inventory.getIterator(ItemType.WEAPON);
        buttonEditor.createItemButton(iterator, mySkin, table2, stage,weaponList, inventory,ItemType.WEAPON, hero);
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
        Gdx.input.setInputProcessor(null);
    }
    @Override
    public void dispose() {
    }

}
