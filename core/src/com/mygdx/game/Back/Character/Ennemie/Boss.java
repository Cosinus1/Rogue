package com.mygdx.game.Back.Character.Ennemie;

import java.util.ArrayList;
import java.util.Random;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.Arme.Massue;

public class Boss extends Ennemie{
    ArrayList<String> Names = new ArrayList<>();
    
    public Boss(int pv, int defense, int power,int range, Inventory bag,String name ,Massue massue){
        super(pv, defense, power, range, bag, name);
        this.Names.add("vampire");
        this.Names.add("jack");
        this.Names.add("minotaure");
        Random random = new Random();
        this.setName(Names.get(random.nextInt(Names.size())));        
    }
    /* 
    // Method to set textures for the boss based on the .tsx file
    public void initBossTextures(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
         
        TiledMapTileSets tileSets = tiledMap.getTileSets();
        for (TiledMapTileSet tileSet : tileSets) {
            if(tileSet.getName().equals("boss")){
                System.out.println("Tileset name: " + tileSet.getName());
                for (TiledMapTile tile : tileSet){
                System.out.println("Tile ID : " + tile.getId());
                }
            }
        }
            


        // Assuming you have tileset named 'boss_tiles' in your .tsx file
        TiledMapTileSet tileSet = tiledMap.getTileSets().getTileSet("boss");

        // You can extract textures for different postures/frames using their IDs
        if (tileSet != null) {
        System.out.println("boss.tsx exists");
        // Assuming the tile ID exists in your tileset
        TiledMapTile tile = tileSet.getTile(1812);

            if (tile != null) {
                TextureRegion posture1Texture = (TextureRegion) tile.getTextureRegion();
                System.out.println("postureTexture : " + posture1Texture);
                this.Object.setTextureRegion(posture1Texture);
                this.texture_list.add(posture1Texture);
            } 
            else System.out.println("Tile ID 20 does not exist in the boss.tsx tileset.");
        } 
        else System.out.println("Tileset 'boss.tsx' not found in the TiledMap.");
        System.out.println("texture_list : " + texture_list);
    }

    public void setbossTexture(int i){
        TextureRegion textureRegion = (TextureRegion) this.texture_list.get(i);
        System.out.println(textureRegion);
        this.Object.setTextureRegion(textureRegion);
    }
    */
}
