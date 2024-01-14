package com.mygdx.game.Graphic.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.MyGame;
import com.mygdx.game.Back.Object.Character.Hero.Hero;
import com.mygdx.game.Back.World.Map;
import com.mygdx.game.Back.World.World;
import com.mygdx.game.Graphic.*;

public class GameScreen implements Screen {
    MyGame game;
    PauseScreen pauseScreen;
    InventoryScreen inventoryScreen;
    MerchantScreen MerchantScreen;
    
    private Map map;
    private World world;
    private Hero hero;
    private OrthographicCamera camera;
    private Renderer renderer;
    private ShapeRenderer shapeRenderer;

    private boolean BlacknWhite = false;

   //Time variables
    private float deltaTime;
    private float waitingTime = 0f;
    private float maxWaitingTime = 2.0f;


    public GameScreen(MyGame game){
        this.game = game;
        world = World.getInstance(game.getHero());
         //Store current map from the world
         map = world.getCurrentMap();
      //   map.getMusic().setLooping(true);
      //   map.getMusic().play();
        // create the camera
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 960, 640);
            //Update Camera
            camera.update();

         // Create the renderers for rendering shapes and textures
         renderer = new Renderer();
         shapeRenderer = new ShapeRenderer();

      
        // Initialize our champion
        hero = world.getHero();

        //Init menu pause
        pauseScreen = new PauseScreen(game);
        // Init inventory screen
        inventoryScreen = new InventoryScreen(game, hero);
        
    }

    @Override
   public void render(float delta) {
      //Get Time
      deltaTime = Gdx.graphics.getDeltaTime();
/*-------------------------------------------------INPUTS---------------------------------------------------------- */
      //Keyboard
         //Arrows Inputs
         hero.move(camera, map);

         if(map.getName().equals("Tavern")){
            if(Gdx.input.isKeyPressed(Keys.SPACE)){
               float x = hero.getX();
               float y = hero.getY();
               if(x>300 && x<340 && y>200 && y<240 && hero.getorY()==1){
                  MerchantScreen = new MerchantScreen(game, map.getMerchant(), hero);
                  game.setScreen(MerchantScreen);
               }
               
            }
         }
        else{
         //Space Input
            if(Gdx.input.isKeyPressed(Keys.SPACE)) hero.Attack(map);
            hero.IncrementAttackTimer(deltaTime);
            if(hero.getName() == "warrior") if(Gdx.input.isKeyPressed(Keys.SPACE)) hero.Attack(map);
            if(hero.getName() == "archer") if(Gdx.input.isKeyJustPressed(Keys.SPACE)) hero.Attack(map);
         }
         

         //Enter input : change color
         if(Gdx.input.isKeyJustPressed(Keys.ENTER))BlacknWhite = !BlacknWhite;

         //Escapeinput : menu
         if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) game.setScreen(pauseScreen);

         if(Gdx.input.isKeyJustPressed(Keys.E)) game.setScreen(inventoryScreen);

          


/*---------------------------------------------NON PLAYER OBJECTS HANDLING--------------------------------------------- */

      /*-----------------------------------------------MOVE-------------------------------------------------------- */
      //Move the Ennemies
         if(map.getPVP() == "ON"){
            map.moveEnnemies(hero);
         }
      /*----------------------------------------------ATTACK--------------------------------------------------------*/
      // Perform Element attacks
         map.ElementAttack();
      // Perform NPC attacks
         //Attack hero if cd + check if hero is dead 
         if(map.PNJAttack()) {
            //Waiting (hero dying animation to put here) 
            //Respawn hero at the Tavern
            map = world.updateCurrentMap(world.getTavern());
            waitingTime = 0f; // Reset waiting time
            //Update position
            hero.setPosition(world.getTavern().getX()-50, world.getTavern().getY());
         }
      
      //Store last position (need delay so that Hero doesnt spawn too close from door)
      if (waitingTime>=maxWaitingTime){
         waitingTime = 0f;
         hero.setlastX(hero.getX());
         hero.setlastY(hero.getY());
      }else waitingTime += Gdx.graphics.getDeltaTime();

      /*---------------------------------------------------UPDATE-------------------------------------------------------------- */
     
      //Update Map
      map.update(deltaTime);
      //Update World
      map = world.update(map);
      
      hero.setlastX(hero.getX());
      hero.setlastY(hero.getY());
      /*----------------------------------------------------RENDER------------------------------------------------------ */
      //render the objects (dead until timed out and alive)
      renderer.render(map, hero, camera);
      
    }

    @Override
    public void dispose() {
        renderer.dispose();
        shapeRenderer.dispose();
        // Dispose other resources if applicable
        
        // It's also a good practice to dispose of the maps and their renderers if they were created and loaded within this class
        if (map != null) {
           map.getTiledMap().dispose();
           map.getRenderer().dispose();
        }
        if (map.getMusic() != null) {
           map.getMusic().dispose();
        }
    }


    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }


    
}
