package com.badlogic.character;

import java.util.Iterator;

import com.badlogic.character.Door;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class Character extends ApplicationAdapter {
   private Texture doorImage;
   private Texture dropImage;
   private Texture tavernImage;
   private Texture campImage;
   private Texture characterImage;
   private Music backgroundMusic;
   private SpriteBatch batch;
   private OrthographicCamera camera;
   private Rectangle character;
   private Door tavern_door;
   private Door camp_door;

   @Override
   public void create() {
      // load the images for the character
      characterImage = new Texture(Gdx.files.internal("character_front.png"));
      //tavern Image
      tavernImage = new Texture(Gdx.files.internal("tavern.jpg"));
      //camp Image
      campImage = new Texture(Gdx.files.internal("camp.gif"));

      int rectangleWidth = 64;  // Set the width of the rectangle
      int rectangleHeight = 64; // Set the height of the rectangle

      //tavern_door is a red rectangle
      Pixmap pixmap = new Pixmap(rectangleWidth, rectangleHeight, Pixmap.Format.RGBA8888);
      pixmap.setColor(1, 0, 0, 1); // Set the color to red (R=1, G=0, B=0, Alpha=1)
      pixmap.fillRectangle(0, 0, rectangleWidth, rectangleHeight);
      doorImage = new Texture(pixmap);
      pixmap.dispose(); // Dispose of the Pixmap to free resources


      // load the background "music"
      backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music_tavern.mp3"));

      // start the playback of the background music immediately
      backgroundMusic.setLooping(true);
      backgroundMusic.play();

      // create the camera and the SpriteBatch
      camera = new OrthographicCamera();
      camera.setToOrtho(false, 800, 480);
      batch = new SpriteBatch();

      // create a Rectangle to logically represent the character
      character = new Rectangle();
      character.x = 800 / 2 - 64 / 2; // center the character horizontally
      character.y = 20; // bottom left corner of the character is 20 pixels above the bottom screen edge
      character.width = 64;
      character.height = 64;

      //create doors
      tavern_door = new Door(0, 0, tavernImage);
      camp_door = new Door(200, 0, campImage);

   }

   @Override
   public void render() {

      // tell the camera to update its matrices.
      camera.update();

      // tell the SpriteBatch to render in the
      // coordinate system specified by the camera.
      batch.setProjectionMatrix(camera.combined);

      // begin a new batch and draw the character
      batch.begin();
      if (tavern_door.isOpen()) {
         // Draw the scenery when the door is entered
         batch.draw(tavern_door.getSceneryImage(), 0, 0, 800, 480);
         batch.draw(doorImage,tavern_door.getX(),tavern_door.getY());
     } else {
         batch.draw(campImage, tavern_door.getX(), tavern_door.getY());
         batch.draw(doorImage,camp_door.getX(),camp_door.getY());
     }
      batch.draw(characterImage, character.x, character.y);

      batch.end();

      // process user input
      if(Gdx.input.isTouched()) {
         Vector3 touchPos = new Vector3();
         touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
         camera.unproject(touchPos);
         character.x = touchPos.x - 64 / 2;
      }
      if(Gdx.input.isKeyPressed(Keys.LEFT)) character.x -= 200 * Gdx.graphics.getDeltaTime();
      if(Gdx.input.isKeyPressed(Keys.RIGHT)) character.x += 200 * Gdx.graphics.getDeltaTime();
      if(Gdx.input.isKeyPressed(Keys.UP)) character.y += 200 * Gdx.graphics.getDeltaTime();
      if(Gdx.input.isKeyPressed(Keys.DOWN)) character.y -= 200 * Gdx.graphics.getDeltaTime();
      if(Gdx.input.isKeyPressed(Keys.ESCAPE))Gdx.app.exit();

      // make sure the character stays within the screen bounds
      if(character.x < 0) character.x = 0;
      if(character.x > 800 - 64) character.x = 800 - 64;

      if(tavern_door.isOpen()){
         if(tavern_door.getBounds().overlaps(character)){
            tavern_door.toggle();
         }
      }
      else{
         if(camp_door.getBounds().overlaps(character)){
            camp_door.toggle();
            tavern_door.toggle();
         }
      }

   }

   @Override
   public void dispose() {
      // dispose of all the native resources
      dropImage.dispose();
      characterImage.dispose();
      doorImage.dispose();
      backgroundMusic.dispose();
      batch.dispose();
   }
}
