package com.mygdx.game.States;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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



    private Stage stage;
    private Table table;

    private Texture[] background;  // permettra l'animation de notre MainMenu
    private float [] backgrounOffsets = {0,0,0,0};
    float deltaTime = 2;
    private Music backgroundMusic;

    private float backgroundMaxScrollingSpeed;

    protected Skin skin;
    private TextureAtlas atlas;
    public MainMenu(GameStateManager gsm) {

        super(gsm);

        atlas = new TextureAtlas("uiskin.atlas");
        skin = new Skin(Gdx.files.internal("uiskin.json"), atlas);





        //this.tavernImage = new Texture(Gdx.files.internal("darkPurpleStarscape.png"));

        this.cam.setToOrtho(false, 800, 480); //  y est oriente vders le haut
        background = new Texture[4];
        background[0] = new Texture("Starscape00.png");
        background[1] = new Texture("Starscape01.png");
        background[2] = new Texture("Starscape02.png");
        background[3] = new Texture("Starscape03.png");





        backgroundMaxScrollingSpeed = (float)( 10 )/4 ;

        // load the background "music"
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("battleThemeA.mp3"));

        // start the playback of the background music immediately
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        stage = new Stage(new ExtendViewport(800, 480));

        // Créez un tableau pour organiser les éléments de l'interface utilisateur
        table = new Table();
        table.setFillParent(true); // Le tableau occupe toute la scène

        // Créez un style de bouton
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        //buttonStyle.font = yourFont; // Remplacez 'yourFont' par votre police de caractères
        // Ajoutez d'autres propriétés de style si nécessaire

        // Créez un bouton "Play"
        TextButton playButton = new TextButton("Play", skin);
       /* playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Code à exécuter lorsque le bouton "Play" est cliqué
                gsm.set(new PlayState(gsm));
            }
        });*/

        // Ajoutez le bouton "Play" à la table
        table.add(playButton).padBottom(20).row();

        // Ajoutez d'autres boutons de la même manière si nécessaire

        // Ajoutez la table à la scène
        stage.addActor(table);


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
       /* Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();*/

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        sb.setProjectionMatrix(this.cam.combined);
        sb.begin();
        renderBackground(deltaTime,sb);
       // sb.draw(this.tavernImage,0,0,1200,800);
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


    public void dispose() {
       for(int i = 0 ; i< background.length ; i++){
           background[i].dispose();
       }

    }
}
