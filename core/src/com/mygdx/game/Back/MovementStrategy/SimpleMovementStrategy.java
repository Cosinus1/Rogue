package com.mygdx.game.Back.MovementStrategy;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Back.Object.Object;
import com.mygdx.game.Back.World.Map;

public class SimpleMovementStrategy implements MovementStrategy {

    @Override
    public Vector2 getNextPosition(Object object, Map map) {
       
        float newX = object.getX() + 1;
        float newY = object.getY();

        // Check if the new position is valid on the map
        if (object.isValidPosition((int) (newX / map.getTilewidth()), (int) (newY / map.getTilewidth()), map)) {
            return new Vector2(newX, newY);
        } else {
            // If the new position is not valid, stay in the current position
            return new Vector2(object.getX(), object.getY());
        }
    }
}