package com.mygdx.game.Back.Object.Element;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.MapLayers;

public class WallFactory {
    protected TiledMapTileLayer baseLayer;
    protected TiledMapTileLayer middleLayer;
    protected TiledMapTileLayer topLayer;
    protected MapLayers Layers;

    public WallFactory(TiledMap tiledmap) {
        MapLayers layers = tiledmap.getLayers();
        baseLayer = (TiledMapTileLayer) layers.get("Base");
        middleLayer = (TiledMapTileLayer) layers.get("Middle");
        topLayer = (TiledMapTileLayer) layers.get("Top");
    }

    public Wall createWall(int x, int y) {

        TextureRegion wallTexture = getTextureRegionSafely(x, y);

        if (wallTexture != null)
            return new Wall(x * baseLayer.getTileWidth(), y * baseLayer.getTileHeight(), 32, 32, wallTexture);
        else
            return null;
    }

    private TextureRegion getTextureRegionSafely(int x, int y) {
        TextureRegion baseTexture = getTextureRegionSafely(baseLayer, x, y);
        TextureRegion middleTexture = getTextureRegionSafely(middleLayer, x, y);
        TextureRegion topTexture = getTextureRegionSafely(topLayer, x, y);

        if (topTexture != null) {
            return topTexture;
        } else if (middleTexture != null) {
            return middleTexture;
        } else {
            return baseTexture;
        }
    }

    private TextureRegion getTextureRegionSafely(TiledMapTileLayer layer, int x, int y) {
        if (layer != null) {
            TiledMapTileLayer.Cell cell = layer.getCell(x, y);
            if (cell != null && cell.getTile() != null) {
                return cell.getTile().getTextureRegion();
            }
        }
        return null;
    }
}
