package com.mygdx.game.States;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.game.Manager.GameStateManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameState extends ApplicationAdapter {
    public  OrthographicCamera cam ;
    public GameStateManager gsm;

    public GameState(GameStateManager gsm){
        this.cam = new OrthographicCamera();
        this.gsm = gsm ;
    }
    public abstract void handleInput();
    public abstract void update(float dt );
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();






}
