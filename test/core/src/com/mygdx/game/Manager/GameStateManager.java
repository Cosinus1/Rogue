package com.mygdx.game.Manager;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.States.GameState;

import java.util.Stack;

public class GameStateManager {
    private Stack<GameState> gameStates;
    private AssetManager assetManager ;

    //private static final AssetDescriptor<TextureAtlas> TILESET_01 = new AssetDescriptor<>();
    //public static final AssetDescriptor<Skin> SKIN  = new AssetDescriptor<Skin>("uiskin.json",Skin.class,new SkinLoader.SkinParameter("uiskin.atlas"));
    public GameStateManager(){
        this.gameStates = new Stack<GameState>();
        this.assetManager = new AssetManager();

    }
    /*public void loadAll(){
        assetManager.load(SKIN);
    }*/

    public  AssetManager getAssetManager(){
        return assetManager;
    }

    public void push(GameState gameState){
        this.gameStates.push(gameState);
    }
    public void set(GameState gameState){
        this.gameStates.pop().dispose();
        this.gameStates.push(gameState);
    }

    public void update(float dt ){
        this.gameStates.peek().update(dt);
    }
    public void render(SpriteBatch sb ){
        this.gameStates.peek().render(sb);
    }
}
