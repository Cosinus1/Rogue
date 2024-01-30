package com.mygdx.game.Back.World;

import java.util.Random;

import com.mygdx.game.Back.Inventory.Inventory;
import com.mygdx.game.Back.Item.Potion;
import com.mygdx.game.Back.Item.Weapon.*;
import com.mygdx.game.Back.Object.Character.Merchant;
import com.mygdx.game.Back.Object.Character.Ennemie.*;
import com.mygdx.game.Back.Object.Character.Hero.*;
import com.mygdx.game.Back.Object.Element.Door;
import com.mygdx.game.Graphic.GraphicObject.GraphicCharacter.GraphicBoss;
import com.mygdx.game.Graphic.GraphicObject.GraphicCharacter.GraphicHero;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class World {

    private static World instance;

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

    private int numberOfMaps = 5;
    private int Dungeonlevel;

    //constructor
    public World(Hero hero){
        //Create Lists of NPCs
        ArrayList<Ennemie> PNJs = new ArrayList<>();
        ArrayList<Door> Door_list = new ArrayList<>();
        //Set up MapFactory
        this.mapFactory = new MapFactory();
        //loading the based tiledmaps
        this.DungeonHub = new Map(50, 90, new TmxMapLoader().load("TMX/Dungeon.tmx"), null, PNJs, Gdx.audio.newMusic(Gdx.files.internal("MP3/Dungeon_adventure.mp3")));
        this.Home = new Map(500,20, new TmxMapLoader().load("TMX/Home.tmx"), Door_list, null, Gdx.audio.newMusic(Gdx.files.internal("MP3/battleThemeA.mp3")));
        this.Tavern = new Map(400, 100, new TmxMapLoader().load("TMX/Tavern.tmx"), null, null, Gdx.audio.newMusic(Gdx.files.internal("MP3/tavernMusic.mp3")));

        //Set Names 
        this.Home.setName("Home");
        this.Tavern.setName("Tavern");
        this.DungeonHub.setName("DungeonHub");

        //Get Tilesets
        this.Tilesets = DungeonHub.getTiledMap().getTileSets();

        //Set up the Doors
        DungeonHub.addDoor(new Door(-40, 100, Home));
        Tavern.addDoor(new Door(480, 120, Home));
        Home.addDoor(new Door(860, 415, DungeonHub));
        Home.addDoor(new Door(0, 400, Tavern));

        //Set up collisions
        DungeonHub.addWalls(true);
        Tavern.addWalls(true);
        Home.addWalls(true);

        //Align TavernMap
        this.Tavern.centerTavernMap();

        //Initialization
            this.CurrentMap = Home;
            this.CurrentcollisionLayer = Home.getcollisionLayer();

            //Init inventory for debugging
            Potion potion1 = new Potion(10,"petite potion");
            Potion potion2 = new Potion(20, "moyenne potion");
            Inventory bag = new Inventory();
            bag.addItem(potion1);
            bag.addItem(potion2);

            //Initialize the Hero
            this.Hero = hero;
            Hero.setX(CurrentMap.getX());
            Hero.setY(CurrentMap.getY());


            Hero.setBag(bag);

            Hero.setGraphicObject(new GraphicHero(Hero.getName(), Home));
            Hero.spawn(DungeonHub);
            Hero.spawn(Home);
            Hero.spawn(Tavern);  
            Merchant merchant = new Merchant(Hero,320,270,Tavern);
            merchant.spawn(Tavern);          
            //Initialize the Boss
            boss = new Boss(0,0,100, 0, 50,3, 10, null,new Massue("massue", 50, 2));
            boss.setGraphicObject(new GraphicBoss(boss.getName(), DungeonHub.getTiledMap().getTileSets()));
            //Initialize Dungeon
            this.Dungeon = new ArrayList<>();
            this.Dungeonlevel = 1;
            this.DungeonHub.setPVP("OFF");
            initializeDungeon(numberOfMaps);
        
    }
    
/* --------------------------------------------- GETTERS ------------------------------------- */
    public static World getInstance(Hero hero) {
    if (instance == null) {
        instance = new World(hero);
    }
    else instance.setHero(hero);
    return instance;
    }

    public Hero getHero(){
        return Hero;
    }
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
    public TiledMapTileLayer getCurrentcollisionLayer(){
        return CurrentcollisionLayer;
    }
    public TiledMapTileSets getTileSets(){
        return Tilesets;
    }
/*------------------------------------------------SETTERS------------------------------------ */
public void setHero(Hero hero){
    //Initialize the Hero
    Hero = hero;
    Hero.setX(CurrentMap.getX());
    Hero.setY(CurrentMap.getY());

    Hero.setGraphicObject(new GraphicHero(Hero.getName(), Home));
    Hero.spawn(DungeonHub);
    Hero.spawn(Home);
    Hero.spawn(Tavern);  
    Merchant merchant = new Merchant(Hero,320,270,Tavern);
    merchant.spawn(Tavern);          
}


/* --------------------------------------------- UPDATE ------------------------------------- */
    public Map update(Map map){
        ArrayList<Door> Doors = map.getDoors();

        if(Doors!=null){
            for (Door Door : Doors) {  
                if(Door.getBounds().overlaps(Hero.getHitbox())){
                    
                    System.out.println(" Door : " + map.getName() + " --> " + Door.getMap().getName() + " is toggled");
                    //change position of the Hero
                    map.updatelastposition(Hero.getlastX(), Hero.getlastY());
                    //Update map
                    updateCurrentMap(Door.getMap());
                    if (map.isOpen()==false) map.toggle();
                    map = CurrentMap;
                    //get the appropriate position for hero
                    if(Door.getMap().isOpen() || (map.getPVP()!="ON" && map!=Home)){          
                        Hero.setPosition(map.getLastposition());
                    }else{
                        Hero.setPosition(map.getX(), map.getY());
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
            if(map.getMusic()!=null){
                map.getMusic().play();
                if(this.CurrentMap.getMusic()!=null)this.CurrentMap.getMusic().pause();
            }
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
        int EnemyCount = Dungeonlevel + 5; // Increase enemy count
        
        // Create new, more powerful enemies
        System.out.println("creating ennemies ...");
        map.createRandomEnnemies(this.Tilesets, EnemyCount);

        System.out.println("Enemies created");
    }

    public void initializeDungeon(int numberOfMaps) {
        int bossX,bossY;
        Random randomX = new Random();
        Random randomY = new Random();

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
            if(i==0)this.DungeonHub.addDoor(new Door(470, 350, newMap));//DungeonHub is linked to Home
            else if(i==numberOfMaps)mapFactory.addDoorBetweenMaps(previousMap, DungeonHub);//Last map is linked back to DungegonHub
            else mapFactory.addDoorBetweenMaps(previousMap, newMap);

            //Track last door position for returning to last map at last position
            if(i!=0){
                float x = previousMap.getDoors().get(1).getX();
                float y = previousMap.getDoors().get(1).getY();

                int TileX = (int) x/32;
                int TileY = (int) y/32;

                //Add the Generated TiledMap into the Map
                previousMap.updateTiledmap(mapFactory.createRandomTiledMap(DungeonHubMap.getTiledMap().getTileSets(), TileX, TileY));
                //Add Collisions with the walls
                previousMap.addWalls(false);
                
                //Spawn Hero in the new map (This can be done in respawn)
                Hero.spawn(previousMap);
                //Spawn Boss in the last Map
                if(i == numberOfMaps){
                    do{
                        bossX = randomX.nextInt(CurrentMap.getmapWidth());
                        bossY = randomY.nextInt(CurrentMap.getmapHeight());
                    } while (!boss.isValidPosition(bossX,bossY, CurrentMap) || bossX<2 || bossY<2 || bossY> CurrentMap.getmapWidth()-2);
                    boss.setX(bossX*32);
                    boss.setY(bossY*32);
                    System.out.println("BOSS POS :" + bossX + " ; " + bossY);
        
                    boss.spawn(previousMap);
                }
            }
            //Add the map into the Dungeon
            Dungeon.add(previousMap);
            // Update the previous map reference for the next iteration
            previousMap = newMap;
        }
        
        System.out.println("             /////////////////Dungeon Initialized/////////////");
    }
    public void IsDungeonFinished(){
        if(CurrentMap==DungeonHub){
            if(boss.getPV()<=0){
                System.out.println("boss killed");
                Dungeonlevel++;
                Hero.setMoney(50);
                disposeDungeon();
                boss = new Boss(0,0,100+10*Dungeonlevel, Dungeonlevel*2, 50,2, 10, null,new Massue("massue", 50+5*Dungeonlevel, 2));
                boss.setGraphicObject(new GraphicBoss(boss.getName(), DungeonHub.getTiledMap().getTileSets()));
                
                int bossX,bossY;
                Random randomX = new Random();
                Random randomY = new Random();
                do{
                    bossX = randomX.nextInt(CurrentMap.getmapWidth());
                    bossY = randomY.nextInt(CurrentMap.getmapHeight());
                } while (!boss.isValidPosition(bossX,bossY, CurrentMap) || bossX<2 || bossY<2 || bossY> CurrentMap.getmapWidth()-2);
                boss.setX(bossX*32);
                boss.setY(bossY*32);
                System.out.println("BOSS POS :" + bossX + " ; " + bossY);
            

                initializeDungeon(numberOfMaps);
            }else for(Map map : Dungeon) map.updatelastposition(map.getX(), map.getY());;
        }
    }
    /*--------------------------------------------------DISPOSE------------------------------------------------------ */
    public void disposeDungeon(){

        for(Map map : Dungeon){
            ArrayList<Door> Doors = map.getDoors();
            if(map!=DungeonHub) disposeTiledmaps(map);
            if(Doors != null){
                int size = Doors.size();
                for(int i=0; i<size; i++){
                    Doors.remove(0);
                }
                System.out.println("Door list : " + Doors + "  for : " + map.getName());
            }else System.out.println("No doors");
        }
        DungeonHub.addDoor(new Door(-40, 100, Home));
        DungeonHub.updatelastposition(DungeonHub.getX(), DungeonHub.getY());
    }

    public void disposeTiledmaps(Map map){
        map.getTiledMap().dispose();
    }
    /*----------------------------------------------LOAD&SAVE---------------------------------------------------- */
   }
