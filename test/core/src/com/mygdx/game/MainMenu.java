package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;

public class MainMenu extends Game {

   // private Texture backgroundImage ;

    @Override
    public void create() {
        Assets assets = new Assets();
        assets.loadAll();
        assets.getAssetManager().finishLoading();
        setScreen(new MainMenuScreen(assets.getAssetManager()));

    }



}
