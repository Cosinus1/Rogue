package com.mygdx.game.Back.World;


import java.util.Comparator;
import java.util.Collections;

import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


import java.util.ArrayList;
import java.util.Random;

import com.mygdx.game.Back.Object.Character.Ennemie.Ennemie;
import com.mygdx.game.Back.Object.Character.Ennemie.EnnemieFactory;
import com.mygdx.game.Back.Object.Character.Hero.Hero;
import com.mygdx.game.Back.Object.Element.Door;
import com.mygdx.game.Back.Object.Element.Element;
import com.mygdx.game.Back.Object.Element.Wall;
import com.mygdx.game.Back.Object.Element.WallFactory;
import com.mygdx.game.Back.Object.Force;
import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Back.Object.Character.Character;
import com.mygdx.game.Back.Object.Character.Merchant;

public class Map {

    private String Name;

    private float x,y;//position 0 of the Hero
    private Vector2 lastposition;
    private boolean isOpen;
    private float camx, camy; //position of the camera (used for Tavern)

    private TiledMap tiledmap;
    private TiledMapTileLayer collisionLayer;
    private int mapWidth;
    private int mapHeight;
    private int tilewidth = 32;
    private int tileheight = 32;
    
    public OrthogonalTiledMapRenderer renderer;

    private String PVP;

    //Objects Variables
    private Merchant merchant;
    private ArrayList<Door> Door_list;
    private ArrayList<Ennemie> NPCs, DeadNPCs;
    private ArrayList<Element> Elements;
    private ArrayList<Wall> Walls;
    private ArrayList<Object> Objects;

    private Hero hero;
    private WallFactory wallFactory;

    //MP3
    private Music music;

