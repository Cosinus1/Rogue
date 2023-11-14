package com.mygdx.game;

import Manager.GameStateManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.States.MainMenu;

public class LaunchGame extends ApplicationAdapter {
    private GameStateManager gsm;
    private SpriteBatch sb;



    public void create(){
        this.gsm = new GameStateManager();
        this.sb = new SpriteBatch();
        this.gsm.push(new MainMenu(this.gsm));
    }

    public void render(){
        this.gsm.update(Gdx.graphics.getDeltaTime());
        this.gsm.render(this.sb);

    }

    public void dispose(){
        this.sb.dispose();


    }
}
