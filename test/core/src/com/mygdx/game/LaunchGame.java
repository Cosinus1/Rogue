package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Manager.GameStateManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.States.MainMenu;

public class LaunchGame extends ApplicationAdapter {
    private GameStateManager gsm;
    private SpriteBatch sb;
    private AssetManager assetManager;



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
