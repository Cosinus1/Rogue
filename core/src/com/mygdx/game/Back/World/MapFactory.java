package com.mygdx.game.Back.World;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import com.mygdx.game.Back.Object.Character.Ennemie.Ennemie;
import com.mygdx.game.Back.Object.Element.Door;

import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.PriorityQueue;
import java.util.Comparator;

public class MapFactory {

    private static int mapWidth = 30;
    private static int mapHeight = 20;
    private static int tilewidth = 32;
    private static int StartX = 0;
    private static int StartY = 3;

    private static final Logger logger = Logger.getLogger(MapFactory.class.getName());

    public Map createMap(float x, float y, TiledMap tiledmap, Music music, Map previousMap) {
        ArrayList<Door> doorList = new ArrayList<>();
        ArrayList<Ennemie> PNJ_list = new ArrayList<>();
        Door backDoor = new Door(x - 100, y, previousMap);
        doorList.add(backDoor);

        Map newMap = new Map(x, y, tiledmap, doorList, PNJ_list, music);
        return newMap;
    }

    public void addDoorBetweenMaps(Map previousMap, Map nextMap) {
        int mapWidth = previousMap.getmapWidth();
        int mapHeight = previousMap.getmapHeight();
        int[] doorCoordinates = generateRandomDoorCoordinates(mapWidth, mapHeight);
        Door doorToNextMap = new Door((float) doorCoordinates[0] * tilewidth, (float) doorCoordinates[1] * tilewidth,
                nextMap);
        previousMap.addDoor(doorToNextMap);
    }

    public int[] generateRandomDoorCoordinates(int mapWidth, int mapHeight) {
        Random random = new Random();
        int[] doorCoordinates = new int[2];
        int border = 1;
        switch (border) {
            case 0:
                doorCoordinates[0] = random.nextInt(mapWidth - 4) + 2;
                doorCoordinates[1] = mapHeight - 1;
                break;
            case 1:
                doorCoordinates[0] = mapWidth - 1;
                doorCoordinates[1] = random.nextInt(mapHeight - 4) + 2;
                break;
            case 2:
                doorCoordinates[0] = random.nextInt(mapWidth / 2) + 8;
                doorCoordinates[1] = 0;
                break;
        }
        return doorCoordinates;
    }

    /*
     * Multi Threading of TiledMapGenerators
     */
    public TiledMap run(TiledMapTileSets tileSets, int EndX, int EndY, Map previousMap) {
        TiledMapGenerator generator = new TiledMapGenerator(tileSets, EndX, EndY);
        logger.info(String.format("Thread %s has started", Thread.currentThread().getName()));

        CountDownLatch latch = new CountDownLatch(1);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                generator.generate(generatedMap -> {
                    logger.info(String.format("Thread %s has generated TiledMap", Thread.currentThread().getName()));
                    synchronized (previousMap) {
                        previousMap.updateTiledmap(generatedMap);
                        logger.info(String.format("Thread %s has updated Map", Thread.currentThread().getName()));
                        // previousMap.addWalls(false);
                        logger.info(String.format("Thread %s has added walls", Thread.currentThread().getName()));
                    }
                    latch.countDown(); // Signal that the work is done
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.severe("Map generation thread interrupted");
            }
        });

        try {
            latch.await(); // Wait for the map generation to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.severe("Main thread interrupted while waiting for map generation to complete");
        } finally {
            executor.shutdown();
        }

        return null;
    }
}
