package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Graphic.Screen.Assets;
import com.mygdx.game.Graphic.Screen.GameScreen;
import com.mygdx.game.Graphic.Screen.MainMenuScreen;
import com.mygdx.game.Graphic.Screen.PauseScreen;

public class MyGame extends Game {

    public GameScreen gameScreen;
    public PauseScreen pauseScreen;
    public Assets assets;


    @Override
    public void create(){


        gameScreen = new GameScreen(this);
        pauseScreen = new PauseScreen(this);
        //setScreen(pauseScreen);


        // Pour charger la texture des boutons
        assets = new Assets();
        assets.loadAll();
        assets.getAssetManager().finishLoading();
        setScreen(new MainMenuScreen(assets.getAssetManager(),this));

    } 
    //je rajoute un commentaire
    
    
}