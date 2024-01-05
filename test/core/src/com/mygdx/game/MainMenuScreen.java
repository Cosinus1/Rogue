package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import jdk.javadoc.internal.doclets.formats.html.markup.Text;

public class MainMenuScreen extends ScreenAdapter{



    private Stage stage;

    private Viewport viewport;

    private AssetManager assetManager;

    private Skin skin;
    private Table mainTable;


    private Texture backImage;

    private Label gameTitleLabel;




    public MainMenuScreen(AssetManager assetManager) {

        this.assetManager = assetManager;
        skin = assetManager.get(Assets.SKIN);

        // load the background "music"
        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("battleThemeA.mp3"));

        // start the playback of the background music immediately
        backgroundMusic.setLooping(true);
        backgroundMusic.play();




    }

    @Override
    public void show() {
        viewport = new ExtendViewport(1200,800);
        stage = new Stage(viewport);
        // Create the background image
        backImage = new Texture(Gdx.files.internal("camp.png"));

        mainTable = new Table();
        mainTable.setFillParent(true);

        gameTitleLabel = new Label("MONSTERS", skin); // le nom  du jeu
        gameTitleLabel.setFontScale(4, 4); // Ajuste la taille de la police

        mainTable.add(gameTitleLabel).colspan(2).padBottom(50); // colspan(2) pour fusionner sur deux colonnes et ajustez la disposition
        mainTable.row();

        stage.addActor(mainTable);

        addbutton("Play");
        addbutton("Options");
        //addbutton("Credits");
        addbutton("Quit");

        Gdx.input.setInputProcessor(stage); //met l'effet quand on click sur le bouton


    }

    private TextButton addbutton(String name){
        TextButton button = new TextButton(name,skin);
        button.getLabel().setFontScale(2 ,2);
        mainTable.add(button).width(500).height(60).padBottom(20);
        mainTable.row();

        return button;

    }

    /************* NOM DU JEU ************/


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
    public void resize(int width, int height) {

        viewport.update(width, height, true); // Mettez à jour le viewport avec le redimensionnement




    }



}
