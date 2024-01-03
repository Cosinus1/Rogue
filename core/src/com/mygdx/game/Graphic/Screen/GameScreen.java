package com.mygdx.game.Graphic.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.MyGame;
import com.mygdx.game.Graphic.*;
import com.mygdx.game.Graphic.Elements.Door;
import com.mygdx.game.Graphic.GraphicCharacter.*;
import com.mygdx.game.Graphic.World.World;
import com.mygdx.game.Graphic.World.Map.Map;

public class GameScreen implements Screen {
    MyGame game;
    PauseScreen pauseScreen;
    
    private Map map;
    private World world;
    private GraphicHero hero;
    private OrthographicCamera camera;
    private Renderer renderer;
    private RendererBW rendererBW;
    private ShapeRenderer shapeRenderer;

    private boolean BlacknWhite = false;

    private float waitingTime = 0f;
    private float maxWaitingTime = 2.0f;


    public GameScreen(MyGame game){
        this.game = game;
        world = new World();
        world.getCurrentMap().getMusic().setLooping(true);
        world.getCurrentMap().getMusic().play();
        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 640);
        // Initialize the renderer for rendering shapes and textures
        renderer = new Renderer();
        rendererBW = new RendererBW();
        shapeRenderer = new ShapeRenderer();

        // Initialize our champion
        hero = world.getHero();

        //Init menu pause
        pauseScreen = new PauseScreen(game);
    }

    @Override
    public void render(float delta) {
        // tell the camera to update its matrices.
      camera.update();

      //Store current map from the world
      map = world.getCurrentMap();
      
      //render the objects (dead until timed out and alive)
      //In Color or B&W
      if(BlacknWhite) rendererBW.render(map, camera);
      else renderer.render(map, hero, camera);

      //Keyboard

      hero.move(camera,world);
      hero.GraphicHeroAttack(map);
      //Enter input : change color
      if(Gdx.input.isKeyJustPressed(Keys.ENTER))BlacknWhite = !BlacknWhite;
      //Escapeinput : quits the game (implement Menu)
      if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
        game.setScreen(pauseScreen);
      }

      //Move the Ennemies
      if(map.getPVP() == "ON"){
         map.moveEnnemies(hero);
      }
      // Perform NPC attacks
      //Attack hero if cd + check if hero is dead 
         if(hero.PNJAttack(map)) {
            //Waiting (hero dying animation to put here) 
            //Respawn hero at the Tavern
            world.updateCurrentMap(world.getTavern());
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

      //Toggle Doors to follow whether they are opened or not
      for (Door Door : map.getDoors()) {  
         if(Door.getBounds().overlaps(hero.getHitbox())){
               //change position of the hero
               world.getCurrentMap().updatelastposition(hero.getlastX(), hero.getlastY());
               world.updateCurrentMap(Door.getMap());
               if(Door.getMap().isOpen()){
                  hero.getHitbox().setPosition(world.getCurrentMap().getLastposition());
               }else{
                  map.toggle();
                  hero.setX(world.getCurrentMap().getX());
                  hero.setY(world.getCurrentMap().getY());
               }
               hero.getObject().getProperties().put("x", hero.getX());
               hero.getObject().getProperties().put("y", hero.getY());
            
         }
      }
      hero.setlastX(hero.getX());
      hero.setlastY(hero.getY());
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
        if (world.getCurrentMap().getMusic() != null) {
           world.getCurrentMap().getMusic().dispose();
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
