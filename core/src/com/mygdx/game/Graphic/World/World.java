package com.mygdx.game.Graphic.World;

import com.mygdx.game.Back.Character.Ennemie.*;
import com.mygdx.game.Back.Character.Hero.Hero;
import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.Item;
import com.mygdx.game.Back.Item.ItemType;
import com.mygdx.game.Back.Item.Potion;
import com.mygdx.game.Back.Item.Arme.Arc;
import com.mygdx.game.Back.Item.Arme.Massue;
import com.mygdx.game.Graphic.Elements.Door;
import com.mygdx.game.Graphic.GraphicCharacter.*;
import com.mygdx.game.Graphic.World.Map.*;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class World {

    private Map Home;
    private Map Tavern;
    private Map Armory;
    private Map Dungeon;

    private MapFactory mapFactory;

    private int Dungeonlevel;
    private TiledMapTileLayer CurrentcollisionLayer;
    private Map CurrentMap;

    private Hero hero;
    private GraphicHero graphicHero;

    //constructor
    public World(){
        //Create Lists of NPCs
        ArrayList<GraphicEnnemie> PNJs = new ArrayList<>();
        ArrayList<Door> Door_list = new ArrayList<>();
        //Set up MapFactory
        this.mapFactory = new MapFactory();
        //loading the based tiledmaps
        this.Dungeon = new Map(50, 90, new TmxMapLoader().load("TMX/Dungeon.tmx"), null, PNJs, Gdx.audio.newMusic(Gdx.files.internal("MP3/Dungeon_adventure.mp3")));
        this.Home = new Map(500,20, new TmxMapLoader().load("TMX/Home.tmx"), Door_list, null, Gdx.audio.newMusic(Gdx.files.internal("MP3/Dungeon_adventure.mp3")));
        this.Tavern = new Map(400, 100, new TmxMapLoader().load("TMX/Tavern.tmx"), null, null, Gdx.audio.newMusic(Gdx.files.internal("MP3/tavernMusic.mp3")));

        //Set up the Doors
        this.Tavern.addDoor(new Door(480, 120, Home));
        this.Home.addDoor(new Door(800, 435, Dungeon));
        this.Home.addDoor(new Door(0, 400, Tavern));

        //Align TavernMap
        this.Tavern.centerTavernMap();

        //Initialization
        this.Dungeonlevel = 1;
        this.Dungeon.setPVP("ON");
        this.CurrentMap = Home;
        this.CurrentcollisionLayer = Home.getcollisionLayer();
        //Initialize the Hero
        Potion potion1 = new Potion(10);
        Potion potion2 = new Potion(20);
        Potion potion3 = new Potion(20);
        Arc arc = new Arc( "arc", 15, 10 );
        Massue massue = new Massue("massue",30, 1);
        Inventory bag = new Inventory();
        bag.addItem(potion1);
        bag.addItem(arc);
        bag.addItem(potion2);
        bag.addItem(potion3);
        bag.addItem(massue);
        
        hero = new Hero(this, 100, 200, 10, 1, bag, "Champion", 0);
        graphicHero = new GraphicHero(this,hero,CurrentMap.getX(), CurrentMap.getY());
        //Init the Dungeons Maps
        initializeDungeon(5);
        
    }
    
/* --------------------------------------------- GETTERS ------------------------------------- */
    public Map getDungeon(){
        return this.Dungeon;
    }
    public Map getHome(){
        return this.Home;
    }
    public Map getTavern(){
        return this.Tavern;
    }
    public Map getArmory(){
        return this.Armory;
    }
    public Map getCurrentMap(){
        return CurrentMap;
    }
    public GraphicHero getHero(){
        return this.graphicHero;
    }
    public TiledMapTileLayer getCurrentcollisionLayer(){
        return CurrentcollisionLayer;
    }

/* --------------------------------------------- UPDATE ------------------------------------- */

    public void updateCurrentMap(Map map){
        if(map != null){
            
            /*if(map.getMusic()!=null){
                map.getMusic().play();
                if(this.CurrentMap.getMusic()!=null)this.CurrentMap.getMusic().pause();
            }*/
            this.CurrentMap = map;
            this.CurrentcollisionLayer = map.getcollisionLayer();
            if((map.getPVP() == "ON") && map.getPNJ_list().size()==0){
                System.out.println("Spawning Dungeon ...");
                respawn(map);
                System.err.println("Dungeon Spawned");
            }
            if(map==Home){
                System.out.println("boss killed");
                Dungeonlevel++;
                initializeDungeon(5);
            }
        }else System.out.println("map is null");
    }

/* ---------------------------------------------------------------------------------------- */

    public void respawn(Map map) {
        int EnemyCount = 5*Dungeonlevel; // Increase enemy count by 5 each time (for example)
        
        // Create new, more powerful enemies
        System.out.println("creating ennemies ...");
        map.createRandomEnnemies(this, EnemyCount);

        System.out.println("Enemies created");
    }

    public void initializeDungeon(int numberOfMaps) {
        Boss boss = new Boss(500, 500, 50,15,null,"Boss",null);
        GraphicBoss graphicboss = new GraphicBoss(boss,500,500, this);
        System.out.println("              /////////////Initializing Dungeon//////////////");
        Map dungeonMap = getDungeon();
        
        Map previousMap = dungeonMap;

        for (int i = 0; i < numberOfMaps + 1; i++) {
            // Create a new map using the MapFactory
            Map newMap = mapFactory.createMap(50, 100, null, null, previousMap);
            newMap.setPVP("ON");
            // Add a door between the previous map and the new map
            if(i==0)this.Dungeon.addDoor(new Door(550, -20, newMap));//Home is linked to first map
            else if(i==numberOfMaps)mapFactory.addDoorBetweenMaps(previousMap, Home);//Last map is linked back to Home
            else mapFactory.addDoorBetweenMaps(previousMap, newMap);

            //Track last door position for returning to last map at last position
            if(i!=0){
            float x = previousMap.getDoors().get(1).getX();
            float y = previousMap.getDoors().get(1).getY();

            int TileX = (int) x/32;
            int TileY = (int) y/32;

            System.out.println("\n  x   : " +  x    + "       y   : " +    y);
            System.out.println("\nTileX : " + TileX + "            TileY : " + TileY + "\n");

            previousMap.updateTiledmap(mapFactory.createRandomTiledMap(dungeonMap.getTiledMap().getTileSets(), TileX, TileY));

            previousMap.setcollisionLayer((TiledMapTileLayer) previousMap.getTiledMap().getLayers().get("Base"));

            graphicHero.spawn(previousMap,dungeonMap.getTiledMap().getTileSets(),this.graphicHero, "hero",false);
            if(i == numberOfMaps) graphicboss.spawn(previousMap, dungeonMap.getTiledMap().getTileSets(), graphicboss, graphicboss.getCharacter().getName());
            }
            // Update the previous map reference for the next iteration
            previousMap = newMap;
        }
        
        System.out.println("             /////////////////Dungeon Initialized/////////////");
    }
}
