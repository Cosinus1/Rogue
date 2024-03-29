package com.mygdx.game.Graphic.Screen;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.mygdx.game.MyGame;
import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Object.Character.Hero.Archer;
import com.mygdx.game.Back.Object.Character.Hero.Warrior;

public class ChooseClassScreen implements Screen {

    private Stage stage;

    private Viewport viewport;


    private Skin skin;
    private Table mainTable;


    private Texture backImage;

    private Label gameTitleLabel;

    private Music backgroundMusic;


    public ChooseClassScreen(final MyGame game, AssetManager assetManager) {

        skin = assetManager.get(Assets.SKIN);

      
        viewport = new ExtendViewport(1200,800);
        stage = new Stage(viewport);
        // Create the background image
        backImage = new Texture(Gdx.files.internal("GIF/camp.gif"));
       

        mainTable = new Table();
        mainTable.setFillParent(true);

        gameTitleLabel = new Label("ROGUE", skin); // le nom  du jeu
        gameTitleLabel.setFontScale(4, 4); // Ajuste la taille de la police

        mainTable.add(gameTitleLabel).colspan(2).padBottom(50); // colspan(2) pour fusionner sur deux colonnes et ajustez la disposition
        mainTable.row();

        stage.addActor(mainTable);

       // Ajouter des gestionnaires d'événements pour les boutons
        addbutton("Warrior").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Play pour jouer
                Warrior warrior = new Warrior(0,0, 100, 200, 20, 1, new Inventory());
                game.setHero(warrior);
                game.gameScreen = new GameScreen(game);
                game.setScreen(game.gameScreen);
            }
        });
        addbutton("Archer").addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Play pour jouer
                Archer archer = new Archer(0,0, 100, 200, 20, 1, new Inventory());
                game.setHero(archer);
                game.gameScreen = new GameScreen(game);
                game.setScreen(game.gameScreen);
            }
        });

        // Bouton Quit
        addbutton("Back").addListener(new ClickListener(){

            //Quit pour quitter le jeu
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Return to Main Menu
                game.setScreen(game.mainMenuScreen);
            }
        });

    }

    private TextButton addbutton(String name){
        TextButton button = new TextButton(name,skin);
        button.getLabel().setFontScale(2 ,2);
        mainTable.add(button).width(500).height(60).padBottom(20);
        mainTable.row();

        return button;

    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f,1f,15f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        stage.act();
        stage.act(Gdx.graphics.getDeltaTime());

        stage.getBatch().begin();
        // Load the background image
        stage.getBatch().draw(backImage, 0, 0, 1200, 800);
        stage.getBatch().end();
        stage.draw();



    }





    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); //Set Buttons
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // Mettez à jour le viewport avec le redimensionnement
    }
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        // Stop BackgroundMusic
        //backgroundMusic.stop();
        Gdx.input.setInputProcessor(null); //Hide Buttons
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundMusic.dispose();
    }






}

