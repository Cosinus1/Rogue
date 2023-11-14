package com.mygdx.game.States;

import com.badlogic.gdx.audio.Music;
import com.mygdx.game.Manager.GameStateManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.math.MathUtils;

//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.ScreenUtils;
//import com.badlogic.gdx.utils.TimeUtils;


public class MainMenu extends GameState{

    private Texture[] background;  // permetra l'animation de notre MainMenu
    private float [] backgrounOffsets = {0,0,0,0};
    float deltaTime = 2;
    private Music backgroundMusic;

    private float backgroundMaxScrollingSpeed;
    private Texture tavernImage;
    public MainMenu(GameStateManager gsm) {
        super(gsm);
        //this.tavernImage = new Texture(Gdx.files.internal("darkPurpleStarscape.png"));
        this.cam.setToOrtho(false, 800, 480); //  y est oriente vders le haut
        background = new Texture[4];
        background[0] = new Texture("Starscape00.png");
        background[1] = new Texture("Starscape01.png");
        background[2] = new Texture("Starscape02.png");
        background[3] = new Texture("Starscape03.png");

        backgroundMaxScrollingSpeed = (float)( 10 )/4 ;

        // load the background "music"
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music_tavern.mp3"));

        // start the playback of the background music immediately
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

    }


    @Override
    public void handleInput() {
        if(Gdx.input.isTouched()){
            this.gsm.set(new PlayState(this.gsm));
            }
        }



    @Override
    public void update(float dt) {
        this.handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(this.cam.combined);
        sb.begin();
        renderBackground(deltaTime,sb);
        //sb.draw(this.tavernImage,0,0,1200,800);
        sb.end();



    }

    private void renderBackground(float deltaTime,SpriteBatch sb){

        backgrounOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgrounOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgrounOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgrounOffsets[3] += deltaTime * backgroundMaxScrollingSpeed ;

        for(int layer = 0 ;layer < backgrounOffsets.length; layer++){
            if(backgrounOffsets[layer] > 800){
                backgrounOffsets[layer] = 0;
            }
            sb.draw(background[layer],0,-backgrounOffsets[layer],1200,800);
            sb.draw(background[layer],0,-backgrounOffsets[layer]+800,1200,800);

        }
    }

    @Override
    public void dispose() {
       for(int i = 0 ; i< background.length ; i++){
           background[i].dispose();
       }

    }
}
