package com.mygdx.game.Graphic.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Inventory.InventoryIteratorInterface;
import com.mygdx.game.Back.Item.Item;
import com.mygdx.game.Back.Item.ItemType;
import com.mygdx.game.Graphic.Elements.MySkin;
import com.mygdx.game.Graphic.GraphicCharacter.GraphicHero;

public class InventoryScreen implements Screen {
    private final MyGame game;
    private Stage stage;
    private Table potionTable;
    private Table weaponTable;

    private Inventory inventory;
    private ShapeRenderer shapeRenderer;
    private GraphicHero hero;
    private BitmapFont font;
    private SpriteBatch batch;
    private boolean showPotion; 
    private boolean showWeapon;
    
    public InventoryScreen(final MyGame game, GraphicHero hero){
        this.game = game;
        this.inventory = hero.getCharacter().getBag();
        this.hero = hero;
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        batch = new SpriteBatch();
        this.stage = new Stage(new ScreenViewport());
        potionTable = new Table();
        weaponTable = new Table();


        MySkin mySkin = new MySkin();
        //on affiche les armes par default
        showWeapon =true;
        showPotion=false;

        //création du bouton Arme 
        TextButton weaponsButton = new TextButton("Weapon", mySkin.createStyle(Color.CYAN)); 
        //création du bouton pour les potions
        TextButton potionButton = new TextButton("Potion", mySkin.createStyle(Color.NAVY));
        weaponsButton.addListener(new MyClickListenerWeapon(potionButton, weaponsButton, potionTable, weaponTable));
        
    
        potionButton.addListener(new MyClickListenerPotion(potionButton, weaponsButton,potionTable, weaponTable, inventory));

        //positionnement des boutons
        Table table = new Table();
        table.add(weaponsButton).padRight(5);
        table.add(potionButton);
        stage.addActor(table);
        table.setPosition( 600,stage.getHeight()-30);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
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
        batch.begin();
        if(showWeapon) showItems(batch, ItemType.WEAPON);
        //else if(showPotion) showItems(batch,ItemType.POTION);
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();

        if(Gdx.input.isKeyJustPressed(Keys.E)){
         game.setScreen(game.gameScreen);
      }

    }


    public void showItems(SpriteBatch batch, ItemType type){
        int i=0;
        InventoryIteratorInterface<Item> iterator = inventory.getIterator(type);
        while(iterator.hasnext()){
            Item item = iterator.next();
            if(type == ItemType.POTION){
                font.draw(batch,"Potion /" + iterator.getPosition() , 10, stage.getHeight()-70 - i );
            }
            else if(type == ItemType.WEAPON){
                font.draw(batch,"Arme /" + iterator.getPosition() , 10, stage.getHeight()-70 - i );
            }
            i+=30;
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
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
    }

    private class MyClickListenerWeapon extends ClickListener{
        private TextButton weaponsButton;
        private TextButton potionButton;
        private MySkin mySkin;

        public MyClickListenerWeapon(TextButton potionButton, TextButton weaponButton, Table potionTable, Table weaponTable){
            this.weaponsButton = weaponButton;
            this.potionButton = potionButton;
            mySkin = new MySkin();
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            if(!showWeapon){
                showWeapon = true;
                showPotion = false;
                potionTable.clear();
                //On change la couleur du bouton d'arme
                TextButton.TextButtonStyle style = weaponsButton.getStyle();
                style.up = mySkin.createStyle(Color.CYAN).newDrawable("buttonBackground", Color.CYAN);
                weaponsButton.setStyle(style);
                //on change la couleur du bouton potion
                style = potionButton.getStyle();
                style.up = mySkin.createStyle(Color.NAVY).newDrawable("buttonBackground", Color.NAVY);
                potionButton.setStyle(style);
                
            }
        }
        
    }

    private class MyClickListenerPotion extends ClickListener{
        private TextButton weaponsButton;
        private TextButton potionButton;
        private MySkin mySkin;

        public MyClickListenerPotion( TextButton potionButton, TextButton weaponButton, Table potionTable, Table weaponTable, Inventory inventory){
            this.weaponsButton = weaponButton;
            this.potionButton = potionButton;
            mySkin = new MySkin();
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            if(!showPotion){
                showPotion = true;
                showWeapon = false;

                //On change la couleur du bouton d'arme
                TextButton.TextButtonStyle style = weaponsButton.getStyle();
                style.up = mySkin.createStyle(Color.NAVY).newDrawable("buttonBackground", Color.NAVY);
                weaponsButton.setStyle(style);
                //on change la couleur du bouton potion
                style = potionButton.getStyle();
                style.up = mySkin.createStyle(Color.CYAN).newDrawable("buttonBackground", Color.CYAN);
                potionButton.setStyle(style);

                //On créer les boutons de rendu
                InventoryIteratorInterface<Item> iterator = inventory.getIterator(ItemType.POTION);
                while(iterator.hasnext()){
                    Item item = iterator.next();
                    TextButton potionButton = new TextButton(item.getType().toString(), mySkin.createStyle(Color.GRAY)); 
                    potionTable.add(potionButton).padBottom(10);
                    potionTable.row();
                }
                stage.addActor(potionTable);
                potionTable.setPosition( 300,stage.getHeight()-160);
            }
        }
    }  
}   
