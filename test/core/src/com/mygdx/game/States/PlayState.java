package com.mygdx.game.States;

import com.mygdx.game.Manager.GameStateManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayState extends GameState{
    private Texture bacground ;
    public PlayState(GameStateManager gsm) {
        super(gsm);
        this.bacground =  new Texture(Gdx.files.internal("tavern.jpg"));
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(bacground,0,0);
        sb.end();
    }

    @Override
    public void dispose() {
        bacground.dispose();

    }
}
