package com.mygdx.game.Graphic.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.mygdx.game.MyGame;
import com.mygdx.game.Graphic.Elements.MySkin;

public class PauseScreen implements Screen{

   private Stage stage;
   private final MyGame game;

    public PauseScreen(final MyGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        MySkin mySkin = new MySkin();

        // Créer des boutons pour Continuer et Quitter
        TextButton continueButton = new TextButton("Continuer", mySkin.createWhiteSkin(),"White"); // Utilisez votre skin pour les boutons
        TextButton quitButton = new TextButton("Quitter", mySkin.createWhiteSkin(),"White");

        // Ajouter des gestionnaires d'événements pour les boutons
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Revenir à l'écran du jeu
                game.setScreen(game.gameScreen);
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Quitter l'application
                Gdx.app.exit();
            }
        });

        // Placer les boutons sur la scène
        Table table = new Table();
        table.center();
        table.add(continueButton).padBottom(20);
        table.row();
        table.add(quitButton);
        stage.addActor(table);

        table.setPosition(
            (stage.getWidth()-table.getWidth()) / 2,
            (stage.getHeight()-table.getHeight())/2
        );

        
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();
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
        stage.dispose();
    }

    
}
