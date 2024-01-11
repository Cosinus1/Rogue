package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Back.Object.Character.Hero.Hero;
import com.mygdx.game.Graphic.Screen.Assets;
import com.mygdx.game.Graphic.Screen.ChooseClassScreen;
import com.mygdx.game.Graphic.Screen.GameScreen;
import com.mygdx.game.Graphic.Screen.MainMenuScreen;

public class MyGame extends Game {

    public MainMenuScreen mainMenuScreen;
    public ChooseClassScreen chooseClassScreen;
    public GameScreen gameScreen;
    public Assets assets;

    private Hero hero;

    @Override
    public void create(){
        
        // Load Button Textures
        assets = new Assets();
        assets.loadAll();
        assets.getAssetManager().finishLoading();
        
        mainMenuScreen = new MainMenuScreen(this, assets.getAssetManager());
        chooseClassScreen = new ChooseClassScreen(this, assets.getAssetManager());
  
        //Set the Main screen
        //setScreen(gameScreen);
        setScreen(mainMenuScreen);


    } 
    
    public Hero getHero(){
        return this.hero;
    }
    
    public void setHero(Hero hero){
        this.hero = hero;
    }
    
}