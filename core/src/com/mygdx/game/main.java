// package com.mygdx.game;


// import com.mygdx.game.Graphic.Renderer;
// import com.mygdx.game.Graphic.RendererBW;
// import com.mygdx.game.Graphic.Elements.Door;
// import com.mygdx.game.Graphic.GraphicCharacter.*;
// import com.mygdx.game.Graphic.World.World;
// import com.mygdx.game.Graphic.World.Map.Map;

// import com.badlogic.gdx.ApplicationAdapter;
// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.Input.Keys;
// import com.badlogic.gdx.graphics.OrthographicCamera;
// import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

// public class main extends ApplicationAdapter {

//    private Renderer renderer;
//    private RendererBW rendererBW;
//    private ShapeRenderer shapeRenderer;
//    private OrthographicCamera camera;
//    private GraphicHero hero;

//    private World world;
//    private Map map;
//    private float attackTimer = 0f;
//    private float attackCooldown = 1.5f;
//    private float waitingTime = 0f;
//    private float maxWaitingTime = 2.0f; // Adjust this value for the desired waiting time after the attack

//    private boolean BlacknWhite = false;


//    @Override
//    public void create() {

//       world = new World();

//       // start the playback of the background music immediately
//       //world.getCurrentMap().getMusic().setLooping(true);
//       //world.getCurrentMap().getMusic().play();
      

//       // create the camera and the SpriteBatch
//       camera = new OrthographicCamera();
//       camera.setToOrtho(false, 960, 640);
//       // Initialize the renderer for rendering shapes and textures
//       renderer = new Renderer();
//       rendererBW = new RendererBW();
//       shapeRenderer = new ShapeRenderer();

//       // Initialize our champion
//       hero = world.getHero();
//    }

//    @Override
//    public void render() {

//       // tell the camera to update its matrices.
//       camera.update();

//       //Store current map from the world
//       map = world.getCurrentMap();

      
//       //render the objects (dead until timed out and alive)
//       //In Color or B&W
//       if(BlacknWhite) rendererBW.render(map, camera);
//       else renderer.render(map, hero, camera);

//    ///////////////////////////////////////////// process user input///////////////////////////////////////////////////
//       //Mouse (cheatcode to be removed)
      

//       //Keyboard

//          hero.move(camera,world);
//          hero.GraphicHeroAttack(map);
         
//          //Enter input : change color
//          if(Gdx.input.isKeyJustPressed(Keys.ENTER))BlacknWhite = !BlacknWhite;
//          //Escapeinput : quits the game (implement Menu)
//          if(Gdx.input.isKeyPressed(Keys.ESCAPE))Gdx.app.exit();

//          //Move the Ennemies
//          // if(map.getPVP() == "ON"){
//          //  for (GraphicEnnemie graphicEnnemie : map.getPNJ_list()) {
//          //    graphicEnnemie.move(hero, map);
//          //  }
//          // }
//          // Perform NPC attacks
//          attackTimer += Gdx.graphics.getDeltaTime();
//          if (attackTimer >= attackCooldown) {
//                if(hero.PNJAttack(map)) {
//                   while(waitingTime >= maxWaitingTime){
//                      waitingTime += Gdx.graphics.getDeltaTime();
//                   }  
//                   world.updateCurrentMap(world.getTavern());
//                   waitingTime = 0f; // Reset waiting time
//                   world.updateCurrentMap(world.getTavern());
//                   hero.setX(world.getTavern().getX()-50);
//                   hero.setY(world.getTavern().getY());
//                   hero.getObject().getProperties().put("x",world.getTavern().getX()-50);
//                   hero.getObject().getProperties().put("y", world.getTavern().getY());
//                }
//                attackTimer = 0f; // Reset the attack timer
//          }
      

//       //Toggle Doors to follow whether they are opened or not
//       for (Door Door : map.getDoors()) {  
//          if(Door.getBounds().overlaps(hero.getHitbox())){
//                //change position of the hero
//                world.getCurrentMap().updatelastposition(hero.getlastX(), hero.getlastY());
//                world.updateCurrentMap(Door.getMap());
//                if(Door.getMap().isOpen()){
//                   hero.getHitbox().setPosition(world.getCurrentMap().getLastposition());
//                }else{
//                   map.toggle();
//                   hero.setX(world.getCurrentMap().getX());
//                   hero.setY(world.getCurrentMap().getY());
//                }
//                hero.getObject().getProperties().put("x", hero.getX());
//                hero.getObject().getProperties().put("y", hero.getY());
            
//          }
//       }
//       hero.setlastX(hero.getX());
//       hero.setlastY(hero.getY());
//    }

//    @Override
//    public void dispose() {
//       renderer.dispose();
//       shapeRenderer.dispose();
//       // Dispose other resources if applicable
      
//       // It's also a good practice to dispose of the maps and their renderers if they were created and loaded within this class
//       if (map != null) {
//          map.getTiledMap().dispose();
//          map.getRenderer().dispose();
//       }
//       if (world.getCurrentMap().getMusic() != null) {
//          world.getCurrentMap().getMusic().dispose();
//       }
//    }

// }
