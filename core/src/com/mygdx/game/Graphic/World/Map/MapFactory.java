package com.mygdx.game.Graphic.World.Map;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;


import com.mygdx.game.Graphic.GraphicObject.Elements.Door;
import com.mygdx.game.Graphic.GraphicObject.GraphicCharacter.GraphicEnnemie;

import java.util.ArrayList;
import java.util.Random;


public class MapFactory {

    int mapWidth = 30;
    int mapHeight = 20;
    int tilewidth = 32;

    public Map createMap(float x, float y, TiledMap tiledmap, Music music, Map previousMap) {
        ArrayList<Door> doorList = new ArrayList<>();
        ArrayList<GraphicEnnemie> PNJ_list = new ArrayList<>();
        // Door to go back to the previous map
        Door backDoor = new Door(x-100, y, previousMap);
        doorList.add(backDoor);

        // Creating the new map (the other door is not specified yet)
        Map newMap = new Map(x, y, tiledmap, doorList, PNJ_list, music);

        return newMap;
    }

    public void addDoorBetweenMaps(Map previousMap, Map nextMap) {
        int mapWidth = previousMap.getmapWidth();
        int mapHeight = previousMap.getmapHeight();
        int[] doorCoordinates = generateRandomDoorCoordinates(mapWidth, mapHeight);
        Door doorToNextMap = new Door((float) doorCoordinates[0]*tilewidth, (float) doorCoordinates[1]*tilewidth, nextMap);
        previousMap.addDoor(doorToNextMap);
    }
    /*------------------------------------------CREATE / GENERATE--------------------------------------------------------- */

