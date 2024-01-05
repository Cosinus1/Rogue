package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {


    private AssetManager assetManager = new AssetManager();


    public static final AssetDescriptor<Skin> SKIN  = new AssetDescriptor<Skin>("uiskin.json",Skin.class,new SkinLoader.SkinParameter("uiskin.atlas"));



    public void loadAll(){
       // backgroundTexture = new Texture(Gdx.files.internal(BACKGROUND_IMAGE));
        assetManager.load(SKIN);


    }

    public AssetManager getAssetManager(){
        return assetManager;
    }

    public static void dispose() {
       // backgroundTexture.dispose();
    }
}