    //constructeur
    public Map(float x, float y, TiledMap tiledmap, ArrayList<Door> Door_list, ArrayList<Ennemie> NPCs, Music music){
        this.isOpen = false;
        this.x = x;
        this.y = y;
        this.lastposition = new Vector2();
        this.lastposition.set(x, y);
        this.camx = 0;
        this.camy = 0;

        this.tiledmap = tiledmap;
        //Get the collision Layer
        if(tiledmap!=null) this.collisionLayer = (TiledMapTileLayer) tiledmap.getLayers().get("collisionLayer");
        //Get Map dimensions
        if(tiledmap == null){
            this.mapHeight = 20;
            this.mapWidth = 30;
        } else{
            TiledMapTileLayer Layer = (TiledMapTileLayer) tiledmap.getLayers().get(0);
            this.mapHeight =  Layer.getHeight();
            this.mapWidth = Layer.getWidth();
        }

        
        //Init Doors
        this.Door_list = Door_list;
        //Init the List of Objects
        this.Objects = new ArrayList<>();
        this.Elements = new ArrayList<>();
        this.Walls = new ArrayList<>();
        this.NPCs = NPCs;
        this.DeadNPCs = new ArrayList<>();
        this.music = music;
        //init renderer for the map
        renderer = new OrthogonalTiledMapRenderer(tiledmap);
    }

/* --------------------------------------------- GETTERS ------------------------------------- */
    public String getName(){
        return Name;
    }
    public boolean isOpen(){
        return isOpen;
    }
    public Vector2 getLastposition(){
        return lastposition;
    }
    public String getPVP(){
        return PVP;
    }
    public ArrayList<Object> getObjects(){
        return Objects;
    }
    public ArrayList<Element> getElements(){
        return Elements;
    }
    public ArrayList<Ennemie> getNPCs(){
        return NPCs;
    }
    public ArrayList<Ennemie> getDeadNPCs(){
        return DeadNPCs;
    }
    public TiledMap getTiledMap(){
        return this.tiledmap;
    }
    public TiledMapTileLayer getcollisionLayer(){
        return this.collisionLayer;
    }
    public MapLayer getobjectLayer(){
        return this.getTiledMap().getLayers().get("Object Layer 1");
    }
    public Hero getHero(){
        return this.hero;
    }
    public Merchant getMerchant(){
        return merchant;
    }
    public Music getMusic(){
        return music;
    }
    public ArrayList<Door> getDoors(){
        return this.Door_list;
    }
    public OrthogonalTiledMapRenderer getRenderer(){
        return this.renderer;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public float getcamX(){
        return camx;
    }
    public float getcamY(){
        return camy;
    }
    public int getmapWidth(){
        return this.mapWidth;
    }
    public int getmapHeight(){
        return this.mapHeight;
    }
    public int getTilewidth(){
        return this.tilewidth;
    }

/* --------------------------------------------- SETTERS ------------------------------------- */
    public void setName(String name){
        Name = name;
    }
    public void toggle(){
        isOpen = !isOpen;
    }
    public void centerTavernMap(){
        camx = -225;
        camy = -150;
    }
    public void setcollisionLayer(TiledMapTileLayer Layer){
        this.collisionLayer = Layer;
    }
    public void setPVP(String pvp){
        this.PVP = pvp;
    }
    public void setHero(Hero hero){
        this.hero = hero;
    }
    public void setMerchant(Merchant merchant){
        this.merchant = merchant;
    }

/*------------------------------------------CHECKERS-------------------------------------------- */
    public boolean insideMap(Object object){
        float X = object.getX();
        float Y = object.getY();
        if( X<0 || X> mapWidth*tilewidth || Y<0 || Y>mapHeight*tileheight) return false;
        return true;
    }
/* --------------------------------------LIST UPDATE ------------------------------------- */
    public void addDoor(Door door){;
        if(this.Door_list==null) this.Door_list = new ArrayList<>();
        this.Door_list.add(door);
    }
    public void addPNJ(Ennemie PNJ){
        if(this.NPCs==null) this.NPCs = new ArrayList<Ennemie>();
        this.NPCs.add(PNJ);
    }
    public void addElement(Element element){
        Elements.add(element);
    }
    public void addWalls() {
        WallFactory wallFactory = new WallFactory(tiledmap);
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight - 1; y++) {
                Wall wall = wallFactory.createWall(x, y);
                if(wall!=null) Walls.add(wall);
            }
        }
        System.out.println(Walls.size());
    }
    

    /*--------------------------------------------------------------LIST SORTING------------------------------------------------------------------- */
    public void sortObjects(){
        // Définition du Comparator pour trier en fonction de l'attribut y
        Comparator<Object> yComparator = new Comparator<Object>() {
            @Override
            public int compare(Object object1, Object object2) {
                float y1 = object1.getY();
                float y2 = object2.getY();
                
                // Comparaison des valeurs de y
                return Float.compare(y2, y1);
                
            }
        };

        // Utilisation de Collections.sort() avec le Comparator défini
        Collections.sort(Objects, yComparator);
    }
