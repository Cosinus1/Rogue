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
import com.mygdx.game.Back.World.World;

public class PauseScreen implements Screen {

    private Stage stage;

    public PauseScreen(final MyGame game) {

        this.stage = new Stage(new ScreenViewport());
        Skin skin = new Skin();
        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);

        Pixmap pixmap = new Pixmap(200, 50, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("buttonBackground", new Texture(pixmap));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font; // Set the font for the button
        textButtonStyle.up = skin.newDrawable("buttonBackground", Color.DARK_GRAY); // Use the texture or Pixmap for the button's visual
        skin.add("default", textButtonStyle); // Add the "default" style for TextButton

        // Create buttons for Continue, Menu, Quit, and Save
        TextButton continueButton = new TextButton("Continue", skin);
        TextButton menuButton = new TextButton("Menu", skin);
        TextButton quitButton = new TextButton("Quit", skin);
        TextButton saveButton = new TextButton("Save", skin);

        // Add event listeners for the buttons
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Return to the game screen
                game.setScreen(game.gameScreen);
            }
        });

        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Quit the application
                Gdx.app.exit();
            }
        });

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Return to the main menu
                game.setScreen(game.mainMenuScreen);
            }
        });

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

        // Place the buttons on the stage
        Table table = new Table();
        table.center();
        table.add(continueButton).padBottom(20);
        table.row();
        table.add(saveButton).padBottom(20);
        table.row();
        table.add(menuButton).padBottom(20);
        table.row();
        table.add(quitButton).padBottom(20);

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
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