    public TiledMap createRandomTiledMap(TiledMapTileSets tileSets, int EndX, int EndY) {
        System.out.println("////////////////////////// CREATING RANDOM MAP..... ////////////////////////////");
        int StartX = 0;
        int StartY = 3;
    
        // Create a new TiledMap
        TiledMap tiledMap = new TiledMap();
    
        // Create TiledMapTileLayers for ground, base, middle, and top parts of walls
        TiledMapTileLayer groundLayer = new TiledMapTileLayer(mapWidth, mapHeight, tilewidth, tilewidth);
        TiledMapTileLayer baseLayer = new TiledMapTileLayer(mapWidth, mapHeight, tilewidth, tilewidth);
        TiledMapTileLayer middleLayer = new TiledMapTileLayer(mapWidth, mapHeight, tilewidth, tilewidth);
        TiledMapTileLayer topLayer = new TiledMapTileLayer(mapWidth, mapHeight, tilewidth, tilewidth);
        groundLayer.setName("Ground");
        baseLayer.setName("Base");
        middleLayer.setName("Middle");
        topLayer.setName("Top");
    
        // Fill groundLayer with ground tiles
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                groundLayer.setCell(x, y, createCell(createOpeningTile(tileSets)));
            }
        }
    
        // Add wall tiles on all borders (only for baseLayer)
        for (int i = 1; i < mapWidth-1; i++) {
            baseLayer.setCell(i, 0, createCell(createWallTile(tileSets, "base", "horizontal")));
            baseLayer.getCell(i, 0).getTile().getProperties().put("border", "border");
            baseLayer.setCell(i, mapHeight - 2, createCell(createWallTile(tileSets, "base", "horizontal")));
            baseLayer.getCell(i, mapHeight - 2).getTile().getProperties().put("border", "border");
        }
        for (int i = 1; i < mapHeight-1; i++) {
            baseLayer.setCell(0, i, createCell(createWallTile(tileSets, "base", "vertical")));
            baseLayer.getCell(0, i).getTile().getProperties().put("border", "border");
            baseLayer.setCell(mapWidth-1, i, createCell(createWallTile(tileSets, "base", "vertical")));
            baseLayer.getCell(mapWidth-1, i).getTile().getProperties().put("border", "border");
        }
        // Add openings for doors (specify the positions and tiles for openings)
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                baseLayer.setCell(StartX+i-1, StartY+j-1, createCell(createOpeningTile(tileSets)));
                baseLayer.setCell(EndX+i-1, EndY+j-1, createCell(createOpeningTile(tileSets)));
            }
        }
        //Add base walls inside the baseLayer
        generateRandomWalls(baseLayer, tileSets);
    
        // Add the Top tiles to the topLayer
        // Iterate through the baseLayer to add top walls to the topLayer
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                Cell baseCell = baseLayer.getCell(x, y);
                if (baseCell != null) {
                    TiledMapTile baseWallTile = baseCell.getTile();
                    if(baseWallTile != null && baseWallTile.getProperties().containsKey("wall")){
                        if (baseWallTile.getProperties().get("level").equals("base")) {
                            // Check if the wall in the baseLayer is at the northern or western border
                            String Orientation = (String) baseWallTile.getProperties().get("orientation");
                            boolean isNorthernBorder = (y == mapHeight);
                            boolean isWesternBorder = (x == 0);

                            // If not on the border, add the top wall to the topLayer
                            if (!isNorthernBorder && !isWesternBorder) {
                                TiledMapTile topWallTile = createWallTile(tileSets, "top", Orientation);
                                topLayer.setCell(x - 1, y + 1, createCell(topWallTile));
                            }
                        }
                    }
                }
            }
        }
        // Add the middle tiles to the middleLayer
        // Iterate through the baseLayer to add middleright and middleleft walls to the middleLayer
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight+1; y++) {
                Cell baseCell = baseLayer.getCell(x, y);
                if (baseCell != null) {
                    TiledMapTile baseWallTile = baseCell.getTile();
                    if(baseWallTile != null && baseWallTile.getProperties().containsKey("wall")){
                        if (baseWallTile != null && baseWallTile.getProperties().get("level").equals("base")) {
                            // Check if the wall in the baseLayer is at the northern or western border
                            String Orientation = (String) baseWallTile.getProperties().get("orientation");
                            boolean isNorthernBorder = (y == mapHeight);
                            boolean isWesternBorder = (x == 0);

                            // If not on the  Northern border, add the middleright wall to the middleLayer
                            if (!isNorthernBorder) {
                                TiledMapTile middlerightWallTile = createWallTile(tileSets, "middleright", Orientation);
                                middleLayer.setCell(x, y+1, createCell(middlerightWallTile));
                            }
                            // If not on the  Western border, add the middleleft wall to the middleLayer
                            if (!isWesternBorder) {
                                TiledMapTile middleleftWallTile = createWallTile(tileSets, "middleleft", Orientation);
                                middleLayer.setCell(x-1, y, createCell(middleleftWallTile));
                            }
                        
                        }
                    }
                }
            }
        }

        // Add the base, middle, and top layers to the tiled map

        tiledMap.getLayers().add(groundLayer);
        tiledMap.getLayers().add(baseLayer);
        tiledMap.getLayers().add(middleLayer);
        tiledMap.getLayers().add(topLayer);    
        // Return the new map
        System.out.println("////////////////////////////RANDOM MAP CREATED/////////////////////////////////");
        return tiledMap;
    }
    
    public void generateRandomWalls(TiledMapTileLayer baseLayer, TiledMapTileSets tileSets) {
    
        Random randomStart = new Random();
        Random randomEnd = new Random();
    
        int i = 0;
        TiledMapTileLayer newBase;
        TiledMapTileLayer backupBase = cloneLayer(baseLayer);//Back up the baseLayer to avoid chain<3 to be added (ugly)

    
        while (i < 5) { // You can adjust the number of chains (5 is just an example)
            // Choose a random starting point along the border
            int startX, startY;
            //Choose a random ending point
            int endX, endY;

            newBase = cloneLayer(baseLayer);

            
            System.out.println("CHAIN No : " + i);

            // Ensure starting point is  on borders if i< 3 else not too close to the corners 
            int breakwhile = 0;
            if(i<4){
                    startX = randomStart.nextInt(mapWidth-3)+2;
                    startY = randomStart.nextInt(2)*(mapHeight-4)+1;
            }else{
            do {
                breakwhile++;
                startX = randomEnd.nextInt(mapWidth - 10) + 5; 
                startY = randomEnd.nextInt(mapHeight - 10) + 5; 
    
            } while (isNearAnotherChain(baseLayer, startX, startY) && breakwhile<2000);
            System.out.println(breakwhile);
            }
            // Set the initial coordinates
            int currentX = startX;
            int currentY = startY;
            int distanceX, distanceY;
            
            breakwhile = 0;
            do {
                breakwhile++;
                endX = randomEnd.nextInt(mapWidth - 10) + 5; 
                endY = randomEnd.nextInt(mapHeight - 10) + 5; 
                distanceX = (endX-currentX)*(endX-currentX);    
                distanceY = (endY-currentY)*(endY-currentY);
                
    
            } while ((distanceX+distanceY<9) && !isValidTrajectory(startX, startY, endX, endY, baseLayer) && !isNearAnotherChain(baseLayer, endX, endY) && breakwhile<2000);
            System.out.println("startX : " + startX + "/ startY : " + startY);
            System.out.println("endX : " + endX + " / endY : " + endY);
            if(breakwhile>=2000) System.out.println(" //BREAK//  isValidTraj : " + isValidTrajectory(startX, startY, endX, endY, baseLayer) + " isNearChain : " + isNearAnotherChain(baseLayer, endX, endY));

            int chainLength = 0; // Initialize chainLength variable
            
            int delta = 1;

            int direction = -1;
            int orientation = -1;
            while (chainLength < 20) { //Desired chainlength
                //get the distance between Current Position and End & orientation
                distanceX = (endX-currentX)*(endX-currentX);
                distanceY = (endY-currentY)*(endY-currentY);
                int signX = (int) Math.signum(endX-currentX);
                int signY = (int) Math.signum(endY-currentY);
                delta = randomEnd.nextInt(4);

                if(0<delta && !isNearAnotherChain(newBase, currentX+signX, currentY)){
                    chainLength++;
                    switch (signY) {
                        case -1:
                            if(direction == 0){
                                if (orientation == -1)baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "down right")));
                                else baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "left down")));

                            }
                            else baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "vertical")));
                            currentY--;
                            orientation = -1;
                            direction = 1;
                            break;
                    
                        case 1:
                            if(direction == 0){
                                if (orientation == -1)baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "up right")));
                                else baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "left up")));

                            }
                            else baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "vertical")));
                            currentY++;
                            orientation = 1;
                            direction = 1;
                            break;
                        
                    }
                    
                }else if(!isNearAnotherChain(newBase, currentX, currentY+signY)){
                    chainLength++;
                    switch (signX) {
                        case -1:
                            if(direction == 1){
                                if (orientation == -1)baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "left up")));
                                else baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "left down")));

                            }
                            else baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "horizontal")));
                            currentX--;
                            orientation = -1;
                            direction = 0;
                            break;
                    
                        case 1:
                            if(direction == 1){
                                if (orientation == -1)baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "up right")));
                                else baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "down right")));

                            }
                            else baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "horizontal")));
                            currentX++;
                            orientation = 1;
                            direction = 0;
                            break;
                            
                        
                    }
                    
                }
                else{
                    endX -= signX;
                    endY -= signY;
                }
                if (distanceX + distanceY == 0) break;
               
            }
            //Backup the base if the chain is too short
            //if(chainLength<3) baseLayer = backupBase;    
            i++; // Increment the chain count
        }
    }    
    
    private TiledMapTile createWallTile(TiledMapTileSets tileSets, String level, String orientation) {
        TiledMapTile wallTile = null;
    
        // Iterate through the tilesets to find the wall tile for the specified level
        for (TiledMapTile tile : tileSets.getTileSet("perspective_walls")) {
            if (tile.getProperties().containsKey("wall") && tile.getProperties().get("level").equals(level) && tile.getProperties().get("orientation").equals(orientation)) {
                wallTile = tile;
                break;
            }
        }
    
        if (wallTile == null) {
            System.out.println("WallTile for level " + level + " not found");
        }
    
        return wallTile;
    }
    
    private TiledMapTile createOpeningTile(TiledMapTileSets tileSets) {
        TiledMapTile openingTile = null;

        // Iterate through the tilesets to find the opening tile
        for (TiledMapTile tile : tileSets.getTileSet("PathAndObjects")) {
            if (tile.getProperties().containsKey("ground")) {
                openingTile = tile;
                break;
            }
        }

        if (openingTile == null) {
            // Handle the case where the opening tile is not found
            // You can log an error or take appropriate action
        }

        return openingTile;
    }

    private Cell createCell(TiledMapTile tile) {
        Cell cell = new Cell();
        cell.setTile(tile);
        return cell;
    }

    public int[] generateRandomDoorCoordinates(int mapWidth, int mapHeight) {
        Random random = new Random();
        int[] doorCoordinates = new int[2];

        int border = 1;//random.nextInt(3);
        
        switch (border) {
            case 0: // North border
                doorCoordinates[0] = random.nextInt(mapWidth-4) + 2; // Random x-coordinate on the border
                doorCoordinates[1] = mapHeight - 1; // Y-coordinate at the northern border
                break;
            case 1: // East border
                doorCoordinates[0] = mapWidth - 1; // X-coordinate at the eastern border
                doorCoordinates[1] = random.nextInt(mapHeight-4) + 2; // Random y-coordinate on the border
                break;
            case 2: // South border
                doorCoordinates[0] = random.nextInt(mapWidth/2) + 8; // Random x-coordinate on the border
                doorCoordinates[1] = 0; // Y-coordinate at the southern border
                break;
        }
        
        return doorCoordinates;
    }
    /*------------------------------------------------- CLONE LAYER---------------------------------------------------------- */
    public TiledMapTileLayer cloneLayer(TiledMapTileLayer Layer) {
        TiledMapTileLayer clonedLayer = new TiledMapTileLayer(Layer.getWidth(), Layer.getHeight(), Layer.getTileWidth(), Layer.getTileHeight());

        // Copy cell information from Layer to clonedLayer
        for (int x = 0; x < Layer.getWidth(); x++) {
            for (int y = 0; y < Layer.getHeight(); y++) {
                clonedLayer.setCell(x, y, Layer.getCell(x, y));
            }
        }

        return clonedLayer;
    }
    /*---------------------------------------------------CHECKERS-------------------------------------------------------------- */
    
    public boolean isValidTrajectory(int X, int Y, int endX, int endY, TiledMapTileLayer Base){
        int moveX = (int) Math.signum(endX-X);
        int moveY = (int) Math.signum(endY-Y);
        int newX = X + moveX;
        int newY = Y + moveY;
        //Check if we arrived at target
        if (X==endX && Y==endY) return true;

        //Otherwise check if the next step is valid
            //Check if we are at the same X than the target
            boolean isValidX;
            if(endX==X){
                //No move on X needed so it's a valid position on X
                isValidX = true;
                newX -= moveX;
            }else isValidX = isValidPosition(X+moveX, Y, Base);
            //Check if we are at the same Y than the target
            boolean isValidY;
            if(endY==Y){
                //No move on Y needed so it's a valid position on Y
                isValidY = true;
                newY -= moveY;
            }else isValidY = isValidPosition(X, Y+moveY, Base);

        if(isValidX && isValidY){
            return isValidTrajectory(newX, newY, endX, endY, Base);
        }else return false;// A wall is in the trajectory
    }
    public boolean isValidPosition(int X, int Y, TiledMapTileLayer Base) {
        //Check Map boundaries
        if(X<1 || X>(mapWidth) || Y<1 || Y>(mapHeight-2)) return false;
        // Check distance from walls
        return checkDistancefromWall(X, Y, Base);
    }
    public boolean checkDistancefromWall(int X, int Y, TiledMapTileLayer Base){
        if (Base == null) System.out.println(Base);
        Cell cell = Base.getCell(X, Y);
        if (cell != null) {
            if(cell.getTile().getProperties().containsKey("blocked")){
                return false;  
            }
        }
        return true;
    }

    private boolean isNearAnotherChain(TiledMapTileLayer baseLayer, int startX, int startY) {
        int searchRange = 2; // Define the range to search for existing chains
    
        for (int x = startX - searchRange; x <= startX + searchRange; x++) {
            for (int y = startY - searchRange; y <= startY + searchRange; y++) {
                if (x > 1 && x < mapWidth && y > 1 && y < mapHeight) {
                    if (baseLayer.getCell(x, y) != null && !baseLayer.getCell(x, y).getTile().getProperties().containsKey("border")) {
                        return true; // Found an existing chain nearby
                    }
                }
            }
        }
        return false; // No existing chain found nearby
    }


}