/*-------------------------------------------------------------------UPDATE------------------------------------------------------------------------ */

    public void update(float deltaTime){
        ArrayList<Object> List = new ArrayList<>();
        // Udate & add remaining Objects
            //NPCs
            updateNPCs(deltaTime);
            if(NPCs!=null) List.addAll(NPCs);
            //Elements
            updateElements(deltaTime);
            if(Elements!=null) List.addAll(Elements);
            //Merchant
            if(merchant != null) List.add(merchant);if(merchant != null) List.add(merchant);
            //Hero
            hero.update(deltaTime);
            List.add(hero);
            if(merchant!=null){
                List.add(merchant);
            }
            //Walls (no update necessary because they dont move)
            List.addAll(Walls);
        Objects = List;
        sortObjects();
    }
    public void updateElements(float deltaTime){ 
        if(Elements!=null){
            ArrayList<Element> List = new ArrayList<>();
            for(Element element : Elements){
                element.update(deltaTime);
                Objectcollision(element);
                Wallcollision(element);
                if((element.getSpeedX()!=0 || element.getSpeedY()!=0) && (insideMap(element))) List.add(element);
            }
            Elements.clear();
            Elements = List;
        }
    }
    public void updateNPCs(float deltaTime){
        if (NPCs!=null){
            ArrayList<Ennemie> List = new ArrayList<>();
            for(Ennemie enemy : NPCs){
                enemy.update(deltaTime);
                Objectcollision(enemy);
                Wallcollision(enemy);
                if(enemy.getPV()<=0) DeadNPCs.add(enemy);
                else List.add(enemy);
            }
            NPCs.clear();
            NPCs = List;
        }
    }
    public void updateTiledmap(TiledMap newTiledMap) {
        this.tiledmap = newTiledMap;
        //Add the collision Layer
            this.collisionLayer = (TiledMapTileLayer) newTiledMap.getLayers().get("Base");
            if(collisionLayer == null) System.err.println("No collision Layer found");
        //Set renderer
        this.renderer = new OrthogonalTiledMapRenderer(newTiledMap);
    }
    public void updatelastposition(float x, float y){
        lastposition.set(x, y);
    }
/*-------------------------------------------COLLISION--------------------------------------------- */
    //Check for collision with an ennemie
    //Return true if there is a collision
    public boolean PNJcollision(Object object){
        if(NPCs != null){
            for (Ennemie ennemie : NPCs){
                
                float distanceX = ennemie.getX()-object.getX();
                float distanceY = ennemie.getY()-object.getY();

                if(Math.abs(distanceX)<25 && Math.abs(distanceY)<20){
                    float signX;
                    float signY;
                    if (Math.abs(distanceX)<20) signX = 0;
                    else signX = Math.signum(distanceX);
                    if (Math.abs(distanceY)<10) signY = 0;
                    else signY = Math.signum(distanceY);

                    object.applyInstantForce(new Force(10000, 10000, -signX, -signY));
                    ennemie.applyInstantForce(new Force(10000, 10000, signX, signY));
                

                }
                
            }
        }
        return false;
    }
    public void Wallcollision(Object object){
        if (Walls!=null){
            for (Wall wall : Walls){

                float distanceX = wall.getX()-object.getX();
                float distanceY = wall.getY()-object.getY();
                if(Math.abs(distanceX)<14 && Math.abs(distanceY)<14){
                    float signX = Math.signum(-distanceX);
                    float signY = Math.signum(-distanceY);
                    if (Math.abs(distanceX)>Math.abs(distanceY)) signX = Math.signum(-distanceX);
                    else signY = Math.signum(-distanceY);
                    //Apply force to object (we ignore force(Object->wall))
                    object.applyForce(new Force(50000, 50000, signX, signY));
                }
            }
        }
    }
    public void Objectcollision(Object object1){
        ArrayList<Object> List = new ArrayList<>();
        if(NPCs!=null) List.addAll(NPCs); if(Elements!=null) List.addAll(Elements); List.add(hero);
        if (List!=null){
            for (Object object2 : List){

                float distanceX = object2.getX()-object1.getX();
                float distanceY = object2.getY()-object1.getY();
                if(Math.abs(distanceX)<16 && Math.abs(distanceY)<16){
                    
                    float signX = 0;
                    float signY = 0;
                    if (Math.abs(distanceX)>Math.abs(distanceY)) signX = Math.signum(distanceX);
                    else signY = Math.signum(distanceY);
                    
                    object1.applyForce(new Force(10000, 10000, -signX, -signY));
                    object2.applyForce(new Force(10000, 10000, signX, signY));
                }
            }
        }
    }
/* -----------------------------------------MOVE ENNEMIES----------------------------------------- */
    public void moveEnnemies(Hero hero){
        for (Ennemie Ennemie : NPCs){
            Ennemie.move(hero, this);
        }
    }
