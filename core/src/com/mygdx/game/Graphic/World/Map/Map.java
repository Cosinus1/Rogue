package com.mygdx.game.Graphic.World.Map;

import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.audio.Music;


import java.util.ArrayList;
import java.util.Random;

import com.mygdx.game.Graphic.World.*;
import com.mygdx.game.Graphic.Elements.Door;
import com.mygdx.game.Graphic.GraphicCharacter.*;

public class Map {
    private float x,y;//position 0 of the character
    private Vector2 lastposition;
    private boolean isOpen;
    private float camx, camy; //position of the camera (used for Tavern)

    private TiledMap tiledmap;
    private TiledMapTileLayer collisionLayer;
    private int mapWidth;
    private int mapHeight;
    
    public OrthogonalTiledMapRenderer renderer;

    private String PVP;
    private ArrayList<Door> Door_list;
    private ArrayList<GraphicEnnemie> PNJ_list, DeadPNJ_list;
    private GraphicHero hero;
    private MapObjects Objects, Dead_Objects;
    private Music music;

    //constructeur
    public Map(float x, float y, TiledMap tiledmap, ArrayList<Door> Door_list, ArrayList<GraphicEnnemie> PNJ_list, Music music){
        this.isOpen = false;
        this.x = x;
        this.y = y;
        this.lastposition = new Vector2();
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

        //Init the List of Objects
        this.Door_list = Door_list;
        this.PNJ_list = PNJ_list;
        this.DeadPNJ_list = new ArrayList<>();
        this.music = music;
        //init renderer for the map
        renderer = new OrthogonalTiledMapRenderer(tiledmap);
        //init Objects
        Objects = new MapObjects();
        //For death rendering
        Dead_Objects = new MapObjects();
    }

/* --------------------------------------------- GETTERS ------------------------------------- */
    public boolean isOpen(){
        return isOpen;
    }
    public Vector2 getLastposition(){
        return lastposition;
    }
    public String getPVP(){
        return PVP;
    }
    public ArrayList<GraphicEnnemie> getPNJ_list(){
        return PNJ_list;
    }
    public ArrayList<GraphicEnnemie> getDeadPNJ_list(){
        return DeadPNJ_list;
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
    public MapObjects getObjects(){
        return this.Objects;
    }
    public MapObjects getDeadObjects(){
        return this.Dead_Objects;
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
    public boolean PNJcollision(GraphicHero hero){
        MapObjects objects = getObjects();
        for(MapObject object : objects){
            if (object.getName()!="hero"){
                float x = (float) object.getProperties().get("x")+10;
                float y = (float) object.getProperties().get("y");
                if(Math.abs(x-hero.getX() )<20 && Math.abs(y-hero.getY())<20) return false;
            }
        }
        return true;
    }
/* --------------------------------------------- SETTERS ------------------------------------- */
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

/* --------------------------------------------- UPDATE ------------------------------------- */
    public void addDoor(Door door){;
        if(this.Door_list==null) this.Door_list = new ArrayList<>();
        this.Door_list.add(door);
    }
    public void addPNJ(GraphicEnnemie PNJ){
        if(this.PNJ_list==null) this.PNJ_list = new ArrayList<GraphicEnnemie>();
        this.PNJ_list.add(PNJ);
    }

    public ArrayList<GraphicEnnemie> lookforPNJinRange(GraphicCharacter character){
        ArrayList<GraphicEnnemie> Ennemies = getPNJ_list();
        ArrayList<GraphicEnnemie> EnnemiesinRange = new ArrayList<>();
        if(Ennemies != null){
            for(GraphicEnnemie ennemie : Ennemies){
                float x = ennemie.getX();
                float y = ennemie.getY();
                double distanceX = x - character.getX();
                double distanceY = y - character.getY();
                if(Math.sqrt(Math.pow(distanceY, 2) + Math.pow(distanceX, 2)) < character.getCharacter().getRange()){
                    EnnemiesinRange.add(ennemie);
                }
            }
        }
        return EnnemiesinRange;
    }
    public void updateTiledmap(TiledMap newTiledMap) {
        this.tiledmap = newTiledMap;
        this.collisionLayer = (TiledMapTileLayer) newTiledMap.getLayers().get("Base");
        this.renderer = new OrthogonalTiledMapRenderer(newTiledMap);
    }
    public void updatelastposition(float x, float y){
        lastposition.set(x, y);
    }

    public void moveEnnemies(GraphicHero hero){
        for (GraphicEnnemie Ennemie : PNJ_list){
            Ennemie.move(hero, this);
        }
    }
/* ----------------------------------------------------------------------------------------- */
    //Sorting in order to render the Characters according to their "y" between each other
    //That way, the hero is rendered behind(resp. ahead) a monster when his Y is lower(resp. greater) than the mnster's
    public void sortObjects(){
        //New Objects to store the sorted Objects
        MapObjects ObjectsSorted = new MapObjects();
        int size = Objects.getCount();

        do{
            for(MapObject object : Objects){
            boolean value = true;
                float y1 = (float) object.getProperties().get("y");
                for(int index=0; index<size; index++){
                    float y2 = (float) Objects.get(index).getProperties().get("y");
                    if(y1<y2)value = false;
                }
                if(value){
                    ObjectsSorted.add(object);
                    Objects.remove(object);
                    size--;
                }
            }
        }while(size!=0);
        Objects = ObjectsSorted;
    }
    
    //Create Methods

    public void createRandomEnnemies(World world, int n) {
        GraphicEnnemieFactory ennemieFactory = new GraphicEnnemieFactory();
        TiledMapTileLayer collisionLayer = getcollisionLayer();
    
        // Get the dimensions of the map
        int mapWidth = collisionLayer.getWidth();
        int mapHeight = collisionLayer.getHeight();
    
        Random random = new Random();
    
        for (int i = 0; i < n; i++) {
            int randomX, randomY;
            boolean withinDistance;
            boolean tooClose;
    
            do {
                randomX = random.nextInt(mapWidth);
                randomY = random.nextInt(mapHeight);
    
                // Check distance from walls
                withinDistance = checkDistancefromWall(randomX, randomY);
    
                // Check distance from other enemies
                tooClose = checkDistancefromEnnemie(randomX, randomY, PNJ_list);
    
            } while (!withinDistance || tooClose);
    
            float x = randomX * collisionLayer.getTileWidth();
            float y = randomY * collisionLayer.getTileHeight();
    
            GraphicEnnemie newEnnemie = ennemieFactory.createRandomGraphicEnnemie(x, y,world);
            if (newEnnemie != null) {
                newEnnemie.spawn(this, world.getDungeon().getTiledMap().getTileSets(), newEnnemie, newEnnemie.getCharacter().getName());
            }
        }
    }

    public boolean checkDistancefromWall(int X, int Y){
        TiledMapTileLayer Layer = (TiledMapTileLayer) this.getTiledMap().getLayers().get("Base");
        Cell cell = Layer.getCell(X, Y);
        if (cell != null) {
            if(cell.getTile().getProperties().containsKey("blocked")){
                return false;  
            }
        }
        return true;

    }

    public boolean checkDistancefromEnnemie(float X, float Y, ArrayList<GraphicEnnemie> PNJ_list){
        int pnj_distance = 50;
        if (!PNJ_list.isEmpty()) {
            for (GraphicEnnemie enemy : PNJ_list) {
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
        
}
