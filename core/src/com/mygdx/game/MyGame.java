package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Graphic.Screen.Assets;
import com.mygdx.game.Graphic.Screen.GameScreen;
import com.mygdx.game.Graphic.Screen.MainMenuScreen;
import com.mygdx.game.Graphic.Screen.PauseScreen;

public class MyGame extends Game {

    public MainMenuScreen mainMenuScreen;
    public GameScreen gameScreen;
    public PauseScreen pauseScreen;
    public Assets assets;


    @Override
    public void create(){
        
        // Load Button Textures
        assets = new Assets();
        assets.loadAll();
        assets.getAssetManager().finishLoading();
        
        mainMenuScreen = new MainMenuScreen(this, assets.getAssetManager());
        gameScreen = new GameScreen(this);
  
        //Set the Main screen
        //setScreen(gameScreen);
        setScreen(mainMenuScreen);


    } 
    //je rajoute un commentaire
    
    
}