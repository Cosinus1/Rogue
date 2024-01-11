package com.mygdx.game.Graphic.Screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.MyGame;
import com.mygdx.game.Back.Inventory.InventoryIteratorInterface;
import com.mygdx.game.Back.Item.Item;
import com.mygdx.game.Back.Item.ItemType;
import com.mygdx.game.Back.Object.Character.Merchant;
import com.mygdx.game.Back.Object.Character.Hero.Hero;
import com.mygdx.game.Graphic.GraphicObject.GraphicElement.ButtonEditor;
import com.mygdx.game.Graphic.GraphicObject.GraphicElement.MyButton;
import com.mygdx.game.Graphic.GraphicObject.GraphicElement.MyMerchantButton;
import com.mygdx.game.Graphic.GraphicObject.GraphicElement.MySkin;

public class MerchantScreen implements Screen {
    private final MyGame game;

    private Merchant merchant;
    private boolean showtext;
    private MySkin mySkin;
    private ButtonEditor buttonEditor;
    private Table table1;
    private Table table2;
    private ArrayList<MyMerchantButton> buttonList;
    private Hero hero;

    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    public MerchantScreen(final MyGame game, Merchant merchant, Hero hero){
        this.game = game;
        this.merchant = merchant;
        batch = new SpriteBatch();
        font = new BitmapFont();
        stage = new Stage();
        shapeRenderer = new ShapeRenderer();
        font.setColor(Color.BLACK); 
        font.getData().setScale(1.5f);
        buttonEditor = new ButtonEditor();
        mySkin = new MySkin();
        table1 = new Table();
        table2 = new Table();
        buttonList = new ArrayList<>();
        this.hero = hero;
        
        showtext = true; //On affiche le texte direct

        InventoryIteratorInterface<Item> iterator = merchant.getInventory().getIterator(ItemType.WEAPON);
        buttonEditor.createMerchantButton(iterator, mySkin, table1, stage, buttonList, merchant.getInventory(),ItemType.ANY, hero, 400, 110);
        InventoryIteratorInterface<Item> iterator2 = merchant.getInventory().getIterator(ItemType.POTION);
        buttonEditor.createMerchantButton(iterator2, mySkin, table2, stage, buttonList, merchant.getInventory(), ItemType.POTION, hero, 800, 110); 
     }


    public void renderText(){
        batch.begin();
            font.draw(batch, merchant.getDialogue(), 210,160);
        batch.end();
    }

    public void renderInventaire(){
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();
    }


    @Override
    public void render(float delta) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(200,50, stage.getWidth()-400, 130);
        shapeRenderer.end();
        if(showtext){
            renderText();
        }
        else{
        //     //On affiche l'inventaire du marchand
          renderInventaire();


        }
            if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
                showtext = false;
            }
            if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) game.setScreen(game.gameScreen);
    }




    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
    
}
