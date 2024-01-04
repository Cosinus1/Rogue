package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MainMenu extends Game {

    @Override
    public void create() {
        Assets assets = new Assets();
        assets.loadAll();
        assets.getAssetManager().finishLoading();
        setScreen(new MainMenuScreen(assets.getAssetManager()));

    }



}
