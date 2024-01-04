package com.mygdx.game.Graphic.World.Map;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.Graphic.Elements.Door;
import com.mygdx.game.Graphic.GraphicCharacter.GraphicEnnemie;

import java.util.ArrayList;
import java.util.Random;

public class MapFactory {

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
        Door doorToNextMap = new Door((float) doorCoordinates[0]*32, (float) doorCoordinates[1]*32, nextMap);
        previousMap.addDoor(doorToNextMap);
    }

    public TiledMap createRandomTiledMap(TiledMapTileSets tileSets, int EndX, int EndY) {
        System.out.println("////////////////////////// CREATING RANDOM MAP..... ////////////////////////////");
        int mapWidth = 30;
        int mapHeight = 20;
        int StartX = 0;
        int StartY = 3;
    
        // Create a new TiledMap
        TiledMap tiledMap = new TiledMap();
    
        // Create TiledMapTileLayers for ground, base, middle, and top parts of walls
        TiledMapTileLayer groundLayer = new TiledMapTileLayer(mapWidth, mapHeight, 32, 32);
        TiledMapTileLayer baseLayer = new TiledMapTileLayer(mapWidth, mapHeight, 32, 32);
        TiledMapTileLayer middleLayer = new TiledMapTileLayer(mapWidth, mapHeight, 32, 32);
        TiledMapTileLayer topLayer = new TiledMapTileLayer(mapWidth, mapHeight, 32, 32);
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
            baseLayer.setCell(i, mapHeight - 2, createCell(createWallTile(tileSets, "base", "horizontal")));
        }
        for (int i = 1; i < mapHeight-1; i++) {
            baseLayer.setCell(0, i, createCell(createWallTile(tileSets, "base", "vertical")));
            baseLayer.setCell(mapWidth-1, i, createCell(createWallTile(tileSets, "base", "vertical")));
        }
        System.out.println("base walls added on borders");
        // Add openings for doors (specify the positions and tiles for openings)
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                baseLayer.setCell(StartX+i-1, StartY+j-1, createCell(createOpeningTile(tileSets)));
                baseLayer.setCell(EndX+i-1, EndY+j-1, createCell(createOpeningTile(tileSets)));
            }
        }
        System.out.println("door openings added");
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
        System.out.println("layers added");
    
        // Return the new map
        System.out.println("////////////////////////////RANDOM MAP CREATED/////////////////////////////////");
        return tiledMap;
    }
    
    public void generateRandomWalls(TiledMapTileLayer baseLayer, TiledMapTileSets tileSets) {
        int mapWidth = baseLayer.getWidth();
        int mapHeight = baseLayer.getHeight();
    
        Random random = new Random();
    
        int i = 0;
    
        while (i < 2) { // You can adjust the number of chains (5 is just an example)
            // Choose a random starting point along the border
            int startX;
            int startY;
            
            System.out.println("NEW CHAIN");

            // Ensure starting point is not too close to the corners
            int breakwhile = 0;
            do {
                breakwhile++;
                startX = random.nextInt(mapWidth - 10) + 5; 
                startY = random.nextInt(mapHeight - 10) + 5; 
    
            } while (isNearAnotherChain(baseLayer, startX, startY) && breakwhile<200);

            int chainLength = 0; // Initialize chainLength variable
            // Set the initial coordinates
            int currentX = startX;
            int currentY = startY;
            //int lastX, lastY; //store last X,Y to rewind in case no moves are possible
            int direction; //Set the direction and stores the last one
            int lastdirection = -1;
            int directionChanges = 0; // Initialize directionChanges variable
            int maxChanges = 2; // Set the MAX changes for the chain
            int breakcondition = 0;//Set a break timer if walls cant be added
            double constanteDirectionchance = 0.9;
            double chance;

            int nb_message = 0; //for MAX REACHED message to be printed only once
            
    
            while (chainLength < 20) { //Desired chainlength

                //if too many changes were made, keep the last direction
                if(directionChanges <= maxChanges){
                    
                    // Randomly select the next direction to extend the wall chain
                    
                    // Check if UP direction is valid (same as down)
                    boolean upValid = (currentX > 2) && (currentX < mapWidth-2);
                    // Check if RIGHT direction is valid (same as left)
                    boolean rightValid = (currentY > 2) && (currentY < mapHeight-1);
        
                    // If UP direction is valid and not close to a map wall, set the direction
                    if (upValid) {
                        //Same for RIGHT
                        if(rightValid)
                            do{
                                chance = Math.random();
                                direction = random.nextInt(4); // 0: up, 1: right, 2: left, 3: down
                            }while (chance<constanteDirectionchance && direction!=lastdirection && lastdirection!=-1);
                        else{
                            do{
                                direction = random.nextInt(4);
                            }while (direction == 1); // 0: up, 1: right, 2: left, 3: down
                        }
                    } else {// we take as supposition that both valid bools cant be false at the same time (corner situation)
                        // If not, choose any direction except UP
                        do {
                            direction = random.nextInt(4); // 0: up, 1: right, 2: left, 3: down
                        } while (direction == 0);
                    }
                    // Check if the direction might create a "dead end" or if walls are too close
                    boolean wallsTooClose = false;
                    switch (direction) {
                        case 0: // Up
                            if (currentY >= mapHeight || baseLayer.getCell(currentX, currentY) != null || baseLayer.getCell(currentX-1, currentY+1) != null || baseLayer.getCell(currentX+1, currentY+1) != null) {
                                wallsTooClose = true;
                            }
                            break;
                        case 1: // Right
                            if (currentX >= mapWidth || baseLayer.getCell(currentX, currentY) != null || baseLayer.getCell(currentX+1, currentY-1) != null || baseLayer.getCell(currentX+1, currentY+1) != null) {
                                wallsTooClose = true;
                            }
                            break;
                        case 2: // Left
                            if (currentX < 0 || baseLayer.getCell(currentX, currentY) != null || baseLayer.getCell(currentX-1, currentY-1) != null || baseLayer.getCell(currentX-1, currentY+1) != null) {
                                wallsTooClose = true;
                            }
                            break;
                        case 3: // Down
                            if (currentY < 0 || baseLayer.getCell(currentX, currentY) != null || baseLayer.getCell(currentX-1, currentY-1) != null || baseLayer.getCell(currentX+1, currentY-1) != null) {
                                wallsTooClose = true;
                            }
                            break;
                    }

                    // If walls are too close dont move
                    if (wallsTooClose) {
                        direction = -1;
                        breakcondition++;
                    }
                    //increase direction changes count if change and update lastdirection
                    else {
                        if(direction != lastdirection && direction!=-1) directionChanges++;
                        //store last direction here (avoid storing -1 when no move)
                        //lastdirection = direction;
                        // Increment chain length
                        chainLength ++;
                        // Reset breakcondition
                        breakcondition = 0;
                    }

                }else {
                    nb_message++;
                    if (nb_message<2) System.out.println("MAX REACHED :" + lastdirection);
                    direction = lastdirection; // MAX changes reached
                    // Increment chain length
                    chainLength ++;
                }
                // Move in the selected direction
                switch (direction) {
                    case 0: // Up
                        if (lastdirection==1){
                            baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "left up")));
                        }
                        else if (lastdirection==2){
                            baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "up right")));
                        }
                        else baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "vertical")));
                        currentY++;
                        break;
                    case 1: // Right
                        if (lastdirection==0){
                            baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "down right")));
                        }
                        else if (lastdirection==3){
                            baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "up right")));
                        }
                        else baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "horizontal")));
                        currentX++;
                        break;
                    case 2: // Left
                        if (lastdirection==0){
                            baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "left down")));
                        }
                        else if (lastdirection==3){
                            baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "left up")));
                        }
                        else baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "horizontal")));
                        currentX--;
                        break;
                    case 3: // Down
                        if (lastdirection==1){
                            baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "left down")));
                        }
                        else if (lastdirection==2){
                            baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "down right")));
                        }
                        else baseLayer.setCell(currentX, currentY, createCell(createWallTile(tileSets, "base", "vertical")));
                        currentY--;
                        break;
                }
                if (direction!=-1) lastdirection = direction;
                // Reset chain if it gets too close to the border
                if (currentX <= 0 || currentX >= mapWidth+1 || currentY <= 0 || currentY >= mapHeight+1|| breakcondition>100) {
                    System.err.println("break condition : " + breakcondition);
                    break;
                }
            }
    
            i++; // Increment the chain count
        }
    }
    
    private boolean isNearAnotherChain(TiledMapTileLayer baseLayer, int startX, int startY) {
        int searchRange = 4; // Define the range to search for existing chains
    
        for (int x = startX - searchRange; x <= startX + searchRange; x++) {
            for (int y = startY - searchRange; y <= startY + searchRange; y++) {
                if (x >= 0 && x < baseLayer.getWidth() && y >= 0 && y < baseLayer.getHeight()) {
                    if (baseLayer.getCell(x, y) != null) {
                        return true; // Found an existing chain nearby
                    }
                }
            }
        }
        return false; // No existing chain found nearby
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

        int border = random.nextInt(3);
        
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
        //System.out.println("DOOR COORDINATES : " + doorCoordinates[0] + doorCoordinates[1]);
        return doorCoordinates;
    }

}