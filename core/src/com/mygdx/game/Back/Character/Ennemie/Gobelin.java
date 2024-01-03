package com.mygdx.game.Back.Character.Ennemie;

import com.mygdx.game.Back.Inventory.*;
import com.mygdx.game.Back.Item.Arme.*;

public class Gobelin extends Ennemie{
    private Massue massue ;


    public Gobelin(int pv, int defense, int power,int range, Inventory bag, String name, Massue massue){
        super(pv, defense, power, range, bag, name);
        this.massue = massue;
        this.setName("gobelin");
    }
    /* 
    // Method to set textures for the Gobelin based on the .tsx file
    public void initGobelinTextures(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
         
        TiledMapTileSets tileSets = tiledMap.getTileSets();
        for (TiledMapTileSet tileSet : tileSets) {
            if(tileSet.getName().equals("gobelin")){
                System.out.println("Tileset name: " + tileSet.getName());
            for (TiledMapTile tile : tileSet){
                System.out.println("Tile ID : " + tile.getId());
            }
        }
        }
        


        // Assuming you have tileset named 'gobelin_tiles' in your .tsx file
        TiledMapTileSet tileSet = tiledMap.getTileSets().getTileSet("gobelin");

        // You can extract textures for different postures/frames using their IDs
        if (tileSet != null) {
        System.out.println("gobelin.tsx exists");
        // Assuming the tile ID exists in your tileset
        TiledMapTile tile = tileSet.getTile(1812);

            if (tile != null) {
                TextureRegion posture1Texture = (TextureRegion) tile.getTextureRegion();
                System.out.println("postureTexture : " + posture1Texture);
                this.Object.setTextureRegion(posture1Texture);
                this.texture_list.add(posture1Texture);
            } 
            else System.out.println("Tile ID 20 does not exist in the gobelin.tsx tileset.");
        } 
        else System.out.println("Tileset 'gobelin.tsx' not found in the TiledMap.");
        System.out.println("texture_list : " + texture_list);
    }

    public void setGobelinTexture(int i){
        TextureRegion textureRegion = (TextureRegion) this.texture_list.get(i);
        System.out.println(textureRegion);
        this.Object.setTextureRegion(textureRegion);
    }
    
    */
    public int attaquer(){
        System.out.println("Grrrrr");
        return power + massue.getPower();
    }
    
}
