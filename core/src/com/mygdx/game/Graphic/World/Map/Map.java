package com.mygdx.game.Graphic.World.Map;


import java.util.Comparator;
import java.util.Collections;

import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.TextureMapObject;
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

import com.mygdx.game.Graphic.GraphicObject.GraphicObject;
import com.mygdx.game.Graphic.GraphicObject.Elements.Door;
import com.mygdx.game.Graphic.GraphicObject.Elements.Element;
import com.mygdx.game.Graphic.GraphicObject.Elements.Wall;
import com.mygdx.game.Graphic.GraphicObject.GraphicCharacter.*;

import com.mygdx.game.Back.Character.Ennemie.Ennemie;

public class Map {

    private String Name;

    private float x,y;//position 0 of the character
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

    private ArrayList<Door> Door_list;
    private ArrayList<GraphicEnnemie> NPCs, DeadNPCs;
    private ArrayList<Element> Elements;
    private ArrayList<GraphicObject> Walls;
    private ArrayList<GraphicObject> Objects;

    private GraphicHero hero;

    private Music music;

    //constructeur
    public Map(float x, float y, TiledMap tiledmap, ArrayList<Door> Door_list, ArrayList<GraphicEnnemie> NPCs, Music music){
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
    public ArrayList<GraphicObject> getObjects(){
        return Objects;
    }
    public ArrayList<Element> getElements(){
        return Elements;
    }
    public ArrayList<GraphicEnnemie> getNPCs(){
        return NPCs;
    }
    public ArrayList<GraphicEnnemie> getDeadNPCs(){
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
    public GraphicHero getHero(){
        return this.hero;
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
    //Check for collision with an ennemie
    //Return true if there is a collision
    public boolean PNJcollision(GraphicHero hero){
        if(NPCs != null){
            for (GraphicEnnemie ennemie : NPCs){
                
                if(Math.abs(ennemie.getX()-hero.getX())<20 && Math.abs(ennemie.getY()-hero.getY())<20) return true;
                
            }
        }
        return false;
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
    public void setHero(GraphicHero hero){
        this.hero = hero;
    }

/* --------------------------------------LIST UPDATE ------------------------------------- */
    public void addDoor(Door door){;
        if(this.Door_list==null) this.Door_list = new ArrayList<>();
        this.Door_list.add(door);
    }
    public void addPNJ(GraphicEnnemie PNJ){
        if(this.NPCs==null) this.NPCs = new ArrayList<GraphicEnnemie>();
        this.NPCs.add(PNJ);
    }
    public void addElement(Element element){
        Elements.add(element);
    }
    public void addWalls(){
        TiledMapTileLayer Base = (TiledMapTileLayer) tiledmap.getLayers().get("Base");
        TiledMapTileLayer Middle = (TiledMapTileLayer) tiledmap.getLayers().get("Middle");
        TiledMapTileLayer Top = (TiledMapTileLayer) tiledmap.getLayers().get("Top");
        ArrayList<TiledMapTileLayer> Layers = new ArrayList<>();
        Layers.add(Base); Layers.add(Middle); Layers.add(Top);
        for(int X = 0; X<mapWidth; X++){
            for(int Y = 0; Y<mapHeight-1; Y++){
                for(TiledMapTileLayer layer : Layers){
                    Cell cell = layer.getCell(X, Y);
                    if(cell!=null && cell.getTile()!=null){
                        Wall wall = new Wall(X*tilewidth, Y*tileheight, 32, 32);
                        wall.setObject(new TextureMapObject(cell.getTile().getTextureRegion()));
                        Walls.add(wall);
                    }
                }
            }
        }
        System.out.println(Walls.size());

    }

    /*--------------------------------------------------------------LIST SORTING------------------------------------------------------------------- */
    public void sortObjects(){
        // Définition du Comparator pour trier en fonction de l'attribut y
        Comparator<GraphicObject> yComparator = new Comparator<GraphicObject>() {
            @Override
            public int compare(GraphicObject object1, GraphicObject object2) {
                boolean object1IsWall = object1 instanceof Wall;
                boolean object2IsWall = object2 instanceof Wall;
                //float distanceX = Math.abs(object1.getX()-object2.getY());

                // If one object is a Wall and the other isn't, prioritize the Wall
                if (object1IsWall || object2IsWall) {
                        return Float.compare(object1.getX(), object2.getX()); // Place object2 (Wall) before object1
                }
                // Comparaison des valeurs de y
                else return Float.compare(object2.getY(), object1.getY());
                
            }
        };

        // Utilisation de Collections.sort() avec le Comparator défini
        Collections.sort(Objects, yComparator);
    }
/*-------------------------------------------------------------------UPDATE------------------------------------------------------------------------ */

    public void update(float deltaTime){
        ArrayList<GraphicObject> List = new ArrayList<>();
        updateNPCs();
        if(NPCs!=null) List.addAll(NPCs);
        updateElements();
        if(Elements!=null) List.addAll(Elements);
        List.add(hero);
        List.addAll(Walls);
        Objects = List;
        sortObjects();
        for(GraphicObject object : Objects){
            object.update(deltaTime);
        }
    }
    public void updateElements(){ 
        if(Elements!=null){
            ArrayList<Element> List = new ArrayList<>();
            for(Element element : Elements){
                if(element.isValidPosition((int) element.getX()/tilewidth, (int) element.getY()/tileheight, this)) List.add(element);
            }
            Elements.clear();
            Elements = List;
        }
    }
    public void updateNPCs(){
        if (NPCs!=null){
            ArrayList<GraphicEnnemie> List = new ArrayList<>();
            for(GraphicEnnemie enemy : NPCs){
                if(enemy.getCharacter().getPV()<=0) DeadNPCs.add(enemy);
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
/* -----------------------------------------MOVE ENNEMIES----------------------------------------- */
    public void moveEnnemies(GraphicHero hero){
        for (GraphicEnnemie Ennemie : NPCs){
            Ennemie.move(hero, this);
        }
    }
/* -----------------------------------------ENNEMY ATTACK ---------------------------------------- */
    public ArrayList<GraphicEnnemie> lookforEnemyinRange(GraphicCharacter character){
        ArrayList<GraphicEnnemie> Ennemies = getNPCs();
        ArrayList<GraphicEnnemie> EnnemiesinRange = new ArrayList<>();
        if(Ennemies != null){
            for(GraphicEnnemie ennemie : Ennemies){
                if(ennemie.inRange(character, this)){
                    EnnemiesinRange.add(ennemie);
                }
            }
        }
        return EnnemiesinRange;
    }
    public ArrayList<GraphicEnnemie> lookforEnemyinRange(float X, float Y){
        ArrayList<GraphicEnnemie> Ennemies = getNPCs();
        ArrayList<GraphicEnnemie> EnnemiesinRange = new ArrayList<>();
        if(Ennemies != null){
            for(GraphicEnnemie ennemie : Ennemies){
                if(ennemie.inRange(X, Y, this)){
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
        
        ArrayList<GraphicEnnemie> PNJinRange = lookforEnemyinRange(hero);
        //Ennemies in range attack the Hero
        if(PNJinRange != null){
            int size = PNJinRange.size();
            for(int index = 0; index<size; index++){
                GraphicEnnemie Graphic_ennemie = PNJinRange.get(index);
                Ennemie ennemie = Graphic_ennemie.getCharacter();

                //get its attack timer and cooldown
                float attackTimer = ennemie.getAttackTimer();
                float attackCooldown = ennemie.getAttackCooldown();

                //Check cooldown
                if(attackTimer >= attackCooldown){
                    //Reset Timer and toggle Attack mode (on/off)
                    ennemie.setAttackTimer(0);
                    ennemie.toggle_Attack();
                    Graphic_ennemie.resetIndex();

                    //Perform the Attack
                    Graphic_ennemie.Attack(this);

                    //Kill the Hero if its HP are 0
                    if(hero.getCharacter().getPV() <= 0){
                        hero.getCharacter().killHero(this);
                        return true;
                    }
                }else ennemie.setAttackTimer(attackTimer + Gdx.graphics.getDeltaTime());//Increment Timer for CD

                //Attack animation if cooldown is up
                if (ennemie.isAttack_Charged()){
                    //Get battle sprite
                    Graphic_ennemie.setBattleTexture();
                }
            }
        }
        return false;
    }
    
    /*-----------------------------------------------------------------SPAWN ENNEMIES----------------------------------------------------------------------- */

    public void createRandomEnnemies(TiledMapTileSets Tilesets, int n) {
        GraphicEnnemieFactory ennemieFactory = new GraphicEnnemieFactory(Tilesets);
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
    
            GraphicEnnemie newEnnemie = ennemieFactory.createRandomGraphicEnnemie(x, y);
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

    public boolean checkDistancefromEnnemie(float X, float Y, ArrayList<GraphicEnnemie> NPCs){
        int pnj_distance = 50;
        if (!NPCs.isEmpty()) {
            for (GraphicEnnemie enemy : NPCs) {
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
