package com.mygdx.game.Graphic.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.mygdx.game.MyGame;
import com.mygdx.game.Graphic.Sound.Volume;

public class SettingsScreen implements Screen {

    private Stage stage;

    public SettingsScreen(final MyGame game) {

        this.stage = new Stage(new ScreenViewport());
        Skin skin = new Skin();
        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        
        //Create Slider background
        Pixmap Sliderbckgrnd= new Pixmap(200, 50, Format.RGBA8888);
        Sliderbckgrnd.setColor(Color.WHITE);
        Sliderbckgrnd.fill();
        skin.add("buttonBackground", new Texture(Sliderbckgrnd));
        
        //Create Slider cursor
        Pixmap Slidercrsr = new Pixmap(200, 50, Format.RGBA8888);
        Slidercrsr.setColor(Color.WHITE);
        Slidercrsr.fill();
        skin.add("sliderCursor", new Texture(Slidercrsr));
    
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font; // Set the font for the button
        textButtonStyle.up = skin.newDrawable("buttonBackground", Color.DARK_GRAY); // Use the texture or Pixmap for the button's visual
        skin.add("default", textButtonStyle); // Add the "default" style for TextButton
    
        // Load the default SliderStyles
            //Vertical
            SliderStyle sliderVStyle = new SliderStyle();
            sliderVStyle.background = skin.newDrawable("buttonBackground", Color.LIGHT_GRAY);
            sliderVStyle.knob = skin.newDrawable("sliderCursor", Color.DARK_GRAY);
            skin.add("default-vertical", sliderVStyle); // Register vertical SliderStyle
            //Horizontal
            SliderStyle sliderHStyle = new SliderStyle();
            sliderHStyle.background = skin.newDrawable("buttonBackground", Color.LIGHT_GRAY);
            sliderHStyle.knob = skin.newDrawable("buttonBackground", Color.DARK_GRAY);
            skin.add("default-horizontal", sliderHStyle); // Register horizontal SliderStyle
            
        // Register the default LabelStyle
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        skin.add("default", labelStyle); // Add the default LabelStyle to the Skin
    
        // Create buttons for Back and Volume
        TextButton backButton = new TextButton("Back", skin);
        final Slider mainVolumeSlider = new Slider(0f, 1f, 0.1f, true, skin); // Horizontal slider
        Slider musicVolumeSlider = new Slider(0f, 1f, 0.1f, true, skin); // Vertical slider
    
        // Set default values for sliders
        float mainVolume = 0.5f;
        mainVolumeSlider.setValue(mainVolume);
        musicVolumeSlider.setValue(0.5f);
    
        // Add event listeners for the buttons
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Return to the pause menu
                game.setScreen(game.gameScreen.pauseScreen);
                hide();
            }
        });

        mainVolumeSlider.addListener(new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                // Adjust volume based on slider value
                float volvalue = mainVolumeSlider.getValue();
                // Call a method to change the volume based on the slider value
                Volume volume = Volume.getV();
                volume.set(volvalue);
            }
        });


        
    
        // Place the buttons on the stage
        Table table = new Table();
        table.center();
        table.add(backButton).padBottom(20);
        table.row();
        table.add(new Label("Main Volume", skin)).padBottom(10);
        table.row();
        table.add(mainVolumeSlider).width(50).height(200).padBottom(20); // Adjust width and height for vertical layout
        table.row();
        table.add(new Label("Music Volume", skin)).padBottom(10);
        table.row();
        table.add(musicVolumeSlider).width(50).height(200).padBottom(20); // Adjust width and height for vertical layout
        table.right();
        table.add(new Label(Volume.getString(), skin));
    
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
