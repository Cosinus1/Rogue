package com.mygdx.game.Graphic;

import java.util.Iterator;
import java.util.ArrayList;

import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Back.Object.Character.Ennemie.Ennemie;
import com.mygdx.game.Back.Object.Character.Hero.Hero;
import com.mygdx.game.Back.Object.Element.Door;
import com.mygdx.game.Back.World.Map.Map;
import com.mygdx.game.Graphic.GraphicObject.GraphicCharacter.*;
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
   public void render(Map map, Hero hero, OrthographicCamera camera){

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

   public void renderObjects(Map map, Hero hero, OrthographicCamera camera){  
      
      boolean renderDoors = false;
      spriteBatch.begin();
      
      if(renderDoors){
         for (Door door : map.getDoors()) {
            spriteBatch.draw(door.doorImage, door.getX(), door.getY());
         }
      }

      for(Object object: map.getObjects()){
         object.render(spriteBatch, camera);
      }
      
      spriteBatch.end();

   }
   
   public void renderDeadObjects(Map map, OrthographicCamera camera){
        // Rendering and destruction of the dead NPCs
         ArrayList<Ennemie> DeadNPCs = map.getDeadNPCs();

         int scaleFactor;

         if (DeadNPCs != null) {
            Iterator<Ennemie> Iterator = DeadNPCs.iterator();

            while (Iterator.hasNext()) {
               Ennemie DeadNPC = Iterator.next();
               GraphicEnnemie graphicDeadNPC = (GraphicEnnemie) DeadNPC.getGraphicObject();

               if (graphicDeadNPC.getDeathTimer() < 60) { // Render the object if death sequence is not done
                        int timer = graphicDeadNPC.getDeathTimer();
                        // Get the appropriate texture
                        TextureRegion textureRegion = graphicDeadNPC.getDeathTexture_List().get(timer);
                        // Increment timer
                        graphicDeadNPC.incrementDeathTimer();
                        // Render the object texture based on its position and properties
                        float objectX = DeadNPC.getX();
                        float objectY = DeadNPC.getY();
                        //Render bigger for boss
                        if (graphicDeadNPC.getTextureObject().getProperties().get("boss")=="boss"){
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
