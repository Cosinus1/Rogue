package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen extends ScreenAdapter{



    private Stage stage;

    private Viewport viewport;

    private AssetManager assetManager;

    private Skin skin;
    private Table mainTable;



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
        viewport = new ExtendViewport(1280,720);
        stage = new Stage(viewport);
        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        addbutton("Play");
        addbutton("Options");
        addbutton("Credits");
        addbutton("Quit");

        Gdx.input.setInputProcessor(stage); //met l'effet quand on click sur le bouton


    }

    private TextButton addbutton(String name){
        TextButton button = new TextButton(name,skin);
        mainTable.add(button).width(400).height(60).padBottom(20);
        mainTable.row();

        return button;

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f,1f,15f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();



    }





    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);

    }



}
