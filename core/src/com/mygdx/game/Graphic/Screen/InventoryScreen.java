package com.mygdx.game.Graphic.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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

    private Inventory inventory;
    private ShapeRenderer shapeRenderer;
    private GraphicHero hero;
    private BitmapFont font;
    private SpriteBatch batch;
    
    public InventoryScreen(final MyGame game, GraphicHero hero){
        this.game = game;
        this.inventory = hero.getCharacter().getBag();
        this.hero = hero;
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        batch = new SpriteBatch();
        this.stage = new Stage(new ScreenViewport());

        MySkin mySkin = new MySkin();
        
        //création du bouton Arme 
        TextButton weaponsButton = new TextButton("Weapon", mySkin.createStyle(Color.NAVY)); 
        /*
        //ajout de l'icone d'arme    
        Texture weaponTexture = new Texture("PNG/weapon.png");
        TextButtonStyle weaponStyle = weaponsButton.getStyle();
        
        //create drawable for back
        TextureRegionDrawable weaponDrawable = new TextureRegionDrawable(new TextureRegion(weaponTexture));
        weaponDrawable.setMinSize(64,64);
        weaponStyle.up = weaponDrawable;
        //set du style
        weaponsButton.setStyle(weaponStyle);
        //add icone to button
        weaponStyle.up = new TextureRegionDrawable(new TextureRegion(weaponTexture));
        */
        //création du bouton pour les potions
        TextButton potionButton = new TextButton("Potion", mySkin.createStyle(Color.NAVY));
        /*
        Texture potionTexture = new Texture("PNG/potion.png");
        TextButtonStyle potionStyle = potionButton.getStyle();
        potionStyle.up = new TextureRegionDrawable(new TextureRegion(potionTexture));
        //redimensioné l'image
        TextureRegionDrawable potionDrawable = new TextureRegionDrawable(new TextureRegion(potionTexture));
        potionDrawable.setMinSize(64,64);
        //set button
        potionStyle.up = potionDrawable;
        potionButton.setStyle(potionStyle);
        */
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
            showPotions(batch);
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();

        if(Gdx.input.isKeyJustPressed(Keys.E)){
         game.setScreen(game.gameScreen);
      }

    }


    public void showPotions(SpriteBatch batch){
        InventoryIteratorInterface<Item> iterator = inventory.getIterator(ItemType.POTION);
        while(iterator.hasnext()){
            Item item = iterator.next();
            font.draw(batch,"Potion" + item.getType().toString(), 10,stage.getHeight()-70-iterator.getPosition() );
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
}   
