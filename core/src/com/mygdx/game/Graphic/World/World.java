package com.mygdx.game.Graphic.World;

import com.mygdx.game.Back.Character.Ennemie.*;
import com.mygdx.game.Back.Character.Hero.*;
import com.mygdx.game.Back.Item.ItemType;
import com.mygdx.game.Back.Item.Weapon.Massue;
import com.mygdx.game.Graphic.GraphicObject.Elements.Door;
import com.mygdx.game.Graphic.GraphicObject.GraphicCharacter.*;
import com.mygdx.game.Graphic.World.Map.*;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class World {

    private Map Home;
    private Map Tavern;
    private Map Armory;
    private Map DungeonHub;
    private ArrayList<Map> Dungeon;
    private Map CurrentMap;

    private MapFactory mapFactory;

    private TiledMapTileSets Tilesets;

    private TiledMapTileLayer CurrentcollisionLayer;

    private Hero Hero;
    private Boss boss;
    private GraphicHero graphicHero;

    private int numberOfMaps = 5;
    private int Dungeonlevel;

    //constructor
    public World(Hero hero){
        //Create Lists of NPCs
        ArrayList<GraphicEnnemie> PNJs = new ArrayList<>();
        ArrayList<Door> Door_list = new ArrayList<>();
        //Set up MapFactory
        this.mapFactory = new MapFactory();
        //loading the based tiledmaps
        this.DungeonHub = new Map(50, 90, new TmxMapLoader().load("TMX/Dungeon.tmx"), null, PNJs, Gdx.audio.newMusic(Gdx.files.internal("MP3/Dungeon_adventure.mp3")));
        this.Home = new Map(500,20, new TmxMapLoader().load("TMX/Home.tmx"), Door_list, null, Gdx.audio.newMusic(Gdx.files.internal("MP3/Dungeon_adventure.mp3")));
        this.Tavern = new Map(400, 100, new TmxMapLoader().load("TMX/Tavern.tmx"), null, null, Gdx.audio.newMusic(Gdx.files.internal("MP3/tavernMusic.mp3")));

        //Set Names 
        this.Home.setName("Home");
        this.Tavern.setName("Tavern");
        this.DungeonHub.setName("DungeonHub");

        //Get Tilesets
        this.Tilesets = DungeonHub.getTiledMap().getTileSets();

        //Set up the Doors
        this.Tavern.addDoor(new Door(480, 120, Home));
        this.Home.addDoor(new Door(860, 415, DungeonHub));
        this.Home.addDoor(new Door(0, 400, Tavern));

        //Align TavernMap
        this.Tavern.centerTavernMap();

        //Initialization
            this.CurrentMap = Home;
            this.CurrentcollisionLayer = Home.getcollisionLayer();

            //Initialize the Hero
            this.Hero = hero;
            graphicHero = new GraphicHero(this,Hero,CurrentMap.getX(), CurrentMap.getY());
        

            //Initialize Dungeon
            this.Dungeon = new ArrayList<>();
            this.Dungeonlevel = 1;
            this.DungeonHub.setPVP("OFF");
            initializeDungeon(numberOfMaps);
        
    }
    
/* --------------------------------------------- GETTERS ------------------------------------- */
    public Map getDungeonHub(){
        return this.DungeonHub;
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
    public TiledMapTileSets getTileSets(){
        return Tilesets;
    }

/* --------------------------------------------- UPDATE ------------------------------------- */
    public Map update(Map map){
        ArrayList<Door> Doors = map.getDoors();

        if(Doors!=null){
            for (Door Door : Doors) {  
                if(Door.getBounds().overlaps(graphicHero.getHitbox())){
                    
                    System.out.println(" Door : " + map.getName() + " --> " + Door.getMap().getName() + " is toggled");
                    //change position of the graphicHero
                    map.updatelastposition(graphicHero.getlastX(), graphicHero.getlastY());
                    //Update map
                    updateCurrentMap(Door.getMap());
                    if (map.isOpen()==false) map.toggle();
                    map = CurrentMap;
                    //get the appropriate position for hero
                    if(Door.getMap().isOpen() || (map.getPVP()!="ON" && map!=Home)){          
                        graphicHero.setPosition(map.getLastposition());
                    }else{
                        graphicHero.setPosition(map.getX(), map.getY());
                    }    
                }
            }
        }
        //Check for Dungeon reset
        IsDungeonFinished();
    return map;
    }

    public Map updateCurrentMap(Map map){
        if(map != null){
            
            /*if(map.getMusic()!=null){
                map.getMusic().play();
                if(this.CurrentMap.getMusic()!=null)this.CurrentMap.getMusic().pause();
            }*/
            this.CurrentMap = map;
            this.CurrentcollisionLayer = map.getcollisionLayer();
            if((map.getPVP() == "ON") && map.getNPCs().size()==0){
                System.out.println("Spawning  Map...");
                respawn(map);
                System.err.println("Map Spawned");
            }
        }else System.out.println("map is null");
        return this.CurrentMap;
    }

/* ---------------------------------------------------------------------------------------- */

    public void respawn(Map map) {
        int EnemyCount = Dungeonlevel; // Increase enemy count
        
        // Create new, more powerful enemies
        System.out.println("creating ennemies ...");
        map.createRandomEnnemies(this.Tilesets, EnemyCount);

        System.out.println("Enemies created");
    }

    public void initializeDungeon(int numberOfMaps) {
        boss = new Boss(100, 0, 50,3, 10, null,"Boss",new Massue(ItemType.WEAPON, "massue", 50, 2));
        GraphicBoss graphicboss = new GraphicBoss(boss,525,400, this.Tilesets);
        System.out.println("              /////////////Initializing Dungeon//////////////");
        Map DungeonHubMap = getDungeonHub();
        
        Map previousMap = DungeonHubMap;

        for (int i = 0; i < numberOfMaps + 1; i++) {
            // Create a new map using the MapFactory
            Map newMap = mapFactory.createMap(50, 100, null, null, previousMap);
            //set Name
            newMap.setName("Dungeon " + i);
            newMap.setPVP("ON");
            // Add a door between the previous map and the new map
            if(i==0)this.DungeonHub.addDoor(new Door(470, 350, newMap));//Home is linked to first map
            else if(i==numberOfMaps)mapFactory.addDoorBetweenMaps(previousMap, Home);//Last map is linked back to Home
            else mapFactory.addDoorBetweenMaps(previousMap, newMap);

            //Track last door position for returning to last map at last position
            if(i!=0){
                float x = previousMap.getDoors().get(1).getX();
                float y = previousMap.getDoors().get(1).getY();

                int TileX = (int) x/32;
                int TileY = (int) y/32;

                //Add the Generated TiledMap into the Map
                previousMap.updateTiledmap(mapFactory.createRandomTiledMap(DungeonHubMap.getTiledMap().getTileSets(), TileX, TileY));
                previousMap.addWalls();
                
                //Spawn Hero in the new map (This can be done in respawn)
                graphicHero.spawn(previousMap);
                //Spawn Boss in the last Map
                if(i == numberOfMaps) graphicboss.spawn(previousMap);
            }
            //Add the map into the Dungeon
            Dungeon.add(previousMap);
            // Update the previous map reference for the next iteration
            previousMap = newMap;
        }
        
        System.out.println("             /////////////////Dungeon Initialized/////////////");
    }
    public void IsDungeonFinished(){
        if(CurrentMap==Home){
            if(boss.getPV()<=0){
                System.out.println("boss killed");
                Dungeonlevel++;
                disposeDungeon();
                initializeDungeon(numberOfMaps);
            }else for(Map map : Dungeon) map.updatelastposition(map.getX(), map.getY());;
        }
    }
    public void disposeDungeon(){

        for(Map map : Dungeon){
            ArrayList<Door> Doors = map.getDoors();
            if(Doors != null){
                int size = Doors.size();
                for(int i=0; i<size; i++){
                    Doors.remove(0);
                }
                System.out.println("Door list : " + Doors + "  for : " + map.getName());
            }else System.out.println("No doors");
        }
        DungeonHub.updatelastposition(DungeonHub.getX(), DungeonHub.getY());
    }

    public void disposeTiledmaps(Map map){
        map.getTiledMap().dispose();
    }
}
