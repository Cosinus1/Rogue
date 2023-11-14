package com.mygdx.game.States;

import Manager.GameStateManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
//import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Arme.Arc;
import com.mygdx.game.Inventory.Inventory;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.ScreenUtils;
//import com.badlogic.gdx.utils.TimeUtils;


public class MainMenu extends GameState{
    private Texture tavernImage;
    public MainMenu(GameStateManager gsm) {
        super(gsm);
        this.tavernImage = new Texture(Gdx.files.internal("camp.gif"));
        this.cam.setToOrtho(false, 800, 480); //  y est oriente vders le haut
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isTouched()){
            this.gsm.set(new PlayState(this.gsm));
            }
        }



    @Override
    public void update(float dt) {
        this.handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(this.cam.combined);
        sb.begin();
        sb.draw(this.tavernImage,0,0);
        sb.end();



    }

    @Override
    public void dispose() {
        this.tavernImage.dispose();

    }
}