/* -----------------------------------------ENNEMY ATTACK ---------------------------------------- */
    public ArrayList<Ennemie> lookforEnemyinRange(Character character){
        ArrayList<Ennemie> Ennemies = getNPCs();
        ArrayList<Ennemie> EnnemiesinRange = new ArrayList<>();
        if(Ennemies != null){
            for(Ennemie ennemie : Ennemies){
                if(ennemie.inRange(character, this)){
                    EnnemiesinRange.add(ennemie);
                }
            }
        }
        return EnnemiesinRange;
    }
    
    //Apply damage to NPC from Element
    public void ElementAttack(){
        if(Elements!=null) for (Element element : Elements){
            element.LookforAttack(this);
        }
    }
    //Apply damage to hero and return true if the hero is dead, false if not
    public boolean PNJAttack(){
        
        ArrayList<Ennemie> PNJinRange = lookforEnemyinRange(hero);
        //Ennemies in range attack the Hero
        if(PNJinRange != null){
            for(Ennemie ennemie : PNJinRange){
                //Attack
                ennemie.Attack(this);
                //Kill the Hero if its HP are 0
                    if(hero.getPV() <= 0){
                        hero.killHero(this);
                        return true;
                    }
                ennemie.IncrementAttackTimer(Gdx.graphics.getDeltaTime());//Increment Timer for CD
            }
        }
        return false;
    }
    
    /*-----------------------------------------------------------------SPAWN ENNEMIES----------------------------------------------------------------------- */

    public void createRandomEnnemies(TiledMapTileSets Tilesets, int n) {
        EnnemieFactory ennemieFactory = new EnnemieFactory(Tilesets);
        TiledMapTileLayer collisionLayer = getcollisionLayer();
    
        // Get the dimensions of the map
        int mapWidth = collisionLayer.getWidth();
        int mapHeight = collisionLayer.getHeight();
    
        Random random = new Random();
    
        for (int i = 0; i < n; i++) {
            int randomX, randomY;
            boolean withinDistance;
            boolean tooClose;
            
            int buffer = 0;
            do {
                randomX = 5+random.nextInt(mapWidth-10);
                randomY = 5+random.nextInt(mapHeight-10);
    
                // Check distance from walls
                withinDistance = checkDistancefromWall(randomX, randomY);
    
                // Check distance from other enemies
                tooClose = checkDistancefromEnnemie(randomX, randomY, NPCs);
    
            } while (!withinDistance || tooClose || buffer>1000);

            if(buffer>1000) System.err.println("Buffer overloaded : " + buffer);
    
            float x = randomX * collisionLayer.getTileWidth();
            float y = randomY * collisionLayer.getTileHeight();
    
            Ennemie newEnnemie = ennemieFactory.createRandomEnnemie(x, y);
            if (newEnnemie != null) {
                newEnnemie.spawn(this);
            }
        }
    }

    public boolean checkDistancefromWall(int X, int Y){
        TiledMapTileLayer Layer = (TiledMapTileLayer) this.getTiledMap().getLayers().get("Base");
        if (Layer == null) System.out.println("Layer is null");
        Cell cell = Layer.getCell(X, Y);
        if (cell != null && cell.getTile()!=null) {
            if(cell.getTile().getProperties().containsKey("blocked")){
                return false;  
            }
        }
        return true;

    }

    public boolean checkDistancefromEnnemie(float X, float Y, ArrayList<Ennemie> NPCs){
        int pnj_distance = 50;
        if (!NPCs.isEmpty()) {
            for (Ennemie enemy : NPCs) {
                float enemyX = enemy.getX();
                float enemyY = enemy.getY();
                double distanceSquared = (enemyX - X) * (enemyX - X) + (enemyY - Y) * (enemyY - Y);
                if (distanceSquared < pnj_distance * pnj_distance) {
                            return true;
                    
                }
            }
        }
        return false;
    }

    /*------------------------------------------------------------DISPOSE--------------------------------------------------------------------- */
        
}
