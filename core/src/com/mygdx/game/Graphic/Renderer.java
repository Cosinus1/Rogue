package com.mygdx.game.Graphic;

import java.util.Iterator;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

import com.mygdx.game.Graphic.Elements.Door;
import com.mygdx.game.Graphic.GraphicCharacter.*;
import com.mygdx.game.Graphic.World.Map.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.TextureMapObject;


public class Renderer {

   private SpriteBatch spriteBatch;

   //Constructor
   public Renderer(){
      this.spriteBatch = new SpriteBatch();
   }
   
   //Rendering functions
   public void render(Map map, GraphicHero hero, OrthographicCamera camera){

    // Set the clear color to black
      Gdx.gl.glClearColor(0, 0, 0, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Render the Tiledmap
      camera.translate(map.getcamX(), map.getcamY());
      camera.update();
      if(map.getTiledMap()!=null){
         map.getRenderer().setView(camera);
         map.getRenderer().render();
      }
      camera.translate(-map.getcamX(), -map.getcamY());

    // Render all Objects
      renderDeadObjects(map, camera);
      renderObjects(map, hero, camera);
   }

   public void renderObjects(Map map, GraphicCharacter hero, OrthographicCamera camera){  
      
      spriteBatch.begin();
      for (Door door : map.getDoors()) {
         spriteBatch.draw(door.doorImage, door.getX(), door.getY());
      }

      ArrayList<GraphicCharacter> list = new ArrayList<>();
      list.add(hero);
      if (map.getPNJ_list()!=null){ 
         list.addAll(map.getPNJ_list());
         sortCharacter(list);
      }
      for(GraphicCharacter character : list){
         character.renderGraphicCharacter(spriteBatch, camera);
      }
      if (map.getPNJ_list()!=null){
         ArrayList<GraphicEnnemie> Characters = new ArrayList<>();
         Characters = map.getPNJ_list();
         for (GraphicCharacter character : Characters) {
            drawLifebar(character);
         }
      }
      else System.out.println("object is null");
      drawHeroLifebar(hero);
      spriteBatch.end();

   }
   
   public void renderDeadObjects(Map map, OrthographicCamera camera){
        // Rendering and destruction of the dead NPCs
         MapObjects Deadobjects = map.getDeadObjects();
         ArrayList<GraphicEnnemie> DeadPNJs = map.getDeadPNJ_list();

         int scaleFactor;

         if (Deadobjects != null) {
            Iterator<MapObject> iterator = Deadobjects.iterator();
            Iterator<GraphicEnnemie> characterIterator = DeadPNJs.iterator();

            while (iterator.hasNext() && characterIterator.hasNext()) {
               MapObject object = iterator.next();
               GraphicCharacter DeadPNJ = characterIterator.next();

               if (DeadPNJ.getDeathTimer() < 60) { // Render the object if death sequence is not done
                     if (object instanceof TextureMapObject) {
                        int timer = DeadPNJ.getDeathTimer();
                        // Get the appropriate texture
                        TextureRegion textureRegion = DeadPNJ.getDeathTexture_List().get(timer);
                        // Increment timer
                        DeadPNJ.incrementDeathTimer();
                        // Render the object texture based on its position and properties
                        float objectX = (float) object.getProperties().get("x");
                        float objectY = (float) object.getProperties().get("y");
                        //Render bigger for boss
                        if (object.getProperties().get("boss")=="boss"){
                           scaleFactor = 3;
                        }
                        else scaleFactor = 1;
                        float objectWidth = textureRegion.getRegionWidth()*scaleFactor;
                        float objectHeight = textureRegion.getRegionHeight()*scaleFactor;
                        
                        spriteBatch.setProjectionMatrix(camera.combined);

                        spriteBatch.begin();
                        spriteBatch.draw(textureRegion, objectX, objectY, objectWidth, objectHeight);
                        spriteBatch.end();
                     } else {
                        System.out.println("object Texture not found");
                     }
               } else {
                     // Remove the object and character from the lists
                     iterator.remove();
                     characterIterator.remove();
                  }
               }
         } else System.out.println("Deadobjects is null");
    }

    public void drawLifebar(GraphicCharacter character){
      // Get properties
            TextureMapObject textureObject = (TextureMapObject) character.getObject();
            TextureRegion textureRegion = textureObject.getTextureRegion();
            float objectX = (float) character.getObject().getProperties().get("x");
            float objectY = (float) character.getObject().getProperties().get("y");
            float objectWidth = textureRegion.getRegionWidth();
            float objectHeight = textureRegion.getRegionHeight();
            // Draw life bar
            int lifeBarWidth = character.getCharacter().getPV(); 
            int lifeBarHeight = 1; // Height of the life bar
            float lifeBarX = objectX + (objectWidth - lifeBarWidth) / 2; 
            float lifeBarY = objectY + objectHeight + 5; // Place the life bar above the character
            Pixmap pixmap = new Pixmap(lifeBarWidth, lifeBarHeight, Format.RGBA8888);
            pixmap.setColor(Color.RED);
            pixmap.fillRectangle(0, 0, lifeBarWidth, lifeBarHeight);
            Texture lifebar = new Texture(pixmap);
            pixmap.dispose();

            spriteBatch.setColor(1, 0, 0, 1); // Red color for the life bar
            spriteBatch.draw(lifebar, lifeBarX, lifeBarY, lifeBarWidth, lifeBarHeight);
            spriteBatch.setColor(1, 1, 1, 1); // Reset color after drawing the life bar
    }

    public void drawHeroLifebar(GraphicCharacter character){
      // Get properties
            // float objectX = (float) 10;
            // float objectY = (float) 600;
            // // Draw life bar
            // int lifeBarWidth = character.getCharacter().getPV();
            // int lifeBarHeight = 10;
            // float lifeBarX = objectX ;
            // float lifeBarY = objectY ;
            // Pixmap pixmap = new Pixmap(lifeBarWidth, lifeBarHeight, Format.RGBA8888);
            // pixmap.setColor(Color.RED);
            // pixmap.fillRectangle(0, 0, lifeBarWidth, lifeBarHeight);
            // Texture lifebar = new Texture(pixmap);
            // pixmap.dispose();

            // spriteBatch.setColor(1, 0, 0, 1); // Red color for the life bar
            // spriteBatch.draw(lifebar, lifeBarX, lifeBarY, lifeBarWidth, lifeBarHeight);
            // spriteBatch.setColor(1, 1, 1, 1); // Reset color after drawing the life bar
            character.getBarlife().drawLifeBar(spriteBatch, character.getCharacter());
    }
    public void dispose(){
      spriteBatch.dispose();
    }

   public void sortCharacter(ArrayList<GraphicCharacter> list){
        // Définition du Comparator pour trier en fonction de l'attribut y
        Comparator<GraphicCharacter> yComparator = new Comparator<GraphicCharacter>() {
            @Override
            public int compare(GraphicCharacter character1, GraphicCharacter character2) {
                // Comparaison des valeurs de y
                return Float.compare(character2.getY(), character1.getY());
            }
        };

        // Utilisation de Collections.sort() avec le Comparator défini
        Collections.sort(list, yComparator);
    }
}
