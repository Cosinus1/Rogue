package com.mygdx.game.Graphic;

import java.util.Iterator;
import java.util.ArrayList;

import com.mygdx.game.Graphic.GraphicObject.GraphicObject;
import com.mygdx.game.Graphic.GraphicObject.Elements.Door;
import com.mygdx.game.Graphic.GraphicObject.GraphicCharacter.*;
import com.mygdx.game.Graphic.World.Map.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


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
    renderObjects(map, hero, camera);
    renderDeadObjects(map, camera);
      
   }

   public void renderObjects(Map map, GraphicCharacter hero, OrthographicCamera camera){  
      
      boolean renderDoors = false;
      spriteBatch.begin();
      
      if(renderDoors){
         for (Door door : map.getDoors()) {
            spriteBatch.draw(door.doorImage, door.getX(), door.getY());
         }
      }

      for(GraphicObject object: map.getObjects()){
         object.render(spriteBatch, camera);
      }
      
      spriteBatch.end();

   }
   
   public void renderDeadObjects(Map map, OrthographicCamera camera){
        // Rendering and destruction of the dead NPCs
         ArrayList<GraphicEnnemie> DeadNPCs = map.getDeadNPCs();

         int scaleFactor;

         if (DeadNPCs != null) {
            Iterator<GraphicEnnemie> Iterator = DeadNPCs.iterator();

            while (Iterator.hasNext()) {
               GraphicEnnemie DeadNPC = Iterator.next();

               if (DeadNPC.getDeathTimer() < 60) { // Render the object if death sequence is not done
                        int timer = DeadNPC.getDeathTimer();
                        // Get the appropriate texture
                        TextureRegion textureRegion = DeadNPC.getDeathTexture_List().get(timer);
                        // Increment timer
                        DeadNPC.incrementDeathTimer();
                        // Render the object texture based on its position and properties
                        float objectX = DeadNPC.getX();
                        float objectY = DeadNPC.getY();
                        //Render bigger for boss
                        if (DeadNPC.getObject().getProperties().get("boss")=="boss"){
                           scaleFactor = 2;
                        }
                        else scaleFactor = 1;
                        float objectWidth = textureRegion.getRegionWidth()*scaleFactor;
                        float objectHeight = textureRegion.getRegionHeight()*scaleFactor;
                        
                        spriteBatch.setProjectionMatrix(camera.combined);

                        spriteBatch.begin();
                        spriteBatch.draw(textureRegion, objectX, objectY, objectWidth, objectHeight);
                        spriteBatch.end();
               } else {
                     // Remove the object and character from the lists
                     Iterator.remove();
                  }
               }
         } else System.out.println("DeadNPCs is null");
    }

    public void dispose(){
      spriteBatch.dispose();
    }
}
