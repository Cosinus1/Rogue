package com.mygdx.game.Back.World;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import com.mygdx.game.Back.Object.Character.Ennemie.Ennemie;
import com.mygdx.game.Back.Object.Element.Door;

import java.util.ArrayList;
import java.util.Random;
import java.util.PriorityQueue;
import java.util.Comparator;

public class MapFactory {

    private static int mapWidth = 30;
    private static int mapHeight = 20;
    private static int tilewidth = 32;

    private static int StartX = 0;
    private static int StartY = 3;


    public Map createMap(float x, float y, TiledMap tiledmap, Music music, Map previousMap) {
        ArrayList<Door> doorList = new ArrayList<>();
        ArrayList<Ennemie> PNJ_list = new ArrayList<>();
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
                baseLayer.setCell(StartX+i-1, StartY+j-1, null);
                baseLayer.setCell(EndX+i-1, EndY+j-1, null);
            }
        }
        //Add base walls inside the baseLayer
        generateRandomWalls(baseLayer, tileSets, EndX, EndY);
    
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
    
    public void generateRandomWalls(TiledMapTileLayer baseLayer, TiledMapTileSets tileSets, int endX, int endY) {

        Random randomWallstart = new Random();
        Random randomWallend = new Random();
    
        int i = 0;
        TiledMapTileLayer backupLayer;
        TiledMapTileLayer newLayer;

    
        while (i < 10) { //Number of chains
            boolean breakcondition = false;//Break if no position is found
            // Choose a random Wallstarting point along the border
            int WallstartX, WallstartY;
            //Choose a random Wallstarting point
            int WallendX, WallendY;

            //Get an independant copy of baseLayer to check for near walls (we dont want to check walls from current chain)
            backupLayer = cloneLayer(baseLayer);
            //Clone baseLayer (delete too short chains)
            newLayer = cloneLayer(baseLayer);

            // Ensure Wallstarting point is  on borders if i< 3 else not too close to the corners 
            int breakwhile = 0;
            if(i<8){
                    WallstartX = randomWallstart.nextInt(mapWidth-6)+3;
                    WallstartY = randomWallstart.nextInt(2)*(mapHeight-4)+1;
            }else{
            do {
                breakwhile++;
                WallstartX = randomWallend.nextInt(mapWidth - 10) + 5; 
                WallstartY = randomWallend.nextInt(mapHeight - 10) + 5; 
    
            } while (isNearAnotherChain(baseLayer, WallstartX, WallstartY) && breakwhile<2000);
            if(breakwhile>=2000) breakcondition = true;
            }
            // Set the initial coordinates
            int currentX = WallstartX;
            int currentY = WallstartY;
            int distanceX, distanceY;
            
            breakwhile = 0;
            do {
                breakwhile++;
                WallendX = randomWallend.nextInt(mapWidth - 10) + 5; 
                WallendY = randomWallend.nextInt(mapHeight - 10) + 5; 
                distanceX = (WallendX-currentX)*(WallendX-currentX);    
                distanceY = (WallendY-currentY)*(WallendY-currentY);
                
    
            } while ((distanceX+distanceY<2) && isNearAnotherChain(baseLayer, WallendX, WallendY) && breakwhile<2000);
            if(breakwhile>=2000) breakcondition = true;

            if(breakcondition == false){//Build the Chain if positions are OK

                int chainLength = 0; // Initialize chainLength variable
                //Init newCells 
                int[][] newCellPosition = new int[2][20];
                ArrayList<Cell> newCells = new ArrayList<>();
                
                int delta = 1;
                int direction = -1;
                int orientation = -1;

                while (chainLength < 10) { //Desired chainlength

                    //get the distance between Current Position and Wallend & orientation
                    distanceX = (WallendX-currentX)*(WallendX-currentX);
                    distanceY = (WallendY-currentY)*(WallendY-currentY);
                    int signX = (int) Math.signum(WallendX-currentX);
                    int signY = (int) Math.signum(WallendY-currentY);
                    int moveX = 0;
                    int moveY = 0;

                    Cell newCell = new Cell();

                    //Get appropriate wall sprite based on move
                    if(0<delta){
                        switch (signY) {
                            case -1:
                                if(direction == 0){
                                    if (orientation == -1) newCell = createCell(createWallTile(tileSets, "base", "down right"));
                                    else newCell = createCell(createWallTile(tileSets, "base", "left down"));

                                }
                                else newCell = createCell(createWallTile(tileSets, "base", "vertical"));
                                moveY--;
                                orientation = -1;
                                direction = 1;
                                break;
                        
                            case 1:
                                if(direction == 0){
                                    if (orientation == -1)newCell = createCell(createWallTile(tileSets, "base", "up right"));
                                    else newCell = createCell(createWallTile(tileSets, "base", "left up"));

                                }
                                else newCell = createCell(createWallTile(tileSets, "base", "vertical"));
                                moveY++;
                                orientation = 1;
                                direction = 1;
                                break; 
                        }
                        
                    }else{
                        switch (signX) {
                            case -1:
                                if(direction == 1){
                                    if (orientation == -1)newCell = createCell(createWallTile(tileSets, "base", "left up"));
                                    else newCell = createCell(createWallTile(tileSets, "base", "left down"));

                                }
                                else newCell = createCell(createWallTile(tileSets, "base", "horizontal"));
                                moveX--;
                                orientation = -1;
                                direction = 0;
                                break;
                        
                            case 1:
                                if(direction == 1){
                                    if (orientation == -1)newCell = createCell(createWallTile(tileSets, "base", "up right"));
                                    else newCell = createCell(createWallTile(tileSets, "base", "down right"));

                                }
                                else newCell = createCell(createWallTile(tileSets, "base", "horizontal"));
                                moveX++;
                                orientation = 1;
                                direction = 0;
                                break;
                        }
                    }

                    //Add newCell to newLayer
                    newLayer.setCell(currentX, currentY, newCell);
                    //Check if the newCell doesnt block the way to the exit
                    if(isValidTrajectory(StartX, StartY, endX, endY, newLayer) && isValidPosition(currentX, currentY, baseLayer)){
                        //Store Cell and position
                            newCells.add(newCell);
                            newCellPosition[0][chainLength] = currentX;
                            newCellPosition[1][chainLength] = currentY;
                            chainLength++;
                        //Make a move
                            currentX += moveX;
                            currentY += moveY;
                    }else break;
                    
                    //Check if we reached Wallend or if we are too close from another chain
                    if (distanceX + distanceY == 0 || isNearAnotherChain(backupLayer, currentX, currentY)) break;

                    delta = randomWallend.nextInt(4);
                
                }
                //Update baseLayer if the chain isnt too short
                if(chainLength>2){
                    int j = 0;
                    for(Cell newCell : newCells){
                        baseLayer.setCell(newCellPosition[0][j], newCellPosition[1][j], newCell);
                        j++;
                    }
                    newCells.clear();
                    i++; // Increment the chain count

                }
                
            }
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
    
    
    public boolean isValidPosition(int X, int Y, TiledMapTileLayer Base) {
        //Check Map boundaries
        if(X<1 || X>(mapWidth) || Y<1 || Y>(mapHeight-2)) return false;
        // Check distance from walls
        return checkDistancefromWall(X, Y, Base);
    }
    
    private boolean checkDistancefromWall(int X, int Y, TiledMapTileLayer Base){
        if (Base == null) System.out.println(Base);
        Cell cell = Base.getCell(X, Y);
        if (cell != null) {
            if(cell.getTile()!=null && cell.getTile().getProperties().containsKey("blocked")){
                return false;  
            }
        }
        return true;
    }

    private boolean isNearAnotherChain(TiledMapTileLayer baseLayer, int startX, int startY) {
        int searchRange = 3; // Define the range to search for existing chains
    
        for (int x = startX - searchRange; x <= startX + searchRange; x++) {
            for (int y = startY - searchRange; y <= startY + searchRange; y++) {
                if (x > 1 && x < mapWidth && y > 1 && y < mapHeight) {
                    if (baseLayer.getCell(x, y) != null){
                        if(baseLayer.getCell(x, y).getTile()!=null && baseLayer.getCell(x, y).getTile().getProperties().containsKey("wall") && !baseLayer.getCell(x, y).getTile().getProperties().containsKey("border")) {
                            return true; // Found an existing chain 
                        }
                    }
                }
            }
        }
        return false; // No existing chain found nearby
    }
    
    // Check if a path from Entry to Exit exists using A*
    private boolean isValidTrajectory(int startX, int startY, int endX, int endY, TiledMapTileLayer baseLayer) {

        // Closed list to keep track of visited nodes
        boolean[][] closed = new boolean[mapWidth][mapHeight];

        // Priority queue to store nodes with the lowest cost
        PriorityQueue<Node> queue = new PriorityQueue<>(mapWidth * mapHeight, new Comparator<Node>() {
            @Override
            public int compare(Node a, Node b) {
                return a.cost - b.cost;
            }
        });

        // Cost and heuristic arrays to store costs and heuristic values for each node
        int[][] cost = new int[mapWidth][mapHeight];
        int[][] heuristic = new int[mapWidth][mapHeight];

        // Initialize cost and heuristic arrays
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                cost[x][y] = Integer.MAX_VALUE;
                heuristic[x][y] = Math.abs(endX - x) + Math.abs(endY - y);
            }
        }

        // Add the starting node to the queue and set its cost to 0
        queue.add(new Node(startX, startY, 0));
        cost[startX][startY] = 0;

        // A* algorithm
        while (!queue.isEmpty()) {
            // Retrieve and remove the node with the lowest cost from the queue
            Node current = queue.poll();
            int x = current.x;
            int y = current.y;

            // Mark the current node as visited
            closed[x][y] = true;

            // Check if reached the exit node
            if (x == endX && y == endY) {
                return true;
            }

            // Explore neighbors
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == j) {
                        continue;
                    }

                    int newX = x + i;
                    int newY = y + j;

                    // Check if the neighbor is within the map boundaries and not visited
                    if (newX >= 0 && newX < mapWidth && newY >= 0 && newY < mapHeight &&
                            !closed[newX][newY] && isValidPosition(newX, newY, baseLayer)) {
                        int newCost = cost[x][y] + 1;

                        // Update the cost if the new cost is lower
                        if (newCost < cost[newX][newY]) {
                            cost[newX][newY] = newCost;
                            int priority = newCost + heuristic[newX][newY];

                            // Add the neighbor to the queue with updated cost and priority
                            queue.add(new Node(newX, newY, priority));
                        }
                    }
                }
            }
        }
        // If no path found, return false
        return false;
    }

    //Adding a node class for A* 
    private static class Node {
        int x;
        int y;
        int cost;

        public Node(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }

}