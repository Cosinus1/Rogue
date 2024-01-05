package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.Graphic.Screen.GameScreen;
import com.mygdx.game.Graphic.Screen.PauseScreen;

public class MyGame extends Game {

    public GameScreen gameScreen;
    public PauseScreen pauseScreen;


    @Override
    public void create(){
        gameScreen = new GameScreen(this);
        pauseScreen = new PauseScreen(this);
        setScreen(gameScreen);
    } 
    //je rajoute un commentaire
    
    
}